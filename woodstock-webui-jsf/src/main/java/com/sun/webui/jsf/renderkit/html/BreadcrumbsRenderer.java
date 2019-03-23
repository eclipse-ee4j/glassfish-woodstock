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

import com.sun.faces.annotation.Renderer;
import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.jsf.component.Breadcrumbs;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;

/**
 * Renderer for the {@link Breadcrumbs} component.
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Breadcrumbs"))
public class BreadcrumbsRenderer extends javax.faces.render.Renderer {

    final static String SEPARATOR_KEY = "Breadcrumbs.separator";

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        // Open the top-most DIV element
        if (!component.isRendered()) {
            return;
        }
        Breadcrumbs breadcrumbs = (Breadcrumbs) component;
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(HTMLElements.DIV, breadcrumbs);
        writer.writeAttribute(HTMLAttributes.ID, breadcrumbs.getClientId(context), HTMLAttributes.ID);
        Theme theme = ThemeUtilities.getTheme(context);
        String styleClass = breadcrumbs.getStyleClass();
        if (!breadcrumbs.isVisible()) {
            String hiddenStyleClass = theme.getStyleClass(ThemeStyles.HIDDEN);
            if (styleClass == null) {
                styleClass = hiddenStyleClass;
            } else {
                if (styleClass.trim().endsWith(";")) {
                    styleClass = styleClass + " " + hiddenStyleClass;
                } else {
                    styleClass = styleClass + "; " + hiddenStyleClass;
                }
            }
        }
        if (theme == null && styleClass != null) {
            writer.writeAttribute(HTMLAttributes.CLASS, styleClass, HTMLAttributes.CLASS);
        } else {
            RenderingUtilities.renderStyleClass(context, writer, breadcrumbs,
                    theme.getStyleClass(ThemeStyles.BREADCRUMB_WHITE_DIV));
        }
        String style = breadcrumbs.getStyle();
        if (style != null) {
            writer.writeAttribute(HTMLAttributes.STYLE, style, HTMLAttributes.STYLE);
        }
        writer.writeText("\n", null); //NOI18N
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        Breadcrumbs breadcrumbs = (Breadcrumbs) component;
        if (!breadcrumbs.isRendered()) {
            return;
        }
        ResponseWriter writer = context.getResponseWriter();
        Theme theme = ThemeUtilities.getTheme(context);
        Hyperlink[] hyperlinks = breadcrumbs.getPages();
        if (hyperlinks != null && hyperlinks.length > 0) {
            // Render hyperlinks supplied by the application
            int i = 0;
            while (i < hyperlinks.length - 1) {
                if (hyperlinks[i].isRendered()) {
                    UIComponent parent = hyperlinks[i].getParent();
                    hyperlinks[i].setParent(breadcrumbs);
                    renderBreadcrumbsLink(context, hyperlinks[i], theme);
                    hyperlinks[i].setParent(parent);
                    if (i < hyperlinks.length - 2 || hyperlinks[i + 1].isRendered()) {
                        renderBreadcrumbsSeparator(context, breadcrumbs, theme);
                    }
                }
                i++;
            }
            if (hyperlinks[i].isRendered()) {
                renderBreadcrumbsText(context, hyperlinks[i], theme);
            }
        } else if (breadcrumbs.getChildCount() > 0) {
            // Render hyperlinks that are children of this breadcrumbs component
            List<UIComponent> childrenList = breadcrumbs.getChildren();
            int i = 0;
            while (i < childrenList.size() - 1) {
                if (Hyperlink.class.isAssignableFrom(childrenList.get(i).getClass())) {
                    Hyperlink hyperlink = (Hyperlink) childrenList.get(i);
                    if (hyperlink.isRendered()) {
                        renderBreadcrumbsLink(context, hyperlink, theme);
                        if (i < childrenList.size() - 2 || childrenList.get(i + 1).isRendered()) {
                            renderBreadcrumbsSeparator(context, breadcrumbs, theme);
                        }
                    }
                }
                i++;
            }
            if (childrenList.get(i).isRendered() &&
                    Hyperlink.class.isAssignableFrom(childrenList.get(i).getClass())) {
                renderBreadcrumbsText(context, (Hyperlink) childrenList.get(i), theme);
            }
        }
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        // Close the top-most DIV element
        if (!component.isRendered()) {
            return;
        }
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement(HTMLElements.DIV);
        writer.writeText("\n", null); //NOI18N
    }
    private String separator;

    /**
     * Renders a separator, in between two breadcrumb hyperlinks.
     */
    private void renderBreadcrumbsSeparator(FacesContext context, Breadcrumbs breadcrumbs, Theme theme)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(HTMLElements.SPAN, breadcrumbs);
        String separatorStyle =
                theme.getStyleClass(ThemeStyles.BREADCRUMB_SEPARATOR);
        writer.writeAttribute(HTMLAttributes.CLASS, separatorStyle, null);
        if (this.separator == null) {
            this.separator = escapeCharacterEntities(theme.getMessage(SEPARATOR_KEY));
        }
        writer.write(this.separator);
        writer.endElement(HTMLElements.SPAN);
    }

    /**
     * Renders a breadcrumb hyperlink.
     */
    private static void renderBreadcrumbsLink(FacesContext context, Hyperlink crumb, Theme theme)
            throws IOException {
        String linkStyle = theme.getStyleClass(ThemeStyles.BREADCRUMB_LINK);
        Map<String, Object> attributes = crumb.getAttributes();
        if (attributes != null && attributes.get("styleClass") == null) {
            attributes.put("styleClass", linkStyle);
        }
        RenderingUtilities.renderComponent(crumb, context);
    }

    /**
     * Renders the final breadcrumb hyperlink as static text.
     */
    private static void renderBreadcrumbsText(FacesContext context, Hyperlink hyperlink, Theme theme)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String text = ConversionUtilities.convertValueToString(hyperlink, hyperlink.getText());
        if (text == null || text.length() <= 0) {
            return;
        }
        writer.startElement(HTMLElements.SPAN, hyperlink);
        String textStyle = theme.getStyleClass(ThemeStyles.BREADCRUMB_TEXT);
        writer.writeAttribute(HTMLAttributes.CLASS, textStyle, null);
        writer.writeText(text, null);
        writer.endElement(HTMLElements.SPAN);
    }

    private static String escapeCharacterEntities(String str) {
        StringBuffer buffer = new StringBuffer();
        for (char c : str.toCharArray()) {
            switch (c) {
                case ('<'):
                    buffer.append("&lt;");
                    break;
                case ('>'):
                    buffer.append("&gt;");
                    break;
                case ('&'):
                    buffer.append("&amp;");
                    break;
                default:
                    buffer.append(c);
            }
        }
        return buffer.toString();
    }
}
