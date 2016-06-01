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

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author matta
 */
public class NumberConvertersTest {

    public NumberConvertersTest() {
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
     * Test of convert method, of class NumberConverters.
     */
    @Test
    public void testConvertInteger() {
        Converter lookup = ConverterRegistry.instance().getConverter(String.class, Integer.class);
        for (int ii = 0; ii < 100; ii++) {
            Integer integer = ii;
            assertEquals(integer, lookup.convert(Integer.class, integer.toString()));
        }
        lookup = ConverterRegistry.instance().getConverter(Integer.class, String.class);
        for (int ii = 0; ii < 100; ii++) {
            Integer integer = ii;
            assertEquals(integer.toString(), lookup.convert(String.class, integer));
        }
    }

    @Test
    public void testConvertShort() {
        Converter lookup = ConverterRegistry.instance().getConverter(String.class, Short.class);
        for (short ii = 0; ii < 100; ii++) {
            Short integer = ii;
            assertEquals(integer, lookup.convert(Short.class, integer.toString()));
        }
        lookup = ConverterRegistry.instance().getConverter(Short.class, String.class);
        for (short ii = 0; ii < 100; ii++) {
            Short integer = ii;
            assertEquals(integer.toString(), lookup.convert(String.class, integer));
        }
    }

    @Test
    public void testConvertByte() {
        Converter lookup = ConverterRegistry.instance().getConverter(String.class, Byte.class);
        for (byte ii = 0; ii < 100; ii++) {
            Byte integer = ii;
            assertEquals(integer, lookup.convert(Byte.class, integer.toString()));
        }
        lookup = ConverterRegistry.instance().getConverter(Byte.class, String.class);
        for (byte ii = 0; ii < 100; ii++) {
            Byte integer = ii;
            assertEquals(integer.toString(), lookup.convert(String.class, integer));
        }
    }

    @Test
    public void testConvertLong() {
        Converter lookup = ConverterRegistry.instance().getConverter(String.class, Long.class);
        for (long ii = 0; ii < 100; ii++) {
            Long integer = ii;
            assertEquals(integer, lookup.convert(Long.class, integer.toString()));
        }
        lookup = ConverterRegistry.instance().getConverter(Long.class, String.class);
        for (long ii = 0; ii < 100; ii++) {
            Long integer = ii;
            assertEquals(integer.toString(), lookup.convert(String.class, integer));
        }
    }

    @Test
    public void testConvertDouble() {
        Converter lookup = ConverterRegistry.instance().getConverter(String.class, Double.class);
        for (double ii = 0.5; ii < 100; ii++) {
            Double integer = ii;
            assertEquals(integer, lookup.convert(Double.class, integer.toString()));
        }
        lookup = ConverterRegistry.instance().getConverter(Double.class, String.class);
        for (double ii = 0.5; ii < 100; ii++) {
            Double integer = ii;
            assertEquals(integer.toString(), lookup.convert(String.class, integer));
        }
    }

    @Test
    public void testConvertFloat() {
        Converter lookup = ConverterRegistry.instance().getConverter(String.class, Float.class);
        for (float ii = 0.5f; ii < 100; ii++) {
            Float integer = ii;
            assertEquals(integer, lookup.convert(Float.class, integer.toString()));
        }
        lookup = ConverterRegistry.instance().getConverter(Float.class, String.class);
        for (float ii = 0.5f; ii < 100; ii++) {
            Float integer = ii;
            assertEquals(integer.toString(), lookup.convert(String.class, integer));
        }
    }
}
