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

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;

import com.sun.webui.jsf.example.util.MessageUtil;

import java.util.Random;
import java.io.Serializable;

/**
 * Backing bean for Inline Alert example.
 */
public final class InlineAlertBackingBean implements Serializable {

    /**
     * Outcome strings used in the faces config.
     */
    private static final String SHOW_INLINE_ALERT = "showInlineAlert";

    /**
     * Outcome strings used in the faces config.
     */
    private static final String SHOW_ALERT_INDEX = "showAlertIndex";

    /**
     * Holds value of property fieldValue.
     */
    private Integer fieldValue;

    /**
     * Holds value of property disabled.
     */
    private boolean disabled = false;

    /**
     * Holds value of property alertSummary.
     */
    private String alertSummary = null;

    /**
     * Holds value of property alertDetail.
     */
    private String alertDetail = null;

    /**
     * Holds value of property alertType.
     */
    private String alertType = null;

    /**
     * Holds value of property alertRendered.
     */
    private boolean alertRendered = false;

    /**
     * Holds value of property linkRendered.
     */
    private boolean linkRendered = false;

    /**
     * Random number.
     */
    private int randomNumber = 0;

    /**
     * Number of attempts.
     */
    private int attempts = 0;

    /**
     * User guess.
     */
    private int guess = 0;

    /**
     * Default constructor.
     */
    public InlineAlertBackingBean() {
    }

    /**
     * Set the value of property fieldValue.
     * @param newFieldValue fieldValue
     */
    public void setFieldValue(final Integer newFieldValue) {
        this.fieldValue = newFieldValue;
    }

    /**
     * Get the value of property fieldValue.
     * @return Integer
     */
    public Integer getFieldValue() {
        return fieldValue;
    }

    /**
     * Get the value of property disabled.
     * @return {@code boolean}
     */
    public boolean getDisabled() {
        return disabled;
    }

    /**
     * Get the value of property alertSummary.
     * @return String
     */
    public String getAlertSummary() {
        if (isTextInvalid()) {
            alertSummary = MessageUtil.getMessage("alert_sumException");
        }
        return alertSummary;
    }

    /**
     * Get the value of property alertDetail.
     * @return String
     */
    public String getAlertDetail() {
        if (isTextInvalid()) {
            alertDetail = null;
        }
        return alertDetail;
    }

    /**
     * Get the value of property alertType.
     * @return String
     */
    public String getAlertType() {
        if (isTextInvalid()) {
            alertType = "error";
        }
        return alertType;
    }

    /**
     * Get the value of property alertRendered.
     * @return {@code boolean}
     */
    public boolean getAlertRendered() {
        if (isTextInvalid()) {
            alertRendered = true;
        }
        return alertRendered;
    }

    /**
     * Get the value of property linkRendered.
     * @return {@code boolean}
     */
    public boolean getLinkRendered() {
        if (isTextInvalid()) {
            linkRendered = false;
        }
        return linkRendered;
    }

    /**
     * Text field validator. This will ONLY be called if a value was specified.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     * @throws ValidatorException if a validation error occurs
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public void validateFieldEntry(final FacesContext context,
            final UIComponent component, final Object value)
            throws ValidatorException {

        int num = ((Integer) value);

        // Number must be between 1 and 10.
        if (num < 1 || num > 10) {
            setAlertInfo("error", "alert_sumException", null, null, false);
            FacesMessage message = new FacesMessage();
            message.setDetail(MessageUtil.getMessage("alert_sumException"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    /**
     * Action handler for the Enter button.
     * @return String
     */
    public String handleAction() {
        // Return null to cause the page to re-render.
        return null;
    }

    /**
     * Action handler for the restart button.
     * @return String
     */
    public String restart() {
        resetAll();
        // Return null to cause the page to re-render.
        return null;
    }

    /**
     * Action handler when navigating to the inline alert example.
     * @return String
     */
    public String showInlineAlert() {
        return SHOW_INLINE_ALERT;
    }

    /**
     * Action handler when navigating to the main example index.
     * @return String
     */
    public String showExampleIndex() {
        resetAll();
        return IndexBackingBean.INDEX_ACTION;
    }

    /**
     * Action handler when navigating to the alert example index.
     * @return String
     */
    public String showAlertIndex() {
        fieldValue = null;
        disabled = false;
        alertRendered = false;
        return SHOW_ALERT_INDEX;
    }

    /**
     * Action listener for the Enter button.
     * @param event action event
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public void processButtonAction(final ActionEvent event) {

        // Get the random number
        if (randomNumber == 0) {
            Random r = new Random();
            randomNumber = r.nextInt(10) + 1;
        }

        try {
            // Get the value entered.
            guess = fieldValue;
            attempts++;

            // Test guess with random number and display the appropriate
            // message.
            if (guess == randomNumber) {
                setAlertInfo("success", "alert_sumCongrats",
                        "alert_number", String.valueOf(randomNumber), false);
                // Disable the button.
                disabled = true;
                reset();
            } else if (attempts >= 3) {
                setAlertInfo("information", "alert_sumWrong", "alert_number",
                        String.valueOf(randomNumber), false);
                // Disable the button.
                disabled = true;
                reset();
            } else if (attempts == 2) {
                if (guess < randomNumber) {
                    setAlertInfo("warning", "alert_sumLastChance",
                            "alert_detHigher", null, true);
                } else {
                    setAlertInfo("warning", "alert_sumLastChance",
                            "alert_detLower", null, true);
                }
            } else {
                if (guess < randomNumber) {
                    setAlertInfo("warning", "alert_sumTryAgain",
                            "alert_detHigher", null, true);
                } else {
                    setAlertInfo("warning", "alert_sumTryAgain",
                            "alert_detLower", null, true);
                }
            }
        } catch (Exception e) {
            setAlertInfo("error", "alert_sumException", null, null, false);
        }

        // Reset the field value so that the old guess doesn't
        // remain in the field.
        fieldValue = null;
    }

    /**
     * Set the alert properties that will be used by that component.
     *
     * @param type The alert type.
     * @param summary The alert summary message key.
     * @param detail The alert detail message key.
     * @param detailArg The alert detail message arguments.
     * @param rendered The rendered value for the alert link.
     */
    private void setAlertInfo(final String type, final String summary,
            final String detail, final String detailArg,
            final boolean rendered) {


        String[] args = {detailArg};
        if (detailArg != null) {
            alertDetail = MessageUtil.getMessage(detail, args);
        } else {
            alertDetail = MessageUtil.getMessage(detail);
        }

        alertSummary = MessageUtil.getMessage(summary);
        alertType = type;
        linkRendered = rendered;

        // Set the alertRendered to true so that when the page is
        // re-rendered, it shows the alert message.
        alertRendered = true;
    }

    /**
     * Check to see if text field is invalid.
     * @return {@code boolean}
     */
    private boolean isTextInvalid() {
        FacesMessage.Severity severity
                = FacesContext.getCurrentInstance().getMaximumSeverity();
        return severity != null
                && severity.compareTo(FacesMessage.SEVERITY_ERROR) == 0;
    }

    /**
     * Reset to initial values.
     */
    private void reset() {
        guess = 0;
        attempts = 0;
        randomNumber = 0;
    }

    /**
     * Reset all to their initial values.
     */
    private void resetAll() {
        disabled = false;
        alertRendered = false;
        fieldValue = null;
        reset();
    }
}
