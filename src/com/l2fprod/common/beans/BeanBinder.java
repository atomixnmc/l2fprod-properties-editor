/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
