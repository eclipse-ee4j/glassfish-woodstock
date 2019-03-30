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
import com.sun.webui.jsf.component.Frame;

import static com.sun.webui.jsf.util.RenderingUtilities.isPortlet;
import static com.sun.webui.jsf.util.RenderingUtilities.renderURLAttribute;

/**
 * Renderer for a {@link Frame} component.
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Frame"))
public class FrameRenderer extends AbstractRenderer {

    /**
     * The set of String pass-through attributes to be rendered.
     */
    private static final String STRING_ATTRIBUTES[] = {
        "name",
        "scrolling"
    };

    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        Frame frame = (Frame) component;

        // I don't think this is the correct way to write the XML header
        if (!isPortlet(context)) {
            writer.startElement("frame", component);
        }
    }

    @Override
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        Frame frame = (Frame) component;

        // Render a nested "head" element
        if (!isPortlet(context)) {
            String id = frame.getClientId(context);
            if (id != null) {
                writer.writeAttribute("id", id, null);
            }
            String url = frame.getUrl();
            if (url != null) {
                // Append context path to relative URLs -- bugtraq #6338307.
                url = context.getApplication().getViewHandler().
                        getResourceURL(context, url);

                renderURLAttribute(context, writer, component, "src",
                        url, null);
            }
            //class
            String styleClass = frame.getStyleClass();
            if (styleClass != null) {
                writer.writeAttribute("class", styleClass, null);
            }
            //style
            String style = frame.getStyle();
            if (style != null) {
                writer.writeAttribute("style", style, null);
            }
            //write out the rest of the attributes
            addStringAttributes(context, component, writer, STRING_ATTRIBUTES);

            //frameborder
            boolean border = frame.isFrameBorder();
            if (border) {
                writer.writeAttribute("frameborder", "1", null);
            } else {
                writer.writeAttribute("frameborder", "0", null);
            }
            //longdesc
            String longdesc = frame.getLongDesc();
            if (longdesc != null) {
                writer.writeAttribute("longdesc", longdesc, null);
            }
            //marginWidth
            Integer marginWidth = frame.getMarginWidth();
            if (frame.getMarginWidth() >= 0) {
                writer.writeAttribute("marginwidth", marginWidth.toString(),
                        null);
            }
            //marginHeight
            Integer marginHeight = frame.getMarginHeight();
            if (frame.getMarginHeight() >= 0) {
                writer.writeAttribute("marginheight", marginHeight.toString(),
                        null);
            }

            renderResizeAttribute(writer, component);

            //tooltip
            String toolTip = frame.getToolTip();
            if (toolTip != null) {
                writer.writeAttribute("title", toolTip, "toolTip");
            }
        }

    }

    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        // End the outermost "html" element
        if (!isPortlet(context)) {
            writer.write(" />");
            writer.write("\n");
        }
    }

    protected void renderResizeAttribute(ResponseWriter writer,
            UIComponent comp) throws IOException {

        boolean noresize = ((Frame) comp).isNoResize();
        if (noresize) {
            writer.writeAttribute("noresize", "noresize", null);
        }
    }
}
