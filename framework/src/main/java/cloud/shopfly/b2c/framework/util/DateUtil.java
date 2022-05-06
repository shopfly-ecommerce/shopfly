/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * 日期相关的操作
 *
 * @author Dawei
 */

@SuppressWarnings({"AlibabaCollectionInitShouldAssignCapacity", "AlibabaUndefineMagicConstant"})
public class DateUtil {
    /**
     * 一天的秒数
     */
    public static final long ONE_DAY = 86400;

    /**
     * 获取当前格式化后的时间
     * @return
     */
    public static String formatNow() {
        return DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
    }
    /**
     * 当天的开始时间
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
     * 将 Sun Apr 15 04:12:39 CST 2018 格式的时间转换为时间戳
     *
     * @param dateFormat Sun Apr 15 04:12:39 CST 2018 格式的时间
     * @return 时间戳
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
     * 当天的结束时间
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
     * 昨天的开始时间
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
     * 昨天的结束时间
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
     * 某天的开始时间
     *
     * @param dayUntilNow 距今多少天以前
     * @return 时间戳
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
     * 某天的开始时间
     *
     * @param dayUntilNow 距今多少天以后
     * @return 时间戳
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
     * 某天的年月日
     *
     * @param dayUntilNow 距今多少天以前
     * @return 年月日map key为 year month day
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
     * 将一个字符串转换成日期格式
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
     * 获取上个月的开始结束时间
     *
     * @return
     */
    public static Long[] getLastMonth() {
        // 取得系统当前时间
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        // 取得系统当前时间所在月第一天时间对象
        cal.set(Calendar.DAY_OF_MONTH, 1);

        // 日期减一,取得上月最后一天时间对象
        cal.add(Calendar.DAY_OF_MONTH, -1);

        // 输出上月最后一天日期
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
     * 获取当月的开始结束时间
     *
     * @return
     */
    public static Long[] getCurrentMonth() {
        // 取得系统当前时间
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        // 输出下月第一天日期
        int notMonth = cal.get(Calendar.MONTH) + 2;
        // 取得系统当前时间所在月第一天时间对象
        cal.set(Calendar.DAY_OF_MONTH, 1);

        // 日期减一,取得上月最后一天时间对象
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
     * 获取某年开始结束时间
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
     * 把日期转换成字符串型
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
     * 为了方便mock 设置此属性
     * 如果设置了此属性，则回直接返回设置的值
     */
    public static Long mockDate;

    public static long getDateline() {
        if (mockDate != null) {
            return mockDate;
        }
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 判断当前时间是否在某个时间范围
     *
     * @param start 开始时间，以秒为单位的时间戳
     * @param end   结束时间，以秒为单位的时间戳
     * @return 是否在范围内
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
