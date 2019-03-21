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
    private String namespacePrefix;
    private Set<DeclaredComponentInfo> compInfos;
    private String namespace;
    private Set<DeclaredRendererInfo> rendererInfos;
    private Set<String> packageNames;

    public DebugGenerator(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    @Override
    public void generate() throws GeneratorException {
        try {
            VelocityContext velocityContext = new VelocityContext();
            String ns = this.getNamespace();
            String nsPrefix = this.getNamespacePrefix();
            velocityContext.put("packageNameSet", this.getPackageNames());
            velocityContext.put("componentInfoSet", this.getDeclaredComponentInfos());
            velocityContext.put("rendererInfoSet", this.getDeclaredRendererInfos());
            velocityContext.put("namespace", ns == null ? "" : ns);
            velocityContext.put("namespacePrefix", nsPrefix == null ? "" : nsPrefix);
            Template template = velocityEngine.getTemplate(TEMPLATE);
            PrintWriter printWriter = this.getPrintWriter();
            template.merge(velocityContext, printWriter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneratorException(e);
        }
    }

    /**
     * Protected getter for property namespace.
     * @return namespace
     */
    protected String getNamespace() {
        return this.namespace;
    }

    /**
     * Setter for property namespace.
     * @param newNs new namespace
     */
    public void setNamespace(String newNs) {
        this.namespace = newNs;
    }

    /**
     * Protected getter for property namespacePrefix.
     *
     * @return Value of property namespacePrefix.
     */
    protected String getNamespacePrefix() {
        return this.namespacePrefix;
    }

    /**
     * Setter for property namespacePrefix.
     * @param newNsPrefix new namespace prefix
     */
    public void setNamespacePrefix(String newNsPrefix) {
        this.namespacePrefix = newNsPrefix;
    }

    /**
     * Protected getter for property declaredComponentInfoSet.
     * @return set of component info
     */
    @SuppressWarnings("unchecked")
    protected Set<DeclaredComponentInfo> getDeclaredComponentInfos() {
        TreeSet<DeclaredComponentInfo> sortedSet =
                new TreeSet<DeclaredComponentInfo>(new ClassInfoComparator());
        sortedSet.addAll(this.compInfos);
        return sortedSet;
    }

    /**
     * Setter for property declaredComponentInfoSet.
     * @param newCompInfos new component info
     */
    public void setDeclaredComponentInfo(
            Set<DeclaredComponentInfo> newCompInfos) {

        this.compInfos = newCompInfos;
    }

    /**
     * Protected getter for property declaredRendererInfoSet.
     * @return set of renderer info
     */
    @SuppressWarnings("unchecked")
    protected Set<DeclaredRendererInfo> getDeclaredRendererInfos() {
        TreeSet<DeclaredRendererInfo> sortedSet =
                new TreeSet<DeclaredRendererInfo>(new ClassInfoComparator());
        sortedSet.addAll(this.rendererInfos);
        return sortedSet;
    }

    /**
     * Setter for property declaredRendererInfoSet.
     * @param newRendererInfos new set of renderer info
     */
    public void setDeclaredRendererInfos(
            Set<DeclaredRendererInfo> newRendererInfos) {

        this.rendererInfos = newRendererInfos;
    }

    /**
     * Getter for property packageNameSet.
     * @return set of package name
     */
    protected Set<String> getPackageNames() {
        TreeSet<String> sortedSet = new TreeSet<String>();
        sortedSet.addAll(this.packageNames);
        return sortedSet;
    }

    /**
     * Setter for property packageNameSet.
     * @param newPackageNames new set of package name
     */
    public void setPackageNames(Set<String> newPackageNames) {
        this.packageNames = newPackageNames;
    }

    static class ClassInfoComparator implements Comparator {

        @Override
        public int compare(Object obj1, Object obj2) {
            return ((ClassInfo) obj1).getQualifiedName()
                    .compareTo(((ClassInfo) obj2).getClassName());
        }
    }
}
