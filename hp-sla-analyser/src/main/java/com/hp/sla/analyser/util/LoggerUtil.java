package com.hp.sla.analyser.util;

import org.apache.log4j.Logger;

/**
 * A Logger Utility class that provides methods to manage debug and info messages
 * @author Benjamin Cisneros Barraza
 */
public class LoggerUtil {

    /**
     * Calls the method debug if the debug mode is enabled
     * @param logger Logger object
     * @param message The message to log
     */
    public static void debugIfEnabled(Logger logger, Object message) {
        debugIfEnabled(logger, message, null);
    }

    /**
     * Calls the overloaded Logger debug method that accepts an throwable object
     * @param logger Logger object
     * @param message The message to log
     * @param t A Throwable object
     */
    public static void debugIfEnabled(Logger logger, Object message, Throwable t) {
        if (logger != null) {
            if (logger.isDebugEnabled()) {
                if (t != null) {
                    logger.debug(message, t);
                } else {
                    logger.debug(message);
                }
            }
        }
    }
}
