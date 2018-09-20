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
 * A base class that defines the basic metadata available for a property, whether
 * it belongs to a class declared in the current compilation unit, or to a class
 * in a dependant library.
 *
 * @author gjmurphy
 */
public abstract class PropertyInfo extends FeatureInfo {
    
    /**
     * Returns the name of this property as a Java instance name. This will usually
     * be the value of {@link #getName), unless that value equals a Java keyword
     * or reserverd word, in which case a "_" character will be prepended to the name.
     */
    public abstract String getInstanceName();

    /**
     * Returns the fully qualified type name of this property.
     */
    public abstract String getType();

    /**
     * Returns the simple name of this property's read method. May be null if 
     * property is write-only or if the property is inherited and the read method 
     * is defined in the super class.
     */
    public abstract String getReadMethodName();

    /**
     * Returns the simple name of this property's write method. May be null if 
     * property is read-only or if the property is inherited and the write method 
     * is defined in the super class.
     */
    public abstract String getWriteMethodName();
    
    /**
     * Returns the fully qualified type name of a property editor class to be used
     * for editing this property. If no editor was assigned to this property, 
     * returns null.
     */
    public abstract String getEditorClassName();
    
    /**
     * Returns the category info for this property, or null if this property is
     * uncategorized.
     */
    public abstract CategoryInfo getCategoryInfo();
    
    
    abstract String getCategoryReferenceName();
    
    /**
     * Returns the attribute info for this property, or null if this property does
     * not correspond to an attribute.
     */
    public abstract AttributeInfo getAttributeInfo();

}
