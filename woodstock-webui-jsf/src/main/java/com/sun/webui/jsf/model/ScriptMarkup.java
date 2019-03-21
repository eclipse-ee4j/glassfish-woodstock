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
 * $Id: ScriptMarkup.java,v 1.1.20.1 2009-12-29 03:47:57 jyeary Exp $
 */
package com.sun.webui.jsf.model;

/**
 * <p>Specialized version of {@link Markup} that automatically surrounds
 * any accumulated markup in this element with the required prolog and
 * epilogue strings for an embedded script element.</p>
 */
public class ScriptMarkup extends Markup {


    // ----------------------------------------------------- Instance Variables
    /**
     * <p>The CDATA wrapping flag for this markup.</p>
     */
    private boolean cdata = false;


    // ------------------------------------------------------------- Properties
    /**
     * <p>Return the current state of CDATA wrapping for this markup.</p>
     */
    public boolean isCdata() {

        return this.cdata;

    }

    /**
     * <p>Set the new state of CDATA wrapping for this markup.</p>
     *
     * @param cdata New wrapping flag
     */
    public void setCdata(boolean cdata) {

        this.cdata = cdata;

    }

    /**
     * <p>Return the accumulated markup for this element, surrounded by the
     * required prolog and epilog strings for an embedded script element.</p>
     */
    @Override
    public String getMarkup() {

        StringBuffer sb = new StringBuffer("<script type=\"text/javascript\">"); //NOI18N
        if (isCdata()) {
            sb.append("<![CDATA["); //NOI18N
        }
        sb.append("\n"); //NOI18N
        sb.append(super.getMarkup());
        sb.append("\n"); //NOI18N
        if (isCdata()) {
            sb.append("]]>"); //NOI18N
        }
        sb.append("</script>\n"); //NOI18N
        return sb.toString();
    }
}
