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
 * @Description: Status
 * @date 2018/8/21 15:13
 * @since v7.0.0
 */
public interface ExchangeGoodsClient {

    /**
     * Add point redemption
     * @param dto Status
     * @return ExchangeSetting Status*/
    ExchangeDO add(ExchangeClientDTO dto);

    /**
     * Modified point redemption
     * @param dto productDTO
     * @return ExchangeSetting Status
     */
    ExchangeDO edit(ExchangeClientDTO dto);

    /**
     * Query information about a product
     * @param goodsId
     * @return
     */
    ExchangeDO getModelByGoods(Integer goodsId);

    /**
     * Delete the point exchange information of a product
     * @param goodsId
     * @return
     */
    void del(Integer goodsId);

}
