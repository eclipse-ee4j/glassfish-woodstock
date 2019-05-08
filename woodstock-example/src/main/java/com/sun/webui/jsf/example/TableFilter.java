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

import com.sun.data.provider.FilterCriteria;
import com.sun.data.provider.impl.CompareFilterCriteria;
import com.sun.webui.jsf.component.Table;
import com.sun.webui.jsf.model.Option;

import com.sun.webui.jsf.example.util.MessageUtil;

/**
 * This class provides functionality for table filters.
 *
 * This utility class sets filters directly on the TableRowGroup component using
 * FilterCriteria; however, there is also a FilteredTableDataProvider class that
 * can used for filtering outside of the table. The table will pick up what ever
 * filter has been applied automatically, for example:
 *
 * The table component itself has no idea that there is any filtering going on,
 * but the filtering functionality has been encapsulated in the data provider.
 * The developer can then use different FilterCriteria types to apply filters,
 * for example:
 */
public final class TableFilter {

    /**
     * Custom filter.
     */
    private String customFilter = null;

    /**
     * Basic filter menu option.
     */
    private String basicFilter = null;

    /**
     * Filter text.
     */
    private String filterText = null;

    /**
     * Group utility.
     */
    private TableGroup group = null;

    /**
     * Filter menu items.
     */
    private static final Option[] FILTER_OPTIONS = {
        new Option("FILTER0", MessageUtil.getMessage("table_filterAllItems")),
        new Option("FILTER1", MessageUtil.getMessage("table_filter1")),
        new Option("FILTER2", MessageUtil.getMessage("table_filter2"))
    };

    /**
     * Default constructor.
     * @param newGroup group utility
     */
    public TableFilter(final TableGroup newGroup) {
        this.group = newGroup;
    }

    // UI guidelines state that a "Custom Filter" option should be added to the
    // filter menu, used to open the table filter panel. Thus, if the
    // CUSTOM_FILTER option is selected, Javascript invoked via the onChange
    // event will open the table filter panel.
    //
    // UI guidelines also state that a "Custom Filter Applied" option should be
    // added to the filter menu, indicating that a custom filter has been
    // applied. In this scenario, set the selected property of the filter menu
    // as CUSTOM_FILTER_APPLIED. This selection should persist until another
    // menu option has been selected.
    //
    // Further, UI guidelines state that the table title should indicate that a
    // custom filter has been applied. To add this text to the table title, set
    // the filter property.
    // Basic filter event.

    /**
     * Apply the basic filter.
     */
    public void applyBasicFilter() {
        if (basicFilter.equals("FILTER1")) {
            filterText = MessageUtil.getMessage("table_filter1");
        } else if (basicFilter.equals("FILTER2")) {
            filterText = MessageUtil.getMessage("table_filter2");
        } else {
            filterText = null;
        }

        // Clear all filters since we don't have an example here.
        //
        // Note: TableRowGroup ensures pagination is reset per UI guidelines.
        group.getTableRowGroup().setFilterCriteria(null);
    }

    /**
     * Custom filter event.
     */
    public void applyCustomFilter() {
        // Set filter menu option.
        basicFilter = Table.CUSTOM_FILTER_APPLIED;
        filterText = MessageUtil.getMessage("table_filterCustom");

        // Filter rows that do not match custom filter.
        CompareFilterCriteria criteria = new CompareFilterCriteria(
                group.getNames().getFieldKey("last"), customFilter);

        // Note: TableRowGroup ensures pagination is reset per UI guidelines.
        group.getTableRowGroup().setFilterCriteria(
                new FilterCriteria[]{criteria});
    }

    /**
     * Get basic filter.
     * @return String
     */
    public String getBasicFilter() {
        // Note: the selected value must be set to restore the default selected
        // value when the embedded filter panel is closed. Further, the selected
        // value should never be set as "Custom Filter...".
        if (basicFilter != null && !basicFilter.equals(Table.CUSTOM_FILTER)) {
            return basicFilter;
        }
        return "FILTER0";
    }

    /**
     * Set basic filter.
     * @param value new basic filter
     */
    public void setBasicFilter(final String value) {
        basicFilter = value;
    }

    /**
     * Get custom filter.
     * @return String
     */
    public String getCustomFilter() {
        return customFilter;
    }

    /**
     * Set custom filter.
     * @param value custom filter
     */
    public void setCustomFilter(final String value) {
        customFilter = value;
    }

    /**
     * Get filter menu options.
     * @return Option[]
     */
    public Option[] getFilterOptions() {
        // Get filter options based on the selected filter menu option.
        return Table.getFilterOptions(FILTER_OPTIONS,
                Table.CUSTOM_FILTER_APPLIED.equals(basicFilter));
    }

    /**
     * Get filter text.
     * @return String
     */
    public String getFilterText() {
        return filterText;
    }
}
