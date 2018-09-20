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

/*
 * DropDownMethodExpression.java
 *
 * Created on June 29, 2006, 10:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.sun.webui.jsf.el;

import javax.faces.component.StateHolder;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.ELContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author dc151887, John Yeary
 */
public class DropDownMethodExpression extends MethodExpression implements StateHolder {

    private static final long serialVersionUID = 3229154216397152494L;

    /** Creates a new instance of DropDownMethodExpression */
    public DropDownMethodExpression() {
    }
    private transient String value = null;

    public void setValue(String value) {
        this.value = value;
    }

    public MethodInfo getMethodInfo(ELContext context) {
        return null;
    }

    public Object invoke(ELContext context, Object[] params) {
        return value;
    }

    public String getExpressionString() {
        return value;
    }

    public boolean isLiteralText() {
        return true;
    }

    //FIXME this does not comply with the contract for equals.
    public boolean equals(Object obj) {
        return true;
    }

    public int hashCode() {
        return value.hashCode();
    }
    private boolean transientFlag = false;

    public boolean isTransient() {
        return this.transientFlag;
    }

    public void setTransient(boolean transientFlag) {
        this.transientFlag = transientFlag;
    }

    public void restoreState(FacesContext context, Object state) {
        this.value = (String) state;
    }

    public Object saveState(FacesContext context) {
        return this.value;
    }
}
