package com.hp.sla.analyser.util;

import java.sql.Timestamp;

/**
 * Utility date time class to perform tests
 *
 * @author Benjamin Cisneros Barraza
 */
public class DateTimeUtil {

    /**
     * Utility Timestamp value used for tests
     */
    public static final Timestamp FIRST_DAY_2015_YEAR_TIMESTAMP = Timestamp.valueOf("2015-01-01 00:00:00");

    /**
     * Converts given hours to milliseconds
     *
     * @param hours Number of hours
     * @return Number of milliseconds
     */
    public static long hoursToMilliseconds(double hours) {
        return (long) (hours * 3600 * 1000);
    }

    /**
     * Adds the specified number of hours to a given Timestamp
     *
     * @param timestamp The base timestamp to add more time
     * @param hours Number of hours to add
     * @return New timestamp with added hours
     */
    public static Timestamp addHours(Timestamp timestamp, double hours) {
        return new Timestamp(timestamp.getTime() + hoursToMilliseconds(hours));
    }
}
