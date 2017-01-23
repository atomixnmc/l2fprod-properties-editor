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
package com.l2fprod.common;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides information gathered from the build environment.
 *
 * @author Matthew Aguirre
 */
public final class Version {

    /**
     * Singleton Instance.
     */
    public static final Version INSTANCE = new Version();

    private String version;
    private String buildDate;
    private String builder;
    private String company;
    private String applicationName;

    /**
     * Constructor.
     *
     * Read build information from the properties file. Values in the properties
     * file are set at build-time if applicable.
     */
    public Version() {
        //load prop file...
        try {
            Properties prop = new Properties();

            String propFile = this.getClass().getPackage().getName().replace('.', '/') + '/' + this.getClass().getSimpleName() + ".properties";
            URL url = ClassLoader.getSystemClassLoader().getResource(propFile);

            prop.load(url.openStream());
            this.version = prop.getProperty("version");
            this.buildDate = prop.getProperty("buildTime");
            this.builder = prop.getProperty("builder");
            this.company = prop.getProperty("company");
            this.applicationName = prop.getProperty("project");
        } catch (IOException ex) {
            Logger.getLogger(Version.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get the version.
     *
     * @return
     */
    public String getVersion() {
        return version;
    }

    /**
     * Get the build time.
     *
     * Build time format: yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public String getBuildtime() {
        return buildDate;
    }

    /**
     * Get the user that built the last instance.
     *
     * @return
     */
    public String getBuilder() {
        return builder;
    }

    /**
     * Get the application name.
     *
     * @return
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * Get the company name.
     *
     * @return
     */
    public String getCompany() {
        return company;
    }

    /**
     * To string.
     *
     * Returns the version value.
     *
     * @return
     */
    @Override
    public String toString() {
        return version;
    }
}
