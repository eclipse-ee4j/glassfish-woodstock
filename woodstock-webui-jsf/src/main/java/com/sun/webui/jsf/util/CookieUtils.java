/*
 * Copyright (c) 2010, 2018 Oracle and/or its affiliates. All rights reserved.
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

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;


/**
 *  <p>	Methods for working with cookies.</p>
 *
 *  @author Ken Paulsen (kenapaulsen@gmail.com)
 */
public class CookieUtils {

    /**
     *	<p> Gets the requested cookie name.  This method will ensure invalid
     *	    characters are not used in the name.  The cooresponding
     *	    <code>setCookieValue</code> call should be used to ensure proper
     *	    setting / retrieval of your cookie. (NOTE: there is a JS version
     *	    of these methods as well.)</p>
     */
    public static Cookie getCookieValue(FacesContext context, String name) {
	name = CookieUtils.getValidCookieName(name);
        return (Cookie) context.getExternalContext().getRequestCookieMap().get(name);
    }

    /**
     *	<p> Sets the specified cookie name / value.  This method will ensure
     *	    invalid characters are not used in the name.  The cooresponding
     *	    <code>getCookieValue</code> call should be used to ensure proper
     *	    setting / retrieval of your cookie. (NOTE: there is a JS version
     *	    of these methods as well.)</p>
     */
    public static void setCookieValue(FacesContext context, String name, String value) {
	// FIXME: not quite implemented...
	name = CookieUtils.getValidCookieName(name);
        context.getExternalContext().getRequestCookieMap().put(name, value);
    }

    /**
     *	<p> Ensure we use a RFC 2109 compliant cookie name.</p>
     */
    public static String getValidCookieName(String name) {
	for (char ch : badCookieChars) {
	    name = name.replace(ch, '_');
	}
	return name;
    }

    // Characters not allowed to be part of a cookie name (RFC 2109)
    private static final char badCookieChars[] = {
	    '(', ')', '<', '>', '@', ',', ';', ':', '\\',
	    '\'', '/', '[', ']', '?', '=', '{', '}', ' ', '\t'};
}
