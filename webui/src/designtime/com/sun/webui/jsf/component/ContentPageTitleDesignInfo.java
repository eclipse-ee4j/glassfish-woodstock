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
import com.sun.rave.designtime.faces.FacesDesignContext;

/**
 * DesignInfo for the ContentPageTitle component.
 * 
 */
public class ContentPageTitleDesignInfo extends AbstractDesignInfo {
    
    /** Creates a new instance of ContentPageTitleDesignInfo */
    public ContentPageTitleDesignInfo() {
        super(ContentPageTitleDesignInfo.class);
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        
        return true;
    }
    
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        
        return true;
    }
    
    /* Adds "-rave-layout: grid" to div style attribute. */
    public void customizeRender(final MarkupDesignBean bean, MarkupRenderContext renderContext) {
        DocumentFragment documentFragment = renderContext.getDocumentFragment();
        MarkupPosition begin = renderContext.getBeginPosition();
        MarkupPosition end = renderContext.getEndPosition();
        
        if (begin == end) {
            return;
        }
                       
        Node n = begin.getBeforeSibling();
        Element div = null;
        
        while (n != null && div == null) {
            if (n.getNodeType() == Node.ELEMENT_NODE && n.getLocalName().equals("div"))
                div = (Element) n;
            n = n.getNextSibling();
        }
        String style = div.getAttribute("style"); //NOI18N
                style = style == null ? "" : style;
                div.setAttribute("style", style + " ; -rave-layout: grid");
                
    }
    
}

