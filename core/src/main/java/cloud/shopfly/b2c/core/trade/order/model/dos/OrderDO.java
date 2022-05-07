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
package cloud.shopfly.b2c.core.trade.order.model.dos;

import cloud.shopfly.b2c.core.trade.order.model.dto.OrderDTO;
import cloud.shopfly.b2c.core.trade.order.model.enums.*;
import cloud.shopfly.b2c.core.trade.order.model.vo.ConsigneeVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PriceDetailVO;
import cloud.shopfly.b2c.core.trade.order.model.enums.*;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Order form entity
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-10 16:13:37
 */
@Table(name = "es_order")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDO implements Serializable {

    private static final long serialVersionUID = 3154292647603519L;

    /**
     * A primary keyID
     */
    @Id(name = "order_id")
    @ApiModelProperty(hidden = true)
    private Integer orderId;

    /**
     * Transaction number
     */
    @Column(name = "trade_sn")
    @ApiModelProperty(name = "trade_sn", value = "Transaction number", required = false)
    private String tradeSn;
    /**
     * Order no.
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "Order no.", required = false)
    private String sn;
    /**
     * The storeID
     */
    @Column(name = "seller_id")
    @ApiModelProperty(name = "seller_id", value = "The storeID", required = false)
    private Integer sellerId;
    /**
     * Shop name
     */
    @Column(name = "seller_name")
    @ApiModelProperty(name = "seller_name", value = "Shop name", required = false)
    private String sellerName;
    /**
     * membersID
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "membersID", required = false)
    private Integer memberId;
    /**
     * Buyers account
     */
    @Column(name = "member_name")
    @ApiModelProperty(name = "member_name", value = "Buyers account", required = false)
    private String memberName;
    /**
     * Status
     */
    @Column(name = "order_status")
    @ApiModelProperty(name = "order_status", value = "Status", required = false)
    private String orderStatus;
    /**
     * Payment status
     */
    @Column(name = "pay_status")
    @ApiModelProperty(name = "pay_status", value = "Payment status", required = false)
    private String payStatus;
    /**
     * Cargo status
     */
    @Column(name = "ship_status")
    @ApiModelProperty(name = "ship_status", value = "Cargo status", required = false)
    private String shipStatus;
    /**
     * Shipping typeID
     */
    @Column(name = "shipping_id")
    @ApiModelProperty(name = "shipping_id", value = "Shipping typeID", required = false)
    private Integer shippingId;
    /**
     * Whether the comment is complete
     */
    @Column(name = "comment_status")
    @ApiModelProperty(name = "comment_status", value = "Whether the comment is complete", required = false)
    private String commentStatus;
    /**
     * Shipping type
     */
    @Column(name = "shipping_type")
    @ApiModelProperty(name = "shipping_type", value = "Shipping type", required = false)
    private String shippingType;
    /**
     * Method of paymentid
     */
    @Column(name = "payment_method_id")
    @ApiModelProperty(name = "payment_method_id", value = "Method of paymentid", required = false)
    private String paymentMethodId;
    /**
     * Pay the plug-inid
     */
    @Column(name = "payment_plugin_id")
    @ApiModelProperty(name = "payment_plugin_id", value = "Pay the plug-inid", required = false)
    private String paymentPluginId;
    /**
     * Name of Payment Method
     */
    @Column(name = "payment_method_name")
    @ApiModelProperty(name = "payment_method_name", value = "Name of Payment Method", required = false)
    private String paymentMethodName;
    /**
     * Type of payment
     */
    @Column(name = "payment_type")
    @ApiModelProperty(name = "payment_type", value = "Type of payment", required = false)
    private String paymentType;
    /**
     * Payment time
     */
    @Column(name = "payment_time")
    @ApiModelProperty(name = "payment_time", value = "Payment time", required = false)
    private Long paymentTime;
    /**
     * Amount paid
     */
    @Column(name = "pay_money")
    @ApiModelProperty(name = "pay_money", value = "Amount paid", required = false)
    private Double payMoney;
    /**
     * Name of consignee
     */
    @Column(name = "ship_name")
    @ApiModelProperty(name = "ship_name", value = "Name of consignee", required = false)
    private String shipName;
    /**
     * Shipping address
     */
    @Column(name = "ship_addr")
    @ApiModelProperty(name = "ship_addr", value = "Shipping address", required = false)
    private String shipAddr;
    /**
     * Consignees zip code
     */
    @Column(name = "ship_zip")
    @ApiModelProperty(name = "ship_zip", value = "Consignees zip code", required = false)
    private String shipZip;
    /**
     * Consignees mobile phone
     */
    @Column(name = "ship_mobile")
    @ApiModelProperty(name = "ship_mobile", value = "Consignees mobile phone", required = false)
    private String shipMobile;
    /**
     * The goods time
     */
    @Column(name = "receive_time")
    @ApiModelProperty(name = "receive_time", value = "The goods time", required = false)
    private String receiveTime;
    /**
     *  -countries
     */
    @Column(name = "ship_country")
    @ApiModelProperty(name = "ship_country", value = " -countries", required = false)
    private String shipCountry;
    /**
     *  -state/province
     */
    @Column(name = "ship_state")
    @ApiModelProperty(name = "ship_state", value = " -state/province", required = false)
    private String shipState;
    /**
     *  -city
     */
    @Column(name = "ship_city")
    @ApiModelProperty(name = "ship_city", value = " -city", required = false)
    private String shipCity;
    /**
     *  -Country code
     */
    @Column(name = "ship_country_code")
    @ApiModelProperty(name = "ship_country_code", value = " -Country code", required = false)
    private String shipCountryCode;
    /**
     *  -state/Province code
     */
    @Column(name = "ship_state_code")
    @ApiModelProperty(name = "ship_state_code", value = " -state/Province code", required = false)
    private String shipStateCode;
    /**
     * The total order
     */
    @Column(name = "order_price")
    @ApiModelProperty(name = "order_price", value = "The total order", required = false)
    private Double orderPrice;
    /**
     * The total amount of goods
     */
    @Column(name = "goods_price")
    @ApiModelProperty(name = "goods_price", value = "The total amount of goods", required = false)
    private Double goodsPrice;
    /**
     * Distribution costs
     */
    @Column(name = "shipping_price")
    @ApiModelProperty(name = "shipping_price", value = "Distribution costs", required = false)
    private Double shippingPrice;
    /**
     * Discount amount
     */
    @Column(name = "discount_price")
    @ApiModelProperty(name = "discount_price", value = "Discount amount", required = false)
    private Double discountPrice;
    /**
     * Deleted or not
     */
    @Column(name = "disabled")
    @ApiModelProperty(name = "disabled", value = "Deleted or not", required = false)
    private Integer disabled;
    /**
     * Total weight of goods ordered
     */
    @Column(name = "weight")
    @ApiModelProperty(name = "weight", value = "Total weight of goods ordered", required = false)
    private Double weight;
    /**
     * The number
     */
    @Column(name = "goods_num")
    @ApiModelProperty(name = "goods_num", value = "The number", required = false)
    private Integer goodsNum;
    /**
     * The order note
     */
    @Column(name = "remark")
    @ApiModelProperty(name = "remark", value = "The order note", required = false)
    private String remark;
    /**
     * Reason for Order Cancellation
     */
    @Column(name = "cancel_reason")
    @ApiModelProperty(name = "cancel_reason", value = "Reason for Order Cancellation", required = false)
    private String cancelReason;
    /**
     * Sign for people
     */
    @Column(name = "the_sign")
    @ApiModelProperty(name = "the_sign", value = "Sign for people", required = false)
    private String theSign;

    /**
     * conversionList<OrderSkuVO>
     */
    @Column(name = "items_json")
    @ApiModelProperty(name = "items_json", value = "List of goodsjson", required = false)
    private String itemsJson;

    @Column(name = "warehouse_id")
    @ApiModelProperty(name = "warehouse_id", value = "The delivery warehouseID", required = false)
    private Integer warehouseId;

    @Column(name = "need_pay_money")
    @ApiModelProperty(name = "need_pay_money", value = "Amount payable", required = false)
    private Double needPayMoney;

    @Column(name = "ship_no")
    @ApiModelProperty(name = "ship_no", value = "Invoice no.", required = false)
    private String shipNo;

    /**
     * Shipping addressID
     */
    @Column(name = "address_id")
    @ApiModelProperty(name = "address_id", value = "Shipping addressID", required = false)
    private Integer addressId;
    /**
     * Administrator Remarks
     */
    @Column(name = "admin_remark")
    @ApiModelProperty(name = "admin_remark", value = "Administrator Remarks", required = false)
    private Integer adminRemark;
    /**
     * Logistics companyID
     */
    @Column(name = "logi_id")
    @ApiModelProperty(name = "logi_id", value = "Logistics companyID", required = false)
    private Integer logiId;
    /**
     * Name of logistics Company
     */
    @Column(name = "logi_name")
    @ApiModelProperty(name = "logi_name", value = "Name of logistics Company", required = false)
    private String logiName;

    /**
     * Completion time
     */
    @Column(name = "complete_time")
    @ApiModelProperty(name = "complete_time", value = "Completion time", required = false)
    private Long completeTime;
    /**
     * Order Creation time
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "Order Creation time", required = false)
    private Long createTime;

    /**
     * To sign for the time
     */
    @Column(name = "signing_time")
    @ApiModelProperty(name = "signing_time", value = "To sign for the time", required = false)
    private Long signingTime;

    /**
     * Delivery time
     */
    @Column(name = "ship_time")
    @ApiModelProperty(name = "ship_time", value = "Delivery time", required = false)
    private Long shipTime;

    /**
     * Payment method returns the transaction number
     */
    @Column(name = "pay_order_no")
    @ApiModelProperty(name = "pay_order_no", value = "Payment method returns the transaction number", required = false)
    private String payOrderNo;

    /**
     * After state
     */
    @Column(name = "service_status")
    @ApiModelProperty(name = "service_status", value = "After state", required = false)
    private String serviceStatus;

    /**
     * Settlement status
     */
    @Column(name = "bill_status")
    @ApiModelProperty(name = "bill_status", value = "Settlement status", required = false)
    private Integer billStatus;

    /**
     * Statement no.
     */
    @Column(name = "bill_sn")
    @ApiModelProperty(name = "bill_sn", value = "Statement no.", required = false)
    private String billSn;

    /**
     * Source of the order
     */
    @Column(name = "client_type")
    @ApiModelProperty(name = "client_type", value = "Source of the order", required = false)
    private String clientType;

    @Column(name = "need_receipt")
    @ApiModelProperty(name = "need_receipt", value = "Do you need an invoice?,0：No,1：is")
    private Integer needReceipt;

    /**
     * @see OrderTypeEnum
     * Added order type field due to added group servicekingapex 2019/1/28 on v7.1.0
     */
    @ApiModelProperty(value = "Order type")
    @Column(name = "order_type")
    private String orderType;


    /**
     * Extended data for orders
     * To increase the scalability of orders, personalized business can personalize data（Such as the number of people missing from the group）This field existskingapex 2019/1/28 on v7.1.0
     */
    @ApiModelProperty(value = "Extension data", hidden = true)
    @Column(name = "order_data")
    private String orderData;


    @PrimaryKeyField
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
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

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentPluginId() {
        return paymentPluginId;
    }

    public void setPaymentPluginId(String paymentPluginId) {
        this.paymentPluginId = paymentPluginId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Long getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Long paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipAddr() {
        return shipAddr;
    }

    public void setShipAddr(String shipAddr) {
        this.shipAddr = shipAddr;
    }

    public String getShipZip() {
        return shipZip;
    }

    public void setShipZip(String shipZip) {
        this.shipZip = shipZip;
    }

    public String getShipMobile() {
        return shipMobile;
    }

    public void setShipMobile(String shipMobile) {
        this.shipMobile = shipMobile;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public Double getOrderPrice() {
        if (orderPrice == null) {
            orderPrice = 0.0d;
        }
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Double getGoodsPrice() {
        if (goodsPrice == null) {
            goodsPrice = 0.0d;
        }
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Double getShippingPrice() {
        if (shippingPrice == null) {
            shippingPrice = 0.0d;
        }
        return shippingPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Double getDiscountPrice() {
        if (discountPrice == null) {
            discountPrice = 0.0d;
        }
        return discountPrice;
    }

    public String getOrderData() {
        return orderData;
    }

    public void setOrderData(String orderData) {
        this.orderData = orderData;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getTheSign() {
        return theSign;
    }

    public void setTheSign(String theSign) {
        this.theSign = theSign;
    }

    public String getItemsJson() {
        return itemsJson;
    }

    public void setItemsJson(String itemsJson) {
        this.itemsJson = itemsJson;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Double getNeedPayMoney() {
        return needPayMoney;
    }

    public void setNeedPayMoney(Double needPayMoney) {
        this.needPayMoney = needPayMoney;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getAdminRemark() {
        return adminRemark;
    }

    public void setAdminRemark(Integer adminRemark) {
        this.adminRemark = adminRemark;
    }

    public Integer getLogiId() {
        return logiId;
    }

    public void setLogiId(Integer logiId) {
        this.logiId = logiId;
    }

    public String getLogiName() {
        return logiName;
    }

    public void setLogiName(String logiName) {
        this.logiName = logiName;
    }

    public Long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Long completeTime) {
        this.completeTime = completeTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getSigningTime() {
        return signingTime;
    }

    public void setSigningTime(Long signingTime) {
        this.signingTime = signingTime;
    }

    public Long getShipTime() {
        return shipTime;
    }

    public void setShipTime(Long shipTime) {
        this.shipTime = shipTime;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillSn() {
        return billSn;
    }

    public void setBillSn(String billSn) {
        this.billSn = billSn;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getNeedReceipt() {
        if (needReceipt == null) {
            needReceipt = 0;
        }
        return needReceipt;
    }

    public void setNeedReceipt(Integer needReceipt) {
        this.needReceipt = needReceipt;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public String getShipState() {
        return shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    public String getShipCountryCode() {
        return shipCountryCode;
    }

    public void setShipCountryCode(String shipCountryCode) {
        this.shipCountryCode = shipCountryCode;
    }

    public String getShipStateCode() {
        return shipStateCode;
    }

    public void setShipStateCode(String shipStateCode) {
        this.shipStateCode = shipStateCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderDO orderDO = (OrderDO) o;

        return new EqualsBuilder()
                .append(orderId, orderDO.orderId)
                .append(tradeSn, orderDO.tradeSn)
                .append(sn, orderDO.sn)
                .append(sellerId, orderDO.sellerId)
                .append(sellerName, orderDO.sellerName)
                .append(memberId, orderDO.memberId)
                .append(memberName, orderDO.memberName)
                .append(orderStatus, orderDO.orderStatus)
                .append(payStatus, orderDO.payStatus)
                .append(shipStatus, orderDO.shipStatus)
                .append(shippingId, orderDO.shippingId)
                .append(commentStatus, orderDO.commentStatus)
                .append(shippingType, orderDO.shippingType)
                .append(paymentMethodId, orderDO.paymentMethodId)
                .append(paymentPluginId, orderDO.paymentPluginId)
                .append(paymentMethodName, orderDO.paymentMethodName)
                .append(paymentType, orderDO.paymentType)
                .append(paymentTime, orderDO.paymentTime)
                .append(payMoney, orderDO.payMoney)
                .append(shipName, orderDO.shipName)
                .append(shipAddr, orderDO.shipAddr)
                .append(shipZip, orderDO.shipZip)
                .append(shipMobile, orderDO.shipMobile)
                .append(receiveTime, orderDO.receiveTime)
                .append(shipCity, orderDO.shipCity)
                .append(orderPrice, orderDO.orderPrice)
                .append(goodsPrice, orderDO.goodsPrice)
                .append(shippingPrice, orderDO.shippingPrice)
                .append(discountPrice, orderDO.discountPrice)
                .append(disabled, orderDO.disabled)
                .append(weight, orderDO.weight)
                .append(goodsNum, orderDO.goodsNum)
                .append(remark, orderDO.remark)
                .append(cancelReason, orderDO.cancelReason)
                .append(theSign, orderDO.theSign)
                .append(itemsJson, orderDO.itemsJson)
                .append(warehouseId, orderDO.warehouseId)
                .append(needPayMoney, orderDO.needPayMoney)
                .append(shipNo, orderDO.shipNo)
                .append(addressId, orderDO.addressId)
                .append(adminRemark, orderDO.adminRemark)
                .append(logiId, orderDO.logiId)
                .append(logiName, orderDO.logiName)
                .append(completeTime, orderDO.completeTime)
                .append(createTime, orderDO.createTime)
                .append(signingTime, orderDO.signingTime)
                .append(shipTime, orderDO.shipTime)
                .append(payOrderNo, orderDO.payOrderNo)
                .append(serviceStatus, orderDO.serviceStatus)
                .append(billStatus, orderDO.billStatus)
                .append(billSn, orderDO.billSn)
                .append(clientType, orderDO.clientType)
                .append(needReceipt, orderDO.needReceipt)
                .append(orderType, orderDO.orderType)
                .append(shipCountry, orderDO.getShipCountry())
                .append(shipState, orderDO.getShipState())
                .append(shipCountryCode, orderDO.getShipCountryCode())
                .append(shipStateCode, orderDO.getShipStateCode())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(orderId)
                .append(tradeSn)
                .append(sn)
                .append(sellerId)
                .append(sellerName)
                .append(memberId)
                .append(memberName)
                .append(orderStatus)
                .append(payStatus)
                .append(shipStatus)
                .append(shippingId)
                .append(commentStatus)
                .append(shippingType)
                .append(paymentMethodId)
                .append(paymentPluginId)
                .append(paymentMethodName)
                .append(paymentType)
                .append(paymentTime)
                .append(payMoney)
                .append(shipName)
                .append(shipAddr)
                .append(shipZip)
                .append(shipMobile)
                .append(receiveTime)
                .append(shipCity)
                .append(orderPrice)
                .append(goodsPrice)
                .append(shippingPrice)
                .append(discountPrice)
                .append(disabled)
                .append(weight)
                .append(goodsNum)
                .append(remark)
                .append(cancelReason)
                .append(theSign)
                .append(itemsJson)
                .append(warehouseId)
                .append(needPayMoney)
                .append(shipNo)
                .append(addressId)
                .append(adminRemark)
                .append(logiId)
                .append(logiName)
                .append(completeTime)
                .append(createTime)
                .append(signingTime)
                .append(shipTime)
                .append(payOrderNo)
                .append(serviceStatus)
                .append(billStatus)
                .append(billSn)
                .append(clientType)
                .append(needReceipt)
                .append(orderType)
                .append(shipCountry)
                .append(shipState)
                .append(shipCountryCode)
                .append(shipStateCode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OrderDO{" +
                "orderId=" + orderId +
                ", tradeSn='" + tradeSn + '\'' +
                ", sn='" + sn + '\'' +
                ", sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", shipStatus='" + shipStatus + '\'' +
                ", shippingId=" + shippingId +
                ", commentStatus='" + commentStatus + '\'' +
                ", shippingType='" + shippingType + '\'' +
                ", paymentMethodId='" + paymentMethodId + '\'' +
                ", paymentPluginId='" + paymentPluginId + '\'' +
                ", paymentMethodName='" + paymentMethodName + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", paymentTime=" + paymentTime +
                ", payMoney=" + payMoney +
                ", shipName='" + shipName + '\'' +
                ", shipAddr='" + shipAddr + '\'' +
                ", shipZip='" + shipZip + '\'' +
                ", shipMobile='" + shipMobile + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", shipCountry='" + shipCountry + '\'' +
                ", shipState='" + shipState + '\'' +
                ", shipCity='" + shipCity + '\'' +
                ", shipCountryCode='" + shipCountryCode + '\'' +
                ", shipStateCode='" + shipStateCode + '\'' +
                ", orderPrice=" + orderPrice +
                ", goodsPrice=" + goodsPrice +
                ", shippingPrice=" + shippingPrice +
                ", discountPrice=" + discountPrice +
                ", disabled=" + disabled +
                ", weight=" + weight +
                ", goodsNum=" + goodsNum +
                ", remark='" + remark + '\'' +
                ", cancelReason='" + cancelReason + '\'' +
                ", theSign='" + theSign + '\'' +
                ", itemsJson='" + itemsJson + '\'' +
                ", warehouseId=" + warehouseId +
                ", needPayMoney=" + needPayMoney +
                ", shipNo='" + shipNo + '\'' +
                ", addressId=" + addressId +
                ", adminRemark=" + adminRemark +
                ", logiId=" + logiId +
                ", logiName='" + logiName + '\'' +
                ", completeTime=" + completeTime +
                ", createTime=" + createTime +
                ", signingTime=" + signingTime +
                ", shipTime=" + shipTime +
                ", payOrderNo='" + payOrderNo + '\'' +
                ", serviceStatus='" + serviceStatus + '\'' +
                ", billStatus=" + billStatus +
                ", billSn='" + billSn + '\'' +
                ", clientType='" + clientType + '\'' +
                ", needReceipt=" + needReceipt +
                ", orderType='" + orderType + '\'' +
                ", orderData='" + orderData + '\'' +
                '}';
    }

    public OrderDO() {

    }

    /**
     * Constructor with initialization parameters
     *
     * @param orderDTO
     */
    public OrderDO(OrderDTO orderDTO) {

        this.receiveTime = orderDTO.getReceiveTime();
        this.sn = orderDTO.getSn();
        this.shipTime = orderDTO.getShipTime();
        this.paymentType = orderDTO.getPaymentType();

        // The consignee
        ConsigneeVO consignee = orderDTO.getConsignee();
        this.shipName = consignee.getName();
        this.shipAddr = consignee.getAddress();
        this.addressId = consignee.getConsigneeId();
        this.shipMobile = consignee.getMobile();
        this.shipCountry = consignee.getCountry();
        this.shipState = consignee.getStateName();
        this.shipCity = consignee.getCity();
        this.shipCountryCode = consignee.getCountryCode();
        this.shipStateCode = consignee.getStateCode();
        this.shipZip = consignee.getZipCode();


        // The price
        PriceDetailVO priceDetail = orderDTO.getPrice();
        this.orderPrice = priceDetail.getTotalPrice();
        this.goodsPrice = priceDetail.getGoodsPrice();
        this.shippingPrice = priceDetail.getFreightPrice();
        this.discountPrice = priceDetail.getDiscountPrice();
        this.needPayMoney = priceDetail.getTotalPrice();
        this.weight = orderDTO.getWeight();
        this.shippingId = orderDTO.getShippingId();

        // The seller
        this.sellerId = orderDTO.getSellerId();
        this.sellerName = orderDTO.getSellerName();

        // buyers
        this.memberId = orderDTO.getMemberId();
        this.memberName = orderDTO.getMemberName();

        // Creation time
        this.createTime = orderDTO.getCreateTime();

        // Initialization state
        this.shipStatus = ShipStatusEnum.SHIP_NO.value();
        this.orderStatus = OrderStatusEnum.NEW.value();
        this.payStatus = PayStatusEnum.PAY_NO.value();
        this.commentStatus = CommentStatusEnum.UNFINISHED.value();
        this.serviceStatus = ServiceStatusEnum.NOT_APPLY.value();
        this.disabled = 0;

        List<OrderSkuVO> orderSkuVOList = new ArrayList<>();
        for (CartSkuVO cartSkuVO : orderDTO.getSkuList()) {
            OrderSkuVO orderSkuVO = new OrderSkuVO();
            BeanUtils.copyProperties(cartSkuVO, orderSkuVO);
            orderSkuVOList.add(orderSkuVO);
        }

        // Product list
        this.itemsJson = JsonUtil.objectToJson(orderSkuVOList);

        // note
        this.remark = orderDTO.getRemark();

        // invoice
        this.needReceipt = orderDTO.getNeedReceipt();

        // Source of the order
        this.clientType = orderDTO.getClientType();
        this.orderType = orderDTO.getOrderType();

    }

}
