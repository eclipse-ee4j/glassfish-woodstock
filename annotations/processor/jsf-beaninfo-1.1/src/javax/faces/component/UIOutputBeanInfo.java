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

package javax.faces.component;

import com.sun.rave.designtime.Constants;
import com.sun.rave.designtime.markup.AttributeDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BeanInfo for {@link javax.faces.component.UICommand}.
 *
 * @author gjmurphy
 */
public class UIOutputBeanInfo extends UIComponentBaseBeanInfo {
    
    public UIOutputBeanInfo() {
        super(UIOutput.class);
    }
    
    public UIOutputBeanInfo(Class beanClass) {
        super(beanClass);
    }
    
    private PropertyDescriptor[] propertyDescriptors;
    
    public PropertyDescriptor[] getPropertyDescriptors() {
        
        if (propertyDescriptors == null) {
            try {
                List<PropertyDescriptor> propertyDescriptorList = new ArrayList<PropertyDescriptor>();
                propertyDescriptorList.addAll(Arrays.asList(super.getPropertyDescriptors()));
                AttributeDescriptor attrib = null;
                
                PropertyDescriptor prop_converter = new PropertyDescriptor("converter", UIOutput.class, "getConverter", "setConverter");
                prop_converter.setDisplayName(resourceBundle.getString("UIOutput_converter_DisplayName"));
                prop_converter.setShortDescription(resourceBundle.getString("UIOutput_converter_Description"));
                prop_converter.setPropertyEditorClass(loadClass(PropertyEditorConstants.CONVERTER_EDITOR));
                attrib = new AttributeDescriptor("converter", false, null, true);
                prop_converter.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR, attrib);
                propertyDescriptorList.add(prop_converter);
                
                PropertyDescriptor prop_value = new PropertyDescriptor("value", UIOutput.class, "getValue", "setValue");
                prop_value.setDisplayName(resourceBundle.getString("UICommand_value_DisplayName"));
                prop_value.setShortDescription(resourceBundle.getString("UICommand_value_Description"));
                prop_value.setPropertyEditorClass(loadClass(PropertyEditorConstants.VALUEBINDING_EDITOR));
                attrib = new AttributeDescriptor("value", false, null, true);
                prop_value.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR, attrib);
                propertyDescriptorList.add(prop_value);
                
                PropertyDescriptor prop_localValue = new PropertyDescriptor("localValue",UIOutput.class,"getLocalValue",null);
                prop_localValue.setHidden(true);
                propertyDescriptorList.add(prop_localValue);
                
                propertyDescriptors = (PropertyDescriptor[]) propertyDescriptorList.toArray(
                        new PropertyDescriptor[propertyDescriptorList.size()]);
                
            } catch (IntrospectionException e) {
                e.printStackTrace();
                return null;
            }
        }
        
        return propertyDescriptors;
        
    }
}
