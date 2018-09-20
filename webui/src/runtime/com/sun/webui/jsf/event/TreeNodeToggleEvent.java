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
 * TreeNodeToggledEvent.java
 *
 * Created on November 8, 2006, 1:44 PM
 *
 */
package com.sun.webui.jsf.event;

import javax.faces.event.ActionEvent;
import javax.faces.event.FacesListener;
import com.sun.webui.jsf.component.TreeNode;

/*
 * This is an event instance that queued by a TreeNode
 * object's associated toggle listener. Queuing this
 * even causes TreeNode's broadcast method to fire
 * during which time actionListenerExpressions, if
 * associated with the TreeNode, can be handled 
 * appropriately. The default phaseId of 
 * "ANY_PHASE" is being used.
 */
public class TreeNodeToggleEvent extends ActionEvent {

    private static final long serialVersionUID = 6421867968165307677L;
    private TreeNode node = null;

    public TreeNodeToggleEvent(TreeNode node) {
        super(node);
        this.node = node;
    }

    /* 
     * Return true if this FacesListener is an instance of a listener 
     * class that this event supports. Typically, this will be accomplished
     * by an "instanceof" check on the listener class.
     */
    @Override
    public boolean isAppropriateListener(FacesListener listener) {
        if (listener instanceof ToggleActionListener) {
            return true;
        } else {
            return false;
        }
    }

    /* 
     * This should be a no op as all we want to do
     * is invoke the actionlistenerExpressions associated
     * with the node.
     */
    @Override
    public void processListener(FacesListener listener) {
        // This should be a no op as all we want to do
        // is invoke the actionlistenerExpressions associated
        // with the node.
    }
}
