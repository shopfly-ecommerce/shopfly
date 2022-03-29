/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
 */
package dev.shopflix.core.client.distribution.impl;

import dev.shopflix.core.aftersale.model.dos.RefundDO;
import dev.shopflix.core.client.distribution.DistributionOrderClient;
import dev.shopflix.core.distribution.model.dos.DistributionOrderDO;
import dev.shopflix.core.distribution.service.DistributionOrderManager;
import dev.shopflix.core.trade.order.model.dos.OrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * DistributionOrderClientDefaultImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 下午1:39
 */
@Service
@ConditionalOnProperty(value = "javashop.product", havingValue = "stand")
public class DistributionOrderClientDefaultImpl implements DistributionOrderClient {

    @Autowired
    private DistributionOrderManager distributionOrderManager;

    /**
     * 根据sn获得分销商订单详情
     *
     * @param orderSn 订单编号
     * @return FxOrderDO
     */
    @Override
    public DistributionOrderDO getModelByOrderSn(String orderSn) {
        return distributionOrderManager.getModelByOrderSn(orderSn);
    }

    /**
     * 保存一条数据
     *
     * @param distributionOrderDO
     * @return
     */
    @Override
    public DistributionOrderDO add(DistributionOrderDO distributionOrderDO) {
        return distributionOrderManager.add(distributionOrderDO);
    }

    /**
     * 通过订单id，计算出各个级别的返利金额并保存到数据库
     *
     * @param orderSn 订单编号
     * @return 计算结果 true 成功， false 失败
     */
    @Override
    public boolean calCommission(String orderSn) {
        return distributionOrderManager.calCommission(orderSn);
    }

    /**
     * 根据购买人增加上级人员订单数量
     *
     * @param buyMemberId 购买人会员id
     */
    @Override
    public void addOrderNum(int buyMemberId) {
        distributionOrderManager.addOrderNum(buyMemberId);
    }

    /**
     * 计算退款时需要退的返利金额
     *
     * @param refundDO 退款金额
     */
    @Override
    public void calReturnCommission(RefundDO refundDO) {
        this.distributionOrderManager.calReturnCommission(refundDO);
    }

    /**
     * 结算订单
     *
     * @param order
     */
    @Override
    public void confirm(OrderDO order) {
        distributionOrderManager.confirm(order);
    }
}
