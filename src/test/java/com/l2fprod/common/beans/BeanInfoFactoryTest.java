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
package com.l2fprod.common.beans;

import com.l2fprod.common.annotations.DirectoryProperty;
import com.l2fprod.common.annotations.FileProperty;
import java.beans.BeanInfo;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class BeanInfoFactoryTest {

    public static class TestBean {

        int intValue;
        float floatValue;
        String stringValue;
        File fileValue;
        File dirValue;

        public TestBean() {
            try {
                fileValue = File.createTempFile("tmp", "file");
            } catch (IOException ex) {
                Logger.getLogger(BeanInfoFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            dirValue = fileValue.getParentFile();
        }

        public void setString(String value) {
            stringValue = value;
        }

        public int getInt() {
            return intValue;
        }

        public void setInt(int value) {
            intValue = value;
        }

        public float getFloat() {
            return floatValue;
        }

        public void setFloat(float value) {
            floatValue = value;
        }

        @FileProperty
        public File getFile() {
            return fileValue;
        }

        @FileProperty
        public void setFile(File value) {
            fileValue = value;
        }

        @DirectoryProperty
        public File getDir() {
            return dirValue;
        }

        @DirectoryProperty
        public void setDir(File value) {
            dirValue = value;
        }
    }

    public BeanInfoFactoryTest() {
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
     * Test of createBeanInfo method, of class BeanInfoFactory.
     */
    @Test
    public void testCreateBeanInfo() {
        System.out.println("createBeanInfo");
        BeanInfo result = BeanInfoFactory.createBeanInfo(TestBean.class);
        assertNotNull(result.getPropertyDescriptors());
        assertNotNull(result.getBeanDescriptor());
        if (BaseBeanInfo.class.isAssignableFrom(result.getClass())) {
            BaseBeanInfo bbi = (BaseBeanInfo) result;
            for (int ii = 0; ii < bbi.getPropertyDescriptorCount(); ii++) {
                assertNotNull(bbi.getPropertyDescriptor(ii));
            }
            boolean thrown = false;
            try {
                bbi.addProperty(null);
            } catch (RuntimeException ex) {
                thrown = true;
            }
            assertTrue(thrown);
            thrown = false;
            try {
                bbi.removeProperty(null);
            } catch (IllegalArgumentException ex) {
                thrown = true;
            }
            assertTrue(thrown);
            bbi.getIcon(0);
        }
        assertNotNull(result);
    }

}
