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

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import com.sun.webui.jsf.component.Checkbox;
import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.component.TextArea;
import com.sun.webui.jsf.example.util.MessageUtil;

/**
 * Backing bean for the Label example.
 */
public final class LabelBackingBean implements Serializable {

    /**
     * Address.
     */
    private String address = "";

    /**
     * Phone number.
     */
    private String phone = "";

    /**
     * Olive selected flag.
     */
    private boolean oliveSelected = false;

    /**
     * Mushroom selected flag.
     */
    private boolean mushroomSelected = false;

    /**
     * Pepperoni selected flag.
     */
    private boolean pepperoniSelected = false;

    /**
     * Sausage selected flag.
     */
    private boolean sausageSelected = false;

    /**
     * Anchovie selected flag.
     */
    private boolean anchovieSelected = false;

    /**
     * Anchovie available.
     */
    private boolean anchoviesAvailable = true;

    /**
     * By default, errors are rendered using an inline alert as per SWAED
     * guidelines. But this limits you to a generic message when there are
     * multiple errors on the page. Setting this to false shows all the messages
     * listed in a messageGroup.
     */
    private static final boolean SHOW_ERRORS_IN_ALERT = true;

    /**
     * Creates a new instance of LabelBackingBean.
     */
    public LabelBackingBean() {
    }

    /**
     * Phone number validator. This will ONLY be called if a value was.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     * specified.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public void phoneValidator(final FacesContext context,
            final UIComponent component, final Object value) {

        // For simplicity's sake, we accept any positive integer < 1000
        try {
            int n = Integer.parseInt(value.toString());
            if ((n <= 0) || (n >= 1000)) {
                throw new Exception("");
            }
        } catch (Exception e) {
            throw new ValidatorException(
                    new FacesMessage(MessageUtil.getMessage(
                            "label_invalidPhone")));
        }
    }

    /**
     * Address validator. This will ONLY be called if a value was specified.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     */
    public void addressValidator(final FacesContext context,
            final UIComponent component, final Object value) {
        // For simplicity's sake, we accept any string but "XXX" as a valid
        // address.
        if (value.toString().equals("XXX")) {
            throw new ValidatorException(
                    new FacesMessage(MessageUtil.getMessage(
                            "label_invalidAddress", "XXX")));
        }
    }

    /**
     * Olive topping validator.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     */
    public void oliveValidator(final FacesContext context,
            final UIComponent component, final Object value) {

    }

    /**
     * Mushroom topping validator.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     */
    public void mushroomValidator(final FacesContext context,
            final UIComponent component, final Object value) {

    }

    /**
     * Pepperoni topping validator.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     */
    public void pepperoniValidator(final FacesContext context,
            final UIComponent component, final Object value) {

    }

    /**
     * Sausage topping validator.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     */
    public void sausageValidator(final FacesContext context,
            final UIComponent component, final Object value) {

    }

    /**
     * Anchovy topping validator.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     */
    public void anchovieValidator(final FacesContext context,
            final UIComponent component, final Object value) {

        if (!(value instanceof Boolean) || (value == Boolean.FALSE)) {
            return;
        }

        // OOPS!  We are out of anchovies.
        anchoviesAvailable = false;
        throw new ValidatorException(
                new FacesMessage(MessageUtil.getMessage("label_noAnchovies")));
    }

    /**
     * Get the phone number property.
     * @return String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the phone number property.
     * @param newPhone new phone
     */
    public void setPhone(final String newPhone) {
        this.phone = newPhone;
    }

    /**
     * Get the address property.
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address property.
     * @param newAddress new address
     */
    public void setAddress(final String newAddress) {
        this.address = newAddress;
    }

    /**
     * Get the olive topping selection.
     * @return {@code true} if selected, {@code false} otherwise
     */
    public boolean getOliveSelected() {
        return oliveSelected;
    }

    /**
     * Set the olive topping selection.
     * @param selected selected
     */
    public void setOliveSelected(final boolean selected) {
        oliveSelected = selected;
    }

    /**
     * Get the mushroom topping selection.
     * @return {@code true} if selected, {@code false} otherwise
     */
    public boolean getMushroomSelected() {
        return mushroomSelected;
    }

    /**
     * Set the mushroom topping selection.
     * @param selected selected
     */
    public void setMushroomSelected(final boolean selected) {
        mushroomSelected = selected;
    }

    /**
     * Get the pepperoni topping selection.
     * @return {@code true} if selected, {@code false} otherwise
     */
    public boolean getPepperoniSelected() {
        return pepperoniSelected;
    }

    /**
     * Set the pepperoni topping selection.
     * @param selected selected
     */
    public void setPepperoniSelected(final boolean selected) {
        pepperoniSelected = selected;
    }

    /**
     * Get the sausage topping selection.
     * @return {@code true} if selected, {@code false} otherwise
     */
    public boolean getSausageSelected() {
        return sausageSelected;
    }

    /**
     * Set the sausage topping selection.
     * @param selected selected
     */
    public void setSausageSelected(final boolean selected) {
        sausageSelected = selected;
    }

    /**
     * Get the anchovy topping selection.
     * @return {@code true} if selected, {@code false} otherwise
     */
    public boolean getAnchovieSelected() {
        return anchovieSelected;
    }

    /**
     * Set the anchovy topping selection.
     * @param selected selected
     */
    public void setAnchovieSelected(final boolean selected) {
        anchovieSelected = selected;
    }

    /**
     * Get the label for the anchovy selection.
     * @return String
     */
    public String getAnchovieLabel() {
        if (anchoviesAvailable) {
            return MessageUtil.getMessage("label_anchovies");
        } else {
            return MessageUtil.getMessage("label_noAnchovies");
        }
    }

    /**
     * Action handler for the Place Order button.
     * @return String
     */
    public String placeOrder() {
        return "showLabelResults";
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
        FacesContext context = FacesContext.getCurrentInstance();
        cb = (Checkbox) context.getViewRoot()
                .findComponent("form1:oliveTopping");
        cb.setSubmittedValue(null);
        cb.setValue(null);
        cb = (Checkbox) context.getViewRoot()
                .findComponent("form1:mushroomTopping");
        cb.setSubmittedValue(null);
        cb.setValue(null);
        cb = (Checkbox) context.getViewRoot()
                .findComponent("form1:pepperoniTopping");
        cb.setSubmittedValue(null);
        cb.setValue(null);
        cb = (Checkbox) context.getViewRoot()
                .findComponent("form1:sausageTopping");
        cb.setSubmittedValue(null);
        cb.setValue(null);
        cb = (Checkbox) context.getViewRoot()
                .findComponent("form1:anchovieTopping");
        cb.setSubmittedValue(null);
        cb.setValue(null);

        TextField tf = (TextField) context.getViewRoot()
                .findComponent("form1:phoneNum");
        tf.setSubmittedValue("");
        TextArea ta = (TextArea) context.getViewRoot()
                .findComponent("form1:address");
        ta.setSubmittedValue("");

        reset();
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
     * Action handler when navigating back to the Label example from results
     * page.
     * @return String
     */
    public String showLabel() {
        reset();
        return "showLabel";
    }

    /**
     * Reset model properties for next order.
     */
    private void reset() {
        setAddress("");
        setPhone("");
        setOliveSelected(false);
        setMushroomSelected(false);
        setPepperoniSelected(false);
        setSausageSelected(false);
        setAnchovieSelected(false);

        // Assume we just got a delivery!
        anchoviesAvailable = true;
    }

    /**
     * Render the message group is there is any error on the page.
     * @return {@code boolean}
     */
    public boolean getMessageGroupRendered() {
        // Never render if showing error in an inline alert.
        if (SHOW_ERRORS_IN_ALERT) {
            return false;
        }
        FacesMessage.Severity severity
                = FacesContext.getCurrentInstance().getMaximumSeverity();
        if (severity == null) {
            return false;
        }
        return severity.compareTo(FacesMessage.SEVERITY_ERROR) == 0;
    }

    /**
     * Render the alert is there is any error on the page.
     * @return {@code boolean}
     */
    public boolean getAlertRendered() {
        // Never render if showing errors in a message group.
        if (!SHOW_ERRORS_IN_ALERT) {
            return false;
        }

        FacesMessage.Severity severity
                = FacesContext.getCurrentInstance().getMaximumSeverity();
        if (severity == null) {
            return false;
        }
        return severity.compareTo(FacesMessage.SEVERITY_ERROR) == 0;
    }

    /**
     * Return summary of pizza order.
     * @return String
     */
    public String getPizza() {
        String result;
        if (!getOliveSelected()
                && !getMushroomSelected()
                && !getPepperoniSelected()
                && !getSausageSelected()) {
            result = MessageUtil.getMessage("label_plainPizza");
        } else {
            result = MessageUtil.getMessage("label_toppingsPizza");
            if (getOliveSelected()) {
                result += " " + MessageUtil.getMessage("label_oliveResult");
            }
            if (getMushroomSelected()) {
                result += " " + MessageUtil.getMessage("label_mushroomResult");
            }
            if (getPepperoniSelected()) {
                result += " " + MessageUtil.getMessage("label_pepperoniResult");
            }
            if (getSausageSelected()) {
                result += " " + MessageUtil.getMessage("label_sausageResult");
            }
        }
        return result;
    }

    /**
     * Return summary of delivery location.
     * @return String
     */
    public String getWhere() {
        String[] args = new String[2];
        args[0] = getAddress();
        args[1] = getPhone();
        return MessageUtil.getMessage("label_whereResult", args);
    }
}
