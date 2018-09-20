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

package com.sun.webui.jsf.component.propertyeditors;

import com.sun.rave.propertyeditors.SelectOneDomainEditor;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.propertyeditors.domains.AttachedDomain;
import com.sun.rave.propertyeditors.domains.Element;
import com.sun.webui.jsf.component.Tab;
import com.sun.webui.jsf.component.TabSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import javax.faces.component.UIComponent;

public class TabIdsEditor extends SelectOneDomainEditor {
    
    public TabIdsEditor() {
        super(new TabIdsDomain());
    }
    
    /**
     * Domain of identifiers of all {@link com.sun.webui.jsf.component.Tab} components
     * within the currently selected {@link com.sun.webui.jsf.component.TabSet}
     * component. Used to provide an enumeration of values for the <code>selected</code>
     * property. Iterates over all tabs in the tabSet in document order.
     */
    static class TabIdsDomain extends AttachedDomain {
        
        public Element[] getElements() {
            // If we have not been attached yet, there is nothing we can do
            // except return an empty list
            if (this.getDesignProperty() == null)
                return Element.EMPTY_ARRAY;
            // Construct a list of all tab descendants
            Stack beanStack = new Stack();
            beanStack.push(this.getDesignProperty().getDesignBean());
            List beanList = new ArrayList();
            while (!beanStack.isEmpty()) {
                DesignBean bean = (DesignBean) beanStack.pop();
                if (bean.getInstance() instanceof Tab)
                    beanList.add(bean);
                DesignBean[] childBeans = bean.getChildBeans();
                for (int i = bean.getChildBeanCount() - 1; i >= 0; i--)
                    beanStack.push(childBeans[i]);
            }
            if (beanList.size() == 0)
                return Element.EMPTY_ARRAY;
            // Construct an array of elements from the labels and identifiers of
            // the retained tab components
            Element elements[] = new Element[beanList.size()];
            for (int i = 0; i < elements.length; i++) {
                DesignBean bean = (DesignBean) beanList.get(i);
                Tab tab = (Tab) bean.getInstance();
                String id = tab.getId();
                String text = (String) tab.getText();
                String gutter = "";
                if (bean.getBeanParent().getInstance() instanceof Tab) {
                    gutter = "  ";
                    if (bean.getBeanParent().getBeanParent().getInstance() instanceof Tab) {
                        gutter = "    ";
                    }
                }
                if (text == null)
                    elements[i] = new Element(id, gutter + id);
                else
                    elements[i] = new Element(id, gutter + text + " (" + id + ")");
            }
            return elements;
            
        }
    }
    
}
