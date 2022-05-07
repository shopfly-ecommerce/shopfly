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
package cloud.shopfly.b2c.core.trade.order.model.vo;

import cloud.shopfly.b2c.core.trade.order.model.enums.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import cloud.shopfly.b2c.core.trade.order.model.enums.*;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Operations that can be performed on a commodity
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsOperateAllowable implements Serializable {

    @ApiModelProperty(value = "Whether application for after-sales service is allowed")
    private Boolean allowApplyService;

    public Boolean getAllowApplyService() {
        return allowApplyService;
    }

    public void setAllowApplyService(Boolean allowApplyService) {
        this.allowApplyService = allowApplyService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoodsOperateAllowable that = (GoodsOperateAllowable) o;

        return new EqualsBuilder()
                .append(allowApplyService, that.allowApplyService)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(allowApplyService)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "GoodsOperateAllowable{" +
                "allowApplyService=" + allowApplyService +
                '}';
    }

    /**
     * An empty constructor
     */
    public GoodsOperateAllowable() {

    }

    /**
     * Build objects from various states
     *
     * @param paymentTypeEnum
     * @param orderStatus
     * @param shipStatus
     * @param serviceStatus
     * @param payStatus
     */
    public GoodsOperateAllowable(PaymentTypeEnum paymentTypeEnum, OrderStatusEnum orderStatus,
                                 ShipStatusEnum shipStatus, ServiceStatusEnum serviceStatus,
                                 PayStatusEnum payStatus) {

        boolean defaultServiceStatus = ServiceStatusEnum.NOT_APPLY.value().equals(serviceStatus.value());

        // Cash on delivery
        if (PaymentTypeEnum.COD.compareTo(paymentTypeEnum) == 0) {

            // Allowed to apply for after sale = Received && Order not applied for after sale && Order is shipped
            allowApplyService = PayStatusEnum.PAY_YES.value().equals(payStatus.value())
                    && defaultServiceStatus;
        } else {
            // Is it allowed to be applied for after sale = Paid && Order not applied for after sale && Order is received status
            allowApplyService = PayStatusEnum.PAY_YES.value().equals(payStatus.value())
                    && defaultServiceStatus
                    && ShipStatusEnum.SHIP_ROG.value().equals(shipStatus.value());
        }

    }


}
