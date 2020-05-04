/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.webui.jsf.example;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import com.sun.webui.jsf.example.util.MessageUtil;
import com.sun.webui.jsf.example.util.UserData;
import com.sun.webui.jsf.component.OrderableList;
import java.io.Serializable;

/**
 * Backing bean for Orderable List example.
 */
public final class OrderableListBackingBean implements Serializable {

    /**
     * Holds value of property listItems.
     */
    private String[] listItems = null;

    /**
     * The original list.
     */
    private String[] originalList = null;

    /**
     * UserData.
     */
    private UserData userData = null;

    /**
     * Default constructor.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public OrderableListBackingBean() {
        // List items.
        listItems = new String[17];
        listItems[0] = MessageUtil.getMessage("orderablelist_flavor1");
        listItems[1] = MessageUtil.getMessage("orderablelist_flavor2");
        listItems[2] = MessageUtil.getMessage("orderablelist_flavor3");
        listItems[3] = MessageUtil.getMessage("orderablelist_flavor4");
        listItems[4] = MessageUtil.getMessage("orderablelist_flavor5");
        listItems[5] = MessageUtil.getMessage("orderablelist_flavor6");
        listItems[6] = MessageUtil.getMessage("orderablelist_flavor7");
        listItems[7] = MessageUtil.getMessage("orderablelist_flavor8");
        listItems[8] = MessageUtil.getMessage("orderablelist_flavor9");
        listItems[9] = MessageUtil.getMessage("orderablelist_flavor10");
        listItems[10] = MessageUtil.getMessage("orderablelist_flavor11");
        listItems[11] = MessageUtil.getMessage("orderablelist_flavor12");
        listItems[12] = MessageUtil.getMessage("orderablelist_flavor13");
        listItems[13] = MessageUtil.getMessage("orderablelist_flavor14");
        listItems[14] = MessageUtil.getMessage("orderablelist_flavor15");
        listItems[15] = MessageUtil.getMessage("orderablelist_flavor16");
        listItems[16] = MessageUtil.getMessage("orderablelist_flavor17");
        originalList = listItems;
    }

    /**
     * Get the value of property listItems.
     * @return String[]
     */
    public String[] getListItems() {
        return this.listItems;
    }

    /**
     * Set the value of property listItems.
     * @param newListItems listItems
     */
    public void setListItems(final String[] newListItems) {
        this.listItems = newListItems;
    }

    /**
     * Get UserData created with an array containing user's ordered list.
     * @return UserData
     */
    public UserData getUserData() {
        if (userData == null) {
            Flavor[] flavor = new Flavor[listItems.length];
            for (int i = 0; i < listItems.length; i++) {
                flavor[i] = new Flavor(listItems[i]);
            }
            userData = new UserData(flavor);
        }
        return userData;
    }

    /**
     * Action listener for the reset button.
     * @param event action event
     */
    public void resetOrder(final ActionEvent event) {
        // Sine the action is immediate, the orderable list component won't
        // go through the Update Model phase. However, its submitted value
        // gets set in the Apply Request Value phase and this value is retained
        // when the page is redisplayed.
        //
        // So, we need to explicitly erase the submitted value and then update
        // the model object with the original list.
        FacesContext context = FacesContext.getCurrentInstance();
        OrderableList list
                = (OrderableList) context.getViewRoot().findComponent(
                        "form:contentPageTitle:orderableList");
        list.setSubmittedValue(null);

        // Set the model object.
        listItems = originalList;
    }

    /**
     * Action listener for the breadcrumbs link.
     * @param event action event
     */
    public void processLinkAction(final ActionEvent event) {
        // All apps should revert to their initial state
        // when they are re-visitted from the Example index.
        // So, set the model object to the original list.
        listItems = originalList;
    }

    /**
     * Action listener for the ShowItems button.
     * @param event action event
     */
    public void resetDataProvider(final ActionEvent event) {
        // reset data provider;
        userData = null;
    }
}
