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

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.sun.webui.jsf.component.WizardStep;

public class WizardStepListBase implements WizardStepList {

    private WizardModel wModel;
    private int currentStep;
    private String currentStepNumberString;

    public WizardStepListBase(WizardModel wModel) {
        this.wModel = wModel;
        iterate();
    }

    public String getCurrentStepNumberString() {
        return currentStepNumberString;
    }
    private static final String DOT = "."; //NOI18N
    private static final String SWBRACKET_OPEN = "["; //NOI18N
    private static final String SWBRACKET_CLOSE = "["; //NOI18N

    protected String formatStepNumber(int stepNumber) {
        return String.valueOf(stepNumber);
    }

    protected String formatSubstepNumber(int stepNumber, int substep) {
        return String.valueOf(stepNumber).concat(DOT).
                concat(String.valueOf(substep));
    }

    protected String formatBranch(String placeholderText) {
        return SWBRACKET_OPEN.concat(placeholderText).concat(SWBRACKET_CLOSE);
    }

    // Do this to set up the state of the list,
    // basically set up currentStep details.
    //
    private void iterate() {
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
    }

    public Iterator iterator() {

        return new Iterator() {

            private int stepNumber = 0;
            private int substep = 0;
            private String stepNumberString;
            private String placeholderText;
            private boolean isBranch;
            private Iterator wizardStepIterator = wModel.getWizardStepIterator();

            public boolean hasNext() {
                return wizardStepIterator.hasNext();
            }

            public Object next() throws NoSuchElementException {

                if (isBranch) {
                    // Log too
                    throw new NoSuchElementException(
                            "No more steps after branch"); //NOI18N
                }

                boolean isCurrentStep = false;

                try {
                    WizardStep step = (WizardStep) wizardStepIterator.next();
                    boolean isSubstep = wModel.isSubstep(step);
                    isBranch = wModel.isBranch(step);
                    placeholderText = null;
                    // A substep cannot be the first step.
                    //
                    if (isSubstep) {
                        ++substep;
                        stepNumberString =
                                formatSubstepNumber(stepNumber, substep);
                    } else if (isBranch) {
                        ++stepNumber;
                        substep = 0;
                        /*
                        placeholderText =
                        formatBranch(wModel.getPlaceholderText(step));
                         */
                        stepNumberString = null;
                    } else {
                        ++stepNumber;
                        substep = 0;
                        stepNumberString = formatStepNumber(stepNumber);
                    }
                    isCurrentStep = wModel.isCurrentStep(step);
                    if (isCurrentStep) {
                        currentStep = stepNumber;
                        currentStepNumberString = stepNumberString;
                    }
                    return new WizardStepListItemBase(step,
                            stepNumberString, isCurrentStep,
                            isSubstep, isBranch,
                            placeholderText,
                            wModel.canGotoStep(step));


                } catch (Exception e) {
                    NoSuchElementException nse = new NoSuchElementException();
                    nse.initCause(e);
                    throw nse;
                }
            }

            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException();
            }
        };
    }
}
