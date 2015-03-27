/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
