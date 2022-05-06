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
 * 统计专用工具类
 * 本类使用单例模式,调用者使用本类中的方法请先获取类的实例对象
 *
 * @author jianghongyan 2016年7月1日 版本改造
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
     * 获取当前月份的 unix时间戳  最大值
     *
     * @param year  年份
     * @param month 月份
     * @return 时间戳
     */
    private long getMaxvalType1(int year, int month) {
        //如果是 12月 那么明年1月的零时作为结束时间
        int months = 12;
        if (month == months) {
            return DateUtil.getDateHaveHour((year + 1) + "-01-01 00");
        }
        //否则 下个月 0时
        return DateUtil.getDateHaveHour(year + "-" + (month + 1) + "-01 00");
    }

    /**
     * 获取当前月份的  unix时间戳  最小值
     *
     * @param year  年份
     * @param month 月份
     * @return 时间戳
     */
    private long getMinvalType1(int year, int month) {
        return DateUtil.getDateHaveHour(year + "-" + month + "-01 00");
    }

    /**
     * 获取当前年份的  unix时间戳 最大值
     *
     * @param year 年份
     * @return 时间戳
     */
    private long getMaxvalType0(int year) {
        return DateUtil.getDateline(year + "-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前年份的 unix时间戳  最小值
     *
     * @param year 年份
     * @return 时间戳
     */
    private long getMinvalType0(int year) {
        return DateUtil.getDateline(year + "-01-01");
    }

    /**
     * 获取最小和最大时间戳
     *
     * @param cycleType 查询周期
     * @param year      查询年份
     * @param month     查询月份
     * @return 时间戳
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
     * 填充客单价分布数据集合
     *
     * @param data   数据
     * @param ranges 区间
     * @return 填充后数据
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> fitOrderPriceData(List<Map<String, Object>> data,
                                                       List<Integer> ranges) {

        if (ranges == null || ranges.size() == 0) {
            return data;
        }
        List<Map<String, Object>> data2 = new ArrayList<Map<String, Object>>();
        //由小到大排序
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
     * 创建SQL语句
     *
     * @param type  日期类型
     * @param year  年份
     * @param month 月份
     * @return sql语句
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
     * @param year 年份
     * @return sql 按年查询条件
     */
    public String createSqlByYear(int year) {
        StringBuffer sql = new StringBuffer();
        // 月数
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
     * 获取当前年月最大日期
     *
     * @param year  年份
     * @param month 月份
     * @return 最大日期
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
     * 对整数集合进行排序，主要用于价格销量
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
     * 公用方法查询list，如果表不存在，捕获异常，返回空数据
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
            //某个年份的统计表不存在，则返回空数据
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
     * 公用方法查询page，如果表不存在，捕获异常，返回空数据
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
            //某个年份的统计表不存在，则返回空数据
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
     * 公用方法查询page，如果表不存在，捕获异常，返回空数据
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
            //某个年份的统计表不存在，则返回空数据
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