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

package com.sun.webui.jsf.example.table;

import java.io.Serializable;
import java.util.ArrayList;

import com.sun.webui.jsf.component.Alarm;

import com.sun.webui.jsf.example.table.util.Group;
import com.sun.webui.jsf.example.table.util.Name;
import com.sun.webui.jsf.example.index.IndexBackingBean;

/**
 * Backing bean for table examples.
 *
 * Note that we must implement java.io.Serializable or
 * javax.faces.component.StateHolder in case client-side
 * state saving is used, or if server-side state saving is
 * used with a distributed system.
 */
public class TableBean implements Serializable {
    
    // Navigation case outcome to go to table index
    public static final String SHOW_TABLE_INDEX = "showTableIndex";
    
    // Group util for table examples.
    private Group groupA = null; // List (rows 0-19).
    private Group groupB = null; // Array (rows 0-9).
    private Group groupC = null; // Array (rows 10-19).
    private Group groupD = null; // List (rows 0-19)-- Used for hidden selected rows.

    // Alarms.
    private static final Alarm down = new Alarm(Alarm.SEVERITY_DOWN);
    private static final Alarm critical = new Alarm(Alarm.SEVERITY_CRITICAL);
    private static final Alarm major = new Alarm(Alarm.SEVERITY_MAJOR);
    private static final Alarm minor = new Alarm(Alarm.SEVERITY_MINOR);
    private static final Alarm ok = new Alarm(Alarm.SEVERITY_OK);

    // Data for table examples.
    protected static final Name[] names = {
        new Name("William", "Dupont", down, 
                "Hot", "Purple", "Yellow", "In Service"),
        new Name("Anna", "Keeney", critical, 
                "Non-recoverable error", "Red", "Pink", "Brown"),
        new Name("Mariko", "Randor", major, 
                "Cold", "Degraded", "Green", "-"),
        new Name("John", "Wilson", minor, 
                "Warm", "Blue", "Power mode", "Xray"),
        new Name("Lynn", "Seckinger", ok, 
                "Cool", "-", "Black", "Ok"),
        new Name("Richard", "Tattersall", down, 
                "Lukewarm", "Aqua", "-", "Starting"),
        new Name("Gabriella", "Sarintia", critical, 
                "Stopped", "Jesmui", "One", "-"),
        new Name("Lisa", "Hartwig", major, 
                "Coolant leak", "Stressed", "Bandaid applied", "-"),
        new Name("Shirley", "Jones", minor, 
                "Orange", "Two", "In service", "Shields are raised"),
        new Name("Bill", "Sprague", ok, 
                "-", "Three", "Tastes great", "Ok"),
        new Name("Greg", "Doench", down, 
                "Less filling", "Four", "-", "Stopping"),
        new Name("Solange", "Nadeau", critical, 
                "-", "Five", "Not responding", "No contact"),
        new Name("Heather", "McGann", major, 
                "Bud", "Predictive failure", "-", "-"),
        new Name("Roy", "Martin", minor, 
                "Coors", "Six", "Slow", "abcdef"),
        new Name("Claude", "Loubier", ok, 
                "Sam Adams", "Seven", "-", "Ok"),
        new Name("Dan", "Woodard", down, 
                "Heineken", "Bud Lite", "-", "Stopping"),
        new Name("Ron", "Dunlap", critical, 
                "Dormant", "Eight", "-", "-"),
        new Name("Keith", "Frankart", major, 
                "Miller", "Degraded", "Twelve", "-"),
        new Name("Andre", "Nadeau", minor, 
                "Nine", "Eleven", "In service", "Foster Lager"),
        new Name("Horace", "Celestin", ok, 
                "Ten", "Molson", "-", "Ok"),
    };

    // Default constructor.
    public TableBean() {
    }

    // Get Group util created with a List containing all names.
    public Group getGroupA() {
        if (groupA != null) {
            return groupA;
        }
        // Create List with all names.
        ArrayList<Name> newNames = new ArrayList<Name>();
        for (int i = names.length - 1; i >= 0; i--) {
            newNames.add(names[i]);
        }
        return (groupA = new Group(newNames));
    }

    // Get Group util created with an array containing a subset of names.
    public Group getGroupB() {
        if (groupB != null) {
            return groupB;
        }
        // Create an array with subset of names (i.e., 0-9).
        Name[] newNames = new Name[10];
        System.arraycopy(names, 0, newNames, 0, 10);
        return (groupB = new Group(newNames));
    }

    // Get Group util created with an array containing a subset of names.
    public Group getGroupC() {
        if (groupC != null) {
            return groupC;
        }
        // Create an array with subset of names (i.e., 10-19).
        Name[] newNames = new Name[10];
        System.arraycopy(names, 10, newNames, 0, 10);
        return (groupC = new Group(newNames));
    }
    
    // Get Group util created with a List containing all names and a
    // flag to indicate that selection state is maintained arcoss pages.
    public Group getGroupD() {
        if (groupD != null) {
            return groupD;
        }
        // Create List with all names.
        ArrayList<Name> newNames = new ArrayList<Name>();
        for (int i = names.length - 1; i >= 0; i--) {
            newNames.add(names[i]);
        }
        return (groupD = new Group(newNames, true));
    }

    // Action handler when navigating to the table index.
    public String showTableIndex() {
        reset();
        return SHOW_TABLE_INDEX;
    }
    
    // Action handler when navigating to the main example index.
    public String showExampleIndex() {
        reset();
        return IndexBackingBean.INDEX_ACTION;
    }
    
    // Reset values so next visit starts fresh.
    private void reset() {
        groupA = null;
        groupB = null;
        groupC = null;
	groupD = null;
    }
}
