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

package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.util.ConversionUtilities;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * The PasswordField component is used to create a password textfield.
 */
@Component(type = "com.sun.webui.jsf.PasswordField", family = "com.sun.webui.jsf.PasswordField",
displayName = "Password Field", instanceName = "passwordField", tagName = "passwordField",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_password_field",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_password_field_props")
public class PasswordField extends Field {

    /**
     * Default constructor.
     */
    public PasswordField() {
        super();
        setRendererType("com.sun.webui.jsf.PasswordField");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.PasswordField";
    }

    /**
     * <p>Return the value to be rendered as a string when the
     * component is readOnly. The value will be 
     * represented using asterisks.</p>
     * @param context FacesContext for the current request
     * @return A String value of the component
     */
    @Override
    public String getReadOnlyValueString(FacesContext context) {

        String value = ConversionUtilities.convertValueToString(this, getValue());
        if (value == null) {
            return new String();
        }
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            chars[i] = '*';
        }
        return new String(chars);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Flag indicating that an input value for this field is mandatory, and 
     * failure to provide one will trigger a validation error.
     */
    @Property(name = "required")
    @Override
    public void setRequired(boolean required) {
        super.setRequired(required);
    }

    /**
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding expression to retrieve
     */
    @Override
    public ValueExpression getValueExpression(String name) {
        if (name.equals("password")) {
            return super.getValueExpression("value");
        }
        return super.getValueExpression(name);
    }

    /**
     * <p>Set the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property
     * aliases.</p>
     *
     * @param name    Name of value binding to set
     * @param binding ValueExpression to set, or null to remove
     */
    @Override
    public void setValueExpression(String name, ValueExpression binding) {
        if (name.equals("password")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }

    // Hide text
    @Property(name = "text", isHidden = true, isAttribute = false)
    @Override
    public Object getText() {
        return super.getText();
    }

    // Hide value
    @Property(name = "value", isHidden = true, isAttribute = false)
    @Override
    public Object getValue() {
        return super.getValue();
    }

    /**
     * <p>Literal value to be rendered in this input field.
     * If this property is specified by a value binding
     * expression, the corresponding value will be updated
     * if validation succeeds.</p>
     */
    @Property(name = "password", displayName = "Password", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    public Object getPassword() {
        return getValue();
    }

    /**
     * <p>Literal value to be rendered in this input field.
     * If this property is specified by a value binding
     * expression, the corresponding value will be updated
     * if validation succeeds.</p>
     * @see #getPassword()
     */
    public void setPassword(Object password) {
        setValue(password);
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[1];
        _values[0] = super.saveState(_context);
        return _values;
    }
}
