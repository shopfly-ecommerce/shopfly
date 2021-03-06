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
package cloud.shopfly.b2c.core.goods.util;

import cloud.shopfly.b2c.core.goodssearch.model.SearchSelector;
import cloud.shopfly.b2c.core.goods.model.vo.CategoryVO;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CategoriesurlGeneration tool
 *
 * @author fk
 * @version v1.0
 * @since v7.0
 * 2018years3month16On the afternoon4:24:38
 */
public class CatUrlUtils {

    /**
     * Generated to add to a categoryurl
     *
     * @param goodsCat
     * @param onlyCat  Split-only classificationtrue/false
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
     * Retrieves the name of a category based on the category in the tree structure
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
     * Gets the selected classification dimension
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
     * Generates the siblings of this classificationselector
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
