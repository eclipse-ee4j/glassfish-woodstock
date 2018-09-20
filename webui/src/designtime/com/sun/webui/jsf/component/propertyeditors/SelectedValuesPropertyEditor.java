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

package com.sun.webui.jsf.component.propertyeditors;

import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.propertyeditors.TabularPropertyEditor;
import com.sun.rave.propertyeditors.TabularPropertyModel;
import com.sun.rave.propertyeditors.util.JavaInitializer;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.model.MultipleSelectOptionsList;
import com.sun.webui.jsf.model.Option;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * An editor for properties that specify the options that are "selected" among
 * a list of options. An option is "selected" if the value of its
 * <code>value</code> property is equal to one of the objects in the selected
 * property.
 *
 * @author gjmurphy
 */
public class SelectedValuesPropertyEditor extends TabularPropertyEditor {
    
    public SelectedValuesPropertyEditor() {
    }
    
    private SelectedValuesTableModel tableModel;
    
    protected TabularPropertyModel getTabularPropertyModel() {
        if (tableModel == null)
            tableModel = new SelectedValuesTableModel();
        tableModel.setDesignProperty(this.getDesignProperty());
        return tableModel;
    }
    
    public String getJavaInitializationString() {
        StringBuffer buffer = new StringBuffer();
        Object value = this.getValue();
        if (value instanceof Object[]) {
            buffer.append("new Object[] {");
            Object[] values = (Object[]) value;
            for (int i = 0; i < values.length; i++) {
                if (i > 0)
                    buffer.append(", ");
                buffer.append(JavaInitializer.toJavaInitializationString(values[i]));
            }
            buffer.append("}");
        } else {
            buffer.append(JavaInitializer.toJavaInitializationString(value));
        }
        return buffer.toString();
    }
    
    public String getAsText() {
        StringBuffer buffer = new StringBuffer();
        Object value = this.getValue();
        if (value instanceof Object[]) {
            Object[] values = (Object[]) value;
            for (int i = 0; i < values.length; i++) {
                if (i > 0)
                    buffer.append(", ");
                buffer.append(values[i].toString());
            }
        } else if (value != null) {
            buffer.append(value.toString());
        }
        return buffer.toString();
    }
    
    static final String[] columnNames = new String[] {
        DesignMessageUtil.getMessage(OptionsPropertyEditor.class, "Options.label"), //NOI18N
                DesignMessageUtil.getMessage(OptionsPropertyEditor.class, "Options.value"), //NOI18N
                DesignMessageUtil.getMessage(OptionsPropertyEditor.class, "Options.selected") //NOI18N
    };
    
    class SelectedValuesTableModel implements TabularPropertyModel {
        
        final int labelIndex = 0;
        final int valueIndex = 1;
        final int selectedIndex = 2;
        
        DesignProperty designProperty;
        // List of the component options from which selections are made
        ArrayList options;
        // List of boolean values that reflected selected state of the options
        ArrayList selected;
        // If true, component allows multiple selections
        boolean isMultiple;
        
        public void setValue(Object value) {
            options = new ArrayList();
            selected = new ArrayList();
            if (designProperty != null) {
                DesignBean bean = designProperty.getDesignBean();
                Option[] opts = (Option[]) bean.getProperty("options").getValue();
                DesignProperty multipleProperty = bean.getProperty("multiple");
                if ((multipleProperty != null && Boolean.TRUE.equals(multipleProperty.getValue())) ||
                        bean.getInstance() instanceof MultipleSelectOptionsList) {
                    Object[] values = value == null ? new Object[0] : (Object[]) value;
                    isMultiple = true;
                    for (int i = 0; i < opts.length; i++) {
                        options.add(opts[i]);
                        selected.add(Boolean.FALSE);
                        for (int j = 0; j < values.length; j++) {
                            if (opts[i].getValue().equals(values[j]))
                                selected.set(i, Boolean.TRUE);
                        }
                    }
                } else {
                    isMultiple = false;
                    for (int i = 0; i < opts.length; i++) {
                        options.add(opts[i]);
                        if (opts[i].getValue().equals(value))
                            selected.add(Boolean.TRUE);
                        else
                            selected.add(Boolean.FALSE);
                    }
                }
            }
        }
        
        public Object getValue() {
            if (selected == null)
                return null;
            if (isMultiple) {
                ArrayList values = new ArrayList();
                for (int i = 0; i < selected.size(); i++) {
                    if (Boolean.TRUE.equals(selected.get(i)))
                        values.add(((Option) options.get(i)).getValue());
                }
                return values.toArray(new Object[values.size()]);
            } else {
                for (int i = 0; i < selected.size(); i++) {
                    if (Boolean.TRUE.equals(selected.get(i)))
                        return ((Option) options.get(i)).getValue();
                }
            }
            return null;
        }
        
        ArrayList tableModelListenerList = new ArrayList();
        
        public void addTableModelListener(TableModelListener listener) {
            tableModelListenerList.add(listener);
        }
        
        public void removeTableModelListener(TableModelListener listener) {
            tableModelListenerList.remove(listener);
        }
        
        public void setDesignProperty(DesignProperty designProperty) {
            this.designProperty = designProperty;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex != selectedIndex)
                return false;
            if (rowIndex < 0 || rowIndex >= options.size())
                return false;
            return true;
        }
        
        public void setValueAt(Object newValue, int rowIndex, int columnIndex) {
            if (!isCellEditable(rowIndex, columnIndex))
                return;
            // If underlying property takes a list of values, update the list, and
            // set property to new state of list.
            if (isMultiple) {
                selected.set(rowIndex, newValue);
                ArrayList selectedValues = new ArrayList();
                for (int i = 0; i < options.size(); i++) {
                    if (Boolean.TRUE.equals(selected.get(i)))
                        selectedValues.add(((Option) options.get(i)).getValue());
                }
                //this.setValue(selectedValues.toArray());
            }
            // If underlying property takes a single value, and user has just deselected
            // the previously selected value, then set property value to null.
            else if(Boolean.FALSE.equals(newValue)) {
                selected.set(rowIndex, Boolean.FALSE);
                //this.setValue(null);
            }
            // If underlying property takes a single value, and user has just selected
            // a new option, deselect previously selected option, and set property value
            // to new option's value.
            else {
                for (int i = 0; i < selected.size(); i++) {
                    if (Boolean.TRUE.equals(selected.get(i))) {
                        selected.set(i, Boolean.FALSE);
                        //((TabularPropertyPanel) super.getCustomEditor()).updateTableData(i, selectedIndex);
                        for (int j = 0; j < tableModelListenerList.size(); j++) {
                            TableModelListener l = (TableModelListener) tableModelListenerList.get(j);
                            l.tableChanged(new TableModelEvent(this, i, i, selectedIndex));
                        }
                    }
                }
                selected.set(rowIndex, Boolean.TRUE);
                //this.setValue(((Option) options.get(rowIndex)).getValue());
            }
        }
        
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex >= 0 && rowIndex < options.size()) {
                if (columnIndex == labelIndex)
                    return ((Option) options.get(rowIndex)).getLabel();
                else if (columnIndex == valueIndex)
                    return ((Option) options.get(rowIndex)).getValue();
                else
                    return selected.get(rowIndex);
            }
            return null;
        }
        
        public Class getColumnClass(int columnIndex) {
            if (columnIndex == labelIndex)
                return String.class;
            else if (columnIndex == selectedIndex)
                return Boolean.class;
            else if (options.size() > 0 && options.get(0) != null)
                return ((Option) options.get(0)).getValue().getClass();
            else
                return Object.class;
        }
        
        public int getRowCount() {
            return options.size();
        }
        
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }
        
        public int getColumnCount() {
            return columnNames.length;
        }
        
        public boolean canAddRow() {
            return false;
        }
        
        public boolean addRow() {
            return false;
        }
        
        public boolean canMoveRow(int indexFrom, int indexTo) {
            return false;
        }
        
        public boolean moveRow(int indexFrom, int indexTo) {
            return false;
        }
        
        public boolean canRemoveRow(int index) {
            return false;
        }
        
        public boolean removeRow(int index) {
            return false;
        }
        
        public boolean removeAllRows() {
            return false;
        }
        
    }
    
}
