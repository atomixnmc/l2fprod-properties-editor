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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matta
 */
public class DateRendererTest {
    
    public DateRendererTest() {
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
     * Test of setValue method, of class DateRenderer.
     */
    @Test
    public void testConstructor() {
        System.out.println("constructor");
        DateRenderer instance1 = new DateRenderer();
        assertNotNull(instance1);
        DateRenderer instance2 = new DateRenderer(Locale.US);
        assertNotNull(instance2);
        DateRenderer instance3 = new DateRenderer(DateFormat.getDateInstance());
        assertNotNull(instance3);
        DateRenderer instance4 = new DateRenderer("dd/MM/yyyy", Locale.US);
        assertNotNull(instance4);
        DateRenderer instance5 = new DateRenderer("dd/MM/yyyy");
        assertNotNull(instance5);
    }
    
}
