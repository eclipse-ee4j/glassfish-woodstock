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
import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import javax.faces.context.FacesContext;

import com.sun.rave.faces.event.Action;
import com.sun.rave.designtime.Constants;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.TreeNode} component.
 *
 * @author gjmurphy
 */
public class TreeNodeBeanInfo extends TreeNodeBeanInfoBase {
    
    public TreeNodeBeanInfo() {
        DesignUtil.hideProperties(this, new String[]{"actionListeners"});    //NOI18N
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
        beanDescriptor.setValue(Constants.BeanDescriptor.PREFERRED_CHILD_TYPES,
                new String[] { TreeNode.class.getName() });

	Theme theme =
	    ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
	String treeContent = theme.getStyleClass(ThemeStyles.TREE_CONTENT);
	String treeImgHeight = theme.getStyleClass(ThemeStyles.TREE_NODE_IMAGE_HEIGHT);
        beanDescriptor.setValue(
            Constants.BeanDescriptor.INLINE_EDITABLE_PROPERTIES,
            new String[] { "*text://div[@class='" + treeContent + //NOI18N
		" " + treeImgHeight + "']" }); // NOI18N
    }
    
    protected EventSetDescriptor[] eventSetDescriptors;
    
    public EventSetDescriptor[] getEventSetDescriptors() {
        if (eventSetDescriptors == null) {
            eventSetDescriptors = DesignUtil.generateCommandEventSetDescriptors(this);
        }
        return eventSetDescriptors;
    }
    
}
