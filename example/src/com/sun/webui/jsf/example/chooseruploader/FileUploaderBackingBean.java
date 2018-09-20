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

package com.sun.webui.jsf.example.chooseruploader;

import com.sun.webui.jsf.model.UploadedFile;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;

import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;

import java.io.*;
import java.util.List;


/**
 * Backing Bean for File Uploader example.
 */
public class FileUploaderBackingBean implements Serializable {
    
    // Holds file name.
    private String tmpFileName = null;
    
    // Holds value of uploadPath property.
    private String uploadPath = null;
    
    // Holds value of property uploadedFile.
    private UploadedFile uploadedFile;
    
    /** Creates a new instance of ChooserUploaderBackingBean */
    public FileUploaderBackingBean() {
    }
    
    /**
     * Getter for property uploadedFile.
     * @return Value of property uploadedFile.
     */
    public UploadedFile getUploadedFile() {
        
        return this.uploadedFile;
    }
    
    /**
     * Setter for property uploadedFile.
     * @param uploadedFile New value of property uploadedFile.
     */
    public void setUploadedFile(UploadedFile uploadedFile) {
        
        this.uploadedFile = uploadedFile;
    }
    
    /**
     *It creates a temp file and uploads it in default temp directory.
     */
    public void writeFile() throws Exception {
        
        if (uploadedFile == null) {
           return;
        }
        String name = uploadedFile.getOriginalName();
        if (name == null || name.length() == 0) {
            name = "tmp.tmp";
        }
        
        int index = name.indexOf(".");
        String suffix = ".tmp";
        if (index != -1) {
            suffix = name.substring(name.indexOf("."));
            if (suffix.length() == 0) {
                suffix = ".tmp";
            }
        }
        String prefix = name;
        if (index != -1) {
            prefix = name.substring(0, name.indexOf("."));
            if (prefix.length() < 3) {
                prefix = "tmp";
            }
            if (prefix.indexOf("\\") != -1) {
                prefix = prefix.replace('\\', '_');
            }
            if (prefix.indexOf(":") != -1) {
                prefix = prefix.replace(':', '_');
            }
        }
        
        File tmpFile = File.createTempFile(prefix, suffix);
        uploadedFile.write(tmpFile);
        tmpFileName = tmpFile.getAbsolutePath();
        
    }
    
    /**
     *Getter method for fileName.
     */
    public String getFileName() {
           return tmpFileName;
    }
   
    /**
     *Setter method for fileName.
     */
    public void setFileName(String fileName) {
           tmpFileName = fileName;
    }

    /**
     * Summary message for Validator exception.
     */
    public String getSummaryMsg() {
           return MessageUtil.getMessage("chooseruploader_summary");
    }

    /**
     * This method throws validator exception if specified file has zero size.
     * You can also upload empty files.This method shows the use of validator. 
     */
    public void validateFile(FacesContext context,
            UIComponent component, Object value)
            throws ValidatorException {
        
        String msgString = null;
        FacesMessage msg = null;
        
        if (value != null) {
            UploadedFile uploadedFileName = (UploadedFile) value;
            long fileSize  = uploadedFileName.getSize();
                        
            if (fileSize == 0) {
                msgString = MessageUtil.
                            getMessage("chooserUploader_invalidFile");
                
                msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     * Checks for errors on page.
     */
    public boolean isErrorsOnPage() {
        
        FacesMessage.Severity severity =
                FacesContext.getCurrentInstance().getMaximumSeverity();
        if (severity == null) {
            return false;
        }
        if (severity.compareTo(FacesMessage.SEVERITY_ERROR) >= 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Action handler when navigating to the main example index.
     */
    public String showExampleIndex() {
        tmpFileName = null;
        uploadPath = null;
        uploadedFile = null;
        return IndexBackingBean.INDEX_ACTION;
    }
    
    /**
     * Action handler when navigating to the chooser uploader example index.
     */
    public String showUploaderIndex() {
        tmpFileName = null;
        uploadPath = null;
        uploadedFile = null;
        return "showChooserUploader";
    }
}

