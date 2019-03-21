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

package com.sun.webui.jsf.event;

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.AbortProcessingException;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.component.Wizard;

/**
 * The WizardActionListener is an internal listener specified on the
 * navigation and tab components of the {@link Wizard Wizard} component.
 */
public class WizardActionListener implements ActionListener, StateHolder {

    private String wizardId;
    private int event;
    private boolean _transient = false;

    /**
     * Create a new instance of <code>WizardActionListener</code>
     * identifying the wizard and event this listener is registered for.
     * This class is used internally to set the internal event state
     * of the wizard.
     */
    public WizardActionListener(String wizardId, int event) {
        super();
        this.wizardId = wizardId;
        this.event = event;
    }

    /**
     * Default constructor for restoring and saving state
     */
    public WizardActionListener() {
        super();
    }

    /**
     * This method locates the Wizard listening for this event and 
     * sets the event state, for eventual broadcast to 
     * {@link WizardEventListeners WizardListeners}.
     *
     * @param actionEvent The ActionEvent generated
     */
    public void processAction(ActionEvent actionEvent)
            throws AbortProcessingException {

        Wizard wizard = Wizard.getWizard((UIComponent) actionEvent.getSource(), wizardId);
        if (wizard == null) {
            // Log  and throw
            if (LogUtil.infoEnabled()) {
                LogUtil.info(WizardActionListener.class, "WEBUI0006",
                        new String[]{wizardId});
            }
            throw new AbortProcessingException(
                    "Wizard parent not found processing " +
                    event);
        }
        wizard.broadcastEvent((UIComponent) actionEvent.getSource(), event);
    }

    /**
     * Save the state of this listener.
     */
    public Object saveState(FacesContext context) {
        Object _values[] = new Object[3];
        _values[0] = new Integer(event);
        _values[1] = new String(wizardId);
        _values[2] = new Boolean(_transient);
        return _values;
    }

    /**
     * Restor the state of this listener.
     */
    public void restoreState(FacesContext context, Object state) {
        Object _values[] = (Object[]) state;
        this.event = ((Integer) _values[0]).intValue();
        this.wizardId = (String) _values[1];
        this._transient = ((Boolean) _values[2]).booleanValue();
    }

    public boolean isTransient() {
        return _transient;
    }

    /**
     * Set the transient nature of this StateHolder.
     */
    public void setTransient(boolean newTransientValue) {
        // Don't honor this.
    }
}
