package com.motwin.android.context.utils;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Time zone helper class
 */
public class TimeZoneHelper {

    // hide constructor
    private TimeZoneHelper() {

    }

    /**
     * Retreive time zone value
     * 
     * @return The time zone formatted like GMT+/-
     */
    public static String retriveTimeZoneValue() {
        long currentTime = System.currentTimeMillis();
        long gmtTime = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
        int currentOffset = TimeZone.getDefault().getOffset(currentTime);
        int gmtOffset = TimeZone.getTimeZone("GMT").getOffset(gmtTime);
        int hourDifference = (currentOffset - gmtOffset) / (1000 * 60 * 60);
        String timeZoneValue;
        if (String.valueOf(hourDifference).contains("-")) {
            timeZoneValue = String.format("GMT%s:00", hourDifference);
        } else {
            timeZoneValue = String.format("GMT+%s:00", hourDifference);
        }

        return timeZoneValue;

    }

}
