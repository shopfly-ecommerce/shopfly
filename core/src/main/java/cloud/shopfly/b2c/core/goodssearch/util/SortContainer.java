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
package cloud.shopfly.b2c.core.goodssearch.util;

import cloud.shopfly.b2c.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sort the container<br>
 * Responsible for returning the sort type and checking that the sort parameters are valid
 *
 * @author kingapex
 * 2015-4-24
 */
public class SortContainer {


    private static List<Map<String, String>> list;
    private static Map<String, String> default_sort;


    /**
     * According to theurlAnd to sortmap<br>
     * Check if the argument is valid, return default sort if it is not
     *
     * @param sort
     * @return
     */
    public static Map<String, String> getSort(String sort) {
        if (StringUtil.isEmpty(sort)) {
            return default_sort;
        }

        String[] sortar = sort.split("_");
        String sortKey = sortar[0];

        String sortUpDown = "";
        if (sortar.length == 2) {
            sortUpDown = sortar[1];
            if (!"desc".equals(sortUpDown) && !"asc".equals(sortUpDown)) {
                sortUpDown = "asc";
            }
        }


        list = getSortList();
        for (Map<String, String> map : list) {
            String id = map.get("id");
            // This sort exists
            if (id.equals(sortKey)) {
                Map<String, String> result = new HashMap<String, String>(16);
                result.putAll(map);
                sortUpDown = StringUtil.isEmpty(sortUpDown) ? map.get("def_sort") : sortUpDown;
                result.put("def_sort", sortUpDown);
                return result;
            }
        }

        // No such sort, illegal, return default sort
        return default_sort;
    }


    /**
     * Generate sorted list
     *
     * @return
     */
    public static List<Map<String, String>> getSortList() {

        if (list != null) {
            return list;
        }

        list = new ArrayList();

        Map<String, String> sortDefault = new HashMap(3);
        sortDefault.put("id", "def");
        sortDefault.put("name", "default");
        sortDefault.put("def_sort", "desc");


        Map<String, String> sortBuyNum = new HashMap(3);
        sortBuyNum.put("id", "buynum");
        sortBuyNum.put("name", "sales");
        sortBuyNum.put("def_sort", "desc");


        Map<String, String> sortPrice = new HashMap(3);
        sortPrice.put("id", "price");
        sortPrice.put("name", "Price");
        sortPrice.put("def_sort", "desc");


        Map<String, String> sortGrade = new HashMap(3);
        sortGrade.put("id", "grade");
        sortGrade.put("name", "evaluation");
        sortGrade.put("def_sort", "desc");

        default_sort = sortDefault;

        list.add(sortDefault);
        list.add(sortBuyNum);
        list.add(sortPrice);
        list.add(sortGrade);

        return list;
    }
}
