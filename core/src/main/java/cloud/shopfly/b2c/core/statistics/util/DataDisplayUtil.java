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


import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.core.statistics.model.enums.QueryDateType;
import cloud.shopfly.b2c.framework.util.DateUtil;

/**
 * Statistical date tool
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018/4/28 In the afternoon5:09
 */
public class DataDisplayUtil {

    private final static String DATA_SEPARATOR = "-";

    /**
     * Gets the days of the month
     *
     * @param searchCriteria Parameters of the class
     * @return Number of days
     */
    public static int getResultSize(SearchCriteria searchCriteria) {

        if (searchCriteria.getCycleType().equals(QueryDateType.YEAR.value())) {
            return 12;
        }

        return getMonthDayNum(searchCriteria.getMonth(), searchCriteria.getYear());

    }


    /**
     * Gets the days of the month
     *
     * @param cycleType The date type
     * @param year      year
     * @param month     in
     * @return Number of days
     */
    public static int getResultSize(String cycleType, Integer year, Integer month) {

        if (cycleType.equals(QueryDateType.YEAR.value())) {
            return 12;
        }

        return getMonthDayNum(month, year);

    }

    /**
     * Gets the days of the month
     *
     * @param month in
     * @param year  years
     * @return Number of days
     */
    public static int getMonthDayNum(Integer month, Integer year) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                boolean flag = year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
                if (flag) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return 0;
        }
    }

    /**
     * According to thevoGets the current start and end condition timestamps
     *
     * @param searchCriteria The time parameter
     * @return The time stamp
     */
    public static long[] getStartTimeAndEndTime(SearchCriteria searchCriteria) {
        long[] timestamp = new long[2];
        String startTime;
        String endTime;
        if (searchCriteria.getCycleType().equals(QueryDateType.YEAR.name())) {
            startTime = new StringBuffer().append(searchCriteria.getYear()).append(DataDisplayUtil.DATA_SEPARATOR).append("01").append(DataDisplayUtil.DATA_SEPARATOR).append("01 ").append("00:00:00").toString();
            endTime = new StringBuffer().append(searchCriteria.getYear()).append(DataDisplayUtil.DATA_SEPARATOR).append("12").append(DataDisplayUtil.DATA_SEPARATOR).append("31 ").append("23:59:59").toString();
        } else {
            startTime = new StringBuffer().append(searchCriteria.getYear()).append(DataDisplayUtil.DATA_SEPARATOR).append(searchCriteria.getMonth()).append(DataDisplayUtil.DATA_SEPARATOR).append("01").append(" 00:00:00").toString();
            endTime = new StringBuffer().append(searchCriteria.getYear()).append(DataDisplayUtil.DATA_SEPARATOR).append(searchCriteria.getMonth() + 1).append(DataDisplayUtil.DATA_SEPARATOR).append("01").append(" 00:00:00").toString();
        }
        timestamp[0] = DateUtil.getDateline(startTime, "yyyy-MM-dd HH:mm:ss");
        timestamp[1] = DateUtil.getDateline(endTime, "yyyy-MM-dd HH:mm:ss");
        return timestamp;
    }

    /**
     * According to thevoGets the start and end condition timestamps of the previous cycle
     *
     * @param searchCriteria The time parameter
     * @return The time stamp
     */
    public static long[] getLastStartTimeAndEndTime(SearchCriteria searchCriteria) {
        long[] lastTimestamp = new long[2];
        String startTime;
        String endTime;
        if (searchCriteria.getCycleType().equals(QueryDateType.YEAR.name())) {
            startTime = new StringBuffer().append(searchCriteria.getYear() - 1).append(DataDisplayUtil.DATA_SEPARATOR).append("01").append(DataDisplayUtil.DATA_SEPARATOR).append("01 ").append("00:00:00").toString();
            endTime = new StringBuffer().append(searchCriteria.getYear() - 1).append(DataDisplayUtil.DATA_SEPARATOR).append("12").append(DataDisplayUtil.DATA_SEPARATOR).append("31 ").append("23:59:59").toString();
        } else {
            startTime = new StringBuffer().append(searchCriteria.getYear()).append(DataDisplayUtil.DATA_SEPARATOR).append(searchCriteria.getMonth() - 1).append(DataDisplayUtil.DATA_SEPARATOR).append("01").append(" 00:00:00").toString();
            endTime = new StringBuffer().append(searchCriteria.getYear()).append(DataDisplayUtil.DATA_SEPARATOR).append(searchCriteria.getMonth()).append(DataDisplayUtil.DATA_SEPARATOR).append("01").append(" 00:00:00").toString();
        }
        lastTimestamp[0] = DateUtil.getDateline(startTime, "yyyy-MM-dd HH:mm:ss");
        lastTimestamp[1] = DateUtil.getDateline(endTime, "yyyy-MM-dd HH:mm:ss");
        return lastTimestamp;

    }

    /**
     * Date format
     *
     * @param date The date of
     * @return Formatted date
     */
    public static String formatDate(Integer date) {
        // Add 0 in front of the units digit
        int isSingle = 10;
        if (date < isSingle) {
            return "0" + date;
        }
        return date + "";
    }
}
