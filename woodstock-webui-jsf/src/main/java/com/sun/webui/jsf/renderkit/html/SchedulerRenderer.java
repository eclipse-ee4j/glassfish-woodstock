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
 * SchedulerRenderer.java
 *
 * Created on February 9, 2005, 3:44 PM
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.util.MissingResourceException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.Scheduler;
import com.sun.webui.jsf.component.Time;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.MessageUtil;
import javax.json.JsonObject;

import static com.sun.webui.jsf.util.JsonUtilities.JSON_BUILDER_FACTORY;
import static com.sun.webui.jsf.util.RenderingUtilities.renderComponent;
import static com.sun.webui.jsf.util.ThemeUtilities.getIcon;
import static com.sun.webui.jsf.util.ThemeUtilities.getTheme;
import static com.sun.webui.jsf.util.JavaScriptUtilities.renderInitScriptTag;
import static com.sun.webui.jsf.util.JavaScriptUtilities.renderInitScriptTag;

/**
 * Renders a guidelines compliant Scheduler component.
 *
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Scheduler"))
public class SchedulerRenderer extends javax.faces.render.Renderer {

    // where is this used?
    private static final String SPACE = "&nbsp;";
    private static final boolean DEBUG = false;

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) {
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws java.io.IOException {
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {

        if(component == null){
            return;
        }
        if (DEBUG) {
            log("encodeEnd");
        }

        if (!(component instanceof Scheduler)) {
            Object[] params = {
                component.toString(),
                this.getClass().getName(),
                Scheduler.class.getName()
            };
            String message = MessageUtil.getMessage(
                    "com.sun.webui.jsf.resources.LogMessages",
                    "Renderer.component", params);
            throw new FacesException(message);
        }

        Scheduler scheduler = (Scheduler) component;
        Theme theme = getTheme(context);
        ResponseWriter writer = context.getResponseWriter();
        String spacerPath = theme.getImagePath(ThemeImages.DOT);

        // render the outer div element.
        renderEnclosingDiv(scheduler, theme, context, writer);
        writer.writeText("\n", null);

        // open the table	
        renderOpenTable(scheduler, writer, null);
        writer.writeText("\n", null);
        writer.startElement("tr", scheduler);
        writer.writeText("\n", null);

        // render date picker in the first column.
        renderDatePicker(scheduler, theme, spacerPath, writer, context);

        // render the input controls in the second column.
        renderInputControls(context, scheduler, writer, theme, spacerPath);

        // close the table
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.endElement("tr");
        writer.writeText("\n", null);
        writer.endElement("table");
        writer.writeText("\n", null);

        // end the enclosing div element
        writer.endElement("div");

        renderJavaScript(context, scheduler, writer, theme);
    }

    private void renderEnclosingDiv(Scheduler scheduler, Theme theme,
            FacesContext context, ResponseWriter writer)
            throws IOException {

        writer.startElement("div", scheduler);
        writer.writeAttribute("id", scheduler.getClientId(context), null);
        String style = scheduler.getStyle();
        if (style != null) {
            writer.writeAttribute("style", style, "style");
        }
        style = scheduler.getStyleClass();
        if (scheduler.isVisible()) {
            if (style != null) {
                writer.writeAttribute("class", style, "styleClass");
            }
        } else {
            style = style + " " + theme.getStyleClass(ThemeStyles.HIDDEN);
            writer.writeAttribute("class", style, "styleClass");
        }
    }

    /**
     * Render a layout table.
     *
     * @param writer The current ResponseWriter
     * @param comp The component instance
     * @param styleClass The styleClass for this component
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderOpenTable(UIComponent comp, ResponseWriter writer,
            String styleClass) throws IOException {

        writer.startElement("table", comp);
        if (styleClass != null) {
            writer.writeAttribute("class", styleClass, null);
        }
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute("cellpadding", "0", null);
        writer.writeAttribute("cellspacing", "0", null);
        writer.writeAttribute("title", "", null);
    }

    // Helper method to render the legend row. 
    private void renderLegendRow(Scheduler scheduler, Theme theme,
            ResponseWriter writer, FacesContext context, String spacerPath)
            throws IOException {

        // This row consists of the legend and nothing else. 
        writer.writeText("\n", null);
        writer.startElement("tr", scheduler);
        writer.writeText("\n", null);
        writer.startElement("td", scheduler);
        renderSpacerImage(scheduler, spacerPath, 1, 30, writer);
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.startElement("td", scheduler);
        writer.writeAttribute("colspan", "3", null);
        writer.writeAttribute("height", "25", null);
        writer.writeAttribute("valign", "top", null);

        if (scheduler.isRequiredLegend()) {
            writer.writeText("\n", null);
            writer.startElement("div", scheduler);
            writer.writeAttribute("align", "left", null);
            writer.writeAttribute("class",
                    theme.getStyleClass(ThemeStyles.LABEL_REQUIRED_DIV), null);
            writer.writeText("\n", null);

            Icon icon = getIcon(theme,
                    ThemeImages.LABEL_REQUIRED_ICON);
            icon.setId(scheduler.getId().concat(Scheduler.ICON_ID));
            // Need to set parent to get clientID
            icon.setParent(scheduler);
            renderComponent(icon, context);
            writer.writeText("\n", null);
            writer.write(SPACE);//N0I18N
            writer.writeText(theme.getMessage("Scheduler.requiredLegend"),
                    null);
            writer.writeText("\n", null);
            writer.endElement("div");
        } else {
            writer.write(SPACE);
        }
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.endElement("tr");
        writer.writeText("\n", null);
    }

    // It must be possible to do this using a style on the row instead! 
    private void renderSpacerRow(UIComponent component, String path,
            int height, int width, int colspan,
            ResponseWriter writer)
            throws IOException {

        writer.startElement("tr", component);
        writer.writeText("\n", null);
        writer.startElement("td", component);
        writer.writeAttribute("colspan", String.valueOf(colspan), null);
        writer.writeText("\n", null);
        renderSpacerImage(component, path, height, width, writer);
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.endElement("tr");
        writer.writeText("\n", null);
    }

    // Helper method to render a spacer image.
    private void renderSpacerImage(UIComponent component, String path,
            int height, int width, ResponseWriter writer) throws IOException {

        writer.startElement("img", component);
        writer.writeAttribute("src", path, null);
        writer.writeAttribute("height", String.valueOf(height), null);
        writer.writeAttribute("width", String.valueOf(width), null);
        writer.writeAttribute("alt", "", null);
        writer.endElement("img");
    }

    // Render date picker.
    private void renderDatePicker(Scheduler scheduler, Theme theme,
            String spacerPath, ResponseWriter writer, FacesContext context)
            throws IOException {

        writer.startElement("td", scheduler);
        writer.writeAttribute("valign", "top", null);
        writer.writeText("\n", null);
        renderComponent(scheduler.getDatePicker(), context);
        writer.endElement("td");
        writer.writeText("\n", null);
    }

    /**
     * <p>Render the Scheduler component input controls such as the start date
     * field, start and end hour / minutes, etc.</p>
     *
     * @param context The current FacesContext
     * @param scheduler The Scheduler component instance
     * @param writer The current ResponseWriter
     * @param sourcePath Path to spacer image
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderInputControls(FacesContext context, Scheduler scheduler,
            ResponseWriter writer, Theme theme, String spacerPath)
            throws IOException {

        writer.startElement("td", scheduler);
        writer.writeAttribute("valign", "top", null);
        writer.writeText("\n", null);

        // the input controls go in another layout table
        String styleClass = theme.getStyleClass(
                ThemeStyles.DATE_TIME_FIELD_TABLE);
        renderOpenTable(scheduler, writer, styleClass);

        // render the legend row.
        renderLegendRow(scheduler, theme, writer, context, spacerPath);

        // render the start date row.
        renderStartDateRow(scheduler, spacerPath, theme, context, writer);

        if (scheduler.isStartTime()) {
            // render start time row
            // TODO - this is one of the cases where we have to check for 
            // private vs public facets!
            UIComponent label = scheduler.getStartTimeLabelComponent(theme);

            renderTimeRow(scheduler, label,
                    scheduler.getStartTimeComponent(),
                    theme,
                    spacerPath,
                    context,
                    writer);
        }

        if (scheduler.isEndTime()) {
            // render end time row
            // TODO - this is one of the cases where we have to check for 
            // private vs public facets!
            UIComponent label = scheduler.getEndTimeLabelComponent(theme);

            renderTimeRow(scheduler, label,
                    scheduler.getEndTimeComponent(),
                    theme,
                    spacerPath,
                    context,
                    writer);
        }

        if (scheduler.isRepeating()) {

            // render the repeat interval
            renderSpacerRow(scheduler, spacerPath, 5, 1, 4, writer);
            renderRepeatIntervalRow(scheduler, theme, context, writer);

            if (scheduler.isLimitRepeating()) {
                // render the repeat limit inputs 
                renderSpacerRow(scheduler, spacerPath, 5, 1, 4, writer);
                renderRepeatLimitRow(context, scheduler, writer, theme, spacerPath);
                renderRepeatLegend(scheduler, theme, context, writer);
            }
        }

        if (scheduler.isPreviewButton()) {
            renderSpacerRow(scheduler, spacerPath, 30, 1, 4, writer);

            // render the preview input
            renderPreviewRow(context, scheduler, writer, theme);
        }

        writer.writeText("\n", null);
        writer.endElement("table");
        writer.writeText("\n", null);
    }

    private void renderTimeRow(Scheduler scheduler, UIComponent label,
            Time time,
            Theme theme,
            String spacerPath,
            FacesContext context,
            ResponseWriter writer)
            throws IOException {

        renderSpacerRow(scheduler, spacerPath, 5, 1, 4, writer);
        writer.writeText("\n", null);

        // tr td span
        renderInputRowStart(scheduler, writer, theme);
        renderComponent(label, context);
        writer.writeText("\n", null);
        writer.endElement("span");
        writer.endElement("td");
        writer.writeText("\n", null);

        writer.startElement("td", scheduler);
        writer.write(SPACE);
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.startElement("td", scheduler);
        writer.writeText("\n", null);
        renderComponent(time, context);
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.endElement("tr");
        writer.writeText("\n", null);
    }

    // Helper method to render the start date row. 
    private void renderStartDateRow(Scheduler scheduler, String spacerPath,
            Theme theme, FacesContext context,
            ResponseWriter writer)
            throws IOException {

        UIComponent date = scheduler.getDateComponent();
        // TODO - this is one of the cases where we have to check for 
        // private vs public facets!
        UIComponent label = scheduler.getDateLabelComponent(theme);

        writer.startElement("tr", scheduler);
        writer.writeText("\n", null);

        // Spacer cell
        writer.startElement("td", scheduler);
        writer.writeText("\n", null);
        renderSpacerImage(scheduler, spacerPath, 1, 30, writer);
        writer.endElement("td");
        writer.writeText("\n", null);

        // Label cell
        writer.startElement("td", scheduler);
        writer.writeAttribute("nowrap", "nowrap", null);
        writer.writeText("\n", null);
        renderComponent(label, context);
        writer.endElement("td");
        writer.writeText("\n", null);

        // Spacer cell
        writer.startElement("td", scheduler);
        writer.writeText("\n", null);
        renderSpacerImage(scheduler, spacerPath, 1, 10, writer);
        writer.endElement("td");
        writer.writeText("\n", null);

        // Field and help text cell
        writer.startElement("td", scheduler);
        writer.writeAttribute("nowrap", "nowrap", null);
        writer.writeText("\n", null);
        renderComponent(date, context);
        writer.writeText("\n", null);
        writer.startElement("span", scheduler);//NOI18N
        String styleClass = theme.getStyleClass(ThemeStyles.HELP_FIELD_TEXT);
        writer.writeAttribute("class", styleClass, null);
        writer.write(SPACE);
        writer.write(SPACE);
        writer.writeText(getPattern(scheduler, theme), null);
        writer.endElement("span");
        writer.endElement("td");
        writer.writeText("\n", null);

        writer.endElement("tr");
        writer.writeText("\n", null);
    }

    private String getPattern(Scheduler scheduler, Theme theme) {

        String hint = scheduler.getDateFormatPatternHelp();
        if (hint == null) {
            try {
                String pattern = scheduler.getDatePicker().getDateFormatPattern();
                hint = theme.getMessage("calendar.".concat(pattern));
            } catch (MissingResourceException mre) {
                hint = ((SimpleDateFormat) (scheduler.getDateFormat()))
                        .toLocalizedPattern().toLowerCase();
            }
        }
        return hint;
    }

    /**
     * Render the "Preview in Browser" button.
     *
     * @param context The current FacesContext
     * @param scheduler The Scheduler component instance
     * @param writer The current ResponseWriter
     * @param theme The current Theme
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderPreviewRow(FacesContext context, Scheduler scheduler,
            ResponseWriter writer, Theme theme) throws IOException {

        writer.startElement("tr", scheduler);
        writer.writeText("\n", null);
        writer.startElement("td", scheduler);
        writer.writeAttribute("colspan", "3", null);
        writer.write(SPACE);
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.startElement("td", scheduler);
        UIComponent button = scheduler.getPreviewButtonComponent();
        renderComponent(button, context);
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.endElement("tr");
        writer.writeText("\n", null);
    }

    private void renderRepeatIntervalRow(Scheduler scheduler, Theme theme,
            FacesContext context, ResponseWriter writer)
            throws IOException {

        UIComponent label = scheduler.getRepeatIntervalLabelComponent();
        DropDown menu = scheduler.getRepeatIntervalComponent();

        // Spacer and label cells 
        renderInputRowStart(scheduler, writer, theme);
        renderComponent(label, context);
        writer.writeText("\n", null);
        writer.endElement("span");
        writer.endElement("td");
        writer.writeText("\n", null);

        // Spacer cell
        writer.startElement("td", scheduler);
        writer.write(SPACE);
        writer.endElement("td");
        writer.writeText("\n", null);

        // Field cell
        writer.startElement("td", scheduler);
        writer.writeText("\n", null);
        renderComponent(menu, context);
        writer.endElement("td");
        writer.writeText("\n", null);

        writer.endElement("tr");
        writer.writeText("\n", null);
    }

    /**
     * Convenience function to render the start of an input control row.
     *
     * @param scheduler The Scheduler component instance
     * @param writer The current ResponseWriter
     * @param theme The current Theme
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderInputRowStart(Scheduler scheduler,
            ResponseWriter writer, Theme theme) throws IOException {

        writer.startElement("tr", scheduler);
        writer.writeText("\n", null);

        writer.startElement("td", scheduler);
        writer.write(SPACE);
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.startElement("td", scheduler);
        writer.writeAttribute("nowrap", "nowrap", null);
        writer.writeText("\n", null);
        writer.startElement("span", scheduler);
        String styleClass =
                theme.getStyleClass(ThemeStyles.DATE_TIME_LABEL_TEXT);
        writer.writeAttribute("class", styleClass, null);
        writer.writeText("\n", null);
    }

    /**
     * Render the repeat limit row.
     *
     * @param context The current FacesContext
     * @param scheduler The Scheduler component instance
     * @param writer The current ResponseWriter
     * @param theme The current Theme
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderRepeatLimitRow(FacesContext context, Scheduler scheduler,
            ResponseWriter writer, Theme theme, String spacerPath)
            throws IOException {

        UIComponent label = scheduler.getRepeatLimitLabelComponent();
        UIComponent field = scheduler.getRepeatingFieldComponent();
        DropDown menu = scheduler.getRepeatUnitComponent();

        // Spacer and label cells 
        renderInputRowStart(scheduler, writer, theme);
        renderComponent(label, context);
        writer.endElement("span");
        writer.endElement("td");
        writer.writeText("\n", null);

        writer.startElement("td", scheduler);
        writer.write(SPACE);
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.startElement("td", scheduler);
        renderComponent(field, context);
        writer.writeText("\n", null);
        renderComponent(menu, context);
        writer.endElement("td");
        writer.writeText("\n", null);

        writer.endElement("tr");
        writer.writeText("\n", null);
    }

    private void renderRepeatLegend(Scheduler scheduler,
            Theme theme,
            FacesContext context,
            ResponseWriter writer)
            throws IOException {

        renderInputRowStart(scheduler, writer, theme);
        writer.write(SPACE);
        writer.endElement("span");
        writer.endElement("td");
        writer.writeText("\n", null);

        writer.startElement("td", scheduler);
        writer.write(SPACE);
        writer.endElement("td");
        writer.write("\n");

        writer.startElement("td", scheduler);
        writer.writeAttribute("nowrap", "nowrap", null);
        writer.startElement("div", scheduler);
        writer.writeAttribute("class",
                theme.getStyleClass(ThemeStyles.HELP_FIELD_TEXT), null);
        writer.writeText(theme.getMessage("Scheduler.blankForWhat"),
                null);
        writer.write("\n");
        writer.endElement("div");
        writer.endElement("td");
        writer.write("\n");

        writer.endElement("tr");
        writer.write("\n");
    }

    private void renderJavaScript(FacesContext context, Scheduler scheduler,
            ResponseWriter writer, Theme theme) throws IOException {

        // Append properties.
        JsonObject initProps = JSON_BUILDER_FACTORY.createObjectBuilder()
                .add("id", scheduler.getClientId(context))
                .add("datePickerId", scheduler.getDatePicker()
                        .getClientId(context))
                .add("dateFieldId", scheduler.getDateComponent()
                        .getClientId(context))
                .add("dateClass", theme.getStyleClass(
                        ThemeStyles.DATE_TIME_LINK))
                .add("selectedClass", theme.getStyleClass(
                        ThemeStyles.DATE_TIME_BOLD_LINK))
                .add("edgeClass", theme.getStyleClass(
                        ThemeStyles.DATE_TIME_OTHER_LINK))
                .add("edgeSelectedClass", theme.getStyleClass(
                        ThemeStyles.DATE_TIME_OTHER_BOLD_LINK))
                .add("todayClass", theme.getStyleClass(
                        ThemeStyles.DATE_TIME_TODAY_LINK))
                .add("dateFormat", scheduler.getDatePicker()
                        .getDateFormatPattern())
                .build();

        renderInitScriptTag(writer, "scheduler", initProps);
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }

    private void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s);
    }
}
