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

/*
 * $Id: PageSeparatorRenderer.java,v 1.1.20.1 2009-12-29 04:52:46 jyeary Exp $
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.PageSeparator;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * <p>Renderer for a {@link PageSeparator} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.PageSeparator"))
public class PageSeparatorRenderer extends AbstractRenderer {


    // ======================================================== Static Variables
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] = {"onClick", "onDblClick", "onMouseUp", //NOI18N
        "onMouseDown", "onMouseMove", "onMouseOut", "onMouseOver"}; //NOI18N


    // -------------------------------------------------------- Renderer Methods
    /**
     * <p>Render the appropriate element start, depending on whether the
     * <code>for</code> property is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component StaticText component
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
    }

    /**
     * <p>Render the appropriate element attributes, followed by the
     * label content, depending on whether the <code>for</code> property
     * is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component StaticText component
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
    }

    /**
     * <p>Render the appropriate element end, depending on whether the
     * <code>for</code> property is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        PageSeparator pageSep = (PageSeparator) component;

        writer.startElement("table", component);
        String style = pageSep.getStyle();
        if (style != null) {
            writer.writeAttribute("style", style, null); // NOI18N
        }
        RenderingUtilities.renderStyleClass(context, writer, component, null);
        writer.writeAttribute("border", "0", null); // NOI18N
        writer.writeAttribute("width", "100%", null); // NOI18N
        writer.writeAttribute("cellpadding", "0", null); // NOI18N
        writer.writeAttribute("cellspacing", "0", null); // NOI18N        
        writer.startElement("tr", component);
        writer.startElement("td", component);
        writer.writeAttribute("colspan", "3", null); // NOI18N

        RenderingUtilities.renderSpacer(context, writer, component, 30, 1);
        writer.endElement("td");
        writer.endElement("tr");
        writer.startElement("tr", component);
        writer.startElement("td", component);
        RenderingUtilities.renderSpacer(context, writer, component, 1, 10);
        writer.endElement("td");
        writer.startElement("td", component);
        Theme theme = ThemeUtilities.getTheme(context);

        writer.writeAttribute("class", theme.getStyleClass(
                ThemeStyles.TITLE_LINE), null); // NOI18N
        writer.writeAttribute("width", "100%", null); // NOI18N
        RenderingUtilities.renderSpacer(context, writer, component, 1, 1);
        writer.endElement("td");
        writer.startElement("td", component);
        RenderingUtilities.renderSpacer(context, writer, component, 1, 10);
        writer.endElement("td");
        writer.endElement("tr");
        writer.endElement("table");
    }
    // --------------------------------------------------------- Private Methods
}
