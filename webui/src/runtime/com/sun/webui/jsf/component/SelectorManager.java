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

import javax.faces.context.FacesContext;

/**
 * For internal use only.
 */
public interface SelectorManager {

    /**
     * JSF standard method from UIComponent
     * @param context The FacesContext for the request
     * @return The client id, also the JavaScript element id
     */
    public String getClientId(FacesContext context);

    /**
     * JSF standard method from UIComponent
     * @return true if the component is disabled
     */
    public boolean isDisabled();

    /**
     * Get the JS onchange event handler
     * @return A string representing the JS event handler
     */
    public String getOnChange();

    /**
     * Get the tab index for the component
     * @return the tabindex
     */
    public int getTabIndex();

    /**
     * Returns true if the component allows multiple selections
     * @return true if the component allows multiple selections
     */
    public boolean isMultiple();

    /**
     * Returns true if the component is readonly
     * @return true if the component is readonly
     */
    public boolean isReadOnly();

    public String getStyle();

    public String getStyleClass();
}
