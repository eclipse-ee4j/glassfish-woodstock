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

package com.sun.webui.jsf.util;

import java.io.Serializable;
import jakarta.faces.component.StateHolder;
import jakarta.faces.context.FacesContext;
import jakarta.faces.el.EvaluationException;
import jakarta.faces.el.MethodBinding;
import jakarta.el.MethodExpression;
import jakarta.el.MethodInfo;
import jakarta.el.ELException;

/**
 * Wrap a MethodExpression instance and expose it as a MethodBinding.
 */
public final class MethodBindingMethodExpressionAdapter extends MethodBinding
        implements StateHolder, Serializable {

    /**
     * Serialization UID.
     */
    private static final long serialVersionUID = 7334926223014401689L;

    /**
     * Method expression.
     */
    private MethodExpression methodExpression = null;

    /**
     * Transient flag.
     */
    private boolean tranzient;

    /**
     * Create a new instance.
     */
    public MethodBindingMethodExpressionAdapter() {
    }

    /**
     * Create a new instance.
     * @param newMethodExpression method expression
     */
    public MethodBindingMethodExpressionAdapter(
            final MethodExpression newMethodExpression) {

        this.methodExpression = newMethodExpression;
    }

    @Override
    public Object invoke(final FacesContext context, final Object[] params)
            throws jakarta.faces.el.EvaluationException,
            jakarta.faces.el.MethodNotFoundException {

        assert (null != methodExpression);
        if (context == null) {
            throw new NullPointerException();
        }

        Object result = null;
        try {
            result = methodExpression.invoke(context.getELContext(),
                    params);
        } catch (jakarta.el.MethodNotFoundException e) {
            throw new jakarta.faces.el.MethodNotFoundException(e);
        } catch (jakarta.el.PropertyNotFoundException e) {
            throw new EvaluationException(e);
        } catch (ELException e) {
            // look for the root cause and pass that to the
            // ctor of EvaluationException
            Throwable cause = e.getCause();
            if (cause != null) {
                while (cause.getCause() != null) {
                    cause = cause.getCause();
                }
            } else {
                cause = e;
            }
            throw new EvaluationException(cause);
        } catch (NullPointerException e) {
            throw new jakarta.faces.el.MethodNotFoundException(e);
        }
        return result;
    }

    @Override
    public Class getType(final FacesContext context)
            throws jakarta.faces.el.MethodNotFoundException {

        assert (null != methodExpression);
        Class result = null;
        if (context == null) {
            throw new NullPointerException();
        }

        try {
            MethodInfo mi
                    = methodExpression.getMethodInfo(context.getELContext());
            result = mi.getReturnType();
        } catch (jakarta.el.PropertyNotFoundException e) {
            throw new jakarta.faces.el.MethodNotFoundException(e);
        } catch (jakarta.el.MethodNotFoundException e) {
            throw new jakarta.faces.el.MethodNotFoundException(e);
        } catch (ELException e) {
            throw new jakarta.faces.el.MethodNotFoundException(e);
        }
        return result;
    }

    @Override
    public String getExpressionString() {
        assert (null != methodExpression);
        return methodExpression.getExpressionString();
    }

    @Override
    public boolean equals(final Object other) {
        assert (null != methodExpression);
        boolean result = false;

        // don't bother even trying to compare, if we're not assignment
        // compatabile with "other"
        if (MethodExpression.class.isAssignableFrom(other.getClass())) {
            MethodExpression otherVE = (MethodExpression) other;
            result = this.getExpressionString()
                    .equals(otherVE.getExpressionString());
        }
        return result;
    }

    @Override
    public int hashCode() {
        assert (null != methodExpression);
        return methodExpression.hashCode();
    }

    @Override
    public boolean isTransient() {
        return this.tranzient;
    }

    @Override
    public void setTransient(final boolean newTranzient) {
        this.tranzient = newTranzient;
    }

    @Override
    public Object saveState(final FacesContext context) {
        Object result = null;
        if (!tranzient) {
            if (methodExpression instanceof StateHolder) {
                Object[] stateStruct = new Object[2];

                // save the actual state of our wrapped methodExpression
                stateStruct[0] = ((StateHolder) methodExpression)
                        .saveState(context);
                // save the class name of the methodExpression impl
                stateStruct[1] = methodExpression.getClass().getName();

                result = stateStruct;
            } else {
                result = methodExpression;
            }
        }
        return result;
    }

    @Override
    public void restoreState(final FacesContext context, final Object state) {
        // if we have state
        if (null == state) {
            return;
        }

        if (!(state instanceof MethodExpression)) {
            Object[] stateStruct = (Object[]) state;
            Object savedState = stateStruct[0];
            String className = stateStruct[1].toString();
            MethodExpression result = null;

            Class toRestoreClass = null;
            if (null != className) {
                try {
                    toRestoreClass = loadClass(className, this);
                } catch (ClassNotFoundException e) {
                    throw new IllegalStateException(e.getMessage());
                }

                if (null != toRestoreClass) {
                    try {
                        result = (MethodExpression) toRestoreClass
                                        .newInstance();
                    } catch (InstantiationException e) {
                        throw new IllegalStateException(e.getMessage());
                    } catch (IllegalAccessException a) {
                        throw new IllegalStateException(a.getMessage());
                    }
                }

                if (null != result && null != savedState) {
                    // don't need to check transient, since that was
                    // done on the saving side.
                    ((StateHolder) result).restoreState(context, savedState);
                }
                methodExpression = result;
            }
        } else {
            methodExpression = (MethodExpression) state;
        }
    }

    /**
     * Load the given class.
     * @param name class name
     * @param fallbackClass object to derive the fallback class from
     * @return Class
     * @throws ClassNotFoundException if the given class is not found
     */
    private static Class loadClass(final String name,
            final Object fallbackClass) throws ClassNotFoundException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = fallbackClass.getClass().getClassLoader();
        }
        return loader.loadClass(name);
    }
}
