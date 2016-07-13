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

import java.beans.BeanInfo;
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
public class DefaultBeanInfoResolverTest {

    public static class TestBean {

        int intValue;
        float floatValue;
        String stringValue;

        public String getString() {
            return stringValue;
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
    }

    public DefaultBeanInfoResolverTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        BeanInfo beanInfoHelper = DefaultBeanInfoResolver.getBeanInfoHelper(TestBean.class);
        assertNull(beanInfoHelper);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getBeanInfo method, of class DefaultBeanInfoResolver.
     */
    @Test
    public void testGetBeanInfo() {
        System.out.println("getBeanInfo");
        DefaultBeanInfoResolver res = new DefaultBeanInfoResolver();
        assertNull(res.getBeanInfo(null));
        BeanInfo beanInfo = res.getBeanInfo(new TestBean());
        assertNotNull(beanInfo);
        BeanInfo beanInfo2 = res.getBeanInfo(new TestBean());
        assertEquals(beanInfo, beanInfo2);
        assertNull(res.getBeanInfo((Object) null));
        assertNull(res.getBeanInfo((Class<?>) null));
        DefaultBeanInfoResolver.addBeanInfo(TestBean.class, beanInfo);
        BeanInfo beanInfoHelper = DefaultBeanInfoResolver.getBeanInfoHelper(TestBean.class);
        assertNotNull(beanInfoHelper);
    }
}
