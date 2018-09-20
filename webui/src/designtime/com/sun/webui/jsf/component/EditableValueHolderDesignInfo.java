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
import com.sun.webui.jsf.design.AbstractDesignInfo;

/**
 * DesignInfo for all input components (extensions of UIInput).
 *
 * @author gjmurphy
 */
public class EditableValueHolderDesignInfo extends AbstractDesignInfo {
    
    /** Creates a new instance of EditableValueHolderDesignInfo */
    public EditableValueHolderDesignInfo(Class clazz) {
        super(clazz);
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean,
            Class childClass) {
        return false;
    }
    
    public void propertyChanged(DesignProperty property, Object oldValue) {
        if (property.getPropertyDescriptor().getName().equals("required")) { //NOI18N
            DesignBean bean = property.getDesignBean();
            String id = bean.getInstanceName();
            DesignContext context = bean.getDesignContext();
            DesignBean[] designBeans = context.getBeans();
            for (int i = 0; i < designBeans.length; i++) {
                DesignProperty p = designBeans[i].getProperty("for"); //NOI18N
                if (p != null && id.equals(p.getValue())) {
                    p.setValue(id);
                }
            }
        }
        super.propertyChanged(property, oldValue);
    }
    
}
