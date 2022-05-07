/*
 * Yi family of hui（Beijing）All Rights Reserved.
 * You may not use this file without permission.
 * The official address：www.javamall.com.cn
 */
package cloud.shopfly.b2c.core.client.distribution.impl;

import cloud.shopfly.b2c.core.client.distribution.DistributionOrderClient;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionOrderDO;
import cloud.shopfly.b2c.core.distribution.service.DistributionOrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * DistributionOrderClientDefaultImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 In the afternoon1:39
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class DistributionOrderClientDefaultImpl implements DistributionOrderClient {

    @Autowired
    private DistributionOrderManager distributionOrderManager;

    /**
     * According to thesnGet distributor order details
     *
     * @param orderSn Order no.
     * @return FxOrderDO
     */
    @Override
    public DistributionOrderDO getModelByOrderSn(String orderSn) {
        return distributionOrderManager.getModelByOrderSn(orderSn);
    }

    /**
     * Save a piece of data
     *
     * @param distributionOrderDO
     * @return
     */
    @Override
    public DistributionOrderDO add(DistributionOrderDO distributionOrderDO) {
        return distributionOrderManager.add(distributionOrderDO);
    }

    /**
     * Through the orderid, calculate the rebate amount of each level and save it in the database
     *
     * @param orderSn Order no.
     * @return The calculation resultstrue Success, false failure
     */
    @Override
    public boolean calCommission(String orderSn) {
        return distributionOrderManager.calCommission(orderSn);
    }

    /**
     * Increase order quantity of superior staff according to buyer
     *
     * @param buyMemberId Buyer membershipid
     */
    @Override
    public void addOrderNum(int buyMemberId) {
        distributionOrderManager.addOrderNum(buyMemberId);
    }

    /**
     * Calculate the amount of rebate to be refunded
     *
     * @param refundDO The refund amount
     */
    @Override
    public void calReturnCommission(RefundDO refundDO) {
        this.distributionOrderManager.calReturnCommission(refundDO);
    }

    /**
     * To settle the order
     *
     * @param order
     */
    @Override
    public void confirm(OrderDO order) {
        distributionOrderManager.confirm(order);
    }
}
