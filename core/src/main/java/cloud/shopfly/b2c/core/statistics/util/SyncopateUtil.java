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

import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;

import java.time.LocalDate;

/**
 * Year of the querysqltool
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-02 In the afternoon8:23
 */
public class SyncopateUtil {

    /**
     * The name of the table for which the year needs to be replaced
     */
    private static String[] table = {
            "es_sss_order_data",
            "es_sss_order_goods_data",
            "es_sss_refund_data",
            "es_sss_goods_pv"
    };


    /**
     * sql To deal with
     *
     * @param year Search for the year
     * @param sql  sql
     * @return
     */
    public static String handleSql(Integer year, String sql) {

        if (StringUtil.isEmpty(sql) || year == null) {
            return "";
        }
        sql = sql.toLowerCase();
        // SQL processing
        sql = replaceTable(sql, year);
        return sql;
    }


    /**
     * Replace the table.
     *
     * @param sql  The query
     * @param year years
     * @return
     */
    private static String replaceTable(String sql, Integer year) {
        for (int i = 0; i < table.length; i++) {
            sql = sql.replaceAll(table[i], table[i] + "_" + year);
        }
        return sql;
    }


    /**
     * Create tables for corresponding years
     *
     * @param year
     * @param daoSupport
     */
    public static void createTable(Integer year, DaoSupport daoSupport) {
        for (String tb : table) {
            daoSupport.execute("create table " + tb + "_" + year + " select *from " + tb + " where 1=0");
        }
    }

    /**
     * Segmentation table
     *
     * @param year
     * @param daoSupport
     */
    public static void syncopateTable(Integer year, DaoSupport daoSupport) {
        Long[] time = DateUtil.getYearTime(year);
        drop(year, daoSupport);
        for (String tb : table) {
            if ("es_sss_goods_pv".equals(tb)) {
                daoSupport.execute("create table " + tb + "_" + year + " like " + tb);
                daoSupport.execute("insert into " + tb + "_" + year + " select * from " + tb + " where vs_year = ?", year);
            } else {
                daoSupport.execute("create table " + tb + "_" + year + " like " + tb);
                daoSupport.execute("insert into " + tb + "_" + year + " select * from " + tb + " where create_time >=? and create_time <?", time[0], time[1]);
            }
        }
    }

    /**
     * Data is initialized globally
     *
     * @param daoSupport
     */
    public static void init(DaoSupport daoSupport) {
        for (int i = 2015; i < LocalDate.now().getYear(); i++) {
            drop(i, daoSupport);
            Long[] year = DateUtil.getYearTime(i);
//          If there is data, initialize it
            int count = daoSupport.queryForInt("select count(0) from es_sss_order_data where create_time > ? and  create_time <  ?", year[0], year[1]);
            if (count > 0) {
                SyncopateUtil.syncopateTable(i, daoSupport);
            }
        }
    }

    /**
     * To create the current
     *
     * @param daoSupport
     */
    public static void createCurrentTable(DaoSupport daoSupport) {
        Integer year = LocalDate.now().getYear();
        syncopateTable(year, daoSupport);
    }

    /**
     * Delete table
     *
     * @param year
     * @param daoSupport
     */
    private static void drop(Integer year, DaoSupport daoSupport) {

        for (String tb : table) {
            daoSupport.execute("DROP TABLE IF EXISTS " + tb + "_" + year);
        }

    }

}
