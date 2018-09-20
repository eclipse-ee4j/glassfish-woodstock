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
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

/**
 * BeanInfo for {@link javax.faces.component.UICommand}.
 *
 * @author gjmurphy
 */
public class UIInputBeanInfo extends UIOutputBeanInfo {
    
    public UIInputBeanInfo() {
        super(UIInput.class);
    }
    
    private PropertyDescriptor[] propertyDescriptors;
    
    public PropertyDescriptor[] getPropertyDescriptors() {
        
        if (propertyDescriptors == null) {
            try {
                List<PropertyDescriptor> propertyDescriptorList = new ArrayList<PropertyDescriptor>();
                propertyDescriptorList.addAll(Arrays.asList(super.getPropertyDescriptors()));
                AttributeDescriptor attrib = null;
                
                PropertyDescriptor prop_validator = new PropertyDescriptor("validator",UIInput.class,"getValidator","setValidator");
                prop_validator.setDisplayName(resourceBundle.getString("UIInput_validator_DisplayName"));
                prop_validator.setShortDescription(resourceBundle.getString("UIInput_validator_Description"));
                prop_validator.setPropertyEditorClass(loadClass(PropertyEditorConstants.VALIDATOR_EDITOR));
                attrib = new AttributeDescriptor("validator",false,null,true);
                prop_validator.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR,attrib);
                propertyDescriptorList.add(prop_validator);
                
                PropertyDescriptor prop_valueChangeListener = new PropertyDescriptor("valueChangeListener",UIInput.class,"getValueChangeListener","setValueChangeListener");
                prop_valueChangeListener.setDisplayName(resourceBundle.getString("UIInput_valueChangeListener_DisplayName"));
                prop_valueChangeListener.setShortDescription(resourceBundle.getString("UIInput_valueChangeListener_Description"));
                prop_valueChangeListener.setPropertyEditorClass(loadClass(PropertyEditorConstants.VALUEBINDING_EDITOR));
                attrib = new AttributeDescriptor("valueChangeListener",false,null,true);
                prop_valueChangeListener.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR,attrib);
                propertyDescriptorList.add(prop_valueChangeListener);
                
                PropertyDescriptor prop_submittedValue = new PropertyDescriptor("submittedValue",UIInput.class,"getSubmittedValue","setSubmittedValue");
                prop_submittedValue.setHidden(true);
                propertyDescriptorList.add(prop_submittedValue);
                
                PropertyDescriptor prop_localValue = new PropertyDescriptor("localValue",UIInput.class,"getlocalValue",null);
                prop_localValue.setHidden(true);
                propertyDescriptorList.add(prop_localValue);
                
                PropertyDescriptor prop_localValueSet = new PropertyDescriptor("localValueSet",UIInput.class,"isLocalValueSet","setLocalValueSet");
                prop_localValueSet.setHidden(true);
                propertyDescriptorList.add(prop_localValueSet);
                
                propertyDescriptors = (PropertyDescriptor[]) propertyDescriptorList.toArray(
                        new PropertyDescriptor[propertyDescriptorList.size()]);
                
            } catch (IntrospectionException e) {
                e.printStackTrace();
                return null;
            }
        }
        
        return propertyDescriptors;
        
    }
    
    EventSetDescriptor[] eventSetDescriptors;
    
    public EventSetDescriptor[] getEventSetDescriptors() {
        if (eventSetDescriptors == null) {
            try {
                EventSetDescriptor valueChangeEventDescriptor =
                        new EventSetDescriptor("valueChangeListener", ValueChangeListener.class,
                        new Method[] {ValueChangeListener.class.getMethod("processValueChange", new Class[] {ValueChangeEvent.class})},
                        UIInput.class.getMethod("addValueChangeListener", new Class[] {ValueChangeListener.class}),
                        UIInput.class.getMethod("removeValueChangeListener", new Class[] {ValueChangeListener.class}),
                        UIInput.class.getMethod("getValueChangeListeners", new Class[] {}));
                eventSetDescriptors = new EventSetDescriptor[] {valueChangeEventDescriptor};
            } catch (IntrospectionException e) {
                e.printStackTrace();
                return null;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        }
        return eventSetDescriptors;
    }
}
