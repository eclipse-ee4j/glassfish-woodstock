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
package com.sun.webui.jsf.example.index;

import com.sun.webui.jsf.example.index.AppData;

/**
 * This class serves as a data wrapper for the Table component in the Example
 * Application Index page.
 */
public class TableData {

    // Fill data for each example in an array object.            
    protected static final AppData[] data = {
        new AppData("index_buttonName", "index_buttonConcepts", "showButton",
            new String[]{
                "button/Button.jspx",
                "button/ButtonResults.jspx",
                "button/ButtonBackingBean.java"
            }
        ),
        new AppData("index_cbrbName", "index_cbrbConcepts", "showCheckboxRadiobutton",
            new String[]{
                "cbrb/checkboxRadiobutton.jspx",
                "cbrb/checkboxRadiobuttonResults.jspx",
                "cbrb/CheckboxRadiobuttonBackingBean.java"
            }
        ),
        new AppData("index_labelName", "index_labelConcepts", "showLabel",
            new String[]{
                "label/Label.jspx",
                "label/LabelResults.jspx",
                "label/Help.jspx",
                "label/LabelBackingBean.java"
            }
        ),
        new AppData("index_alertName", "index_alertConcepts", "showAlertIndex",
            new String[]{
                "alert/Alert.jspx",
                "alert/InlineAlert.jspx",
                "alert/PageAlertExample.jspx",
                "alert/PageAlert.jspx",
                "alert/HelpAlert.jspx",
                "alert/InlineAlertBackingBean.java",
                "alert/PageAlertBackingBean.java"
            }
        ),
        new AppData("index_textInputName", "index_textInputConcepts", "showTextInput",
            new String[]{
                "field/TextInput.jspx",
                "field/TextInputResults.jspx",
                "field/TextInputBackingBean.java"
            }
        ),
        new AppData("index_tableName", "index_tableConcepts", "showTableIndex",
            new String[]{
                "table/actions.jspx",
                "table/actionsBottom.jspx",
                "table/actionsTop.jspx",
                "table/alarms.jspx",
                "table/basicTable.jspx",
                "table/customTitle.jspx",
                "table/dynamicGroupTable.jspx",
                "table/dynamicTable.jspx",
                "table/embeddedActions.jspx",
                "table/emptyCells.jspx",
                "table/filter.jspx",
                "table/filterPanel.jspx",
                "table/groupTable.jspx",
                "table/hiddenRowsActionsBottom.jspx",
                "table/hiddenRowsActionsTop.jspx",
                "table/hiddenSelectedRows.jspx",
                "table/index.jspx",
                "table/multipleHeadersFooters.jspx",
                "table/liteTable.jspx",
                "table/paginatedTable.jspx",
                "table/preferences.jspx",
                "table/preferencesPanel.jspx",
                "table/selectMultipleRows.jspx",
                "table/selectSingleRow.jspx",
                "table/spacerColumn.jspx",
                "table/sortableTable.jspx",
                "table/table.jspx",
                "table/TableBean.java",
                "table/DynamicGroupTableBean.java",
                "table/DynamicTableBean.java",
                "table/util/Actions.java",
                "table/util/Dynamic.java",
                "table/util/Filter.java",
                "table/util/Group.java",
                "table/util/Messages.java",
                "table/util/Name.java",
                "table/util/Preferences.java",
                "table/util/Select.java",
                "table/js/actions.js",
                "table/js/filter.js",
                "table/js/preferences.js",
                "table/js/select.js"
            }
        ),
        new AppData("index_addRemoveName", "index_addRemoveConcepts", "showAddRemove",
            new String[]{
                "addremove/AddRemove.jspx",
                "addremove/AddRemoveResults.jspx",
                "addremove/AddRemoveBackingBean.java",
                "common/UserData.java"
            }
        ),
        new AppData("index_orderableListName", "index_orderableListConcepts", "showOrderableList",
            new String[]{
                "orderablelist/OrderableList.jspx",
                "orderablelist/OrderableListResults.jspx",
                "orderablelist/OrderableListBackingBean.java",
                "orderablelist/Flavor.java",
                "common/UserData.java"
            }
        ),
        new AppData("index_chooserUploader", "index_chooserUploaderConcepts", "showChooserUploader",
            new String[]{
                "chooseruploader/fileUploader.jspx",
                "chooseruploader/fileChooser.jspx",
                "chooseruploader/folderChooserPopup.jspx",
                "chooseruploader/fileUploaderPopup.jspx",
                "chooseruploader/folderChooser.jspx",
                "chooseruploader/fileChooserPopup.jspx",
                "chooseruploader/FileChooserBackingBean.java",
                "chooseruploader/FolderChooserBackingBean.java",
                "chooseruploader/FileUploaderBackingBean.java",
                "chooseruploader/ChooserUploaderBackingBean.java",
                "chooseruploader/ChooserUploaderValidator.java"
            }
        ),
        new AppData("index_menuListName", "index_menuListConcepts", "showMenuList",
            new String[]{
                "menu/MenuList.jspx",
                "menu/MenuListResults.jspx",
                "menu/MenuListBackingBean.java"
            }
        ),
        new AppData("index_mastheadName", "index_mastheadConcepts", "showMasthead",
            new String[]{
                "masthead/Index.jspx",
                "masthead/Masthead.jspx",
                "masthead/MastheadFacets.jspx",
                "masthead/Version.jspx",
                "masthead/Popup.jspx",
                "masthead/Images.jspx",
                "masthead/ResultMasthead.jspx",
                "masthead/ResultMastheadFacets.jspx",
                "masthead/MastheadBackingBean.java"
            }
        ),
        new AppData("index_propertysheet", "index_propertySheetConcepts", "showPropertySheet",
            new String[]{
                "propertysheet/PropertySheet.jspx",
                "propertysheet/PropertySheetResult.jspx",
                "propertysheet/PropertySheetBackingBean.java"
            }
        ),
        new AppData("index_editablelist", "index_editableListConcepts", "showEditableList",
            new String[]{
                "editablelist/editableList.jspx",
                "editablelist/editableListResult.jspx",
                "editablelist/EditableListBackingBean.java"
            }
        ),
        new AppData("index_pagetitleName", "index_pagetitleConcepts", "showPagetitle",
            new String[]{
                "pagetitle/Pagetitle.jspx",
                "pagetitle/PagetitleBackingBean.java"
            }
        ),
        new AppData("index_hyperlink", "index_hyperlinkConcepts", "showHyperlink",
            new String[]{
                "hyperlink/hyperLink.jspx",
                "hyperlink/hyperLinkResult.jspx",
                "hyperlink/HyperlinkBackingBean.java"
            }
        ),
        new AppData("index_statictextName", "index_statictextConcepts", "showStaticText",
            new String[]{
                "statictext/Statictext.jspx",
                "statictext/StatictextBackingBean.java",
                "statictext/Employee.java",
                "statictext/EmployeeConverter.java"
            }
        ),
        new AppData("index_commonTaskName", "index_commonTaskConcepts", "showCommonTask",
            new String[]{
                "commontask/commonTasks.jspx",
                "commontask/sample.jspx",
                "commontask/commonTaskBean.java"
            }
        ),
        new AppData("index_treeName", "index_treeConcepts", "showTreeIndex",
            new String[]{
                "tree/content.jspx",
                "tree/dynamicTree.jspx",
                "tree/header.jspx",
                "tree/index.jspx",
                "tree/navTree.jspx",
                "tree/treeFrame.jspx",
                "common/ClientSniffer.java",
                "util/ExampleUtilities.java",
                "tree/DynamicTreeBackingBean.java",
                "tree/NavTreeBackingBean.java"
            }
        ),
        new AppData("index_progressBar", "index_progressBarConcepts", "showProgressBar",
            new String[]{
                "progressbar/index.jspx",
                "progressbar/determinate.jspx",
                "progressbar/indeterminate.jspx",
                "progressbar/busy.jspx",
                "progressbar/ProgressBarBackingBean.java"
            }
        ),
        new AppData("index_wizardName", "index_wizardConcepts", "showWizardIndex",
            new String[]{
                "wizard/index.jspx",
                "wizard/simpleWizard.jspx",
                "wizard/SimpleWizardBackingBean.java"
            }
        )
    };

    /**
     * Default constructor
     */
    public TableData() {
    }
}
