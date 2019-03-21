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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.SimpleElementVisitor6;

/**
 * Represents a class or interface declared in the current compilation unit.
 *
 * @author gjmurphy
 */
public abstract class DeclaredTypeInfo extends ClassInfo {

    TypeElement decl;
    Set<String> methodNames;
    Map<String, PropertyInfo> propertyInfos;
    Map<String, EventInfo> eventInfos;
    protected final ProcessingEnvironment env;
    private final String className;
    private final String packageName;
    private ClassInfo superClassInfo;
    private PropertyInfo defaultPropertyInfo;
    private EventInfo defaultEventInfo;

    DeclaredTypeInfo(ProcessingEnvironment env, TypeElement decl) {
        this.env = env;
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

    @Override
    public String getClassName() {
        return this.className;
    }

    @Override
    public String getPackageName() {
        return this.packageName;
    }

    @Override
    public ClassInfo getSuperClassInfo() {
        return this.superClassInfo;
    }

    void setSuperClassInfo(ClassInfo classInfo) {
        this.superClassInfo = classInfo;
    }

    @Override
    public boolean isAssignableTo(String qualifiedName) {
        TypeElement t2 = env.getElementUtils().getTypeElement(qualifiedName);
        return env.getTypeUtils().isAssignable(decl.asType(),t2.asType());
    }

    /**
     * Returns a map of all properties declared explicitly in this class or
     * interface.
     * @return Map<String, PropertyInfo>
     */
    @Override
    public Map<String, PropertyInfo> getPropertyInfos() {
        return this.propertyInfos;
    }

    void setPropertyInfos(Map<String, PropertyInfo> propertyInfos) {
        this.propertyInfos = propertyInfos;
        for (PropertyInfo propertyInfo : propertyInfos.values()) {
            DeclaredPropertyInfo declaredPropertyInfo = (DeclaredPropertyInfo) propertyInfo;
            declaredPropertyInfo.setDeclaringClassInfo(this);
            if (Boolean.TRUE.equals(declaredPropertyInfo.annotationValueMap.get("isDefault"))) {
                this.setDefaultPropertyInfo(declaredPropertyInfo);
            }
        }
    }

    /**
     * Returns a map of all events declared explicitly in this class or
     * interface.
     * @return Map<String, EventInfo>
     */
    @Override
    public Map<String, EventInfo> getEventInfos() {
        return this.eventInfos;
    }

    void setEventInfos(Map<String, EventInfo> eventInfos) {
        this.eventInfos = eventInfos;
        for (EventInfo eventInfo : eventInfos.values()) {
            DeclaredEventInfo declaredEventInfo = (DeclaredEventInfo) eventInfo;
            declaredEventInfo.setDeclaringClassInfo(this);
            if (Boolean.TRUE.equals(declaredEventInfo.annotationValueMap.get("isDefault"))) {
                this.setDefaultEventInfo(declaredEventInfo);
            }
        }
    }

    @Override
    public PropertyInfo getDefaultPropertyInfo() {
        return this.defaultPropertyInfo;
    }

    void setDefaultPropertyInfo(PropertyInfo defaultPropertyInfo) {
        this.defaultPropertyInfo = defaultPropertyInfo;
    }

    @Override
    public EventInfo getDefaultEventInfo() {
        return this.defaultEventInfo;
    }

    void setDefaultEventInfo(EventInfo defaultEventInfo) {
        this.defaultEventInfo = defaultEventInfo;
    }

    @Override
    Set<String> getMethodNames() {
        if (this.methodNames == null) {
            this.methodNames = new HashSet<String>();
            MethodVistitor visitor = new MethodVistitor();
            this.decl.accept(visitor, null);
            this.methodNames.addAll(visitor.getMethodNames());
        }
        return this.methodNames;
    }

    /**
     * Returns the JavaDoc comments associated with the type declaration.
     * @return String
     */
    public String getDocComment() {
        return env.getElementUtils().getDocComment(decl);
    }

    private final class MethodVistitor
            extends SimpleElementVisitor6<Object, Object> {

        private final Set<String> methodNames = new HashSet<String>();
        private final Set<ExecutableElement> methods = new HashSet<ExecutableElement>();

        @Override
        public Object visitExecutable(ExecutableElement elt, Object p) {
            if(elt.getKind().equals(ElementKind.METHOD)
                    && elt.getModifiers().contains(Modifier.PUBLIC)
                    && !elt.getModifiers().contains(Modifier.STATIC)){
                methods.add(elt);
                methodNames.add(elt.getSimpleName().toString());
            }
            return elt;
        }

        @Override
        public Object visitType(TypeElement elt, Object p) {
            for(Element e : env.getElementUtils().getAllMembers(elt)){
                e.accept(this, p);
            }
            return elt;
        }

        public Set<String> getMethodNames() {
            return methodNames;
        }

        public Set<ExecutableElement> getMethods() {
            return methods;
        }
    }

    Set<ExecutableElement> getMethods() {
        MethodVistitor visitor = new MethodVistitor();
        getDeclaration().accept(visitor, null);
        return visitor.getMethods();
    }
}
