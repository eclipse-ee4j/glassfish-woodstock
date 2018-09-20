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

package com.sun.webui.jsf.model;

import com.sun.webui.jsf.component.WizardStep;

public class WizardStepListItemBase implements WizardStepListItem {

    String stepNumberString;
    boolean substep;
    boolean branch;
    String placeholderText;
    boolean currentStep;
    boolean canGotoStep;
    WizardStep step;

    WizardStepListItemBase(WizardStep step, String stepNumberString,
            boolean currentStep, boolean substep, boolean branch,
            String placeholderText, boolean canGotoStep) {
        this.step = step;
        this.stepNumberString = stepNumberString;
        this.currentStep = currentStep;
        this.substep = substep;
        this.branch = branch;
        this.placeholderText = placeholderText;
        this.canGotoStep = canGotoStep;
    }

    public boolean isSubstep() {
        return substep;
    }

    public boolean isBranch() {
        return branch;
    }

    public String getPlaceholderText() {
        return placeholderText;
    }

    public boolean isCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(boolean currentStep) {
        this.currentStep = currentStep;
    }

    public boolean canGotoStep() {
        return canGotoStep;
    }

    public void setCanGotoStep(boolean canGotoStep) {
        this.canGotoStep = canGotoStep;
    }

    public WizardStep getStep() {
        return step;
    }

    public void setStep(WizardStep step) {
        this.step = step;
    }

    public String getStepNumberString() {
        return stepNumberString;
    }
}
