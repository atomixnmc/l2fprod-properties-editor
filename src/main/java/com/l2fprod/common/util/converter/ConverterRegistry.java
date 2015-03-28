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
 * ConverterRegistry. <br>
 *
 */
public final class ConverterRegistry implements Registry {

    private static final ConverterRegistry sharedInstance = new ConverterRegistry();

    private final Map fromMap;

    public ConverterRegistry() {
        fromMap = new HashMap();
        ServiceLoader<Converter> loader = ServiceLoader.load(Converter.class);
        Iterator<Converter> iterator = loader.iterator();
        while(iterator.hasNext()) {
            iterator.next().register((Registry) this);
        }
    }

    @Override
    public void addConverter(Class from, Class to, Converter converter) {
        Map toMap = (Map) fromMap.get(from);
        if (toMap == null) {
            toMap = new HashMap();
            fromMap.put(from, toMap);
        }
        toMap.put(to, converter);
    }

    @Override
    public Converter getConverter(Class from, Class to) {
        Map toMap = (Map) fromMap.get(from);
        if (toMap != null) {
            return (Converter) toMap.get(to);
        } else {
            return null;
        }
    }

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

    public static ConverterRegistry instance() {
        return sharedInstance;
    }
}
