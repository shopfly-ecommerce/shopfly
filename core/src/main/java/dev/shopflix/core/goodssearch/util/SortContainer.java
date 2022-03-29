/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goodssearch.util;

import dev.shopflix.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排序容器<br>
 * 负责返回排序类型和检查排序参数合法
 *
 * @author kingapex
 * 2015-4-24
 */
public class SortContainer {


    private static List<Map<String, String>> list;
    private static Map<String, String> default_sort;


    /**
     * 根据url中的排序和到排序map<br>
     * 检查了参数合法的，不合法返回默认排序
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
            //存在此排序
            if (id.equals(sortKey)) {
                Map<String, String> result = new HashMap<String, String>(16);
                result.putAll(map);
                sortUpDown = StringUtil.isEmpty(sortUpDown) ? map.get("def_sort") : sortUpDown;
                result.put("def_sort", sortUpDown);
                return result;
            }
        }

        //没有此排序，非法的，返回默认排序
        return default_sort;
    }


    /**
     * 生成排序列表
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
        sortDefault.put("name", "默认");
        sortDefault.put("def_sort", "desc");


        Map<String, String> sortBuyNum = new HashMap(3);
        sortBuyNum.put("id", "buynum");
        sortBuyNum.put("name", "销量");
        sortBuyNum.put("def_sort", "desc");


        Map<String, String> sortPrice = new HashMap(3);
        sortPrice.put("id", "price");
        sortPrice.put("name", "价格");
        sortPrice.put("def_sort", "desc");


        Map<String, String> sortGrade = new HashMap(3);
        sortGrade.put("id", "grade");
        sortGrade.put("name", "评价");
        sortGrade.put("def_sort", "desc");

        default_sort = sortDefault;

        list.add(sortDefault);
        list.add(sortBuyNum);
        list.add(sortPrice);
        list.add(sortGrade);

        return list;
    }
}
