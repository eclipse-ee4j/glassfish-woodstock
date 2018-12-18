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

import com.sun.mirror.declaration.ClassDeclaration;
import javax.lang.model.element.Element;
import com.sun.mirror.type.InterfaceType;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a component class or a non-component base class declared in the current
 * compilation unit. This class offers several different "views" of its properties,
 * which can be useful during source code generation. In addition to providing a
 * map of all declared properties, it provides a map which contains all inherited
 * properties.
 *
 *
 * @author gjmurphy
 */
public class DeclaredClassInfo extends DeclaredTypeInfo {
    
    DeclaredClassInfo(ClassDeclaration decl) {
        super(decl);
    }
    
    /**
     * Returns this class's default property. If no default property was declared
     * explicitly via a {@link com.sun.faces.annotation.Property} annotation, and
     * this type implements or extends {@link javax.faces.component.ValueHolder},
     * then the {@code value} property is made default.
     */
    public PropertyInfo getDefaultPropertyInfo() {
        PropertyInfo defaultPropertyInfo = super.getDefaultPropertyInfo();
        if (defaultPropertyInfo == null) {
            if (this.getSuperClassInfo() != null) {
                defaultPropertyInfo = this.getSuperClassInfo().getDefaultPropertyInfo();
            }
            if (defaultPropertyInfo == null) {
                for (InterfaceType interfaceType : this.decl.getSuperinterfaces()) {
                    if (interfaceType.getDeclaration().getQualifiedName().equals("javax.faces.component.ValueHolder")) {
                        defaultPropertyInfo = this.getPropertyInfoMap().get("value");
                        if (defaultPropertyInfo == null)
                            defaultPropertyInfo = this.getInheritedPropertyInfoMap().get("value");
                    }
                }
            }
        }
        return defaultPropertyInfo;
    }
    
    /**
     * Returns this class's default Event. If no default Event was declared
     * explicitly via a {@link com.sun.faces.annotation.Event} annotation, and
     * this type implements or extends {@link javax.faces.component.ValueHolder},
     * then the {@code value} Event is made default.
     */
    public EventInfo getDefaultEventInfo() {
        EventInfo defaultEventInfo = super.getDefaultEventInfo();
        if (defaultEventInfo == null) {
            if (this.getSuperClassInfo() != null) {
                defaultEventInfo = this.getSuperClassInfo().getDefaultEventInfo();
            }
            if (defaultEventInfo == null) {
                for (InterfaceType interfaceType : this.decl.getSuperinterfaces()) {
                    if (interfaceType.getDeclaration().getQualifiedName().equals("javax.faces.component.EditableValueHolder")) {
                        defaultEventInfo = this.getEventInfoMap().get("valueChange");
                        if (defaultEventInfo == null)
                            defaultEventInfo = this.getInheritedEventInfoMap().get("valueChange");
                    }
                    if (interfaceType.getDeclaration().getQualifiedName().equals("javax.faces.component.ActionSource")) {
                        defaultEventInfo = this.getEventInfoMap().get("action");
                        if (defaultEventInfo == null)
                            defaultEventInfo = this.getInheritedEventInfoMap().get("action");
                    }
                }
            }
        }
        return defaultEventInfo;
    }
    
    private Map<String,PropertyInfo> inheritedPropertyInfoMap;
    
    /**
     * Returns a map of all properties inherited by this class, but not overriden
     * by properties in this class.
     */
    public Map<String,PropertyInfo> getInheritedPropertyInfoMap() {
        if (this.inheritedPropertyInfoMap == null) {
            ClassInfo classInfo = this.getSuperClassInfo();
            this.inheritedPropertyInfoMap = new HashMap<String,PropertyInfo>();
            while (classInfo != null) {
                for (PropertyInfo propertyInfo : classInfo.getPropertyInfoMap().values()) {
                    String name = propertyInfo.getName();
                    if (!this.propertyInfoMap.containsKey(name) && !this.inheritedPropertyInfoMap.containsKey(name))
                        this.inheritedPropertyInfoMap.put(name, propertyInfo);
                }
                classInfo = classInfo.getSuperClassInfo();
            }
        }
        return this.inheritedPropertyInfoMap;
    }
    
    private Map<String,EventInfo> inheritedEventInfoMap;
    
    /**
     * Returns a map of all events inherited by this class, but not overriden
     * by properties in this class.
     */
    public Map<String,EventInfo> getInheritedEventInfoMap() {
        if (this.inheritedEventInfoMap == null) {
            ClassInfo classInfo = this.getSuperClassInfo();
            this.inheritedEventInfoMap = new HashMap<String,EventInfo>();
            while (classInfo != null) {
                for (EventInfo eventInfo : classInfo.getEventInfoMap().values()) {
                    String name = eventInfo.getName();
                    if (!this.eventInfoMap.containsKey(name) && !this.inheritedEventInfoMap.containsKey(name))
                        this.inheritedEventInfoMap.put(name, eventInfo);
                }
                classInfo = classInfo.getSuperClassInfo();
            }
        }
        return this.inheritedEventInfoMap;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DeclaredClassInfo))
            return false;
        DeclaredClassInfo that = (DeclaredClassInfo) obj;
        if (!this.getClassName().equals(that.getClassName()))
            return false;
        if (this.getClassName() == null && that.getClassName() != null)
            return false;
        if (!this.getClassName().equals(that.getClassName()))
            return false;
        return true;
    }
    
}
