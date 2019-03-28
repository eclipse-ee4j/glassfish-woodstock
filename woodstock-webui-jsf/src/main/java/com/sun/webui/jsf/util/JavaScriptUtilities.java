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

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.component.ProgressBar;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeJavascript;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.theme.ThemeTemplates;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class provides common methods for rendering JavaScript includes, default
 * properties, etc.
 */
public class JavaScriptUtilities {
    // The number of spaces to add to each level of indentation.
    public static final int INDENT_FACTOR = 4;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // JavaScript config methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get JavaScript used to configure Dojo.
     *
     * Note: Must be rendered before including dojo.js in page.
     *
     * @param debug Enable JavaScript debugging.
     * @param parseWidgets Enable searching of dojoType widget tags.
     */
    public static String getDojoConfig(boolean debug, boolean parseWidgets) {
        Theme theme = getTheme();
        StringBuffer buff = new StringBuffer(256);

        try {
            JSONObject json = new JSONObject();
            JSONObject json1 = new JSONObject();
            json1.put("webui/suntheme", "../../com/sun/webui/jsf/suntheme/javascript");
            json.put("isDebug", debug)
                .put("debugAtAllCosts", debug)
                .put("parseWidgets", parseWidgets)
                .put("async", true)
                .put("paths", json1);

            buff.append("var dojoConfig=")
                .append(json.toString(INDENT_FACTOR))
                .append(";\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }    
        return buff.toString();
    }

    /**
     * Get JavaScript used to configure module resources.
     *
     * @param writeIncludes Write includes for JavaScript debugging.
     */
    public static String getModuleConfig(boolean writeIncludes) {
        Theme theme = getTheme();
        StringBuffer buff = new StringBuffer(256);

        // Append JavaScript.
//        buff.append("dojo.hostenv.setModulePrefix(\"")
////            .append(getTheme().getJSString(ThemeJavascript.MODULE_PREFIX))
//            .append("webui/suntheme")
//            .append("\", \"")
//            .append(theme.getPathToJSFile(ThemeJavascript.MODULE_PATH))
//            .append("\");\n")
//            .append(getModule("*"))
//            .append("\n");
        // Output includes for debugging. This will ensure that JavaScript
        // files are accessible to JavaScript debuggers.
        if (writeIncludes) {
            buff.append(getModule("widget.*"))
                .append("\n")
                .append(getModule("widget.jsfx.*"))
                .append("\n")
                .append("dojo.hostenv.writeIncludes();")
                .append("\n");
        }
        return buff.toString();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // JavaScript include methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to render JavaScript include.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderDojoInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        renderJavaScriptIncludeDojo(component, writer, ThemeJavascript.DOJO);
    }

    /**
     * Helper method to render JavaScript include.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     */
    public static void renderGlobalInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        String javascripts[] = getTheme().getGlobalJSFiles();
        if (javascripts == null) {
            return;
        }
        for (int i = 0; i < javascripts.length; i++) {
            Object file = javascripts[i];
            if (file == null) {
                continue;
            }
            writer.startElement("script", component);
            writer.writeAttribute("type", "text/javascript", null);
            writer.writeURIAttribute("src", file.toString(), null);
            writer.endElement("script");
            writer.write("\n");
        }
    }

    /**
     * Helper method to render JavaScript include.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderJsfxInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        renderJavaScriptInclude(component, writer, ThemeJavascript.JSFX);
    }

    /**
     * Helper method to render JavaScript include.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderJsonInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        renderJavaScriptInclude(component, writer, ThemeJavascript.JSON);
    }

    /**
     * Helper method to render JavaScript include.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderPrototypeInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        renderJavaScriptInclude(component, writer, ThemeJavascript.PROTOTYPE);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Rendering methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Returns JavaScript to obtain the DOM node associated with the given
     * component.
     * 
     * When complex components are rendered, a DOM object corresponding to the
     * component is created. To manipulate the component on the client side, we
     * will invoke functions on the DOM object.
     * 
     * Providing a component, with a client id of "form1:btn1", will return
     * "document.getElementById('form1:btn1')". This JavaScript obtains the
     * DOM object associated the HTML element. Thus, we can disable a button 
     * using "document.getElementById('form1:btn1').disable(true);"
     *
     * @param context The current FacesContext.
     * @param component The current component being rendered.
     * @param name The JavaScript object name to append.
     */
    public static String getDomNode(FacesContext context,
            UIComponent component) {
        StringBuffer buff = new StringBuffer(128);
        buff.append("document.getElementById('")
            .append(component.getClientId(context))
            .append("')");
        return buff.toString();
    }

    /**
     * Returns JavaScript used to require a Dojo module. For example, a value of
     * For example, a value of "widget.*" will return
     * "dojo.require('webui.suntheme.widget.*')" for a theme, named 
     * "suntheme".
     *
     * @param name The JavaScript object name to append.
     */
    public static String getModule(String name) {
        StringBuffer buff = new StringBuffer(128);
        buff.append("require([\"")
            .append(getModuleName(name))
            .append("\"]);");
        return buff.toString();
    }

    /**
     * Returns a string comprised of a theme prifix and the given module name.
     * For example, a value of "widget.button" will return 
     * "webui.suntheme.widget.button" for a theme, named "suntheme".
     *
     * @param name The module to append to the theme prefix.
     */
    public static String getModuleName(String name) {
        StringBuffer buff = new StringBuffer(128);
//        buff.append(getTheme().getJSString(ThemeJavascript.MODULE_PREFIX))
        buff.append("webui/suntheme")
            .append("/")
            .append(name);
        return buff.toString();
    }

    /**
     * Returns a string comprised of a theme prifix and the given widget name.
     * For example, a value of "button" will return "webui.suntheme:button" for
     * a theme, named "suntheme".
     *
     * @param name The widget name to append to the namespace prefix.
     */
    public static String getNamespace(String name) {
        StringBuffer buff = new StringBuffer(128);
//        buff.append(getTheme().getJSString(ThemeJavascript.MODULE_PREFIX))
        buff.append("webui/suntheme")
            .append(":")
            .append(name);
        return buff.toString();
    }

    /**
     * Render JavaScript in the page, including enclosing script tags.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     * @param js The JavaScript string to render.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderJavaScript(UIComponent component,
            ResponseWriter writer, String js) throws IOException {
        if (js == null) {
            return;
        }
        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", null);
        writer.write("\n");
        writer.write(js);
        writer.endElement("script");
        writer.write("\n");
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Helper method to get Theme objects.
    private static Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }

    /**
     * Render given JavaScript file in page, including script tags.
     *
     * Note: JavaScript includes must be output in page prior to instantiating
     * widgets. This can be done via the head, themeLinks, or portalTheme tags,
     * but not via any other component renderer. Thus, this method is declared
     * private to ovoid misuse.
     * 
     * If JavaScript includes are output by renderers, timing issues can occur 
     * when client-side widgets and server-side components are rendered in the 
     * same page. For example, button HTML may be rendered as a JSON property
     * (the child of a widget), which also contains a JavaScript include. In 
     * this scenario, ther buttons in the page may not initialize correctly
     * because the widget has not added the JavaScript include, yet. See CR 6517246.
     *
     * @param component The current component being rendered.
     * @param writer The current ResponseWriter.
     * @param file The JavaScript file to include.
     */
    private static void renderJavaScriptInclude(UIComponent component,
            ResponseWriter writer, String file) throws  IOException {
        if (file == null) {
	    return;
	}

	String jsFile = getTheme().getPathToJSFile(file);
	if (jsFile == null) {
	    return;
	}

        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", null);
        writer.writeURIAttribute("src", jsFile, null);
        writer.endElement("script");
        writer.write("\n");
    }
    
    private static void renderJavaScriptIncludeDojo(UIComponent component,
            ResponseWriter writer, String file) throws  IOException {
        if (file == null) {
	    return;
	}

	String jsFile = getTheme().getPathToJSFile(file);
	if (jsFile == null) {
	    return;
	}

        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", null);
//        writer.writeAttribute("data-dojo-config", "async: true", null);
        writer.writeURIAttribute("src", jsFile, null);
        writer.endElement("script");
        writer.write("\n");
    }
}
