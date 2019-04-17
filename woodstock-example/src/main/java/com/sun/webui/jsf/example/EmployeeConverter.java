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

import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.application.FacesMessage;

import com.sun.webui.jsf.example.util.MessageUtil;

/**
 * Employee Converter Class.
 */
public final class EmployeeConverter implements Converter {

    /**
     * Creates an instance of EmployeeConverter.
     */
    public EmployeeConverter() {
    }

    @Override
    public String getAsString(final FacesContext context,
            final UIComponent component, final Object value)
            throws ConverterException {

        if (value instanceof Employee) {
            StringBuilder strbuf = new StringBuilder();
            strbuf.append(((Employee) value).getFirstName());
            strbuf.append(" ");
            strbuf.append(((Employee) value).getLastName());
            strbuf.append("-");
            strbuf.append(((Employee) value).getDesignation());
            return strbuf.toString();
        }
        throw new ConverterException(MessageUtil
                .getMessage("statictext_errorMessage1") + value.toString());
    }

    @Override
    public Object getAsObject(final FacesContext context,
            final UIComponent component, final String value)
            throws ConverterException {

        try {
            String[] names = value.split(" ");
            Employee emp = new Employee(names[0], names[1], names[2]);
            return emp;
        } catch (Exception ex) {
            String message = MessageUtil
                    .getMessage("statictext_errorMessage2");
            throw new ConverterException(new FacesMessage(message));
        }
    }
}
