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
 * <p>Factory class responsible for setting up the Sun Web Component
 * application's ThemeManager.</p>
 */
public interface ThemeFactory {

    // Private attribute names
    public final static String MANIFEST = "META-INF/MANIFEST.MF"; //NOI18N
    public final static String FILENAME = "manifest-file"; //NOI18N
    public final static String COMPONENTS_SECTION = "com/sun/webui/jsf/"; //NOI18N
    public final static String THEME_SECTION = "com/sun/webui/jsf/theme/"; //NOI18N
    public final static String THEME_VERSION_REQUIRED =
            "X-SJWUIC-Theme-Version-Required"; //NOI18N
    public final static String THEME_VERSION = "X-SJWUIC-Theme-Version"; //NOI18N
    public final static String NAME = "X-SJWUIC-Theme-Name"; //NOI18N
    public final static String PREFIX = "X-SJWUIC-Theme-Prefix"; //NOI18N
    public final static String DEFAULT = "X-SJWUIC-Theme-Default"; //NOI18N
    public final static String STYLESHEETS = "X-SJWUIC-Theme-Stylesheets"; //NOI18N
    public final static String JSFILES = "X-SJWUIC-Theme-JavaScript"; //NOI18N
    public final static String CLASSMAPPER = "X-SJWUIC-Theme-ClassMapper"; //NOI18N
    public final static String IMAGES = "X-SJWUIC-Theme-Images"; //NOI18N
    public final static String MESSAGES = "X-SJWUIC-Theme-Messages"; //NOI18N
    public final static String TEMPLATES = "X-SJWUIC-Theme-Templates"; //NOI18N

    /**
     * Return the default <code>Theme</code> for
     * <code>locale</code> within the theme runtime environment of
     * <code>themeContext</code>.
     */
    public Theme getTheme(Locale locale, ThemeContext themeContext);

    /**
     * Return the <code>themeName</code> <code>Theme</code> for
     * <code>locale</code> within the theme runtime environment of
     * <code>themeContext</code>.
     */
    public Theme getTheme(String themeName, Locale locale,
            ThemeContext themeContext);

    /**
     * Hack - this will go away
     */
    public String getDefaultThemeName(ThemeContext themeContext);
}
