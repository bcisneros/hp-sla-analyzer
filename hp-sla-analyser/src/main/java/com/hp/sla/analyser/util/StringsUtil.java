package com.hp.sla.analyser.util;

/**
 * Utility class to manage some validations related with String objects
 *
 * @author Benjamin Cisneros Barraza
 */
public class StringsUtil {

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }
}
