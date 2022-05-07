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
package cloud.shopfly.b2c.core.distribution.service;

import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionOrderDO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionOrderVO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionSellbackOrderVO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.framework.database.Page;


/**
 * distributionOrder Managerinterface
 *
 * @author Chopper
 * @version v1.0
 * @since v6.1
 * 2016years10month2On the afternoon5:24:14
 */
public interface DistributionOrderManager {

    /**
     * According to thesnGet distributor order details
     *
     * @param orderSn Order no.
     * @return FxOrderDO
     */
    DistributionOrderDO getModelByOrderSn(String orderSn);

    /**
     * According to theidGet distributor order details
     *
     * @param orderId The orderid
     * @return FxOrderDO
     */
    DistributionOrderDO getModel(Integer orderId);

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
     * Through the orderid, add the rebate amount of each level to the frozen amount of distributor
     *
     * @param orderSn The ordersn
     * @return Operating resultstrue Success, false failure
     */
    boolean addDistributorFreeze(String orderSn);

    /**
     * Distributor return orders are paginated
     *
     * @param pagesize
     * @param page
     * @param memberId
     * @param billId
     * @return
     */
    Page<DistributionSellbackOrderVO> pageSellBackOrder(Integer pagesize, Integer page, Integer memberId, Integer billId);


    /**
     * Settlement order inquiry
     *
     * @param pageSize
     * @param page
     * @param memberId
     * @param billId
     * @return
     */
    Page<DistributionOrderVO> pageDistributionOrder(Integer pageSize, Integer page, Integer memberId, Integer billId);


    /**
     * Settlement order inquiry
     *
     * @param pageSize
     * @param page
     * @param memberId
     * @param billId
     * @return
     */
    Page<DistributionOrderVO> pageDistributionTotalBillOrder(Integer pageSize, Integer page, Integer memberId, Integer billId);

    /**
     * According to the membershipidAcquisition of turnover
     *
     * @param memberId membersid
     * @return turnover
     */
    double getTurnover(int memberId);

    /**
     * Increase order quantity of superior staff according to buyer
     *
     * @param buyMemberId Buyer membershipid
     */
    void addOrderNum(int buyMemberId);

    /**
     * To settle the order
     *
     * @param order
     */
    void confirm(OrderDO order);
    /**
     * Calculate the rebate amount to be returned when the commodity template is refunded
     *
     * @param refundDO   The refund information
     */
    void calReturnCommission(RefundDO refundDO);
}
