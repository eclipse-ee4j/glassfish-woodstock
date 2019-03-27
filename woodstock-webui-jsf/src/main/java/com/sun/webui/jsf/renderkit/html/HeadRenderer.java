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
import com.sun.webui.jsf.component.Head;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Renderer for a {@link Head} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Head"))
public class HeadRenderer extends AbstractRenderer {

    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] = {"profile"}; //NOI18N

    private static final String DATE_ONE =
            (new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US)).format(new Date(1));

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
            writer.startElement("head", component); //NOI18N
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
        Head head = (Head) component;
        if (!RenderingUtilities.isPortlet(context)) {
            // Profile
            addStringAttributes(context, component, writer, stringAttributes);

            // Meta tags
            if (head.isMeta()) {
                writer.write("\n"); //NOI18N
                
                HttpServletResponse servletResponse = (HttpServletResponse) context.getCurrentInstance().getExternalContext().getResponse();
                servletResponse.setHeader("Pragma", "no-cache");
                servletResponse.setHeader("Cache-Control", "no-store");
                servletResponse.setHeader("Cache-Control", "no-cache");
                servletResponse.setHeader("Expires", DATE_ONE);
                servletResponse.setHeader("X-Frame-Options", "SAMEORIGIN");
                
                renderMetaTag("no-cache", "Pragma", writer, head);
                renderMetaTag("no-cache", "Cache-Control", writer, head);
                renderMetaTag("no-store", "Cache-Control", writer, head);
                renderMetaTag("max-age=0", "Cache-Control", writer, head);
                renderMetaTag("1", "Expires", writer, head);
            }

            // Title
            String title = head.getTitle();
            if (title == null) {
                title = "";
            }

            writer.startElement("title", head);
            writer.write(title);
            writer.endElement("title");
            writer.write("\n"); //NOI18N

            // Base
            if (head.isDefaultBase()) {
                writer.startElement("base", head); //NOI18N
                // TODO - verify the requirements w.r.t. printing this href
                writer.writeURIAttribute("href", Util.getBase(context), null); //NOI18N
                writer.endElement("base"); //NOI18N
                writer.write("\n"); //NOI18N
            }

            // Master link to always write out.
            Theme theme = ThemeUtilities.getTheme(context);
            RenderingUtilities.renderStyleSheetLink(head, theme, context, writer);

            // Do not render any JavaScript.
            if (!head.isJavaScript()) {
                return;
            }

            // Render Dojo config.
            JavaScriptUtilities.renderJavaScript(component, writer,
                    JavaScriptUtilities.getDojoConfig(head.isDebug(),
                    head.isParseWidgets()));

            // Render Dojo include.
            JavaScriptUtilities.renderDojoInclude(component, writer);

            // Render JSON include.
            //JavaScriptUtilities.renderJsonInclude(component, writer);

            // Render Prototype include before JSF Extensions.
            JavaScriptUtilities.renderPrototypeInclude(component, writer);

            // Render JSF Extensions include.
            //JavaScriptUtilities.renderJsfxInclude(component, writer);

            // Render module config after including dojo.
            JavaScriptUtilities.renderJavaScript(component, writer,
                    JavaScriptUtilities.getModuleConfig(head.isDebug()));

            // Render global include.
            JavaScriptUtilities.renderGlobalInclude(component, writer);
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
        // Start the appropriate element.
        if (!RenderingUtilities.isPortlet(context)) {
            writer.endElement("head"); //NOI18N
            writer.write("\n"); //NOI18N
        }
    }

    private void renderMetaTag(String content, String httpEquivalent,
            ResponseWriter writer, Head head) throws IOException {
        writer.startElement("meta", head);
        writer.writeAttribute("content", content, null);
        writer.writeAttribute("http-equiv", httpEquivalent, null);
        writer.endElement("meta");
        writer.writeText("\n", null);
    }
}
