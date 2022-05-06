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

import cloud.shopfly.b2c.core.trade.order.model.dos.TradeDO;

/**
 * 交易查询接口
 *
 * @author Snow create in 2018/5/21
 * @version v2.0
 * @since v7.0.0
 */
public interface TradeQueryManager {

    /**
     * 根据交易单号查询交易对象
     *
     * @param tradeSn
     * @return
     */
    TradeDO getModel(String tradeSn);

}
