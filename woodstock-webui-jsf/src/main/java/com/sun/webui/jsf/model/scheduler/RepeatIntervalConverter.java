/*
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.webui.jsf.model.scheduler;

import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.convert.Converter;
import javax.faces.context.FacesContext;

// Delete the setters once you have reimplemented this not to
// use the default Serializable mechanism, but the same as
// in the converter....
/**
 * Repeat interval converter.
 */
public final class RepeatIntervalConverter implements Converter, Serializable {

    /**
     * Serialization UID.
     */
    private static final long serialVersionUID = -1508546467189111720L;

    @Override
    public String getAsString(final FacesContext context,
            final UIComponent component, final Object value) {

        if (value == null) {
            return null;
        }
        return ((RepeatInterval) value).getRepresentation();
    }

    @Override
    public Object getAsObject(final FacesContext context,
            final UIComponent component, final String value) {

        return RepeatInterval.getInstance(value);
    }
}
