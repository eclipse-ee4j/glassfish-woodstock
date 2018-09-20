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

import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.CustomizerResult;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.impl.BasicDisplayAction;
import com.sun.webui.jsf.component.util.DesignMessageUtil;

/**
 * Bind to Data Action for the <code>Table</code> or <code>TableGroup</code> component.
 * @author Winston Prakash
 */

public class TableBindToDataAction extends BasicDisplayAction {

    protected DesignBean designBean;

    public TableBindToDataAction(DesignBean bean) {
        super(DesignMessageUtil.getMessage(TableCustomizerAction.class, "tableBindToDataAction.displayName")); //NOI18N
        setHelpKey("projrave_ui_elements_dialogs_data_table_bind_to_data_db"); //NOI18N
        designBean = bean;
    }

    public Result invoke() {
        return new CustomizerResult(designBean, new TableBindToDataCustomizer());
    }
}
