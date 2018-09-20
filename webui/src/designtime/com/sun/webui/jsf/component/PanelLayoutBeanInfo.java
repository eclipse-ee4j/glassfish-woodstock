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

import com.sun.webui.jsf.component.util.DesignUtil;
import java.beans.PropertyDescriptor;
import com.sun.rave.designtime.Constants;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.PanelLayout} component.
 */
public class PanelLayoutBeanInfo extends PanelLayoutBeanInfoBase {
    
    /** Creates a new instance of PanelLayoutBeanInfo */
    public PanelLayoutBeanInfo() {
        PropertyDescriptor prop_panelLayout = DesignUtil.getPropertyDescriptor(this, "panelLayout");
        prop_panelLayout.setValue(Constants.PropertyDescriptor.CATEGORY,com.sun.webui.jsf.design.CategoryDescriptors.APPEARANCE);
        prop_panelLayout.setPropertyEditorClass(loadClass("com.sun.rave.propertyeditors.SelectOneDomainEditor"));
        prop_panelLayout.setValue("com.sun.rave.propertyeditors.DOMAIN_CLASS", com.sun.rave.propertyeditors.domains.LayoutDomain.class);
    }
    
    /**
     * <p>Return a class loaded by name via the class loader that loaded this class.</p>
     */
    private java.lang.Class loadClass(java.lang.String name) {

        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    
}
