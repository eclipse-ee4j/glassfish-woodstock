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
import com.sun.webui.jsf.component.Anchor;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.util.ClientSniffer;
import java.beans.Beans;
import java.io.IOException;
import java.net.URL;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * <p>This class is responsible for rendering the {@link Anchor} component for the
 * HTML Render Kit.</p> <p> The {@link Anchor} component can be used as an anchor</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Anchor"))
public class AnchorRenderer extends AbstractRenderer {

    // -------------------------------------------------------- Renderer Methods
    /**
     * <p>Render the start of an anchor (Anchor) tag.</p>
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * start should be rendered
     * @exception IOException if an input/output error occurs
     */
    /*
     *<p> Id of the transparent image to be rendered for IE browsers
     */
    private static String ANCHOR_IMAGE = "_img";   //NOI18N

    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        Anchor anchor = (Anchor) component;
        writer.startElement("a", anchor); //NOI18N

    }

    /**
     * <p>Render the attributes for an anchor tag.  The onclick attribute will contain
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

        // Set up local variables we will need
        Anchor anchor = (Anchor) component;
        String id = anchor.getId();

        // Design time only behavior - display an icon so that this
        // component has a visual appearance in the IDE
        if (Beans.isDesignTime()) {
            writer.startElement("img", anchor); // NOI18N
            try {
                URL url = this.getClass().getResource("/com/sun/webui/jsf/design/resources/AnchorIcon.gif"); // NOI18N
                writer.writeURIAttribute("src", url, null); // NOI18N
            } catch (Exception e) {
                System.out.println("Error getting anchor icon: " + e);
                e.printStackTrace(System.out);
            }
            writer.endElement("img"); // NOI18N
        }

        // Render core and pass through attributes as necessary
        // NOTE - id is being rendered "as is" instead of the normal convention
        // that we render the client id.
        writer.writeAttribute("id", id, "id"); //NOI8N
        String style = anchor.getStyle();
        String styleClass = anchor.getStyleClass();
        if (styleClass != null) {
            RenderingUtilities.renderStyleClass(context, writer, component, null);
        }
        if (style != null) {
            writer.writeAttribute("style", style, null);
        }

        // XHTML requires that this been the same as the id and it may
        // removed.
        writer.writeAttribute("name", id, null); //NO18N

    }

    /**
     * <p>Close off the anchor tag.</p>
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * end should be rendered
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        ClientSniffer sniffer = ClientSniffer.getInstance(context);
        if (sniffer.isIe6up() || sniffer.isIe7() || sniffer.isIe7up()) {
            Icon icon = new Icon();
            icon.setIcon(ThemeImages.DOT);
            icon.setId(component.getId() + ANCHOR_IMAGE);
            RenderingUtilities.renderComponent(icon, context);
        }

        // End the appropriate element
        Anchor anchor = (Anchor) component;
        writer.endElement("a"); //NOI18N

    }
    // --------------------------------------------------------- Private Methods
}
