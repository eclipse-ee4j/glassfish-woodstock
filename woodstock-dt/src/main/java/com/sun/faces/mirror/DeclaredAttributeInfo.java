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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an attribute for a property declared on a class in the current
 * compilation unit.
 *
 * @author gjmurphy
 */
public final class DeclaredAttributeInfo implements AttributeInfo {

    static final String NAME = "name";
    static final String IS_REQUIRED = "isRequired";
    static final String IS_BINDABLE = "isBindable";

    Map<String, Object> annotationValueMap;
    PropertyInfo parentPropertyInfo;

    DeclaredAttributeInfo(PropertyInfo parentPropertyInfo) {
        this(null, parentPropertyInfo);
    }

    DeclaredAttributeInfo(AttributeInfo attributeInfo) {
        this.annotationValueMap = new HashMap<String, Object>();
        this.annotationValueMap.put(NAME, attributeInfo.getName());
        this.annotationValueMap.put(IS_REQUIRED, attributeInfo.isRequired());
        this.annotationValueMap.put(IS_BINDABLE, attributeInfo.isBindable());
        this.setDescription(attributeInfo.getDescription());
        this.setMethodSignature(attributeInfo.getMethodSignature());
        if (attributeInfo instanceof DeclaredAttributeInfo) {
            this.parentPropertyInfo = ((DeclaredAttributeInfo) attributeInfo).parentPropertyInfo;
        }
    }

    DeclaredAttributeInfo(Map<String, Object> annotationValueMap, PropertyInfo parentPropertyInfo) {
        this.annotationValueMap = annotationValueMap;
        this.parentPropertyInfo = parentPropertyInfo;
    }

    @Override
    public String getName() {
        if (this.annotationValueMap == null) {
            return this.parentPropertyInfo.getName();
        }
        String name = (String) this.annotationValueMap.get(NAME);
        return name == null ? this.parentPropertyInfo.getName() : name;
    }

    @Override
    public boolean isRequired() {
        if (this.annotationValueMap == null) {
            return false;
        }
        return Boolean.TRUE.equals(this.annotationValueMap.get(IS_REQUIRED));
    }

    @Override
    public boolean isBindable() {
        if (this.annotationValueMap == null) {
            return true;
        }
        return !Boolean.FALSE.equals(this.annotationValueMap.get(IS_BINDABLE));
    }

    private String methodSignature;

    @Override
    public String getMethodSignature() {
        return this.methodSignature;
    }

    void setMethodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
    }

    private String description;

    @Override
    public String getDescription() {
        if (description == null && this.parentPropertyInfo != null) {
            return ((DeclaredPropertyInfo) this.parentPropertyInfo).getDocComment();
        }
        return this.description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getWriteMethodName() {
        String name = this.getName();
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

}
