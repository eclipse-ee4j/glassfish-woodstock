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
package com.sun.webui.jsf.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class caches ResourceBundle objects per locale.
 */
public class ResourceBundleManager {

    /**
     * Singleton.
     */
    private static ResourceBundleManager _instance
            = new ResourceBundleManager();

    /**
     * The cache of ResourceBundles.
     */
    private Map<String, ResourceBundle> _cache =
            new HashMap<String, ResourceBundle>();

    /**
     * Use getInstance() to obtain an instance.
     */
    protected ResourceBundleManager() {
    }

    /**
     * Use this method to get the instance of this class.
     * @return ResourceBundleManager
     */
    public static ResourceBundleManager getInstance() {
        if (_instance == null) {
            _instance = new ResourceBundleManager();
        }
        return _instance;
    }

    /**
     * This method checks the cache for the requested resource bundle.
     *
     * @param baseName Name of the bundle
     * @param locale The locale
     *
     * @return The requested ResourceBundle in the most appropriate locale.
     */
    protected ResourceBundle getCachedBundle(String baseName, Locale locale) {
        return (ResourceBundle) _cache.get(getCacheKey(baseName, locale));
    }

    /**
     * This method generates a unique key for setting / getting Resources
     * bundles from the cache. It is important to have different keys per locale
     * (obviously).
     * @param baseName Name of the bundle
     * @param locale The locale
     * @return String
     */
    protected String getCacheKey(String baseName, Locale locale) {
        return baseName + "__" + locale.toString();
    }

    /**
     * This method adds a ResourceBundle to the cache.
     * @param baseName
     * @param locale
     * @param bundle
     */
    protected void addCachedBundle(String baseName, Locale locale,
            ResourceBundle bundle) {

        // Copy the old Map to prevent changing a Map while someone is
        // accessing it.
        Map<String, ResourceBundle> map =
                new HashMap<String, ResourceBundle>(_cache);

        // Add the new bundle
        map.put(getCacheKey(baseName, locale), bundle);

        // Set this new Map as the shared cache Map
        _cache = map;
    }

    /**
     * This method obtains the requested resource bundle as specified by the
     * given base name and locale.
     * @param baseName Name of the bundle
     * @param locale The locale
     * @return ResourceBundle
     */
    public ResourceBundle getBundle(String baseName, Locale locale) {
        ResourceBundle bundle = getCachedBundle(baseName, locale);
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(baseName, locale,
                    ClassLoaderFinder.getCurrentLoader(MessageUtil.class));
            if (bundle != null) {
                addCachedBundle(baseName, locale, bundle);
            }
        }
        return bundle;
    }

    /**
     * This method obtains the requested resource bundle as specified by the
     * given base name, locale, and class-loader.
     * @param baseName Name of the bundle
     * @param locale The locale
     * @param loader class-loader to use
     * @return ResourceBundle
     */
    public ResourceBundle getBundle(String baseName, Locale locale,
            ClassLoader loader) {

        ResourceBundle bundle = getCachedBundle(baseName, locale);
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(baseName, locale, loader);
            if (bundle != null) {
                addCachedBundle(baseName, locale, bundle);
            }
        }
        return bundle;
    }
}
