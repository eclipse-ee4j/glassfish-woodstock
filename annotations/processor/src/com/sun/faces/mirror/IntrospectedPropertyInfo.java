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

import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.rave.designtime.Constants;
import com.sun.rave.designtime.markup.AttributeDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Represents a property of a class from a dependant library, discovered using
 * introspection.
 *
 * @author gjmurphy
 */
public class IntrospectedPropertyInfo extends PropertyInfo {
    
    PropertyDescriptor propertyDescriptor;
    
    IntrospectedPropertyInfo(PropertyDescriptor propertyDescriptor) {
        this.propertyDescriptor = propertyDescriptor;
    }
    
    public PropertyDescriptor getPropertyDescriptor() {
        return this.propertyDescriptor;
    }

    public String getName() {
        return this.propertyDescriptor.getName();
    }
    
    public String getInstanceName() {
        String name = this.getName();
        if (PropertyInfo.JAVA_KEYWORD_PATTERN.matcher(name).matches())
            return "_" + name;
        return name;
    }

    public String getType() {
        return this.propertyDescriptor.getPropertyType().getName();
    }

    public String getWriteMethodName() {
        Method method = this.propertyDescriptor.getWriteMethod();
        if (method == null)
            return null;
        return method.getName();
    }

    public String getReadMethodName() {
        Method method = this.propertyDescriptor.getReadMethod();
        if (method == null)
            return null;
        return method.getName();
    }

    public String getShortDescription() {
        return this.propertyDescriptor.getShortDescription();
    }

    public String getDisplayName() {
        return this.propertyDescriptor.getDisplayName();
    }
    
    public boolean isHidden() {
        return this.propertyDescriptor.isHidden();
    }

    public String getEditorClassName() {
        Class editorClass = this.propertyDescriptor.getPropertyEditorClass();
        if (editorClass == null)
            return null;
        return editorClass.getName();
    }
    
    private CategoryInfo categoryInfo;
    
    public CategoryInfo getCategoryInfo() {
        return this.categoryInfo;
    }
    
    void setCategoryInfo(CategoryInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }
    
    String getCategoryReferenceName() {
        if (this.propertyDescriptor.getValue(Constants.PropertyDescriptor.CATEGORY) != null)
            return ((CategoryDescriptor) this.propertyDescriptor.getValue(Constants.PropertyDescriptor.CATEGORY)).getName();
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PropertyInfo))
            return false;
        PropertyInfo that = (PropertyInfo) obj;
        if (!this.getName().equals(that.getName()))
            return false;
        String thisReadName = this.getReadMethodName();
        String thatReadName = that.getReadMethodName();
        if (thisReadName != null && (thatReadName == null || !thatReadName.equals(thisReadName)))
            return false;
        if (thatReadName == null && thatReadName != null)
            return false;
        String thisWriteName = this.getReadMethodName();
        String thatWriteName = that.getReadMethodName();
        if (thisWriteName != null && (thatWriteName == null || !thatWriteName.equals(thisWriteName)))
            return false;
        if (thatWriteName == null && thatWriteName != null)
            return false;
        return true;
    }

    public AttributeInfo getAttributeInfo() {
        AttributeDescriptor attributeDescriptor =
                (AttributeDescriptor) this.propertyDescriptor.getValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR);
        if (attributeDescriptor == null)
            return null;
        return new IntrospectedAttributeInfo(attributeDescriptor);
    }
    
}
