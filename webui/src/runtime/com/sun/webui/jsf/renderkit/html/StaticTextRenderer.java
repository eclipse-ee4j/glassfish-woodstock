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
 * $Id: StaticTextRenderer.java,v 1.1.20.1 2009-12-29 04:52:44 jyeary Exp $
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.util.ConversionUtilities;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * <p>Renderer for a {@link StaticText} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.StaticText"))
public class StaticTextRenderer extends AbstractRenderer {


    // ======================================================== Static Variables
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] = {"onClick", "onDblClick", "onMouseUp", //NOI18N
        "onMouseDown", "onMouseMove", "onMouseOut", "onMouseOver"}; //NOI18N


    // -------------------------------------------------------- Renderer Methods
    /**
     * <p>Render the appropriate element start, depending on whether the
     * <code>for</code> property is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component StaticText component
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        writer.writeText("\n", null); //NOI18N
        writer.startElement("span", component);
    }

    /**
     * <p>Render the appropriate element attributes, followed by the
     * label content, depending on whether the <code>for</code> property
     * is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component StaticText component
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        StaticText st = (StaticText) component;
        addCoreAttributes(context, component, writer, null);
        addStringAttributes(context, component, writer, stringAttributes);
        if (st.getToolTip() != null) {
            writer.writeAttribute("title", st.getToolTip(), null);
        }
    }

    /**
     * <p>Render the appropriate element end, depending on whether the
     * <code>for</code> property is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        StaticText staticText = (StaticText) component;

        String currentValue =
                ConversionUtilities.convertValueToString(component,
                staticText.getText());

        String style = staticText.getStyle();
        String styleClass = staticText.getStyleClass();

        if (currentValue != null) {
            java.util.ArrayList parameterList = new ArrayList();

            // get UIParameter children...

            java.util.Iterator kids = component.getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();

                //PENDING(rogerk) ignore if child is not UIParameter?

                if (!(kid instanceof UIParameter)) {
                    continue;
                }

                parameterList.add(((UIParameter) kid).getValue());
            }

            // If at least one substitution parameter was specified,
            // use the string as a MessageFormat instance.
            String message = null;
            if (parameterList.size() > 0) {
                message = MessageFormat.format(currentValue, parameterList.toArray(new Object[parameterList.size()]));
            } else {
                message = currentValue;
            }

            if (message != null) {
                if (staticText.isEscape()) {
                    writer.writeText(message, "value");
                } else {
                    writer.write(message);
                }
            }
        }
        writer.endElement("span");
    }
    // --------------------------------------------------------- Private Methods
}
