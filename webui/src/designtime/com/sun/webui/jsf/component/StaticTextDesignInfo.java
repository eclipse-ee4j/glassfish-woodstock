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
import com.sun.webui.jsf.design.AbstractDesignInfo;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;

/**
 * <p>Design time behavior for a <code>StaticText</code> component.  The
 * following behavior is provided:</p>
 * <ul>
 * <li>Default the <code>text</code> property to the instance
 *     name when we are created.</li>
 * <li>The <code>text</code> property can be edited directly in the 
 *     design-time rendering of the component.
 * <li>Only <code>UIParameter</code> components may be dropped onto
 *     this component.</li>
 * </ul>
 *
 * @author gjmurphy
 */
public class StaticTextDesignInfo extends AbstractDesignInfo {
    
    /**
     * <p>Construct a new <code>StaticTextDesignInfo</code> instance.</p>
     */
    public StaticTextDesignInfo() {
        super(StaticText.class);
    }
    
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        return Result.SUCCESS;        
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        return UIParameter.class.isAssignableFrom(childClass) ? true : false;
    }
    
}
