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
 * DefaultTableDataModel.java
 *
 * Created on April 29, 2005, 12:40 PM
 *
 */
package com.sun.webui.jsf.model;

import com.sun.data.provider.FieldKey;
import com.sun.data.provider.impl.ObjectArrayDataProvider;
import com.sun.webui.jsf.util.MessageUtil;

/**
 * Default date for the <code>Table</code> component. The following behavior is
 * implemented:
 * <ul>
 * <li>Upon component creation, pre-populate the table with some dummy data</li>
 * </ul>
 *
 * @author Winston Prakash, John Yeary
 */
public class DefaultTableDataProvider extends ObjectArrayDataProvider {

    private static final long serialVersionUID = 4182857827502651144L;

    /** Default constructor. */
    public DefaultTableDataProvider() {
        setArray(getDefaultTableData());
    }

    /**
     * Create data that will be displayed when the table is first dropped
     * in the designer
     */
    public Data[] getDefaultTableData() {
        int noRows = 5;
        int noCols = 3;
        Data[] dataSet = new Data[noRows];
        for (int i = 0; i < noRows; i++) {
            String[] dataStrs = new String[noCols];
            for (int j = 0; j < noCols; j++) {
                dataStrs[j] = getMessage("defaultTblCell", String.valueOf(i + 1), String.valueOf(j + 1));
            }
            dataSet[i] = new Data(dataStrs);
        }
        return dataSet;
    }

    /** Return the Field Keys skiiping the 0th index
     *   which is the "class" property
     */
    @Override
    public FieldKey[] getFieldKeys() {
        FieldKey[] superFieldKeys = super.getFieldKeys();
        FieldKey[] fieldKeys = new FieldKey[superFieldKeys.length - 1];
        for (int i = 1; i < superFieldKeys.length; i++) {
            fieldKeys[i - 1] = superFieldKeys[i];
        }
        return fieldKeys;
    }

    /**
     * Get the message substituting the arguments
     */
    public String getMessage(String key, String arg1, String arg2) {
        String bundle = getClass().getPackage().getName() + ".Bundle";
        return MessageUtil.getMessage(bundle, key, new Object[]{arg1, arg2});
    }

    /**
     * Data structure that holds data for three columns of a table
     */
    public static class Data {

        private String[] columns = null;

        public Data(String[] cols) {
            columns = cols;
        }

        /** Get first column. */
        public String getColumn1() {
            return columns[0];
        }

        /** Set first column. */
        public void setColumn1(String col) {
            columns[0] = col;
        }

        /** Get second column. */
        public String getColumn2() {
            return columns[1];
        }

        /** Set second column. */
        public void setColumn2(String col) {
            columns[1] = col;
        }

        /** Get third column. */
        public String getColumn3() {
            return columns[2];
        }

        /** Set third column. */
        public void setColumn3(String col) {
            columns[2] = col;
        }
    }
}
