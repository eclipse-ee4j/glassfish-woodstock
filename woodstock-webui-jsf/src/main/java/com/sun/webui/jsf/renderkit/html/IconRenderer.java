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
 * IconRenderer.java
 *
 * Created on November 16, 2004, 2:29 PM
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;

/**
 * Renders an Icon component. An Icon is essentially an Image picked from the 
 * current theme via the Theme Image property. The Icon component will
 * automatically output the appropriate image element attributes (i.e. height,
 * width) for the specified theme image identifier.
 * 
 * @author  Sean Comerford
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Icon"))
public class IconRenderer extends ImageRenderer {
}
