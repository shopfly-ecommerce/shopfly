/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.aftersale.model.dto;

import dev.shopflix.core.aftersale.model.dos.RefundDO;
import dev.shopflix.core.aftersale.model.enums.AccountTypeEnum;
import dev.shopflix.core.aftersale.model.enums.RefundStatusEnum;
import dev.shopflix.core.aftersale.model.enums.RefundWayEnum;
import dev.shopflix.core.aftersale.model.enums.RefuseTypeEnum;
import dev.shopflix.core.aftersale.service.AfterSaleOperateAllowable;
import dev.shopflix.core.trade.order.model.enums.PaymentTypeEnum;
import dev.shopflix.framework.util.StringUtil;
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
