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

import com.sun.webui.jsf.component.util.DesignMessageUtil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import com.sun.webui.jsf.component.PanelLayout;

/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.PanelLayout} that
 * makes the component appear correctly at designtime when it has no children.
 *
 * @author gjmurphy
 */
public class PanelLayoutDesignTimeRenderer extends PanelDesignTimeRenderer {

    public PanelLayoutDesignTimeRenderer() {
        super(new PanelLayoutRenderer());
    }
    
    protected String getShadowText(UIComponent component) {
        return DesignMessageUtil.getMessage(PanelLayoutDesignTimeRenderer.class, "panelLayout.label");
    }

    protected String getContainerElementName(UIComponent component) {
        return "div"; //NOI18N
    }

    protected String getStyle(UIComponent component) {
        if (((PanelLayout) component).getPanelLayout().equals(PanelLayout.GRID_LAYOUT))
            return "position: relative; -rave-layout: grid";
        return null;
    }
    
}
