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

package com.sun.webui.jsf.component.propertyeditors;

import com.sun.rave.propertyeditors.SelectOneDomainEditor;
import com.sun.rave.propertyeditors.domains.Domain;
import com.sun.rave.propertyeditors.domains.Element;
import com.sun.webui.jsf.component.util.DesignMessageUtil;

public class FileTypesEditor extends SelectOneDomainEditor {
    
    public FileTypesEditor() {
        super(new FileTypesDomain());
    }
    
    static class FileTypesDomain extends Domain {
        
        private static Element[] elements = new Element[] {
            new Element("file", DesignMessageUtil.getMessage(FileTypesDomain.class, "FileType.file")), //NOI18N
            new Element("folder", DesignMessageUtil.getMessage(FileTypesDomain.class, "FileType.folder")) //NOI18N
        };
        
        public Element[] getElements() {
            return FileTypesDomain.elements;
        }
        
    }
}
