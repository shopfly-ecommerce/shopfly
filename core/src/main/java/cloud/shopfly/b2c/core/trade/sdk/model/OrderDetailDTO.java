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
package cloud.shopfly.b2c.core.trade.sdk.model;

import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderOperateAllowable;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CouponVO;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The order informationDTO
 *
 * @author Snow create in 2018/5/28
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel(description = "The order informationDTO")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDetailDTO {

    /**
     * A primary keyID
     */
    @ApiModelProperty(hidden = true)
    private Integer orderId;

    /**
     * Transaction number
     */
    @ApiModelProperty(name = "trade_sn", value = "Transaction number", required = false)
    private String tradeSn;

    /**
     * Order no.
     */
    @ApiModelProperty(name = "sn", value = "Order no.", required = false)
    private String sn;

    /**
     * The storeID
     */
    @ApiModelProperty(name = "seller_id", value = "The storeID", required = false)
    private Integer sellerId;

    /**
     * Shop name
     */
    @ApiModelProperty(name = "seller_name", value = "Shop name", required = false)
    private String sellerName;

    /**
     * membersID
     */
    @ApiModelProperty(name = "member_id", value = "membersID", required = false)
    private Integer memberId;

    /**
     * Buyers account
     */
    @ApiModelProperty(name = "member_name", value = "Buyers account", required = false)
    private String memberName;

    /**
     * Status
     */
    @ApiModelProperty(name = "order_status", value = "Status", required = false)
    private String orderStatus;

    /**
     * Payment status
     */
    @ApiModelProperty(name = "pay_status", value = "Payment status", required = false)
    private String payStatus;

    /**
     * Cargo status
     */
    @ApiModelProperty(name = "ship_status", value = "Cargo status", required = false)
    private String shipStatus;

    /**
     * Shipping typeID
     */
    @ApiModelProperty(name = "shipping_id", value = "Shipping typeID", required = false)
    private Integer shippingId;

    @ApiModelProperty(name = "comment_status", value = "Whether the comment is complete", required = false)
    private String commentStatus;

    /**
     * Shipping type
     */
    @ApiModelProperty(name = "shipping_type", value = "Shipping type", required = false)
    private String shippingType;

    /**
     * Method of paymentid
     */
    @ApiModelProperty(name = "payment_method_id", value = "Method of paymentid", required = false)
    private String paymentMethodId;

    /**
     * Pay the plug-inid
     */
    @ApiModelProperty(name = "payment_plugin_id", value = "Pay the plug-inid", required = false)
    private String paymentPluginId;

    /**
     * Name of Payment Method
     */
    @ApiModelProperty(name = "payment_method_name", value = "Name of Payment Method", required = false)
    private String paymentMethodName;

    /**
     * Type of payment
     */
    @ApiModelProperty(name = "payment_type", value = "Type of payment", required = false)
    private String paymentType;

    /**
     * Payment time
     */
    @ApiModelProperty(name = "payment_time", value = "Payment time", required = false)
    private Long paymentTime;

    /**
     * Amount paid
     */
    @ApiModelProperty(name = "pay_money", value = "Amount paid", required = false)
    private Double payMoney;

    /**
     * Name of consignee
     */
    @ApiModelProperty(name = "ship_name", value = "Name of consignee", required = false)
    private String shipName;

    /**
     * Shipping address
     */
    @ApiModelProperty(name = "ship_addr", value = "Shipping address", required = false)
    private String shipAddr;

    /**
     * Consignees zip code
     */
    @ApiModelProperty(name = "ship_zip", value = "Consignees zip code", required = false)
    private String shipZip;

    /**
     * Consignees mobile phone
     */
    @ApiModelProperty(name = "ship_mobile", value = "Consignees mobile phone", required = false)
    private String shipMobile;

    /**
     * Consignees phone number
     */
    @ApiModelProperty(name = "ship_tel", value = "Consignees phone number", required = false)
    private String shipTel;

    /**
     * The goods time
     */
    @ApiModelProperty(name = "receive_time", value = "The goods time", required = false)
    private String receiveTime;

    /**
     *  -provincesID
     */
    @ApiModelProperty(name = "ship_province_id", value = " -provincesID", required = false)
    private Integer shipProvinceId;

    /**
     *  -cityID
     */
    @ApiModelProperty(name = "ship_city_id", value = " -cityID", required = false)
    private Integer shipCityId;

    /**
     *  -area(county)ID
     */
    @ApiModelProperty(name = "ship_county_id", value = " -area(county)ID", required = false)
    private Integer shipCountyId;

    /**
     * Distribution streetid
     */
    @ApiModelProperty(name = "ship_town_id", value = "Distribution streetid", required = false)
    private Integer shipTownId;

    /**
     *  -provinces
     */
    @ApiModelProperty(name = "ship_province", value = " -provinces", required = false)
    private String shipProvince;

    /**
     *  -city
     */
    @ApiModelProperty(name = "ship_city", value = " -city", required = false)
    private String shipCity;

    /**
     *  -area(county)
     */
    @ApiModelProperty(name = "ship_county", value = " -area(county)", required = false)
    private String shipCounty;

    /**
     * Distribution street
     */
    @ApiModelProperty(name = "ship_town", value = "Distribution street", required = false)
    private String shipTown;

    /**
     * The total order
     */
    @ApiModelProperty(name = "order_price", value = "The total order", required = false)
    private Double orderPrice;

    /**
     * The total amount of goods
     */
    @ApiModelProperty(name = "goods_price", value = "The total amount of goods", required = false)
    private Double goodsPrice;

    /**
     * Distribution costs
     */
    @ApiModelProperty(name = "shipping_price", value = "Distribution costs", required = false)
    private Double shippingPrice;

    /**
     * Discount amount
     */
    @ApiModelProperty(name = "discount_price", value = "Discount amount", required = false)
    private Double discountPrice;

    /**
     * Deleted or not
     */
    @ApiModelProperty(name = "disabled", value = "Deleted or not", required = false)
    private Integer disabled;

    @ApiModelProperty(name = "weight", value = "Total weight of goods ordered", required = false)
    private Double weight;

    @ApiModelProperty(name = "goods_num", value = "The number", required = false)
    private Integer goodsNum;

    @ApiModelProperty(name = "remark", value = "The order note", required = false)
    private String remark;

    @ApiModelProperty(name = "cancel_reason", value = "Reason for Order Cancellation", required = false)
    private String cancelReason;

    @ApiModelProperty(name = "the_sign", value = "Sign for people", required = false)
    private String theSign;

    /**
     * conversionList<OrderSkuVO>
     */
    @ApiModelProperty(name = "items_json", value = "List of goodsjson", required = false)
    private String itemsJson;

    @ApiModelProperty(name = "warehouse_id", value = "The delivery warehouseID", required = false)
    private Integer warehouseId;

    @ApiModelProperty(name = "need_pay_money", value = "Amount payable", required = false)
    private Double needPayMoney;

    @ApiModelProperty(name = "ship_no", value = "Invoice no.", required = false)
    private String shipNo;

    @ApiModelProperty(name = "address_id", value = "Shipping addressID", required = false)
    private Integer addressId;

    @ApiModelProperty(name = "admin_remark", value = "Administrator Remarks", required = false)
    private Integer adminRemark;

    @ApiModelProperty(name = "logi_id", value = "Logistics companyID", required = false)
    private Integer logiId;

    @ApiModelProperty(name = "logi_name", value = "Name of logistics Company", required = false)
    private String logiName;

    @ApiModelProperty(name = "need_receipt", value = "Do you need an invoice?", required = false)
    private String needReceipt;

    @ApiModelProperty(name = "receipt_title", value = "The invoice looked up", required = false)
    private String receiptTitle;

    @ApiModelProperty(name = "receipt_content", value = "The invoice content", required = false)
    private String receiptContent;

    @ApiModelProperty(name = "duty_invoice", value = "ein", required = false)
    private String dutyInvoice;

    @ApiModelProperty(name = "receipt_type", value = "Invoice type", required = false)
    private String receiptType;

    @ApiModelProperty(name = "complete_time", value = "Completion time", required = false)
    private Long completeTime;

    @ApiModelProperty(name = "create_time", value = "Order Creation time", required = false)
    private Long createTime;

    @ApiModelProperty(name = "signing_time", value = "To sign for the time", required = false)
    private Long signingTime;

    @ApiModelProperty(name = "ship_time", value = "Delivery time", required = false)
    private Long shipTime;

    @ApiModelProperty(name = "pay_order_no", value = "Payment method returns the transaction number", required = false)
    private String payOrderNo;

    @ApiModelProperty(name = "service_status", value = "After state", required = false)
    private String serviceStatus;

    @ApiModelProperty(name = "bill_status", value = "Settlement status", required = false)
    private Integer billStatus;

    @ApiModelProperty(name = "bill_sn", value = "Statement no.", required = false)
    private String billSn;

    @ApiModelProperty(name = "client_type", value = "Source of the order", required = false)
    private String clientType;

    @ApiModelProperty(value = "Order status text")
    private String orderStatusText;

    @ApiModelProperty(value = "Payment status text")
    private String payStatusText;

    @ApiModelProperty(value = "Delivery status text")
    private String shipStatusText;

    @ApiModelProperty(value = "Method of payment")
    private String paymentName;

    @ApiModelProperty(value = "skuThe list of")
    private List<OrderSkuDTO> orderSkuList;

    @ApiModelProperty(value = "Order list of freebies")
    private List<FullDiscountGiftDO> giftList;

    @ApiModelProperty(value = "Order operation permit status")
    private OrderOperateAllowable orderOperateAllowableVO;


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

    public String getShipTel() {
        return shipTel;
    }

    public void setShipTel(String shipTel) {
        this.shipTel = shipTel;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
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

    public Integer getShipCountyId() {
        return shipCountyId;
    }

    public void setShipCountyId(Integer shipCountyId) {
        this.shipCountyId = shipCountyId;
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

    public String getShipCounty() {
        return shipCounty;
    }

    public void setShipCounty(String shipCounty) {
        this.shipCounty = shipCounty;
    }

    public String getShipTown() {
        return shipTown;
    }

    public void setShipTown(String shipTown) {
        this.shipTown = shipTown;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Double getDiscountPrice() {
        return discountPrice;
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

    public String getNeedReceipt() {
        return needReceipt;
    }

    public void setNeedReceipt(String needReceipt) {
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

    public String getDutyInvoice() {
        return dutyInvoice;
    }

    public void setDutyInvoice(String dutyInvoice) {
        this.dutyInvoice = dutyInvoice;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
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

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public List<OrderSkuDTO> getOrderSkuList() {

        if (orderSkuList == null && itemsJson != null) {
            List<OrderSkuVO> skuList = JsonUtil.jsonToList(itemsJson, OrderSkuVO.class);
            List<OrderSkuDTO> res = new ArrayList<>();
            for (OrderSkuVO vo : skuList) {
                OrderSkuDTO dto = new OrderSkuDTO();
                BeanUtils.copyProperties(vo, dto);
                res.add(dto);
            }

            return res;
        }
        return orderSkuList;
    }

    public void setOrderSkuList(List<OrderSkuDTO> orderSkuList) {
        this.orderSkuList = orderSkuList;
    }

    public OrderOperateAllowable getOrderOperateAllowableVO() {
        return orderOperateAllowableVO;
    }

    public void setOrderOperateAllowableVO(OrderOperateAllowable orderOperateAllowableVO) {
        this.orderOperateAllowableVO = orderOperateAllowableVO;
    }

    public List<FullDiscountGiftDO> getGiftList() {
        return giftList;
    }

    public Double getCashBack() {
        return cashBack;
    }

    public void setCashBack(Double cashBack) {
        this.cashBack = cashBack;
    }

    public Double getCouponPrice() {
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

    public CouponVO getGiftCoupon() {
        return giftCoupon;
    }

    public void setGiftCoupon(CouponVO giftCoupon) {
        this.giftCoupon = giftCoupon;
    }

    public Integer getUsePoint() {
        return usePoint;
    }

    public void setUsePoint(Integer usePoint) {
        this.usePoint = usePoint;
    }

    public void setGiftList(List<FullDiscountGiftDO> giftList) {
        this.giftList = giftList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderDetailDTO that = (OrderDetailDTO) o;

        return new EqualsBuilder()
                .append(orderId, that.orderId)
                .append(tradeSn, that.tradeSn)
                .append(sn, that.sn)
                .append(sellerId, that.sellerId)
                .append(sellerName, that.sellerName)
                .append(memberId, that.memberId)
                .append(memberName, that.memberName)
                .append(orderStatus, that.orderStatus)
                .append(payStatus, that.payStatus)
                .append(shipStatus, that.shipStatus)
                .append(shippingId, that.shippingId)
                .append(commentStatus, that.commentStatus)
                .append(shippingType, that.shippingType)
                .append(paymentMethodId, that.paymentMethodId)
                .append(paymentPluginId, that.paymentPluginId)
                .append(paymentMethodName, that.paymentMethodName)
                .append(paymentType, that.paymentType)
                .append(paymentTime, that.paymentTime)
                .append(payMoney, that.payMoney)
                .append(shipName, that.shipName)
                .append(shipAddr, that.shipAddr)
                .append(shipZip, that.shipZip)
                .append(shipMobile, that.shipMobile)
                .append(shipTel, that.shipTel)
                .append(receiveTime, that.receiveTime)
                .append(shipProvinceId, that.shipProvinceId)
                .append(shipCityId, that.shipCityId)
                .append(shipCountyId, that.shipCountyId)
                .append(shipTownId, that.shipTownId)
                .append(shipProvince, that.shipProvince)
                .append(shipCity, that.shipCity)
                .append(shipCounty, that.shipCounty)
                .append(shipTown, that.shipTown)
                .append(orderPrice, that.orderPrice)
                .append(goodsPrice, that.goodsPrice)
                .append(shippingPrice, that.shippingPrice)
                .append(discountPrice, that.discountPrice)
                .append(disabled, that.disabled)
                .append(weight, that.weight)
                .append(goodsNum, that.goodsNum)
                .append(remark, that.remark)
                .append(cancelReason, that.cancelReason)
                .append(theSign, that.theSign)
                .append(itemsJson, that.itemsJson)
                .append(warehouseId, that.warehouseId)
                .append(needPayMoney, that.needPayMoney)
                .append(shipNo, that.shipNo)
                .append(addressId, that.addressId)
                .append(adminRemark, that.adminRemark)
                .append(logiId, that.logiId)
                .append(logiName, that.logiName)
                .append(needReceipt, that.needReceipt)
                .append(receiptTitle, that.receiptTitle)
                .append(receiptContent, that.receiptContent)
                .append(dutyInvoice, that.dutyInvoice)
                .append(receiptType, that.receiptType)
                .append(completeTime, that.completeTime)
                .append(createTime, that.createTime)
                .append(signingTime, that.signingTime)
                .append(shipTime, that.shipTime)
                .append(payOrderNo, that.payOrderNo)
                .append(serviceStatus, that.serviceStatus)
                .append(billStatus, that.billStatus)
                .append(billSn, that.billSn)
                .append(clientType, that.clientType)
                .append(orderStatusText, that.orderStatusText)
                .append(payStatusText, that.payStatusText)
                .append(shipStatusText, that.shipStatusText)
                .append(paymentName, that.paymentName)
                .append(orderSkuList, that.orderSkuList)
                .append(giftList, that.giftList)
                .append(orderOperateAllowableVO, that.orderOperateAllowableVO)
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
                .append(shipTel)
                .append(receiveTime)
                .append(shipProvinceId)
                .append(shipCityId)
                .append(shipCountyId)
                .append(shipTownId)
                .append(shipProvince)
                .append(shipCity)
                .append(shipCounty)
                .append(shipTown)
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
                .append(needReceipt)
                .append(receiptTitle)
                .append(receiptContent)
                .append(dutyInvoice)
                .append(receiptType)
                .append(completeTime)
                .append(createTime)
                .append(signingTime)
                .append(shipTime)
                .append(payOrderNo)
                .append(serviceStatus)
                .append(billStatus)
                .append(billSn)
                .append(clientType)
                .append(orderStatusText)
                .append(payStatusText)
                .append(shipStatusText)
                .append(paymentName)
                .append(orderSkuList)
                .append(giftList)
                .append(orderOperateAllowableVO)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
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
                ", shipTel='" + shipTel + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", shipProvinceId=" + shipProvinceId +
                ", shipCityId=" + shipCityId +
                ", shipCountyId=" + shipCountyId +
                ", shipTownId=" + shipTownId +
                ", shipProvince='" + shipProvince + '\'' +
                ", shipCity='" + shipCity + '\'' +
                ", shipCounty='" + shipCounty + '\'' +
                ", shipTown='" + shipTown + '\'' +
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
                ", needReceipt='" + needReceipt + '\'' +
                ", receiptTitle='" + receiptTitle + '\'' +
                ", receiptContent='" + receiptContent + '\'' +
                ", dutyInvoice='" + dutyInvoice + '\'' +
                ", receiptType='" + receiptType + '\'' +
                ", completeTime=" + completeTime +
                ", createTime=" + createTime +
                ", signingTime=" + signingTime +
                ", shipTime=" + shipTime +
                ", payOrderNo='" + payOrderNo + '\'' +
                ", serviceStatus='" + serviceStatus + '\'' +
                ", billStatus=" + billStatus +
                ", billSn='" + billSn + '\'' +
                ", clientType='" + clientType + '\'' +
                ", orderStatusText='" + orderStatusText + '\'' +
                ", payStatusText='" + payStatusText + '\'' +
                ", shipStatusText='" + shipStatusText + '\'' +
                ", paymentName='" + paymentName + '\'' +
                ", orderSkuList=" + orderSkuList +
                ", giftList=" + giftList +
                ", orderOperateAllowableVO=" + orderOperateAllowableVO +
                '}';
    }


}
