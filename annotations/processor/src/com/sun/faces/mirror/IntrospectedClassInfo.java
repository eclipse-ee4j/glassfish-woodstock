/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2019 Payara Services Ltd.
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

import com.sun.rave.designtime.CategoryDescriptor;
import java.beans.BeanInfo;
import java.beans.MethodDescriptor;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;

/**
 * Represents a component class or a non-component base class from a dependant library,
 * discovered using introspection.
 *
 * @author gjmurphy
 * @author jonathan coustick
 */
public class IntrospectedClassInfo extends ClassInfo {
    
    BeanInfo beanInfo;
    Map<String, PropertyInfo> propertyInfoMap;
    Map<String, EventInfo> eventInfoMap;
    
    IntrospectedClassInfo(BeanInfo beanInfo) {
        this.beanInfo = beanInfo;
    }
    
    public String getName() {
        return this.beanInfo.getBeanDescriptor().getName();
    }
    
    @Override
    public String getClassName() {
        return this.beanInfo.getBeanDescriptor().getBeanClass().getSimpleName();
    }
    
    @Override
    public String getPackageName() {
        return this.beanInfo.getBeanDescriptor().getBeanClass().getPackage().getName();
    }
    
    public BeanInfo getBeanInfo() {
        return beanInfo;
    }
    
    @Override
    public ClassInfo getSuperClassInfo() {
        return null;
    }
    
    @Override
    public boolean isAssignableTo(String qualifiedClassName) {
        try {
            Class superClass = Class.forName(qualifiedClassName);
            if (superClass.isAssignableFrom(this.getBeanInfo().getBeanDescriptor().getBeanClass()))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public Map<String, PropertyInfo> getPropertyInfoMap() {
        return this.propertyInfoMap;
    }
    
    void setPropertyInfoMap(Map<String, PropertyInfo> propertyInfoMap) {
        this.propertyInfoMap = propertyInfoMap;
    }
    
    @Override
    public Map<String, EventInfo> getEventInfoMap() {
        return this.eventInfoMap;
    }
    
    void setEventInfoMap(Map<String, EventInfo> eventInfoMap) {
        this.eventInfoMap = eventInfoMap;
    }
    
    @Override
    public PropertyInfo getDefaultPropertyInfo() {
        String defaultPropertyName = null;
        int index = this.beanInfo.getDefaultPropertyIndex();
        if (index >= 0) {
            defaultPropertyName = this.beanInfo.getPropertyDescriptors()[index].getName();
        } else {
            try {
                if (Class.forName("javax.faces.component.ValueHolder").isAssignableFrom(
                        this.getBeanInfo().getBeanDescriptor().getBeanClass()))
                    defaultPropertyName = "value";
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (defaultPropertyName == null)
            return null;
        return this.getPropertyInfoMap().get(defaultPropertyName);
    }
    
    @Override
    public EventInfo getDefaultEventInfo() {
        String defaultEventName = null;
        int index = this.beanInfo.getDefaultEventIndex();
        if (index >= 0) {
            defaultEventName = this.beanInfo.getEventSetDescriptors()[index].getName();
        } else {
            try {
                Class beanClass = this.getBeanInfo().getBeanDescriptor().getBeanClass();
                if (Class.forName("javax.faces.component.ActionSource").isAssignableFrom(beanClass))
                    defaultEventName = "action";
                else if (Class.forName("javax.faces.component.EditableValueHolder").isAssignableFrom(beanClass))
                    defaultEventName = "valueChange";
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (defaultEventName == null)
            return null;
        return this.getEventInfoMap().get(defaultEventName);
    }
    
    Set<String> methodNameSet;
    
    @Override
    Set<String> getMethodNameSet() {
        if (this.methodNameSet == null) {
            this.methodNameSet = new HashSet<String>();
            for (MethodDescriptor method : this.getBeanInfo().getMethodDescriptors())
                this.methodNameSet.add(method.getName());
        }
        return this.methodNameSet;
    }
    
    Set<CategoryDescriptor> categoryDescriptors;

    @Override
    public Name getQualifiedName() {
        return new NameInfo(beanInfo.getBeanDescriptor().getBeanClass().getName());
    }

    @Override
    public TypeMirror asType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.CLASS;
    }

    @Override
    public Set<Modifier> getModifiers() {
        beanInfo.getBeanDescriptor().getName();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Name getSimpleName() {
        return new NameInfo(beanInfo.getBeanDescriptor().getBeanClass().getSimpleName());
    }

    @Override
    public Element getEnclosingElement() {
        beanInfo.getBeanDescriptor().getBeanClass().getPackage();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return beanInfo.getBeanDescriptor().getBeanClass().getAnnotation(annotationType);
    }

    @Override
    public <R, P> R accept(ElementVisitor<R, P> v, P p) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
        return beanInfo.getBeanDescriptor().getBeanClass().getAnnotationsByType(annotationType);
    }
    
    
    
}
