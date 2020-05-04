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

import java.io.Serializable;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionTitle;
import com.sun.webui.jsf.example.util.MessageUtil;

/**
 * Backing bean for Button example.
 *
 * Note that we must implement {@code java.io.Serializable} or
 * {@Code javax.faces.component.StateHolder} in case client-side state saving is
 * used, or if server-side state saving is used with a distributed system.
 */
public final class ButtonBackingBean implements Serializable {

    /**
     * Test case options.
     */
    private Option[] testCaseOptions = null;

    /**
     * Summary.
     */
    private String summary = null;

    /**
     * Detail.
     */
    private String detail = null;

    /**
     * Primary disabled flag.
     */
    private boolean primaryDisabled = false;

    /**
     * Primary mini disabled flag.
     */
    private boolean primaryMiniDisabled = false;

    /**
     * Secondary disabled flag.
     */
    private boolean secondaryDisabled = false;

    /**
     * Secondary mini disabled flag.
     */
    private boolean secondaryMiniDisabled = false;

    /**
     * Creates a new instance of ButtonBackingBean.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public ButtonBackingBean() {
        testCaseOptions = new Option[3];
        testCaseOptions[0] = new OptionTitle(
                MessageUtil.getMessage("button_testCase_title"));
        testCaseOptions[1] = new Option("button_testCase_disableAll",
                MessageUtil.getMessage("button_testCase_disableAll"));
        testCaseOptions[2] = new Option("button_testCase_enableAll",
                MessageUtil.getMessage("button_testCase_enableAll"));
    }

    /**
     * Return the array of test case options.
     * @return Option[]
     */
    public Option[] getTestCaseOptions() {
        return testCaseOptions;
    }

    /**
     * Action listener for icon button.
     * @param event action event
     */
    public void iconActionListener(final ActionEvent event) {
        setAlertInfo(event, null);
    }

    /**
     * Action listener for primary button.
     * @param event action event
     */
    public void primaryActionListener(final ActionEvent event) {
        setAlertInfo(event, "button_primaryButtonText");
    }

    /**
     * Action listener for primary mini button.
     * @param event action event
     */
    public void primaryMiniActionListener(final ActionEvent event) {
        setAlertInfo(event, "button_primaryMiniButtonText");
    }

    /**
     * Action listener for secondary button.
     * @param event action event
     */
    public void secondaryActionListener(final ActionEvent event) {
        setAlertInfo(event, "button_secondaryButtonText");
    }

    /**
     * Action listener for secondary mini button.
     * @param event action event
     */
    public void secondaryMiniActionListener(final ActionEvent event) {
        setAlertInfo(event, "button_secondaryMiniButtonText");
    }

    /**
     * Set the alert summary and detail messages based on the component
     * associated with the event. The valueKey is the message key for the detail
     * message, or null if no detail is required.
     * @param event action event
     * @param valueKey message key
     */
    private void setAlertInfo(final ActionEvent event, final String valueKey) {
        FacesContext context = FacesContext.getCurrentInstance();
        String clientID = event.getComponent().getClientId(context);

        // We only want the last part of the fully-qualified clientID
        int n = clientID.lastIndexOf(':');
        if (n >= 0) {
            clientID = clientID.substring(n + 1);
        }

        Object[] args;
        String detailKey;
        if (valueKey != null) {
            args = new Object[2];
            args[1] = MessageUtil.getMessage(valueKey);
            detailKey = "button_alertElementDetail";
        } else {
            args = new Object[1];
            detailKey = "button_alertElementDetailNoValue";
        }
        args[0] = clientID;
        summary = MessageUtil.getMessage("button_alertElement");
        detail = MessageUtil.getMessage(detailKey, args);
    }

    /**
     * Action handler for all buttons.
     * @return String
     */
    public String actionHandler() {
        // Returning null causes page to re-render.
        return null;
    }

    /**
     * Action handler for the test case drop-down menu.
     * @return String
     */
    public String testCaseActionHandler() {
        // Disable the alert by having no summary and detail.
        summary = null;
        detail = null;

        return null;
    }

    /**
     * Return the enable/disable state of the primary button.
     * @return {@code boolean}
     */
    public boolean getPrimaryDisabled() {
        return primaryDisabled;
    }

    /**
     * Set the enable/disable state of the primary button.
     * @param newPrimaryDisabled primaryDisabled
     */
    public void setPrimaryDisabled(final boolean newPrimaryDisabled) {
        primaryDisabled = newPrimaryDisabled;
    }

    /**
     * Return the enable/disable state of the primary mini button.
     * @return {@code boolean}
     */
    public boolean getPrimaryMiniDisabled() {
        return primaryMiniDisabled;
    }

    /**
     * Set the enable/disable state of the primary mini button.
     * @param newPrimaryMiniDisabled primaryMiniDisabled
     */
    public void setPrimaryMiniDisabled(final boolean newPrimaryMiniDisabled) {
        primaryMiniDisabled = newPrimaryMiniDisabled;
    }

    /**
     * Return the enable/disable state of the secondary button.
     * @return {@code boolean}
     */
    public boolean getSecondaryDisabled() {
        return secondaryDisabled;
    }

    /**
     * Set the enable/disable state of the secondary button.
     * @param newSecondaryDisabled secondaryDisabled
     */
    public void setSecondaryDisabled(final boolean newSecondaryDisabled) {
        secondaryDisabled = newSecondaryDisabled;
    }

    /**
     * Return the enable/disable state of the secondary mini button.
     * @return {@code boolean}
     */
    public boolean getSecondaryMiniDisabled() {
        return secondaryMiniDisabled;
    }

    /**
     * Set the enable/disable state of the secondary mini button.
     * @param newSecondaryMiniDisabled secondaryMiniDisabled
     */
    public void setSecondaryMiniDisabled(
            final boolean newSecondaryMiniDisabled) {

        secondaryMiniDisabled = newSecondaryMiniDisabled;
    }

    /**
     * Return the alert summary message.
     * @return String
     */
    public String getAlertSummary() {
        return summary;
    }

    /**
     * Return the alert detail message.
     * @return String
     */
    public String getAlertDetail() {
        return detail;
    }

    /**
     * Return the render state of the alert.
     * @return {@code boolean}
     */
    public boolean getAlertRendered() {
        return summary != null;
    }

    /**
     * Return the selected state of the primary button's checkbox.
     * @return {@code boolean}
     */
    public boolean getPrimaryCBSelected() {
        // Checkbox state is inverse of button's disabled state.
        return !getPrimaryDisabled();
    }

    /**
     * Set the selected state of the primary button's checkbox.
     * @param state new primary state
     */
    public void setPrimaryCBSelected(final boolean state) {
        // Checkbox state is inverse of button's disabled state.
        setPrimaryDisabled(!state);
    }

    /**
     * Return the selected state of the primary mini button's checkbox.
     * @return {@code boolean}
     */
    public boolean getPrimaryMiniCBSelected() {
        // Checkbox state is inverse of button's disabled state.
        return !getPrimaryMiniDisabled();
    }

    /**
     * Set the selected state of the primary mini button's checkbox.
     * @param state new primary mini state
     */
    public void setPrimaryMiniCBSelected(final boolean state) {
        // Checkbox state is inverse of button's disabled state.
        setPrimaryMiniDisabled(!state);
    }

    /**
     * Return the selected state of the secondary button's checkbox.
     * @return {@code boolean}
     */
    public boolean getSecondaryCBSelected() {
        // Checkbox state is inverse of button's disabled state.
        return !getSecondaryDisabled();
    }

    /**
     * Set the selected state of the secondary button's checkbox.
     * @param state new secondary state
     */
    public void setSecondaryCBSelected(final boolean state) {
        // Checkbox state is inverse of button's disabled state.
        setSecondaryDisabled(!state);
    }

    /**
     * Return the selected state of the secondary mini button's checkbox.
     * @return {@code boolean}
     */
    public boolean getSecondaryMiniCBSelected() {
        // Checkbox state is inverse of button's disabled state.
        return !getSecondaryMiniDisabled();
    }

    /**
     * Set the selected state of the secondary mini button's checkbox.
     * @param state new secondary mini state
     */
    public void setSecondaryMiniCBSelected(final boolean state) {
        // Checkbox state is inverse of button's disabled state.
        setSecondaryMiniDisabled(!state);
    }

    /**
     * Action handler when navigating to the main example index.
     * @return String
     */
    public String showExampleIndex() {
        reset();
        return IndexBackingBean.INDEX_ACTION;
    }

    /**
     * Reset.
     */
    private void reset() {
        summary = null;
        detail = null;
        primaryDisabled = false;
        primaryMiniDisabled = false;
        secondaryDisabled = false;
        secondaryMiniDisabled = false;
    }

    /**
     * Return the state result for the primary button.
     * @return String
     */
    public String getPrimaryResult() {
        String buttonLabel = MessageUtil.getMessage("button_primaryButtonText");
        if (getPrimaryDisabled()) {
            return MessageUtil.getMessage("button_resultDisabled", buttonLabel);
        } else {
            return MessageUtil.getMessage("button_resultEnabled", buttonLabel);
        }
    }

    /**
     * Return the state result for the primary-mini button.
     * @return String
     */
    public String getPrimaryMiniResult() {
        String buttonLabel = MessageUtil
                .getMessage("button_primaryMiniButtonText");
        if (getPrimaryMiniDisabled()) {
            return MessageUtil.getMessage("button_resultDisabled", buttonLabel);
        } else {
            return MessageUtil.getMessage("button_resultEnabled", buttonLabel);
        }
    }

    /**
     * Return the state result for the secondary button.
     * @return String
     */
    public String getSecondaryResult() {
        String buttonLabel = MessageUtil
                .getMessage("button_secondaryButtonText");
        if (getSecondaryDisabled()) {
            return MessageUtil.getMessage("button_resultDisabled", buttonLabel);
        } else {
            return MessageUtil.getMessage("button_resultEnabled", buttonLabel);
        }
    }

    /**
     * Return the state result for the secondary-mini button.
     * @return String
     */
    public String getSecondaryMiniResult() {
        String buttonLabel = MessageUtil
                .getMessage("button_secondaryMiniButtonText");
        if (getSecondaryMiniDisabled()) {
            return MessageUtil.getMessage("button_resultDisabled", buttonLabel);
        } else {
            return MessageUtil.getMessage("button_resultEnabled", buttonLabel);
        }
    }
}
