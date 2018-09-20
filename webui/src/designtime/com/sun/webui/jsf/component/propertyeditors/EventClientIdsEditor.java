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

import com.sun.rave.propertyeditors.SelectOneDomainEditor;
import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;

public class EventClientIdsEditor extends SelectOneDomainEditor {
    
    public EventClientIdsEditor() {
        super(new EventClientIdsDomain());
    }
    
    /**
     * Domain of client ids of all components in scope that generate events of any
     * kind, i.e. all action and input components. An input component is one that
     * implements {@link javax.faces.component.EditableValueHolder}, and an action
     * component is one that implements {@link javax.faces.component.ActionSource}.
     */
    static class EventClientIdsDomain extends ClientIdsDomain {
        
        protected boolean isDomainComponent(UIComponent component) {
            Class c = component.getClass();
            if (EditableValueHolder.class.isAssignableFrom(c) || ActionSource.class.isAssignableFrom(c))
                return true;
            return false;
        }
        
    }
}
