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

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.*;
import cloud.shopfly.b2c.core.trade.order.support.OrderOperateChecker;
import cloud.shopfly.b2c.core.trade.order.support.OrderOperateFlow;
import cloud.shopfly.b2c.core.trade.order.support.OrderStep;
import cloud.shopfly.b2c.core.trade.order.model.enums.*;
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * Operations that can be performed on an order
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderOperateAllowable implements Serializable {

    @ApiModelProperty(value = "Whether to allow cancellation")
    private Boolean allowCancel;

    @ApiModelProperty(value = "Whether to allow validation")
    private Boolean allowConfirm;

    @ApiModelProperty(value = "Whether it is allowed to be paid")
    private Boolean allowPay;

    @ApiModelProperty(value = "Whether to allow shipment")
    private Boolean allowShip;

    @ApiModelProperty(value = "Whether the goods are allowed to be received")
    private Boolean allowRog;

    @ApiModelProperty(value = "Whether to allow comments")
    private Boolean allowComment;

    @ApiModelProperty(value = "Whether to allow completion")
    private Boolean allowComplete;

    @ApiModelProperty(value = "Whether application for after-sales service is allowed")
    private Boolean allowApplyService;

    @ApiModelProperty(value = "Whether cancellation is allowed(after-sales)")
    private Boolean allowServiceCancel;

    @ApiModelProperty(value = "Whether to view logistics information")
    private Boolean allowCheckExpress;

    @ApiModelProperty(value = "Whether change of consignee information is allowed")
    private Boolean allowEditConsignee;

    @ApiModelProperty(value = "Whether price changes are allowed")
    private Boolean allowEditPrice;

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());


    public Boolean getAllowCancel() {
        return allowCancel;
    }

    public void setAllowCancel(Boolean allowCancel) {
        this.allowCancel = allowCancel;
    }

    public Boolean getAllowConfirm() {
        return allowConfirm;
    }

    public void setAllowConfirm(Boolean allowConfirm) {
        this.allowConfirm = allowConfirm;
    }

    public Boolean getAllowPay() {
        return allowPay;
    }

    public void setAllowPay(Boolean allowPay) {
        this.allowPay = allowPay;
    }

    public Boolean getAllowShip() {
        return allowShip;
    }

    public void setAllowShip(Boolean allowShip) {
        this.allowShip = allowShip;
    }

    public Boolean getAllowRog() {
        return allowRog;
    }

    public void setAllowRog(Boolean allowRog) {
        this.allowRog = allowRog;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    public Boolean getAllowComplete() {
        return allowComplete;
    }

    public void setAllowComplete(Boolean allowComplete) {
        this.allowComplete = allowComplete;
    }

    public Boolean getAllowApplyService() {
        return allowApplyService;
    }

    public void setAllowApplyService(Boolean allowApplyService) {
        this.allowApplyService = allowApplyService;
    }

    public Boolean getAllowServiceCancel() {
        return allowServiceCancel;
    }

    public void setAllowServiceCancel(Boolean allowServiceCancel) {
        this.allowServiceCancel = allowServiceCancel;
    }

    public Boolean getAllowCheckExpress() {
        return allowCheckExpress;
    }

    public void setAllowCheckExpress(Boolean allowCheckExpress) {
        this.allowCheckExpress = allowCheckExpress;
    }

    public Boolean getAllowEditConsignee() {
        return allowEditConsignee;
    }

    public void setAllowEditConsignee(Boolean allowEditConsignee) {
        this.allowEditConsignee = allowEditConsignee;
    }

    public Boolean getAllowEditPrice() {
        return allowEditPrice;
    }

    public void setAllowEditPrice(Boolean allowEditPrice) {
        this.allowEditPrice = allowEditPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderOperateAllowable allowable = (OrderOperateAllowable) o;

        return new EqualsBuilder()
                .append(allowCancel, allowable.allowCancel)
                .append(allowConfirm, allowable.allowConfirm)
                .append(allowPay, allowable.allowPay)
                .append(allowShip, allowable.allowShip)
                .append(allowRog, allowable.allowRog)
                .append(allowComment, allowable.allowComment)
                .append(allowComplete, allowable.allowComplete)
                .append(allowApplyService, allowable.allowApplyService)
                .append(allowServiceCancel, allowable.allowServiceCancel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(allowCancel)
                .append(allowConfirm)
                .append(allowPay)
                .append(allowShip)
                .append(allowRog)
                .append(allowComment)
                .append(allowComplete)
                .append(allowApplyService)
                .append(allowServiceCancel)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OrderOperateAllowable{" +
                "allowCancel=" + allowCancel +
                ", allowConfirm=" + allowConfirm +
                ", allowPay=" + allowPay +
                ", allowShip=" + allowShip +
                ", allowRog=" + allowRog +
                ", allowComment=" + allowComment +
                ", allowComplete=" + allowComplete +
                ", allowApplyService=" + allowApplyService +
                ", allowServiceCancel=" + allowServiceCancel +
                '}';
    }

    /**
     * An empty constructor
     */
    public OrderOperateAllowable() {

    }


    /**
     * Build objects from various states
     *
     * @param order
     */
    public OrderOperateAllowable(OrderDO order) {

        String paymentType = order.getPaymentType();

        // Locate the appropriate order process and build the Checker
        Map<OrderStatusEnum, OrderStep> flow = OrderOperateFlow.getFlow(PaymentTypeEnum.valueOf(paymentType), OrderTypeEnum.valueOf(order.getOrderType()));

//If you need to debug, you can open the surface comment
//        if (logger.isDebugEnabled()) {
//
//            logger.debug("For order acquisitionflow:");
//            logger.debug(order.toString());
//            logger.debug("To get toflowfor：");
//            logger.debug(flow.toString());
//
//        }

        OrderOperateChecker orderOperateChecker = new OrderOperateChecker(flow);

        // Status
        OrderStatusEnum orderStatus = OrderStatusEnum.valueOf(order.getOrderStatus());

        String serviceStatus = order.getServiceStatus();
        // Whether to allow cancellation
        this.allowCancel = orderOperateChecker.checkAllowable(orderStatus, OrderOperateEnum.CANCEL);
        // Whether to allow validation
        this.allowConfirm = orderOperateChecker.checkAllowable(orderStatus, OrderOperateEnum.CONFIRM);

        // Whether it is allowed to be paid
        this.allowPay = orderOperateChecker.checkAllowable(orderStatus, OrderOperateEnum.PAY);


        // Allowed to be shipped or not: special requirements: after sales have been requested or approved
        this.allowShip = orderOperateChecker.checkAllowable(orderStatus, OrderOperateEnum.SHIP)
                && !ServiceStatusEnum.APPLY.name().equals(serviceStatus)
                && !ServiceStatusEnum.PASS.name().equals(serviceStatus);

        // Whether the goods are allowed to be received
        this.allowRog = orderOperateChecker.checkAllowable(orderStatus, OrderOperateEnum.ROG);

        // The delivery status
        String shipStatus = order.getShipStatus();
        // Review status
        String commentStatus = order.getCommentStatus();

        // Payment status
        String payStatus = order.getPayStatus();

        // Allowed to be commented: comments can be made after receiving the goods, and the comments are not completed (comments can not be made after the completion of the comments)
        this.allowComment = CommentStatusEnum.UNFINISHED.value().equals(commentStatus) && ShipStatusEnum.SHIP_ROG.value().equals(shipStatus);

        // Whether to allow completion
        this.allowComplete = orderOperateChecker.checkAllowable(orderStatus, OrderOperateEnum.COMPLETE);

        boolean defaultServiceStatus = ServiceStatusEnum.NOT_APPLY.value().equals(serviceStatus);

        // Cash on delivery
        if (PaymentTypeEnum.COD.name().equals(paymentType)) {

            // Is it allowed to be applied for after sale = Received && Unapplied for after sale && the order is received
            allowApplyService = ShipStatusEnum.SHIP_ROG.value().equals(shipStatus)
                    && defaultServiceStatus
                    && ShipStatusEnum.SHIP_ROG.value().equals(shipStatus);

        } else {
            // Is it allowed to apply for after sale = paid && Not applied for after sale && the order is received status
            allowApplyService = PayStatusEnum.PAY_YES.value().equals(payStatus)
                    && defaultServiceStatus
                    && ShipStatusEnum.SHIP_ROG.value().equals(shipStatus);

            // Is the order allowed to cancel (after sale) = Payment status paid && The order paid
            this.allowServiceCancel = PayStatusEnum.PAY_YES.value().equals(payStatus)
                    && OrderStatusEnum.PAID_OFF.value().equals(orderStatus.value())
                    && (ServiceStatusEnum.NOT_APPLY.name().equals(serviceStatus)
                    || ServiceStatusEnum.EXPIRED.name().equals(serviceStatus));

        }

        // Whether to view logistics information = when the logistics order number is not empty and the logistics company is not empty
        this.allowCheckExpress = order.getLogiId() != null && !order.getLogiId().equals(0) && !StringUtil.isEmpty(order.getShipNo());
        // Not cancelled and not failed to exit
        boolean flag = !OrderStatusEnum.INTODB_ERROR.equals(orderStatus) && !OrderStatusEnum.CANCELLED.equals(orderStatus);
        // Allow change of consignee information = Shipping Status Not shipped
        this.allowEditConsignee = flag && ShipStatusEnum.SHIP_NO.value().equals(order.getShipStatus());
        // Whether to allow change price = (online payment & not paying) | | (cod && unfilled)
        this.allowEditPrice = flag && (PaymentTypeEnum.ONLINE.value().equals(order.getPaymentType())
                && PayStatusEnum.PAY_NO.value().equals(order.getPayStatus()))
                || (PaymentTypeEnum.COD.value().equals(order.getPaymentType())
                && ShipStatusEnum.SHIP_NO.value().equals(order.getShipStatus()));


    }


}
