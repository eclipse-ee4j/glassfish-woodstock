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
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.UIPanel;
import com.sun.rave.designtime.DesignContext;

/**
 * DesignInfo for the WizardStep component.
 * 
 */
public class WizardStepDesignInfo extends AbstractDesignInfo {
    
    /** Creates a new instance of WizardStepDesignInfo */
    public WizardStepDesignInfo() {
        
        super(WizardStep.class);
    }
    
    
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        
        DesignContext context = bean.getDesignContext();
        // Added one layout panel so that added component can be aligned inside the wizardStep.    
        if (context.canCreateBean(PanelLayout.class.getName(), bean, null)) {
            DesignBean panelBean = context.createBean(PanelLayout.class.getName(), bean, null);
            panelBean.getDesignInfo().beanCreatedSetup(panelBean);
            panelBean.getProperty("panelLayout").setValue(PanelLayout.GRID_LAYOUT);
        }
        WizardStep ws = (WizardStep) bean.getInstance();
        //check for id, if its null then assign "wizardStep1" to it. 
        if (ws.getId() == null)
            ws.setId("wizardStep1");

            return Result.SUCCESS;
        }
    
}
