/*
 * Copyright 2015 Matthew Aguirre
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.l2fprod.common.propertysheet;

import com.l2fprod.common.annotations.PropertyRendererOverride;
import com.l2fprod.common.annotations.RendererRegistry;
import com.l2fprod.common.beans.ExtendedPropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.TableCellRenderer;

/**
 * Mapping between Properties, Property Types and Renderers.
 */
public final class PropertyRendererRegistry implements PropertyRendererFactory {

    private final Map typeToRenderer;
//    private final Map propertyToRenderer;

    public PropertyRendererRegistry() {
        typeToRenderer = new HashMap();
//        propertyToRenderer = new HashMap();
        registerDefaults();
    }

    @Override
    public TableCellRenderer createTableCellRenderer(Property property) {
        return getRenderer(property);
    }

    @Override
    public TableCellRenderer createTableCellRenderer(Class type) {
        return getRenderer(type);
    }

    /**
     * Gets a renderer for the given property. The lookup is as follow:
     * <ul>
     * <li>if a renderer was registered with
     * {@link ExtendedPropertyDescriptor#setPropertyTableRendererClass(Class)} -
     * BeanInfo, it is returned, else</li>
     * <li>if a renderer was registered with
     * {@link #registerRenderer(Property, TableCellRenderer)}, it is returned,
     * else</li>
     * <li>if a renderer class was registered with
     * {@link #registerRenderer(Property, Class)}, it is returned, else
     * <li>
     * <li>look for renderer for the property type using
     * {@link #getRenderer(Class)}.</li>
     * </ul>
     *
     * @param property
     * @return a renderer suitable for the Property.
     */
    public synchronized TableCellRenderer getRenderer(Property property) {

        // editors bound to the property descriptor have the highest priority
        TableCellRenderer renderer = null;
        if (property instanceof PropertyDescriptorAdapter) {
            PropertyDescriptor descriptor = ((PropertyDescriptorAdapter) property).getDescriptor();
            Method readMethod = descriptor.getReadMethod();
            //allow a per/get property renderer override.
            if (readMethod != null) {
                PropertyRendererOverride annotation = readMethod.getAnnotation(PropertyRendererOverride.class);
                if (annotation != null) {
                    try {
                        return annotation.type().newInstance();
                    } catch (InstantiationException | IllegalAccessException ex) {
                        Logger.getLogger(PropertyRendererRegistry.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (descriptor instanceof ExtendedPropertyDescriptor) {
                if (((ExtendedPropertyDescriptor) descriptor).getPropertyTableRendererClass() != null) {
                    try {
                        return (TableCellRenderer) (((ExtendedPropertyDescriptor) descriptor).getPropertyTableRendererClass()).newInstance();
                    } catch (IllegalAccessException | InstantiationException e) {
                        Logger.getLogger(PropertyRendererRegistry.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }
        }
        Object value = typeToRenderer.get(property);
        if (value instanceof TableCellRenderer) {
            renderer = (TableCellRenderer) value;
        } else if (value instanceof Class) {
            try {
                renderer = (TableCellRenderer) ((Class) value).newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                Logger.getLogger(PropertyRendererRegistry.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            renderer = getRenderer(property.getType());
        }
        return renderer;
    }

    /**
     * Gets a renderer for the given property type. The lookup is as follow:
     * <ul>
     * <li>if a renderer was registered with
     * {@link #registerRenderer(Class, TableCellRenderer)}, it is returned,
     * else</li>
     * <li>if a renderer class was registered with
     * {@link #registerRenderer(Class, Class)}, it is returned, else
     * <li>
     * <li>it returns null.</li>
     * </ul>
     *
     * @param type
     * @return a renderer editor suitable for the Property type or null if none
     * found
     */
    public synchronized TableCellRenderer getRenderer(Class type) {
        TableCellRenderer renderer = null;
        Object value = typeToRenderer.get(type);
        if (value instanceof TableCellRenderer) {
            renderer = (TableCellRenderer) value;
        } else if (value instanceof Class) {
            try {
                renderer = (TableCellRenderer) ((Class) value).newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                Logger.getLogger(PropertyRendererRegistry.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return renderer;
    }

    public synchronized void registerRenderer(Class type, Class rendererClass) {
        typeToRenderer.put(type, rendererClass);
    }

    public synchronized void registerRenderer(Class type, TableCellRenderer renderer) {
        typeToRenderer.put(type, renderer);
    }

    public synchronized void unregisterRenderer(Class type) {
        typeToRenderer.remove(type);
    }

//    public synchronized void registerRenderer(Property property, Class rendererClass) {
//        propertyToRenderer.put(property, rendererClass);
//    }
//
//    public synchronized void registerRenderer(Property property,
//            TableCellRenderer renderer) {
//        propertyToRenderer.put(property, renderer);
//    }
//
//    public synchronized void unregisterRenderer(Property property) {
//        propertyToRenderer.remove(property);
//    }

    /**
     * Adds default renderers. This method is called by the constructor but may
     * be called later to reset any customizations made through the
     * <code>registerRenderer</code> methods. <b>Note: if overriden,
     * <code>super.registerDefaults()</code> must be called before plugging
     * custom defaults. </b>
     */
    public void registerDefaults() {
        typeToRenderer.clear();
//        propertyToRenderer.clear();

        ServiceLoader<TableCellRenderer> serviceLoader = ServiceLoader.load(TableCellRenderer.class);
        Iterator<TableCellRenderer> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            TableCellRenderer next = iterator.next();
            try {
                RendererRegistry annotation = next.getClass().getAnnotation(RendererRegistry.class);
                if (annotation != null) {
                    for (Class<?> clazz : annotation.type()) {
                        registerRenderer(clazz, next);
                    }
                }
                Method m = next.getClass().getMethod("setShowOddAndEvenRows", boolean.class);
                if (m != null) {
                    m.invoke(next, false);
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
    }
}
