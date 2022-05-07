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
package cloud.shopfly.b2c.core.aftersale.model.vo;

import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * RefundSkuVO
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-12-18 In the morning9:04
 */
public class RefundSkuVO extends OrderSkuVO implements Serializable {

    @ApiModelProperty("Single refundable amount")
    private double refundPrice;

    @ApiModelProperty("Refundable amount for the last item（For buying more than one item）")
    private double lastRefundPrice;

    public double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(double refundPrice) {
        this.refundPrice = refundPrice;
    }

    public double getLastRefundPrice() {
        return lastRefundPrice;
    }

    public void setLastRefundPrice(double lastRefundPrice) {
        this.lastRefundPrice = lastRefundPrice;
    }

    public RefundSkuVO() {

    }

    public RefundSkuVO(OrderSkuVO skuVO) {
        BeanUtils.copyProperties(skuVO, this);
    }

    @Override
    public String toString() {
        return "RefundSkuVO{" +
                "refundPrice=" + refundPrice +
                ", lastRefundPrice=" + lastRefundPrice +
                '}';
    }
}
