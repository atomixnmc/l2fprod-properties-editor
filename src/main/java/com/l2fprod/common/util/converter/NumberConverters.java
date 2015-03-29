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
package com.l2fprod.common.util.converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Convert to and from numbers.
 */
public class NumberConverters implements Converter {

    public static final NumberFormat defaultFormat;

    private NumberFormat format;

    public NumberConverters() {
        this(defaultFormat);
    }

    public NumberConverters(NumberFormat format) {
        this.format = format;
    }

    static {
        defaultFormat = NumberFormat.getNumberInstance();
        defaultFormat.setMinimumIntegerDigits(1);
        defaultFormat.setMaximumIntegerDigits(64);
        defaultFormat.setMinimumFractionDigits(0);
        defaultFormat.setMaximumFractionDigits(64);
    }

    @Override
    public void register(Registry registry) {
        Class<?>[] classes = new Class<?>[]{Number.class, Double.class,
            Float.class, Integer.class, Long.class, Short.class, Byte.class};
        for (Class<?> classe : classes) {
            for (Class<?> classe1 : classes) {
                registry.addConverter(classe, classe1, this);
            }
            if (!classe.equals(Number.class)) {
                registry.addConverter(classe, String.class, this);
                registry.addConverter(String.class, classe, this);
            }
        }
    }

    /**
     * Do conversion.
     *
     * @param targetType
     * @param value
     * @return
     */
    @Override
    public Object convert(Class targetType, Object value) {
        // are we dealing with a number to number conversion?
        if ((value instanceof Number) && Number.class.isAssignableFrom(targetType)) {
            if (Integer.class.equals(targetType)) {
                //intValue is the odd man out
                return ((Number) value).intValue();
            } else {
                try {
                    //everything else is short/long/float/byte/doubleValue().
                    Method m = value.getClass().getMethod(targetType.getSimpleName().toLowerCase() + "Value");
                    return m.invoke(value);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(NumberConverters.class.getName()).log(Level.SEVERE, null, ex);
                    throw new IllegalArgumentException("this code must not be reached");
                }
            }
        } else if ((value instanceof Number) && String.class.equals(targetType)) {
            //widening conversions only
            if ((value instanceof Double) || (value instanceof Float)) {
                return format.format(((Number) value).doubleValue());
            } else {
                return format.format(((Number) value).longValue());
            }
        } else if ((value instanceof String) && Number.class.isAssignableFrom(targetType)) {
            //convert fron string
            if (Integer.class.equals(targetType)) {
                //parseInt is the odd man out
                return Integer.parseInt(value.toString());
            } else {
                //everything else is parseShort/Long/Float/etc.
                try {
                    Method m = targetType.getMethod("parse" + targetType.getSimpleName(), String.class);
                    return m.invoke(null, value.toString());
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(NumberConverters.class.getName()).log(Level.SEVERE, null, ex);
                    throw new IllegalArgumentException("this code must not be reached");
                }
            }
        }
        throw new IllegalArgumentException("no conversion supported");
    }
}
