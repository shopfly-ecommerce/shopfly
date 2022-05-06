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

/**
 * 交易价格业务接口
 *
 * @author kingapex
 * @version v2.0
 * @since v7.0.0
 * 2017年3月23日上午10:01:30
 */
public interface TradePriceManager {


    /**
     * 未付款的订单，商家修改订单金额，同时修改交易价格
     *
     * @param tradeSn       交易编号
     * @param tradePrice
     * @param discountPrice
     */
    void updatePrice(String tradeSn, Double tradePrice, Double discountPrice);

}
