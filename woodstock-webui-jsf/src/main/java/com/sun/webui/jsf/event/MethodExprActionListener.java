/*
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
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
import javax.faces.context.FacesContext;
import javax.faces.component.StateHolder;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.StringWriter;
import java.io.PrintWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * <strong>MethodExprActionListener</strong> is an {@link ActionListener} that
 * wraps a {@link MethodExpression}. When it receives a {@link ActionEvent}, it
 * executes a method on an object identified by the
 * {@link MethodExpression}.
 */
public final class MethodExprActionListener
        implements ActionListener, StateHolder {

    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger("javax.faces.event", "javax.faces.LogStrings");

    /**
     * Method expression.
     */
    private MethodExpression methodExpression = null;

    /**
     * Transient flag.
     */
    private boolean isTransient;

    /**
     * Create a new instance.
     */
    public MethodExprActionListener() {
    }

    /**
     * Construct a {@link ValueChangeListener} that contains a
     * {@link MethodExpression}.
     * @param newMethodExpression method expression
     */
    public MethodExprActionListener(
            final MethodExpression newMethodExpression) {

        super();
        this.methodExpression = newMethodExpression;

    }

    @Override
    @SuppressWarnings("checkstyle:magicnumber")
    public void processAction(final ActionEvent actionEvent)
            throws AbortProcessingException {

        if (actionEvent == null) {
            throw new NullPointerException();
        }
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ELContext elContext = context.getELContext();
            methodExpression.invoke(elContext, new Object[]{actionEvent});
        } catch (ELException ee) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.log(Level.SEVERE,
                        "severe.event.exception_invoking_processaction",
                        new Object[]{
                            ee.getCause().getClass().getName(),
                            methodExpression.getExpressionString(),
                            actionEvent.getComponent().getId()
                        });
                StringWriter writer = new StringWriter(1024);
                ee.getCause().printStackTrace(new PrintWriter(writer));
                LOGGER.severe(writer.toString());
            }
            throw new AbortProcessingException(ee.getMessage(), ee.getCause());
        }
    }

    @Override
    public Object saveState(final FacesContext context) {
        return new Object[]{methodExpression};
    }

    @Override
    public void restoreState(final FacesContext context, final Object state) {
        methodExpression = (MethodExpression) ((Object[]) state)[0];
    }

    @Override
    public boolean isTransient() {
        return isTransient;
    }

    @Override
    public void setTransient(final boolean newTransientValue) {
        isTransient = newTransientValue;
    }

    /**
     * Get the method expression.
     * @return MethodExpression
     */
    public MethodExpression getMethodExpression() {
        return methodExpression;
    }

    @Override
    public boolean equals(final Object otherObject) {
        if (!(otherObject instanceof MethodExprActionListener)) {
            return false;
        }

        MethodExprActionListener other =
                (MethodExprActionListener) otherObject;
        MethodExpression otherMe = other.getMethodExpression();
        //methodExpression should not be null
        return methodExpression.equals(otherMe);
    }

    @Override
    @SuppressWarnings("checkstyle:magicnumber")
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash;
        if (this.methodExpression != null) {
            hash = hash + this.methodExpression.hashCode();
        }
        hash = 11 * hash;
        if (this.isTransient) {
            hash = hash + 1;
        }
        return hash;
    }
}
