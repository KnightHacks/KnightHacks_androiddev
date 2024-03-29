package org.httpsknighthacks.knighthacksandroid.Resources;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static final String DATE_TIME_STRING_PATTERN = "EE MMM dd HH:mm:ss z yyyy";
    public static final String DEFAULT_DATE_TIME_STRING_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String YEAR_MONTH_DAY_PATTERN = "yyyyMMdd";
    public static final String TIME_STRING_PATTERN = "hh:mma";
    public static final String DATE_WEEKDAY_PATTERN = "EEEE";
    public static final String DATE_WEEKDAY_ABBREV_PATTERN = "EE";

    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(DATE_TIME_STRING_PATTERN, Locale.ENGLISH);
    public static final SimpleDateFormat DEFAULT_DATE_TIME_FORMAT = new SimpleDateFormat(DEFAULT_DATE_TIME_STRING_PATTERN, Locale.ENGLISH);
    public static final SimpleDateFormat YEAR_MONTH_DAY_FORMAT = new SimpleDateFormat(YEAR_MONTH_DAY_PATTERN, Locale.ENGLISH);
    public static final SimpleDateFormat TIME_STRING_FORMAT = new SimpleDateFormat(TIME_STRING_PATTERN, Locale.ENGLISH);
    public static final SimpleDateFormat DATE_WEEKDAY_FORMAT = new SimpleDateFormat(DATE_WEEKDAY_PATTERN, Locale.ENGLISH);
    public static final SimpleDateFormat DATE_WEEKDAY_ABBREV_FORMAT = new SimpleDateFormat(DATE_WEEKDAY_ABBREV_PATTERN, Locale.ENGLISH);

    public static final String DAYS_AGO = "days ago";
    public static final String MINUTES_AGO = "mins ago";
    public static final String SECONDS_AGO = "secs ago";

    public static String getWeekDayString(String dateTime) {
        try {
            Date date = DATE_TIME_FORMAT.parse(dateTime);

            return DATE_WEEKDAY_FORMAT.format(date);
        } catch (ParseException ex) {
            return "Day";
        }
    }

    public static String getTime(String dateTime) {
        try {
            Date date = DATE_TIME_FORMAT.parse(dateTime);

            return TIME_STRING_FORMAT.format(date);
        } catch (ParseException ex) {
            Log.d("KEVIN", ex.toString());
            return "00:00am";
        }
    }

    public static String getTimeAndDurationSinceGiven(String dateTime) {
        try {
            Date now = new Date();
            Date date = DEFAULT_DATE_TIME_FORMAT.parse(dateTime);
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

            return TIME_STRING_FORMAT.format(date) + String.format(Locale.ENGLISH, " -- %d %s", timeDifference, differenceUnit);
        } catch (ParseException ex) {
            return "00:00 -- 0 secs ago";
        }
    }

    public static boolean daysAreDifferent(String first, String second) {
        try {
            Date firstDate = DATE_TIME_FORMAT.parse(first);
            Date secondDate = DATE_TIME_FORMAT.parse(second);

            return !DATE_WEEKDAY_ABBREV_FORMAT.format(firstDate).equals(DATE_WEEKDAY_ABBREV_FORMAT.format(secondDate));
        } catch (Exception ex) {
            return true;
        }
    }

    public static long getDaysFromSeconds(long seconds) {
        return seconds / (60 * 60 * 24);
    }

    public static long getMinutesFromSeconds(long seconds) {
        return seconds / 60;
    }
}
