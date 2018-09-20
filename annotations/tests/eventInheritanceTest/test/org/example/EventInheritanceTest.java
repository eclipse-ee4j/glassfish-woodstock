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
 * EventInheritanceTest.java
 * JUnit based test
 *
 * Created on 23 juin 2006, 09:56
 */

package org.example;

import com.sun.rave.designtime.Constants;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test integrity of generated BeanInfo classes.
 *
 * @author gjmurphy
 */
public class EventInheritanceTest extends TestCase {
    
    public EventInheritanceTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(EventInheritanceTest.class);
        return suite;
    }
    
    public void testInheritanceComponent01() throws Exception {
        this.introspectBeanTest01(ComponentBean01.class);
    }
    
    public void testInheritanceComponent02() throws Exception {
        this.introspectBeanTest01(ComponentBean02.class);
    }
    
    public void testInheritanceComponent03() throws Exception {
        this.introspectBeanTest01(ComponentBean03.class);
    }
    
    public void testInheritanceComponent04() throws Exception {
        this.introspectBeanTest01(ComponentBean04.class);
    }
    
    public void testInheritanceComponent05() throws Exception {
        this.introspectBeanTest02(ComponentBean05.class);
    }
    
    public void testInheritanceComponent06() throws Exception {
        this.introspectBeanTest02(ComponentBean06.class);
    }
    
    
    public void introspectBeanTest01(Class c) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(c);
        EventSetDescriptor[] eventSetDescriptors = beanInfo.getEventSetDescriptors();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        this.assertEquals("Wrong number of event descriptors", 2, eventSetDescriptors.length);
        this.assertEquals("Wrong number of property descriptors", 1, propertyDescriptors.length);
        EventSetDescriptor introspectedAction01Event = findEventSetDescriptor(eventSetDescriptors, "introspectedAction01");
        this.assertNotNull("No such event: introspectedAction01", introspectedAction01Event);
        EventSetDescriptor introspectedAction02Event = findEventSetDescriptor(eventSetDescriptors, "introspectedAction02");
        this.assertNotNull("No such event: introspectedAction02", introspectedAction02Event);
        PropertyDescriptor introspectedAction02EventListener = propertyDescriptors[0];
        this.assertTrue(introspectedAction02EventListener.equals(
                introspectedAction02Event.getValue(Constants.EventSetDescriptor.BINDING_PROPERTY)));
    }
    
    
    public void introspectBeanTest02(Class c) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(c);
        EventSetDescriptor[] eventSetDescriptors = beanInfo.getEventSetDescriptors();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        this.assertEquals("Wrong number of event descriptors", 2, eventSetDescriptors.length);
        this.assertEquals("Wrong number of property descriptors", 2, propertyDescriptors.length);
        EventSetDescriptor introspectedAction01Event = findEventSetDescriptor(eventSetDescriptors, "introspectedAction01");
        this.assertNotNull("No such event: introspectedAction01", introspectedAction01Event);
        EventSetDescriptor introspectedAction02Event = findEventSetDescriptor(eventSetDescriptors, "introspectedAction02");
        this.assertNotNull("No such event: introspectedAction02", introspectedAction02Event);
        PropertyDescriptor introspectedAction01EventListener = 
                findPropertyDescriptor(propertyDescriptors, "introspectedListener1Expression");
        this.assertTrue(introspectedAction01EventListener.equals(
                introspectedAction01Event.getValue(Constants.EventSetDescriptor.BINDING_PROPERTY)));
        PropertyDescriptor introspectedAction02EventListener = 
                findPropertyDescriptor(propertyDescriptors, "introspectedListener2Expression");
        this.assertTrue(introspectedAction02EventListener.equals(
                introspectedAction02Event.getValue(Constants.EventSetDescriptor.BINDING_PROPERTY)));
    }
    
    EventSetDescriptor findEventSetDescriptor(EventSetDescriptor[] eventSetDescriptors, String name) {
        for (EventSetDescriptor d : eventSetDescriptors) {
            if (d.getName().equals(name))
                return d;
        }
        return null;
    }
    
    PropertyDescriptor findPropertyDescriptor(PropertyDescriptor[] propertyDescriptors, String name) {
        for (PropertyDescriptor d : propertyDescriptors) {
            if (d.getName().equals(name))
                return d;
        }
        return null;
    }
    
}
