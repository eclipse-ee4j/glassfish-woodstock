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
 * The Html component is used to create the &lt;html&gt; element.
 */
@Component(type = "com.sun.webui.jsf.Html", family = "com.sun.webui.jsf.Html",
displayName = "Html", tagName = "html",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_html",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_html_props")
public class Html extends UIComponentBase {

    /**
     * <p>Construct a new <code>Html</code>.</p>
     */
    public Html() {
        super();
        setRendererType("com.sun.webui.jsf.Html");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Html";
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
     * <p>Sets the language code for this document</p>
     */
    @Property(name = "lang", displayName = "Lang", category = "Advanced")
    private String lang = null;

    /**
     * <p>Sets the language code for this document</p>
     */
    public String getLang() {
        if (this.lang != null) {
            return this.lang;
        }
        ValueExpression _vb = getValueExpression("lang");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the language code for this document</p>
     * @see #getLang()
     */
    public void setLang(String lang) {
        this.lang = lang;
    }
    /**
     * <p>Defines the XML namespace attribute.  Default value is: 
     * http://www.w3.org/1999/xhtml</p>
     */
    @Property(name = "xmlns", displayName = "XML Namespace", category = "Advanced", isDefault = true)
    private String xmlns = null;

    /**
     * <p>Defines the XML namespace attribute.  Default value is: 
     * http://www.w3.org/1999/xhtml</p>
     */
    public String getXmlns() {
        if (this.xmlns != null) {
            return this.xmlns;
        }
        ValueExpression _vb = getValueExpression("xmlns");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return "http://www.w3.org/1999/xhtml";
    }

    /**
     * <p>Defines the XML namespace attribute.  Default value is: 
     * http://www.w3.org/1999/xhtml</p>
     * @see #getXmlns()
     */
    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.lang = (String) _values[1];
        this.xmlns = (String) _values[2];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[3];
        _values[0] = super.saveState(_context);
        _values[1] = this.lang;
        _values[2] = this.xmlns;
        return _values;
    }
}
