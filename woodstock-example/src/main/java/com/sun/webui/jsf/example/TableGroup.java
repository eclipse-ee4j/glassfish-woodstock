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
import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.ObjectArrayDataProvider;
import com.sun.data.provider.impl.ObjectListDataProvider;
import com.sun.webui.jsf.component.Checkbox;
import com.sun.webui.jsf.component.TableRowGroup;

import java.util.List;

/**
 * This class contains data provider and utility classes. Note that not all
 * utility classes are used for each example.
 */
public final class TableGroup {

    /**
     * TableRowGroup component.
     */
    private TableRowGroup tableRowGroup = null;

    /**
     * Data provider.
     */
    private TableDataProvider provider = null;

    /**
     * Checkbox component.
     */
    private Checkbox checkbox = null;

    /**
     * Preferences utility.
     */
    private TablePreferences prefs = null;

    /**
     * Messages utility.
     */
    private TableMessages messages = null;

    /**
     * Actions utility.
     */
    private TableActions actions = null;

    /**
     * Filter utility.
     */
    private TableFilter filter = null;

    /**
     * Select utility.
     */
    private TableSelect select = null;

    /**
     * Default constructor.
     */
    public TableGroup() {
        actions = new TableActions(this);
        filter = new TableFilter(this);
        select = new TableSelect(this);
        prefs = new TablePreferences();
        messages = new TableMessages();
    }

    /**
     * Construct an instance using given Object array.
     * @param array provider data
     */
    public TableGroup(final Object[] array) {
        this();
        provider = new ObjectArrayDataProvider(array);
    }

    /**
     * Construct an instance using given List.
     * @param list provider data
     */
    public TableGroup(final List list) {
        this();
        provider = new ObjectListDataProvider(list);
    }

    /**
     * Construct an instance using given List and a flag indicating that
     * selected objects should not be cleared after the render response phase.
     *
     * @param list provider data
     * @param keepSelected keep selected flag
     */
    public TableGroup(final List list, final boolean keepSelected) {
        actions = new TableActions(this);
        select = new TableSelect(this, keepSelected);
        messages = new TableMessages();
        provider = new ObjectListDataProvider(list);
    }

    /**
     * Get data provider.
     * @return TableDataProvider
     */
    public TableDataProvider getNames() {
        return provider;
    }

    /**
     * Get Actions utility.
     * @return TableActions
     */
    public TableActions getActions() {
        return actions;
    }

    /**
     * Get Filter utility.
     * @return TableFilter
     */
    public TableFilter getFilter() {
        return filter;
    }

    /**
     * Get Messages utility.
     * @return TableMessages
     */
    public TableMessages getMessages() {
        return messages;
    }

    /**
     * Get Preferences utility.
     * @return TablePreferences
     */
    public TablePreferences getPreferences() {
        return prefs;
    }

    /**
     * Get Select utility.
     * @return TableSelect
     */
    public TableSelect getSelect() {
        return select;
    }

    /**
     * Get tableRowGroup component.
     * @return TableRowGroup
     */
    public TableRowGroup getTableRowGroup() {
        return tableRowGroup;
    }

    /**
     * Set tableRowGroup component.
     * @param newTableRowGroup tableRowGroup
     */
    public void setTableRowGroup(final TableRowGroup newTableRowGroup) {
        this.tableRowGroup = newTableRowGroup;
    }

    /**
     * Get checkbox component.
     * @return Checkbox
     */
    public Checkbox getCheckbox() {
        return checkbox;
    }

    /**
     * Set checkbox component.
     * @param newCheckbox checkbox
     */
    public void setCheckbox(final Checkbox newCheckbox) {
        this.checkbox = newCheckbox;
    }
}
