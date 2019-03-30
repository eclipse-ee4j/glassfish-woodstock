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
 * The Page component is used to indicate the beginning of the part of the JSP
 * page that is used by the Sun Java Web UI Components.
 */
@Component(type = "com.sun.webui.jsf.Page", family = "com.sun.webui.jsf.Page",
        displayName = "Page", tagName = "page",
        helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_page",
        propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_page_props")
public final class Page extends UIComponentBase {

    /**
     * Create a new instance.
     */
    public Page() {
        super();
        setRendererType("com.sun.webui.jsf.Page");
    }

    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.Page";
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
     * <span style="color: rgb(0, 0, 0);">Use the frame attribute to
     * indicate whether the page should render frames. If this attribute is
     * true, the rendered HTML page includes a </span><code
     * style="color: rgb(0, 0, 0);">&lt;frameset&gt;</code><span
     * style="color: rgb(0, 0, 0);"> element. If false, the rendered page
     * uses a </span><code style="color: rgb(0, 0, 0);">&lt;body&gt;</code><span
     * style="color: rgb(0, 0, 0);"> tag.&nbsp; This attribute also
     * influences the rendering of the <code>&lt;!DOCTYPE&gt;</code>
     * attribute. If frameset is true, the <code>&lt;!DOCTYPE&gt;</code> will
     * be one of the following,
     * depending on the setting of xhtml attribute.<br></span>
     * <pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"&gt;<br><br>&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN"<br> "http://www.w3.org/TR/html4/DTD/frameset.dtd"&gt;<br></pre>
     */
    @Property(name = "frame", displayName = "Needs Frame", category = "Appearance")
    private boolean frame = false;
    private boolean frame_set = false;

    /**
     * <span style="color: rgb(0, 0, 0);">Use the frame attribute to
     * indicate whether the page should render frames. If this attribute is
     * true, the rendered HTML page includes a </span><code
     * style="color: rgb(0, 0, 0);">&lt;frameset&gt;</code><span
     * style="color: rgb(0, 0, 0);"> element. If false, the rendered page
     * uses a </span><code style="color: rgb(0, 0, 0);">&lt;body&gt;</code><span
     * style="color: rgb(0, 0, 0);"> tag.&nbsp; This attribute also
     * influences the rendering of the <code>&lt;!DOCTYPE&gt;</code>
     * attribute. If frameset is true, the <code>&lt;!DOCTYPE&gt;</code> will
     * be one of the following,
     * depending on the setting of xhtml attribute.<br></span>
     * <pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"&gt;<br><br>&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN"<br> "http://www.w3.org/TR/html4/DTD/frameset.dtd"&gt;<br></pre>
     */
    public boolean isFrame() {
        if (this.frame_set) {
            return this.frame;
        }
        ValueExpression _vb = getValueExpression("frame");
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
     * <span style="color: rgb(0, 0, 0);">Use the frame attribute to
     * indicate whether the page should render frames. If this attribute is
     * true, the rendered HTML page includes a </span><code
     * style="color: rgb(0, 0, 0);">&lt;frameset&gt;</code><span
     * style="color: rgb(0, 0, 0);"> element. If false, the rendered page
     * uses a </span><code style="color: rgb(0, 0, 0);">&lt;body&gt;</code><span
     * style="color: rgb(0, 0, 0);"> tag.&nbsp; This attribute also
     * influences the rendering of the <code>&lt;!DOCTYPE&gt;</code>
     * attribute. If frameset is true, the <code>&lt;!DOCTYPE&gt;</code> will
     * be one of the following,
     * depending on the setting of xhtml attribute.<br></span><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"&gt;<br><br>&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN"<br> "http://www.w3.org/TR/html4/DTD/frameset.dtd"&gt;<br></pre>
     * @see #isFrame()
     */
    public void setFrame(boolean frame) {
        this.frame = frame;
        this.frame_set = true;
    }

    /**
     * <span style="color: rgb(0, 0, 0);">XHTML transitional page or HTML
     * transitional page. This attribute influences
     * the rendering of the <code>&lt;!DOCTYPE&gt;</code> attribute. If xhtml
     * is true, the <code>&lt;!DOCTYPE&gt;</code> will be one of the
     * following,
     * depending on the setting of frameset attribute.<br></span><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"</pre><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;</pre>
     */
    @Property(name = "xhtml", displayName = "XHTML Transitional", category = "Appearance")
    private boolean xhtml = false;
    private boolean xhtml_set = false;

    /**
     * <span style="color: rgb(0, 0, 0);">XHTML transitional page or HTML
     * transitional page. This attribute influences
     * the rendering of the <code>&lt;!DOCTYPE&gt;</code> attribute. If xhtml
     * is true, the <code>&lt;!DOCTYPE&gt;</code> will be one of the
     * following,
     * depending on the setting of frameset attribute.<br></span><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"</pre><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;</pre>
     */
    public boolean isXhtml() {
        if (this.xhtml_set) {
            return this.xhtml;
        }
        ValueExpression _vb = getValueExpression("xhtml");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result);
            }
        }
        return true;
    }

    /**
     * <span style="color: rgb(0, 0, 0);">XHTML transitional page or HTML
     * transitional page. This attribute influences
     * the rendering of the <code>&lt;!DOCTYPE&gt;</code> attribute. If xhtml
     * is true, the <code>&lt;!DOCTYPE&gt;</code> will be one of the
     * following,
     * depending on the setting of frameset attribute.<br></span><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"</pre><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;</pre>
     * @see #isXhtml()
     */
    public void setXhtml(boolean xhtml) {
        this.xhtml = xhtml;
        this.xhtml_set = true;
    }

    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.frame = ((Boolean) _values[1]);
        this.frame_set = ((Boolean) _values[2]);
        this.xhtml = ((Boolean) _values[3]);
        this.xhtml_set = ((Boolean) _values[4]);
    }

    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[5];
        _values[0] = super.saveState(_context);
        _values[1] = this.frame ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.frame_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.xhtml ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.xhtml_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
