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
 * This class serves as a data wrapper for the Table component in the
 * Example Application Index page.
 */
public class TableData {  
   
    // Fill data for each example in an array object.            
    protected static final AppData[] data = {
        new AppData("index_buttonName","index_buttonConcepts", "showButton",
                new String[] {
                    "button/Button.jsp",
                    "button/ButtonResults.jsp",
                    "button/ButtonBackingBean.java"}
        ),
        new AppData("index_cbrbName","index_cbrbConcepts", "showCheckboxRadiobutton",
                new String[] {
                    "cbrb/checkboxRadiobutton.jsp",
                    "cbrb/checkboxRadiobuttonResults.jsp",
                    "cbrb/CheckboxRadiobuttonBackingBean.java"}
        ),
        new AppData("index_labelName","index_labelConcepts", "showLabel",
                new String[] {
                    "label/Label.jsp",
                    "label/LabelResults.jsp",
                    "label/Help.jsp",
                    "label/LabelBackingBean.java"}
        ),
        new AppData("index_alertName","index_alertConcepts", "showAlertIndex",
                new String[] {
                    "alert/Alert.jsp",
                    "alert/InlineAlert.jsp",
                    "alert/PageAlertExample.jsp",
                    "alert/PageAlert.jsp",
                    "alert/HelpAlert.jsp",
                    "alert/InlineAlertBackingBean.java",        
                    "alert/PageAlertBackingBean.java"}
        ),
        new AppData("index_textInputName", "index_textInputConcepts", "showTextInput", 
                new String[] {
                    "field/TextInput.jsp",
                    "field/TextInputResults.jsp",
                    "field/TextInputBackingBean.java"}
        ),
        new AppData("index_tableName", "index_tableConcepts", "showTableIndex", 
                new String[] {
                    "table/actions.jsp",
                    "table/actionsBottom.jsp",
                    "table/actionsTop.jsp",
                    "table/alarms.jsp",
                    "table/basicTable.jsp",
                    "table/customTitle.jsp",
                    "table/dynamicGroupTable.jsp",
                    "table/dynamicTable.jsp",
                    "table/embeddedActions.jsp",
                    "table/emptyCells.jsp",
                    "table/filter.jsp",
                    "table/filterPanel.jsp",
                    "table/groupTable.jsp",
                    "table/hiddenRowsActionsBottom.jsp",        
                    "table/hiddenRowsActionsTop.jsp",        
                    "table/hiddenSelectedRows.jsp",                    
                    "table/index.jsp",
                    "table/multipleHeadersFooters.jsp",
                    "table/liteTable.jsp",
                    "table/paginatedTable.jsp",
                    "table/preferences.jsp",
                    "table/preferencesPanel.jsp",
                    "table/selectMultipleRows.jsp",
                    "table/selectSingleRow.jsp",
                    "table/spacerColumn.jsp",
                    "table/sortableTable.jsp",
                    "table/table.jsp",
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
                    "table/js/select.js"}
        ),
        new AppData("index_addRemoveName", "index_addRemoveConcepts", "showAddRemove",
                new String[] {
                    "addremove/AddRemove.jsp",
                    "addremove/AddRemoveResults.jsp",
                    "addremove/AddRemoveBackingBean.java",
                    "common/UserData.java"}
        ),
        new AppData("index_orderableListName", "index_orderableListConcepts", "showOrderableList", 
                new String[] {
                    "orderablelist/OrderableList.jsp",
                    "orderablelist/OrderableListResults.jsp",
                    "orderablelist/OrderableListBackingBean.java",
                    "orderablelist/Flavor.java",
                    "common/UserData.java"}        
        ),
        new AppData("index_chooserUploader", "index_chooserUploaderConcepts", "showChooserUploader",
                new String[] {
                    "chooseruploader/fileUploader.jsp",
                    "chooseruploader/fileChooser.jsp", 
                    "chooseruploader/folderChooserPopup.jsp",
                    "chooseruploader/fileUploaderPopup.jsp",
                    "chooseruploader/folderChooser.jsp", 
                    "chooseruploader/fileChooserPopup.jsp",        
                    "chooseruploader/FileChooserBackingBean.java",  
                    "chooseruploader/FolderChooserBackingBean.java",
                    "chooseruploader/FileUploaderBackingBean.java",        
                    "chooseruploader/ChooserUploaderBackingBean.java",
                    "chooseruploader/ChooserUploaderValidator.java"}
        ),
        new AppData("index_menuListName", "index_menuListConcepts", "showMenuList", 
                new String[] {
                    "menu/MenuList.jsp",
                    "menu/MenuListResults.jsp",
                    "menu/MenuListBackingBean.java"}
        ),
        new AppData("index_mastheadName", "index_mastheadConcepts", "showMasthead",
                new String[] {
                    "masthead/Index.jsp",
                    "masthead/Masthead.jsp",
                    "masthead/MastheadFacets.jsp",
                    "masthead/Version.jsp",
                    "masthead/Popup.jsp",
                    "masthead/Images.jsp",
                    "masthead/ResultMasthead.jsp",
                    "masthead/ResultMastheadFacets.jsp",
                    "masthead/MastheadBackingBean.java"}
        ),
        new AppData("index_propertysheet", "index_propertySheetConcepts", "showPropertySheet",
                new String[] {
                    "propertysheet/PropertySheet.jsp",
                    "propertysheet/PropertySheetResult.jsp",         
                    "propertysheet/PropertySheetBackingBean.java"}
        ),
        new AppData("index_editablelist", "index_editableListConcepts", "showEditableList",
                new String[] {
                    "editablelist/editableList.jsp",
                    "editablelist/editableListResult.jsp",        
                    "editablelist/EditableListBackingBean.java"}
        ),
        new AppData("index_pagetitleName", "index_pagetitleConcepts", "showPagetitle",
                new String[] {
                    "pagetitle/Pagetitle.jsp",
                    "pagetitle/PagetitleBackingBean.java"}
        ),
        new AppData("index_hyperlink", "index_hyperlinkConcepts", "showHyperlink",
                new String[] {
                    "hyperlink/hyperLink.jsp",
                    "hyperlink/hyperLinkResult.jsp",        
                    "hyperlink/HyperlinkBackingBean.java"}
        ),
        new AppData("index_statictextName", "index_statictextConcepts", "showStaticText",
                new String[] {
                    "statictext/Statictext.jsp",
                    "statictext/StatictextBackingBean.java",
                    "statictext/Employee.java",
                    "statictext/EmployeeConverter.java"}
        ),         
        new AppData("index_commonTaskName", "index_commonTaskConcepts", "showCommonTask",
                new String[] {
                    "commontask/commonTasks.jsp",
                    "commontask/sample.jsp",
                    "commontask/commonTaskBean.java"}
        ),
        new AppData("index_treeName", "index_treeConcepts", "showTreeIndex",
                new String[] {
                    "tree/content.jsp",
                    "tree/dynamicTree.jsp",
                    "tree/header.jsp",
                    "tree/index.jsp",
                    "tree/navTree.jsp",
                    "tree/treeFrame.jsp",
		    "common/ClientSniffer.java",
		    "util/ExampleUtilities.java",
		    "tree/DynamicTreeBackingBean.java",
		    "tree/NavTreeBackingBean.java"}
        ),
        new AppData("index_progressBar", "index_progressBarConcepts", "showProgressBar",
                new String[] {
                    "progressbar/index.jsp",
                    "progressbar/determinate.jsp",
                    "progressbar/indeterminate.jsp",
                    "progressbar/busy.jsp",
                    "progressbar/ProgressBarBackingBean.java"}
        ),
        new AppData("index_wizardName","index_wizardConcepts", "showWizardIndex",
                new String[] {
                    "wizard/index.jsp",
                    "wizard/simpleWizard.jsp",
                    "wizard/SimpleWizardBackingBean.java"}
        )
    };    
    
    /** Default constructor */
    public TableData() { 		
    }
}
	
