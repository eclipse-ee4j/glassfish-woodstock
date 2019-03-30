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
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.IFrame;

import static com.sun.webui.jsf.util.RenderingUtilities.isPortlet;

/**
 * Renderer for a {@link IFrameRenderer} component.
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.IFrame"))
public class IFrameRenderer extends FrameRenderer {

    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        if (!isPortlet(context)) {
            writer.startElement("iframe", component);
        }
    }

    @Override
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        IFrame frame = (IFrame) component;

        super.renderAttributes(context, component, writer);

        // Render a nested "head" element
        if (!isPortlet(context)) {
            String align = frame.getAlign();
            if (align != null) {
                writer.writeAttribute("align", align, null);
            }

            // marginWidth
            String width = frame.getWidth();
            if (width != null) {
                writer.writeAttribute("width", width, null);
            }
            // marginHeight
            String height = frame.getHeight();
            if (height != null) {
                writer.writeAttribute("height", height, null);
            }
        }
    }

    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        // End the outermost "html" element
        if (!isPortlet(context)) {
            writer.endElement("iframe"); //NOI18N
            writer.write("\n"); //NOI18N
        }

    }

    @Override
    protected void renderResizeAttribute(ResponseWriter writer, UIComponent comp)
            throws IOException {
    }
}
