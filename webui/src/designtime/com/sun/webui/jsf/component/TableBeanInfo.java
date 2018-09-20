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

/*
 * TableBeanInfo.java
 * Created on April 29, 2005, 12:40 PM
 * Version 1.0
 */

package com.sun.webui.jsf.component;

import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.sun.rave.designtime.Constants;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.Table} component.
 *
 * @author Winston Prakash
 */

public class TableBeanInfo extends TableBeanInfoBase {
    
    public TableBeanInfo(){

        java.beans.PropertyEditorManager.registerEditor(Object.class, null);
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
        // Suppose to show up in a right-click "Add >" menu item.
        beanDescriptor.setValue(Constants.BeanDescriptor.PREFERRED_CHILD_TYPES,
                new String[] {TableRowGroup.class.getName()}
        );
        PropertyDescriptor[] descriptors = this.getPropertyDescriptors();
        for (int i = 0; i < descriptors.length; i++) {
            if (descriptors[i].getName().equals("actionListener")) //NOI18N
                descriptors[i].setHidden(true);
        }

	Theme theme =
	    ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
	String tblTtlTxt = theme.getStyleClass(ThemeStyles.TABLE_TITLE_TEXT);
	String tblFtrRowTxt = theme.getStyleClass(ThemeStyles.TABLE_FOOTER_TEXT);

        beanDescriptor.setValue(
            Constants.BeanDescriptor.INLINE_EDITABLE_PROPERTIES,
            new String[] { "*title://caption[@class='" + tblTtlTxt + //NOI18N
	    "']", "footerText://span[@class='" + tblFtrRowTxt + "']" }); // NOI18N
    }
    
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
        // Do not allow component to be resized vertically
        //beanDescriptor.setValue(Constants.BeanDescriptor.RESIZE_CONSTRAINTS,
                //new Integer(Constants.ResizeConstraints.HORIZONTAL));
        return beanDescriptor;
    }
}

