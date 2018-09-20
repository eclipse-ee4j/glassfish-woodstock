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

package com.sun.webui.jsf.component.customizers;

import com.sun.rave.designtime.Customizer2;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.rave.designtime.faces.FacesDesignProperty;
import com.sun.rave.designtime.faces.ResolveResult;
import com.sun.rave.designtime.impl.BasicCustomizer2;
import com.sun.webui.jsf.component.AddRemove;
import com.sun.webui.jsf.component.Selector;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.model.DefaultOptionsList;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionsList;
import com.sun.webui.jsf.model.MultipleSelectOptionsList;
import com.sun.webui.jsf.model.SingleSelectOptionsList;
import java.awt.Component;
import java.beans.PropertyChangeSupport;


public class OptionsListCustomizer extends BasicCustomizer2 {
    
    public static String ITEMS_PROP = "items"; //NOI18N
    public static String SELECTED_PROP = "selected"; //NOI18N
    public static String MULTIPLE_PROP = "multiple"; //NOI18N
    public static String OPTIONS_PROP = "options"; //NOI18N
    public static String SELECTEDVALUE_PROP = "selectedValue"; //NOI18N
    
    public OptionsListCustomizer() {
        super(OptionsListPanel.class, DesignMessageUtil.getMessage(OptionsListCustomizer.class,
                "OptionsListCustomizer.title"), null, "the-help-key"); // NOI18N
        setApplyCapable(true);
    }
    
    private OptionsListPanel optionsListPanel;
    private DesignBean designBean;
    private DesignBean optionsListBean;
    
    public Component getCustomizerPanel(DesignBean designBean) {
        this.setDisplayName(DesignMessageUtil.getMessage(OptionsListCustomizer.class,
                "OptionsListCustomizer.title") + " - " + designBean.getInstanceName());
        // If component does not have "items" or "selected" properties, or if
        // "items" is not bound to an instance of DefaultItemsList, return null.
        DesignProperty itemsProperty = designBean.getProperty(ITEMS_PROP);
        if (itemsProperty == null || !(itemsProperty instanceof FacesDesignProperty) || !((FacesDesignProperty) itemsProperty).isBound())
            return null;
        DesignProperty selectedProperty = designBean.getProperty(SELECTED_PROP);
        if (selectedProperty == null || !(selectedProperty instanceof FacesDesignProperty))
            return null;
        String expression = ((FacesDesignProperty) itemsProperty).getValueBinding().getExpressionString();
        ResolveResult resolveResult = ((FacesDesignContext)designBean.getDesignContext()).resolveBindingExprToBean(expression);
        if (resolveResult == null || resolveResult.getDesignBean() == null ||
                        !(OptionsList.class.isAssignableFrom(resolveResult.getDesignBean().getInstance().getClass())))
            return null;
        // Configure a new options editing panel. If compoment has a multiple property
        // make the panel an instance that accepts multiple choice. If compoment's selected
        // property is bound to the DefaultOptionList's selectedValues property, set
        // the panel's valueSelecting property to true. Set the panel's options and
        // selected values to the DefaultOptionsLists's current values.
        this.designBean = designBean;
        this.optionsListBean = resolveResult.getDesignBean();
        DesignProperty multipleProperty = designBean.getProperty(MULTIPLE_PROP);
        if (multipleProperty != null && optionsListBean.getInstance() instanceof DefaultOptionsList) {
            optionsListPanel = new OptionsListPanel(true);
            optionsListPanel.setMultipleChoice(((Boolean) multipleProperty.getValue()).booleanValue());
        } else if (optionsListBean.getInstance() instanceof MultipleSelectOptionsList) {
            optionsListPanel = new OptionsListPanel(true);
            optionsListPanel.setMultipleChoice(true);
        } else if (optionsListBean.getInstance() instanceof SingleSelectOptionsList){
            optionsListPanel = new OptionsListPanel(false);
        }
        optionsListPanel.setOptions((Option []) optionsListBean.getProperty(OPTIONS_PROP).getValue());
        optionsListPanel.setSelectedValues(optionsListBean.getProperty(SELECTEDVALUE_PROP).getValue());
        if (((FacesDesignProperty) selectedProperty).isBound())
            optionsListPanel.setValueSelecting(true);
        return optionsListPanel;
    }
    
    public boolean isModified() {
        return false;
    }
    
    public Result applyChanges() {
        if (optionsListBean == null)
            return Result.FAILURE;
        DesignProperty multipleProperty = designBean.getProperty(MULTIPLE_PROP);
        if (multipleProperty != null && optionsListBean.getInstance() instanceof DefaultOptionsList) {
            Boolean b = optionsListPanel.isMultipleChoice() ? Boolean.TRUE : Boolean.FALSE;
            multipleProperty.setValue(b);
            optionsListBean.getProperty(MULTIPLE_PROP).setValue(b);
        }
        optionsListBean.getProperty(OPTIONS_PROP).setValue(optionsListPanel.getOptions());
        optionsListBean.getProperty(SELECTEDVALUE_PROP).setValue(optionsListPanel.getSelectedValues());
        FacesDesignProperty selectedProperty = (FacesDesignProperty) designBean.getProperty(SELECTED_PROP);
        if (optionsListPanel.isValueSelecting()) {
            FacesDesignContext context = (FacesDesignContext) designBean.getDesignContext();
            designBean.getProperty(SELECTED_PROP).setValueSource(
                    context.getBindingExpr(optionsListBean, "." + SELECTEDVALUE_PROP));
        } else {
            designBean.getProperty(SELECTED_PROP).setValueSource(null);
        }
        return Result.SUCCESS;
    }
    
}
