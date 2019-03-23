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
import com.sun.webui.jsf.converter.DateConverter;
import com.sun.webui.jsf.validator.DateInRangeValidator;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.Validator;

/**
 * The Calendar component is used to allow a user to select a date.
 */
@Component(type = "com.sun.webui.jsf.Calendar", family = "com.sun.webui.jsf.Calendar", displayName = "Calendar", tagName = "calendar",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_calendar",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_calendar_props")
public class Calendar extends Field implements DateManager, NamingContainer {

    private static final String DATE_PICKER_LINK_FACET = "datePickerLink";//NOI18N
    private static final String DATE_PICKER_LINK_ID = "_datePickerLink";//NOI18N
    private static final String DATE_PICKER_FACET = "datePicker";//NOI18N
    private static final String DATE_PICKER_ID = "_datePicker";//NOI18N
    public static final String PATTERN_ID = "_pattern"; //NOI18N
    private DateConverter dateConverter = null;

    /** Creates a new instance of Calendar */
    public Calendar() {
        super();
        setRendererType("com.sun.webui.jsf.Calendar");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.Calendar";
    }

    /**
     * This method returns the ImageHyperlink that serves as the "button" to
     * show or hide the calendar date picker display.
     *
     * @param context The current FacesContext.
     * @return The ImageHyperlink to show or hide the calendar date picker.
     */
    public ImageHyperlink getDatePickerLink(FacesContext context) {

        UIComponent component = getFacet(DATE_PICKER_LINK_FACET);

        ImageHyperlink datePickerLink;
        if (component instanceof ImageHyperlink) {
            datePickerLink = (ImageHyperlink) component;
        } else {
            datePickerLink = new ImageHyperlink();
            getFacets().put(DATE_PICKER_LINK_FACET, datePickerLink);
        }

        datePickerLink.setId(DATE_PICKER_LINK_ID);
        datePickerLink.setAlign("middle");  //NOI18N

        // render the image hyperlink to show/hide the calendar
        StringBuffer js = new StringBuffer(200);
        js.append("javascript: ").append(getJavaScriptObjectName(context)).append(".toggle(); return false;");  //NOI18N

        // Don't set Javascript as the URL -- bugtraq #6306848.
        datePickerLink.setOnClick(js.toString());


        // We should do this, but unfortunately the component can't be enabled
        // from the client-side yet. 
        //component.getAttributes().put("disabled", new Boolean(isDisabled()));  //NOI18N

        return datePickerLink;
    }

    public CalendarMonth getDatePicker() {

        UIComponent comp = getFacet(DATE_PICKER_FACET);
        if (comp == null || !(comp instanceof CalendarMonth)) {
            CalendarMonth datePicker = new CalendarMonth();
            datePicker.setPopup(true);
            datePicker.setId(DATE_PICKER_ID);
            getFacets().put(DATE_PICKER_FACET, datePicker);
            comp = datePicker;
        }
        ((CalendarMonth) comp).setJavaScriptObjectName(getJavaScriptObjectName(FacesContext.getCurrentInstance()));
        return (CalendarMonth) comp;
    }

    public String getJavaScriptObjectName(FacesContext context) {
        return JavaScriptUtilities.getDomNode(getFacesContext(), this);
    }

    @Override
    public Converter getConverter() {

        // We add the validator at this point, if needed...
        Validator[] validators = getValidators();
        int len = validators.length;
        boolean found = false;
        for (int i = 0; i < len; ++i) {
            if (validators[i] instanceof DateInRangeValidator) {
                found = true;
                break;
            }
        }
        if (!found) {
            addValidator(new DateInRangeValidator());
        }
        Converter converter = super.getConverter();

        if (converter == null) {
            if (dateConverter == null) {
                dateConverter = new DateConverter();
            }
            converter = dateConverter;
        }
        return converter;
    }

    @Override
    public String getReadOnlyValueString(FacesContext context) {
        if (getValue() == null) {
            return "-"; //NOI18N
        } else {
            return super.getReadOnlyValueString(context);
        }
    }

    public DateFormat getDateFormat() {
        return getDatePicker().getDateFormat();
    }

    // Since the value of the minDate attribute could change, we can't
    // cache this in an attribute.
    public Date getFirstAvailableDate() {
        Date minDate = getMinDate();
        if (minDate == null) {
            java.util.Calendar calendar = getDatePicker().getCalendar();
            calendar.add(java.util.Calendar.YEAR, -100);
            calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
            calendar.set(java.util.Calendar.MINUTE, 0);
            calendar.set(java.util.Calendar.SECOND, 0);
            calendar.set(java.util.Calendar.MILLISECOND, 0);
            minDate = calendar.getTime();
        }
        return minDate;
    }

    public Date getLastAvailableDate() {
        Date maxDate = getMaxDate();
        if (maxDate == null) {
            Date minDate = getFirstAvailableDate();
            java.util.Calendar calendar = getDatePicker().getCalendar();
            calendar.setTime(minDate);
            calendar.add(java.util.Calendar.YEAR, 200);
            calendar.set(java.util.Calendar.HOUR_OF_DAY, 23);
            calendar.set(java.util.Calendar.MINUTE, 59);
            calendar.set(java.util.Calendar.SECOND, 59);
            calendar.set(java.util.Calendar.MILLISECOND, 999);
            maxDate = calendar.getTime();
        }
        return maxDate;
    }

    /**
     * Implement this method so that it returns the DOM ID of the 
     * HTML element which should receive focus when the component 
     * receives focus, and to which a component label should apply. 
     * Usually, this is the first element that accepts input. 
     * 
     * @param context The FacesContext for the request
     * @return The client id, also the JavaScript element id
     *
     * @deprecated
     * @see #getLabeledElementId
     * @see #getFocusElementId
     */
    @Override
    public String getPrimaryElementID(FacesContext context) {
        return getLabeledElementId(context);
    }

    /**
     * Returns the absolute ID of an HTML element suitable for use as
     * the value of an HTML LABEL element's <code>for</code> attribute.
     * If the <code>ComplexComponent</code> has sub-compoents, and one of 
     * the sub-components is the target of a label, if that sub-component
     * is a <code>ComplexComponent</code>, then
     * <code>getLabeledElementId</code> must called on the sub-component and
     * the value returned. The value returned by this 
     * method call may or may not resolve to a component instance.
     *
     * @param context The FacesContext used for the request
     * @return An abolute id suitable for the value of an HTML LABEL element's
     * <code>for</code> attribute.
     */
    @Override
    public String getLabeledElementId(FacesContext context) {
        // The id of the calendar component is assigned to the
        // the HTML table element that lays out an optional
        // specified label, an input text field and an icon button
        // to launch the calendar.
        //
        // The element id returned is the text field since this is the
        // element that the label, that typically calls this method,
        // is interested in so that it can render the required and
        // invalid icons appropriately.
        // 
        // Input id is defined in the Field super class of CalendarBase.
        // The field component is responsible for the input text field
        // as well.
        //
        return this.getClientId(context).concat(INPUT_ID);
    }

    /**
     * Returns the id of an HTML element suitable to
     * receive the focus.
     * If the <code>ComplexComponent</code> has sub-compoents, and one of 
     * the sub-components is to reveive the focus, if that sub-component
     * is a <code>ComplexComponent</code>, then
     * <code>getFocusElementId</code> must called on the sub-component and
     * the value returned. The value returned by this 
     * method call may or may not resolve to a component instance.
     * <p>
     * This implementation returns the value of
     * <code>getLabeledElementId</code>.
     * </p>
     *
     * @param context The FacesContext used for the request
     */
    @Override
    public String getFocusElementId(FacesContext context) {
        return getLabeledElementId(context);
    }

    /**
     * Update the datePicker with an explicitly set date format pattern.
     */
    public void setDateFormatPattern(String dateFormatPattern) {
        _setDateFormatPattern(dateFormatPattern);
        CalendarMonth dp = getDatePicker();
        dp.setDateFormatPattern(dateFormatPattern);
    }

    /**
     * Update the datePicker with an explicitly set time zone.
     */
    public void setTimeZone(TimeZone timeZone) {
        _setTimeZone(timeZone);
        CalendarMonth dp = getDatePicker();
        dp.setTimeZone(timeZone);
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

    // Hide maxLength
    @Property(name = "maxLength", isHidden = true, isAttribute = false)
    @Override
    public int getMaxLength() {
        return super.getMaxLength();
    }

    // Hide text
    @Property(name = "text", isHidden = true, isAttribute = false)
    @Override
    public Object getText() {
        return super.getText();
    }

    // Hide trim
    @Property(name = "trim", isHidden = true, isAttribute = false)
    @Override
    public boolean isTrim() {
        return super.isTrim();
    }

    /**
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding expression to retrieve
     */
    @Override
    public ValueExpression getValueExpression(String name) {
        if (name.equals("selectedDate")) {
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
        if (name.equals("selectedDate")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }
    /**
     * <p>The date format pattern to use (i.e. yyyy-MM-dd). The
     * component uses an instance of
     * <code>java.text.SimpleDateFormat</code> and you may specify 
     * a pattern to be used by this component, with the following
     * restriction: the format pattern must include <code>yyyy</code> (not
     * <code>yy</code>), <code>MM</code>, and <code>dd</code>; and no
     * other parts of time may be displayed. If a pattern is not
     * specified, a locale-specific default is used.</p> 
     * <p> 
     * If you change the date format pattern, you may also need to
     * change the <code>dateFormatPatternHelp</code> attribute. See the
     * documentation for that attribute. 
     * </p>
     */
    @Property(name = "dateFormatPattern", displayName = "Date Format Pattern", category = "Appearance", editorClassName = "com.sun.webui.jsf.component.propertyeditors.DateFormatPatternsEditor", shortDescription = "The date format pattern to use (e.g., yyyy-MM-dd).")
    private String dateFormatPattern = null;

    /**
     * <p>The date format pattern to use (i.e. yyyy-MM-dd). The
     * component uses an instance of
     * <code>java.text.SimpleDateFormat</code> and you may specify 
     * a pattern to be used by this component, with the following
     * restriction: the format pattern must include <code>yyyy</code> (not
     * <code>yy</code>), <code>MM</code>, and <code>dd</code>; and no
     * other parts of time may be displayed. If a pattern is not
     * specified, a locale-specific default is used.</p> 
     * <p> 
     * If you change the date format pattern, you may also need to
     * change the <code>dateFormatPatternHelp</code> attribute. See the
     * documentation for that attribute. 
     * </p>
     */
    public String getDateFormatPattern() {
        if (this.dateFormatPattern != null) {
            return this.dateFormatPattern;
        }
        ValueExpression _vb = getValueExpression("dateFormatPattern");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The date format pattern to use (i.e. yyyy-MM-dd). The
     * component uses an instance of
     * <code>java.text.SimpleDateFormat</code> and you may specify 
     * a pattern to be used by this component, with the following
     * restriction: the format pattern must include <code>yyyy</code> (not
     * <code>yy</code>), <code>MM</code>, and <code>dd</code>; and no
     * other parts of time may be displayed. If a pattern is not
     * specified, a locale-specific default is used.</p> 
     * <p> 
     * If you change the date format pattern, you may also need to
     * change the <code>dateFormatPatternHelp</code> attribute. See the
     * documentation for that attribute. 
     * </p>
     * @see #getDateFormatPattern()
     */
    private void _setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
    }
    /**
     * <p>A message below the textfield for the date, indicating the
     * string format to use when entering a date as text into the
     * textfield.</p>  
     * 
     * <p>The component internally relies on an instance of
     * <code>java.text.SimpleDateFormat</code> to produce the hint. 
     * The default hint is constructed by invoking the
     * <code>toLocalizedPattern()</code> method on the
     * <code>SimpleDateFormat</code> instance and converting this 
     * String to lower case.</p> 
     * 
     * <p>Due to a bug in
     * <code>SimpleDateFormat</code>,
     * <code>toLocalizedPattern()</code> does not actually produce
     * locale-appropriate strings for most locales (it works for
     * German, but not for other locales). If the default value for
     * the <code>dateFormtPattern</code> is used, the
     * component takes care of the localization itself, but if the default 
     * is overridden, you may need to override the hint on a
     * per-locale basis too. </p>
     */
    @Property(name = "dateFormatPatternHelp", displayName = "Date Format Pattern Help", category = "Appearance", editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String dateFormatPatternHelp = null;

    /**
     * <p>A message below the textfield for the date, indicating the
     * string format to use when entering a date as text into the
     * textfield.</p>  
     * 
     * <p>The component internally relies on an instance of
     * <code>java.text.SimpleDateFormat</code> to produce the hint. 
     * The default hint is constructed by invoking the
     * <code>toLocalizedPattern()</code> method on the
     * <code>SimpleDateFormat</code> instance and converting this 
     * String to lower case.</p> 
     * 
     * <p>Due to a bug in
     * <code>SimpleDateFormat</code>,
     * <code>toLocalizedPattern()</code> does not actually produce
     * locale-appropriate strings for most locales (it works for
     * German, but not for other locales). If the default value for
     * the <code>dateFormtPattern</code> is used, the
     * component takes care of the localization itself, but if the default 
     * is overridden, you may need to override the hint on a
     * per-locale basis too. </p>
     */
    public String getDateFormatPatternHelp() {
        if (this.dateFormatPatternHelp != null) {
            return this.dateFormatPatternHelp;
        }
        ValueExpression _vb = getValueExpression("dateFormatPatternHelp");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>A message below the textfield for the date, indicating the
     * string format to use when entering a date as text into the
     * textfield.</p>  
     * 
     * <p>The component internally relies on an instance of
     * <code>java.text.SimpleDateFormat</code> to produce the hint. 
     * The default hint is constructed by invoking the
     * <code>toLocalizedPattern()</code> method on the
     * <code>SimpleDateFormat</code> instance and converting this 
     * String to lower case.</p> 
     * 
     * <p>Due to a bug in
     * <code>SimpleDateFormat</code>,
     * <code>toLocalizedPattern()</code> does not actually produce
     * locale-appropriate strings for most locales (it works for
     * German, but not for other locales). If the default value for
     * the <code>dateFormtPattern</code> is used, the
     * component takes care of the localization itself, but if the default 
     * is overridden, you may need to override the hint on a
     * per-locale basis too. </p>
     * @see #getDateFormatPatternHelp()
     */
    public void setDateFormatPatternHelp(String dateFormatPatternHelp) {
        this.dateFormatPatternHelp = dateFormatPatternHelp;
    }
    /**
     * <p>A <code>java.util.Date</code> object representing the last
     * selectable day. The default value is 200 years after the
     * <code>minDate</code> (which is evaluated first).</p> 
     * <p>The value of this attribute is reflected in the years that
     * are available for selection in the month display. In future
     * releases of this component, web application users will also not
     * be able to view months after this date, or select days that
     * follow this date. At present such dates can be selected, but
     * will not be validated when the form is submitted.</p>
     */
    @Property(name = "maxDate", displayName = "Last selectable date", category = "Data", editorClassName = "com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor", shortDescription = "The last selectable date.")
    private java.util.Date maxDate = null;

    /**
     * <p>A <code>java.util.Date</code> object representing the last
     * selectable day. The default value is 200 years after the
     * <code>minDate</code> (which is evaluated first).</p> 
     * <p>The value of this attribute is reflected in the years that
     * are available for selection in the month display. In future
     * releases of this component, web application users will also not
     * be able to view months after this date, or select days that
     * follow this date. At present such dates can be selected, but
     * will not be validated when the form is submitted.</p>
     */
    public java.util.Date getMaxDate() {
        if (this.maxDate != null) {
            return this.maxDate;
        }
        ValueExpression _vb = getValueExpression("maxDate");
        if (_vb != null) {
            return (java.util.Date) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>A <code>java.util.Date</code> object representing the last
     * selectable day. The default value is 200 years after the
     * <code>minDate</code> (which is evaluated first).</p> 
     * <p>The value of this attribute is reflected in the years that
     * are available for selection in the month display. In future
     * releases of this component, web application users will also not
     * be able to view months after this date, or select days that
     * follow this date. At present such dates can be selected, but
     * will not be validated when the form is submitted.</p>
     * @see #getMaxDate()
     */
    public void setMaxDate(java.util.Date maxDate) {
        this.maxDate = maxDate;
    }
    /**
     * <p>A <code>java.util.Date</code> object representing the first
     * selectable day. The default value is 100 years prior to today's date.</p> 
     * <p>The value of this attribute is reflected in the years that
     * are available for selection in the month display. In future
     * releases of this component, web application users will also not
     * be able to view months before this date, or select days that
     * precede this date. At present such dates can be selected, but
     * will not be validated when the form is submitted.</p>
     */
    @Property(name = "minDate", displayName = "First selectable date", category = "Data", editorClassName = "com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor", shortDescription = "The first selectable date.")
    private java.util.Date minDate = null;

    /**
     * <p>A <code>java.util.Date</code> object representing the first
     * selectable day. The default value is 100 years prior to today's date.</p> 
     * <p>The value of this attribute is reflected in the years that
     * are available for selection in the month display. In future
     * releases of this component, web application users will also not
     * be able to view months before this date, or select days that
     * precede this date. At present such dates can be selected, but
     * will not be validated when the form is submitted.</p>
     */
    public java.util.Date getMinDate() {
        if (this.minDate != null) {
            return this.minDate;
        }
        ValueExpression _vb = getValueExpression("minDate");
        if (_vb != null) {
            return (java.util.Date) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>A <code>java.util.Date</code> object representing the first
     * selectable day. The default value is 100 years prior to today's date.</p> 
     * <p>The value of this attribute is reflected in the years that
     * are available for selection in the month display. In future
     * releases of this component, web application users will also not
     * be able to view months before this date, or select days that
     * precede this date. At present such dates can be selected, but
     * will not be validated when the form is submitted.</p>
     * @see #getMinDate()
     */
    public void setMinDate(java.util.Date minDate) {
        this.minDate = minDate;
    }

    /**
     * <p>A <code>java.util.Date</code> object representing the currently
     * selected calendar date.</p>
     */
    @Property(name = "selectedDate", displayName = "Selected Date", category = "Data", editorClassName = "com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor", shortDescription = "The date currently selected.")
    public java.util.Date getSelectedDate() {
        return (java.util.Date) getValue();
    }

    /**
     * <p>A <code>java.util.Date</code> object representing the currently
     * selected calendar date.</p>
     * @see #getSelectedDate()
     */
    public void setSelectedDate(java.util.Date selectedDate) {
        setValue((Object) selectedDate);
    }
    /**
     * <p>The <code>java.util.TimeZone</code> used with this
     * component. Unless set, the default TimeZone for the locale in  
     * <code>javax.faces.component.UIViewRoot</code> is used.</p>
     */
    @Property(name = "timeZone", displayName = "Time Zone", category = "Appearance", isHidden = true)
    private java.util.TimeZone timeZone = null;

    /**
     * <p>The <code>java.util.TimeZone</code> used with this
     * component. Unless set, the default TimeZone for the locale in  
     * <code>javax.faces.component.UIViewRoot</code> is used.</p>
     */
    public java.util.TimeZone getTimeZone() {
        if (this.timeZone != null) {
            return this.timeZone;
        }
        ValueExpression _vb = getValueExpression("timeZone");
        if (_vb != null) {
            return (java.util.TimeZone) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The <code>java.util.TimeZone</code> used with this
     * component. Unless set, the default TimeZone for the locale in  
     * <code>javax.faces.component.UIViewRoot</code> is used.</p>
     * @see #getTimeZone()
     */
    private void _setTimeZone(java.util.TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.dateFormatPattern = (String) _values[1];
        this.dateFormatPatternHelp = (String) _values[2];
        this.maxDate = (java.util.Date) _values[3];
        this.minDate = (java.util.Date) _values[4];
        this.timeZone = (java.util.TimeZone) _values[5];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[6];
        _values[0] = super.saveState(_context);
        _values[1] = this.dateFormatPattern;
        _values[2] = this.dateFormatPatternHelp;
        _values[3] = this.maxDate;
        _values[4] = this.minDate;
        _values[5] = this.timeZone;
        return _values;
    }
}

