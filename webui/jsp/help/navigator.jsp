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
    <webuijsf:head title="#{JavaHelpBean.navigatorHeadTitle}" />
    <webuijsf:body styleClass="#{JavaHelpBean.bodyClassName}">
      <webuijsf:form id="helpNavigatorForm">
        <webuijsf:markup tag="div" styleClass="#{JavaHelpBean.stepTabClassName}">
            <webuijsf:tabSet id="javaHelpTabSet" mini="true">
                <webuijsf:tab id="contentsTab" text="#{JavaHelpBean.contentsText}" 
                              actionExpression="#{JavaHelpBean.contentsTabClicked}" />
                <webuijsf:tab id="indexTab" text="#{JavaHelpBean.indexText}" 
                              actionExpression="#{JavaHelpBean.indexTabClicked}" />
                <webuijsf:tab id="searchTab" text="#{JavaHelpBean.searchText}" 
                              actionExpression="#{JavaHelpBean.searchTabClicked}" />
            </webuijsf:tabSet>
        </webuijsf:markup> 
        <div style="margin-left: 10px;">
        <webuijsf:tree binding="#{JavaHelpBean.contentsTree}" />        
        <webuijsf:tree binding="#{JavaHelpBean.indexTree}" />        
        </div>
        <webuijsf:panelGroup binding="#{JavaHelpBean.searchPanel}">
          <webuijsf:markup tag="div" styleClass="#{JavaHelpBean.searchClassName}">
          <webuijsf:textField id="searchText" />
          <webuijsf:button id="searchButton" text="#{JavaHelpBean.searchLabel}" 
	      actionExpression="#{JavaHelpBean.doSearch}" />
          <webuijsf:markup tag="div" styleClass="#{JavaHelpBean.inlineHelpClassName}">
              <webuijsf:hyperlink binding="#{JavaHelpBean.tipsLink}" />
          </webuijsf:markup>
          <f:verbatim>
            <table border="0" cellspacing="0" cellpadding="0" width="98%">
            <tr><td>
          </f:verbatim>
          <webuijsf:image icon="DOT" alt="" border="0" height="5" width="1" />
          <f:verbatim></td></tr><tr></f:verbatim>
          <webuijsf:markup tag="td" styleClass="#{JavaHelpBean.titleClassName}">
              <webuijsf:image icon="DOT" alt="" border="0" height="1" width="1" />
          </webuijsf:markup>
          <f:verbatim>
            </tr><tr><td>
          </f:verbatim>
          <webuijsf:image icon="DOT" alt="" border="0" height="5" width="1" />
          <f:verbatim>
            </td></tr></table>
          </f:verbatim>
          <webuijsf:panelGroup binding="#{JavaHelpBean.searchResultsPanel}" />
          </webuijsf:markup>
        </webuijsf:panelGroup>
      </webuijsf:form>      
    </webuijsf:body> 
  </webuijsf:page>
</f:view>
