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
 * ComponentIntrospectionTest.java
 * JUnit based test
 *
 * Created on 23 juin 2006, 09:56
 */

package org.example;

import com.sun.rave.designtime.Constants;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test integrity of generated BeanInfo classes.
 *
 * @author gjmurphy
 */
public class ComponentIntrospectionTest extends TestCase {
    
    Set<String> displayNameSet = new HashSet();
    
    public ComponentIntrospectionTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        this.displayNameSet.add("The First");
        this.displayNameSet.add("The Second");
        this.displayNameSet.add("The Third");
    }
    
    protected void tearDown() throws Exception {
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(ComponentIntrospectionTest.class);
        return suite;
    }
    
    public void testInheritance01() throws Exception {
        this.introspectBean(Component01.class);
    }
    
    public void testInheritance02() throws Exception {
        this.introspectBean(Component02.class);
    }
    
    public void testInheritance03() throws Exception {
        this.introspectBean(Component03.class);
    }
    
    public void testInheritance04() throws Exception {
        this.introspectBean(Component04.class);
    }
    
    public void testInheritance05() throws Exception {
        this.introspectBean(Component05.class);
    }
    
    public void testInheritance06() throws Exception {
        this.introspectBean(Component06.class);
    }
    
    public void testInheritance07() throws Exception {
        this.introspectBean(Component07.class);
        this.introspectHiddenProperties(Component07.class);
    }
    
    public void testInheritance08() throws Exception {
        this.introspectBean(Component08.class);
    }
    
    public void testInheritance09() throws Exception {
        this.introspectBean(Component09.class);
    }
    
    public void testInheritance10() throws Exception {
        this.introspectBean(Component10.class);
    }
    
    public void testInheritance11() throws Exception {
        this.introspectBean(Component11.class);
    }
    
    public void testInheritance12() throws Exception {
        this.introspectBean(Component12.class);
        this.introspectUnhiddenProperties(Component12.class);
    }
    
    public void testInheritance13() throws Exception {
        this.introspectBean(Component13.class);
    }
    
    public void testInheritance14() throws Exception {
        this.introspectBean(Component14.class);
        this.introspectUnhiddenProperties(Component14.class);
    }
    
    public void testInheritance16() throws Exception {
        this.introspectBean(Component16.class);
        this.introspectUnhiddenProperties(Component16.class);
    }
    
    public void introspectHiddenProperties(Class c) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(c);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor p : propertyDescriptors) {
            this.assertTrue("Property not hidden: " + p.getName(), p.isHidden());
            this.assertNull("Property has attribute: " + p.getName(),
                    p.getValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR));
        }
    }
    
    public void introspectUnhiddenProperties(Class c) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(c);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor p : propertyDescriptors) {
            this.assertFalse("Property is hidden: " + p.getName(), p.isHidden());
            this.assertTrue("Missing category descriptor", CategoryDescriptors.CATEGORY.equals(p.getValue(Constants.PropertyDescriptor.CATEGORY)));
            this.assertNotNull("Property missing attribute: " + p.getName(),
                    p.getValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR));
        }
    }
    
    public void introspectBean(Class c) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(c);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        this.assertEquals("Wrong number of property descriptors", 3, propertyDescriptors.length);
        for (PropertyDescriptor p : propertyDescriptors) {
            if (!p.isHidden())
                this.assertTrue("Incorrect property display name", this.displayNameSet.contains(p.getDisplayName()));
        }
    }
    
}
