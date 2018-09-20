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
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.el.EvaluationException;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;

/**
 * Stuff. No fluff.
 *
 * @author gjmurphy
 */
public class AbstractDesignTimeRenderer extends Renderer {
    
    protected static String UNINITITIALIZED_STYLE_CLASS = "rave-uninitialized-text";
    protected static String BORDER_STYLE_CLASS = "rave-design-border";
    
    // Delagatee renderer
    protected Renderer renderer;
    
    public AbstractDesignTimeRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue)
            throws ConverterException {
        return this.renderer.getConvertedValue(context, component, submittedValue);
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        this.renderer.encodeEnd(context, component);
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        this.renderer.encodeChildren(context, component);
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        this.renderer.encodeBegin(context, component);
    }

    public void decode(FacesContext context, UIComponent component) {
        this.renderer.decode(context, component);
    }

    public String convertClientId(FacesContext context, String clientId) {
        return this.renderer.convertClientId(context, clientId);
    }

    public boolean getRendersChildren() {
        return this.renderer.getRendersChildren();
    }
    
    protected static String addStyleClass(String value, String styleClass) {
        if (value == null) {
            return styleClass;
        } else if (value.indexOf(styleClass) >= 0) {
            return value;
        } else {
            return value + " " + styleClass;
        }
    }
    
    protected static String removeStyleClass(String value, String styleClass) {
        if (value == null || value.indexOf(styleClass) == -1)
            return value;
        int i = value.indexOf(styleClass);
        while (i > 0 && Character.isSpaceChar(value.charAt(i)))
            i--;
        return value.substring(0, i) + value.substring(i + styleClass.length());
    }
    
    protected static Object getDummyData(FacesContext context, ValueBinding vb) {
        Class type = null;
        try {
            type = vb.getType(context);
        } catch (EvaluationException e) {
            type = Object.class;
        }
        return getDummyData(type);
    }

    protected static Object getDummyData(Class clazz) {
        if (clazz.equals(Object.class))
            return null;
        return com.sun.data.provider.impl.AbstractDataProvider.getFakeData(clazz);
    }
    
}
