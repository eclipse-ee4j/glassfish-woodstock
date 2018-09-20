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
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * BeanInfo for {@link javax.faces.component.UIComponentBase}.
 *
 * @author gjmurphy
 */
public class UIComponentBaseBeanInfo extends SimpleBeanInfo {
        
    protected static ResourceBundle resourceBundle =
            ResourceBundle.getBundle("javax.faces.component.bundle", Locale.getDefault());
    
    private Class componentClass;
    private String unqualifiedClassName;
    
    public UIComponentBaseBeanInfo() {
        this.componentClass = UIComponentBase.class;
        String className = componentClass.getName();
        unqualifiedClassName = className.substring(className.lastIndexOf(".") + 1);
    }
    
    /**
     * Constructor for use by extending classes.
     */
    protected UIComponentBaseBeanInfo(Class componentClass) {
        this.componentClass = componentClass;
        String className = componentClass.getName();
        unqualifiedClassName = className.substring(className.lastIndexOf(".") + 1);
    }
    
    private String instanceName;
    
    protected String getInstanceName() {
        if (instanceName == null) {
            String str = this.componentClass.getName();
            StringBuffer buffer = new StringBuffer();
            buffer.append(str.substring(0, 1).toLowerCase());
            buffer.append(str.substring(1));
            instanceName = buffer.toString();
        }
        return instanceName;
    }
    
    BeanDescriptor beanDescriptor;
    
    public BeanDescriptor getBeanDescriptor() {
        if (beanDescriptor != null)
            return beanDescriptor;
        beanDescriptor = new BeanDescriptor(componentClass);
        String instanceName = getInstanceName();
        if (instanceName != null)
            beanDescriptor.setValue(com.sun.rave.designtime.Constants.BeanDescriptor.INSTANCE_NAME, instanceName);
        return beanDescriptor;
    }
    
    private PropertyDescriptor[] propertyDescriptors;
    
    public PropertyDescriptor[] getPropertyDescriptors() {
        
        if (propertyDescriptors != null)
            return propertyDescriptors;
        
        try {
            AttributeDescriptor attrib = null;
        
            PropertyDescriptor prop_id = new PropertyDescriptor("id", UIComponent.class, "getId", "setId");
            prop_id.setDisplayName(resourceBundle.getString("UIComponent_id_DisplayName"));
            prop_id.setShortDescription(resourceBundle.getString("UIComponent_id_Description"));
            prop_id.setHidden(true);
            attrib = new AttributeDescriptor("id", false, null, true);
            prop_id.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR, attrib);
            
            PropertyDescriptor prop_rendered = new PropertyDescriptor("rendered", UIComponent.class, "isRendered", "setRendered");
            prop_rendered.setDisplayName(resourceBundle.getString("UIComponent_rendered_DisplayName"));
            prop_rendered.setShortDescription(resourceBundle.getString("UIComponent_rendered_Description"));
            attrib = new AttributeDescriptor("rendered", false, null, true);
            prop_rendered.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR, attrib);
            prop_rendered.setValue(Constants.PropertyDescriptor.CATEGORY, CategoryDescriptorsConstants.ADVANCED);
            
            propertyDescriptors = new PropertyDescriptor[] {
                prop_id, 
                prop_rendered
            };
            return propertyDescriptors;
            
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    /**
     * Utility method that returns a class loaded by name via the class loader that
     * loaded this class.
     */
    protected static Class loadClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
}
