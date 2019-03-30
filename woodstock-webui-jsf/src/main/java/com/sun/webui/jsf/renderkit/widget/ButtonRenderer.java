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

package com.sun.webui.jsf.renderkit.widget;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.Button;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.json.JsonObjectBuilder;
import java.io.IOException;

import static com.sun.webui.jsf.util.ConversionUtilities.convertValueToString;
import static com.sun.webui.jsf.util.JsonUtilities.JSON_BUILDER_FACTORY;

/**
 * This class renders Table components.
 */
@Renderer(@Renderer.Renders(rendererType = "com.sun.webui.jsf.widget.Button",
componentFamily = "com.sun.webui.jsf.Button"))
public final class ButtonRenderer extends RendererBase {

    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String ATTRIBUTES[] = {
        "alt",
        "align",
        "dir",
        "lang",
        "onBlur",
        "onClick",
        "onDblClick",
        "onFocus",
        "onKeyDown",
        "onKeyPress",
        "onKeyUp",
        "onMouseDown",
        "onMouseOut",
        "onMouseOver",
        "onMouseUp",
        "onMouseMove",
        "style",
        "tabIndex"
    };

    @Override
    public void decode(FacesContext context, UIComponent component) {
        // Enforce NPE requirements in the Javadocs
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        Button button = (Button) component;

        // Do not process disabled or reset components.
        if (button.isReset()) {
            return;
        }

        // Was our command the one that caused this submission?
        String clientId = button.getClientId(context);
        Map map = context.getExternalContext().getRequestParameterMap();

        if (map.containsKey(clientId) ||
                (map.containsKey(clientId + ".x")
                && map.containsKey(clientId + ".y"))) {
            button.queueEvent(new ActionEvent(button));
        }
    }

    @Override
    protected String[] getModuleNames(UIComponent component) {
        return new String[] {
            "button"
        };
    }

    @Override
    protected JsonObjectBuilder getProperties(FacesContext context,
            UIComponent component) throws IOException {

        Button button = (Button) component;
        JsonObjectBuilder jsonBuilder = JSON_BUILDER_FACTORY
                .createObjectBuilder()
                .add("className", button.getStyleClass())
                .add("disabled", button.isDisabled())
                .add("mini", button.isMini())
                .add("name", button.getClientId(context))
                .add("primary", button.isPrimary())
                .add("title", button.getToolTip())
                .add("visible", button.isVisible());

        if (button.isReset()) {
            jsonBuilder.add("type", "reset");

        } else {
            jsonBuilder.add("type", "submit");
        }

        // Get the textual label of the button.
        String text = convertValueToString(button, button.getText());

        // Pad the text, if needed.
        if (text != null && text.trim().length() > 0) {
            // Note: This code appears in the UI guidelines, but it may have been
            // for Netscape 4.x. We may be able to do this with styles instead.
            if (!button.isNoTextPadding()) {
                if (text.trim().length() <= 3) {
                    text = "  " + text + "  ";
                } else if (text.trim().length() == 4) {
                    text = " " + text + " ";
                }
            }
            // This is button label.
            jsonBuilder.add("contents", text);
        }

        // Add core and attribute properties.
        addAttributeProperties(ATTRIBUTES, component, jsonBuilder);
        return jsonBuilder;
    }

    @Override
    protected void renderNestedContent(FacesContext context,
            UIComponent component) throws IOException {
    }
}
