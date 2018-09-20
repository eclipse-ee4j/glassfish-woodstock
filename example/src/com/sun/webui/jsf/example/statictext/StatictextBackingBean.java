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
import java.util.Date;
import java.io.Serializable;

import com.sun.webui.jsf.example.common.MessageUtil;
import javax.faces.convert.Converter;

/**
 * Backing bean for Static Text example.
 */

public class StatictextBackingBean implements Serializable {
    
    // Holds the Date.
    Date date = null;
    
    // Holds the Employee object.
    Employee emp = null;

    // Converter for the Employee object.
    EmployeeConverter empConverter = new EmployeeConverter(); 
                       
    /** Creates a new instance of StatictextBackingBean. */
    public StatictextBackingBean() {
        date = new Date();
        emp = new Employee(MessageUtil.getMessage("statictext_firstname"), 
                MessageUtil.getMessage("statictext_lastname"), 
                MessageUtil.getMessage("statictext_designation"));
    }
   
    /** Returns the date. */
    public Date getDate() {
        return date;
    }
    
    /** Sets the date. */
    public void setDate(Date date) {
        this.date = date;
    }
           
    /** Returns employee object. */
    public Employee getEmp() {
        return emp;
    }
    
    /** Sets employee object. */
    public void setEmp(Employee emp) {
        this.emp = emp;
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
