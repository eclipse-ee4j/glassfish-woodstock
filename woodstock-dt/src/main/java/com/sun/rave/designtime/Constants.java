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
public final class Constants {

    private Constants(){
    }

    public static final class PropertyDescriptor {

        public static final String CATEGORY = "propertyCagetory";
        public static final String ATTRIBUTE_DESCRIPTOR = "propertyAttributeDescriptor";

        private PropertyDescriptor() {
        }
    }

    public static final class BeanDescriptor {

        public static final String INSTANCE_NAME = "beanInstanceName";
        public static final String FACET_DESCRIPTORS = "beanFacetDescriptors";
        public static final String HELP_KEY = "beanHelpKey";
        public static final String IS_CONTAINER = "beanIsContainer";
        public static final String PROPERTIES_HELP_KEY = "beanPropertiesHelpKey";
        public static final String PROPERTY_CATEGORIES = "beanPropertyCategories";
        public static final String TAG_NAME = "beanTagName";
        public static final String TAGLIB_PREFIX = "beanTagLibPrefix";
        public static final String TAGLIB_URI = "beanTagLibUri";

        private BeanDescriptor() {
        }
    }

    public static final class EventSetDescriptor {

        public static final String BINDING_PROPERTY = "eventSetProperty";

        private EventSetDescriptor() {
        }
    }
}
