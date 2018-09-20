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
 * $Id: ResourceModelException.java,v 1.1.20.1 2009-12-29 03:47:57 jyeary Exp $
 */
package com.sun.webui.jsf.model;

import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;

/**
 *
 * Need to make this an interface as well.
 * And in our implementation log the exception as well.
 */
public class ResourceModelException extends ValidatorException {

    private static final long serialVersionUID = -8267067702146533999L;

    public ResourceModelException(FacesMessage fmsg) {
        super(fmsg);
    }

    public ResourceModelException(FacesMessage fmsg, Throwable cause) {
        super(fmsg, cause);
    }
}
