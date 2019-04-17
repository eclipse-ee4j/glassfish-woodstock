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
import javax.faces.event.ActionEvent;
import javax.faces.application.FacesMessage;

import com.sun.webui.jsf.example.util.MessageUtil;
import java.io.File;
import java.io.Serializable;

/**
 * Backing Bean for Chooser Uploader example.
 */
public final class ChooserUploaderBackingBean implements Serializable {

    /**
     * Holds file name.
     */
    private String tmpFileName = null;

    /**
     * String constant.
     */
    public static final String WINDOWS_OS = "WINDOW";

    /**
     * Holds value of property lookin.
     */
    private File lookin = null;

    /**
     * Holds value of property fileChooserLookin.
     */
    private File fileChooserLookin = null;

    /**
     * Holds value of property selected.
     */
    private File selected = null;

    /**
     * Holds value of property uploadPath.
     */
    private String uploadPath = null;

    /**
     * Holds value of property forwardName.
     */
    private String forwardName = null;

    /**
     * Holds value of property uploadedFile.
     */
    private UploadedFile uploadedFile;

    /**
     * Creates a new instance of ChooserUploaderBackingBean.
     */
    public ChooserUploaderBackingBean() {
        String osName = System.getProperty("os.name").toUpperCase();
        if (osName.startsWith(WINDOWS_OS)) {
            lookin = new File("c:\\\\windows");
        } else {
            lookin = new File("/usr");
        }
        fileChooserLookin = new File(lookin.toString());
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
     * Getter for property lookin.
     *
     * @return Value of property lookin.
     */
    public File getLookin() {
        return lookin;
    }

    /**
     * Getter for property fileChooserLookin.
     *
     * @return File
     */
    public File getFileChooserLookin() {
        if (uploadPath != null) {
            return (new File(uploadPath));
        } else {
            return fileChooserLookin;
        }
    }

    /**
     * Getter method for selected.
     *
     * @return File
     */
    public File getSelected() {
        return selected;
    }

    /**
     * Setter method for selected.
     *
     * @param newSelected selected File to set
     */
    public void setSelected(final File newSelected) {
        this.selected = newSelected;
    }

    /**
     * It creates a temp file and uploads it in a specified directory.
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
        File tempDir = null;
        if (uploadPath != null) {
            tempDir = new File(uploadPath);
        }
        File tmpFile = File.createTempFile(prefix, suffix, tempDir);
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
     * Getter method for uploadPath.
     *
     * @return String
     */
    public String getUploadPath() {
        return uploadPath;
    }

    /**
     * Setter method for uploadPath.
     *
     * @param path new path
     */
    public void setUploadPath(final String path) {
        uploadPath = path;
    }

    /**
     * Action Listener method.
     *
     * @param event action event
     */
    public void goToPage(final ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        String id = event.getComponent().getClientId(context);
        if (id.equals("indexForm:fileChooser")) {
            forwardName = "fileChooser";
        } else if (id.equals("indexForm:folderChooser")) {
            forwardName = "folderChooser";
        } else if (id.equals("indexForm:fileUploader")) {
            forwardName = "fileUploader";
        } else {
            forwardName = "fileChooserUploader";
        }
    }

    /**
     * Action method.
     *
     * @return String
     */
    public String forwardAction() {
        return forwardName;
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
        lookin = null;
        selected = null;
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
        lookin = null;
        selected = null;
        uploadPath = null;
        uploadedFile = null;
        return "showChooserUploader";
    }
}
