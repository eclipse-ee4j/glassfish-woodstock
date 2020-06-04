/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

/**
 * Common example utilities.
 */
public final class ExampleUtilities {

    /**
     * Cannot be instanciated.
     */
    private ExampleUtilities() {
        super();
    }

    /**
     * Helper method to set value expression property.
     *
     * @param component The UIComponent to set a value expression for.
     * @param name The name of the value expression property.
     * @param expression The expression for the value expression.
     */
    public static void setValueExpression(final UIComponent component,
            final String name, final String expression) {

        if (expression == null) {
            return;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        component.setValueExpression(name, createValueExpression(
                context, expression, Object.class));
    }

    /**
     * Helper method to set a method expression property. Create a method
     * expression that returns String and has no input parameters.
     *
     * @param component The UIComponent to set a value binding for.
     * @param name The name of the method expression property
     * @param expression The expression to create.
     */
    public static void setMethodExpression(final UIComponent component,
            final String name, final String expression) {

        setMethodExpression(component, name, expression,
                Object.class, new Class[0]);
    }

    /**
     * Helper method to set a method expression property.
     *
     * @param component The UIComponent to set a value binding for.
     * @param name The name of the method expression property
     * @param expression The expression to create.
     * @param out return type
     * @param in parameter type
     */
    public static void setMethodExpression(final UIComponent component,
            final String name, final String expression, final Class out,
            final Class[] in) {

        if (expression == null) {
            return;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        component.getAttributes().put(name,
                createMethodExpression(context, expression, out, in));
    }

    /**
     * Helper method to create a method expression.
     *
     * @param context faces context
     * @param expr expression to create
     * @param out return type
     * @param in parameter type
     * @return MethodExpression
     */
    public static MethodExpression createMethodExpression(
            final FacesContext context, final String expr, final Class out,
            final Class[] in) {

        return context.getApplication().getExpressionFactory().
                createMethodExpression(context.getELContext(), expr, out, in);
    }

    /**
     * Helper method to create a value expression.
     * @param context faces context
     * @param expr expression to create
     * @param value value type
     * @return ValueExpression
     */
    public static ValueExpression createValueExpression(
            final FacesContext context, final String expr, final Class value) {

        return context.getApplication().getExpressionFactory().
                createValueExpression(context.getELContext(), expr, value);
    }
}
