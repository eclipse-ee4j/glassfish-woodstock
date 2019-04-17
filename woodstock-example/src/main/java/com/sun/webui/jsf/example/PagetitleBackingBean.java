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

import java.io.Serializable;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.model.Option;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.AbortProcessingException;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import com.sun.webui.jsf.example.util.MessageUtil;

/**
 * Backing bean for Content Page Title example.
 */
public final class PagetitleBackingBean implements Serializable {

    /**
     * Holds the text to be displayed in the alert boxes.
     */
    private String message = null;

    /**
     * Holds the detail text to be displayed in the alert boxes.
     */
    private String detail = null;

    /**
     * Holds the texts to be displayed in the text fields.
     */
    private String text1 = null;

    /**
     * Holds the texts to be displayed in the text fields.
     */
    private String text2 = null;

    /**
     * Holds the PageViews.
     */
    private Option[] views = null;

    /**
     * Holds the selected item of PageView.
     */
    private String selectedItem = "View1";

    /**
     * Initial value for rendering the Alert box.
     */
    private boolean isRendered = false;

    /**
     * Creates a new instance of PagetitleBackingBean.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public PagetitleBackingBean() {
        views = new Option[3];
        views[0] = new Option("View1", MessageUtil
                .getMessage("pagetitle_view1"));
        views[1] = new Option("View2", MessageUtil
                .getMessage("pagetitle_view2"));
        views[2] = new Option("View3", MessageUtil
                .getMessage("pagetitle_view3"));
    }

    /**
     * Returns value that decides whether the alert-box should be rendered or
     * not.
     * @return {@code boolean}
     */
    public boolean getIsRendered() {
        return isRendered;
    }

    /**
     * Return message to be displayed in alert box.
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
     * Returns options to be displayed in PageView drop-down menu.
     * @return Option[]
     */
    public Option[] getViews() {
        isRendered = false;
        return views;
    }

    /**
     * Return text to be displayed in textfield.
     * @return String
     */
    public String getText1() {
        return text1;
    }

    /**
     * Sets text to be displayed in textfield.
     * @param newText1 text1
     */
    public void setText1(final String newText1) {
        this.text1 = newText1;
    }

    /**
     * Return text to be displayed in textfield.
     * @return String
     */
    public String getText2() {
        return text2;
    }

    /**
     * Sets text to be displayed in textfield.
     * @param newText2 text2
     */
    public void setText2(final String newText2) {
        this.text2 = newText2;
    }

    /**
     * Returns the selected item in the PageView.
     * @return String
     */
    public String getSelectedItem() {
        return selectedItem;
    }

    /**
     * Sets the selected item in the PageView.
     * @param newSelectedItem selectedItem
     */
    public void setSelectedItem(final String newSelectedItem) {
        selectedItem = newSelectedItem;
    }

    /**
     * Checks if there is any error on the page.
     * @return {@code boolean}
     */
    public boolean isErrorsOnPage() {
        message = MessageUtil.getMessage("pagetitle_error");
        detail = MessageUtil.getMessage("pagetitle_detail");
        FacesMessage.Severity severity
                = FacesContext.getCurrentInstance().getMaximumSeverity();
        if (severity == null) {
            return false;
        }
        return severity.compareTo(FacesMessage.SEVERITY_ERROR) >= 0;
    }

    /**
     * Message to be displayed when Save Button is clicked.
     */
    public void saveClicked() {
        isRendered = true;
        message = MessageUtil.getMessage("pagetitle_elementClicked");
        detail = MessageUtil.getMessage("pagetitle_saveClicked");
    }

    /**
     * Message to be displayed when Reset Button is clicked.
     * @param event action event
     */
    public void resetClicked(final ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        TextField tf1 = (TextField) context.getViewRoot()
                .findComponent("form1:pagetitle:text1");
        TextField tf2 = (TextField) context.getViewRoot()
                .findComponent("form1:pagetitle:text2");
        tf1.setSubmittedValue(null);
        tf2.setSubmittedValue(null);
        tf2.setValue(null);
        text1 = null;
        text2 = null;
        isRendered = true;
        message = MessageUtil.getMessage("pagetitle_elementClicked");
        detail = MessageUtil.getMessage("pagetitle_resetClicked");
    }

    /**
     * Message to be displayed when the PageViews are clicked.
     * @param event action event
     * @throws AbortProcessingException if an error occurs
     */
    public void menuChanged(final ActionEvent event)
            throws AbortProcessingException {

        UIComponent c = event.getComponent();
        DropDown menu = (DropDown) c;
        isRendered = true;
        message = MessageUtil.getMessage("pagetitle_elementClicked");
        detail = MessageUtil.getMessage("pagetitle_viewClicked") + " "
                + menu.getValue();
    }

    /**
     * Resets page defaults while navigating to the Index Page.
     * @return String
     */
    public String showIndex() {
        isRendered = false;
        text1 = null;
        text2 = null;
        selectedItem = "View1";
        return "showIndex";
    }
}
