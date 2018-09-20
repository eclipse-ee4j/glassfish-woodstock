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
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.impl.BasicDesignInfo;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import javax.faces.component.UIComponent;

/**
 * <p>Design time behavior for a <code>Button</code> component.</p>
 * <ul>
 * <li>When dropped, set the <code>text</code> property to <code>Submit</code>
 * (localized).</li>
 * <li>If an image component is linked to this component, set our <code>imageURL</code>
 * property to the image's <code>url</code> property, and delete the image.</li>
 * </ul>
 */

public class ButtonDesignInfo extends AbstractDesignInfo {

    public ButtonDesignInfo() {
        super(Button.class);
    }

    public Result beanCreatedSetup(DesignBean bean) {
        
        super.beanCreatedSetup(bean);
        DesignProperty textProperty = bean.getProperty("text"); //NOI18N
        textProperty.setValue(
                bean.getBeanInfo().getBeanDescriptor().getDisplayName());
        return Result.SUCCESS;
    }

    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        return false;
    }

}
