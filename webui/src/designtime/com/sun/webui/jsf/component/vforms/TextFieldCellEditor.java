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

/*
 * TextFieldCellEditor.java
 *
 * Created on October 19, 2005, 2:57 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.sun.webui.jsf.component.vforms;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * A JTextField cell editor by default all the text is selected
 */
public class TextFieldCellEditor extends DefaultCellEditor  {
    
    private JTable table;
    
    public TextFieldCellEditor( JTable table, JTextField component ) {
        super( component );
        this.table = table;
    }
    
    // This method is called when a cell value is edited by the user.
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
        
        // Configure the component with the specified value
        ((JTextField)super.getComponent()).setText((String)value);
        
        if (isSelected) {
            ((JTextField)super.getComponent()).selectAll();
            table.repaint();
        }
        
        // Return the configured component
        return super.getComponent();
    }
}
