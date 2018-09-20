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

import com.sun.webui.jsf.component.Anchor;
import com.sun.webui.jsf.component.PropertySheetSection;
import com.sun.webui.jsf.component.SkipHyperlink;
import com.sun.webui.jsf.util.RenderingUtilities;
import java.io.IOException;
import java.net.URL;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.Anchor} that 
 * outputs an HTML named anchor, and an image with the anchor icon, only if
 * the anchor is not being used as an unparented helper component and it is
 * not a child of the utility SkipHyperlink.
 *
 * @author gjmurphy
 */
public class AnchorDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    static final String ANCHOR_ICON = 
            "/com/sun/webui/jsf/component/Anchor_C16.png"; //NOI18N
    
    boolean isTextDefaulted;
    
    public AnchorDesignTimeRenderer() {
        super(new AnchorRenderer());
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        UIComponent parent = component.getParent();
        if (component instanceof Anchor && parent != null && !SkipHyperlink.class.isAssignableFrom(parent.getClass())
                && !PropertySheetSection.class.isAssignableFrom(parent.getClass())) { 
            Anchor anchor = (Anchor) component; 
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("a", anchor); //NOI18N
            String id = anchor.getId();
            writer.writeAttribute("id", id, "id"); //NOI18N
            String style = anchor.getStyle();
            if (style != null)
                writer.writeAttribute("style", style, null); //NOI18N
            String styleClass = anchor.getStyleClass();
            if (styleClass != null)
                RenderingUtilities.renderStyleClass(context, writer, component, null);
            writer.writeAttribute("name", id, null); //NOI18N
            writer.startElement("img", anchor); //NOI18N
            URL url = this.getClass().getResource(ANCHOR_ICON); // NOI18N
            writer.writeURIAttribute("src", url, null); //NOI18N
            writer.endElement("img"); // NOI18N
            writer.endElement("a"); //NOI18N
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
    }

    public void encodeChildren(FacesContext context, UIComponent component) {
    }
    
}
