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

package com.sun.webui.jsf.renderkit.ajax;

import com.sun.faces.annotation.Renderer;
import com.sun.data.provider.RowKey;
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import com.sun.webui.jsf.component.Table2Column;
import com.sun.webui.jsf.component.Table2RowGroup;
import com.sun.webui.jsf.util.WidgetUtilities;
import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders Table2RowGroup components.
 */
@Renderer(@Renderer.Renders(rendererType = "com.sun.webui.jsf.ajax.Table2RowGroup",
componentFamily = "com.sun.webui.jsf.Table2RowGroup"))
public class Table2RowGroupRenderer extends javax.faces.render.Renderer {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Render the beginning of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    @Override
    public void encodeBegin(FacesContext context, UIComponent component) {
        // Do nothing...
    }

    /**
     * Render the children of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        // Get first and max rows parameters.
        String xjson = (String) context.getExternalContext().
                getRequestHeaderMap().get(AsyncResponse.XJSON_HEADER);
        if (xjson == null) {
            return;
        }

        try {
            Table2RowGroup group = (Table2RowGroup) component;
            JSONObject json = new JSONObject(xjson);

            // Set first and max rows.
            if (json != null) {
                Integer first = (Integer) json.get("first");
                if (first != null) {
                    // To do: move to decode method.
                    group.setFirst(first.intValue());
                }
            }

            // To do: Need algorithm to retrieve rows
            int maxRows = group.getRows();
            group.setRows(maxRows * 2);
            JSONArray rows = getRows(context, group);
            group.setRows(maxRows);

            if (rows != null) {
                rows.write(context.getResponseWriter());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Render the ending of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    @Override
    public void encodeEnd(FacesContext context, UIComponent component) {
        // Do nothing...
    }

    /**
     * Return a flag indicating whether this Renderer is responsible
     * for rendering the children the component it is asked to render.
     * The default implementation returns false.
     */
    @Override
    public boolean getRendersChildren() {
        return true;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Helper method to render rows.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    private JSONArray getRows(FacesContext context, Table2RowGroup component)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            return null;
        }

        // Render empty data message.
        if (component.getRowCount() == 0) {
            return null;
        }

        // Get rendered row keys.
        RowKey[] rowKeys = component.getRenderedRowKeys();
        if (rowKeys == null) {
            return null;
        }

        JSONArray json = new JSONArray();
        try {
            // Iterate over the rendered RowKey objects.
            for (int i = 0; i < rowKeys.length; i++) {
                component.setRowKey(rowKeys[i]);
                if (!component.isRowAvailable()) {
                    break;
                }

                // Render Table2Column components.
                JSONArray cols = new JSONArray();
                Iterator kids = component.getTable2ColumnChildren();
                while (kids.hasNext()) {
                    Table2Column col = (Table2Column) kids.next();
                    if (!col.isRendered()) {
                        continue;
                    }
                    // Render Table2Column children.
                    Iterator grandKids = col.getChildren().iterator();
                    while (grandKids.hasNext()) {
                        WidgetUtilities.addProperties(cols,
                                WidgetUtilities.renderComponent(context, (UIComponent) grandKids.next()));
                    }
                }
                json.put(cols);
            }
            component.setRowKey(null); // Clean up.
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
