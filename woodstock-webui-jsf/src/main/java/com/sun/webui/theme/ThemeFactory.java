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

package com.sun.webui.theme;

import java.util.Locale;

/**
 * Factory class responsible for setting up the Sun Web Component
 * application's ThemeManager.
 */
public interface ThemeFactory {

    // Private attribute names
    public final static String MANIFEST = "META-INF/MANIFEST.MF";
    public final static String FILENAME = "manifest-file";
    public final static String COMPONENTS_SECTION = "com/sun/webui/jsf/";
    public final static String THEME_SECTION = "com/sun/webui/jsf/theme/";
    public final static String THEME_VERSION_REQUIRED =
            "X-SJWUIC-Theme-Version-Required";
    public final static String THEME_VERSION = "X-SJWUIC-Theme-Version";
    public final static String NAME = "X-SJWUIC-Theme-Name";
    public final static String PREFIX = "X-SJWUIC-Theme-Prefix";
    public final static String DEFAULT = "X-SJWUIC-Theme-Default";
    public final static String STYLESHEETS = "X-SJWUIC-Theme-Stylesheets";
    public final static String JSFILES = "X-SJWUIC-Theme-JavaScript";
    public final static String CLASSMAPPER = "X-SJWUIC-Theme-ClassMapper";
    public final static String IMAGES = "X-SJWUIC-Theme-Images";
    public final static String MESSAGES = "X-SJWUIC-Theme-Messages";
    public final static String TEMPLATES = "X-SJWUIC-Theme-Templates";

    /**
     * Return the default {@code Theme} for {@code locale} within the theme
     * run-time environment of {@code themeContext}.
     *
     * @param locale
     * @param themeContext
     * @return
     */
    public Theme getTheme(Locale locale, ThemeContext themeContext);

    /**
     * Return the {@code themeName} {@code Theme} for {@code locale} within the
     * theme run-time environment of {@code themeContext}.
     *
     * @param themeName the theme name
     * @param locale the theme locale
     * @param themeContext the theme context
     * @return Theme
     */
    public Theme getTheme(String themeName, Locale locale,
            ThemeContext themeContext);

    /**
     * Hack - this will go away
     *
     * @param themeContext
     * @return
     */
    public String getDefaultThemeName(ThemeContext themeContext);
}
