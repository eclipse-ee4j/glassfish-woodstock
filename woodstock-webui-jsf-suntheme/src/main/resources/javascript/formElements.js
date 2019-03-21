/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
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

dojo.provide("webui.suntheme.formElements");

dojo.require("webui.suntheme.common");

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// button functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.suntheme.button name space.
 */
webui.suntheme.button = {
    /**
     * This function is used to initialize HTML element properties with the
     * following Object literals.
     *
     * <ul>
     *  <li>disabled</li>
     *  <li>icon</li>
     *  <li>id</li>
     *  <li>mini</li>
     *  <li>secondary</li>
     * </ul>
     *
     * Note: This is considered a private API, do not use.
     *
     * @param props Key-Value pairs of properties.
     */
    init: function(props) {
        if (props == null || props.id == null) {
            return false;
        }
        var domNode = document.getElementById(props.id);
        if (domNode == null) {
                return false;
        }

        // Save given properties with the DOM node for later updates.
        Object.extend(domNode, {
            icon: new Boolean(props.icon).valueOf(),
            id: props.id,
            isOneOfOurButtons: true,
            mini: new Boolean(props.mini).valueOf(),
            mydisabled: new Boolean(props.disabled).valueOf(),
            secondary: new Boolean(props.secondary).valueOf()
        });

        // Set style classes
        if (domNode.icon == true) {
            domNode.classNamePrimary = webui.suntheme.props.button.imageClassName;
            domNode.classNamePrimaryDisabled = webui.suntheme.props.button.imageDisabledClassName;
            domNode.classNamePrimaryHov = webui.suntheme.props.button.imageHovClassName;

            // Currently not used in theme.
            domNode.classNamePrimaryMini = "";
            domNode.classNamePrimaryMiniDisabled = "";
            domNode.classNamePrimaryMiniHov = "";
            domNode.classNameSecondary = "";
            domNode.classNameSecondaryDisabled = "";
            domNode.classNameSecondaryHov = "";
            domNode.classNameSecondaryMini = "";
            domNode.classNameSecondaryMiniDisabled = "";
            domNode.classNameSecondaryMiniHov = "";
        } else {
            domNode.classNamePrimary = webui.suntheme.props.button.primaryClassName;
            domNode.classNamePrimaryDisabled = webui.suntheme.props.button.primaryDisabledClassName;
            domNode.classNamePrimaryHov = webui.suntheme.props.button.primaryHovClassName;
            domNode.classNamePrimaryMini = webui.suntheme.props.button.primaryMiniClassName;
            domNode.classNamePrimaryMiniDisabled = webui.suntheme.props.button.primaryMiniDisabledClassName;
            domNode.classNamePrimaryMiniHov = webui.suntheme.props.button.primaryMiniHovClassName;
            domNode.classNameSecondary = webui.suntheme.props.button.secondaryClassName;
            domNode.classNameSecondaryDisabled = webui.suntheme.props.button.secondaryDisabledClassName;
            domNode.classNameSecondaryHov = webui.suntheme.props.button.secondaryHovClassName;
            domNode.classNameSecondaryMini = webui.suntheme.props.button.secondaryMiniClassName;
            domNode.classNameSecondaryMiniDisabled = webui.suntheme.props.button.secondaryMiniDisabledClassName;
            domNode.classNameSecondaryMiniHov = webui.suntheme.props.button.secondaryMiniHovClassName;
        }

        // Set functions
        domNode.isSecondary = webui.suntheme.button.isSecondary;
        domNode.setSecondary = webui.suntheme.button.setSecondary;
        domNode.isPrimary = webui.suntheme.button.isPrimary;
        domNode.setPrimary = webui.suntheme.button.setPrimary;
        domNode.isMini = webui.suntheme.button.isMini;
        domNode.setMini = webui.suntheme.button.setMini;
        domNode.getDisabled = webui.suntheme.button.getDisabled;
        domNode.setDisabled = webui.suntheme.button.setDisabled;
        domNode.getVisible = webui.suntheme.button.getVisible;
        domNode.setVisible = webui.suntheme.button.setVisible;
        domNode.getText = webui.suntheme.button.getText;
        domNode.setText = webui.suntheme.button.setText;
        domNode.doClick = webui.suntheme.button.click;
        domNode.myonblur = webui.suntheme.button.onblur;
        domNode.myonfocus = webui.suntheme.button.onfocus;
        domNode.myonmouseover = webui.suntheme.button.onmouseover;
        domNode.myonmouseout = webui.suntheme.button.onmouseout;

        // Set button state.
        domNode.setDisabled(domNode.mydisabled);
        domNode.setSecondary(domNode.secondary);
        domNode.setMini(domNode.mini);
    },

    /**
     * Simulate a mouse click in a button. 
     *
     * @return true if successful; otherwise, false
     */
    click: function() {
        this.click()
        return true;
    },

    /**
     * Get the textual label of a button. 
     *
     * @return The element value or null
     */
    getText: function() {
        return this.value;
    },

    /**
     * Set the textual label of a button. 
     *
     * @param text The element value
     * @return true if successful; otherwise, false
     */
    setText: function(text) {
        if (text == null) {
            return false;
        }

        this.value = text;
        return true;
    },

    /**
     * Use this function to show or hide a button. 
     *
     * @param show true to show the element, false to hide the element
     * @return true if successful; otherwise, false
     */
    setVisible: function(show) {
        if (show == null) {
            return false;
        }
        // Get element.
        webui.suntheme.common.setVisibleElement(this, show);

        return true;
    },

    /**
     * Use this function to find whether or not this is visible according to our
     * spec.
     *
     * @return true if visible; otherwise, false
     */
    getVisible: function() {
         // Get element.
        styles = webui.suntheme.common.splitStyleClasses(this);
        if (styles == null) {
            return true;
        }
        return !webui.suntheme.common.checkStyleClasses(styles,
            webui.suntheme.props.hiddenClassName);
    },

    /**
     * Test if button is set as "primary".
     *
     * @return true if primary; otherwise, false for secondary
     */
    isPrimary: function() {
        return !this.isSecondary();
    },

    /**
     * Set button as "primary".
     *
     * @param primary true for primary, false for secondary
     * @return true if successful; otherwise, false
     */
    setPrimary: function(primary) {
        return this.setSecondary(!primary);
    },

    /**
     * Test if button is set as "secondary".
     *
     * @return true if secondary; otherwise, false for primary
     */
    isSecondary: function() {
        return this.secondary;
    },

    /**
     * Set button as "secondary".
     *
     * @param secondary true for secondary, false for primary
     * @return true if successful; otherwise, false
     */
    setSecondary: function(secondary) {
        if (secondary == null || this.mydisabled) {
            return false;
        }
        var stripType;
        var stripTypeHov;
        var newType;

        if (this.secondary == false && secondary == true) {
            //change from primary to secondary
            if (this.mini) {
                stripTypeHov = this.classNamePrimaryMiniHov;
                stripType = this.classNamePrimaryMini;
                newType = this.classNameSecondaryMini;
            } else {
                stripTypeHov = this.classNamePrimaryHov;
                stripType = this.classNamePrimary;
                hovType = this.classNameSecondaryHov;
                newType = this.classNameSecondary;
            }
        } else if (this.secondary == true && secondary == false) {
            //change from secondary to primary
            if (this.mini) {
                //this is currently a mini button
                stripTypeHov = this.classNameSecondaryMiniHov;
                stripType = this.classNameSecondaryMini;
                newType = this.classNamePrimaryMini;
            } else {
                stripTypeHov = this.classNameSecondaryHov;
                stripType = this.classNameSecondary;
                newType = this.classNamePrimary;
            }
        } else {
            // don't need to do anything
            return false;
        }
        webui.suntheme.common.stripStyleClass(this, stripTypeHov);
        webui.suntheme.common.stripStyleClass(this, stripType);
        webui.suntheme.common.addStyleClass(this, newType);
        this.secondary=secondary;
        return this.secondary;
    },

    /**
     * Test if button is set as "mini".
     *
     * @return true if mini; otherwise, false
     */
    isMini: function() {
        return this.mini;
    },

    /**
     * Set button as "mini".
     *
     * @param mini true for mini, false for standard button
     * @return true if successful; otherwise, false
     */
    setMini: function(mini) {
        if (mini == null || this.mydisabled) {
            return false;
        }
        var stripType;
        var stripTypeHov;
        var newType;
        if (this.mini == true && mini == false) {
            //change from mini to nonmini
            if (!this.secondary) {
                //this is currently a primary button
                stripTypeHov = this.classNamePrimaryMiniHov;
                stripType = this.classNamePrimaryMini;
                newType = this.classNamePrimary;
            } else {
                stripTypeHov = this.classNameSecondaryMiniHov;
                stripType = this.classNameSecondaryMini;
                newType = this.classNameSecondary;
            }
        } else if (this.mini == false && mini == true) {
            if (!this.secondary) {
                //this is currently a primary button
                stripTypeHov = this.classNamePrimaryHov;
                stripType = this.classNamePrimary;
                newType = this.classNamePrimaryMini;
            } else {
                stripTypeHov = this.classNameSecondaryHov;
                stripType = this.classNameSecondary;
                newType = this.classNameSecondaryMini;
            }
        } else {
            // don't need to do anything
            return false;
        }
        webui.suntheme.common.stripStyleClass(this, stripTypeHov);
        webui.suntheme.common.stripStyleClass(this, stripType);
        webui.suntheme.common.addStyleClass(this, newType);
        this.mini = mini;
        return this.mini;
    },

    /**
     * Test disabled state of button.
     *
     * @return true if disabled; otherwise, false
     */
    getDisabled: function() {
        return this.mydisabled;
    },

    /**
     * Test disabled state of button.
     *
     * @param disabled true if disabled; otherwise, false
     * @return true if successful; otherwise, false
     */
    setDisabled: function(disabled) {
        if (disabled == null || this.mydisabled == disabled) {
            return false;
        }
        var stripType;
        var stripHovType;
        var newType;
        if (!this.secondary) {
            //this is currently a primary button
            if (this.mini) {
                if (disabled == false) {
                    stripType = this.classNamePrimaryMiniDisabled;
                    stripHovType = this.classNamePrimaryMiniDisabled;
                    newType = this.classNamePrimaryMini;
                } else {
                    stripType = this.classNamePrimaryMini;
                    stripHovType = this.classNamePrimaryMiniHov;
                    newType = this.classNamePrimaryMiniDisabled;
                }
            } else { // not mini
                if (disabled == false) {
                    stripType = this.classNamePrimaryDisabled;
                    stripHovType = this.classNamePrimaryDisabled;
                    newType = this.classNamePrimary;
                } else {
                    stripType = this.classNamePrimary;
                    stripHovType = this.classNamePrimaryHov;
                    newType = this.classNamePrimaryDisabled;
                }
            }
        } else {
            //this is currently a secondary button
            if (this.mini) {
                if (disabled == false) {
                    stripType = this.classNameSecondaryMiniDisabled;
                    stripHovType = this.classNameSecondaryMiniDisabled;
                    newType = this.classNameSecondaryMini;
                } else {
                    stripType = this.classNameSecondaryMini;
                    stripHovType = this.classNameSecondaryMiniHov;
                    newType = this.classNameSecondaryMiniDisabled;
                }
            } else { // not mini
                if (disabled == false) {
                    stripType = this.classNameSecondaryDisabled;
                    stripHovType = this.classNameSecondaryDisabled;
                    newType = this.classNameSecondary;
                } else {
                    stripType = this.classNameSecondary;
                    stripHovType = this.classNameSecondaryHov;
                    newType = this.classNameSecondaryDisabled;
                }
            }
        }
        webui.suntheme.common.stripStyleClass(this, stripHovType);
        webui.suntheme.common.stripStyleClass(this, stripType);
        webui.suntheme.common.addStyleClass(this, newType);
        this.mydisabled = disabled;
        this.disabled = disabled;
        return true;
    },

    /**
     * Set CSS styles for onblur event.
     *
     * @return true if successful; otherwise, false
     */
    onblur: function() {
        if (this.mydisabled == true) {
            return true;
        }
        var stripType;
        var newType;
        if (!this.secondary) {
            if (this.mini) {
                stripType = this.classNamePrimaryMiniHov;
                newType = this.classNamePrimaryMini;        
            } else {
                stripType = this.classNamePrimaryHov;
                newType = this.classNamePrimary;        
            }
        } else { //is secondary 
            if (this.mini) {
                stripType = this.classNameSecondaryMiniHov;
                newType = this.classNameSecondaryMini;        
            } else {
                stripType = this.classNameSecondaryHov;
                newType = this.classNameSecondary;        
            }
        }
        // This code can generate a JavaScript error if the cursor quickly moves
        // off the button while the page is being refreshed.
        try {
            webui.suntheme.common.stripStyleClass(this, stripType);
            webui.suntheme.common.addStyleClass(this, newType);
        } catch (e) {}
        return true;
    },

    /**
     * Set CSS styles for onmouseout event.
     *
     * @return true if successful; otherwise, false
     */
    onmouseout: function() {
        if (this.mydisabled == true) {
            return true;
        }

        var stripType;
        var newType;
        if (!this.secondary) {
            if (this.mini) {
                stripType = this.classNamePrimaryMiniHov;
                newType = this.classNamePrimaryMini;        
            } else {
                stripType = this.classNamePrimaryHov;
                newType = this.classNamePrimary;        
            }
        } else { //is secondary 
            if (this.mini) {
                stripType = this.classNameSecondaryMiniHov;
                newType = this.classNameSecondaryMini;        
            } else {
                stripType = this.classNameSecondaryHov;
                newType = this.classNameSecondary;        
            }
        }
        // This code can generate a JavaScript error if the cursor quickly moves
        // off the button while the page is being refreshed.
        try {
            webui.suntheme.common.stripStyleClass(this, stripType);
            webui.suntheme.common.addStyleClass(this, newType);
        } catch (e) {}
        return true;
    },

    /**
     * Set CSS styles for onfocus event.
     *
     * @return true if successful; otherwise, false
     */
    onfocus: function() {
        if (this.mydisabled == true) {
            return true;
        }
        var stripType;
        var newType;
        if (!this.secondary) {
            if (this.mini) {
                stripType = this.classNamePrimaryMini;
                newType = this.classNamePrimaryMiniHov;        
            } else {
                stripType = this.classNamePrimary;
                newType = this.classNamePrimaryHov;        
            }
        } else { //is secondary 
            if (this.mini) {
                stripType = this.classNameSecondaryMini;
                newType = this.classNameSecondaryMiniHov;        
            } else {
                stripType = this.classNameSecondary;
                newType = this.classNameSecondaryHov;        
            }
        }
        // This code can generate a JavaScript error if the cursor quickly moves
        // off the button while the page is being refreshed.
        try {
            webui.suntheme.common.stripStyleClass(this, stripType);
            webui.suntheme.common.addStyleClass(this, newType);
        } catch (e) {}
        return true;
    },

    /**
     * Set CSS styles for onmouseover event.
     *
     * @return true if successful; otherwise, false
     */
    onmouseover: function() {
        if (this.mydisabled == true) {
            return false;
        }
        var stripType;
        var newType;
        if (!this.secondary) {
            if (this.mini) {
                stripType = this.classNamePrimaryMini;
                newType = this.classNamePrimaryMiniHov;        
            } else {
                stripType = this.classNamePrimary;
                newType = this.classNamePrimaryHov;        
            }
        } else { //is secondary 
            if (this.mini) {
                stripType = this.classNameSecondaryMini;
                newType = this.classNameSecondaryMiniHov;        
            } else {
                stripType = this.classNameSecondary;
                newType = this.classNameSecondaryHov;        
            }
        }
        // This code can generate a JavaScript error if the cursor quickly moves
        // off the button while the page is being refreshed.
        try {
            webui.suntheme.common.stripStyleClass(this, stripType);
            webui.suntheme.common.addStyleClass(this, newType);
        } catch (e) {}
        return true;
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// checkbox functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.suntheme.checkbox name space.
 */
webui.suntheme.checkbox = {
    /**
     * Set the disabled state for the given checkbox element Id. If the disabled 
     * state is set to true, the element is shown with disabled styles.
     *
     * @param elementId The element Id
     * @param disabled true or false
     * @return true if successful; otherwise, false
     */
    setDisabled: function(elementId, disabled) {
        return webui.suntheme.rbcb.setDisabled(elementId, disabled,
            "checkbox", "Cb", "CbDis");
    },

    /** 
     * Set the disabled state for all the checkboxes in the check box
     * group identified by controlName. If disabled
     * is set to true, the check boxes are shown with disabled styles.
     *
     * @param controlName The checkbox group control name
     * @param disabled true or false
     * @return true if successful; otherwise, false
     */
    setGroupDisabled: function(controlName, disabled) {    
        return webui.suntheme.rbcb.setGroupDisabled(controlName,
            disabled, "checkbox", "Cb", "CbDis");
    },

    /**
     * Set the checked property for a checkbox with the given element Id.
     *
     * @param elementId The element Id
     * @param checked true or false
     * @return true if successful; otherwise, false
     */
    setChecked: function(elementId, checked) {
        return webui.suntheme.rbcb.setChecked(elementId, checked,
            "checkbox");
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// dropdown functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.suntheme.dropdown name space.
 */
webui.suntheme.dropDown = {
    /**
     * Use this function to access the HTML select element that makes up
     * the dropDown.
     *
     * @param elementId The component id of the JSF component (this id is
     * assigned to the span tag enclosing the HTML elements that make up
     * the dropDown).
     * @return a reference to the select element. 
     */
    getSelectElement: function(elementId) { 
        var element = document.getElementById(elementId); 
        if(element != null) { 
            if(element.tagName == "SELECT") { 
                return element; 
            } 
        } 
        return document.getElementById(elementId + "_list");
    },

    /**
     * This function is invoked by the choice onselect action to set the
     * selected, and disabled styles.
     *
     * Page authors should invoke this function if they set the 
     * selection using JavaScript.
     *
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return true if successful; otherwise, false
     */
    changed: function(elementId) {         
        var listItem = webui.suntheme.dropDown.getSelectElement(elementId).options;

        //disabled items should not be selected (IE problem)
        //So setting selectedIndex = -1 for disabled items.
        
        if (webui.suntheme.common.browser.is_ie) { 
          for(var i = 0;i < listItem.length;++i) {
              if(listItem[i].disabled == true &&
                           listItem[i].selected == true) {

               listItem.selectedIndex = -1;
              }
          }  
        }        
  
        for (var cntr=0; cntr < listItem.length; ++cntr) { 
            if (listItem[cntr].className == webui.suntheme.props.dropDown.optionSeparatorClassName
                    || listItem[cntr].className == webui.suntheme.props.dropDown.optionGroupClassName) {
                continue;	
            } else if (listItem[cntr].disabled) {
                // Regardless if the option is currently selected or not,
                // the disabled option style should be used when the option
                // is disabled. So, check for the disabled item first.
                // See CR 6317842.
                listItem[cntr].className = webui.suntheme.props.dropDown.optionDisabledClassName;
            } else if (listItem[cntr].selected) {
                listItem[cntr].className = webui.suntheme.props.dropDown.optionSelectedClassName;
            } else {
                // This does not work on Opera 7. There is a bug such that if 
                // you touch the option at all (even if I explicitly set
                // selected to false!), it goes back to the original
                // selection. 
                listItem[cntr].className = webui.suntheme.props.dropDown.optionClassName;
            }
        }
        return true;
    },

    /**
     * Set the disabled state for given dropdown element Id. If the disabled 
     * state is set to true, the element is shown with disabled styles.
     *
     * Page authors should invoke this function if they dynamically
     * enable or disable a dropdown using JavaScript.
     * 
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @param disabled true or false
     * @return true if successful; otherwise, false
     */
    setDisabled: function(elementId, disabled) { 
        var choice = webui.suntheme.dropDown.getSelectElement(elementId); 
        if(disabled) {
            choice.disabled = true;
            choice.className = webui.suntheme.props.dropDown.disabledClassName;
        } else { 
            choice.disabled = false;
            choice.className = webui.suntheme.props.dropDown.className;
        }
        return true;
    },

    /**
     * Invoke this JavaScript function to get the value of the first
     * selected option on the dropDown. If no option is selected, this
     * function returns null. 
     *
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return The value of the selected option, or null if none is
     * selected. 
     */
    getSelectedValue: function(elementId) { 
        var dropDown = webui.suntheme.dropDown.getSelectElement(elementId); 
        var index = dropDown.selectedIndex; 
        if(index == -1) { 
            return null; 
        } else { 
            return dropDown.options[index].value; 
        }
    },

    /**
     * Invoke this JavaScript function to get the label of the first
     * selected option on the dropDown. If no option is selected, this
     * function returns null.
     * 
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return The label of the selected option, or null if none is
     * selected. 
     */
    getSelectedLabel: function(elementId) { 
        var dropDown = webui.suntheme.dropDown.getSelectElement(elementId); 
        var index = dropDown.selectedIndex; 
        if(index == -1) { 
            return null; 
        } else { 
            return dropDown.options[index].label; 
        }
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// field functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.suntheme.field name space.
 */
webui.suntheme.field = {
    /**
     * Use this function to get the HTML input or textarea element
     * associated with a TextField, PasswordField, HiddenField or TextArea
     * component. 
     * @param elementId The element ID of the field 
     * @return the input or text area element associated with the field
     * component 
     */
    getInputElement: function(elementId) { 
        var element = document.getElementById(elementId); 
        if(element != null) { 
            if(element.tagName == "INPUT") { 
                return element; 
            }
            if(element.tagName == "TEXTAREA") { 
                return element; 
            } 
        } 
        return document.getElementById(elementId + "_field");
    },

    /**
     * Use this function to get the value of the HTML element 
     * corresponding to the Field component
     * @param elementId The element ID of the Field component
     * @return the value of the HTML element corresponding to the 
     * Field component 
     */
    getValue: function(elementId) { 
        return webui.suntheme.field.getInputElement(elementId).value; 
    },

    /**
     * Use this function to set the value of the HTML element 
     * corresponding to the Field component
     * @param elementId The element ID of the Field component
     * @param newStyle The new value to enter into the input element
     * Field component 
     */
    setValue: function(elementId, newValue) { 
        webui.suntheme.field.getInputElement(elementId).value = newValue;
    },

    /** 
     * Use this function to get the style attribute for the field. 
     * The style retrieved will be the style on the span tag that 
     * encloses the (optional) label element and the input element. 
     * @param elementId The element ID of the Field component
     */
    getStyle: function(elementId) { 
        return webui.suntheme.field.getInputElement(elementId).style; 
    },

    /**
     * Use this function to set the style attribute for the field. 
     * The style will be set on the <span> tag that surrounds the field. 
     * @param elementId The element ID of the Field component
     * @param newStyle The new style to apply
     */
    setStyle: function(elementId, newStyle) { 
        webui.suntheme.field.getInputElement(elementId).style = newStyle; 
    },

    /**
     * Use this function to disable or enable a field. As a side effect
     * changes the style used to render the field. 
     *
     * @param elementId The element ID of the field 
     * @param show true to disable the field, false to enable the field
     * @return true if successful; otherwise, false
     */
    setDisabled: function(elementId, disabled) {  
        if (elementId == null || disabled == null) {
            // must supply an elementId && state
            return false;
        }
        var textfield = webui.suntheme.field.getInputElement(elementId); 
        if (textfield == null) {
            return false;
        }
        var newState = new Boolean(disabled).valueOf();    
        var isTextArea = textfield.className.indexOf(
            webui.suntheme.props.field.textAreaClassName) > -1;
        if (newState) { 
            if(isTextArea) {
                textfield.className = webui.suntheme.props.field.areaDisabledClassName;
            } else {
                textfield.className = webui.suntheme.props.field.fieldDisabledClassName;
            }
        } else {
            if(isTextArea) {
                textfield.className = webui.suntheme.props.field.areaClassName;
            } else {
                textfield.className = webui.suntheme.props.field.fieldClassName;
            }
        }
        textfield.disabled = newState;
        return true;
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// hyperlink functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.suntheme.hyperlink name space.
 */
webui.suntheme.hyperlink = {
    /**
     * This function is used to submit a hyperlink.
     *
     * @params hyperlink The hyperlink element
     * @params formId The form id
     * @params params Name value pairs
     */
    submit: function(hyperlink, formId, params) {
        //params are name value pairs but all one big string array
        //so params[0] and params[1] form the name and value of the first param
//        var theForm = document.getElementById(formId);
//	var oldTarget = theForm.target;
//        var oldAction = theForm.action;
//        theForm.action += "?" + hyperlink.id + "_submittedField="+hyperlink.id; 
//        if (params != null) {
//            for (var i = 0; i < params.length; i++) {
//             theForm.action +="&" + params[i] + "=" + params[i+1]; 
//                i++;
//            }
//        }
//        if (hyperlink.target != null) {
//            theForm.target = hyperlink.target;
//        }
//        theForm.submit();
//        // Fix for CR 6469040 - Hyperlink:Does not work correctly in
//        // frames environment. 
//	if (hyperlink.target != null) {
//	    theForm.target = oldTarget;
//            theForm.action = oldAction;
//        }
        return false;
    },
	
    
   /**
     * Use this function to access the HTML img element that makes up
     * the icon hyperlink. 
     *
     * @param elementId The component id of the JSF component (this id is
     * assigned to the outter most tag enclosing the HTML img element).
     * @return a reference to the img element. 
     */
    getImgElement: function(elementId) {
        // Image hyperlink is now a naming container and the img element id 
        // includes the ImageHyperlink parent id.
        if (elementId != null) {
            var parentid = elementId;
            var colon_index = elementId.lastIndexOf(":");
            if (colon_index != -1) {
                parentid = elementId.substring(colon_index + 1);
            }
            return document.getElementById(elementId + ":" + parentid + "_image");
        }
        return document.getElementById(elementId + "_image");
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// jumpDropDown functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.suntheme.jumpdropdown name space.
 */
webui.suntheme.jumpDropDown = {
    /**
     * This function is invoked by the jumpdropdown onchange action to set the
     * form action and then submit the form.
     *
     * Page authors should invoke this function if they set the selection using 
     * JavaScript.
     *
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return true
     */
    changed: function(elementId) {
        var jumpDropdown = webui.suntheme.dropDown.getSelectElement(elementId); 
        var form = jumpDropdown; 
        while(form != null) { 
            form = form.parentNode; 
            if(form.tagName == "FORM") { 
                break;
            }
        }
        if(form != null) { 
            var submitterFieldId = elementId + "_submitter"; 
            document.getElementById(submitterFieldId).value = "true"; 

            var listItem = jumpDropdown.options;
            for (var cntr=0; cntr < listItem.length; ++cntr) { 
                if (listItem[cntr].className ==
                            webui.suntheme.props.jumpDropDown.optionSeparatorClassName
                        || listItem[cntr].className == 
                            webui.suntheme.props.jumpDropDown.optionGroupClassName) {
                    continue;		
                } else if (listItem[cntr].disabled) {
                    // Regardless if the option is currently selected or not,
                    // the disabled option style should be used when the option
                    // is disabled. So, check for the disabled item first.
                    // See CR 6317842.
                    listItem[cntr].className = webui.suntheme.props.jumpDropDown.optionDisabledClassName;
                } else if (listItem[cntr].selected) {
                    listItem[cntr].className = webui.suntheme.props.jumpDropDown.optionSelectedClassName;
                } else { 
                    listItem[cntr].className = webui.suntheme.props.jumpDropDown.optionClassName;
                }
            }
            form.submit();
        }
        return true; 
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// listbox functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.suntheme.listbox name space.
 */
webui.suntheme.listbox = {
    /**
     * Use this function to access the HTML select element that makes up
     * the list. 
     *
     * @param elementId The component id of the JSF component (this id is
     * assigned to the span tag enclosing the HTML elements that make up
     * the list).
     * @return a reference to the select element. 
     */
    getSelectElement: function(elementId) { 
        var element = document.getElementById(elementId); 
        if(element != null) { 
            if(element.tagName == "SELECT") { 
                return element; 
            }
        }
        return document.getElementById(elementId + "_list");
    },

    /**
     * This function is invoked by the list onselect action to set the selected, 
     * and disabled styles.
     *
     * Page authors should invoke this function if they set the selection
     * using JavaScript.
     *
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return true if successful; otherwise, false
     */
    changed: function(elementId) { 
        var cntr = 0; 
        var listItem = webui.suntheme.listbox.getSelectElement(elementId).options;

        //disabled items should not be selected (IE problem)
        //So setting selectedIndex = -1 for disabled selected items.
    
        if(webui.suntheme.common.browser.is_ie) {
            for(var i = 0;i < listItem.length;++i) {
               if(listItem[i].disabled == true && 
                            listItem[i].selected == true ) {
                
                  listItem.selectedIndex = -1;
            
               }
            }
        }    
        while(cntr < listItem.length) { 
            if(listItem[cntr].selected) {
                listItem[cntr].className = webui.suntheme.props.listbox.optionSelectedClassName;
            } else if(listItem[cntr].disabled) {
                listItem[cntr].className = webui.suntheme.props.listbox.optionDisabledClassName;
            } else {
                // This does not work on Opera 7. There is a bug such that if 
                // you touch the option at all (even if I explicitly set
                // selected to false!), it goes back to the original
                // selection. 
                listItem[cntr].className = webui.suntheme.props.listbox.optionClassName;
            }
            ++ cntr;
        }
        return true;
    },

    /**
     * Invoke this JavaScript function to set the enabled/disabled state
     * of the listbox component. In addition to disabling the list, it
     * also changes the styles used when rendering the component. 
     *
     * Page authors should invoke this function if they dynamically
     * enable or disable a list using JavaScript.
     * 
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @param disabled true or false
     * @return true if successful; otherwise, false
     */
    setDisabled: function(elementId, disabled) {
        var listbox = webui.suntheme.listbox.getSelectElement(elementId); 
        var regular = webui.suntheme.props.listbox.className;
        var _disabled = webui.suntheme.props.listbox.disabledClassName;

        if(listbox.className.indexOf(webui.suntheme.props.listbox.monospaceClassName) > 1) {
            regular = webui.suntheme.props.listbox.monospaceClassName;
            _disabled = webui.suntheme.props.listbox.monospaceDisabledClassName;
        }
        if(disabled) {
            listbox.disabled = true;
            listbox.className = _disabled;
        } else {
            listbox.disabled = false;
            listbox.className = regular;
        }
        return true;
    },

    /**
     * Invoke this JavaScript function to get the value of the first
     * selected option on the listbox. If no option is selected, this
     * function returns null. 
     * 
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return The value of the selected option, or null if none is
     * selected. 
     */
    getSelectedValue: function(elementId) { 
        var listbox = webui.suntheme.listbox.getSelectElement(elementId); 
        var index = listbox.selectedIndex; 
        if(index == -1) { 
            return null; 
        } else { 
            return listbox.options[index].value; 
        }
    },

    /**
     * Invoke this JavaScript function to get the label of the first
     * selected option on the listbox. If no option is selected, this
     * function returns null. 
     * 
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return The label of the selected option, or null if none is selected. 
     */
    getSelectedLabel: function(elementId) {
        var listbox = webui.suntheme.listbox.getSelectElement(elementId); 
        var index = listbox.selectedIndex; 
        if(index == -1) { 
            return null; 
        } else { 
            return listbox.options[index].label; 
        }
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Generic checkbox and radio button functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.suntheme.upload name space.
 */
webui.suntheme.rbcb = {
    setChecked: function(elementId, checked, type) {
        if (elementId == null || type == null) {
            return false;
        }
        var rbcb = document.getElementById(elementId);
        if (rbcb == null) {
            return false;
        }
        // wrong type
        if (rbcb.type != type.toLowerCase()) {
            return false;
        }
        // Get boolean value to ensure correct data type.
        rbcb.checked = new Boolean(checked).valueOf();
        return true;
    },

    setDisabled: function(elementId, disabled, type, enabledStyle,
            disabledStyle) {
        if (elementId == null || disabled == null || type == null) {
            // must supply an elementId && state && type
            return false;
        }
        var rbcb = document.getElementById(elementId);
        if (rbcb == null) {
            // specified elementId not found
            return false;
        }
        // wrong type
        if (rbcb.type != type.toLowerCase()) {
            return false;
        }
        rbcb.disabled = new Boolean(disabled).valueOf();
        if (rbcb.disabled) {
            if (disabledStyle != null) {
                rbcb.className = disabledStyle;
            }
        } else {
            if (enabledStyle != null) {
                rbcb.className = enabledStyle;
            }
        }
        return true;
    },

    /** 
     * Set the disabled state for all radio buttons with the given controlName.
     * If disabled is set to true, the element is shown with disabled styles.
     *
     * @param elementId The element Id
     * @param formName The name of the form containing the element
     * @param disabled true or false
     * @return true if successful; otherwise, false
     */
    setGroupDisabled: function(controlName, disabled, type, enabledStyle,
            disabledStyle) {
        // Validate params.
        if (controlName == null) {
            return false;
        }
        if (disabled == null) {
            return false;
        }
        if (type == null) {
            return false;
        }

        // Get radiobutton group elements.
        var x = document.getElementsByName(controlName)
 
        // Set disabled state.
        for (var i = 0; i < x.length; i++) {
            // Get element.
            var element = x[i];
            if (element == null || element.name != controlName) {
                continue;
            }
            // Validate element type.
            if (element.type.toLowerCase() != type) {
                return false;
            }
            // Set disabled state.
            element.disabled = new Boolean(disabled).valueOf();

            // Set class attribute.
            if (element.disabled) {
                if (disabledStyle != null) {
                    element.className = disabledStyle;
                }
            } else {
                if (enabledStyle != null) {
                    element.className = enabledStyle;
                }
            }
        }
        return true;
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// radiobutton functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.suntheme.radiobutton name space.
 */
webui.suntheme.radiobutton = {
    /**
     * Set the disabled state for the given radiobutton element Id. If the disabled 
     * state is set to true, the element is shown with disabled styles.
     *
     * @param elementId The element Id
     * @param disabled true or false
     * @return true if successful; otherwise, false
     */
    setDisabled: function(elementId, disabled) {    
        return webui.suntheme.rbcb.setDisabled(elementId, disabled,
            "radio", "Rb", "RbDis");
    },

    /**
     * Set the disabled state for all the radio buttons in the radio button
     * group identified by controlName. If disabled
     * is set to true, the check boxes are displayed with disabled styles.
     *
     * @param controlName The radio button group control name
     * @param disabled true or false
     * @return true if successful; otherwise, false
     */
    setGroupDisabled: function(controlName, disabled) {    
        return webui.suntheme.rbcb.setGroupDisabled(controlName, 
            disabled, "radio", "Rb", "RbDis");
    },

    /**
     * Set the checked property for a radio button with the given element Id.
     *
     * @param elementId The element Id
     * @param checked true or false
     * @return true if successful; otherwise, false
     */
    setChecked: function(elementId, checked) {
        return webui.suntheme.rbcb.setChecked(elementId, checked,
            "radio");
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// upload functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.suntheme.upload name space.
 */
webui.suntheme.upload = {
    /**
     * Use this function to get the HTML input element associated with the
     * Upload component.  
     * @param elementId The element ID of the Upload
     * @return the input element associated with the Upload component 
     */
    getInputElement: function(elementId) { 
        var element = document.getElementById(elementId); 
        if(element.tagName == "INPUT") { 
            return element; 
        } 
        return document.getElementById(elementId + "_com.sun.webui.jsf.upload");
    },

    /**
     * Use this function to disable or enable a upload. As a side effect
     * changes the style used to render the upload. 
     *
     * @param elementId The element ID of the upload 
     * @param show true to disable the upload, false to enable the upload
     * @return true if successful; otherwise, false
     */
    setDisabled: function(elementId, disabled) {  
        if (elementId == null || disabled == null) {
            // must supply an elementId && state
            return false;
        }
        var input = webui.suntheme.upload.getInputElement(elementId); 
        if (input == null) {
            // specified elementId not found
            return false;
        }
        // Disable field using setDisabled function -- do not hard code styles here.
        return webui.suntheme.field.setDisabled(input.id, disabled);
    },

    setEncodingType: function(elementId) { 
        var upload = webui.suntheme.upload.getInputElement(elementId); 
        var form = upload; 
        while(form != null) { 
            form = form.parentNode; 
            if(form.tagName == "FORM") { 
                break; 
            } 
        }
        if(form != null) {
            // form.enctype does not work for IE, but works Safari
            // form.encoding works on both IE and Firefox, but does not work for Safari
            // form.enctype = "multipart/form-data";

            // convert all characters to lowercase to simplify testing
            var agent = navigator.userAgent.toLowerCase();
       
            if( agent.indexOf('safari') != -1) {
                // form.enctype works for Safari
                // form.encoding does not work for Safari
                form.enctype = "multipart/form-data"
            } else {
                // form.encoding works for IE, FireFox
                form.encoding = "multipart/form-data"
            }
        }
        return false;
    }
}

//-->
