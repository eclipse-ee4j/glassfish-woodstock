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


import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import javax.faces.component.html.HtmlPanelGrid;
import com.sun.rave.designtime.markup.*;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import com.sun.webui.jsf.component.CommonTask;

/**
 *DesignInfo class for components that extend the {@link
 * com.sun.webui.jsf.component.CommonTasksGroup} component.
 */

public class CommonTasksGroupDesignInfo extends AbstractDesignInfo {
    
    /** Creates a new instance of CommonTasksGroupDesignInfo */
    public CommonTasksGroupDesignInfo() {
        super (CommonTasksGroup.class);
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {        
        return true;
    }
    
        public Result beanCreatedSetup(DesignBean bean) {
        DesignContext context = bean.getDesignContext();
        if (context.canCreateBean(CommonTask.class.getName(), bean, null)) {
            DesignBean commonTaskBean = context.createBean(CommonTask.class.getName(), bean, null);
            commonTaskBean.getDesignInfo().beanCreatedSetup(commonTaskBean);
        }
        return Result.SUCCESS;
    }
        
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        Class parentClass = parentBean.getInstance().getClass();
        if(CommonTasksGroup.class.equals(parentClass)) {
            return false;
        } else {
            return true;
        }
    }
}
