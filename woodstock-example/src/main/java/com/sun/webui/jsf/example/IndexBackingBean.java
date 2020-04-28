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

import com.sun.webui.jsf.example.util.MessageUtil;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.TableColumn;
import com.sun.webui.jsf.component.Markup;
import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.ObjectArrayDataProvider;
import jakarta.faces.component.UIParameter;
import java.io.Serializable;
import com.sun.webui.jsf.example.util.ExampleUtilities;

/**
 * This bean class provides data to the index page table in the example app.
 */
public final class IndexBackingBean implements Serializable {

    /**
     * Hyperlink id.
     */
    private static final String HYPERLINK_ID = "link";

    /**
     * The outcome strings used in the faces config file.
     */
    private static final String SHOWCODE_ACTION = "showCode";

    /**
     * The outcome strings used in the faces config file.
     */
    public static final String INDEX_ACTION = "showIndex";

    /**
     * TableColumn component.
     */
    private TableColumn tableColumn = null;

    /**
     * Table provider.
     */
    private TableDataProvider provider = null;

    /**
     * Default constructor.
     */
    public IndexBackingBean() {
    }

    /**
     * Get table data provider.
     * @return TableDataProvider
     */
    public TableDataProvider getDataProvider() {
        if (provider == null) {
            provider = new ObjectArrayDataProvider(getTableData());
        }
        return provider;
    }

    /**
     * Get table data.
     * @return ExampleData[]
     */
    public ExampleData[] getTableData() {
        return ExampleData.EXAMPLE_DATA;
    }

    /**
     * Get tableColumn component.
     * @return TableColumn
     */
    public TableColumn getTableColumn() {
        if (tableColumn == null) {
            String id;
            int maxLinks = 0;
            int numFiles;

            // create tableColumn
            tableColumn = createTableColumn("col3", "index_filesHeader");

            // Find the maximum number of hyperlinks that should be created
            // by counting the number of files in the AppData array object.
            ExampleData[] tableData = getTableData();
            for (ExampleData tableData1 : tableData) {
                numFiles = tableData1.getFiles().length;
                if (numFiles > maxLinks) {
                    maxLinks = numFiles;
                }
            }

            // Create hyperlinks up to the maximum number of files
            // in the AppData object. Use the "rendered" attribute
            // of the hyperlink component to hide it if there are
            // less links to show.
            if (maxLinks > 0) {
                for (int i = 0; i < maxLinks; i++) {
                    id = HYPERLINK_ID + "_" + i;

                    // Create hyperlinks for each file in an example app
                    // to show the source code
                    Hyperlink hyperlink = createHyperlink(id,
                            "#{data.value.files[" + i + "]}",
                            "#{IndexBean.action}",
                            "#{data.value.files[" + i + "]}");

                    // The hyperlink is an ActionSource component
                    // which should be marked immediate by default.
                    hyperlink.setImmediate(true);

                    // Don't display the link if no file name exists.
                    ExampleUtilities.setValueExpression(hyperlink, "rendered",
                            "#{data.value.files[" + i + "] != null}");

                    // Add new lines between the hyperlinks.
                    Markup markup = createMarkup("br", true);
                    ExampleUtilities.setValueExpression(markup, "rendered",
                            "#{data.value.files[" + i + "] != null}");

                    tableColumn.getChildren().add(hyperlink);
                    tableColumn.getChildren().add(markup);
                }
            }
        }
        return tableColumn;
    }

    /**
     * Set tableColumn component.
     *
     * @param newTableColumn The TableColumn component
     */
    public void setTableColumn(final TableColumn newTableColumn) {
        this.tableColumn = newTableColumn;
    }

    /**
     * Return the string used for hyperlink action.
     * @return String
     */
    public String action() {
        return SHOWCODE_ACTION;
    }

    /**
     * Return the string used for breadcrumbs action.
     * @return String
     */
    public String showIndex() {
        return INDEX_ACTION;
    }

    /**
     * Helper method to create table column.
     *
     * @param id The component id.
     * @param header The component header text.
     * @return TableColumn
     */
    private static TableColumn createTableColumn(final String id,
            final String header) {

        TableColumn col = new TableColumn();
        col.setId(id);
        col.setHeaderText(MessageUtil.getMessage(header));
        return col;
    }

    /**
     * Helper method to create hyperlink.
     *
     * @param id The component id.
     * @param text Value binding expression for text.
     * @param action Method binding expression for action.
     * @param parameter Value binding expression for parameter.
     * @return Hyperlink
     */
    private static Hyperlink createHyperlink(final String id, final String text,
            final String action, final String parameter) {

        // Get hyperlink.
        Hyperlink hyperlink = new Hyperlink();
        // Set id.
        hyperlink.setId(id);
        // Set text.
        ExampleUtilities.setValueExpression(hyperlink, "text", text);
        // Set action.
        ExampleUtilities.setMethodExpression(hyperlink, "actionExpression",
                action);

        // Create paramerter.
        UIParameter param = new UIParameter();
        param.setId(id + "_param");
        param.setName("param");
        // Set parameter.
        ExampleUtilities.setValueExpression(param, "value", parameter);
        hyperlink.getChildren().add(param);
        return hyperlink;
    }

    /**
     * Helper method to create markup.
     * @param tag markup tag
     * @param singleton singleton flag
     * @return Markup
     */
    private static Markup createMarkup(final String tag,
            final boolean singleton) {

        // Get markup.
        Markup markup = new Markup();
        markup.setTag(tag);
        markup.setSingleton(singleton);
        return markup;
    }
}
