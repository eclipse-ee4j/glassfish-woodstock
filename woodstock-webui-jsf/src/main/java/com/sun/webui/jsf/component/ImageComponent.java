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

import javax.el.ValueExpression;
import javax.faces.component.UIGraphic;
import javax.faces.context.FacesContext;
import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * The ImageComponent is used to display in inline graphic image. 
 */
@Component(type = "com.sun.webui.jsf.Image", family = "com.sun.webui.jsf.Image",
displayName = "Image",
tagName = "image", instanceName = "image",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_image_component",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_image_component_props")
public class ImageComponent extends UIGraphic {

    /**
     * <p>Construct a new <code>ImageComponent</code>.</p>
     */
    public ImageComponent() {
        super();
        setRendererType("com.sun.webui.jsf.Image");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.Image";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding expression to retrieve
     */
    @Override
    public ValueExpression getValueExpression(String name) {
        if (name.equals("url")) {
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
        if (name.equals("url")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }

    // Hide value
    @Property(name = "value", isHidden = true, isAttribute = false)
    @Override
    public Object getValue() {
        return super.getValue();
    }
    /**
     * <p>Specifies the position of the image with respect to its context.
     * Valid values are: bottom (the default); middle; top; left; right.</p>
     */
    @Property(name = "align", displayName = "Align", category = "Appearance",
    editorClassName = "com.sun.webui.jsf.component.propertyeditors.HtmlAlignEditor")
    private String align = null;

    /**
     * <p>Specifies the position of the image with respect to its context.
     * Valid values are: bottom (the default); middle; top; left; right.</p>
     */
    public String getAlign() {
        if (this.align != null) {
            return this.align;
        }
        ValueExpression _vb = getValueExpression("align");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Specifies the position of the image with respect to its context.
     * Valid values are: bottom (the default); middle; top; left; right.</p>
     * @see #getAlign()
     */
    public void setAlign(String align) {
        this.align = align;
    }
    /**
     * <p>Alternative textual description of the image rendered by this
     * component. The alt text can be used by screen readers and in tool tips,
     * and when image display is turned off in the web browser.</p>
     */
    @Property(name = "alt", displayName = "Alt Text", category = "Accessibility",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String alt = null;

    /**
     * <p>Alternative textual description of the image rendered by this
     * component. The alt text can be used by screen readers and in tool tips,
     * and when image display is turned off in the web browser.</p>
     */
    public String getAlt() {
        if (this.alt != null) {
            return this.alt;
        }
        ValueExpression _vb = getValueExpression("alt");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Alternative textual description of the image rendered by this 
     * component. The alt text can be used by screen readers and in tool tips,
     * and when image display is turned off in the web browser.</p>
     * @see #getAlt()
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }
    /**
     * <p>Specifies the width of the img border in pixels.
     * The default value for this attribute depends on the web browser.</p>
     */
    @Property(name = "border", displayName = "Border", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int border = Integer.MIN_VALUE;
    private boolean border_set = false;

    /**
     * <p>Specifies the width of the img border in pixels.
     * The default value for this attribute depends on the web browser.</p>
     */
    public int getBorder() {
        if (this.border_set) {
            return this.border;
        }
        ValueExpression _vb = getValueExpression("border");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return 0;
    }

    /**
     * <p>Specifies the width of the img border in pixels.
     * The default value for this attribute depends on the web browser.</p>
     * @see #getBorder()
     */
    public void setBorder(int border) {
        this.border = border;
        this.border_set = true;
    }
    /**
     * <p>Image height override. When specified, the width and height attributes 
     * tell web browsers to override the natural image or object size in favor 
     * of these values, specified in pixels. Some browsers might not support 
     * this behavior.</p>
     */
    @Property(name = "height", displayName = "Height", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int height = Integer.MIN_VALUE;
    private boolean height_set = false;

    /**
     * <p>Image height override. When specified, the width and height attributes 
     * tell web browsers to override the natural image or object size in favor 
     * of these values, specified in pixels. Some browsers might not support 
     * this behavior.</p>
     */
    public int getHeight() {
        if (this.height_set) {
            return this.height;
        }
        ValueExpression _vb = getValueExpression("height");
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
     * <p>Image height override. When specified, the width and height attributes 
     * tell web browsers to override the natural image or object size in favor 
     * of these values, specified in pixels. Some browsers might not support 
     * this behavior.</p>
     * @see #getHeight()
     */
    public void setHeight(int height) {
        this.height = height;
        this.height_set = true;
    }
    /**
     * <p>Specifies the amount of white space in pixels to be inserted to the
     * left and right of the image. The default value is not specified but is       
     * generally a small, non-zero size.</p>
     */
    @Property(name = "hspace", displayName = "Horizontal Space", category = "Advanced",
    editorClassName = "com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int hspace = Integer.MIN_VALUE;
    private boolean hspace_set = false;

    /**
     * <p>Specifies the amount of white space in pixels to be inserted to the
     * left and right of the image. The default value is not specified but is       
     * generally a small, non-zero size.</p>
     */
    public int getHspace() {
        if (this.hspace_set) {
            return this.hspace;
        }
        ValueExpression _vb = getValueExpression("hspace");
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
     * <p>Specifies the amount of white space in pixels to be inserted to the
     * left and right of the image. The default value is not specified but is       
     * generally a small, non-zero size.</p>
     * @see #getHspace()
     */
    public void setHspace(int hspace) {
        this.hspace = hspace;
        this.hspace_set = true;
    }
    /**
     * <p>The identifier of the desired theme image.</p>
     */
    @Property(name = "icon", displayName = "Icon", category = "Appearance",
    editorClassName = "com.sun.webui.jsf.component.propertyeditors.ThemeIconsEditor")
    private String icon = null;

    /**
     * <p>The identifier of the desired theme image.</p>
     */
    public String getIcon() {
        if (this.icon != null) {
            return this.icon;
        }
        ValueExpression _vb = getValueExpression("icon");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The identifier of the desired theme image.</p>
     * @see #getIcon()
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    /**
     * <p>A verbose description of this image.</p>
     */
    @Property(name = "longDesc", displayName = "Long Description", category = "Accessibility",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String longDesc = null;

    /**
     * <p>A verbose description of this image.</p>
     */
    public String getLongDesc() {
        if (this.longDesc != null) {
            return this.longDesc;
        }
        ValueExpression _vb = getValueExpression("longDesc");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>A verbose description of this image.</p>
     * @see #getLongDesc()
     */
    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }
    /**
     * <p>Scripting code executed when a mouse click
     * occurs over this component.</p>
     */
    @Property(name = "onClick", displayName = "Click Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onClick = null;

    /**
     * <p>Scripting code executed when a mouse click
     * occurs over this component.</p>
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
     * <p>Scripting code executed when a mouse click
     * occurs over this component.</p>
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
     * <p>Absolute or relative URL to the image to be rendered.</p>
     */
    @Property(name = "url", displayName = "Url", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.ImageUrlPropertyEditor")
    @Override
    public String getUrl() {
        return (String) getValue();
    }

    /**
     * <p>Absolute or relative URL to the image to be rendered.</p>
     * @see #getUrl()
     */
    @Override
    public void setUrl(String url) {
        setValue((Object) url);
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
     * <p>Specifies the amount of white space in pixels to be inserted above and
     * below the image. The default value is not specified but is generally a
     * small, non-zero size.</p>
     */
    @Property(name = "vspace", displayName = "Vertical Space", category = "Advanced",
    editorClassName = "com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int vspace = Integer.MIN_VALUE;
    private boolean vspace_set = false;

    /**
     * <p>Specifies the amount of white space in pixels to be inserted above and
     * below the image. The default value is not specified but is generally a
     * small, non-zero size.</p>
     */
    public int getVspace() {
        if (this.vspace_set) {
            return this.vspace;
        }
        ValueExpression _vb = getValueExpression("vspace");
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
     * <p>Specifies the amount of white space in pixels to be inserted above and
     * below the image. The default value is not specified but is generally a
     * small, non-zero size.</p>
     * @see #getVspace()
     */
    public void setVspace(int vspace) {
        this.vspace = vspace;
        this.vspace_set = true;
    }
    /**
     * <p>Image width override. When specified, the width and height attributes 
     * tell web browsers to override the natural image or object size in favor 
     * of these values, specified in pixels. Some browsers might not support 
     * this behavior.</p>
     */
    @Property(name = "width", displayName = "Width", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int width = Integer.MIN_VALUE;
    private boolean width_set = false;

    /**
     * <p>Image width override. When specified, the width and height attributes 
     * tell web browsers to override the natural image or object size in favor 
     * of these values, specified in pixels. Some browsers might not support 
     * this behavior.</p>
     */
    public int getWidth() {
        if (this.width_set) {
            return this.width;
        }
        ValueExpression _vb = getValueExpression("width");
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
     * <p>Image width override. When specified, the width and height attributes 
     * tell web browsers to override the natural image or object size in favor 
     * of these values, specified in pixels. Some browsers might not support 
     * this behavior.</p>
     * @see #getWidth()
     */
    public void setWidth(int width) {
        this.width = width;
        this.width_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.align = (String) _values[1];
        this.alt = (String) _values[2];
        this.border = ((Integer) _values[3]).intValue();
        this.border_set = ((Boolean) _values[4]).booleanValue();
        this.height = ((Integer) _values[5]).intValue();
        this.height_set = ((Boolean) _values[6]).booleanValue();
        this.hspace = ((Integer) _values[7]).intValue();
        this.hspace_set = ((Boolean) _values[8]).booleanValue();
        this.icon = (String) _values[9];
        this.longDesc = (String) _values[10];
        this.onClick = (String) _values[11];
        this.onDblClick = (String) _values[12];
        this.onMouseDown = (String) _values[13];
        this.onMouseMove = (String) _values[14];
        this.onMouseOut = (String) _values[15];
        this.onMouseOver = (String) _values[16];
        this.onMouseUp = (String) _values[17];
        this.style = (String) _values[18];
        this.styleClass = (String) _values[19];
        this.toolTip = (String) _values[20];
        this.visible = ((Boolean) _values[21]).booleanValue();
        this.visible_set = ((Boolean) _values[22]).booleanValue();
        this.vspace = ((Integer) _values[23]).intValue();
        this.vspace_set = ((Boolean) _values[24]).booleanValue();
        this.width = ((Integer) _values[25]).intValue();
        this.width_set = ((Boolean) _values[26]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[27];
        _values[0] = super.saveState(_context);
        _values[1] = this.align;
        _values[2] = this.alt;
        _values[3] = new Integer(this.border);
        _values[4] = this.border_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = new Integer(this.height);
        _values[6] = this.height_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = new Integer(this.hspace);
        _values[8] = this.hspace_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.icon;
        _values[10] = this.longDesc;
        _values[11] = this.onClick;
        _values[12] = this.onDblClick;
        _values[13] = this.onMouseDown;
        _values[14] = this.onMouseMove;
        _values[15] = this.onMouseOut;
        _values[16] = this.onMouseOver;
        _values[17] = this.onMouseUp;
        _values[18] = this.style;
        _values[19] = this.styleClass;
        _values[20] = this.toolTip;
        _values[21] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[22] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[23] = new Integer(this.vspace);
        _values[24] = this.vspace_set ? Boolean.TRUE : Boolean.FALSE;
        _values[25] = new Integer(this.width);
        _values[26] = this.width_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
