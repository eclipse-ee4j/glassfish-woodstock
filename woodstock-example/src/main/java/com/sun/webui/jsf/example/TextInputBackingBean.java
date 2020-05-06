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

import jakarta.faces.event.ActionEvent;
import jakarta.faces.context.FacesContext;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionTitle;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.component.PasswordField;
import com.sun.webui.jsf.component.TextArea;
import com.sun.webui.jsf.example.util.MessageUtil;
import java.io.Serializable;

/**
 * Backing bean for the Text Input example.
 */
public final class TextInputBackingBean implements Serializable {

    /**
     * Text input default value.
     */
    private static final String TEXTINPUT_DEFAULT_VALUE
            = MessageUtil.getMessage("field_textFieldTitle");

    /**
     * Text area default value.
     */
    private static final String TEXTAREA_DEFAULT_VALUE
            = MessageUtil.getMessage("field_textAreaTitle");

    /**
     * Holds value of property textFieldValue.
     */
    private String textFieldValue = TEXTINPUT_DEFAULT_VALUE;

    /**
     * Holds value of property passwordValue.
     */
    private String passwordValue = "";

    /**
     * Holds value of property textAreaValue.
     */
    private String textAreaValue = TEXTAREA_DEFAULT_VALUE;

    /**
     * Holds value of property testCaseOptions.
     */
    private Option[] testCaseOptions = null;

    /**
     * Holds value of property textFieldDisabled.
     */
    private boolean textFieldDisabled = false;

    /**
     * Holds value of property passwordDisabled.
     */
    private boolean passwordDisabled = false;

    /**
     * Holds value of property textAreaDisabled.
     */
    private boolean textAreaDisabled = false;

    /**
     * Default constructor.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public TextInputBackingBean() {
        testCaseOptions = new Option[6];
        testCaseOptions[0] = new OptionTitle(
                MessageUtil.getMessage("field_testCaseTitle"));
        testCaseOptions[1] = new Option("field_testCaseToggleTextFieldState",
                MessageUtil.getMessage("field_testCaseToggleTextFieldState"));
        testCaseOptions[2] = new Option("field_testCaseTogglePasswordState",
                MessageUtil.getMessage("field_testCaseTogglePasswordState"));
        testCaseOptions[3] = new Option("field_testCaseToggleTextAreaState",
                MessageUtil.getMessage("field_testCaseToggleTextAreaState"));
        testCaseOptions[4] = new Option("field_testCaseDisableAll",
                MessageUtil.getMessage("field_testCaseDisableAll"));
        testCaseOptions[5] = new Option("field_testCaseEnableAll",
                MessageUtil.getMessage("field_testCaseEnableAll"));
    }

    /**
     * Get the value of the text field.
     * @return String
     */
    public String getTextFieldValue() {
        return textFieldValue;
    }

    /**
     * Set the value of the text field.
     * @param newTextFieldValue textFieldValue
     */
    public void setTextFieldValue(final String newTextFieldValue) {
        this.textFieldValue = newTextFieldValue;
    }

    /**
     * Get the value of the password field.
     * @return String
     */
    public String getPasswordValue() {
        return passwordValue;
    }

    /**
     * Set the value of the password field.
     * @param newPasswordValue passwordValue
     */
    public void setPasswordValue(final String newPasswordValue) {
        this.passwordValue = newPasswordValue;
    }

    /**
     * Get the value of the text area.
     * @return String
     */
    public String getTextAreaValue() {
        return textAreaValue;
    }

    /**
     * Set the value of the text area.
     * @param newTextAreaValue textAreaValue
     */
    public void setTextAreaValue(final String newTextAreaValue) {
        this.textAreaValue = newTextAreaValue;
    }

    /**
     * Return the array of test case options.
     * @return Option[]
     */
    public Option[] getTestCaseOptions() {
        return testCaseOptions;
    }

    /**
     * Get the disabled state of the text field.
     * @return {@code boolean}
     */
    public boolean getTextFieldDisabled() {
        return textFieldDisabled;
    }

    /**
     * Set the disabled state of the text field.
     * @param newTextFieldDisabled textFieldDisabled
     */
    public void setTextFieldDisabled(final boolean newTextFieldDisabled) {
        this.textFieldDisabled = newTextFieldDisabled;
    }

    /**
     * Get the disabled state of the password field.
     * @return {@code boolean}
     */
    public boolean getPasswordDisabled() {
        return passwordDisabled;
    }

    /**
     * Set the disabled state of the password field.
     * @param newPasswordDisabled passwordDisabled
     */
    public void setPasswordDisabled(final boolean newPasswordDisabled) {
        this.passwordDisabled = newPasswordDisabled;
    }

    /**
     * Get the disabled state of the text area.
     * @return {@code boolean}
     */
    public boolean getTextAreaDisabled() {
        return textAreaDisabled;
    }

    /**
     * Set the disabled state of the text area.
     * @param newTextAreaDisabled textAreaDisabled
     */
    public void setTextAreaDisabled(final boolean newTextAreaDisabled) {
        this.textAreaDisabled = newTextAreaDisabled;
    }

    /**
     * Return the state result for text field.
     * @return String
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public String getTextFieldResult() {
        Object[] args = new Object[3];
        args[0] = MessageUtil.getMessage("field_textFieldTitle");
        if (getTextFieldDisabled()) {
            args[1] = MessageUtil.getMessage("field_disabled");
        } else {
            args[1] = MessageUtil.getMessage("field_enabled");
        }
        args[2] = getTextFieldValue();

        return MessageUtil.getMessage("field_fieldResult", args);
    }

    /**
     * Return the state result for password.
     * @return String
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public String getPasswordResult() {
        Object[] args = new Object[3];
        args[0] = MessageUtil.getMessage("field_passwordTitle");
        if (getPasswordDisabled()) {
            args[1] = MessageUtil.getMessage("field_disabled");
        } else {
            args[1] = MessageUtil.getMessage("field_enabled");
        }
        args[2] = getPasswordValue();

        return MessageUtil.getMessage("field_fieldResult", args);
    }

    /**
     * Return the state result for text area.
     * @return String
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public String getTextAreaResult() {
        Object[] args = new Object[3];
        args[0] = MessageUtil.getMessage("field_textAreaTitle");
        if (getTextAreaDisabled()) {
            args[1] = MessageUtil.getMessage("field_disabled");
        } else {
            args[1] = MessageUtil.getMessage("field_enabled");
        }
        args[2] = getTextAreaValue();

        return MessageUtil.getMessage("field_fieldResult", args);
    }

    /**
     * Action handler when navigating to the main example index.
     * @return String
     */
    public String showExampleIndex() {
        // Reset when leaving the example.
        textFieldValue = TEXTINPUT_DEFAULT_VALUE;
        passwordValue = "";
        textAreaValue = TEXTAREA_DEFAULT_VALUE;
        disable(false);
        return IndexBackingBean.INDEX_ACTION;
    }

    /**
     * Action listener for the test case dropDown menu.
     * @param event action event
     */
    public void processMenuSelection(final ActionEvent event) {
        DropDown dropDown = (DropDown) event.getComponent();
        String selected = (String) dropDown.getSelected();
        FacesContext context = FacesContext.getCurrentInstance();
        TextField textField = (TextField) context.getViewRoot().findComponent(
                "form:textField");
        PasswordField password
                = (PasswordField) context.getViewRoot().findComponent(
                        "form:password");
        TextArea textArea = (TextArea) context.getViewRoot().findComponent(
                "form:textArea");

        // Since the action is immediate, the components won't
        // go through the Update Model phase.  So, we need to explicitly set the
        // state of the components and update the model object for the given
        // action selected.
        if (selected.equals("field_testCaseDisableAll")) {
            textField.setDisabled(true);
            password.setDisabled(true);
            textArea.setDisabled(true);
            disable(true);
        } else if (selected.equals("field_testCaseEnableAll")) {
            textField.setDisabled(false);
            password.setDisabled(false);
            textArea.setDisabled(false);
            disable(false);
        } else if (selected.equals("field_testCaseToggleTextFieldState")) {
            textField.setDisabled(!getTextFieldDisabled());
            setTextFieldDisabled(!getTextFieldDisabled());
        } else if (selected.equals("field_testCaseTogglePasswordState")) {
            password.setDisabled(!getPasswordDisabled());
            setPasswordDisabled(!getPasswordDisabled());
        } else if (selected.equals("field_testCaseToggleTextAreaState")) {
            textArea.setDisabled(!getTextAreaDisabled());
            setTextAreaDisabled(!getTextAreaDisabled());
        }
    }

    /**
     * Action listener for the preset button.
     * @param event action event
     */
    public void presetFields(final ActionEvent event) {
        // Sine the action is immediate, the text input components won't
        // go through the Update Model phase. However, their submitted values
        // get set in the Apply Request Value phase and these values are
        // retained when the page is redisplayed.
        //
        // So, we need to explicitly erase the submitted values and then update
        // the model object with initial values.
        FacesContext context = FacesContext.getCurrentInstance();

        TextField textField = (TextField) context.getViewRoot().findComponent(
                "form:textField");
        textField.setSubmittedValue(null);
        textField.setDisabled(false);
        textFieldValue = TEXTINPUT_DEFAULT_VALUE;

        PasswordField password
                = (PasswordField) context.getViewRoot().findComponent(
                        "form:password");
        password.setSubmittedValue(null);
        password.setDisabled(false);
        passwordValue = "";

        TextArea textArea = (TextArea) context.getViewRoot().findComponent(
                "form:textArea");
        textArea.setSubmittedValue(null);
        textArea.setDisabled(false);
        textAreaValue = TEXTAREA_DEFAULT_VALUE;

        disable(false);
    }

    /**
     * Helper method to set the disabled state of the input fields.
     * @param value new state
     */
    private void disable(final boolean value) {
        setTextFieldDisabled(value);
        setTextAreaDisabled(value);
        setPasswordDisabled(value);
    }
}
