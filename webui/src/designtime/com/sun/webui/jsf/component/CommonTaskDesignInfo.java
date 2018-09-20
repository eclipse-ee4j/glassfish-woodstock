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
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.component.CommonTasksSection;
import com.sun.webui.jsf.component.CommonTasksGroup;
import com.sun.webui.jsf.design.AbstractDesignInfo;

/**
 *DesignInfo class for components that extend the {@link
 * com.sun.webui.jsf.component.CommonTask} component.
 */
public class CommonTaskDesignInfo extends AbstractDesignInfo {
    
    /** Creates a new instance of CommonTaskDesignInfo */
    public CommonTaskDesignInfo() {
              super(CommonTask.class);
    }
    
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {        
        return true;
    }
    
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        Class parentClass = parentBean.getInstance().getClass();
        if(CommonTasksSection.class.equals(parentClass) || CommonTasksGroup.class.equals(parentClass)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     *
     * @param bean <code>DesignBean</code> for the newly created instance
     */
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        
         FacesDesignContext context = (FacesDesignContext) bean.getDesignContext();
         DesignProperty text = bean.getProperty("text"); // NOI18N
         text.setValue(DesignMessageUtil.getMessage(CommonTaskDesignInfo.class, "CommonTask.text")); // NOI18N
         
          return Result.SUCCESS;
    }

    
}
