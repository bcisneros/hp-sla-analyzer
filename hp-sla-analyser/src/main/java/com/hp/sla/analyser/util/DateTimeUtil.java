package com.hp.sla.analyser.util;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public class DateTimeUtil {

    public static long hoursToMilliseconds(double hours) {
        return (long) (hours * 3600 * 1000);
    }

}
