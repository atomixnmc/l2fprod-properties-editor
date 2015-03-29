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

import com.l2fprod.common.annotations.EditorRegistry;
import com.l2fprod.common.annotations.PropertyEditorOverride;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mapping between Properties, Property Types and Property Editors.
 */
public final class PropertyEditorRegistry implements PropertyEditorFactory {

    private final Map typeToEditor;
    private final Map propertyToEditor;

    public final static PropertyEditorRegistry Instance = new PropertyEditorRegistry();

    private PropertyEditorRegistry() {
        typeToEditor = new HashMap();
        propertyToEditor = new HashMap();
        registerDefaults();
    }

    @Override
    public PropertyEditor createPropertyEditor(Property property) {
        return getEditor(property);
    }

    /**
     * Gets an editor for the given property. The lookup is as follow:
     * <ul>
     * <li>if propertyDescriptor.getPropertyEditorClass() returns a valid value,
     * it is returned, else,
     * <li>if an editor was registered with
     * {@link #registerEditor(Property, PropertyEditor)}, it is returned,
     * else</li>
     * <li>if an editor class was registered with
     * {@link #registerEditor(Property, Class)}, it is returned, else
     * <li>
     * <li>look for editor for the property type using
     * {@link #getEditor(Class)}.it is returned, else
     * </li>
     * <li>look for editor using PropertyEditorManager.findEditor(Class);
     * </li>
     * </ul>
     *
     * @param property
     * @return an editor suitable for the Property.
     */
    public synchronized PropertyEditor getEditor(Property property) {
        PropertyEditor editor = null;
        if (property instanceof PropertyDescriptorAdapter) {
            PropertyDescriptor descriptor = ((PropertyDescriptorAdapter) property).getDescriptor();
            if (descriptor != null) {
                //allow a per/set property editor override
                PropertyEditorOverride annotation = descriptor.getWriteMethod().getAnnotation(PropertyEditorOverride.class);
                if (annotation != null) {
                    Class clz = annotation.type();
                    if (clz != null) {
                        editor = loadPropertyEditor(clz);
                    }
                }
                if (editor == null) {
                    Class clz = descriptor.getPropertyEditorClass();
                    if (clz != null) {
                        editor = loadPropertyEditor(clz);
                    }
                }
            }
        }
        if (editor == null) {
            Object value = propertyToEditor.get(property);
            if (value instanceof PropertyEditor) {
                editor = (PropertyEditor) value;
            } else if (value instanceof Class) {
                editor = loadPropertyEditor((Class) value);
            } else {
                editor = getEditor(property.getType());
            }
        }
        if ((editor == null) && (property instanceof PropertyDescriptorAdapter)) {
            PropertyDescriptor descriptor = ((PropertyDescriptorAdapter) property).getDescriptor();
            Class clz = descriptor.getPropertyType();
            editor = PropertyEditorManager.findEditor(clz);
        }
        return editor;
    }

    /**
     * Load PropertyEditor from clz through reflection.
     *
     * @param clz Class to load from.
     * @return Loaded propertyEditor
     */
    private PropertyEditor loadPropertyEditor(Class clz) {
        PropertyEditor editor = null;
        try {
            editor = (PropertyEditor) clz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Logger.getLogger(PropertyEditorRegistry.class.getName()).log(Level.SEVERE, null, e);
        }
        return editor;
    }

    /**
     * Gets an editor for the given property type. The lookup is as follow:
     * <ul>
     * <li>if an editor was registered with
     * {@link #registerEditor(Class, PropertyEditor)}, it is returned, else</li>
     * <li>if an editor class was registered with
     * {@link #registerEditor(Class, Class)}, it is returned, else
     * <li>
     * <li>it returns null.</li>
     * </ul>
     *
     * @param type
     * @return an editor suitable for the Property type or null if none found
     */
    public synchronized PropertyEditor getEditor(Class type) {
        PropertyEditor editor = null;
        Object value = typeToEditor.get(type);
        if (value == null && type.isEnum()) {
            value = typeToEditor.get(Enum.class);
        }
        if (value instanceof PropertyEditor) {
            editor = (PropertyEditor) value;
        } else if (value instanceof Class) {
            try {
                editor = (PropertyEditor) ((Class) value).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                Logger.getLogger(PropertyEditorRegistry.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return editor;
    }

    public synchronized void registerEditor(Class type, Class editorClass) {
        typeToEditor.put(type, editorClass);
    }

    public synchronized void registerEditor(Class type, PropertyEditor editor) {
        typeToEditor.put(type, editor);
    }

    public synchronized void unregisterEditor(Class type) {
        typeToEditor.remove(type);
    }

    public synchronized void registerEditor(Property property, Class editorClass) {
        propertyToEditor.put(property, editorClass);
    }

    public synchronized void registerEditor(Property property,
            PropertyEditor editor) {
        propertyToEditor.put(property, editor);
    }

    public synchronized void unregisterEditor(Property property) {
        propertyToEditor.remove(property);
    }

    /**
     * Adds default editors. This method is called by the constructor but may be
     * called later to reset any customizations made through the
     * <code>registerEditor</code> methods. <b>Note: if overridden,
     * <code>super.registerDefaults()</code> must be called before plugging
     * custom defaults. </b>
     */
    public void registerDefaults() {
        typeToEditor.clear();
        propertyToEditor.clear();

        //switch to service loader and use of custom annotation
        ServiceLoader<PropertyEditor> propertyLoader = ServiceLoader.load(PropertyEditor.class);
        try {
            Iterator<PropertyEditor> controllers_it = propertyLoader.iterator();
            while (controllers_it.hasNext()) {
                PropertyEditor c = controllers_it.next();
                EditorRegistry annotation = c.getClass().getAnnotation(EditorRegistry.class);
                if (annotation != null) {
                    for (Class<?> clazz : annotation.type()) {
                        registerEditor(clazz, c.getClass());
                    }
                }
            }
        } catch (ServiceConfigurationError serviceError) {
        }
    }

}
