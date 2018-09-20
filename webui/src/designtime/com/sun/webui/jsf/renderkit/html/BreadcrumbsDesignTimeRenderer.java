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

import com.sun.webui.jsf.component.Breadcrumbs;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.ImageHyperlink;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.renderkit.html.BreadcrumbsRenderer;
import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;

/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.Breadcrumbs}. If
 * the breadcrumbs has no children hyperlinks, then a minimual block of markup
 * is output. If the breadcrummbs has children, any of them are missing both
 * text and content will have their text temporarily set to the display name
 * of the hyperlink component. If the breadcrumbs is bound to an array or list
 * of hyperlinks, and design-time evaluation of the bound property returns an
 * empty value, a default set of dummy hyperlinks is temporarily added to the
 * component while it renders.
 *
 * @author gjmurphy
 */
public class BreadcrumbsDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    static int DUMMY_PAGES_SET = 1;
    static int COMPONENT_SHADOWED = 2;
    static int LINK_CHILDREN_SHADOWED = 3;
    
    int rendererStatus;
    
    public BreadcrumbsDesignTimeRenderer() {
        super(new BreadcrumbsRenderer());
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        rendererStatus = 0;
        if (component instanceof Breadcrumbs) {
            if(component.getValueBinding("pages") != null) {
                ValueBinding pagesBinding = component.getValueBinding("pages");
                Object value = pagesBinding.getValue(context);
                if (value != null && value instanceof Hyperlink[] && ((Hyperlink[])value).length == 0) {
                    ((Breadcrumbs) component).setPages(getDummyHyperlinks());
                    rendererStatus = DUMMY_PAGES_SET;
                }
            } else if(component.getChildCount() == 0) {
                ResponseWriter writer = context.getResponseWriter();
                writer.startElement("div", component);
                String style = ((Breadcrumbs) component).getStyle();
                writer.writeAttribute("style", style, "style");
                writer.startElement("span", component);
                writer.writeAttribute("class", super.UNINITITIALIZED_STYLE_CLASS, "class");
                String label = DesignMessageUtil.getMessage(BreadcrumbsDesignTimeRenderer.class, "breadcrumbs.label");
                char[] chars = label.toCharArray();
                writer.writeText(chars, 0, chars.length);
                writer.endElement("span");
                writer.endElement("div");
                rendererStatus = COMPONENT_SHADOWED;
            } else {
                List children = component.getChildren();
                int i = children.size() - 1;
                if (Hyperlink.class.isAssignableFrom(children.get(i).getClass())) {
                    Hyperlink link = (Hyperlink)children.get(i);
                    if (link.getText() == null && link.getChildCount() == 0) {
                        if (link instanceof ImageHyperlink)
                            link.setText(DesignMessageUtil.getMessage(BreadcrumbsDesignTimeRenderer.class,
                                    "imageHyperlink.label"));
                        else
                            link.setText(DesignMessageUtil.getMessage(BreadcrumbsDesignTimeRenderer.class,
                                    "hyperlink.label"));
                        link.setStyleClass(addStyleClass(link.getStyleClass(), UNINITITIALIZED_STYLE_CLASS));
                        rendererStatus = LINK_CHILDREN_SHADOWED;
                    }
                }
            }
        }
        if (rendererStatus != COMPONENT_SHADOWED) {
            super.encodeBegin(context, component);
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if (rendererStatus != COMPONENT_SHADOWED) {
            super.encodeEnd(context, component);
        }
        if (rendererStatus == DUMMY_PAGES_SET) {
            ((Breadcrumbs) component).setPages(null);
        } else if (rendererStatus == LINK_CHILDREN_SHADOWED) {
            List children = component.getChildren();
            int i = children.size() - 1;
            Hyperlink link = (Hyperlink)children.get(i);
            link.setText(null);
            link.setStyleClass(removeStyleClass(link.getStyleClass(), UNINITITIALIZED_STYLE_CLASS));
        }
        rendererStatus = 0;
    }
    
    static Hyperlink[] dummyHyperlinks;
    
    static Hyperlink[] getDummyHyperlinks() {
        if (dummyHyperlinks == null) {
            Hyperlink dummyHyperlink = new Hyperlink();
            String dummyText = getDummyData(String.class).toString();
            dummyHyperlink.setText(dummyText);
            dummyHyperlinks = new Hyperlink[]{dummyHyperlink, dummyHyperlink, dummyHyperlink};
        }
        return dummyHyperlinks;
    }
    
}
