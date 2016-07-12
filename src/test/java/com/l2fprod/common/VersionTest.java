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
package com.l2fprod.common;

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
public class VersionTest {
    
    public VersionTest() {
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
     * Test of getVersion method, of class Version.
     */
    @Test
    public void testGetVersion() {
        System.out.println("getVersion");
        Version instance = new Version();
        assertNotNull(instance.getVersion());
    }

    /**
     * Test of getBuildtime method, of class Version.
     */
    @Test
    public void testGetBuildtime() {
        System.out.println("getBuildtime");
        Version instance = new Version();
        assertNotNull(instance.getBuildtime());
    }

    /**
     * Test of getBuilder method, of class Version.
     */
    @Test
    public void testGetBuilder() {
        System.out.println("getBuilder");
        Version instance = new Version();
        assertNotNull(instance.getVersion());
    }

    /**
     * Test of getApplicationName method, of class Version.
     */
    @Test
    public void testGetApplicationName() {
        System.out.println("getApplicationName");
        Version instance = new Version();
        assertNotNull(instance.getApplicationName());
    }

    /**
     * Test of getCompany method, of class Version.
     */
    @Test
    public void testGetCompany() {
        System.out.println("getCompany");
        Version instance = new Version();
        assertNotNull(instance.getCompany());
    }

    /**
     * Test of toString method, of class Version.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Version instance = new Version();
        assertNotNull(instance.toString());
    }
    
}
