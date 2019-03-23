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
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.Label;
import com.sun.webui.jsf.component.Table;
import com.sun.webui.jsf.component.TablePanels;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * This class renders TablePanels components.
 * <p>
 * Note: To see the messages logged by this class, set the following global
 * defaults in your JDK's "jre/lib/logging.properties" file.
 * </p><p><pre>
 * java.util.logging.ConsoleHandler.level = FINE
 * com.sun.webui.jsf.renderkit.html.TablePanelsRenderer.level = FINE
 * </pre></p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.TablePanels"))
public class TablePanelsRenderer extends javax.faces.render.Renderer {

    /**
     * The set of String pass-through attributes to be rendered.
     * <p>
     * Note: The WIDTH, HEIGHT, and BGCOLOR attributes are all deprecated (in
     * the HTML 4.0 spec) in favor of style sheets. In addition, the DIR and 
     * LANG attributes are not cuurently supported.
     * </p>
     */
    private static final String stringAttributes[] = {
        "abbr", //NOI18N
        "align", //NOI18N
        "axis", //NOI18N
        "bgColor", //NOI18N
        "char", //NOI18N
        "charOff", //NOI18N
        "dir", //NOI18N
        "headers", //NOI18N
        "height", //NOI18N
        "lang", //NOI18N
        "onClick", //NOI18N
        "onDblClick", //NOI18N
        "onKeyDown", //NOI18N
        "onKeyPress", //NOI18N
        "onKeyUp", //NOI18N
        "onMouseDown", //NOI18N
        "onMouseUp", //NOI18N
        "onMouseMove", //NOI18N
        "onMouseOut", //NOI18N
        "onMouseOver", //NOI18N
        "style", //NOI18N
        "valign", //NOI18N
        "width"}; //NOI18N

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Render the beginning of the specified UIComponent to the output stream or 
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            log("encodeBegin", //NOI18N
                    "Cannot render, FacesContext or UIComponent is null"); //NOI18N
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            log("encodeBegin", "Component not rendered, nothing to display"); //NOI18N
            return;
        }

        TablePanels panel = (TablePanels) component;
        ResponseWriter writer = context.getResponseWriter();
        renderEnclosingTagStart(context, panel, writer);
    }

    /**
     * Render the children of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be decoded.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            log("encodeChildren", //NOI18N
                    "Cannot render, FacesContext or UIComponent is null"); //NOI18N
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            log("encodeChildren", "Component not rendered, nothing to display"); //NOI18N
            return;
        }

        TablePanels panel = (TablePanels) component;
        ResponseWriter writer = context.getResponseWriter();

        // Render panels.
        if (panel.isFilterPanel()) {
            renderFilterPanel(context, panel, writer);
        }
        if (panel.isPreferencesPanel()) {
            renderPreferencesPanel(context, panel, writer);
        }
        renderSortPanel(context, panel, writer);
    }

    /**
     * Render the ending of the specified UIComponent to the output stream or 
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            log("encodeEnd", //NOI18N
                    "Cannot render, FacesContext or UIComponent is null"); //NOI18N
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            log("encodeEnd", "Component not rendered, nothing to display"); //NOI18N
            return;
        }

        TablePanels panel = (TablePanels) component;
        ResponseWriter writer = context.getResponseWriter();
        renderEnclosingTagEnd(context, panel, writer);
    }

    /**
     * Return a flag indicating whether this Renderer is responsible
     * for rendering the children the component it is asked to render.
     * The default implementation returns false.
     */
    @Override
    public boolean getRendersChildren() {
        return true;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Panel methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Render filter panel for TablePanels components.
     *
     * @param context FacesContext for the current request.
     * @param component TablePanels to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderFilterPanel(FacesContext context,
            TablePanels component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderFilterPanel", //NOI18N
                    "Cannot render filter panel, TablePanels is null"); //NOI18N
            return;
        }

        // Get facet.
        Table table = component.getTableAncestor();
        UIComponent facet = (table != null)
                ? table.getFacet(Table.FILTER_PANEL_FACET) : null;
        if (!(facet != null && facet.isRendered())) {
            log("renderFilterPanel", //NOI18N
                    "Filter panel not rendered, nothing to display"); //NOI18N
            return;
        }

        // Render filter panel.
        renderPanelStart(context, component, writer, TablePanels.FILTER_PANEL_ID,
                getTheme().getMessage("table.panel.filterTitle"), null); //NOI18N
        RenderingUtilities.renderComponent(facet, context);
        renderPanelEnd(writer);
    }

    /**
     * Render preferences panel for TablePanels components.
     *
     * @param context FacesContext for the current request.
     * @param component TablePanels to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderPreferencesPanel(FacesContext context,
            TablePanels component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderPreferencesPanel", //NOI18N
                    "Cannot render preferences panel, TablePanels is null"); //NOI18N
            return;
        }

        // Get facet.
        Table table = component.getTableAncestor();
        UIComponent facet = (table != null)
                ? table.getFacet(Table.PREFERENCES_PANEL_FACET) : null;
        if (!(facet != null && facet.isRendered())) {
            log("renderPreferencesPanel", //NOI18N
                    "Preferences panel not rendered, nothing to display"); //NOI18N
            return;
        }

        // Render filter panel.
        renderPanelStart(context, component, writer, TablePanels.PREFERENCES_PANEL_ID,
                getTheme().getMessage("table.panel.preferencesTitle"), null); //NOI18N
        RenderingUtilities.renderComponent(facet, context);
        renderPanelEnd(writer);
    }

    /**
     * Render sort panel for TablePanels components.
     *
     * @param context FacesContext for the current request.
     * @param component TablePanels to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderSortPanel(FacesContext context,
            TablePanels component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderSortPanel", //NOI18N
                    "Cannot render sort panel, TablePanels is null"); //NOI18N
            return;
        }

        // Render filter panel
        Theme theme = getTheme();
        renderPanelStart(context, component, writer, TablePanels.SORT_PANEL_ID,
                theme.getMessage("table.panel.sortTitle"), //NOI18N
                theme.getMessage("table.panel.help")); //NOI18N

        // Get facet.
        Table table = component.getTableAncestor();
        UIComponent facet = (table != null)
                ? table.getFacet(Table.SORT_PANEL_FACET) : null;
        if (facet != null && facet.isRendered()) {
            RenderingUtilities.renderComponent(facet, context);
        } else {
            renderSortPanelLayout(context, component, writer);
        }
        renderPanelEnd(writer);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Enclosing tag methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Render enclosing tag for TablePanels components.
     *
     * @param context FacesContext for the current request.
     * @param component TablePanels to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderEnclosingTagStart(FacesContext context,
            TablePanels component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderEnclosingTagStart", //NOI18N
                    "Cannot render enclosing tag, TablePanels is null"); //NOI18N
            return;
        }

        Theme theme = getTheme();
        writer.writeText("\n", null); //NOI18N
        writer.startElement("td", component); //NOI18N
        writer.writeAttribute("id", component.getClientId(context), null); //NOI18N

        // Render style class.
        String extraHtml = RenderingUtilities.renderStyleClass(context, writer,
                component, theme.getStyleClass(ThemeStyles.TABLE_PANEL_TD),
                component.getExtraHtml());

        // Render colspan.
        if (component.getColSpan() > -1 && (extraHtml == null || extraHtml.indexOf("colspan=") == -1)) { //NOI18N
            writer.writeAttribute("colspan", //NOI18N
                    Integer.toString(component.getColSpan()), null); //NOI18N
        }

        // Render rowspan.
        if (component.getRowSpan() > -1 && (extraHtml == null || extraHtml.indexOf("rowspan=") == -1)) { //NOI18N
            writer.writeAttribute("rowspan", //NOI18N
                    Integer.toString(component.getRowSpan()), null); //NOI18N
        }

        // Render nowrap.
        if (component.isNoWrap() && (extraHtml == null || extraHtml.indexOf("nowrap=") == -1)) { //NOI18N
            writer.writeAttribute("nowrap", "nowrap", null); //NOI18N
        }

        // Render tooltip.
        if (component.getToolTip() != null && (extraHtml == null || extraHtml.indexOf("title=") == -1)) { //NOI18N
            writer.writeAttribute("title", component.getToolTip(), "toolTip"); //NOI18N
        }

        // Render pass through attributes.
        RenderingUtilities.writeStringAttributes(component, writer,
                stringAttributes, extraHtml);
    }

    /**
     * Render enclosing tag for TablePanels components.
     *
     * @param context FacesContext for the current request.
     * @param component TablePanels to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderEnclosingTagEnd(FacesContext context,
            TablePanels component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderEnclosingTagEnd", //NOI18N
                    "Cannot render enclosing tag, TablePanels is null"); //NOI18N
            return;
        }
        writer.endElement("td"); //NOI18N
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Get component id.
     *
     * @param component The parent UIComponent component.
     * @param id The id of the the component to be rendered.
     */
    private String getId(UIComponent component, String id) {
        String clientId = component.getClientId(FacesContext.getCurrentInstance());
        return clientId + NamingContainer.SEPARATOR_CHAR + id;
    }

    /** Helper method to get Theme objects. */
    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }

    /**
     * Log fine messages.
     */
    private void log(String method, String message) {
        // Get class.
        Class clazz = this.getClass();
        if (LogUtil.fineEnabled(clazz)) {
            // Log method name and message.
            LogUtil.fine(clazz, clazz.getName() + "." + method + ": " + message); //NOI18N
        }
    }

    /**
     * Helper method to render embedded panel for TablePanels components.
     *
     * @param context FacesContext for the current request.
     * @param component TablePanels to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     * @param id The identifier for the component.
     * @param title The title for the panel.
     * @param tip The help tip for the panel.
     *
     * @exception IOException if an input/output error occurs.
     */
    private void renderPanelStart(FacesContext context, TablePanels component,
            ResponseWriter writer, String id, String title, String tip)
            throws IOException {
        if (component == null) {
            log("renderPanelStart", "Cannot render panel, TablePanels is null"); //NOI18N
            return;
        }

        Theme theme = getTheme();
        writer.writeText("\n", null); //NOI18N
        writer.startElement("div", component); //NOI18N

        // Render client id.
        writer.writeAttribute("id", getId(component, id), null); //NOI18N

        // Render style (i.e., div is initially hidden).
        writer.writeAttribute("class", theme.getStyleClass(ThemeStyles.HIDDEN), //NOI18N
                null);

        // Render div used to create drop shadow effect.
        writer.writeText("\n", null); //NOI18N
        writer.startElement("div", component); //NOI18N

        // Render style class.
        writer.writeAttribute("class", theme.getStyleClass( //NOI18N
                ThemeStyles.TABLE_PANEL_SHADOW3_DIV), null);

        // Render div used to create drop shadow effect.
        writer.writeText("\n", null); //NOI18N
        writer.startElement("div", component); //NOI18N

        // Render style class.
        writer.writeAttribute("class", theme.getStyleClass( //NOI18N
                ThemeStyles.TABLE_PANEL_SHADOW2_DIV), null);

        // Render div used to create drop shadow effect.
        writer.writeText("\n", null); //NOI18N
        writer.startElement("div", component); //NOI18N

        // Render style class.
        writer.writeAttribute("class", theme.getStyleClass( //NOI18N
                ThemeStyles.TABLE_PANEL_SHADOW1_DIV), null);

        // Render div used to create the yellow box itself.
        writer.writeText("\n", null); //NOI18N
        writer.startElement("div", component); //NOI18N

        // Render style class.
        writer.writeAttribute("class", theme.getStyleClass( //NOI18N
                ThemeStyles.TABLE_PANEL_DIV), null);

        // Render div used to create help tip.
        if (tip != null) {
            writer.writeText("\n", null); //NOI18N
            writer.startElement("div", component); //NOI18N
            writer.writeAttribute("class", theme.getStyleClass( //NOI18N
                    ThemeStyles.TABLE_PANEL_HELP_TEXT), null);
            writer.writeText(tip, null); //NOI18N        
            writer.endElement("div"); //NOI18N
        }

        // Render div used to format the title in the box.
        writer.writeText("\n", null); //NOI18N
        writer.startElement("div", component); //NOI18N

        // Render style class.
        writer.writeAttribute("class", theme.getStyleClass( //NOI18N
                ThemeStyles.TABLE_PANEL_TITLE), null);

        // Render title.
        writer.writeText(title, null);
        writer.endElement("div"); //NOI18N

        // Render div for whatever content goes in the box.
        writer.writeText("\n", null); //NOI18N
        writer.startElement("div", component); //NOI18N

        // Render style class.
        writer.writeAttribute("class", theme.getStyleClass( //NOI18N
                ThemeStyles.TABLE_PANEL_CONTENT), null);
    }

    /**
     * Helper method to render embedded panel for Table components.
     *
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    private void renderPanelEnd(ResponseWriter writer) throws IOException {
        writer.endElement("div"); //NOI18N
        writer.endElement("div"); //NOI18N
        writer.endElement("div"); //NOI18N
        writer.endElement("div"); //NOI18N
        writer.endElement("div"); //NOI18N
        writer.endElement("div"); //NOI18N
    }

    /**
     * Helper method to render sort panel layout for Table components.
     *
     * @param context FacesContext for the current request.
     * @param component TablePanels to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    private void renderSortPanelLayout(FacesContext context,
            TablePanels component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderSortPanelLayout", //NOI18N
                    "Cannot render sort panel layout, TablePanels is null"); //NOI18N
            return;
        }

        // Render panel layout table.
        Theme theme = getTheme();
        writer.writeText("\n", null); //NOI18N
        writer.startElement("table", component); //NOI18N
        writer.writeAttribute("class", //NOI18N
                theme.getStyleClass(ThemeStyles.TABLE_PANEL_TABLE), null);
        writer.writeAttribute("border", "0", null); //NOI18N
        writer.writeAttribute("cellpadding", "0", null); //NOI18N
        writer.writeAttribute("cellspacing", "0", null); //NOI18N

        // Render primary sort column menu.
        renderSortPanelRow(context, component, writer,
                component.getPrimarySortColumnMenuLabel(),
                component.getPrimarySortColumnMenu(),
                component.getPrimarySortOrderMenu());

        // Render secondary sort column menu if there are more than 2 choices,
        // including the none selected option.
        UIComponent menu = component.getSecondarySortColumnMenu();
        Option[] options = (menu instanceof DropDown)
                ? (Option[]) ((DropDown) menu).getItems() : null;
        if (options != null && options.length > 2) {
            renderSortPanelRow(context, component, writer,
                    component.getSecondarySortColumnMenuLabel(), menu,
                    component.getSecondarySortOrderMenu());
        } else {
            log("renderSortPanelLayout", //NOI18N
                    "Secondary sort column menu not rendered"); //NOI18N
        }

        // Render tertiary sort column menu if there are more than 3 choices,
        // including the none selected option.
        menu = component.getTertiarySortColumnMenu();
        options = (menu instanceof DropDown)
                ? (Option[]) ((DropDown) menu).getItems() : null;
        if (options != null && options.length > 3) {
            renderSortPanelRow(context, component, writer,
                    component.getTertiarySortColumnMenuLabel(), menu,
                    component.getTertiarySortOrderMenu());
        } else {
            log("renderSortPanelLayout", //NOI18N
                    "Tertiary sort column menu not rendered"); //NOI18N
        }

        writer.endElement("table"); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.startElement("div", component); //NOI18N
        writer.writeAttribute("class", theme.getStyleClass( //NOI18N
                ThemeStyles.TABLE_PANEL_BUTTON_DIV), null);

        // Render ok and cancel buttons.
        RenderingUtilities.renderComponent(
                component.getSortPanelSubmitButton(), context);
        RenderingUtilities.renderComponent(
                component.getSortPanelCancelButton(), context);

        writer.endElement("div"); //NOI18N
    }

    /**
     * Helper method to render sort panel row for Table components.
     *
     * @param context FacesContext for the current request.
     * @param component TablePanels to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     * @param label The sort label.
     * @param columnMenu The sort column menu.
     * @param orderMenu The sort order menu.
     *
     * @exception IOException if an input/output error occurs.
     */
    private void renderSortPanelRow(FacesContext context, TablePanels component,
            ResponseWriter writer, UIComponent label, UIComponent columnMenu,
            UIComponent orderMenu) throws IOException {
        if (component == null) {
            log("renderSortPanelRow", //NOI18N
                    "Cannot render sort panel row, TablePanels is null"); //NOI18N
            return;
        }

        writer.writeText("\n", null); //NOI18N
        writer.startElement("tr", component); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.startElement("td", component); //NOI18N

        // Render primary sort column menu label.
        if (label instanceof Label) {
            ((Label) label).setFor(columnMenu.getClientId(context));
            RenderingUtilities.renderComponent(label, context);
        } else {
            log("renderSortPanelRow", //NOI18N
                    "Cannot render label, not Label instance"); //NOI18N
        }

        writer.endElement("td"); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.startElement("td", component); //NOI18N

        // Render primary sort column menu.
        RenderingUtilities.renderComponent(columnMenu, context);

        writer.endElement("td"); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.startElement("td", component); //NOI18N

        // Render primary sort order menu.
        RenderingUtilities.renderComponent(orderMenu, context);

        writer.endElement("td"); //NOI18N
        writer.endElement("tr"); //NOI18N
    }
}
