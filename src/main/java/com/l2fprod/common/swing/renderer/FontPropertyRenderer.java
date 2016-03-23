package com.l2fprod.common.swing.renderer;

import com.l2fprod.common.annotations.RendererRegistry;
import java.awt.Font;

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
/**
 *
 * @author matta
 */
@RendererRegistry(type = Font.class)
public class FontPropertyRenderer extends DefaultCellRenderer {

    @Override
    public void setValue(Object value) {
        super.setValue(((Font) value).getFontName()
                + ", "
                + ((Font) value).getSize());
    }
}
