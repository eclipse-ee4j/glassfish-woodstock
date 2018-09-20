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

package com.sun.webui.jsf.example.table.util;

import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.ObjectArrayDataProvider;
import com.sun.data.provider.impl.ObjectListDataProvider;
import com.sun.webui.jsf.component.Checkbox;
import com.sun.webui.jsf.component.TableRowGroup;

import java.util.List;

// This class contains data provider and util classes. Note that not all util
// classes are used for each example.
public class Group {
    private TableRowGroup tableRowGroup = null; // TableRowGroup component.
    private TableDataProvider provider = null; // Data provider.
    private Checkbox checkbox = null; // Checkbox component.
    private Preferences prefs = null; // Preferences util.
    private Messages messages = null; // Messages util.
    private Actions actions = null; // Actions util.
    private Filter filter = null; // Filter util.
    private Select select = null; // Select util.

    // Default constructor.
    public Group() {
        actions = new Actions(this);
        filter = new Filter(this);
        select = new Select(this);
        prefs = new Preferences();
        messages = new Messages();
    }

    // Construct an instance using given Object array.
    public Group(Object[] array) {
        this();
        provider = new ObjectArrayDataProvider(array);
    }

    // Construct an instance using given List.
    public Group(List list) {
        this();
        provider = new ObjectListDataProvider(list);
    }

    // Construct an instance using given List and a
    // flag indicating that selected objects should
    // not be cleared after the render response phase.        
    public Group(List list, boolean keepSelected) {
        actions = new Actions(this);        
        select = new Select(this, keepSelected);
        messages = new Messages();      
        provider = new ObjectListDataProvider(list);
    }
    
    // Get data provider.
    public TableDataProvider getNames() {
        return provider;
    }

    // Get Actions util.
    public Actions getActions() {
        return actions;
    }

    // Get Filter util.
    public Filter getFilter() {
        return filter;
    }

    // Get Messages util.
    public Messages getMessages() {
        return messages;
    }

    // Get Preferences util.
    public Preferences getPreferences() {
        return prefs;
    }

    // Get Select util.
    public Select getSelect() {
        return select;
    }

    // Get tableRowGroup component.
    public TableRowGroup getTableRowGroup() {
        return tableRowGroup;
    }

    // Set tableRowGroup component.
    public void setTableRowGroup(TableRowGroup tableRowGroup) {
        this.tableRowGroup = tableRowGroup;
    }

    // Get checkbox component.
    public Checkbox getCheckbox() {
        return checkbox;
    }

    // Set checkbox component.
    public void setCheckbox(Checkbox checkbox) {
        this.checkbox = checkbox;
    }
}
