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
package cloud.shopfly.b2c.core.goods.model.dto;

import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;

import java.io.Serializable;

/**
 * @author fk
 * @version v2.0
 * @Description:
 * @date 2018/8/21 15:15
 * @since v7.0.0
 */
public class ExchangeClientDTO implements Serializable {

    private ExchangeDO exchangeSetting;

    private PromotionGoodsDTO goodsDTO;

    public ExchangeClientDTO() {
    }

    public ExchangeClientDTO(ExchangeDO exchangeSetting, PromotionGoodsDTO goodsDTO) {
        this.exchangeSetting = exchangeSetting;
        this.goodsDTO = goodsDTO;
    }

    public ExchangeDO getExchangeSetting() {
        return exchangeSetting;
    }

    public void setExchangeSetting(ExchangeDO exchangeSetting) {
        this.exchangeSetting = exchangeSetting;
    }

    public PromotionGoodsDTO getGoodsDTO() {
        return goodsDTO;
    }

    public void setGoodsDTO(PromotionGoodsDTO goodsDTO) {
        this.goodsDTO = goodsDTO;
    }

    @Override
    public String toString() {
        return "ExchangeClientDTO{" +
                "exchangeSetting=" + exchangeSetting +
                ", goodsDTO=" + goodsDTO +
                '}';
    }
}
