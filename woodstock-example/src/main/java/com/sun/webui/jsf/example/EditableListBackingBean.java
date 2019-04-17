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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.event.ValueChangeEvent;

import com.sun.webui.jsf.example.util.MessageUtil;
import java.io.Serializable;

/**
 * Backing Bean for editable list example.
 */
public final class EditableListBackingBean implements Serializable {

    /**
     * Holds value for property roles.
     */
    private String[] roles;

    /**
     * Holds value for listTopChkBox property.
     */
    private boolean listTopChkBox = true;

    /**
     * Holds value for sortedChkBox property.
     */
    private boolean sortedChkBox = true;

    /**
     * Holds value for listTopBottom.
     */
    private boolean listTopBottom = true;

    /**
     * Holds value for sortedList.
     */
    private boolean sortedList = true;

    /**
     * Creates a new instance of EfitableListBackingBean.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public EditableListBackingBean() {
        roles = new String[5];
        roles[0] = "Cron Management";
        roles[1] = "DHCP Management";
        roles[2] = "Log Management";
        roles[3] = "Mail Management";
        roles[4] = "Network Management";

    }

    /**
     * this method assigns value to listonTop property of editable list tag.
     * @return {@code boolean}
     */
    public boolean getListTopBottom() {
        return listTopBottom;

    }

    /**
     * this method assigns value to sorted property of editablelist tag.
     * @return {@code boolean}
     */
    public boolean getSortedList() {
        return sortedList;
    }

    /**
     * valueChangelistener for checkbox that sets list on top property.
     * @param event value change event
     */
    public void listOnToplistener(final ValueChangeEvent event) {
        Boolean newValue = (Boolean) event.getNewValue();
        listTopBottom = newValue != null && newValue;
    }

    /**
     * valueChangelistener for checkbox that sets sorted property.
     * @param event value change event
     */
    public void sortedlistener(final ValueChangeEvent event) {
        Boolean newValue = (Boolean) event.getNewValue();
        sortedList = newValue != null && newValue;

    }

    /**
     * getter method for property roles.
     * @return String[]
     */
    public String[] getRoles() {
        return this.roles;
    }

    /**
     * Setter method for property roles.
     * @param newRoles roles
     */
    public void setRoles(final String[] newRoles) {
        this.roles = newRoles;
    }

    /**
     * Getter method for listTopChkBox property.
     * @return {@code boolean}
     */
    public boolean getListTopChkBox() {
        return listTopChkBox;
    }

    /**
     * Getter method for sortedChkBox property.
     * @return {@code boolean}
     */
    public boolean getSortedChkBox() {
        return sortedChkBox;
    }

    /**
     * Setter method for listTopChkBox property.
     * @param newListTopChkBox listTopChkBox
     */
    public void setListTopChkBox(final boolean newListTopChkBox) {
        this.listTopChkBox = newListTopChkBox;
    }

    /**
     * Setter method for sortedChkBox property.
     * @param newSortedChkBox sortedChkBox
     */
    public void setSortedChkBox(final boolean newSortedChkBox) {
        this.sortedChkBox = newSortedChkBox;
    }

    /**
     * validate method for role validation.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     * @throws ValidatorException if a validation error occurs
     */
    public void validate(final FacesContext context,
            final UIComponent component, final Object value)
            throws ValidatorException {

        String msgString;
        FacesMessage msg;
        String string = value.toString();

        char[] characters = string.toCharArray();
        for (int counter = 0; counter < characters.length; ++counter) {
            if (!Character.isLetter(characters[counter])
                    && characters[counter] != ' ') {
                msgString = MessageUtil.
                        getMessage("editablelist_string");
                msg = new FacesMessage(msgString);
                throw new ValidatorException(msg);
            }
        }

    }

    /**
     * list validator that checks for number of list items.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     * @throws ValidatorException if a validation error occurs
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public void validateList(final FacesContext context,
            final UIComponent component, final Object value)
            throws ValidatorException {

        if (value instanceof String[]) {
            String[] list = (String[]) value;
            if (list.length < 5) {
                String msgString = MessageUtil.
                        getMessage("editablelist_listvalidate");
                FacesMessage msg = new FacesMessage(msgString);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     * Summary message for Validator exception.
     * @return String
     */
    public String getSummaryMsg() {
        return MessageUtil.getMessage("editablelist_summary");
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
     * method to get edited roles for resultpage.
     * @return String
     */
    public String getEditedRoles() {
        if (roles == null) {
            return "";
        }
        int i;
        StringBuilder editedRole = new StringBuilder();
        for (i = 0; i < roles.length - 1; ++i) {
            editedRole.append(roles[i]);
            editedRole.append(", ");
        }
        editedRole.append(roles[i]);
        return editedRole.toString();
    }

    /**
     * Action handler when navigating to the main example index.
     * @return String
     */
    public String showExampleIndex() {
        listTopChkBox = true;
        sortedChkBox = true;
        listTopBottom = true;
        sortedList = true;
        return IndexBackingBean.INDEX_ACTION;
    }
}
