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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author matta
 */
public class BooleanConverterTest {
    
    public BooleanConverterTest() {
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
     * Test of convert method, of class BooleanConverter.
     */
    @Test
    public void testConvert() {
        Converter lookup = ConverterRegistry.instance().getConverter(String.class, Boolean.class);
        assertTrue((Boolean) lookup.convert(Boolean.class, "true"));
        assertFalse((Boolean) lookup.convert(Boolean.class, "false"));
        lookup = ConverterRegistry.instance().getConverter(Boolean.class, String.class);
        assertEquals("true", lookup.convert(String.class, true));
        assertEquals("false", lookup.convert(String.class, false));
    }
}
