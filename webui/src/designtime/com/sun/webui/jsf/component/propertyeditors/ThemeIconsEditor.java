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

package com.sun.webui.jsf.component.propertyeditors;

import com.sun.rave.propertyeditors.SelectOneDomainEditor;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.rave.designtime.faces.FacesDesignProject;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.propertyeditors.domains.AttachedDomain;
import com.sun.rave.propertyeditors.domains.Domain;
import com.sun.rave.propertyeditors.domains.Element;
import com.sun.webui.jsf.util.ClassLoaderFinder;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import javax.faces.context.FacesContext;

/**
 * A custom property editor domain for available theme icons. The editor will
 * display symbolic names for all images discovered in the current theme. The JAR
 * that corresponds to the current theme is discovered by first searching the
 * context loader's class path for all instances of the theme manifest file.
 * Each of these is in turn searched for a name property that corresponds to the
 * current theme name. If found, that JAR's image properties file is searched
 * for images, and a list generated from the images' symbolic names.
 *
 * @author gjmurphy
 */
//TODO - Get theme name from faces context, or wherever it is stored when configured
public class ThemeIconsEditor extends SelectOneDomainEditor {
    
    public ThemeIconsEditor() {
        super(new ThemeIconsDomain());
    }
    
    public static class ThemeIconsDomain extends AttachedDomain {
        
        /**
         * Name of the manifest file that a JAR must contains for it to be a theme JAR.
         */
        final static String MANIFEST_FILE = "META-INF/MANIFEST.MF"; //NOI18N
        
        /**
         * Name of the manifest attribute that refers to the theme name.
         */
        final static String NAME_ATTRIBUTE = "X-SJWUIC-Theme-Name"; //NOI18N
        
        /**
         * Name of the manifest attribute that refers to the images properties file.
         */
        final static String IMAGES_ATTRIBUTE = "X-SJWUIC-Theme-Images"; //NOI18N
        
        
        static ResourceBundle imagesBundle = null;
        
        Element[] elements;
        
        /**
         * Return an array of elements that represent the currently available theme
         * icons. If this domain has not been attached to a design property, an
         * empty array is returned.
         */
        public Element[] getElements() {
            if (this.elements != null)
                return this.elements;
            if (imagesBundle == null) {
                if (this.getDesignProperty() == null)
                    return Element.EMPTY_ARRAY;
                FacesDesignContext designContext =
                        (FacesDesignContext) this.getDesignProperty().getDesignBean().getDesignContext();
                Locale locale = designContext.getFacesContext().getExternalContext().getRequestLocale();
                if (locale == null)
                    locale = Locale.getDefault();
                imagesBundle = loadImagesBundle("suntheme", locale);
            }
            if (imagesBundle == null) {
                this.elements = Element.EMPTY_ARRAY;
            } else {
                Enumeration imagesEnum = imagesBundle.getKeys();
                ArrayList elementList = new ArrayList();
                while (imagesEnum.hasMoreElements()) {
                    String resourceName = (String)imagesEnum.nextElement();
                    String resourceValue = imagesBundle.getString(resourceName);
                    if (resourceValue.endsWith("gif"))
                        elementList.add(new Element(resourceName));
                }
                this.elements = (Element[]) elementList.toArray(new Element[elementList.size()]);
                Arrays.sort(this.elements);
            }
            return this.elements;
        }
        
        /**
         * Search all theme JARs in context class path for that which has a theme
         * name corresponding to the name specified. If found, load the properties
         * for the theme's images, and return it.
         */
        private ResourceBundle loadImagesBundle(String themeName, Locale locale) {
            ResourceBundle bundle = null;
            try {
                DesignProperty designProperty = this.getDesignProperty();
                FacesDesignProject facesDesignProject =
                        (FacesDesignProject)designProperty.getDesignBean().getDesignContext().getProject();
                ClassLoader loader = facesDesignProject.getContextClassLoader();
                Enumeration filesEnum = loader.getResources(MANIFEST_FILE);
                while (filesEnum.hasMoreElements() && bundle == null) {
                    URL url = (URL) filesEnum.nextElement();
                    Manifest manifest = new Manifest(url.openConnection().getInputStream());
                    Attributes attributes = manifest.getAttributes("com/sun/webui/jsf/theme/");
                    if (attributes != null && themeName.equals(attributes.getValue(NAME_ATTRIBUTE))) {
                        String imagesBundleName = attributes.getValue(IMAGES_ATTRIBUTE);
                        bundle = ResourceBundle.getBundle(imagesBundleName, locale, loader);
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
            return bundle;
        }
        
    }
    
}
