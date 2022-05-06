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

import cloud.shopfly.b2c.core.client.trade.AfterSaleClient;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundGoodsDO;
import cloud.shopfly.b2c.core.aftersale.service.AfterSaleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description:
 * @date 2018/8/13 16:02
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="shopfly.product", havingValue="stand")
public class AfterSaleClientDefaultImpl implements AfterSaleClient {

    @Autowired
    private AfterSaleManager afterSaleManager;

    @Override
    public void queryRefundStatus() {
        afterSaleManager.queryRefundStatus();
    }

    @Override
    public List<RefundGoodsDO> getRefundGoods(String sn) {

        return afterSaleManager.getRefundGoods(sn);
    }
}
