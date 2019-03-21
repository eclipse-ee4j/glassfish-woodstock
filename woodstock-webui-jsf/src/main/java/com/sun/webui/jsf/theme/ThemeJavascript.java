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

package com.sun.webui.jsf.theme;

/**
 * <p> This class contains javascript related theme constants.</p>
 * TODO: Eventually these need to move to a theme-based
 * resource file.
 */
public class ThemeJavascript {

    /**
     * A Javascript file that contains Dojo functions.
     */
    public static final String DOJO = "dojo";
    /**
     * A properties file key whose value is a space separated list of
     * keys identifying javascript files that are included in every page.
     */
    public static final String GLOBAL = "global";
    /**
     * A Javascript file that contains JSON functions.
     */
    public static final String JSON = "json";
    /**
     * A Javascript file that contains JSF Extensions functions.
     */
    public static final String JSFX = "jsfx";
    /**
     * The path to module resources.
     */
    public static final String MODULE_PATH = "modulePath";
    /**
     * The module to prefix to all resources.
     */
    public static final String MODULE_PREFIX = "modulePrefix";
    /**
     * A Javascript file that contains Prototype functions.
     */
    public static final String PROTOTYPE = "prototype";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Deprecations
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * A Javascript file that contains functions for manipulating
     * the AddRemove component.
     *
     * @deprecated
     */
    public static final String ADD_REMOVE = "global";
    /**
     * A Javascript file that contains general functions used by
     * simple components.
     *
     * @deprecated
     */
    public static final String BASIC = "global";
    /**
     * A javascript file that contains functions for manipulating
     * the Calendar component.
     *
     * @deprecated
     */
    public static final String CALENDAR = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * cookies.
     *
     * @deprecated
     */
    public static final String COOKIE = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the common tasks section component.
     *
     * @deprecated
     */
    public static final String COMMONTASKSSECTION = "global";
    /**
     * A Javascript file that contains DynaFaces functions.
     *
     * @deprecated
     */
    public static final String DYNAFACES = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the EditableList component.
     *
     * @deprecated
     */
    public static final String EDITABLE_LIST = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the FileChooser component.
     *
     * @deprecated
     */
    public static final String FILE_CHOOSER = "global";
    /**
     * A Javascript file that contains functions for maintaining
     * the focus within the page.
     *
     * @deprecated
     */
    public static final String FOCUS_COOKIE = "global";
    /**
     * A Javascript prefix for locating function names.
     *
     * @deprecated Use MODULE_PREFIX.
     */
    public static final String JS_PREFIX = "modulePrefix";
    /**
     * A Javascript file that contains functions for manipulating
     * the OrderableList component.
     *
     * @deprecated
     */
    public static final String ORDERABLE_LIST = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the ProgressBar component.
     *
     * @deprecated
     */
    public static final String PROGRESSBAR = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the ProgressBar component based on JSF Extensions.
     *
     * @deprecated
     */
    public static final String PROGRESSBAR_DYNAFACES = "global";
    /**
     * A Javascript file that contains functions for maintaining
     * the scroll position within a page.
     *
     * @deprecated
     */
    public static final String SCROLL_COOKIE = "global";
    /**
     * A javascript file that contains functions for manipulating
     * the Scheduler component.
     *
     * @deprecated
     */
    public static final String SCHEDULER = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * component styles.
     *
     * @deprecated
     */
    public static final String STYLESHEET = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the Table component.
     *
     * @deprecated
     */
    public static final String TABLE = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the Tree component.
     *
     * @deprecated
     */
    public static final String TREE = "global";
    /**
     * A Javascript file that contains functions for manipulating
     * the Wizard component.
     *
     * @deprecated
     */
    public static final String WIZARD = "global";
    /**
     * A Javascript file that contains common functions for widgets.
     *
     * @deprecated
     */
    public static final String WIDGET = "global";
    /**
     * The location of the widget module assigned via Dojo.
     *
     * @deprecated Use MODULE_PATH.
     */
    public static final String WIDGET_MODULE = "modulePath";

    /**
     * This private constructor prevents this class from being instantiated
     * directly as its only purpose is to provide image constants.
     */
    private ThemeJavascript() {
        // do nothing
    }
}
