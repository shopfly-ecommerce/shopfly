/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.sss;

import dev.shopflix.consumer.core.event.RefundStatusChangeEvent;
import dev.shopflix.core.aftersale.model.enums.RefundStatusEnum;
import dev.shopflix.core.base.message.RefundChangeMsg;
import dev.shopflix.core.client.statistics.RefundDataClient;
import dev.shopflix.core.statistics.model.dto.RefundData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 订单申请通过
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/20 下午2:21
 */
@Component
public class DataRefundConsumer implements RefundStatusChangeEvent {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefundDataClient refundDataClient;

    /**
     * 售后消息
     *
     * @param refundChangeMsg
     */
    @Override
    public void refund(RefundChangeMsg refundChangeMsg) {
        try {
            if (refundChangeMsg.getRefundStatusEnum().equals(RefundStatusEnum.PASS)) {
                this.refundDataClient.put(new RefundData(refundChangeMsg.getRefund()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("订单售后异常：", e);
        }
    }
}
