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

package com.sun.webui.jsf.renderkit.html;

import com.sun.webui.jsf.component.TreeNode;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.renderkit.html.TreeNodeRenderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;


/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.TreeNode}.
 *
 * @author gjmurphy
 */
public class TreeNodeDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    protected static String STYLE_CLASS_PROP = "styleClass"; //NOI18N
    
    boolean isTextSet;
    boolean isStyleSet;
    
    public TreeNodeDesignTimeRenderer() {
        super(new TreeNodeRenderer());
    }
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (TreeNode.class.isAssignableFrom(component.getClass())) {
            ValueBinding valueBinding = component.getValueBinding("text"); //NOI18N
            TreeNode treeNode = (TreeNode) component;
            String text = treeNode.getText();
            if (valueBinding != null && (text == null || text.toString().length() == 0)) {
                Object dummyText = getDummyData(context, valueBinding);
                if (dummyText == null) {
                    treeNode.setText("");
                } else {
                    treeNode.setText(dummyText.toString());
                }
                isTextSet = true;
            } else if (treeNode.getText() == null) {
                treeNode.setText(DesignMessageUtil.getMessage(TreeNodeDesignTimeRenderer.class, "treeNode.label"));
                String styleClass = (String) component.getAttributes().get(STYLE_CLASS_PROP);
                component.getAttributes().put(STYLE_CLASS_PROP, addStyleClass(styleClass, UNINITITIALIZED_STYLE_CLASS));
                isTextSet = true;
                isStyleSet = true;
            }
        }
        super.encodeBegin(context, component);
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        super.encodeEnd(context, component);
        if (isTextSet) {
            TreeNode treeNode = (TreeNode) component;
            treeNode.setText(null);
            isTextSet = false;
        }
        if (isStyleSet) {
            String styleClass = (String) component.getAttributes().get(STYLE_CLASS_PROP);
            component.getAttributes().put(STYLE_CLASS_PROP, removeStyleClass(styleClass, UNINITITIALIZED_STYLE_CLASS));
            isStyleSet = false;
        }
    }
}
