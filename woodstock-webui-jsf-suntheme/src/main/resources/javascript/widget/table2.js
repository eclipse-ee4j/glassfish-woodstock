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

dojo.provide("webui.suntheme.widget.table2");

dojo.require("dojo.widget.*");
dojo.require("webui.suntheme.*");
dojo.require("webui.suntheme.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.suntheme.widget.table2.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.suntheme.widget.table2 = function() {
    this.widgetType = "table2";
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set ids.
        if (this.id) {
            this.actionsContainer.id = this.id + "_actionsContainer";
            this.filterPanelContainer.id = this.id + "_filterPanelContainer";
            this.marginContainer.id = this.id + "_marginContainer";
            this.preferencesPanelContainer.id = this.id + "_preferencesPanelContainer";
            this.sortPanelContainer.id = this.id + "_sortPanelContainer";
            this.rowGroupsContainer.id = this.id + "_rowGroupsContainer";
            this.titleContainer.id = this.id + "_titleContainer";
            this.tableFooterContainer.id = this.id + "_tableFooterContainer";
        }

        // Set public functions.
        this.domNode.setProps = webui.suntheme.widget.table2.setProps;

        // Set private functions (private functions/props prefixed with "_").
        // TBD...

        // Set properties.
        this.domNode.setProps({
            actions: this.actions,
            filterText: this.filterText,
            id: this.id,
            rowGroups: this.rowGroups,
            templatePath: this.templatePath,
            title: this.title,
            width: this.width
        });
        return true;
    }
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>actions</li>
 *  <li>filterText</li>
 *  <li>id</li>
 *  <li>rowGroups</li>
 *  <li>title</li>
 *  <li>width</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.suntheme.widget.table2.setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Save properties for later updates.
    if (this._props) {
        Object.extend(this._props, props); // Override existing values, if any.
    } else {
        this._props = props;
    }

    // Set DOM node properties.
    webui.suntheme.widget.common.setCoreProperties(this, props);
    webui.suntheme.widget.common.setJavaScriptProperties(this, props);

    // Set container width.
    if (props.width) {
        this.style.width = this.width;
    }

    // Set widget properties.
    var widget = dojo.widget.byId(this.id);
    if (widget == null) {
        return false;
    }

    // Add title.
    if (props.title) {
        webui.suntheme.widget.common.addFragment(widget.titleContainer, props.title);
        webui.suntheme.common.setVisibleElement(widget.titleContainer, true);
    }

    // Add actions.
    if (props.actions) {
        webui.suntheme.widget.common.addFragment(widget.actionsContainer, props.actions);
        webui.suntheme.common.setVisibleElement(widget.actionsContainer, true);
    }

    // Add row groups.
    if (props.rowGroups) {
        widget.rowGroupsContainer.innerHTML = null; // Clear contents.
        for (var i = 0; i < props.rowGroups.length; i++) {
            var rowGroupsClone = widget.rowGroupsContainer;

            // Clone nodes.
            if (i + 1 < props.rowGroups.length) {
                rowGroupsClone = widget.rowGroupsContainer.cloneNode(true);
                widget.marginContainer.insertBefore(rowGroupsClone, widget.rowGroupsContainer);
            }
            webui.suntheme.widget.common.addFragment(rowGroupsClone, props.rowGroups[i], "last");
        }
    }
    return true;
}

dojo.inherits(webui.suntheme.widget.table2, dojo.widget.HtmlWidget);

//-->
