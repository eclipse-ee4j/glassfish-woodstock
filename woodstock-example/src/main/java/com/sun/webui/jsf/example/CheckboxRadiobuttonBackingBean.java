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
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.Checkbox;
import com.sun.webui.jsf.component.RadioButton;
import com.sun.webui.jsf.example.util.MessageUtil;

/**
 * CBRB backing bean.
 */
public final class CheckboxRadiobuttonBackingBean implements Serializable {

    /**
     * Default selection value for the "red" checkbox.
     */
    private static final boolean RED_DEFAULT_SELECTED = false;

    /**
     * Default selection for the server button.
     */
    private static final boolean SERVER_DEFAULT_SELECTED = false;

    /**
     * Default selection for the volume button.
     */
    private static final boolean VOLUME_DEFAULT_SELECTED = true;

    /**
     * Default selection for the pool button.
     */
    private static final boolean POOL_DEFAULT_SELECTED = false;

    /**
     * Default selection for the server image.
     */
    private static final boolean SERVER_IMAGE_DEFAULT_SELECTED = false;

    /**
     * Default selection for the volume image.
     */
    private static final boolean VOLUME_IMAGE_DEFAULT_SELECTED = true;

    /**
     * Default selection for the pool image.
     */
    private static final boolean POOL_IMAGE_DEFAULT_SELECTED = false;

    /**
     * Red CB selected flag.
     */
    private boolean redSelected = RED_DEFAULT_SELECTED;

    /**
     * Server RB selected flag.
     */
    private boolean serverSelected = SERVER_DEFAULT_SELECTED;

    /**
     * Volume RB selected flag.
     */
    private boolean volumeSelected = VOLUME_DEFAULT_SELECTED;

    /**
     * Pool RB selected flag.
     */
    private boolean poolSelected = POOL_DEFAULT_SELECTED;

    /**
     * Server image selected flag.
     */
    private boolean serverImageSelected = SERVER_IMAGE_DEFAULT_SELECTED;

    /**
     * Volume image selected flag.
     */
    private boolean volumeImageSelected = VOLUME_IMAGE_DEFAULT_SELECTED;

    /**
     * Pool image selected flag.
     */
    private boolean poolImageSelected = POOL_IMAGE_DEFAULT_SELECTED;

    /**
     * Red CB disabled flag.
     */
    private boolean redCBDisabled = false;

    /**
     * Server RB disabled flag.
     */
    private boolean serverRBDisabled = false;

    /**
     * Volume RB disabled flag.
     */
    private boolean volumeRBDisabled = false;

    /**
     * Pool RB disabled flag.
     */
    private boolean poolRBDisabled = false;

    /**
     * Server image RB disabled flag.
     */
    private boolean serverImageRBDisabled = false;

    /**
     * Volume image RB disabled flag.
     */
    private boolean volumeImageRBDisabled = false;

    /**
     * Pool image RB disabled flag.
     */
    private boolean poolImageRBDisabled = false;

    /**
     * Test case options.
     */
    private Option[] testCaseOptions = null;

    /**
     * Creates a new instance of CheckboxRadiobuttonBackingBean.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public CheckboxRadiobuttonBackingBean() {
        testCaseOptions = new Option[6];
        testCaseOptions[0] = new OptionTitle(
                MessageUtil.getMessage("cbrb_testCase_title"));
        testCaseOptions[1] = new Option("cbrb_testCase_toggleCheckboxState",
                MessageUtil.getMessage("cbrb_testCase_toggleCheckboxState"));
        testCaseOptions[2] = new Option("cbrb_testCase_toggleRadiobuttonState",
                MessageUtil.getMessage("cbrb_testCase_toggleRadiobuttonState"));
        testCaseOptions[3] = new Option(
                "cbrb_testCase_toggleRadiobuttonImageState",
                MessageUtil.getMessage(
                        "cbrb_testCase_toggleRadiobuttonImageState"));
        testCaseOptions[4] = new Option("cbrb_testCase_disableAll",
                MessageUtil.getMessage("cbrb_testCase_disableAll"));
        testCaseOptions[5] = new Option("cbrb_testCase_enableAll",
                MessageUtil.getMessage("cbrb_testCase_enableAll"));
    }

    /**
     * Return the array of test case options.
     * @return Option[]
     */
    public Option[] getTestCaseOptions() {
        return testCaseOptions;
    }

    /**
     * ActionListener for the test case menu.
     * @param event action event
     */
    public void testCaseActionListener(final ActionEvent event) {
        DropDown dropDown = (DropDown) event.getComponent();
        String selected = (String) dropDown.getSelected();

        // Since the action is immediate, the components won't
        // go through the Update Model phase.  So, we need to explicitly set the
        // values and update the model object for the given action selected.
        Checkbox cb;
        RadioButton rb;
        FacesContext context = FacesContext.getCurrentInstance();

        if (selected.equals("cbrb_testCase_toggleCheckboxState")) {
            cb = (Checkbox) context.getViewRoot()
                    .findComponent("form1:RedCheckbox");
            cb.setDisabled(!getRedCBDisabled());
            setRedCBDisabled(!getRedCBDisabled());

        } else if (selected.equals("cbrb_testCase_toggleRadiobuttonState")) {
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbServer");
            rb.setDisabled(!getServerRBDisabled());
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbVolume");
            rb.setDisabled(!getVolumeRBDisabled());
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbPool");
            rb.setDisabled(!getPoolRBDisabled());

            setServerRBDisabled(!getServerRBDisabled());
            setVolumeRBDisabled(!getVolumeRBDisabled());
            setPoolRBDisabled(!getPoolRBDisabled());

        } else if (selected.equals(
                "cbrb_testCase_toggleRadiobuttonImageState")) {
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbimServer");
            rb.setDisabled(!getServerImageRBDisabled());
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbimVolume");
            rb.setDisabled(!getVolumeImageRBDisabled());
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbimPool");
            rb.setDisabled(!getPoolImageRBDisabled());

            setServerImageRBDisabled(!getServerImageRBDisabled());
            setVolumeImageRBDisabled(!getVolumeImageRBDisabled());
            setPoolImageRBDisabled(!getPoolImageRBDisabled());

        } else if (selected.equals("cbrb_testCase_disableAll")) {
            cb = (Checkbox) context.getViewRoot()
                    .findComponent("form1:RedCheckbox");
            cb.setDisabled(true);
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbServer");
            rb.setDisabled(true);
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbVolume");
            rb.setDisabled(true);
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbPool");
            rb.setDisabled(true);
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbimServer");
            rb.setDisabled(true);
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbimVolume");
            rb.setDisabled(true);
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbimPool");
            rb.setDisabled(true);

            setRedCBDisabled(true);
            setServerRBDisabled(true);
            setVolumeRBDisabled(true);
            setPoolRBDisabled(true);
            setServerImageRBDisabled(true);
            setVolumeImageRBDisabled(true);
            setPoolImageRBDisabled(true);

        } else if (selected.equals("cbrb_testCase_enableAll")) {
            cb = (Checkbox) context.getViewRoot()
                    .findComponent("form1:RedCheckbox");
            cb.setDisabled(false);
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbServer");
            rb.setDisabled(false);
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbVolume");
            rb.setDisabled(false);
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbPool");
            rb.setDisabled(false);
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbimServer");
            rb.setDisabled(false);
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbimVolume");
            rb.setDisabled(false);
            rb = (RadioButton) context.getViewRoot()
                    .findComponent("form1:rbimPool");
            rb.setDisabled(false);

            setRedCBDisabled(false);
            setServerRBDisabled(false);
            setVolumeRBDisabled(false);
            setPoolRBDisabled(false);
            setServerImageRBDisabled(false);
            setVolumeImageRBDisabled(false);
            setPoolImageRBDisabled(false);
        }
    }

    /**
     * Reset.
     */
    private void reset() {
        // Set the initial selected values
        setRedSelected(RED_DEFAULT_SELECTED);
        setServerSelected(SERVER_DEFAULT_SELECTED);
        setVolumeSelected(VOLUME_DEFAULT_SELECTED);
        setPoolSelected(POOL_DEFAULT_SELECTED);
        setServerImageSelected(SERVER_IMAGE_DEFAULT_SELECTED);
        setVolumeImageSelected(VOLUME_IMAGE_DEFAULT_SELECTED);
        setPoolImageSelected(POOL_IMAGE_DEFAULT_SELECTED);

        // Set the initial states
        setRedCBDisabled(false);
        setServerRBDisabled(false);
        setVolumeRBDisabled(false);
        setPoolRBDisabled(false);
        setServerImageRBDisabled(false);
        setVolumeImageRBDisabled(false);
        setPoolImageRBDisabled(false);
    }

    /**
     * ActionListener for the Reset button.
     * @param event action event
     */
    public void resetActionListener(final ActionEvent event) {
        // Since the action is immediate, the components won't
        // go through the Update Model phase. However, its submitted value
        // gets set in the Apply Request Value phase and this value is retained
        // when the page is redisplayed.
        //
        // So, we need to explicitly erase the submitted values and then update
        // the model object with initial values.
        Checkbox cb;
        RadioButton rb;

        FacesContext context = FacesContext.getCurrentInstance();
        cb = (Checkbox) context.getViewRoot()
                .findComponent("form1:RedCheckbox");
        cb.setSubmittedValue(null);

        rb = (RadioButton) context.getViewRoot()
                .findComponent("form1:rbServer");
        rb.setSubmittedValue(null);
        rb = (RadioButton) context.getViewRoot()
                .findComponent("form1:rbVolume");
        rb.setSubmittedValue(null);
        rb = (RadioButton) context.getViewRoot()
                .findComponent("form1:rbPool");
        rb.setSubmittedValue(null);

        rb = (RadioButton) context.getViewRoot()
                .findComponent("form1:rbimServer");
        rb.setSubmittedValue(null);
        rb = (RadioButton) context.getViewRoot()
                .findComponent("form1:rbimVolume");
        rb.setSubmittedValue(null);
        rb = (RadioButton) context.getViewRoot()
                .findComponent("form1:rbimPool");
        rb.setSubmittedValue(null);

        reset();
    }

    /**
     * Action handler for the test case drop-down menu.
     * @return String
     */
    public String testCaseActionHandler() {
        // Returning null causes page to re-render.
        return null;
    }

    /**
     * Return the enable/disable state of the checkbox.
     * @return {@code boolean}
     */
    public boolean getRedCBDisabled() {
        return redCBDisabled;
    }

    /**
     * Set the enable/disable state of the checkbox.
     * @param state new state
     */
    public void setRedCBDisabled(final boolean state) {
        redCBDisabled = state;
    }

    /**
     * Return the enable/disable state of the server radio-button.
     * @return {@code boolean}
     */
    public boolean getServerRBDisabled() {
        return serverRBDisabled;
    }

    /**
     * Set the enable/disable state of the server radio-button.
     * @param state new state
     */
    public void setServerRBDisabled(final boolean state) {
        serverRBDisabled = state;
    }

    /**
     * Return the enable/disable state of the volume radio-button.
     * @return {@code boolean}
     */
    public boolean getVolumeRBDisabled() {
        return volumeRBDisabled;
    }

    /**
     * Set the enable/disable state of the volume radio-button.
     * @param state new state
     */
    public void setVolumeRBDisabled(final boolean state) {
        volumeRBDisabled = state;
    }

    /**
     * Return the enable/disable state of the pool radio-button.
     * @return {@code boolean}
     */
    public boolean getPoolRBDisabled() {
        return poolRBDisabled;
    }

    /**
     * Set the enable/disable state of the pool radio-button.
     * @param state new state
     */
    public void setPoolRBDisabled(final boolean state) {
        poolRBDisabled = state;
    }

    /**
     * Return the enable/disable state of the server image radio-button.
     * @return {@code boolean}
     */
    public boolean getServerImageRBDisabled() {
        return serverImageRBDisabled;
    }

    /**
     * Set the enable/disable state of the server image radio-button.
     * @param state new state
     */
    public void setServerImageRBDisabled(final boolean state) {
        serverImageRBDisabled = state;
    }

    /**
     * Return the enable/disable state of the volume image radio-button.
     * @return {@code boolean}
     */
    public boolean getVolumeImageRBDisabled() {
        return volumeImageRBDisabled;
    }

    /**
     * Set the enable/disable state of the volume image radio-button.
     * @param state new state
     */
    public void setVolumeImageRBDisabled(final boolean state) {
        volumeImageRBDisabled = state;
    }

    /**
     * Return the enable/disable state of the pool image radio-button.
     * @return {@code boolean}
     */
    public boolean getPoolImageRBDisabled() {
        return poolImageRBDisabled;
    }

    /**
     * Set the enable/disable state of the pool radio-button.
     * @param state new state
     */
    public void setPoolImageRBDisabled(final boolean state) {
        poolImageRBDisabled = state;
    }

    /**
     * Return the selected state of the "red" checkbox.
     * @return {@code boolean}
     */
    public boolean getRedSelected() {
        return redSelected;
    }

    /**
     * Set the selected state of the "red" checkbox.
     * @param state new state
     */
    public void setRedSelected(final boolean state) {
        redSelected = state;
    }

    /**
     * Return the selected state of the server radio-button.
     * @return {@code boolean}
     */
    public boolean getServerSelected() {
        return serverSelected;
    }

    /**
     * Set the selected state of the server radio-button.
     * @param state new state
     */
    public void setServerSelected(final boolean state) {
        serverSelected = state;
    }

    /**
     * Return the selected state of the volume radio-button.
     * @return {@code boolean}
     */
    public boolean getVolumeSelected() {
        return volumeSelected;
    }

    /**
     * Set the selected state of the volume radio-button.
     * @param state new state
     */
    public void setVolumeSelected(final boolean state) {
        volumeSelected = state;
    }

    /**
     * Return the selected state of the pool radio-button.
     * @return {@code boolean}
     */
    public boolean getPoolSelected() {
        return poolSelected;
    }

    /**
     * Set the selected state of the volume radio-button.
     * @param state new state
     */
    public void setPoolSelected(final boolean state) {
        poolSelected = state;
    }

    /**
     * Return the selected state of the server image radio-button.
     * @return {@code boolean}
     */
    public boolean getServerImageSelected() {
        return serverImageSelected;
    }

    /**
     * Set the selected state of the server image radio-button.
     * @param state new state
     */
    public void setServerImageSelected(final boolean state) {
        serverImageSelected = state;
    }

    /**
     * Return the selected state of the volume image radio-button.
     * @return {@code boolean}
     */
    public boolean getVolumeImageSelected() {
        return volumeImageSelected;
    }

    /**
     * Set the selected state of the volume image radio-button.
     * @param state new state
     */
    public void setVolumeImageSelected(final boolean state) {
        volumeImageSelected = state;
    }

    /**
     * Return the selected state of the pool image radio-button.
     * @return {@code boolean}
     */
    public boolean getPoolImageSelected() {
        return poolImageSelected;
    }

    /**
     * Set the selected state of the volume image radio-button.
     * @param state new state
     */
    public void setPoolImageSelected(final boolean state) {
        poolImageSelected = state;
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
     * Return the state result for the checkbox.
     * @return String
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public String getCheckboxResult() {
        String[] args = new String[4];
        args[0] = MessageUtil.getMessage("cbrb_checkboxResult");
        args[1] = MessageUtil.getMessage("crcb_redCheckbox");
        if (getRedSelected()) {
            args[2] = MessageUtil.getMessage("cbrb_selected");
        } else {
            args[2] = MessageUtil.getMessage("cbrb_notSelected");
        }
        if (getRedCBDisabled()) {
            args[3] = MessageUtil.getMessage("cbrb_disabled");
        } else {
            args[3] = MessageUtil.getMessage("cbrb_enabled");
        }
        return MessageUtil.getMessage("cbrb_result", args);
    }

    /**
     * Return the state result for the radioButton.
     * @return String
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public String getRadioButtonResult() {
        String[] args = new String[4];
        args[0] = MessageUtil.getMessage("cbrb_radioButtonResult");
        if (getServerSelected()) {
            args[1] = MessageUtil.getMessage("cbrb_radioButton1");
            if (getServerRBDisabled()) {
                args[3] = MessageUtil.getMessage("cbrb_disabled");
            } else {
                args[3] = MessageUtil.getMessage("cbrb_enabled");
            }
        }
        if (getVolumeSelected()) {
            args[1] = MessageUtil.getMessage("cbrb_radioButton2");
            if (getVolumeRBDisabled()) {
                args[3] = MessageUtil.getMessage("cbrb_disabled");
            } else {
                args[3] = MessageUtil.getMessage("cbrb_enabled");
            }
        }
        if (getPoolSelected()) {
            args[1] = MessageUtil.getMessage("cbrb_radioButton3");
            if (getPoolRBDisabled()) {
                args[3] = MessageUtil.getMessage("cbrb_disabled");
            } else {
                args[3] = MessageUtil.getMessage("cbrb_enabled");
            }
        }
        args[2] = MessageUtil.getMessage("cbrb_selected");
        return MessageUtil.getMessage("cbrb_result", args);
    }

    /**
     * Return the state result for the radioButton image.
     * @return String
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public String getRadioButtonImageResult() {
        String[] args = new String[4];
        args[0] = MessageUtil.getMessage("cbrb_radioButtonImageResult");
        if (getServerImageSelected()) {
            args[1] = MessageUtil.getMessage("cbrb_radioButton1");
            if (getServerImageRBDisabled()) {
                args[3] = MessageUtil.getMessage("cbrb_disabled");
            } else {
                args[3] = MessageUtil.getMessage("cbrb_enabled");
            }
        }
        if (getVolumeImageSelected()) {
            args[1] = MessageUtil.getMessage("cbrb_radioButton2");
            if (getVolumeImageRBDisabled()) {
                args[3] = MessageUtil.getMessage("cbrb_disabled");
            } else {
                args[3] = MessageUtil.getMessage("cbrb_enabled");
            }
        }
        if (getPoolImageSelected()) {
            args[1] = MessageUtil.getMessage("cbrb_radioButton3");
            if (getPoolImageRBDisabled()) {
                args[3] = MessageUtil.getMessage("cbrb_disabled");
            } else {
                args[3] = MessageUtil.getMessage("cbrb_enabled");
            }
        }
        args[2] = MessageUtil.getMessage("cbrb_selected");
        return MessageUtil.getMessage("cbrb_result", args);
    }
}
