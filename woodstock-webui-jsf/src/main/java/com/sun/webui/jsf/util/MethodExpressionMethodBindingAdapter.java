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
 * $Id: MethodExpressionMethodBindingAdapter.java,v 1.1 2007-02-16 01:50:26 bob_yennaco Exp $
 */

 
package com.sun.webui.jsf.util;

import javax.el.MethodExpression;
import javax.el.ELException;
import javax.el.ELContext;
import javax.el.MethodInfo;
import javax.faces.el.MethodBinding;
import javax.faces.context.FacesContext;
import javax.faces.component.StateHolder;

import java.io.Serializable;

/**
 * <p>Wrap a MethodBinding instance and expose it as a
 * MethodExpression.</p>
 */

 public class MethodExpressionMethodBindingAdapter extends MethodExpression implements Serializable, StateHolder {

    private static final long serialVersionUID = -1822420567946048452L;

    public MethodExpressionMethodBindingAdapter() {} // for StateHolder

    private MethodBinding binding = null;

    public MethodExpressionMethodBindingAdapter(MethodBinding binding) {
	assert(null != binding);
	this.binding = binding;
    }

    //
    // Methods from MethodExpression
    //

    private transient MethodInfo info = null;

    public MethodInfo getMethodInfo(ELContext context) throws ELException {
	assert(null != binding);

	if (null == info) {
	    FacesContext facesContext = (FacesContext) 
		context.getContext(FacesContext.class);
	    if (null != facesContext) {
		try {
		    info = new MethodInfo(null, binding.getType(facesContext), 
					  null);
		}
		catch (Exception e) {
		    throw new ELException(e);
		}
	    }
	}
		
	return info;
    }
    
    public Object invoke(ELContext context, Object[] params) throws ELException {
	assert(null != binding);

	Object result = null;
	FacesContext facesContext = (FacesContext) 
	    context.getContext(FacesContext.class);
	if (null != facesContext) {
	    try {
		result = binding.invoke(facesContext, params);
	    }
	    catch (Exception e) {
		throw new ELException(e);
	    }
	}
	return result;
    }

    public String getExpressionString() {
	assert(null != binding);
	return binding.getExpressionString();
	
    }

    public boolean isLiteralText() {
        assert (binding != null);
        String expr = binding.getExpressionString();
        return (!(expr.startsWith("#{")
            && expr.endsWith("}")));    
    }

    public boolean equals(Object other) {
	assert(null != binding);
	boolean result = false;
	
	// don't bother even trying to compare, if we're not assignment
	// compatabile with "other"
	if (MethodExpression.class.isAssignableFrom(other.getClass())) {
	    MethodExpression otherVE = (MethodExpression) other;
	    result = this.getExpressionString().equals(otherVE.getExpressionString());
	}
	return result;
    }

    public int hashCode() {
	assert(null != binding);

	return binding.hashCode();
    }
    
    public String getDelimiterSyntax() {
       // PENDING (visvan) Implementation
        return "";
    }
    
    // 
    // Methods from StateHolder
    //

    

    public Object saveState(FacesContext context) {
	Object result = null;
	if (!tranzient) {
	    if (binding instanceof StateHolder) {
		Object [] stateStruct = new Object[2];
		
		// save the actual state of our wrapped binding
		stateStruct[0] = ((StateHolder)binding).saveState(context);
		// save the class name of the binding impl
		stateStruct[1] = binding.getClass().getName();

		result = stateStruct;
	    }
	    else {
		result = binding;
	    }
	}

	return result;
    }

    public void restoreState(FacesContext context, Object state) {
	// if we have state
	if (null == state) {
	    return;
	}
	
	if (!(state instanceof MethodBinding)) {
	    Object [] stateStruct = (Object []) state;
	    Object savedState = stateStruct[0];
	    String className = stateStruct[1].toString();
	    MethodBinding result = null;
	    
	    Class toRestoreClass = null;
	    if (null != className) {
		try {
		    toRestoreClass = loadClass(className, this);
		}
		catch (ClassNotFoundException e) {
		    throw new IllegalStateException(e.getMessage());
		}
		
		if (null != toRestoreClass) {
		    try {
			result = 
			    (MethodBinding) toRestoreClass.newInstance();
		    }
		    catch (InstantiationException e) {
			throw new IllegalStateException(e.getMessage());
		    }
		    catch (IllegalAccessException a) {
			throw new IllegalStateException(a.getMessage());
		    }
		}
		
		if (null != result && null != savedState) {
		    // don't need to check transient, since that was
		    // done on the saving side.
		    ((StateHolder)result).restoreState(context, savedState);
		}
		binding = result;
	    }
	}
	else {
	    binding = (MethodBinding) state;
	}
    }

    private boolean tranzient = false;

    public boolean isTransient() {
	return tranzient;
    }

    public void setTransient(boolean newTransientMethod) {
	tranzient = newTransientMethod;
    }

    //
    // Helper methods for StateHolder
    //

    private static Class loadClass(String name, 
            Object fallbackClass) throws ClassNotFoundException {
        ClassLoader loader =
            Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = fallbackClass.getClass().getClassLoader();
        }
        return loader.loadClass(name);
    }
 

    // 
    // methods used by classes aware of this class's wrapper nature
    //

    public MethodBinding getWrapped() {
	return binding;
    }

}
