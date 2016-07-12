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
package com.l2fprod.common.demo;

import java.awt.BorderLayout;
import java.awt.Robot;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author matta
 */
public class PropertySheetMainTest {

    public PropertySheetMainTest() {
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
     * Test of main method, of class PropertySheetMain.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        JFrame frame = new JFrame("PropertySheet");
        frame.getContentPane().setLayout(new BorderLayout());
        PropertySheetMain psm = new PropertySheetMain();
        frame.getContentPane().add("Center", psm);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocation(100, 100);
        frame.setVisible(true);
        
        Robot robot = new Robot();
        robot.delay(1000);
        psm.tabs.setSelectedIndex(1);
        robot.delay(1000);
        psm.tabs.setSelectedIndex(2);
        robot.delay(1000);
        frame.dispose();
    }

    void pressKey(Robot robot, int[] keys, int delay) {
        for (int key : keys) {
            robot.keyPress(key);
        }
        robot.delay(delay);
        for (int key : keys) {
            robot.keyRelease(key);
        }
        robot.delay(delay);
    }

}
