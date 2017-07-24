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
package com.l2fprod.common.demo;

import com.l2fprod.common.annotations.PropertyEditorOverride;
import com.l2fprod.common.annotations.PropertyRendererOverride;
import com.l2fprod.common.beans.BeanBinder;
import com.l2fprod.common.beans.editor.ComboBoxPropertyEditor;
import com.l2fprod.common.propertysheet.PropertySheet;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.swing.renderer.DefaultCellRenderer;
import com.l2fprod.common.annotations.Description;
import com.l2fprod.common.annotations.DisplayName;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import javax.swing.Icon;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * PropertySheetPage. <br>
 *
 */
public class PropertySheetPage extends JPanel {

    public enum Seasons {

        SUMMER,
        FALL,
        WINTER,
        SPRING
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PropertySheetPage() {
        setLayout(LookAndFeelTweaks.createVerticalPercentLayout());

        JTextArea message = new JTextArea();
        message.setText(PropertySheetMain.RESOURCE.getString("Main.sheet1.message"));
        LookAndFeelTweaks.makeMultilineLabel(message);
        add(message);

        final Bean data = new Bean();
        data.setName("John Smith");
        data.setText("Any text here");
        data.setColor(Color.green);
        data.setPath(new File("."));
        data.setVisible(true);
        data.setTime(System.currentTimeMillis());
        data.setCalendar(java.util.Calendar.getInstance());

        final PropertySheetPanel sheet = new PropertySheetPanel();
        sheet.setMode(PropertySheet.VIEW_AS_CATEGORIES);
        sheet.setDescriptionVisible(true);
        sheet.setSortingCategories(true);
        sheet.setSortingProperties(true);
        sheet.setRestoreToggleStates(true);
        add(sheet, "*");

        // everytime a property change, update the sheet with it
        new BeanBinder(data, sheet);
    }

    public static class Bean {

        private java.util.Calendar calendar;
        private java.util.Date date;
        private java.util.Calendar calendar1;
        private java.util.Date date1;
        private String name;
        private String text;
        private long time;
        private boolean visible;
        private int id;
        private File path;
        private Color color = Color.blue;
        private double doubleValue = 121210.4343543;
        private Seasons seasonEnum = Seasons.SUMMER;
        private String season = "SUMMER";
        private String constrained;

        public Bean() {
            calendar = Calendar.getInstance();
            date = calendar.getTime();
        }

        public java.util.Calendar getCalendar() {
            return calendar;
        }

        public void setCalendar(java.util.Calendar value) {
            calendar = value;
        }

        public java.util.Date getDate() {
            return date;
        }

        public void setDate(java.util.Date value) {
            date = value;
        }

        public java.util.Calendar getCalendar1() {
            return calendar1;
        }

        public void setCalendar1(java.util.Calendar value) {
            calendar1 = value;
        }

        public java.util.Date getDate1() {
            return date1;
        }

        public void setDate1(java.util.Date value) {
            date1 = value;
        }

        @Description("The name of this object<br>Here I'm using multple lines<br>for the property<br>so scrollbars will get enabled")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getVersion() {
            return "1.0";
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public File getPath() {
            return path;
        }

        public void setPath(File path) {
            this.path = path;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void setADouble(double d) {
            this.doubleValue = d;
        }

        @DisplayName("a double")
        @Description("a double description")
        public double getADouble() {
            return doubleValue;
        }

        public void setSeasonEnum(Seasons s) {
            seasonEnum = s;
        }

        public Seasons getSeasonEnum() {
            return seasonEnum;
        }

        @PropertyEditorOverride(type = SeasonEditor.class)
        public void setSeason(String s) {
            season = s;
        }

        @PropertyRendererOverride(type = SeasonRenderer.class)
        public String getSeason() {
            return season;
        }

        public String getConstrained() {
            return constrained;
        }

        public void setConstrained(String constrained) throws PropertyVetoException {
            if ("blah".equals(constrained)) {
                throw new PropertyVetoException("e",
                        new PropertyChangeEvent(this, "constrained", this.constrained,
                                constrained));
            }
            this.constrained = constrained;
        }

        @Override
        public String toString() {
            return "[name=" + getName() + ",text=" + getText() + ",time=" + getTime()
                    + ",version=" + getVersion() + ",visible=" + isVisible() + ",id="
                    + getId() + ",path=" + getPath() + ",aDouble=" + getADouble()
                    + ",season=" + getSeason() + "]";
        }

    }

    public static class SeasonEditor extends ComboBoxPropertyEditor {

        @SuppressWarnings("OverridableMethodCallInConstructor")
        public SeasonEditor() {
            super();
            setAvailableValues(new String[]{"Spring", "Summer", "Fall", "Winter",});
            Icon[] icons = new Icon[4];
            Arrays.fill(icons, UIManager.getIcon("Tree.openIcon"));
            setAvailableIcons(icons);
        }
    }

    public static class SeasonRenderer extends DefaultCellRenderer {

        public SeasonRenderer() {
            super();
        }

        @Override
        public void setValue(Object value) {
            if (value == null) {
                setText("");
            } else {
                setText(value + " STRING");
            }
        }
    }
//
//    public static class BeanRB extends ListResourceBundle {
//
//        @Override
//        protected Object[][] getContents() {
//            return new Object[][]{{"name", "Name"},
//            {"name.shortDescription", "The name of this object<br>Here I'm using multple lines<br>for the property<br>so scrollbars will get enabled"},
//            {"text", "Text"}, {"time", "Time"}, {"color", "Background"},
//            {"aDouble", "a double"},
//            {"season", "Season"},
//            {
//                "constrained.shortDescription",
//                "This property is constrained. Try using <b>blah</b> as the value, the previous value will be restored"}};
//        }
//    }

}
