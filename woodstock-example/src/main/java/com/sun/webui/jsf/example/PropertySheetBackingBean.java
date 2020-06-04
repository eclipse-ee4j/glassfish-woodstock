/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;

/**
 * Backing Bean for property sheet example.
 */
public final class PropertySheetBackingBean implements Serializable {

    /**
     * Creates a new instance of PropertySheetBackingBean.
     */
    public PropertySheetBackingBean() {
    }

    /**
     * Holds value for jumpLinkChkBox property.
     */
    private boolean jumpLinkChkBox = true;

    /**
     * Holds value for requiredLabelChkBox property.
     */
    private boolean requiredLabelChkBox = true;

    /**
     * Holds value for jumpLink property.
     */
    private boolean jumpLink = true;

    /**
     * Holds value for requiredlabel.
     */
    private String requiredLabel = "true";

    /**
     * This method assigns value to jumpLinks property of property sheet tag.
     * @return {@code boolean}
     */
    public boolean getJumpLink() {
        return jumpLink;
    }

    /**
     * this method assigns value to requiredFields property of property sheet
     * tag.
     * @return String
     */
    public String getRequiredLabel() {
        return requiredLabel;
    }

    /**
     * Getter method for jumpLinkChkBox property.
     * @return {@code boolean}
     */
    public boolean getJumpLinkChkBox() {
        return jumpLinkChkBox;
    }

    /**
     * Getter method for requiredLabelChkBox property.
     * @return {@code boolean}
     */
    public boolean getRequiredLabelChkBox() {
        return requiredLabelChkBox;
    }

    /**
     * Setter method for jumpLinkChkBox property.
     * @param newJumpChkBox jumpChkBox
     */
    public void setJumpLinkChkBox(final boolean newJumpChkBox) {
        this.jumpLinkChkBox = newJumpChkBox;
    }

    /**
     * Setter method for requiredLabelChkBox property.
     * @param newRequiredChkBox requiredChkBox
     */
    public void setRequiredLabelChkBox(final boolean newRequiredChkBox) {
        this.requiredLabelChkBox = newRequiredChkBox;
    }

    /**
     * valueChangelistener for checkbox that sets jumpLink property.
     * @param event value change event
     */
    public void jumpMenulistener(final ValueChangeEvent event) {
        Boolean newValue = (Boolean) event.getNewValue();
        jumpLink = newValue != null && newValue;
    }

    /**
     * valueChangelistener for checkbox that sets requiredLabel property.
     * @param event value change event
     */
    public void requiredValuelistener(final ValueChangeEvent event) {
        Boolean newValue = (Boolean) event.getNewValue();
        if (newValue != null && newValue) {
            requiredLabel = "true";
        } else {
            requiredLabel = "false";
        }

    }

    /**
     * Action handler when navigating to the main example index.
     * @return String
     */
    public String showExampleIndex() {
        jumpLinkChkBox = true;
        requiredLabelChkBox = true;
        jumpLink = true;
        requiredLabel = "true";
        return IndexBackingBean.INDEX_ACTION;
    }
}
