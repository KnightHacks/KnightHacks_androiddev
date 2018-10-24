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

    public static final String DAYS_AGO = "days ago";
    public static final String MINUTES_AGO = "mins ago";
    public static final String SECONDS_AGO = "secs ago";

    public static String getTimeAndDurationSinceGiven(String dateTime) {
        try {
            Date now = new Date();
            Date date = SIMPLE_DATE_FORMAT.parse(dateTime);
            long differenceInSeconds = (now.getTime() - date.getTime()) / 1000;
            long timeDifference = differenceInSeconds;
            String differenceUnit = SECONDS_AGO;

            if (getDaysFromSeconds(differenceInSeconds) > 0) {
                timeDifference = getDaysFromSeconds(differenceInSeconds);
                differenceUnit = DAYS_AGO;
            } else if (getMinutesFromSeconds(differenceInSeconds) > 0) {
                timeDifference = getMinutesFromSeconds(differenceInSeconds);
                differenceUnit = MINUTES_AGO;
            }

            return SIMPLE_DATE_FORMAT_CONVERTED.format(date) + String.format(Locale.ENGLISH, " -- %d %s", timeDifference, differenceUnit);
        } catch (ParseException ex) {
            return "00:00 -- 0 secs ago";
        }
    }

    public static long getDaysFromSeconds(long seconds) {
        return seconds / (60 * 60 * 24);
    }

    public static long getMinutesFromSeconds(long seconds) {
        return seconds / 60;
    }
}
