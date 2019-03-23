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
import javax.faces.context.FacesContext;
import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * The Iframe component is used to create an inline frame.
 */
@Component(type = "com.sun.webui.jsf.IFrame", family = "com.sun.webui.jsf.IFrame",
displayName = "Iframe", instanceName = "iFrame", tagName = "iframe",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_i_frame",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_i_frame_props")
public class IFrame extends Frame {

    /**
     * <p>Construct a new <code>IFrame</code>.</p>
     */
    public IFrame() {
        super();
        setRendererType("com.sun.webui.jsf.IFrame");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.IFrame";
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
     * <p>Specifies how to align the iframe according to the surrounding text.  One
     * of the following: left, right, top, middle, bottom</p>
     */
    @Property(name = "align", displayName = "Align", category = "Appearance")
    private String align = null;

    /**
     * <p>Specifies how to align the iframe according to the surrounding text.  One
     * of the following: left, right, top, middle, bottom</p>
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
     * <p>Specifies how to align the iframe according to the surrounding text.  One
     * of the following: left, right, top, middle, bottom</p>
     * @see #getAlign()
     */
    public void setAlign(String align) {
        this.align = align;
    }
    /**
     * <p>Defines the height of the iframe in pixels or as a percentage of it's 
     * container</p>
     */
    @Property(name = "height", displayName = "Height", category = "Appearance")
    private String height = null;

    /**
     * <p>Defines the height of the iframe in pixels or as a percentage of it's 
     * container</p>
     */
    public String getHeight() {
        if (this.height != null) {
            return this.height;
        }
        ValueExpression _vb = getValueExpression("height");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Defines the height of the iframe in pixels or as a percentage of it's 
     * container</p>
     * @see #getHeight()
     */
    public void setHeight(String height) {
        this.height = height;
    }
    /**
     * <p>Set the value of the noResize attribute to "true" when  user 
     * is not allowed to resize the frame.</p>
     */
    @Property(name = "noResize", displayName = "No Resize", category = "Appearance", isHidden = true, isAttribute = false)
    private boolean noResize = false;
    private boolean noResize_set = false;

    /**
     * <p>Set the value of the noResize attribute to "true" when  user 
     * is not allowed to resize the frame.</p>
     */
    @Override
    public boolean isNoResize() {
        if (this.noResize_set) {
            return this.noResize;
        }
        ValueExpression _vb = getValueExpression("noResize");
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
     * <p>Set the value of the noResize attribute to "true" when  user 
     * is not allowed to resize the frame.</p>
     * @see #isNoResize()
     */
    @Override
    public void setNoResize(boolean noResize) {
        this.noResize = noResize;
        this.noResize_set = true;
    }
    /**
     * <p>Defines the width of the iframe in pixels or as a percentage of it's 
     * container</p>
     */
    @Property(name = "width", displayName = "Width", category = "Appearance")
    private String width = null;

    /**
     * <p>Defines the width of the iframe in pixels or as a percentage of it's 
     * container</p>
     */
    public String getWidth() {
        if (this.width != null) {
            return this.width;
        }
        ValueExpression _vb = getValueExpression("width");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Defines the width of the iframe in pixels or as a percentage of it's 
     * container</p>
     * @see #getWidth()
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.align = (String) _values[1];
        this.height = (String) _values[2];
        this.noResize = ((Boolean) _values[3]).booleanValue();
        this.noResize_set = ((Boolean) _values[4]).booleanValue();
        this.width = (String) _values[5];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[6];
        _values[0] = super.saveState(_context);
        _values[1] = this.align;
        _values[2] = this.height;
        _values[3] = this.noResize ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.noResize_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.width;
        return _values;
    }
}
