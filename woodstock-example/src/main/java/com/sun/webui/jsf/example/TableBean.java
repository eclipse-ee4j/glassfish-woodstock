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
import java.util.ArrayList;

import com.sun.webui.jsf.component.Alarm;

/**
 * Backing bean for table examples.
 */
public final class TableBean implements Serializable {

    /**
     * Navigation case outcome to go to table index.
     */
    public static final String SHOW_TABLE_INDEX = "showTableIndex";

    /**
     * Group utility for table examples, List (rows 0-19).
     */
    private TableGroup groupA = null;

    /**
     * Group utility for table examples, Array (rows 0-9).
     */
    private TableGroup groupB = null;

    /**
     * Group utility for table examples, Array (rows 10-19).
     */
    private TableGroup groupC = null;

    /**
     * Group utility for table examples, List (rows 0-19)-- Used for hidden
     * selected rows.
     */
    private TableGroup groupD = null;

    /**
     * Down alarm.
     */
    private static final Alarm DOWN = new Alarm(Alarm.SEVERITY_DOWN);

    /**
     * Critical alarm.
     */
    private static final Alarm CRITICAL = new Alarm(Alarm.SEVERITY_CRITICAL);

    /**
     * Major alarm.
     */
    private static final Alarm MAJOR = new Alarm(Alarm.SEVERITY_MAJOR);

    /**
     * Minor alarm.
     */
    private static final Alarm MINOR = new Alarm(Alarm.SEVERITY_MINOR);

    /**
     * OK alarm.
     */
    private static final Alarm OK = new Alarm(Alarm.SEVERITY_OK);

    /**
     * Data for table examples.
     */
    protected static final TableName[] NAMES = {
        new TableName("William", "Dupont", DOWN,
        "Hot", "Purple", "Yellow", "In Service"),
        new TableName("Anna", "Keeney", CRITICAL,
        "Non-recoverable error", "Red", "Pink", "Brown"),
        new TableName("Mariko", "Randor", MAJOR,
        "Cold", "Degraded", "Green", "-"),
        new TableName("John", "Wilson", MINOR,
        "Warm", "Blue", "Power mode", "Xray"),
        new TableName("Lynn", "Seckinger", OK,
        "Cool", "-", "Black", "Ok"),
        new TableName("Richard", "Tattersall", DOWN,
        "Lukewarm", "Aqua", "-", "Starting"),
        new TableName("Gabriella", "Sarintia", CRITICAL,
        "Stopped", "Jesmui", "One", "-"),
        new TableName("Lisa", "Hartwig", MAJOR,
        "Coolant leak", "Stressed", "Bandaid applied", "-"),
        new TableName("Shirley", "Jones", MINOR,
        "Orange", "Two", "In service", "Shields are raised"),
        new TableName("Bill", "Sprague", OK,
        "-", "Three", "Tastes great", "Ok"),
        new TableName("Greg", "Doench", DOWN,
        "Less filling", "Four", "-", "Stopping"),
        new TableName("Solange", "Nadeau", CRITICAL,
        "-", "Five", "Not responding", "No contact"),
        new TableName("Heather", "McGann", MAJOR,
        "Bud", "Predictive failure", "-", "-"),
        new TableName("Roy", "Martin", MINOR,
        "Coors", "Six", "Slow", "abcdef"),
        new TableName("Claude", "Loubier", OK,
        "Sam Adams", "Seven", "-", "Ok"),
        new TableName("Dan", "Woodard", DOWN,
        "Heineken", "Bud Lite", "-", "Stopping"),
        new TableName("Ron", "Dunlap", CRITICAL,
        "Dormant", "Eight", "-", "-"),
        new TableName("Keith", "Frankart", MAJOR,
        "Miller", "Degraded", "Twelve", "-"),
        new TableName("Andre", "Nadeau", MINOR,
        "Nine", "Eleven", "In service", "Foster Lager"),
        new TableName("Horace", "Celestin", OK,
        "Ten", "Molson", "-", "Ok")
    };

    /**
     * Default constructor.
     */
    public TableBean() {
    }

    /**
     * Get Group util created with a List containing all names.
     * @return TableGroup
     */
    public TableGroup getGroupA() {
        if (groupA != null) {
            return groupA;
        }
        // Create List with all names.
        ArrayList<TableName> newNames = new ArrayList<TableName>();
        for (int i = NAMES.length - 1; i >= 0; i--) {
            newNames.add(NAMES[i]);
        }
        groupA = new TableGroup(newNames);
        return groupA;
    }

    /**
     * Get Group util created with an array containing a subset of names.
     * @return TableGroup
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public TableGroup getGroupB() {
        if (groupB != null) {
            return groupB;
        }
        // Create an array with subset of names (i.e., 0-9).
        TableName[] newNames = new TableName[10];
        System.arraycopy(NAMES, 0, newNames, 0, 10);
        groupB = new TableGroup(newNames);
        return groupB;
    }

    /**
     * Get Group util created with an array containing a subset of names.
     * @return TableGroup
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public TableGroup getGroupC() {
        if (groupC != null) {
            return groupC;
        }
        // Create an array with subset of names (i.e., 10-19).
        TableName[] newNames = new TableName[10];
        System.arraycopy(NAMES, 10, newNames, 0, 10);
        groupC = new TableGroup(newNames);
        return groupC;
    }

    /**
     * Get Group utility created with a List containing all names and a flag to
     * indicate that selection state is maintained across pages.
     *
     * @return TableGroup
     */
    public TableGroup getGroupD() {
        if (groupD != null) {
            return groupD;
        }
        // Create List with all names.
        ArrayList<TableName> newNames = new ArrayList<TableName>();
        for (int i = NAMES.length - 1; i >= 0; i--) {
            newNames.add(NAMES[i]);
        }
        groupD = new TableGroup(newNames, true);
        return groupD;
    }

    /**
     * Action handler when navigating to the table index.
     * @return String
     */
    public String showTableIndex() {
        reset();
        return SHOW_TABLE_INDEX;
    }

    /**
     * Action handler when navigating to the main example index.
     * @return String
     */
    public String showExampleIndex() {
        reset();
        return IndexBackingBean.INDEX_ACTION;
    }

    /**
     * Reset values so next visit starts fresh.
     */
    private void reset() {
        groupA = null;
        groupB = null;
        groupC = null;
        groupD = null;
    }
}
