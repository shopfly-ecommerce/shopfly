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
import cloud.shopfly.b2c.core.goods.model.dos.BrandDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 品牌url工具
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年9月15日 下午5:00:28
 */
public class BrandUrlUtils {


    /**
     * 生成品牌的url
     *
     * @param brandid
     * @return
     */
    public static String createBrandUrl(String brandid) {
        Map<String, String> params = ParamsUtils.getReqParams();

        params.put("brand", brandid);

        return ParamsUtils.paramsToUrlString(params);
    }

    /**
     * 生成没有品牌的url
     *
     * @return
     */
    private static String createUrlWithOutBrand() {
        Map<String, String> params = ParamsUtils.getReqParams();

        params.remove("brand");

        return ParamsUtils.paramsToUrlString(params);
    }

    /**
     * 根据id查找brand
     *
     * @param brandList
     * @param brandid
     * @return
     */
    public static BrandDO findBrand(List<BrandDO> brandList, int brandid) {

        for (BrandDO brand : brandList) {
            if (brand.getBrandId() == brandid) {
                return brand;
            }
        }
        return null;
    }

    /**
     * 生成已经选择的品牌
     *
     * @param brandList
     * @param brandId
     * @return
     */
    public static List<SearchSelector> createSelectedBrand(List<BrandDO> brandList, Integer brandId) {
        List<SearchSelector> selectorList = new ArrayList();
        if (brandId == null) {
            return selectorList;
        }
        String brandName = "";
        BrandDO findBrand = findBrand(brandList, brandId);
        if (findBrand != null) {
            brandName = findBrand.getName();
        }

        SearchSelector selector = new SearchSelector();
        selector.setName(brandName);
        String url = createUrlWithOutBrand();
        selector.setUrl(url);
        selectorList.add(selector);
        return selectorList;
    }

}
