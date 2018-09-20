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

package com.sun.webui.jsf.event;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

/**
 * <p><strong>MethodExprValueChangeListener</strong> is a {@link ValueChangeListener} that
 * wraps a {@link MethodExpression}. When it receives a {@link ValueChangeEvent}, it executes
 * a method on an object identified by the {@link MethodExpression}.</p>
 * @author mbohm
 */
//FIXME add hashcode
public class MethodExprValueChangeListener implements ValueChangeListener, StateHolder {

    // ------------------------------------------------------ Instance Variables
    private MethodExpression methodExpression = null;
    private boolean isTransient;

    public MethodExprValueChangeListener() {
    }

    /**
     * <p>Construct a {@link ValueChangeListener} that contains a {@link MethodExpression}.</p>
     */
    public MethodExprValueChangeListener(MethodExpression methodExpression) {

        super();
        this.methodExpression = methodExpression;

    }

    /**
     * @throws NullPointerException {@inheritDoc}
     * @throws AbortProcessingException {@inheritDoc}
     */
    public void processValueChange(ValueChangeEvent valueChangeEvent) throws AbortProcessingException {

        if (valueChangeEvent == null) {
            throw new NullPointerException();
        }
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ELContext elContext = context.getELContext();
            methodExpression.invoke(elContext, new Object[]{valueChangeEvent});
        } catch (ELException ee) {
            throw new AbortProcessingException(ee.getMessage(), ee.getCause());
        }
    }

    public Object saveState(FacesContext context) {
        return new Object[]{methodExpression};
    }

    public void restoreState(FacesContext context, Object state) {
        methodExpression = (MethodExpression) ((Object[]) state)[0];
    }

    public boolean isTransient() {
        return isTransient;
    }

    public void setTransient(boolean newTransientValue) {
        isTransient = newTransientValue;
    }

    public MethodExpression getMethodExpression() {
        return methodExpression;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof MethodExprValueChangeListener)) {
            return false;
        }

        MethodExprValueChangeListener other = (MethodExprValueChangeListener) otherObject;
        MethodExpression otherMe = other.getMethodExpression();
        //methodExpression should not be null
        return methodExpression.equals(otherMe);
    }
}

