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

import com.sun.rave.designtime.*;
import com.sun.rave.designtime.markup.*;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import javax.faces.component.html.HtmlPanelGrid;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * DesignInfo for the {@link com.sun.webui.jsf.component.PanelLayout} component.
 *
 * @author gjmurphy
 */
public class PanelLayoutDesignInfo extends AbstractDesignInfo implements MarkupDesignInfo {
    
    public PanelLayoutDesignInfo() {
        super(PanelLayoutDesignInfo.class);
    }
    
    public Result beanCreatedSetup(DesignBean bean) {
        Object parent = bean.getBeanParent().getInstance();
        bean.getProperty("panelLayout").setValue(PanelLayout.FLOW_LAYOUT);
        if (parent instanceof HtmlPanelGrid) {
            appendStyle(bean, "width: 100%; height: 100%;");
        } else if (parent instanceof PanelLayout && ((PanelLayout)parent).getPanelLayout().equals(PanelLayout.FLOW_LAYOUT)) {
            appendStyle(bean, "width: 100%; height: 100%;");
        } else if (parent instanceof Tab) {
            appendStyle(bean, "width: 100%; height: 128px;");
        } else {
            appendStyle(bean, "width: 128px; height: 128px;");
        }
        return Result.SUCCESS;
    }
    
    private static void appendStyle(DesignBean bean, String style) {
        DesignProperty styleProperty = bean.getProperty("style");
        String styleValue = (String) styleProperty.getValue();
        if (styleValue == null || styleValue.length() == 0) {
            styleValue = style;
        } else {
            styleValue = styleValue.trim();
            if (styleValue.charAt(styleValue.length() - 1) == ';')
                styleValue = styleValue + " " + style;
            else
                styleValue = styleValue + "; " + style;
        }
        styleProperty.setValue(styleValue);
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if(childClass.equals(PanelLayout.class))
            return true;
        if (childClass.getName().equals("com.sun.rave.xhtml.Jsp_Directive_Include"))
            return false;
        return super.acceptChild(parentBean, childBean, childClass);
    }
    
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if(Tab.class.isAssignableFrom(parentBean.getInstance().getClass()))
            return true;
        if(PanelLayout.class.isAssignableFrom(parentBean.getInstance().getClass()))
            return true;
        return super.acceptParent(parentBean, childBean, childClass);
    }
    
    public void customizeRender(MarkupDesignBean markupDesignBean, MarkupRenderContext renderContext) {
//        DocumentFragment documentFragment = renderContext.getDocumentFragment();
//        MarkupPosition begin = renderContext.getBeginPosition();
//        MarkupPosition end = renderContext.getEndPosition();
//        
//        if (begin == end || markupDesignBean.getChildBeanCount() > 0) {
//            return;
//        }
//        
//        assert begin.getUnderParent() == end.getUnderParent();
//        Node child = begin.getBeforeSibling();
//        Node stop = end.getBeforeSibling();
//        
//        // Draw a rave design border around the panel
//        for (child = begin.getBeforeSibling(); child != stop; child = child.getNextSibling()) {
//            if (child instanceof Element) {
//                Element e = (Element)child;
//                String styleClass = e.getAttribute("class"); //NOI18N
//                styleClass = styleClass == null ? "" : styleClass;
//                e.setAttribute("class", styleClass + " rave-design-border"); // NOI18N
//                break;
//            }
//        }
    }
    
}
