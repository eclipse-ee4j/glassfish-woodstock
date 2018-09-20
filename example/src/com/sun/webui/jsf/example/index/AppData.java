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

package com.sun.webui.jsf.example.index;

import com.sun.webui.jsf.example.index.AppAction;

/**
 * A convenient class to wrap data about a given example.
 */
public class AppData {    
    
    /** The example app name */
    private String name;

    /** The concepts illustrated by this example */
    private String concepts;
    
    /** The example app action */
    private String appAction;

    /** 
     * Array of file names that will have source code
     * links for this example
     */
    private String[] files;               
    
    /**
     * Accepts info necessary to describe the given
     * example.
     *
     * @param name The name of the example
     * @param concepts The concepts illustrated by this example
     * @param appAction The example app action
     * @param files Array of file names for this example
     */
    public AppData(String name, String concepts, String appAction, String[] files) {
        this.name = name;
	this.concepts = concepts;
        this.appAction = appAction;
	this.files = files;
    }    
    
    /**
     * Get the name of the example
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the concepts illustrated by this example
     */
    public String getConcepts() {
        return concepts;
    }         
    
    /**
     * Get AppAction.
     */
    public AppAction getAppAction() {               
        return new AppAction(appAction);                
    }
    
    /**
     * Get array of files for this example
     */
    public String[] getFiles() {
        return files;
    }      
}
