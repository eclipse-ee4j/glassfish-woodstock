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

package com.sun.webui.jsf.component.propertyeditors;

import java.beans.PropertyEditorSupport;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.PropertyEditor2;

/**
 * A property editor for the <code>selected</code> property of radioButton and
 * checkbox components.
 *
 * @author gjmurphy
 */
public class RbCbSelectedPropertyEditor extends PropertyEditorSupport implements PropertyEditor2 {
    
    static final int UNSET = 0;
    static final int TRUE = 1;
    static final int FALSE = 2;
    
    DesignProperty designProperty;
    String tags[] = new String[3];
    
    /**
     * Creates a new instance of RbCbSelectedPropertyEditor 
     */
    public RbCbSelectedPropertyEditor() {
        tags[UNSET] = "";
        tags[TRUE] = Boolean.TRUE.toString();
        tags[FALSE] = Boolean.FALSE.toString();
    }
    
    /**
     * Set the design property for this editor.
     */
    public void setDesignProperty (DesignProperty designProperty) {
        this.designProperty = designProperty;
    }
    
    public void setAsText(String text) throws IllegalArgumentException {
        if (text.equals(tags[UNSET])) {
            this.setValue(null);
        } else if (text.equals(tags[FALSE])) {
            if (Boolean.TRUE.equals(getSelectedValue()))
                this.setValue(Boolean.FALSE);
            else
                this.setValue(null);
        } else {
            this.setValue(getSelectedValue());
        }
    }

    public String getAsText() {
        Object value = this.getValue();
        Object selectedValue = getSelectedValue();
        if (value == null)
            return tags[UNSET];
        if (value.equals(selectedValue)) {
            if (selectedValue instanceof String)
                return tags[TRUE] + " (" + getSelectedValue().toString() + ")";
            else
                return tags[TRUE];
        } else if (value instanceof String && Boolean.valueOf((String) value).equals(selectedValue)) {
            return tags[TRUE];
        }
        return tags[FALSE];
    }

    public String[] getTags() {
        return tags;
    }
    
    private Object getSelectedValue() {
        return designProperty.getDesignBean().getProperty("selectedValue").getValue(); //NOI18N
    }
    
}
