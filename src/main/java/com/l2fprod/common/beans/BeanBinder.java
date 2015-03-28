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
package com.l2fprod.common.beans;

import com.l2fprod.common.model.DefaultBeanInfoResolver;
import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheetPanel;

import java.beans.BeanInfo;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.UIManager;

/**
 * Binds a bean object to a PropertySheet.
 *
 * <b>Note: this class is not part of the library</b>
 */
public class BeanBinder {

    private final Object bean;
    private final PropertySheetPanel sheet;
    private final PropertyChangeListener listener;

    public BeanBinder(final Object bean, final PropertySheetPanel sheet) {
        this(bean, sheet, new DefaultBeanInfoResolver().getBeanInfo(bean));
    }

    public BeanBinder(final Object bean, final PropertySheetPanel sheet, final BeanInfo beanInfo) {
        this.bean = bean;
        this.sheet = sheet;

        sheet.setProperties(beanInfo.getPropertyDescriptors());
        sheet.readFromObject(bean);

        //changing the property causes this to recursivly call,
        //so kill it before it gets out of hand.
        final AtomicBoolean fire = new AtomicBoolean(true);

        // everytime a property change, update the button with it
        listener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (fire.get()) {
                    fire.set(false);
                    Property prop = (Property) evt.getSource();
                    try {
                        prop.writeToObject(BeanBinder.this.bean);
                        sheet.readFromObject(bean);
                    } catch (RuntimeException e) {
                        // handle PropertyVetoException and restore previous value
                        if (e.getCause() instanceof PropertyVetoException) {
                            UIManager.getLookAndFeel().provideErrorFeedback(
                                    BeanBinder.this.sheet);
                            prop.setValue(evt.getOldValue());
                        }
                    }
                }
                fire.set(true);
            }
        };
        sheet.addPropertySheetChangeListener(listener);
    }

    public void update() {
        sheet.readFromObject(bean);
    }

    public void unbind() {
        sheet.removePropertyChangeListener(listener);
        sheet.setProperties(new Property[0]);
    }
}
