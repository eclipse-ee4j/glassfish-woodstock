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

package com.sun.webui.jsf.component;

import com.sun.webui.jsf.component.util.DesignUtil;
import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.PropertyDescriptor;
import com.sun.rave.designtime.Constants;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.ImageHyperlink} component.
 */
public class ImageHyperlinkBeanInfo extends ImageHyperlinkBeanInfoBase {

    public ImageHyperlinkBeanInfo() {
        DesignUtil.hideProperties(this, new String[]{"action", "value"});
        this.getBeanDescriptor().setValue(
            Constants.BeanDescriptor.INLINE_EDITABLE_PROPERTIES,
            new String[] { "*text://a" }); // NOI18N

        // This is set here rather than using <resize-constraints> metadata
        // in conf-image-renderer.xml because we need to do a bitwise
        // or of two constants, which the DTD (or code generator) doesn't
        // allow
        this.getBeanDescriptor().setValue(Constants.BeanDescriptor.RESIZE_CONSTRAINTS,
            new Integer(Constants.ResizeConstraints.MAINTAIN_ASPECT_RATIO|Constants.ResizeConstraints.ANY));
    }

    private EventSetDescriptor[] eventSetDescriptors;

    public EventSetDescriptor[] getEventSetDescriptors() {
        if (eventSetDescriptors == null)
            eventSetDescriptors = DesignUtil.generateCommandEventSetDescriptors(this);
        return eventSetDescriptors;
    }
}
