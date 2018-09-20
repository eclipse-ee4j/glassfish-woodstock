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

package com.sun.webui.jsf.example.table.util;

import com.sun.webui.jsf.component.Alarm;
import com.sun.webui.jsf.theme.ThemeImages;

public class Name {
    private String last = null; // Last name.
    private String first = null; // First name.
    private Alarm alarm = null; // Alarm.
    private String statusA = null; // A status
    private String statusB = null; // B status
    private String statusC = null; // C status
    private String statusD = null; // D status
    
    // Default constructor.
    public Name(String first, String last) {
        this.last = last;
        this.first = first;
    }

    // Construct an instance with given alarm severity.
    public Name(String first, String last, Alarm alarm) {
        this(first, last);
        this.alarm = alarm;
    }

    // Construct an instance with given alarm severity and statuses.
    public Name(String first, String last, Alarm alarm, 
            String statusA, String statusB, String statusC, String statusD) {
        this(first, last, alarm);
        this.statusA = statusA;
        this.statusB = statusB;
        this.statusC = statusC;
        this.statusD = statusD;
    }

    // Get first name.
    public String getFirst() {
        return first;
    }

    // Set first name.
    public void setFirst(String first) {
        this.first = first;
    }

    // Get last name.
    public String getLast() {
        return last;
    }

    // Set last name.
    public void setLast(String last) {
        this.last = last;
    }

    // Get alarm.
    public Alarm getAlarm() {
        return alarm;
    }

    // Get alarm.
    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }
    
    // Get status A
    public String getStatusA() {
        return statusA;
    }
    
    // Set status A
    public void setStatusA(String status) {
        this.statusA = status;
    }
    
    // Get status B
    public String getStatusB() {
        return statusB;
    }
    
    // Set status B
    public void setStatusB(String status) {
        this.statusB = status;
    }
    
    // Get status C
    public String getStatusC() {
        return statusC;
    }
    
    // Set status C
    public void setStatusC(String status) {
        this.statusC = status;
    }
    
    // Get status D
    public String getStatusD() {
        return statusD;
    }
    
    // Set status D
    public void setStatusD(String status) {
        this.statusD = status;
    }

    // Get alarm severity.
    public String getSeverity() {
        return alarm.getSeverity();
    }

    // Get alarm severity.
    public void setSeverity(String severity) {
        alarm.setSeverity(severity);
    }
}
