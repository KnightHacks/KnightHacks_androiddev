package org.httpsknighthacks.knighthacksandroid.Resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static final String DATE_STRING_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_STRING_PATTERN_CONVERSION = "hh:mma";

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_STRING_PATTERN, Locale.ENGLISH);
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_CONVERTED = new SimpleDateFormat(DATE_STRING_PATTERN_CONVERSION, Locale.ENGLISH);

    public static String getTimeAndDurationSinceGiven(String dateTime) {
        try {
            Date date = SIMPLE_DATE_FORMAT.parse(dateTime);
            return SIMPLE_DATE_FORMAT_CONVERTED.format(date);
        } catch (ParseException ex) {
            return "00:00";
        }
    }
}
