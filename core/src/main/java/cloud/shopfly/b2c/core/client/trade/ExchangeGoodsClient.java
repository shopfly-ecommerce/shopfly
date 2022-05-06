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
package cloud.shopfly.b2c.core.client.trade;

import cloud.shopfly.b2c.core.goods.model.dto.ExchangeClientDTO;
import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;

/**
 * @author fk
 * @version v2.0
 * @Description: 积分兑换
 * @date 2018/8/21 15:13
 * @since v7.0.0
 */
public interface ExchangeGoodsClient {

    /**
     * 添加积分兑换
     * @param dto 积分兑换
     * @return ExchangeSetting 积分兑换 */
    ExchangeDO add(ExchangeClientDTO dto);

    /**
     * 修改积分兑换
     * @param dto 商品DTO
     * @return ExchangeSetting 积分兑换
     */
    ExchangeDO edit(ExchangeClientDTO dto);

    /**
     * 查询某个商品的积分兑换信息
     * @param goodsId
     * @return
     */
    ExchangeDO getModelByGoods(Integer goodsId);

    /**
     * 删除某个商品的积分兑换信息
     * @param goodsId
     * @return
     */
    void del(Integer goodsId);

}
