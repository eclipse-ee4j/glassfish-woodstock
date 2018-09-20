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
 * $Id: HtmlRenderer.java,v 1.1.20.1 2009-12-29 04:52:45 jyeary Exp $
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.Html;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.util.RenderingUtilities;

/**
 * <p>Renderer for a {@link Html} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Html"))
public class HtmlRenderer extends AbstractRenderer {


    // ======================================================== Static Variables
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] = {"xmlns", "lang"}; //NOI18N

    // -------------------------------------------------------- Renderer Methods
    /**
     * <p>Render the appropriate element start, depending on whether the
     * <code>for</code> property is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component component to render.
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        // Start the appropriate element
        if (!RenderingUtilities.isPortlet(context)) {
            writer.startElement("html", component); //NOI18N
        }
    }

    /**
     * <p>Render the appropriate element attributes, 
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component component to be rendered
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        if (!RenderingUtilities.isPortlet(context)) {
            addStringAttributes(context, component, writer, stringAttributes);
            //these attributes needed for accessibility support
            writer.writeAttribute("xmlns:wairole",
                    "http://www.w3.org/2005/01/wai-rdf/GUIRoleTaxonomy#", null);
            writer.writeAttribute("xmlns:waistate",
                    "http://www.w3.org/2005/07/aaa", null);
            writer.write("\n");
        }
    }

    /**
     * <p>Render the appropriate element end, depending on whether the
     * <code>for</code> property is set or not.</p>
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

        if (!RenderingUtilities.isPortlet(context)) {
            writer.endElement("html"); //NOI18N
            writer.write("\n"); //NOI18N
        }
    }
    // --------------------------------------------------------- Private Methods
}
