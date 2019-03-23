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

import com.sun.faces.annotation.Renderer;
import java.util.List;
import java.util.Iterator;
import javax.el.MethodExpression;
import com.sun.webui.jsf.component.ImageHyperlink;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.IconHyperlink;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.component.TreeNode;
import com.sun.webui.jsf.component.Tree;
import com.sun.webui.jsf.util.ComponentUtilities;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionListener;

import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;

/**
 * <p>Renderer for a {@link TreeNode} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.TreeNode"))
public class TreeNodeRenderer extends javax.faces.render.Renderer {

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
     * Does nothing.
     * 
     * @param context The current FacesContext
     * @param component The Property object to render
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
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

        if (context == null || component == null) {
            throw new NullPointerException();
        }

        if (!component.isRendered()) {
            return;
        }

        ResponseWriter writer = context.getResponseWriter();
        TreeNode node = (TreeNode) component;

        // Get the theme
        //
        Theme theme = ThemeUtilities.getTheme(context);

        Tree root = node.getAbsoluteRoot(node);
        boolean csFlag = root.isClientSide();
        // boolean esFlag = root.isExpandOnSelect();


        // Check if the TreeNode has children. If so, render each child which 
        // in turn will invoke methods of this class.

        // Check if the treeNode has facets, if so render these facets and
        // disregard the corresponding attribute values that these facets 
        // override.

        // If no facets, simply render according to the attribute values or 
        // assume default values wherever possible

        writer.startElement(HTMLElements.DIV, node); //start of node div
        writer.writeAttribute(HTMLAttributes.ID, node.getClientId(context), null);

        // Set styleclass. First check if node is hidden. Else check if styleclass
        // attribute has been supplied. Else set the default styleclass.

        String nodeStyleClass = theme.getStyleClass(ThemeStyles.TREE_ROW);
        if (!node.isVisible()) {
            nodeStyleClass = theme.getStyleClass(ThemeStyles.HIDDEN);
        } else if (node.getStyleClass() != null) {
            nodeStyleClass = node.getStyleClass();
        }
        writer.writeAttribute(HTMLAttributes.CLASS, nodeStyleClass, null);

        if (node.getStyle() != null) {
            writer.writeAttribute(HTMLAttributes.STYLE,
                    node.getStyle(), null);
        }
        // Getting the list of images for a given row before they are
        // used for the first time.

        Iterator imageIter = node.getImageKeys().iterator();

        IconHyperlink ihl = node.getTurnerImageHyperlink();
        UIComponent imageFacet = null;
        if (ihl != null) {
            imageFacet = ihl.getImageFacet();
        }
        StringBuffer buff = new StringBuffer();
        buff.append(JavaScriptUtilities.getDomNode(context, root)).append(".onTreeNodeClick(this, '");
        if (imageFacet != null) {
            buff.append(imageFacet.getClientId(context));
        } else {
            buff.append("null");
        }
        buff.append("', event);");

        writer.writeAttribute(HTMLAttributes.ONCLICK,
                buff.toString(), null);

        //writer.writeText("\n", null);

        // Render the treeRow now. Passing the retrived
        // iterator so that the same task does not have to
        // be repeated inside the method.

        renderTreeRow(node, imageIter, context, writer);
        writer.endElement(HTMLElements.DIV); // treerow div ends
        //writer.writeText("\n", null);
        List<UIComponent> children = node.getChildren();


        // If node is expanded or if its a client side tree we need to lay
        // down the next level of child nodes. Child nodes will be visible
        // only if node is visible and the client side node handler 
        // is meant to show the nodes.
        // 

        if (node.isExpanded() || csFlag) {

            //writer.writeText("\n", null);
            writer.startElement(HTMLElements.DIV, node);
            writer.writeAttribute(HTMLAttributes.ID,
                    node.getClientId(context) + "_children", null);
            if (!node.isVisible()) {
                writer.writeAttribute(HTMLAttributes.CLASS,
                        theme.getStyleClass(ThemeStyles.HIDDEN), null);
            }
            if (!node.isExpanded()) {
                writer.writeAttribute(HTMLAttributes.STYLE, "display:none", null);
            }
            // writer.writeText("\n", null);

            Iterator<UIComponent> iter = children.iterator();
            while (iter.hasNext()) {
		RenderingUtilities.renderComponent(iter.next(), context);
            }

            writer.endElement(HTMLElements.DIV);
        // writer.writeText("\n", null);
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
        if (context == null || component == null) {
            throw new NullPointerException();
        }
    }

    private void setToolTip(ImageHyperlink ihl, TreeNode node) {
        ihl.setToolTip(node.getText() + " node");  // GF-required 508 change
        ihl.setAlt(node.getText() + " node image");  // GF-required 508 change
    }

    /**
     * Renders each row of the tree. A tree row consists of a set of images
     * followed by the actual row image or text.
     *
     * @param node The TreeNode object whose row is to be rendered
     * @param context The current FacesContext
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderTreeRow(TreeNode node, Iterator imageIter,
            FacesContext context, ResponseWriter writer) throws IOException {

        Tree root = node.getAbsoluteRoot(node);

        Theme theme = ThemeUtilities.getTheme(context);

        writer.startElement(HTMLElements.DIV, node); //image div
        String lineimagesID = node.getClientId(context) + "LineImages";
        writer.writeAttribute(HTMLAttributes.ID, lineimagesID, null);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.FLOAT), null);

        // render turner and other images before the actual 
        // data for a given tree row.

        // Iterator imageIter = node.getImageKeys().iterator();
        int count = 0;
        while (imageIter.hasNext()) {
            // read each image IconHyperlink or ImageHyperlink and render it
            UIComponent imageComp = (UIComponent) imageIter.next();
            RenderingUtilities.renderComponent(imageComp, context);
        //writer.writeText("\n", null);
        }

        // check if image facet has been supplied. If so, render it.
        UIComponent imageFacet = node.getFacet(Tree.TREE_IMAGE_FACET_NAME);
        if (imageFacet != null) {
            if (imageFacet instanceof ImageHyperlink) {
                ImageHyperlink ihl = (ImageHyperlink) imageFacet;
                setToolTip(ihl, node);
            }
            RenderingUtilities.renderComponent(imageFacet, context);
        } else {
            String imageURL = node.getImageURL();
            if (imageURL != null && imageURL.length() > 0) {
                ImageHyperlink ihl =
                        node.getNodeImageHyperlink();
                setToolTip(ihl, node);
                renderImageOrText(node, ihl, context);
            }
        }
        //writer.writeText("\n", null);
        writer.endElement(HTMLElements.DIV); // handler/image div ends here
        //writer.writeText("\n", null);

        // check if content facet has been supplied. If so, render it.
        UIComponent contentFacet = node.getFacet(Tree.TREE_CONTENT_FACET_NAME);
        writer.startElement(HTMLElements.DIV, node);
        writer.writeAttribute(HTMLAttributes.ID, node.getClientId(context) + "Text", null);
        String textStyle = theme.getStyleClass(ThemeStyles.TREE_CONTENT) + " " +
                theme.getStyleClass(ThemeStyles.TREE_NODE_IMAGE_HEIGHT);
        writer.writeAttribute(HTMLAttributes.CLASS, textStyle, null);
        //writer.writeText("\n", null);

        if (contentFacet != null) {
            RenderingUtilities.renderComponent(contentFacet, context);

        } else { // render text attribute value if supplied
            String treeText = node.getText();
            String nodeURL = node.getUrl();
            boolean hasText = ((treeText != null) && (treeText.length() > 0));
            boolean hasURL = ((nodeURL != null) && (nodeURL.length() > 0));
            MethodExpression mex = node.getActionExpression();
            boolean hasAction = (mex != null);

            if (hasURL || hasAction) {
                Hyperlink link = node.getContentHyperlink(); // new Hyperlink();
                renderImageOrText(node, link, context);
            } else if (treeText != null && treeText.length() > 0) {
                writer.write(treeText);
            }
        }
        //writer.writeText("\n", null);

        writer.endElement(HTMLElements.DIV); // content DIV ends
    //writer.writeText("\n", null);
    }

    private void renderImageOrText(TreeNode node, Hyperlink link,
            FacesContext context) throws IOException {

        Tree root = node.getAbsoluteRoot(node);
        boolean csFlag = root.isClientSide();
        boolean esFlag = root.isExpandOnSelect();
        String nodeURL = node.getUrl();
        MethodExpression mex = node.getActionExpression();
        ActionListener[] nodeListeners = node.getActionListeners();
        boolean hasURL = ((nodeURL != null) && (nodeURL.length() > 0));
        boolean hasAction = (mex != null);

        if (hasURL || hasAction) {
            String jsObject = JavaScriptUtilities.getDomNode(context, root);
            if (hasAction || ((nodeListeners != null) && (nodeListeners.length > 0))) {

                link.setOnClick(jsObject +
                        ".treecontent_submit('" + node.getClientId(context) + "');");  //NOI18N
            } else {
                if (esFlag) {
                    IconHyperlink ihl =
                            (IconHyperlink) ComponentUtilities.getPrivateFacet(node,
                            "turner", true);
                    UIComponent iconImage = null;
                    StringBuffer buff = new StringBuffer();
                    buff.append(jsObject).append(".selectTreeNode(") //NOI18N
                            .append(jsObject).append(".findContainingTreeNode(this).id);") //NOI18N
                            .append(jsObject).append(".expandTurner(this, '"); //NOI18N
                    if (ihl != null) {
                        buff.append(ihl.getClientId(context));
                        iconImage = ihl.getImageFacet();
                    } else {
                        buff.append("null");
                    }
                    buff.append("', '");
                    if (iconImage != null) {
                        buff.append(iconImage.getClientId(context));
                    } else {
                        buff.append("null");
                    }
                    buff.append("', ").append(csFlag).append(", event);");
                    link.setOnClick(buff.toString());
                }
            }
            RenderingUtilities.renderComponent(link, context);
        }
    }
}
