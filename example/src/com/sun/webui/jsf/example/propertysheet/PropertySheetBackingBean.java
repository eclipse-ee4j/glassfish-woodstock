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

package com.sun.webui.jsf.example.propertysheet;

import javax.faces.event.ValueChangeEvent;
import com.sun.webui.jsf.example.index.IndexBackingBean;

import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.model.UploadedFile;
import java.io.Serializable;

/**
 * Backing Bean for property sheet example.
 */
public class PropertySheetBackingBean implements Serializable {
    
    /** Creates a new instance of PropertySheetBackingBean */
    public PropertySheetBackingBean() {
    }
 
    // Holds value for jumpLinkChkBox property.
    private boolean jumpLinkChkBox = true;
    
    // Holds value for requiredLabelChkBox property.
    private boolean requiredLabelChkBox = true;
    
    // Holds value for jumpLink property.
    private boolean jumpLink = true;
    
    // Holds value for requiredlabel.
    private String requiredLabel = "true";
    
    /** 
     *  this method assigns value to jumpLinks 
     *  property of property sheet tag. 
     */
    public boolean getJumpLink() {
        
        return jumpLink;
        
    }
    
    /** 
     *  this method assigns value to requiredFields 
     *  property of property sheet tag. 
     */
    public String getRequiredLabel() {
        
        return requiredLabel;
    }
    
    /** Getter method for jumpLinkChkBox property. */
    public boolean getJumpLinkChkBox() {
             return jumpLinkChkBox;    
    }
    
    /** Getter method for requiredLabelChkBox property. */
    public boolean getRequiredLabelChkBox() {
           return requiredLabelChkBox;    
    }
    
    /** Setter method for jumpLinkChkBox property. */
    public void setJumpLinkChkBox(boolean jumpChkBox) {
           this.jumpLinkChkBox = jumpChkBox;    
    }
    
    /** Setter method for requiredLabelChkBox property. */
    public void setRequiredLabelChkBox(boolean requiredChkBox) {
           this.requiredLabelChkBox = requiredChkBox;    
    }
    
    /** valueChangelistener for checkbox that sets jumpLink property. */
    public void jumpMenulistener(ValueChangeEvent event) {
        
        Boolean newValue = (Boolean) event.getNewValue();
        if (newValue != null 
                          && newValue.booleanValue()) {
            jumpLink = true;
        } else {
            jumpLink = false;
        }
        
    }
    
    /** valueChangelistener for checkbox that sets requiredLabel property. */
    public void requiredValuelistener(ValueChangeEvent event) {
        
        Boolean newValue = (Boolean) event.getNewValue();
        if (newValue != null 
                          && newValue.booleanValue()) {
            requiredLabel = "true";
        } else {
            requiredLabel = "false";
        }
        
    }
 
    /**
     * Action handler when navigating to the main example index.
     */
    public String showExampleIndex() {
        
        jumpLinkChkBox = true;
        requiredLabelChkBox = true;
        jumpLink = true;
        requiredLabel = "true";
        return IndexBackingBean.INDEX_ACTION;
    }
}
