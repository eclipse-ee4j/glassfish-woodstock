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

package com.sun.webui.jsf.design;

import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.faces.annotation.PropertyCategory;
import java.util.Locale;
import java.util.ResourceBundle;

public class CategoryDescriptors {

    private static final ResourceBundle bundle =
      ResourceBundle.getBundle("com.sun.webui.jsf.design.Bundle", // NOI18N
                               Locale.getDefault(),
                               CategoryDescriptors.class.getClassLoader());

    @PropertyCategory(name="Accessibility", sortKey="h")
    public static final CategoryDescriptor ACCESSIBILITY = new CategoryDescriptor(
        bundle.getString("accessibility"), bundle.getString("accessibilityCatDesc"), false); //NOI18N

    @PropertyCategory(name="Advanced", sortKey="j")
    public static final CategoryDescriptor ADVANCED = new CategoryDescriptor(
        bundle.getString("adv"), bundle.getString("advCatDesc"), false); //NOI18N

    @PropertyCategory(name="Appearance", sortKey="b")
    public static final CategoryDescriptor APPEARANCE = new CategoryDescriptor(
        bundle.getString("appear"), bundle.getString("appearCatDesc"), true); //NOI18N

    @PropertyCategory(name="Behavior", sortKey="g")
    public static final CategoryDescriptor BEHAVIOR = new CategoryDescriptor(
        bundle.getString("behavior"), bundle.getString("behaviorCatDesc"), false); //NOI18N

    @PropertyCategory(name="Data", sortKey="d")
    public static final CategoryDescriptor DATA = new CategoryDescriptor(
        bundle.getString("data"), bundle.getString("dataCatDesc"), true); //NOI18N

    @PropertyCategory(name="Events", sortKey="e")
    public static final CategoryDescriptor EVENTS = new CategoryDescriptor(
        bundle.getString("events"), bundle.getString("eventsCatDesc"), true); //NOI18N

    @PropertyCategory(name="General", sortKey="a")
    public static final CategoryDescriptor GENERAL = new CategoryDescriptor(
        bundle.getString("general"), bundle.getString("generalCatDesc"), true); //NOI18N

    @PropertyCategory(name="Internal", sortKey="k")
    public static final CategoryDescriptor INTERNAL = new CategoryDescriptor(
        bundle.getString("internal"), bundle.getString("internalCatDesc"), false); //NOI18N

    @PropertyCategory(name="Javascript", sortKey="i")
    public static final CategoryDescriptor JAVASCRIPT = new CategoryDescriptor(
        bundle.getString("javascript"), bundle.getString("javascriptCatDesc"), false); //NOI18N
    
    @PropertyCategory(name="Layout", sortKey="c")
    public static final CategoryDescriptor LAYOUT = new CategoryDescriptor(
        bundle.getString("layout"), bundle.getString("layoutCatDesc"), false); //NOI18N

    @PropertyCategory(name="Navigation", sortKey="f")
    public static final CategoryDescriptor NAVIGATION = new CategoryDescriptor(
        bundle.getString("navigation"), bundle.getString("navigationCatDesc"), false); //NOI18N

    private static CategoryDescriptor defaultCategoryDescriptors[] = {
            GENERAL, APPEARANCE, LAYOUT, DATA, EVENTS, NAVIGATION, BEHAVIOR, ACCESSIBILITY,
            JAVASCRIPT, ADVANCED, INTERNAL
        };

}
