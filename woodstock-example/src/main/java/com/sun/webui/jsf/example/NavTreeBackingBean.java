/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.webui.jsf.example;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.context.FacesContext;

import com.sun.webui.jsf.component.Tree;
import com.sun.webui.jsf.component.TreeNode;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.ImageHyperlink;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.example.util.ClientSniffer;
import com.sun.webui.jsf.example.util.MessageUtil;
import com.sun.webui.jsf.theme.ThemeImages;

/**
 * Backing bean for Dynamic Tree example.
 */
public final class NavTreeBackingBean implements Serializable {

    /**
     * Outcome strings used in the faces config.
     */
    public static final String SHOW_NAV_TREE = "showNavTree";

    /**
     * Outcome strings used in the faces config.
     */
    public static final String SHOW_TREE_INDEX = "showTreeIndex";

    /**
     * Tree document image.
     */
    public static final String TREE_DOCUMENT = ThemeImages.TREE_DOCUMENT;

    /**
     * Tree folder image.
     */
    public static final String TREE_FOLDER = ThemeImages.TREE_FOLDER;

    /**
     * Client sniffer.
     */
    private ClientSniffer cs;

    /**
     * Tree component.
     */
    private Tree tree = null;

    /**
     * Node clicked counter.
     */
    private int nodeClicked = 0;

    /**
     * Breadcrumbs rendered flag.
     */
    private Boolean breadcrumbsRendered = Boolean.FALSE;

    /**
     * Due to a bug, the root node is not selectable.
     */
    private static final boolean ROOTNODESELECTABLE = false;

    /**
     * Default constructor.
     */
    public NavTreeBackingBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        cs = new ClientSniffer(context);
    }

    /**
     * Get the Tree object.
     * @return Tree
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public Tree getTree() {
        if (tree != null) {
            return tree;
        }

        // Create root of tree
        tree = new Tree();
        tree.setId("navtree");
        tree.setText("Node 0");
        tree.setClientSide(true);

        // Until root node is selectable, we do not create a facet for
        // the content, but only for the image via an ImageComponent
        // that is, it's not a link.
        if (ROOTNODESELECTABLE) {
            tree.getFacets().put("content",
                    nodeLink(tree.getText(), 0));
            tree.getFacets().put("image",
                    nodeImage(tree.getText(), 0, TREE_FOLDER));
        } else {
            ImageComponent imageComponent = new ImageComponent();
            imageComponent.setId(nodeImageID(0));
            tree.getFacets().put("image", imageComponent);
            setNodeImage(tree, TREE_FOLDER);
        }
        makeToolTip(tree, TREE_FOLDER);

        // Create a series of 2nd-level child nodes, and a couple of 3rd-level
        // grand-child nodes.
        for (int i = 10; i < 21; i += 10) {
            TreeNode n, c;

            // 2nd-level child node
            n = addTreeNode(tree, "Node " + i, i, TREE_FOLDER);

            // 3rd-level grandchild nodes
            c = addTreeNode(n, "Node " + (i + 1), (i + 1), TREE_DOCUMENT);
            c = addTreeNode(n, "Node " + (i + 2), (i + 2), TREE_DOCUMENT);
        }

        // Create a 2nd-level folder child of node10, and then create some
        // 3rd-level grand-child nodes.
        TreeNode node10 = tree.getChildNode(treeNodeID(10));
        if (node10 != null) {
            TreeNode node30 = addTreeNode(node10, "Node 30", 30, TREE_FOLDER);
            addTreeNode(node30, "Node 31", 31, TREE_DOCUMENT);
            addTreeNode(node30, "Node 32", 32, TREE_DOCUMENT);
            addTreeNode(node30, "Node 33", 33, TREE_DOCUMENT);
        }

        return tree;
    }

    /**
     * Set the Tree Object.
     * @param newTree tree
     */
    public void setTree(final Tree newTree) {
        this.tree = newTree;
    }

    /**
     * Get the 'rows' attribute value for the outer frame-set based on the
     * client browser.
     *
     * @return String
     */
    public String getOuterFramesetRows() {
        if (cs.isIe6up()) {
            return "68,*";
        }

        // assume default is Mozilla-based
        return "75,*";
    }

    /**
     * Return the node number of the tree node that was clicked.
     * @return String
     */
    public String getNodeClicked() {
        String param = (String) FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().
                get("nodeClicked");
        try {
            nodeClicked = Integer.parseInt(param);
        } catch (NumberFormatException e) {
            nodeClicked = 0;
            return "Nothing";
        }

        return Integer.toString(nodeClicked);
    }

    /**
     * Return the rendered status of the bread-crumbs.
     * @return String
     */
    public String getBreadcrumbsRendered() {
        getNodeClicked();
        if (nodeClicked == 0) {
            breadcrumbsRendered = Boolean.FALSE;
        } else {
            breadcrumbsRendered = Boolean.TRUE;
        }
        return breadcrumbsRendered.toString();
    }

    /**
     * Return the parentage path for the currently selected node.
     * @return Hyperlink[]
     */
    public Hyperlink[] getPageList() {

        // No path needed if not rendering breadcrumbs
        getBreadcrumbsRendered();
        if (!breadcrumbsRendered) {
            return null;
        }

        // No path needed for the root node
        if (nodeClicked == 0) {
            return null;
        }

        // Get the node object of the selected node,
        // with usual protection from Murphy's Law!
        TreeNode node = tree.getChildNode(treeNodeID(nodeClicked));
        if (node == null) {
            return null;
        }

        // Build up an array ancestry nodes starting with the selection
        ArrayList<TreeNode> ancestry = new ArrayList<TreeNode>();
        ancestry.add(node);
        TreeNode parent = TreeNode.getParentTreeNode(node);
        while (parent != null) {
            ancestry.add(parent);
            parent = TreeNode.getParentTreeNode(parent);
        }

        // Create the breadcrumbs for the ancestry, in the reverse
        // order of the node array list.
        Hyperlink[] pages = new Hyperlink[ancestry.size()];
        int n = 0;
        for (int i = ancestry.size() - 1; i >= 0; i--) {
            node = (TreeNode) ancestry.get(i);
            Hyperlink link = new Hyperlink();

            // Breadcrumb link text same as node's content facet if it
            // exists, otherwise from the node.
            Hyperlink facet = (Hyperlink) node.getFacets().get("content");
            if (facet != null) {
                link.setText(facet.getText());
            } else {
                link.setText(node.getText());
            }

            // Config what happens when this link is clicked
            link.setTarget("contentsFrame");
            try {
                String toolTip = MessageUtil.getMessage(
                        "tree_contentBreadcrumbToolip",
                        (String) link.getText());

                // Re-render the contents frame to show which node is selected.
                int nodeNum = getNodeNum(node);
                if (!ROOTNODESELECTABLE && (nodeNum == 0)) {
                    link.setUrl("content.jspx");
                    toolTip += " (root node currently not selectable)";
                } else {
                    link.setUrl("content.jspx?nodeClicked=" + nodeNum);
                }

                link.setToolTip(toolTip);
                link.setId(breadcrumbLinkID(nodeNum));

                // Yoke to this node in the tree.
                FacesContext context = FacesContext.getCurrentInstance();
                String clientID = node.getClientId(context);
                if (!ROOTNODESELECTABLE && (nodeNum == 0)) {
                    link.setOnClick(
                            "javascript:ClearHighlight(); return true;");
                } else {
                    link.setOnClick("javascript:YokeToNode('" + clientID
                            + "'); return true;");
                }
            } catch (Exception e) {
            }
            pages[n++] = link;
        }
        return pages;
    }

    /**
     * Action handler when navigating to the navigation tree example.
     * @return String
     */
    public String showNavTree() {
        return SHOW_NAV_TREE;
    }

    /**
     * Action handler when navigating to the main example index.
     * @return String
     */
    public String showExampleIndex() {
        reset();
        return IndexBackingBean.INDEX_ACTION;
    }

    /**
     * Action handler when navigating to the tree example index.
     * @return String
     */
    public String showTreeIndex() {
        reset();
        return SHOW_TREE_INDEX;
    }

    /**
     * Reset everything back to initial state.
     */
    private void reset() {
        nodeClicked = 0;
        breadcrumbsRendered = Boolean.FALSE;
        tree = null;
    }

    /**
     * Create a new tree node child of a specified parent node.
     * @param parent parent node to add to
     * @param label text for the new node
     * @param nodeNum id for the new node
     * @param icon image for the new node
     * @return TreeNode
     */
    private static TreeNode addTreeNode(final TreeNode parent,
            final String label, final int nodeNum, final String icon) {

        TreeNode node = new TreeNode();
        node.setText(label);
        node.setId(treeNodeID(nodeNum));
        node.getFacets().put("image", nodeImage(label, nodeNum, icon));
        node.getFacets().put("content", nodeLink(label, nodeNum));
        makeToolTip(node, icon);

        parent.getChildren().add(node);
        return node;
    }

    /**
     * Return a Hyperlink object.
     * @param label link text
     * @param nodeNum link id
     * @return Hyperlink
     */
    private static Hyperlink nodeLink(final String label, final int nodeNum) {
        Hyperlink link = new Hyperlink();
        link.setId(nodeLinkID(nodeNum));
        link.setText(label);
        link.setTarget("contentsFrame");
        link.setUrl("content.jspx?nodeClicked=" + nodeNum);
        return link;
    }

    /**
     * Return an ImageHyperlink object.
     * @param label link text
     * @param nodeNum link id
     * @param icon link image
     * @return ImageHyperlink
     */
    private static ImageHyperlink nodeImage(final String label,
            final int nodeNum, final String icon) {

        ImageHyperlink link = new ImageHyperlink();
        link.setId(nodeImageID(nodeNum));
        setNodeImage(link, icon);
        link.setTarget("contentsFrame");
        link.setUrl("content.jspx?nodeClicked=" + nodeNum);
        return link;
    }

    /**
     * Set the image for a specified tree node.
     * @param node node to set the image of
     * @param icon image to set
     */
    private static void setNodeImage(final TreeNode node, final String icon) {
        setNodeImage((Object) (node.getFacets().get("image")), icon);
        makeToolTip(node, icon);
    }

    /**
     * Set the image for a specified image component.
     * @param component component to set the image of
     * @param icon image to set
     */
    private static void setNodeImage(final Object component,
            final String icon) {

        if (component == null) {
            return;
        }

        if (component instanceof ImageHyperlink) {
            ((ImageHyperlink) component).setIcon(icon);
        }
        if (component instanceof ImageComponent) {
            ((ImageComponent) component).setIcon(icon);
        }
    }

    /**
     * Compose the tool-tip and set it on all facets of the node.
     * @param node node tot set the tool tip of
     * @param icon image
     */
    private static void makeToolTip(final TreeNode node, final String icon) {
        String tip = node.getText();
        Object o = (Object) (node.getFacets().get("image"));
        if (o instanceof ImageHyperlink) {
            ((ImageHyperlink) o).setToolTip(tip);
        }
        if (o instanceof ImageComponent) {
            ((ImageComponent) o).setToolTip(tip);
        }
        o = (Object) (node.getFacets().get("content"));
        if (o instanceof Hyperlink) {
            ((Hyperlink) o).setToolTip(tip);
        } else {
            node.setToolTip(tip);
        }
    }

    // Because we're identifying node with an integer, and JSF requires
    // IDs to be start with a letter or underscore, we provide convenience
    // methods for creating unique IDs from an integer.
    /**
     * Return a valid component ID for a treeNode.
     * @param nodeNum node ID
     * @return String
     */
    private static String treeNodeID(final int nodeNum) {
        return "Node" + nodeNum;
    }

    /**
     * Return a valid component ID for a treeNode content facet.
     * @param nodeNum node ID
     * @return String
     */
    private static String nodeLinkID(final int nodeNum) {
        return treeNodeID(nodeNum) + "Link";
    }

    /**
     * Return a valid component ID for a treeNode image facet.
     * @param nodeNum node ID
     * @return String
     */
    private static String nodeImageID(final int nodeNum) {
        return treeNodeID(nodeNum) + "Image";
    }

    /**
     * Return a valid component ID for a breadcrumb link.
     * @param nodeNum node ID
     * @return String
     */
    private static String breadcrumbLinkID(final int nodeNum) {
        return "Breadcrumb" + nodeNum;
    }

    /**
     * Extract and return the node number from the specified component ID.
     * @param id component id
     * @return int
     */
    private static int getNodeNum(final String id) {
        String nodeNum = id;
        nodeNum = nodeNum.replace("Node", "");
        nodeNum = nodeNum.replace("Image", "");
        nodeNum = nodeNum.replace("Link", "");
        nodeNum = nodeNum.replace("Breadcrumb", "");
        return Integer.parseInt(nodeNum);
    }

    /**
     * Extract and return the node number from the specified TreeNode.
     * @param node node to get the ID of
     * @return int
     */
    private static int getNodeNum(final TreeNode node) {
        Object o = (Object) (node.getFacets().get("content"));
        if (o instanceof Hyperlink) {
            return getNodeNum(((Hyperlink) o).getId());
        }
        o = (Object) (node.getFacets().get("image"));
        if (o instanceof ImageHyperlink) {
            return getNodeNum(((ImageHyperlink) o).getId());
        }
        if (o instanceof ImageComponent) {
            return getNodeNum(((ImageComponent) o).getId());
        }
        return getNodeNum(node.getId());
    }
}
