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

import java.beans.EventSetDescriptor;
import java.lang.reflect.Method;

/**
 *
 * @author gjmurphy
 */
public class IntrospectedEventInfo extends EventInfo {
    
    EventSetDescriptor eventDescriptor;
    
    IntrospectedEventInfo(EventSetDescriptor eventDescriptor) {
        this.eventDescriptor = eventDescriptor;
    }

    public String getName() {
        return this.eventDescriptor.getName();
    }

    public String getDisplayName() {
        return this.eventDescriptor.getDisplayName();
    }

    public String getShortDescription() {
        return this.eventDescriptor.getShortDescription();
    }

    public String getAddListenerMethodName() {
        if (this.eventDescriptor.getAddListenerMethod() != null)
            return this.eventDescriptor.getAddListenerMethod().getName();
        return null;
    }

    public String getRemoveListenerMethodName() {
        if (this.eventDescriptor.getRemoveListenerMethod() != null)
            return this.eventDescriptor.getRemoveListenerMethod().getName();
        return null;
    }

    public String getGetListenersMethodName() {
        if (this.eventDescriptor.getGetListenerMethod() != null)
            return this.eventDescriptor.getGetListenerMethod().getName();
        return null;
    }
    
    String listenerMethodSignature;

    public String getListenerMethodSignature() {
        if (this.listenerMethodSignature == null) {
            StringBuffer buffer = new StringBuffer();
            Method listenerMethod = this.eventDescriptor.getListenerMethods()[0];
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
            this.listenerMethodSignature = buffer.toString();
        }
        return this.listenerMethodSignature;
    }

    public String getListenerClassName() {
        return this.eventDescriptor.getListenerType().getName();
    }
    
    String[] listenerMethodParameterClassNames;
    
    public String[] getListenerMethodParameterClassNames() {
        if (listenerMethodParameterClassNames == null) {
            Class[] paramClasses = 
                    this.eventDescriptor.getListenerMethods()[0].getParameterTypes();
            listenerMethodParameterClassNames = new String[paramClasses.length];
            for (int i = 0; i < paramClasses.length; i++)
                listenerMethodParameterClassNames[i] = paramClasses[i].getName();
        }
        return listenerMethodParameterClassNames;
    }

    public String getListenerMethodName() {
        return this.eventDescriptor.getListenerMethods()[0].getName();
    }

    public boolean isHidden() {
        return this.eventDescriptor.isHidden();
    }

}
