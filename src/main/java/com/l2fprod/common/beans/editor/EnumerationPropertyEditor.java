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
import javax.swing.JComboBox;

/**
 *
 * @author matta
 */
@EditorRegistry(type = Enum.class)
public class EnumerationPropertyEditor extends ComboBoxPropertyEditor {

    private Class<?> baseType;

    public EnumerationPropertyEditor() {
        super();

    }

    @Override
    public Object getValue() {
        JComboBox e = (JComboBox) editor;
        return e.getSelectedItem();
    }

    @Override
    public void setValue(Object value) {
        if (this.baseType == null) {
            this.baseType = value.getClass();
            setAvailableValues(baseType.getEnumConstants());
        }

        JComboBox e = (JComboBox) editor;

        for (int ii = 0; ii < e.getItemCount(); ii++) {
            if (value != null && e.getItemAt(ii).equals(value.getClass().getCanonicalName())) {
                e.setSelectedIndex(ii);
                break;
            }
        }
    }
}
