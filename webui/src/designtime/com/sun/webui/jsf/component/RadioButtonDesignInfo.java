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

import javax.faces.component.UIComponentBase;

import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.webui.jsf.design.AbstractDesignInfo;

/** Design time behavior for a {@link com.sun.webui.jsf.component.RadioButton}
 * component. The following design-time behavior is defined:
 * <ul>
 * <li>When a new <code>RadioButton</code> is dropped, set its
 * <code>label</code> property to the component's display name.</li>
 * <li>When a new <code>RadioButton</code> is dropped, set its <code>name</code>
 * property to the value of the <code>name</code> property of the nearest
 * radio button in the same parent container, or if none, to the id of the parent
 * container.
 * </ul>
 *
 * @author gjmurphy
 */
public class RadioButtonDesignInfo extends AbstractDesignInfo {
    
    public RadioButtonDesignInfo() {
        super(RadioButton.class);
    }
    
    public Result beanCreatedSetup(DesignBean bean) {
        DesignProperty label = bean.getProperty("label"); //NOI18N
        DesignProperty name = bean.getProperty("name"); //NOI18N
        label.setValue(
            bean.getBeanInfo().getBeanDescriptor().getDisplayName());
        DesignBean parent = bean.getBeanParent();
        for (int i = 0; i < parent.getChildBeanCount(); i++) {
            DesignBean child = parent.getChildBean(i);
            if (child.getBeanInfo().getBeanDescriptor().getBeanClass() == RadioButton.class
                    && child.getProperty("name").isModified()
                    && child != bean)
                name.setValue(child.getProperty("name").getValue());
        }
        if (name.getValue() == null) {
            if (parent.getInstance() instanceof UIComponentBase)
                name.setValue("radioButton-group-" + ((UIComponentBase)parent.getInstance()).getId());
            else
                name.setValue(((UIComponentBase)bean.getInstance()).getId());
        }
        return Result.SUCCESS;
    }

    protected DesignProperty getDefaultBindingProperty(DesignBean targetBean) {
        return targetBean.getProperty("selectedValue"); //NOI18N
    }
    
    public void propertyChanged(DesignProperty property, Object oldValue) {
        // If the value of this component's "selected" property was set to equal
        // the value of its "selectedValue" property, this indicates that the user
        // wanted the widget to be preselected at run-time. If this was the case,
        // and "selectedValue" has been changed, updated "selected" accordingly.
        if (property.getPropertyDescriptor().getName().equals("selectedValue")) {
            DesignProperty selectedProperty = property.getDesignBean().getProperty("selected");
            if (oldValue != null && oldValue.equals(selectedProperty.getValue()))
                selectedProperty.setValue(property.getValue());
        }
        super.propertyChanged(property, oldValue);
    }
        
}
