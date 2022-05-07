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

import cloud.shopfly.b2c.core.aftersale.model.dos.RefundGoodsDO;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: after-salesclient
 * @date 2018/8/13 16:01
 * @since v7.0.0
 */
public interface AfterSaleClient {

    /**
     * Query the status of the refund slip
     */
    void queryRefundStatus();

    /**
     * Get a list of items for the return
     * @param sn The refund number
     * @return List of Returned Goods
     */
    List<RefundGoodsDO> getRefundGoods(String sn);

}
