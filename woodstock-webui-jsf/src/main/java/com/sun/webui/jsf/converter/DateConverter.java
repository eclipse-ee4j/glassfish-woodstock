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
 * DateConverter.java
 *
 * Created on September 30, 2005, 12:30 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package com.sun.webui.jsf.converter;

import java.io.Serializable;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import com.sun.webui.jsf.component.DateManager;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.text.DateFormat;
import java.text.MessageFormat;
import javax.faces.application.FacesMessage;

/**
 *
 * @author avk, John Yeary
 */
public class DateConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1580936705838582740L;
    private static final String INVALID_DATE_ID = "DateConverter.invalidDate"; //NOI18N

    public DateConverter() {
    }

    public String getAsString(FacesContext context, UIComponent component, Object o) throws ConverterException {
        try {
            return getDateManager(component).getDateFormat().format((Date) o);
        } catch (Exception ex) {
            throw new ConverterException(ex);
        }
    }

    public Object getAsObject(FacesContext context, UIComponent component, String s) throws ConverterException {
        if (s.length() == 0) {
            return null;
        }
        // Generate errors for dates that don't strictly follow format 6347646
        DateFormat df = getDateManager(component).getDateFormat();
        // Save old state in case there is other code that relies on it
        boolean saveLenient = df.isLenient();
        df.setLenient(false);
        try {
            Date date = df.parse(s);
            return date;
        } catch (Exception ex) {
            FacesMessage facesMessage = null;
            try {
                String message = ThemeUtilities.getTheme(context).getMessage(INVALID_DATE_ID);
                MessageFormat mf = new MessageFormat(message,
                        context.getViewRoot().getLocale());
                String example = getDateManager(component).getDateFormat().format(new Date());
                Object[] params = {s, example};
                facesMessage = new FacesMessage(mf.format(params));
            } catch (Exception e) {
                throw new ConverterException(ex);
            }
            throw new ConverterException(facesMessage);
        } finally {
            // Restore original state
            df.setLenient(saveLenient);
        }
    }

    private DateManager getDateManager(UIComponent component) {
        DateManager dateManager = null;
        if (component instanceof DateManager) {
            dateManager = (DateManager) component;
        } else if (component.getParent() instanceof DateManager) {
            dateManager = (DateManager) (component.getParent());
        }
        if (dateManager == null) {
            throw new RuntimeException("The DateConverter can only be used with components which implement DateManager"); //NOI18N
        }
        return dateManager;
    }
}

