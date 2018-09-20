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
 * ProgressBarDesignInfo.java
 *
 * Created on September 5, 2006, 11:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.webui.jsf.component;

import com.sun.rave.designtime.*;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.webui.jsf.component.ProgressBar;
/**
 *
 * @author root
 */
public class ProgressBarDesignInfo extends AbstractDesignInfo {
    
    /** Creates a new instance of ProgressBarDesignInfo */
    public ProgressBarDesignInfo() {
        super(ProgressBar.class);
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        
        return false;
    }
    
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        
        return true;
    }
    
}
