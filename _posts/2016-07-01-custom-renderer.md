---
layout: post
title: Custom Renderer
date: '2016-07-01 13:37:00 -0400'
categories: dev
tags:
  - documentation
author: Matthew Aguirre
---

Renderers are used to display the data in the properties editor when NOT editing the value.

These are generally pretty straight forward. The DefaultCellRenderer provides the ability to show an Icon and String rendering of the value to display.

## Font Renderer

Here is a quick example of a `Font` object being rendered using a `DefaultCellRenderer`.

```java
package com.l2fprod.common.swing.renderer;

import com.l2fprod.common.annotations.RendererRegistry;
import java.awt.Font;

@RendererRegistry(type = Font.class)
public class FontPropertyRenderer extends DefaultCellRenderer {

    @Override
    public void setValue(Object value) {
        super.setValue(((Font) value).getFontName()
                + ", "
                + ((Font) value).getSize());
    }
}
```

Essentially, use the `RendererRegistry` custom annotation to register as a Renderer for the `Font` class type.

Then call the `super.setValue(...)` with a `String` value to represent the value of the `Font` object to modify. This will display the name and the font size of the Font object.

## Color Renderer

Here you can see that we are registering the class to be a renderer for the `Color` class type.

```java
package com.l2fprod.common.swing.renderer;

import com.l2fprod.common.annotations.RendererRegistry;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.Icon;
import javax.swing.UIManager;

/**
 * ColorCellRenderer.
 */
@RendererRegistry(type = Color.class)
public class ColorCellRenderer extends DefaultCellRenderer {

    public static String toHex(Color color) {
        String red = Integer.toHexString(color.getRed());
        String green = Integer.toHexString(color.getGreen());
        String blue = Integer.toHexString(color.getBlue());

        if (red.length() == 1) {
            red = "0" + red;
        }
        if (green.length() == 1) {
            green = "0" + green;
        }
        if (blue.length() == 1) {
            blue = "0" + blue;
        }
        return ("#" + red + green + blue).toUpperCase();
    }

    @Override
    protected String convertToString(Object value) {
        if (value instanceof Integer) {
            value = new Color(((Integer) value));
        }
        if (!(value instanceof Color)) {
            return null;
        }

        Color color = (Color) value;
        return "R:" + color.getRed() + " G:" + color.getGreen() + " B:"
                + color.getBlue() + " - " + toHex(color);
    }

    @Override
    protected Icon convertToIcon(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            value = new Color(((Integer) value));
        }
        return new PaintIcon((Paint) value);
    }

    public static class PaintIcon implements Icon {

        private final Paint color;
        private final int width;
        private final int height;

        public PaintIcon(Paint color) {
            this(color, 20, 10);
        }

        public PaintIcon(Paint color, int width, int height) {
            this.color = color;
            this.width = width;
            this.height = height;
        }

        @Override
        public int getIconHeight() {
            return height;
        }

        @Override
        public int getIconWidth() {
            return width;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g;
            Paint oldPaint = g2d.getPaint();

            if (color != null) {
                g2d.setPaint(color);
                g.fillRect(x, y, getIconWidth(), getIconHeight());
            }

            g.setColor(UIManager.getColor("controlDkShadow"));
            g.drawRect(x, y, getIconWidth(), getIconHeight());

            g2d.setPaint(oldPaint);
        }
    }
}
```

In this case, I am not calling `super.setValue(...)`, instead I am overriding the `convertToIcon` and `convertToString` methods. The DefaultCellRenderer class uses the setValue() method to call these 2 methods.

```java
@Override
public void setValue(Object value) {
    String text = convertToString(value);
    Icon icon = convertToIcon(value);

    setText(text == null ? "" : text);
    setIcon(icon);
    setDisabledIcon(icon);
}

protected String convertToString(Object value) {
    return objectRenderer.getText(value);
}

protected Icon convertToIcon(Object value) {
    return null;
}
```

So when it is done; you get an icon of the color, the RGB, and Hex representation.

![]({{site.baseurl}}/tros-images/color-screen.png)

# Registration

1. The `javax.swing.table.TableCellRenderer` class must be the base type for the renderer.
2. A custom attribute `@RendererRegistry(type = { ... types ... })` is used at the Class level.
3. In the META-INF.services directory, create a file: `javax.swing.table.TableCellRenderer` and add the full class name of the new renderer.

If using Maven, the services file (#3) can be automatically generated using the `eu.somatik.serviceloader-maven-plugin` plugin in your pom.xml file.

```xml
<plugin>
    <groupId>eu.somatik.serviceloader-maven-plugin</groupId>
    <artifactId>serviceloader-maven-plugin</artifactId>
    <version>1.0.7</version>
    <configuration>
        <services>
            <param>javax.swing.table.TableCellRenderer</param>
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

This will automatically generate the ServiceLoader file with all derived classes from `javax.swing.table.TableCellRenderer`.
