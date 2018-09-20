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

import com.sun.mirror.declaration.ClassDeclaration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a JSF renderer class declared in the current compilation unit.
 *
 * @author gjmurphy
 */
public class DeclaredRendererInfo extends DeclaredClassInfo {
    
    static String VALUE = "value";
    
    Map<String,Object> annotationValueMap;
    List<RendersInfo> renderings;
    
    DeclaredRendererInfo(Map<String,Object> annotationValueMap, ClassDeclaration decl) {
        super(decl);
        this.annotationValueMap = annotationValueMap;
        renderings = new ArrayList<RendersInfo>();
        if (this.annotationValueMap.containsKey(VALUE)) {
            for (Object value : (List) this.annotationValueMap.get(VALUE)) {
                Map nestedAnnotationValueMap = (Map) value;
                renderings.add(new RendersInfo(nestedAnnotationValueMap));
            }
        }
    }
    
    public List<RendersInfo> getRenderings() {
        return this.renderings;
    }
    
    /**
     * Represents a single rendering declared within a renderer annotation.
     */
    static public class RendersInfo {
        
        static String RENDERER_TYPE = "rendererType";
        static String COMPONENT_FAMILY = "componentFamily";
        
        Map annotationValueMap;
        
        RendersInfo(Map annotationValueMap) {
            this.annotationValueMap = annotationValueMap;
        }
        
        /**
         * The renderer type.
         */
        public String getRendererType() {
            if (this.annotationValueMap.containsKey(RENDERER_TYPE))
                return (String) this.annotationValueMap.get(RENDERER_TYPE);
            String[] componentFamilies = this.getComponentFamilies();
            if (componentFamilies.length > 0)
                return componentFamilies[0];
            return null;
        }
        
        /**
         * One or more component families to which this render type applies.
         */
        public String[] getComponentFamilies() {
            if (this.annotationValueMap.containsKey(COMPONENT_FAMILY)) {
                List componentFamilies = (List) this.annotationValueMap.get(COMPONENT_FAMILY);
                if (componentFamilies != null)
                    return (String[]) componentFamilies.toArray(new String[componentFamilies.size()]);
            }
            return new String[0];
        }
    }
    
}
