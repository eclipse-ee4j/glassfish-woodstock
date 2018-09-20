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

package com.sun.webui.jsf.example.statictext;

import java.beans.*;
import java.io.Serializable; 

/**
 * Employee Class
 */
public class Employee implements Serializable {
    
    // Holds employee details
    private String firstName;
    private String lastName;
    private String designation;
    
    /** Creates an instance of Employee. */
    public Employee(String firstName, String lastName, String designation) {
        this.firstName = firstName; 
        this.lastName = lastName; 
        this.designation = designation; 
    }
    
    /** Getter for property firstName. */    
    public String getFirstName() {
        return this.firstName;
    }
    
    /** Setter for property firstName. */ 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /** Getter for property lastName. */    
    public String getLastName() {
        return this.lastName;
    }    
    
    /** Setter for property lastName. */ 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /** Getter for property designation. */    
    public String getDesignation() {
        return this.designation;
    }
    
    /** Setter for property designation. */ 
    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
