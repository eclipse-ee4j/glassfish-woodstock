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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
        
import com.sun.webui.jsf.component.propertyeditors.OptionsPropertyEditor;
import com.sun.webui.jsf.component.propertyeditors.SelectedValuesPropertyEditor;

/**
 * BeanInfo for {@link com.sun.webui.jsf.model.OptionsList}.
 *
 * @author gjmurphy
 */
public abstract class OptionsListBeanInfo extends SimpleBeanInfo {
    private PropertyDescriptor[] propertyDescriptors;
    
    public PropertyDescriptor[] getPropertyDescriptors() {
        if (propertyDescriptors != null)
            return propertyDescriptors;
        try {
            PropertyDescriptor[] additionalPropertyDescriptors = 
                    getAdditionalPropertyDescriptors();
            PropertyDescriptor optionsDesc = new PropertyDescriptor( "options",
                    OptionsList.class, "getOptions", "setOptions");
            optionsDesc.setPropertyEditorClass(OptionsPropertyEditor.class);
            PropertyDescriptor selectedValueDesc = new PropertyDescriptor( "selectedValue",
                    OptionsList.class, "getSelectedValue", "setSelectedValue");
            selectedValueDesc.setPropertyEditorClass(SelectedValuesPropertyEditor.class);
            propertyDescriptors = new PropertyDescriptor[additionalPropertyDescriptors.length + 2];
            int i = 0;
            while (i < additionalPropertyDescriptors.length) {
                propertyDescriptors[i] = additionalPropertyDescriptors[i];
                i++;
            }
            propertyDescriptors[i++] = optionsDesc;
            propertyDescriptors[i++] = selectedValueDesc;
        } catch(IntrospectionException e) {
            propertyDescriptors = new PropertyDescriptor[0];
        }
        return propertyDescriptors;
    }
    
    abstract protected PropertyDescriptor[] getAdditionalPropertyDescriptors()
        throws IntrospectionException;
    
}
