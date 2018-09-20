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

package javax.faces.component;

import com.sun.rave.designtime.CategoryDescriptor;
import java.util.Locale;
import java.util.ResourceBundle;

public class CategoryDescriptorsConstants {
    
    protected static ResourceBundle bundle =
            ResourceBundle.getBundle("javax.faces.component.bundle", Locale.getDefault());

    public static final CategoryDescriptor ADVANCED =
            new CategoryDescriptor(bundle.getString("advanced_category"));

    public static final CategoryDescriptor APPEARANCE =
            new CategoryDescriptor(bundle.getString("appearance_category"));

    public static final CategoryDescriptor DATA = 
            new CategoryDescriptor(bundle.getString("data_category"));
    
    public static final CategoryDescriptor GENERAL =
            new CategoryDescriptor(bundle.getString("general_category"));

}
