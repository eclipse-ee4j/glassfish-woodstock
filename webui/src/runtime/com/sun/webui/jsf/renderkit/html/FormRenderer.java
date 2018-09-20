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
 * $Id: FormRenderer.java,v 1.1.20.1 2009-12-29 04:52:44 jyeary Exp $
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.Form;
import com.sun.webui.jsf.model.Markup;
import com.sun.webui.jsf.model.ScriptMarkup;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.RenderingUtilities;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * <p>Renderer for a {@link Form} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Form"))
public class FormRenderer extends AbstractRenderer {


    // ======================================================== Static Variables
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] = {"enctype", "accessKey", "onReset", "onSubmit", "target"}; //NOI18N
    private static final String SUBMISSION_COMPONENT_HIDDEN_FIELD = "_submissionComponentId";  //NOI18N
    private static final String FORM_HIDDEN_FIELD = "_hidden";  //NOI18N

    // -------------------------------------------------------- Renderer Methods
    /**
     * <p>Record a flag indicating whether this was the form (of the several
     * forms on the current page) that was submitted. Also, if the submission 
     * component id is known, then set the submitted virtual form if 
     * appropriate.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be decoded
     *
     * @exception NullPointerException if <code>context</code> or
     *  <code>component</code> is <code>null</code>
     */
    @Override
    public void decode(FacesContext context, UIComponent component) {

        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        Form form = (Form) component;
        Map map = context.getExternalContext().getRequestParameterMap();
        boolean b = map.containsKey(form.getClientId(context) + FORM_HIDDEN_FIELD);
        form.setSubmitted(b);
        if (LogUtil.fineEnabled()) {
            LogUtil.fine("Form(id=" + form.getId() + ",submitted=" +
                    form.isSubmitted() + ")");
        }

        String hiddenFieldClientId = SUBMISSION_COMPONENT_HIDDEN_FIELD;
        String submissionComponentId = (String) map.get(hiddenFieldClientId);
        if (submissionComponentId != null) {
            Form.VirtualFormDescriptor vfd = form.getVirtualFormComponentSubmits(submissionComponentId);
            if (vfd != null) {
                form.setSubmittedVirtualForm(vfd);
            }
        }

    }

    /**
     * <p>Render the appropriate element start for the outermost
     * element.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        // Start the appropriate element
        Form form = (Form) component;
        if (LogUtil.fineEnabled()) {
            LogUtil.fine("Form(id=" + form.getId() + ")"); //NOI18N
        }
        writer.startElement("form", form); //NOI18N

        //reapply any submitted values erased by the virtual forms mechanism
        form.restoreNonParticipatingSubmittedValues();
    }

    /**
     * <p>Render the appropriate element attributes.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        Form form = (Form) component;

        // Render the core attributes for the "form" element
        addCoreAttributes(context, form, writer, "form"); //NOI18N
        writer.writeAttribute("method", "post", null); //NOI18N
        writer.writeAttribute("action", action(context), null); //NOI18N
        addStringAttributes(context, form, writer, EVENTS_ATTRIBUTES);
        addStringAttributes(context, form, writer, stringAttributes);

        if (!form.isAutoComplete()) {
            //only render it if it's false
            writer.writeAttribute("autocomplete", "off", null); // NOI18N
        }
        // Render a newline for pretty printing
        writer.write("\n"); //NOI18N

    }

    /**
     * <p>Render the appropriate element end.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        Form form = (Form) component;
        List list = null;
        int n = 0;

        writer.write("\n"); //NOI18N
        // Render the hidden field noting this form as being submitted
        RenderingUtilities.renderHiddenField(component, writer,
                form.getClientId(context) + FORM_HIDDEN_FIELD,
                form.getClientId(context) + FORM_HIDDEN_FIELD);

        writer.write("\n"); //NOI18N
        context.getApplication().getViewHandler().writeState(context);
        writer.write("\n"); //NOI18N
        // Render the end of the form element
        writer.endElement("form"); //NOI18N
        writer.write("\n"); //NOI18N

        if (LogUtil.finestEnabled()) {
            LogUtil.finest("  Rendering completed"); //NOI18N
        }
    }


    // --------------------------------------------------------- Private Methods
    /**
     * <p>Return the URI to which this form should be submitted.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    private String action(FacesContext context) {

        String viewId = context.getViewRoot().getViewId();
        String url =
                context.getApplication().getViewHandler().
                getActionURL(context, viewId);
        return context.getExternalContext().encodeActionURL(url);

    }

    /**
     * <p>Return the name of a JavaScript function that will be called
     * by the specified event handler.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param form {@link Form} being rendered
     * @param handler Name of the event handler that will call
     */
    private String function(FacesContext context, Form form, String handler) {

        String clientId = form.getClientId(context);
        return handler + "_" + clientId.replace(':', '_');  //NOI18N

    }

    /**
     * <p>Create and return the markup for the specified event handler.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param form {@link Form} being rendered
     * @param name Name of the event handler to be rendered
     * @param list List of code elements to be included
     */
    private Markup handler(FacesContext context, Form form,
            String name, List list) {

        String code = null;
        Markup markup = new ScriptMarkup();
        markup.writeRaw("function " + //NOI18N
                function(context, form, name) + //NOI18N
                "(form) {\n", null); //NOI18N
        code = (String) form.getAttributes().get(name);
        if (code != null) {
            markup.writeRaw("    ", null); //NOI18N
            markup.writeRaw(code, null);
            if (!code.endsWith(";")) { //NOI18N
                markup.writeRaw(";", null); //NOI18N
            }
            markup.writeRaw("\n", null); //NOI18N
        }
        for (int i = 0; i < list.size(); i++) {
            code = ((String) list.get(i)).trim();
            markup.writeRaw("    ", null); //NOI18N
            markup.writeRaw(code, null);
            if (!code.endsWith(";")) {
                markup.writeRaw(";", null); //NOI18N
            }
            markup.writeRaw("\n", null); //NOI18N
        }
        markup.writeRaw("    return true;\n", null); //NOI18N
        markup.writeRaw("}\n", null); //NOI18N
        return markup;

    }
}
