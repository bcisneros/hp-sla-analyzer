package com.hp.sla.analyser.util;

import java.io.InputStream;

/**
 * Utility class to load files easily
 *
 * @author Benjamin Cisneros Barraza
 */
public class ResourcesUtil {

	final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ResourcesUtil.class);
	static ClassLoader classLoader = ResourcesUtil.class.getClassLoader();

	/**
	 * Returns a FileInputStream object stored in the project classpath
	 *
	 * @param file
	 * @return A FileInputStream object
	 */
	public static InputStream getResourceFromProjectClasspath(String file) {
		InputStream testFile = null;
		logger.debug("1");
		try {
			testFile = classLoader.getResourceAsStream(file);
			logger.debug("2");
		} catch (Exception ex) {
			final String message = "Error while getting the file " + file;
			logger.error(message, ex);
			throw new RuntimeException(message, ex);
		}
		return testFile;
	}

}
