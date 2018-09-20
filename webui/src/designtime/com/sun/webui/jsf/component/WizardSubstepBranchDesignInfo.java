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
import com.sun.rave.designtime.Result;
import com.sun.webui.jsf.design.AbstractDesignInfo;

/**
 * DesignInfo for the WizardSubstepBranch component.
 * 
 */
public class WizardSubstepBranchDesignInfo extends AbstractDesignInfo {
    
    /** Creates a new instance of WizardSubstepBranchDesignInfo */
    public WizardSubstepBranchDesignInfo() {
        
        super(WizardSubstepBranch.class);
    }
    
    
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        
        WizardSubstepBranch wsb = (WizardSubstepBranch) bean.getInstance();
        //check for id, if its null then assign "wizardSubstepBranch1" to it.
        if (wsb.getId() == null)
        wsb.setId("wizardSubstepBranch1");
        return Result.SUCCESS;
    }
    
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        // can accept wizard or wizardBranchSteps as parent.
        if (parentBean.getInstance().getClass().equals(Wizard.class) ||
                parentBean.getInstance().getClass().equals(WizardBranchSteps.class))
            return true;
        return false;
    }
}
