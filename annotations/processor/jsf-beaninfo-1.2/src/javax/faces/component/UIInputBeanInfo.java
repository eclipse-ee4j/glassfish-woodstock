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
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.validator.Validator;

/**
 * BeanInfo for {@link javax.faces.component.UIInput}.
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
                
                PropertyDescriptor prop_validator = new PropertyDescriptor("validator", UIInput.class, "getValidator", "setValidator");
                prop_validator.setDisplayName(resourceBundle.getString("UIInput_validator_DisplayName"));
                prop_validator.setShortDescription(resourceBundle.getString("UIInput_validator_Description"));
                prop_validator.setPropertyEditorClass(loadClass(PropertyEditorConstants.VALIDATOR_EDITOR));
                attrib = new AttributeDescriptor("validator", false, null, true);
                prop_validator.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR, attrib);
                prop_validator.setValue(Constants.PropertyDescriptor.CATEGORY, CategoryDescriptorsConstants.DATA);
                propertyDescriptorList.add(prop_validator);
                
                PropertyDescriptor prop_immediate = new PropertyDescriptor("immediate",UIInput.class,"isImmediate","setImmediate");
                prop_immediate.setDisplayName(resourceBundle.getString("UIInput_immediate_DisplayName"));
                prop_immediate.setShortDescription(resourceBundle.getString("UIInput_immediate_Description"));
                attrib = new AttributeDescriptor("immediate",false,null,true);
                prop_immediate.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR,attrib);
                prop_immediate.setValue(Constants.PropertyDescriptor.CATEGORY, CategoryDescriptorsConstants.ADVANCED);
                propertyDescriptorList.add(prop_immediate);
                
                PropertyDescriptor prop_required = new PropertyDescriptor("required",UIInput.class,"isRequired","setRequired");
                prop_required.setDisplayName(resourceBundle.getString("UIInput_required_DisplayName"));
                prop_required.setShortDescription(resourceBundle.getString("UIInput_required_Description"));
                attrib = new AttributeDescriptor("required",false,null,true);
                prop_required.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR,attrib);
                prop_required.setValue(Constants.PropertyDescriptor.CATEGORY, CategoryDescriptorsConstants.DATA);
                propertyDescriptorList.add(prop_required);
                
                PropertyDescriptor prop_submittedValue = new PropertyDescriptor("submittedValue",UIInput.class,"getSubmittedValue","setSubmittedValue");
                prop_submittedValue.setHidden(true);
                propertyDescriptorList.add(prop_submittedValue);
                
                PropertyDescriptor prop_localValue = new PropertyDescriptor("localValue",UIInput.class,"getlocalValue",null);
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
    
    EventSetDescriptor[] eventSetDescriptors;
    
    public EventSetDescriptor[] getEventSetDescriptors() {
        if (eventSetDescriptors == null) {
            try {
                EventSetDescriptor valueChangeEventDescriptor =
                        new EventSetDescriptor("valueChange", ValueChangeListener.class,
                        new Method[] {ValueChangeListener.class.getMethod("processValueChange", new Class[] {ValueChangeEvent.class})},
                        UIInput.class.getMethod("addValueChangeListener", new Class[] {ValueChangeListener.class}),
                        UIInput.class.getMethod("removeValueChangeListener", new Class[] {ValueChangeListener.class}),
                        UIInput.class.getMethod("getValueChangeListeners", new Class[] {}));
                EventSetDescriptor validateEventDescriptor =
                        new EventSetDescriptor("validate", Validator.class,
                        new Method[] {Validator.class.getMethod("validate", new Class[] {FacesContext.class, UIComponent.class, Object.class})},
                        null, null);
                eventSetDescriptors = new EventSetDescriptor[] {valueChangeEventDescriptor,validateEventDescriptor};
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
