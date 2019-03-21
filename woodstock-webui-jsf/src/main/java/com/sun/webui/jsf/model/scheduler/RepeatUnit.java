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

package com.sun.webui.jsf.model.scheduler;

import java.io.Serializable;
import java.util.Calendar;
import com.sun.webui.jsf.util.ThemeUtilities;

import javax.faces.context.FacesContext;

// Delete the setters once you have reimplemented this not to 
// use the default Serializable mechanism, but the same as 
// in the converter....
//TODO add hashcode
public class RepeatUnit implements Serializable {

    private static final long serialVersionUID = -8055799403044778734L;
    public final static String HOURS = "HOURS";
    public final static String DAYS = "DAYS";
    public final static String WEEKS = "WEEKS";
    public final static String MONTHS = "MONTHS";
    private static final boolean DEBUG = false;
    private static RepeatUnit HOURS_RI = null;
    private static RepeatUnit DAYS_RI = null;
    private static RepeatUnit WEEKS_RI = null;
    private static RepeatUnit MONTHS_RI = null;
    private Integer calField = null;
    private String key = null;
    private String representation = null;

    public RepeatUnit() {
    }

    public RepeatUnit(int calFieldInt, String key, String rep) {
        if (DEBUG) {
            log("Create new RU");
        }
        this.calField = new Integer(calFieldInt);
        this.key = key;
        this.representation = rep;
        if (DEBUG) {
            log("Representation is " + this.representation);
        }
    }

    public static RepeatUnit getInstance(String representation) {

        if (DEBUG) {
            log("getInstance(" + representation + ")");
        }

        if (representation.equals(HOURS)) {
            if (HOURS_RI == null) {
                HOURS_RI = new RepeatUnit(Calendar.HOUR_OF_DAY, "Scheduler.hours", HOURS);
            }
            return HOURS_RI;
        }
        if (representation.equals(DAYS)) {
            if (DAYS_RI == null) {
                DAYS_RI = new RepeatUnit(Calendar.DATE, "Scheduler.days", DAYS);
            }
            return DAYS_RI;
        }
        if (representation.equals(WEEKS)) {
            if (WEEKS_RI == null) {
                WEEKS_RI = new RepeatUnit(Calendar.WEEK_OF_YEAR, "Scheduler.weeks", WEEKS);
            }
            return WEEKS_RI;
        }
        if (representation.equals(MONTHS)) {
            if (MONTHS_RI == null) {
                MONTHS_RI = new RepeatUnit(Calendar.MONTH, "Scheduler.months", MONTHS);
            }
            return MONTHS_RI;
        }
        return null;
    }

    /**
     * Getter for property calendarField.
     * @return Value of property calendarField.
     */
    public Integer getCalendarField() {
        return calField;
    }

    /**
     * Setter for property calendarField.
     */
    public void setCalendarField(Integer calField) {
        this.calField = calField;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setRepresentation(String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }

    /**
     * Getter for property labelKey.
     * @return Value of property labelKey.
     */
    public String getLabel(FacesContext context) {
        return ThemeUtilities.getTheme(context).getMessage(key);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!(object instanceof RepeatUnit)) {
            return false;
        }

        RepeatUnit ru = (RepeatUnit) object;

        if (getKey() == null) {
            if (ru.getKey() != null) {
                return false;
            }
        } else if (!getKey().equals(ru.getKey())) {
            return false;
        }

        if (getRepresentation() == null) {
            if (ru.getRepresentation() != null) {
                return false;
            }
        } else if (!getRepresentation().equals(ru.getRepresentation())) {
            return false;
        }

        if (getCalendarField() == null) {
            if (ru.getCalendarField() != null) {
                return false;
            }
        } else if (!getCalendarField().equals(ru.getCalendarField())) {
            return false;
        }

        return true;
    }

    private static void log(String s) {
        System.out.println("RepeatUnit::" + s);
    }
}

