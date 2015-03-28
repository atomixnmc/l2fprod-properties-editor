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
package com.l2fprod.common.swing.renderer;

import com.l2fprod.common.annotations.RendererRegistry;
import com.l2fprod.common.beans.editor.CalendarStringPropertyEditor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A renderer for Date.
 *
 * @author Ricardo Lopes
 */
@RendererRegistry(type = Calendar.class)
public class CalendarRenderer extends DefaultCellRenderer {

    private DateFormat dateFormat;

    public CalendarRenderer() {
        this(CalendarStringPropertyEditor.DEFAULT_DATE_FORMAT);
    }

    public CalendarRenderer(String formatString) {
        this(formatString, Locale.getDefault());
    }

    public CalendarRenderer(Locale l) {
        this(DateFormat.getDateInstance(DateFormat.SHORT, l));
    }

    public CalendarRenderer(String formatString, Locale l) {
        this(new SimpleDateFormat(formatString, l));
    }

    public CalendarRenderer(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            setText("");
        } else {
            setText(dateFormat.format(((Calendar) value).getTime()));
        }
    }

}
