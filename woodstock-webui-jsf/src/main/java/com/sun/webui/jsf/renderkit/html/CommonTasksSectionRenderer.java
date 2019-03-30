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

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.faces.annotation.Renderer;
import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.component.CommonTasksGroup;
import com.sun.webui.jsf.component.CommonTasksSection;
import com.sun.webui.jsf.component.HelpInline;
import com.sun.webui.jsf.component.Icon;
import javax.json.JsonObject;

import static com.sun.webui.jsf.util.JsonUtilities.JSON_BUILDER_FACTORY;
import static com.sun.webui.jsf.util.RenderingUtilities.getStyleClasses;
import static com.sun.webui.jsf.util.RenderingUtilities.renderComponent;
import static com.sun.webui.jsf.util.ThemeUtilities.getIcon;
import static com.sun.webui.jsf.util.ThemeUtilities.getTheme;
import static com.sun.webui.jsf.util.JavaScriptUtilities.renderInitScriptTag;

/**
 * Renderer for a {@link com.sun.webui.jsf.component.CommonTasksSection} component.
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.CommonTasksSection"))
public class CommonTasksSectionRenderer extends AbstractRenderer {

    /**
     *Append this string for the id of the  spacer image.
     */
    private static final String SPACER_IMAGE = "_spacerImg";
    private static final String WHITE_SPACE = "&nbsp;";
    private static final String COLUMN_COUNT = "commonTasks.columnCount";
    private static final String SECTION_TITLE = "commonTasks.sectionTitle";

    /**
     * Creates a new instance of CommonTaskPageRenderer.
     */
    public CommonTasksSectionRenderer() {
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {

        // purposefully don't want to do anything here!
    }

    /**
     * Render a common tasks section.
     * 
     * @param context The current FacesContext
     * @param component The CommonTasksSection object to render
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

        if (!component.isRendered()) {
            return;
        }

        CommonTasksSection cts = (CommonTasksSection) component;
        Theme theme = getTheme(context);

        // Determine at what point should rendering go to the next column.
        int numColumns = 2;
        if (cts.getColumns() > 0) {
            numColumns = cts.getColumns();
        }

        String title;
        writer.write("\n");
        writer.startElement(HTMLElements.DIV, cts);
        writer.writeAttribute(HTMLAttributes.ID, cts.getClientId(context),
                HTMLAttributes.ID);
        String styles = getStyleClasses(context, cts,
                theme.getStyleClass(ThemeStyles.CTS_SECTION));
        if (styles != null) {
            writer.writeAttribute(HTMLAttributes.CLASS, styles,
                    HTMLAttributes.CLASS);
        }

        styles = cts.getStyle();

        if (styles != null) {
            writer.writeAttribute(HTMLAttributes.STYLE, styles,
                    HTMLAttributes.STYLE);
        }
        writer.startElement(HTMLElements.TABLE, cts);
        writer.writeAttribute(HTMLAttributes.WIDTH, "100%",
                HTMLAttributes.WIDTH);
        writer.writeAttribute(HTMLAttributes.BORDER, "0",
                HTMLAttributes.BORDER);
        writer.writeAttribute(HTMLAttributes.CELLPADDING, "0",
                HTMLAttributes.CELLPADDING);
        writer.writeAttribute(HTMLAttributes.CELLSPACING, "0",
                HTMLAttributes.CELLSPACING);
        writer.writeAttribute(HTMLAttributes.TITLE, "", HTMLAttributes.TITLE);
        writer.writeAttribute(HTMLAttributes.ROLE,
                HTMLAttributes.ROLE_PRESENTATION, null);
        if (cts.getTitle() != null) {
            title = cts.getTitle();
        } else {
            title = theme.getMessage(SECTION_TITLE);
            cts.setTitle(title);
        }

        writer.write("\n");
        renderHeading(title, writer, cts, theme, context);
        writer.endElement(HTMLElements.TABLE);
        writer.startElement(HTMLElements.TABLE, cts);
        writer.writeAttribute(HTMLAttributes.WIDTH, "100%",
                HTMLAttributes.WIDTH);
        writer.writeAttribute(HTMLAttributes.BORDER, "0",
                HTMLAttributes.BORDER);
        writer.writeAttribute(HTMLAttributes.CELLPADDING, "0",
                HTMLAttributes.CELLPADDING);
        writer.writeAttribute(HTMLAttributes.CELLSPACING, "0",
                HTMLAttributes.CELLSPACING);
        writer.writeAttribute(HTMLAttributes.TITLE, "", HTMLAttributes.TITLE);
        writer.writeAttribute(HTMLAttributes.ROLE,
                HTMLAttributes.ROLE_PRESENTATION, null);

        writer.write("\n");

        renderSpacer(writer, cts, theme, numColumns, context);
        writer.startElement(HTMLElements.TR, cts);
        writer.startElement(HTMLElements.TD, cts);
        writer.writeAttribute(HTMLAttributes.VALIGN, "top",
                HTMLAttributes.VALIGN);
        layoutCommonTasks(cts, theme, context, writer, numColumns);

        writer.endElement(HTMLElements.TD);
        writer.startElement(HTMLElements.TD, cts);
        writer.writeAttribute(HTMLAttributes.HEIGHT, "503",
                HTMLAttributes.HEIGHT);
        renderSpacerImage(cts, 503, 1, theme, context, SPACER_IMAGE);
        writer.endElement(HTMLElements.TD);
        writer.endElement(HTMLElements.TR);
        writer.endElement(HTMLElements.TABLE);
        writer.endElement(HTMLElements.DIV);

    }

    /**
     * Renders the JS necessary to initialize DOM object.
     *
     * @param theme The current theme
     * @param writer The ResponseWriter object
     * @param component The commonTasksSection component
     * @param context faces context
     * @throws java.io.IOException if an IO error occurs
     */
    protected void renderJavascript(Theme theme, ResponseWriter writer,
            UIComponent component, FacesContext context) throws IOException {

        JsonObject initProps = JSON_BUILDER_FACTORY.createObjectBuilder()
                .add("id", component.getClientId(context))
                .add("pic1URL", theme.getImagePath(
                        ThemeImages.CTS_RIGHT_TOGGLE_SELECTED))
                .add("pic2URL", theme.getImagePath(
                        ThemeImages.CTS_RIGHT_TOGGLE_OVER))
                .add("pic3URL", theme.getImagePath(
                        ThemeImages.CTS_RIGHT_TOGGLE))
                .build();

        renderInitScriptTag(writer, "commonTasksSection", initProps);
    }

    /**
     * Render the heading for the common tasks section.
     *
     * @param title The title to be rendered
     * @param writer The ResponseWriter object
     * @param cts The commonTasksSection object
     * @param theme The current theme
     * @param context The FacesContext
     * @throws java.io.IOException
     */
    protected void renderHeading(String title, ResponseWriter writer,
            CommonTasksSection cts, Theme theme, FacesContext context)
            throws IOException {

        writer.startElement(HTMLElements.TR, cts);
        writer.writeAttribute(HTMLAttributes.VALIGN, "top",
                HTMLAttributes.VALIGN);
        writer.startElement(HTMLElements.TD, cts);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_TOP_BOX),
                HTMLAttributes.CLASS);
        writer.writeAttribute(HTMLAttributes.COLSPAN, "4",
                HTMLAttributes.COLSPAN);
        writer.writeAttribute(HTMLAttributes.HEIGHT, "64",
                HTMLAttributes.HEIGHT);
        writer.startElement(HTMLElements.DIV, cts);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_HEADER),
                HTMLAttributes.CLASS);
        writer.write(title);
        writer.endElement(HTMLElements.DIV);

        // Add Inline help.
        UIComponent comp = cts.getHelp(context);

        // If a div does not exist, then the styles dont get applied
        // properly. So, just check whether if it is help inline for
        // which case the div is rendered. Otherwise, assume no div
        // and render an extra div.
        if (!(comp instanceof HelpInline)) {
            writer.startElement(HTMLElements.DIV, cts);
            writer.writeAttribute(HTMLAttributes.CLASS,
                    theme.getStyleClass(ThemeStyles.CTS_SECTION_HELP),
                    HTMLAttributes.CLASS);
            renderComponent(comp, context);
            writer.endElement(HTMLElements.DIV);
        } else {
            ((HelpInline) comp).setStyleClass(
                    theme.getStyleClass(ThemeStyles.CTS_SECTION_HELP));
            renderComponent(comp, context);
        }

        writer.endElement(HTMLElements.TD);
        writer.endElement(HTMLElements.TR);
    }

    /**
     * Set appropriate column widths for the {@code commontaskssection}.
     * 
     * @param context The current FacesContext
     * @param cts The commonTasksSection object.
     * @param writer The ResponseWriter object
     * @param theme The current theme
     * @param numColumns The number of columns for the commonTasksSection.
     * @exception IOException if an input/output error occurs
     */
    protected void renderSpacer(ResponseWriter writer, UIComponent cts,
            Theme theme, int numColumns, FacesContext context)
            throws IOException {

        int _spacerWidth = 40;
        int columnWidth = 200;
        int _sepWidth = 9;
        int _initWidth = 5;

        if (numColumns > 1) {
            _spacerWidth = _spacerWidth / (numColumns - 1);
            columnWidth = columnWidth / (numColumns - 1);
            _sepWidth = _sepWidth / (numColumns - 1);
            _initWidth = _initWidth / (numColumns - 1);
        }
        String spacerWidth = "" + _spacerWidth + "%";
        String sepWidth = "" + _sepWidth + "%";
        String initWidth = "" + _initWidth + "%";

        writer.startElement(HTMLElements.TR, cts);
        // This is for tasks

        if (numColumns == 1) {
            // Fix the case when only one column exists.
            numColumns = 2;
        }
        for (int i = 0; i < numColumns; i++) {
            // Set the spacing for each commontask element
            writer.startElement(HTMLElements.TD, cts);
            writer.writeAttribute(HTMLAttributes.WIDTH, spacerWidth,
                    HTMLAttributes.WIDTH);
            renderSpacerImage(cts, 1, columnWidth, theme, context,
                    SPACER_IMAGE + i);
            writer.endElement(HTMLElements.TD);

            //set the spacing between two columns
            writer.startElement(HTMLElements.TD, cts);
            if (i == numColumns - 1) {
                writer.writeAttribute(HTMLAttributes.WIDTH, initWidth,
                        HTMLAttributes.WIDTH);
            } else {
                writer.writeAttribute(HTMLAttributes.WIDTH, sepWidth,
                        HTMLAttributes.WIDTH);
            }
            writer.write(WHITE_SPACE);
            writer.endElement(HTMLElements.TD);
        }

        // End of tasks

        writer.endElement(HTMLElements.TR);
    }

    /**
     * Render The spacer image.
     * 
     * @param context The current FacesContext
     * @param height height value
     * @param width width value
     * @param theme theme
     * @param component The CommonTasksSection object to render
     * @param id component id
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderSpacerImage(UIComponent component, int height,
            int width, Theme theme, FacesContext context, String id)
            throws IOException {

        Icon img = getIcon(theme, ThemeImages.CTS_SPACER_IMAGE);
        img.setParent(component);
        img.setId(id);
        renderComponent(img, context);
    }

    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer)
            throws IOException {
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    /**
     * Distributes the common tasks between the columns in the common tasks
     * section.
     *
     * @param cts The common tasks section component
     * @param theme The current theme
     * @param context
     * @throws java.io.IOException
     * @oaram context The faces context
     * @param writer the writer to use
     * @param numColumns The number of columns the common tasks section has.
     */
    protected void layoutCommonTasks(CommonTasksSection cts, Theme theme,
            FacesContext context, ResponseWriter writer, int numColumns)
            throws IOException {

        List children = cts.getChildren();
        Iterator itr = children.iterator();
        UIComponent comp;
        int commonTaskCount = cts.getCommonTaskCount();
        int cnt = numColumns;
        int separator =
                (int) java.lang.Math.ceil((double) commonTaskCount /
                (double) numColumns);

        // Keep track of number of commontasks that have been rendered;
        int tmp, count = 0;

        renderJavascript(theme, writer, cts, context);

        // Render all the cobmmon task groups.
        while (itr.hasNext()) {
            Object obj = itr.next();
            comp = (UIComponent) obj;

            // For the case when number of common tasks groups is lesser than
            // or equal to the number of columns, just renderer each task group
            // in each column. 

            if (cts.getCommonTaskCount() <= numColumns && count > 0) {
                writer.endElement(HTMLElements.TD);
                writer.startElement(HTMLElements.TD, cts);
                writer.write(WHITE_SPACE);
                writer.endElement(HTMLElements.TD);
                writer.startElement(HTMLElements.TD, cts);
                writer.writeAttribute(HTMLAttributes.VALIGN, "top",
                        HTMLAttributes.VALIGN);

            // For other cases, try to distribute the commonTasks between
            // all the columns as evenly as possible.
            // CommonTasks for a particular commonTasksGroup cannot be
            // split between two columns.

            } else if ((count >= separator
                    || ((comp instanceof CommonTasksGroup)
                    && (count + ((CommonTasksGroup) comp)
                            .getChildCount() > separator)&& count > 0))
                    && cnt > 1) {

                cnt--;
                writer.endElement(HTMLElements.TD);
                writer.startElement(HTMLElements.TD, cts);
                writer.write(WHITE_SPACE);
                writer.endElement(HTMLElements.TD);
                writer.startElement(HTMLElements.TD, cts);
                writer.writeAttribute(HTMLAttributes.VALIGN, "top",
                        HTMLAttributes.VALIGN);
                tmp = numColumns - 1;
                separator = (int) java.lang.Math.ceil(
                        (double) (commonTaskCount - count) / (double) (tmp));
                count = 0;
            }

            renderComponent(comp, context);
            if (comp instanceof CommonTasksGroup) {
                count = count + comp.getChildCount();
            } else {
                count++;
            }
        }
    }
}
