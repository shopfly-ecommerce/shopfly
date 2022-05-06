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
package cloud.shopfly.b2c.core.base.message;

import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;

import java.io.Serializable;

/**
 * 退货退款消息
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/19 上午10:36
 */
public class RefundChangeMsg implements Serializable {

    private static final long serialVersionUID = -5608209655474949712L;

    private RefundDO refund;

    /**
     * 售后状态
     */
    private RefundStatusEnum refundStatusEnum;

    public RefundChangeMsg(RefundDO refundDO, RefundStatusEnum refundStatusEnum) {
        this.refund = refundDO;
        this.refundStatusEnum = refundStatusEnum;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public RefundDO getRefund() {
        return refund;
    }

    public void setRefund(RefundDO refund) {
        this.refund = refund;
    }

    public RefundStatusEnum getRefundStatusEnum() {
        return refundStatusEnum;
    }

    public void setRefundStatusEnum(RefundStatusEnum refundStatusEnum) {
        this.refundStatusEnum = refundStatusEnum;
    }
}
