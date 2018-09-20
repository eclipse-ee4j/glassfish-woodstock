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

package com.sun.webui.jsf.example.table;

import java.io.Serializable;

import com.sun.webui.jsf.example.table.util.Dynamic;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;

import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.component.Table;
import com.sun.webui.jsf.component.TableRowGroup;

/** Backing bean for dynamic group table examples.
 *
 * Note: To simplify the example, this bean is used only to create the table 
 * layout. The resulting table will use methods already defined in TableBean.
 *
 * Note that we must implement java.io.Serializable or
 * javax.faces.component.StateHolder in case client-side
 * state saving is used, or if server-side state saving is
 * used with a distributed system.
 */
public class DynamicGroupTableBean implements Serializable {
    private Dynamic dynamic = null; // Dynamic util.
    private Table table = null; // Table component.

    // Default constructor.
    public DynamicGroupTableBean() {
        dynamic = new Dynamic();
    }

    // Get Table component.
    public Table getTable() {
        if (table == null) {
            // Get table row group.
            TableRowGroup rowGroup1 = dynamic.getTableRowGroup("rowGroup1",
                "#{TableBean.groupB.names}",
                "#{TableBean.groupB.select.selectedState}",
                "Group Header");
            TableRowGroup rowGroup2 = dynamic.getTableRowGroup("rowGroup2",
                "#{TableBean.groupC.names}",
                "#{TableBean.groupC.select.selectedState}",
                "Group Header");

            // Set table row group properties.
            dynamic.setTableRowGroupChildren(rowGroup1, 
                "#{TableBean.groupB.select.selectedState}", 
                "#{TableBean.groupB.select.selected}",
                "#{TableBean.groupB.select.selectedValue}", null, true);
            dynamic.setTableRowGroupChildren(rowGroup2, 
                "#{TableBean.groupC.select.selectedState}", 
                "#{TableBean.groupC.select.selected}",
                "#{TableBean.groupC.select.selectedValue}", null, false);

            // Set select and row group toggle buttons.
            rowGroup1.setSelectMultipleToggleButton(true); 
            rowGroup2.setSelectMultipleToggleButton(true); 
            rowGroup1.setGroupToggleButton(true);
            rowGroup2.setGroupToggleButton(true);

            // Get table.
            table = dynamic.getTable("table1", null);
            table.getChildren().add(rowGroup1);
            table.getChildren().add(rowGroup2);

            // Add title facet.
            StaticText title = new StaticText();
            title.setText(MessageUtil.getMessage("table_dynamicGroupTitle"));
            table.getFacets().put(Table.TITLE_FACET, title);
        }
        return table;
    }

    // Set Table component. 
    //
    // @param table The Table component.
    public void setTable(Table table) {
        this.table = table;
    }
    
    // Action handler when navigating to the table index.
    public String showTableIndex() {
        reset();
        return TableBean.SHOW_TABLE_INDEX;
    }
    
    // Action handler when navigating to the main example index.
    public String showExampleIndex() {
        reset();
        return IndexBackingBean.INDEX_ACTION;
    }
    
    // Reset values so next visit starts fresh.
    private void reset() {
        table = null;
        dynamic = new Dynamic();
    }
}
