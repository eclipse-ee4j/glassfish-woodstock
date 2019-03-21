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
import javax.faces.context.FacesContext;

/**
 * The Icon component is used to display a clickable icon image from the current
 * theme in the rendered HTML page.
 */
@Component(type = "com.sun.webui.jsf.IconHyperlink", family = "com.sun.webui.jsf.IconHyperlink",
displayName = "Hyperlink", isTag = false,
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_icon_hyperlink",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_icon_hyperlink_props")
public class IconHyperlink extends ImageHyperlink {

    /**
     * <p>Construct a new <code>IconHyperlink</code>.</p>
     */
    public IconHyperlink() {
        super();
        setRendererType("com.sun.webui.jsf.IconHyperlink");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.IconHyperlink";
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[1];
        _values[0] = super.saveState(_context);
        return _values;
    }
}
