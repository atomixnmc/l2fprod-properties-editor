/* Created by JReleaseInfo AntTask from Open Source Competence Group */
/* Creation date Tue Dec 10 12:37:20 EST 2013 */
package com.l2fprod.common;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides information gathered from the build environment.
 *
 * @author JReleaseInfo AntTask
 */
public class Version {

    private String _version;
    private String _build_date;
    private String _builder;
    private String _company;
    private String _applicationName;

    /**
     * Singleton Instance.
     */
    public final static Version Instance = new Version();

    public Version() {
        //load prop file...
        initializeFromProperties();
    }

    private void initializeFromProperties() {
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
