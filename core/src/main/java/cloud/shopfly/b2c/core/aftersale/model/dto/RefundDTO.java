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
package cloud.shopfly.b2c.core.aftersale.model.dto;

import cloud.shopfly.b2c.core.aftersale.model.enums.RefundWayEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.core.aftersale.model.enums.AccountTypeEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefuseTypeEnum;
import cloud.shopfly.b2c.core.aftersale.service.AfterSaleOperateAllowable;
import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zjp
 * @version v7.0
 * @Description A single list of refundsDTO
 * @ClassName RefundDTO
 * @since v7.0 In the morning11:32 2018/5/8
 */
public class RefundDTO extends RefundDO {

    @ApiModelProperty(value = "Return of the goods(paragraph)Single-state text description", name = "refund_status_text")
    private String refundStatusText;

    @ApiModelProperty(value = "Refund account Type Text description", name = "account_type_text")
    private String accountTypeText;

    @ApiModelProperty(value = "refund(cargo)Section Type Text description:refund款，refundcargo", name = "refuse_type_text")
    private String refuseTypeText;

    @ApiModelProperty(value = "Whether the operation is allowed", name = "after_sale_operate_allowable")
    private AfterSaleOperateAllowable afterSaleOperateAllowable;

    public String getRefundStatusText() {
        return RefundStatusEnum.valueOf(this.getRefundStatus()).description();
    }

    @ApiModelProperty(value = "After-sales operation permit")
    public AfterSaleOperateAllowable getAfterSaleOperateAllowable() {
        AfterSaleOperateAllowable allowable = new AfterSaleOperateAllowable(RefuseTypeEnum.valueOf(this.getRefuseType()),
                RefundStatusEnum.valueOf(this.getRefundStatus()), PaymentTypeEnum.valueOf(this.getPaymentType()));
        return allowable;
    }

    @ApiModelProperty(value = "Text of refund Method")
    public String getAccountTypeText() {

        try {

            // Refund means park road return or offline payment
            String refundWay = RefundWayEnum.valueOf(this.getRefundWay()).description();

            if (!StringUtil.isEmpty(this.getAccountType())) {
                String text = AccountTypeEnum.valueOf(this.getAccountType()).description();

                return refundWay + "-" + text;
            }

            return refundWay;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "The unknown";
    }

    public String getRefuseTypeText() {
        return RefuseTypeEnum.valueOf(this.getRefuseType()).description();
    }

    @Override
    public String toString() {
        return "RefundDTO{}";
    }
}
