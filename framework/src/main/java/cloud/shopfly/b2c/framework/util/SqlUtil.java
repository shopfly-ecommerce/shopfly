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

import java.util.List;


/**
 * sqlStatement concatenation utility class
 *
 * @author zjp
 * @version v1.0
 * @since v6.4.0
 * 2017years12month1On the afternoon4:51:28
 */
public class SqlUtil {
    /**
     * sqlJoining together
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
     * Joining together into asqlthe?Form,term中会将对应the值加上
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
     * To deal withsqlAnd change the condition to？You can pass parameters in by yourself
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
