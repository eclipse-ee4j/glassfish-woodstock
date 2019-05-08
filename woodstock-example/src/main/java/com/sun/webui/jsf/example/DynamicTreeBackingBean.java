/*
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
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
import javax.faces.event.ActionEvent;
import javax.faces.component.UIComponentBase;
import com.sun.webui.jsf.component.Tree;
import com.sun.webui.jsf.component.TreeNode;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.ImageHyperlink;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionTitle;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.example.util.ExampleUtilities;
import com.sun.webui.jsf.example.util.MessageUtil;

/**
 * Backing bean for Dynamic Tree example.
 */
public final class DynamicTreeBackingBean implements Serializable {

    /**
     * Outcome strings used in the faces config.
     */
    public static final String SHOW_DYNAMIC_TREE = "showDynamicTree";

    /**
     * Outcome strings used in the faces config.
     */
    public static final String SHOW_TREE_INDEX = "showTreeIndex";

    // Constants for tree node images.  For all leaf nodes that require
    // a badging of the alarm severity with the document images, we had to
    // create our own images which are loaded from within the app's
    // context.  For all others we simply create mappings to the names
    // of the themed icons.

    //CHECKSTYLE:OFF
    public static final String TREE_DOCUMENT_ALARM_MAJOR
            = "/images/tree_document_major.gif";
    public static final String TREE_DOCUMENT_ALARM_MINOR
            = "/images/tree_document_minor.gif";
    public static final String TREE_DOCUMENT_ALARM_DOWN
            = "/images/tree_document_down.gif";
    public static final String TREE_DOCUMENT_ALARM_CRITICAL
            = "/images/tree_document_critial.gif";
    public static final String TREE_DOCUMENT
            = ThemeImages.TREE_DOCUMENT;
    public static final String TREE_FOLDER
            = ThemeImages.TREE_FOLDER;
    public static final String TREE_FOLDER_ALARM_MAJOR
            = ThemeImages.TREE_FOLDER_ALARM_MAJOR;
    public static final String TREE_FOLDER_ALARM_MINOR
            = ThemeImages.TREE_FOLDER_ALARM_MINOR;
    public static final String TREE_FOLDER_ALARM_DOWN
            = ThemeImages.TREE_FOLDER_ALARM_DOWN;
    public static final String TREE_FOLDER_ALARM_CRITICAL
            = ThemeImages.TREE_FOLDER_ALARM_CRITICAL;
    //CHECKSTYLE:ON

    /**
     * The tree component.
     */
    private Tree tree = null;

    /**
     * Alert detail.
     */
    private String alertDetail = null;

    /**
     * Alert rendered flag.
     */
    private Boolean alertRendered = Boolean.FALSE;

    /**
     * Options.
     */
    private Option[] testCaseOptions = null;

    /**
     * Constructor.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public DynamicTreeBackingBean() {
        testCaseOptions = new Option[3];
        testCaseOptions[0] = new OptionTitle(
                MessageUtil.getMessage("tree_testCase_title"));
        testCaseOptions[1] = new Option("tree_testCase_reload",
                MessageUtil.getMessage("tree_testCase_reload"));
        testCaseOptions[2] = new Option("tree_testCase_yoke34",
                MessageUtil.getMessage("tree_testCase_yoke34"));
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
        tree.setId("DynamicTree");
        tree.setText("Node 0");

        // At this time, the root node is not selectable. Until it is,
        // we do not create a facet for the content, but only for the image
        // via an ImageComponent - that is, it's not a link.
        boolean rootNodeSelectable = false;
        if (rootNodeSelectable) {
            tree.getFacets().put("content",
                    nodeLink(tree.getText(), 0));
            tree.getFacets().put("image",
                    nodeImage(tree.getText(), 0, TREE_FOLDER_ALARM_DOWN));
        } else {
            ImageComponent imageComponent = new ImageComponent();
            imageComponent.setId(nodeImageID(0));
            tree.getFacets().put("image", imageComponent);
            setNodeImage(tree, TREE_FOLDER_ALARM_DOWN);
        }
        makeToolTip(tree, TREE_FOLDER_ALARM_DOWN);

        // Create a series of 2nd-level child nodes, and a couple of 3rd-level
        // grand-child nodes.
        for (int i = 10; i < 51; i += 10) {
            TreeNode n, c;

            // 2nd-level child node
            n = addTreeNode(tree, "Node " + i, i, TREE_FOLDER);

            // 3rd-level grandchild nodes
            c = addTreeNode(n, "Node " + (i + 1), (i + 1), TREE_DOCUMENT);
            c = addTreeNode(n, "Node " + (i + 2), (i + 2), TREE_DOCUMENT);
        }

        // Create more 3rd-level grandchild nodes but with at least one
        // showing a major alarm.
        TreeNode node30 = tree.getChildNode(treeNodeID(30));
        if (node30 != null) {
            TreeNode node31 = node30.getChildNode(treeNodeID(31));
            if (node31 != null) {
                addTreeNode(node31, "Node 33", 33, TREE_DOCUMENT);
                addTreeNode(node31, "Node 34", 34, TREE_DOCUMENT_ALARM_MAJOR);

                // Propogate the alarm condition up the ancestry chain.
                setNodeImage(node31, TREE_FOLDER_ALARM_MAJOR);
                setNodeImage(node30, TREE_FOLDER_ALARM_MAJOR);
            }
        }

        // Create more 3rd-level grandchild nodes but with at least one
        // showing a down alarm.
        TreeNode node50 = tree.getChildNode(treeNodeID(50));
        if (node50 != null) {
            TreeNode node52 = node50.getChildNode(treeNodeID(52));
            if (node52 != null) {
                addTreeNode(node52, "Node 53", 53, TREE_DOCUMENT);
                addTreeNode(node52, "Node 54", 54, TREE_FOLDER_ALARM_DOWN);

                // Propogate the alarm condition up the ancestry chain.
                setNodeImage(node52, TREE_FOLDER_ALARM_DOWN);
                setNodeImage(node50, TREE_FOLDER_ALARM_DOWN);
            }
        }

        // Set a minor alarm on an existing leaf node and propogate up
        // the ancestry chain.
        TreeNode node40 = tree.getChildNode(treeNodeID(40));
        if (node40 != null) {
            TreeNode node42 = node40.getChildNode(treeNodeID(42));
            if (node42 != null) {
                setNodeImage(node42, TREE_FOLDER_ALARM_MINOR);
                setNodeImage(node40, TREE_FOLDER_ALARM_MINOR);
            }
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
     * Get the alert detail.
     * @return String
     */
    public String getAlertDetail() {
        return alertDetail;
    }

    /**
     * Get the alert rendered flag as a String.
     * @return String
     */
    public String getAlertRendered() {
        return alertRendered.toString();
    }

    /**
     * Return the array of test case options.
     * @return Option[]
     */
    public Option[] getTestCaseOptions() {
        return testCaseOptions;
    }

    /**
     * Action handler when navigating to the dynamic tree example.
     * @return String
     */
    public String showDynamicTree() {
        return SHOW_DYNAMIC_TREE;
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
     * Action handler when clicking on a tree node.
     * @return String
     */
    public String treeNodeAction() {
        return null;
    }

    /**
     * Action listener when clicking on a tree node.
     * @param event action event
     */
    public void treeNodeActionListener(final ActionEvent event) {
        String componentID = ((UIComponentBase) event.getSource()).getId();
        String nodeNum;
        try {
            nodeNum = Integer.toString(getNodeNum(componentID));
        } catch (Exception e) {
            nodeNum = componentID;
        }
        Object[] args = {tree.getId(), nodeNum};
        alertDetail = MessageUtil.getMessage("tree_alertDetail", args);
        alertRendered = Boolean.TRUE;
        tree.setSelected(componentID);
    }

    /**
     * Action handler for the test case drop-down menu.
     * @return String
     */
    public String testCaseAction() {
        alertDetail = null;
        alertRendered = Boolean.FALSE;
        return null;
    }

    /**
     * Action listener for the test case drop-down menu.
     * @param event action event
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public void testCaseActionListener(final ActionEvent event) {
        // Get the selection from the dropdown
        DropDown dropDown = (DropDown) event.getComponent();
        String selected = (String) dropDown.getSelected();

        if (selected.equals("tree_testCase_yoke34")) {
            // Make sure node 34 exists.
            String id = treeNodeID(34);
            TreeNode node = tree.getChildNode(id);
            if (node != null) {
                // It exists, so select it and expand all ancester nodes
                tree.setSelected(id);
                TreeNode parent = TreeNode.getParentTreeNode(node);
                while (parent != null) {
                    parent.setExpanded(true);
                    parent = TreeNode.getParentTreeNode(parent);
                }
            }
        }
    }

    /**
     * Reset everything back to initial state.
     */
    private void reset() {
        alertDetail = null;
        alertRendered = Boolean.FALSE;
        tree = null;
    }

    /**
     * A a new node.
     * @param parent parent node
     * @param label text for the created node
     * @param nodeNum node number of the created node
     * @param icon image for the created node
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
        ExampleUtilities.setMethodExpression(link, "actionExpression",
                "#{DynamicTreeBean.treeNodeAction}");

        Class[] paramTypes = new Class[]{ActionEvent.class};
        ExampleUtilities.setMethodExpression(link,
                "actionListenerExpression",
                "#{DynamicTreeBean.treeNodeActionListener}",
                null, paramTypes);

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
        ExampleUtilities.setMethodExpression(link, "actionExpression",
                "#{DynamicTreeBean.treeNodeAction}");

        Class[] paramTypes = new Class[]{ActionEvent.class};
        ExampleUtilities.setMethodExpression(link,
                "actionListenerExpression",
                "#{DynamicTreeBean.treeNodeActionListener}",
                null, paramTypes);
        return link;
    }

    /**
     * Set the image for a specified tree node.
     * @param node node
     * @param icon image
     */
    private void setNodeImage(final TreeNode node, final String icon) {
        setNodeImage((Object) (node.getFacets().get("image")), icon);
        makeToolTip(node, icon);
    }

    /**
     * Set the image for a specified image component.
     * @param component UI component to set the image of
     * @param icon image to set
     */
    private static void setNodeImage(final Object component,
            final String icon) {

        if (component == null) {
            return;
        }

        // For themed images, we call setIcon on the ImageHyperLink.
        // Otherwise we call setImageURL.
        if (icon.equals(TREE_DOCUMENT)
                || icon.equals(TREE_FOLDER)
                || icon.equals(TREE_FOLDER_ALARM_MINOR)
                || icon.equals(TREE_FOLDER_ALARM_MAJOR)
                || icon.equals(TREE_FOLDER_ALARM_DOWN)
                || icon.equals(TREE_FOLDER_ALARM_CRITICAL)) {
            if (component instanceof ImageHyperlink) {
                ((ImageHyperlink) component).setIcon(icon);
            }
            if (component instanceof ImageComponent) {
                ((ImageComponent) component).setIcon(icon);
            }
        } else {
            if (component instanceof ImageHyperlink) {
                ((ImageHyperlink) component).setImageURL(icon);
            }
            if (component instanceof ImageComponent) {
                ((ImageComponent) component).setUrl(icon);
            }
        }
    }

    /**
     * Compose the tool-tip and set it on all facets of the node.
     * @param node node to set the tool-tip of
     * @param icon image to set
     */
    private static void makeToolTip(final TreeNode node, final String icon) {
        String tip = node.getText();
        if (icon.equals(TREE_FOLDER_ALARM_MINOR)) {
            tip = MessageUtil.getMessage("tree_alarmMinorTip", tip);
        } else if (icon.equals(TREE_FOLDER_ALARM_MAJOR)) {
            tip = MessageUtil.getMessage("tree_alarmMajorTip", tip);
        } else if (icon.equals(TREE_FOLDER_ALARM_DOWN)) {
            tip = MessageUtil.getMessage("tree_alarmDownTip", tip);
        } else if (icon.equals(TREE_FOLDER_ALARM_CRITICAL)) {
            tip = MessageUtil.getMessage("tree_alarmCriticalTip", tip);
        } else if (icon.equals(TREE_DOCUMENT_ALARM_MINOR)) {
            tip = MessageUtil.getMessage("tree_alarmMinorTip", tip);
        } else if (icon.equals(TREE_DOCUMENT_ALARM_MAJOR)) {
            tip = MessageUtil.getMessage("tree_alarmMajorTip", tip);
        } else if (icon.equals(TREE_DOCUMENT_ALARM_DOWN)) {
            tip = MessageUtil.getMessage("tree_alarmDownTip", tip);
        } else if (icon.equals(TREE_DOCUMENT_ALARM_CRITICAL)) {
            tip = MessageUtil.getMessage("tree_alarmCriticalTip", tip);
        }

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
     * Return a valid component ID for a tree-node content facet.
     * @param nodeNum node ID
     * @return String
     */
    private static String nodeLinkID(final int nodeNum) {
        return treeNodeID(nodeNum) + "Link";
    }

    /**
     * Return a valid component ID for a tree-node image facet.
     * @param nodeNum node ID
     * @return String
     */
    private static String nodeImageID(final int nodeNum) {
        return treeNodeID(nodeNum) + "Image";
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
        return Integer.parseInt(nodeNum);
    }
}
