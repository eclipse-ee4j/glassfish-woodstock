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

/*
 * $Id: WizardBranchSteps.java,v 1.1.20.1 2009-12-29 03:06:25 jyeary Exp $
 */
package com.sun.webui.jsf.component;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;
import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.event.WizardEventListener;

/**
 * The WizardBranchSteps component groups a sequence of 
 * {@link WizardStep WizardStep} children into a conditional branch.
 * This component has no visual representation in the wizard step list.
 * If this branch is taken, they steps appear as if they were
 * specified as children of the {@link Wizard Wizard} component.
 */
@Component(type = "com.sun.webui.jsf.WizardBranchSteps",
family = "com.sun.webui.jsf.WizardBranchSteps",
displayName = "WizardBranchSteps", tagName = "wizardBranchSteps",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_wizard_branch_steps",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_wizard_branch_steps_props")
public class WizardBranchSteps extends WizardStep implements NamingContainer {

    /**
     * Construct a new <code>WizardBranchSteps</code>.
     */
    public WizardBranchSteps() {
        super();
    }

    /**
     * Return the family for this component, <code>
     * com.sun.webui.jsf.WizardBranchSteps</code>.
     */
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.WizardBranchSteps";
    }

    //TODO remove unused code
    /*
     * Returns null.
     * The WizardRenderer is responsible for directly rendering a
     * WizardBranchSteps component during the rendering of a Wizard component.
    public String getRendererType() {
    return null;
    }
     */

    /*
     * Returns null.
     * The WizardRenderer is responsible for directly rendering a
     * WizardBranchSteps component during the rendering of a Wizard component.
    public String getRenderer() {
    return null;
    }
     */
    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name = "id")
    @Override
    public void setId(String id) {
        super.setId(id);
    }

    // Hide detail
    @Property(isHidden = true, isAttribute = false)
    @Override
    public String getDetail() {
        return super.getDetail();
    }

    // Hide eventListener
    @Property(isHidden = true, isAttribute = false)
    @Override
    public WizardEventListener getEventListener() {
        return super.getEventListener();
    }

    // Hide finish
    @Property(isHidden = true, isAttribute = false)
    @Override
    public boolean isFinish() {
        return super.isFinish();
    }

    // Hide help
    @Property(isHidden = true, isAttribute = false)
    @Override
    public String getHelp() {
        return super.getHelp();
    }

    // Hide onCancel
    @Property(isHidden = true, isAttribute = false)
    @Override
    public String getOnCancel() {
        return super.getOnCancel();
    }

    // Hide onClose
    @Property(isHidden = true, isAttribute = false)
    @Override
    public String getOnClose() {
        return super.getOnClose();
    }

    // Hide onFinish
    @Property(isHidden = true, isAttribute = false)
    @Override
    public String getOnFinish() {
        return super.getOnFinish();
    }

    // Hide onHelpTab
    @Property(isHidden = true, isAttribute = false)
    @Override
    public String getOnHelpTab() {
        return super.getOnHelpTab();
    }

    // Hide onNext
    @Property(isHidden = true, isAttribute = false)
    @Override
    public String getOnNext() {
        return super.getOnNext();
    }

    // Hide onPrevious
    @Property(isHidden = true, isAttribute = false)
    @Override
    public String getOnPrevious() {
        return super.getOnPrevious();
    }

    // Hide onStepLink
    @Property(isHidden = true, isAttribute = false)
    @Override
    public String getOnStepLink() {
        return super.getOnStepLink();
    }

    // Hide onStepsTab
    @Property(isHidden = true, isAttribute = false)
    @Override
    public String getOnStepsTab() {
        return super.getOnStepsTab();
    }

    // Hide results
    @Property(isHidden = true, isAttribute = false)
    @Override
    public boolean isResults() {
        return super.isResults();
    }

    // Hide summary
    @Property(isHidden = true, isAttribute = false)
    @Override
    public String getSummary() {
        return super.getSummary();
    }

    // Hide title
    @Property(isHidden = true, isAttribute = false)
    @Override
    public String getTitle() {
        return super.getTitle();
    }

    // taken
    /**
     * The taken attribute is used to evaluate whether the
     * steps of this branch sequence are displayed. If taken is true, 
     * the child components included in the <code>webuijsf:wizardStep</code>
     * tags within this <code>webuijsf:wizardBranchSteps</code> tag are
     * rendered. The taken attribute should be
     * a JavaServer Faces EL expression that uses the user's response in
     * a previous step to determine whether the branch sequence should be
     * rendered.
     */
    @Property(name = "taken")
    private boolean taken = false;
    private boolean taken_set = false;

    /**
     * The taken attribute is used to evaluate whether the
     * steps of this branch sequence are displayed. If taken is true, 
     * the child components included in the <code>webuijsf:wizardStep</code>
     * tags within this <code>webuijsf:wizardBranchSteps</code> tag are
     * rendered. The taken attribute should be
     * a JavaServer Faces EL expression that uses the user's response in
     * a previous step to determine whether the branch sequence should be
     * rendered.
     */
    public boolean isTaken() {
        if (this.taken_set) {
            return this.taken;
        }
        ValueExpression _vb = getValueExpression("taken");
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
     * The taken attribute is used to evaluate whether the
     * steps of this branch sequence are displayed. If taken is true, 
     * the child components included in the <code>webuijsf:wizardStep</code>
     * tags within this <code>webuijsf:wizardBranchSteps</code> tag are
     * rendered. The taken attribute should be
     * a JavaServer Faces EL expression that uses the user's response in
     * a previous step to determine whether the branch sequence should be
     * rendered.
     * @see #isTaken()
     */
    public void setTaken(boolean taken) {
        this.taken = taken;
        this.taken_set = true;
    }

    /**
     * Restore the state of this component.
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.taken = ((Boolean) _values[1]).booleanValue();
        this.taken_set = ((Boolean) _values[2]).booleanValue();
    }

    /**
     * Save the state of this component.
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[3];
        _values[0] = super.saveState(_context);
        _values[1] = this.taken ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.taken_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
