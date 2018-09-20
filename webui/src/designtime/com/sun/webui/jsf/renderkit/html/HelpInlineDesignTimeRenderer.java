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

import com.sun.webui.jsf.component.HelpInline;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.renderkit.html.HelpInlineRenderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.HelpInline} that 
 * sets a default text property when the property is null.
 *
 * @author gjmurphy
 */
public class HelpInlineDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    boolean isTextDefaulted;
    
    public HelpInlineDesignTimeRenderer() {
        super(new HelpInlineRenderer());
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component instanceof HelpInline && ((HelpInline)component).getText() == null) {
            HelpInline HelpInline = (HelpInline)component;
            HelpInline.setText(DesignMessageUtil.getMessage(HelpInlineDesignTimeRenderer.class, "helpInline.label"));
            HelpInline.setStyleClass(addStyleClass(HelpInline.getStyleClass(), UNINITITIALIZED_STYLE_CLASS));
            isTextDefaulted = true;
        }
        super.encodeBegin(context, component);
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        super.encodeEnd(context, component);
        if (isTextDefaulted) {
            HelpInline HelpInline = (HelpInline)component;
            HelpInline.setText(null);
            HelpInline.setStyleClass(removeStyleClass(HelpInline.getStyleClass(), UNINITITIALIZED_STYLE_CLASS));
            isTextDefaulted = false;
        }
    }
    
}
