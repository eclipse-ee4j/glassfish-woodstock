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
package com.sun.webui.jsf.example.util;

import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.ObjectArrayDataProvider;

/**
 * This class contains data provider.
 */
public final class UserData {

    /**
     * Data provider.
     */
    private TableDataProvider provider = null;

    /**
     * Default constructor.
     */
    public UserData() {
    }

    /**
     * Construct an instance using given Object array.
     * @param array data for the created provider
     */
    public UserData(final Object[] array) {
        provider = new ObjectArrayDataProvider(array);
    }

    /**
     * Get data provider.
     * @return TableDataProvider
     */
    public TableDataProvider getDataProvider() {
        return provider;
    }
}
