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

import java.text.*;
import java.util.*;

import javax.faces.context.FacesContext;


/**
 * Factory class for retrieving server-side i18n messages within the JSF 
 * framework. Note that the ServletResponse locale, content type, and character 
 * encoding are not set here. Since tags may be used outside the Sun Web 
 * Console, that task will most likely be done in the console's session filter.
 * <p>
 * Example:
 * </p><code>
 * ResponseWriter w = FacesContext.getCurrentInstance().getResponseWriter();
 * w.write(MessageUtil.getMessage("com.sun.webui.jsf.Resources", "key"));
 * </code>
 *
 * @author Dan Labrecque
 */
public class MessageUtil extends Object {  
    // Default constructor.
    protected MessageUtil() {
    }

    /**
     * Get a message from a desired resource bundle.
     *
     * @param context The FacesContext object used to obtain locale.
     * @param baseName The fully qualified name of the resource bundle.
     * @param key The key for the desired string.
     * @throws NullPointerException if context or baseName is null.
     */     
    public static String getMessage(FacesContext context, String baseName,
            String key) {
        return getMessage(context, baseName, key, null);
    }

    /**
     * Get a formatted message from a desired resource bundle.
     *
     * @param context The FacesContext object used to obtain locale.
     * @param baseName The fully qualified name of the resource bundle.
     * @param key The key for the desired string.
     * @param args The arguments to be inserted into the string.
     * @throws NullPointerException if context or baseName is null.
     */    
    public static String getMessage(FacesContext context, String baseName, 
            String key, Object args[]) {
        return getMessage(getLocale(context), baseName, key, args);
    }  

    /**
     * Get a message from a desired resource bundle.
     *
     * @param baseName The fully qualified name of the resource bundle.
     * @param key The key for the desired string.
     * @throws NullPointerException if baseName is null.
     */    
    public static String getMessage(String baseName, String key) {
        return getMessage(baseName, key, null);
    }

    /**
     * Get a formatted message from a desired resource bundle.
     *
     * @param baseName The fully qualified name of the resource bundle.
     * @param key The key for the desired string.
     * @param args The arguments to be inserted into the string.
     * @throws NullPointerException if baseName is null.
     */
    public static String getMessage(String baseName, String key, 
            Object args[]) {
	return getMessage(getLocale(), baseName, key, args);
    }

    /**
     * Get a formatted message from a desired resource bundle.
     *
     * @param locale The locale for which a resource bundle is desired.
     * @param baseName The fully qualified name of the resource bundle.
     * @param key The key for the desired string.
     * @param args The arguments to be inserted into the string.
     * @throws NullPointerException if locale or baseName is null.
     */
    public static String getMessage(Locale locale, String baseName, String key,
            Object args[]) {
        ClassLoader loader =
                ClassLoaderFinder.getCurrentLoader(MessageUtil.class);
        // First try the context CL
        return getMessage(locale, baseName, key, args, loader);
    }

    /**
     * Get a formatted message from a desired resource bundle.
     *
     * @param locale The locale for which a resource bundle is desired.
     * @param baseName The fully qualified name of the resource bundle.
     * @param key The key for the desired string.
     * @param args The arguments to be inserted into the string.
     * @param loader The class loader used to load the resource bundle.
     * @throws NullPointerException if locale, baseName, or loader is null.
     */
    public static String getMessage(Locale locale, String baseName, String key, 
            Object args[], ClassLoader loader) {
        if (key == null)
            return key;
        else if (locale == null || baseName == null || loader == null)
            throw new NullPointerException("One or more parameters is null");
                
        ResourceBundle bundle = ResourceBundleManager.getInstance().getBundle(baseName, locale, 
            loader);
        
	if (null == bundle)
            throw new NullPointerException("Could not obtain resource bundle");

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
    protected static String getFormattedMessage(String message, Object args[]) {
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
    
    /**
     * Get locale from current FacesContext instance.
     */
    protected static Locale getLocale() {
        return getLocale(FacesContext.getCurrentInstance());
    }

    /**
     * Get locale from given FacesContext object.
     *
     * @param context The FacesContext object used to obtain locale.
     */
    protected static Locale getLocale(FacesContext context) {
        if (context == null) {
	    return Locale.getDefault();
	}
                
        Locale locale = null;
        
        // context.getViewRoot() may not have been initialized at this point.
        if (context.getViewRoot() != null)
            locale = context.getViewRoot().getLocale();
        
        return (locale != null) ? locale : Locale.getDefault();
    }
    
    /**
     * Get current class loader from given object.
     *
     * @param o Object used to obtain fallback class loader.
     */
    public static ClassLoader getCurrentLoader(Object o) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
	return (loader != null) ? loader : o.getClass().getClassLoader();
    }
}
