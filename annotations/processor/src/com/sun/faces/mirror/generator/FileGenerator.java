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

package com.sun.faces.mirror.generator;

/**
 * Base generator for configuration files. Generators for configuration files
 * must provide a relative path to the file to be generated, reported separately
 * as the directory name and file name.
 *
 * @author gjmurphy
 */
abstract public class FileGenerator extends Generator {
    
    /**
     * Read-only property by which this file generator reports the name of the file
     * to which it should write.
     */
    abstract public String getFileName();
    
    /**
     * Read-only property by which this file generator reports the name of the directory
     * to which its file should be written.
     */
    abstract public String getDirectoryName();
    
}
