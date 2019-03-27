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
import com.sun.webui.jsf.component.ThemeLinks;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renderer for a {@link Theme} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.ThemeLinks"))
public class ThemeLinksRenderer extends javax.faces.render.Renderer {

    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        return;
    }

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (!(component instanceof ThemeLinks)) {
            Object[] params = {component.toString(),
                this.getClass().getName(),
                ThemeLinks.class.getName()
            };
            String message = MessageUtil.getMessage("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                    "Renderer.component", params);              //NOI18N
            throw new FacesException(message);
        }

        ThemeLinks themeLinks = (ThemeLinks) component;
        ResponseWriter writer = context.getResponseWriter();

        // Link and Scripts
        Theme theme = ThemeUtilities.getTheme(context);
        if (themeLinks.isStyleSheetInline()) {
            RenderingUtilities.renderStyleSheetInline(themeLinks, theme, context, writer);
        } else if (themeLinks.isStyleSheetLink()) {
            RenderingUtilities.renderStyleSheetLink(themeLinks, theme, context, writer);
        }

        // Do not render any JavaScript.
        if (!themeLinks.isJavaScript()) {
            return;
        }

        // Render Dojo config.
        JavaScriptUtilities.renderJavaScript(component, writer,
                JavaScriptUtilities.getDojoConfig(themeLinks.isDebug(),
                themeLinks.isParseWidgets()));

        // Render Dojo include.
        JavaScriptUtilities.renderDojoInclude(component, writer);

        // Render JSON include.
        //JavaScriptUtilities.renderJsonInclude(component, writer);

        // Render Prototype include before JSF Extensions.
        JavaScriptUtilities.renderPrototypeInclude(component, writer);

        // Render JSF Extensions include.
        //JavaScriptUtilities.renderJsfxInclude(component, writer);

        // Render module config after including dojo.
        JavaScriptUtilities.renderJavaScript(component, writer,
                JavaScriptUtilities.getModuleConfig(themeLinks.isDebug()));

        // Render global include.
        JavaScriptUtilities.renderGlobalInclude(component, writer);
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        return;
    }
}
