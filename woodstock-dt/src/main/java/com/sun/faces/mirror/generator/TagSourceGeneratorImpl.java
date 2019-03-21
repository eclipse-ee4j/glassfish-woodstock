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
package com.sun.faces.mirror.generator;

import com.sun.faces.mirror.DeclaredComponentInfo;
import com.sun.faces.mirror.PropertyInfo;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 *
 * @author gjmurphy
 */
class TagSourceGeneratorImpl extends TagSourceGenerator {

    final static String TEMPLATE = "com/sun/faces/mirror/generator/TagSource.template";

    VelocityEngine velocityEngine;

    public TagSourceGeneratorImpl(VelocityEngine vEngine) {
        this.velocityEngine = vEngine;
    }

    @Override
    public void generate() throws GeneratorException {
        try {
            DeclaredComponentInfo componentInfo = this.getDeclaredComponentInfo();
            String namespace = this.getNamespace();
            String namespacePrefix = this.getNamespacePrefix();
            PrintWriter printWriter = this.getPrintWriter();
            Collection<PropertyInfo> propertyInfos = new ArrayList<PropertyInfo>();
            propertyInfos.addAll(componentInfo.getInheritedPropertyInfos().values());
            propertyInfos.addAll(componentInfo.getPropertyInfos().values());
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("date", DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
            velocityContext.put("tagPackage", getPackageName());
            velocityContext.put("tagClass", getClassName());
            velocityContext.put("componentInfo", componentInfo);
            velocityContext.put("propertyInfos", propertyInfos);
            velocityContext.put("namespace", namespace == null ? "" : namespace);
            velocityContext.put("namespacePrefix", namespacePrefix == null ? "" : namespacePrefix);
            Template template = this.velocityEngine.getTemplate(TEMPLATE);
            template.merge(velocityContext, printWriter);
            printWriter.flush();
            printWriter.close();
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
        return this.getDeclaredComponentInfo().getClassName() + "Tag";
    }

}
