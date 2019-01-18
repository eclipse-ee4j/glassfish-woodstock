/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2019 Payara Services Ltd.
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

package com.sun.faces.mirror;

import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;

/**
 * An interface that defines the basic metadata available for a class with one
 * or more properties. This may be a component class or a non-component base class,
 * either in the current compilation unit, or in an external library.
 *
 * @author gjmurphy
 */
public abstract class ClassInfo implements Element {
    
    /**
     * Returns the name of this class.
     */
    public abstract String getClassName();
    
    /**
     * Returns the name of this class' package.
     */
    public abstract String getPackageName();
    
    /**
     * Returns the info for this class's super class. If this class extends a 
     * class which provides no properties, then this method should return null.
     *
     * @return Value of property superClassInfo.
     */
    public abstract ClassInfo getSuperClassInfo();

    /**
     * Returns a map in which keys are property names (see {@link PropertyInfo#getName}), 
     * and values are instance of {@link PropertyInfo}. The map contains all
     * properties explicitly declared in this class (which may or may not override
     * properties in the super class), as well as all properties declared in any
     * interface which this class implements.
     *
     * @return Value of property propertyInfoMap.
     */
    public abstract Map<String,PropertyInfo> getPropertyInfoMap();

    /**
     * Returns a map in which keys are event names (see {@link EventInfo#getName}), 
     * and values are instance of {@link EventInfo}. The map contains all
     * events explicitly declared in this class, (which may or may not override
     * events in the super class), as well as all events declared in any interface
     * which this class implements.
     *
     * @return Value of property eventInfoMap.
     */
    public abstract Map<String,EventInfo> getEventInfoMap();
    
    /**
     * Returns the default property for this class, or null if the class has no
     * default property. If a default property was specified explicitly as part of a
     * class's annotations, or implicitly via introspection, it is returned.
     * Otherwise, if this class is a Faces component class and implements 
     * {@link javax.faces.component.ValueHolder}, the {@code value} property is
     * returned. Otherwise null is returned.
     */
    public abstract PropertyInfo getDefaultPropertyInfo();
    
    /**
     * Returns the default event for this class, or null if the class has no
     * default event. If a default event was specified explicitly as part of a
     * class's annotations, or implicitly via introspection, it is returned.
     * Otherwise, if this class is a Faces component class and implements 
     * {@link javax.faces.component.ValueHolder}, the {@code valueChange} event
     * is returned if it is defined; if it implements (@link javax.faces.component.ActionSource},
     * the {@code action} event is returned if it is defined.
     */
    public abstract EventInfo getDefaultEventInfo();
    
    /**
     * Returns the fully qualified name of this class.
     */
    public abstract Name getQualifiedName();
    
    /**
     * Returns true if the fully qualified name specified belongs to a class or interface
     * that is a superclass or superinterface of this class.
     */
    public abstract boolean isAssignableTo(String qualifiedClassName);
    
    /**
     * Returns a unique, generated key for the property specified, suitable for
     * use as a key in a properties resource bundle file, if the property corresponds
     * to a localizable annotation element. For example, the property {@link DeclaredComponentInfo#getDisplayName})
     * corresponds to the localizable annotation element {@link com.sun.faces.Component#displayName}
     * so a unique key for it may be generated by calling
     * <pre>
     *    String key = declaredComponentInfo.getKey("displayName");
     * </pre>
     * If the specified property does not correspond to a localizable annotation, 
     * returns null. If the specified property does not exist, throws {@link
     * java.lang.NoSuchMethodException}. Only properties of type {@link java.lang.String}
     * are supported.
     */
    public String getKey(String propertyName) throws NoSuchMethodException {
        String methodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        this.getClass().getMethod(methodName);
        String baseName = this.getClassName();
        return baseName + "_" + propertyName;
    }
    
    /**
     * Returns a set of the names of all public methods accessible through this class,
     * whether declared by it, or inherited.
     */
    abstract Set<String> getMethodNameSet();
    
}
