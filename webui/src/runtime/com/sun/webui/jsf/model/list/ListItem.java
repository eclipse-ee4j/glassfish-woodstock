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
 * ListItem.java
 *
 * Created on December 23, 2004, 3:01 PM
 */
package com.sun.webui.jsf.model.list;

/**
 *
 * @author avk
 */
public class ListItem {

    Object valueObject;
    String label;
    String value;
    String description = null;
    boolean selected = false;
    boolean disabled = false;
    boolean title = false;

    public ListItem(String label) {
        this.label = label;
        this.valueObject = label;
    }

    public ListItem(Object realValue, String label) {
        this.label = label;
        this.valueObject = realValue;
    }

    public ListItem(Object realValue, String label, boolean disabled) {
        this.label = label;
        this.valueObject = realValue;
        this.disabled = disabled;
    }

    public ListItem(Object realValue, String label, String description,
            boolean disabled) {
        this.label = label;
        this.valueObject = realValue;
        this.description = description;
        this.disabled = disabled;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Object getValueObject() {
        return valueObject;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setTitle(boolean title) {
        this.title = title;
    }

    public boolean isTitle() {
        return title;
    }
}
