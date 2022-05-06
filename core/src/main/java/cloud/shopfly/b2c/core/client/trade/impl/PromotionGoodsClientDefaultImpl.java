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
package cloud.shopfly.b2c.core.client.trade.impl;

import cloud.shopfly.b2c.core.client.trade.PromotionGoodsClient;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillGoodsManager;
import cloud.shopfly.b2c.core.promotion.tool.service.PromotionGoodsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 促销商品客户端实现
 *
 * @author zh
 * @version v7.0
 * @date 19/3/28 上午11:30
 * @since v7.0
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class PromotionGoodsClientDefaultImpl implements PromotionGoodsClient {

    @Autowired
    private PromotionGoodsManager promotionGoodsManager;

    @Autowired
    private SeckillGoodsManager seckillGoodsManager;


    @Override
    public void delPromotionGoods(Integer goodsId) {
        //删除促销商品
        promotionGoodsManager.delete(goodsId);
        //删除限时抢购商品
        seckillGoodsManager.deleteSeckillGoods(goodsId);

    }
}
