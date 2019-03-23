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
 * $Id: EditableListRenderer.java,v 1.1.12.1 2009-12-29 04:52:46 jyeary Exp $
 */
/*
 * EditableListRenderer.java
 *
 * Created on December 23, 2004, 11:11 AM
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import com.sun.webui.jsf.component.ComplexComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.EditableList;
import com.sun.webui.jsf.component.ListSelector;
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
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.EditableList"))
public class EditableListRenderer extends ListRendererBase {

    private final static boolean DEBUG = false;

    /**
     * <p>Render the editable list component
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
        if (component instanceof EditableList) {

            renderListComponent((EditableList) component,
                    context,
                    getStyles(component, context));
        } else {
            String message = "Component " + component.toString() + //NOI18N
                    " has been associated with an EditableListRenderer. " + //NOI18N
                    " This renderer can only be used by components " + //NOI18N
                    " that extend com.sun.webui.jsf.component.Selector."; //NOI18N
            throw new FacesException(message);
        }
    }

    /**
     * Retrieve user input from the UI. 
     * @param context The FacesContext of this request
     * @param component The component associated with the renderer
     */
    @Override
    public void decode(FacesContext context, UIComponent component) {

        if (DEBUG) {
            log("decode()");
        }

        EditableList list = (EditableList) component;

        if (list.isReadOnly()) {
            if (DEBUG) {
                log("component is readonly...");
            }
            return;
        }

        String id = list.getClientId(context);
        Map params =
                context.getExternalContext().getRequestParameterValuesMap();

        // The id for the editable list content or "select" element.
        // If there is a parameter matching listID then
        // there have been selections made. These are only
        // respected when the action is remove.
        // Do it every time, since we cannot know which action
        // has taken place, an add or a remove.
        //
        String listID = null;
        if (list instanceof ComplexComponent) {
            listID = list.getLabeledElementId(context);
        } else {
            listID = list.getClientId(context);
        }
        String[] selections = null;
        Object parameters = params.get(listID);
        if (parameters == null) {
            selections = new String[0];
        } else if (parameters instanceof String[]) {
            selections = (String[]) parameters;
        } else {
            selections = new String[1];
            selections[0] = parameters.toString();
        }
        // These are the values to remove.
        // The submitted value for the component will be the
        // current contents of the select element.
        // It is important to not reference the values to
        // remove during an add action.
        //
        list.setValuesToRemove(selections);

        // Always decode the list contents.
        // This is the only place where the current values of the
        // list are kept. This is a result of having both the
        // add and remove buttons set with immediate == true.
        // With immediate true, update model values does not
        // occur. In the listbox case, validation does not occur
        // either therefore the local value of the EditableList
        // which is the contents of the list, is not updated
        // either. The current value is stored only in the
        // submittedValue during the lifecycle of a request
        // initiated by the add or remove button.
        // The update model values will only occur when a submit
        // of the page has occurred.
        // The decoded field is the hidden field rendered
        // by the ListRenderer containing the current list contents.

        String valueID = id.concat(ListSelector.VALUE_ID);
        super.decode(context, component, valueID);
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
    void renderListComponent(EditableList component, FacesContext context,
            String[] styles) throws IOException {
        if (DEBUG) {
            log("renderListComponent()");
        }
        if (component.isReadOnly()) {
            UIComponent label = component.getListLabelComponent();
            super.renderReadOnlyList(component, label, context, styles[8]);
            return;
        }

        UIComponent headerComponent =
                component.getFacet(EditableList.HEADER_FACET);
        UIComponent footerComponent =
                component.getFacet(EditableList.FOOTER_FACET);

        boolean gotHeaderOrFooter =
                (headerComponent != null) || (footerComponent != null);

        ResponseWriter writer = context.getResponseWriter();
        super.renderOpenEncloser(component, context, "div", styles[8]);

        if (DEBUG) {
            log("layout the component");
        }
        if (gotHeaderOrFooter) {
            writer.startElement("table", component);  //NOI18N
            writer.writeText("\n", null);        //NOI18N

            if (headerComponent != null) {
                addComponentSingleRow(component, headerComponent, context);
            }

            // New table row for the list

            writer.startElement("tr", component);     //NOI18N
            writer.writeText("\n", null);        //NOI18N
            writer.startElement("td", component);     //NOI18N
            writer.writeText("\n", null);        //NOI18N
        }

        writer.startElement("table", component);      //NOI18N
        writer.writeAttribute("class", styles[9], null); //NOI18N
        writer.writeText("\n", null);            //NOI18N

        boolean listOnTop = component.isListOnTop();

        if (listOnTop) {
            addListRow(component, context, styles);
            addFieldRow(component, context, styles);
        } else {
            addFieldRow(component, context, styles);
            addListRow(component, context, styles);
        }
        writer.endElement("table");               //NOI18N
        writer.writeText("\n", null);             //NOI18N

        if (gotHeaderOrFooter) {
            writer.endElement("td");              //NOI18N
            writer.writeText("\n", null);         //NOI18N
            writer.endElement("tr");              //NOI18N
            writer.writeText("\n", null);         //NOI18N

            if (footerComponent != null) {
                addComponentSingleRow(component, footerComponent, context);
            }

            writer.endElement("table");           //NOI18N
            writer.writeText("\n", null);         //NOI18N
        }
        writer.endElement("div");                 //NOI18N
        renderJavaScript(component, context, writer);
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
            javax.faces.component.UIComponent component)
            throws java.io.IOException {
        return;
    }

    /**
     * <p>Renders a component in a table row.</p>
     * @param component The component
     * @param context The FacesContext of the request
     * @throws java.io.IOException if the renderer fails to write to
     * the response
     */
    private void addComponentSingleRow(EditableList list,
            UIComponent component,
            FacesContext context)
            throws IOException {

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
     * <p>Renders the list row</p>
     * @param component The component
     * @param context The FacesContext of the request
     * @throws java.io.IOException if the renderer fails to write to
     * the response
     */
    private void addListRow(EditableList component,
            FacesContext context,
            String[] styles)
            throws IOException {


        // Perhaps this should depend on the dir?
        // writer.writeAttribute("align", "left", null);

        UIComponent listLabelComponent =
                component.getListLabelComponent();
        UIComponent removeButtonComponent =
                component.getRemoveButtonComponent();

        ResponseWriter writer = context.getResponseWriter();

        // Begin new row for the list label, list & remove button
        writer.startElement("tr", component);                    //NOI18N
        writer.writeText("\n", null);                       //NOI18N

        // Render the label in a new table cell
        writer.startElement("td", component);               //NOI18N
        writer.writeAttribute("class", styles[13], null);   //NOI18N
        writer.writeAttribute("valign", "top", null);       //NOI18N
        writer.writeText("\n", null);                       //NOI18N
        RenderingUtilities.renderComponent(listLabelComponent, context);
        writer.endElement("td");                            //NOI18N
        writer.writeText("\n", null);                       //NOI18N

        // Render the textfield in a new table cell             
        writer.startElement("td", component);               //NOI18N
        writer.writeAttribute("class", styles[14], null);   //NOI18N
        writer.writeAttribute("valign", "top", null);       //NOI18N
        writer.writeText("\n", null);                       //NOI18N
        super.renderHiddenValue(component, context, writer, styles[8]);
        writer.writeText("\n", null);
        String id = component.getClientId(context).concat(ListSelector.LIST_ID);
        super.renderList(component, id, context, styles);
        writer.endElement("td");                            //NOI18N
        writer.writeText("\n", null);                       //NOI18N

        // Render the button in a new table cell

        // Create another table cell for the list and the remove buttons...
        writer.startElement("td", component);               //NOI18N
        writer.writeAttribute("class", styles[15], null);   //NOI18N
        writer.writeAttribute("valign", "top", null);       //NOI18N
        writer.writeText("\n", null);                       //NOI18N
        RenderingUtilities.renderComponent(removeButtonComponent, context);
        writer.endElement("td");                            //NOI18N
        writer.writeText("\n", null);                       //NOI18N

        // End the row
        writer.endElement("tr");                            //NOI18N
        writer.writeText("\n", null);                       //NOI18N
    }

    /**
     * <p>Renders the list row</p>
     * @param component The component
     * @param context The FacesContext of the request
     * @throws java.io.IOException if the renderer fails to write to
     * the response
     */
    private void addFieldRow(EditableList component,
            FacesContext context, String[] styles)
            throws IOException {


        // Perhaps this should depend on the dir?
        // writer.writeAttribute("align", "left", null);

        UIComponent textfieldLabelComponent =
                component.getFieldLabelComponent();
        UIComponent textfieldComponent =
                component.getFieldComponent();
        UIComponent addButtonComponent =
                component.getAddButtonComponent();
        UIComponent searchButtonComponent =
                component.getFacet(EditableList.SEARCH_FACET);

        ResponseWriter writer = context.getResponseWriter();

        // Begin new row
        writer.startElement("tr", component); //NOI18N
        writer.writeText("\n", null);    //NOI18N

        // Render the label
        writer.startElement("td", component); //NOI18N
        writer.writeAttribute("class", styles[10], null); //NOI18N
        writer.writeText("\n", null);    //NOI18N
        RenderingUtilities.renderComponent(textfieldLabelComponent, context);
        writer.writeText("\n", null);    //NOI18N
        writer.endElement("td");         //NOI18N
        writer.writeText("\n", null);    //NOI18N

        // Create another table cell for the text field, the add
        // button and the search button...
        writer.startElement("td", component); //NOI18N
        writer.writeAttribute("class", styles[11], null); //NOI18N
        writer.writeAttribute("valign", "top", null); //NOI18N
        writer.writeText("\n", null); //NOI18N
        RenderingUtilities.renderComponent(textfieldComponent, context);
        writer.writeText("\n", null);    //NOI18N
        writer.endElement("td");         //NOI18N
        writer.writeText("\n", null);    //NOI18N

        // Create another table cell for the text field, the add
        // button and the search button...
        writer.startElement("td", component); //NOI18N
        writer.writeAttribute("class", styles[12], null); //NOI18N
        writer.writeAttribute("valign", "top", null); //NOI18N
        writer.writeText("\n", null); //NOI18N
        RenderingUtilities.renderComponent(addButtonComponent, context);
        writer.writeText("\n", null);    //NOI18N
        writer.endElement("td");            //NOI18N
        writer.writeText("\n", null);       //NOI18N

        if (searchButtonComponent != null) {
            writer.startElement("td", component); //NOI18N
            writer.writeAttribute("class", styles[12], null); //NOI18N
            writer.writeAttribute("valign", "top", null); //NOI18N
            writer.writeText("\n", null); //NOI18N
            RenderingUtilities.renderComponent(searchButtonComponent, context);
            writer.writeText("\n", null);    //NOI18N
            writer.endElement("td");                          //NOI18N
            writer.writeText("\n", null);                     //NOI18N
        }

        // End the row
        writer.endElement("tr");                              //NOI18N
        writer.writeText("\n", null);                         //NOI18N
    }

    private void renderJavaScript(UIComponent component, FacesContext context,
            ResponseWriter writer) throws IOException {
        try {
            // Append properties.
            StringBuffer buff = new StringBuffer(256);
            JSONObject json = new JSONObject();
            json.put("id", component.getClientId(context));

            // Append JavaScript.
//            buff.append(JavaScriptUtilities.getModule("editableList")).append("\n") // NOI18N
            buff.append("require(['").append(JavaScriptUtilities.getModuleName("editableList")).append("'], function (editableList) {").append("\n") // NOI18N
//                    .append(JavaScriptUtilities.getModuleName("editableList.init")) // NOI18N
                    .append("editableList.init") // NOI18N
                    .append("(") //NOI18N
                    .append(json.toString(JavaScriptUtilities.INDENT_FACTOR)).append(");\n") //NOI18N
                    .append(JavaScriptUtilities.getDomNode(context, component)).append(EditableList.UPDATE_BUTTONS_FUNCTION);
            buff.append("});");

            // Render JavaScript.
            JavaScriptUtilities.renderJavaScript(component, writer,
                    buff.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
    private String[] getStyles(UIComponent component,
            FacesContext context) {

        if (DEBUG) {
            log("getStyles()"); //NOI18N
        }
        Theme theme = ThemeUtilities.getTheme(context);

        StringBuffer onchangeBuffer = new StringBuffer(128);
        onchangeBuffer.append(((EditableList) component).getOnChange());
        onchangeBuffer.append(JavaScriptUtilities.getModuleName("listbox.changed")); //NOI18N
        onchangeBuffer.append("('"); //NOI18N
        onchangeBuffer.append(component.getClientId(context));
        onchangeBuffer.append("'); return false;"); //NOI18N

        String[] styles = new String[17];
        styles[0] = onchangeBuffer.toString(); //NOI18N
        styles[1] = theme.getStyleClass(ThemeStyles.LIST);
        styles[2] = theme.getStyleClass(ThemeStyles.LIST_DISABLED);
        styles[3] = theme.getStyleClass(ThemeStyles.LIST_OPTION);
        styles[4] = theme.getStyleClass(ThemeStyles.LIST_OPTION_DISABLED);
        styles[5] = theme.getStyleClass(ThemeStyles.LIST_OPTION_SELECTED);
        styles[6] = theme.getStyleClass(ThemeStyles.LIST_OPTION_GROUP);
        styles[7] = theme.getStyleClass(ThemeStyles.LIST_OPTION_SEPARATOR);
        styles[8] = theme.getStyleClass(ThemeStyles.HIDDEN);
        styles[9] = theme.getStyleClass(ThemeStyles.EDITABLELIST_TABLE);
        styles[10] = theme.getStyleClass(ThemeStyles.EDITABLELIST_FIELD_LABEL);
        styles[11] = theme.getStyleClass(ThemeStyles.EDITABLELIST_FIELD);
        styles[12] = theme.getStyleClass(ThemeStyles.EDITABLELIST_ADD_BUTTON);
        styles[13] = theme.getStyleClass(ThemeStyles.EDITABLELIST_LIST_LABEL);
        styles[14] = theme.getStyleClass(ThemeStyles.EDITABLELIST_LIST);
        styles[15] = theme.getStyleClass(ThemeStyles.EDITABLELIST_REMOVE_BUTTON);
        styles[16] = null;
        return styles;
    }
}

