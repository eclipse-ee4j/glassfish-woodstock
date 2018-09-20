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
public class PropertySheetSectionDesignInfo extends AbstractDesignInfo {
    
    public PropertySheetSectionDesignInfo() {
        super(PropertySheetSection.class);
    }
    
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        DesignContext context = bean.getDesignContext();
        DesignProperty textProperty = bean.getProperty("label"); //NOI18N
        String suffix = DesignUtil.getNumericalSuffix(bean.getInstanceName());
        textProperty.setValue(
                bean.getBeanInfo().getBeanDescriptor().getDisplayName() + " " + suffix);
        
        DesignBean propertyBean = context.createBean(Property.class.getName(), bean, null);
        suffix = DesignUtil.getNumericalSuffix(propertyBean.getInstanceName());
        propertyBean.getProperty("label").setValue(
                propertyBean.getBeanInfo().getBeanDescriptor().getDisplayName() + " " + suffix);
        return Result.SUCCESS;
    }
    
    /**
     * A property sheet section must be contained by a property sheet.
     */
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if (parentBean.getInstance().getClass().equals(PropertySheet.class))
            return true;
        return false;
    }
    
    /**
     * A property sheet section may contain only properties.
     */
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if (childClass.equals(Property.class))
            return true;
        return false;
    }
    
}
