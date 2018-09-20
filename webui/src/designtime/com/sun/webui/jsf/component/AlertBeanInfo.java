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

import com.sun.rave.faces.event.Action;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.component.util.DesignUtil;
import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.PropertyDescriptor;
import com.sun.rave.designtime.Constants;
import java.lang.reflect.Method;
import javax.faces.context.FacesContext;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;


/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.Alert} component.
 *
 * @author gjmurphy
 */
public class AlertBeanInfo extends AlertBeanInfoBase {
    
    public AlertBeanInfo() {
    }
    
    public BeanDescriptor getBeanDescriptor() {
        Theme theme =
                ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        String alertLabelStyle = theme.getStyleClass(ThemeStyles.ALERT_TEXT);
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
        beanDescriptor.setValue(
                Constants.BeanDescriptor.INLINE_EDITABLE_PROPERTIES,
                new String[] {
            "*summary://span[@class='" + alertLabelStyle + "']",//NOI18N
            "detail://span[@class='" + alertLabelStyle + "']" }); // NOI18N
        PropertyDescriptor[] descriptors = this.getPropertyDescriptors();
        for (int i = 0; i < descriptors.length; i++) {
            if (descriptors[i].getName().equals("linkAction")) //NOI18N
                descriptors[i].setHidden(true);
        }
        return beanDescriptor;
    }
    
    EventSetDescriptor[] eventSetDescriptors;
    
    public EventSetDescriptor[] getEventSetDescriptors() {
        if (eventSetDescriptors == null) {
            try {
                PropertyDescriptor actionDescriptor = null;
                PropertyDescriptor[] propertyDescriptors = this.getPropertyDescriptors();
                for (int i = 0; i < propertyDescriptors.length && actionDescriptor == null; i++) {
                    if (propertyDescriptors[i].getName().equals("linkActionExpression")) //NOI18N
                        actionDescriptor = propertyDescriptors[i];
                }
                EventSetDescriptor actionEventDescriptor =
                        new EventSetDescriptor("linkAction", Action.class,  //NOI18N
                        new Method[] {Action.class.getMethod("action", new Class[] {})},  //NOI18N
                        null, null);
                actionEventDescriptor.setDisplayName(actionDescriptor.getDisplayName());
                actionEventDescriptor.setShortDescription(actionDescriptor.getShortDescription());
                actionEventDescriptor.setValue(Constants.EventSetDescriptor.BINDING_PROPERTY,
                        actionDescriptor);
                actionEventDescriptor.setValue(Constants.EventDescriptor.DEFAULT_EVENT_BODY,
                        DesignMessageUtil.getMessage(this.getClass(), "actionHandler")); //NOI18N
                eventSetDescriptors = new EventSetDescriptor[] {actionEventDescriptor};
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return eventSetDescriptors;
    }
    
    
    
}
