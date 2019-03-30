/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
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

/*
 * StringLengthValidator.java
 *
 * Created on February 11, 2005, 9:58 AM
 */
package com.sun.webui.jsf.validator;

import java.text.MessageFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.component.DateManager;

/**
 * Use this validator to check the number of characters in a string when
 * you need to set the validation messages.
 */
public class DateInRangeValidator implements Validator {

    /**
     * The converter id for this converter.
     */
    public static final String VALIDATOR_ID = "com.sun.webui.jsf.DateInRange";

    /**
     * Debug flag.
     */
    private static final boolean DEBUG = false;

    /**
     * Creates a new instance of StringLengthValidator.
     */
    public DateInRangeValidator() {
    }

    @Override
    public void validate(FacesContext context,
            UIComponent component,
            Object value) throws ValidatorException {

        //log("validate()" + String.valueOf(value)); 
        if ((context == null) || (component == null)) {
            //if(DEBUG) log("\tContext or component is null");
            throw new NullPointerException();
        }

        if (!(value instanceof Date)) {
            return;
        }

        DateManager dateManager = null;
        if (component instanceof DateManager) {
            dateManager = (DateManager) component;
        } else if (component.getParent() instanceof DateManager) {
            dateManager = (DateManager) (component.getParent());
        }
        if (dateManager == null) {
            //log("Didn't find a DateManager "
            // + component.getClass().toString()); 
            return;
        }

        Date date = (Date) value;
        Date minDate = dateManager.getFirstAvailableDate();
        if (minDate != null && date.before(minDate)) {
            //log("Date is before minDAte!"); 
            FacesMessage msg = getFacesMessage(component, context, minDate,
                    "DateInRangeValidator.after");
            throw new ValidatorException(msg);
        }
        Date maxDate = dateManager.getLastAvailableDate();
        if (maxDate != null && maxDate.before(date)) {
            //log("Date is after maxDAte!"); 
            FacesMessage msg = getFacesMessage(component, context, maxDate,
                    "DateInRangeValidator.before");
            throw new ValidatorException(msg);

        }
    }

    /**
     * Get the faces message
     * @param component UI componnet
     * @param context faces context
     * @param date date
     * @param key message key
     * @return FacesMessage
     */
    private FacesMessage getFacesMessage(UIComponent component,
            FacesContext context, Date date, String key) {

        String message = ThemeUtilities.getTheme(context).getMessage(key);
        String arg = ConversionUtilities.convertValueToString(component, date);
        MessageFormat mf = new MessageFormat(message,
                context.getViewRoot().getLocale());
        Object[] params = {arg};
        return new FacesMessage(mf.format(params));
    }

    /**
     * Log a message to the standard output.
     * @param msg message to log
     */
    private void log(String msg) {
        System.out.println(this.getClass().getName() + "::" + msg);
    }
}
