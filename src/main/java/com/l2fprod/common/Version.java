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

    private String _version;
    private String _build_date;
    private String _builder;
    private String _company;
    private String _applicationName;

    /**
     * Singleton Instance.
     */
    public final static Version INSTANCE = new Version();

    public Version() {
        //load prop file...
        try {
            Properties prop = new Properties();

            String propFile = this.getClass().getPackage().getName().replace('.', '/') + '/' + this.getClass().getSimpleName() + ".properties";
            URL url = ClassLoader.getSystemClassLoader().getResource(propFile);

            prop.load(url.openStream());
            this._version = prop.getProperty("version");
            this._build_date = prop.getProperty("buildTime");
            this._builder = prop.getProperty("builder");
            this._company = prop.getProperty("company");
            this._applicationName = prop.getProperty("project");
        } catch (IOException ex) {
            Logger.getLogger(Version.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Version Accessor.
     *
     * @return
     */
    public String getVersion() {
        return _version;
    }

    /**
     * Build Time Accessor.
     *
     * @return
     */
    public String getBuildtime() {
        return _build_date;
    }

    /**
     * Get the user that built the last instance.
     *
     * @return
     */
    public String getBuilder() {
        return _builder;
    }

    /**
     * Get the application name.
     *
     * @return
     */
    public String getApplicationName() {
        return _applicationName;
    }

    /**
     * Get the company name.
     *
     * @return
     */
    public String getCompany() {
        return _company;
    }

    /**
     * To string, return version.
     *
     * @return
     */
    @Override
    public String toString() {
        return _version;
    }
}
