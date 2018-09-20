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
 * $Id: IFrameRenderer.java,v 1.1.20.1 2009-12-29 04:52:43 jyeary Exp $
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.IFrame;
import com.sun.webui.jsf.util.RenderingUtilities;

/**
 * <p>Renderer for a {@link IFrameRenderer} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.IFrame"))
public class IFrameRenderer extends FrameRenderer {

    // -------------------------------------------------------- Renderer Methods
    /**
     * <p>Render the appropriate element start for the outermost
     * element.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component component to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        IFrame frame = (IFrame) component;

        // I don't think this is the correct way to write the XML
        // header /avk

        if (!RenderingUtilities.isPortlet(context)) {
            writer.startElement("iframe", component);
        }
    }

    /**
     * <p>Render the appropriate element attributes, followed by the
     * nested <code>&lt;head&gt;</code> element, plus the beginning
     * of a nested <code>&lt;body&gt;</code> element.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component component to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        IFrame frame = (IFrame) component;

        super.renderAttributes(context, component, writer);

        // Render a nested "head" element
        if (!RenderingUtilities.isPortlet(context)) {
            //align
            String align = frame.getAlign();
            if (align != null) {
                writer.writeAttribute("align", align, null); //NOI18N
            }

            //marginWidth
            String width = frame.getWidth();
            if (width != null) {
                writer.writeAttribute("width", width.toString(), null); //NOI18N
            }
            //marginHeight
            String height = frame.getHeight();
            if (height != null) {
                writer.writeAttribute("height", height.toString(), null); //NOI18N
            }
        }
    }

    /**
     * <p>Render the appropriate element end.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component component to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        // End the outermost "html" element
        if (!RenderingUtilities.isPortlet(context)) {
            writer.endElement("iframe"); //NOI18N
            writer.write("\n"); //NOI18N
        }

    }

    // ------------------------------------------------------- Protected Methods
    @Override
    protected void renderResizeAttribute(ResponseWriter writer, UIComponent comp)
            throws IOException {
        //intentionally blank
    }
}
