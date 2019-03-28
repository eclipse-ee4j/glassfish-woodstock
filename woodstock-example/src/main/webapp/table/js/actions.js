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

// Note: Do not use multiline comments below for TLD examples as renderer XML
// files shall be used to generate Javadoc. Embedding a "*/" in a Javadoc 
// comment cuases compile errors because it terminates the outer comment.

// Set disabled state of table actions. If a selection has been made, actions
// are enabled. If no selection has been made, actions are disabled.
// 
// Note: Use setTimeout when invoking this function. This will ensure that 
// checkboxes and radiobutton are selected immediately, instead of waiting for 
// the onClick event to complete. For example: 
//
// onClick="setTimeout('initAllRows(); disableActions()', 0)"
function disableActions() {
    require(['webui/suntheme/dropDown'], function(dropDown){
        // Disable table actions by default.
        var table = document.getElementById("form1:table1");
        var selections = table.getAllSelectedRowsCount(); // Hidden & visible selections.
        var disabled = (selections > 0) ? false : true;

        // Set disabled state for top actions.
        document.getElementById("form1:table1:actionsTop:action1").setDisabled(disabled);
        document.getElementById("form1:table1:actionsTop:action2").setDisabled(disabled);
        document.getElementById("form1:table1:actionsTop:action3").setDisabled(disabled);
        document.getElementById("form1:table1:actionsTop:action4").setDisabled(disabled);
        dropDown.setDisabled("form1:table1:actionsTop:moreActions", disabled);

        // Set disabled state for bottom actions.
        document.getElementById("form1:table1:actionsBottom:action1").setDisabled(disabled);
        document.getElementById("form1:table1:actionsBottom:action2").setDisabled(disabled);
        document.getElementById("form1:table1:actionsBottom:action3").setDisabled(disabled);
        document.getElementById("form1:table1:actionsBottom:action4").setDisabled(disabled);
        dropDown.setDisabled("form1:table1:actionsBottom:moreActions", disabled);
    });
}

//
// Use this function to confirm the number of selected components (i.e., 
// checkboxes or radiobuttons used to de/select rows of the table), affected by
// a delete action. This functionality requires the selectId property of the
// tableColumn component and hiddenSelectedRows property of the tableRowGroup
// component to be set.
// 
// If selections are hidden from view, the confirmation message indicates the
// number of selections not displayed in addition to the total number of
// selections. If selections are not hidden, the confirmation message indicates
// only the total selections.
function confirmDeleteSelectedRows() {
    var table = document.getElementById("form1:table1");
    return table.confirmDeleteSelectedRows();
}

// Use this function to confirm the number of selected components (i.e., 
// checkboxes or radiobuttons used to de/select rows of the table), affected by
// an action such as edit, archive, etc. This functionality requires the 
// selectId property of the tableColumn component and hiddenSelectedRows
// property of the tableRowGroup component to be set.
// 
// If selections are hidden from view, the confirmation message indicates the
// number of selections not displayed in addition to the total number of
// selections. If selections are not hidden, the confirmation message indicates
// only the total selections.
function confirmSelectedRows() {
    var table = document.getElementById("form1:table1");
    return table.confirmSelectedRows("\n\nArchive all selections?");
}

//-->
