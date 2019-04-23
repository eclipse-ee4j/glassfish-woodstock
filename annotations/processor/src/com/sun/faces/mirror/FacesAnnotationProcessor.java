/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2018, 2019 Payara Services Ltd.
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

import com.sun.faces.annotation.*;
import com.sun.faces.mirror.DeclaredRendererInfo.RendersInfo;
import com.sun.faces.mirror.generator.*;
import javax.annotation.processing.ProcessingEnvironment;
import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.rave.designtime.Constants;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Completion;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor8;
import javax.tools.Diagnostic.Kind;
import javax.tools.StandardLocation;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * An annotation processor that generates JSF source and configuration files for
 * the current compilation unit, as reported by the annotation processor
 * environment.
 *
 * @author gjmurphy
 * @author jonathan coustick
 */
// TODO - Handle localization of property metadata inherited from external libraries
// TODO - Make all XxxInfo classes immutable when seen by generators
// TODO - Handle different versions of JSF (1.1 and 1.2)
// TODO - Handle attribute annotations within a tag class
@SupportedAnnotationTypes("com.sun.faces.annotation.*")
@SupportedOptions({"localize", "javaee.version", "generate.runtime", "generate.designtime", "namespace.uri", "namespace.prefix", "taglibdoc",  "debug"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class FacesAnnotationProcessor extends AbstractProcessor  {
    
    
    public static void main(String[] args) {
        System.out.println("Processor main");
        FacesAnnotationProcessor fap = new FacesAnnotationProcessor();
        System.out.println("new processor made");
    }
    
    // Properties
    private String namespaceUri;
    private String namespacePrefix;
    private boolean initialized = false;
    
    private Map<String, Writer> heldFiles = new HashMap<>();
    
    /** Set of all packages that define the current compilation unit */
    Set<String> packageNameSet = new HashSet<String>();
    /** Set of all JSF components declared in the current compilation unit */
    Set<DeclaredComponentInfo> declaredComponentSet = new HashSet<DeclaredComponentInfo>();
    /** Set of all JSF renderers declared in the current compilation unit */
    Set<DeclaredRendererInfo> declaredRendererSet = new HashSet<DeclaredRendererInfo>();
    /** A map of fully qualified class names to their class info, for all classes
     * in the current compilation unit */
    Map<String,DeclaredClassInfo> declaredClassMap = new HashMap<String,DeclaredClassInfo>();
    /** A map of fully qualified interface names to their interface info, for all interfaces
     * in the current compilation unit */
    Map<String,DeclaredInterfaceInfo> declaredInterfaceMap = new HashMap<String,DeclaredInterfaceInfo>();
    /** A map of component names to hand-authored tag classes, for all components
     * for which hand-authored tag classes were declared */
    Map<String,DeclaredClassInfo> declaredTagClassMap = new HashMap<String,DeclaredClassInfo>();
    /** A map of property category names to their category descriptor info */
    Map<String,CategoryInfo> categoryMap = new HashMap<String,CategoryInfo>();
    /** A set of all JSF property resolvers declared in the current compilation unit */
    Set<String> propertyResolverNameSet = new HashSet<String>();
    /** A set of all JSF variable resolvers declared in the current compilation unit */
    Set<String> variableResolverNameSet = new HashSet<String>();
    /** A set of all Java EE EL resolvers declared in the current compilation unit */
    Set<String> javaeeResolverNameSet = new HashSet<String>();
    
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
    
    public FacesAnnotationProcessor() {super();}
    
    /**
     * Create a new annotation processor for the processing environment specified.
     */
    public FacesAnnotationProcessor(ProcessingEnvironment env) {
        this();
        this.processingEnv = env;
        configureOptions();
    }
    
    @Override
    public void init(ProcessingEnvironment env) {
        super.init(env);
        this.processingEnv = env;
        configureOptions();
    }
    
    String getNamespaceUri() {
        return this.namespaceUri;
    }
    
    void setNamespaceUri(String namespaceUri) {
        this.namespaceUri = namespaceUri;
    }
    
    String getNamespacePrefix() {
        return this.namespacePrefix;
    }
    
    void setNamespacePrefix(String namespacePrefix) {
        this.namespacePrefix = namespacePrefix;
    }
    
    private String taglibDoc;
    
    String getTaglibDoc() {
        return this.taglibDoc;
    }
    
    void setTaglibDoc(String taglibDoc) {
        this.taglibDoc = taglibDoc;
    }
    
    private boolean localize;
    
    void setLocalize(boolean localize) {
        this.localize = localize;
    }
    
    private boolean processDesignTime;
    
    void setProcessDesignTime(boolean processDesignTime) {
        this.processDesignTime = processDesignTime;
    }
    
    private boolean processRunTime;
    
    void setProcessRunTime(boolean processRunTime) {
        this.processRunTime = processRunTime;
    }
    
    private Class generatorFactoryClass;
    
    Class getGeneratorFactoryClass() {
        return this.generatorFactoryClass;
    }
    
    void setGeneratorFactoryClass(Class generatorFactoryClass) {
        this.generatorFactoryClass = generatorFactoryClass;
    }
    
    private boolean debug;
    
    void setDebug(boolean debug) {
        this.debug = debug;
    }
    
    
    // Processor methods
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager envMessager = processingEnv.getMessager();
        envMessager.printMessage(Kind.NOTE, "Entered annotation processing...");
        
        //Are the supplied annotations ones that are supported by this?
        //if (!supportedAnnotations.containsAll(annotations)) {
        //    envMessager.printMessage(Kind.ERROR, "Processor does not support given annotations - " +  annotations.toString());
        //    return false;
        //}
        
        // Find all component classes in the compilation unit, creating a skeleton
        // ComponentInfo for each, then invoke a declaration classMemberVisitor that will
        // process all properties declared within each class or interface.
        visitComponentClasses(annotations, roundEnv);
        
        // Set super class info for all declared classes. If super class is in the current
        // compilation unit, then we have already seen it; if not, then it must be
        // introspected.
        setSuperClassInfo();
        
        // Now that all annotations have been read and the inheritance hierachy has
        // been established among all classes, update inherited and overridden property
        // metadata. This is also the time to check annotations that contain cross
        // references: properties may refer to category descriptors, and they may
        // also refer to events; components may refer to renderer types.
        updatePropertyMetadata();
        
        // Verify that all declared tag classes correspond to a component class in this
        // compilation unit
        verifyClasses();
        
        // Finally, if our toil hitherto has not been in vain, generate the source and config files
        generateSourceAndConfig();
        envMessager.printMessage(Kind.NOTE, "processing over");
        if (roundEnv.processingOver()) {
            envMessager.printMessage(Kind.NOTE, "cleaning handles...");
            cleanFileHandles();
        }
        
        return true;
    }
    
    /**
     * Find all component classes in the compilation unit, creating a skeleton
     * ComponentInfo for each, then invoke a declaration classMemberVisitor that will
     * process all properties declared within each class or interface.
     * @param annotations
     * @param roundEnv 
     */
    private void visitComponentClasses(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Kind.NOTE, "Visting component classes...");
        MemberDeclarationVisitor classMemberVisitor = new MemberDeclarationVisitor(this.processingEnv);
        classMemberVisitor.setCategoryMap(this.categoryMap);
        for (TypeElement typeAnnotation : annotations) {
            for (Element typeDecl : roundEnv.getElementsAnnotatedWith(typeAnnotation)) {

                this.packageNameSet.add(typeDecl.getEnclosingElement().getSimpleName().toString());
                if (true) {
                    DeclaredTypeInfo typeInfo = null;
                    classMemberVisitor.reset();
                    typeDecl.accept(classMemberVisitor, processingEnv);
                    switch (typeDecl.getKind()) {
                        case CLASS:
                            if (typeDecl.getAnnotation(Component.class) != null) {
                                // This is a component class
                                
                                
                                Map<String, Object> annotationValueMap = getAnnotationValueMap(typeDecl, Component.class.getName());
                                DeclaredComponentInfo componentInfo = new DeclaredComponentInfo(annotationValueMap, (TypeElement) typeDecl);
                                this.declaredComponentSet.add(componentInfo);
                                this.declaredClassMap.put(((TypeElement) typeDecl).getQualifiedName().toString(), componentInfo);
                                typeInfo = componentInfo;
                            } else if (typeDecl.getAnnotation(Renderer.class) != null) {
                                // This is a renderer class
                                Map<String, Object> annotationValueMap = getAnnotationValueMap(typeDecl, Renderer.class.getName());
                                DeclaredRendererInfo rendererInfo = new DeclaredRendererInfo(annotationValueMap, (TypeElement) typeDecl);
                                if (rendererInfo.getRenderings().isEmpty()) {
                                    this.processingEnv.getMessager().printMessage(Kind.WARNING, "No renderings declared in renderer annotation", typeDecl);
                                }
                                this.declaredRendererSet.add(rendererInfo);
                            } else if (typeDecl.getAnnotation(Tag.class) != null) {
                                // This is a hand-authored tag class
                                Map<String, Object> annotationValueMap = getAnnotationValueMap(typeDecl, Tag.class.getName());
                                String componentType = (String) annotationValueMap.get("componentType");
                                DeclaredClassInfo tagClassInfo = new DeclaredClassInfo((TypeElement) typeDecl);
                                this.declaredTagClassMap.put(componentType, tagClassInfo);
                            } else if (typeDecl.getAnnotation(Resolver.class) != null) {
                                // This is a JSF property or variable resolver, or a JavaEE EL resolver
                                TypeMirror superClassType = ((TypeElement) typeDecl).getSuperclass();
                                while (superClassType != null) {
                                    String superClassName = superClassType.toString();
                                    if (superClassName.equals(Object.class.getCanonicalName())){
                                        break;
                                    }
                                    switch (superClassName) {
                                        case "javax.faces.el.PropertyResolver":
                                            this.propertyResolverNameSet.add(typeDecl.toString());
                                            break;
                                        case "javax.faces.el.VariableResolver":
                                            this.variableResolverNameSet.add(typeDecl.toString());
                                            break;
                                        case "javax.el.ELResolver":
                                            this.javaeeResolverNameSet.add(typeDecl.toString());
                                            break;
                                        default:
                                            break;
                                    }
                                    
                                    List<? extends TypeMirror> supertypes = processingEnv.getTypeUtils().directSupertypes(superClassType);
                                    if (!supertypes.isEmpty()) {
                                        superClassType = processingEnv.getTypeUtils().directSupertypes(superClassType).get(0);
                                    }
                                }
                            } else {
                                // This is probably a base class that provides one or more properties
                                // (possibly via its super class)
                                DeclaredClassInfo declaredClassInfo = new DeclaredClassInfo((TypeElement) typeDecl);
                                this.declaredClassMap.put(((TypeElement) typeDecl).getQualifiedName().toString(), declaredClassInfo);
                                typeInfo = declaredClassInfo;
                            }   break;
                        case INTERFACE:
                            // This is an interface that may provide one or more properties
                            DeclaredInterfaceInfo declaredInterfaceInfo = new DeclaredInterfaceInfo((TypeElement) typeDecl);
                            this.declaredInterfaceMap.put(((TypeElement) typeDecl).getQualifiedName().toString(), declaredInterfaceInfo);
                            typeInfo = declaredInterfaceInfo;
                            break;
                        case METHOD:
                        case FIELD:
                            //The annotation is on a method or field, need to get the class that contains it
                            DeclaredClassInfo declaredClassInfo = new DeclaredClassInfo((TypeElement) typeDecl.getEnclosingElement());
                            this.declaredClassMap.put(declaredClassInfo.getQualifiedName().toString(), declaredClassInfo);
                            typeInfo = declaredClassInfo;
                            break;
                        default:
                            break;
                    }
                    if (typeInfo != null) {
                        Map<String, PropertyInfo> propertyInfoMap = classMemberVisitor.getPropertyInfoMap();
                        typeInfo.setPropertyInfoMap(propertyInfoMap);
                        Map<String, EventInfo> eventInfoMap = classMemberVisitor.getEventInfoMap();
                        typeInfo.setEventInfoMap(eventInfoMap);
                    }
                }
            }
        }
    }
    
    
    /**
     * Set super class info for all declared classes. If super class is in the current
     * compilation unit, then we have already seen it; if not, then it must be
     * introspected.
     */
    private void setSuperClassInfo() {
        Map<String,ClassInfo> introspectedClassMap = new HashMap<String,ClassInfo>();
        for (DeclaredClassInfo declaredClassInfo : this.declaredClassMap.values()) {
            TypeMirror superClassType = processingEnv.getTypeUtils().directSupertypes(declaredClassInfo.asType()).get(0);
            String superClassName = superClassType.toString();
            if (this.declaredClassMap.containsKey(superClassName)) {
                declaredClassInfo.setSuperClassInfo(this.declaredClassMap.get(superClassName));
            } else if (introspectedClassMap.containsKey(superClassName)) {
                declaredClassInfo.setSuperClassInfo(introspectedClassMap.get(superClassName));
            } else if (!superClassName.equals("java.lang.Object")) {
                try {
                    Class superClass = Class.forName(superClassName);
                    BeanInfo superBeanInfo = Introspector.getBeanInfo(superClass);
                    IntrospectedClassInfo superClassInfo = new IntrospectedClassInfo(superBeanInfo);
                    Map<String, PropertyInfo> propertyInfoMap = new HashMap<String, PropertyInfo>();
                    for (PropertyDescriptor propertyDescriptor : superBeanInfo.getPropertyDescriptors()) {
                        String name = propertyDescriptor.getName();
                        IntrospectedPropertyInfo propertyInfo = new IntrospectedPropertyInfo(propertyDescriptor);
                        CategoryDescriptor categoryDescriptor = (CategoryDescriptor) propertyDescriptor.getValue(Constants.PropertyDescriptor.CATEGORY);
                        if (categoryDescriptor != null) {
                            String categoryName = categoryDescriptor.getName();
                            if (this.categoryMap.containsKey(categoryName)) {
                                propertyInfo.setCategoryInfo(this.categoryMap.get(categoryName));
                            } else {
                                this.processingEnv.getMessager().printMessage(Kind.WARNING,
                                        "No category descriptor found in current compilation unit for '" + categoryName +
                                        "', referenced in " + superBeanInfo.getClass().getName());
                            }
                        }
                        propertyInfo.setDeclaringClassInfo(superClassInfo);
                        propertyInfoMap.put(name, propertyInfo);
                    }
                    superClassInfo.setPropertyInfoMap(propertyInfoMap);
                    Map<String, EventInfo> eventInfoMap = new HashMap<String, EventInfo>();
                    for (EventSetDescriptor eventDescriptor : superBeanInfo.getEventSetDescriptors()) {
                        if (eventDescriptor.getListenerMethods().length == 1) {
                            String name = eventDescriptor.getName();
                            IntrospectedEventInfo eventInfo = new IntrospectedEventInfo(eventDescriptor);
                            eventInfo.setDeclaringClassInfo(superClassInfo);
                            eventInfoMap.put(name, eventInfo);
                        }
                    }
                    superClassInfo.setEventInfoMap(eventInfoMap);
                    introspectedClassMap.put(superClassName, superClassInfo);
                    declaredClassInfo.setSuperClassInfo(superClassInfo);
                } catch (ClassNotFoundException | IntrospectionException e) {
                    e.printStackTrace();
                }
            }
        }
    }
       
    private void updatePropertyMetadata() {
        processingEnv.getMessager().printMessage(Kind.NOTE, "Updating property metadata...");
        for (DeclaredClassInfo declaredClassInfo : this.declaredClassMap.values()) {
            // Update metadata of overriding properties and events
            updateInheritedInfo(declaredClassInfo);
            Set<String> methodNameSet = declaredClassInfo.getMethodNameSet();
            // Validate each declared property
            for (PropertyInfo propertyInfo : declaredClassInfo.getPropertyInfoMap().values()) {
                // Ensure that property name is valid
                if (!isNameValid(propertyInfo.getName()))
                    this.processingEnv.getMessager().printMessage(Kind.ERROR,
                            "The name specified is not a valid property name", ((DeclaredPropertyInfo) propertyInfo).getDeclaration());
                if (propertyInfo.getAttributeInfo() != null) {
                    String name = propertyInfo.getAttributeInfo().getName();
                    if (!isNameValid(name))
                        this.processingEnv.getMessager().printMessage(Kind.ERROR,
                                "The name specified is not a valid attribute name", ((DeclaredPropertyInfo) propertyInfo).getDeclaration());
                }
                // Ensure that property does not correspond to the special "binding" tag attribute
                if (propertyInfo.getAttributeInfo() != null && propertyInfo.getAttributeInfo().getName().equals("binding"))
                    this.processingEnv.getMessager().printMessage(Kind.WARNING,
                            "Property corresponds to the reserved 'binding' tag attribute", ((DeclaredPropertyInfo) propertyInfo).getDeclaration());
                // Ensure that property read method exists
                String readMethodName = propertyInfo.getReadMethodName();
                if (readMethodName == null) {
                    readMethodName = generateReadMethodName(propertyInfo);
                    if (methodNameSet.contains(readMethodName)) {
                        ((DeclaredPropertyInfo) propertyInfo).setReadMethodName(readMethodName);
                    } else {
                        readMethodName = null;
                    }
                } else if (!methodNameSet.contains(readMethodName)) {
                    processingEnv.getMessager().printMessage(Kind.ERROR,
                            "No such property method " + readMethodName, ((DeclaredPropertyInfo) propertyInfo).getDeclaration());
                }
                // Ensure that read method has a valid signature
                for (Element enclosedElement : declaredClassInfo.getDeclaration().getEnclosedElements()) {
                    if (!(enclosedElement instanceof ExecutableElement)) {
                        continue;
                    }
                    ExecutableElement methodDecl = (ExecutableElement) enclosedElement;
                    if (methodDecl.getSimpleName().toString().equals(readMethodName)) {
                        TypeMirror returnType = methodDecl.getReturnType();
                        if(!returnType.toString().equals(propertyInfo.getType()) ||
                                methodDecl.getParameters().size() > 0) {
                            processingEnv.getMessager().printMessage(Kind.ERROR, 
                                    "Method " + readMethodName + " for property " + propertyInfo.getName() + " has incorrect signature", methodDecl);
                        }
                        break;
                    }
                }
                // Ensure that property write method exists
                String writeMethodName = propertyInfo.getWriteMethodName();
                if (writeMethodName == null) {
                    writeMethodName = generateWriteMethodName(propertyInfo);
                    if (methodNameSet.contains(writeMethodName))
                        ((DeclaredPropertyInfo) propertyInfo).setWriteMethodName(writeMethodName);
                    else
                        writeMethodName = null;
                } else if (!methodNameSet.contains(writeMethodName)) {
                    processingEnv.getMessager().printMessage(Kind.ERROR,
                            "No such property method " + writeMethodName, (
                            (DeclaredPropertyInfo) propertyInfo).getDeclaration());
                }
                if (readMethodName == null && writeMethodName == null) {
                    processingEnv.getMessager().printMessage(Kind.ERROR,
                            "No get or set method found for property", ((DeclaredPropertyInfo) propertyInfo).getDeclaration());
                }
                if (writeMethodName == null && propertyInfo.getAttributeInfo() != null) {
                    processingEnv.getMessager().printMessage(Kind.ERROR,
                            "A read-only method cannot be associated with a JSP tag attribute", ((DeclaredPropertyInfo) propertyInfo).getDeclaration());
                }
                // Ensure that write method has a valid signature
                
                
                for (Element enclosedElement : declaredClassInfo.getDeclaration().getEnclosedElements()) {
                    if (!(enclosedElement instanceof ExecutableElement)) {
                        continue;
                    }
                    ExecutableElement methodDecl = (ExecutableElement) enclosedElement;
                    if (methodDecl.getSimpleName().toString().equals(writeMethodName)) {
                        TypeMirror returnType = methodDecl.getReturnType();
                        Collection<? extends VariableElement> params = methodDecl.getParameters();
                        if(!(returnType.getKind() == TypeKind.VOID ) || params.size() != 1 ||
                                !params.iterator().next().asType().toString().equals(propertyInfo.getType())) {
                            processingEnv.getMessager().printMessage(Kind.ERROR,
                                    "Method " + writeMethodName + " for property " + propertyInfo.getName() + " has incorrect signature", methodDecl);
                        }
                        break;
                    }
                }
                // If property is categorized, verify that the referenced category
                // descriptor exists
                String categoryReferenceName =
                        ((DeclaredPropertyInfo) propertyInfo).getCategoryReferenceName();
                if (categoryReferenceName != null) {
                    CategoryInfo categoryInfo = this.categoryMap.get(categoryReferenceName);
                    if (categoryInfo == null) {
                        this.processingEnv.getMessager().printMessage(Kind.ERROR,
                                "Reference to non-existant category descriptor: " + categoryReferenceName, ((DeclaredPropertyInfo) propertyInfo).getDeclaration());
                    } else {
                        ((DeclaredPropertyInfo) propertyInfo).setCategoryInfo(categoryInfo);
                    }
                }
                // If property is of type javax.el.MethodExpression, verfiy that it is
                // annotated with a signature, or, that it refers to an event from which
                // the signature can be derived
                Element decl = ((DeclaredPropertyInfo) propertyInfo).getDeclaration();
                if (decl.getAnnotation(Property.Method.class) != null) {
                    if (propertyInfo.getType().equals("javax.el.MethodExpression")) {
                        Map<String,Object> annotationMap = getAnnotationValueMap(decl, Property.Method.class.getCanonicalName());
                        DeclaredAttributeInfo attributeInfo = (DeclaredAttributeInfo) propertyInfo.getAttributeInfo();
                        if (annotationMap.containsKey("signature")) {
                            attributeInfo.setMethodSignature((String) annotationMap.get("signature"));
                        } else if (annotationMap.containsKey("event")) {
                            String eventName = (String) annotationMap.get("event");
                            Map<String,EventInfo> eventInfoMap = propertyInfo.getDeclaringClassInfo().getEventInfoMap();
                            EventInfo eventInfo = eventInfoMap.get(eventName);
                            if (eventInfo == null) {
                                eventInfoMap = ((DeclaredClassInfo) propertyInfo.getDeclaringClassInfo()).getInheritedEventInfoMap();
                                eventInfo = eventInfoMap.get(eventName);
                            }
                            if (eventInfo != null) {
                                attributeInfo.setMethodSignature(eventInfo.getListenerMethodSignature());
                                eventInfo.setPropertyInfo(propertyInfo);
                            } else {
                                this.processingEnv.getMessager().printMessage(Kind.ERROR, "No such component event", decl);
                            }
                        } else {
                            this.processingEnv.getMessager().printMessage(Kind.ERROR, "Method annotation is missing an event or signature element", decl);
                        }
                    } else {
                        this.processingEnv.getMessager().printMessage(Kind.ERROR,
                                "Method annotation for property that is not of type javax.el.MethodExpression", decl);
                    }
                }
            }
            // Validate events, and supply event listener method names if defaulted
            for (EventInfo eventInfo : declaredClassInfo.getEventInfoMap().values()) {
                String addListenerMethodName = eventInfo.getAddListenerMethodName();
                if (addListenerMethodName == null) {
                    addListenerMethodName = generateAddListenerMethodName(eventInfo);
                    if (methodNameSet.contains(addListenerMethodName))
                        ((DeclaredEventInfo) eventInfo).setAddListenerMethodName(addListenerMethodName);
                    else
                        processingEnv.getMessager().printMessage(Kind.ERROR,
                                "No add event listener method declared or found", ((DeclaredEventInfo) eventInfo).getDeclaration());
                } else if (!methodNameSet.contains(addListenerMethodName)) {
                    processingEnv.getMessager().printMessage(Kind.ERROR, 
                            "No such event method " + addListenerMethodName, ((DeclaredEventInfo) eventInfo).getDeclaration());
                }
                String removeListenerMethodName = eventInfo.getRemoveListenerMethodName();
                if (removeListenerMethodName == null) {
                    removeListenerMethodName = generateRemoveListenerMethodName(eventInfo);
                    if (methodNameSet.contains(removeListenerMethodName))
                        ((DeclaredEventInfo) eventInfo).setRemoveListenerMethodName(removeListenerMethodName);
                    else
                        processingEnv.getMessager().printMessage(Kind.ERROR,
                                "No remove event listener method declared or found", ((DeclaredEventInfo) eventInfo).getDeclaration());
                } else if (!methodNameSet.contains(removeListenerMethodName)) {
                    processingEnv.getMessager().printMessage(Kind.ERROR, 
                            "No such event method " + removeListenerMethodName, ((DeclaredEventInfo) eventInfo).getDeclaration());
                }
                String getListenersMethodName = eventInfo.getGetListenersMethodName();
                if (getListenersMethodName == null) {
                    getListenersMethodName = generateGetListenersMethodName(eventInfo);
                    if (methodNameSet.contains(getListenersMethodName))
                        ((DeclaredEventInfo) eventInfo).setGetListenersMethodName(getListenersMethodName);
                }
            }
            // If this is a component and it will generate a tag, determine its preferred renderer
            if (declaredClassInfo instanceof DeclaredComponentInfo && ((DeclaredComponentInfo) declaredClassInfo).isTag()) {
                String rendererType = ((DeclaredComponentInfo) declaredClassInfo).getTagRendererType();
                boolean rendererFound = false;
                if (rendererType == null) {
                    String componentFamily = ((DeclaredComponentInfo) declaredClassInfo).getFamily();
                    for (DeclaredRendererInfo rendererInfo : this.declaredRendererSet) {
                        for (RendersInfo rendersInfo : rendererInfo.renderings) {
                            for (String rendererComponentFamily : rendersInfo.getComponentFamilies()) {
                                if (componentFamily.equals(rendererComponentFamily)) {
                                    ((DeclaredComponentInfo) declaredClassInfo).setTagRendererType(rendersInfo.getRendererType());
                                    rendererFound = true;
                                }
                            }
                        }
                    }
                } else {
                    for (DeclaredRendererInfo rendererInfo : this.declaredRendererSet) {
                        for (RendersInfo rendersInfo : rendererInfo.renderings) {
                            if (rendererType.equals(rendersInfo.getRendererType()))
                                rendererFound = true;
                        }
                    }
                }
                if (!rendererFound)
                    this.processingEnv.getMessager().printMessage(Kind.WARNING,
                            "No renderer found of correct renderer type and component family", declaredClassInfo.getDeclaration());
            }
        }
    }
    
    private void verifyClasses() {
        for (String componentType : this.declaredTagClassMap.keySet()) {
            boolean found = false;
            for (DeclaredComponentInfo componentInfo : this.declaredComponentSet) {
                if (componentType.equals(componentInfo.getType())) {
                    found = true;
                    break;
                }
            }
            if (!found)
                this.processingEnv.getMessager().printMessage(Kind.WARNING,
                        "No component found for tag's component type", this.declaredTagClassMap.get(componentType).getDeclaration());
        }
    }
    
    
     private void generateSourceAndConfig() {
        try {
            GeneratorFactory generatorFactory = new GeneratorFactory();
            if (this.getGeneratorFactoryClass() != null) {
                try {
                    generatorFactory = (GeneratorFactory) this.getGeneratorFactoryClass().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            Filer filer = processingEnv.getFiler();
            if (this.debug) {
                DebugGenerator debugGenerator = generatorFactory.getDebugGenerator();
                debugGenerator.setNamespace(this.getNamespaceUri());
                debugGenerator.setNamespacePrefix(this.getNamespacePrefix());
                debugGenerator.setPackageNameSet(this.packageNameSet);
                debugGenerator.setDeclaredComponentInfoSet(this.declaredComponentSet);
                debugGenerator.setDeclaredRendererInfoSet(this.declaredRendererSet);
                try (PrintWriter debugWriter = new PrintWriter(System.out)) {
                    debugGenerator.setPrintWriter(debugWriter);
                    debugGenerator.generate();
                }
            }
            
            if (this.processDesignTime) {
                processDesignTimeAnnotations(generatorFactory, filer);
            }
            if (this.processRunTime) {
                processRuntimeAnnotatons(generatorFactory, filer);
            }
        } catch (IOException e) {
            e.printStackTrace();
            processingEnv.getMessager().printMessage(Kind.ERROR, e.toString());
        } catch (GeneratorException e) {
            processingEnv.getMessager().printMessage(Kind.ERROR, e.toString());
        }
    }
    /**
     * Generate BeanInfo base class files
     * @param generatorFactory
     * @param filer
     * @throws IOException 
     */
    private void processDesignTimeAnnotations(GeneratorFactory generatorFactory, Filer filer) throws IOException, GeneratorException {
        processingEnv.getMessager().printMessage(Kind.NOTE, "Processing design time annotations...");
        Map<String, PropertyBundleMap> propertyBundleMapsMap = new HashMap<String, PropertyBundleMap>();
        BeanInfoSourceGenerator beanInfoSourceGenerator = generatorFactory.getBeanInfoSourceGenerator();
        beanInfoSourceGenerator.setNamespace(this.getNamespaceUri());
        beanInfoSourceGenerator.setNamespacePrefix(this.getNamespacePrefix());
        for (DeclaredComponentInfo componentInfo : this.declaredComponentSet) {
            beanInfoSourceGenerator.setDeclaredComponentInfo(componentInfo);
            
            String beanQualifiedName = beanInfoSourceGenerator.getQualifiedName();
            if (heldFiles.containsKey(beanQualifiedName)) {
                //return;
                //beanInfoSourceGenerator.setPrintWriter(heldFiles.get(beanQualifiedName));
            } else {
                Writer sourceWriter = filer.createSourceFile(beanQualifiedName).openWriter();
                heldFiles.put(beanQualifiedName, sourceWriter);
                beanInfoSourceGenerator.setPrintWriter(sourceWriter);
            //}
            //beanInfoSourceGenerator.setPrintWriter(filer.createSourceFile(beanInfoSourceGenerator.getQualifiedName()).openWriter());
            
            if (this.localize) {
                String packageName = componentInfo.getPackageName();
                PropertyBundleMap propertyBundleMap = propertyBundleMapsMap.get(packageName);
                if (propertyBundleMap == null) {
                    String qualifiedName = packageName + ".BeanInfoBundle";
                    propertyBundleMap = new PropertyBundleMap(qualifiedName);
                    propertyBundleMapsMap.put(packageName, propertyBundleMap);
                }
                beanInfoSourceGenerator.setPropertyBundleMap(propertyBundleMap);
            }
            beanInfoSourceGenerator.generate();
            } //TODO: This or 708
        }
        if (this.localize) {
            for (PropertyBundleMap propertyBundleMap : propertyBundleMapsMap.values()) {
                if (propertyBundleMap.size() > 0) {
                    String fileName = propertyBundleMap.getQualifiedName().replace('.', File.separatorChar) + ".properties";
                    
                    Writer printWriter;
                    if (heldFiles.containsKey(fileName)) {
                        printWriter = heldFiles.get(fileName);
                    } else {
                        printWriter = filer.createResource(StandardLocation.CLASS_OUTPUT, "", fileName).openWriter();
                        heldFiles.put(fileName, printWriter);
                    }
                        for (Object key : propertyBundleMap.keyList()) {
                            Object value = propertyBundleMap.get(key);
                            printWriter.write(key.toString() + "=" + value.toString());
                            printWriter.write(System.lineSeparator());
                        }
                }
            }
        }
    }
    
    /**
     * Generate faces configuration file
     * @param generatorFactory
     * @param filer
     * @throws IOException
     * @throws GeneratorException 
     */
    private void processRuntimeAnnotatons(GeneratorFactory generatorFactory, Filer filer) throws IOException, GeneratorException {
        processingEnv.getMessager().printMessage(Kind.NOTE, "Processing runtime annotations...");
        if (this.declaredComponentSet.size() > 0) {
            FacesConfigFileGenerator facesConfigGenerator = generatorFactory.getFacesConfigFileGenerator();
            facesConfigGenerator.setDeclaredComponentInfoSet(this.declaredComponentSet);
            facesConfigGenerator.setDeclaredRendererInfoSet(this.declaredRendererSet);
            facesConfigGenerator.setDeclaredPropertyResolverNameSet(this.propertyResolverNameSet);
            facesConfigGenerator.setDeclaredVariableResolverNameSet(this.variableResolverNameSet);
            facesConfigGenerator.setDeclaredJavaEEResolverNameSet(this.javaeeResolverNameSet);
            String fileName = facesConfigGenerator.getFileName();
            if (heldFiles.containsKey(fileName)) {
                facesConfigGenerator.setPrintWriter(heldFiles.get(fileName));
            } else {
                Writer sourceWriter = filer.createResource(StandardLocation.CLASS_OUTPUT, "", fileName).openWriter();
                heldFiles.put(fileName, sourceWriter);
                facesConfigGenerator.setPrintWriter(sourceWriter);
                facesConfigGenerator.generate();
            }
            //facesConfigGenerator.setPrintWriter(filer.createResource(StandardLocation.CLASS_OUTPUT, "", fileName).openWriter());
            //facesConfigGenerator.generate(); //TODO: This or 769
        }
        // Generate JSP tag class files, unless a hand-authored tag class exists
        processingEnv.getMessager().printMessage(Kind.NOTE, "Generate JSP tag class files, unless a hand-authored tag class exists");
        TagSourceGenerator tagSourceGenerator = generatorFactory.getTagSourceGenerator();
        tagSourceGenerator.setNamespace(this.getNamespaceUri());
        tagSourceGenerator.setNamespacePrefix(this.getNamespacePrefix());
        for (DeclaredComponentInfo componentInfo : this.declaredComponentSet) {
            if (!this.declaredTagClassMap.containsKey(componentInfo.getType()) && componentInfo.isTag()) {
                tagSourceGenerator.setDeclaredComponentInfo(componentInfo);
                String qualifiedName = tagSourceGenerator.getQualifiedName();
                if (heldFiles.containsKey(qualifiedName)) {
                    heldFiles.get(qualifiedName).close();
                    continue;
                } else {
                    Writer sourceWriter = filer.createSourceFile(qualifiedName).openWriter();
                    heldFiles.put(qualifiedName, sourceWriter);
                    tagSourceGenerator.setPrintWriter(sourceWriter);
                }
                tagSourceGenerator.generate();
            }
        }
        // Generate JSP tag library configuration file
         processingEnv.getMessager().printMessage(Kind.NOTE, "Generate JSP tag library configuration file");
        if (this.declaredComponentSet.size() > 0) {
            if (this.taglibDoc != null) {
                try {
                    SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
                    TaglibDocHandler handler = new TaglibDocHandler();
                    parser.parse(new File(this.taglibDoc), handler);
                    Map<String, String> tagDescriptionMap = handler.getTagDescriptionMap();
                    Map<String, Map> tagAttributeMap = handler.getTagAttributeMap();
                    for (DeclaredComponentInfo componentInfo : this.declaredComponentSet) {
                        String tagName = componentInfo.getTagName();
                        if (tagDescriptionMap.containsKey(tagName)) {
                            componentInfo.setTagDescription(tagDescriptionMap.get(tagName));
                        }
                        if (tagAttributeMap.containsKey(tagName)) {
                            Map<String, String> attrDescriptionMap = tagAttributeMap.get(tagName);
                            for (PropertyInfo propertyInfo : componentInfo.getPropertyInfoMap().values()) {
                                AttributeInfo attrInfo = propertyInfo.getAttributeInfo();
                                if (attrInfo instanceof DeclaredAttributeInfo
                                        && attrInfo != null && attrDescriptionMap.containsKey(attrInfo.getName())) {
                                    ((DeclaredAttributeInfo) attrInfo).setDescription(attrDescriptionMap.get(attrInfo.getName()));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    this.processingEnv.getMessager().printMessage(Kind.ERROR, "Error occurred while processing input tag description file: "
                            + e.getMessage());
                }
            }
            
            processingEnv.getMessager().printMessage(Kind.NOTE, "Generate final taglibs");
            try {
            TagLibFileGenerator tagLibGenerator = generatorFactory.getTagLibFileGenerator();
            tagLibGenerator.setDeclaredComponentInfoSet(this.declaredComponentSet);
            tagLibGenerator.setNamespace(this.getNamespaceUri());
            tagLibGenerator.setNamespacePrefix(this.getNamespacePrefix());
            String fileName = tagLibGenerator.getFileName();
            processingEnv.getMessager().printMessage(Kind.NOTE, "Going to write file " + fileName);
            if (heldFiles.containsKey(fileName)) {
                    heldFiles.get(fileName).close();
                    return;
                } else {
                    Writer sourceWriter = filer.createResource(StandardLocation.CLASS_OUTPUT, "", fileName).openWriter();
                    heldFiles.put(fileName, sourceWriter);
                    tagLibGenerator.setPrintWriter(sourceWriter);
            }
            //tagLibGenerator.setPrintWriter(filer.createResource(StandardLocation.CLASS_OUTPUT, "", fileName).openWriter());
            processingEnv.getMessager().printMessage(Kind.NOTE, "generator.generate()");
            tagLibGenerator.generate();
            } catch (Exception e){
                processingEnv.getMessager().printMessage(Kind.ERROR, e.toString());
                throw e;
            }
            processingEnv.getMessager().printMessage(Kind.NOTE, "Finished generator.generate()");
        }
    }
    
    /**
     * Configure the processor based on the options passed in,
     * based on the old factory which previously did the configuration.
     * @see FacesAnnotationProcessorFactory#getProcessorFor(java.util.Set, javax.annotation.processing.ProcessingEnvironment) 
     */
    public void configureOptions() {
        Map<String, String> optionMap = processingEnv.getOptions();
        // Process options passed to the annotation processor tool. The tool should be
        // doing this processing, but the apt released with JDK 5 does not. 
        // TODO - cross verify with calls to apt on other platforms, and outside of ant
        for (String name : optionMap.keySet()) {
  
                String value = optionMap.get(name);
                switch (name) {
                    case LOCALIZE_OPTION:
                        setLocalize(true);
                        break;
                    // TODO - Add support for different versions of JavaEE
                    case JAVAEE_VERSION_OPTION:
                        break;
                    case GENERATE_RUNTIME_OPTION:
                        setProcessRunTime(true);
                        break;
                    case GENERATE_DESIGNTIME_OPTION:
                        setProcessDesignTime(true);
                        break;
                    case GENERATOR_FACTORY_OPTION:
                        if (value == null || value.length() == 0) {
                            processingEnv.getMessager().printMessage(Kind.ERROR, "Option " + GENERATOR_FACTORY_OPTION + " missing value");
                        }
                        try {
                            Class factoryClass = Class.forName(value);
                            if (!GeneratorFactory.class.isAssignableFrom(factoryClass)) {
                                processingEnv.getMessager().printMessage(Kind.ERROR, "Generator factory class must extend " + GeneratorFactory.class.toString());
                            } else {
                                setGeneratorFactoryClass(Class.forName(value));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                        break;
                    case NAMESPACE_URI_OPTION:
                        if (value == null || value.length() == 0) {
                            processingEnv.getMessager().printMessage(Kind.ERROR, "Option " + NAMESPACE_URI_OPTION + " missing value");
                        }
                        setNamespaceUri(value);
                        break;
                    case NAMESPACE_PREFIX_OPTION:
                        if (value == null || value.length() == 0) {
                            processingEnv.getMessager().printMessage(Kind.ERROR, "Option " + NAMESPACE_PREFIX_OPTION + " missing value");
                        }
                        setNamespacePrefix(value);
                        break;
                    case TAGLIB_DOC_OPTION:
                        if (value == null || value.length() == 0) {
                            processingEnv.getMessager().printMessage(Kind.ERROR, "Option " + TAGLIB_DOC_OPTION + " missing value");
                        }
                        setTaglibDoc(value);
                        break;
                    case DEBUG_OPTION:
                        setDebug(true);
                        break;
                    default:
                        break;
                }
            //}
        }
        
    }

    //@Override
    //public Set<String> getSupportedOptions() {
     //   return Collections.unmodifiableSet(SUPPORTED_OPTIONS);
    //}

    //@Override
    //public Set<String> getSupportedAnnotationTypes() {
    //    return Collections.unmodifiableSet(supportedAnnotations);
    //}

//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        // Find all component classes in the compilation unit, creating a skeleton
//        // ComponentInfo for each, then invoke a declaration classMemberVisitor that will
//        // process all properties declared within each class or interface.
//        MemberDeclarationVisitor classMemberVisitor = new MemberDeclarationVisitor(this.env);
//        
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return Arrays.asList();
    }

    /**
     * Closes all files that are held open
     */
    private void cleanFileHandles() {
        for (Writer openWriter: heldFiles.values()) {
            try {
                openWriter.close();
            } catch (IOException ex) {
                //presumable because they are closed already
                Logger.getLogger(FacesAnnotationProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    /**
     * Visitor implementation used to visit field and method declarations in a
     * class.
     */
    private static class MemberDeclarationVisitor extends SimpleElementVisitor8 {
        
        ProcessingEnvironment env;
        Map<String,PropertyInfo> propertyInfoMap = new HashMap<String,PropertyInfo>();
        Map<String,EventInfo> eventInfoMap = new HashMap<String,EventInfo>();
        String defaultPropertyName = null;
        String defaultEventName = null;
        
        MemberDeclarationVisitor(ProcessingEnvironment env) {
            this.env = env;
        }
        
        public void visitFieldDeclaration(VariableElement decl) {
            if (decl.getAnnotation(Property.class) != null) {
                // Annotation indicates that this field corresponds to a property
                Map<String,Object> annotationMap = getAnnotationValueMap(decl, Property.class.getName());
                DeclaredPropertyInfo propertyInfo = new DeclaredPropertyInfo(annotationMap, decl);
                String name = (String) annotationMap.get(DeclaredPropertyInfo.NAME);
                if (name == null || name.length() == 0)
                    name = decl.getSimpleName().toString();
                if (Boolean.TRUE.equals(annotationMap.get(DeclaredPropertyInfo.IS_DEFAULT))) {
                    if (this.defaultPropertyName == null)
                        this.defaultPropertyName = name;
                    else
                        this.env.getMessager().printMessage(Kind.ERROR, "Duplicate default property", decl);
                }
                propertyInfo.setName(name);
                propertyInfo.setType(decl.asType().toString());
                if (propertyInfoMap.containsKey(name))
                    this.env.getMessager().printMessage(Kind.ERROR, "Duplicate property annotation", decl);
                propertyInfoMap.put(name, propertyInfo);
            } else if (decl.getAnnotation(PropertyCategory.class) != null) {
                // Annotation indicates that this field corresponds to a property category descriptor
                boolean categoryIsValid = true;
                Collection modifiers = decl.getModifiers();
                if (!decl.asType().toString().equals(CategoryDescriptor.class.getName())) {
                    env.getMessager().printMessage(Kind.ERROR, "Fields identified as property categories must be of type " + CategoryDescriptor.class.getName(), decl);
                    categoryIsValid = false;
                }
                if (!modifiers.contains(Modifier.PUBLIC)) {
                    this.env.getMessager().printMessage(Kind.ERROR, 
                            "Non-public field cannot be a property category", decl);
                    categoryIsValid = false;
                }
                if(!modifiers.contains(Modifier.STATIC)) {
                    this.env.getMessager().printMessage(Kind.ERROR, 
                            "Non-static field cannot be a property category", decl);
                    categoryIsValid = false;
                }
                if (categoryIsValid) {
                    CategoryInfo categoryInfo = new CategoryInfo(getAnnotationValueMap(decl, PropertyCategory.class.getName()));
                    String className = decl.asType().getClass().getName();
                    String fieldName = decl.getSimpleName().toString();
                    categoryInfo.setFieldName(className + "." + fieldName);
                    this.categoryMap.put(categoryInfo.getName(), categoryInfo);
                }
            }
        }
        
        public void visitMethodDeclaration(ExecutableElement decl) {
            if (decl.getAnnotation(Property.class) != null) {
                // Annotation indicates that this method is a property getter or setter method
                Map<String,Object> annotationMap = getAnnotationValueMap(decl, Property.class.getName());
                DeclaredPropertyInfo propertyInfo = new DeclaredPropertyInfo(annotationMap, decl);
                String name = (String) annotationMap.get(DeclaredPropertyInfo.NAME);
                TypeMirror returnType = decl.getReturnType();
                Collection<? extends VariableElement> paramDecls = decl.getParameters();
                if (returnType.getKind() == TypeKind.VOID && paramDecls.size() == 1) {
                    // This is a "set" method
                    String methodName = decl.getSimpleName().toString();
                    propertyInfo.setWriteMethodName(methodName);
                    propertyInfo.setType(paramDecls.iterator().next().asType().toString());
                    if (name == null || name.length() == 0) {
                        if (methodName.startsWith("set")) {
                            name = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
                        } else {
                            this.env.getMessager().printMessage(Kind.ERROR,
                                    "Property name implied for a write method name that is not standard", decl);
                        }
                    }
                } else if (!(returnType.getKind() == TypeKind.VOID) && paramDecls.isEmpty()) {
                    // This is a "get" method
                    String methodName = decl.getSimpleName().toString();
                    propertyInfo.setReadMethodName(methodName);
                    propertyInfo.setType(returnType.toString());
                    if (name == null || name.length() == 0) {
                        if (methodName.startsWith("get")) {
                            name = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
                        } else if (propertyInfo.getType().equals("boolean") && methodName.startsWith("is")) {
                            name = methodName.substring(2,3).toLowerCase() + methodName.substring(3);
                        } else {
                            this.env.getMessager().printMessage(Kind.ERROR,
                                    "Property name implied for a read method name that is not standard", decl);
                        }
                    }
                } else {
                    // This is not a valid property "get" or "set" method
                    this.env.getMessager().printMessage(Kind.ERROR,
                            "Annotated property method does not have correct signature", decl);
                }
                if (name != null) {
                    propertyInfo.setName(name);
                    if (propertyInfoMap.containsKey(name))
                        this.env.getMessager().printMessage(Kind.ERROR, "Duplicate property annotation", decl);
                    propertyInfoMap.put(name, propertyInfo);
                }
            } else if (decl.getAnnotation(Event.class) != null) {
                // Annotation indicating that this method defines a component event
                Map<String,Object> annotationMap = getAnnotationValueMap(decl, Event.class.getName());
                DeclaredEventInfo eventInfo = new DeclaredEventInfo(annotationMap, decl);
                String name = (String) annotationMap.get(DeclaredEventInfo.NAME);
                TypeMirror returnType = decl.getReturnType();
                Collection<? extends VariableElement> paramDecls = decl.getParameters();
                if (Boolean.TRUE.equals(annotationMap.get(DeclaredEventInfo.IS_DEFAULT))) {
                    if (this.defaultEventName == null)
                        this.defaultEventName = name;
                    else
                        this.env.getMessager().printMessage(Kind.ERROR, "Duplicate default event", decl);
                }
                if (returnType.getKind() == TypeKind.VOID && paramDecls.size() == 1) {
                    String methodName = decl.getSimpleName().toString();
                    if (methodName.startsWith("add")) {
                        eventInfo.setAddListenerMethodName(methodName);
                    } else if (methodName.startsWith("remove")) {
                        eventInfo.setRemoveListenerMethodName(methodName);
                    } else {
                        if (!methodName.equals(annotationMap.get(DeclaredEventInfo.ADD_LISTENER_METHOD_NAME)) &&
                                !methodName.equals(annotationMap.get(DeclaredEventInfo.REMOVE_LISTENER_METHOD_NAME)))
                            this.env.getMessager().printMessage(Kind.ERROR,
                                    "Indeterminate event listener method (may be 'add' or 'remove' method)", decl);
                        if (name == null)
                            this.env.getMessager().printMessage(Kind.ERROR,
                                    "Event name unspecified for event listener method with non-standard name", decl);
                    }
                    TypeMirror paramType = paramDecls.iterator().next().asType();
                    if (DeclaredType.class.isAssignableFrom(paramType.getClass())) {
                        // Event listener class is defined in this compilation unit
                        eventInfo.setListenerDeclaration(((DeclaredType) paramType).asElement());
                    } else {
                        // Event listener class is defined in an external library
                        try {
                            eventInfo.setListenerClass(Class.forName(paramType.toString()));
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (name == null) {
                        String listenerClassName = eventInfo.getListenerClassName();
                        if (listenerClassName.endsWith("Listener")) {
                            int offset = listenerClassName.contains(".") ? listenerClassName.lastIndexOf('.') + 1: 0;
                            name = listenerClassName.substring(offset, offset + 1).toLowerCase() +
                                    listenerClassName.substring(offset + 1, listenerClassName.indexOf("Listener"));
                        } else {
                            this.env.getMessager().printMessage(Kind.ERROR,
                                    "Event name unspecified for event listener class with non-standard name", decl);
                        }
                    }
                    if (name != null) {
                        eventInfo.setName(name);
                        if (eventInfoMap.containsKey(name))
                            this.env.getMessager().printMessage(Kind.ERROR, "Duplicate event annotation", decl);
                        eventInfoMap.put(name, eventInfo);
                    }
                } else {
                    this.env.getMessager().printMessage(Kind.ERROR,
                            "Invalid signature for an event listener 'add' or 'remove' method", decl);
                }
            }
        }
        
        public Map<String,PropertyInfo> getPropertyInfoMap() {
            return this.propertyInfoMap;
        }
        
        public Map<String,EventInfo> getEventInfoMap() {
            return this.eventInfoMap;
        }
        
        private Map<String,CategoryInfo> categoryMap;
        
        public void setCategoryMap(Map<String,CategoryInfo> categoryMap) {
            this.categoryMap = categoryMap;
        }
        
        public void reset() {
            this.propertyInfoMap = new HashMap<String,PropertyInfo>();
            this.eventInfoMap = new HashMap<String,EventInfo>();
            this.defaultPropertyName = null;
        }
        
    }
    
    
    /**
     * Handler implementation used to collect tag and attribute descriptions
     * during the parsing of a taglib file.
     */
    private static class TaglibDocHandler extends DefaultHandler {
        
        String attrName;
        String attrDescription;
        String tagName;
        String tagDescription;
        
        StringBuffer buffer = new StringBuffer();
        Stack<String> elementStack = new Stack<String>();
        Map<String,String> tagDescriptionMap = new HashMap<String,String>();
        Map<String,Map> tagAttributeMap = new HashMap<String,Map>();
        Map<String,String> attributeDescriptionMap;
        
        @Override
        public void startDocument() throws SAXException {
            this.tagDescriptionMap.clear();
            this.tagAttributeMap.clear();
            this.elementStack.clear();
        }
        
        @Override
        public void characters(char[] chars, int start, int length) throws SAXException {
            buffer.append(chars, start, length);
        }
        
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (localName == null || localName.length() == 0)
                localName = qName;
            if (localName.equals("tag")) {
                this.attributeDescriptionMap = new HashMap<String,String>();
                this.tagName = null;
                this.tagDescription = null;
            } else if (localName.equals("attribute")) {
                this.attrName = null;
                this.attrDescription = null;
            }
            this.elementStack.push(localName);
        }
        
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            this.elementStack.pop();
            if (localName == null || localName.length() == 0)
                localName = qName;
            switch (localName) {
                case "name":
                    if (this.elementStack.peek().equals("tag"))
                        this.tagName = buffer.toString().trim();
                    else
                        this.attrName = buffer.toString().trim();
                    break;
                case "description":
                    if (this.elementStack.peek().equals("tag"))
                        this.tagDescription = buffer.toString().trim();
                    else
                        this.attrDescription = buffer.toString().trim();
                    break;
                case "attribute":
                    if (attrDescription != null)
                        this.attributeDescriptionMap.put(attrName, attrDescription);
                    break;
                case "tag":
                    if (tagDescription != null)
                        this.tagDescriptionMap.put(tagName, tagDescription);
                    this.tagAttributeMap.put(tagName, this.attributeDescriptionMap);
                    break;
                default:
                    break;
            }
            this.buffer.setLength(0);
        }
        
        public Map<String,String> getTagDescriptionMap() {
            return this.tagDescriptionMap;
        }
        
        public Map<String,Map> getTagAttributeMap() {
            return this.tagAttributeMap;
        }
        
    }
    
    
    /**
     * A recursive method that updates the metadata for inherited and overriden
     * properties. A number of validity checks are performed on overriden properties
     * to ensure that the properties are indeed the same: the property attrName and
     * type must be equal, and neither method attrName can be modified by the
     * overriding property. Metadata from the overridden property is inherited,
     * unless it is overriden by metadata in the overriding property. Finally,
     * any properties inherited from a class not in the current compilation unit
     * are copied into this class's property map.
     */
    private void updateInheritedInfo(DeclaredClassInfo classInfo) {
        // A map of all inherited properties, whether inherited from a super class or
        // from a super interface
        Map<String,PropertyInfo> superPropertyInfoMap = new HashMap<String,PropertyInfo>();
        ClassInfo superClassInfo = classInfo.getSuperClassInfo();
        if (superClassInfo != null) {
            if (DeclaredClassInfo.class.isAssignableFrom(superClassInfo.getClass()))
                updateInheritedInfo((DeclaredClassInfo) superClassInfo);
            // Add properties from super class to map of inherited properties
            superPropertyInfoMap.putAll(superClassInfo.getPropertyInfoMap());
            if (DeclaredClassInfo.class.isAssignableFrom(superClassInfo.getClass()))
                superPropertyInfoMap.putAll(((DeclaredClassInfo) superClassInfo).getInheritedPropertyInfoMap());
            classInfo.getMethodNameSet().addAll(superClassInfo.getMethodNameSet());
        }
        Stack<TypeMirror> superInterfaces = new Stack<TypeMirror>();
        superInterfaces.addAll(classInfo.getDeclaration().getInterfaces());
        while (!superInterfaces.isEmpty()) {
            TypeMirror interfaceType = superInterfaces.pop();
            // Add properties from super interface to map of inherited properties
            if (this.declaredInterfaceMap.containsKey(interfaceType.toString())) {
                DeclaredInterfaceInfo interfaceInfo = this.declaredInterfaceMap.get(interfaceType.toString());
                for (PropertyInfo propertyInfo : interfaceInfo.getPropertyInfoMap().values()) {
                    if (superPropertyInfoMap.containsKey(propertyInfo.getName())) {
                        this.processingEnv.getMessager().printMessage(Kind.ERROR, 
                                classInfo.getQualifiedName() + " inherits property " + propertyInfo.getName() + " more than once");
                    } else {
                        superPropertyInfoMap.put(propertyInfo.getName(), propertyInfo);
                    }
                }
            }
            //superInterfaces.addAll(interfaceType.getInterfaces());
        }
        for (PropertyInfo propertyInfo : classInfo.getPropertyInfoMap().values()) {
            // Validate overriden properties, and merge their property info
            if (superPropertyInfoMap.containsKey(propertyInfo.getName()) && propertyInfo instanceof DeclaredPropertyInfo) {
                DeclaredPropertyInfo thisPropertyInfo = (DeclaredPropertyInfo) propertyInfo;
                PropertyInfo superPropertyInfo = superPropertyInfoMap.get(propertyInfo.getName());
                boolean updateInheritedValues = true;
                if (!propertyInfo.getType().equals(superPropertyInfo.getType())) {
                    this.processingEnv.getMessager().printMessage(Kind.ERROR,
                            "Property in sub class must be of same type as the property that it overrides", thisPropertyInfo.getDeclaration());
                    updateInheritedValues = false;
                }
                if (propertyInfo.getReadMethodName() != null && superPropertyInfo.getReadMethodName() != null
                        && !propertyInfo.getReadMethodName().equals(superPropertyInfo.getReadMethodName())) {
                    this.processingEnv.getMessager().printMessage(Kind.ERROR,
                            "Read method of property in sub class must have same name as the method that it overrides", thisPropertyInfo.getDeclaration());
                    updateInheritedValues = false;
                }
                if (propertyInfo.getWriteMethodName() != null && superPropertyInfo.getWriteMethodName() != null
                        && !propertyInfo.getWriteMethodName().equals(superPropertyInfo.getWriteMethodName())) {
                    this.processingEnv.getMessager().printMessage(Kind.ERROR,
                            "Write method of property in sub class must have same name as the method that it overrides", thisPropertyInfo.getDeclaration());
                    updateInheritedValues = false;
                }
                if (updateInheritedValues)
                    thisPropertyInfo.updateInheritedValues(superPropertyInfo);
            }
        }
        // Any properties or events inherited from an interface that were not overridden must
        // be added explicitly to this component's property and event info maps
        Map<String,PropertyInfo> propertyInfoMap = classInfo.getPropertyInfoMap();
        Map<String,EventInfo> eventInfoMap = classInfo.getEventInfoMap();
        superInterfaces.clear();
        superInterfaces.addAll(classInfo.getDeclaration().getInterfaces());
        while (!superInterfaces.isEmpty()) {
            TypeMirror interfaceType = superInterfaces.pop();
            if (this.declaredInterfaceMap.containsKey(interfaceType.toString())) {
                DeclaredInterfaceInfo interfaceInfo = this.declaredInterfaceMap.get(interfaceType.toString());
                for (PropertyInfo propertyInfo : interfaceInfo.getPropertyInfoMap().values()) {
                    if (!propertyInfoMap.containsKey(propertyInfo.getName()))
                        propertyInfoMap.put(propertyInfo.getName(), propertyInfo);
                }
                for (EventInfo eventInfo : interfaceInfo.getEventInfoMap().values()) {
                    if (!eventInfoMap.containsKey(eventInfo.getName()))
                        eventInfoMap.put(eventInfo.getName(), eventInfo);
                }
            }
            //superInterfaces.addAll(interfaceType.getKind().getDeclaringClass().);
        }
    }
    
    
    // Static utility methods
    
    /**
     * A utility method for creating a simple map of attrName-value pairs for all annotation
     * elements and values found in the annotation specified among all annotations found
     * in the declaration specified. Note that this map will contain entries only for
     * those elements and value supplied explicitly in the declaration. Elements that
     * are implicitly assuming their default value will not be present.
     */
    private static Map<String,Object> getAnnotationValueMap(Element decl, String annotationClassName) {
        for (AnnotationMirror annotationMirror : decl.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType().asElement().asType().toString().equals(annotationClassName)) {
                return getAnnotationValueMap(annotationMirror);
            }
        }
        return null;
    }
    
    /**
     * A utility method for creating a simple map of attrName-value pairs for all annotation
     * elements and values found in the annotation specified.
     */
    private static Map<String,Object> getAnnotationValueMap(AnnotationMirror annotationMirror) {
        Map<String,Object> annotationValueMap = new HashMap<String,Object>();
        for (ExecutableElement elementDecl : annotationMirror.getElementValues().keySet()) {
            String name = elementDecl.getSimpleName().toString();
            Object value = annotationMirror.getElementValues().get(elementDecl).getValue();
            if (value != null && AnnotationMirror.class.isAssignableFrom(value.getClass())) {
                // Nested annotations should also be stored as maps
                value = getAnnotationValueMap(((AnnotationMirror) value));
            } else if (List.class.isAssignableFrom(value.getClass())) {
                Iterator iter = ((List) value).iterator();
                ArrayList valueList = new ArrayList();
                while (iter.hasNext()) {
                    Object obj = iter.next();
                    if (AnnotationValue.class.isAssignableFrom(obj.getClass())) {
                        Object annotationValue = ((AnnotationValue) obj).getValue();
                        if (AnnotationMirror.class.isAssignableFrom(annotationValue.getClass()))
                            valueList.add(getAnnotationValueMap((AnnotationMirror) annotationValue));
                        else
                            valueList.add(annotationValue);
                    } else {
                        valueList.add(obj);
                    }
                }
                value = valueList;
            }
            annotationValueMap.put(name,value);
        }
        return annotationValueMap;
    }
    
    /**
     * Validate the attrName specified, to ensure that it can serve as an instance attrName
     * in Java source or as an XML element or attribute attrName in JSP source.
     */
    private static boolean isNameValid(String name) {
        if (name == null || name.length() == 0)
            return false;
        if (!Character.isJavaIdentifierStart(name.charAt(0)))
            return false;
        for (int i = 1; i < name.length(); i++) {
            if (!Character.isJavaIdentifierPart(name.charAt(i)))
                return false;
        }
        return true;
    }
    
    /**
     * Create a default read method attrName for the property specified.
     */
    private static String generateReadMethodName(PropertyInfo propertyInfo) {
        String name = propertyInfo.getName();
        String prefix = propertyInfo.getType().equals("boolean") ? "is" : "get";
        return prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    
    /**
     * Create a default write method attrName for the property specified.
     */
    private static String generateWriteMethodName(PropertyInfo propertyInfo) {
        String name = propertyInfo.getName();
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    
    /**
     * Create a default add listener method attrName for the event specified.
     */
    private static String generateAddListenerMethodName(EventInfo eventInfo) {
        String name = eventInfo.getName();
        return "add" + name.substring(0, 1).toUpperCase() + name.substring(1) + "Listener";
    }
    
    /**
     * Create a default remove listener method attrName for the event specified.
     */
    private static String generateRemoveListenerMethodName(EventInfo eventInfo) {
        String name = eventInfo.getName();
        return "remove" + name.substring(0, 1).toUpperCase() + name.substring(1) + "Listener";
    }
    
    /**
     * Create a default get listeners method attrName for the event specified.
     */
    private static String generateGetListenersMethodName(EventInfo eventInfo) {
        String name = eventInfo.getName();
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1) + "Listeners";
    }
    
}
