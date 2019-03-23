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
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.FactoryFinder;
import javax.faces.render.Renderer;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.RenderKit;
import javax.servlet.ServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class provides common methods for widget renderers.
 */
public class WidgetUtilities {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // JSON methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to add component properties.
     *
     * @param json The JSONArray to append value to.
     * @param value A string containing JSON or HTML text.
     */
    public static void addProperties(JSONArray json, String value) 
            throws JSONException {
        if (value != null) {
            try {
                // If JSON text is given, append a new JSONObject.
                json.put(new JSONObject(value));
            } catch (JSONException e) {
                // Append HTML string.
                json.put(value);
            }
        }
    }

    /**
     * Helper method to add component properties.
     *
     * @param json The JSONObject to append value to.
     * @param key A key string.
     * @param value A string containing JSON or HTML text.
     */
    public static void addProperties(JSONObject json, String key,
            String value) throws JSONException {
        if (value != null) {
            try {
                // If JSON text is given, append a new JSONObject.
                json.put(key, new JSONObject(value));
            } catch (JSONException e) {
                // Append HTML string.
                json.put(key, value);
            }
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to capture rendered component properties for client-side
     * rendering. Based on the component renderer, either JSON or HTML text may 
     * be returned.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @returns An HTML or JSON string.
     */
    public static String renderComponent(FacesContext context,
            UIComponent component) throws IOException, JSONException {
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

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Writer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to initialize a writer used to buffer rendered output.
     * 
     * Note: Be certain to save the old writer pior to invoking this method. The
     * writer in the given context is replaced with a new writer.
     *
     * @param context FacesContext for the current request.
     *
     * @returns The Writer used to buffer rendered output.
     */
    protected static Writer initStringWriter(FacesContext context) {
        if (context == null) {
            return null;
        }

        // Get render kit.
        RenderKitFactory renderFactory = (RenderKitFactory)
        FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        RenderKit renderKit = renderFactory.getRenderKit(context,
            context.getViewRoot().getRenderKitId());

        // Get writers.
        ResponseWriter oldWriter = context.getResponseWriter();
        Writer strWriter = new FastStringWriter(1024);
        ResponseWriter newWriter = null;

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

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
