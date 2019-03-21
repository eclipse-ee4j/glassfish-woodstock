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

package com.sun.webui.jsf.renderkit.widget;

import com.sun.faces.annotation.Renderer;

import com.sun.webui.jsf.component.ProgressBar;
import com.sun.webui.jsf.component.TextArea;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders ProgressBar components.
 */
@Renderer(@Renderer.Renders(rendererType = "com.sun.webui.jsf.widget.ProgressBar",
componentFamily = "com.sun.webui.jsf.ProgressBar"))
public class ProgressBarRenderer extends RendererBase {

    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {
        "style"};

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONArray getModules(FacesContext context, UIComponent component)
            throws JSONException {
        ProgressBar progressBar = (ProgressBar) component;

        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.progressBar"));

        if (progressBar.isAjaxify()) {
            json.put(JavaScriptUtilities.getModuleName(
                    "widget.jsfx.progressBar"));
        }
        return json;
    }

    /**
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws IOException, JSONException {
        ProgressBar progressBar = (ProgressBar) component;
        Theme theme = ThemeUtilities.getTheme(context);
        String templatePath = progressBar.getHtmlTemplate(); // Get HTML template.

        JSONObject json = new JSONObject();
        json.put("barHeight", progressBar.getHeight()).put("barWidth", progressBar.getWidth()).put("failedStateText", progressBar.getFailedStateText()).put("logMessage", progressBar.getLogMessage()).put("overlayAnimation", progressBar.isOverlayAnimation()).put("percentChar", theme.getMessage("ProgressBar.percentChar")).put("progress", String.valueOf(progressBar.getProgress())).put("progressImageUrl", progressBar.getProgressImageUrl()).put("refreshRate", progressBar.getRefreshRate()).put("taskState", progressBar.getTaskState()).put("templatePath", (templatePath != null)
                ? templatePath
                : theme.getPathToTemplate(ThemeTemplates.PROGRESSBAR)).put("toolTip", (progressBar.getToolTip() != null)
                ? progressBar.getToolTip()
                : theme.getMessage("ProgressBar.toolTip")).put("type", progressBar.getType()).put("visible", progressBar.isVisible());

        // Add busy icon.
        WidgetUtilities.addProperties(json, "busyImage",
                WidgetUtilities.renderComponent(context,
                progressBar.getBusyIcon()));

        // Append properties.
        addAttributeProperties(attributes, component, json);
        setCoreProperties(context, component, json);
        setFacetProperties(context, progressBar, json);

        return json;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Helper method to set facet properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json JSONObject to append to.
     */
    private void setFacetProperties(FacesContext context, ProgressBar component,
            JSONObject json) throws IOException, JSONException {
        // Button facets.
        UIComponent rightButtonCon = component.getFacet(
                ProgressBar.RIGHTTASK_CONTROL_FACET);
        UIComponent bottomButtonCon = component.getFacet(
                ProgressBar.BOTTOMTASK_CONTROL_FACET);

        if (rightButtonCon != null) {
            WidgetUtilities.addProperties(json, "progressControlRight",
                    WidgetUtilities.renderComponent(context, rightButtonCon));
        }
        if (bottomButtonCon != null) {
            WidgetUtilities.addProperties(json, "progressControlBottom",
                    WidgetUtilities.renderComponent(context, bottomButtonCon));
        }

        ProgressBar pb = (ProgressBar) component;
        if (pb.getLogMessage() != null) {
            // TextArea for running log
            UIComponent textArea = (TextArea) pb.getLogMsgComponent(component);

            WidgetUtilities.addProperties(json, "log",
                    WidgetUtilities.renderComponent(context, textArea));
            json.put("logId", textArea.getClientId(context));
        }

        // Bottom text facet.
        UIComponent bottomTextFacet = component.getFacet(
                ProgressBar.BOTTOMTEXT_FACET);
        if (bottomTextFacet != null) {
            WidgetUtilities.addProperties(json, "bottomText",
                    WidgetUtilities.renderComponent(context, bottomTextFacet));
        } else {
            json.put("bottomText", pb.getStatus());
        }

        // Top Text facet.
        UIComponent topTextFacet = component.getFacet(ProgressBar.TOPTEXT_FACET);
        if (topTextFacet != null) {
            WidgetUtilities.addProperties(json, "topText",
                    WidgetUtilities.renderComponent(context, topTextFacet));
        } else {
            json.put("topText", pb.getDescription());
        }
    }
}
