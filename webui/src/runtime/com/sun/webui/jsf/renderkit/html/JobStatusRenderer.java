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

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;
import com.sun.webui.jsf.component.JobStatus;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renders an instance of the JobStatus component.</p>
 *
 * @author Sean Comerford
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.JobStatus"))
public class JobStatusRenderer extends HyperlinkRenderer {

    /** Creates a new instance of JobStatusRenderer */
    public JobStatusRenderer() {
    }

    /**
     * <p>Render the start of the JobStatus component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * start should be rendered
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        JobStatus jobStatus = (JobStatus) component;

        UIComponent image = jobStatus.getImageFacet();
        if (image != null) {
            RenderingUtilities.renderComponent(image, context);
        }

        writer.write("&nbsp;");
    }

    @Override
    protected void finishRenderAttributes(FacesContext context,
            UIComponent component, ResponseWriter writer) throws IOException {
        JobStatus jobStatus = (JobStatus) component;
        Theme theme = ThemeUtilities.getTheme(context);

        Object textObj = jobStatus.getText();
        String text = textObj != null ? ConversionUtilities.convertValueToString(component, textObj) : theme.getMessage("masthead.tasksRunning");

        writer.startElement("span", jobStatus);
        addCoreAttributes(context, jobStatus, writer,
                theme.getStyleClass(ThemeStyles.MASTHEAD_TEXT));
        writer.write(text);
        writer.write("&nbsp;" + jobStatus.getNumJobs()); // NOI18N
        writer.endElement("span");
    }
}
