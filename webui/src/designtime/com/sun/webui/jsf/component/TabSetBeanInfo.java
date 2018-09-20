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

import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import javax.faces.event.ActionEvent;

import com.sun.rave.designtime.Constants;
import com.sun.webui.jsf.component.util.DesignUtil;
import java.beans.EventSetDescriptor;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.TabSet} component.
 *
 * @author gjmurphy
 */
public class TabSetBeanInfo extends TabSetBeanInfoBase {

    public TabSetBeanInfo() {
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
        beanDescriptor.setValue(Constants.BeanDescriptor.PREFERRED_CHILD_TYPES,
                new String[] {Tab.class.getName()});
        DesignUtil.updateInputEventSetDescriptors(this);
        DesignUtil.hideProperties(this, new String[]{"actionListeners"});
    }
    
    // Hide the events
     public EventSetDescriptor[] getEventSetDescriptors() {
         return new EventSetDescriptor[0];
     }

}
