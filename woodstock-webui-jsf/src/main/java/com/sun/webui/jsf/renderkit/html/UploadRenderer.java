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
 * $Id: UploadRenderer.java,v 1.1.4.1.2.1 2009-12-29 04:52:45 jyeary Exp $
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import com.sun.webui.jsf.component.Upload;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renderer for a {@link Upload} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Upload"))
public class UploadRenderer extends FieldRenderer {

    private static final boolean DEBUG = false;

    /**
     * <p>Override the default implementation to conditionally trim the
     * leading and trailing spaces from the submitted value.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>Upload</code> component being processed
     */
    @Override
    public void decode(FacesContext context, UIComponent component) {

        if (DEBUG) {
            log("decode()");
        }
        Upload upload = (Upload) component;
        String id = component.getClientId(context).concat(upload.INPUT_ID);
        if (DEBUG) {
            log("\tLooking for id " + id);
        }
        Map map = context.getExternalContext().getRequestMap();

        if (map.containsKey(id)) {
            if (DEBUG) {
                log("\tFound id " + id);
            }
            upload.setSubmittedValue(id);
        }

        return;
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {

        if (!(component instanceof Upload)) {
            Object[] params = {component.toString(),
                this.getClass().getName(),
                Upload.class.getName()};
            String message = MessageUtil.getMessage("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                    "Upload.renderer", params);              //NOI18N
            throw new FacesException(message);
        }

        Theme theme = ThemeUtilities.getTheme(context);
        Map map = context.getExternalContext().getRequestMap();
        Object error = map.get(Upload.UPLOAD_ERROR_KEY);
        if (error != null) {
            if (error instanceof Throwable) {
                if (error instanceof org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException) {
                    // Caused by the file size is too big
                    String maxSize = (String) map.get(Upload.FILE_SIZE_KEY);
                    String[] detailArgs = {maxSize};
                    String summaryMsg = theme.getMessage("FileUpload.noFile");
                    String detailMsg =
                            theme.getMessage("Upload.error", detailArgs);
                    FacesMessage fmsg = new FacesMessage(summaryMsg, detailMsg);
                    context.addMessage(
                            ((Upload) component).getClientId(context), fmsg);
                } else {
                    String summaryMsg = theme.getMessage("FileUpload.noFile");
                    FacesException fe = new FacesException(summaryMsg);
                    fe.initCause((Throwable) error);
                    throw fe;
                }
            }
        }

        boolean spanRendered = super.renderField(context, (Upload) component,
                "file", getStyles(context));

        String id = component.getClientId(context);

        StringBuilder jsString = new StringBuilder(256);
        jsString.append("require([\"")
                .append(JavaScriptUtilities.getModuleName("upload")) //NOI18N
                .append("\"], function(upload) {")
                .append("upload.setEncodingType(\'") //NOI18N
                .append(id)
                .append("\'); });\n"); //NOI18N

        // Render JavaScript.
        ResponseWriter writer = context.getResponseWriter();
        JavaScriptUtilities.renderJavaScript(component, writer,
                jsString.toString());

        if (!spanRendered) {
            String param = id.concat(Upload.INPUT_PARAM_ID);
            RenderingUtilities.renderHiddenField(component, writer, param, id);
        }
    }
}
