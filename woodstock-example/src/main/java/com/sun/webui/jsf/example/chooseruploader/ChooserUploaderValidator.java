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

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sun.webui.jsf.example.common.MessageUtil;

import java.io.File;

/**
 * Validator class for Chooser/Uploader example.
 */
public class ChooserUploaderValidator implements Validator {
    
    /** Creates a new instance of ChooserUploaderValidator */
    public ChooserUploaderValidator() {
    }
    
    /**
     * This method throws validator exception if specified directory is 
     * invalid or do not have write permission.
     */
    public void validate(FacesContext context,
            UIComponent component, Object value)
            throws ValidatorException {
        
        String msgString = null;
        FacesMessage msg = null;
        
        if (value != null) {
            String dirPath = (String) value;
            File tempDir = new File(dirPath);
            
            if (!tempDir.isDirectory()) {
                msgString = MessageUtil.
                            getMessage("chooserUploader_invalidDir");
                
                msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
                
            } else if (!tempDir.canWrite()) {
                msgString = MessageUtil.
                            getMessage("chooserUploader_permissionDenied");
                
                msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            } 
        }
    }
}
