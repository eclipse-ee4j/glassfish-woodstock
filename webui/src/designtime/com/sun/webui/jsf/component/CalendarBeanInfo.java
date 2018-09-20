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

import com.sun.rave.designtime.Constants;
import com.sun.webui.jsf.component.util.DesignUtil;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.Calendar} component.
 * 
 * @author gjmurphy
 */
public class CalendarBeanInfo extends CalendarBeanInfoBase {

    public CalendarBeanInfo() {
        DesignUtil.hideProperties(this,
                new String[] { "converter", "maxLength" });
        DesignUtil.updateInputEventSetDescriptors(this);
    }

    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
        // Do not allow component to be resized.
        beanDescriptor.setValue(Constants.BeanDescriptor.RESIZE_CONSTRAINTS,
                new Integer(Constants.ResizeConstraints.NONE));
        beanDescriptor.setValue(
                Constants.BeanDescriptor.INLINE_EDITABLE_PROPERTIES,
                new String[] { "label://label" }); // NOI18N
        return beanDescriptor;
    }
}
