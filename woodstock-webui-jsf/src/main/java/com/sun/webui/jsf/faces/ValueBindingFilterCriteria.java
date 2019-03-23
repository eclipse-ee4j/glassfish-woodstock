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

package com.sun.webui.jsf.faces;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.el.ValueExpression;
import com.sun.data.provider.FilterCriteria;
import com.sun.data.provider.RowKey;
import com.sun.data.provider.TableDataFilter;
import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.CompareFilterCriteria;
import com.sun.data.provider.impl.TableRowDataProvider;
import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * An implementation of {@link FilterCriteria}
 * that compares the value of a {@link ValueExpression} with a predefined
 * <code>compareValue</code>.  A user may specify matches to include less than
 * (<), equal to (==), or greater than (>) the <code>compareValue</code> Object,
 * or any combination of the above.</p>
 *
 * <p>Use the <code>requestMapKey</code> property
 *
 * @see TableDataProvider
 * @see TableDataFilter
 *
 * @author Joe Nuxoll, John Yeary
 */
@Component(isTag = false)
public class ValueBindingFilterCriteria extends FilterCriteria {
    private static final long serialVersionUID = 7979125228842716689L;

    public ValueBindingFilterCriteria() {
    }

    /**
     *
     * @param valueExpression ValueExpression
     */
    public ValueBindingFilterCriteria(ValueExpression valueExpression) {
        this.valueExpression = valueExpression;
    }

    /**
     *
     * @param compareValue The desired compare value
     */
    public ValueBindingFilterCriteria(Object compareValue) {
        this.compareValue = compareValue;
    }

    /**
     *
     * @param valueExpression ValueExpression
     * @param compareValue The desired compare value
     */
    public ValueBindingFilterCriteria(ValueExpression valueExpression, Object compareValue) {
        this.valueExpression = valueExpression;
        this.compareValue = compareValue;
    }

    /**
     *
     * @param valueExpression ValueExpression
     * @param compareValue Object
     * @param matchLessThan boolean
     * @param matchEqualTo boolean
     * @param matchGreaterThan boolean
     */
    public ValueBindingFilterCriteria(ValueExpression valueExpression, Object compareValue,
            boolean matchLessThan, boolean matchEqualTo, boolean matchGreaterThan) {

        this.valueExpression = valueExpression;
        this.compareValue = compareValue;
        this.matchLessThan = matchLessThan;
        this.matchEqualTo = matchEqualTo;
        this.matchGreaterThan = matchGreaterThan;
    }

    /**
     *
     */
    public String getDisplayName() {
        String name = super.getDisplayName();
        if (name != null && !"".equals(name)) {
            return name;
        }

        // if there's no display name, make one...
        Object val = getCompareValue();
        StringBuffer sb = new StringBuffer();
        sb.append(isInclude() ? "Include [" : "Exclude [");
        sb.append(valueExpression != null ? valueExpression.getExpressionString() : "<no value binding>");
        sb.append("] ");
        boolean anyMatches = false;
        if (matchLessThan) {
            anyMatches = true;
            sb.append("is less than ");
        }
        if (matchEqualTo) {
            if (anyMatches) {
                sb.append("OR ");
            }
            anyMatches = true;
            sb.append("is equal to ");
        }
        if (matchGreaterThan) {
            if (anyMatches) {
                sb.append("OR ");
            }
            sb.append("is greater than ");
        }
        sb.append("[" + val + "]");
        return sb.toString();
    }

    /**
     *
     * @param valueExpression ValueExpression
     */
    public void setValueExpression(ValueExpression valueExpression) {
        this.valueExpression = valueExpression;
    }

    /**
     *
     * @return ValueExpression
     */
    public ValueExpression getValueExpression() {
        return valueExpression;
    }

    /**
     * Returns the request map variable key that will be used to store the
     * {@link TableRowDataProvider} for the current row being match tested.
     * This allows value expressions to refer to the "current" row during the
     * filter operation.
     *
     * @return String key to use for the {@link TableRowDataProvider}
     */
    public String getRequestMapKey() {
        return requestMapKey;
    }

    /**
     * Sets the request map variable key that will be used to store the
     * {@link TableRowDataProvider} for the current row being match tested.
     * This allows value expressions to refer to the "current" row during the
     * filter operation.
     *
     * @param requestMapKey String key to use for the {@link TableRowDataProvider}
     */
    public void setRequestMapKey(String requestMapKey) {
        this.requestMapKey = requestMapKey;
    }

    /**
     *
     * @param value Object
     */
    public void setCompareValue(Object value) {
        this.compareValue = value;
    }

    /**
     *
     * @return Object
     */
    public Object getCompareValue() {
        return compareValue;
    }
    /**
     * Storage for the compare locale
     */
    protected Locale compareLocale;

    /**
     *
     * @param compareLocale Locale
     */
    public void setCompareLocale(Locale compareLocale) {
        this.compareLocale = compareLocale;
    }

    /**
     *
     * @return Locale
     */
    public Locale getCompareLocale() {
        return compareLocale;
    }
    /**
     *
     */
    protected boolean matchEqualTo = true;

    /**
     *
     * @param matchEqualTo boolean
     */
    public void setMatchEqualTo(boolean matchEqualTo) {
        this.matchEqualTo = matchEqualTo;
    }

    /**
     *
     * @return boolean
     */
    public boolean isMatchEqualTo() {
        return matchEqualTo;
    }
    /**
     *
     */
    protected boolean matchLessThan = false;

    /**
     *
     * @param matchLessThan boolean
     */
    public void setMatchLessThan(boolean matchLessThan) {
        this.matchLessThan = matchLessThan;
    }

    /**
     *
     * @return boolean
     */
    public boolean isMatchLessThan() {
        return matchLessThan;
    }
    /**
     *
     */
    protected boolean matchGreaterThan = false;

    /**
     *
     * @param matchGreaterThan boolean
     */
    public void setMatchGreaterThan(boolean matchGreaterThan) {
        this.matchGreaterThan = matchGreaterThan;
    }

    /**
     *
     * @return boolean
     */
    public boolean isMatchGreaterThan() {
        return matchGreaterThan;
    }

    /**
     * <p>This method tests a match by comparing the <code>compareValue</code>
     * and the data value stored under the {@link ValueExpression}.  The passed
     * TableDataProvider and RowKey parameters are ignored.  The
     * <code>matchLessThan</code>,  <code>matchEqualTo</code>, and
     * <code>matchGreaterThan</code> properties  are used to determine if a
     * match was found.  The <code>compareLocale</code> is used for String
     * comparisons.</p>
     *
     * {@inheritDoc}
     */
    public boolean match(TableDataProvider provider, RowKey row) {

        if (valueExpression == null) {
            return true;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
        Object value = null;

        //FIXME synchronization on a non-final field
        synchronized (rowProviderLock) {

            Object storedRequestMapValue = null;
            if (requestMapKey != null && !"".equals(requestMapKey)) {
                storedRequestMapValue = requestMap.get(requestMapKey);
                if (rowProvider == null) {
                    rowProvider = new TableRowDataProvider();
                }
                rowProvider.setTableDataProvider(provider);
                rowProvider.setTableRow(row);
                requestMap.put(requestMapKey, rowProvider);
            }

            value = valueExpression.getValue(facesContext.getELContext());

            if (requestMapKey != null && !"".equals(requestMapKey)) {
                if (rowProvider != null) {
                    rowProvider.setTableDataProvider(null);
                    rowProvider.setTableRow(null);
                }
                requestMap.put(requestMapKey, storedRequestMapValue);
            }
        }

        int compare = CompareFilterCriteria.compare(value, compareValue, compareLocale);
        switch (compare) {
            case -1: // Less Than
                return matchLessThan;
            case 0: // Equal To
                return matchEqualTo;
            case 1: // Greater Than
                return matchGreaterThan;
        }
        return false; // This should never be reached
    }
    @Property(displayName = "Value Binding", isAttribute = false)
    private transient ValueExpression valueExpression;
    @Property(displayName = "Compare Value")
    private Object compareValue;
    @Property(displayName = "Request Map Key")
    private String requestMapKey = "currentRow"; // NOI18N
    private transient TableRowDataProvider rowProvider;
    private String rowProviderLock = "rowProviderLock"; // this is a monitor lock for rowProvider

    private void writeObject(ObjectOutputStream out) throws IOException {

        // Serialize simple objects first
        out.writeObject(compareValue);
        out.writeObject(requestMapKey);
        out.writeObject(rowProviderLock);

        // Serialize valueExpression specially
        if (valueExpression != null) {
            out.writeObject(valueExpression.getExpressionString());
        } else {
            out.writeObject((String) null);
        }

        // NOTE - rowProvider is reconstituted on demand,
        // so we don't need to serialize it



        if (valueExpression != null) {
            out.writeObject(valueExpression.getExpressionString());
        }
    }

    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {

        // Deserialize simple objects first
        compareValue = in.readObject();
        requestMapKey = (String) in.readObject();
        rowProviderLock = (String) in.readObject();

        // Deserialize valueExpression specially
        String s = (String) in.readObject();
        if (s != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            valueExpression = facesContext.getApplication().getExpressionFactory().createValueExpression(facesContext.getELContext(), s, Object.class);
        } else {
            valueExpression = null;
        }
    }
}
