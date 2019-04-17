/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
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

// Toggle the filter panel from the filter menu.
//
// If the "Custom Filter" option has been selected, the filter panel is 
// toggled. In this scenario, false is returned indicating the onChange event,
// generated by the filter menu, should not be allowed to continue.
// 
// If the "Custom Filter Applied" option has been selected, no action is taken.
// Instead, the filter menu is reverted back to the original selection. In this
// scenario, false is also returned indicating the onChange event, generated by
// the filter menu, should not be allowed to continue.
//
// For all other selections, true is returned indicating the onChange event, 
// generated by the filter menu, should be allowed to continue.
function filterMenuChanged() {
    var table = document.getElementById("form1:table1");
    return table.filterMenuChanged();
}

// Use this function to toggle the filter panel open or closed. This
// functionality requires the filterId of the table component to be set. In 
// addition, the selected value must be set as well to restore the default
// selected value when the embedded filter panel is closed.
function toggleFilterPanel() {
    var table = document.getElementById("form1:table1");
    table.toggleFilterPanel();
}

//-->
