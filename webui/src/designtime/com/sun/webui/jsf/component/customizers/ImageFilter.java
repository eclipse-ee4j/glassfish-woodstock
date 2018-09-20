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
 * ImageFilter.java
 *
 * Created on March 22, 2005, 1:37 PM
 */

package com.sun.webui.jsf.component.customizers;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/* ImageFilter.java is a 1.4 example used by FileChooserDemo2.java. */
public class ImageFilter extends FileFilter {
    
    //Accept all directories and all gif, jpg, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        
        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.gif) ||                
                    extension.equals(Utils.jpg) ||
                    extension.equals(Utils.jpe) ||                    
                    extension.equals(Utils.png) ||
                    extension.equals(Utils.jpeg)) {
                return true;
            } else {
                return false;
            }
        }
        
        return false;
    }
    
    //The description of this filter
    public String getDescription() {
        //"All Image Files (.gif, .jpg, .png, .jpe, .jpeg)";
        return java.util.ResourceBundle.getBundle("com/sun/webui/jsf/component/customizers/Bundle-DT").getString("imageFilterLabel"); // NOI18N
                
    }
}

