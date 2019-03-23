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

import com.sun.data.provider.RowKey;
import com.sun.webui.jsf.util.LogUtil;
import java.util.HashMap;
import javax.faces.FactoryFinder;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * A utility class for radiobutton and checkbox components used to select rows
 * of a table.
 * <p>
 * Note: UI guidelines recomend that rows should be unselected when no longer in
 * view. For example, when a user selects rows of the table and navigates to
 * another page. Or, when a user applies a filter or sort that may hide
 * previously selected rows from view. If a user invokes an action to delete the
 * currently selected rows, they may inadvertently remove rows not displayed on
 * the current page. Using TableSelectPhaseListener ensures that invalid row
 * selections are not rendered by clearing selected state after the render
 * response phase. That said, there are cases when maintaining state across
 * table pages is necessary. In this scenario, use the keepSelected method to
 * prevent state from being cleared by this instance.
 * </p><p>
 * Note: To see the messages logged by this class, set the following global
 * defaults in your JDK's "jre/lib/logging.properties" file.
 * </p><p><pre>
 * java.util.logging.ConsoleHandler.level = FINE
 * com.sun.webui.jsf.event.TableSelectPhaseListener.level = FINE
 * </pre></p>
 */
public class TableSelectPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 6955269103244653901L;
    private Object unselected = null; // Unselected object for primitve values.
    private HashMap<String, Object> selected = new HashMap<String, Object>(); // Selected values map.
    private boolean keepSelected = false; // Do not clear selected flag.

    /** Default constructor */
    public TableSelectPhaseListener() {
        // Add phase listener.
        LifecycleFactory factory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        Lifecycle lifecycle = factory.getLifecycle(
                LifecycleFactory.DEFAULT_LIFECYCLE);
        lifecycle.addPhaseListener(this);
    }

    /** 
     * Construct an instance with the given flag indicating that selected
     * objects should not be cleared after the render response phase.
     *
     * @param keepSelected If true, ojects are not cleared.
     */
    public TableSelectPhaseListener(boolean keepSelected) {
        this();
        keepSelected(keepSelected);
    }

    /** Construct an instance with an unselected parameter.
     * <p>
     * The unselected parameter is only required if a primitve value is being 
     * used for the selecteValue attribute of the checkbox or radiobutton. If 
     * the selectedValue property is an Object value then unselected can be 
     * null. If however it is a primitive type then it should be the MIN_VALUE 
     * constant instance of the wrapper Object type. For example if the 
     * application is assigning int values to selectedValue then unselected 
     * should be new Integer(Integer.MIN_VALUE).
     * </p>
     * @param unselected the object to return for an unselected checkbox.
     */
    public TableSelectPhaseListener(Object unselected) {
        this();
        this.unselected = unselected;
    }

    /**
     * Called during the JSF Lifecycle after the RENDER_RESPONSE phase.
     *
     * @param event The PhaseEvent object.
     */
    public void afterPhase(PhaseEvent event) {
        if (!keepSelected) {
            selected.clear();
        } else {
            log("afterPhase", //NOI18N
                    "Selected values not cleared, keepSelected is false");
        }
    }

    /**
     * Called during the JSF Lifecycle before the RENDER_RESPONSE phase.
     *
     * @param event The PhaseEvent object.
     */
    public void beforePhase(PhaseEvent event) {
        // Not needed
    }

    /** Get the phase id. */
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    /**
     * Clear all selected objects.
     */
    public void clear() {
        selected.clear();
    }

    /**
     * Get the selected object from this instance.
     * <p>
     * Note: Call this method from the get method that that is bound to the 
     * selected attribute.
     * </p>
     * @param rowKey The current RowKey.
     * @return The selected object.
     */
    public Object getSelected(RowKey rowKey) {
        Object object = (rowKey != null)
                ? selected.get(rowKey.getRowId()) : null;

        // If null, return the unselected value.
        return (object != null) ? object : unselected;
    }

    /**
     * Test if the flag indicating that selected objects should be cleared
     * after the render response phase.
     *
     * @return true if ojects are not to be cleared.
     */
    public boolean isKeepSelected() {
        return keepSelected;
    }

    /**
     * Test if the object associated with the given RowKey is selected.
     *
     * @param rowKey The current RowKey.
     * @return A true or false value.
     */
    public boolean isSelected(RowKey rowKey) {
        Object object = getSelected(rowKey);
        return (object != null && object != unselected);
    }

    /**
     * Set the flag indicating that selected objects should be cleared after
     * the render response phase.
     *
     * @param keepSelected Selected objects are kept true, cleared if false.
     */
    public void keepSelected(boolean keepSelected) {
        this.keepSelected = keepSelected;
    }

    /**
     * Set the selected object for this instance.
     * <p>
     * Note: Call this method from the set method that that is bound to the 
     * selected attribute.
     * </p>
     * @param rowKey The current RowKey.
     * @param object The selected object.
     */
    public void setSelected(RowKey rowKey, Object object) {
        if (rowKey != null) {
            selected.put(rowKey.getRowId(), object);
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
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
