<%--

    Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.

    This program and the accompanying materials are made available under the
    terms of the Eclipse Public License v. 2.0, which is available at
    http://www.eclipse.org/legal/epl-2.0.

    This Source Code may also be made available under the following Secondary
    Licenses when the conditions for such availability set forth in the
    Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
    version 2 with the GNU Classpath Exception, which is available at
    https://www.gnu.org/software/classpath/license.html.

    SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0

--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>

<%
  // set certain browser dependent frame style attributes
  String outerFramesetRows = "100";
  String middleFramesetSpacing = "";
  String frameBorderTrue = "yes";
  String frameBorderFalse = "no";
  String middleFramesetBorderColor = "";
  String navFrameBorder = "yes";
  String navFrameScrolling = "auto";
  String innerFramesetSpacing = "";
  String innerFramesetBorderColor = "";
  String buttonFrameBorder = "yes";
  
  if (request.getHeader("USER-AGENT").indexOf("MSIE") >= 0) {
      // set the style attrs for IE
      outerFramesetRows = "104";
      middleFramesetSpacing = "\n framespacing=\"2\"";
      innerFramesetSpacing = "\n framespacing=\"1\"";
      frameBorderTrue = "1";
      frameBorderFalse = "0";
      middleFramesetBorderColor = "\n bordercolor=\"#CCCCCC\"";
      navFrameBorder = "1";
      navFrameScrolling = "auto";
      innerFramesetSpacing = "\nf ramespacing=\"1\"";
      innerFramesetBorderColor = "\n bordercolor=\"#939CA3\"";
      buttonFrameBorder = "1";
  }
  
  // get the query params from the helpwindow link
  String windowTitle = request.getParameter("windowTitle") != null ?
      request.getParameter("windowTitle") : "";
  String helpFile = request.getParameter("helpFile") != null ?
      request.getParameter("helpFile") : "";
  %>

<f:view>
  <HTML>
    <HEAD><TITLE><%=windowTitle%></TITLE></HEAD>
 
<!-- Frameset for Nav, ButtonNav, and Content frames -->
<frameset cols="33%,67%"
 frameborder="<%=frameBorderTrue%>"
 border="1">

<!-- Nav Frame -->
<frame src="<h:outputText value="#{JavaHelpBean.navigatorUrl}"/>"
 name="navFrame"
 frameBorder="<%=navFrameBorder%>"
 scrolling="<%=navFrameScrolling%>"
 id="navFrame"
 title="<h:outputText value="#{JavaHelpBean.navFrameTitle}" />" />

<!-- Frameset for ButtonNav and Content Frames -->
<frameset rows="32,*"
 frameborder="<%=frameBorderTrue%>"
 border="0">

<!-- ButtonNav Frame -->
<frame src="<h:outputText value="#{JavaHelpBean.buttonFrameUrl}"/>"
 name="buttonNavFrame"
 frameBorder="<%=buttonFrameBorder%>"
 scrolling="no" border="0"
 id="buttonNavFrame"
 title="<h:outputText value="#{JavaHelpBean.buttonFrameTitle}" />" />

<!-- Content Frame -->
<frame src="<h:outputText value="#{JavaHelpBean.localizedHelpPath}" /><%=helpFile %>"
 name="contentFrame" border="1"
 frameBorder="<%=frameBorderTrue%>"
 scrolling="auto"
 id="contentFrame"
 title="<h:outputText value="#{JavaHelpBean.contentFrameTitle}" />" />

</frameset>
</frameset>

<noframes>
<body>
<webuijsf:staticText id="noFramesText" text="#{JavaHelpBean.noFrames}" />
</body>
</noframes>

  </HTML>
</f:view>
