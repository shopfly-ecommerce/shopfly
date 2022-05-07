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
import com.alipay.api.domain.OrderItem;
import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.support.AbstractOrderSpecialStatus;
import cloud.shopfly.b2c.core.trade.order.model.enums.*;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Order list item
 *
 * @author Snow create in 2018/5/10
 * @version v2.0
 * @since v7.0.0
 */
public class OrderLineVO implements Serializable {

    @ApiModelProperty(value = "The orderid")
    private Integer orderId;

    @ApiModelProperty(value = "Order no.")
    private String sn;

    @ApiModelProperty(value = "The seller name")
    private Integer sellerId;

    @ApiModelProperty(value = "The seller name")
    private String sellerName;

    @ApiModelProperty(value = "Shipping type")
    private String shippingType;

    @ApiModelProperty(value = "Method of payment")
    private String paymentName;

    @ApiModelProperty(value = "Order status text")
    private String orderStatusText;

    @ApiModelProperty(value = "Payment status text")
    private String payStatusText;

    @ApiModelProperty(value = "Freight status text")
    private String shipStatusText;

    @ApiModelProperty(value = "Order status value")
    private String orderStatus;

    @ApiModelProperty(value = "Payment status value")
    private String payStatus;

    @ApiModelProperty(value = "Freight status value")
    private String shipStatus;

    @ApiModelProperty(value = "Review status")
    private String commentStatus;

    @ApiModelProperty(value = "Order operation permit status")
    private OrderOperateAllowable orderOperateAllowableVO;

    @ApiModelProperty(value = "Payment type")
    private String paymentType;

    @ApiModelProperty(value = "Name of consignee")
    private String shipName;

    @ApiModelProperty(value = "Last update")
    private Long createTime;

    @ApiModelProperty(value = "Actual delivery time")
    private Long shipTime;

    @ApiModelProperty(value = "Total")
    private Double orderAmount;

    @ApiModelProperty(value = "freight")
    private Double shippingAmount;

    @ApiModelProperty(value = "After state")
    private String serviceStatus;

    @ApiModelProperty(value = "Product list")
    private List<OrderSkuVO> skuList;

    @ApiModelProperty(value = "Coupon list")
    private List<CouponDO> couponList;

    @ApiModelProperty(value = "Gift list")
    private List<FullDiscountGiftDO> giftList;

    @ApiModelProperty(value = "Order item is not a database field")
    private List<OrderItem> itemList;

    @ApiModelProperty(value = "Source of the order")
    private String clientType;

    @ApiModelProperty(value = "membersID")
    private Integer memberId;

    @ApiModelProperty(value = "Members nickname")
    private String memberName;

    @ApiModelProperty(value = "This product needs to be prompted to the customers discount label")
    private List<String> promotionTags;


    @ApiModelProperty(value = "Automatically cancels the remaining seconds, or if it has already timed out0")
    private Long cancelLeftTime;

    /**
     * Whether the order can be returned in the original way
     * The outstanding order isfalse
     */
    @ApiModelProperty(name = "is_retrace", value = "Whether the order can be returned in the original way")
    private Boolean isRetrace;

    public Long getCancelLeftTime() {
        return cancelLeftTime;
    }

    public void setCancelLeftTime(Long cancelLeftTime) {
        this.cancelLeftTime = cancelLeftTime;
    }

    /**
     * @see OrderTypeEnum
     * Added order type field due to added group servicekingapex 2019/1/28
     */
    @ApiModelProperty(value = "Order type")
    private String orderType;

    /**
     * Added order type field due to added group servicekingapex 2019/1/28
     */
    @ApiModelProperty(value = "Were a few people short of a group. If0Should not display")
    private int waitingGroupNums;

    /**
     * Group order status
     */
    @ApiModelProperty(value = "Group order status")
    private String pingTuanStatus;


    public List<String> getPromotionTags() {
        return promotionTags;
    }

    public void setPromotionTags(List<String> promotionTags) {
        this.promotionTags = promotionTags;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getOrderStatusText() {
        return orderStatusText;
    }

    public void setOrderStatusText(String orderStatusText) {
        this.orderStatusText = orderStatusText;
    }

    public String getPayStatusText() {
        return payStatusText;
    }

    public void setPayStatusText(String payStatusText) {
        this.payStatusText = payStatusText;
    }

    public String getShipStatusText() {
        return shipStatusText;
    }

    public void setShipStatusText(String shipStatusText) {
        this.shipStatusText = shipStatusText;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public OrderOperateAllowable getOrderOperateAllowableVO() {
        return orderOperateAllowableVO;
    }

    public void setOrderOperateAllowableVO(OrderOperateAllowable orderOperateAllowableVO) {
        this.orderOperateAllowableVO = orderOperateAllowableVO;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getShipTime() {
        return shipTime;
    }

    public void setShipTime(Long shipTime) {
        this.shipTime = shipTime;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Double getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(Double shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public List<OrderSkuVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<OrderSkuVO> skuList) {
        this.skuList = skuList;
    }

    public List<CouponDO> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponDO> couponList) {
        this.couponList = couponList;
    }

    public List<FullDiscountGiftDO> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<FullDiscountGiftDO> giftList) {
        this.giftList = giftList;
    }

    public List<OrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderItem> itemList) {
        this.itemList = itemList;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getWaitingGroupNums() {
        return waitingGroupNums;
    }

    public void setWaitingGroupNums(int waitingGroupNums) {
        this.waitingGroupNums = waitingGroupNums;
    }

    public String getPingTuanStatus() {
        return pingTuanStatus;
    }

    public void setPingTuanStatus(String pingTuanStatus) {
        this.pingTuanStatus = pingTuanStatus;
    }

    public Boolean getIsRetrace() {
        return this.isRetrace;
    }

    public void setIsRetrace(Boolean retrace) {
        this.isRetrace = retrace;
    }

    @Override
    public String toString() {
        return "OrderLineVO{" +
                "orderId=" + orderId +
                ", sn='" + sn + '\'' +
                ", sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                ", shippingType='" + shippingType + '\'' +
                ", paymentName='" + paymentName + '\'' +
                ", orderStatusText='" + orderStatusText + '\'' +
                ", payStatusText='" + payStatusText + '\'' +
                ", shipStatusText='" + shipStatusText + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", shipStatus='" + shipStatus + '\'' +
                ", commentStatus='" + commentStatus + '\'' +
                ", orderOperateAllowableVO=" + orderOperateAllowableVO +
                ", paymentType='" + paymentType + '\'' +
                ", shipName='" + shipName + '\'' +
                ", createTime=" + createTime +
                ", shipTime=" + shipTime +
                ", orderAmount=" + orderAmount +
                ", shippingAmount=" + shippingAmount +
                ", serviceStatus='" + serviceStatus + '\'' +
                ", skuList=" + skuList +
                ", couponList=" + couponList +
                ", giftList=" + giftList +
                ", itemList=" + itemList +
                ", clientType='" + clientType + '\'' +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", promotionTags=" + promotionTags +
                ", cancelLeftTime=" + cancelLeftTime +
                ", isRetrace=" + isRetrace +
                ", orderType='" + orderType + '\'' +
                ", waitingGroupNums=" + waitingGroupNums +
                ", pingTuanStatus='" + pingTuanStatus + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineVO that = (OrderLineVO) o;
        return waitingGroupNums == that.waitingGroupNums &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(sn, that.sn) &&
                Objects.equals(sellerId, that.sellerId) &&
                Objects.equals(sellerName, that.sellerName) &&
                Objects.equals(shippingType, that.shippingType) &&
                Objects.equals(paymentName, that.paymentName) &&
                Objects.equals(orderStatusText, that.orderStatusText) &&
                Objects.equals(payStatusText, that.payStatusText) &&
                Objects.equals(shipStatusText, that.shipStatusText) &&
                Objects.equals(orderStatus, that.orderStatus) &&
                Objects.equals(payStatus, that.payStatus) &&
                Objects.equals(shipStatus, that.shipStatus) &&
                Objects.equals(commentStatus, that.commentStatus) &&
                Objects.equals(orderOperateAllowableVO, that.orderOperateAllowableVO) &&
                Objects.equals(paymentType, that.paymentType) &&
                Objects.equals(shipName, that.shipName) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(shipTime, that.shipTime) &&
                Objects.equals(orderAmount, that.orderAmount) &&
                Objects.equals(shippingAmount, that.shippingAmount) &&
                Objects.equals(serviceStatus, that.serviceStatus) &&
                Objects.equals(skuList, that.skuList) &&
                Objects.equals(couponList, that.couponList) &&
                Objects.equals(giftList, that.giftList) &&
                Objects.equals(itemList, that.itemList) &&
                Objects.equals(clientType, that.clientType) &&
                Objects.equals(memberId, that.memberId) &&
                Objects.equals(memberName, that.memberName) &&
                Objects.equals(promotionTags, that.promotionTags) &&
                Objects.equals(cancelLeftTime, that.cancelLeftTime) &&
                Objects.equals(isRetrace, that.isRetrace) &&
                Objects.equals(orderType, that.orderType) &&
                Objects.equals(pingTuanStatus, that.pingTuanStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, sn, sellerId, sellerName, shippingType, paymentName, orderStatusText, payStatusText, shipStatusText, orderStatus, payStatus, shipStatus, commentStatus, orderOperateAllowableVO, paymentType, shipName, createTime, shipTime, orderAmount, shippingAmount, serviceStatus, skuList, couponList, giftList, itemList, clientType, memberId, memberName, promotionTags, cancelLeftTime, isRetrace, orderType, waitingGroupNums, pingTuanStatus);
    }

    /**
     * An empty constructor
     */
    public OrderLineVO() {

    }


    /**
     * Parameter assignment constructor
     *
     * @param orderDO
     */
    public OrderLineVO(OrderDO orderDO) {
        this.orderId = orderDO.getOrderId();
        this.sn = orderDO.getSn();
        this.sellerId = orderDO.getSellerId();
        this.sellerName = orderDO.getSellerName();
        this.shippingType = orderDO.getShippingType();
        this.paymentName = orderDO.getPaymentMethodName();

        // It is read from the special process-state display definition first. If it is empty, it indicates that it is not a special state and directly displays the prompt corresponding to the state
        orderStatusText = AbstractOrderSpecialStatus.getStatusText(orderDO.getOrderType(), orderDO.getPaymentType(), orderDO.getOrderStatus());
        if (StringUtil.isEmpty(orderStatusText)) {
            orderStatusText = OrderStatusEnum.valueOf(orderDO.getOrderStatus()).description();
        }

        this.payStatusText = PayStatusEnum.valueOf(orderDO.getPayStatus()).description();
        this.shipStatusText = ShipStatusEnum.valueOf(orderDO.getShipStatus()).description();

        this.orderStatus = orderDO.getOrderStatus();
        this.payStatus = orderDO.getPayStatus();
        this.shipStatus = orderDO.getShipStatus();
        this.commentStatus = orderDO.getCommentStatus();
        this.serviceStatus = orderDO.getServiceStatus();

        this.shipName = orderDO.getShipName();
        this.paymentType = orderDO.getPaymentType();

        this.createTime = orderDO.getCreateTime();
        if (orderDO.getShipTime() != null) {
            this.shipTime = orderDO.getShipTime();
        }
        this.orderAmount = orderDO.getOrderPrice();
        this.shippingAmount = orderDO.getShippingPrice();
        this.paymentType = orderDO.getPaymentType();

        this.skuList = JsonUtil.jsonToList(orderDO.getItemsJson(), OrderSkuVO.class);
        this.clientType = orderDO.getClientType();

        this.memberId = orderDO.getMemberId();
        this.memberName = orderDO.getMemberName();
        this.orderType = orderDO.getOrderType();


        // Go through all the goods
        for (OrderSkuVO skuVO : skuList) {
            // Sets the operational state of the item
            skuVO.setGoodsOperateAllowableVO(new GoodsOperateAllowable(PaymentTypeEnum.valueOf(this.paymentType), OrderStatusEnum.valueOf(this.orderStatus),
                    ShipStatusEnum.valueOf(this.shipStatus), ServiceStatusEnum.valueOf(skuVO.getServiceStatus()),
                    PayStatusEnum.valueOf(this.payStatus)));
        }

        this.orderOperateAllowableVO = new OrderOperateAllowable(orderDO);
    }

}
