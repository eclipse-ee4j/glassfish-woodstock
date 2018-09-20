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

package com.sun.webui.jsf.component.customizers;

import com.sun.rave.designtime.CheckedDisplayAction;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.impl.BasicDisplayAction;
import com.sun.webui.jsf.component.RbCbSelector;
import com.sun.webui.jsf.component.RadioButtonGroup;
import com.sun.webui.jsf.component.CheckboxGroup;
import com.sun.webui.jsf.component.Form;
import javax.faces.component.NamingContainer;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import com.sun.webui.jsf.component.FormDesignInfo;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * A basic implementation of auto-submit for editable value-holder
 * components.
 *
 * @author gjmurphy
 */

public class AutoSubmitOnChangeAction extends BasicDisplayAction implements
        CheckedDisplayAction {
    
    private static Pattern submitPattern;
    
    private static Pattern getSubmitPattern() {
        if (submitPattern == null) {
            submitPattern = Pattern.compile(JavaScriptUtilities.getModuleName("common.timeoutSubmitForm") +
                    "\\s*\\(\\s*this\\s*\\.\\s*form\\s*,\\s*'\\S+'\\s*\\)\\s*;?"); //NOI18N
        }
        return submitPattern;
    }
    
    protected DesignBean bean;
    
    public AutoSubmitOnChangeAction(DesignBean bean) {
        super(DesignMessageUtil.getMessage(AutoSubmitOnChangeAction.class,
                "AutoSubmitOnChangeAction.label")); //NOI18N
        this.bean = bean;
    }
    
    public boolean isChecked() {
        return isAutoSubmit();
    }
    
    public Result invoke() {
        return toggleAutoSubmit();
    }
    
    public boolean isAutoSubmit() {
        DesignProperty property = getSubmitProperty();
        if (property == null)
            return false;
        String value = (String) property.getValue();
        if(value == null)
            return false;
        return getSubmitPattern().matcher(value).find();
    }
    
    public Result toggleAutoSubmit() {
        DesignProperty property = getSubmitProperty();
        if (property == null)
            return Result.FAILURE;
        String value = (String) property.getValue();
        if (value == null || value.length() == 0) {
            // If no property value, set it
            property.setValue(getSubmitScript(null));
        } else {
            if (isAutoSubmit()) {
                // If property value contains the onSubmit script, remove it
                property.setValue(getSubmitPattern().matcher(value).replaceFirst("")); //NOI18N
            } else {
                // Otherwise, append the onSubmit script
                property.setValue(getSubmitScript(value));
            }
        }
        return Result.SUCCESS;
    }
    
    /**
     * Returns the <code>onChange</code> property for all components except
     * checkbox and radio button types, for which <code>onClick</code> is
     * returned. Special casing for these components needed by Internet
     * Explorer.
     */
    DesignProperty getSubmitProperty() {
        Object beanInstance = bean.getInstance();
        Class beanType = beanInstance.getClass();
        if (RbCbSelector.class.isAssignableFrom(beanType) ||
                beanInstance instanceof RadioButtonGroup ||
                beanInstance instanceof CheckboxGroup)
            return bean.getProperty("onClick"); //NOI18N
        else
            return bean.getProperty("onChange"); //NOI18N
    }
    
    String getSubmitScript(String previousScript) {
        StringBuffer buffer = new StringBuffer();
        if (previousScript != null) {
            buffer.append(previousScript);
            if (!Pattern.compile(";\\s*$").matcher(previousScript).find()) {
                buffer.append(';');
            }
            if (!Pattern.compile("\\s+$").matcher(buffer.toString()).find()) {
                buffer.append(' ');
            }
        }
        String id = FormDesignInfo.getFullyQualifiedId(bean);
        if (id == null) {
            id = bean.getInstanceName();
        } else if (id.startsWith(String.valueOf(NamingContainer.SEPARATOR_CHAR)) && id.length() > 1) {
            //fully qualified id (starting with ":") could look intimidating to users. so just chop off leading ":"
            id = id.substring(1, id.length());
        }
//        buffer.append(JavaScriptUtilities.getModuleName("common.timeoutSubmitForm"));
        buffer.append("require(['").append(JavaScriptUtilities.getModuleName("common")).append("'], function (common) {").append("\n");
        buffer.append("common.timeoutSubmitForm");
        buffer.append("(this.form, '");
        buffer.append(id);
        buffer.append("');");
        buffer.append("});");
        return buffer.toString();
    }
    
}
