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

/*
 * MastFooterRenderer.java
 *
 * Created on September 14, 2006, 6:09 PM
 *
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.MastFooter;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;

/**
 *
 * @author deep
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.MastFooter"))
public class MastFooterRenderer extends AbstractRenderer {

    /** Creates a new instance of MastheadRenderer */
    public MastFooterRenderer() {
    }

    /**
     * All of the necessary Masthead rendering is done here.
     * 
     * @param context The current FacesContext
     * @param component The ImageComponent object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        MastFooter footer = (MastFooter) component;
        Theme theme = ThemeUtilities.getTheme(context);

        writer.startElement(HTMLElements.DIV, footer);
        String styleClass = footer.getStyleClass();
        if (styleClass != null && styleClass.length() > 0) {
            writer.writeAttribute(HTMLAttributes.CLASS, styleClass, null);
        } else {
            writer.writeAttribute(HTMLAttributes.CLASS,
                    theme.getStyleClass(ThemeStyles.MASTHEAD_FOOTER),
                    null);
        }

        String style = footer.getStyle();
        if (style != null && style.length() > 0) {
            writer.writeAttribute(HTMLAttributes.STYLE, style, null);
        }

        writer.writeAttribute(HTMLAttributes.ID, footer.getClientId(context),
                null);
        writer.writeAttribute(HTMLAttributes.ALIGN, "right", null); //NOI18N
        renderCorporateImage(context, footer, writer, theme);
        writer.endElement(HTMLElements.DIV); // NOI18N

    }

    /**
     * Render the corporate logo as an image in the footer associated with the
     * masthead.
     *
     * @param context The current FacesContext
     * @param masthead The Masthead component
     * @param writer The current ResponseWriter
     * @param theme The current Theme
     */
    protected void renderCorporateImage(FacesContext context,
            MastFooter footer, ResponseWriter writer, Theme theme)
            throws IOException {

        UIComponent corporateFacet =
                footer.getFacet("corporateImage"); // NOI18N
        if (corporateFacet != null) {
            RenderingUtilities.renderComponent(corporateFacet, context);
            return;
        }

        // no facet specified
        // use the values specified for component props if set.
        //
        String imageAttr = footer.getCorporateImageURL();
        if (imageAttr != null && imageAttr.trim().length() != 0) {
            ImageComponent image = new ImageComponent();
            image.setUrl(imageAttr);
            imageAttr = footer.getCorporateImageDescription();
            if (imageAttr != null) {
                image.setAlt(imageAttr);
            }
            int dim = footer.getCorporateImageHeight();
            if (dim != 0) {
                image.setHeight(dim);
            }
            dim = footer.getCorporateImageWidth();
            if (dim != 0) {
                image.setWidth(dim);
            }
            RenderingUtilities.renderComponent(image, context);
            return;
        }

        // use default Theme corporate image
        // First see if there is an image path. If there is
        // call ThemeUtilities.getIcon to an Icon.
        //
        try {
            String imagePath =
                    theme.getImagePath(ThemeImages.MASTHEAD_CORPNAME);
            if (imagePath == null) {
                return;
            }
            Icon icon = ThemeUtilities.getIcon(theme,
                    ThemeImages.MASTHEAD_CORPNAME);
            icon.setId(footer.getId() + "_corporateImage"); // NOI18N
            RenderingUtilities.renderComponent(icon, context);
        } catch (Exception e) {
            // Don't care.
        }
    }
}
