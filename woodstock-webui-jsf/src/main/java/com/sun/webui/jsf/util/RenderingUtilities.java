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
package com.sun.webui.jsf.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;

import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.theme.Theme;
import javax.faces.component.UINamingContainer;

/**
 * This class provides common methods for renderers.
 */
public class RenderingUtilities {

    /**
     * Creates a new instance of RenderingUtilities.
     */
    public RenderingUtilities() {
    }

    /**
     * Render a component.
     *
     * @param component The component to render
     * @param context The FacesContext of the request
     * @throws java.io.IOException if an IO error occurs
     */
    public static void renderComponent(UIComponent component,
            FacesContext context) throws IOException {

        if (!component.isRendered()) {
            return;
        }

        // this is a workaround a jsf bug in tables where it caches the
        // client id.  We are forcing the caching not to happen.
        // this could be a potential source of performance issues if
        // it turns out the jsf folks really wanted this
        String id = component.getId();
        if (id != null) {
            component.setId(id);
        }
        component.encodeBegin(context);
        if (component.getRendersChildren()) {
            component.encodeChildren(context);
        } else {
            Iterator kids = component.getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                renderComponent(kid, context);
            }
        }
        component.encodeEnd(context);
    }

    /**
     * This method goes through an array of possible attribute names, evaluates
     * if they have been set on the component, and writes them out using the
     * specified writer.
     *
     * @param component The component being rendered
     * @param writer The writer to use to write the attributes
     * @param possibleAttributes String attributes that are treated as
     * pass through for this component
     * @throws java.io.IOException if an IO error occurs
     */
    public static void writeStringAttributes(UIComponent component,
            ResponseWriter writer, String[] possibleAttributes)
            throws IOException {

        // Get the rest of the component attributes and display them
        Map attributes = component.getAttributes();

        int numNames = possibleAttributes.length;
        String attributeName;
        Object attributeValue;

        for (int counter = 0; counter < numNames; counter++) {
            attributeName = possibleAttributes[counter];
            attributeValue = attributes.get(attributeName);
            if (attributeValue != null) {
                writer.writeAttribute(attributeName.toLowerCase(),
                        String.valueOf(attributeValue),
                        attributeName);
            }
        }
    }

    /**
     * Add any attributes on the specified list directly to the specified
     * ResponseWriter for which the specified UIComponent has a non-null String
     * value. This method may be used to "pass through" commonly used attribute
     * name/value pairs with a minimum of code. Attribute names are converted to
     * lower case in the rendered output. Any name/value pairs in the extraHtml
     * String shall take precedence over attribute values.
     *
     * @param component EditableValueHolder component whose submitted value is
     * to be stored.
     * @param writer ResponseWriter to which the element start should be
     * rendered.
     * @param names List of attribute names to be passed through.
     * @param extraHtml Extra name/value pairs to be rendered.
     *
     * @exception IOException if an input/output error occurs
     */
    public static void writeStringAttributes(UIComponent component,
            ResponseWriter writer, String names[], String extraHtml)
            throws IOException {

        if (component == null || names == null) {
            return;
        }
        Map attributes = component.getAttributes();
        Object value;
        for (String name : names) {
            // Special case for names matching "valign" instead of "align".
            if (extraHtml == null || extraHtml.indexOf(name + "=") != 0
                    && !extraHtml.contains(" " + name + "=")) {
                value = attributes.get(name);
                if (value != null) {
                    if (value instanceof String) {
                        writer.writeAttribute(name.toLowerCase(), (String) value, name);
                    } else {
                        writer.writeAttribute(name.toLowerCase(), value.toString(), name);
                    }
                }
            }
        }
        // Render extra HTML attributes.
        renderExtraHtmlAttributes(writer, extraHtml);
    }

    /**
     * This method will output a hidden field for use with Params and components
     * that need to submit a value through a hidden field.Note: The name of the
     * hidden field will be written as is.For Params no encoding inside the form
     * is done.This is intentional.
     *
     * @param component UI component
     * @param writer The writer to use to write the attributes
     * @param id The identifier of the hidden field. pass through for this
     * component
     * @param value field value
     * @throws java.io.IOException if an IO error occurs
     */
    public static void renderHiddenField(UIComponent component,
            ResponseWriter writer, String id, String value) throws IOException {

        if (id == null) {
            // TODO: when we figure out our exception string strategy, fix this
            throw new IllegalArgumentException(
                    "An f:param tag had a null name attribute");
        }

        writer.startElement("input", component);  //NOI18N
        writer.writeAttribute("id", id, null);
        writer.writeAttribute("name", id, null);
        if (value != null) {
            writer.writeAttribute("value", value, null);
        }
        writer.writeAttribute("type", "hidden", null);
        writer.endElement("input");
    }

    /**
     * Decode a hidden field.
     *
     * @param context The FacesContext associated with this request
     * @param parameterMapId Identifies the value in the parameters map
     * @return String
     */
    public static String decodeHiddenField(FacesContext context,
            String parameterMapId) {

        Map params = context.getExternalContext().getRequestParameterMap();
        Object valueObject = params.get(parameterMapId);
        return (String) valueObject;
    }

    /**
     * Return a space-separated list of CSS style classes to render for this
     * component, or {@code null} for none.
     *
     * @param context faces context
     * @param component {@code UIComponent} for which to calculate classes
     * @param styles Additional styles specified by the renderer
     * @return String
     */
    public static String getStyleClasses(FacesContext context,
            UIComponent component, String styles) {

        String styleClass = (String) component.getAttributes().get("styleClass");

        boolean componentNotVisible = !isVisible(component);

        if (componentNotVisible) {
            String hiddenStyleClass = ThemeUtilities.getTheme(context)
                    .getStyleClass(ThemeStyles.HIDDEN);
            if (styleClass != null) {
                styleClass += " " + hiddenStyleClass;
            } else {
                styleClass = hiddenStyleClass;
            }
        }

        if (styleClass != null) {
            if (styles != null) {
                return styleClass + " " + styles;
            } else {
                return styleClass;
            }
        } else {
            if (styles != null) {
                return styles;
            } else {
                return null;
            }
        }
    }

    /**
     * Render the style class.
     * @param context faces context
     * @param writer writer to use
     * @param component component to style
     * @param extraStyles additional styles
     * @throws java.io.IOException if an IO error occurs
     */
    public static void renderStyleClass(FacesContext context,
            ResponseWriter writer, UIComponent component, String extraStyles)
            throws IOException {

        String classes = getStyleClasses(context, component, extraStyles);
        if (classes != null) {
            writer.writeAttribute("class", classes, "styleClass");
        }
    }

    /**
     * Helper method to render style classes when name/value pairs are given via
     * an extraHtml String.This method will append the given style to the class
     * name/value pair found in the extraHtml String. The class name/value is
     * removed from the returned extraHtml String so that developers may invoke
     * the writeStringAttributes method without rendering the style class,
     * again.
     *
     * @param context FacesContext for the current request.
     * @param component The UI component component to be rendered.
     * @param writer ResponseWriter to which the element start should be
     * rendered.
     * @param styleClass The style to append to the component's styleClass
     * property.
     * @param extraHtml Extra name/value pairs to be rendered.
     * @return String
     * @throws java.io.IOException if an IO error occurs
     */
    public static String renderStyleClass(FacesContext context,
            ResponseWriter writer, UIComponent component, String styleClass,
            String extraHtml) throws IOException {

        if (styleClass != null) {
            int first = -1;
            if (extraHtml != null
                    && (first = extraHtml.indexOf("class=")) != -1) {
                try {
                    // Concat given class value with styleClass attribute.
                    // Quote char index.
                    int quote = first + 6;
                    // Get quote char.
                    char ch = extraHtml.charAt(quote);
                    // Last index.
                    int last = extraHtml.indexOf(ch, quote + 1);
                    // Get name/value pair
                    String s = extraHtml.substring(first, last + 1);
                    // Remove substring.
                    extraHtml = extraHtml.replaceAll(s, "");
                    // Remove quote chars.
                    s = s.substring(7, s.length() - 1);
                    // Append styleClass.
                    styleClass = s + " " + styleClass;
                } catch (IndexOutOfBoundsException e) {
                }
            }
            renderStyleClass(context, writer, component, styleClass);
        }
        return extraHtml;
    }

    /**
     * Return {@code true} if we are we running in a portlet environment, as
     * opposed to a servlet based web application.
     *
     * @param context {@code FacesContext} for the current request
     * @return {@code true} if in a portlet environment, {@code false} otherwise
     */
    public static boolean isPortlet(FacesContext context) {
        return !(context.getExternalContext().getContext()
                instanceof ServletContext);
    }

    /**
     * Get the client ID of the last component to have focus.
     *
     * @param context faces context
     * @deprecated
     * @see
     * com.sun.webui.jsf.component.Body.getRequestFocusElementId(FacesContext)
     * @return String
     */
    public static String getLastClientID(FacesContext context) {
        return FocusManager.getRequestFocusElementId(context);
    }

    /**
     * Set the client ID of the last component to have focus.
     *
     * @param context faces context
     * @param clientId
     * @deprecated
     * @see
     * com.sun.webui.jsf.component.Body.setRequestFocusElementId(FacesContext,String)
     */
    public static void setLastClientID(FacesContext context,
            String clientId) {
        FocusManager.setRequestFocusElementId(context, clientId);
    }

    /**
     * Return the focus element id for {@code component}.The returned id should
     * be the id of an HTML element suitable to receive the focus.If
     * {@code component} is a {@code ComplexComponent} call
     * {@code component.getFocusElementId} else return
     * {@code component.getClientId}.
     *
     * @param context faces context
     * @param component component
     * @return String or {@code null} if component is null
     */
    public static String getFocusElementId(FacesContext context,
            UIComponent component) {

        if (component == null) {
            return null;
        }
        if (component instanceof ComplexComponent) {
            return ((ComplexComponent) component).getFocusElementId(context);
        } else {
            return component.getClientId(context);
        }
    }

    /**
     * Helper function to render a transparent spacer image.
     *
     * @param writer The current ResponseWriter
     * @param component The UI component
     * @param dotSrc
     * @param height The value to use for the image height attribute
     * @param width The value to use for the image width attribute
     * @throws java.io.IOException
     */
    public static void renderSpacer(ResponseWriter writer,
            UIComponent component, String dotSrc, int height, int width)
            throws IOException {

        if (height == 0 && width == 0) {
            return;
        }
        writer.startElement("img", component);
        writer.writeAttribute("src", dotSrc, null);
        writer.writeAttribute("alt", "", null);
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute("height", height, null);
        writer.writeAttribute("width", width, null);
        writer.endElement("img");
    }

    /**
     * Helper function to render a transparent spacer image.
     *
     * @param context faces context
     * @param writer The current ResponseWriter
     * @param component The UI component
     * @param height The value to use for the image height attribute
     * @param width The value to use for the image width attribute
     * @throws java.io.IOException
     */
    public static void renderSpacer(FacesContext context,
            ResponseWriter writer, UIComponent component, int height, int width)
            throws IOException {

        if (height == 0 && width == 0) {
            return;
        }
        Theme theme = ThemeUtilities.getTheme(context);
        String dotSrc = theme.getImagePath(ThemeImages.DOT);
        renderSpacer(writer, component, dotSrc, height, width);
    }

    /**
     * Returns an id suitable for the HTML label element's "for" attribute. The
     * returned id is obtained as follows.
     * <p>
     * <ul>
     * <li>The value of "id" is expected to be an absolute id and not a relative
     * id and will be prepended with {@code NamingContainer.SEPARATOR_CHAR} and resolved
     * to a component instance from the ViewRoot.
     * </li>
     * <li>If the id cannot be resolved, return the id argument.
     * </li>
     * <li>If the id can be resolved to a component instance, and it is an
     * instance of ComplexComponent, then the instance method
     * {@code getLebeledElementId} is called and the value returned, else
     * the value of {@code getClientId} is returned.
     * </li>
     * </ul>
     * </p>
     *
     * @param context The faces context
     * @param id The absolute client id of the component to be labeled.
     * @return An id suitable for an HTML LABEL element's "for" attribute.
     */
    public static String getLabeledElementId(FacesContext context, String id) {

        if (id == null || context == null) {
            return null;
        }

        String _id = id;

        char separatorChar = UINamingContainer.getSeparatorChar(context);
        if (id.charAt(0) != separatorChar) {
            _id = String.valueOf(separatorChar).concat(id);
        }

        UIComponent component = null;
        try {
            component = context.getViewRoot().findComponent(_id);
        } catch (Exception e) {
            if (LogUtil.fineEnabled()) {
                LogUtil.fine("Component with that particular id "
                        + "cannot be found");
            }
        }
        if (component == null) {
            return id;
        }

        if (component instanceof ComplexComponent) {
            return ((ComplexComponent) component).getLabeledElementId(context);
        }
        return component.getClientId(context);
    }

    /**
     * Helper function to render theme style sheet link(s)
     *
     *
     * @param context containing theme
     * @param theme
     * @param component The UI component
     * @param writer writer to use
     * @throws java.io.IOException if an IO error occurs
     */
    public static void renderStyleSheetLink(UIComponent component, Theme theme,
            FacesContext context, ResponseWriter writer)
            throws IOException {

        //Master.
        //String master = theme.getPathToMasterStylesheet(); 
        String[] files = theme.getMasterStylesheets();
        if (files != null && files.length != 0) {
            renderStylesheetLinks(files, component, writer);
        }
        // browser specific stylesheets
        //
        ClientType clientType = ClientSniffer.getClientType(context);
        files = theme.getStylesheets(clientType.toString());
        if (files != null && files.length != 0) {
            renderStylesheetLinks(files, component, writer);
        }

        // Global stylesheets
        //
        files = theme.getGlobalStylesheets();
        if (files != null && files.length != 0) {
            renderStylesheetLinks(files, component, writer);
        }
    }

    /**
     * Render {@code link} elements for {@code css} files.
     * @param css style sheets
     * @param component the UI component
     * @param writer write to use
     * @throws java.io.IOException if an IO error occurs
     */
    public static void renderStylesheetLinks(String[] css,
            UIComponent component, ResponseWriter writer) throws IOException {

        for (String cs : css) {
            writer.startElement(HTMLElements.LINK, component);
            writer.writeAttribute(HTMLAttributes.REL,
                    "stylesheet", null);
            writer.writeAttribute(HTMLAttributes.TYPE,
                    "text/css", null);
            writer.writeURIAttribute(HTMLAttributes.HREF, cs, null);
            writer.endElement(HTMLElements.LINK);
            writer.write("\n");
        }
    }

    /**
     * Helper function to render theme style sheet definitions inline
     *
     * @param context containing theme
     * @param theme
     * @param writer The current ResponseWriter
     * @param component The UI component
     * @throws java.io.IOException if an IO error occurs
     */
    public static void renderStyleSheetInline(UIComponent component,
            Theme theme, FacesContext context, ResponseWriter writer)
            throws IOException {

        writer.startElement(HTMLElements.STYLE, component);
        writer.writeAttribute(HTMLAttributes.TYPE, "text/css", null);
        writer.write("\n");

        String[] files = theme.getMasterStylesheets();
        if (files != null && files.length != 0) {
            renderImports(files, writer);
        }

        // browser specific stylesheets
        //
        ClientType clientType = ClientSniffer.getClientType(context);
        files = theme.getStylesheets(clientType.toString());
        if (files != null && files.length != 0) {
            renderImports(files, writer);
        }

        files = theme.getGlobalStylesheets();
        if (files != null && files.length != 0) {
            renderImports(files, writer);
        }

        writer.endElement(HTMLElements.STYLE);
    }

    /**
     * Render {@code import} directives for {@code imports}.
     * @param imports imports to render
     * @param writer writer to use
     * @throws java.io.IOException if an IO error occurs
     */
    public static void renderImports(String[] imports, ResponseWriter writer)
            throws IOException {

        for (String import1 : imports) {
            writer.write("@import(\"");
            writer.write(import1);
            writer.write("\");");
            writer.write("\n");
        }
    }

    /**
     * Perform a {@code RequestDispatcher.include} of the specified URI
     * {@code jspURI}.
     * <p>
     * The path identified by {@code jspURI} must begin with a
     * {@code &lt;f:subview&gt;} tag. The URI must not have as part of its
     * path the FacesServlet mapping. For example if the FacesServlet mapping
     * maps to {@code /faces/*} then {@code jspURI} must not have
     * {@code /faces/} as part of its path.
     * </p>
     * <p>
     * If {@code jspUIR} is a relative path then the request context path
     * is prepended to it.
     * </p>
     *
     * @param context the {@code FacesContext} for this request
     * @param writer the {@code ResponseWrite} destination for the rendered
     * output
     * @param jspURI the URI identifying a JSP page to be included.
     * @throws IOException if response can't be written or {@code jspURI}
     * cannot be included. Real cause is chained.
     */
    public static void includeJsp(FacesContext context, ResponseWriter writer,
            String jspURI) throws IOException {

        class ResponseWrapper extends HttpServletResponseWrapper {

            private final PrintWriter printWriter;

            public ResponseWrapper(HttpServletResponse response,
                    Writer writer) {
                super((HttpServletResponse) response);
                this.printWriter = new PrintWriter(writer);
            }

            @Override
            public PrintWriter getWriter() {
                return printWriter;
            }

            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                throw new IllegalStateException();
            }

            @Override
            public void resetBuffer() {
            }
        }

        if (jspURI == null) {
            return;
        }

        // prepend the request path if there is one in this path is not
        // a relative path. It appears that the servlet context algorithm
        // differs from the JspRuntime algorithm that allowed a relative
        // path in the lockhart wizard.
        //
        try {
            if (!jspURI.startsWith("/")) { //NOI18N
                String contextPath
                        = context.getExternalContext().getRequestContextPath();
                jspURI = contextPath.concat("/").concat(jspURI);
            }

            ServletRequest request
                    = (ServletRequest) context.getExternalContext().getRequest();
            ServletResponse response
                    = (ServletResponse) context.getExternalContext().getResponse();

            RequestDispatcher rd = request.getRequestDispatcher(jspURI);

            // JSF is already buffering and suppressing output.
            // 
            rd.include(request,
                    new ResponseWrapper((HttpServletResponse) response, writer));

        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * Helper method to render extra attributes.
     *
     * @param writer {@code ResponseWriter} to which the element end should
     * be rendered
     * @param extraHtml Extra HTML appended to the tag enclosing the header
     *
     * @exception IOException if an input/output error occurs
     */
    public static void renderExtraHtmlAttributes(ResponseWriter writer,
            String extraHtml) throws IOException {
        if (extraHtml == null) {
            return;
        }

        int n = extraHtml.length();
        int i = 0;
        while (i < n) {
            StringBuilder name = new StringBuilder();
            StringBuilder value = new StringBuilder();

            // Skip extra space characters.
            while (i < n && Character.isWhitespace(extraHtml.charAt(i))) {
                i++;
            }

            // Find name.
            for (; i < n; i++) {
                char c = extraHtml.charAt(i);
                if (c == '\'' || c == '"') {
                    return; // Not well formed.
                } else if (c == '=') {
                    break;
                } else {
                    name.append(c);
                }
            }
            i++; // Skip =

            // Process quote character.
            char quote = (i < n) ? extraHtml.charAt(i) : '\0';
            if (!(quote == '\'' || quote == '"')) {
                return; // Not well formed.
            }
            i++; // Skip quote character.

            // Find value.
            for (; i < n; i++) {
                char c = extraHtml.charAt(i);
                if (c == quote) {
                    break;
                } else {
                    value.append(c);
                }
            }
            i++; // Skip quote character.

            writer.writeAttribute(name.toString(), value.toString(), null);
        }
    }

    /**
     * Helper function to render a typical URL.
     * <p>
     * Note: Path must be a valid absolute URL or full path URI.
     * </p>
     *
     * @param context faces context
     * @param writer The current ResponseWriter
     * @param component The UI component
     * @param name The attribute name of the URL to write out
     * @param url The value passed in by the developer for the URL
     * @param compPropName The property name of the component's property that
     * specifies this property. Should be null if same as name.
     * @throws java.io.IOException if an IO error occurs
     *
     */
    public static void renderURLAttribute(FacesContext context,
            ResponseWriter writer, UIComponent component, String name,
            String url, String compPropName) throws IOException {

        if (url == null) {
            return;
        }

        Param paramList[] = getParamList(context, component);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int len = paramList.length;

        // Don't append context path here as themed images already include it.
        sb.append(url);
        if (0 < len) {
            sb.append("?");
        }
        for (i = 0; i < len; i++) {
            if (0 != i) {
                sb.append("&");
            }
            sb.append(paramList[i].getName());
            sb.append("=");
            sb.append(paramList[i].getValue());
        }

        String newName = null;
        if (compPropName != null) {
            newName = (compPropName.equals(name))
                    ? null
                    : compPropName;
        }

        // Note: Path must be a valid absolute URL or full path URI -- see
        // bugtraq #6306848 & #6322887.
        writer.writeURIAttribute(name, (url.trim().length() != 0)
                ? context.getExternalContext().encodeResourceURL(sb.toString())
                : "", newName);
    }

    static protected Param[] getParamList(FacesContext context,
            UIComponent command) {
        ArrayList<Param> parameterList = new ArrayList<Param>();

        for (UIComponent kid : command.getChildren()) {
            if (kid instanceof UIParameter) {
                UIParameter uiParam = (UIParameter) kid;
                Object value = uiParam.getValue();
                Param param = new Param(uiParam.getName(),
                        (value == null ? null
                                : value.toString()));
                parameterList.add(param);
            }
        }

        return (Param[]) parameterList.toArray(new Param[parameterList.size()]);
    }

    // inner class to store parameter name and value pairs
    static protected class Param {

        public Param(String name, String value) {
            set(name, value);
        }

        private String name;
        private String value;

        public void set(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Render an HREF for a "skip link".Note that "anchor" must be unique if
     * this method is called more than once to render on the same page.
     *
     * This method is written in such a way that you can use it without using
     * the component.
     *
     * @param anchorName name of the anchor
     * @param styleClass the CSS class
     * @param context faces context
     * @param toolTip the tool tip text
     * @param style the style
     * @param tabIndex tab index
     * @param component UI component
     * @throws java.io.IOException if an ERROR occurs
     */
    public static void renderSkipLink(String anchorName, String styleClass,
            String style, String toolTip, Integer tabIndex,
            UIComponent component, FacesContext context)
            throws IOException {

        ResponseWriter writer = context.getResponseWriter();

        writer.startElement("div", component);
        if (styleClass != null) {
            writer.writeAttribute("class", styleClass, null);
        } else {
            writer.writeAttribute("class", ThemeUtilities.getTheme(context).
                    getStyleClass(ThemeStyles.SKIP_WHITE), null);
        }
        if (style != null) {
            writer.writeAttribute("style", styleClass, null);
        }

        StringBuilder buffer = new StringBuilder(128);
        // Use this for the href and for the icon id
        buffer.append("#").
                append(component.getClientId(context)).
                append("_").
                append(anchorName);

        writer.startElement("a", component);
        writer.writeAttribute("href", buffer.toString(), null);

        // This is generating and XHTML invalid error.
        // It doesn't like "alt".
        String defaultAlt = ThemeUtilities.getTheme(context).getMessage(
                "skipLink.defaultAlt", new String[]{anchorName});
        if (toolTip != null) {
            writer.writeAttribute("alt", toolTip, null);
        } else {
            writer.writeAttribute("alt", defaultAlt, null); // GF 508 change
        }
        if (tabIndex != null) {
            writer.writeAttribute("tabindex", tabIndex.toString(), null);
        }

        Icon icon = ThemeUtilities.getIcon(ThemeUtilities.getTheme(context),
                ThemeImages.DOT);
        icon.setParent(component);
        icon.setWidth(1);
        icon.setHeight(1);
        icon.setBorder(0);
        icon.setToolTip((toolTip == null) ? defaultAlt : toolTip);

        buffer.setLength(0);
        buffer.append(anchorName).append("_icon");
        icon.setId(buffer.toString());

        RenderingUtilities.renderComponent(icon, context);

        writer.endElement("a");
        writer.endElement("div");
    }

    // This method is written in such a way that you can use it without
    // using the component.
    static public void renderAnchor(String anchorName, UIComponent component,
            FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        StringBuilder buffer = new StringBuilder();
        buffer.append(component.getClientId(context));
        buffer.append("_");
        buffer.append(anchorName);

        writer.startElement("div", component);
        writer.write("\n");
        writer.startElement("a", component);
        writer.writeAttribute("name", buffer.toString(), null);
        writer.endElement("a");
        writer.write("\n");
        writer.endElement("div");
    }

    /**
     * <p>
     * Return whether the given  {@code UIComponent} is "visible". If the
     * property is null, it will return true. Otherwise the value of the
     * property is returned.</p>
     *
     * @param component The {@code UIComponent} to check
     *
     * @return True if the property is null or true, false otherwise.
     */
    public static boolean isVisible(UIComponent component) {
        Object visible = component.getAttributes().get("visible");
        if (visible == null) {
            return true;
        } else {
            return ((Boolean) visible);
        }
    }
}
