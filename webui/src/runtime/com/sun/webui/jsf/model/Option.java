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

package com.sun.webui.jsf.model;

import javax.faces.model.SelectItem;

/**
 * <p>Model bean that represents a selectable choice in a selection
 * component such as <code>Menu</code>, <code>RadioButtonGroup</code>,
 * etc.
 * </p>
 */
public class Option extends SelectItem {

    private static final long serialVersionUID = -2164172320702956584L;
    private String image;
    // Zero is a valid width and height
    //
    private int imageWidth = -1;
    private int imageHeight = -1;
    private String imageAlt;
    private String tooltip;

    /**
     * Create an instance of Selection.
     */
    public Option() {
        super();
    }

    /**
     * Create an instance of Selection.
     */
    public Option(Object value) {
        super(value, null);
    }

    /**
     * Create an instance of Selection.
     */
    public Option(Object value, String label) {
        super(value, label);
    }

    /**
     * Create an instance of Selection.
     */
    public Option(Object value, String label, String description) {
        super(value, label, description);
    }

    /**
     * Create an instance of Selection.
     */
    public Option(Object value, String label, String description, boolean disabled) {
        super(value, label, description, disabled);
    }

    /**
     * Get the image resource path.
     */
    public String getImage() {
        return image;
    }

    /**
     * Set an image resource path
     * Used for an image in a radio button for example.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Get the image width.
     */
    public int getImageWidth() {
        return imageWidth;
    }

    /**
     * Set an image resource path
     * Used for an image in a radio button for example.
     */
    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    /**
     * Get the image height.
     */
    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * Set an image resource path
     * Used for an image in a radio button for example.
     */
    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    /**
     * Get the alternate text for the image.
     */
    public String getImageAlt() {
        return imageAlt;
    }

    /**
     * Set the alternate text for the image.
     */
    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    /**
     * Get the tooltip for this instance.
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * Set the tooltip for this instance.
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }
}
