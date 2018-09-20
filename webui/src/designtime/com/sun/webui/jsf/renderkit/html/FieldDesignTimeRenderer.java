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

import com.sun.webui.jsf.component.Field;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.io.IOException;
import javax.faces.render.Renderer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;


/**
 * A delegating renderer for components based on {@link com.sun.webui.jsf.component.Field}.
 * This delegating renderer takes over when the component is read-only, since the
 * field renderers replace the field component with a proxy component when the
 * <code>readOnly</code> property is true, which makes the resulting HTML unselectable
 * on the designer. This delegating renderer also provides a shadow text value
 * when the component is read-only and there is no text value.
 *
 * @author gjmurphy
 */
public class FieldDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    /** Creates a new instance of FieldDesignTimeRenderer */
    public FieldDesignTimeRenderer(Renderer renderer) {
        super(renderer);
    }
    
    protected String getShadowText(FacesContext context, Field field) {
        return DesignMessageUtil.getMessage(FieldDesignTimeRenderer.class, "field.readOnly.value");
    }
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      
        Field field = (Field) component;        
        if (field.isReadOnly()) {
            ResponseWriter writer = context.getResponseWriter();
            Object value = field.getText();
            writer.startElement("span", field); // NOI18N
            writer.writeAttribute("id", field.getId(), "id"); //NOI18N
            String style = field.getStyle();
            if (style != null && style.length() > 0)
                writer.writeAttribute("style", style, null); //NOI18N
            String styleClass = field.getStyleClass();
            StringBuffer styleClassBuffer = new StringBuffer();
            if (styleClass != null)
                styleClassBuffer.append(styleClass);
            UIComponent label = field.getLabelComponent(context, "");
            if (label != null) {
                writer.writeAttribute("class", styleClassBuffer.toString(), null); // NOI18N
                styleClassBuffer.setLength(0);
                RenderingUtilities.renderComponent(label, context);
                Theme theme = ThemeUtilities.getTheme(context);
                Icon icon = ThemeUtilities.getIcon(theme, ThemeImages.DOT);
                icon.setId(component.getId().concat("_spacer")); //NOI18N
                icon.setHeight(1);
                icon.setWidth(10);
                RenderingUtilities.renderComponent(icon, context);
                writer.startElement("span", field); // NOI18N
                writer.writeAttribute("id", field.getId().concat("_readOnly"), "id"); //NOI18N
            }
            if (value == null) {
                if (styleClassBuffer.length() > 0)
                    styleClassBuffer.append(' ');
                styleClassBuffer.append(UNINITITIALIZED_STYLE_CLASS);
                writer.writeAttribute("class", styleClassBuffer.toString(), null); // NOI18N
                writer.writeText(getShadowText(context, field), null); // NOI18N
            } else {
                String stringValue = getTextValue(field, value);
                writer.writeAttribute("class", styleClassBuffer.toString(), null); // NOI18N
                writer.writeText(stringValue, null);
            }
            writer.endElement("span"); // NOI18N
        } else {
            super.encodeBegin(context, component);
        }
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        Field field = (Field) component;
        if (!field.isReadOnly())
            super.encodeEnd(context, component);
    }
    
    protected String getTextValue(Field field, Object value) {
        String stringValue = ConversionUtilities.convertValueToString
            (field, value);
        return stringValue;
    }
    
}
