/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.model.vo;

import dev.shopflix.core.trade.order.model.dos.OrderDO;
import dev.shopflix.core.trade.order.support.OrderOperateChecker;
import dev.shopflix.core.trade.order.support.OrderOperateFlow;
import dev.shopflix.core.trade.order.support.OrderStep;
import dev.shopflix.core.trade.order.model.enums.*;
import dev.shopflix.framework.util.StringUtil;
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
 * 订单可进行的操作
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderOperateAllowable implements Serializable {

    @ApiModelProperty(value = "是否允许被取消")
    private Boolean allowCancel;

    @ApiModelProperty(value = "是否允许被确认")
    private Boolean allowConfirm;

    @ApiModelProperty(value = "是否允许被支付")
    private Boolean allowPay;

    @ApiModelProperty(value = "是否允许被发货")
    private Boolean allowShip;

    @ApiModelProperty(value = "是否允许被收货")
    private Boolean allowRog;

    @ApiModelProperty(value = "是否允许被评论")
    private Boolean allowComment;

    @ApiModelProperty(value = "是否允许被完成")
    private Boolean allowComplete;

    @ApiModelProperty(value = "是否允许申请售后")
    private Boolean allowApplyService;

    @ApiModelProperty(value = "是否允许取消(售后)")
    private Boolean allowServiceCancel;

    @ApiModelProperty(value = "是否允许查看物流信息")
    private Boolean allowCheckExpress;

    @ApiModelProperty(value = "是否允许更改收货人信息")
    private Boolean allowEditConsignee;

    @ApiModelProperty(value = "是否允许更改价格")
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
     * 空构造器
     */
    public OrderOperateAllowable() {

    }


    /**
     * 根据各种状态构建对象
     *
     * @param order
     */
    public OrderOperateAllowable(OrderDO order) {

        String paymentType = order.getPaymentType();

        //定位到相应的订单流程，并构建checker
        Map<OrderStatusEnum, OrderStep> flow = OrderOperateFlow.getFlow(PaymentTypeEnum.valueOf(paymentType), OrderTypeEnum.valueOf(order.getOrderType()));

//如需调试时可打开面注释
//        if (logger.isDebugEnabled()) {
//
//            logger.debug("为订单获取flow:");
//            logger.debug(order.toString());
//            logger.debug("获取到的flow为：");
//            logger.debug(flow.toString());
//
//        }

        OrderOperateChecker orderOperateChecker = new OrderOperateChecker(flow);

        //订单状态
        OrderStatusEnum orderStatus = OrderStatusEnum.valueOf(order.getOrderStatus());

        String serviceStatus = order.getServiceStatus();
        //是否允许被取消
        this.allowCancel = orderOperateChecker.checkAllowable(orderStatus, OrderOperateEnum.CANCEL);
        //是否允许被确认
        this.allowConfirm = orderOperateChecker.checkAllowable(orderStatus, OrderOperateEnum.CONFIRM);

        //是否允许被支付
        this.allowPay = orderOperateChecker.checkAllowable(orderStatus, OrderOperateEnum.PAY);


        //是否允许被发货： 要有特殊的要求：申请了售后，或售后已经被通过了
        this.allowShip = orderOperateChecker.checkAllowable(orderStatus, OrderOperateEnum.SHIP)
                && !ServiceStatusEnum.APPLY.name().equals(serviceStatus)
                && !ServiceStatusEnum.PASS.name().equals(serviceStatus);

        //是否允许被收货
        this.allowRog = orderOperateChecker.checkAllowable(orderStatus, OrderOperateEnum.ROG);

        //发货状态
        String shipStatus = order.getShipStatus();
        //评论状态
        String commentStatus = order.getCommentStatus();

        //付款状态
        String payStatus = order.getPayStatus();

        //是否允许被评论: 收货后可以评论，且评论未完成（评论完成后就不可以再评论了）
        this.allowComment = CommentStatusEnum.UNFINISHED.value().equals(commentStatus) && ShipStatusEnum.SHIP_ROG.value().equals(shipStatus);

        //是否允许被完成
        this.allowComplete = orderOperateChecker.checkAllowable(orderStatus, OrderOperateEnum.COMPLETE);

        boolean defaultServiceStatus = ServiceStatusEnum.NOT_APPLY.value().equals(serviceStatus);

        //货到付款
        if (PaymentTypeEnum.COD.name().equals(paymentType)) {

            //是否允许被申请售后 = 已收货 && 未申请过售后 && 订单是已收货状态
            allowApplyService = ShipStatusEnum.SHIP_ROG.value().equals(shipStatus)
                    && defaultServiceStatus
                    && ShipStatusEnum.SHIP_ROG.value().equals(shipStatus);

        } else {
            //是否允许被申请售后 = 已付款 && 未申请过售后 && 订单是已收货状态
            allowApplyService = PayStatusEnum.PAY_YES.value().equals(payStatus)
                    && defaultServiceStatus
                    && ShipStatusEnum.SHIP_ROG.value().equals(shipStatus);

            //订单是否允许取消(售后) = 支付状态已付款  &&  订单已付款
            this.allowServiceCancel = PayStatusEnum.PAY_YES.value().equals(payStatus)
                    && OrderStatusEnum.PAID_OFF.value().equals(orderStatus.value())
                    && (ServiceStatusEnum.NOT_APPLY.name().equals(serviceStatus)
                    || ServiceStatusEnum.EXPIRED.name().equals(serviceStatus));

        }

        //是否允许查看物流信息 = 当物流单号不为空并且物流公司不为空
        this.allowCheckExpress = order.getLogiId() != null && !order.getLogiId().equals(0) && !StringUtil.isEmpty(order.getShipNo());
        //不是已取消并且不是出库失败
        boolean flag = !OrderStatusEnum.INTODB_ERROR.equals(orderStatus) && !OrderStatusEnum.CANCELLED.equals(orderStatus);
        //是否允许更改收货人信息 = 发货状态未发货
        this.allowEditConsignee = flag && ShipStatusEnum.SHIP_NO.value().equals(order.getShipStatus());
        //是否允许更改价格 = （在线支付 && 未付款）||（货到付款 && 未发货）
        this.allowEditPrice = flag && (PaymentTypeEnum.ONLINE.value().equals(order.getPaymentType())
                && PayStatusEnum.PAY_NO.value().equals(order.getPayStatus()))
                || (PaymentTypeEnum.COD.value().equals(order.getPaymentType())
                && ShipStatusEnum.SHIP_NO.value().equals(order.getShipStatus()));


    }


}
