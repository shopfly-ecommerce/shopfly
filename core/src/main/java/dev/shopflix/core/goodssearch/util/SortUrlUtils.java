/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goodssearch.util;

import dev.shopflix.core.goods.util.ParamsUtils;
import dev.shopflix.core.goodssearch.model.SearchSelector;
import dev.shopflix.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 排序url生成工具
 *
 * @author fk
 * 2017年6月30日18:01:50
 */
public class SortUrlUtils {


    /**
     * 向map中压入sort selector
     *
     * @param map
     */
    public static void createAndPut(Map<String, Object> map) {
        List<SearchSelector> selectorList = new ArrayList();
        List<Map<String, String>> sortList = SortContainer.getSortList();

        for (Map<String, String> sort : sortList) {
            SearchSelector searchSelector = creareSortUrl(sort);
            selectorList.add(searchSelector);
        }
        map.put("sort", selectorList);
    }


    /**
     * 创建排序url
     *
     * @param sort
     * @return 数组第一个元素是url，第二个元素是：是否为当前排序
     */
    private static SearchSelector creareSortUrl(Map<String, String> sort) {

        SearchSelector searchSelector = new SearchSelector();
        String isCurrent = "no";
        Map<String, String> params = ParamsUtils.getReqParams();
        String oldSort = params.get("sort");


        String id = sort.get("id");
        String defSort = sort.get("def_sort");

        String ud = defSort;


        if (StringUtil.isEmpty(oldSort)) {
            oldSort = id + "_" + defSort;
            if ("def".equals(id)) {
                isCurrent = "yes";
                oldSort = id + "_asc";
            }
        } else {

            String[] sortar = oldSort.split("_");
            String oldId = sortar[0];
            String upordown = defSort;

            //防止非法的排序
            if (!checkExists(oldId)) {
                oldId = "def";
            }

            //当前排序，则切换 升降序
            if (oldId.equals(id)) {
                isCurrent = "yes";
                if (sortar.length == 2) {
                    upordown = sortar[1];
                }


                upordown = "desc".equals(upordown) ? "asc" : "desc";

                oldSort = id + "_" + upordown;
            } else {
                //非当前排序
                oldSort = id + "_" + defSort;
            }

            ud = upordown;
        }

        params.put("sort", oldSort);

        //是否当前排序
        if ("yes".equals(isCurrent)) {
            searchSelector.setSelected(true);
        } else {
            searchSelector.setSelected(false);
        }

        String name = sort.get("name");
        String url = ParamsUtils.paramsToUrlString(params);
        searchSelector.setName(name);
        searchSelector.setUrl(url);
        searchSelector.setValue("desc".equals(ud) ? "asc" : "desc");

        return searchSelector;

    }


    /**
     * 检测request中的排序是否有定义
     *
     * @param oldSort
     * @return
     */
    private static boolean checkExists(String oldSort) {
        List<Map<String, String>> list = SortContainer.getSortList();
        for (Map<String, String> map : list) {
            if (map.get("id").equals(oldSort)) {
                return true;
            }
        }
        return false;
    }

}
