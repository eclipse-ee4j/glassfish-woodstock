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

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.Filer;
import java.io.File;
import java.io.PrintWriter;
import org.apache.velocity.app.VelocityEngine;

/**
 *
 * @author gjmurphy
 */
public class GeneratorFactory {
    
    VelocityEngine velocityEngine;
    
    public GeneratorFactory() {
        this.velocityEngine = new VelocityEngine();
        this.velocityEngine.addProperty("resource.loader", "classpath");
        this.velocityEngine.addProperty("classpath.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        try {
            this.velocityEngine.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public FacesConfigFileGenerator getFacesConfigFileGenerator() {
        return new FacesConfigFileGeneratorImpl(this.velocityEngine);
    }
    
    public BeanInfoSourceGenerator getBeanInfoSourceGenerator() {
        return new BeanInfoSourceGeneratorImpl(this.velocityEngine);
    }
    
    public TagLibFileGenerator getTagLibFileGenerator() {
        return new TagLibFileGeneratorImpl(this.velocityEngine);
    }
    
    public TagSourceGenerator getTagSourceGenerator() {
        return new TagSourceGeneratorImpl(this.velocityEngine);
    }
    
    public DebugGenerator getDebugGenerator() {
        return new DebugGenerator(this.velocityEngine);
    }
    
}
