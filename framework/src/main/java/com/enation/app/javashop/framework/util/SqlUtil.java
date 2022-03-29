/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.util;

import java.util.List;


/**
 * sql语句拼接工具类
 *
 * @author zjp
 * @version v1.0
 * @since v6.4.0
 * 2017年12月1日 下午4:51:28
 */
public class SqlUtil {
    /**
     * sql拼接
     *
     * @param list
     * @return
     */
    public static String sqlSplicing(List<String> list) {
        StringBuffer sql = new StringBuffer("");
        if (list.size() > 0) {
            sql.append(" where ");
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    sql.append(list.get(i) + " ");
                } else {
                    sql.append(list.get(i) + " and ");
                }
            }
        }
        return sql.toString();
    }

    /**
     * 拼接成sql的?形式，term中会将对应的值加上
     *
     * @param list
     * @param term
     * @return
     */
    public static String getInSql(Integer[] list, List<Object> term) {

        String[] temp = new String[list.length];
        for (int i = 0; i < list.length; i++) {
            temp[i] = "?";
            term.add(list[i]);
        }

        return StringUtil.arrayToString(temp, ",");

    }

    /****
     * 处理sql，将条件变成？可以自行传递参数进去
     * @param params
     * @return
     */
    public static String getInSql(Integer[] params) {
        String[] temp = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            temp[i] = "?";
        }

        return StringUtil.arrayToString(temp, ",");

    }



}
