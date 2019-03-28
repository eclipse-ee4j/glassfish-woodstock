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
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.ListSelector;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renderer for a {@link DropDown} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.DropDown"))
public class DropDownRenderer extends ListRendererBase {

    private final static boolean DEBUG = false;

    /**
     * <p>Render the drop-down dropDown.
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

        if (!(component instanceof DropDown)) {
            Object[] params = {component.toString(),
                this.getClass().getName(),
                DropDown.class.getName()};
            String message = MessageUtil.getMessage("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                    "Renderer.component", params);   //NOI18N
            throw new FacesException(message);

        }

        DropDown dropDown = (DropDown) component;
        if (dropDown.isForgetValue()) {
            dropDown.setValue(null);
        }

        // Render the element and attributes for this component
        //ResponseWriter writer = context.getResponseWriter();

        String[] styles = null;
        if (dropDown.isSubmitForm()) {
            styles = getJumpDropDownStyles(dropDown, context);
        } else {
            styles = getDropDownStyles(dropDown, context);
        }

        super.renderListComponent((ListSelector) dropDown, context, styles);
        ResponseWriter writer = context.getResponseWriter();
        if (dropDown.isSubmitForm()) {
            String id = dropDown.getClientId(context).concat(DropDown.SUBMIT);
            RenderingUtilities.renderHiddenField(dropDown, writer, id, "false");
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
    private String[] getDropDownStyles(DropDown component, FacesContext context) {

        Theme theme = ThemeUtilities.getTheme(context);
        String[] styles = new String[10];
        styles[0] = getOnChangeJavaScript(component,
                JavaScriptUtilities.getModuleName("dropDown"), "changed", //NOI18N
                context);
        styles[1] = theme.getStyleClass(ThemeStyles.MENU_STANDARD);
        styles[2] = theme.getStyleClass(ThemeStyles.MENU_STANDARD_DISABLED);
        styles[3] = theme.getStyleClass(ThemeStyles.MENU_STANDARD_OPTION);
        styles[4] = theme.getStyleClass(ThemeStyles.MENU_STANDARD_OPTION_DISABLED);
        styles[5] = theme.getStyleClass(ThemeStyles.MENU_STANDARD_OPTION_SELECTED);
        styles[6] = theme.getStyleClass(ThemeStyles.MENU_STANDARD_OPTION_GROUP);
        styles[7] = theme.getStyleClass(ThemeStyles.MENU_STANDARD_OPTION_SEPARATOR);
        styles[8] = theme.getStyleClass(ThemeStyles.HIDDEN);
        return styles;
    }

    /**
     * Helper function to get the theme specific styles for this dropdown given
     * the current context
     */
    private String[] getJumpDropDownStyles(DropDown component, FacesContext context) {

        Theme theme = ThemeUtilities.getTheme(context);
        String[] styles = new String[10];
        styles[0] = getOnChangeJavaScript(component, null,
                "admingui.woodstock.dropDownChanged", //NOI18N
                context);
        styles[1] = theme.getStyleClass(ThemeStyles.MENU_JUMP);
        styles[2] = ""; // jumpMENU can't be disabled
        styles[3] = theme.getStyleClass(ThemeStyles.MENU_JUMP_OPTION);
        styles[4] = theme.getStyleClass(ThemeStyles.MENU_JUMP_OPTION_DISABLED);
        styles[5] = theme.getStyleClass(ThemeStyles.MENU_JUMP_OPTION_SELECTED);
        styles[6] = theme.getStyleClass(ThemeStyles.MENU_JUMP_OPTION_GROUP);
        styles[7] = theme.getStyleClass(ThemeStyles.MENU_JUMP_OPTION_SEPARATOR);
        styles[8] = theme.getStyleClass(ThemeStyles.HIDDEN);
        return styles;
    }
}
