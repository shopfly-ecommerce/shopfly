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
package cloud.shopfly.b2c.core.trade.order.service.impl;

import cloud.shopfly.b2c.core.trade.order.model.dos.TradeDO;
import cloud.shopfly.b2c.core.trade.order.service.TradeQueryManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Trading query
 * @author Snow create in 2018/5/21
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class TradeQueryManagerImpl implements TradeQueryManager {

    @Autowired

    private DaoSupport daoSupport;

    @Override
    public TradeDO getModel(String tradeSn) {
        String sql = "select * from es_trade where trade_sn = ?";
        return this.daoSupport.queryForObject(sql, TradeDO.class,tradeSn);
    }
}
