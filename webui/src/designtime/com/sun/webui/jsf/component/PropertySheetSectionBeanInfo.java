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

import java.beans.BeanDescriptor;
import com.sun.rave.designtime.Constants;

//import javax.faces.context.FacesContext;
//import com.sun.webui.theme.Theme;
//import com.sun.webui.jsf.theme.ThemeStyles;
//import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.PropertySheetSection} 
 * component.
 */
public class PropertySheetSectionBeanInfo extends PropertySheetSectionBeanInfoBase {
    
    /** Creates a new instance of PropertySheetSectionBeanInfo */
    public PropertySheetSectionBeanInfo() {
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();         
        beanDescriptor.setValue(Constants.BeanDescriptor.PREFERRED_CHILD_TYPES,
                new String[] {Property.class.getName()});
        // Not yet working right
	//Theme theme =
	//    ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
	//String conFldLegDiv =
	//    theme.getStyleClass(ThemeStyles.CONTENT_FIELDSET_LEGEND_DIV);
        //beanDescriptor.setValue(
        //    Constants.BeanDescriptor.INLINE_EDITABLE_PROPERTIES,
        //    new String[] { "*label://div[@class='" + conFldLegDiv + //NOI18N
	//	  "']" }); // NOI18N
    }

}
