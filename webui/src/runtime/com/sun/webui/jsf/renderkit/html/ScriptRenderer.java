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
import com.sun.webui.jsf.component.Script;
import com.sun.webui.jsf.util.RenderingUtilities;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * <p>This class is responsible for rendering the script component for the
 * HTML Render Kit.</p> <p> The script component can be used as an Script</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Script"))
public class ScriptRenderer extends AbstractRenderer {

    // -------------------------------------------------------- Static Variables
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] = {"charset", "type"}; //NOI18N

    // -------------------------------------------------------- Renderer Methods
    /**
     * <p>Render the start of an Script (Script) tag.</p>
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * start should be rendered
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        writer.startElement("script", component);
    }

    /**
     * <p>Render the attributes for an Script tag.  The onclick attribute will contain
     * extra javascript that will appropriately submit the form if the URL field is
     * not set.</p>
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * attributes should be rendered
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        Script script = (Script) component;
        addCoreAttributes(context, component, writer, null);
        addStringAttributes(context, component, writer, stringAttributes);
        // the URL is the tough thing because it needs to be encoded:
        String url = script.getUrl();
        if (url != null) {
            // Get resource URL -- bugtraq #6305522.
            RenderingUtilities.renderURLAttribute(context, writer, script, "src", //NO18N
                    context.getApplication().getViewHandler().getResourceURL(context, url),
                    "url"); //NO18N
        }
    }

    /**
     * <p>Close off the Script tag.</p>
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * end should be rendered
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        // End the appropriate element
        Script script = (Script) component;
        writer.endElement("script"); //NOI18N
        writer.write("\n"); //NOI18N
    }
    // --------------------------------------------------------- Private Methods
}
