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

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;

/**
 * A delegating renderer for components that extend 
 * {@link UICommand}, which does two things. If
 * the value property is not set, temporarily sets the value and style to
 * cause the component to be rendered with a default "shadow label". If the
 * value property is bound, but the value is null or the empty string,
 * temporarily sets the value to a "dummy string" that is keyed to the type
 * of the value.
 *
 * @author gjmurphy
 */
public abstract class ActionSourceDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    protected static String STYLE_CLASS_PROP = "styleClass"; //NOI18N
    protected static String VALUE_PROP = "value"; //NOI18N
    
    boolean isTextSet;
    boolean isStyleSet;
    
    public ActionSourceDesignTimeRenderer(Renderer renderer) {
        super(renderer);
    }
    
    /**
     * Returns a display string to set as the component's value if shadowed
     * text is required.
     */
    protected abstract String getShadowText();
    
    /**
     * Determines if shadowed text is required. Default implementation is 
     * simply to check for a null value.
     */
    protected boolean needsShadowText(UICommand component) {
        return component.getValue() == null;
    }
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (UICommand.class.isAssignableFrom(component.getClass())) {
            ValueBinding valueBinding = component.getValueBinding(VALUE_PROP); //NOI18N
            UICommand command = (UICommand) component;
            Object value = command.getValue();
            if (valueBinding != null && (value == null || value.toString().length() == 0)) {
                Object dummyValue = getDummyData(context, valueBinding);
                command.setValue(dummyValue);
                isTextSet = true;
            } else if (needsShadowText(command)) {
                command.setValue(getShadowText());
                String styleClass = (String) component.getAttributes().get(STYLE_CLASS_PROP);
                component.getAttributes().put(STYLE_CLASS_PROP, addStyleClass(styleClass, UNINITITIALIZED_STYLE_CLASS));
                isTextSet = true;
                isStyleSet = true;
            }
        }
        super.encodeBegin(context, component);
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        super.encodeEnd(context, component);
        if (isTextSet) {
            UICommand command = (UICommand)component;
            command.setValue(null);
            isTextSet = false;
        }
        if (isStyleSet) {
            String styleClass = (String) component.getAttributes().get(STYLE_CLASS_PROP);
            component.getAttributes().put(STYLE_CLASS_PROP, removeStyleClass(styleClass, UNINITITIALIZED_STYLE_CLASS));
            isStyleSet = false;
        }
    }
    
}
