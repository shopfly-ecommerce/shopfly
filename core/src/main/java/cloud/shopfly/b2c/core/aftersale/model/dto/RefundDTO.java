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
 * @Description 退款单列表DTO
 * @ClassName RefundDTO
 * @since v7.0 上午11:32 2018/5/8
 */
public class RefundDTO extends RefundDO {

    @ApiModelProperty(value = "退货(款)单状态文字描述", name = "refund_status_text")
    private String refundStatusText;

    @ApiModelProperty(value = "退款账户类型文字描述", name = "account_type_text")
    private String accountTypeText;

    @ApiModelProperty(value = "退(货)款类型文字描述:退款，退货", name = "refuse_type_text")
    private String refuseTypeText;

    @ApiModelProperty(value = "操作是否允许", name = "after_sale_operate_allowable")
    private AfterSaleOperateAllowable afterSaleOperateAllowable;

    public String getRefundStatusText() {
        return RefundStatusEnum.valueOf(this.getRefundStatus()).description();
    }

    @ApiModelProperty(value = "售后操作允许情况")
    public AfterSaleOperateAllowable getAfterSaleOperateAllowable() {
        AfterSaleOperateAllowable allowable = new AfterSaleOperateAllowable(RefuseTypeEnum.valueOf(this.getRefuseType()),
                RefundStatusEnum.valueOf(this.getRefundStatus()), PaymentTypeEnum.valueOf(this.getPaymentType()));
        return allowable;
    }

    @ApiModelProperty(value = "退款方式文字")
    public String getAccountTypeText() {

        try {

            // 退款方式  園路退回或者线下支付
            String refundWay = RefundWayEnum.valueOf(this.getRefundWay()).description();

            if (!StringUtil.isEmpty(this.getAccountType())) {
                String text = AccountTypeEnum.valueOf(this.getAccountType()).description();

                return refundWay + "-" + text;
            }

            return refundWay;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "未知";
    }

    public String getRefuseTypeText() {
        return RefuseTypeEnum.valueOf(this.getRefuseType()).description();
    }

    @Override
    public String toString() {
        return "RefundDTO{}";
    }
}
