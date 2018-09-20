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

package com.sun.webui.jsf.component.util;

import com.sun.webui.jsf.util.ClassLoaderFinder;
import com.sun.webui.jsf.util.MessageUtil;

/**
 * Provides access to design-time resources with a Bundle-DT baseName.
 * 
 * @author Edwin Goei
 */
public class DesignMessageUtil {

    /**
     * Get a message from a design-time resource bundle.
     * 
     * @param clazz
     *            class determines package where resources are located
     * @param key
     *            The key for the desired string.
     * @return localized String
     */
    public static String getMessage(Class clazz, String key) {
        return getMessage(clazz, key, null);
    }

    /**
     * Get a formatted message from a design-time resource bundle.
     * 
     * @param clazz
     *            class determines package where resources are located
     * @param key
     *            The key for the desired string.
     * @param args
     *            The arguments to be inserted into the string.
     * @return localized String
     */
    public static String getMessage(Class clazz, String key, Object args[]) {
        String baseName = clazz.getPackage().getName() + ".Bundle-DT";
        return MessageUtil.getMessage(baseName, key, args);
    }
}
