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
 * NoRepeatOption.java
 *
 * Created on July 14, 2005, 2:19 PM
 *
 */
package com.sun.webui.jsf.model.scheduler;

import javax.faces.context.FacesContext;
import com.sun.webui.jsf.model.Option;

/**
 *
 * @author avk, John Yeary
 */
public class RepeatUnitOption extends Option {

    private static final long serialVersionUID = -2361907202066625777L;

    /** Creates a new instance of RepeatMonthly */
    public RepeatUnitOption(RepeatUnit repeatUnit) {
        this.setValue(repeatUnit);
    }

    @Override
    public String getLabel() {
        return ((RepeatUnit) getValue()).getLabel(FacesContext.getCurrentInstance());
    }
}
  
