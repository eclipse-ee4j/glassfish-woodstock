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
package com.sun.webui.jsf.example.index;

import javax.faces.context.FacesContext;
import com.sun.webui.jsf.example.common.MessageUtil;
import java.util.Map;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Serializable;

import javax.servlet.ServletContext;

/**
 * Backing bean for the show code page.
 */
public class ShowCodeBackingBean implements Serializable {

    // name of the file to display its content
    private String fileName;

    // relative path to java and properties resources
    private static final String RELATIVE_PATH
            = "/WEB-INF/classes/com/sun/webui/jsf/example/";

    /**
     * Default constructor
     */
    public ShowCodeBackingBean() {
    }

    /**
     * Get file name.
     * @return String
     */
    public String getFileName() {
        // Get hyperlink parameter
        Map map = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap();
        String param = (String) map.get("param");
        this.fileName = (param != null) ? param
                : MessageUtil.getMessage("index_noFileName");
        return this.fileName;
    }

    /**
     * Get the source code in the form of HTML.
     * @return
     */
    public String getSourceCode() {
        try {
            boolean isJavaCode = false;
            String sourceName = this.fileName;
            if (sourceName.endsWith(".java")) {
                sourceName = RELATIVE_PATH + sourceName;
                isJavaCode = true;
            } else if (sourceName.endsWith(".properties")) {
                sourceName = RELATIVE_PATH + sourceName;
                isJavaCode = false;
            } else if (sourceName.endsWith(".jspx")
                    || sourceName.endsWith(".js")
                    || sourceName.endsWith(".xml")) {
                sourceName = "/" + sourceName;
                isJavaCode = false;
            } else {
                throw new Exception("Unknown file type");
            }

            // Get the source file input stream
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servletContext
                    = (ServletContext) context.getExternalContext().getContext();
            InputStream is = servletContext.getResourceAsStream(sourceName);
            if (is == null) {
                throw new Exception("Resource not found: " + sourceName);
            }

            InputStreamReader reader = new InputStreamReader(is);
            StringWriter writer = new StringWriter();

            // It turns out that the Java->HTML converter does a decent
            // job on the JSPs as well; we just want to tell it not to
            // highlight keywords
            JavaHtmlConverter.convert(reader, writer, true, isJavaCode);

            return writer.getBuffer().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.toString();
        }
    }
}
