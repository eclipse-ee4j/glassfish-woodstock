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
 * TabRenderer.java
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.component.Tab;
import com.sun.webui.jsf.component.TabSet;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;

/**
 * <p>Renders a Tab component.</p>
 *
 * <p>A Tab is a Hyperlink that, when clicked, also udpates the
 * lastSelectedChild value of any parent Tab instance as well as the selected
 * value of the enclosing TabSet component.</p>
 *
 * @author  Sean Comerford
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Tab"))
public class TabRenderer extends HyperlinkRenderer {

    /** Default constructor */
    public TabRenderer() {
        super();
    }

    /**
     * This method is always called by the base class (HyperlinkRenderer)
     * renderEnd method. TabRenderer should NOT render any Tab children as the
     * enclosing TabSet component will do so (if necessary).
     *
     * @param context The current FacesContext
     * @param component The current component
     */
    @Override
    protected void renderChildren(FacesContext context, UIComponent component)
            throws IOException {
        // do nothing
    }

    /**
     * <p>Render the start of an anchor (hyperlink) tag.</p>
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * start should be rendered
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        super.renderStart(context, component, writer);

        // ensure that this tab's parent is either a Tab or TabSet
        UIComponent parent = component.getParent();

        if (!(parent instanceof Tab || parent instanceof TabSet)) {
            if (LogUtil.infoEnabled()) {
                LogUtil.info(TabRenderer.class, "WEBUI0006",
                        new String[]{component.getId()});
            }
        }
    }

    @Override
    protected void finishRenderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        Tab tab = (Tab) component;

        // Set up local variables we will need
        int tabIndex = tab.getTabIndex();
        if (tabIndex >= 0) {
            writer.writeAttribute("tabIndex", Integer.toString(tabIndex), null);
        }
        super.finishRenderAttributes(context, component, writer);
    }

    /**
     * This function returns the style classes necessary to display the
     * {@link Hyperlink} component as it's state indicates
     * @return the style classes needed to display the current state of
     * the component
     */
    @Override
    protected String getStyles(FacesContext context, UIComponent component) {
        Tab link = (Tab) component;

        StringBuffer sb = new StringBuffer(200);

        Theme theme = ThemeUtilities.getTheme(context);
        if (link.isDisabled()) {
            sb.append(" "); //NOI18N
            sb.append(theme.getStyleClass(ThemeStyles.LINK_DISABLED));
        }

        Object value = link.getText();
        if (value != null) {
            String text = ConversionUtilities.convertValueToString(link, value);
            if (text.length() <= 6) {
                sb.append(" "); // NOI18N
                sb.append(theme.getStyleClass(ThemeStyles.TAB_PADDING));
            }
        }

        return (sb.length() > 0) ? sb.toString() : null;
    }

    @Override
    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
        String paramId = super.getSubmittedParameterId(context, component);
        String submittedValue =
                (String) context.getExternalContext().getRequestParameterMap().get(paramId);
        if (submittedValue != null) {
            // If this is the tab that submitted the page (i.e. the tab that the user
            // clicked on to submit the page), update the submitted value of the
            // ancestor tabSet. Note that even though this tab was clicked, if this
            // tab has children, the selected tab will be one of its descendants.
            // Also refresh the selectedChildId property of all parent tabs of the
            // selected tab.
            Tab selectedTab = (Tab) component;
            while (selectedTab.getTabChildCount() > 0) {
                String previousSelectedTabId = selectedTab.getSelectedChildId();
                List<Tab> childrenTabs = selectedTab.getTabChildren();
                selectedTab = childrenTabs.get(0);
                for (Tab childTab : childrenTabs) {
                    if (childTab.getId() != null && childTab.getId().equals(previousSelectedTabId)) {
                        selectedTab = childTab;
                    }
                }
            }
            TabSet tabSet = Tab.getTabSet(selectedTab);
            tabSet.setSubmittedValue(selectedTab.getId());
        }
    }
}
