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

package com.sun.webui.jsf.example.util;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

public class ExampleUtilities {

    protected ExampleUtilities() {
        super();
    }

    /**
     * Helper method to set value expression property.
     *
     * @param component The UIComponent to set a value expression for.
     * @param name The name of the value expression property.
     * @param expression The expresion for the value expression.
     */
    public static void setValueExpression(UIComponent component, String name, 
            String expression) {
        if (expression == null) {
            return;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        component.setValueExpression(name, createValueExpression(
		context, expression, Object.class));
    }
    
    /**
     * Helper method to set a method expression property.
     * Create a method expression that returns String and has no
     * input paramaters.
     *   
     * @param component The UIComponent to set a value binding for.     
     * @param name The name of the method expression property
     * @param expression The expression to create.
     */
    public static void setMethodExpression(UIComponent component,
	    String name, String expression) {
	setMethodExpression(component, name, expression,
		Object.class, new Class[0]);
    }
    /**
     * Helper method to set a method expression property.
     *   
     * @param component The UIComponent to set a value binding for.     
     * @param name The name of the method expression property
     * @param expression The expression to create.
     */
    public static void setMethodExpression(UIComponent component,
	    String name, String expression, Class out, Class[] in) {
        if (expression == null) {
            return;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        component.getAttributes().put(name, 
	    createMethodExpression(context, expression, out, in));
    }

    public static MethodExpression createMethodExpression(
	    FacesContext context, String expr, Class out, Class[] in) {

	return context.getApplication().getExpressionFactory().
	    createMethodExpression(context.getELContext(), expr, out, in);
    }

    public static ValueExpression createValueExpression(
	    FacesContext context, String expr, Class value) {

	return context.getApplication().getExpressionFactory().
	    createValueExpression(context.getELContext(), expr, value);
    }
}
