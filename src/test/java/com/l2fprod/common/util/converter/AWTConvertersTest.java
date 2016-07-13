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
package com.l2fprod.common.util.converter;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matta
 */
public class AWTConvertersTest {

    public AWTConvertersTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of convert method, of class AWTConverters.
     */
    @Test
    public void testConvert() {
        System.out.println("convert");
        AWTConverters instance = new AWTConverters();
        
        assertNull(instance.convert(AWTConverters.class, ""));
        
        Rectangle r = new Rectangle(1, 1, 10, 10);
        String rstr = (String) instance.convert(String.class, r);
        assertNotNull(rstr);
        Insets i = new Insets(2, 2, 3, 3);
        String istr = (String) instance.convert(String.class, i);
        assertNotNull(istr);
        Dimension d = new Dimension(10, 10);
        String dstr = (String) instance.convert(String.class, d);
        assertNotNull(dstr);
        Point p = new Point(5, 5);
        String pstr = (String) instance.convert(String.class, p);
        assertNotNull(pstr);
        Font f = new Font("arial", Font.PLAIN, 10);
        String fstr = (String) instance.convert(String.class, f);
        assertNotNull(fstr);

        r = (Rectangle) instance.convert(Rectangle.class, rstr);
        assertNotNull(r);
        i = (Insets) instance.convert(Insets.class, istr);
        assertNotNull(i);
        d = (Dimension) instance.convert(Dimension.class, dstr);
        assertNotNull(d);
        p = (Point) instance.convert(Point.class, pstr);
        assertNotNull(p);
        f = (Font) instance.convert(Font.class, fstr);
        assertNull(f);

        try {
            r = (Rectangle) instance.convert(Rectangle.class, rstr + " 2");
            assertNotNull(r);
        } catch (IllegalArgumentException ex) {
        }
        try {
            i = (Insets) instance.convert(Insets.class, istr + " 2");
            assertNotNull(i);
        } catch (IllegalArgumentException ex) {
        }
        try {
            d = (Dimension) instance.convert(Dimension.class, dstr + " 2");
            assertNotNull(d);
        } catch (IllegalArgumentException ex) {
        }
        try {
            p = (Point) instance.convert(Point.class, pstr + " 2");
            assertNotNull(p);
        } catch (IllegalArgumentException ex) {
        }
        try {
            f = (Font) instance.convert(Font.class, fstr + " 2");
            assertNull(f);
        } catch (IllegalArgumentException ex) {
        }

        try {
            r = (Rectangle) instance.convert(Rectangle.class, "a b c d");
            assertNotNull(r);
        } catch (IllegalArgumentException ex) {
        }
        try {
            i = (Insets) instance.convert(Insets.class, istr + "a b c d");
            assertNotNull(i);
        } catch (IllegalArgumentException ex) {
        }
        try {
            d = (Dimension) instance.convert(Dimension.class, dstr + "a b");
            assertNotNull(d);
        } catch (IllegalArgumentException ex) {
        }
        try {
            p = (Point) instance.convert(Point.class, pstr + "a b");
            assertNotNull(p);
        } catch (IllegalArgumentException ex) {
        }
        try {
            f = (Font) instance.convert(Font.class, fstr + "arial 0 10");
            assertNull(f);
        } catch (IllegalArgumentException ex) {
        }
    }

}
