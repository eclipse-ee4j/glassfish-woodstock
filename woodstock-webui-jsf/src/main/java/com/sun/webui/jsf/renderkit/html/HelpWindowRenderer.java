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

import com.sun.webui.jsf.util.LogUtil;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.HelpWindow;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * <p>This class is responsible for rendering the {@link HelpWindow} component for
 * the HTML Render Kit.</p> <p> The {@link HelpWindow} component can be used as 
 * a link which when clicked opens up the a popup window that displays help data </p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.HelpWindow"))
public class HelpWindowRenderer extends HyperlinkRenderer {

    // -------------------------------------------------------- Static Variables
    // -------------------------------------------------------- Renderer Methods
    /**
     * This method returns the most appropriate URL under the
     * circumstances. Insome cases viewhandler.getActionURL() needs
     * to be invoked while in other cases viewhandler.getResourceURL()
     * needs to be invoked. The hyperlink renderer, by default, will
     * always use latter while generating the complete URL. Subclasses
     * of this renderer can do it their own way.
     */
    @Override
    protected String getCorrectURL(FacesContext context, UIComponent component,
            String url) {

        if (!(component instanceof HelpWindow)) {
            return null;
        }

        if (url == null) {
            return null;
        }

        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(context.getApplication().getViewHandler().getActionURL(context, url)).append("?");

        HelpWindow hw = (HelpWindow) component;
        try {
            if (hw.getWindowTitle() != null) {
                addParameter(urlBuffer, "windowTitle", hw.getWindowTitle());
            }

            if (hw.getHelpFile() != null) {
                addParameter(urlBuffer, "helpFile", hw.getHelpFile());
            }
        } catch (UnsupportedEncodingException e) {
            LogUtil.warning("Encoding error: ", e);
        }
        return urlBuffer.toString();
    }

    // --------------------------------------------------------- Private Methods

    // Helper method to append encoded request parameters to the URL in the
    // appropriate character encoding
    private void addParameter(StringBuffer buffer, String name, String value)
            throws UnsupportedEncodingException {
        if (buffer == null || name == null || value == null) {
            return;
        }

        buffer.append("&").append(name).append("=").append(URLEncoder.encode(value, "UTF-8"));
    }
}
