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

package com.sun.faces.mirror.generator;

import com.sun.faces.mirror.CategoryInfo;
import com.sun.faces.mirror.ClassInfo;
import com.sun.faces.mirror.DeclaredComponentInfo;
import com.sun.faces.mirror.EventInfo;
import com.sun.faces.mirror.PropertyBundleMap;
import com.sun.faces.mirror.PropertyInfo;
import java.io.Writer;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 *
 * @author gjmurphy
 */
class BeanInfoSourceGeneratorImpl extends BeanInfoSourceGenerator{
    
    final static String TEMPLATE = "com/sun/faces/mirror/generator/BeanInfoSource.template";
    
    VelocityEngine velocityEngine;
    
    public BeanInfoSourceGeneratorImpl(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    
    @Override
    public void generate() throws GeneratorException {
        try {
            DeclaredComponentInfo componentInfo = this.getDeclaredComponentInfo();
            String namespace = this.getNamespace();
            String namespacePrefix = this.getNamespacePrefix();
            VelocityContext velocityContext = new VelocityContext();
            ClassInfo superClassInfo = componentInfo.getSuperClassInfo();
            Collection<PropertyInfo> propertyInfos = new ArrayList<PropertyInfo>();
            propertyInfos.addAll(componentInfo.getPropertyInfoMap().values());
            Collection<EventInfo> eventInfos = new ArrayList<EventInfo>();
            eventInfos.addAll(componentInfo.getEventInfoMap().values());
            if (superClassInfo != null) {
                if (DeclaredComponentInfo.class.isAssignableFrom(superClassInfo.getClass())) {
                    DeclaredComponentInfo declaredSuperClassInfo = (DeclaredComponentInfo) superClassInfo;
                    // If super class is a component in this compilation unit, instruct
                    // template to add a call to fetch properties and events from the parent
                    // BeanInfo
                    if (declaredSuperClassInfo.getPropertyInfoMap().size() > 0 ||
                            declaredSuperClassInfo.getInheritedPropertyInfoMap().size() > 0)
                        velocityContext.put("fetchSuperClassPropertyInfo", Boolean.TRUE);
                    if (declaredSuperClassInfo.getEventInfoMap().size() > 0 ||
                            declaredSuperClassInfo.getInheritedEventInfoMap().size() > 0)
                        velocityContext.put("fetchSuperClassEventInfo", Boolean.TRUE);
                } else {
                    // If super class is in an external library or is not a component,
                    // make sure inherited properties and events are declared along side the
                    // properties delared in this class
                    propertyInfos.addAll(componentInfo.getInheritedPropertyInfoMap().values());
                    eventInfos.addAll(componentInfo.getInheritedEventInfoMap().values());
                }
            }
            SortedSet<CategoryInfo> categoryInfoSet = new TreeSet<CategoryInfo>();
            ClassInfo classInfo = componentInfo;
            while (classInfo != null) {
                for (PropertyInfo propertyInfo : classInfo.getPropertyInfoMap().values()) {
                    CategoryInfo categoryInfo = propertyInfo.getCategoryInfo();
                    if (categoryInfo != null)
                        categoryInfoSet.add(categoryInfo);
                }
                classInfo = classInfo.getSuperClassInfo();
            }
            if (this.getPropertyBundleMap() != null) {
                // This generator has been passed a propertyBundleMap in which to put localizable properties
                PropertyBundleMap propertyBundleMap = this.getPropertyBundleMap();
                propertyBundleMap.put(componentInfo.getKey("displayName"), componentInfo.getDisplayName());
                propertyBundleMap.put(componentInfo.getKey("shortDescription"), componentInfo.getShortDescription());
                for (PropertyInfo propertyInfo : componentInfo.getPropertyInfoMap().values()) {
                    propertyBundleMap.put(propertyInfo.getKey("displayName"), propertyInfo.getDisplayName());
                    propertyBundleMap.put(propertyInfo.getKey("shortDescription"), propertyInfo.getShortDescription());
                }
                for (PropertyInfo propertyInfo : componentInfo.getInheritedPropertyInfoMap().values()) {
                    if (!(propertyInfo.getDeclaringClassInfo() instanceof DeclaredComponentInfo)) {
                        propertyBundleMap.put(propertyInfo.getKey("displayName"), propertyInfo.getDisplayName());
                        propertyBundleMap.put(propertyInfo.getKey("shortDescription"), propertyInfo.getShortDescription());
                    }
                }
                velocityContext.put("resourceBundle", propertyBundleMap.getQualifiedName());
            }
            velocityContext.put("date", DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
            velocityContext.put("beanInfoPackage", getPackageName());
            velocityContext.put("beanInfoClass", getClassName());
            velocityContext.put("componentInfo", componentInfo);
            velocityContext.put("propertyInfoSet", propertyInfos);
            velocityContext.put("categoryInfoSet", categoryInfoSet);
            velocityContext.put("eventInfoSet", eventInfos);
            velocityContext.put("namespace", namespace == null ? "" : namespace);
            velocityContext.put("namespacePrefix", namespacePrefix == null ? "" : namespacePrefix);
            Template template = velocityEngine.getTemplate(TEMPLATE);
            Writer printWriter = this.getPrintWriter();
            template.merge(velocityContext, printWriter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneratorException(e);
        }
    }
    
    @Override
            public String getPackageName() {
        return this.getDeclaredComponentInfo().getPackageName();
    }
    
    @Override
            public String getClassName() {
        return this.getDeclaredComponentInfo().getClassName() + "BeanInfoBase";
    }
    
}
