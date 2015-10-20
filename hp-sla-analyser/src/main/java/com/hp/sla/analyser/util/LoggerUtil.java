package com.hp.sla.analyser.util;

import org.apache.log4j.Logger;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public class LoggerUtil {

    public static void debugIfEnabled(Logger logger, Object message) {
        debugIfEnabled(logger, message, null);
    }

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
