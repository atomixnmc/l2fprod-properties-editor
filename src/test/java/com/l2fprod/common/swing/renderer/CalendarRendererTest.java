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
package com.l2fprod.common.swing.renderer;

import java.text.DateFormat;
import java.util.Locale;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author matta
 */
public class CalendarRendererTest {
    
    public CalendarRendererTest() {
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
     * Test of setValue method, of class CalendarRenderer.
     */
    @Test
    public void testConstructor() {
        System.out.println("constructor");
        CalendarRenderer instance1 = new CalendarRenderer();
        assertNotNull(instance1);
        CalendarRenderer instance2 = new CalendarRenderer(Locale.US);
        assertNotNull(instance2);
        CalendarRenderer instance3 = new CalendarRenderer(DateFormat.getDateInstance());
        assertNotNull(instance3);
        CalendarRenderer instance4 = new CalendarRenderer("dd/MM/yyyy", Locale.US);
        assertNotNull(instance4);
        CalendarRenderer instance5 = new CalendarRenderer("dd/MM/yyyy");
        assertNotNull(instance5);
    }
    
}
