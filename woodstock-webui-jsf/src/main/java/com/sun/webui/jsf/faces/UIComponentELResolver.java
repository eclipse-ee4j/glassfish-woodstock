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

import com.sun.faces.annotation.Resolver;
import com.sun.webui.jsf.util.LogUtil;
import java.util.List;
import java.util.ArrayList;
import javax.faces.component.UIComponent;
import javax.el.ELResolver;
import javax.el.ELContext;
import java.util.Iterator;
import java.beans.FeatureDescriptor;
import java.util.Arrays;

/**
 * <p>Custom <code>ELResolver that, when the <code>base</code>
 * object is a <code>UIComponent</code>, scans for a child with the
 * <code>id</code> specified by the property name.</p>
 */
@Resolver
public class UIComponentELResolver extends ELResolver {


    // -------------------------------------------------------- Static Variables
    // ------------------------------------------------------ Instance Variables
    // ------------------------------------------------------------ Constructors
    // ------------------------------------------------ ELResolver Methods
    /**
     * <p>When the base object is a <code>UIComponent</code>, treat the
     * property name as the <code>id</code> of a child component to be
     * returned.  If there is no such child, return without calling 
     * <code>context.setPropertyResolved(true)</code>.</p>
     *
     * @param context the ELContext
     * @param base Base object
     * @param property Property name
     */
    public Object getValue(ELContext context, Object base, Object property) {

        log("getValue(ctx, " + base + "," + property + ")");

        if (context == null) {
            throw new NullPointerException();
        }

        if ((base == null) || (!(base instanceof UIComponent)) ||
                (property == null)) {
            log("argument is null or not of applicable type. returning");
            return null;
        }

        // Try to resolve to facet or child UIComponent
        UIComponent component = (UIComponent) base;
        String id = property.toString();

        // First check for a facet w/ that name
        UIComponent kid = (UIComponent) component.getFacets().get(id);
        if (kid != null) {
            context.setPropertyResolved(true);
            log("returning facet " + kid);
            return kid;
        }

        // Now check for child component w/ that id
        if (component.getChildCount() < 1) {
            log("child count less than 1. returning");
            return null;
        }
        List kids = component.getChildren();
        for (int i = 0; i < kids.size(); i++) {
            kid = (UIComponent) kids.get(i);
            if (id.equals(kid.getId())) {
                context.setPropertyResolved(true);
                log("returning child " + kid);
                return kid;
            }
        }

        // Not found
        log("can't resolve. returning");
        return null;
    }

    /**
     * <p>When the base object is a <code>UIComponent</code>, treat the
     * property name as the <code>id</code> of a child component to be
     * replaced.  If there is no such child, return without calling 
     * <code>context.setPropertyResolved(true)</code>.</p>
     *
     * @param context the ELContext
     * @param base Base object
     * @param property Property name
     * @param value Replacement component
     */
    @SuppressWarnings("unchecked")
    public void setValue(ELContext context, Object base, Object property, Object value) {

        log("setValue(ctx, " + base + "," + property + "," + value + ")");

        if (context == null) {
            throw new NullPointerException();
        }

        if ((base == null) || (!(base instanceof UIComponent)) ||
                (property == null) ||
                (value == null) || (!(value instanceof UIComponent))) {
            log("argument is null or not of applicable type. returning");
            return;
        }

        UIComponent component = (UIComponent) base;
        String id = property.toString();
        // First check to for facet w/ this name
        if (component.getFacets().get(id) != null) {
            component.getFacets().put(id, (UIComponent) value);
            context.setPropertyResolved(true);
            log("set facet");
            return;
        }
        // Not a facet, see if it's a child
        if (component.getChildCount() < 1) {
            log("child count less than 1. returning");
            return;
        }
        List<UIComponent> kids = component.getChildren();
        for (int i = 0; i < kids.size(); i++) {
            UIComponent kid = (UIComponent) kids.get(i);
            if (id.equals(kid.getId())) {
                kids.set(i, (UIComponent) value);
                context.setPropertyResolved(true);
                log("set child");
                return;
            }
        }

        log("can't resolve. returning");
    }

    /**
     * <p>When the base object is a <code>UIComponent</code>, treat the
     * property name as the <code>id</code> of a child component to be
     * retrieved.  If the specified child actually exists, return
     * <code>false</code> (because replacement is allowed).  If there
     * is no such child, , return without calling 
     * <code>context.setPropertyResolved(true)</code>.</p>
     *
     * @param context the ELContext
     * @param base Base object
     * @param property Property name
     */
    public boolean isReadOnly(ELContext context, Object base, Object property) {

        log("isReadOnly(ctx, " + base + "," + property + ")");

        if (context == null) {
            throw new NullPointerException();
        }

        if ((base == null) || (!(base instanceof UIComponent)) ||
                (property == null)) {
            log("argument is null or not of applicable type. returning");
            return false;
        }

        UIComponent component = (UIComponent) base;
        String id = property.toString();
        if (component.getFacets().get(id) != null) {
            context.setPropertyResolved(true);
            log("not read-only. found facet");
            return false;
        }
        if (component.getChildCount() < 1) {
            log("child count less than 1. returning");
            return false;
        }
        List kids = component.getChildren();
        for (int i = 0; i < kids.size(); i++) {
            UIComponent kid = (UIComponent) kids.get(i);
            if (id.equals(kid.getId())) {
                context.setPropertyResolved(true);
                log("not read-only. found child");
                return false;
            }
        }

        log("can't resolve. returning");
        return false;

    }

    /**
     * <p>When the base object is a <code>UIComponent</code>, treat the
     * property name as the <code>id</code> of a child component to be
     * retrieved.  If the specified child actually exists, return
     * <code>javax.faces.component.UIComponent</code>.  If there is
     * no such child, return without calling 
     * <code>context.setPropertyResolved(true)</code>.</p>
     *
     * @param context the ELContext
     * @param base Base object
     * @param property Property name
     */
    public Class getType(ELContext context, Object base, Object property) {

        log("getType(ctx, " + base + "," + property + ")");

        if (context == null) {
            throw new NullPointerException();
        }

        if ((base == null) || (!(base instanceof UIComponent)) ||
                (property == null)) {
            log("argument is null or not of applicable type. returning");
            return null;
        }

        UIComponent component = (UIComponent) base;
        String id = property.toString();
        if (component.getFacets().get(id) != null) {
            context.setPropertyResolved(true);
            log("found facet. returning UIComponent.class");
            return UIComponent.class;
        }
        if (component.getChildCount() < 1) {
            log("child count less than 1. returning");
            return null;
        }
        List kids = component.getChildren();
        for (int i = 0; i < kids.size(); i++) {
            UIComponent kid = (UIComponent) kids.get(i);
            if (id.equals(kid.getId())) {
                context.setPropertyResolved(true);
                log("found child. returning UIComponent.class");
                return UIComponent.class;
            }
        }

        log("can't resolve. returning");
        return null;
    }

    private void log(String message) {
        if (LogUtil.finestEnabled(UIComponentELResolver.class)) {
            LogUtil.finest(UIComponentELResolver.class, message);
        }
    }

    /**
     * <p>When the base object is a <code>UIComponent</code>, return
     * an <code>Iterator</code> of <code>FeatureDescriptor</code> objects
     * containing the component ids of the base's children and facets. If
     * the base is <code>null</code>, return an empty <code>Iterator</code>.
     * Otherwise, if the base is not a <code>UIComponent</code>, return
     * <code>null</code>.
     *
     * @param context the ELContext
     * @param base Base object
     */
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {

        log("getFeatureDescriptors(ctx, " + base + ")");

        if (context == null) {
            throw new NullPointerException();
        }

        if (base == null) {
            return Arrays.asList(new FeatureDescriptor[0]).iterator();
        }

        if (!(base instanceof UIComponent)) {
            return null;
        }

        List<FeatureDescriptor> result = new ArrayList<FeatureDescriptor>();

        UIComponent baseUic = (UIComponent) base;
        Iterator<UIComponent> facetsAndChildren = baseUic.getFacetsAndChildren();
        while (facetsAndChildren.hasNext()) {
            UIComponent kid = facetsAndChildren.next();
            FeatureDescriptor desc = new FeatureDescriptor();
            String kidId = kid.getId();
            desc.setName(kidId);
            desc.setDisplayName(kidId);
            desc.setValue(ELResolver.TYPE, String.class);
            desc.setValue(ELResolver.RESOLVABLE_AT_DESIGN_TIME, true);
            result.add(desc);
        }

        return result.iterator();
    }

    /**
     * <p>When the base object is a <code>UIComponent</code>, return
     * <code>String.class</code>. If
     * the base is <code>null</code> or not a <code>UIComponent</code>, return
     * <code>null</code>.
     *
     * @param context the ELContext
     * @param base Base object
     */
    public Class getCommonPropertyType(ELContext context,
            Object base) {
        log("getCommonPropertyType(ctx, " + base + ")");

        if (context == null) {
            throw new NullPointerException();
        }

        if (!(base instanceof UIComponent)) {
            return null;
        }

        return String.class;
    }
}
