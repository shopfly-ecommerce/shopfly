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
package cloud.shopfly.b2c.core.goods.service.impl.util;

import cloud.shopfly.b2c.core.goods.GoodsErrorCode;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsQueryParam;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * SearchUtil
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-01-25 In the afternoon4:17
 */
public class SearchUtil {


    /**
     * Classification of query
     *
     * @param goodsQueryParam
     * @param term
     * @param sqlBuffer
     * @param daoSupport
     */
    public static void categoryQuery(GoodsQueryParam goodsQueryParam, List<Object> term, StringBuffer sqlBuffer, DaoSupport daoSupport) {
        // Mall classification, at the same time need to query the sub-classification of goods
        if (!StringUtil.isEmpty(goodsQueryParam.getCategoryPath())) {
            List<Map> list = daoSupport.queryForList(
                    "select category_id from es_category where category_path like ? ",
                    goodsQueryParam.getCategoryPath() + "%");

            if (!StringUtil.isNotEmpty(list)) {
                throw new ServiceException(GoodsErrorCode.E301.code(), "Classification does not exist");
            }

            String[] temp = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                temp[i] = "?";
                term.add(list.get(i).get("category_id"));
            }
            String str = StringUtil.arrayToString(temp, ",");
            sqlBuffer.append(" and g.category_id in (" + str + ")");

        }
    }

    /**
     * Based on the query
     *
     * @param goodsQueryParam
     * @param term
     * @param sqlBuffer
     */
    public static void baseQuery(GoodsQueryParam goodsQueryParam, List<Object> term, StringBuffer sqlBuffer) {
        if (goodsQueryParam.getDisabled() == null) {
            goodsQueryParam.setDisabled(1);
        }
        sqlBuffer.append(" where  g.disabled = ? ");
        term.add(goodsQueryParam.getDisabled());

        // Stand up and down
        if (goodsQueryParam.getMarketEnable() != null) {
            sqlBuffer.append(" and g.market_enable = ? ");
            term.add(goodsQueryParam.getMarketEnable());
        }
        // Fuzzy keyword
        if (!StringUtil.isEmpty(goodsQueryParam.getKeyword())) {
            sqlBuffer.append(" and (g.goods_name like ? or g.sn like ? ) ");
            term.add("%" + goodsQueryParam.getKeyword() + "%");
            term.add("%" + goodsQueryParam.getKeyword() + "%");
        }
        // The name of the
        if (!StringUtil.isEmpty(goodsQueryParam.getGoodsName())) {
            sqlBuffer.append(" and g.goods_name like ?");
            term.add("%" + goodsQueryParam.getGoodsName() + "%");
        }

        // Product id
        if (!StringUtil.isEmpty(goodsQueryParam.getGoodsSn())) {
            sqlBuffer.append(" and g.sn like ?");
            term.add("%" + goodsQueryParam.getGoodsSn() + "%");
        }

        // Type
        if (!StringUtil.isEmpty(goodsQueryParam.getGoodsType())) {
            sqlBuffer.append(" and g.goods_type = ?");
            term.add(goodsQueryParam.getGoodsType());
        }

    }

}
