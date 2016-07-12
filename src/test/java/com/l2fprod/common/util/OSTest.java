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
package com.l2fprod.common.util;

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
public class OSTest {
    
    public OSTest() {
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
     * Test of isMacOSX method, of class OS.
     */
    @Test
    public void testIsMacOSX() {
        System.out.println("isMacOSX");
        boolean result = OS.isMacOSX();
    }

    /**
     * Test of isWindows method, of class OS.
     */
    @Test
    public void testIsWindows() {
        System.out.println("isWindows");
        boolean result = OS.isWindows();
    }

    /**
     * Test of isWindowsXP method, of class OS.
     */
    @Test
    public void testIsWindowsXP() {
        System.out.println("isWindowsXP");
        boolean result = OS.isWindowsXP();
    }

    /**
     * Test of isWindows2003 method, of class OS.
     */
    @Test
    public void testIsWindows2003() {
        System.out.println("isWindows2003");
        boolean result = OS.isWindows2003();
    }

    /**
     * Test of isWindowsVista method, of class OS.
     */
    @Test
    public void testIsWindowsVista() {
        System.out.println("isWindowsVista");
        boolean result = OS.isWindowsVista();
    }

    /**
     * Test of isLinux method, of class OS.
     */
    @Test
    public void testIsLinux() {
        System.out.println("isLinux");
        boolean result = OS.isLinux();
    }

    /**
     * Test of isUsingWindowsVisualStyles method, of class OS.
     */
    @Test
    public void testIsUsingWindowsVisualStyles() {
        System.out.println("isUsingWindowsVisualStyles");
        boolean result = OS.isUsingWindowsVisualStyles();
    }

    /**
     * Test of getWindowsVisualStyle method, of class OS.
     */
    @Test
    public void testGetWindowsVisualStyle() {
        System.out.println("getWindowsVisualStyle");
        String result = OS.getWindowsVisualStyle();
    }
    
}
