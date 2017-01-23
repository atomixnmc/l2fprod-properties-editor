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

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BeanUtils. <br>
 *
 * Adds helper methods for accessing read/write methods for a property.
 */
public final class BeanUtils {

    /**
     * Hidden Constructor.
     */
    private BeanUtils() {
    }

    /**
     * Helper method for getting a read method for a property.
     *
     * @param clazz the type to get the method for.
     * @param propertyName the name of the property.
     * @return the method for reading the property.
     */
    public static Method getReadMethod(Class<?> clazz, String propertyName) {
        Method readMethod = null;
        try {
            PropertyDescriptor[] thisProps = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            for (PropertyDescriptor pd : thisProps) {
                if (pd.getName().equals(propertyName) && pd.getReadMethod() != null) {
                    readMethod = pd.getReadMethod();
                    break;
                }
            }
        } catch (IntrospectionException ex) {
            Logger.getLogger(BeanUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readMethod;
    }

    /**
     * Helper method for getting a write method for a property.
     *
     * @param clazz
     * @param propertyName
     * @return
     */
    public static Method getWriteMethod(Class<?> clazz, String propertyName) {
        Method writeMethod = null;
        try {
            PropertyDescriptor[] thisProps = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            for (PropertyDescriptor pd : thisProps) {
                if (pd.getName().equals(propertyName) && pd.getWriteMethod() != null) {
                    writeMethod = pd.getWriteMethod();
                    break;
                }
            }
        } catch (IntrospectionException ex) {
            Logger.getLogger(BeanUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return writeMethod;
    }

}
