---
layout: post
title: Custom Editor
date: '2016-07-01 19:01:00 -0400'
categories: dev
tags:
  - documentation
author: Matthew Aguirre
---

Editors are used to allow a user to change the values of the specified property.

Some Editors allow text input, some can pop-up a combo box, and others can display new windows for complex data.

## String Value Editors

Probably the most basic method of changing a value is changing a `String` value.  The `String` editor sets up the editor to be a `JTextField` object and this is used withing the `AbstractPropertyEditor` to display and allow editing of a `String` value.

```java
package com.l2fprod.common.beans.editor;

import com.l2fprod.common.annotations.EditorRegistry;
import com.l2fprod.common.swing.LookAndFeelTweaks;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 * StringPropertyEditor.
 *
 */
@EditorRegistry (type = String.class)
public class StringPropertyEditor extends AbstractPropertyEditor {

    public StringPropertyEditor() {
        editor = new JTextField();
        ((JTextField) editor).setBorder(LookAndFeelTweaks.EMPTY_BORDER);
    }

    @Override
    public Object getValue() {
        return ((JTextComponent) editor).getText();
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            ((JTextComponent) editor).setText("");
        } else {
            ((JTextComponent) editor).setText(String.valueOf(value));
        }
    }
}
```

## Boolean Checkbox

This one is a bit more complex as it uses a `JCheckBox` as the editor instead of the above `JTextField`, but the concept is the same.

```java
package com.l2fprod.common.beans.editor;

import com.l2fprod.common.annotations.EditorRegistry;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

/**
 * BooleanAsCheckBoxPropertyEditor.
 *
 */
@EditorRegistry (type = {Boolean.class, boolean.class})
public class BooleanAsCheckBoxPropertyEditor extends AbstractPropertyEditor {

    public BooleanAsCheckBoxPropertyEditor() {
        editor = new JCheckBox();
        ((JCheckBox) editor).setOpaque(false);
        ((JCheckBox) editor).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firePropertyChange(
                        ((JCheckBox) editor).isSelected() ? Boolean.FALSE : Boolean.TRUE,
                        ((JCheckBox) editor).isSelected() ? Boolean.TRUE : Boolean.FALSE);
                ((JCheckBox) editor).transferFocus();
            }
        });
    }

    @Override
    public Object getValue() {
        return ((JCheckBox) editor).isSelected() ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public void setValue(Object value) {
        ((JCheckBox) editor).setSelected(Boolean.TRUE.equals(value));
    }
}
```

## Editors Spawning a new Window

This is an editor from my work where I popped up a map for selecting lat/lon regions from a map and returning back bounding boxes.  This one adds a mini button and when clicked, pops up the new windows for handling the data input.  In this example, the window popped up inherits from JDialog.

```java
package com.artistech.algolink.gui.editors;

import com.artistech.algolink.gui.map.EditingMapWindow;
import com.artistech.geo.bounding.BoundingArea;
import com.l2fprod.common.annotations.EditorRegistry;
import com.l2fprod.common.beans.editor.AbstractPropertyEditor;
import com.l2fprod.common.swing.ComponentFactory;
import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.swing.PercentLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

@EditorRegistry(type = BoundingArea.class)
public class BoundingAreaPropertyEditor extends AbstractPropertyEditor {

    private BoundingArea _boundingArea;
    protected final JTextField textfield;
    private final JButton button;
    private final static Logger LOGGER = Logger.getLogger(BoundingAreaPropertyEditor.class.getName());

    public BoundingAreaPropertyEditor() {
        this(true);
    }

    public BoundingAreaPropertyEditor(boolean asTableEditor) {
        editor = new JPanel(new PercentLayout(PercentLayout.HORIZONTAL, 0)) {
            @Override
            public void setEnabled(boolean enabled) {
                super.setEnabled(enabled);
                textfield.setEnabled(enabled);
                button.setEnabled(enabled);
            }
        };
        ((JPanel) editor).add("*", textfield = new JTextField());
        ((JPanel) editor).add(button = ComponentFactory.Helper.getFactory()
                .createMiniButton());
        if (asTableEditor) {
            textfield.setEditable(false);
            textfield.setBorder(LookAndFeelTweaks.EMPTY_BORDER);
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectBounds();
            }
        });
    }

    @Override
    public Object getValue() {
        return _boundingArea;
    }

    @Override
    public void setValue(Object value) {
        if (value != null && BoundingArea.class.isAssignableFrom(value.getClass())) {
            _boundingArea = (BoundingArea) value;
            textfield.setText(_boundingArea.getClass().getSimpleName());
        }
    }

    protected void selectBounds() {
        final Object o = getValue();
        final EditingMapWindow mw = new EditingMapWindow();
        mw.setBoundingArea((BoundingArea) o);
        mw.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent we) {
            }

            @Override
            public void windowClosing(WindowEvent we) {
                setValue(mw.getBoundingArea());
            }

            @Override
            public void windowClosed(WindowEvent we) {
            }

            @Override
            public void windowIconified(WindowEvent we) {
            }

            @Override
            public void windowDeiconified(WindowEvent we) {
            }

            @Override
            public void windowActivated(WindowEvent we) {
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
        mw.setVisible(true);
    }
}
```

# Registration

1. The `java.beans.PropertyEditor` class must be the base type for the editor.
2. A custom attribute `@EditorRegistry(type = { ... types ... })` is used at the Class level.
3. In the META-INF.services directory, create a file: `java.beans.PropertyEditor` and add the full class name of the new editor.

If using Maven, the services file (#3) can be automatically generated using the `eu.somatik.serviceloader-maven-plugin` plugin in your pom.xml file.

```xml
<plugin>
    <groupId>eu.somatik.serviceloader-maven-plugin</groupId>
    <artifactId>serviceloader-maven-plugin</artifactId>
    <version>1.0.7</version>
    <configuration>
        <services>
            <param>java.beans.PropertyEditor</param>
        </services>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

This will automatically generate the ServiceLoader file with all derived classes from `java.beans.PropertyEditor`.
