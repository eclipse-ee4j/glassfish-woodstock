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
package com.sun.webui.jsf.util;

import java.io.IOException;
import java.io.Writer;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.FactoryFinder;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.RenderKit;
import javax.servlet.ServletRequest;

/**
 * This class provides common methods for widget renderers.
 */
public class WidgetUtilities {

    /**
     * Helper method to capture rendered component properties for client-side
     * rendering.Based on the component renderer, either JSON or HTML text may
     * be returned.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @throws java.io.IOException
     * @return An HTML string.
     */
    public static String renderComponent(FacesContext context,
            UIComponent component) throws IOException {

        if (component == null || !component.isRendered()) {
            return null;
        }

        // Initialize Writer to buffer rendered output.
        ResponseWriter oldWriter = context.getResponseWriter();
        Writer strWriter = initStringWriter(context);

        // Render component and restore writer.
        RenderingUtilities.renderComponent(component, context);
        context.setResponseWriter(oldWriter);

        return strWriter.toString(); // Return buffered output.
    }

    /**
     * Helper method to initialize a writer used to buffer rendered output.Note:
     * Be certain to save the old writer prior to invoking this method.
     *
     * The writer in the given context is replaced with a new writer.
     *
     * @param context FacesContext for the current request.
     * @return
     *
     * @returns The Writer used to buffer rendered output.
     */
    protected static Writer initStringWriter(FacesContext context) {
        if (context == null) {
            return null;
        }

        // Get render kit.
        RenderKitFactory renderFactory = (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        RenderKit renderKit = renderFactory.getRenderKit(context,
                context.getViewRoot().getRenderKitId());

        // Get writers.
        ResponseWriter oldWriter = context.getResponseWriter();
        Writer strWriter = new FastStringWriter(1024);
        ResponseWriter newWriter;

        // Initialize new writer.
        if (null != oldWriter) {
            newWriter = oldWriter.cloneWithWriter(strWriter);
        } else {
            ExternalContext extContext = context.getExternalContext();
            ServletRequest request = (ServletRequest) extContext.getRequest();
            newWriter = renderKit.createResponseWriter(strWriter, null,
                    request.getCharacterEncoding());
        }
        // Set new writer in context.
        context.setResponseWriter(newWriter);
        return strWriter;
    }
}
