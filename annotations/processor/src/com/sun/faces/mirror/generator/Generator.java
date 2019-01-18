/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2019 Payara Services Ltd.
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

import java.io.Writer;

/**
 * Abstract base class for all source file and config file generators.
 *
 * @author gjmurphy
 */
abstract public class Generator {
    
    /**
     * The generate method is invoked by the processor, after the {@code printWriter}
     * property has been set. The generator should write the entire contents of its 
     * output file to the print writer when this method is called. A generator's properties
     * will always be initialized before it is asked to generate its source. A generator
     * may be invoked more than once. The {@code printWriter} will be reset before each
     * call to generate.
     */
    abstract public void generate() throws GeneratorException;

    private Writer printWriter;

    /**
     * Protected getter for property printWriter, used by subclasses to obtain the
     * print writer to use during generation.
     */
    protected Writer getPrintWriter() {
        return this.printWriter;
    }

    /**
     * Setter for property printWriter. The annotation processor will set the print
     * writer before each call to {@link #generate}.
     */
    public void setPrintWriter(Writer printWriter) {
        this.printWriter = printWriter;
    }
    
}
