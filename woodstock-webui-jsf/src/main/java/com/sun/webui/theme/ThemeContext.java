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

package com.sun.webui.theme;

import java.util.Locale;
import java.util.Set;

/**
 * {@code ThemeContext} encapsulates the run-time environment for theme. The
 * run-time environment and dictates how information, required to locate theme
 * resources, and make those resources available to an application, is obtained.
 * For example:
 * <p>
 * <ul>
 * <li>In a {@code javax.servlet.Servlet} application environment information
 * about a theme and its resources is specified by the application in its
 * {@code web.xml} file
 * </li>
 * <li>In a Creator design time environment there is no need to prefix a theme
 * resource reference with a theme servlet prefix
 * </li>
 * <li>In a Sun Management Web Console environment that is sharing resources via
 * a specific servlet and class loader, the console determines the class loader
 * and theme servlet prefix.
 * </li>
 * <li>In a JSF environment information must be obtained from the
 * {@code FacesContext} which encapsulates servlet and portlet contexts.
 * </li>
 * </ul>
 * </p>
 */
public abstract class ThemeContext {

    /**
     * Identifies a theme messages bundle to override the message bundle in a
     * theme.
     *
     * @deprecated do not use
     */
    public final static String THEME_MESSAGES
            = "com.sun.webui.theme.THEME_MESSAGES";

    /**
     * Messages.
     * @deprecated do not use
     */
    private String messages;

    /**
     * Get the messages
     * @return messages
     * @deprecated do not use
     */
    public String getMessages() {
        return messages;
    }

    /**
     * Set the messages.
     * @param messages new messages
     * @deprecated do not use
     */
    public void setMessages(String messages) {
        this.messages = messages;
    }

    /**
     * Identifies a theme messages bundle to override the message bundle in a
     * theme.
     * @deprecated do not use
     */
    public final static String SUPPORTED_LOCALES
            = "com.sun.webui.theme.SUPPORTED_LOCALES";

    /**
     * The separator for the supported locales list.
     */
    protected static final String LOCALE_SEPARATOR = ",";

    /**
     * Supported locales.
     * @deprecated do not use
     */
    private Set supportedLocales;

    /**
     * Get the supported locales.
     * @return supported locales
     * @deprecated do not use
     */
    public Set getSupportedLocales() {
        return supportedLocales;
    }

    /**
     * Set the supported locales.
     * @param supportedLocales new supported locales
     * @deprecated do not use
     */
    public void setSupportedLocales(Set supportedLocales) {
        this.supportedLocales = supportedLocales;
    }

    /**
     * Intended as a key identifying a {@code ThemeContext} instance.
     */
    protected final static String THEME_CONTEXT
            = "com.sun.webui.theme.THEME_CONTEXT";

    /**
     * Identifies the context's default locale.
     */
    protected final static String DEFAULT_LOCALE
            = "com.sun.webui.theme.DEFAULT_LOCALE";

    /**
     * Identifies the context's default theme.
     */
    protected final static String DEFAULT_THEME
            = "com.sun.webui.theme.DEFAULT_THEME";

    /**
     * Identifies the context's desired theme version.
     */
    protected final static String DEFAULT_THEME_VERSION
            = "com.sun.webui.theme.DEFAULT_THEME_VERSION";

    /**
     * Identifies additional theme bundles.
     */
    protected final static String THEME_RESOURCES
            = "com.sun.webui.theme.THEME_RESOURCES";

    /**
     * Identifies the {@code ThemeFactory} class name.
     */
    protected final static String THEME_FACTORY_CLASS_NAME
            = "com.sun.webui.theme.THEME_FACTORY_CLASS_NAME";

    /**
     * Identifies the {@code ThemeServlet} context for obtaining resources via
     * HTTP.
     */
    protected final static String THEME_SERVLET_CONTEXT
            = "com.sun.webui.theme.THEME_SERVLET_CONTEXT";

    /**
     * The default locale for the default theme in this {@code ThemeContext}.
     */
    private Locale defaultLocale = Locale.getDefault();

    /**
     * The name of the default theme for this {@code ThemeContext}. If a
     * requested resource cannot be found in a specified theme then the default
     * theme will be used to obtain that resource. Should be configurable in the
     * environment like a system property but not only as a compile time
     * constant.
     */
    private String defaultTheme = null;

    /**
     * If more than one version of the default theme exists, the theme instance
     * with version equal to {@code defaultThemeVersion} will be used to obtain
     * theme resources. Need to firm up versioning semantics. Does specification
     * version and implementation make more sense than Major/Minor ?
     *
     * Should also be configurable from the runtime environment as system
     * property, versus just a compiled default.
     */
    private String defaultThemeVersion = null;

    /**
     * The absolute portion of the theme servlet's servlet-mapping url-pattern
     * element. For example if the theme servlet is configured as:
     * <p>
     * {@code
     * <pre>
     *     &lt;servlet-mapping&gt;
     *         &lt;servlet-name&gt;FacesServlet&lt;/servlet-name&gt;
     *         &lt;url-pattern&gt;faces/*&lt;/url-pattern&gt;
     *     &lt;/servlet-mapping&gt;
     * </pre>
     * }
     * </p>
     * <p>
     * Then the value of {@code themeServletContext} would be {@code theme}.
     * </p>
     * <p>
     * When trying to locate a specific theme resource this prefix is prepending
     * to the theme resource reference to locate the resource.
     * </p>
     */
    private String themeServletContext;

    /**
     * Bundle names of theme resources that augment a core theme. These
     * resources typically contain theme overrides and are referenced first
     * before a default or core theme.
     */
    private String[] themeResources;

    /**
     * The class name of a {@code ThemeFactory} implementation. This class will
     * be used to instantiate an instance of a {@code ThemeFactory}.
     */
    private String themeFactoryClassName;

    /**
     * The {@code ClassLoader} that this {@code ThemeContext} should use when
     * obtaining resources.
     */
    private ClassLoader defaultClassLoader;

    /**
     * The application context path as a path prefix that is prepended to theme
     * resource path references.
     */
    private String requestContextPath;

    /**
     * Theme factory.
     */
    private ThemeFactory themeFactory;

    /**
     * Construct a {@code ThemeContext}.
     */
    ThemeContext() {
    }

    public ThemeFactory getThemeFactory() {
        //FIXME double-checked locking
        synchronized (this) {
            if (themeFactory == null) {
                try {
                    themeFactory = (ThemeFactory) Class
                            .forName(getThemeFactoryClassName()).
                            newInstance();
                } catch (Exception e) {
                    // Use JarThemeFactory as the fallback default
                    // This should come from subclasses.
                    return (ThemeFactory) new JarThemeFactory();
                }
            }
        }
        return themeFactory;
    }

    /**
     * Return bundle names of theme resources that augment a core theme.These
     * resources typically contain theme overrides and are referenced first
     * before a default or core theme.
     *
     * @return theme resources
     */
    public String[] getThemeResources() {
        return themeResources;
    }

    /**
     * Set the bundle names of theme resources that augment a core theme.These
     * resources typically contain theme overrides and are referenced first
     * before a default or core theme.
     *
     * @param themeResources new theme resources
     */
    public void setThemeResources(String[] themeResources) {
        this.themeResources = themeResources;
    }

    /**
     * Return the application context path as a prefix that is prepended to
     * theme resource path references.
     *
     * @return request context path
     */
    public String getRequestContextPath() {
        return requestContextPath;
    }

    /**
     * Set the application context path prefix that is prepended to theme
     * resource path references.
     *
     * @param requestContextPath new request context path
     */
    public void setRequestContextPath(String requestContextPath) {
        this.requestContextPath = requestContextPath;
    }

    /**
     * Return a path used as a prefix that is prepended to a a theme resource
     * path reference.This implementation returns
     * {@code getRequestContextPath() + getThemeServletContext()}.
     *
     * @param path input path
     * @return resource path
     */
    public String getResourcePath(String path) {
        return getRequestContextPath() + getThemeServletContext()
                + (String) (path.startsWith("/") ? "/" + path : path);
    }

    /**
     * Set the default locale for the themes in this {@code ThemeContext}.
     *
     * @param defaultLocale new default locale
     */
    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    /**
     * Set the default locale for the themes in this {@code ThemeContext}.
     *
     * @param defaultLocale new default locale string
     */
    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = getLocale(defaultLocale);
    }

    /**
     * Return the default locale for thee themes in this {@code ThemeContext}.
     *
     * @return Locale
     */
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * Return the {@code ClassLoader} that this {@code ThemeContext} should use
     * when obtaining resources.
     *
     * @return default class-loader
     */
    public ClassLoader getDefaultClassLoader() {
        if (defaultClassLoader == null) {
            return this.getClass().getClassLoader();
        }
        return defaultClassLoader;
    }

    /**
     * Set the {@code ClassLoader} that this {@code ThemeContext} should use
     * when obtaining resources.
     *
     * @param defaultClassLoader new default class-loader
     */
    public void setDefaultClassLoader(ClassLoader defaultClassLoader) {
        this.defaultClassLoader = defaultClassLoader;
    }

    /**
     * Return the class name of a {@code ThemeFactory} implementation.This class
     * will be used to instantiate an instance of a {@code ThemeFactory}.
     *
     * @return theme factory class name
     */
    public String getThemeFactoryClassName() {
        return themeFactoryClassName;
    }

    /**
     * Set the class name of a {@code ThemeFactory} implementation.This class
     * will be used to instantiate an instance of a {@code ThemeFactory}.
     *
     * @param themeFactoryClassName new theme factory class name
     */
    public void setThemeFactoryClassName(String themeFactoryClassName) {
        this.themeFactoryClassName = themeFactoryClassName;
    }

    /**
     * Return the the name of the default theme.If a requested resource cannot
     * be found in a specified theme then the default theme will be used to
     * obtain that resource.
     *
     * @return default theme
     */
    public String getDefaultTheme() {
        return defaultTheme;
    }

    /**
     * Set the default theme name for this {@code ThemeContext}.If a requested
     * resource cannot be found in a specified theme then the default theme will
     * be used to obtain that resource.
     *
     * @param defaultTheme new default theme
     */
    public void setDefaultTheme(String defaultTheme) {
        this.defaultTheme = defaultTheme;
    }

    /**
     * Return the version of the default theme. If more than one version of the
     * default theme exists, the theme instance with version equal to
     * {@code defaultThemeVersion} will be used to obtain theme resources.
     *
     * @return default theme version
     */
    public String getDefaultThemeVersion() {
        return defaultThemeVersion;
    }

    /**
     * Set the version of the default theme.If more than one version of the
     * default theme exists, the {@code defaultThemeVersion} will be used to
     * obtain {@code Theme} resources.
     *
     * @param defaultThemeVersion new default theme version
     */
    public void setDefaultThemeVersion(String defaultThemeVersion) {
        this.defaultThemeVersion = defaultThemeVersion;
    }

    /**
     * Return a path prefix of a theme resource.When trying to locate a specific
     * theme resource this prefix is prepending to the theme resource identifier
     * to locate the resource. It is the same as the ThemeServlet's url-pattern,
     * less any "/*" specification.
     *
     * @return theme servlet context
     */
    public String getThemeServletContext() {
        return themeServletContext;
    }

    /**
     * Set a path prefix of a theme resource.When trying to locate a specific
     * theme resource this prefix is prepending to the theme resource identifier
     * to locate the resource. It is the same as the ThemeServlet's URL pattern,
     * less any "/*" specification.
     *
     * @param themeServletContext theme servlet context
     */
    public void setThemeServletContext(String themeServletContext) {
        this.themeServletContext = themeServletContext;
    }
    /**
     * If no version can be identified from one of the version methods, this
     * constant is returned.
     */
    public static final int NOVERSION = -1;

    /**
     * Flags for obtaining major and minor version components.
     */
    private enum Version {

        MAJOR, MINOR
    };

    /**
     * Version separator. Need to escape "." because the string is used as a
     * regular expression.
     */
    private static final String DOT = "\\.";

    // This also doesn't make sense in the presence of more than
    // one theme. Consider this an implementation detail for an
    // application, theme validation feature.
    // Or one of these is needed for every theme that exists in the
    // context. Is there one theme context for each theme ?
    //
    /**
     * Return the major version of the default theme version or
     * {@code ThemeContext.NOVERSION}.
     *
     * @return major version
     */
    public int getDefaultThemeMajorVersion() {
        return getVersionNumber(Version.MAJOR);
    }

    /**
     * Return the minor version of the default theme version or
     * {@code ThemeContext.NOVERSION}.
     *
     * @return minor version
     */
    public int getDefaultThemeMinorVersion() {
        return getVersionNumber(Version.MINOR);
    }

    /**
     * Convert the theme version to integer. The format is "MAJOR.MINOR" where
     * MAJOR and MINOR are integers.
     *
     * @param majorOrMinor major or minor
     */
    private int getVersionNumber(Version majorOrMinor) {
        int version = NOVERSION;
        if (getDefaultThemeVersion() == null) {
            return NOVERSION;
        }
        try {
            String[] majmin = getDefaultThemeVersion().split(DOT);
            switch (majorOrMinor) {
                case MAJOR:
                    version = Integer.parseInt(majmin[0]);
                    break;
                case MINOR:
                    version = Integer.parseInt(majmin[1]);
                    break;
            }
        } catch (Exception e) {
            //Log error
        }
        return version;
    }

    /**
     * Get a {@code Locale} from a {@code String} of the form
     * {@code language[_country[_variant]]}.
     *
     * @param localeString locale string
     * @return Locale
     */
    private Locale getLocale(String localeString) {
        if (localeString == null) {
            return null;
        }
        localeString = localeString.trim();
        if (localeString.length() == 0) {
            return null;
        }
        Locale locale = null;
        String[] strings = localeString.split("_");
        if (strings.length > 2) {
            locale = new Locale(strings[0], strings[1], strings[2]);
        } else if (strings.length > 1) {
            locale = new Locale(strings[0], strings[1]);
        } else if (strings.length > 0) {
            locale = new Locale(strings[0]);
        }
        return locale;
    }
}
