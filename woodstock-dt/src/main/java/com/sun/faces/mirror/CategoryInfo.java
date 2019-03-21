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
package com.sun.faces.mirror;

import java.util.Map;

/**
 * Represents a category descriptor.
 *
 * @author gjmurphy
 */
public class CategoryInfo implements Comparable {

    static final String NAME = "name";
    static final String SORT_KEY = "sortKey";

    Map<String, Object> annotationValueMap;

    CategoryInfo(Map<String, Object> annotationValueMap) {
        this.annotationValueMap = annotationValueMap;
    }

    public String getName() {
        return (String) this.annotationValueMap.get(NAME);
    }

    public String getSortKey() {
        if (this.annotationValueMap.containsKey(SORT_KEY)) {
            return (String) this.annotationValueMap.get(SORT_KEY);
        }
        return this.getName();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.annotationValueMap != null
                ? this.annotationValueMap.hashCode() : 0);
        hash = 59 * hash + (this.fieldName != null
                ? this.fieldName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CategoryInfo) {
            return ((CategoryInfo) obj).getName().equals(this.getName());
        }
        return false;
    }

    @Override
    public int compareTo(Object obj) {
        if (obj instanceof CategoryInfo) {
            return this.getSortKey().compareTo(((CategoryInfo) obj).getSortKey());
        }
        return -1;
    }

    private String fieldName;

    public String getFieldName() {
        return this.fieldName;
    }

    void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

}
