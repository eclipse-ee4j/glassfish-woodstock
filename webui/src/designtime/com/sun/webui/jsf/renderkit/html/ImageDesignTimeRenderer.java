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

import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
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
 * A delegating renderer for {@link com.sun.webui.jsf.component.ImageComponent}.
 * This delegating renderer takes over when the component has no image or icon
 * property set, outputting the component's display name.
 *
 * @author gjmurphy
 */
public class ImageDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    public ImageDesignTimeRenderer() {
        super(new ImageRenderer());
    }
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ImageComponent image = (ImageComponent) component;
        if (image.getUrl() == null && image.getIcon() == null) {
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("span", image); // NOI18N
            writer.writeAttribute("id", image.getId(), "id"); //NOI18N
            writer.writeAttribute("style", image.getStyle(), "style"); //NOI18N
            writer.writeText("<" + DesignMessageUtil.getMessage(StaticTextDesignTimeRenderer.class, "image.label") + ">", null); //NOI18N
            writer.endElement("span"); // NOI18N
        } else {
            super.encodeBegin(context, component);
        }
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ImageComponent image = (ImageComponent) component;
        if (image.getUrl() != null || image.getIcon() != null) {
            super.encodeEnd(context, component);
        }
    }
    
}
