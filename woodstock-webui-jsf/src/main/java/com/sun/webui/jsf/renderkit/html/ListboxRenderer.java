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
 * $Id: ListboxRenderer.java,v 1.1.18.1 2009-12-29 04:52:43 jyeary Exp $
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.beans.Beans;
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import com.sun.webui.jsf.component.Listbox;
import com.sun.webui.jsf.component.ListManager;
import com.sun.webui.jsf.component.ListSelector;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renderer for a {@link com.sun.webui.jsf.component.Listbox} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Listbox"))
public class ListboxRenderer extends ListRendererBase {

    private final static boolean DEBUG = false;

    /**
     * <p>Render the list.
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * end should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {

        if (DEBUG) {
            log("encodeEnd()");
        }

        if (component instanceof ListSelector) {

            ListSelector selector = (ListSelector) component;

            if (!Beans.isDesignTime()) {
                selector.checkSelectionModel(context);
            }

            boolean useMonospace = false;
            if (selector instanceof Listbox) {
                useMonospace = ((Listbox) selector).isMonospace();
            }

            super.renderListComponent(selector, context, getStyles(context, component, useMonospace));
        } else {
            String message = "Component " + component.toString() + //NOI18N
                    " has been associated with a ListboxRenderer. " + //NOI18N
                    " This renderer can only be used by components " + //NOI18N
                    " that extend com.sun.webui.jsf.component.Selector."; //NOI18N
            throw new FacesException(message);
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * <p>Render the appropriate element end, depending on the value of the
     * <code>type</code> property.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param monospace <code>UIComponent</code> if true, use the monospace
     * styles to render the list.
     *
     * @exception IOException if an input/output error occurs
     */
    private String[] getStyles(FacesContext context,
            UIComponent component,
            boolean monospace) {

        if (DEBUG) {
            log("getStyles()");
        }

        Theme theme = ThemeUtilities.getTheme(context);

        String[] styles = new String[10];
        styles[0] = getOnChangeJavaScript((ListManager) component,
                JavaScriptUtilities.getModuleName("listbox.changed"), //NOI18N
                context);
        if (monospace) {
            styles[1] = theme.getStyleClass(ThemeStyles.LIST_MONOSPACE);
            styles[2] =
                    theme.getStyleClass(ThemeStyles.LIST_MONOSPACE_DISABLED);
        } else {
            styles[1] = theme.getStyleClass(ThemeStyles.LIST);
            styles[2] = theme.getStyleClass(ThemeStyles.LIST_DISABLED);
        }
        styles[3] = theme.getStyleClass(ThemeStyles.LIST_OPTION);
        styles[4] = theme.getStyleClass(ThemeStyles.LIST_OPTION_DISABLED);
        styles[5] = theme.getStyleClass(ThemeStyles.LIST_OPTION_SELECTED);
        styles[6] = theme.getStyleClass(ThemeStyles.LIST_OPTION_GROUP);
        styles[7] = theme.getStyleClass(ThemeStyles.LIST_OPTION_SEPARATOR);
        styles[8] = theme.getStyleClass(ThemeStyles.HIDDEN);
        styles[9] = theme.getStyleClass(ThemeStyles.LIST_ALIGN);
        return styles;
    }
}
