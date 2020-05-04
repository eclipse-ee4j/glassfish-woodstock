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

import jakarta.el.MethodExpression;
import jakarta.el.ELException;
import jakarta.el.ELContext;
import jakarta.el.MethodInfo;
import jakarta.faces.el.MethodBinding;
import jakarta.faces.context.FacesContext;
import jakarta.faces.component.StateHolder;

import java.io.Serializable;
import jakarta.faces.el.EvaluationException;
import jakarta.faces.el.MethodNotFoundException;

/**
 * Wrap a MethodBinding instance and expose it as a MethodExpression.
 */
public final class MethodExpressionMethodBindingAdapter extends MethodExpression
        implements Serializable, StateHolder {

    /**
     * Serialization UID.
     */
    private static final long serialVersionUID = -1822420567946048452L;

    /**
     * Method binding.
     */
    private MethodBinding binding = null;

    /**
     * Method info.
     */
    private transient MethodInfo info = null;

    /**
     * Transient flag.
     */
    private boolean tranzient = false;

    /**
     * Create a new instance.
     */
    public MethodExpressionMethodBindingAdapter() {
    }

    /**
     * Create a new instance.
     * @param newBinding method binding
     */
    public MethodExpressionMethodBindingAdapter(
            final MethodBinding newBinding) {

        assert (null != newBinding);
        this.binding = newBinding;
    }

    @Override
    public MethodInfo getMethodInfo(final ELContext context)
            throws ELException {

        assert (null != binding);

        if (null == info) {
            FacesContext facesContext = (FacesContext) context
                    .getContext(FacesContext.class);
            if (null != facesContext) {
                try {
                    info = new MethodInfo(null, binding.getType(facesContext),
                            null);
                } catch (MethodNotFoundException e) {
                    throw new ELException(e);
                }
            }
        }
        return info;
    }

    @Override
    public Object invoke(final ELContext context, final Object[] params)
            throws ELException {

        assert (null != binding);

        Object result = null;
        FacesContext facesContext = (FacesContext) context
                .getContext(FacesContext.class);
        if (null != facesContext) {
            try {
                result = binding.invoke(facesContext, params);
            } catch (EvaluationException e) {
                throw new ELException(e);
            }
        }
        return result;
    }

    @Override
    public String getExpressionString() {
        assert (null != binding);
        return binding.getExpressionString();

    }

    @Override
    public boolean isLiteralText() {
        assert (binding != null);
        String expr = binding.getExpressionString();
        return (!(expr.startsWith("#{")
                && expr.endsWith("}")));
    }

    @Override
    public boolean equals(final Object other) {
        assert (null != binding);
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
        assert (null != binding);
        return binding.hashCode();
    }

    /**
     * Get the delimiter syntax.
     * @return String
     */
    public String getDelimiterSyntax() {
        return ""; // not implemented
    }

    @Override
    public Object saveState(final FacesContext context) {
        Object result = null;
        if (!tranzient) {
            if (binding instanceof StateHolder) {
                Object[] stateStruct = new Object[2];

                // save the actual state of our wrapped binding
                stateStruct[0] = ((StateHolder) binding).saveState(context);
                // save the class name of the binding impl
                stateStruct[1] = binding.getClass().getName();

                result = stateStruct;
            } else {
                result = binding;
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

        if (!(state instanceof MethodBinding)) {
            Object[] stateStruct = (Object[]) state;
            Object savedState = stateStruct[0];
            String className = stateStruct[1].toString();
            MethodBinding result = null;

            Class toRestoreClass = null;
            if (null != className) {
                try {
                    toRestoreClass = loadClass(className, this);
                } catch (ClassNotFoundException e) {
                    throw new IllegalStateException(e.getMessage());
                }

                if (null != toRestoreClass) {
                    try {
                        result
                                = (MethodBinding) toRestoreClass.newInstance();
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
                binding = result;
            }
        } else {
            binding = (MethodBinding) state;
        }
    }

    @Override
    public boolean isTransient() {
        return tranzient;
    }

    @Override
    public void setTransient(final boolean newTransientMethod) {
        tranzient = newTransientMethod;
    }

    /**
     * Get the wrapped method binding.
     *
     * @return MethodBinding
     */
    public MethodBinding getWrapped() {
        return binding;
    }

    /**
     * Load the given class.
     * @param name class name
     * @param fallbackClass object to derive the fallback from
     * @return Class
     * @throws ClassNotFoundException if the given class name is not found
     */
    private static Class loadClass(final String name,
            final Object fallbackClass) throws ClassNotFoundException {

        ClassLoader loader
                = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = fallbackClass.getClass().getClassLoader();
        }
        return loader.loadClass(name);
    }

}
