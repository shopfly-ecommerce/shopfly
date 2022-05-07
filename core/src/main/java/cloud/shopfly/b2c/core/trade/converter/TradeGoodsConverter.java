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
package cloud.shopfly.b2c.core.trade.converter;

import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.TradeConvertGoodsSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.TradeConvertGoodsVO;
import cloud.shopfly.b2c.framework.util.BeanUtil;


/**
 * conversiongoodsPackage, used by this packagemodelAnd the field
 *
 * @author Snow create in 2018/3/20
 * @version v2.0
 * @since v7.0.0
 */
public class TradeGoodsConverter {

    /**
     * conversiongoodsVOobject
     *
     * @return
     */
    public static TradeConvertGoodsVO goodsVOConverter(CacheGoods goods) {
        TradeConvertGoodsVO goodsVO = new TradeConvertGoodsVO();
        goodsVO.setTemplateId(goods.getTemplateId());
        goodsVO.setLastModify(goods.getLastModify());
        return goodsVO;
    }

    /**
     * conversiongoodsSkuVOobject
     *
     * @return
     */
    public static TradeConvertGoodsSkuVO goodsSkuVOConverter(GoodsSkuVO goodsSkuVO) {
        TradeConvertGoodsSkuVO skuVO = new TradeConvertGoodsSkuVO();
        BeanUtil.copyProperties(goodsSkuVO, skuVO);
        return skuVO;
    }
}
