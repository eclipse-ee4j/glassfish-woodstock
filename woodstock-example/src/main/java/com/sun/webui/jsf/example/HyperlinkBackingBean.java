/*
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
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

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.example.util.MessageUtil;
import java.io.Serializable;
import java.util.Date;

/**
 * Backing Bean for Hyperlink example.
 */
public final class HyperlinkBackingBean implements Serializable {

    /**
     * Holds value for property userName.
     */
    private String userName = null;

    /**
     * Holds value for property linkDisable.
     */
    private boolean linkDisable = false;

    /**
     * Holds value for displayParam property.
     */
    private boolean displayParam = false;

    /**
     * Holds value for property date.
     */
    private String date = null;

    /**
     * Holds value for property linkOnoff.
     */
    private boolean linkOnoff = false;

    /**
     * Holds value for property enable hyperlink.
     */
    private boolean enableHyperlink = true;

    /**
     * Holds value for property disable hyperlink.
     */
    private boolean disableHyperlink = false;

    /**
     * String constant for disable button id.
     */
    private static final String DISABLE_BUTTON_ID
            = "hyperlinkForm:pageButtonsGroupBottom:disable";

    /**
     * Creates a new instance of HyperlinkBackingBean.
     */
    public HyperlinkBackingBean() {
    }

    /**
     * Getter method for username property.
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for property userName.
     * @param name user name
     */
    public void setUserName(final String name) {
        this.userName = name;
    }

    /**
     * This method assigns value to disabled property of hyperlink tag.
     * @return {@code boolean}
     */
    public boolean getLinkOnoff() {
        return linkOnoff;
    }

    /**
     * This method returns current date.
     * @return String
     */
    public String getStartDate() {
        return (new Date()).toString();
    }

    /**
     * This method returns param value.
     * @return String
     */
    public String getParamValue() {
        date = (String) FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().
                get("dateParam");
        return date;
    }

    /**
     * this method returns {@code boolean} for visible attribute of staticText
     * and label tag.
     *
     * @return {@code boolean}
     */
    public boolean getRenderParam() {
        date = (String) FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().
                get("dateParam");
        return date != null;
    }

    /**
     * Action listener for the immediate hyperlink.
     * @param event action event
     */
    public void immediateAction(final ActionEvent event) {
        // Since the action is immediate, the textfield component won't
        // go through the Update Model phase and the model object won't
        // get updated with the submitted value. So, we need to update the
        // model here so that it reflects the new value.
        FacesContext context = FacesContext.getCurrentInstance();
        TextField textField = (TextField) context.getViewRoot().findComponent(
                "hyperlinkForm:nameField");
        userName = (String) textField.getSubmittedValue();
    }

    /**
     * Action handler.
     * @return String
     */
    public String nextPage() {
        return "resultHyperlink";
    }

    /**
     * Summary message for Validator exception.
     * @return String
     */
    public String getSummaryMsg() {
        return MessageUtil.getMessage("hyperlink_summary");
    }

    /**
     * getter method for enabling hyperlink.
     * @return {@code boolean}
     */
    public boolean getEnableHyperlink() {
        return enableHyperlink;
    }

    /**
     * getter method for disabling hyperlink.
     * @return {@code boolean}
     */
    public boolean getDisableHyperlink() {
        return disableHyperlink;
    }

    /**
     * Detail message for Validator exception.
     * @return String
     */
    public String getDetailMsg() {
        return MessageUtil.getMessage("hyperlink_detail");
    }

    /**
     * Checks for errors on page.
     * @return {@code boolean}
     */
    public boolean isErrorsOnPage() {
        FacesMessage.Severity severity
                = FacesContext.getCurrentInstance().getMaximumSeverity();
        if (severity == null) {
            return false;
        }
        return severity.compareTo(FacesMessage.SEVERITY_ERROR) >= 0;
    }

    /**
     * Action listener for the disable/enable hyperlink button.
     * @param event action event
     */
    public void onOffbuttonAction(final ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        String clientId = event.getComponent().getClientId(context);
        if (clientId != null && clientId.equals(DISABLE_BUTTON_ID)) {
            linkOnoff = true;
            enableHyperlink = false;
            disableHyperlink = true;
        } else {
            linkOnoff = false;
            enableHyperlink = true;
            disableHyperlink = false;
        }

    }

    /**
     * Action handler when navigating to the main example index.
     * @return String
     */
    public String showExampleIndex() {
        userName = null;
        linkOnoff = false;
        enableHyperlink = true;
        disableHyperlink = false;
        return IndexBackingBean.INDEX_ACTION;
    }
}
