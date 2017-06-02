package com.Logger.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class DateUtils {

    public static final String DB_FORMAT_DATETIME = "yyyy-MM-dd";
    public static final String DEFAULT_TIMEZONE = "GMT";

    private DateUtils() {
        // not publicly instantiable
    }

    public static Date getDate(String dateStr, String format) {
        final SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}