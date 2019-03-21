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

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * <p>
 * Utility methods for localized messages for design time classes. This class
 * expects a resource bundle named <code>Bundle-DT</code> in the same pacakge
 * as the class passed to its constructor. This class is also useful for
 * design-time behavior in component renderers and so exists in this runtime
 * package.
 * </p>
 */
public class Bundle {


    // ------------------------------------------------------------- Constructor
    /**
     * <p>Construct a <code>Bundle</code> instance for the specified
     * class.</p>
     *
     * @param clazz Class for which to construct a bundle instance
     */
    public Bundle(Class clazz) {
        String name = clazz.getName();
        int period = name.lastIndexOf('.'); // NOI18N
        if (period >= 0) {
            name = name.substring(0, period + 1);
        } else {
            name = "";
        }
        name += "Bundle-DT";
        bundle =
                ResourceBundle.getBundle(name, format.getLocale(), clazz.getClassLoader());
    }
    // -------------------------------------------------------- Static Variables
    /**
     * <p>The <code>MessageFormat</code> instance we will use for messages
     * that require parameter replacement.</p>
     */
    private MessageFormat format = new MessageFormat("");
    // ------------------------------------------------------ Instance Variables
    /**
     * <p>The <code>ResourceBundle</code> containing our messages.</p>
     */
    private ResourceBundle bundle;


    // ---------------------------------------------------------- Public Methods
    /**
     * <p>Return the message for the specified key.</p>
     *
     * @param key Message key to look up
     */
    public String message(String key) {
        return bundle.getString(key);
    }

    /**
     * <p>Return the message for the specified key, after substituting
     * the specified parameters.</p>
     *
     * @param key Message key to look up
     * @param params Replacement parameters
     */
    public String message(String key, Object params[]) {
        String pattern = message(key);

        //FIXME synchronization on a non-final variable
        synchronized (format) {
            format.applyPattern(pattern);
            return format.format(params);
        }
    }
}
