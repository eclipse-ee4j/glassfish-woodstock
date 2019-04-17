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

import com.sun.webui.jsf.component.Alarm;

/**
 * Name POJO for the table example.
 */
public final class TableName {

    /**
     * Last name.
     */
    private String last = null;

    /**
     * First name.
     */
    private String first = null;

    /**
     * Alarm.
     */
    private Alarm alarm = null;

    /**
     * A status.
     */
    private String statusA = null;

    /**
     * B status.
     */
    private String statusB = null;

    /**
     * C status.
     */
    private String statusC = null;

    /**
     * D status.
     */
    private String statusD = null;

    /**
     * Default constructor.
     * @param firstName first name
     * @param lastName last name
     */
    public TableName(final String firstName, final String lastName) {
        this.last = lastName;
        this.first = firstName;
    }

    /**
     * Construct an instance with given alarm severity.
     * @param firstName first name
     * @param lastName last name
     * @param newAlarm alarm
     */
    public TableName(final String firstName, final String lastName,
            final Alarm newAlarm) {

        this(firstName, lastName);
        this.alarm = newAlarm;
    }

    /**
     * Construct an instance with given alarm severity and statuses.
     * @param firstName first name
     * @param lastName last name
     * @param newAlarm alarm
     * @param newStatusA status A
     * @param newStatusB status B
     * @param newStatusC status C
     * @param newStatusD status D
     */
    public TableName(final String firstName, final String lastName,
            final Alarm newAlarm, final String newStatusA,
            final String newStatusB, final String newStatusC,
            final String newStatusD) {

        this(firstName, lastName, newAlarm);
        this.statusA = newStatusA;
        this.statusB = newStatusB;
        this.statusC = newStatusC;
        this.statusD = newStatusD;
    }

    /**
     * Get first name.
     * @return String
     */
    public String getFirst() {
        return first;
    }

    /**
     * Set first name.
     * @param firstName new first name
     */
    public void setFirst(final String firstName) {
        this.first = firstName;
    }

    /**
     * Get last name.
     * @return String
     */
    public String getLast() {
        return last;
    }

    /**
     * Set last name.
     * @param lastName new last name
     */
    public void setLast(final String lastName) {
        this.last = lastName;
    }

    /**
     * Get alarm.
     * @return Alarm
     */
    public Alarm getAlarm() {
        return alarm;
    }

    /**
     * Get alarm.
     * @param newAlarm alarm
     */
    public void setAlarm(final Alarm newAlarm) {
        this.alarm = newAlarm;
    }

    /**
     * Get status A.
     * @return String
     */
    public String getStatusA() {
        return statusA;
    }

    /**
     * Set status A.
     * @param newStatus new status A
     */
    public void setStatusA(final String newStatus) {
        this.statusA = newStatus;
    }

    /**
     * Get status B.
     * @return String
     */
    public String getStatusB() {
        return statusB;
    }

    /**
     * Set status B.
     * @param newStatus new status B
     */
    public void setStatusB(final String newStatus) {
        this.statusB = newStatus;
    }

    /**
     * Get status C.
     * @return String
     */
    public String getStatusC() {
        return statusC;
    }

    /**
     * Set status C.
     * @param newStatus new status C
     */
    public void setStatusC(final String newStatus) {
        this.statusC = newStatus;
    }

    /**
     * Get status D.
     * @return String
     */
    public String getStatusD() {
        return statusD;
    }

    /**
     * Set status D.
     * @param newStatus new status D
     */
    public void setStatusD(final String newStatus) {
        this.statusD = newStatus;
    }

    /**
     * et alarm severity.
     * @return String
     */
    public String getSeverity() {
        return alarm.getSeverity();
    }

    /**
     * Get alarm severity.
     * @param severity new alarm severity
     */
    public void setSeverity(final String severity) {
        alarm.setSeverity(severity);
    }
}
