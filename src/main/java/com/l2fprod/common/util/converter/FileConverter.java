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

import java.io.File;

/**
 * Uses the getAbsolutePath() method for toString().
 * 
 * Converts a File to string and vice-versa.
 */
public class FileConverter implements Converter {

    @Override
    public void register(Registry registry) {
        registry.addConverter(String.class, File.class, this);
        registry.addConverter(File.class, String.class, this);
    }

    @Override
    public Object convert(Class<?> type, Object value) {
        if (String.class.equals(type) && File.class.equals(value.getClass())) {
            return ((File)value).getAbsolutePath();
        } else if (File.class.equals(type) || String.class.equals(type)) {
            return new File((String) value);
        } else {
            throw new IllegalArgumentException("Can't convert " + value + " to "
                    + type.getName());
        }
    }
}
