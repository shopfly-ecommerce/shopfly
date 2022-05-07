/*
 * Yi family of hui（Beijing）All Rights Reserved.
 * You may not use this file without permission.
 * The official address：www.javamall.com.cn
 */
package cloud.shopfly.b2c.consumer.shop.distribution;

import cloud.shopfly.b2c.consumer.core.event.RefundStatusChangeEvent;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;
import cloud.shopfly.b2c.core.base.message.RefundChangeMsg;
import cloud.shopfly.b2c.core.client.distribution.DistributionOrderClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Distribution order refund
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/23 In the morning7:44
 */

@Component
public class DistributionRefundConsumer implements RefundStatusChangeEvent {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private DistributionOrderClient distributionOrderClient;


    @Override
    @Transactional( rollbackFor = Exception.class)
    public void refund(RefundChangeMsg refundChangeMsg) {
        try {
            if (refundChangeMsg.getRefundStatusEnum().equals(RefundStatusEnum.PASS)) {
                // When returning goods, calculate all levels of rebate amount to be returned into the database
                this.distributionOrderClient.calReturnCommission(refundChangeMsg.getRefund());
            }
        } catch (Exception e) {
            logger.error("The order refund calculation is abnormal：", e);
        }
    }

}
