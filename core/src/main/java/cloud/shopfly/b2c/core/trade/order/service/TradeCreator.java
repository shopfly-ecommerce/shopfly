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
package cloud.shopfly.b2c.core.trade.order.service;

import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;

/**
 * Created by kingapex on 2019-01-23.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-23
 */
public interface TradeCreator {

    /**
     * 检测配送范围
     *
     * @return
     */
    TradeCreator checkShipRange();

    /**
     * 检测商品合法性
     *
     * @return
     */
    TradeCreator checkGoods();

    /**
     * 检测促销活动合法性
     *
     * @return
     */
    TradeCreator checkPromotion();

    /**
     * 创建交易
     *
     * @return
     */
    TradeVO createTrade();

}
