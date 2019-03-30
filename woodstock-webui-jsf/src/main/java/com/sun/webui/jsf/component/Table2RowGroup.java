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

package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Component that represents a group of table rows.
 */
@Component(type = "com.sun.webui.jsf.Table2RowGroup",
family = "com.sun.webui.jsf.Table2RowGroup",
tagRendererType = "com.sun.webui.jsf.widget.Table2RowGroup",
displayName = "Table2RowGroup", tagName = "table2RowGroup", isTag = false) // Remove isTag to run
public final class Table2RowGroup extends TableRowGroup
        implements NamingContainer, Widget {

    /**
     * A List containing Table2Column children. 
     */
    private List<Table2Column> table2ColumnChildren = null;

    public Table2RowGroup() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Table2RowGroup");
    }

    public FacesContext getContext() {
        return getFacesContext();
    }

    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.Table2RowGroup";
    }

    @Override
    public String getRendererType() {
        if (AsyncResponse.isAjaxRequest()) {
            return "com.sun.webui.jsf.ajax.Table2RowGroup";
        } else {
            return super.getRendererType();
        }
    }

    @Override
    public String getWidgetType() {
        return "table2RowGroup";
    }

    /**
     * Get an Iterator over the Table2Column children found for
     * this component.
     *
     * @return An Iterator over the Table2Column children.
     */
    public Iterator getTable2ColumnChildren() {
        if (table2ColumnChildren == null) {
            table2ColumnChildren = new ArrayList<Table2Column>();
            for (UIComponent kid : getChildren()) {
                if ((kid instanceof Table2Column)) {
                    table2ColumnChildren.add((Table2Column)kid);
                }
            }
        }
        return table2ColumnChildren.iterator();
    }

    /**
     * Get the number of rows to be displayed for a paginated table.
     * <p>
     * Note: UI guidelines recommend a default value of 25 rows per page.
     * </p>
     * @return The number of rows to be displayed for a paginated table.
     */
    @Override
    public int getRows() {
        setPaginated(true);
        return Math.max(1, super.getRows());
    }

    /**
     * Flag indicating to turn off default Ajax functionality.
     * Set {@code ajaxify} to false when providing a different Ajax
     * implementation.
     */
    @Property(name = "ajaxify", displayName = "Ajaxify", category = "Javascript")
    private boolean ajaxify = true;
    private boolean ajaxify_set = false;

    /**
     * Test if default Ajax functionality should be turned off.
     * @return 
     */
    public boolean isAjaxify() {
        if (this.ajaxify_set) {
            return this.ajaxify;
        }
        ValueExpression _vb = getValueExpression("ajaxify");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result);
            }
        }
        return true;
    }

    /**
     * Set flag indicating to turn off default Ajax functionality.
     * @param ajaxify
     */
    public void setAjaxify(boolean ajaxify) {
        this.ajaxify = ajaxify;
        this.ajaxify_set = true;
    }
}
