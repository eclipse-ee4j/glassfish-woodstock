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

package com.sun.webui.jsf.event;

import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.component.Table;
import com.sun.webui.jsf.component.TableActions;
import com.sun.webui.jsf.component.TableRowGroup;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.LogUtil;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * A listener for receiving pagination toggle events.
 * <p>
 * A class that is interested in receiving such events registers itself with the
 * source {@link Table} of interest, by calling addActionListener().
 * </p><p>
 * Note: To see the messages logged by this class, set the following global
 * defaults in your JDK's "jre/lib/logging.properties" file.
 * </p><p><pre>
 * java.util.logging.ConsoleHandler.level = FINE
 * com.sun.webui.jsf.event.TablePaginationActionListener.level = FINE
 * </pre></p>
 */
public class TablePaginationActionListener implements ActionListener {

    /**
     * Invoked when the action described by the specified
     * {@link ActionEvent} occurs. The source parent is expected to be a
     * TablePagination object.
     *
     * @param event The {@link ActionEvent} that has occurred
     *
     * @exception AbortProcessingException Signal the JavaServer Faces
     *  implementation that no further processing on the current event
     *  should be performed
     */
    public void processAction(ActionEvent event)
            throws AbortProcessingException {
        UIComponent source = (event != null)
                ? (UIComponent) event.getSource() : null;
        if (source == null) {
            log("processAction", //NOI18N
                    "Cannot process action, ActionEvent source is null"); //NOI18N
            return;
        }
        processTable(getTableAncestor(source), source.getId());
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Process Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Helper method to process Table components.
    private void processTable(Table component, String id)
            throws AbortProcessingException {
        if (component == null) {
            log("processTable", "Cannot process Table action, Table is null"); //NOI18N
            return;
        }

        // Iterate over every TableRowGroup child and set pagination for each.
        Iterator kids = component.getTableRowGroupChildren();
        while (kids.hasNext()) {
            TableRowGroup group = (TableRowGroup) kids.next();

            if (id.equals(TableActions.PAGINATION_FIRST_BUTTON_ID)) {
                setFirst(group);
            } else if (id.equals(TableActions.PAGINATION_LAST_BUTTON_ID)) {
                setLast(group);
            } else if (id.equals(TableActions.PAGINATION_NEXT_BUTTON_ID)) {
                setNext(group);
            } else if (id.equals(TableActions.PAGINATE_BUTTON_ID)) {
                setPaginated(group);
            } else if (id.equals(TableActions.PAGINATION_PREV_BUTTON_ID)) {
                setPrev(group);
            } else if (id.equals(TableActions.PAGINATION_SUBMIT_BUTTON_ID)) {
                try {
                    setPage(group, getPage(component));
                } catch (NumberFormatException e) {
                    log("processTable", "Cannot obtain page field value"); //NOI18N
                    return;
                }
            }
        }
    }

    /**
     * Get the closest Table ancestor that encloses this component.
     *
     * @param component UIComponent for which to extract children.
     */
    private Table getTableAncestor(UIComponent component) {
        if (component == null) {
            log("getTableAncestor", //NOI18N
                    "Cannot obtain Table ancestor, UIComponent is null"); //NOI18N
            return null;
        } else if (component instanceof Table) {
            return (Table) component;
        } else {
            return getTableAncestor(component.getParent());
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Pagination Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Get the value of the page field.
    private int getPage(Table component) {
        UIComponent actions = component.getFacet(Table.TABLE_ACTIONS_BOTTOM_ID);
        UIComponent field = (actions != null)
                ? (UIComponent) actions.getFacet(TableActions.PAGINATION_PAGE_FIELD_ID)
                : null;
        String value = null;
        if (field instanceof TextField) {
            value = ConversionUtilities.convertValueToString(field,
                    ((TextField) field).getValue());
        } else {
            log("getPage", //NOI18N
                    "Cannot obtain page text field value, not TextField instance"); //NOI18N
        }
        return (value != null) ? Integer.parseInt(value) : -1;
    }

    // Set current page.
    private void setPage(TableRowGroup component, int page) {
        if (component == null) {
            log("setPage", "Cannot set page, TableRowGroup is null"); //NOI18N
            return;
        }
        // Set the starting row for the current page.
        component.setPage(page);
    }

    // Set first row.
    private void setFirst(TableRowGroup component) {
        if (component == null) {
            log("setFirst", "Canot set first row, TableRowGroup is null"); //NOI18N
            return;
        }
        // Set the starting row for the first page.
        component.setPage(1);
    }

    // Set last row.
    private void setLast(TableRowGroup component) {
        if (component == null) {
            log("setLast", "Cannot set last row, TableRowGroup is null"); //NOI18N
            return;
        }
        // Get the row number of the last page to be displayed.
        component.setPage(component.getPages());
    }

    // Set next row.
    private void setNext(TableRowGroup component) {
        if (component == null) {
            log("setNext", "Cannot set next row, TableRowGroup is null"); //NOI18N
            return;
        }
        // Get the starting row index for the next page.
        component.setPage(component.getPage() + 1);
    }

    // Set paginated.
    private void setPaginated(TableRowGroup component) {
        if (component == null) {
            log("setPaginated", "Cannot set paginated, TableRowGroup is null"); //NOI18N
            return;
        }
        // Toggle between paginated and scroll mode.
        component.setPaginated(!component.isPaginated());
    }

    // Set previous row.
    private void setPrev(TableRowGroup component) {
        if (component == null) {
            log("setPrev", "Cannot set previous row, TableRowGroup is null"); //NOI18N
            return;
        }
        // Set the starting row index for the previous page.
        component.setPage(component.getPage() - 1);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Misc methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Log fine messages.
     */
    private void log(String method, String message) {
        // Get class.
        Class clazz = this.getClass();
        if (LogUtil.fineEnabled(clazz)) {
            // Log method name and message.
            LogUtil.fine(clazz, clazz.getName() + "." + method + ": " + message); //NOI18N
        }
    }
}
