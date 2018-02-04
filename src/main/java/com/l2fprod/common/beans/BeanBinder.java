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

import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheetPanel;

import java.beans.BeanInfo;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.UIManager;

/**
 * Binds a bean object to a PropertySheet.
 *
 * When the property value of a bean changes, it is reflected back in the UI.
 */
public class BeanBinder {

    private final List<Object> bean = new ArrayList<Object>();
    private final PropertySheetPanel sheet;
    private final PropertyChangeListener listener;

    /**
     * Constructor.
     *
     * @param bean
     * @param sheet
     */
    public BeanBinder(final Object bean, final PropertySheetPanel sheet) {
        this(new Object[]{bean}, sheet, new DefaultBeanInfoResolver().getBeanInfo(bean));
    }

    /**
     * Constructor.
     *
     * @param bean
     * @param sheet
     */
    public BeanBinder(final Object[] bean, final PropertySheetPanel sheet) {
        this(bean, sheet, new DefaultBeanInfoResolver().getBeanInfo(bean));
    }

    /**
     * Constructor.
     *
     * @param bean
     * @param sheet
     * @param beanInfo
     */
    public BeanBinder(final Object[] bean, final PropertySheetPanel sheet, final BeanInfo beanInfo) {
        this.bean.addAll(Arrays.asList(bean));
//        this.bean = bean;
        this.sheet = sheet;

        for (Object bn : bean) {
            BeanInfo info = new DefaultBeanInfoResolver().getBeanInfo(bn);
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                sheet.addProperty(pd);
            }
            sheet.readFromObject(bn);
        }

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
                    for (Object bn : BeanBinder.this.bean) {
                        try {
                            prop.writeToObject(bn);
                            sheet.readFromObject(bn);
                        } catch (RuntimeException e) {
                            // handle PropertyVetoException and restore previous value
                            if (e.getCause() instanceof PropertyVetoException) {
                                UIManager.getLookAndFeel().provideErrorFeedback(
                                        BeanBinder.this.sheet);
                                prop.setValue(evt.getOldValue());
                            }
                        }
                    }
                }
                fire.set(true);
            }
        };
        sheet.addPropertySheetChangeListener(listener);
    }

    /**
     * Update the sheet data.
     */
    public void update() {
        for (Object bn : bean) {
            sheet.readFromObject(bn);
        }
    }

    /**
     * Unbind the bean and the sheet.
     */
    public void unbind() {
        sheet.removePropertyChangeListener(listener);
        sheet.setProperties(new Property[0]);
    }
}
