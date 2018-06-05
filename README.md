# l2fprod
[![Join the chat at https://gitter.im/ZenHarbinger/l2fprod-properties-editor](https://badges.gitter.im/ZenHarbinger/l2fprod-properties-editor.svg)](https://gitter.im/ZenHarbinger/l2fprod-properties-editor?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![Build Status](https://circleci.com/gh/ZenHarbinger/l2fprod-properties-editor.svg?style=shield&circle-token=:circle-token)](https://circleci.com/gh/ZenHarbinger/l2fprod-properties-editor)
[![Coverage Status](https://codecov.io/github/ZenHarbinger/l2fprod-properties-editor/coverage.svg?branch=master)](https://codecov.io/github/ZenHarbinger/l2fprod-properties-editor) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.tros/l2fprod-properties-editor/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.tros/l2fprod-properties-editor/) [![License](https://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Fork and Extension of l2fprod's Java properties editing component.

I found this source somewhere on the Internet after looking for a property editor for Java.
Originally it came from [l2fprod](http://www.l2fprod.com/common/), but it is no longer available for download there.
I honestly don't remember where I found the source anymore, but the available sources on [Maven](http://mvnrepository.com/artifact/com.l2fprod.common/l2fprod-common-shared/6.9.1) don't seem to have the PropertySheet editing source anymore.  There is a package avaiable by [another third party though](http://mvnrepository.com/artifact/org.nuiton.thirdparty/l2fprod-common/0.1).

I have been working w/ this code for a while and have updated some of it to Java6 with Generics and ServiceLoader functionality.  I have been using it in a project for work.  Changes are made as needed.

For more complete examples from below to go [to my web-site](http://tros.org/l2fprod-properties-editor/).

## Example
In this example, data is an object with get/set methods which we wish to configure.

```java
final PropertySheetPanel sheet = new PropertySheetPanel();
JPanel p = new JPanel();
p.setLayout(new BorderLayout());
p.setBorder(BorderFactory.createTitledBorder(data.displayName()));
add(p, java.awt.BorderLayout.CENTER);
sheet.setMode(PropertySheet.VIEW_AS_CATEGORIES);
sheet.setDescriptionVisible(true);
sheet.setSortingCategories(true);
sheet.setSortingProperties(true);
sheet.setRestoreToggleStates(true);
p.add(sheet, java.awt.BorderLayout.CENTER);

// everytime a property change, update the sheet with it
BeanBinder bb = new BeanBinder(data, sheet);
```

## Extending
To extend addtional types, 2 new classes are used.

### Renderers
The renderer class will display the value of the object while it is not being edited.  By default, toString() is called if a new type is not defined and registered.
 1. First, the `javax.swing.table.TableCellRenderer` class must be derived.
 2. A custom attribute `@RendererRegistry(type = { ... types ... })` is added.
 3. In the META-INF.services directory, create a file: `javax.swing.table.TableCellRenderer` and add the full class name of the new Renderer.

### Editors
The renderer class will allow the user to change the value of the type.
 1. First, the `java.beans.PropertyEditor` class must be derived.
 2. A custom attribute `@EditorRegistry (type = { ... types ... } )` is added.
 3. In the META-INF.services directory, create a file: `java.beans.PropertyEditor` and add the full class name of the new Editor.
