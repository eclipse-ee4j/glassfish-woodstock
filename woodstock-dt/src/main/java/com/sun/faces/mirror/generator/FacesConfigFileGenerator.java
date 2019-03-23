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
import com.sun.faces.mirror.DeclaredRendererInfo;
import java.util.Set;

/**
 * Base generator for the faces configuration file.
 *
 * @author gjmurphy
 */
abstract public class FacesConfigFileGenerator extends Generator {

    private Set<DeclaredComponentInfo> declaredComponentInfos;
    private Set<DeclaredRendererInfo> declaredRendererInfos;
    private Set<String> declaredPropertyResolverNames;
    private Set<String> declaredVariableResolverNames;
    private Set<String> declaredJavaEEResolverName;

    /**
     * Protected getter for property declaredComponentInfoSet.
     *
     * @return set of component info
     */
    protected Set<DeclaredComponentInfo> getDeclaredComponentInfos() {
        return this.declaredComponentInfos;
    }

    /**
     * Setter for property declaredComponentInfoSet.
     *
     * @param newCompInfos new set of component info
     */
    public void setDeclaredComponentInfos(
            Set<DeclaredComponentInfo> newCompInfos) {

        this.declaredComponentInfos = newCompInfos;
    }

    /**
     * Protected getter for property declaredRendererInfoSet.
     *
     * @return set of renderer info
     */
    protected Set<DeclaredRendererInfo> getDeclaredRendererInfos() {
        return this.declaredRendererInfos;
    }

    /**
     * Setter for property declaredRendererInfoSet.
     *
     * @param newRendererInfos
     */
    public void setDeclaredRendererInfos(
            Set<DeclaredRendererInfo> newRendererInfos) {

        this.declaredRendererInfos = newRendererInfos;
    }

    /**
     * Protected getter for property declaredPropertyResolverNameSet.
     *
     * @return Value of property declaredPropertyResolverNameSet.
     */
    protected Set<String> getDeclaredPropertyResolverNames() {
        return this.declaredPropertyResolverNames;
    }

    /**
     * Setter for property declaredPropertyResolverNameSet.
     *
     * @param newPropertyResolverNames New value of property
     * declaredPropertyResolverNameSet.
     */
    public void setDeclaredPropertyResolverNames(
            Set<String> newPropertyResolverNames) {

        this.declaredPropertyResolverNames = newPropertyResolverNames;
    }

    /**
     * Protected getter for Variable declaredVariableResolverNameSet.
     *
     * @return Value of Variable declaredVariableResolverNameSet.
     */
    protected Set<String> getDeclaredVariableResolverNameSet() {
        return this.declaredVariableResolverNames;
    }

    /**
     * Setter for Variable declaredVariableResolverNameSet.
     *
     * @param newVariableResolverNames New value of Variable
     * declaredVariableResolverNameSet.
     */
    public void setDeclaredVariableResolverNames(
            Set<String> newVariableResolverNames) {

        this.declaredVariableResolverNames = newVariableResolverNames;
    }

    /**
     * Protected getter for JavaEE declaredJavaEEResolverNameSet.
     *
     * @return Value of JavaEE declaredJavaEEResolverNameSet.
     */
    protected Set<String> getDeclaredJavaEEResolverNames() {
        return this.declaredJavaEEResolverName;
    }

    /**
     * Setter for JavaEE declaredJavaEEResolverNameSet.
     *
     * @param newJavaEEResolverNames New value of JavaEE
     * declaredJavaEEResolverNameSet.
     */
    public void setDeclaredJavaEEResolverNames(
            Set<String> newJavaEEResolverNames) {

        this.declaredJavaEEResolverName = newJavaEEResolverNames;
    }
}
