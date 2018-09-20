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
import com.sun.webui.jsf.util.JavaScriptUtilities;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Component that represents a table.
 */
@Component(type = "com.sun.webui.jsf.Table2",
family = "com.sun.webui.jsf.Table2",
tagRendererType = "com.sun.webui.jsf.widget.Table2",
displayName = "Table2", tagName = "table2", isTag = false) // Remove isTag to run
public class Table2 extends Table implements NamingContainer, Widget {

    /** The facet name for the actions area. */
    public static final String ACTIONS_FACET = "actions"; //NOI18N 
    /** The facet name for the title area. */
    public static final String TITLE_FACET = "title"; //NOI18N

    // A List containing Table2RowGroup children. 
    private List table2RowGroupChildren = null;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Base methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public Table2() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Table2");
    }

    public FacesContext getContext() {
        return getFacesContext();
    }

    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.Table2";
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @return The type of widget represented by this component.
     */
    public String getWidgetType() {
        return JavaScriptUtilities.getNamespace("table2");
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Child methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /** 
     * Get an Iterator over the Table2RowGroup children found for this
     * component. 
     * 
     * @return An Iterator over the Table2RowGroup children. 
     */
    public Iterator getTable2RowGroupChildren() {
        // Get TableRowGroup children. 
        if (table2RowGroupChildren == null) {
            table2RowGroupChildren = new ArrayList();
            Iterator kids = getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                if ((kid instanceof Table2RowGroup)) {
                    table2RowGroupChildren.add(kid);
                }
            }
        }
        return table2RowGroupChildren.iterator();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Alternative HTML template to be used by this component.
     */
    @Property(name = "htmlTemplate", displayName = "HTML Template", category = "Appearance")
    private String htmlTemplate = null;

    /**
     * Get alternative HTML template to be used by this component.
     */
    public String getHtmlTemplate() {
        if (this.htmlTemplate != null) {
            return this.htmlTemplate;
        }
        ValueExpression _vb = getValueExpression("htmlTemplate");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set alternative HTML template to be used by this component.
     */
    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }
}
