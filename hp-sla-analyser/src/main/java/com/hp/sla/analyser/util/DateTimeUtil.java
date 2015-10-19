package com.hp.sla.analyser.util;

import java.sql.Timestamp;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public class DateTimeUtil {

    public static final Timestamp FIRST_DAY_2015_YEAR_TIMESTAMP = Timestamp.valueOf("2015-01-01 00:00:00");

    public static long hoursToMilliseconds(double hours) {
        return (long) (hours * 3600 * 1000);
    }

}
