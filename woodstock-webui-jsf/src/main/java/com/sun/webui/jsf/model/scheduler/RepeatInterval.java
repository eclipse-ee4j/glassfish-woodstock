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
//TODO add hashcoded
public class RepeatInterval implements Serializable {

    private static final long serialVersionUID = 6773122235537978959L;
    public final static String ONETIME = "ONETIME";
    public final static String HOURLY = "HOURLY";
    public final static String DAILY = "DAILY";
    public final static String WEEKLY = "WEEKLY";
    public final static String MONTHLY = "MONTHLY";
    private static final boolean DEBUG = false;
    private static RepeatInterval ONETIME_RI = null;
    private static RepeatInterval HOURLY_RI = null;
    private static RepeatInterval DAILY_RI = null;
    private static RepeatInterval WEEKLY_RI = null;
    private static RepeatInterval MONTHLY_RI = null;
    private Integer calField = null;
    private String key = null;
    private String representation = null;
    private String defaultRepeatUnitString = null;

    public RepeatInterval() {
    }

    public RepeatInterval(int calFieldInt, String key, String rep, String repUnit) {
        if (DEBUG) {
            log("Create new RI");
        }
        this.calField = new Integer(calFieldInt);
        this.key = key;
        this.representation = rep;
        this.defaultRepeatUnitString = repUnit;
        if (DEBUG) {
            log("Representation is " + this.representation);
        }
    }

    public static RepeatInterval getInstance(String representation) {

        if (DEBUG) {
            log("getInstance(" + representation + ")");
        }

        if (representation.equals(ONETIME)) {
            if (ONETIME_RI == null) {
                ONETIME_RI = new RepeatInterval(-1, "Scheduler.oneTime", ONETIME, null);
            }
            return ONETIME_RI;
        } else if (representation.equals(HOURLY)) {
            if (HOURLY_RI == null) {
                HOURLY_RI = new RepeatInterval(Calendar.HOUR_OF_DAY,
                        "Scheduler.hourly",
                        HOURLY,
                        RepeatUnit.HOURS);
            }
            return HOURLY_RI;
        }
        if (representation.equals(DAILY)) {
            if (DAILY_RI == null) {
                DAILY_RI = new RepeatInterval(Calendar.DATE,
                        "Scheduler.daily",
                        DAILY,
                        RepeatUnit.DAYS);
            }
            return DAILY_RI;
        }
        if (representation.equals(WEEKLY)) {
            if (WEEKLY_RI == null) {
                WEEKLY_RI = new RepeatInterval(Calendar.WEEK_OF_YEAR,
                        "Scheduler.weekly",
                        WEEKLY,
                        RepeatUnit.WEEKS);
            }
            return WEEKLY_RI;
        }
        if (representation.equals(MONTHLY)) {
            if (MONTHLY_RI == null) {
                MONTHLY_RI = new RepeatInterval(Calendar.MONTH,
                        "Scheduler.monthly",
                        MONTHLY,
                        RepeatUnit.MONTHS);
            }
            return MONTHLY_RI;
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
        if (!(object instanceof RepeatInterval)) {
            return false;
        }
        RepeatInterval ri = (RepeatInterval) object;

        if (getCalendarField() == null) {
            if (ri.getCalendarField() != null) {
                return false;
            }
        } else if (!getCalendarField().equals(ri.getCalendarField())) {
            return false;
        }


        if (getRepresentation() == null) {
            if (ri.getRepresentation() != null) {
                return false;
            }
        } else if (!getRepresentation().equals(ri.getRepresentation())) {
            return false;
        }


        if (getKey() == null) {
            if (ri.getKey() != null) {
                return false;
            }
        } else if (!getKey().equals(ri.getKey())) {
            return false;
        }

        return true;
    }

    public RepeatUnit getDefaultRepeatUnit() {
        if (defaultRepeatUnitString == null) {
            return null;
        }
        return RepeatUnit.getInstance(defaultRepeatUnitString);
    }

    private static void log(String s) {
        System.out.println("RepeatInterval::" + s);
    }
}
