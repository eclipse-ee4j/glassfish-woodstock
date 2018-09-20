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
 * FormDesignInfo.java
 *
 * Created on February 8, 2005, 2:18 PM
 */

package com.sun.webui.jsf.component;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectManyCheckbox;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.DisplayAction;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import com.sun.webui.jsf.renderkit.html.FormRenderer;
import com.sun.webui.jsf.component.vforms.VirtualFormsCustomizerAction;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;

/**
 * DesignInfo for the {@link com.sun.webui.jsf.component.Form} component.
 *
 * @author Matt
 * @author gjmurphy
 */
public class FormDesignInfo extends AbstractDesignInfo {
    
    private static final String ID_SEP = String.valueOf(NamingContainer.SEPARATOR_CHAR);

    /** Creates a new instance of FormDesignInfo */
    public FormDesignInfo() {
        super(Form.class);
    }

    /**
     * Allow form anywhere, so long as parent is not a form and the parent has
     * no form ancestor.
     */
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {        
        DesignBean thisBean = parentBean;
        while (thisBean.getBeanParent() != null) {
            if (thisBean.getInstance() instanceof Form)
                return false;
            thisBean = thisBean.getBeanParent();
        }
        //return super.isSunWebUIContext(parentBean);
        return true;
    }
    
    /** 
     * <p>Designtime version of 
     * <code>Form.getFullyQualifiedId(UIComponent)</code> for webui.
     */
    /*
     * Be sure to keep this method in sync with the versions in 
     * <code>com.sun.webui.jsf.component.Form</code> (in webui) and 
     * <code>javax.faces.component.html.HtmlFormDesignInfo</code> 
     * (in jsfcl).</p>
     */
    public static String getFullyQualifiedId(DesignBean bean) {
        if (bean == null) {
            return null;
        }
        Object beanInstance = bean.getInstance();
        if (! (beanInstance instanceof UIComponent)) {
            return null;
        }
        if (beanInstance instanceof Form) {
            return ID_SEP;
        }
        String compId = bean.getInstanceName();
        if (compId == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer(compId);
        DesignBean currentBean = bean.getBeanParent();
        boolean formEncountered = false;
        while (currentBean != null) {
            sb.insert(0, ID_SEP);
            if (currentBean.getInstance() instanceof Form) {
                formEncountered = true;
                break;
            }
            else {
                String currentCompId = currentBean.getInstanceName();
                if (currentCompId == null) {
                    return null;
                }
                sb.insert(0, currentCompId);
            }
            currentBean = currentBean.getBeanParent();
        }
        if (formEncountered) {
            return sb.toString();
        }
        else {
            return null;
        }
    }
}
