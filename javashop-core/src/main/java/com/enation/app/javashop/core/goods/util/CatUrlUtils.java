/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goods.util;

import com.enation.app.javashop.core.goods.model.vo.CategoryVO;
import com.enation.app.javashop.core.goodssearch.model.SearchSelector;
import com.enation.app.javashop.framework.util.StringUtil;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类url生成工具
 *
 * @author fk
 * @version v1.0
 * @since v7.0
 * 2018年3月16日 下午4:24:38
 */
public class CatUrlUtils {

    /**
     * 生成加入某个分类的url
     *
     * @param goodsCat
     * @param onlyCat  只拼接分类 true/false
     * @return
     */
    public static String createCatUrl(CategoryVO goodsCat, boolean onlyCat) {
        Map<String, String> params = null;

        if (onlyCat) {
            params = new HashMap<String, String>(16);
        } else {
            params = ParamsUtils.getReqParams();
        }

        String catpath = goodsCat.getCategoryPath();
        catpath = catpath.substring(2, catpath.length());
        if (catpath.endsWith("|")) {
            catpath = catpath.substring(0, catpath.length() - 1);
        }
        catpath = catpath.replace('|', Separator.SEPARATOR_PROP_VLAUE.charAt(0));

        params.put("category", catpath);

        return catpath;
    }

    /**
     * 根据树型结构的分类取出某个分类的名称
     *
     * @param allCatList
     * @param catid
     * @return
     */
    public static CategoryVO findCat(List<CategoryVO> allCatList, int catid) {
        for (CategoryVO cat : allCatList) {
            if (cat.getCategoryId().intValue() == catid) {
                return cat;
            }

            if (StringUtil.isNotEmpty(cat.getChildren())) {
                CategoryVO findCat = findCat(cat.getChildren(), catid);
                if (findCat != null) {
                    return findCat;
                }

            }

        }
        return null;
    }

    /**
     * 获取已经选择的分类维度
     *
     * @return
     */
    public static List<SearchSelector> getCatDimSelected(List<LongTerms.Bucket> categoryBuckets, List<CategoryVO> allCatList, String cat) {
        List<SearchSelector> selectorList = new ArrayList();
        if (!StringUtil.isEmpty(cat)) {
            String[] catAr = cat.split(Separator.SEPARATOR_PROP_VLAUE);
            String catStr = "";
            for (String catId : catAr) {
                String catName = "";
                CategoryVO findCat = findCat(allCatList, StringUtil.toInt(catId, 0));
                if (findCat != null) {
                    catName = findCat.getName();
                }

                if (StringUtil.isEmpty(catName)) {
                    continue;
                }

                if (!StringUtil.isEmpty(catStr)) {
                    catStr = catStr + Separator.SEPARATOR_PROP_VLAUE;
                }
                catStr = catStr + catId;


                SearchSelector selector = new SearchSelector();
                selector.setName(catName);
                selector.setValue(findCat.getCategoryId()+"");

                selector.setOtherOptions(createBrothersCat(categoryBuckets,allCatList, findCat));
                selectorList.add(selector);

            }
        }
        return selectorList;

    }

    /**
     * 生成此分类的同级别的selector
     *
     * @param allCatList
     * @param cat
     * @return
     */
    private static List<SearchSelector> createBrothersCat(List<LongTerms.Bucket> categoryBuckets, List<CategoryVO> allCatList, CategoryVO cat) {
        List<SearchSelector> selectorList = new ArrayList();

        int parentId = cat.getParentId();
        List<CategoryVO> children = new ArrayList();
        if (parentId == 0) {
            children = allCatList;
        } else {
            CategoryVO parentCat = findCat(allCatList, parentId);
            if (parentCat == null) {
                return selectorList;
            }

            if (StringUtil.isNotEmpty(parentCat.getChildren())) {
                children = parentCat.getChildren();
            }
        }

        for (CategoryVO child : children) {
            SearchSelector selector = new SearchSelector();
            selector.setName(child.getName());
            selector.setValue(child.getCategoryId()+"");
            selectorList.add(selector);
        }

        return selectorList;
    }

}
