/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.framework.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Date-related operations
 *
 * @author Dawei
 */

@SuppressWarnings({"AlibabaCollectionInitShouldAssignCapacity", "AlibabaUndefineMagicConstant"})
public class DateUtil {
    /**
     * The number of seconds in a day
     */
    public static final long ONE_DAY = 86400;

    /**
     * Gets the time after the current formatting
     * @return
     */
    public static String formatNow() {
        return DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
    }
    /**
     * The start time of the day
     *
     * @return
     */
    public static long startOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date.getTime() / 1000;
    }

    /**
     * willSun Apr 15 04:12:39 CST 2018 Format time is converted to timestamp
     *
     * @param dateFormat Sun Apr 15 04:12:39 CST 2018 Format time
     * @return The time stamp
     */
    public static long getFormatDate(String dateFormat) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

            return sdf1.parse(dateFormat).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * The end of the day
     *
     * @return
     */
    public static long endOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date date = calendar.getTime();
        return date.getTime() / 1000;
    }

    /**
     * Yesterdays start time
     *
     * @return
     */
    public static long startOfyesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date.getTime() / 1000;
    }

    /**
     * Yesterdays closing time
     *
     * @return
     */
    public static long endOfyesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        return date.getTime() / 1000;
    }

    /**
     * The start time of a day
     *
     * @param dayUntilNow How many days ago
     * @return The time stamp
     */
    public static long startOfSomeDay(int dayUntilNow) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, -dayUntilNow);
        Date date = calendar.getTime();
        return date.getTime() / 1000;
    }

    /**
     * The start time of a day
     *
     * @param dayUntilNow How many days from now
     * @return The time stamp
     */
    public static long endOfSomeDay(int dayUntilNow) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, +dayUntilNow);
        Date date = calendar.getTime();
        return date.getTime() / 1000;
    }

    /**
     * The date of a certain day
     *
     * @param dayUntilNow How many days ago
     * @return (date) (month) (year)map keyforyear month day
     */
    public static Map<String, Object> getYearMonthAndDay(int dayUntilNow) {

        Map<String, Object> map = new HashMap<String, Object>(3);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, -dayUntilNow);
        map.put("year", calendar.get(Calendar.YEAR));
        map.put("month", calendar.get(Calendar.MONTH) + 1);
        map.put("day", calendar.get(Calendar.DAY_OF_MONTH));
        return map;
    }

    /**
     * Converts a string to a date format
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date toDate(String date, String pattern) {
        if ("".equals("" + date)) {
            return null;
        }
        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date newDate = new Date();
        try {
            newDate = sdf.parse(date);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDate;
    }

    /**
     * Gets the start and end time of the previous month
     *
     * @return
     */
    public static Long[] getLastMonth() {
        // Gets the current system time
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        // Obtain the time object on the first day of the month in which the current system time resides
        cal.set(Calendar.DAY_OF_MONTH, 1);

        // Subtract one from the date to get the last day of the last month
        cal.add(Calendar.DAY_OF_MONTH, -1);

        // Output last day date of last month
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String months = "";
        String days = "";

        if (month > 1) {
            month--;
        } else {
            year--;
            month = 12;
        }
        if (String.valueOf(month).length() <= 1) {
            months = "0" + month;
        } else {
            months = String.valueOf(month);
        }
        if (String.valueOf(day).length() <= 1) {
            days = "0" + day;
        } else {
            days = String.valueOf(day);
        }
        String firstDay = "" + year + "-" + months + "-01";
        String lastDay = "" + year + "-" + months + "-" + days + " 23:59:59";

        Long[] lastMonth = new Long[2];
        lastMonth[0] = DateUtil.getDateline(firstDay);
        lastMonth[1] = DateUtil.getDateline(lastDay, "yyyy-MM-dd HH:mm:ss");

        return lastMonth;
    }

    /**
     * Gets the start and end time of the month
     *
     * @return
     */
    public static Long[] getCurrentMonth() {
        // Gets the current system time
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        // Output the date on the first day of the next month
        int notMonth = cal.get(Calendar.MONTH) + 2;
        // Obtain the time object on the first day of the month in which the current system time resides
        cal.set(Calendar.DAY_OF_MONTH, 1);

        // Subtract one from the date to get the last day of the last month
        cal.add(Calendar.DAY_OF_MONTH, -1);

        String months = "";
        String nextMonths = "";

        if (String.valueOf(month).length() <= 1) {
            months = "0" + month;
        } else {
            months = String.valueOf(month);
        }
        if (String.valueOf(notMonth).length() <= 1) {
            nextMonths = "0" + notMonth;
        } else {
            nextMonths = String.valueOf(notMonth);
        }
        String firstDay = "" + year + "-" + months + "-01";
        String lastDay = "" + year + "-" + nextMonths + "-01";
        Long[] currentMonth = new Long[2];
        currentMonth[0] = DateUtil.getDateline(firstDay);
        currentMonth[1] = DateUtil.getDateline(lastDay);

        return currentMonth;
    }

    /**
     * Gets the start and end time of a year
     *
     * @return
     */
    public static Long[] getYearTime(Integer year) {


        Calendar firstCal = Calendar.getInstance();
        firstCal.set(Calendar.YEAR, year - 1);
        firstCal.set(Calendar.MONTH, Calendar.DECEMBER);
        firstCal.set(Calendar.DATE, 31);

        Calendar lastCal = Calendar.getInstance();
        lastCal.set(Calendar.YEAR, year);
        lastCal.set(Calendar.MONTH, Calendar.DECEMBER);
        lastCal.set(Calendar.DATE, 31);

        Long[] yearTime = new Long[2];
        yearTime[0] = firstCal.getTime().getTime() / 1000;
        yearTime[1] = lastCal.getTime().getTime() / 1000;

        return yearTime;
    }

    /**
     * Converts the date to a string
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String toString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }
        String dateString = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            dateString = sdf.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dateString;
    }

    public static String toString(Long time, String pattern) {
        if (time > 0) {
            if (time.toString().length() == 10) {
                time = time * 1000;
            }
            Date date = new Date(time);
            String str = DateUtil.toString(date, pattern);
            return str;
        }
        return "";
    }

    /**
     * In order to facilitatemock Set this property
     * If this property is set, the set value is returned directly
     */
    public static Long mockDate;

    public static long getDateline() {
        if (mockDate != null) {
            return mockDate;
        }
        return System.currentTimeMillis() / 1000;
    }

    /**
     * Checks whether the current time is in a certain time range
     *
     * @param start Start time, timestamp in seconds
     * @param end   End time, timestamp in seconds
     * @return Is it within range
     */
    public static boolean inRangeOf(long start, long end) {
        long now = getDateline();
        return start <= now && end >= now;
    }

    public static long getDateline(String date) {
        return toDate(date, "yyyy-MM-dd").getTime() / 1000;
    }

    public static long getDateHaveHour(String date) {
        return toDate(date, "yyyy-MM-dd HH").getTime() / 1000;
    }

    public static long getDateline(String date, String pattern) {
        return toDate(date, pattern).getTime() / 1000;
    }


    public static ZonedDateTime getNowZoneDateTime(){
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.of( "Asia/Chongqing" );
        ZonedDateTime zdt = ZonedDateTime.ofInstant( now , zoneId );
        return zdt.toLocalDate().atStartOfDay( zoneId );
    }

}
