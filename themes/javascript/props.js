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

//dojo.provide("webui.@THEME@.props");
define( function() {

/**
 * This closure is used to provide theme properties.
 */
    return {
    // Common properties.
    hiddenClassName: "hidden@THEME_CSS@", // Use webui.@THEME@.common.setVisibleElement

    // Button properties.
    button: {
        imageClassName: "Btn3@THEME_CSS@",
        imageDisabledClassName: "Btn3Dis@THEME_CSS@",
        imageHovClassName: "Btn3Hov@THEME_CSS@",
        primaryClassName: "Btn1@THEME_CSS@",
        primaryDisabledClassName: "Btn1Dis@THEME_CSS@",
        primaryHovClassName: "Btn1Hov@THEME_CSS@",
        primaryMiniClassName: "Btn1Mni@THEME_CSS@",
        primaryMiniHovClassName: "Btn1MniHov@THEME_CSS@",
        primaryMiniDisabledClassName: "Btn1MniDis@THEME_CSS@",
        secondaryClassName: "Btn2@THEME_CSS@",
        secondaryDisabledClassName: "Btn2Dis@THEME_CSS@",
        secondaryHovClassName: "Btn2Hov@THEME_CSS@",
        secondaryMiniClassName: "Btn2Mni@THEME_CSS@",
        secondaryMiniDisabledClassName: "Btn2MniDis@THEME_CSS@",
        secondaryMiniHovClassName: "Btn2MniHov@THEME_CSS@"
    },

    // Drop Down properties.
    dropDown: {
        className: "MnuStd@THEME_CSS@",
        disabledClassName: "MnuStdDis@THEME_CSS@",
        optionClassName: "MnuStdOpt@THEME_CSS@",
        optionDisabledClassName: "MnuStdOptDis@THEME_CSS@",
        optionGroupClassName: "MnuStdOptGrp@THEME_CSS@",
        optionSelectedClassName: "MnuStdOptSel@THEME_CSS@",
        optionSeparatorClassName: "MnuStdOptSep@THEME_CSS@"
    },

    // Field properties.
    field: {
        areaClassName: "TxtAra@THEME_CSS@",
        areaDisabledClassName: "TxtAraDis@THEME_CSS@",
        fieldClassName: "TxtFld@THEME_CSS@",
        fieldDisabledClassName: "TxtFldDis@THEME_CSS@"
    },

    // Jump drop down properties.
    jumpDropDown: {
        className: "MnuJmp@THEME_CSS@",
        disabledClassName: "",
        optionClassName: "MnuJmpOpt@THEME_CSS@",
        optionDisabledClassName: "MnuJmpOptDis@THEME_CSS@",
        optionGroupClassName: "MnuJmpOptGrp@THEME_CSS@",
        optionSelectedClassName: "MnuJmpOptSel@THEME_CSS@",
        optionSeparatorClassName: "MnuJmpOptSep@THEME_CSS@"
    },

    // Listbox properties.
    listbox: {
        className: "Lst@THEME_CSS@",
        disabledClassName: "LstDis@THEME_CSS@",
        monospaceClassName: "LstMno@THEME_CSS@",
        monospaceDisabledClassName: "LstMnoDis@THEME_CSS@",
        optionClassName: "LstOpt@THEME_CSS@",
        optionDisabledClassName: "LstOptDis@THEME_CSS@",
        optionGroupClassName: "LstOptGrp@THEME_CSS@",
        optionSelectedClassName: "LstOptSel@THEME_CSS@",
        optionSeparatorClassName: "LstOptSep@THEME_CSS@"
    },

    // Tree properties.
    tree: {
        selectedTreeRowClass: "TreeSelRow@THEME_CSS@",
        treeRowClass: "TreeRow@THEME_CSS@"
    }
}
});

//-->
