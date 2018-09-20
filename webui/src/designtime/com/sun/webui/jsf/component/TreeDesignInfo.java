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

import com.sun.data.provider.DataProvider;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.design.AbstractDesignInfo;

/**
 * Design time behavior for a <code>Tree</code> component.
 * 
 * @author gjmurphy
 * @author Edwin Goei
 */
public class TreeDesignInfo extends AbstractDesignInfo {

    public TreeDesignInfo() {
        super(Tree.class);
    }

    public Result beanCreatedSetup(DesignBean bean) {
        DesignProperty prop;

        DesignProperty textProperty = bean.getProperty("text"); //NOI18N
        textProperty.setValue(bean.getBeanInfo().getBeanDescriptor().getDisplayName());

        DesignContext context = bean.getDesignContext();
        if (context.canCreateBean(TreeNode.class.getName(), bean, null)) {
            DesignBean treeNodeBean = context.createBean(TreeNode.class.getName(), bean, null);
            // Use same DT property state as if user added TreeNode
            treeNodeBean.getDesignInfo().beanCreatedSetup(treeNodeBean);
        }

        return Result.SUCCESS;
    }

    public boolean acceptChild(DesignBean parentBean, DesignBean childBean,
            Class childClass) {
        if (TreeNode.class.equals(childClass))
            return true;
        return false;
    }

    public boolean acceptLink(DesignBean targetBean, DesignBean sourceBean, Class sourceClass) {
        if (DataProvider.class.isAssignableFrom(sourceClass)) {
            if (this.getDefaultBindingProperty(targetBean) != null)
                return true;
        }
        return false;
    }
    
}
