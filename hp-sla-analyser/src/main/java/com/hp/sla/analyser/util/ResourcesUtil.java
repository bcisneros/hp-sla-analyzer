package com.hp.sla.analyser.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Utility class to load files easily
 *
 * @author Benjamin Cisneros Barraza
 */
public class ResourcesUtil {

    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ResourcesUtil.class);

    /**
     * Returns a FileInputStream object stored in the project classpath
     *
     * @param file
     * @return A FileInputStream object
     */
    public static InputStream getResourceFromProjectClasspath(String file) {
        ClassLoader classLoader = ResourcesUtil.class.getClassLoader();
        InputStream testFile = null;
        try {
            testFile = classLoader.getResourceAsStream(file);
        } catch (Exception ex) {
            logger.error("Error while getting the file " + file, ex);
        }
        return testFile;
    }

}
