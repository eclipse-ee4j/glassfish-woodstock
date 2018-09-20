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

package com.sun.faces.mirror.generator;

import com.sun.faces.mirror.PropertyBundleMap;

/**
 * Base generator for Java source files. A source generator must provide the
 * class name and package name of the class for which a source file will be generated.
 *
 * @author gjmurphy
 */
abstract public class SourceGenerator extends Generator {
    
    /**
     * Read-only property by which this source generator reports the name of
     * class to which it should write.
     */
    abstract public String getClassName();
    
    /**
     * Read-only property by which this source generator reports the name of the
     * package in which its class should be located.
     */
    abstract public String getPackageName();
    
    /**
     * Read-only property that returns the fully qualified name of the generated
     * class.
     */
    public String getQualifiedName() {
        return this.getPackageName() + "." + this.getClassName();
    }

    private PropertyBundleMap propertyBundleMap;

    /**
     * Protected getter for property propertyBundleWriter.
     */
    protected PropertyBundleMap getPropertyBundleMap() {
        return this.propertyBundleMap;
    }

    /**
     * Setter for property propertyBundleWriter. The annotation processor will set the 
     * property bundle print writer before each call to {@link #generate}, if annotation
     * processing was invoked with the localization option.
     */
    public void setPropertyBundleMap(PropertyBundleMap propertyBundleMap) {
        this.propertyBundleMap = propertyBundleMap;
    }
    
}
