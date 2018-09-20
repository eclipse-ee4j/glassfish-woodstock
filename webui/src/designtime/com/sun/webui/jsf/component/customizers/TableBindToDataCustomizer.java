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
 * TableDesignInfo.java
 * Created on April 29, 2005, 12:40 PM
 * Version 1.0
 */

package com.sun.webui.jsf.component.customizers;

import com.sun.webui.jsf.component.table.TableBindToDataPanel;
import java.awt.Component;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.impl.BasicCustomizer2;
import com.sun.webui.jsf.component.table.TableBindToDataPanel;
import com.sun.webui.jsf.component.util.DesignMessageUtil;

/**
 * Customizer for the <code>Table</code> component.
 * @author Winston Prakash
 */

public class TableBindToDataCustomizer extends BasicCustomizer2 {
    public TableBindToDataCustomizer() {
        super(null, DesignMessageUtil.getMessage(TableCustomizerAction.class, "tableBindToDataCustomizer.title"), null, null); //NOI18N
        setApplyCapable(true);
        setHelpKey("projrave_ui_elements_dialogs_bindtodata_table_db"); //NOI18N
    }

    protected TableBindToDataPanel panel;

    public Component getCustomizerPanel(DesignBean bean) {
        this.setDisplayName(DesignMessageUtil.getMessage(TableCustomizerAction.class, "tableBindToDataCustomizer.title") + " - " + bean.getInstanceName());
        panel = new TableBindToDataPanel(bean);
        return panel;
    }

    public boolean isModified() {
        if (panel != null) {
            return panel.isModified();
        }
        return false;
    }

    public Result applyChanges() {
        if (panel != null) {
            return panel.applyChanges();
        }
        return Result.SUCCESS;
    }
}
