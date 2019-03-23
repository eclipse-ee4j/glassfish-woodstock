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

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.jsf.component.CommonTasksGroup;
import com.sun.webui.jsf.component.CommonTasksSection;
import com.sun.webui.jsf.component.StaticText;
import java.util.Iterator;

/**
 * <p>Renderer for a {@link com.sun.webui.jsf.component.CommonTasksGroup} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.CommonTasksGroup"))
public class CommonTasksGroupRenderer extends AbstractRenderer {

    /**
     *Append this string for the id of span for the group header's title.
     */
    private static final String TITLE_SPAN = "_groupTitle";
    public static final String GROUP_TITLE = "commonTasks.groupTitle";
    public static final String SKIP_GROUP = "commonTasks.skipTagAltText";
    /**
     *Skip a complete common tasks group.
     */
    private static final String SKIP_TASKSGROUP = "skipGroup"; // NOI18N

    /** Creates a new instance of CommonTaskGroupRenderer */
    public CommonTasksGroupRenderer() {
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {

        // purposefully don't want to do anything here!
    }

    /**
     * Render a common tasks group.
     * 
     * @param context The current FacesContext
     * @param component The CommonTasksGroup object to render
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        CommonTasksGroup ctg = (CommonTasksGroup) component;

        if (!ctg.isRendered()) {
            return;
        }
        Theme theme = ThemeUtilities.getTheme(context);
        String title = ctg.getTitle();
        if (title == null) {
            title = theme.getMessage(GROUP_TITLE);
            ctg.setTitle(title);
        }

        writer.startElement(HTMLElements.DIV, ctg);
        writer.writeAttribute(HTMLAttributes.ID, ctg.getClientId(context),
                HTMLAttributes.ID); //NOI18N

        String styles = RenderingUtilities.getStyleClasses(context, ctg,
                theme.getStyleClass(ThemeStyles.CTS_GROUP));

        if (styles != null) {
            writer.writeAttribute(HTMLAttributes.CLASS, styles,
                    HTMLAttributes.CLASS);// NOI18N
        }

        if (ctg.getStyle() != null) {
            writer.writeAttribute(HTMLAttributes.STYLE, ctg.getStyle(),
                    HTMLAttributes.STYLE);  // NOI18N
        }

        StringBuffer jsBuffer = new StringBuffer();
        renderTaskGroupHeader(title, writer, context, ctg, theme);

        if (!(ctg.getParent() instanceof CommonTasksSection)) {
            renderJavascriptImages(theme, writer, ctg, context);
        }

        Iterator it = ctg.getChildren().iterator();
        UIComponent comp;
        while (it.hasNext()) {
            comp = (UIComponent) it.next();
            RenderingUtilities.renderComponent(comp, context);
        }

        writer.endElement(HTMLElements.DIV);
    }

    /**
     * Renders the javascript necessary to precache the "i" images
     *
     * @param theme The current theme
     * @param writer The ResponseWriter object
     * @param component The commonTasksSection component
     */
    protected void renderJavascriptImages(Theme theme, ResponseWriter writer,
            UIComponent component, FacesContext context) throws IOException {
        StringBuffer buff = new StringBuffer();


        /*
         * Create the JSON object.
         */

        try {
            JSONObject json = getJSONProperties(context, theme, component);

            buff.append("require(['").append(JavaScriptUtilities.getModuleName("commonTasksSection")).append("'], function (commonTasksSection) {").append("\n") // NOI18N
//                    .append(JavaScriptUtilities.getModuleName("commonTasksSection.init(")) // NOI18N
                    .append("commonTasksSection.init(")
                    .append(json.toString(JavaScriptUtilities.INDENT_FACTOR)).append(");\n"); //NOI18N
            buff.append("});");
            
            // Render JavaScript.
            JavaScriptUtilities.renderJavaScript(component, writer,
                    buff.toString());
        } catch (JSONException e) {
            if (LogUtil.fineEnabled()) {
                LogUtil.fine(e.getStackTrace().toString()); //NOI18N
            }
        }
        writer.write("\n");             // NOI18N
    }

    protected JSONObject getJSONProperties(FacesContext context, Theme theme,
            UIComponent component) throws IOException, JSONException {

        JSONObject json = new JSONObject();

        json.put("id", component.getClientId(context));
        String url =
                theme.getImagePath(ThemeImages.CTS_RIGHT_TOGGLE_SELECTED);
        json.put("pic1URL", url);
        url = theme.getImagePath(ThemeImages.CTS_RIGHT_TOGGLE_OVER);
        json.put("pic2URL", url);
        url = theme.getImagePath(ThemeImages.CTS_RIGHT_TOGGLE);
        json.put("pic3URL", url);

        return json;
    }

    /**
     * Render a common tasks group.
     * 
     * @param context The current FacesContext
     * @param component The CommonTasksGroup object to render
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer)
            throws IOException {
    }

    /**
     * Renders the task group header.
     * @param header The header text to be rendered
     * @param writer The current ResponseWriter object
     * @param component The CommonTasksGroup object
     * @param theme The current theme.
     * @param context The current FacesContext
     */
    protected void renderTaskGroupHeader(String header, ResponseWriter writer,
            FacesContext context, UIComponent component, Theme theme)
            throws IOException {

        StaticText st = new StaticText();
        st.setParent(component);
        st.setId(TITLE_SPAN);
        st.setStyleClass(theme.getStyleClass(ThemeStyles.CTS_HEADER));
        st.setText(header);
        RenderingUtilities.renderComponent(st, context);
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }
}
