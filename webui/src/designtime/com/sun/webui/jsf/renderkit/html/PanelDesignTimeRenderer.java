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

import com.sun.webui.jsf.component.Tab;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

/**
 * A delegating renderer for panel-like components. Outputs default minimum
 * CSS width and height settings if panel contains no children.
 *
 * @author gjmurphy
 */
public abstract class PanelDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    /** Creates a new instance of PanelDesignTimeRenderer */
    public PanelDesignTimeRenderer(Renderer renderer) {
        super(renderer);
    }
    
    abstract protected String getContainerElementName(UIComponent component);
    
    abstract protected String getShadowText(UIComponent component);
    
    abstract protected String getStyle(UIComponent component);
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component.getChildCount() == 0) {
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement(getContainerElementName(component), component);
            String id = component.getId();
            writer.writeAttribute("id", id, "id"); //NOI18N
            
            // Calculate CSS height and width style settings
            UIComponent parentComponent = component.getParent();
            StringBuffer sb = new StringBuffer();
            String style = (String) component.getAttributes().get("style");
            if ((style != null) && (style.length() > 0)) {
                sb.append(style);
                sb.append("; "); // NOI18N
            }
            if (style == null || style.indexOf("width:") == -1) {
                sb.append("width: 128px; "); // NOI18N
            }
            if (style == null || style.indexOf("height:") == -1) {
                if ("span".equals(getContainerElementName(component))) {
                    sb.append("height: 24px; "); // NOI18N
                } else {
                    sb.append("height: 128px; "); // NOI18N
                }
            }
            style = this.getStyle(component);
            if (style != null && style.length() > 0) {
                sb.append(style);
                sb.append("; ");
            }
            writer.writeAttribute("style", sb.toString(), null); //NOI18N
            sb.setLength(0);
            
            // Calculate CSS style classes
            String styleClass = (String) component.getAttributes().get("styleClass");
            if ((styleClass != null) && (styleClass.length() > 0)) {
                sb.append(styleClass);
                sb.append(" ");
            }
            sb.append(UNINITITIALIZED_STYLE_CLASS);
            sb.append(" ");
            sb.append(BORDER_STYLE_CLASS);
            writer.writeAttribute("class", sb.toString(), null); // NOI18
            
            writer.writeText(getShadowText(component), null);
            writer.endElement(getContainerElementName(component));
            
            return;
        }
        
        super.encodeBegin(context, component);
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if (component.getChildCount() == 0) {
            return;
        }
        
        super.encodeEnd(context, component);
    }
    
}
