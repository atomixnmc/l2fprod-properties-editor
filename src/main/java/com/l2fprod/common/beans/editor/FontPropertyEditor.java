/*
 * Copyright 2016 matta.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.l2fprod.common.beans.editor;

import com.l2fprod.common.annotations.EditorRegistry;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JComboBox;

/**
 *
 * @author matta
 */
@EditorRegistry(type = Font.class)
public class FontPropertyEditor extends ComboBoxPropertyEditor {

    private final static Map<String, Font> fontMap = new TreeMap<>();

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FontPropertyEditor() {
        for (Font f : GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()) {
            if (!fontMap.containsKey(f.getName())) {
                fontMap.put(f.getName(), f);
            }
        }
        setAvailableValues(fontMap.keySet().toArray(new String[]{}));
    }

    @Override
    public Object getValue() {
        JComboBox e = (JComboBox) editor;
        String f1 = (String) e.getSelectedItem();
        Font f = new Font(f1, Font.PLAIN, 12);
        return f;
    }

    @Override
    public void setValue(Object value) {
        JComboBox e = (JComboBox) editor;

        Font f = (Font) value;
        for (int ii = 0; ii < e.getItemCount(); ii++) {
            if (value != null && e.getItemAt(ii).equals(f.getName())) {
                e.setSelectedIndex(ii);
                break;
            }
        }
    }
}
