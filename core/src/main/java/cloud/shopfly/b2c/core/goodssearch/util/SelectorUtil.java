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

import cloud.shopfly.b2c.core.goods.model.dos.BrandDO;
import cloud.shopfly.b2c.core.goods.model.vo.CategoryVO;
import cloud.shopfly.b2c.core.goods.util.BrandUrlUtils;
import cloud.shopfly.b2c.core.goods.util.CatUrlUtils;
import cloud.shopfly.b2c.core.goods.util.Separator;
import cloud.shopfly.b2c.core.goodssearch.model.PropSelector;
import cloud.shopfly.b2c.core.goodssearch.model.SearchSelector;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;

import java.util.*;

/**
 * The selector utility class
 *
 * @author fk
 * @version v1.0
 * 2017years4month25On the afternoon4:59:31
 */
public class SelectorUtil {

    /**
     * Create a category selector
     *
     * @param categoryBuckets
     * @param allCatList
     * @return
     */
    public static List<SearchSelector> createCatSelector(List<LongTerms.Bucket> categoryBuckets, List<CategoryVO> allCatList, Integer catId) {
        String catPid = null;
        if (catId != null) {
//            String[] catar = catId.split(Separator.SEPARATOR_PROP_VLAUE);
            catPid = catId + "";
        }
        List<SearchSelector> selectorList = new ArrayList<>();
        Map<String, String> map = new HashMap<>(16);
        if (categoryBuckets != null && categoryBuckets.size() > 0) {
            for (Bucket bucket : categoryBuckets) {
                String categoryId = bucket.getKey().toString();
                String catname = "";
                CategoryVO findcat = CatUrlUtils.findCat(allCatList, StringUtil.toInt(categoryId, 0));
                // The CAT argument is not passed
                if (catPid == null) {
                    String[] path = findcat.getCategoryPath().replace('|', Separator.SEPARATOR_PROP_VLAUE.charAt(0)).split(Separator.SEPARATOR_PROP_VLAUE);
                    if (map.get(path[1]) != null) {
                        continue;
                    }
                    CategoryVO parentCat = CatUrlUtils.findCat(allCatList, StringUtil.toInt(path[1], 0));
                    map.put(path[1], path[1]);
                    catname = parentCat.getName();
                    findcat = parentCat;
                } else {//A parameter
                    int index = findcat.getCategoryPath().indexOf(catPid);
                    if (index == -1) {
                        continue;
                    }
                    String[] path = findcat.getCategoryPath().substring(index + catPid.length() + 1).replace('|', Separator.SEPARATOR_PROP_VLAUE.charAt(0))
                            .split(Separator.SEPARATOR_PROP_VLAUE);
                    if (map.get(path[0]) != null || "".equals(path[0])) {
                        continue;
                    }
                    CategoryVO cat = CatUrlUtils.findCat(allCatList, StringUtil.toInt(path[0], 0));
                    map.put(path[0], path[0]);
                    catname = cat.getName();
                    findcat = cat;
                }

                if (StringUtil.isEmpty(catname)) {
                    continue;
                }
                SearchSelector selector = new SearchSelector();
                selector.setName(catname);
//                String url = CatUrlUtils.createCatUrl(findcat, false);
                selector.setValue(findcat.getCategoryId()+"");
                selectorList.add(selector);
            }
        }

        return selectorList;
    }

    /**
     * Create a brand selector
     *
     * @param brandBuckets
     * @param brandList
     * @return
     */
    public static List<SearchSelector> createBrandSelector(List<LongTerms.Bucket> brandBuckets, List<BrandDO> brandList) {
        List<SearchSelector> selectorList = new ArrayList<>();

        if (brandBuckets != null && brandBuckets.size() > 0) {
            for (Bucket bucket : brandBuckets) {
                int brandid = StringUtil.toInt(bucket.getKey().toString(), 0);
                String brandname = "";
                BrandDO findbrand = BrandUrlUtils.findBrand(brandList, brandid);
                if (findbrand != null) {
                    brandname = findbrand.getName();
                }
                if (StringUtil.isEmpty(brandname)) {
                    continue;
                }
                SearchSelector selector = new SearchSelector();
                selector.setName(brandname);
                selector.setUrl(findbrand.getLogo());
                selector.setValue(brandid+"");
                selectorList.add(selector);
            }
        }
        return selectorList;

    }

    /**
     * Checks whether a dimension is already selected
     *
     * @param dim
     * @param props
     * @return Return if no selection is madenullIf the property value is selected
     */
    private static String checkSelected(String dim, String[] props) {
        for (int i = 0; i < props.length; i++) {
            String p = props[i];
            String[] onepropAr = p.split(Separator.SEPARATOR_PROP_VLAUE);
            if (onepropAr[0].equals(dim)) {
                return onepropAr[1];
            }
        }
        return null;
    }

    /**
     * Create a parameter selector
     *
     * @param paramBucketIt
     * @return
     */
    public static List<PropSelector> createParamSelector(Iterator<StringTerms.Bucket> paramBucketIt) {

        List<PropSelector>  propSelectorList = new ArrayList<>();
        while (paramBucketIt.hasNext()) {
            PropSelector propSelector = new PropSelector();
            Bucket paramBucket = paramBucketIt.next();
            String param = paramBucket.getKey().toString();
            StringTerms valueTerms = (StringTerms)paramBucket.getAggregations().asMap().get("valueAgg");
            Iterator<StringTerms.Bucket> valueBucketIt = valueTerms.getBuckets().iterator();
            List<SearchSelector> selectList = new ArrayList<>();
            while (valueBucketIt.hasNext()) {

                Bucket valueBucket = valueBucketIt.next();
                String value = valueBucket.getKey().toString();

                if(!StringUtil.isEmpty(value)){
                    SearchSelector selector = new SearchSelector();
                    selector.setName(value);
                    selector.setValue(value);

                    selectList.add(selector);
                }
            }
            propSelector.setKey(param);
            propSelector.setValue(selectList);
            propSelectorList.add(propSelector);
        }

        return propSelectorList;
    }

    /**
     * The sorting way
     *
     * @return
     */
    public static List<SearchSelector> createSortSelector() {

        return null;
    }


}
