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
package cloud.shopfly.b2c.core.statistics.util;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 日期相关的操作
 *
 * @author Dawei
 */

public class DateUtil {

    /**
     * 将一个字符串转换成日期格式
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date toDate(String date, String pattern) {

        if (("").equals("" + date)) {
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

    public static long getDateline() {
        return System.currentTimeMillis() / 1000;
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

}
