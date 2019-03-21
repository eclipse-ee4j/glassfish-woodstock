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

/*
 * ClockTime.java
 *
 * Created on July 8, 2005, 1:32 PM
 */
package com.sun.webui.jsf.model;

import java.io.Serializable;

//TODO add missing hashcode
public class ClockTime implements Serializable {

    private static final long serialVersionUID = 3125735012249146691L;

    /** Creates a new instance of ClockTime */
    public ClockTime() {
    }
    /**
     * Holds value of property hour.
     */
    private Integer hour;

    /**
     * Getter for property hour.
     * @return Value of property hour.
     */
    public Integer getHour() {

        return this.hour;
    }

    /**
     * Setter for property hour.
     * @param hour New value of property hour.
     */
    public void setHour(Integer hour) {
        if (hour.intValue() > -1 && hour.intValue() < 24) {
            this.hour = hour;
        } else {
            throw new RuntimeException();
        }
    }
    /**
     * Holds value of property minute.
     */
    private Integer minute;

    /**
     * Getter for property minute.
     * @return Value of property minute.
     */
    public Integer getMinute() {

        return this.minute;
    }

    /**
     * Setter for property minute.
     * @param minute New value of property minute.
     */
    public void setMinute(Integer minute) {
        if (minute.intValue() > -1 && minute.intValue() < 60) {
            this.minute = minute;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClockTime) {
            return (((ClockTime) obj).getHour().equals(hour) &&
                    ((ClockTime) obj).getMinute().equals(minute));
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer(128);
        buffer.append(this.getClass().getName());
        buffer.append(": ");
        buffer.append(String.valueOf(hour));
        buffer.append(":");
        buffer.append(String.valueOf(minute));
        return buffer.toString();
    }
}
