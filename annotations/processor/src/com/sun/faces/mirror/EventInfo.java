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

package com.sun.faces.mirror;

import java.util.regex.Pattern;

/**
 * A base class that defines the basic metadata available for an event set, whether
 * it belongs to a class declared in the current compilation unit, or to a class
 * in a dependant library.
 *
 * @author gjmurphy
 */
public abstract class EventInfo extends FeatureInfo {

    /**
     * Returns the simple name of the method used to add event listeners.
     */
    public abstract String getAddListenerMethodName();

    /**
     * Returns the simple name of the method used to remove event listeners.
     */
    public abstract String getRemoveListenerMethodName();

    /**
     * Returns the simple name of the method used to get event listeners. May be
     * null if there is no such method.
     */
    public abstract String getGetListenersMethodName();
    
    /**
     * Returns the signature of the singleton method defined by this event's listener
     * class or interface.
     */
    public abstract String getListenerMethodSignature();
    
    /**
     * Returns the simple name of the event listener class's singleton method.
     */
    public abstract String getListenerMethodName();
    
    /**
     * Returns an array of the fully qualified names of the singleton listener 
     * method's parameters.
     */
    public abstract String[] getListenerMethodParameterClassNames();
    
    /**
     * Returns the fully qualified name of the event listener class.
     */
    public abstract String getListenerClassName();

    
    private PropertyInfo propertyInfo;

    /**
     * If a property is used to bind to this event, returns the {@code PropertyInfo}
     * instance corresponding to the binding property. Otherwise returns null.
     */
    public PropertyInfo getPropertyInfo() {
        return this.propertyInfo;
    }

    void setPropertyInfo(PropertyInfo propertyInfo) {
        this.propertyInfo = propertyInfo;
    }

}
