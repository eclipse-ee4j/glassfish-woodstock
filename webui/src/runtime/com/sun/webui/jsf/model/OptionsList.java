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

package com.sun.webui.jsf.model;

import java.util.ArrayList;
import java.io.Serializable;

/** 
 * Represents a list of selectable options, which can be used to
 * initialize the <code>items</code> property of all selector-based components
 * (<code>Listbox</code>, <code>Dropdown</code>, <code>JumpDropdown</code>, 
 * <code>CheckboxGroup</code>, <code>RadioButtonGroup</code>), and 
 * <code>AddRemove</code>.
 *
 * @author gjmurphy, John Yeary
 */
public class OptionsList implements Serializable {

    private static final long serialVersionUID = 6695656179045426419L;
    private ArrayList options;
    private ArrayList selectedValues;
    private boolean isMultiple;

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean isMultiple) {
        this.isMultiple = isMultiple;
    }

    public OptionsList() {
        options = new ArrayList();
        selectedValues = new ArrayList();
        isMultiple = false;
    }

    public void setOptions(Option[] options) {
        this.options.clear();
        if (options == null) {
            return;
        }
        for (int i = 0; i < options.length; i++) {
            this.options.add(options[i]);
        }
    }

    public Option[] getOptions() {
        Option[] options = new Option[this.options.size()];
        this.options.toArray(options);
        return options;
    }

    /**
     * If this options list is in "multiple" mode, value specified may be
     * an array of objects or a singleton. Otherwise, the value is treated as
     * a singleton.
     */
    public void setSelectedValue(Object value) {
        selectedValues.clear();
        if (value == null) {
            return;
        }
        if (value instanceof Object[]) {
            Object[] values = (Object[]) value;
            for (int i = 0; i < values.length; i++) {
                selectedValues.add(values[i]);
            }
        } else {
            selectedValues.add(value);
        }
    }

    /**
     * If this options list is in "multiple" mode, returns an array of objects;
     * otherwise, returns a singleton.
     */
    public Object getSelectedValue() {
        if (isMultiple) {
            if (selectedValues.size() == 0) {
                return new Object[0];
            }
            return selectedValues.toArray();
        } else {
            if (selectedValues.size() == 0) {
                return null;
            }
            return selectedValues.get(0);
        }
    }
}
