package com.inghubs.loan.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Slf4j
public final class Dates {

    private Dates() {
    }

    public static final String ZONE_ID = "Europe/Istanbul";
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of(ZONE_ID));
    }

    public static String toString(LocalDateTime sources) {
        return toString(sources, FORMATTER);
    }

    public static String toString(LocalDateTime sources, DateTimeFormatter formatter) {
        return sources
                .atZone(ZoneId.of(ZONE_ID)).format(formatter);
    }

    public static LocalDateTime getDueRangeEnd(LocalDateTime source) {
        return source
                .plusMonths(3).withDayOfMonth(1)
                .toLocalDate().atTime(23, 59, 59);
    }

    public static LocalDateTime getInstallmentDueDate(int monthsToAdd) {
        return Dates.now()
                .plusMonths(monthsToAdd).withDayOfMonth(1)
                .toLocalDate().atStartOfDay();
    }

}
