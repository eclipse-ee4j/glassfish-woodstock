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

package com.sun.webui.jsf.renderkit.html;

import java.util.Iterator;
import com.sun.webui.jsf.component.Tree;
import com.sun.webui.jsf.component.TreeNode;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.LogUtil;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.application.FacesMessage;
import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;
import org.json.JSONObject;

/**
 * <p>Renderer for a {@link Tree} component.</p>
 */
@com.sun.faces.annotation.Renderer(@com.sun.faces.annotation.Renderer.Renders(componentFamily = "com.sun.webui.jsf.Tree"))
public class TreeRenderer extends TreeNodeRenderer {

    private static final String SKIPTREE_LINK = "skipTreeLink";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Does nothing
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be decoded
     *
     * @exception NullPointerException if <code>context</code> or
     *  <code>component</code> is <code>null</code>
     */
    @Override
    public void decode(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
    }

    /**
     * Render a property component.
     *
     * @param context The current FacesContext
     * @param component The Property object to render
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {

        Iterator messages = context.getMessages();
        if (messages != null) {
            while (messages.hasNext()) {
                FacesMessage fm = (FacesMessage) messages.next();
                LogUtil.fine(fm.getSummary());
                LogUtil.fine(fm.getDetail());
            }
        }

        if (context == null || component == null) {
            throw new NullPointerException();
        }

        if (!component.isRendered()) {
            return;
        }

        ResponseWriter writer = context.getResponseWriter();
        Tree node = (Tree) component;

        // Get the theme
        //
        Theme theme = ThemeUtilities.getTheme(context);

        // The title bar can be defined with either ui:tree tag attributes or facets. 
        // The title bar is rendered if the tree component includes imageURL property 
        // for the graphic, the text property for the title text, the content facet, 
        // or the image facet.

        // render outermost div of the tree.

        writer.write("\n\n\n");
        writer.startElement(HTMLElements.DIV, node);
        writer.writeAttribute(HTMLAttributes.ID, node.getClientId(context), null);
        String nodeStyleClass = theme.getStyleClass(ThemeStyles.TREE);
        if (!node.isVisible()) {
            nodeStyleClass = theme.getStyleClass(ThemeStyles.HIDDEN);
        } else if (node.getStyleClass() != null) {
            nodeStyleClass = node.getStyleClass();
        }
        writer.writeAttribute(HTMLAttributes.CLASS, nodeStyleClass, null);
        if (node.getStyle() != null) {
            writer.writeAttribute(HTMLAttributes.STYLE, node.getStyle(), null);
        }
        writer.write("\n");

        // render the skip hyper link to support A11Y
        RenderingUtilities.renderSkipLink(SKIPTREE_LINK,
                theme.getStyleClass(ThemeStyles.SKIP_WHITE), null,
                theme.getMessage("tree.skipTagAltText"), // NOI18N
                null, node, context);
        writer.write("\n");

        // add the spacer
        writer.write("\n");
        String rootText = node.getText();
        String rootImageURL = node.getImageURL();
        boolean hasRootContentFacet =
                (node.getFacet(Tree.TREE_CONTENT_FACET_NAME) != null);
        boolean hasRootImageFacet =
                (node.getFacet(Tree.TREE_IMAGE_FACET_NAME) != null);

        if ((rootText != null && rootText.length() > 0) ||
                rootImageURL != null ||
                hasRootImageFacet || hasRootContentFacet) {

            String titlebarSpacerDivID = node.getClientId(context) + "TitleBarSpacer";
            String titlebarDivID = node.getClientId(context) + "TitleBar";
            String lineImageDivID = node.getClientId(context) + "LineImages";
            String lineTxtDivID = node.getClientId(context) + "LineText";

            // title bar spacer
            writer.startElement(HTMLElements.DIV, node);
            writer.writeAttribute(HTMLAttributes.ID, titlebarSpacerDivID, null);
            writer.writeAttribute(HTMLAttributes.CLASS,
                    theme.getStyleClass(ThemeStyles.TREE_ROOT_ROW_HEADER), null);
            writer.endElement(HTMLElements.DIV);
            writer.write("\n"); // NOI18N

            writer.startElement(HTMLElements.DIV, node); // tree root row start
            writer.writeAttribute(HTMLAttributes.ID, titlebarDivID, null);
            writer.writeAttribute(HTMLAttributes.CLASS,
                    theme.getStyleClass(ThemeStyles.TREE_ROOT_ROW), null);
            writer.write("\n");
            Iterator imageIter = node.getImageKeys().iterator();
            if (((node.getUrl() != null) && (node.getUrl().length() > 0)) ||
                    hasRootContentFacet) {

                renderTreeRow(node, imageIter, context, writer);

            } else {

                writer.write("\n"); // NOI18N
                writer.startElement(HTMLElements.SPAN, node);
                writer.writeAttribute(HTMLAttributes.CLASS,
                        theme.getStyleClass(ThemeStyles.TREE_TITLE), null);
                writer.write("\n"); // NOI18N
                renderTreeRow(node, imageIter, context, writer);
                writer.endElement(HTMLElements.SPAN);
            }
            writer.endElement(HTMLElements.DIV);

        }

        // Check if the TreeNode has children. If so, render each child which
        // in turn would cause each of the descendent nodes to get rendered.

        Iterator<UIComponent> iter = node.getChildren().iterator();

        //writer.writeText("\n", null);
        String clientID = node.getClientId(context);
        writer.startElement(HTMLElements.DIV, node);
        writer.writeAttribute(HTMLAttributes.ID,
                clientID + "_children", null);
        while (iter.hasNext()) {
	    RenderingUtilities.renderComponent(iter.next(), context);
        }

        writer.endElement(HTMLElements.DIV);
        //writer.writeText("\n", null);

        String nodeID = null;
        if (node.getSelected() != null) {
            String childID = (String) node.getSelected();
            TreeNode childNode = node.getChildNode(childID);
            if (childNode != null) {
                nodeID = childNode.getClientId(context);
            }
        }

        try {
            // Render JavaScript to initialize tree.
            StringBuffer buff = new StringBuffer(256);
            JSONObject json = new JSONObject();
            json.put("id", clientID);

            // Append JavaScript.
            String jsObject = JavaScriptUtilities.getDomNode(context, node);
//            buff.append(JavaScriptUtilities.getModule("tree")).append("\n") // NOI18N
            buff.append("require(['").append(JavaScriptUtilities.getModuleName("tree")).append("'], function (tree) {").append("\n") // NOI18N
//                    .append(JavaScriptUtilities.getModuleName("tree.init")) // NOI18N
                    .append("tree.init") // NOI18N
                    .append("(") //NOI18N
                    .append(json.toString(JavaScriptUtilities.INDENT_FACTOR)).append(");\n"); //NOI18N

            if (nodeID != null) {
                buff.append("tree").append(".selectTreeNode('").append(nodeID).append("');");
            } else {
                buff.append("tree").append(".updateHighlight('").append(clientID).append("');");
            }
            buff.append("});");

            // Render JavaScript.
            JavaScriptUtilities.renderJavaScript(component, writer,
                    buff.toString());

            // Render skip anchor.
            RenderingUtilities.renderAnchor(SKIPTREE_LINK, node, context);
            writer.write("\n"); // NOI18N
            writer.endElement(HTMLElements.DIV);
        } catch (Exception e) {
            LogUtil.warning(e.getMessage(), e);
        }
    }

    /**
     * Does nothing.
     * 
     * @param context The current FacesContext
     * @param component The Property object to render
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        // Do nothing...          
    }
}
