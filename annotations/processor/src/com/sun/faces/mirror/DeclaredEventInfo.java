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

import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
    
    Declaration decl;
    Map<String,Object> annotationValueMap;
    
    DeclaredEventInfo(Map<String,Object> annotationValueMap, Declaration decl) {
        this.annotationValueMap = annotationValueMap;
        this.decl = decl;
    }
    
    public Declaration getDeclaration() {
        return this.decl;
    }
    
    private String name;
    
    public String getName() {
        return this.name;
    }
    
    void setName(String name) {
        this.name = name;
    }
    
    public String getDisplayName() {
        return this.getName();
    }
    
    public String getShortDescription() {
        return this.getDisplayName();
    }
    
    public boolean isHidden() {
        return false;
    }
    
    private String addListenerMethodName;
    
    public String getAddListenerMethodName() {
        if (this.addListenerMethodName == null)
            this.addListenerMethodName = (String) this.annotationValueMap.get(ADD_LISTENER_METHOD_NAME);
        return this.addListenerMethodName;
    }
    
    void setAddListenerMethodName(String addMethodName) {
        this.addListenerMethodName = addMethodName;
    }
    
    private String removeListenerMethodName;
    
    public String getRemoveListenerMethodName() {
        if (this.removeListenerMethodName == null)
            this.removeListenerMethodName = (String) this.annotationValueMap.get(REMOVE_LISTENER_METHOD_NAME);
        return this.removeListenerMethodName;
    }
    
    void setRemoveListenerMethodName(String removeMethodName) {
        this.removeListenerMethodName = removeMethodName;
    }
    
    private String getListenersMethodName;
    
    public String getGetListenersMethodName() {
        if (this.getListenersMethodName == null)
            this.getListenersMethodName = (String) this.annotationValueMap.get(GET_LISTENERS_METHOD_NAME);
        return this.getListenersMethodName;
    }
    
    void setGetListenersMethodName(String getMethodName) {
        this.getListenersMethodName = getMethodName;
    }
    
    public String getListenerMethodSignature() {
        StringBuffer buffer = new StringBuffer();
        if (this.getListenerDeclaration() != null) {
            MethodDeclaration listenerMethodDecl =
                    this.getListenerDeclaration().getMethods().iterator().next();
            buffer.append(listenerMethodDecl.getReturnType().toString());
            buffer.append(" ");
            buffer.append(listenerMethodDecl.getSimpleName());
            buffer.append("(");
            for (ParameterDeclaration paramDecl : listenerMethodDecl.getParameters()) {
                buffer.append(paramDecl.getType().toString());
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
    
    private TypeDeclaration listenerDeclaration;
    
    public TypeDeclaration getListenerDeclaration() {
        return this.listenerDeclaration;
    }
    
    void setListenerDeclaration(TypeDeclaration listenerDeclaration) {
        this.listenerDeclaration = listenerDeclaration;
    }
    
    private Class listenerClass;
    
    public Class getListenerClass() {
        return this.listenerClass;
    }
    
    void setListenerClass(Class listenerClass) {
        this.listenerClass = listenerClass;
    }
    
    public String getListenerClassName() {
        if (this.getListenerDeclaration() != null) {
            return this.getListenerDeclaration().getQualifiedName();
        } else if (this.getListenerClass() != null) {
            return this.getListenerClass().getName();
        }
        return null;
    }
    
    private String listenerMethodName;
    
    public String getListenerMethodName() {
        if (listenerMethodName == null) {
            if (this.getListenerDeclaration() != null) {
                MethodDeclaration listenerMethodDecl =
                        this.getListenerDeclaration().getMethods().iterator().next();
                listenerMethodName = listenerMethodDecl.getSimpleName();
            } else if (this.getListenerClass() != null) {
                listenerMethodName = this.getListenerClass().getMethods()[0].getName();
            }
        }
        return listenerMethodName;
    }
    
    private String[] listenerMethodParameterClassNames;
    
    public String[] getListenerMethodParameterClassNames() {
        if (listenerMethodParameterClassNames == null) {
            ArrayList<String> paramNameList = new ArrayList<String>();
            if (this.getListenerDeclaration() != null) {
                for (ParameterDeclaration paramDecl : this.getListenerDeclaration().getMethods().iterator().next().getParameters())
                    paramNameList.add(paramDecl.getType().toString());
            } else if (this.getListenerClass() != null) {
                for (Class paramClass: this.getListenerClass().getMethods()[0].getParameterTypes())
                    paramNameList.add(paramClass.getName());
            }
            listenerMethodParameterClassNames = paramNameList.toArray(new String[paramNameList.size()]);
        }
        return listenerMethodParameterClassNames;
    }
    
}
