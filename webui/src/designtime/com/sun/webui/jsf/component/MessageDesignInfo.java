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
import com.sun.webui.jsf.design.AbstractDesignInfo;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * DesignInfo for {@link com.sun.webui.jsf.component.Message} component.
 *
 * @author gjmurphy
 */
public class MessageDesignInfo extends AbstractDesignInfo {

    /** Creates a new instance of MessageDesignInfo. */
    public MessageDesignInfo() {
        super(Message.class);
    }

    public Result beanCreatedSetup(DesignBean bean) {
        bean.getProperty("showSummary").setValue(Boolean.TRUE);
        bean.getProperty("showDetail").setValue(Boolean.FALSE);
        return Result.SUCCESS;
    }

    /**
     * Returns true if source bean implements EditaleValueHolder.
     *
     * @param targetBean Target <code>Label</code> bean
     * @param sourceBean Source bean (or <code>null</code>)
     * @param sourceClass Class of source object being dropped
     */
    public boolean acceptLink(DesignBean targetBean, DesignBean sourceBean,
                                Class sourceClass) {
        return (super.acceptLink(targetBean, sourceBean, sourceClass) ||
                EditableValueHolder.class.isAssignableFrom(sourceClass));
    }


    /** If a component that implements <code>EditableValueHolder</code> is
     * linked to us, update our <code>for</code> property such that it contains
     * the component's id. Links from all other types of components are treated
     * as no-ops.
     *
     * @param targetBean Target <code>Label</code> bean
     * @param sourceBean Source bean (or <code>null</code>)
     */
    public Result linkBeans(DesignBean targetBean, DesignBean sourceBean) {

        if (!EditableValueHolder.class.isAssignableFrom(sourceBean.getInstance().getClass()))
            return super.linkBeans(targetBean, sourceBean);

        DesignProperty forProperty = targetBean.getProperty("for"); //NOI18N
        if (forProperty == null)
            return Result.FAILURE;
        forProperty.setValue(sourceBean.getProperty("id").getValue()); //NOI18N
        return Result.SUCCESS;
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        return false;
    }
    
    protected DesignProperty getDefaultBindingProperty(DesignBean targetBean) {
        return null;
    }

}
