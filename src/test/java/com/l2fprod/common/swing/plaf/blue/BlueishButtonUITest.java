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
package com.l2fprod.common.swing.plaf.blue;

import java.awt.Graphics;
import javax.swing.JComponent;
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
public class BlueishButtonUITest {
    
    public BlueishButtonUITest() {
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
     * Test of installUI method, of class BlueishButtonUI.
     */
    @Test
    public void testInstallUI() {
        System.out.println("installUI");
        BlueishButtonUI bbui = new BlueishButtonUI();
        assertNotNull(bbui);
    }

    /**
     * Test of paint method, of class BlueishButtonUI.
     */
    @Test
    public void testPaint() {
        System.out.println("paint");
        BlueishButtonUI bbui = new BlueishButtonUI();
        assertNotNull(bbui);
    }
    
}
