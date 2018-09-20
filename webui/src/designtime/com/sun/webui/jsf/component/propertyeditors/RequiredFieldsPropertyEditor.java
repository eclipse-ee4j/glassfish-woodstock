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
 * RequiredFieldsPropertyEditor.java
 *
 * Created on October 20, 2005, 2:19 PM
 */

package com.sun.webui.jsf.component.propertyeditors;


import java.beans.PropertyEditorSupport;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.PropertyEditor2;


/**
 *A property editor for the <code>requiredFields</code> property of Property Sheet component
 * 
 * @author Gowri
 */
public class RequiredFieldsPropertyEditor extends PropertyEditorSupport implements PropertyEditor2 {
    
    static final int UNSET = 0;
    static final int TRUE = 1;
    static final int FALSE = 2;
    
    DesignProperty designProperty;
    String tags[] = new String[3];
    
    /**
     * Creates a new instance of RequiredFieldsPropertyEditor
     */
    public RequiredFieldsPropertyEditor() {
        tags[UNSET] = "";
        tags[TRUE] = Boolean.TRUE.toString();
        tags[FALSE] = Boolean.FALSE.toString();
    }
    
    /**
     * Set the design property for this editor.
     */
    public void setDesignProperty(DesignProperty designProperty) {
        this.designProperty = designProperty;
    }
    
    public void setAsText(String text) throws IllegalArgumentException {        
        if (text.equals(tags[UNSET])) {            
            this.setValue(null);
        } else if (text.equals(tags[FALSE])) {            
            this.setValue(tags[FALSE]);            
        } else {            
            this.setValue(tags[TRUE]);
        }
    }
    
    public String getAsText() {
        Object value = this.getValue();        
        if (value == null)
            return tags[UNSET];
        if (value.equals(tags[TRUE])) {
            return tags[TRUE];
        } else {
            return tags[FALSE];
        }
    }
    
    public String[] getTags() {
        return tags;
    }
}
