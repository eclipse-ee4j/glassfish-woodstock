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
 * HomogonousCellRenderer.java
 *
 * Created on October 19, 2005, 2:54 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.sun.webui.jsf.component.vforms;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * A delegating renderer class that consistently sets the background color
 * of cells to reflect "selected" and "unselected" states.
 */
public class HomogonousCellRenderer extends DefaultTableCellRenderer {
    
    private static Color SELECTION_BACKGROUND =
            UIManager.getDefaults().getColor("TextField.selectionBackground");
    
    private static Color SELECTION_FOREGROUND =
            UIManager.getDefaults().getColor("TextField.selectionForeground");
    
    private static Color BACKGROUND =
            UIManager.getDefaults().getColor("TextField.background");
    
    private static Color FOREGROUND =
            UIManager.getDefaults().getColor("TextField.foreground");
    
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table,  value, isSelected, hasFocus, row, column);
        
        // To make the selection more visual
        
        //if( hasFocus )
        //    setBorder( new LineBorder(Color.WHITE) );
        
        if (isSelected) {
            c.setBackground(SELECTION_BACKGROUND);
            c.setForeground(SELECTION_FOREGROUND);
        }
        else {
            c.setBackground(BACKGROUND);
            c.setForeground(FOREGROUND);
        }
        
        return c;
    }
}
