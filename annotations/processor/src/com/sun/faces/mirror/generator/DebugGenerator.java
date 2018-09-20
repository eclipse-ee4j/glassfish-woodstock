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

import com.sun.faces.mirror.ClassInfo;
import com.sun.faces.mirror.DeclaredComponentInfo;
import com.sun.faces.mirror.DeclaredRendererInfo;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 *
 * @author gjmurphy
 */
public class DebugGenerator extends Generator {
    
    final static String TEMPLATE = "com/sun/faces/mirror/generator/Debug.template";
    
    VelocityEngine velocityEngine;
    
    public DebugGenerator(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    
    @Override
    public void generate() throws GeneratorException {
        try {
            VelocityContext velocityContext = new VelocityContext();
            String namespace = this.getNamespace();
            String namespacePrefix = this.getNamespacePrefix();
            velocityContext.put("packageNameSet", this.getPackageNameSet());
            velocityContext.put("componentInfoSet", this.getDeclaredComponentInfoSet());
            velocityContext.put("rendererInfoSet", this.getDeclaredRendererInfoSet());
            velocityContext.put("namespace", namespace == null ? "" : namespace);
            velocityContext.put("namespacePrefix", namespacePrefix == null ? "" : namespacePrefix);
            Template template = velocityEngine.getTemplate(TEMPLATE);
            PrintWriter printWriter = this.getPrintWriter();
            template.merge(velocityContext, printWriter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneratorException(e);
        }
    }

    private String namespace;

    /**
     * Protected getter for property namespace.
     */
    protected String getNamespace() {
        return this.namespace;
    }

    /**
     * Setter for property namespace.
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    private String namespacePrefix;

    /**
     * Protected getter for property namespacePrefix.
     * @return Value of property namespacePrefix.
     */
    protected String getNamespacePrefix() {
        return this.namespacePrefix;
    }

    /**
     * Setter for property namespacePrefix.
     */
    public void setNamespacePrefix(String namespacePrefix) {
        this.namespacePrefix = namespacePrefix;
    }
    
    
    private Set<DeclaredComponentInfo> declaredComponentInfoSet;
    
    /**
     * Protected getter for property declaredComponentInfoSet.
     */
    protected Set<DeclaredComponentInfo> getDeclaredComponentInfoSet() {
        TreeSet<DeclaredComponentInfo> sortedSet = new TreeSet<DeclaredComponentInfo>(new ClassInfoComparator());
        sortedSet.addAll(this.declaredComponentInfoSet);
        return sortedSet;
    }
    
    /**
     * Setter for property declaredComponentInfoSet.
     */
    public void setDeclaredComponentInfoSet(Set<DeclaredComponentInfo> declaredComponentInfoSet) {
        this.declaredComponentInfoSet = declaredComponentInfoSet;
    }
    
    private Set<DeclaredRendererInfo> declaredRendererInfoSet;
    
    /**
     * Protected getter for property declaredRendererInfoSet.
     */
    protected Set<DeclaredRendererInfo> getDeclaredRendererInfoSet() {
        TreeSet<DeclaredRendererInfo> sortedSet = new TreeSet<DeclaredRendererInfo>(new ClassInfoComparator());
        sortedSet.addAll(this.declaredRendererInfoSet);
        return sortedSet;
    }
    
    /**
     * Setter for property declaredRendererInfoSet.
     */
    public void setDeclaredRendererInfoSet(Set<DeclaredRendererInfo> declaredRendererInfoSet) {
        this.declaredRendererInfoSet = declaredRendererInfoSet;
    }

    private Set<String> packageNameSet;

    /**
     * Getter for property packageNameSet.
     */
    protected Set<String> getPackageNameSet() {
        TreeSet<String> sortedSet = new TreeSet<String>();
        sortedSet.addAll(this.packageNameSet);
        return sortedSet;
    }

    /**
     * Setter for property packageNameSet.
     */
    public void setPackageNameSet(Set<String> packageNameSet) {
        this.packageNameSet = packageNameSet;
    }
    
    
    static class ClassInfoComparator implements Comparator {
        
        public int compare(Object obj1, Object obj2) {
            return ((ClassInfo)obj1).getQualifiedName().compareTo(((ClassInfo)obj2).getClassName());
        }
        
    }
    
}
