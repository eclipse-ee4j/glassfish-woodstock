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
 * UploadedFile.java
 *
 * Created on April 1, 2005, 9:39 AM
 */
package com.sun.webui.jsf.model;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author avk
 */
//FIXME Should this be implements Serializable?
public interface UploadedFile extends Serializable {

    /**
     * Returns a {@link java.io.InputStream InputStream} for reading the file. 
     *
     * @return An {@link java.io.InputStream InputStream} for reading the file. 
     *
     * @exception IOException if there is a problem while reading the file
     */
    public InputStream getInputStream() throws IOException;

    /**
     * Get the content-type that the browser communicated with the request
     * that included the uploaded file. If the browser did not specify a 
     * content-type, this method returns null. 
     *
     * @return  the content-type that the browser communicated with the request
     * that included the uploaded file
     */
    public String getContentType();

    /**
     * Use this method to retrieve the name that the file has on the web 
     * application user's local system. 
     *
     * @return the name of the file on the web app user's system
     */
    public String getOriginalName();


    // ------------------------------------------------------- FileItem methods
    /**
     * The size of the file in bytes
     *
     * @return The size of the file in bytes.
     */
    public long getSize();

    /**
     * Use this method to retrieve the contents of the file as an array of bytes. 
     * @return The contents of the file as a byte array
     */
    public byte[] getBytes();

    /**
     * Use this method to retrieve the contents of the file as a String
     *
     * @return the contents of the file as a String
     */
    public String getAsString();

    /**
     * Write the contents of the uploaded file to a file on the server host. 
     * Note that writing files outside of the web server's tmp directory
     * must be explicitly permitted through configuration of the server's 
     * security policy. 
     * 
     * This method is not guaranteed to succeed if called more than once for
     * the same item. 
     * @param file The <code>File</code> where the contents should be written
     *
     * @exception Exception the 
     */
    public void write(File file) throws Exception;

    /**
     * Dispose of the resources associated with the file upload (this will
     * happen automatically when the resource is garbage collected). 
     */
    public void dispose();
}
