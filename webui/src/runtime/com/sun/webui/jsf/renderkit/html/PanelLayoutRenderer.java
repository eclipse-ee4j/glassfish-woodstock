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
import com.sun.webui.jsf.component.PanelLayout;
import com.sun.webui.jsf.util.RenderingUtilities;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * Renderer for a {@link com.sun.webui.jsf.component.PanelLayout} component.
 *
 * @author gjmurphy
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.PanelLayout"))
public class PanelLayoutRenderer extends AbstractRenderer {

    @Override
    protected void renderStart(FacesContext context, UIComponent component, ResponseWriter writer)
            throws IOException {
        writer.startElement("div", component); //NOI18N
    }

    @Override
    protected void renderAttributes(FacesContext context, UIComponent component, ResponseWriter writer)
            throws IOException {
        PanelLayout panelLayout = (PanelLayout) component;
        StringBuffer buffer = new StringBuffer();

        // Write id attribute
        String id = component.getId();
        writer.writeAttribute("id", panelLayout.getClientId(context), "id");

        // Write style attribute
        if (PanelLayout.GRID_LAYOUT.equals(panelLayout.getPanelLayout())) {
            buffer.append("position: relative; -rave-layout: grid;"); //NOI18N
        }
        String style = panelLayout.getStyle();
        if (style != null && style.length() > 0) {
            buffer.append(" ");
            buffer.append(style);
        }
        writer.writeAttribute("style", buffer.toString(), "style");

        // Write style class attribute
        RenderingUtilities.renderStyleClass(context, writer, component, null);
    }

    @Override
    protected void renderEnd(FacesContext context, UIComponent component, ResponseWriter writer)
            throws IOException {
        writer.endElement("div"); //NOI18N
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }
}
