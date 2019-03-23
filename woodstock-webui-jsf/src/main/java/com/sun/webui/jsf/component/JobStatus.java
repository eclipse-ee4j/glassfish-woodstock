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
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * The JobStatus component is used to show the number of jobs currently running.
 */
@Component(type = "com.sun.webui.jsf.JobStatus", family = "com.sun.webui.jsf.JobStatus",
displayName = "Job Status", tagName = "jobStatus",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_job_status",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_job_status_props")
public class JobStatus extends ImageHyperlink {

    /**
     * Default constructor.
     */
    public JobStatus() {
        super();
        setRendererType("com.sun.webui.jsf.JobStatus");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.JobStatus";
    }

    @Override
    public String getIcon() {
        String icon = super.getIcon();

        if (icon == null) {
            icon = ThemeImages.MASTHEAD_STATUS_ICON;
        }

        return icon;
    }

    @Override
    public String getStyleClass() {
        String styleClass = super.getStyleClass();

        if (styleClass == null) {
            styleClass = ThemeStyles.MASTHEAD_PROGRESS_LINK;
        }
        Theme theme = ThemeUtilities.getTheme(
                FacesContext.getCurrentInstance());
        return theme.getStyleClass(styleClass);
    }

    @Override
    public boolean isDisabled() {
        if (getNumJobs() == 0) {
            // always disable the hyperlink when 0 jobs
            return true;
        }

        return super.isDisabled();
    }

    @Override
    public int getHeight() {
        return 17;
    }

    @Override
    public int getWidth() {
        return 17;
    }

    @Override
    public int getBorder() {
        return 0;
    }

    @Override
    public String getAlign() {
        return "middle";
    }

    @Override
    public String getAlt() {
        String alt = super.getAlt();

        if (alt == null) {
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
            alt = theme.getMessage("masthead.tasksRunningAltText");
        }
        return alt;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * <p>Specifies the position of the image with respect to its context.
     * Valid values are: bottom (the default); middle; top; left; right.</p>
     */
    @Property(name = "onDblClick", isHidden = false, isAttribute = true)
    @Override
    public String getOnDblClick() {
        return super.getOnDblClick();
    }

    // Hide textPosition
    @Property(name = "textPosition", isHidden = true, isAttribute = false)
    @Override
    public String getTextPosition() {
        return super.getTextPosition();
    }
    /**
     * <p>The number of currently executing jobs, displayed next to the job label.</p>
     */
    @Property(name = "numJobs", displayName = "Number of Jobs")
    private int numJobs = Integer.MIN_VALUE;
    private boolean numJobs_set = false;

    public int getNumJobs() {
        if (this.numJobs_set) {
            return this.numJobs;
        }
        ValueExpression _vb = getValueExpression("numJobs");
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
     * <p>The number of currently executing jobs, displayed next to the job label.</p>
     * @see #getNumJobs()
     */
    public void setNumJobs(int numJobs) {
        this.numJobs = numJobs;
        this.numJobs_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.numJobs = ((Integer) _values[1]).intValue();
        this.numJobs_set = ((Boolean) _values[2]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[3];
        _values[0] = super.saveState(_context);
        _values[1] = new Integer(this.numJobs);
        _values[2] = this.numJobs_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
