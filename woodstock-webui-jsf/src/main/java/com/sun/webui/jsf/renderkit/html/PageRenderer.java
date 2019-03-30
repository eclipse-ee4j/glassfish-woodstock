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
 * $Id: PageRenderer.java,v 1.1.20.1 2009-12-29 04:52:43 jyeary Exp $
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.Page;
import com.sun.webui.jsf.util.MessageUtil;

import static com.sun.webui.jsf.util.RenderingUtilities.isPortlet;

/**
 * Renderer for a {@link Page} component.
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Page"))
public class PageRenderer extends javax.faces.render.Renderer {

    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
    }

    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {

        if (component == null){
            return;
        }
        if (!(component instanceof Page)) {
            Object[] params = {
                component.toString(),
                this.getClass().getName(),
                Page.class.getName()
            };
            String message = MessageUtil.getMessage(
                    "com.sun.webui.jsf.resources.LogMessages",
                    "Renderer.component", params);
            throw new FacesException(message);
        }

        Page page = (Page) component;

        ResponseWriter writer = context.getResponseWriter();

        if (!isPortlet(context)) {
            //write the doctype stuff
            if (page.isXhtml()) {
                if (page.isFrame()) {
                    //xhtml transitional frames
                    writer.write("<!DOCTYPE html PUBLIC \""
                            + "-//W3C//DTD XHTML 1.0 Frameset//EN\" "
                            + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">");
                } else {
                    //html transitional
                    writer.write("<!DOCTYPE html PUBLIC \""
                            + "-//W3C//DTD XHTML 1.0 Transitional//EN\" "
                            + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
                }
            } else {
                if (page.isFrame()) {
                    //html transitional frames
                    writer.write("<!DOCTYPE html PUBLIC \""
                            + "-//W3C//DTD HTML 4.01 Frameset//EN\" "
                            + "\"http://www.w3.org/TR/html4/frameset.dtd\">");
                } else {
                    //html transitional
                    writer.write("<!DOCTYPE html PUBLIC \""
                            + "-//W3C//DTD HTML 4.01 Transitional//EN\" "
                            + "\"http://www.w3.org/TR/html4/loose.dtd\">");
                }
            }
            writer.write("\n");
        }
    }

    @Override
    public boolean getRendersChildren() {
        return false;
    }
}
