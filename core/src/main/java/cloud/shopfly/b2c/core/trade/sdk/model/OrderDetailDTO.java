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
 * 订单信息DTO
 *
 * @author Snow create in 2018/5/28
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel(description = "订单信息DTO")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDetailDTO {

    /**
     * 主键ID
     */
    @ApiModelProperty(hidden = true)
    private Integer orderId;

    /**
     * 交易编号
     */
    @ApiModelProperty(name = "trade_sn", value = "交易编号", required = false)
    private String tradeSn;

    /**
     * 订单编号
     */
    @ApiModelProperty(name = "sn", value = "订单编号", required = false)
    private String sn;

    /**
     * 店铺ID
     */
    @ApiModelProperty(name = "seller_id", value = "店铺ID", required = false)
    private Integer sellerId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(name = "seller_name", value = "店铺名称", required = false)
    private String sellerName;

    /**
     * 会员ID
     */
    @ApiModelProperty(name = "member_id", value = "会员ID", required = false)
    private Integer memberId;

    /**
     * 买家账号
     */
    @ApiModelProperty(name = "member_name", value = "买家账号", required = false)
    private String memberName;

    /**
     * 订单状态
     */
    @ApiModelProperty(name = "order_status", value = "订单状态", required = false)
    private String orderStatus;

    /**
     * 付款状态
     */
    @ApiModelProperty(name = "pay_status", value = "付款状态", required = false)
    private String payStatus;

    /**
     * 货运状态
     */
    @ApiModelProperty(name = "ship_status", value = "货运状态", required = false)
    private String shipStatus;

    /**
     * 配送方式ID
     */
    @ApiModelProperty(name = "shipping_id", value = "配送方式ID", required = false)
    private Integer shippingId;

    @ApiModelProperty(name = "comment_status", value = "评论是否完成", required = false)
    private String commentStatus;

    /**
     * 配送方式
     */
    @ApiModelProperty(name = "shipping_type", value = "配送方式", required = false)
    private String shippingType;

    /**
     * 支付方式id
     */
    @ApiModelProperty(name = "payment_method_id", value = "支付方式id", required = false)
    private String paymentMethodId;

    /**
     * 支付插件id
     */
    @ApiModelProperty(name = "payment_plugin_id", value = "支付插件id", required = false)
    private String paymentPluginId;

    /**
     * 支付方式名称
     */
    @ApiModelProperty(name = "payment_method_name", value = "支付方式名称", required = false)
    private String paymentMethodName;

    /**
     * 支付方式类型
     */
    @ApiModelProperty(name = "payment_type", value = "支付方式类型", required = false)
    private String paymentType;

    /**
     * 支付时间
     */
    @ApiModelProperty(name = "payment_time", value = "支付时间", required = false)
    private Long paymentTime;

    /**
     * 已支付金额
     */
    @ApiModelProperty(name = "pay_money", value = "已支付金额", required = false)
    private Double payMoney;

    /**
     * 收货人姓名
     */
    @ApiModelProperty(name = "ship_name", value = "收货人姓名", required = false)
    private String shipName;

    /**
     * 收货地址
     */
    @ApiModelProperty(name = "ship_addr", value = "收货地址", required = false)
    private String shipAddr;

    /**
     * 收货人邮编
     */
    @ApiModelProperty(name = "ship_zip", value = "收货人邮编", required = false)
    private String shipZip;

    /**
     * 收货人手机
     */
    @ApiModelProperty(name = "ship_mobile", value = "收货人手机", required = false)
    private String shipMobile;

    /**
     * 收货人电话
     */
    @ApiModelProperty(name = "ship_tel", value = "收货人电话", required = false)
    private String shipTel;

    /**
     * 收货时间
     */
    @ApiModelProperty(name = "receive_time", value = "收货时间", required = false)
    private String receiveTime;

    /**
     * 配送地区-省份ID
     */
    @ApiModelProperty(name = "ship_province_id", value = "配送地区-省份ID", required = false)
    private Integer shipProvinceId;

    /**
     * 配送地区-城市ID
     */
    @ApiModelProperty(name = "ship_city_id", value = "配送地区-城市ID", required = false)
    private Integer shipCityId;

    /**
     * 配送地区-区(县)ID
     */
    @ApiModelProperty(name = "ship_county_id", value = "配送地区-区(县)ID", required = false)
    private Integer shipCountyId;

    /**
     * 配送街道id
     */
    @ApiModelProperty(name = "ship_town_id", value = "配送街道id", required = false)
    private Integer shipTownId;

    /**
     * 配送地区-省份
     */
    @ApiModelProperty(name = "ship_province", value = "配送地区-省份", required = false)
    private String shipProvince;

    /**
     * 配送地区-城市
     */
    @ApiModelProperty(name = "ship_city", value = "配送地区-城市", required = false)
    private String shipCity;

    /**
     * 配送地区-区(县)
     */
    @ApiModelProperty(name = "ship_county", value = "配送地区-区(县)", required = false)
    private String shipCounty;

    /**
     * 配送街道
     */
    @ApiModelProperty(name = "ship_town", value = "配送街道", required = false)
    private String shipTown;

    /**
     * 订单总额
     */
    @ApiModelProperty(name = "order_price", value = "订单总额", required = false)
    private Double orderPrice;

    /**
     * 商品总额
     */
    @ApiModelProperty(name = "goods_price", value = "商品总额", required = false)
    private Double goodsPrice;

    /**
     * 配送费用
     */
    @ApiModelProperty(name = "shipping_price", value = "配送费用", required = false)
    private Double shippingPrice;

    /**
     * 优惠金额
     */
    @ApiModelProperty(name = "discount_price", value = "优惠金额", required = false)
    private Double discountPrice;

    /**
     * 是否被删除
     */
    @ApiModelProperty(name = "disabled", value = "是否被删除", required = false)
    private Integer disabled;

    @ApiModelProperty(name = "weight", value = "订单商品总重量", required = false)
    private Double weight;

    @ApiModelProperty(name = "goods_num", value = "商品数量", required = false)
    private Integer goodsNum;

    @ApiModelProperty(name = "remark", value = "订单备注", required = false)
    private String remark;

    @ApiModelProperty(name = "cancel_reason", value = "订单取消原因", required = false)
    private String cancelReason;

    @ApiModelProperty(name = "the_sign", value = "签收人", required = false)
    private String theSign;

    /**
     * 转换 List<OrderSkuVO>
     */
    @ApiModelProperty(name = "items_json", value = "货物列表json", required = false)
    private String itemsJson;

    @ApiModelProperty(name = "warehouse_id", value = "发货仓库ID", required = false)
    private Integer warehouseId;

    @ApiModelProperty(name = "need_pay_money", value = "应付金额", required = false)
    private Double needPayMoney;

    @ApiModelProperty(name = "ship_no", value = "发货单号", required = false)
    private String shipNo;

    @ApiModelProperty(name = "address_id", value = "收货地址ID", required = false)
    private Integer addressId;

    @ApiModelProperty(name = "admin_remark", value = "管理员备注", required = false)
    private Integer adminRemark;

    @ApiModelProperty(name = "logi_id", value = "物流公司ID", required = false)
    private Integer logiId;

    @ApiModelProperty(name = "logi_name", value = "物流公司名称", required = false)
    private String logiName;

    @ApiModelProperty(name = "need_receipt", value = "是否需要发票", required = false)
    private String needReceipt;

    @ApiModelProperty(name = "receipt_title", value = "发票抬头", required = false)
    private String receiptTitle;

    @ApiModelProperty(name = "receipt_content", value = "发票内容", required = false)
    private String receiptContent;

    @ApiModelProperty(name = "duty_invoice", value = "税号", required = false)
    private String dutyInvoice;

    @ApiModelProperty(name = "receipt_type", value = "发票类型", required = false)
    private String receiptType;

    @ApiModelProperty(name = "complete_time", value = "完成时间", required = false)
    private Long completeTime;

    @ApiModelProperty(name = "create_time", value = "订单创建时间", required = false)
    private Long createTime;

    @ApiModelProperty(name = "signing_time", value = "签收时间", required = false)
    private Long signingTime;

    @ApiModelProperty(name = "ship_time", value = "送货时间", required = false)
    private Long shipTime;

    @ApiModelProperty(name = "pay_order_no", value = "支付方式返回的交易号", required = false)
    private String payOrderNo;

    @ApiModelProperty(name = "service_status", value = "售后状态", required = false)
    private String serviceStatus;

    @ApiModelProperty(name = "bill_status", value = "结算状态", required = false)
    private Integer billStatus;

    @ApiModelProperty(name = "bill_sn", value = "结算单号", required = false)
    private String billSn;

    @ApiModelProperty(name = "client_type", value = "订单来源", required = false)
    private String clientType;

    @ApiModelProperty(value = "订单状态文字")
    private String orderStatusText;

    @ApiModelProperty(value = "付款状态文字")
    private String payStatusText;

    @ApiModelProperty(value = "发货状态文字")
    private String shipStatusText;

    @ApiModelProperty(value = "支付方式")
    private String paymentName;

    @ApiModelProperty(value = "sku列表")
    private List<OrderSkuDTO> orderSkuList;

    @ApiModelProperty(value = "订单赠品列表")
    private List<FullDiscountGiftDO> giftList;

    @ApiModelProperty(value = "订单操作允许情况")
    private OrderOperateAllowable orderOperateAllowableVO;


    @ApiModelProperty(value = "返现金额")
    private Double cashBack;

    @ApiModelProperty(value = "优惠券抵扣金额")
    private Double couponPrice;

    @ApiModelProperty(value = "赠送的积分")
    private Integer giftPoint;

    @ApiModelProperty(value = "赠送的优惠券")
    private CouponVO giftCoupon;


    @ApiModelProperty(value = "此订单使用的积分")
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
