package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static <T extends Comparable<T>> boolean isBetween(T t, T start, T end) {
        if (start == null && end == null) {
            return true;
        } else if (start != null && end != null) {
            return t.compareTo(start) >= 0 && t.compareTo(end) < 0;
        } else if (start != null && end == null) {
            return t.compareTo(start) >= 0;
        } else {
            return t.compareTo(end) < 0;
        }
    }
}

