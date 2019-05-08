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

import java.util.Date;
import java.io.Serializable;
import com.sun.webui.jsf.example.util.MessageUtil;
import javax.faces.convert.Converter;

/**
 * Backing bean for Static Text example.
 */
public final class StatictextBackingBean implements Serializable {

    /**
     * Holds the Date.
     */
    private Date date = null;

    /**
     * Holds the Employee object.
     */
    private Employee emp = null;

    /**
     * Converter for the Employee object.
     */
    private EmployeeConverter empConverter = new EmployeeConverter();

    /**
     * Creates a new instance of StatictextBackingBean.
     */
    public StatictextBackingBean() {
        date = new Date();
        emp = new Employee(MessageUtil.getMessage("statictext_firstname"),
                MessageUtil.getMessage("statictext_lastname"),
                MessageUtil.getMessage("statictext_designation"));
    }

    /**
     * Returns the date.
     * @return Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date.
     * @param newDate date
     */
    public void setDate(final Date newDate) {
        this.date = newDate;
    }

    /**
     * Returns employee object.
     * @return Employee
     */
    public Employee getEmp() {
        return emp;
    }

    /**
     * Sets employee object.
     * @param newEmp employee
     */
    public void setEmp(final Employee newEmp) {
        this.emp = newEmp;
    }

    /**
     * Get the converter.
     *
     * @return converter for the Employee object.
     */
    public Converter getEmpConverter() {
        return empConverter;
    }
}
