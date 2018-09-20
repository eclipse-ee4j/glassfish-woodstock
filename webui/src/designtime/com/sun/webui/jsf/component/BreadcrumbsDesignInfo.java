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
import java.beans.PropertyDescriptor;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;

/**
 * DesignInfo for the {@link com.sun.webui.jsf.component.Breadcrumbs} component.
 * The following behaviors are implemented:
 * <ul>
 * <li>Upon creation, populate breadcrumbs with two hyperlink components, one
 * for the web application, and one for the current page
 * ({@link com.sun.webui.jsf.component.Hyperlink} is used, since that is
 * the only hyperlink used by Creator).</li>
 * </ul>
 *
 * @author gjmurphy
 */
public class BreadcrumbsDesignInfo extends AbstractDesignInfo {
    
    public BreadcrumbsDesignInfo() {
        super(Breadcrumbs.class);
    }
    
    public Result beanCreatedSetup(DesignBean bean) {
        DesignContext context = bean.getDesignContext();
        if (context.canCreateBean(Hyperlink.class.getName(), bean, null)) {
            // Add an initial hyperlink for every page in the project
            try {
                DesignContext[] contexts = bean.getDesignContext().getProject().getDesignContexts();
                URI rootURI = context.getProject().getResourceFile(new URI("./web")).toURI(); //NOI18N
                for (int i = 0; i < contexts.length; i++) {
                    DesignBean rootBean = contexts[i].getRootContainer();
                    Object instance = rootBean.getInstance();
                    // Test to determine whether this rootBean corresponds to a page
                    if (instance != null && UIViewRoot.class.isAssignableFrom(instance.getClass()) &&
                            rootBean.getChildBeanCount() > 0 && rootBean.getChildBean(0).getInstance() instanceof Page) {
                        DesignBean hyperlinkBean =
                                context.createBean(Hyperlink.class.getName(), bean, null);                        
                        URI pageURI = new URI(contexts[i].resolveResource(rootBean.getInstanceName() + ".jsp").toString()); //NOI18N
                        URI relativeURI = rootURI.relativize(pageURI);
                        String contextRelativePath = "/faces/" + relativeURI.toString();
                        hyperlinkBean.getProperty("url").setValue(contextRelativePath); //NOI18N
                        hyperlinkBean.getProperty("text").setValue(((FacesDesignContext) contexts[i]).getDisplayName()); //NOI18N
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return Result.SUCCESS;
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        Class parentClass = parentBean.getInstance().getClass();
        if(Hyperlink.class.equals(childClass) || ImageHyperlink.class.equals(childClass))
            return true;
        return super.acceptChild(parentBean, childBean, childClass);
    }
    
    protected DesignProperty getDefaultBindingProperty(DesignBean targetBean) {
        return targetBean.getProperty("pages"); //NOI18N
    }
    
}
