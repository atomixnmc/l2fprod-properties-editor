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
package com.l2fprod.common.beans.editor;

import com.l2fprod.common.annotations.EditorRegistry;
import com.l2fprod.common.util.converter.ConverterRegistry;

import java.awt.Dimension;

/**
 * DimensionPropertyEditor. <br>Editor for java.awt.Dimension object, where the
 * dimension is specified as "width x height"
 */
@EditorRegistry(type = Dimension.class)
public class DimensionPropertyEditor extends StringConverterPropertyEditor {

    @Override
    protected Object convertFromString(String text) {
        return ConverterRegistry.instance().convert(Dimension.class, text);
    }

}
