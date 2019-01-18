/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2019 Payara Service Ltd.
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

/**
 *
 * @author gjmurphy
 */
public class DeclaredEventInfo extends EventInfo {
    
    static final String NAME = "name";
    static final String DISPLAY_NAME = "displayName";
    static final String SHORT_DESCRIPTION = "shortDescription";
    static final String ADD_LISTENER_METHOD_NAME = "addListenerMethodName";
    static final String REMOVE_LISTENER_METHOD_NAME = "removeListenerMethodName";
    static final String GET_LISTENERS_METHOD_NAME = "getListenersMethodName";
    static final String IS_DEFAULT = "isDefault";
    
    Element decl;
    Map<String,Object> annotationValueMap;
    
    private String name;
    private Element listenerDeclaration;
    private String addListenerMethodName;
    private String removeListenerMethodName;
    private String[] listenerMethodParameterClassNames;
    private String listenerMethodName;
    private String getListenersMethodName;
    
    DeclaredEventInfo(Map<String,Object> annotationValueMap, Element decl) {
        this.annotationValueMap = annotationValueMap;
        this.decl = decl;
    }
    
    public Element getDeclaration() {
        return this.decl;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getDisplayName() {
        return this.getName();
    }
    
    @Override
    public String getShortDescription() {
        return this.getDisplayName();
    }
    
    @Override
    public boolean isHidden() {
        return false;
    }
    
    @Override
    public String getAddListenerMethodName() {
        if (this.addListenerMethodName == null)
            this.addListenerMethodName = (String) this.annotationValueMap.get(ADD_LISTENER_METHOD_NAME);
        return this.addListenerMethodName;
    }
    
    void setAddListenerMethodName(String addMethodName) {
        this.addListenerMethodName = addMethodName;
    }
    
    @Override
    public String getRemoveListenerMethodName() {
        if (this.removeListenerMethodName == null)
            this.removeListenerMethodName = (String) this.annotationValueMap.get(REMOVE_LISTENER_METHOD_NAME);
        return this.removeListenerMethodName;
    }
    
    void setRemoveListenerMethodName(String removeMethodName) {
        this.removeListenerMethodName = removeMethodName;
    }
    
    @Override
    public String getGetListenersMethodName() {
        if (this.getListenersMethodName == null)
            this.getListenersMethodName = (String) this.annotationValueMap.get(GET_LISTENERS_METHOD_NAME);
        return this.getListenersMethodName;
    }
    
    void setGetListenersMethodName(String getMethodName) {
        this.getListenersMethodName = getMethodName;
    }
    
    @Override
    public String getListenerMethodSignature() {
        StringBuilder buffer = new StringBuilder();
        if (this.getListenerDeclaration() != null) {
            
            ExecutableElement listenerMethodDecl = (ExecutableElement) listenerDeclaration.getEnclosedElements().iterator().next();
            buffer.append(listenerMethodDecl.getReturnType().toString());
            buffer.append(" ");
            buffer.append(listenerMethodDecl.getSimpleName());
            buffer.append("(");
            for (VariableElement paramDecl : listenerMethodDecl.getParameters()) {
                buffer.append(paramDecl.asType().toString());
                buffer.append(",");
            }
            buffer.setLength(buffer.length() - 1);
            buffer.append(")");
        } else if (this.getListenerClass() != null) {
            Method listenerMethod = this.getListenerClass().getMethods()[0];
            buffer.append(listenerMethod.getReturnType().toString());
            buffer.append(" ");
            buffer.append(listenerMethod.getName());
            buffer.append("(");
            for (Class paramClass : listenerMethod.getParameterTypes()) {
                buffer.append(paramClass.getName());
                buffer.append(",");
            }
            buffer.setLength(buffer.length() - 1);
            buffer.append(")");
        }
        return buffer.toString();
    }
    
    public Element getListenerDeclaration() {
        return this.listenerDeclaration;
    }
    
    void setListenerDeclaration(Element listenerDeclaration) {
        this.listenerDeclaration = listenerDeclaration;
    }
    
    private Class listenerClass;
    
    public Class getListenerClass() {
        return this.listenerClass;
    }
    
    void setListenerClass(Class listenerClass) {
        this.listenerClass = listenerClass;
    }
    
    @Override
    public String getListenerClassName() {
        if (this.getListenerDeclaration() != null) {
            return this.getListenerDeclaration().asType().toString();
        } else if (this.getListenerClass() != null) {
            return this.getListenerClass().getName();
        }
        return null;
    }
    
    @Override
    public String getListenerMethodName() {
        if (listenerMethodName == null) {
            if (this.getListenerDeclaration() != null) {
                ExecutableElement listenerMethodDecl = (ExecutableElement) listenerDeclaration.getEnclosedElements().iterator().next();
                listenerMethodName = listenerMethodDecl.getSimpleName().toString();
            } else if (this.getListenerClass() != null) {
                listenerMethodName = this.getListenerClass().getMethods()[0].getName();
            }
        }
        return listenerMethodName;
    }
    
    @Override
    public String[] getListenerMethodParameterClassNames() {
        if (listenerMethodParameterClassNames == null) {
            ArrayList<String> paramNameList = new ArrayList<String>();
            if (this.getListenerDeclaration() != null) {
                for (VariableElement paramDecl : ((ExecutableElement) listenerDeclaration.getEnclosedElements().iterator().next()).getParameters())
                    paramNameList.add(paramDecl.asType().toString());
            } else if (this.getListenerClass() != null) {
                for (Class paramClass: this.getListenerClass().getMethods()[0].getParameterTypes())
                    paramNameList.add(paramClass.getName());
            }
            listenerMethodParameterClassNames = paramNameList.toArray(new String[paramNameList.size()]);
        }
        return listenerMethodParameterClassNames;
    }
    
}
