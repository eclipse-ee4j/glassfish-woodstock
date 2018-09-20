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
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.swing.Action;

/**
 * BeanInfo for {@link javax.faces.component.UICommand}.
 *
 * @author gjmurphy
 */
public class UICommandBeanInfo extends UIComponentBaseBeanInfo {
    
    public UICommandBeanInfo() {
        super(UICommand.class);
    }
    
    private PropertyDescriptor[] propertyDescriptors;
    
    public PropertyDescriptor[] getPropertyDescriptors() {
        
        if (propertyDescriptors == null) {
            try {
                List<PropertyDescriptor> propertyDescriptorList = new ArrayList<PropertyDescriptor>();
                propertyDescriptorList.addAll(Arrays.asList(super.getPropertyDescriptors()));
                AttributeDescriptor attrib = null;
                
                PropertyDescriptor prop_actionExpr = new PropertyDescriptor("actionExpression",UICommand.class,"getActionExpression","setActionExpression");
                prop_actionExpr.setDisplayName(resourceBundle.getString("UICommand_actionExpression_DisplayName"));
                prop_actionExpr.setShortDescription(resourceBundle.getString("UICommand_actionExpression_Description"));
                prop_actionExpr.setPropertyEditorClass(loadClass(PropertyEditorConstants.METHODBINDING_EDITOR));
                attrib = new AttributeDescriptor("actionExpression",false,null,true);
                prop_actionExpr.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR,attrib);
                propertyDescriptorList.add(prop_actionExpr);
                
                PropertyDescriptor prop_immediate = new PropertyDescriptor("immediate",UICommand.class,"isImmediate","setImmediate");
                prop_immediate.setDisplayName(resourceBundle.getString("UICommand_immediate_DisplayName"));
                prop_immediate.setShortDescription(resourceBundle.getString("UICommand_immediate_Description"));
                attrib = new AttributeDescriptor("immediate",false,null,true);
                prop_immediate.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR,attrib);
                prop_immediate.setValue(Constants.PropertyDescriptor.CATEGORY, CategoryDescriptorsConstants.ADVANCED);
                propertyDescriptorList.add(prop_immediate);
                
                PropertyDescriptor prop_value = new PropertyDescriptor("value",UICommand.class,"getValue","setValue");
                prop_value.setDisplayName(resourceBundle.getString("UICommand_value_DisplayName"));
                prop_value.setShortDescription(resourceBundle.getString("UICommand_value_Description"));
                prop_value.setPropertyEditorClass(loadClass(PropertyEditorConstants.VALUEBINDING_EDITOR));
                attrib = new AttributeDescriptor("value",false,null,true);
                prop_value.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR,attrib);
                propertyDescriptorList.add(prop_value);
                
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
                EventSetDescriptor actionEventDescriptor =
                        new EventSetDescriptor("action", ActionListener.class,
                        new Method[] {ActionListener.class.getMethod("processAction", new Class[] {ActionEvent.class})},
                        UICommand.class.getMethod("addActionListener", new Class[] {ActionListener.class}),
                        UICommand.class.getMethod("removeActionListener", new Class[] {ActionListener.class}),
                        UICommand.class.getMethod("getActionListeners", new Class[] {}));
                eventSetDescriptors = new EventSetDescriptor[] {actionEventDescriptor};
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
