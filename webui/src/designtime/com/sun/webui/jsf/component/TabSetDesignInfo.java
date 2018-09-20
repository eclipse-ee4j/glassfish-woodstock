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

import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.design.AbstractDesignInfo;

/**
 * DesignInfo for the <code>TabSet</code> component. The following behavior is
 * implemented:
 * <ul>
 * <li>Upon component creation, pre-populate with one tab component.</li>
 * </ul>
 *
 * @author gjmurphy
 */
public class TabSetDesignInfo extends AbstractDesignInfo {
    
    public TabSetDesignInfo() {
        super(TabSet.class);
    }

    public Result beanCreatedSetup(DesignBean bean) {
        DesignContext context = bean.getDesignContext();
        if (context.canCreateBean(Tab.class.getName(), bean, null)) {
            DesignBean tabBean = context.createBean(Tab.class.getName(), bean, null);
            tabBean.getDesignInfo().beanCreatedSetup(tabBean);
        }
        DesignProperty selectedChildSavedProperty = bean.getProperty("lastSelectedChildSaved");
        if (selectedChildSavedProperty != null)
            selectedChildSavedProperty.setValue(true);
        return Result.SUCCESS;
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        return childClass.equals(Tab.class);
    }
    
}
