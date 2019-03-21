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
 * $Id: FrameSetRenderer.java,v 1.1.12.1 2009-12-29 04:52:47 jyeary Exp $
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.FrameSet;
import com.sun.webui.jsf.util.RenderingUtilities;

/**
 * <p>Renderer for a {@link FrameSet} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.FrameSet"))
public class FrameSetRenderer extends AbstractRenderer {

    // ======================================================== Static Variables
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] = {"rows", "cols", "borderColor"}; //NOI18N
    private static final String integerAttributes[] = {"border", "frameSpacing"}; //NOI18N
    private static final String booleanAttributes[] = {"frameBorder"}; //NOI18N

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
        FrameSet frameset = (FrameSet) component;

        // I don't think this is the correct way to write the XML
        // header /avk

        if (!RenderingUtilities.isPortlet(context)) {
            writer.startElement("frameset", component);
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

        FrameSet frameset = (FrameSet) component;

        // Render a nested "head" element
        if (!RenderingUtilities.isPortlet(context)) {
            //id
            String id = frameset.getClientId(context);
            if (id != null) {
                writer.writeAttribute("id", id, null); //NOI18N
            }
            //class
            String styleClass = frameset.getStyleClass();
            if (styleClass != null) {
                writer.writeAttribute("class", styleClass, null); //NOI18N
            }
            //style
            String style = frameset.getStyle();
            if (style != null) {
                writer.writeAttribute("style", style, null); //NOI18N
            }
            //tooltip
            String toolTip = frameset.getToolTip();
            if (toolTip != null) {
                writer.writeAttribute("title", toolTip, "toolTip"); //NOI18N
            }
            //write out the rest of the attributes
            addStringAttributes(context, component, writer, stringAttributes);
            addBooleanAttributes(context, component, writer, booleanAttributes);
            addIntegerAttributes(context, component, writer, integerAttributes);
            writer.write("\n"); //NOI18N
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

        FrameSet frameset = (FrameSet) component;

        // End the outermost "html" element
        if (!RenderingUtilities.isPortlet(context)) {
            writer.endElement("frameset"); //NOI18N
            writer.write("\n"); //NOI18N
        }

    }
    // --------------------------------------------------------- Private Methods
}
