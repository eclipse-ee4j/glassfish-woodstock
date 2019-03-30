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
 * MethodExprValidator.java
 *
 * Created on August 7, 2006, 1:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.webui.jsf.validator;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * <strong>MethodExprValidator</strong> is a {@link Validator} that 
 * wraps a {@link MethodExpression}, and it performs validation by executing
 * a method on an object identified by the {@link MethodExpression}.
 */
public class MethodExprValidator implements Validator, StateHolder {

    /**
     * Method expression.
     */
    private MethodExpression methodExpression = null;

    /**
     * Create a new instance.
     */
    public MethodExprValidator() {
        super();
    }

    /**
     * Construct a {@link Validator} that contains a {@link MethodExpression}.
     * @param methodExpression
     */
    public MethodExprValidator(MethodExpression methodExpression) {

        super();
        this.methodExpression = methodExpression;
    }

    @Override
    public void validate(FacesContext context,
                         UIComponent  component,
                         Object       value) throws ValidatorException {

        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
        if (value != null) {
            try {
                ELContext elContext = context.getELContext();
                methodExpression.invoke(elContext, new Object[] {
                    context,
                    component,
                    value
                });
            } catch (ELException ee) {
                Throwable e = ee.getCause();
                if (e instanceof ValidatorException) {
                    throw (ValidatorException) e;
                }
                FacesMessage message = new FacesMessage(ee.getMessage());
                throw new ValidatorException(message, ee.getCause());
            }
        }

    }

    @Override
    public Object saveState(FacesContext context) {

        Object values[] = new Object[1];
        values[0] = methodExpression;
        return (values);
    }

    @Override
    public void restoreState(FacesContext context, Object state) {

        Object values[] = (Object[]) state;
        methodExpression = (MethodExpression)values[0];
    }

    private boolean transientValue = false;

    @Override
    public boolean isTransient() {
        return (this.transientValue);
    }

    @Override
    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }

    public MethodExpression getMethodExpression() {
        return methodExpression;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (! (otherObject instanceof MethodExprValidator)) {
            return false;
        }
        MethodExprValidator other = (MethodExprValidator)otherObject;
        MethodExpression otherMe = other.getMethodExpression();
        return methodExpression.equals(otherMe);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.methodExpression != null
                ? this.methodExpression.hashCode() : 0);
        return hash;
    }
}
