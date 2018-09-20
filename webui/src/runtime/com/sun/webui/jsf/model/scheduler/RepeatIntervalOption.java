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
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package com.sun.webui.jsf.model.scheduler;

import javax.faces.context.FacesContext;
import com.sun.webui.jsf.model.Option;

/**
 *
 * @author avk, John Yeary
 */
public class RepeatIntervalOption extends Option {

    private static final long serialVersionUID = 5893100201596785304L;

    /** Creates a new instance of RepeatMonthly */
    public RepeatIntervalOption(RepeatInterval repeatInterval) {
        this.setValue(repeatInterval);
    }

    @Override
    public String getLabel() {
        return ((RepeatInterval) getValue()).getLabel(FacesContext.getCurrentInstance());
    }
}
  
