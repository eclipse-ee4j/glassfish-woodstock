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
import com.sun.webui.jsf.component.Markup;
import com.sun.webui.jsf.util.RenderingUtilities;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * <p>This class is responsible for rendering any type of XML tag markup. </p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Markup"))
public class MarkupRenderer extends AbstractRenderer {

    // -------------------------------------------------------- Static Variables
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    // no pass throughs.
    // -------------------------------------------------------- Renderer Methods
    @Override
    public boolean getRendersChildren() {
        return true;
    }

    /**
     * <p>Render the start of an Link (Link) tag.</p>
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * start should be rendered
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        //intentionally empty
        Markup markup = (Markup) component;

        String tagName = markup.getTag();

        if (tagName == null) {
            return;  //TODO: write out log message
        }
        if (!markup.isSingleton()) {
            // Markup is a singleton
            writeInsides(markup, context, writer);
        }
    }

    /**
     * <p>Render the attributes for the Markup. </p>
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * attributes should be rendered
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        Markup markup = (Markup) component;

    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        Markup markup = (Markup) component;

        if (!markup.isSingleton()) {
            super.encodeChildren(context, component);
        }
    }

    /**
     * <p>Write out the Markup.</p>
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

        Markup markup = (Markup) component;

        String tagName = markup.getTag();

        if (tagName == null) {
            return;  //TODO: write out log message
        }
        if (markup.isSingleton()) {
            // Markup is a singleton
            writeInsides(markup, context, writer);
        }
        writer.endElement(tagName);
    }

    // --------------------------------------------------------- Private Methods
    private void writeInsides(Markup markup,
            FacesContext context, ResponseWriter writer) throws IOException {
        String tagName = markup.getTag();
        writer.startElement(tagName, markup);
        writeId(markup, context, writer);
        writeStyles(markup, context, writer);
        writeExtraAttributes(markup, context, writer);
    }

    private void writeId(Markup markup,
            FacesContext context, ResponseWriter writer) throws IOException {

        String id = markup.getClientId(context);
        if (id != null) {
            writer.writeAttribute("id", id, null); //NO18N
        }
    }

    private void writeStyles(Markup markup,
            FacesContext context, ResponseWriter writer) throws IOException {

        String style = markup.getStyle();
        String styleClass = markup.getStyleClass();
        if (style != null) {
            writer.writeAttribute("style", style, null);  //NO18N
        }

        if (styleClass != null) {
            writer.writeAttribute("class", styleClass, "styleClass"); //NO18N
        }
    }

    private void writeExtraAttributes(Markup markup,
            FacesContext context, ResponseWriter writer) throws IOException {

        String extra = markup.getExtraAttributes();
        if (extra != null) {
            RenderingUtilities.renderExtraHtmlAttributes(writer, extra);
        }

        Map attributes = markup.getAttributes();

        Set keys = attributes.keySet();

        Iterator listOfKeys = keys.iterator();

        while (listOfKeys.hasNext()) {
            String key = (String) listOfKeys.next();
            Object value = attributes.get(key);

            // Only take strings (their is a private arraylist that I need to
            // avoid

            if (value != null && value instanceof String) {
                writer.writeAttribute(key, value, null);
            }
        }
    }
}
