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

import com.sun.rave.propertyeditors.PropertyEditorBase;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.scheduler.RepeatUnit;

/**
 * Property editor for the <code>RepeatUnitItems</code> property of Scheduler component.
 */
public class RepeatUnitEditor extends PropertyEditorBase {
    
    /** Creates a new instance of RepeatUnitEditor */
    public RepeatUnitEditor() {
    }
    
    /**
     * Sets the property value by parsing a given string. If the
     * passed-in string is of type value binding, the property
     * value will be set, otherwise, it will be ignored.
     *
     * @param text The string to be parsed.
     */
    public void setAsText(String text) throws IllegalArgumentException {        
        if (text == null) {
            this.setValue(null);
        } else {
            text = text.trim();
            if (text.length() <= 0) {
                this.setValue(null);
            } else if (text.startsWith("#{") && text.endsWith("}")) {
                this.setValue(text);
            }
        }
    }
    
    /**
     * Gets the property's default value as a string suitable for
     * presentation to user in the property sheets. This will be 
     * a comma-separated list of pre-defined repeat unit object
     * representations.
     *
     * @return  The poperty's default value as a string, or null if
     *          the property value can't be expressed as a string.
     */
    public String getAsText() {        
        Object value = (Object) this.getValue();
        if (value == null) {
            return null;
        } else if (value instanceof Option[]) {
            Option[] options = (Option[]) value;
            RepeatUnit repeatUnit;
            StringBuffer buffer = new StringBuffer();            
            for (int i = 0; i < options.length; i++) {
                repeatUnit = (RepeatUnit)options[i].getValue();
                if (repeatUnit != null) {
                    if (i > 0) {
                        buffer.append(", ");
                    }                    
                    buffer.append(repeatUnit.getRepresentation());
                }
            }
            return buffer.toString();
        } 
        return null;
    }     
}
