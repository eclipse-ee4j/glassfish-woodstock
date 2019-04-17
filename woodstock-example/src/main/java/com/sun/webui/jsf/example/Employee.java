/*
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.webui.jsf.example;

import java.io.Serializable;

/**
 * Employee Class.
 */
public final class Employee implements Serializable {

    /**
     * First name.
     */
    private String firstName;

    /**
     * Last name.
     */
    private String lastName;

    /**
     * Employee designation.
     */
    private String designation;

    /**
     * Creates an instance of Employee.
     * @param newFirstName first name
     * @param newLastName last name
     * @param newDesignation designation
     */
    public Employee(final String newFirstName, final String newLastName,
            final String newDesignation) {

        this.firstName = newFirstName;
        this.lastName = newLastName;
        this.designation = newDesignation;
    }

    /**
     * Getter for property firstName.
     * @return String
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Setter for property firstName.
     * @param newFirstName firstName
     */
    public void setFirstName(final String newFirstName) {
        this.firstName = newFirstName;
    }

    /**
     * Getter for property lastName.
     * @return String
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Setter for property lastName.
     * @param newLastName lastName
     */
    public void setLastName(final String newLastName) {
        this.lastName = newLastName;
    }

    /**
     * Getter for property designation.
     * @return String
     */
    public String getDesignation() {
        return this.designation;
    }

    /**
     * Setter for property designation.
     * @param newDesignation designation
     */
    public void setDesignation(final String newDesignation) {
        this.designation = newDesignation;
    }
}
