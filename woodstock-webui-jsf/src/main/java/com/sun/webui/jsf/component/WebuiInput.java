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

import com.sun.webui.jsf.event.MethodExprValueChangeListener;
import javax.el.MethodExpression;
import javax.faces.component.UIInput;
import javax.faces.convert.Converter;
import javax.faces.event.ValueChangeListener;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.validator.MethodExprValidator;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author mbohm
 */
public class WebuiInput extends UIInput {

    /**
     * The converter attribute is used to specify a method to translate native
     * property values to String and back for this component. The converter 
     * attribute value must be one of the following:
     * <ul>
     * <li>A JavaServer Faces EL expression that resolves to a backing bean or
     * bean property that implements the 
     * <code>javax.faces.converter.Converter</code> interface; or
     * </li><li>the ID of a registered converter (a String).</li>
     * </ul>
     */
    @Property(name = "converter")
    @Override
    public void setConverter(Converter converter) {
        super.setConverter(converter);
    }

    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name = "id")
    @Override
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * Flag indicating that event handling for this component should be handled
     * immediately (in Apply Request Values phase) rather than waiting until 
     * Invoke Application phase.
     */
    @Property(name = "immediate")
    @Override
    public void setImmediate(boolean immediate) {
        super.setImmediate(immediate);
    }

    /**
     * Use the rendered attribute to indicate whether the HTML code for the
     * component should be included in the rendered HTML page. If set to false,
     * the rendered HTML page does not include the HTML for the component. If
     * the component is not rendered, it is also not processed on any subsequent
     * form submission.
     */
    @Property(name = "rendered")
    @Override
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }

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
     * {@inheritDoc}
     **/
    //Override to annotate
    @Property(isHidden = true, isAttribute = false)
    @Override
    public MethodBinding getValidator() {
        return super.getValidator();
    }

    /**
     * {@inheritDoc}
     **/
    //Override to annotate
    @Property(isHidden = true, isAttribute = false)
    @Override
    public MethodBinding getValueChangeListener() {
        return super.getValueChangeListener();
    }
    /**
     * The <code>valueChangeListener</code> attribute is used to specify a
     * method to handle an value-change event that is triggered when the
     * user enters data in the input component. The value of the 
     * attribute value must be a JavaServer Faces EL expression that
     * resolves to a backing bean method. The method must take a single
     * parameter of type <code>javax.faces.event.ValueChangeEvent</code>,
     * and its return type must be void. The backing bean where the
     * method is defined must implement <code>java.io.Serializable</code>
     * or <code>javax.faces.component.StateHolder</code>.
     */
    @Property(name = "valueChangeListenerExpression", isHidden = true, 
    displayName = "Value Change Listener Expression", category = "Advanced",
            editorClassName = "com.sun.rave.propertyeditors.MethodBindingPropertyEditor")
    @Property.Method(event = "valueChange")
    private MethodExpression valueChangeListenerExpression;

    /**
     * <p>Get the <code>valueChangeListenerExpression</code>. 
     * The corresponding listener will be called from the 
     * <code>broadcast</code> method.</p>
     */
    public MethodExpression getValueChangeListenerExpression() {
        return this.valueChangeListenerExpression;
    }

    /** 
     * <p>Set the <code>valueChangeListenerExpression</code>.
     * The corresponding listener will be called from the 
     * <code>broadcast</code> method.</p>
     */
    public void setValueChangeListenerExpression(MethodExpression me) {
        this.valueChangeListenerExpression = me;
    }

    /**
     * The validator attribute is used to specify a method in a backing bean to
     * validate input to the component. The validator attribute value must be a
     * JavaServer Faces EL expression that resolves to a public method with
     * return type void. The method must take three parameters:
     * <ul>
     * <li>a <code>javax.faces.context.FacesContext</code></li>
     * <li>a <code>javax.faces.component.UIComponent</code> (the component whose
     * data is to be validated)</li>
     * <li>a <code>java.lang.Object</code> containing the data to be validated.
     * </li>
     * </ul> 
     * <p>The backing bean where the method is defined must implement 
     * <code>java.io.Serializable</code> or
     * <code>javax.faces.component.StateHolder</code>.</p>
     * <p>The method is invoked during the Process Validations Phase.</p> 
     */
    @Property(name = "validatorExpression", displayName = "Validator Expression",
    category = "Data", editorClassName = "com.sun.rave.propertyeditors.ValidatorPropertyEditor")
    @Property.Method(event = "validate")
    private MethodExpression validatorExpression;

    /**
     * <p>Get the <code>validatorExpression</code>.
     * The corresponding validator will be called from the
     * <code>validateValue</code> method.</p>
     *
     */
    public MethodExpression getValidatorExpression() {
        return this.validatorExpression;
    }

    /**
     * <p>Set the <code>validatorExpression</code>.
     * The corresponding validator will be called from the
     * <code>validateValue</code> method.</p>
     *
     */
    public void setValidatorExpression(MethodExpression me) {
        this.validatorExpression = me;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void broadcast(FacesEvent event) throws AbortProcessingException {
        // Perform standard superclass processing
        super.broadcast(event);

        if (event instanceof ValueChangeEvent) {
            MethodExpression vclExpression = getValueChangeListenerExpression();
            if (vclExpression != null) {
                ValueChangeListener vcl = new MethodExprValueChangeListener(vclExpression);
                //just to be sure, use the semantics of the inherited broadcast method
                if (event.isAppropriateListener(vcl)) {
                    event.processListener(vcl);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    @SuppressWarnings("unchecked")
    protected void validateValue(FacesContext context, Object newValue) {
        // Perform standard superclass processing
        super.validateValue(context, newValue);

        // If our value is valid and not empty, try to call validatorExpression
        if (isValid() && !isEmpty(newValue)) {
            MethodExpression vExpression = getValidatorExpression();
            if (vExpression != null) {
                Validator validator = new MethodExprValidator(vExpression);
                try {
                    validator.validate(context, this, newValue);
                } catch (ValidatorException ve) {
                    // If the validator throws an exception, we're
                    // invalid, and we need to add a message
                    setValid(false);
                    FacesMessage message = null;
                    String validatorMessageString = getValidatorMessage();

                    if (null != validatorMessageString) {
                        message = new FacesMessage(validatorMessageString,
                                validatorMessageString);
                    } else {
                        message = ve.getFacesMessage();
                    }
                    if (message != null) {
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        context.addMessage(getClientId(context), message);
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;

        super.restoreState(context, values[0]);
        valueChangeListenerExpression = (MethodExpression) restoreAttachedState(context, values[1]);
        validatorExpression = (MethodExpression) restoreAttachedState(context, values[2]);

    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public Object saveState(FacesContext context) {
        Object values[] = new Object[3];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, valueChangeListenerExpression);
        values[2] = saveAttachedState(context, validatorExpression);
        return values;
    }
}
