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

package com.sun.webui.jsf.example.common;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * Factory class for retrieving server-side i18n messages for the Java ES(tm)
 * Monitoring Console within the JSF framework. This class does not override
 * all methods in com.sun.webui.jsf.util.MessageUtil; it merely provides a few
 * commonly used methods for getting resources using the Resources.properties
 * files for this web application.
 *
 * @version 1.3 10/10/05
 * @author  Sun Microsystems, Inc.
 */
public class MessageUtil {
    /** The ResourceBundle basename for this web app. */
    static final public String BASENAME = "com.sun.webui.jsf.example.resources.Resources";

    /** Get a message from the application's resource file. */
    static public String getMessage(String key) {
	return getMessage(key, (Object[]) null);
    }

    /** Get a formatted message from the application's resource file. */
    static public String getMessage(String key, String arg) {
	Object[] args = {
	    arg
	};
	return getMessage(key, args);
    }

    /** Get a formatted message from the application's resource file. */
    static public String getMessage(String key, Object[] args) {
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

	return getFormattedMessage((message != null) ? message : key, args);
    }

    /**
     * Format message using given arguments.
     *
     * @param message The string used as a pattern for inserting arguments.
     * @param args The arguments to be inserted into the string.
     */
    static protected String getFormattedMessage(String message, Object args[]) {
	if ((args == null) || (args.length == 0)) {
	    return message;
	}

	String result = null;
	try {
	    MessageFormat mf = new MessageFormat(message);
	    result = mf.format(args);
	} catch (NullPointerException e) {
	}

	return (result != null) ? result : message;
    }

    /** Get locale from the FacesContext object. */
    protected static Locale getLocale() {
	FacesContext context = FacesContext.getCurrentInstance();
	if (context == null) {
	    return Locale.getDefault();
	}

	// context.getViewRoot() may not have been initialized at this point.
	Locale locale = null;
	if (context.getViewRoot() != null) {
	    locale = context.getViewRoot().getLocale();
	}

	return (locale != null) ? locale : Locale.getDefault();
    }

} // MessageUtil
