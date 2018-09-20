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
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.DisplayAction;
import com.sun.rave.designtime.Result;
import com.sun.webui.jsf.component.customizers.ImageCustomizerAction;
import com.sun.webui.jsf.design.AbstractDesignInfo;

/**
 * Design time behavior for an <code>Image</code> component.
 *
 * @author gjmurphy
 */
public class ImageComponentDesignInfo extends AbstractDesignInfo {
    
    public ImageComponentDesignInfo() {
        super(ImageComponent.class);
    }

    public Result beanCreatedSetup(DesignBean bean) {
        ImageComponent image = (ImageComponent)bean.getInstance();
        return Result.SUCCESS;
    }

    public DisplayAction[] getContextItems(DesignBean bean) {
        DisplayAction[] superActions = super.getContextItems(bean);
        if (superActions == null)
            return new DisplayAction[] {new ImageCustomizerAction(bean)};
        DisplayAction[] actions = new DisplayAction[superActions.length + 1];
        int i = 0;
        while (i < superActions.length) {
            actions[i] = superActions[i];
            i++;
        }
        actions[i] = new ImageCustomizerAction(bean);
        return actions;
    }

    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        return false;
    }

    protected DesignProperty getDefaultBindingProperty(DesignBean targetBean) {
        return targetBean.getProperty("url"); //NOI18N
    }
        
}
