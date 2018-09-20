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
 * $Id: JSFThemeContext.java,v 1.1.6.1 2009-12-29 04:57:17 jyeary Exp $
 */
package com.sun.webui.jsf.theme;

import java.beans.Beans;
import java.net.URL;
import java.util.Map;
import javax.faces.context.FacesContext;
import com.sun.webui.jsf.util.ClassLoaderFinder;
import com.sun.webui.theme.ServletThemeContext;
import com.sun.webui.theme.ThemeContext;

/**
 * <code>JSFThemeContext</code> encapsulates the theme JSF runtime
 * environment. It is different from other potential runtime environments
 * in that JSF encapsulates runtime contexts in a <code>FacesContext</code>.
 * This context could encapsulate a servlet context or a portlet context.
 * As such application information affecting a theme may be obtained
 * differently. This class encapsulates that behavior.
 */
public class JSFThemeContext extends ServletThemeContext {

    /**
     * An object to synchronize with.
     */
    private static Object synchObj = new Object();

    /**
     * Construction is controlled by <code>getInstance</code>.
     */
    protected JSFThemeContext(FacesContext context) {
        super(context.getExternalContext().getInitParameterMap());
    }

    // Note that since a ThemeServlet MUST be defined then 
    // getInstance should never have to create a ThemeContext instance.
    //
    /**
     * Return an instance of <code>ThemeContext</code> creating one
     * if necessary and persisting it in the <code>ApplicationMap</code>.
     */
    public static ThemeContext getInstance(FacesContext context) {

        // Does it make sense call a "super.getInstance()" ?
        //
        // In a servlet environment the JSF ApplicationMap is the
        // ServletContext and entires in the map are references by
        // ServletContext.{get/set}Attribute().
        // But a PortletContext is not a direct descendant of
        // ServletContext and there is not "interface compatible".
        //
        // We need synchronization here because there is one
        // ThemeContext per application servlet.
        //
        Map map = context.getExternalContext().getApplicationMap();
        ThemeContext themeContext = (ThemeContext) map.get(THEME_CONTEXT);
        if (themeContext == null) {

            //FIXME synchronization on a non-final field.
            synchronized (synchObj) {
                // try again in case another thread created it.
                //
                themeContext = (ThemeContext) map.get(THEME_CONTEXT);
                if (themeContext == null) {
                    themeContext = new JSFThemeContext(context);
                    map.put(THEME_CONTEXT, themeContext);
                }
            }
        }
        // It's not clear if this is necessary. Since this is a
        // JSFThemeContext, it would not be unreasonable to just
        // call "FacesContext.getCurrentInstance()" whenever
        // a value from that context is required like the
        // request context path, or referenced when calling
        // "ThemeContext.getResourcePath()".
        //
        //String path = context.getExternalContext().getRequestContextPath();
        //themeContext.setRequestContextPath(path);

        return themeContext;
    }

    /**
     * Return the default ClassLoader using ClassLoaderFinder.
     * ClassLoaderFinder encapsulates Creator requirements.
     */
    @Override
    public ClassLoader getDefaultClassLoader() {
        return ClassLoaderFinder.getCurrentLoader(JSFThemeContext.class);
    }

    /**
     * This implementation is a no-op. See ClassLoaderFinder.
     */
    @Override
    public void setDefaultClassLoader(ClassLoader classLoader) {
    }

    /**
     * This implementation always returns 
     * <code>FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()</code>.
     * This depends on the implementation of
     * <code>{@link com.sun.webui.theme.ThemeContext#getResourcePath()}</code>
     */
    @Override
    public String getRequestContextPath() {
        return FacesContext.getCurrentInstance().getExternalContext().
                getRequestContextPath();
    }

    /**
     * This implementation is a no-op.
     * @see #getRequestContextPath()
     */
    @Override
    public void setRequestContextPath(String path) {
    }

    /**
     * Return an appropriate resource path within this theme context.
     * Use is Beans.isDesignTime to prevent ThemeServlet prefix from
     * being appended. Creator handles doing the right thing with 
     * getRequestContextPath()
     */
    @Override
    public String getResourcePath(String path) {
        String resourcePath = path;
        if (Beans.isDesignTime()) {
            ClassLoader cl = getDefaultClassLoader();
            URL url = cl.getResource(path);
            if (url != null) {
                resourcePath = url.toExternalForm();
            }
        } else if (path != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            String servletContext = getThemeServletContext();
            StringBuilder sb = new StringBuilder(128);
            // Just to make sure
            //
            if (!servletContext.startsWith("/")) {
                sb.append("/");
            }
            sb.append(servletContext);
            if (!path.startsWith("/") && !servletContext.endsWith("/")) {
                sb.append("/");
            }
            sb.append(path);
            resourcePath = context.getApplication().getViewHandler().
                    getResourceURL(context, sb.toString());
        }
        return resourcePath;
    }
}
