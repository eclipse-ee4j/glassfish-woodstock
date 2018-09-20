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

package com.sun.webui.jsf.example.orderablelist;

/**
 * This class represents a flavor.
 */
public class Flavor {
    
    /** Name of a flavor. */
    private String name;
    
    /** Creates a new instance of Flavor. */
    public Flavor() {
    }
 
    /** Creates a new instance of Flavor. */
    public Flavor(String name) {
        this.name = name;
    }
    
    /** Get the name of the flavor. */
    public String getName() {
        return this.name;
    }
    
    /** Set the name of the flavor. */
    public void setName(String name) {
        this.name = name;
    }
 
    /** Returns a string representation of the object. */
    public String toString() { 
        return name;
    }
}
