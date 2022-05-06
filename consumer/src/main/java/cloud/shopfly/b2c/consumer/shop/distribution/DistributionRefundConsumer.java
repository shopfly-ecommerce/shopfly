/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 分销订单退款
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/23 上午7:44
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
                // 退货时算好各个级别需要退的返利金额 放入数据库
                this.distributionOrderClient.calReturnCommission(refundChangeMsg.getRefund());
            }
        } catch (Exception e) {
            logger.error("订单退款计算返利异常：", e);
        }
    }

}
