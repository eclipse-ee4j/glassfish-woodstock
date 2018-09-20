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

import com.sun.webui.jsf.component.TabSet;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.TabSet} that renders
 * a minimal block of markup when the there are no tab children.
 *
 * @author gjmurphy
 */
public class TabSetDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    /** Creates a new instance of TabSetDesignTimeRenderer */
    public TabSetDesignTimeRenderer() {
        super(new TabSetRenderer());
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component instanceof TabSet && component.getChildCount() == 0) {
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("div", component);
            String style = ((TabSet) component).getStyle();
            writer.writeAttribute("style", style, "style");
            writer.startElement("span", component);
            writer.writeAttribute("class", super.UNINITITIALIZED_STYLE_CLASS, "class");
            String label = DesignMessageUtil.getMessage(TabSetDesignTimeRenderer.class, "tabSet.label");
            char[] chars = label.toCharArray();
            writer.writeText(chars, 0, chars.length);
            writer.endElement("span");
            writer.endElement("div");
        } else {
            super.encodeBegin(context, component);
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if (!(component instanceof TabSet && component.getChildCount() == 0))
            super.encodeEnd(context, component);
    }
    
}
