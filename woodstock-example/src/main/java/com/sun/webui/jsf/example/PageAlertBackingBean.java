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
 * Backing bean for Page Alert example.
 */
public class PageAlertBackingBean implements Serializable {

    /**
     * Outcome strings used in the faces config.
     */
    private static final String SHOW_PAGEALERT_EXAMPLE = "showPageAlertExample";

    /**
     * Outcome strings used in the faces config.
     */
    private static final String SHOW_PAGEALERT = "showPageAlert";

    /**
     * Outcome strings used in the faces config.
     */
    private static final String SHOW_ALERT_INDEX = "showAlertIndex";

    /**
     * Holds value of property fieldValue.
     */
    private Integer fieldValue;

    /**
     * Holds value of property alertTitle.
     */
    private String alertTitle = null;

    /**
     * Holds value of property alertDetail.
     */
    private String alertDetail = null;

    /**
     * Holds value of property alertType.
     */
    private String alertType = null;

    /**
     * Random number.
     */
    private int randomNumber = 0;

    /**
     * Number of attempts.
     */
    private int count = 0;

    /**
     * User guess.
     */
    private int userGuess;

    /**
     * Default constructor.
     */
    public PageAlertBackingBean() {
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
     * Get the value of property alertTitle.
     * @return String
     */
    public String getAlertTitle() {
        return alertTitle;
    }

    /**
     * Get the value of property alertDetail.
     * @return String
     */
    public String getAlertDetail() {
        return alertDetail;
    }

    /**
     * Get the value of property alertType.
     * @return String
     */
    public String getAlertType() {
        return alertType;
    }

    /**
     * Display the inline alert if there was an error on the page.
     * @return {@code boolean}
     */
    public boolean getInlineAlertRendered() {
        FacesMessage.Severity severity
                = FacesContext.getCurrentInstance().getMaximumSeverity();
        return severity != null
                && severity.compareTo(FacesMessage.SEVERITY_ERROR) == 0;
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
            FacesMessage message = new FacesMessage();
            message.setDetail(MessageUtil.getMessage("alert_sumException"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    /**
     * Action handler when navigating to the page alert example.
     * @return String
     */
    public String showPageAlertExample() {
        return SHOW_PAGEALERT_EXAMPLE;
    }

    /**
     * Action handler when navigating to page alert.
     * @return String
     */
    public String showPageAlert() {
        return SHOW_PAGEALERT;
    }

    /**
     * Action handler when navigating to the main example index.
     * @return String
     */
    public String showExampleIndex() {
        fieldValue = null;
        resetValues();
        return IndexBackingBean.INDEX_ACTION;
    }

    /**
     * Action handler when navigating to the alert example index.
     * @return String
     */
    public String showAlertIndex() {
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
            userGuess = fieldValue;
            count++;

            // Test user guess with random number and display
            // the appropriate message.
            if (userGuess == randomNumber) {
                setAlertInfo("information", "alert_congratHeader",
                        "alert_numberCongrats", String.valueOf(randomNumber));
                resetValues();
            } else if (count >= 3) {
                setAlertInfo("information", "alert_gameOverHeader",
                        "alert_numberWrong", String.valueOf(randomNumber));
                resetValues();
            } else if (count == 2) {
                if (userGuess < randomNumber) {
                    setAlertInfo("warning", "alert_incorrectNumHeader",
                            "alert_detHigherLastChance", null);
                } else {
                    setAlertInfo("warning", "alert_incorrectNumHeader",
                            "alert_detLowerLastChance", null);
                }
            } else {
                if (userGuess < randomNumber) {
                    setAlertInfo("warning", "alert_incorrectNumHeader",
                            "alert_detHigherTryAgain", null);
                } else {
                    setAlertInfo("warning", "alert_incorrectNumHeader",
                            "alert_detLowerTryAgain", null);
                }
            }
        } catch (Exception e) {
            setAlertInfo("error", "alert_incorrectNumHeader",
                    "alert_sumExceptionWithHelp", null);
        }

        // Reset the field value so that the old guess doesn't
        // remain in the field.
        fieldValue = null;
    }

    /**
     * Set the alert type, title and detail message.
     *
     * @param type The alert type.
     * @param title The key for the alert title.
     * @param detail The alert detail message key.
     * @param detailArg The alert detail message arguments.
     */
    private void setAlertInfo(final String type, final String title,
            final String detail, final String detailArg) {

        alertType = type;
        alertTitle = MessageUtil.getMessage(title);
        String[] args = {detailArg};
        if (detailArg != null) {
            alertDetail = MessageUtil.getMessage(detail, args);
        } else {
            alertDetail = MessageUtil.getMessage(detail);
        }
    }

    /**
     * Reset to the initial values.
     */
    private void resetValues() {
        randomNumber = 0;
        count = 0;
    }
}
