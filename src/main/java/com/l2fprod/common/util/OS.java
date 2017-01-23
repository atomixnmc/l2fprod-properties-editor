/*
 * Copyright 2015 Matthew Aguirre
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.l2fprod.common.util;

import java.awt.Toolkit;

import javax.swing.UIManager;

/**
 * Provides methods related to the runtime environment.
 */
public final class OS {

    private static final boolean OS_IS_MAC_OSX;
    private static final boolean OS_IS_WINDOWS;
    private static final boolean OS_IS_WINDOWS_XP;
    private static final boolean OS_IS_WINDOWS_2003;
    private static final boolean OS_IS_WINDOWS_VISTA;
    private static final boolean OS_IS_LINUX;

    static {
        String os = System.getProperty("os.name").toLowerCase();

        OS_IS_MAC_OSX = "mac os x".equals(os);
        OS_IS_WINDOWS = os != null && os.contains("windows");
        OS_IS_WINDOWS_XP = "windows xp".equals(os);
        OS_IS_WINDOWS_2003 = "windows 2003".equals(os);
        OS_IS_WINDOWS_VISTA = "windows vista".equals(os);
        OS_IS_LINUX = os != null && os.contains("linux");
    }

    private OS() {
    }

    /**
     * @return true if this VM is running on Mac OS X
     */
    public static boolean isMacOSX() {
        return OS_IS_MAC_OSX;
    }

    /**
     * @return true if this VM is running on Windows
     */
    public static boolean isWindows() {
        return OS_IS_WINDOWS;
    }

    /**
     * @return true if this VM is running on Windows XP
     */
    public static boolean isWindowsXP() {
        return OS_IS_WINDOWS_XP;
    }

    /**
     * @return true if this VM is running on Windows 2003
     */
    public static boolean isWindows2003() {
        return OS_IS_WINDOWS_2003;
    }

    /**
     * @return true if this VM is running on Windows Vista
     */
    public static boolean isWindowsVista() {
        return OS_IS_WINDOWS_VISTA;
    }

    /**
     * @return true if this VM is running on a Linux distribution
     */
    public static boolean isLinux() {
        return OS_IS_LINUX;
    }

    /**
     * @return true if the VM is running Windows and the Java application is
     * rendered using XP Visual Styles.
     */
    public static boolean isUsingWindowsVisualStyles() {
        if (!isWindows()) {
            return false;
        }

        boolean xpthemeActive = Boolean.TRUE.equals(Toolkit.getDefaultToolkit()
                .getDesktopProperty("win.xpstyle.themeActive"));
        if (!xpthemeActive) {
            return false;
        } else {
            try {
                return System.getProperty("swing.noxp") == null;
            } catch (RuntimeException e) {
                return true;
            }
        }
    }

    /**
     * Returns the name of the current Windows visual style.
     * <ul>
     * <li>it looks for a property name "win.xpstyle.name" in UIManager and if
     * not found
     * <li>it queries the win.xpstyle.colorName desktop property
     * ({@link Toolkit#getDesktopProperty(java.lang.String)})
     * </ul>
     *
     * @return the name of the current Windows visual style if any.
     */
    public static String getWindowsVisualStyle() {
        String style = UIManager.getString("win.xpstyle.name");
        if (style == null) {
            // guess the name of the current XPStyle
            // (win.xpstyle.colorName property found in awt_DesktopProperties.cpp in
            // JDK source)
            style = (String) Toolkit.getDefaultToolkit().getDesktopProperty(
                    "win.xpstyle.colorName");
        }
        return style;
    }

}
