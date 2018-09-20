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

package com.sun.webui.jsf.example.table.util;

import com.sun.data.provider.FieldKey;
import com.sun.data.provider.RowKey;
import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.ObjectListDataProvider;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionTitle;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.sun.webui.jsf.example.common.MessageUtil;

// This class provides functionality for table actions.
public class Actions {
    private Group group = null; // Group util.

    // Action menu items.
    protected static final Option[] moreActionsOptions = {
        new OptionTitle(MessageUtil.getMessage("table_ActionsMenuTitle")),
        new Option("ACTION1", MessageUtil.getMessage("table_Action1")),
        new Option("ACTION2", MessageUtil.getMessage("table_Action2")),
        new Option("ACTION3", MessageUtil.getMessage("table_Action3")),
        new Option("ACTION4", MessageUtil.getMessage("table_Action4")),
    };

    // Default constructor.
    public Actions(Group group) {
        this.group = group;
    }

    // Action button event.
    public void action() {
        String message = null;

        // Get hyperlink parameter used for embedded actions example.
        Map map = FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap();
        String param = (String) map.get("param");
        if (param != null) {
            message = MessageUtil.getMessage("table_embeddedActionMsg") + " " + param;
        } else {
            message = MessageUtil.getMessage("table_tableActionMsg");
        }

        group.getMessages().setMessage(message);
    }

    // Action to remove rows from ObjectListDataProvider.
    public void delete() {
        // Since mutiple examples are using the same beans, the binding
        // simply tells us that checkbox state is maintained arcoss pages.
        if (group.getSelect().isKeepSelected()) {
            // If we got here, then we're maintaining state across pages.
            delete(group.getTableRowGroup().getSelectedRowKeys());
        } else {
            // If we got here, then we're using the phase listener and must
            // take filtering, sorting, and pagination into account.
            delete(group.getTableRowGroup().getRenderedSelectedRowKeys());
        }
    }

    // Set disabled value for table actions.
    public boolean getDisabled() {
        // If there is at least one row selection, actions are enabled.
        boolean result = true;
        if (group.getTableRowGroup() == null) {
            return result;
        }

        // Since mutiple examples are using the same beans, the binding
        // simply tells us that checkbox state is maintained arcoss pages.
        if (group.getSelect().isKeepSelected()) {
            // If we got here, then we're maintaining state across pages.
            result = group.getTableRowGroup().getSelectedRowsCount() < 1;
        } else {
            // If we got here, then we're using the phase listener and must
            // take filtering, sorting, and pagination into account.
            result = group.getTableRowGroup().getRenderedSelectedRowsCount() < 1;
        }
        return result;
    }

    // Get action.
    public String getMoreActions() {
        // Per the UI guidelines, always snap back to "More Actions...".
        return OptionTitle.NONESELECTED;
    }

    // Get action menu options.
    public Option[] getMoreActionsOptions() {
        return moreActionsOptions;
    }

    // Action menu event.
    public void moreActions() {
        group.getMessages().setMessage(MessageUtil.getMessage("table_moreActionsMsg"));
    }

    // Set action.
    public void setMoreActions(String action) {
        // Do nothing.
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Action to remove rows from ObjectListDataProvider.
    private void delete(RowKey[] rowKeys) {
        if (rowKeys == null) {
            return;
        }
        TableDataProvider provider = group.getNames();
        for (int i = 0; i < rowKeys.length; i++) {
            RowKey rowKey = rowKeys[i];
            if (provider.canRemoveRow(rowKey)) {
                provider.removeRow(rowKey);
            }
        }
        ((ObjectListDataProvider) provider).commitChanges(); // Commit.
        group.getSelect().clear(); // Clear phase listener.
    }
}
