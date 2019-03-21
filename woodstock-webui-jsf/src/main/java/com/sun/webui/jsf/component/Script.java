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
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * The Script component is can be used to refer to a Javascript file, by using 
 * the url attribute. The tag can also be used embed Javascript code within the 
 * rendered HTML page.
 */
@Component(type = "com.sun.webui.jsf.Script", family = "com.sun.webui.jsf.Script",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_script",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_script_props")
public class Script extends UIComponentBase {

    /**
     * Default constructor.
     */
    public Script() {
        super();
        setRendererType("com.sun.webui.jsf.Script");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Script";
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
     * <p>Defines the character (charset) encoding of the target URL.
     * See iana.org for a complete list of character encodings.</p>
     */
    @Property(name = "charset", displayName = "Charset", category = "Advanced",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String charset = null;

    /**
     * <p>Defines the character (charset) encoding of the target URL.
     * See iana.org for a complete list of character encodings.</p>
     */
    public String getCharset() {
        if (this.charset != null) {
            return this.charset;
        }
        ValueExpression _vb = getValueExpression("charset");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Defines the character (charset) encoding of the target URL.
     * See iana.org for a complete list of character encodings.</p>
     * @see #getCharset()
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }
    /**
     * <p>Indicates the MIME type of the script.  Default is "text/javascript"</p>
     */
    @Property(name = "type", displayName = "Type", category = "Advanced",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String type = null;

    /**
     * <p>Indicates the MIME type of the script.  Default is "text/javascript"</p>
     */
    public String getType() {
        if (this.type != null) {
            return this.type;
        }
        ValueExpression _vb = getValueExpression("type");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return "text/javascript";
    }

    /**
     * <p>Indicates the MIME type of the script.  Default is "text/javascript"</p>
     * @see #getType()
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * <p>Defines the absolute or relative URL to a file that contains the 
     * script.  Use this attribute to refer to a file instead of inserting the 
     * script into your HTML document</p>
     */
    @Property(name = "url", displayName = "URL", category = "Data", isDefault = true,
    editorClassName = "com.sun.webui.jsf.component.propertyeditors.SunWebUrlPropertyEditor")
    private String url = null;

    /**
     * <p>Defines the absolute or relative URL to a file that contains the 
     * script.  Use this attribute to refer to a file instead of inserting the 
     * script into your HTML document</p>
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
     * <p>Defines the absolute or relative URL to a file that contains the 
     * script.  Use this attribute to refer to a file instead of inserting the 
     * script into your HTML document</p>
     * @see #getUrl()
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.charset = (String) _values[1];
        this.type = (String) _values[2];
        this.url = (String) _values[3];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[4];
        _values[0] = super.saveState(_context);
        _values[1] = this.charset;
        _values[2] = this.type;
        _values[3] = this.url;
        return _values;
    }
}
