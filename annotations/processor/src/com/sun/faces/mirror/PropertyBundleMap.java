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

package com.sun.faces.mirror;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A map for storing internationalized properties during file generation.
 *
 * @author gjmurphy
 */
public class PropertyBundleMap implements Map {
    
    Map<Object,Object> map = new HashMap<Object,Object>();
    List<Object> keyList = new ArrayList<Object>();
    
    PropertyBundleMap(String qualifiedName) {
        this.setQualifiedName(qualifiedName);
    }

    private String qualifiedName;

    public String getQualifiedName() {
        return this.qualifiedName;
    }
    
    void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public Object remove(Object key) {
        this.keyList.remove(key);
        return this.map.remove(key);
    }

    public Object get(Object key) {
        return this.map.get(key);
    }

    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    public Collection<Object> values() {
        return this.map.values();
    }

    public int size() {
        return this.map.size();
    }

    public Object put(Object key, Object value) {
        if (this.map.put(key, value) == null) {
            this.keyList.add(key);
            return null;
        }
        return key;
    }
    
    public void putAll(Map map) {
        this.map.putAll(map);
        this.keyList.addAll(map.keySet());
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public void clear() {
        this.map.clear();
        this.keyList.clear();
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return this.map.entrySet();
    }

    public Set<Object> keySet() {
        return this.map.keySet();
    }
    
    public List<Object> keyList() {
        return this.keyList;
    }
    
}
