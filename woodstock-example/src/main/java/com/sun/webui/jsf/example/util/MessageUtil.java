/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.webui.jsf.example.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * Factory class for retrieving server-side i18n messages for the Java ES(tm)
 * Monitoring Console within the JSF framework. This class provides a few
 * commonly used methods for getting resources using the Resources.properties
 * files for this web application.
 */
public final class MessageUtil {

    /**
     * Cannot be instanciated.
     */
    private MessageUtil() {
    }

    /**
     * The ResourceBundle base name for this web app.
     */
    public static final String BASENAME =
            "com.sun.webui.jsf.example.resources.Resources";

    /**
     * Get a message from the application's resource file.
     * @param key message key
     * @return String
     */
    public static String getMessage(final String key) {
        return getMessage(key, (Object[]) null);
    }

    /**
     * Get a formatted message from the application's resource file.
     * @param key message key
     * @param arg single message argument
     * @return String
     */
    public static String getMessage(final String key, final String arg) {
        Object[] args = {
            arg
        };
        return getMessage(key, args);
    }

    /**
     * Get a formatted message from the application's resource file.
     * Get a formatted message from the application's resource file.
     * @param key message key
     * @param args message arguments
     * @return String
     */
    public static String getMessage(final String key, final Object[] args) {
        if (key == null) {
            return key;
        }

        ResourceBundle bundle = ResourceBundle.getBundle(BASENAME, getLocale());
        if (bundle == null) {
            throw new NullPointerException("Could not obtain resource bundle");
        }

        String message = null;
        try {
            message = bundle.getString(key);
        } catch (MissingResourceException e) {
        }

        if (message != null) {
            return getFormattedMessage(message, args);
        }
        return getFormattedMessage(key, args);
    }

    /**
     * Format message using given arguments.
     *
     * @param message The string used as a pattern for inserting arguments.
     * @param args The arguments to be inserted into the string.
     * @return String
     */
    private static String getFormattedMessage(final String message,
            final Object[] args) {

        if ((args == null) || (args.length == 0)) {
            return message;
        }

        String result = null;
        try {
            MessageFormat mf = new MessageFormat(message);
            result = mf.format(args);
        } catch (NullPointerException e) {
        }
        if (result != null) {
            return result;
        }
        return message;
    }

    /**
     * Get locale from the FacesContext object.
     * @return Locale
     */
    private static Locale getLocale() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null) {
            return Locale.getDefault();
        }

        // context.getViewRoot() may not have been initialized at this point.
        Locale locale = null;
        if (context.getViewRoot() != null) {
            locale = context.getViewRoot().getLocale();
        }

        if (locale != null) {
            return locale;
        }
        return Locale.getDefault();
    }
}
