/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import com.sun.webui.jsf.example.util.MessageUtil;
import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Backing Bean for Folder Chooser example.
 */
public final class FolderChooserBackingBean implements Serializable {

    /**
     * String constant.
     */
    public static final String WINDOWS_OS = "WINDOW";

    /**
     * Holds value of lookin property.
     */
    private File lookin = null;

    /**
     * Holds value of selected property.
     */
    private File selected = null;

    /**
     * Creates a new instance of FileChooserBackingBean.
     */
    public FolderChooserBackingBean() {
        String osName = System.getProperty("os.name").toUpperCase();
        if (osName.startsWith(WINDOWS_OS)) {
            lookin = new File("c:\\\\windows");
        } else {
            lookin = new File("/usr");
        }
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
     * returns absolute path for selected objects.
     * @param object object to convert to String
     * @return String
     */
    @SuppressWarnings("unchecked")
    private String valueString(final Object object) {

        Object obj = object;
        if (obj instanceof List) {
            List files = (List) object;
            Object first = files.get(0);
            if (first instanceof File) {
                obj = files.toArray(new File[files.size()]);
            } else if (first instanceof String) {
                obj = files.toArray(new String[files.size()]);
            } else {
                String value = files.get(0).toString();
                for (int i = 1; i < files.size(); ++i) {
                    value = value + ", " + files.get(i).toString();
                }
                return value;
            }
        }

        String value = null;
        if (obj instanceof File[]) {
            File[] files = (File[]) object;
            value = files[0].getAbsolutePath();
            for (int i = 1; i < files.length; ++i) {
                value = value + ", " + files[i].getAbsolutePath();
            }
        } else if (obj instanceof File) {
            File file = (File) object;
            value = file.getAbsolutePath();
        } else if (obj instanceof String[]) {
            String[] files = (String[]) object;
            value = files[0];
            for (int i = 1; i < files.length; ++i) {
                value = value + ", " + files[i];
            }
        } else if (obj instanceof String) {
            String file = (String) object;
            value = file;
        }
        return value;
    }

    /**
     * Getter method for selected.
     * @return File
     */
    public File getSelected() {
        return selected;
    }

    /**
     * Setter method for selected.
     * @param newSelected selected
     */
    public void setSelected(final File newSelected) {
        this.selected = newSelected;
    }

    /**
     * Getter method for folderName.
     * @return String
     */
    public String getFolderName() {

        if (selected != null) {
            return valueString(selected);
        } else {
            return null;
        }

    }

    /**
     * Summary message for Validator exception.
     * @return String
     */
    public String getSummaryMsg() {
        return MessageUtil.getMessage("chooseruploader_summary");
    }

    /**
     * Checks for errors on page.
     * @return {@code boolean}
     */
    public boolean isErrorsOnPage() {
        FacesMessage.Severity severity
                = FacesContext.getCurrentInstance().getMaximumSeverity();
        if (severity == null) {
            return false;
        }
        return severity.compareTo(FacesMessage.SEVERITY_ERROR) >= 0;
    }

    /**
     * Action handler when navigating to the main example index.
     * @return String
     */
    public String showExampleIndex() {

        selected = null;
        return IndexBackingBean.INDEX_ACTION;
    }

    /**
     * Action handler when navigating to the chooser uploader example index.
     * @return String
     */
    public String showUploaderIndex() {
        selected = null;
        return "showChooserUploader";
    }
}
