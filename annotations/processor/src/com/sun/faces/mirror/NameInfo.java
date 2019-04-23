/*
 * Copyright (c) 2019 Payara Services Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package com.sun.faces.mirror;

import javax.lang.model.element.Name;

/**
 *
 * @author jonathan
 */
public class NameInfo implements Name {
    
    private final String stored;
    
    public NameInfo(String input) {
        if (input == null) {
            throw new IllegalArgumentException("null provided to constuctor");
        }
        stored = input;
    }

    @Override
    public boolean contentEquals(CharSequence cs) {
        return stored.equals(cs);
    }

    @Override
    public int length() {
        return stored.length();
    }

    @Override
    public char charAt(int index) {
        return stored.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return stored.subSequence(start, end);
    }
    
}
