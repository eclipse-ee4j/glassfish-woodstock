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
import com.sun.webui.jsf.design.AbstractDesignInfo;
import com.sun.webui.jsf.model.MultipleSelectOptionsList;
import com.sun.webui.jsf.model.Option;

/** DesignInfo class for components that extend the {@link
 * com.sun.webui.jsf.component.AddRemove} component.
 *
 * @author gjmurphy
 */
public class AddRemoveDesignInfo extends SelectorDesignInfo {
    
    /** Name of the Add Button facet */
    private static final String ADD_BUTTON_FACET = "addButton"; //NOI18N
    
    /** Name of the Remove Button facet */
    private static final String REMOVE_BUTTON_FACET = "removeButton"; //NOI18N
    
    public AddRemoveDesignInfo() {
        super(AddRemove.class);
    }
        
    
    /** When a new AddRemove-based component is dropped, create a default
     * list of options and bind if to this component's <code>items</code> and
     * <code>selected</code> properties.
     *
     * @param bean <code>DesignBean</code> for the newly created instance
     */
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        
        FacesDesignContext context = (FacesDesignContext) bean.getDesignContext();
        
        DesignProperty availableItemsLabel = bean.getProperty("availableItemsLabel"); // NOI18N
        availableItemsLabel.setValue(DesignMessageUtil.getMessage(AddRemoveDesignInfo.class,"AddRemove.available")); // NOI18N
        
        DesignProperty selectedItemsLabel = bean.getProperty("selectedItemsLabel"); // NOI18N
        selectedItemsLabel.setValue(DesignMessageUtil.getMessage(AddRemoveDesignInfo.class,"AddRemove.selected"));  // NOI18N    
        
        
        DesignBean options = context.getBeanByName(getOptionsListName(bean));
        if(options != null) {
            bean.getProperty("selected").setValueSource(context.getBindingExpr(options, ".selectedValue")); //NOI18N
        }
        return Result.SUCCESS;
    }
    /**
     * AddRemove component doesn't need to support "autosubmit" behavior so disabling this
     * default behavior.
     * @param bean <code>DesignBean</code> for the newly created instance 
     */
    public boolean supportsAutoSubmit(DesignBean bean) {
           return false;
    }
    
    protected Class getOptionsListClass() {
        return MultipleSelectOptionsList.class;
    }
    
}
