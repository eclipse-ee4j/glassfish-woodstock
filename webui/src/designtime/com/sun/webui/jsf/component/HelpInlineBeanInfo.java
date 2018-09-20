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

import com.sun.rave.designtime.Constants;
import java.beans.BeanDescriptor;
import com.sun.webui.jsf.component.util.DesignUtil;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.HelpInline} component.
 *
 * @author gjmurphy
 */
public class HelpInlineBeanInfo extends HelpInlineBeanInfoBase {
    
    public HelpInlineBeanInfo() {
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
        beanDescriptor.setValue(Constants.BeanDescriptor.HELP_KEY, "projrave_ui_elements_palette_bh_inline_help");
        beanDescriptor.setValue(Constants.BeanDescriptor.PROPERTIES_HELP_KEY,  "projrave_ui_elements_palette_bh_propsheets_bh_inline_help_props");
        beanDescriptor.setValue(
            Constants.BeanDescriptor.INLINE_EDITABLE_PROPERTIES,
            new String[] { "*text://div" }); // NOI18N
    }
    
}
