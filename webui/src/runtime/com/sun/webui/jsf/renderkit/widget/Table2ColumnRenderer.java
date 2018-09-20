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

package com.sun.webui.jsf.renderkit.widget;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.Table2Column;
import com.sun.webui.jsf.util.WidgetUtilities;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders Table2Column components.
 */
@Renderer(@Renderer.Renders(rendererType = "com.sun.webui.jsf.widget.Table2Column",
componentFamily = "com.sun.webui.jsf.Table2Column"))
public class Table2ColumnRenderer extends RendererBase {

    /**
     * The set of pass-through attributes to be rendered.
     * <p>
     * Note: The WIDTH, HEIGHT, and BGCOLOR attributes are all deprecated (in
     * the HTML 4.0 spec) in favor of style sheets. In addition, the DIR and 
     * LANG attributes are not cuurently supported.
     * </p>
     */
    private static final String attributes[] = {
        "abbr",
        "axis",
        "bgColor",
        "char",
        "charOff",
        "dir",
        "headers",
        "height",
        "lang",
        "onClick",
        "onDblClick",
        "onKeyDown",
        "onKeyPress",
        "onKeyUp",
        "onMouseDown",
        "onMouseUp",
        "onMouseMove",
        "onMouseOut",
        "onMouseOver",
        "style",
        "valign",
        "width"};

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONArray getModules(FacesContext context, UIComponent component)
            throws JSONException {
        return null; // not implementd.
    }

    /** 
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws IOException, JSONException {
        Table2Column col = (Table2Column) component;
        JSONObject json = new JSONObject();

        // Add properties.
        addAttributeProperties(attributes, col, json);
        setCoreProperties(context, col, json);
        setFooterProperties(context, col, json);
        setHeaderProperties(context, col, json);

        return json;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /** 
     * Helper method to obtain footer properties.
     *
     * @param context FacesContext for the current request.
     * @param component Table2Column to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setFooterProperties(FacesContext context, Table2Column component,
            JSONObject json) throws IOException, JSONException {
        // Get footer facet.
        UIComponent facet = component.getFacet(Table2Column.FOOTER_FACET);
        if (facet != null && facet.isRendered()) {
            WidgetUtilities.addProperties(json, "footerText",
                    WidgetUtilities.renderComponent(context, facet));
        } else {
            // Add footer text.
            json.put("footerText", component.getFooterText());
        }
    }

    /** 
     * Helper method to obtain header properties.
     *
     * @param context FacesContext for the current request.
     * @param component Table2Column to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setHeaderProperties(FacesContext context, Table2Column component,
            JSONObject json) throws IOException, JSONException {
        // Get header facet.
        UIComponent facet = component.getFacet(Table2Column.HEADER_FACET);
        if (facet != null && facet.isRendered()) {
            WidgetUtilities.addProperties(json, "headerText",
                    WidgetUtilities.renderComponent(context, facet));
        } else {
            // Add header text.
            json.put("headerText", component.getHeaderText());
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
