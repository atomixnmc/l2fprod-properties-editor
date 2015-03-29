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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * ConverterRegistry.
 */
public final class ConverterRegistry implements Registry {

    private static final ConverterRegistry sharedInstance = new ConverterRegistry();
    private final Map<Class<?>, Map<Class<?>, Converter>> fromMap;

    /**
     * Constructor.
     */
    private ConverterRegistry() {
        //use ServiceLoader to get instances of Converter classes.
        //force them to register w/ this class.
        fromMap = new HashMap<>();
        ServiceLoader<Converter> loader = ServiceLoader.load(Converter.class);
        Iterator<Converter> iterator = loader.iterator();
        while (iterator.hasNext()) {
            iterator.next().register((Registry) this);
        }
    }

    /**
     * Converter calls this method to register conversion path.
     *
     * @param from
     * @param to
     * @param converter
     */
    @Override
    public void addConverter(Class from, Class to, Converter converter) {
        Map toMap = fromMap.get(from);
        if (toMap == null) {
            toMap = new HashMap<>();
            fromMap.put(from, toMap);
        }
        toMap.put(to, converter);
    }

    /**
     * Get the desired converter.
     *
     * @param from
     * @param to
     * @return
     */
    @Override
    public Converter getConverter(Class from, Class to) {
        Map<Class<?>, Converter> toMap = fromMap.get(from);
        if (toMap != null) {
            return toMap.get(to);
        } else {
            return null;
        }
    }

    /**
     * Do Conversion.
     *
     * @param targetType
     * @param value
     * @return
     */
    public Object convert(Class targetType, Object value) {
        if (value == null) {
            return null;
        }

        Converter converter = getConverter(value.getClass(), targetType);
        if (converter == null) {
            throw new IllegalArgumentException(
                    "No converter from " + value.getClass() + " to " + targetType.getName());
        } else {
            return converter.convert(targetType, value);
        }
    }

    /**
     * Get the instance.
     *
     * @return
     */
    public static ConverterRegistry instance() {
        return sharedInstance;
    }
}
