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
 * IntervalListener.java
 *
 * Created on September 30, 2005, 1:53 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package com.sun.webui.jsf.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import com.sun.webui.jsf.component.Scheduler;
import java.io.Serializable;

/**
 *
 * @author avk, John Yeary
 */
public class SchedulerPreviewListener implements ActionListener, Serializable {

    private static final long serialVersionUID = -8944673873698745402L;

    public void processAction(ActionEvent event) {

        UIComponent comp = event.getComponent();
        while (comp != null && !(comp instanceof Scheduler)) {
            comp = comp.getParent();
        }
        if (comp != null) {

            FacesContext context = FacesContext.getCurrentInstance();
            ((Scheduler) comp).processValidators(FacesContext.getCurrentInstance());
            if (((Scheduler) comp).isValid()) {
                ((Scheduler) comp).getDatePicker().setValue(((Scheduler) comp).getValue());
                ((Scheduler) comp).getDatePicker().displayValue();
            }
            context.renderResponse();
        }
    }
}
