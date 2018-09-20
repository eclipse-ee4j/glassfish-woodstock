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
 * NonDefaultPropertyTest.java
 * JUnit based test
 *
 * Created on 24 juin 2006, 20:18
 */

package org.example;

import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.rave.designtime.Constants;
import com.sun.rave.designtime.markup.AttributeDescriptor;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import junit.framework.*;

/**
 *
 * @author gjmurphy
 */
public class NonDefaultPropertyTest extends TestCase {
    
    public NonDefaultPropertyTest(String testName) {
        super(testName);
    }
    
    BeanInfo beanInfo;
    Map<String,PropertyDescriptor> propertyMap = new HashMap<String,PropertyDescriptor>();
    
    protected void setUp() throws Exception {
        beanInfo = Introspector.getBeanInfo(NonDefaultPropertyComponent.class);
        for (PropertyDescriptor p : beanInfo.getPropertyDescriptors()) {
            propertyMap.put(p.getName(), p);
        }
    }
    
    public void testNonAttributeProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("nonAttribute");
        this.assertNull(p.getValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR));
    }
    
    public void testNamedAttributeProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("namedAttribute");
        this.assertEquals(((AttributeDescriptor) p.getValue(
                (Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR))).getName(), "specialNamedAttribute");
    }
    
    public void testRequiredAttributeProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("requiredAttribute");
        this.assertTrue(((AttributeDescriptor) p.getValue(
                (Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR))).isRequired());
    }
    
    public void testReadOnlyProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("readOnlyProperty");
        this.assertNull(p.getWriteMethod());
    }
    
    public void testWriteOnlyProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("writeOnlyProperty");
        this.assertNull(p.getReadMethod());
    }
    
    public void testCategorizedProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("categorizedProperty");
        CategoryDescriptor c = (CategoryDescriptor) p.getValue(Constants.PropertyDescriptor.CATEGORY);
        this.assertNotNull(c);
        this.assertEquals("category", c.getName());
    }
    
    public void testHiddenProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("hiddenProperty");
        this.assertTrue(p.isHidden());
    }
    
    public void testDefaultProperty() throws Exception {
        int index = this.beanInfo.getDefaultPropertyIndex();
        this.assertTrue("No default property index", index >= 0);
        this.assertEquals("Wrong default property name", "defaultProperty", this.beanInfo.getPropertyDescriptors()[index].getName());
    }
    
    public void testNamedProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("namedProperty");
        this.assertEquals("Wrong read method name", "getNamedProperty", p.getReadMethod().getName());
        this.assertEquals("Wrong write method name", "setNamedProperty", p.getWriteMethod().getName());
    }
    
    public void testDifferentReadNamedProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("readNamedProperty");
        this.assertEquals("Wrong read method name", "getDifferentReadNamedProperty", p.getReadMethod().getName());
        this.assertEquals("Wrong write method name", "setReadNamedProperty", p.getWriteMethod().getName());
    }
    
    public void testDifferentWriteNamedProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("writeNamedProperty");
        this.assertEquals("Wrong read method name", "getWriteNamedProperty", p.getReadMethod().getName());
        this.assertEquals("Wrong write method name", "setDifferentWriteNamedProperty", p.getWriteMethod().getName());
    }
    
    public void testDifferentBothNamedProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("bothNamedProperty");
        this.assertEquals("Wrong read method name", "getDifferentBothNamedProperty", p.getReadMethod().getName());
        this.assertEquals("Wrong write method name", "setDifferentBothNamedProperty", p.getWriteMethod().getName());
    }
    
    protected void tearDown() throws Exception {
    }
    
    private PropertyDescriptor getPropertyDescriptor(String name) throws Exception {
        PropertyDescriptor p = propertyMap.get(name);
        this.assertNotNull("No such property: " + name, p);
        return p;
    }
    
}
