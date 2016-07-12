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
package com.l2fprod.common.swing.plaf;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
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
public class LookAndFeelAddonsTest {
    
    public LookAndFeelAddonsTest() {
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
     * Test of initialize method, of class LookAndFeelAddons.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        LookAndFeelAddons instance = new LookAndFeelAddons();
        instance.initialize();
    }

    /**
     * Test of uninitialize method, of class LookAndFeelAddons.
     */
    @Test
    public void testUninitialize() {
        System.out.println("uninitialize");
        LookAndFeelAddons instance = new LookAndFeelAddons();
        instance.uninitialize();
    }

    /**
     * Test of loadDefaults method, of class LookAndFeelAddons.
     */
    @Test
    public void testLoadDefaults() {
//        System.out.println("loadDefaults");
//        Object[] keysAndValues = null;
//        LookAndFeelAddons instance = new LookAndFeelAddons();
//        instance.loadDefaults(keysAndValues);
    }

    /**
     * Test of unloadDefaults method, of class LookAndFeelAddons.
     */
    @Test
    public void testUnloadDefaults() {
        System.out.println("unloadDefaults");
//        Object[] keysAndValues = null;
//        LookAndFeelAddons instance = new LookAndFeelAddons();
//        instance.unloadDefaults(keysAndValues);
    }

    /**
     * Test of setAddon method, of class LookAndFeelAddons.
     */
    @Test
    public void testSetAddon_String() throws Exception {
        System.out.println("setAddon");
//        String addonClassName = "";
//        LookAndFeelAddons.setAddon(addonClassName);
    }

    /**
     * Test of setAddon method, of class LookAndFeelAddons.
     */
    @Test
    public void testSetAddon_Class() throws Exception {
        System.out.println("setAddon");
//        Class addonClass = null;
//        LookAndFeelAddons.setAddon(addonClass);
    }

    /**
     * Test of setAddon method, of class LookAndFeelAddons.
     */
    @Test
    public void testSetAddon_LookAndFeelAddons() {
        System.out.println("setAddon");
//        LookAndFeelAddons addon = null;
//        LookAndFeelAddons.setAddon(addon);
    }

    /**
     * Test of getAddon method, of class LookAndFeelAddons.
     */
    @Test
    public void testGetAddon() {
        System.out.println("getAddon");
//        LookAndFeelAddons expResult = null;
//        LookAndFeelAddons result = LookAndFeelAddons.getAddon();
//        assertEquals(expResult, result);
    }

    /**
     * Test of getBestMatchAddonClassName method, of class LookAndFeelAddons.
     */
    @Test
    public void testGetBestMatchAddonClassName() {
        System.out.println("getBestMatchAddonClassName");
//        String expResult = "";
//        String result = LookAndFeelAddons.getBestMatchAddonClassName();
//        assertEquals(expResult, result);
    }

    /**
     * Test of getSystemAddonClassName method, of class LookAndFeelAddons.
     */
    @Test
    public void testGetSystemAddonClassName() {
        System.out.println("getSystemAddonClassName");
//        String expResult = "";
//        String result = LookAndFeelAddons.getSystemAddonClassName();
//        assertEquals(expResult, result);
    }

    /**
     * Test of contribute method, of class LookAndFeelAddons.
     */
    @Test
    public void testContribute() {
        System.out.println("contribute");
//        ComponentAddon component = null;
//        LookAndFeelAddons.contribute(component);
    }

    /**
     * Test of uncontribute method, of class LookAndFeelAddons.
     */
    @Test
    public void testUncontribute() {
        System.out.println("uncontribute");
//        ComponentAddon component = null;
//        LookAndFeelAddons.uncontribute(component);
    }

    /**
     * Test of getUI method, of class LookAndFeelAddons.
     */
    @Test
    public void testGetUI() {
        System.out.println("getUI");
//        JComponent component = null;
//        Class expectedUIClass = null;
//        ComponentUI expResult = null;
//        ComponentUI result = LookAndFeelAddons.getUI(component, expectedUIClass);
//        assertEquals(expResult, result);
    }

    /**
     * Test of setTrackingLookAndFeelChanges method, of class LookAndFeelAddons.
     */
    @Test
    public void testSetTrackingLookAndFeelChanges() {
        System.out.println("setTrackingLookAndFeelChanges");
//        boolean tracking = false;
//        LookAndFeelAddons.setTrackingLookAndFeelChanges(tracking);
    }

    /**
     * Test of isTrackingLookAndFeelChanges method, of class LookAndFeelAddons.
     */
    @Test
    public void testIsTrackingLookAndFeelChanges() {
        System.out.println("isTrackingLookAndFeelChanges");
//        boolean expResult = false;
//        boolean result = LookAndFeelAddons.isTrackingLookAndFeelChanges();
//        assertEquals(expResult, result);
    }
}
