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
import com.sun.data.provider.RowKey;
import com.sun.webui.jsf.event.TableSelectPhaseListener;
import jakarta.faces.context.FacesContext;
import jakarta.el.ValueExpression;

/**
 * This class provides functionality for select tables.
 *
 * Note: UI guidelines recommend that rows should be unselected when no longer
 * in view. For example, when a user selects rows of the table and navigates to
 * another page. Or, when a user applies a filter or sort that may hide
 * previously selected rows from view. If a user invokes an action to delete the
 * currently selected rows, they may inadvertently remove rows not displayed on
 * the current page. Using TableSelectPhaseListener ensures that invalid row
 * selections are not rendered by clearing selected state after the render
 * response phase.
 */
public final class TableSelect {

    /**
     * Phase listener.
     */
    private TableSelectPhaseListener tspl = null;

    /**
     * Group utility.
     */
    private TableGroup group = null;

    /**
     * Default constructor.
     * @param newGroup group utility
     */
    public TableSelect(final TableGroup newGroup) {
        this.group = newGroup;
        tspl = new TableSelectPhaseListener();
    }

    /**
     * Construct an instance. The given flag indicates that selected objects
     * should not be cleared after the render response phase.
     *
     * @param newGroup group utility
     * @param keepSelected keep selected flag
     */
    public TableSelect(final TableGroup newGroup, final boolean keepSelected) {
        this.group = newGroup;
        tspl = new TableSelectPhaseListener(keepSelected);
    }

    /**
     * Clear selected state from phase listener (e.g., when deleting rows).
     */
    public void clear() {
        tspl.clear();
    }

    /**
     * Test flag indicating that selected objects should not be cleared.
     * @return {@code boolean}
     */
    public boolean isKeepSelected() {
        return tspl.isKeepSelected();
    }

    /**
     * Set flag indicating that selected objects should not be cleared.
     * @param keepSelected keep selected
     */
    public void keepSelected(final boolean keepSelected) {
        tspl.keepSelected(keepSelected);
    }

    /**
     * Get selected property.
     * @return Object
     */
    public Object getSelected() {
        return tspl.getSelected(getTableRow());
    }

    /**
     * Set selected property.
     * @param object object to set selected
     */
    public void setSelected(final Object object) {
        RowKey rowKey = getTableRow();
        if (rowKey != null) {
            tspl.setSelected(rowKey, object);
        }
    }

    /**
     * Get selected value property.
     * @return Object
     */
    public Object getSelectedValue() {
        RowKey rowKey = getTableRow();
        if (rowKey != null) {
            return rowKey.getRowId();
        }
        return null;
    }

    /**
     * Get the selected state -- Sort on checked state only.
     * @return {@code boolean}
     */
    public boolean getSelectedState() {
        // Typically, selected state is tested by comparing the selected and
        // selectedValue properties. In this example, however, the phase
        // listener value is not null when selected.
        return getSelectedState(getTableRow());
    }

    /**
     * Get the selected state.
     * @param rowKey row key
     * @return {@code boolean}
     */
    public boolean getSelectedState(final RowKey rowKey) {
        return tspl.isSelected(rowKey);
    }

    // Note: To obtain a RowKey for the current table row, the use the same
    // sourceVar property given to the TableRowGroup component. For example, if
    // sourceVar="name", use "#{name.tableRow}" as the expression string.
    /**
     * Get current table row.
     * @return RowKey
     */
    private RowKey getTableRow() {
        FacesContext context = FacesContext.getCurrentInstance();
        ValueExpression ve = ExampleUtilities.createValueExpression(context,
                "#{name.tableRow}", Object.class);
        return (RowKey) ve.getValue(context.getELContext());
    }
}
