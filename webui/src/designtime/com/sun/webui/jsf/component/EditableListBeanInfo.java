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

package com.sun.webui.jsf.component;

import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.rave.designtime.Constants;
import java.beans.EventSetDescriptor;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.design.CategoryDescriptors;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.EditableList} component.
 */
public class EditableListBeanInfo extends EditableListBeanInfoBase {
    
    public EditableListBeanInfo() {
        DesignUtil.updateInputEventSetDescriptors(this);
    }
    
    public EventSetDescriptor[] getEventSetDescriptors() {
        EventSetDescriptor[] eventSetDescriptors = super.getEventSetDescriptors();
        //change validate to listValidate
        if (eventSetDescriptors != null) {
            for (EventSetDescriptor eventDescriptor : eventSetDescriptors) {
                if (eventDescriptor.getName().equals("validate")) {
                    eventDescriptor.setName("listValidate");
               
                    PropertyDescriptor lvePropertyDescriptor = DesignUtil.getPropertyDescriptor(this, "listValidatorExpression"); //NOI18N
                    eventDescriptor.setValue(Constants.EventSetDescriptor.BINDING_PROPERTY, lvePropertyDescriptor);
                }
            }
        }
        
        //create a fieldValidate event set descriptor
        EventSetDescriptor eventSetDescriptor = null;
        try {
            eventSetDescriptor = new EventSetDescriptor("fieldValidate", //NOI18N
            javax.faces.validator.Validator.class,
            new Method[] {
                javax.faces.validator.Validator.class.getMethod(
                    "validate",  //NOI18N
                    new Class[] {javax.faces.context.FacesContext.class, javax.faces.component.UIComponent.class, java.lang.Object.class, })
            },
            null,
            null);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        PropertyDescriptor eventPropertyDescriptor = DesignUtil.getPropertyDescriptor(this, "fieldValidatorExpression"); //NOI18N
        eventSetDescriptor.setValue(Constants.EventSetDescriptor.BINDING_PROPERTY, eventPropertyDescriptor);
        eventSetDescriptor.setValue(Constants.EventDescriptor.DEFAULT_EVENT_BODY,
            DesignMessageUtil.getMessage(this.getClass(), "validateHandler")); //NOI18N
        eventSetDescriptor.setValue(Constants.EventDescriptor.PARAMETER_NAMES,
            new String[] { "context", "component", "value" }); //NOI18N
        
        //copy eventSetDescriptors into a new revisedEventSetDescriptors
        EventSetDescriptor[] revisedEventSetDescriptors = new EventSetDescriptor[eventSetDescriptors.length + 1];
        for (int i = 0; i < eventSetDescriptors.length; i++) {
            revisedEventSetDescriptors[i] = eventSetDescriptors[i];
        }
        
        //add eventSetDescriptor as the last entry in revisedEventSetDescriptors
        revisedEventSetDescriptors[revisedEventSetDescriptors.length - 1] = eventSetDescriptor;
        return revisedEventSetDescriptors;
    }

    protected CategoryDescriptor[] categoryDescriptors;
    
    protected CategoryDescriptor[] getCategoryDescriptors() {
        // A hack to add the category descriptor for events. Since events are not
        // properties, they cannot be annotated with category information.
        if (categoryDescriptors == null) {
            CategoryDescriptor[] superCategoryDescriptors = super.getCategoryDescriptors();
            categoryDescriptors = new CategoryDescriptor[superCategoryDescriptors.length + 1];
            for (int i = 0, j = 0; i < superCategoryDescriptors.length; i++, j++) {
                categoryDescriptors[j] = superCategoryDescriptors[i];
                if (categoryDescriptors[j] == CategoryDescriptors.DATA)
                    categoryDescriptors[++j] = CategoryDescriptors.EVENTS;
            }
        }
        return categoryDescriptors;
    }
}
