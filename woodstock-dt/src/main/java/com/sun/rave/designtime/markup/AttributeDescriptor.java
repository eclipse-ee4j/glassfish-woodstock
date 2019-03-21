/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.rave.designtime.markup;

/**
 *
 * @author rgrecour
 */
public class AttributeDescriptor {

    private final String name;
    private final boolean required;
    private final Object defaultValue;
    private final boolean bindable;

    public AttributeDescriptor(String name, boolean required,
            Object defaultValue, boolean bindable) {

        this.name = name;
        this.required = required;
        this.defaultValue = defaultValue;
        this.bindable = bindable;
    }

    public String getName() {
        return name;
    }

    public boolean isBindable() {
        return bindable;
    }

    public boolean isRequired() {
        return required;
    }
}
