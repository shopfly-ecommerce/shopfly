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
package cloud.shopfly.b2c.consumer.shop.sss;

import cloud.shopfly.b2c.consumer.core.event.RefundStatusChangeEvent;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;
import cloud.shopfly.b2c.core.base.message.RefundChangeMsg;
import cloud.shopfly.b2c.core.client.statistics.RefundDataClient;
import cloud.shopfly.b2c.core.statistics.model.dto.RefundData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Order application approved
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/20 In the afternoon2:21
 */
@Component
public class DataRefundConsumer implements RefundStatusChangeEvent {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private RefundDataClient refundDataClient;

    /**
     * After the news
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
            logger.error("Abnormal after-sale order：", e);
        }
    }
}
