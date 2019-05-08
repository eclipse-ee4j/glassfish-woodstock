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

/**
 * This class provides functionality for table preferences.
 */
public final class TablePreferences {

    /**
     * Rows preference.
     */
    private String preference = null;

    /**
     * Rows per page.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private int rows = 5;

    /**
     * Default constructor.
     */
    public TablePreferences() {
    }

    /**
     * Table preferences event.
     */
    public void applyPreferences() {
        try {
            int tableRows = Integer.parseInt(preference);
            if (tableRows > 0) {
                this.rows = tableRows;
            }
        } catch (NumberFormatException e) {
        }
    }

    /**
     * Get rows per page.
     * @return int
     */
    public int getRows() {
        return rows;
    }

    /**
     * Get preference.
     * @return String
     */
    public String getPreference() {
        return Integer.toString(rows);
    }

    /**
     * Set preference.
     * @param value preference
     */
    public void setPreference(final String value) {
        preference = value;
    }
}
