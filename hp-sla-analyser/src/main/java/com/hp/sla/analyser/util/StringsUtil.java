package com.hp.sla.analyser.util;

/**
 * Utility class to manage some validations related with String objects
 * @author Benjamin Cisneros Barraza
 */
public class StringsUtil {

    /**
     * Determines if a String is null or is empty. 
     * If you have a string with only blank spaces it returns true
     * @param string The evaluated string
     * @return A boolean value that determines if is null or empty string
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }
}
