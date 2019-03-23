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
import javax.faces.component.UIComponentBase;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;

/**
 * The PanelGroup component is used to arrange a group of components.
 */
@Component(type = "com.sun.webui.jsf.PanelGroup", family = "com.sun.webui.jsf.PanelGroup",
displayName = "Group Panel", instanceName = "groupPanel", tagName = "panelGroup",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_group_panel",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_panel_group_props")
public class PanelGroup extends UIComponentBase implements NamingContainer {

    public final static String SEPARATOR_FACET = "separator"; //NOI18N

    /**
     * Default constructor.
     */
    public PanelGroup() {
        super();
        setRendererType("com.sun.webui.jsf.PanelGroup");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.PanelGroup";
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
     * <p>By default, the panelGroup component is rendered on the same
     * line as the component that comes before it and the component
     * that follows, in a flow layout.  If the block attribute is set
     * to true, the panelGroup component is rendered on its own line.
     * The components before it and after it are on different lines.
     * The block attribute has no effect on the panelGroup component's
     * children.</p>
     */
    @Property(name = "block", displayName = "Block", category = "Appearance")
    private boolean block = false;
    private boolean block_set = false;

    /**
     * <p>By default, the panelGroup component is rendered on the same
     * line as the component that comes before it and the component
     * that follows, in a flow layout.  If the block attribute is set
     * to true, the panelGroup component is rendered on its own line.
     * The components before it and after it are on different lines.
     * The block attribute has no effect on the panelGroup component's
     * children.</p>
     */
    public boolean isBlock() {
        if (this.block_set) {
            return this.block;
        }
        ValueExpression _vb = getValueExpression("block");
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
     * <p>By default, the panelGroup component is rendered on the same
     * line as the component that comes before it and the component
     * that follows, in a flow layout.  If the block attribute is set
     * to true, the panelGroup component is rendered on its own line.
     * The components before it and after it are on different lines.
     * The block attribute has no effect on the panelGroup component's
     * children.</p>
     * @see #isBlock()
     */
    public void setBlock(boolean block) {
        this.block = block;
        this.block_set = true;
    }
    /**
     * <p> The string of characters or HTML element that should be inserted between each
     * component that is a child of this component.  To specify an HTML element,
     * use the character entities &amp;lt; and &amp;gt; to produce the &lt; and &gt;
     * characters. You can use a block element such as &lt;p&gt; or &lt;br&gt; to 
     * force each component to be rendered on a separate line. If the 
     * separator attribute is not specified, the components are rendered with a single
     * space between them.</p>
     */
    @Property(name = "separator", displayName = "Separator", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String separator = null;

    /**
     * <p> The string of characters or HTML element that should be inserted between each
     * component that is a child of this component.  To specify an HTML element,
     * use the character entities &amp;lt; and &amp;gt; to produce the &lt; and &gt;
     * characters. You can use a block element such as &lt;p&gt; or &lt;br&gt; to 
     * force each component to be rendered on a separate line. If the 
     * separator attribute is not specified, the components are rendered with a single
     * space between them.</p>
     */
    public String getSeparator() {
        if (this.separator != null) {
            return this.separator;
        }
        ValueExpression _vb = getValueExpression("separator");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p> The string of characters or HTML element that should be inserted between each
     * component that is a child of this component.  To specify an HTML element,
     * use the character entities &amp;lt; and &amp;gt; to produce the &lt; and &gt;
     * characters. You can use a block element such as &lt;p&gt; or &lt;br&gt; to 
     * force each component to be rendered on a separate line. If the 
     * separator attribute is not specified, the components are rendered with a single
     * space between them.</p>
     * @see #getSeparator()
     */
    public void setSeparator(String separator) {
        this.separator = separator;
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
        this.block = ((Boolean) _values[1]).booleanValue();
        this.block_set = ((Boolean) _values[2]).booleanValue();
        this.separator = (String) _values[3];
        this.style = (String) _values[4];
        this.styleClass = (String) _values[5];
        this.visible = ((Boolean) _values[6]).booleanValue();
        this.visible_set = ((Boolean) _values[7]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[8];
        _values[0] = super.saveState(_context);
        _values[1] = this.block ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.block_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.separator;
        _values[4] = this.style;
        _values[5] = this.styleClass;
        _values[6] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
