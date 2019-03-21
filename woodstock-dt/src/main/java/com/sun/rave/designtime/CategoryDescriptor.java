/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.rave.designtime;

/**
 *
 * @author rgrecour
 */
public class CategoryDescriptor {

    private final String name;

    public CategoryDescriptor(String name) {
        this.name = name;
    }

    public CategoryDescriptor(String name, String description,
            boolean expandByDefault) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
