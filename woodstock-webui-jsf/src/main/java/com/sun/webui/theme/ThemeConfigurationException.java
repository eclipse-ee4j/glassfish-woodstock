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

/**
 * Exception describing a problem configuring a specified {@link Theme}.
 */
public class ThemeConfigurationException extends RuntimeException {

    /**
     * Serialization UID.
     */
    private static final long serialVersionUID = 3798546969459906555L;

    /**
     * Construct a ThemeConfigurationException with a message.
     * @param message Message describing the configuration problem
     */
    public ThemeConfigurationException(String message) {
        super(message);
    }
}
