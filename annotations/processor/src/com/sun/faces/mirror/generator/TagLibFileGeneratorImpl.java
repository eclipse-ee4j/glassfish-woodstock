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
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * An implementation of TagLibFileGenerator that creates the taglib file by
 * merging component info with a template file.
 * 
 * @author gjmurphy
 */
class TagLibFileGeneratorImpl extends TagLibFileGenerator {
    
    static final String TEMPLATE = "com/sun/faces/mirror/generator/TagLib.template";
    
    VelocityEngine velocityEngine;
    
    
    TagLibFileGeneratorImpl(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    
    @Override
    public void generate() throws GeneratorException {
        try {
            Set<DeclaredComponentInfo> componentInfoSet = new TreeSet(new Comparator() {
                public int compare(Object obj1, Object obj2) {
                    String tag1 = ((DeclaredComponentInfo) obj1).getTagName();
                    String tag2 = ((DeclaredComponentInfo) obj2).getTagName();
                    if (tag1 == null) {
                        if (tag2 == null)
                            return 0;
                        return -1;
                    } else {
                        return tag1.compareTo(tag2);
                    }
                }
            });
            componentInfoSet.addAll(this.getDeclaredComponentInfoSet());
            String namespace = this.getNamespace();
            String namespacePrefix = this.getNamespacePrefix();
            PrintWriter printWriter = this.getPrintWriter();
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("date", DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
            velocityContext.put("componentInfoSet", componentInfoSet);
            velocityContext.put("namespace", namespace == null ? "" : namespace);
            velocityContext.put("namespacePrefix", namespacePrefix == null ? "" : namespacePrefix);
            Template template = this.velocityEngine.getTemplate(TEMPLATE);
            template.merge(velocityContext, printWriter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneratorException(e);
        }
    }
    
    @Override
    public String getFileName() {
        return "taglib.xml";
    }
    
    @Override
    public String getDirectoryName() {
        return "META-INF";
    }
    
}
