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
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>

<f:view>
  <webuijsf:page>
    <webuijsf:head title="#{JavaHelpBean.tipsHeadTitle}" />
    <webuijsf:body styleClass="#{JavaHelpBean.bodyClassName}">
      <webuijsf:form id="tips">              
       <h2><webuijsf:staticText text="#{JavaHelpBean.tipsTitle}" /></h2>

<p><webuijsf:staticText text="#{JavaHelpBean.tipsImprove}" /></p>
<ul>
 <li><webuijsf:staticText text="#{JavaHelpBean.tipsImprove1}" /></li>

 <li><webuijsf:staticText text="#{JavaHelpBean.tipsImprove2}" /></li>
   
 <li><webuijsf:staticText text="#{JavaHelpBean.tipsImprove3}" /></li>
   
 <li><webuijsf:staticText text="#{JavaHelpBean.tipsImprove4}" /></li>
</ul>
<p>
<b><webuijsf:staticText text="#{JavaHelpBean.tipsNote}" /></b> <webuijsf:staticText text="#{JavaHelpBean.tipsNoteDetails}" />
</p>  
<p><webuijsf:staticText text="#{JavaHelpBean.tipsSearch}" /></p>

<ul>
  <li><webuijsf:staticText text="#{JavaHelpBean.tipsSearch1}" /></li>
  <li><webuijsf:staticText text="#{JavaHelpBean.tipsSearch2}" /></li>
  <li><webuijsf:staticText text="#{JavaHelpBean.tipsSearch3}" /></li>
  <li><webuijsf:staticText text="#{JavaHelpBean.tipsSearch4}" /></li>
</ul>
      </webuijsf:form>      
    </webuijsf:body> 
  </webuijsf:page>
</f:view>
