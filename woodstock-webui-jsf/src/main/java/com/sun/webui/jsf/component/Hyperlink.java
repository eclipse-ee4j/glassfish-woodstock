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
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * The Hyperlink component is used to create a link. 
 */
@Component(type = "com.sun.webui.jsf.Hyperlink", family = "com.sun.webui.jsf.Hyperlink",
displayName = "Hyperlink", tagName = "hyperlink",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_hyperlink",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_hyperlink_props")
public class Hyperlink extends WebuiCommand implements ComplexComponent {

    /**
     * Default constructor.
     */
    public Hyperlink() {
        super();
        setRendererType("com.sun.webui.jsf.Hyperlink");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.Hyperlink";
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
    public String getLabeledElementId(FacesContext context) {
        return getClientId(context);
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
     *<p>
     * This implementations returns the value of
     * <code>getLabeledElementId</code>.
     *
     * @param context The FacesContext used for the request
     */
    public String getFocusElementId(FacesContext context) {
        // For now return the labeled component.
        //
        return getLabeledElementId(context);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer(100);
        buffer.append(this.getClass().getName());
        buffer.append(" id: ");
        buffer.append(getId());
        return buffer.toString();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding expression to retrieve
     */
    @Override
    public ValueExpression getValueExpression(String name) {
        if (name.equals("text")) {
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
        if (name.equals("text")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }

    /**
     * <p>Flag indicating that activation of this component by the user is not
     * currently permitted. In this component library, the disabled attribute
     * also causes the hyperlink to be renderered as formatted text in an 
     * HTML <span> tag. The hyperlink cannot be enabled from the client 
     * because this is a server side only feature. You cannot 
     * disable an anchor.</p>
     */
    @Property(isHidden = true, isAttribute = false)
    @Override
    public Object getValue() {
        return super.getValue();
    }
    /**
     * <p>Flag indicating that activation of this component by the user is not
     * currently permitted. In this component library, the disabled attribute
     * also causes the hyperlink to be renderered as formatted text in an 
     * HTML <span> tag. The hyperlink cannot be enabled from the client 
     * because this is a server side only feature. You cannot 
     * disable an anchor.</p>
     */
    @Property(name = "disabled", displayName = "Disabled", category = "Behavior")
    private boolean disabled = false;
    private boolean disabled_set = false;

    /**
     * <p>Flag indicating that activation of this component by the user is not
     * currently permitted. In this component library, the disabled attribute
     * also causes the hyperlink to be renderered as formatted text in an 
     * HTML <span> tag. The hyperlink cannot be enabled from the client 
     * because this is a server side only feature. You cannot 
     * disable an anchor.</p>
     */
    public boolean isDisabled() {
        if (this.disabled_set) {
            return this.disabled;
        }
        ValueExpression _vb = getValueExpression("disabled");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }

    /**
     * <p>Flag indicating that activation of this component by the user is not
     * currently permitted. In this component library, the disabled attribute
     * also causes the hyperlink to be renderered as formatted text in an 
     * HTML <span> tag. The hyperlink cannot be enabled from the client 
     * because this is a server side only feature. You cannot 
     * disable an anchor.</p>
     * @see #isDisabled()
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        this.disabled_set = true;
    }
    /**
     * <p>Scripting code executed when this element loses focus.</p>
     */
    @Property(name = "onBlur", displayName = "Blur Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onBlur = null;

    /**
     * <p>Scripting code executed when this element loses focus.</p>
     */
    public String getOnBlur() {
        if (this.onBlur != null) {
            return this.onBlur;
        }
        ValueExpression _vb = getValueExpression("onBlur");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when this element loses focus.</p>
     * @see #getOnBlur()
     */
    public void setOnBlur(String onBlur) {
        this.onBlur = onBlur;
    }
    /**
     * <p>Scripting code executed when a mouse click occurs over this component.
     * If the component submits the form (by using the action attribute), the 
     * script that you use with the onClick attribute should not return from 
     * the function. When the action attribute is used, the component handles the 
     * return with a script that is appended to the anchor element's onclick 
     * property. When you supply an onClick attribute, this return script is 
     * appended after your script in the anchor's onclick. It is ok to return 
     * from your script to abort the submit process if necessary.</p>
     */
    @Property(name = "onClick", displayName = "Click Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onClick = null;

    /**
     * <p>Scripting code executed when a mouse click occurs over this component.
     * If the component submits the form (by using the action attribute), the 
     * script that you use with the onClick attribute should not return from 
     * the function. When the action attribute is used, the component handles the 
     * return with a script that is appended to the anchor element's onclick 
     * property. When you supply an onClick attribute, this return script is 
     * appended after your script in the anchor's onclick. It is ok to return 
     * from your script to abort the submit process if necessary.</p>
     */
    public String getOnClick() {
        if (this.onClick != null) {
            return this.onClick;
        }
        ValueExpression _vb = getValueExpression("onClick");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when a mouse click occurs over this component.
     * If the component submits the form (by using the action attribute), the 
     * script that you use with the onClick attribute should not return from 
     * the function. When the action attribute is used, the component handles the 
     * return with a script that is appended to the anchor element's onclick 
     * property. When you supply an onClick attribute, this return script is 
     * appended after your script in the anchor's onclick. It is ok to return 
     * from your script to abort the submit process if necessary.</p>
     * @see #getOnClick()
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }
    /**
     * <p>Scripting code executed when a mouse double click
     * occurs over this component.</p>
     */
    @Property(name = "onDblClick", displayName = "Double Click Script", category = "Javascript",
    isHidden = true, isAttribute = false,
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onDblClick = null;

    /**
     * <p>Scripting code executed when a mouse double click
     * occurs over this component.</p>
     */
    public String getOnDblClick() {
        if (this.onDblClick != null) {
            return this.onDblClick;
        }
        ValueExpression _vb = getValueExpression("onDblClick");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when a mouse double click
     * occurs over this component.</p>
     * @see #getOnDblClick()
     */
    public void setOnDblClick(String onDblClick) {
        this.onDblClick = onDblClick;
    }
    /**
     * <p>Scripting code executed when this component  receives focus. An
     * element receives focus when the user selects the element by pressing
     * the tab key or clicking the mouse.</p>
     */
    @Property(name = "onFocus", displayName = "Focus Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onFocus = null;

    /**
     * <p>Scripting code executed when this component  receives focus. An
     * element receives focus when the user selects the element by pressing
     * the tab key or clicking the mouse.</p>
     */
    public String getOnFocus() {
        if (this.onFocus != null) {
            return this.onFocus;
        }
        ValueExpression _vb = getValueExpression("onFocus");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when this component  receives focus. An
     * element receives focus when the user selects the element by pressing
     * the tab key or clicking the mouse.</p>
     * @see #getOnFocus()
     */
    public void setOnFocus(String onFocus) {
        this.onFocus = onFocus;
    }
    /**
     * <p>Scripting code executed when the user presses down on a key while the
     * component has focus.</p>
     */
    @Property(name = "onKeyDown", displayName = "Key Down Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyDown = null;

    /**
     * <p>Scripting code executed when the user presses down on a key while the
     * component has focus.</p>
     */
    public String getOnKeyDown() {
        if (this.onKeyDown != null) {
            return this.onKeyDown;
        }
        ValueExpression _vb = getValueExpression("onKeyDown");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user presses down on a key while the
     * component has focus.</p>
     * @see #getOnKeyDown()
     */
    public void setOnKeyDown(String onKeyDown) {
        this.onKeyDown = onKeyDown;
    }
    /**
     * <p>Scripting code executed when the user presses and releases a key while
     * the component has focus.</p>
     */
    @Property(name = "onKeyPress", displayName = "Key Press Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyPress = null;

    /**
     * <p>Scripting code executed when the user presses and releases a key while
     * the component has focus.</p>
     */
    public String getOnKeyPress() {
        if (this.onKeyPress != null) {
            return this.onKeyPress;
        }
        ValueExpression _vb = getValueExpression("onKeyPress");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user presses and releases a key while
     * the component has focus.</p>
     * @see #getOnKeyPress()
     */
    public void setOnKeyPress(String onKeyPress) {
        this.onKeyPress = onKeyPress;
    }
    /**
     * <p>Scripting code executed when the user releases a key while the
     * component has focus.</p>
     */
    @Property(name = "onKeyUp", displayName = "Key Up Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyUp = null;

    /**
     * <p>Scripting code executed when the user releases a key while the
     * component has focus.</p>
     */
    public String getOnKeyUp() {
        if (this.onKeyUp != null) {
            return this.onKeyUp;
        }
        ValueExpression _vb = getValueExpression("onKeyUp");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user releases a key while the
     * component has focus.</p>
     * @see #getOnKeyUp()
     */
    public void setOnKeyUp(String onKeyUp) {
        this.onKeyUp = onKeyUp;
    }
    /**
     * <p>Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     */
    @Property(name = "onMouseDown", displayName = "Mouse Down Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseDown = null;

    /**
     * <p>Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     */
    public String getOnMouseDown() {
        if (this.onMouseDown != null) {
            return this.onMouseDown;
        }
        ValueExpression _vb = getValueExpression("onMouseDown");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     * @see #getOnMouseDown()
     */
    public void setOnMouseDown(String onMouseDown) {
        this.onMouseDown = onMouseDown;
    }
    /**
     * <p>Scripting code executed when the user moves the mouse pointer while
     * over the component.</p>
     */
    @Property(name = "onMouseMove", displayName = "Mouse Move Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseMove = null;

    /**
     * <p>Scripting code executed when the user moves the mouse pointer while
     * over the component.</p>
     */
    public String getOnMouseMove() {
        if (this.onMouseMove != null) {
            return this.onMouseMove;
        }
        ValueExpression _vb = getValueExpression("onMouseMove");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user moves the mouse pointer while
     * over the component.</p>
     * @see #getOnMouseMove()
     */
    public void setOnMouseMove(String onMouseMove) {
        this.onMouseMove = onMouseMove;
    }
    /**
     * <p>Scripting code executed when a mouse out movement
     * occurs over this component.</p>
     */
    @Property(name = "onMouseOut", displayName = "Mouse Out Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOut = null;

    /**
     * <p>Scripting code executed when a mouse out movement
     * occurs over this component.</p>
     */
    public String getOnMouseOut() {
        if (this.onMouseOut != null) {
            return this.onMouseOut;
        }
        ValueExpression _vb = getValueExpression("onMouseOut");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when a mouse out movement
     * occurs over this component.</p>
     * @see #getOnMouseOut()
     */
    public void setOnMouseOut(String onMouseOut) {
        this.onMouseOut = onMouseOut;
    }
    /**
     * <p>Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     */
    @Property(name = "onMouseOver", displayName = "Mouse In Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOver = null;

    /**
     * <p>Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     */
    public String getOnMouseOver() {
        if (this.onMouseOver != null) {
            return this.onMouseOver;
        }
        ValueExpression _vb = getValueExpression("onMouseOver");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     * @see #getOnMouseOver()
     */
    public void setOnMouseOver(String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }
    /**
     * <p>Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     */
    @Property(name = "onMouseUp", displayName = "Mouse Up Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseUp = null;

    /**
     * <p>Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     */
    public String getOnMouseUp() {
        if (this.onMouseUp != null) {
            return this.onMouseUp;
        }
        ValueExpression _vb = getValueExpression("onMouseUp");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     * @see #getOnMouseUp()
     */
    public void setOnMouseUp(String onMouseUp) {
        this.onMouseUp = onMouseUp;
    }
    /**
     * <p>The shape of the hot spot on the screen (for use in client-side image 
     * maps). Valid values are: default (entire region); rect (rectangular 
     * region); circle (circular region); and poly (polygonal region).</p>
     */
    @Property(name = "shape", displayName = "Shape", category = "Advanced", isHidden = true, isAttribute = false)
    private String shape = null;

    /**
     * <p>The shape of the hot spot on the screen (for use in client-side image 
     * maps). Valid values are: default (entire region); rect (rectangular 
     * region); circle (circular region); and poly (polygonal region).</p>
     */
    public String getShape() {
        if (this.shape != null) {
            return this.shape;
        }
        ValueExpression _vb = getValueExpression("shape");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The shape of the hot spot on the screen (for use in client-side image 
     * maps). Valid values are: default (entire region); rect (rectangular 
     * region); circle (circular region); and poly (polygonal region).</p>
     * @see #getShape()
     */
    public void setShape(String shape) {
        this.shape = shape;
    }
    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name = "style", displayName = "CSS Style(s)", category = "Appearance",
    editorClassName = "com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    public String getStyle() {
        if (this.style != null) {
            return this.style;
        }
        ValueExpression _vb = getValueExpression("style");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }
    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name = "styleClass", displayName = "CSS Style Class(es)", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        ValueExpression _vb = getValueExpression("styleClass");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    @Property(name = "tabIndex", displayName = "Tab Index", category = "Accessibility",
    editorClassName = "com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    public int getTabIndex() {
        if (this.tabIndex_set) {
            return this.tabIndex;
        }
        ValueExpression _vb = getValueExpression("tabIndex");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     * @see #getTabIndex()
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
        this.tabIndex_set = true;
    }
    /**
     * <p>The resource at the specified URL is displayed in the frame that is 
     * specified with the target attribute. Values such as "_blank" that are 
     * valid for the target attribute of a HTML anchor element are also valid 
     * for this attribute in this component</p>
     */
    @Property(name = "target", displayName = "Target", category = "Behavior",
    editorClassName = "com.sun.webui.jsf.component.propertyeditors.FrameTargetsEditor")
    private String target = null;

    /**
     * <p>The resource at the specified URL is displayed in the frame that is 
     * specified with the target attribute. Values such as "_blank" that are 
     * valid for the target attribute of a HTML anchor element are also valid 
     * for this attribute in this component</p>
     */
    public String getTarget() {
        if (this.target != null) {
            return this.target;
        }
        ValueExpression _vb = getValueExpression("target");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The resource at the specified URL is displayed in the frame that is 
     * specified with the target attribute. Values such as "_blank" that are 
     * valid for the target attribute of a HTML anchor element are also valid 
     * for this attribute in this component</p>
     * @see #getTarget()
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * <p>The text to be displayed for the hyperlink.</p>
     */
    @Property(name = "text", displayName = "text", category = "Appearance", isDefault = true,
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    public Object getText() {
        return getValue();
    }

    /**
     * <p>The text to be displayed for the hyperlink.</p>
     * @see #getText()
     */
    public void setText(Object text) {
        setValue(text);
    }
    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    @Property(name = "toolTip", displayName = "Tool Tip", category = "Behavior",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String toolTip = null;

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    public String getToolTip() {
        if (this.toolTip != null) {
            return this.toolTip;
        }
        ValueExpression _vb = getValueExpression("toolTip");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     * @see #getToolTip()
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }
    /**
     * <p>The MIME content type of the resource specified by this component.</p>
     */
    @Property(name = "type", displayName = "Type", category = "Advanced",
    editorClassName = "com.sun.webui.jsf.component.propertyeditors.MimeTypesEditor")
    private String type = null;

    /**
     * <p>The MIME content type of the resource specified by this component.</p>
     */
    public String getType() {
        if (this.type != null) {
            return this.type;
        }
        ValueExpression _vb = getValueExpression("type");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The MIME content type of the resource specified by this component.</p>
     * @see #getType()
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * <p>Absolute, relative, or context relative (starting with "/") URL to the 
     * resource selected by this hyperlink. If the url attribute is specified, 
     * clicking this hyperlink sends the browser to the new location. If the action
     * attribute is specified, the form is submitted. If both are specified,
     * the url attribute takes precedence.</p>
     */
    @Property(name = "url", displayName = "URL", category = "Behavior",
    editorClassName = "com.sun.webui.jsf.component.propertyeditors.SunWebUrlPropertyEditor")
    private String url = null;

    /**
     * <p>Absolute, relative, or context relative (starting with "/") URL to the 
     * resource selected by this hyperlink. If the url attribute is specified, 
     * clicking this hyperlink sends the browser to the new location. If the action
     * attribute is specified, the form is submitted. If both are specified,
     * the url attribute takes precedence.</p>
     */
    public String getUrl() {
        if (this.url != null) {
            return this.url;
        }
        ValueExpression _vb = getValueExpression("url");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Absolute, relative, or context relative (starting with "/") URL to the 
     * resource selected by this hyperlink. If the url attribute is specified, 
     * clicking this hyperlink sends the browser to the new location. If the action
     * attribute is specified, the form is submitted. If both are specified,
     * the url attribute takes precedence.</p>
     * @see #getUrl()
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * <p>The language code of the resource designated by this hyperlink.</p>
     */
    @Property(name = "urlLang", displayName = "URL Lang", category = "Advanced",
    editorClassName = "com.sun.webui.jsf.component.propertyeditors.LanguagesEditor")
    private String urlLang = null;

    /**
     * <p>The language code of the resource designated by this hyperlink.</p>
     */
    public String getUrlLang() {
        if (this.urlLang != null) {
            return this.urlLang;
        }
        ValueExpression _vb = getValueExpression("urlLang");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The language code of the resource designated by this hyperlink.</p>
     * @see #getUrlLang()
     */
    public void setUrlLang(String urlLang) {
        this.urlLang = urlLang;
    }
    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name = "visible", displayName = "Visible", category = "Behavior")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    public boolean isVisible() {
        if (this.visible_set) {
            return this.visible;
        }
        ValueExpression _vb = getValueExpression("visible");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.disabled = ((Boolean) _values[1]).booleanValue();
        this.disabled_set = ((Boolean) _values[2]).booleanValue();
        this.onBlur = (String) _values[3];
        this.onClick = (String) _values[4];
        this.onDblClick = (String) _values[5];
        this.onFocus = (String) _values[6];
        this.onKeyDown = (String) _values[7];
        this.onKeyPress = (String) _values[8];
        this.onKeyUp = (String) _values[9];
        this.onMouseDown = (String) _values[10];
        this.onMouseMove = (String) _values[11];
        this.onMouseOut = (String) _values[12];
        this.onMouseOver = (String) _values[13];
        this.onMouseUp = (String) _values[14];
        this.shape = (String) _values[15];
        this.style = (String) _values[16];
        this.styleClass = (String) _values[17];
        this.tabIndex = ((Integer) _values[18]).intValue();
        this.tabIndex_set = ((Boolean) _values[19]).booleanValue();
        this.target = (String) _values[20];
        this.toolTip = (String) _values[21];
        this.type = (String) _values[22];
        this.url = (String) _values[23];
        this.urlLang = (String) _values[24];
        this.visible = ((Boolean) _values[25]).booleanValue();
        this.visible_set = ((Boolean) _values[26]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[27];
        _values[0] = super.saveState(_context);
        _values[1] = this.disabled ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.disabled_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.onBlur;
        _values[4] = this.onClick;
        _values[5] = this.onDblClick;
        _values[6] = this.onFocus;
        _values[7] = this.onKeyDown;
        _values[8] = this.onKeyPress;
        _values[9] = this.onKeyUp;
        _values[10] = this.onMouseDown;
        _values[11] = this.onMouseMove;
        _values[12] = this.onMouseOut;
        _values[13] = this.onMouseOver;
        _values[14] = this.onMouseUp;
        _values[15] = this.shape;
        _values[16] = this.style;
        _values[17] = this.styleClass;
        _values[18] = new Integer(this.tabIndex);
        _values[19] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[20] = this.target;
        _values[21] = this.toolTip;
        _values[22] = this.type;
        _values[23] = this.url;
        _values[24] = this.urlLang;
        _values[25] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[26] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
