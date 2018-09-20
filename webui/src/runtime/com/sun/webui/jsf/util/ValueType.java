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
 * ValueType.java
 *
 * Created on December 16, 2004, 8:19 AM
 */

package com.sun.webui.jsf.util;

/**
 * This class provides a typesafe enumeration of value types (see also 
 * ValueTypeEvaluator). The ValueTypeEvaluator and the
 * ValueTypes are helper classes for UIComponents which accept
 * value bindings that can be either single objects or a collection of 
 * objects (for example, an array). Typically, these components have
 * to process input differently depending on the type of the value 
 * object.
 *@see com.sun.webui.jsf.util.ValueTypeEvaluator
 *
 */
public class ValueType { 

    private String type; 
        
    /** Indicates that the value binding is an array (of primitives
     *  or of objects). */
    public static final ValueType ARRAY = new ValueType("array") ;
    /** Indicates that the value binding is assigneable to a
     * java.util.List. */
    public static final ValueType LIST = new ValueType("list");
    /** Indicates that the value binding is neither an array, nor
     * does it implement java.util.List. */
    public static final ValueType OBJECT = new ValueType("object"); 
   /** Indicates that the value binding is invalid. This is a place
    * holder, currently the ValueTypeEvaluator does not return this.
    * It should be used to help page authors identify what the valid
    * types are (e.g. java.util.List works, but java.util.Collection
    *does not). */
    public static final ValueType INVALID = new ValueType("invalid"); 
    /** Indicates that no value was specified for the component. */
    public static final ValueType NONE = new ValueType("none"); 

    private ValueType(String s) { 
	type = s; 
    } 
       
    /**
     * Get a String representation of the action
     * @return A String representation of the value type.
     */
    public String toString() {
	return type;
    }
}
