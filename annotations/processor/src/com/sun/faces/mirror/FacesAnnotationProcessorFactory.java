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

package com.sun.faces.mirror;

import com.sun.faces.mirror.generator.GeneratorFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.tools.Diagnostic.Kind;

/**
 * The annotation processing factory that should be specified to the annotation
 * processing tool. The processor supports the following options (when passed to
 * the annotation processing tool an option must begin with the <code>-A</code>
 * prefix):
 *
 * <ul>
 * <li><b>{@code localize}</b> - Causes the processor to generate localizable source files.
 * If an element is localizable, a key will be generated for it and declared in a
 * bundle file, written to the same package as the generated source files.</li>
 * <li><b>{@code javaee.version ["1.4"|"5"]}</b> - Specify which version of JavaEE to
 * use when generating source code. The default is JavaEE 5. This option effects the
 * version of JSP and JSF used when generating tag classes and the taglib configuration
 * file. JavaEE 1.4 results in generated source based on JSF 1.1 and JSP 1.2; JavaEE 5 
 * results in source based on JSF 1.2 and JSP 2.1. [Not yet implemented].</li>
 * <li><b>{@code generate.runtime}</b> - Causes the processor to generate
 * run-time source and configuration code (the tag classes, the faces-config file,
 * and the tag library descriptor file).</li>
 * <li><b>{@code generate.designtime}</b>- Causes the processor to generate
 * design-time source code (the BeanInfo base classes).</li> 
 * <li><b>{@code namespace.uri <uri>}</b> - The URI of the namespace for the JSP tags defined
 * in the tag library.</li>
 * <li><b>{@code namespace.prefix <prefix>}</b> - The prefered namespace prefix for the JSP
 * tags defined in the tag library.</li>
 * <li><b>{@code generatorfactory <class>}</b> - The fully-qualified name of a class
 * that extends {@link com.sun.faces.mirror.generator.GeneratorFactory}, to be used to
 * create source code generators.
 * <li><b>{@code taglibdoc <file>}</b> - The name of a file that contains taglib
 * descriptions. Descriptions found in this file for tags and attributes are used in
 * the generated taglib, in lieu of the component and property descriptions.
 * <li><b>{@code debug}</b> - Causes a report of all components found to be printed
 * to stdout, useful for debugging.</li>
 * </ul>
 *
 * @deprecated All functionality is now included in {@link FacesAnnotationProcessor} 
 * for javac annotation processing. To remove.
 * @author gjmurphy
 */
// TODO - Implement javaee.version option.
@Deprecated
public class FacesAnnotationProcessorFactory  {
    
    private static final String LOCALIZE_OPTION = "localize";
    private static final String JAVAEE_VERSION_OPTION = "javaee.version";
    private static final String GENERATE_RUNTIME_OPTION = "generate.runtime";
    private static final String GENERATE_DESIGNTIME_OPTION = "generate.designtime";
    private static final String GENERATOR_FACTORY_OPTION = "generatorfactory";
    private static final String NAMESPACE_URI_OPTION = "namespace.uri";
    private static final String NAMESPACE_PREFIX_OPTION = "namespace.prefix";
    private static final String TAGLIB_DOC_OPTION = "taglibdoc";
    private static final String DEBUG_OPTION = "debug";
    
    private static final Pattern optionPattern = Pattern.compile("-A\\s*([^\\s=]+)\\s*=?([^\\s=]*)");
    
    private static final Collection<String> supportedOptions = Collections.unmodifiableList(Arrays.asList(new String[] {LOCALIZE_OPTION}));
    
    private static final Collection<String> supportedAnnotationTypes = Collections.unmodifiableList(Arrays.asList(new String[] {"*"}));
    
    public Collection<String> supportedAnnotationTypes() {
        return supportedAnnotationTypes;
    }
    
    public Collection<String> supportedOptions() {
        return supportedOptions;
    }
    
    public Processor getProcessorFor(Set<javax.annotation.processing.SupportedAnnotationTypes> declSet,
            ProcessingEnvironment env) {
        FacesAnnotationProcessor annotationProcessor = new FacesAnnotationProcessor(env);
        Map<String,String> optionMap = env.getOptions();
        // Process options passed to the annotation processor tool. The tool should be
        // doing this processing, but the apt released with JDK 5 does not. 
        // TODO - cross verify with calls to apt on other platforms, and outside of ant
        for (String key : optionMap.keySet()) {
            Matcher matcher = optionPattern.matcher(key);
            if (matcher.matches()) {
                String name = matcher.group(1);
                String value = matcher.group(2);
                switch (name) {
                    case LOCALIZE_OPTION:
                        annotationProcessor.setLocalize(true);
                        break;
                // TODO - Add support for different versions of JavaEE
                    case JAVAEE_VERSION_OPTION:
                        break;
                    case GENERATE_RUNTIME_OPTION:
                        annotationProcessor.setProcessRunTime(true);
                        break;
                    case GENERATE_DESIGNTIME_OPTION:
                        annotationProcessor.setProcessDesignTime(true);
                        break;
                    case GENERATOR_FACTORY_OPTION:
                        if (value == null || value.length() == 0)
                            env.getMessager().printMessage(Kind.ERROR, "Option " + GENERATOR_FACTORY_OPTION + " missing value");
                        try {
                            Class factoryClass = Class.forName(value);
                            if (!GeneratorFactory.class.isAssignableFrom(factoryClass))
                                env.getMessager().printMessage(Kind.ERROR, "Generator factory class must extend " + GeneratorFactory.class.toString());
                            else
                                annotationProcessor.setGeneratorFactoryClass(Class.forName(value));
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }   break;
                    case NAMESPACE_URI_OPTION:
                        if (value == null || value.length() == 0)
                            env.getMessager().printMessage(Kind.ERROR, "Option " + NAMESPACE_URI_OPTION + " missing value");
                        annotationProcessor.setNamespaceUri(value);
                        break;
                    case NAMESPACE_PREFIX_OPTION:
                        if (value == null || value.length() == 0)
                            env.getMessager().printMessage(Kind.ERROR, "Option " + NAMESPACE_PREFIX_OPTION + " missing value");
                        annotationProcessor.setNamespacePrefix(value);
                        break;
                    case TAGLIB_DOC_OPTION:
                        if (value == null || value.length() == 0)
                            env.getMessager().printMessage(Kind.ERROR, "Option " + TAGLIB_DOC_OPTION + " missing value");
                        annotationProcessor.setTaglibDoc(value);
                        break;
                    case DEBUG_OPTION:
                        annotationProcessor.setDebug(true);
                        break;
                    default:
                        break;
                }
            }
        }
        return annotationProcessor;
    }
    
}
