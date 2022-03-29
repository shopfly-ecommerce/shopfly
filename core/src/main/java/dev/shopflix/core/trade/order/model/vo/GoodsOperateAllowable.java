/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.shopflix.core.trade.order.model.enums.*;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 商品可进行的操作
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsOperateAllowable implements Serializable {

    @ApiModelProperty(value = "是否允许申请售后")
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
     * 空构造器
     */
    public GoodsOperateAllowable() {

    }

    /**
     * 根据各种状态构建对象
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

        //货到付款
        if (PaymentTypeEnum.COD.compareTo(paymentTypeEnum) == 0) {

            //是否允许被申请售后 = 已收货 && 订单没有申请售后 && 订单是已发货状态
            allowApplyService = PayStatusEnum.PAY_YES.value().equals(payStatus.value())
                    && defaultServiceStatus;
        } else {
            //是否允许被申请售后 = 已付款 && 订单没有申请售后 && 订单是已收货状态
            allowApplyService = PayStatusEnum.PAY_YES.value().equals(payStatus.value())
                    && defaultServiceStatus
                    && ShipStatusEnum.SHIP_ROG.value().equals(shipStatus.value());
        }

    }


}
