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

package com.sun.webui.jsf.util;

import javax.faces.component.UIComponent;

/**
 * Methods for general component manipulation.
 */
public class ComponentUtilities {

    private final static String USCORE = "_";

    /**
     * Creates a new instance of ComponentUtilities.
     */
    public ComponentUtilities() {
    }

    /**
     * Store an internally created component utilizing the 
     * internal facet naming convention by mapping the facet
     * to the name returned by {@code createPrivateFacetName()}.
     * Add the component to the parent's facets map.
     *
     * @param parent the component that created the facet
     * @param facetName the public facet name
     * @param facet the private facet component instance
     */
    public static void putPrivateFacet(UIComponent parent,
            String facetName, UIComponent facet) {

        if (parent == null || facet == null || facetName == null) {
            return;
        }
        parent.getFacets().put(createPrivateFacetName(facetName), facet);
    }

    /**
     * Remove an internally created component utilizing the 
     * internal facet naming convention by mapping the facet
     * to the name returned by {@code createPrivateFacetName()}.
     * Remove the component from the parent's facets map.
     *
     * @param parent the component that created the facet
     * @param facetName the public facet name
     */
    public static void removePrivateFacet(UIComponent parent,
            String facetName) {

        if (parent == null || facetName == null) {
            return;
        }
        parent.getFacets().remove(createPrivateFacetName(facetName));
    }

    /**
     * Return a private facet from the the parent component's facet map.Look for
     * a private facet name by calling {@code createPrivateFacetName()} on the
     * facetName parameter.
     * If the matchId parameter is true, verify that the facet that is found has
     * an id that matches the value of
     * {@code getPrivateFacetId(parent.getId(), facetName)}. If the id's do not
     * match return null and remove the existing facet.<br>
     * If matchId is false, return the facet if found or null.
     *
     * @param parent the component that contains the facet
     * @param facetName the public facet name
     * @param matchId verify a the id of the facet
     * @return a UIComponent if the facet is found else null.
     */
    public static UIComponent getPrivateFacet(UIComponent parent,
            String facetName, boolean matchId) {

        if (parent == null || facetName == null) {
            return null;
        }

        String pfacetName = createPrivateFacetName(facetName);
        UIComponent facet = (UIComponent) parent.getFacets().get(pfacetName);
        if (facet == null) {
            return null;
        }

        if (matchId == false) {
            return facet;
        }

        // Will never be null as long as facetName is not null.
        String id = createPrivateFacetId(parent, facetName);
        if (!id.equals(facet.getId())) {
            parent.getFacets().remove(pfacetName);
            return null;
        }
        return facet;
    }

    /**
     * Prefix the facetName parameter with an "_".
     *
     * @param facetName the public facet name
     * @return a private facet name
     */
    public static String createPrivateFacetName(String facetName) {
        return USCORE.concat(facetName);
    }

    /**
     * Return an id using the convention: {@code parent.getId() + "_" + facetName}.
     * If {@code parent.getId()} is {@code null}, {@code "_" + facetName }
     * is returned.
     *
     * @param parent the component that contains the facet
     * @param facetName the public facet name
     * @return an id for a private facet.
     */
    public static String createPrivateFacetId(UIComponent parent,
            String facetName) {

        String pfacetName = createPrivateFacetName(facetName);
        String id = parent.getId();
        if (id != null) {
            pfacetName = id.concat(pfacetName);
        }
        return pfacetName;
    }
}
