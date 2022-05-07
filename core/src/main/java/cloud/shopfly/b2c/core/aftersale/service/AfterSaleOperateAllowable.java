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
package cloud.shopfly.b2c.core.aftersale.service;

import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundOperateEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefuseTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * @author zjp
 * @version v7.0
 * @Description The condition under which the operation is allowed
 * @ClassName AfterSaleOperateAllowable
 * @since v7.0 In the morning11:32 2018/5/8
 */
@ApiModel(description = "The condition under which the operation is allowed")
public class AfterSaleOperateAllowable implements Serializable {

    private static final long serialVersionUID = -6083914452276811925L;

    public AfterSaleOperateAllowable() {
    }

    private RefuseTypeEnum type;
    private RefundStatusEnum status;
    private PaymentTypeEnum paymentType;

    public AfterSaleOperateAllowable(RefuseTypeEnum type, RefundStatusEnum status, PaymentTypeEnum paymentType) {
        this.type = type;
        this.status = status;
        this.paymentType = paymentType;
    }

    @ApiModelProperty(value = "Whether to allow cancellation", name = "allow_cancel")
    private boolean allowCancel;

    @ApiModelProperty(value = "Whether application is allowed", name = "allow_apply")
    private boolean allowApply;

    @ApiModelProperty(value = "Whether returns are allowed for storage", name = "allow_stock_in")
    private boolean allowStockIn;

    @ApiModelProperty(value = "Administrator refund", name = "allow_admin_refund")
    private boolean allowAdminRefund;


    public boolean getAllowCancel() {
        allowCancel = RefundOperateChecker.checkAllowable(type, paymentType, status, RefundOperateEnum.CANCEL);
        return allowCancel;
    }


    public boolean getAllowApply() {
        allowApply = RefundOperateChecker.checkAllowable(type, paymentType, status, RefundOperateEnum.APPLY);
        return allowApply;
    }


    public boolean getAllowStockIn() {
        allowStockIn = RefundOperateChecker.checkAllowable(type, paymentType, status, RefundOperateEnum.STOCK_IN);
        return allowStockIn;
    }


    public boolean getAllowAdminRefund() {
        allowAdminRefund = RefundOperateChecker.checkAllowable(type, paymentType, status, RefundOperateEnum.ADMIN_REFUND);
        return allowAdminRefund;
    }



}
