/*
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.webui.jsf.example;

import com.sun.webui.jsf.example.util.ExampleUtilities;
import com.sun.webui.jsf.component.Checkbox;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.component.Table;
import com.sun.webui.jsf.component.TableColumn;
import com.sun.webui.jsf.component.TableRowGroup;
import jakarta.faces.component.UIParameter;

/**
 * This class provides functionality for dynamic tables.
 */
public final class DynamicTables {

    /**
     * Checkbox ID.
     */
    public static final String CHECKBOX_ID = "select";

    /**
     * Hyperlink ID.
     */
    public static final String HYPERLINK_ID = "link";

    /**
     * Default constructor.
     */
    public DynamicTables() {
    }

    // Note: When using tags in a JSP page, UIComponentTag automatically creates
    // a unique id for the component. However, when dynamically creating
    // components, via a backing bean, the id has not been set. In this
    // scenario, allowing JSF to create unique Ids may cause problems with
    // Javascript and components may not be able to maintain state properly.
    // For example, if a component was assigned "_id6" as an id, that means
    // there were 5 other components that also have auto-generated ids. Let us
    // assume one of those components was a complex component that, as part of
    // its processing, adds an additional non-id'd child before redisplaying the
    // view. Now, the id of this component will be "_id7" instead of "_id6".
    // Assigning your own id ensures that conflicts do not occur.
    // Get Table component.
    //
    /**
     * Get a table.
     * @param id table id
     * @param title table title
     * @return Table
     */
    public Table getTable(final String id, final String title) {
        // Get table.
        Table table = new Table();
        // Show deselect multiple button.
        table.setDeselectMultipleButton(true);
        // Show select multiple button.
        table.setSelectMultipleButton(true);
        // Set title text.
        table.setTitle(title);
        return table;
    }

    /**
     * Get TableRowGroup component with header.
     * @param id The component id.
     * @param sourceData Value binding expression for model data.
     * @param selected Value binding expression for selected property.
     * @param header Value binding expression for row group header text.
     * @return TableRowGroup
     */
    public TableRowGroup getTableRowGroup(final String id,
            final String sourceData, final String selected,
            final String header) {

        // Get table row group.
        TableRowGroup rowGroup = new TableRowGroup();
        // Set id.
        rowGroup.setId(id);
        // Set source var.
        rowGroup.setSourceVar("name");
        // Set header text.
        rowGroup.setHeaderText(header);
        // Set row highlight.
        ExampleUtilities.setValueExpression(rowGroup, "selected", selected);
        // Set source data.
        ExampleUtilities.setValueExpression(rowGroup, "sourceData", sourceData);
        return rowGroup;
    }

    /**
     * Get TableColumn component.
     * @param id The component id.
     * @param sort Value binding expression for column sort.
     * @param align The field key for column alignment.
     * @param header The column header text.
     * @param selectId The component id used to select table rows.
     * @return TableColumn
     */
    public TableColumn getTableColumn(final String id, final String sort,
            final String align, final String header, final String selectId) {

        // Get table column.
        TableColumn col = new TableColumn();
        // Set id.
        col.setId(id);
        // Set id used to select table rows.
        col.setSelectId(selectId);
        // Set header text.
        col.setHeaderText(header);
        // Set align key.
        col.setAlignKey(align);
        // Set sort.
        ExampleUtilities.setValueExpression(col, "sort", sort);
        return col;
    }

    /**
     * Get Checkbox component used for select column.
     * @param id The component id.
     * @param selected Value binding expression for selected property.
     * @param selectedValue Value binding expression for selectedValue property.
     * @return Checkbox
     */
    public Checkbox getCheckbox(final String id, final String selected,
            final String selectedValue) {

        // Get checkbox.
        Checkbox cb = new Checkbox();
        // Set id here and set row highlighting below.
        cb.setId(id);
        cb.setOnClick("setTimeout('initAllRows()', 0)");
        // Set selected.
        ExampleUtilities.setValueExpression(cb, "selected", selected);
        // Set selected value.
        ExampleUtilities.setValueExpression(cb, "selectedValue",
                selectedValue);
        return cb;
    }

    /**
     * Get Hyperlink component.
     * @param id The component id.
     * @param text Value binding expression for text.
     * @param action Method binding expression for action.
     * @param parameter Value binding expression for parameter.
     * @return Hyperlink
     */
    public Hyperlink getHyperlink(final String id, final String text,
            final String action, final String parameter) {

        // Get hyperlink.
        Hyperlink hyperlink = new Hyperlink();
        // Set id.
        hyperlink.setId(id);
        // Set text.
        ExampleUtilities.setValueExpression(hyperlink, "text", text);
        // Set action.
        ExampleUtilities.setMethodExpression(hyperlink, "actionExpression",
                action);

        // Create paramerter.
        UIParameter param = new UIParameter();
        param.setId(id + "_param");
        param.setName("param");
        // Set parameter.
        ExampleUtilities.setValueExpression(param, "value", parameter);
        hyperlink.getChildren().add(param);
        return hyperlink;
    }

    /**
     * Get StaticText component.
     * @param text Value binding expression for text.
     * @return StaticText
     */
    public StaticText getText(final String text) {
        // Get static text.
        StaticText staticText = new StaticText();
        ExampleUtilities.setValueExpression(staticText,
                "text", text); // Set text.

        return staticText;
    }

    /**
     * Set TableRowGroup children.
     *
     * @param rowGroup The TableRowGroup component.
     * @param cbSort Value binding expression for cb sort.
     * @param cbSelected Value binding expression for cb selected property.
     * @param cbSelectedValue Value binding expression for cb selectedValue
     * property.
     * @param action The Method binding expression for hyperlink action.
     * @param showHeader Flag indicating to display column header text.
     */
    public void setTableRowGroupChildren(final TableRowGroup rowGroup,
            final String cbSort, final String cbSelected,
            final String cbSelectedValue, final String action,
            final boolean showHeader) {

        // UI guidelines recomend no headers for second row group.
        String header1;
        if (showHeader) {
            header1 = "Last Name";
        } else {
            header1 = null;
        }

        String header2;
        if (showHeader) {
            header2 = "First Name";
        } else {
            header2 = null;
        }

        // Get columns.
        TableColumn col1 = getTableColumn(
                "col0", cbSort, null, null, CHECKBOX_ID);
        TableColumn col2 = getTableColumn(
                "col1", "#{name.value.last}", "last", header1, null);
        TableColumn col3 = getTableColumn(
                "col2", "#{name.value.first}", "first", header2, null);

        // Get column components.
        Checkbox cb = getCheckbox(CHECKBOX_ID, cbSelected, cbSelectedValue);
        StaticText firstName = getText("#{name.value.first}");

        // If action was provided, add a hyperlink; otherwise, use static text.
        if (action != null) {
            Hyperlink lastName = getHyperlink(HYPERLINK_ID,
                    "#{name.value.last}", action,
                    "#{name.value.last}");
            col2.getChildren().add(lastName);
        } else {
            StaticText lastName = getText("#{name.value.last}");
            col2.getChildren().add(lastName);
        }

        // Add Children.
        col1.getChildren().add(cb);
        col3.getChildren().add(firstName);
        rowGroup.getChildren().add(col1);
        rowGroup.getChildren().add(col2);
        rowGroup.getChildren().add(col3);
    }
}
