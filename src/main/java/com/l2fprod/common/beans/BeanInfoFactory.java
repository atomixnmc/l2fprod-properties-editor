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
package com.l2fprod.common.beans;

import com.l2fprod.common.annotations.Browsable;
import com.l2fprod.common.annotations.Categorization;
import com.l2fprod.common.annotations.DirectoryProperty;
import com.l2fprod.common.annotations.FileProperty;
import com.l2fprod.common.beans.editor.DirectoryPropertyEditor;
import com.l2fprod.common.beans.editor.FilePropertyEditor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matta
 */
public final class BeanInfoFactory {

    /**
     * Hidden Constructor.
     */
    private BeanInfoFactory() {
    }

    /**
     * Get the bean information of a type.
     *
     * @param c The type to get the bean information of.
     * @return The BeanInfo of the specified type.
     */
    public static BeanInfo createBeanInfo(Class<? extends Object> c) {
        BeanInfo bi = DefaultBeanInfoResolver.getBeanInfoHelper(c);
        if (bi == null) {
            bi = new ConfigBeanInfo(c);
            DefaultBeanInfoResolver.addBeanInfo(c, bi);
        }
        return bi;
    }

    /**
     * This class overrides BaseBeanInfo.
     *
     * This class will auto-populate the properties reading from the
     * Introspector.
     */
    private static class ConfigBeanInfo extends BaseBeanInfo {

        /**
         * Constructor.
         *
         * Initialize properties from the Introspector.
         *
         * @param c The type to wrap.
         */
        @SuppressWarnings("OverridableMethodCallInConstructor")
        ConfigBeanInfo(Class<? extends Object> c) {
            super(c);
            try {
                PropertyDescriptor[] props = Introspector.getBeanInfo(c).getPropertyDescriptors();
                for (PropertyDescriptor prop : props) {
                    if (prop.getReadMethod() == null) {
                        Logger.getLogger(BeanInfoFactory.class.getName()).log(Level.WARNING, "Property: {1}:{0} has a null read method.", new Object[]{prop.getDisplayName(), c.getName()});
                    } else if (prop.getReadMethod().getDeclaringClass() != Object.class) {
                        Categorization cat = prop.getReadMethod().getAnnotation(Categorization.class);
                        Browsable browse = prop.getReadMethod().getAnnotation(Browsable.class);
                        FileProperty fp = prop.getReadMethod().getAnnotation(FileProperty.class);
                        DirectoryProperty dir = prop.getReadMethod().getAnnotation(DirectoryProperty.class);

                        /*
                         * Do not add properties that have the Browse annotation set to false.
                         */
                        if (browse == null || browse.enabled()) {
                            ExtendedPropertyDescriptor epd = addProperty(prop.getName()).setCategory(cat == null ? "General" : cat.category());
                            if (fp != null) {
                                epd.setPropertyEditorClass(FilePropertyEditor.class);
                            } else if (dir != null) {
                                epd.setPropertyEditorClass(DirectoryPropertyEditor.class);
                            // } else {
                            }
                        }
                    }
                }
            } catch (IntrospectionException ex) {
                Logger.getLogger(BeanInfoFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
