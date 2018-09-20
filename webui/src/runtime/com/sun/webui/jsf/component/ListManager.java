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

import java.util.Iterator;
import javax.faces.context.FacesContext;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;

/**
 * This interface is used to allow both list components which
 * allow the user to select from a set of Options (e.g. Listbox, 
 * AddRemove) and list components which allow the user to edit 
 * a list to use the same renderer.
 *
 * @author avk
 */
//TODO: consider making this a base class instead. There
// is code which is shared between Selectors and Editable List,
// Orderable List. (getConvertedValue, getValueAsString, ...)
public interface ListManager extends EditableValueHolder, SelectorManager,
        ComplexComponent {

    /**
     * Get an Iterator of the items to display. The items are of
     * type <code>com.sun.webui.jsf.model.list.ListItem</code> and
     * are an abstraction over different types of actual data
     * to be used by the renderer
     * @param rulerAtEnd If this attribute is set to true, the iterator will
     * contain, as the last item, a disabled list option with a blank label whose
     * sole function is to guarantee that the list stays the same size
     * @return An Iterator of  <code>com.sun.webui.jsf.model.list.ListItem</code>
     */
    public Iterator getListItems(FacesContext context, boolean rulerAtEnd);

    /**
     * Retrieves the tooltip for the list
     * @return A string with the text for the tool tip
     */
    public String getToolTip();

    /**
     * Get the value of the component as a String array. The array consists
     * of the converted value of each list item is shown.
     * @param context The FacesContext of the request
     * @return A string representation of the value
     */
    public String[] getValueAsStringArray(FacesContext context);

    /**
     * Get the number of rows to display (the size of the HTML 
     * select element)
     * @return The size of the list
     */
    public int getRows();

    /**
     * Returns a UIComponent used to display the readonly value for this 
     * component
     * @return a UIComponent used to display the readonly value for this 
     * component
     */
    public UIComponent getReadOnlyValueComponent();

    public boolean isVisible();

    // return true if the select element associated with the component 
    // represents the value in the HTTP request. 
    public boolean mainListSubmits();
}
