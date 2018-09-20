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

import com.sun.webui.jsf.component.Selector;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;

/**
 * A delegating renderer base class for {@link com.sun.webui.jsf.component.CheckboxGroup}
 * and {@link com.sun.webui.jsf.component.RadioButtonGroup}. If component's items
 * property is not bound, outputs a minimal block of markup so that the component
 * can be manipulated on the design surface.
 *
 * @author gjmurphy
 */
public abstract class RbCbGroupDesignTimeRenderer extends SelectorDesignTimeRenderer {
    
    String label;
    
    /** Creates a new instance of RbCbGroupDesignTimeRenderer */
    public RbCbGroupDesignTimeRenderer(Renderer renderer, String label) {
        super(renderer);
        this.label = label;
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component instanceof Selector) {
            Selector selector = (Selector) component;
            ValueBinding itemsBinding = selector.getValueBinding("items");
            if (itemsBinding == null) {
                ResponseWriter writer = context.getResponseWriter();
                writer.startElement("div", component);
                String style = selector.getStyle();
                writer.writeAttribute("style", style, "style");
                writer.startElement("span", component);
                writer.writeAttribute("class", super.UNINITITIALIZED_STYLE_CLASS, "class");
                char[] chars = label.toCharArray();
                writer.writeText(chars, 0, chars.length);
                writer.endElement("span");
                writer.endElement("div");
            }
        }
        super.encodeBegin(context, component);
    }
    
}
