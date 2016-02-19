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
    public static final float FIVE_TEEN_MINUTES = 0.25F;
    public static final float ONE_HOUR = 1F;
    public static final Float THREE_HOURS = 3.0F;
    public static final float FOUR_HOURS = 4F;
    public static final Float SIX_HOURS = 6.0F;
    public static final Float EIGHT_HOURS = 8.0F;
    public static final Float ONE_DAY = 24.0F;
    public static final Float THREE_DAYS = 72.0F;
    public static final Float FOUR_DAYS = 96.0F;
    public static final Float ONE_HUNDRED_TEN_HOURS = 110.0F;
    public static final Float SIX_DAYS = 144.0F;
    public static final Float SEVENTY_FIVE_PERCENT = 0.75F;

    /**
     * Converts given hours to milliseconds
     *
     * @param hours
     *            Number of hours
     * @return Number of milliseconds
     */
    public static long hoursToMilliseconds(double hours) {
	return (long) (hours * 3600 * 1000);
    }

    /**
     * Adds the specified number of hours to a given Timestamp
     *
     * @param timestamp
     *            The base timestamp to add more time
     * @param hours
     *            Number of hours to add
     * @return New timestamp with added hours
     */
    public static Timestamp addHours(Timestamp timestamp, double hours) {
	return new Timestamp(timestamp.getTime() + hoursToMilliseconds(hours));
    }
}
