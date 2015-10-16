package com.hp.sla.analyser.util;

import java.io.File;
import java.io.FileInputStream;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public class ResourcesUtil {
    
    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ResourcesUtil.class);

    public static FileInputStream getResourceFromProjectClasspath(String file) {
        ClassLoader classLoader = ResourcesUtil.class.getClassLoader();
        FileInputStream testFile = null;
        try {
            
            testFile = new FileInputStream(new File(classLoader.getResource(file).getFile()));
        } catch (Exception ex) {
            logger.error("Error while getting the file " + file, ex);
        }
        return testFile;
    }
    
}
