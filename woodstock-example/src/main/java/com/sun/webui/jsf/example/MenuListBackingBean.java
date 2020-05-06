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
import com.sun.webui.jsf.model.OptionGroup;
import com.sun.webui.jsf.model.OptionTitle;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.Listbox;
import com.sun.webui.jsf.example.util.MessageUtil;
import java.io.Serializable;

/**
 * Backing bean for Menu and List example.
 */
public final class MenuListBackingBean implements Serializable {

    /**
     * Holds value of property testCaseOptions.
     */
    private Option[] testCaseOptions = null;

    /**
     * Holds value of property jumpMenuOptions.
     */
    private Option[] jumpMenuOptions = null;

    /**
     * Holds value of property jumpMenuSelectedOption.
     */
    private String jumpMenuSelectedOption = null;

    /**
     * Holds value of property standardMenuOptions.
     */
    private Option[] standardMenuOptions = null;

    /**
     * Holds value of property standardMenuSelectedOption.
     */
    private String standardMenuSelectedOption = null;

    /**
     * Holds value of property listboxOptions.
     */
    private Option[] listboxOptions = null;

    /**
     * Holds value of property listboxSelectedOption.
     */
    private String listboxSelectedOption = null;

    /**
     * Holds value of property jumpMenuDisbaled.
     */
    private boolean jumpMenuDisabled = false;

    /**
     * Holds value of property standardMenuDisbaled.
     */
    private boolean standardMenuDisabled = false;

    /**
     * Holds value of property listboxDisbaled.
     */
    private boolean listboxDisabled = false;

    /**
     * Holds value of property alertDetail.
     */
    private String alertDetail = null;

    /**
     * Holds value of property alertRendered.
     */
    private boolean alertRendered = false;

    /**
     * Flag indicationg the disabled state of the menus and list.
     */
    private boolean menusDisabled = false;

    /**
     * Outcome strings used in the faces config file.
     */
    private static final String SHOW_MENULIST_RESULTS = "showMenuListResults";

    /**
     * Default constructor.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public MenuListBackingBean() {
        // Test case menu options.
        testCaseOptions = new Option[5];
        testCaseOptions[0] = new OptionTitle(
                MessageUtil.getMessage("menu_testCase0"));
        testCaseOptions[1] = new Option("menu_testCase1",
                MessageUtil.getMessage("menu_testCase1"));
        testCaseOptions[2] = new Option("menu_testCase2",
                MessageUtil.getMessage("menu_testCase2"));
        testCaseOptions[3] = new Option("menu_testCase3",
                MessageUtil.getMessage("menu_testCase3"));
        testCaseOptions[4] = new Option("menu_testCase4",
                MessageUtil.getMessage("menu_testCase4"));

        // Jump menu option group 1.
        Option[] groupedOptions1 = new Option[3];
        groupedOptions1[0] = new Option("action_A_value",
                MessageUtil.getMessage("menu_actiona"));
        groupedOptions1[1] = new Option("action_B_value",
                MessageUtil.getMessage("menu_actionb"));
        groupedOptions1[2] = new Option("action_C_value",
                MessageUtil.getMessage("menu_actionc"));

        OptionGroup jumpGroup1 = new OptionGroup();
        jumpGroup1.setLabel(MessageUtil.getMessage("menu_actionGroup1"));
        jumpGroup1.setOptions(groupedOptions1);

        // Jump menu option group 2.
        Option[] groupedOptions2 = new Option[3];
        groupedOptions2[0] = new Option("action_D_value",
                MessageUtil.getMessage("menu_actiond"));
        groupedOptions2[1] = new Option("action_E_value",
                MessageUtil.getMessage("menu_actione"));
        groupedOptions2[2] = new Option("action_F_value",
                MessageUtil.getMessage("menu_actionf"));

        OptionGroup jumpGroup2 = new OptionGroup();
        jumpGroup2.setLabel(MessageUtil.getMessage("menu_actionGroup2"));
        jumpGroup2.setOptions(groupedOptions2);

        // Jump menu options.
        jumpMenuOptions = new Option[9];
        jumpMenuOptions[0] = new OptionTitle(
                MessageUtil.getMessage("menu_action0"));
        jumpMenuOptions[1] = new Option("action_1_value",
                MessageUtil.getMessage("menu_action1"));
        jumpMenuOptions[2] = new Option("action_2_value",
                MessageUtil.getMessage("menu_action2"));
        jumpMenuOptions[3] = new Option("action_3_value",
                MessageUtil.getMessage("menu_action3"));
        jumpMenuOptions[4] = jumpGroup1;
        jumpMenuOptions[5] = jumpGroup2;
        jumpMenuOptions[6] = new Option("action_4_value",
                MessageUtil.getMessage("menu_action4"));
        jumpMenuOptions[7] = new Option("action_5_value",
                MessageUtil.getMessage("menu_action5"));
        jumpMenuOptions[8] = new Option("action_6_value",
                MessageUtil.getMessage("menu_action6"));

        // Standard menu and list option group 1.
        Option[] listGroupedOptions1 = new Option[3];
        listGroupedOptions1[0] = new Option("option_A_value",
                MessageUtil.getMessage("menu_optiona"));
        listGroupedOptions1[1] = new Option("option_B_value",
                MessageUtil.getMessage("menu_optionb"));
        listGroupedOptions1[2] = new Option("option_C_value",
                MessageUtil.getMessage("menu_optionc"));

        OptionGroup standardGroup1 = new OptionGroup();
        standardGroup1.setLabel(MessageUtil.getMessage("menu_optionGroup1"));
        standardGroup1.setOptions(listGroupedOptions1);

        // Standard menu and list option group 2.
        Option[] listGroupedOptions2 = new Option[3];
        listGroupedOptions2[0] = new Option("option_D_value",
                MessageUtil.getMessage("menu_optiond"));
        listGroupedOptions2[1] = new Option("option_E_value",
                MessageUtil.getMessage("menu_optione"));
        listGroupedOptions2[2] = new Option("option_F_value",
                MessageUtil.getMessage("menu_optionf"));

        OptionGroup standardGroup2 = new OptionGroup();
        standardGroup2.setLabel(MessageUtil.getMessage("menu_optionGroup2"));
        standardGroup2.setOptions(listGroupedOptions2);

        // Standard menu and scrolling list options.
        Option[] options = new Option[9];
        options[0] = new Option("option_0_value",
                MessageUtil.getMessage("menu_option0"));
        options[1] = new Option("option_1_value",
                MessageUtil.getMessage("menu_option1"));
        options[2] = new Option("option_2_value",
                MessageUtil.getMessage("menu_option2"));
        options[3] = new Option("option_3_value",
                MessageUtil.getMessage("menu_option3"));
        options[4] = standardGroup1;
        options[5] = standardGroup2;
        options[6] = new Option("option_4_value",
                MessageUtil.getMessage("menu_option4"));
        options[7] = new Option("option_5_value",
                MessageUtil.getMessage("menu_option5"));
        options[8] = new Option("option_6_value",
                MessageUtil.getMessage("menu_option6"));

        standardMenuOptions = options;
        listboxOptions = options;

        // Set the initial selection for standard menu and scrolling list.
        standardMenuSelectedOption = (String) standardMenuOptions[0].getValue();
        listboxSelectedOption = (String) listboxOptions[0].getValue();
    }

    /**
     * Return the array of test case options.
     * @return Option[]
     */
    public Option[] getTestCaseOptions() {
        return testCaseOptions;
    }

    /**
     * Return the array of jump menu options.
     * @return Option[]
     */
    public Option[] getJumpMenuOptions() {
        return this.jumpMenuOptions;
    }

    /**
     * Set the array of jump menu options.
     * @param newJumpMenuOptions jumpMenuOptions
     */
    public void setJumpMenuOptions(final Option[] newJumpMenuOptions) {
        this.jumpMenuOptions = newJumpMenuOptions;
    }

    /**
     * Get the value of property jumpMenuSelectedOption.
     * @return String
     */
    public String getJumpMenuSelectedOption() {
        return this.jumpMenuSelectedOption;
    }

    /**
     * Set the value of property jumpMenuSelectedOption.
     * @param newJumpMenuSelectedOption jumpMenuSelectedOption
     */
    public void setJumpMenuSelectedOption(
            final String newJumpMenuSelectedOption) {

        this.jumpMenuSelectedOption = newJumpMenuSelectedOption;
    }

    /**
     * Return the array of standard menu options.
     * @return Option[]
     */
    public Option[] getStandardMenuOptions() {
        return this.standardMenuOptions;
    }

    /**
     * Set the array of standard menu options.
     * @param newStandardMenuOptions standardMenuOptions
     */
    public void setStandardMenuOptions(final Option[] newStandardMenuOptions) {
        this.standardMenuOptions = newStandardMenuOptions;
    }

    /**
     * Get the value of property standardMenuSelectedOption.
     * @return String
     */
    public String getStandardMenuSelectedOption() {
        return this.standardMenuSelectedOption;
    }

    /**
     * Set the value of property standardMenuSelectedOption.
     * @param newStandardMenuSelectedOption standardMenuSelectedOption
     */
    public void setStandardMenuSelectedOption(
            final String newStandardMenuSelectedOption) {

        this.standardMenuSelectedOption = newStandardMenuSelectedOption;
    }

    /**
     * Return the array of list-box options.
     * @return Option[]
     */
    public Option[] getListboxOptions() {
        return this.listboxOptions;
    }

    /**
     * Get the value of property listboxSelectedOption.
     * @return String
     */
    public String getListboxSelectedOption() {
        return this.listboxSelectedOption;
    }

    /**
     * Set the value of property listboxSelectedOption.
     * @param newListboxSelectedOption listboxSelectedOption
     */
    public void setListboxSelectedOption(
            final String newListboxSelectedOption) {

        this.listboxSelectedOption = newListboxSelectedOption;
    }

    /**
     * Get the disabled state of the jump menu.
     * @return {@code boolean}
     */
    public boolean getJumpMenuDisabled() {
        return jumpMenuDisabled;
    }

    /**
     * Set the disabled state of the jump menu.
     * @param newJumpMenuDisabled jumpMenuDisabled
     */
    public void setJumpMenuDisabled(final boolean newJumpMenuDisabled) {
        this.jumpMenuDisabled = newJumpMenuDisabled;
    }

    /**
     * Get the disabled state of the standard menu.
     * @return {@code boolean}
     */
    public boolean getStandardMenuDisabled() {
        return standardMenuDisabled;
    }

    /**
     * Set the disabled state of the standard menu.
     * @param newStandardMenuDisabled standardMenuDisabled
     */
    public void setStandardMenuDisabled(final boolean newStandardMenuDisabled) {
        this.standardMenuDisabled = newStandardMenuDisabled;
    }

    /**
     * Get the disabled state of the scroll list.
     * @return {@code boolean}
     */
    public boolean getListboxDisabled() {
        return listboxDisabled;
    }

    /**
     * Set the disabled state of the scroll list.
     * @param newListboxDisabled listboxDisabled
     */
    public void setListboxDisabled(final boolean newListboxDisabled) {
        this.listboxDisabled = newListboxDisabled;
    }

    /**
     * Get the value of property alertDetail.
     * @return String
     */
    public String getAlertDetail() {
        return alertDetail;
    }

    /**
     * Get the value of property alertRendered.
     * @return {@code boolean}
     */
    public boolean getAlertRendered() {
        return alertRendered;
    }

    /**
     * Return the result for standard menu.
     * @return String
     */
    public String getStandardMenuResult() {
        String noOption = (String) standardMenuOptions[0].getValue();
        if (standardMenuSelectedOption.equals(noOption)) {
            return MessageUtil.getMessage("menu_noOption");
        }
        return standardMenuSelectedOption;
    }

    /**
     * Return the result for scrolling list.
     * @return String
     */
    public String getScrollingListResult() {
        String noOption = (String) listboxOptions[0].getValue();
        if (listboxSelectedOption.equals(noOption)) {
            return MessageUtil.getMessage("menu_noOption");
        }
        return listboxSelectedOption;
    }

    /**
     * Action listener for the jump drop-down menu.
     * @param event action event
     */
    public void processJumpMenuSelection(final ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        String clientID = event.getComponent().getClientId(context);

        DropDown dropDown = (DropDown) event.getComponent();
        String selected = (String) dropDown.getSelected();

        // We only want the last part of the fully-qualified clientID
        int n = clientID.lastIndexOf(':');
        if (n >= 0) {
            clientID = clientID.substring(n + 1);
        }

        Object[] args = new Object[2];
        args[0] = clientID;
        args[1] = selected;
        alertDetail = MessageUtil.getMessage("menu_alertElementDetail", args);
        alertRendered = true;
    }

    /**
     * Action listener for the test case drop-down menu.
     * @param event action event
     */
    public void processTestCaseMenuSelection(final ActionEvent event) {
        // disable alert.
        alertRendered = false;

        // Since the action is immediate, the components won't go through the
        // Update Model phase.  So, we need to explicitly set the values and
        // update the model object for the given action selected.
        FacesContext context = FacesContext.getCurrentInstance();
        DropDown dropDown = (DropDown) event.getComponent();
        String selected = (String) dropDown.getSelected();
        Listbox listbox;
        DropDown dd;

        if (selected.equals("menu_testCase1")) {
            listbox = (Listbox) context.getViewRoot().findComponent(
                    "form:scrollList");
            listbox.setSubmittedValue(null);

            dd = (DropDown) context.getViewRoot().findComponent(
                    "form:standardMenu");
            dd.setSubmittedValue(null);

            // set the selected option to the default option.
            listboxSelectedOption = (String) listboxOptions[0].getValue();
            standardMenuSelectedOption
                    = (String) standardMenuOptions[0].getValue();

            disable(true);
            menusDisabled = true;

        } else if (selected.equals("menu_testCase2")) {
            // Need to enable the menus and list before disabling their options.
            if (menusDisabled) {
                disable(false);
            }
            disableOptions(true);

        } else if (selected.equals("menu_testCase3")) {
            listbox = (Listbox) context.getViewRoot().findComponent(
                    "form:scrollList");
            listbox.setSubmittedValue(null);

            dd = (DropDown) context.getViewRoot().findComponent(
                    "form:standardMenu");
            dd.setSubmittedValue(null);

            disable(false);

        } else if (selected.equals("menu_testCase4")) {
            disableOptions(false);
        }
    }

    /**
     * Action handler when navigating to the main example index.
     * @return String
     */
    public String showExampleIndex() {
        // reset.
        alertRendered = false;
        menusDisabled = false;
        jumpMenuSelectedOption = null;
        standardMenuSelectedOption = (String) standardMenuOptions[0].getValue();
        listboxSelectedOption = (String) listboxOptions[0].getValue();
        disable(false);
        disableOptions(false);

        return IndexBackingBean.INDEX_ACTION;
    }

    /**
     * Action handler when navigating to the results page.
     * @return String
     */
    public String showMenuListResults() {
        alertRendered = false;
        return SHOW_MENULIST_RESULTS;
    }

    /**
     * Helper method to set the disabled state of the menus and list.
     * @param value new value
     */
    private void disable(final boolean value) {
        setJumpMenuDisabled(value);
        setStandardMenuDisabled(value);
        setListboxDisabled(value);
    }

    /**
     * Helper method to set the disabled state of the menus and list options.
     * @param value new value
     */
    private void disableOptions(final boolean value) {
        disableOptions(jumpMenuOptions, value);
        disableOptions(standardMenuOptions, value);
        disableOptions(listboxOptions, value);
    }

    /**
     * Helper method to set the disabled state of the menus and list options.
     * @param options options to disable
     * @param value new value
     */
    private static void disableOptions(final Option[] options,
            final boolean value) {

        for (int i = 1; i < options.length; i++) {
            if (options[i] instanceof OptionGroup) {
                Option[] groupedOptions
                        = ((OptionGroup) options[i]).getOptions();
                for (Option groupedOption : groupedOptions) {
                    groupedOption.setDisabled(value);
                }
            } else {
                options[i].setDisabled(value);
            }
        }
    }
}
