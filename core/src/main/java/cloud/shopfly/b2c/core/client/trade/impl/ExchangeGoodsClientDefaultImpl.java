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

import cloud.shopfly.b2c.core.client.trade.ExchangeGoodsClient;
import cloud.shopfly.b2c.core.goods.model.dto.ExchangeClientDTO;
import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;
import cloud.shopfly.b2c.core.promotion.exchange.service.ExchangeGoodsManager;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author fk
 * @version v2.0
 * @Description:
 * @date 2018/8/21 16:14
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="shopfly.product", havingValue="stand")
public class ExchangeGoodsClientDefaultImpl implements ExchangeGoodsClient {

    @Autowired
    private ExchangeGoodsManager exchangeGoodsManager;

    @Override
    public ExchangeDO add(ExchangeClientDTO dto) {
        ExchangeDO exchange = dto.getExchangeSetting();
        PromotionGoodsDTO goodsDTO = dto.getGoodsDTO();

        return exchangeGoodsManager.add(exchange, goodsDTO);
    }

    @Override
    public ExchangeDO edit(ExchangeClientDTO dto) {
        ExchangeDO exchange = dto.getExchangeSetting();
        PromotionGoodsDTO goodsDTO = dto.getGoodsDTO();

        return exchangeGoodsManager.edit(exchange, goodsDTO);
    }

    @Override
    public ExchangeDO getModelByGoods(Integer goodsId) {

        return exchangeGoodsManager.getModelByGoods(goodsId);
    }

    /**
     * Delete the point exchange information of a product
     *
     * @param goodsId
     * @return
     */
    @Override
    public void del(Integer goodsId) {
        exchangeGoodsManager.deleteByGoods(goodsId);
    }
}
