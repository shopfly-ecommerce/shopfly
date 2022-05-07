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
package cloud.shopfly.b2c.core.client.distribution;

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionOrderDO;


/**
 * distributionOrder client
 *
 * @author Chopper
 * @version v1.0
 * @since v6.1
 * 2016years10month2On the afternoon5:24:14
 */
public interface DistributionOrderClient {

    /**
     * According to thesnGet distributor order details
     *
     * @param orderSn Order no.
     * @return FxOrderDO
     */
    DistributionOrderDO getModelByOrderSn(String orderSn);

    /**
     * Save a piece of data
     *
     * @param distributionOrderDO
     * @return
     */
    DistributionOrderDO add(DistributionOrderDO distributionOrderDO);

    /**
     * Through the orderid, calculate the rebate amount of each level and save it in the database
     *
     * @param orderSn Order no.
     * @return The calculation resultstrue Success, false failure
     */
    boolean calCommission(String orderSn);

    /**
     * Increase order quantity of superior staff according to buyer
     *
     * @param buyMemberId Buyer membershipid
     */
    void addOrderNum(int buyMemberId);


    /**
     * Calculate the amount of rebate to be refunded
     *
     * @param refundDO
     */
    void calReturnCommission( RefundDO refundDO);


    /**
     * To settle the order
     *
     * @param order
     */
    void confirm(OrderDO order);

}
