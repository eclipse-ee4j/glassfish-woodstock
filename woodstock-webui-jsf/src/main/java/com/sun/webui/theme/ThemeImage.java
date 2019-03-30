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

/* $Id: ThemeImage.java,v 1.1.6.1 2009-12-29 05:05:17 jyeary Exp $ */
package com.sun.webui.theme;

/**
 * {@code ThemeImage} encapsulates the properties of a theme image
 * resource. If a theme provides an image resource it is desirable that the
 * theme also be configured with the details of that image in addition
 * to the path location of the resource, such as the
 * height, width, the units that the dimensions are expressed in,
 * alt text if the browser cannot load the image, and a title
 * that acts as a tool tip in a browser environment.
 */
public class ThemeImage {

    /**
     * The suffix applied to the theme property key that defines an image
     * for the height of the image expressed in the units defined for the units
     * member.
     */
    public static final String HEIGHT_SUFFIX = "_HEIGHT";
    /**
     * The suffix applied to the theme property key that defines an image
     * for the width of the image expressed in the units defined for the units
     * member.
     */
    public static final String WIDTH_SUFFIX = "_WIDTH";
    /**
     * The suffix applied to the theme property key that defines an image
     * for the units to expressed by the width and height properties.
     */
    public static final String UNITS_SUFFIX = "_UNITS";
    /**
     * The suffix applied to the theme property key that defines an image
     * for the alt text for the image if the resource cannot be loaded 
     * in the client.
     */
    public static final String ALT_SUFFIX = "_ALT";
    /**
     * The suffix applied to the theme property key that defines an image
     * for the title attribute for the image and used for a tooltip
     * in the client.
     */
    public static final String TITLE_SUFFIX = "_TITLE";

    /**
     * Defines the recognized units that the height and width can be
     * expressed in.
     */
    public enum UNITS {
        px, em, percent, none
    };

    private int width;
    private int height;
    private UNITS units;
    private String alt;
    private String title;
    private String path;

    /**
     * Construct a {@code ThemeImage} from the passed arguments.
     * @param width image width
     * @param height image height
     * @param units size units
     * @param alt alt attribute value
     * @param title image title
     * @param path image path
     */
    public ThemeImage(int width, int height, ThemeImage.UNITS units,
            String alt, String title, String path) {

        this.width = width;
        this.height = height;
        this.units = units;
        this.alt = alt;
        this.title = title;
        this.path = path;
    }

    /**
     * Returns the width of the image in units defined in the 
     * {@code units} member.
     * @return width
     * @see #getUnits()
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the image in units defined in the 
     * {@code units} member.
     * @return height
     * @see #getUnits()
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the {@code UNITS} expressed in the {@code height}
     * and {@code width}  members.{@code units} member.
     * @return size units
     * @see #getHeight()
     * @see #setHeight(int)
     * @see #getWidth()
     * @see #setWidth(int)
     */
    public ThemeImage.UNITS getUnits() {
        return units;
    }

    /**
     * Returns the {@code alt} member or alternate text to display if the
     * client cannot render the image.
     * @return alt attribute value
     */
    public String getAlt() {
        return alt;
    }

    /**
     * Returns the {@code title} member sometimes used to display a
     * tool tip on the client.
     * @return image title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the application or jar relative path of this image resource.The
     * path is relative to location that can be found on the application class
     * path.
     *
     * @return image path
     */
    public String getPath() {
        return path;
    }

    /**
     * Set the width of the image in units defined by the {@code units}
     * member.
     * @param width new width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Set the height of the image in units defined by the {@code units}
     * member.
     * @param height new height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Set the units used by the {@code height} and {@code width}
     * members.
     * @param units new units
     */
    public void setUnits(ThemeImage.UNITS units) {
        this.units = units;
    }

    /**
     * Set the text that will appear on the client if the image cannot be
     * loaded.
     * @param alt new alt
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }

    /**
     * Set the text that may appear as a tool tip on the client.
     * @param title new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the application or jar relative path of the image. 
     * The path is relative to location that can be found on the application
     * class path. This path must begin with a "/".
     * @param path new path
     */
    public void setPath(String path) {
        this.path = path;
    }
}
