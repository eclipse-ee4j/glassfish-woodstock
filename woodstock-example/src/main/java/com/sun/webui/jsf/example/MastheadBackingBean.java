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

import com.sun.webui.jsf.component.Hyperlink;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import jakarta.faces.event.ValueChangeEvent;
import com.sun.webui.jsf.example.util.MessageUtil;

/**
 * Backing bean for Image and Masthead example.
 */
public final class MastheadBackingBean implements Serializable {

    /**
     * Holds the server value.
     */
    private String server = "test_server";

    /**
     * Holds the user value.
     */
    private String user = "test_user";

    /**
     * Holds the text to be displayed in the alert boxes.
     */
    private String message = null;

    /**
     * Hold the alert detail.
     */
    private String detail = null;

    /**
     * Holds the severity of Alarms clicked.
     */
    private String severity = null;

    /**
     * Holds the severity of Alarms clicked.
     */
    private static final String MASTHEAD_INDEX = "showMasthead";

    /**
     * Holds the severity of Alarms clicked.
     */
    private static final String MASTHEAD1 = "showMasthead1";

    /**
     * Holds the severity of Alarms clicked.
     */
    private static final String MASTHEAD2 = "showMasthead2";

    /**
     * Holds the severity of Alarms clicked.
     */
    private static final String MASTHEAD3 = "showMasthead3";

    /**
     * Holds the severity of Alarms clicked.
     */
    private static final String RESULT_MASTHEAD = "showResultMasthead";

    /**
     * Holds the severity of Alarms clicked.
     */
    private static final String RESULT_MASTHEAD_FACETS =
            "showResultMastheadFacets";

    /**
     * Holds the severity of Alarms clicked.
     */

    /**
     * Initial value for rendering the alert boxes.
     */
    private boolean isRendered1 = false;

    /**
     * Initial value for rendering the alert boxes.
     */
    private boolean isRendered2 = false;

    /**
     * Initial value for rendering the alert boxes.
     */
    private boolean isRendered3 = false;

    /**
     * Initial value for rendering the alert boxes.
     */
    private boolean isRendered4 = false;

    /**
     * Value for rendering the Back button.
     */
    private boolean buttonDisplayed = false;

    /**
     * Default selection value for the "status area facet" checkbox.
     */
    private boolean cb1Selected = true;

    /**
     * Default selection value for the "status area facet" checkbox.
     */
    private boolean cb2Selected = true;

    /**
     * Default selection value for the "status area facet" checkbox.
     */
    private boolean cb3Selected = true;

    /**
     * Default selection value for the "status area facet" checkbox.
     */
    private boolean cb4Selected = true;

    /**
     * Holds the Utility Links.
     */
    private Hyperlink[] links = new Hyperlink[2];

    /**
     * Holds the Alarms.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private int[] alarms = new int[]{1, 2, 3, 4};

    /**
     * Creates a new instance of MastheadBackingBean.
     */
    public MastheadBackingBean() {
        links[0] = new Hyperlink();
        links[0].setText(MessageUtil.getMessage("masthead_utilityLink1Text"));
        links[0].setUrl("http://www.sun.com");
        links[0].setToolTip(MessageUtil
                .getMessage("masthead_utilityLink1ToolTip"));
        links[0].setTarget("_blank");

        links[1] = new Hyperlink();
        links[1].setText(MessageUtil.getMessage("masthead_utilityLink2Text"));
        links[1].setUrl("http://developers.sun.com/");
        links[1].setToolTip(MessageUtil
                .getMessage("masthead_utilityLink2ToolTip"));
    }

    /**
     * Returns the server name.
     * @return String
     */
    public String getServer() {
        return server;
    }

    /**
     * Returns the user name.
     * @return String
     */
    public String getUser() {
        return user;
    }

    /**
     * Returns message to be displayed in alert box.
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns detail to be displayed in alert box.
     * @return String
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Returns value that decides whether the alert-box should be rendered or
     * not in Masthead with Attributes Page.
     * @return {@code boolean}
     */
    public boolean getIsRendered1() {
        return isRendered1;
    }

    /**
     * Returns value that decides whether the alert-box should be rendered or
     * not in Masthead with Facets Page.
     *
     * @return {@code boolean}
     */
    public boolean getIsRendered2() {
        return isRendered2;
    }

    /**
     * Return value that decides whether the alert-box should be rendered or not
     * in Results Page of Masthead with Attributes.
     *
     * @return {@code boolean}
     */
    public boolean getisRendered3() {
        return isRendered3;
    }

    /**
     * Returns value that decides whether the alert-box should be rendered or
     * not in Results Page of Masthead with Facets.
     *
     * @return {@code boolean}
     */
    public boolean getisRendered4() {
        return isRendered4;
    }

    /**
     * Returns value that decides whether the Back button should be rendered or
     * not in Results Page of Masthead with Facets.
     *
     * @return {@code boolean}
     */
    public boolean getbuttonDisplayed() {
        return buttonDisplayed;
    }

    /**
     * Returns Utility Links.
     * @return Hyperlink[]
     */
    public Hyperlink[] getLinks() {
        return links;
    }

    /**
     * Set alarms in the masthead.
     * @param newAlarms alarms
     */
    public void setAlarms(final int[] newAlarms) {
        this.alarms = newAlarms;
    }

    /**
     * Returns the alarms.
     * @return int[]
     */
    public int[] getAlarms() {
        return alarms;
    }

    /**
     * Returns the enable/disable state of the status area facet checkbox.
     * @return {@code boolean}
     */
    public boolean getCb1Selected() {
        return cb1Selected;
    }

    /**
     * Sets the enable/disable state of the status area facet checkbox.
     * @param selected selected
     */
    public void setCb1Selected(final boolean selected) {
        cb1Selected = selected;
    }

    /**
     * Returns the enable/disable state of the status area facet checkbox.
     * @return {@code boolean}
     */
    public boolean getCb2Selected() {
        return cb2Selected;
    }

    /**
     * Sets the enable/disable state of the status area facet checkbox.
     * @param selected selected
     */
    public void setCb2Selected(final boolean selected) {
        cb2Selected = selected;
    }

    /**
     * Returns the enable/disable state of the status area facet checkbox.
     * @return {@code boolean}
     */
    public boolean getCb3Selected() {
        return cb3Selected;
    }

    /**
     * Sets the enable/disable state of the status area facet checkbox.
     * @param selected selected
     */
    public void setCb3Selected(final boolean selected) {
        cb3Selected = selected;
    }

    /**
     * Returns the enable/disable state of the status area facet checkbox.
     * @return {@code boolean}
     */
    public boolean getCb4Selected() {
        return cb4Selected;
    }

    /**
     * Sets the enable/disable state of the status area facet checkbox.
     * @param selected selected
     */
    public void setCb4Selected(final boolean selected) {
        cb4Selected = selected;
    }

    /**
     * Removes displayed alert box when Update Masthead is clicked.
     */
    public void buttonClicked() {
        isRendered2 = false;
    }

    /**
     * Message to be displayed when HelpLink is clicked.
     */
    public void helpPage1Clicked() {
        isRendered1 = true;
        message = MessageUtil.getMessage("masthead_helpLinkClicked");
    }

    /**
     * Message to be displayed when LogoutLink is clicked.
     * @return String
     */
    public String logoutPage1Clicked() {
        isRendered1 = false;
        isRendered3 = true;
        message = MessageUtil.getMessage("masthead_logoutLinkClicked");
        return RESULT_MASTHEAD;
    }

    /**
     * Message to be displayed when VersionLink is clicked.
     */
    public void versionPage1Clicked() {
        isRendered1 = true;
        message = MessageUtil.getMessage("masthead_versionLinkClicked");
    }

    /**
     * Message to be displayed when ConsoleLink is clicked.
     */
    public void consolePage1Clicked() {
        isRendered1 = true;
        message = MessageUtil.getMessage("masthead_consoleLinkClicked");
    }

    /**
     * Message to be displayed when HelpLink is clicked.
     */
    public void helpPage2Clicked() {
        isRendered2 = true;
        message = MessageUtil.getMessage("masthead_helpLinkClicked");
        detail = "";
    }

    /**
     * Message to be displayed when LogoutLink is clicked.
     * @return String
     */
    public String logoutPage2Clicked() {
        isRendered2 = false;
        isRendered4 = true;
        buttonDisplayed = false;
        message = MessageUtil.getMessage("masthead_logoutLinkClicked");
        detail = "";
        return RESULT_MASTHEAD_FACETS;
    }

    /**
     * Message to be displayed when VersionLink is clicked.
     */
    public void versionPage2Clicked() {
        isRendered2 = true;
        message = MessageUtil.getMessage("masthead_versionLinkClicked");
        detail = "";
    }

    /**
     * Message to be displayed when ConsoleLink is clicked.
     */
    public void consolePage2Clicked() {
        isRendered2 = true;
        message = MessageUtil.getMessage("masthead_consoleLinkClicked");
        detail = "";
    }

    /**
     * Message to be displayed when Notification Phrase is clicked.
     * @return String
     */
    public String notificationClicked() {
        isRendered2 = false;
        isRendered4 = true;
        buttonDisplayed = true;
        message = MessageUtil.getMessage("masthead_notificationClicked");
        detail = "";
        return RESULT_MASTHEAD_FACETS;
    }

    /**
     * Message to be displayed when Job Status is clicked.
     * @return String
     */
    public String jobstatusClicked() {
        isRendered2 = false;
        isRendered4 = true;
        buttonDisplayed = true;
        message = MessageUtil.getMessage("masthead_jobStatusClicked");
        detail = "";
        return RESULT_MASTHEAD_FACETS;
    }

    /**
     * Message to be displayed when Alarms are clicked.
     * @return String
     */
    public String alarmClicked() {
        isRendered2 = false;
        isRendered4 = true;
        buttonDisplayed = true;
        message = MessageUtil.getMessage("masthead_alarmClicked");

        severity = (String) FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().
                get("severity");

        if (severity.equals("critical")) {
            detail = MessageUtil.getMessage("masthead_criticalalarmClicked");
        } else if (severity.equals("major")) {
            detail = MessageUtil.getMessage("masthead_majoralarmClicked");
        } else if (severity.equals("minor")) {
            detail = MessageUtil.getMessage("masthead_minoralarmClicked");
        }
        return RESULT_MASTHEAD_FACETS;
    }

    /**
     * Page Navigation for breadcrumb.
     * @return String
     */
    public String goToMastheadIndex() {
        return MASTHEAD_INDEX;
    }

    /**
     * Page Navigation for Masthead with attributes Page.
     * @return String
     */
    public String goToPage1() {
        isRendered1 = false;
        isRendered2 = false;
        return MASTHEAD1;
    }

    /**
     * Page Navigation for Masthead with Facets Page.
     * @return String
     */
    public String goToPage2() {
        isRendered1 = false;
        isRendered2 = false;
        return MASTHEAD2;
    }

    /**
     * Page Navigation for Images and imageHyperlink Page.
     * @return String
     */
    public String goToPage3() {
        isRendered1 = false;
        isRendered2 = false;
        return MASTHEAD3;
    }

    /**
     * ValueChangelistener for checkbox that shows Notification Info.
     * @param event value change event
     */
    public void listener1(final ValueChangeEvent event) {
        cb1Selected = ((Boolean) event.getNewValue());
    }

    /**
     * ValueChangelistener for checkbox that shows Jobs Running Info.
     * @param event value change event
     */
    public void listener2(final ValueChangeEvent event) {
        cb2Selected = ((Boolean) event.getNewValue());
    }

    /**
     * ValueChangelistener for checkbox that shows Time Stamp Info.
     * @param event value change event
     */
    public void listener3(final ValueChangeEvent event) {
        cb3Selected = ((Boolean) event.getNewValue());
    }

    /**
     * ValueChangelistener for checkbox that shows Current Alarms Info.
     * @param event value change event
     */
    public void listener4(final ValueChangeEvent event) {
        cb4Selected = ((Boolean) event.getNewValue());
    }
}
