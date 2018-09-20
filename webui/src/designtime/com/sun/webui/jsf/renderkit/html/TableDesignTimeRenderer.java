/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.Table;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * This class renders Table components and is a subclass of TableRenderer.
 * It is the same as the superclass in every respect except this class
 * implements <code>renderEnclosingTagStart</code> and <code>
 * enclosingTagEnd</code>. The difference is in the number of "div" elements
 * used and which HTML element is associated with the Table component's id.
 */
public class TableDesignTimeRenderer extends TableRenderer {
    /**
     * Render enclosing tag for Table components.
     * This implementation associates the component id with the table
     * element.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderEnclosingTagStart(FacesContext context,
            Table component, ResponseWriter writer) throws IOException {
        if (component == null) {
	    Class clazz = this.getClass();
            if (LogUtil.fineEnabled(clazz)) {
		// Log method name and message.
		LogUtil.fine(clazz, clazz.getName() + 
		    ".renderEnclosingTagStart: " + //NOI18N
		    "Cannot render enclosing tag, Table is null"); //NOI18N
	    }
            return;
        }

        Theme theme = ThemeUtilities.getTheme(context);
        
        // Render table.
        writer.writeText("\n", null); //NOI18N
        writer.startElement("table", component); //NOI18N
        writer.writeAttribute("id", //NOI18N
		component.getClientId(context), null);

        // Render style.
	String style = component.getStyle();
	StringBuffer buff = new StringBuffer(128);
        if (style != null) {
	    buff.append(style).append(";"); //NOI18N
	}

	String width = component.getWidth();
	if (width != null){ 
	    buff.append("width: "); //NOI18N

	    // If not a percentage, units are in pixels.
	    if (width.indexOf("%") == -1) { //NOI18N
		buff.append(width).append("px"); //NOI18N
            } else  {
		buff.append(width);
	    }
	    buff.append(";"); //NOI18N
	} else {
	    buff.append("width: 100%;"); //NOI18N
	}

	// There will always be at least "width: 100%" in buffer.
	writer.writeAttribute("style", buff.toString(), null); //NOI18N
            
        // Get default table style class.
        String styleClass = theme.getStyleClass(ThemeStyles.TABLE);

        if (component.isLite()) {
            styleClass += " " +  //NOI18N
		theme.getStyleClass(ThemeStyles.TABLE_LITE);
        }
        
	// This call renders the hidden  style class as appropriate.
	// Note that creator does not override the default style
	// in deference to the developer set styleClass but incudes both.
	// (This was the case even before using "renderStyleClass" for
	// this implementation)
	//
	RenderingUtilities.renderStyleClass(context, writer, component,
		styleClass);

	renderTableAttributes(context, component, writer);
    }

    /**
     * Render enclosing tag for Table components.
     * This implementation ends the table element rendered in 
     * renderEnclosingTagStart.
     *
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderEnclosingTagEnd(ResponseWriter writer)
            throws IOException {
        writer.endElement("table"); //NOI18N
    }
}
