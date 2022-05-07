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
package cloud.shopfly.b2c.core.trade.order.model.dto;

import cloud.shopfly.b2c.core.trade.order.model.vo.ConsigneeVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.ReceiptVO;
import cloud.shopfly.b2c.core.trade.cart.model.dos.CartDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderTypeEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;

/**
 * The orderDTO
 *
 * @author kingapex
 * @version 1.0
 * @since v7.0.0 2017years3month22On the afternoon9:28:30
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
@ApiIgnore
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDTO extends CartDO implements Serializable {

    private static final long serialVersionUID = 8206833000476657708L;

    @ApiModelProperty(value = "Transaction number")
    private String tradeSn;

    @ApiModelProperty(value = "Order no.")
    private String sn;

    @ApiModelProperty(value = "Receiving information")
    private ConsigneeVO consignee;

    @ApiModelProperty(value = "Shipping type")
    private Integer shippingId;

    @ApiModelProperty(value = "Method of payment")
    private String paymentType;

    @ApiModelProperty(value = "The delivery time")
    private Long shipTime;

    @ApiModelProperty(value = "Delivery time type")
    private String receiveTime;

    @ApiModelProperty(value = "membersid")
    private Integer memberId;

    @ApiModelProperty(value = "Member name")
    private String memberName;

    @ApiModelProperty(value = "The order note")
    private String remark;

    @ApiModelProperty(value = "Last update")
    private Long createTime;


    @ApiModelProperty(value = "Name of delivery method")
    private String shippingType;

    @ApiModelProperty(value = "Status")
    private String orderStatus;

    @ApiModelProperty(value = "Payment status")
    private String payStatus;

    @ApiModelProperty(value = "Shipment status")
    private String shipStatus;

    @ApiModelProperty(value = "Name of consignee")
    private String shipName;

    @ApiModelProperty(value = "The order price")
    private Double orderPrice;

    @ApiModelProperty(value = "The shipping fee,")
    private Double shippingPrice;

    @ApiModelProperty(value = "Review status")
    private String commentStatus;

    @ApiModelProperty(value = "Has been deleted?")
    private Integer disabled;

    @ApiModelProperty(value = "Method of paymentid")
    private Integer paymentMethodId;

    @ApiModelProperty(value = "Pay the plug-inid")
    private String paymentPluginId;

    @ApiModelProperty(value = "Name of Payment Method")
    private String paymentMethodName;

    @ApiModelProperty(value = "Payment account")
    private String paymentAccount;

    @ApiModelProperty(value = "The number")
    private Integer goodsNum;

    @ApiModelProperty(value = "The delivery warehouseid")
    private Integer warehouseId;

    @ApiModelProperty(value = "Cancel the reason")
    private String cancelReason;

    @ApiModelProperty(value = "Delivery address provinceId")
    private Integer shipProvinceId;

    @ApiModelProperty(value = "Shipping address cityId")
    private Integer shipCityId;

    @ApiModelProperty(value = "Receiving address areaId")
    private Integer shipRegionId;

    @ApiModelProperty(value = "Delivery address StreetId")
    private Integer shipTownId;

    @ApiModelProperty(value = "Receiving provinces")
    private String shipProvince;

    @ApiModelProperty(value = "Shipping address city")
    private String shipCity;

    @ApiModelProperty(value = "Receiving address area")
    private String shipRegion;

    @ApiModelProperty(value = "Delivery address Street")
    private String shipTown;

    @ApiModelProperty(value = "To sign for the time")
    private Long signingTime;

    @ApiModelProperty(value = "Name of consignee")
    private String theSign;

    @ApiModelProperty(value = "Administrator Remarks")
    private String adminRemark;

    @ApiModelProperty(value = "Shipping addressid")
    private Integer addressId;

    @ApiModelProperty(value = "Amount payable")
    private Double needPayMoney;

    @ApiModelProperty(value = "Invoice no.")
    private String shipNo;

    @ApiModelProperty(value = "Logistics companyId")
    private Integer logiId;

    @ApiModelProperty(value = "Name of logistics Company")
    private String logiName;

    @ApiModelProperty(value = "Do you need an invoice?")
    private Integer needReceipt;

    @ApiModelProperty(value = "Look up")
    private String receiptTitle;

    @ApiModelProperty(value = "content")
    private String receiptContent;

    @ApiModelProperty(value = "After state")
    private String serviceStatus;

    @ApiModelProperty(value = "Source of the order")
    private String clientType;
    @ApiModelProperty(value = "The invoice information")
    private ReceiptVO receiptVO;

    /**
     * @see OrderTypeEnum
     * Added order type field due to added group servicekingapex 2019/1/28 on v7.1.0
     */
    @ApiModelProperty(value = "Order type")
    private String orderType;


    /**
     * Extended data for orders
     * To increase the scalability of orders, personalized business can personalize data（Such as the number of people missing from the group）This field existskingapex 2019/1/28 on v7.1.0
     */
    @ApiModelProperty(value = "Extension data", hidden = true)
    private String orderData;


    public void setOrderData(String orderData) {
        this.orderData = orderData;
    }

    /**
     * No parameter constructor
     */
    public OrderDTO() {

    }


    /**
     * Build orders with a shopping cart
     *
     * @param cart
     */
    public OrderDTO(CartDO cart) {

        super(cart.getSellerId(), cart.getSellerName());

        // Initialize product and offer data
        this.setWeight(cart.getWeight());
        this.setPrice(cart.getPrice());
        this.setSkuList(cart.getSkuList());
        this.setCouponList(cart.getCouponList());
        this.setGiftCouponList(cart.getGiftCouponList());
        this.setGiftList(cart.getGiftList());
        this.setGiftPoint(cart.getGiftPoint());
        this.orderType = OrderTypeEnum.normal.name();

    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "tradeSn='" + tradeSn + '\'' +
                ", sn='" + sn + '\'' +
                ", consignee=" + consignee +
                ", shippingId=" + shippingId +
                ", paymentType='" + paymentType + '\'' +
                ", shipTime=" + shipTime +
                ", receiveTime='" + receiveTime + '\'' +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", shippingType='" + shippingType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", shipStatus='" + shipStatus + '\'' +
                ", shipName='" + shipName + '\'' +
                ", orderPrice=" + orderPrice +
                ", shippingPrice=" + shippingPrice +
                ", commentStatus='" + commentStatus + '\'' +
                ", disabled=" + disabled +
                ", paymentMethodId=" + paymentMethodId +
                ", paymentPluginId='" + paymentPluginId + '\'' +
                ", paymentMethodName='" + paymentMethodName + '\'' +
                ", paymentAccount='" + paymentAccount + '\'' +
                ", goodsNum=" + goodsNum +
                ", warehouseId=" + warehouseId +
                ", cancelReason='" + cancelReason + '\'' +
                ", shipProvinceId=" + shipProvinceId +
                ", shipCityId=" + shipCityId +
                ", shipRegionId=" + shipRegionId +
                ", shipTownId=" + shipTownId +
                ", shipProvince='" + shipProvince + '\'' +
                ", shipCity='" + shipCity + '\'' +
                ", shipRegion='" + shipRegion + '\'' +
                ", shipTown='" + shipTown + '\'' +
                ", signingTime=" + signingTime +
                ", theSign='" + theSign + '\'' +
                ", adminRemark='" + adminRemark + '\'' +
                ", addressId=" + addressId +
                ", needPayMoney=" + needPayMoney +
                ", shipNo='" + shipNo + '\'' +
                ", logiId=" + logiId +
                ", logiName='" + logiName + '\'' +
                ", needReceipt=" + needReceipt +
                ", receiptTitle='" + receiptTitle + '\'' +
                ", receiptContent='" + receiptContent + '\'' +
                ", serviceStatus='" + serviceStatus + '\'' +
                ", clientType='" + clientType + '\'' +
                ", receiptVO=" + receiptVO +
                ", orderType='" + orderType + '\'' +
                "} " + super.toString();
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

    public ConsigneeVO getConsignee() {
        return consignee;
    }

    public void setConsignee(ConsigneeVO consignee) {
        this.consignee = consignee;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Long getShipTime() {
        return shipTime;
    }

    public void setShipTime(Long shipTime) {
        this.shipTime = shipTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
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

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
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

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Integer getShipProvinceId() {
        return shipProvinceId;
    }

    public void setShipProvinceId(Integer shipProvinceId) {
        this.shipProvinceId = shipProvinceId;
    }

    public Integer getShipCityId() {
        return shipCityId;
    }

    public void setShipCityId(Integer shipCityId) {
        this.shipCityId = shipCityId;
    }

    public Integer getShipRegionId() {
        return shipRegionId;
    }

    public void setShipRegionId(Integer shipRegionId) {
        this.shipRegionId = shipRegionId;
    }

    public Integer getShipTownId() {
        return shipTownId;
    }

    public void setShipTownId(Integer shipTownId) {
        this.shipTownId = shipTownId;
    }

    public String getShipProvince() {
        return shipProvince;
    }

    public void setShipProvince(String shipProvince) {
        this.shipProvince = shipProvince;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipRegion() {
        return shipRegion;
    }

    public void setShipRegion(String shipRegion) {
        this.shipRegion = shipRegion;
    }

    public String getShipTown() {
        return shipTown;
    }

    public void setShipTown(String shipTown) {
        this.shipTown = shipTown;
    }

    public Long getSigningTime() {
        return signingTime;
    }

    public void setSigningTime(Long signingTime) {
        this.signingTime = signingTime;
    }

    public String getTheSign() {
        return theSign;
    }

    public void setTheSign(String theSign) {
        this.theSign = theSign;
    }

    public String getAdminRemark() {
        return adminRemark;
    }

    public void setAdminRemark(String adminRemark) {
        this.adminRemark = adminRemark;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
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

    public Integer getNeedReceipt() {
        return needReceipt;
    }

    public void setNeedReceipt(Integer needReceipt) {
        this.needReceipt = needReceipt;
    }

    public String getReceiptTitle() {
        return receiptTitle;
    }

    public void setReceiptTitle(String receiptTitle) {
        this.receiptTitle = receiptTitle;
    }

    public String getReceiptContent() {
        return receiptContent;
    }

    public void setReceiptContent(String receiptContent) {
        this.receiptContent = receiptContent;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public ReceiptVO getReceiptVO() {
        return receiptVO;
    }

    public void setReceiptVO(ReceiptVO receiptVO) {
        this.receiptVO = receiptVO;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderData() {
        return orderData;
    }


}
