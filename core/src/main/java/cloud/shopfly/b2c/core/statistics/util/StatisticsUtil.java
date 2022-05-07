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

import cloud.shopfly.b2c.core.statistics.StatisticsErrorCode;
import cloud.shopfly.b2c.core.statistics.StatisticsException;
import cloud.shopfly.b2c.core.statistics.model.enums.QueryDateType;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.*;

/**
 * Statistical utility classes
 * This class uses the singleton pattern,Callers using methods in this class first get instance objects of the class
 *
 * @author jianghongyan 2016years7month1Japanese version modification
 * @version v6.1
 * @since v6.1
 */
public class StatisticsUtil {

    private static StatisticsUtil statisticsUtil = new StatisticsUtil();

    private StatisticsUtil() {
    }

    public static StatisticsUtil getInstance() {
        return statisticsUtil;
    }

    /**
     * Gets the value of the current monthunixTimestamp maximum
     *
     * @param year  year
     * @param month in
     * @return The time stamp
     */
    private long getMaxvalType1(int year, int month) {
        // If its December then zero o clock next January will be the end time
        int months = 12;
        if (month == months) {
            return DateUtil.getDateHaveHour((year + 1) + "-01-01 00");
        }
        // Otherwise 0 o clock next month
        return DateUtil.getDateHaveHour(year + "-" + (month + 1) + "-01 00");
    }

    /**
     * Gets the value of the current monthunixMinimum timestamp value
     *
     * @param year  year
     * @param month in
     * @return The time stamp
     */
    private long getMinvalType1(int year, int month) {
        return DateUtil.getDateHaveHour(year + "-" + month + "-01 00");
    }

    /**
     * Gets the current years valueunixTimestamp maximum
     *
     * @param year year
     * @return The time stamp
     */
    private long getMaxvalType0(int year) {
        return DateUtil.getDateline(year + "-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Gets the current years valueunixMinimum timestamp value
     *
     * @param year year
     * @return The time stamp
     */
    private long getMinvalType0(int year) {
        return DateUtil.getDateline(year + "-01-01");
    }

    /**
     * Gets the minimum and maximum time stamps
     *
     * @param cycleType Query cycle
     * @param year      Year of the query
     * @param month     In the query
     * @return The time stamp
     */
    public long[] getStartTimeAndEndTime(String cycleType, Integer year, Integer month) {
        long[] times = new long[2];

        switch (cycleType) {
            case "MONTH":
                times[0] = getMinvalType1(year, month);
                times[1] = getMaxvalType1(year, month);
                break;
            case "YEAR":
                times[0] = getMinvalType0(year);
                times[1] = getMaxvalType0(year);
                break;
            default:
                times[0] = getMinvalType1(year, month);
                times[1] = getMaxvalType1(year, month);
                break;
        }
        return times;
    }

    /**
     * Populate the customer unit price distribution data set
     *
     * @param data   data
     * @param ranges interval
     * @return Populated data
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> fitOrderPriceData(List<Map<String, Object>> data,
                                                       List<Integer> ranges) {

        if (ranges == null || ranges.size() == 0) {
            return data;
        }
        List<Map<String, Object>> data2 = new ArrayList<Map<String, Object>>();
        // Sort from smallest to largest
        Collections.sort(ranges, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 < o2) {
                    return -1;
                } else if (o1 > o2) {
                    return 1;
                }
                return 0;
            }
        });
        Map<String, Object> exchangesMap = new HashMap<>(data.size());
        for (Map<String, Object> map : data) {
            String eltData = map.get("elt_data").toString();
            exchangesMap.put(eltData, map);
        }
        for (int i = 0; i < ranges.size() - 1; i += 2) {
            String r = ranges.get(i).toString() + "~" + ranges.get(i + 1).toString();
            if (!exchangesMap.containsKey(r)) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("num", 0.0);
                map.put("elt_data", r);
                map.put("need_pay_money", 0.0);
                data2.add(map);
            } else {
                data2.add((Map<String, Object>) exchangesMap.get(r));
            }
        }
        String endKey = ranges.get(ranges.size() - 1) + "+";
        if (exchangesMap.containsKey(endKey)) {
            data2.add((Map<String, Object>) exchangesMap.get(endKey));
        } else {
            Map<String, Object> map = new HashMap<>(16);
            map.put("num", 0.0);
            map.put("elt_data", endKey);
            map.put("need_pay_money", 0.0);
            data2.add(map);
        }
        return data2;
    }

    /**
     * createSQLstatements
     *
     * @param type  The date type
     * @param year  year
     * @param month in
     * @return sqlstatements
     */
    public String createSql(String type, int year, int month) {

        if (QueryDateType.YEAR.value().equals(type)) {
            return createSqlByYear(year);
        }
        StringBuffer sql = new StringBuffer();
        String date = year + "-" + month;
        int day = getDaysByYearMonth(year, month);
        for (int i = 1; i <= day; i++) {
            String dayDate = date + "-" + i;
            long start = DateUtil.getDateline(dayDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            long end = DateUtil.getDateline(dayDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            sql.append(" when create_time >= " + start + " and   create_time <=" + end + " then " + i);
        }

        sql.append(" else 0 end");
        return sql.toString();
    }

    /**
     * @param year year
     * @return sql Query conditions by year
     */
    public String createSqlByYear(int year) {
        StringBuffer sql = new StringBuffer();
        // months
        int months = 12;
        for (Integer i = 1; i <= months; i++) {
            Integer month = i;
            String dayDate = year + "-" + month;
            long start = DateUtil.getDateline(dayDate + "-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
            int day = getDaysByYearMonth(year, month);
            long end = DateUtil.getDateline(dayDate + "-" + day + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            sql.append(" when create_time >= " + start + " and  create_time <=" + end + " then " + i);
        }
        sql.append(" else 0 end");
        return sql.toString();
    }

    /**
     * Gets the maximum date of the current month
     *
     * @param year  year
     * @param month in
     * @return The biggest date
     */
    public int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * To sort a collection of integers, primarily for price sales
     *
     * @param ranges
     */
    public static void sortRanges(List<Integer> ranges) {
        Collections.sort(ranges, (o1, o2) -> {
            if (o1 > o2) {
                return -1;
            } else if (o1 < o2) {
                return 1;
            }
            return 0;
        });
    }

    /**
     * Common method querylistIf the table does not exist, catch the exception and return null data
     *
     * @param daoSupport
     * @param year
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> getDataList(DaoSupport daoSupport, Integer year, String sql, Object[] params) {

        List<Map<String, Object>> list;

        try {
            list = daoSupport.queryForList(SyncopateUtil.handleSql(year, sql), params);
            if (list == null || list.size() <= 0) {
                list = daoSupport.queryForList(sql, params);
            }
        } catch (BadSqlGrammarException e) {
            // If the statistics table for a certain year does not exist, null data is returned
            if (e.getMessage().endsWith("doesn't exist")) {
                list = new ArrayList<>();
            } else {
                e.printStackTrace();
                throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
        return list;
    }

    /**
     * Common method querypageIf the table does not exist, catch the exception and return null data
     *
     * @param daoSupport
     * @param year
     * @param sql
     * @param params
     * @return
     */
    public static Page getDataPage(DaoSupport daoSupport, Integer year, String sql, Integer pageNo, Integer pageSize, Object[] params) {

        Page page;

        try {
            page = daoSupport.queryForPage(SyncopateUtil.handleSql(year, sql), pageNo, pageSize, params);
            if (page == null || page.getData().size() <= 0) {
                page = daoSupport.queryForPage(sql, pageNo, pageSize, params);
            }
        } catch (BadSqlGrammarException e) {
            // If the statistics table for a certain year does not exist, null data is returned
            if (e.getMessage().endsWith("doesn't exist")) {
                return new Page(pageNo, 0L, pageSize, new ArrayList());
            } else {
                e.printStackTrace();
                throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
        return page;
    }

    /**
     * Common method querypageIf the table does not exist, catch the exception and return null data
     *
     * @param daoSupport
     * @param year
     * @param sql
     * @param params
     * @return
     */
    public static Page getDataPage(DaoSupport daoSupport, Integer year, String sql, String countSql, Integer pageNo, Integer pageSize, Object[] params) {

        Page page;

        try {
            page = daoSupport.queryForPage(SyncopateUtil.handleSql(year, sql), countSql, pageNo, pageSize, params);
            if (page == null || page.getData().size() <= 0) {
                page = daoSupport.queryForPage(sql, countSql, pageNo, pageSize, params);
            }
        } catch (BadSqlGrammarException e) {
            // If the statistics table for a certain year does not exist, null data is returned
            if (e.getMessage().endsWith("doesn't exist")) {
                return new Page(pageNo, 0L, pageSize, new ArrayList());
            } else {
                e.printStackTrace();
                throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
        return page;
    }


}
