package com.rydr.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * Utility class providing distance, duration, and pricing unit conversions for ride valuation.
 *
 * @author Rydr Team
 */
public class UnitConverter {

    private static final BigDecimal METERS_PER_KM = new BigDecimal("1000");
    private static final BigDecimal SECONDS_PER_MINUTE = new BigDecimal("60");

    /**
     * Convert Date to LocalDateTime using system default time zone.
     *
     * @param date input Date object
     * @return equivalent LocalDateTime instance
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Convert Date to LocalDate.
     *
     * @param date input Date object
     * @return equivalent LocalDate instance
     */
    public static LocalDate dateToLocalDate(Date date) {
        LocalDateTime ldt = dateToLocalDateTime(date);
        return ldt != null ? ldt.toLocalDate() : null;
    }

    /**
     * Convert Date to LocalTime.
     *
     * @param date input Date object
     * @return equivalent LocalTime instance
     */
    public static LocalTime dateToLocalTime(Date date) {
        LocalDateTime ldt = dateToLocalDateTime(date);
        return ldt != null ? ldt.toLocalTime() : null;
    }

    /**
     * Convert per-kilometer rate to per-meter rate.
     *
     * @param price per-kilometer rate as BigDecimal
     * @return per-meter rate rounded down to 5 decimal places
     */
    public static BigDecimal kiloToMeterPrice(BigDecimal price) {
        if (price == null) {
            return BigDecimal.ZERO;
        }
        return price.divide(METERS_PER_KM, 5, RoundingMode.DOWN);
    }

    /**
     * Convert per-minute rate to per-second rate.
     *
     * @param price per-minute rate as BigDecimal
     * @return per-second rate rounded down to 5 decimal places
     */
    public static BigDecimal minuteToSecondPrice(BigDecimal price) {
        if (price == null) {
            return BigDecimal.ZERO;
        }
        return price.divide(SECONDS_PER_MINUTE, 5, RoundingMode.DOWN);
    }

    /**
     * Convert seconds duration to minutes.
     *
     * @param seconds number of seconds
     * @return number of minutes rounded HALF_DOWN to 2 decimal places
     */
    public static double secondToMinute(Double seconds) {
        return BigDecimal.valueOf(Optional.ofNullable(seconds).orElse(0D))
                .divide(SECONDS_PER_MINUTE, 2, RoundingMode.HALF_DOWN)
                .doubleValue();
    }

    /**
     * Convert meters distance to kilometers.
     *
     * @param meters number of meters
     * @return number of kilometers rounded HALF_DOWN to 2 decimal places
     */
    public static double meterToKilo(Double meters) {
        return BigDecimal.valueOf(Optional.ofNullable(meters).orElse(0D))
                .divide(METERS_PER_KM, 2, RoundingMode.HALF_DOWN)
                .doubleValue();
    }
}

