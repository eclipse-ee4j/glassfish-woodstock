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

import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.design.AbstractDesignInfo;

/** DesignInfo class for components that extend the {@link 
 * com.sun.webui.jsf.component.Property} component.
 *
 * @author gjmurphy
 */
public class PropertySheetDesignInfo extends AbstractDesignInfo {
    
    public PropertySheetDesignInfo () {
        super(PropertySheet.class);
    }
    
    /**
     * On component creation, pre-populate with a property sheet section that
     * contains one property.
     */
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        DesignContext context = bean.getDesignContext();
        if (context.canCreateBean(PropertySheetSection.class.getName(), bean, null)) {
            DesignBean propertySectionBean = context.createBean(PropertySheetSection.class.getName(), bean, null);
            String suffix = DesignUtil.getNumericalSuffix(propertySectionBean.getInstanceName());
            propertySectionBean.getProperty("label").setValue(
                propertySectionBean.getBeanInfo().getBeanDescriptor().getDisplayName() + " " + suffix);
            DesignBean propertyBean = context.createBean(Property.class.getName(), propertySectionBean, null);
            suffix = DesignUtil.getNumericalSuffix(propertyBean.getInstanceName());
            propertyBean.getProperty("label").setValue(
                propertyBean.getBeanInfo().getBeanDescriptor().getDisplayName() + " " + suffix);
        }
        return Result.SUCCESS;
    }

    /**
     * A property sheet accepts only PropertySheetSection children.
     */
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if (childClass.equals(PropertySheetSection.class))
            return true;
        return false;
    }


 protected DesignProperty getDefaultBindingProperty(DesignBean targetBean) {
        return targetBean.getProperty("requiredFields"); //NOI18N
    }

    public void propertyChanged(DesignProperty property, Object oldValue) {
        // If the value of this component's "requiredFields" property was set to equal
        // the value of its "requiredFields" property, this indicates that the user
        // wanted the widget to be preselected at run-time. If this was the case,
        // and "requiredFields" has been changed, updated "requiredFields" accordingly.
        if (property.getPropertyDescriptor().getName().equals("requiredFields")) {
            DesignProperty requiredProperty = property.getDesignBean().getProperty("requiredFields");
            if (oldValue != null && oldValue.equals(requiredProperty.getValue()))
                requiredProperty.setValue(property.getValue());
        }
        super.propertyChanged(property, oldValue);
    }


    
}
