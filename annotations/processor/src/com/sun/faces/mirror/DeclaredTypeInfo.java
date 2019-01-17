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

import com.sun.mirror.declaration.MethodDeclaration;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import com.sun.mirror.type.InterfaceType;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a class or interface declared in the current compilation unit.
 *
 * @author gjmurphy
 */
public abstract class DeclaredTypeInfo extends ClassInfo implements TypeElement {
    
    TypeElement decl;
    
    DeclaredTypeInfo(TypeElement decl) {
        this.decl = decl;
        String qualifiedName = this.decl.getQualifiedName().toString();
        int index = qualifiedName.lastIndexOf('.');
        if (index >= 0) {
            this.className = qualifiedName.substring(index + 1);
            this.packageName = qualifiedName.substring(0, index);
        } else {
            this.className = qualifiedName;
            this.packageName = null;
        }
    }
    
    public TypeElement getDeclaration() {
        return this.decl;
    }
    
    private String className;
    
    public String getClassName() {
        return this.className;
    }
    
    private String packageName;
    
    public String getPackageName() {
        return this.packageName;
    }
    
    private ClassInfo superClassInfo;
    
    public ClassInfo getSuperClassInfo() {
        return this.superClassInfo;
    }
    
    void setSuperClassInfo(ClassInfo classInfo) {
        this.superClassInfo = classInfo;
    }

    public boolean isAssignableTo(String qualifiedName) {
        TypeElement decl = this.getDeclaration();
        if (decl.getQualifiedName().equals(qualifiedName))
            return true;
        for (InterfaceType interfaceType : decl.getInterfaces()) {
            if (interfaceType.getDeclaration().getQualifiedName().equals(qualifiedName))
                return true;
        }
        return false;
    }
    
    Map<String, PropertyInfo> propertyInfoMap;
    
    /**
     * Returns a map of all properties declared explicitly in this class or interface.
     */
    public Map<String, PropertyInfo> getPropertyInfoMap() {
        return this.propertyInfoMap;
    }
    
    void setPropertyInfoMap(Map<String, PropertyInfo> propertyInfoMap) {
        this.propertyInfoMap = propertyInfoMap;
        for (PropertyInfo propertyInfo : propertyInfoMap.values()) {
            DeclaredPropertyInfo declaredPropertyInfo = (DeclaredPropertyInfo) propertyInfo;
            declaredPropertyInfo.setDeclaringClassInfo(this);
            if (Boolean.TRUE.equals(declaredPropertyInfo.annotationValueMap.get("isDefault")))
                this.setDefaultPropertyInfo(declaredPropertyInfo);
        }
    }
    
    Map<String, EventInfo> eventInfoMap;
    
    /**
     * Returns a map of all events declared explicitly in this class or interface.
     */
    public Map<String, EventInfo> getEventInfoMap() {
        return this.eventInfoMap;
    }
    
    void setEventInfoMap(Map<String, EventInfo> eventInfoMap) {
        this.eventInfoMap = eventInfoMap;
        for (EventInfo eventInfo : eventInfoMap.values()) {
            DeclaredEventInfo declaredEventInfo = (DeclaredEventInfo) eventInfo;
            declaredEventInfo.setDeclaringClassInfo(this);
            if (Boolean.TRUE.equals(declaredEventInfo.annotationValueMap.get("isDefault")))
                this.setDefaultEventInfo(declaredEventInfo);
        }
    }
    
    private PropertyInfo defaultPropertyInfo;
    
    public PropertyInfo getDefaultPropertyInfo() {
        return this.defaultPropertyInfo;
    }
    
    void setDefaultPropertyInfo(PropertyInfo defaultPropertyInfo) {
        this.defaultPropertyInfo = defaultPropertyInfo;
    }
    
    private EventInfo defaultEventInfo;
    
    public EventInfo getDefaultEventInfo() {
        return this.defaultEventInfo;
    }
    
    void setDefaultEventInfo(EventInfo defaultEventInfo) {
        this.defaultEventInfo = defaultEventInfo;
    }
    
    Set<String> methodNameSet;
    
    Set<String> getMethodNameSet() {
        if (this.methodNameSet == null) {
            this.methodNameSet = new HashSet<String>();
            for (MethodDeclaration decl : this.decl.getMethods())
                this.methodNameSet.add(decl.getSimpleName());
        }
        return this.methodNameSet;
    }
    
    /**
     * Returns the JavaDoc comments associated with the type declaration.
     */
    public String getDocComment() {
        return Elements.getDocComment(this);
    }
    
}
