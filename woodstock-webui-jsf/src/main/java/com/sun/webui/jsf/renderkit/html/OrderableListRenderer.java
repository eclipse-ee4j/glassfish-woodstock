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
 * $Id: OrderableListRenderer.java,v 1.1.4.1.2.1 2009-12-29 04:52:44 jyeary Exp $
 */
/*
 * OrderableListRenderer.java
 *
 * Created on December 23, 2004, 11:11 AM
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.ListSelector;
import com.sun.webui.jsf.component.OrderableList;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author avk
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.OrderableList"))
public class OrderableListRenderer extends ListRendererBase {

    private final static boolean DEBUG = false;

    /**
     * <p>Render the orderable list component
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * end should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {

        if (DEBUG) {
            log("encodeEnd()"); //NOI18N
        }
        if (component instanceof OrderableList) {
            renderListComponent(context, (OrderableList) component,
                    getStyles(component, context));
        } else {
            String message = "Component " + component.toString() + //NOI18N
                    " has been associated with an OrderableListRenderer. " + //NOI18N
                    " This renderer can only be used by components " + //NOI18N
                    " that extend com.sun.webui.jsf.component.Selector."; //NOI18N
            throw new FacesException(message);
        }
    }

    /*
     * Retrieve user input from the UI. 
     * @param context The FacesContext of this request
     * @param component The component associated with the renderer
     */
    @Override
    public void decode(FacesContext context, UIComponent component) {

        if (DEBUG) {
            log("decode()");
        }

        String id = component.getClientId(context).concat(ListSelector.VALUE_ID);
        super.decode(context, component, id);

        if (DEBUG && ((OrderableList) component).getSubmittedValue() != null) {
            log("Submitted value is not null");
        }
        return;
    }

    /**
     * <p>This method determines whether the component should be
     * rendered as a standalone list, or laid out together with a
     * label that was defined as part of the component.</p> 
     *
     * <p>A label will be rendered if either of the following is
     * true:</p> 
     * <ul>
     * <li>The page author defined a label facet; or</li>
     * <li>The page author specified text in the label attribute.</li>
     * </ul> 
     * <p>If there is a label, the component will be laid out using a
     * HTML table. If not, the component will be rendered as a
     * standalone HTML <tt>select</tt> element.</p>
     * @param component The component associated with the
     * renderer. Must be a subclass of ListSelector.
     * @param context The FacesContext of the request
     * @param styles A String array of styles used to render the
     * component. The first item of the array is the name of the
     * JavaScript method that handles change event. The second item is
     * the style used when the list is enabled. The third style is the
     * one to use when the list is disabled. The fourth item is the
     * style to use for an item that is enabled, the fifth to use for
     * an item that is disabled, and the sixth to use when the item is
     * selected.
     * @throws java.io.IOException if the renderer fails to write to
     * the response
     */
    private void renderListComponent(FacesContext context,
            OrderableList component, String[] styles) throws IOException {
        if (DEBUG) {
            log("renderListComponent()");
        }
        if (component.isReadOnly()) {
            UIComponent label = component.getHeaderComponent();
            super.renderReadOnlyList(component, label, context, styles[15]);
            return;
        }

        ResponseWriter writer = context.getResponseWriter();
        renderOpenEncloser(component, context, "div", styles[15]); //NOI18N

        // If the label goes on top, render it first... 

        UIComponent headerComponent = component.getHeaderComponent();
        if (headerComponent != null) {
            if (!component.isLabelOnTop()) {
                writer.writeText("\n", null); //NOI18N
                writer.startElement("span", component);
                writer.writeAttribute("class", styles[10], null);
                writer.writeText("\n", null); //NOI18N

                RenderingUtilities.renderComponent(headerComponent, context);
                writer.writeText("\n", null); //NOI18N
                writer.endElement("span"); //NOI18N
                writer.writeText("\n", null); //NOI18N              
            } else {
                RenderingUtilities.renderComponent(headerComponent, context);
                writer.startElement("br", component); //NOI18N
                writer.endElement("br"); //NOI18N
            }
        }

        // First column: available items
        renderColumnTop(component, writer, styles[10]);
        String id =
                component.getClientId(context).concat(ListSelector.LIST_ID);


        renderList(component, id, context, styles);
        renderColumnBottom(writer);

        // Second column: button row
        renderColumnTop(component, writer, styles[10]);
        renderButtons(component, context, writer, styles);
        renderColumnBottom(writer);

        writer.startElement("div", component); //NOI18N
        writer.writeAttribute("class", styles[11], null); //NOI18N
        writer.endElement("div"); //NOI18N

        UIComponent footerComponent =
                component.getFacet(OrderableList.FOOTER_FACET);
        if (footerComponent != null) {
            writer.startElement("div", component); //NOI18N
            writer.writeText("\n", null); //NOI18N
            RenderingUtilities.renderComponent(footerComponent, context);
            writer.writeText("\n", null); //NOI18N
            writer.endElement("div"); //NOI18N
            writer.writeText("\n", null); //NOI18N
        }

        String jsID = component.getClientId(context);

        // The value field
        // Call super renderValueField ?
        //
	/*
        RenderingUtilities.renderHiddenField
        (component, writer, jsID.concat(VALUES_ID),
        component.getValueAsString(context, component.getSeparator()));
         */
        renderHiddenValue(component, context, writer, styles[15]);

        writer.writeText("\n", null); //NOI18N
        writer.endElement("div"); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.endElement("div"); //NOI18N
        writer.writeText("\n", null); //NOI18N

        renderJavaScript(component, context, writer, styles);
    }

    private void renderJavaScript(UIComponent component, FacesContext context,
            ResponseWriter writer, String[] styles) throws IOException {
        try {
            // Append properties.
            StringBuffer buff = new StringBuffer(256);
            JSONObject json = new JSONObject();
            json.put("id", component.getClientId(context)).put("moveMessage", styles[14]);

            // Append JavaScript.
//            buff.append(JavaScriptUtilities.getModule("orderableList")).append("\n") // NOI18N
            buff.append("require(['").append(JavaScriptUtilities.getModuleName("orderableList")).append("'], function (orderableList) {").append("\n") // NOI18N
//                    .append(JavaScriptUtilities.getModuleName("orderableList.init")) // NOI18N
                    .append("orderableList.init") // NOI18N
                    .append("(") //NOI18N
                    .append(json.toString(JavaScriptUtilities.INDENT_FACTOR)).append(");\n") //NOI18N
                    .append(JavaScriptUtilities.getDomNode(context, component)).append(OrderableList.UPDATEBUTTONS_FUNCTION);
            buff.append("});");

            // Render JavaScript.
            JavaScriptUtilities.renderJavaScript(component, writer,
                    buff.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void renderColumnTop(OrderableList component, ResponseWriter writer,
            String style) throws IOException {
        // Render the available elements
        writer.startElement("div", component); //NOI18N
        writer.writeAttribute("class", style, null);  //NOI18N
        writer.writeText("\n", null); //NOI18N
    }

    private void renderColumnBottom(ResponseWriter writer) throws IOException {
        writer.writeText("\n", null); //NOI18N
        writer.endElement("div"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }

    private void renderButtons(OrderableList component, FacesContext context,
            ResponseWriter writer, String[] styles) throws IOException {
        writer.writeText("\n", null); //NOI18N
        writer.startElement("div", component); //NOI18N
        String style = "padding-left:10;padding-right:10"; //NOI18N
        writer.writeAttribute("style", style, null);  //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.startElement("table", component); //NOI18N
        writer.writeAttribute("class", styles[12], null); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.startElement("tr", component); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.startElement("td", component); //NOI18N
        writer.writeAttribute("align", "center", null);  //NOI18N
        writer.writeAttribute("width", "125", null);  //NOI18N
        writer.writeText("\n", null); //NOI18N
        RenderingUtilities.renderComponent(
                component.getMoveUpButtonComponent(context), context);
        writer.writeText("\n", null); //NOI18N

        renderButton(component, component.getMoveDownButtonComponent(context),
                styles[9], writer, context);

        if (component.isMoveTopBottom()) {
            renderButton(component, component.getMoveTopButtonComponent(context),
                    styles[8], writer, context);
            renderButton(component, component.getMoveBottomButtonComponent(context),
                    styles[9], writer, context);
        }
        writer.endElement("td"); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.endElement("tr"); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.endElement("table"); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.endElement("div"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }

    private void renderButton(OrderableList list, UIComponent comp, String style,
            ResponseWriter writer, FacesContext context) throws IOException {
        if (comp == null) {
            return;
        }

        writer.startElement("div", list); //NOI18N
        writer.writeAttribute("class", style, null);  //NOI18N
        writer.writeText("\n", null); //NOI18N
        RenderingUtilities.renderComponent(comp, context);
        writer.writeText("\n", null); //NOI18N
        writer.endElement("div"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }

    /**
     * Overrides encodeChildren of Renderer to do nothing. This
     * renderer renders its own children, but not through this
     * method. 
     * @param context The FacesContext of the request
     * @param component The component associated with the
     * renderer. Must be a subclass of ListSelector.
     */
    @Override
    public void encodeChildren(javax.faces.context.FacesContext context,
            javax.faces.component.UIComponent component) throws java.io.IOException {
        return;
    }

    /**
     * <p>Renders a component in a table row.</p>
     * @param component The component
     * @param context The FacesContext of the request
     * @throws java.io.IOException if the renderer fails to write to
     * the response
     */
    private void addComponentSingleRow(OrderableList list,
            UIComponent component, FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("tr", list);                    //NOI18N
        writer.writeText("\n", null);                       //NOI18N
        writer.startElement("td", list);                    //NOI18N
        RenderingUtilities.renderComponent(component, context);
        writer.writeText("\n", null);                       //NOI18N
        // Perhaps this should depend on the dir?
        // writer.writeAttribute("align", "left", null);
        writer.endElement("td");                            //NOI18N
        writer.writeText("\n", null);                       //NOI18N
        writer.endElement("tr");                            //NOI18N
        writer.writeText("\n", null);                       //NOI18N
    }

    /**
     * <p>Render the appropriate element end, depending on the value of the
     * <code>type</code> property.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param monospace <code>UIComponent</code> if true, use the monospace
     * styles to render the list.
     *
     * @exception IOException if an input/output error occurs
     */
    private String[] getStyles(UIComponent component, FacesContext context) {
        if (DEBUG) {
            log("getStyles()");
        }

        Theme theme = ThemeUtilities.getTheme(context);
        StringBuffer buff = new StringBuffer(256);
        buff.append(JavaScriptUtilities.getDomNode(context, component)).append(OrderableList.ONCHANGE_FUNCTION).append(OrderableList.RETURN);

        String[] styles = new String[16];

        styles[0] = buff.toString();
        styles[1] = theme.getStyleClass(ThemeStyles.LIST);
        styles[2] = theme.getStyleClass(ThemeStyles.LIST_DISABLED);
        styles[3] = theme.getStyleClass(ThemeStyles.LIST_OPTION);
        styles[4] = theme.getStyleClass(ThemeStyles.LIST_OPTION_DISABLED);
        styles[5] = theme.getStyleClass(ThemeStyles.LIST_OPTION_SELECTED);
        styles[6] = theme.getStyleClass(ThemeStyles.LIST_OPTION_GROUP);
        styles[7] = theme.getStyleClass(ThemeStyles.LIST_OPTION_SEPARATOR);
        styles[8] = theme.getStyleClass(ThemeStyles.ADDREMOVE_HORIZONTAL_BETWEEN);
        styles[9] = theme.getStyleClass(ThemeStyles.ADDREMOVE_HORIZONTAL_WITHIN);
        styles[10] = theme.getStyleClass(ThemeStyles.ADDREMOVE_HORIZONTAL_ALIGN);
        styles[11] = theme.getStyleClass(ThemeStyles.ADDREMOVE_HORIZONTAL_LAST);
        styles[12] = theme.getStyleClass(ThemeStyles.ADDREMOVE_BUTTON_TABLE);
        styles[13] = null;
        styles[14] = theme.getMessage("OrderableList.moveMessage"); //NOI18N
        styles[15] = theme.getStyleClass(ThemeStyles.HIDDEN);
        return styles;
    }
}

