/*
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.webui.jsf.example;

/**
 * This class is used to get the action value of an example app.
 */
public final class ExampleAction {

    /**
     * The action.
     */
    private final String action;

    /**
     * Construct a new instance of {@code ExampleAction}.
     *
     * @param act The example app action
     */
    public ExampleAction(final String act) {
        this.action = act;
    }

    /**
     * Return the action value.
     *
     * @return String
     */
    public String action() {
        return action;
    }
}
