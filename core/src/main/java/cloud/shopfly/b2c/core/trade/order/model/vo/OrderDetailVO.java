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

import cloud.shopfly.b2c.core.member.model.dos.ReceiptHistory;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CouponVO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PayStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.ServiceStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.ShipStatusEnum;
import cloud.shopfly.b2c.core.trade.order.support.AbstractOrderSpecialStatus;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;

/**
 * The order details
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel(description = "The order details")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDetailVO extends OrderDO {

    @ApiModelProperty(value = "Order operation permit status")
    private OrderOperateAllowable orderOperateAllowableVO;

    @ApiModelProperty(value = "Order status text")
    private String orderStatusText;

    @ApiModelProperty(value = "Payment status text")
    private String payStatusText;

    @ApiModelProperty(value = "Delivery status text")
    private String shipStatusText;

    @ApiModelProperty(value = "Post sale status text")
    private String serviceStatusText;

    @ApiModelProperty(value = "Method of payment")
    private String paymentName;

    @ApiModelProperty(value = "skuThe list of")
    private List<OrderSkuVO> orderSkuList;

    @ApiModelProperty(value = "The invoice information")
    private ReceiptHistory receiptHistory;

    @ApiModelProperty(value = "Order list of freebies")
    private List<FullDiscountGiftDO> giftList;


    @ApiModelProperty(value = "Cashback amount")
    private Double cashBack;

    @ApiModelProperty(value = "Coupon deduction amount")
    private Double couponPrice;

    @ApiModelProperty(value = "Bonus points")
    private Integer giftPoint;

    @ApiModelProperty(value = "Complimentary coupons")
    private CouponVO giftCoupon;


    @ApiModelProperty(value = "Credits used for this order")
    private Integer usePoint;


    @ApiModelProperty(value = "Full amount reduction")
    private Double fullMinus;

    /**
     * Group order status
     */
    @ApiModelProperty(value = "Group order status")
    private String pingTuanStatus;

    @ApiModelProperty(name = "pay_order_no", value = "Payment method returns the transaction number")
    private String payOrderNo;


    public ReceiptHistory getReceiptHistory() {
        return receiptHistory;
    }

    public void setReceiptHistory(ReceiptHistory receiptHistory) {
        this.receiptHistory = receiptHistory;
    }

    public OrderOperateAllowable getOrderOperateAllowableVO() {
        return orderOperateAllowableVO;
    }

    public void setOrderOperateAllowableVO(OrderOperateAllowable orderOperateAllowableVO) {
        this.orderOperateAllowableVO = orderOperateAllowableVO;
    }

    public String getPingTuanStatus() {
//        pingTuanStatus = "";
//        //The paid group order is in the status of pending group
//        if (OrderTypeEnum.pintuan.name().equals(this.getOrderType())) {
//            if (this.getPayStatus().equals(PayStatusEnum.PAY_NO.value())) {
//                if(OrderStatusEnum.CANCELLED.value().equals(this.getOrderStatus())){
//                    pingTuanStatus = "No clouds";
//                }else{
//                    pingTuanStatus = "To stay together";
//                }
//            } else if (OrderStatusEnum.PAID_OFF.value().equals(this.getOrderStatus())) {
//                pingTuanStatus = "To stay together";
//            } else {
//                pingTuanStatus = "Have to make";
//            }
//
//        }

        return pingTuanStatus;
    }

    public void setPingTuanStatus(String pingTuanStatus) {
        this.pingTuanStatus = pingTuanStatus;
    }

    public String getOrderStatusText() {

        // It is read from the special process-state display definition first. If it is empty, it indicates that it is not a special state and directly displays the prompt corresponding to the state
        orderStatusText = AbstractOrderSpecialStatus.getStatusText(getOrderType(), getPaymentType(), getOrderStatus());
        if (StringUtil.isEmpty(orderStatusText)) {
            orderStatusText = OrderStatusEnum.valueOf(getOrderStatus()).description();
        }

        return orderStatusText;
    }

    public void setOrderStatusText(String orderStatusText) {
        this.orderStatusText = orderStatusText;
    }

    public String getPayStatusText() {
        if (this.getPayStatus() != null) {
            PayStatusEnum payStatusEnum = PayStatusEnum.valueOf(this.getPayStatus());
            payStatusText = payStatusEnum.description();
        }
        return payStatusText;
    }

    public void setPayStatusText(String payStatusText) {
        this.payStatusText = payStatusText;
    }

    public String getShipStatusText() {
        if (this.getShipStatus() != null) {
            ShipStatusEnum shipStatusEnum = ShipStatusEnum.valueOf(this.getShipStatus());
            shipStatusText = shipStatusEnum.description();
        }
        return shipStatusText;
    }

    public void setShipStatusText(String shipStatusText) {
        this.shipStatusText = shipStatusText;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }


    public List<OrderSkuVO> getOrderSkuList() {
        return orderSkuList;
    }

    public void setOrderSkuList(List<OrderSkuVO> orderSkuList) {
        this.orderSkuList = orderSkuList;
    }

    public String getServiceStatusText() {
        if (this.getServiceStatus() != null) {
            ServiceStatusEnum serviceStatusEnum = ServiceStatusEnum.valueOf(this.getServiceStatus());
            serviceStatusText = serviceStatusEnum.description();
        }
        return serviceStatusText;
    }

    public void setServiceStatusText(String serviceStatusText) {
        this.serviceStatusText = serviceStatusText;
    }

    public List<FullDiscountGiftDO> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<FullDiscountGiftDO> giftList) {
        this.giftList = giftList;
    }

    public Double getCashBack() {
        return cashBack;
    }

    public void setCashBack(Double cashBack) {
        this.cashBack = cashBack;
    }

    public Double getCouponPrice() {
        if (couponPrice == null) {
            return 0D;
        }
        return couponPrice;
    }

    public void setCouponPrice(Double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Integer getGiftPoint() {
        return giftPoint;
    }

    public void setGiftPoint(Integer giftPoint) {
        this.giftPoint = giftPoint;
    }

    public Integer getUsePoint() {
        return usePoint;
    }

    public void setUsePoint(Integer usePoint) {
        this.usePoint = usePoint;
    }

    public CouponVO getGiftCoupon() {
        return giftCoupon;
    }

    public void setGiftCoupon(CouponVO giftCoupon) {
        this.giftCoupon = giftCoupon;
    }

    public Double getFullMinus() {
        if (fullMinus == null) {
            return 0D;
        }
        return fullMinus;
    }

    public void setFullMinus(Double fullMinus) {
        this.fullMinus = fullMinus;
    }

    @Override
    public String getPayOrderNo() {
        return payOrderNo;
    }

    @Override
    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    @Override
    public Double getGoodsPrice() {

        Double goodsPrice = super.getGoodsPrice();
        // The 7.0.x database lacks the field of commodity original price. Dynamic calculation method is used to calculate the commodity original price
        goodsPrice = CurrencyUtil.add(goodsPrice, getDiscountPrice());

        return goodsPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderDetailVO that = (OrderDetailVO) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(orderOperateAllowableVO, that.orderOperateAllowableVO)
                .append(orderStatusText, that.orderStatusText)
                .append(payStatusText, that.payStatusText)
                .append(shipStatusText, that.shipStatusText)
                .append(serviceStatusText, that.serviceStatusText)
                .append(paymentName, that.paymentName)
                .append(orderSkuList, that.orderSkuList)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(orderOperateAllowableVO)
                .append(orderStatusText)
                .append(payStatusText)
                .append(shipStatusText)
                .append(serviceStatusText)
                .append(paymentName)
                .append(orderSkuList)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OrderDetailVO{" +
                "orderOperateAllowableVO=" + orderOperateAllowableVO +
                ", orderStatusText='" + orderStatusText + '\'' +
                ", payStatusText='" + payStatusText + '\'' +
                ", shipStatusText='" + shipStatusText + '\'' +
                ", serviceStatusText='" + serviceStatusText + '\'' +
                ", paymentName='" + paymentName + '\'' +
                ", orderSkuList=" + orderSkuList +
                '}';
    }


}
