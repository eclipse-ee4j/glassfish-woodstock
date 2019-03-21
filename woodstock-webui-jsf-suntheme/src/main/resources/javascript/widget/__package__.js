/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
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

dojo.provide("webui.suntheme.widget.*");

dojo.kwCompoundRequire({
    common: [
        "webui.suntheme.widget.props",
        "webui.suntheme.widget.common"]
});

// For debugging only, obtain all module resources before invoking
// dojo.hostenv.writeIncludes(). This will ensure that JavaScript
// files are accessible to JavaScript debuggers.
dojo.requireIf(djConfig.isDebug, "webui.suntheme.widget.button");
dojo.requireIf(djConfig.isDebug, "webui.suntheme.widget.progressBar");
dojo.requireIf(djConfig.isDebug, "webui.suntheme.widget.table2");
dojo.requireIf(djConfig.isDebug, "webui.suntheme.widget.table2RowGroup");

dojo.require("dojo.ns");
dojo.registerNamespace("webui.suntheme", "webui.suntheme.widget");
dojo.widget.manager.registerWidgetPackage("webui.suntheme.widget");

//-->
