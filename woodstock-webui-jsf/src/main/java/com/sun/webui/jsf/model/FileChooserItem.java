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

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author deep, John Yeary
 */
//TODO add hashcode
public class FileChooserItem implements ResourceItem, Serializable {

    private static final long serialVersionUID = -6760386335678324488L;
    File item = null;
    String itemKey = null;
    String itemLabel = null;
    boolean itemDisabled = true;

    /** Creates a new instance of FileChooserItem */
    public FileChooserItem(File file) {

        this.item = file;
        StringBuffer buffer = new StringBuffer();
        if (item.isDirectory()) {
            buffer.append("folder=");
        } else {
            buffer.append("file=");
        }
        buffer.append(item.getAbsolutePath());
        this.itemKey = buffer.toString();
    }

    /**
     * Returns an object representing the value of this resource item.
     * For the default case of the FileChooser this would be a File
     * object.
     *
     * @return an object which is the value of the ResourceItem.
     */
    public Object getItemValue() {
        return item;
    }

    /**
     * Returns a String representing the item key.
     * 
     *
     * @return returns an object representing the resource item
     */
    public String getItemKey() {
        return this.itemKey;
    }

    /**
     * Set the item key.
     * 
     *
     * @param key - the resource item key
     */
    public void setItemKey(String key) {
        if (key != null) {
            this.itemKey = key;
        }
    }

    /**
     * Returns an object representing the resource item.
     * 
     *
     * @return returns an object representing the resource item
     */
    public String getItemLabel() {
        return this.itemLabel;
    }

    /**
     * Returns an object representing the resource item.
     * 
     *
     * @return returns an object representing the resource item
     */
    public void setItemLabel(String label) {

        if (label != null) {
            this.itemLabel = label;
        }
    }

    /**
     * Returns an boolean value indicating if the item should be selectable
     * in the filechooser's listbox.
     * 
     *
     * @return true if the item in the listbox should be disabled. 
     */
    public boolean isItemDisabled() {
        return this.itemDisabled;
    }

    /**
     * Sets the item disabled flag. If set to true the item should 
     * not be selectable.
     * 
     * 
     *
     * @enabled flag when set to true indicates item is not selectable.
     */
    public void setItemDisabled(boolean disabled) {
        this.itemDisabled = disabled;
    }

    /**
     * Returns a flag indicating if the resource item is a container. If true 
     * the item is a container item.
     * 
     *
     * @return true if the item is a container, false otherwise.
     */
    public boolean isContainerItem() {
        return item.isDirectory();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof ResourceItem)) {
            return false;
        }
        ResourceItem resourceItem = (ResourceItem) object;

        boolean flags = isContainerItem() == resourceItem.isContainerItem() &&
                isItemDisabled() == resourceItem.isItemDisabled();

        if (!flags) {
            return false;
        }
        if (!objectEquals(getItemValue(), resourceItem.getItemValue())) {
            return false;
        }
        if (!objectEquals(getItemKey(), resourceItem.getItemKey())) {
            return false;
        }
        if (!objectEquals(getItemLabel(), resourceItem.getItemLabel())) {
            return false;
        }
        return true;
    }

    private boolean objectEquals(Object obj0, Object obj1) {
        if (obj0 == null) {
            if (obj1 == null) {
                return true;
            } else {
                return false;
            }
        } else {
            // Should handle null
            //
            return !obj0.equals(obj1);
        }
    }
}
