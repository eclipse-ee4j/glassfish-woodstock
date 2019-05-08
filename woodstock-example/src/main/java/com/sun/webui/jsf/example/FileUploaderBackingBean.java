/*
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
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

import com.sun.webui.jsf.model.UploadedFile;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;
import com.sun.webui.jsf.example.util.MessageUtil;
import java.io.File;
import java.io.Serializable;

/**
 * Backing Bean for File Uploader example.
 */
public final class FileUploaderBackingBean implements Serializable {

    /**
     * Holds file name.
     */
    private String tmpFileName = null;

    /**
     * Holds value of uploadPath property.
     */
    private String uploadPath = null;

    /**
     * Holds value of property uploadedFile.
     */
    private UploadedFile uploadedFile;

    /**
     * Creates a new instance of ChooserUploaderBackingBean.
     */
    public FileUploaderBackingBean() {
    }

    /**
     * Getter for property uploadedFile.
     *
     * @return Value of property uploadedFile.
     */
    public UploadedFile getUploadedFile() {
        return this.uploadedFile;
    }

    /**
     * Setter for property uploadedFile.
     *
     * @param newUploadedFile New value of property uploadedFile.
     */
    public void setUploadedFile(final UploadedFile newUploadedFile) {
        this.uploadedFile = newUploadedFile;
    }

    /**
     * It creates a temp file and uploads it in default temp directory.
     *
     * @throws java.lang.Exception if an error occurs
     */
    @SuppressWarnings("checkstyle:magicnumber")
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
            if (prefix.contains("\\")) {
                prefix = prefix.replace('\\', '_');
            }
            if (prefix.contains(":")) {
                prefix = prefix.replace(':', '_');
            }
        }
        File tmpFile = File.createTempFile(prefix, suffix);
        uploadedFile.write(tmpFile);
        tmpFileName = tmpFile.getAbsolutePath();

    }

    /**
     * Getter method for fileName.
     *
     * @return String
     */
    public String getFileName() {
        return tmpFileName;
    }

    /**
     * Setter method for fileName.
     *
     * @param fileName new file name
     */
    public void setFileName(final String fileName) {
        tmpFileName = fileName;
    }

    /**
     * Summary message for Validator exception.
     *
     * @return String
     */
    public String getSummaryMsg() {
        return MessageUtil.getMessage("chooseruploader_summary");
    }

    /**
     * This method throws validator exception if specified file has zero size.
     * You can also upload empty files.This method shows the use of validator.
     *
     * @param context faces context
     * @param component UI component
     * @param value value to be validated
     * @throws ValidatorException if a validation error occurs
     */
    public void validateFile(final FacesContext context,
            final UIComponent component, final Object value)
            throws ValidatorException {

        String msgString;
        FacesMessage msg;
        if (value != null) {
            UploadedFile uploadedFileName = (UploadedFile) value;
            long fileSize = uploadedFileName.getSize();

            if (fileSize == 0) {
                msgString
                        = MessageUtil.getMessage("chooserUploader_invalidFile");
                msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     * Checks for errors on page.
     *
     * @return boolean
     */
    public boolean isErrorsOnPage() {
        FacesMessage.Severity severity = FacesContext.getCurrentInstance()
                .getMaximumSeverity();
        if (severity == null) {
            return false;
        }
        return severity.compareTo(FacesMessage.SEVERITY_ERROR) >= 0;
    }

    /**
     * Action handler when navigating to the main example index.
     *
     * @return String
     */
    public String showExampleIndex() {
        tmpFileName = null;
        uploadPath = null;
        uploadedFile = null;
        return IndexBackingBean.INDEX_ACTION;
    }

    /**
     * Action handler when navigating to the chooser uploader example index.
     *
     * @return String
     */
    public String showUploaderIndex() {
        tmpFileName = null;
        uploadPath = null;
        uploadedFile = null;
        return "showChooserUploader";
    }
}
