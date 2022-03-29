/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.model.dto;

import com.enation.app.javashop.core.trade.cart.model.dos.CartDO;
import com.enation.app.javashop.core.trade.order.model.enums.OrderTypeEnum;
import com.enation.app.javashop.core.trade.order.model.vo.ConsigneeVO;
import com.enation.app.javashop.core.trade.order.model.vo.ReceiptVO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;

/**
 * 订单DTO
 *
 * @author kingapex
 * @version 1.0
 * @since v7.0.0 2017年3月22日下午9:28:30
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
@ApiIgnore
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDTO extends CartDO implements Serializable {

    private static final long serialVersionUID = 8206833000476657708L;

    @ApiModelProperty(value = "交易编号")
    private String tradeSn;

    @ApiModelProperty(value = "订单编号")
    private String sn;

    @ApiModelProperty(value = "收货信息")
    private ConsigneeVO consignee;

    @ApiModelProperty(value = "配送方式")
    private Integer shippingId;

    @ApiModelProperty(value = "支付方式")
    private String paymentType;

    @ApiModelProperty(value = "发货时间")
    private Long shipTime;

    @ApiModelProperty(value = "发货时间类型")
    private String receiveTime;

    @ApiModelProperty(value = "会员id")
    private Integer memberId;

    @ApiModelProperty(value = "会员姓名")
    private String memberName;

    @ApiModelProperty(value = "订单备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;


    @ApiModelProperty(value = "配送方式名称")
    private String shippingType;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @ApiModelProperty(value = "付款状态")
    private String payStatus;

    @ApiModelProperty(value = "配送状态")
    private String shipStatus;

    @ApiModelProperty(value = "收货人姓名")
    private String shipName;

    @ApiModelProperty(value = "订单价格")
    private Double orderPrice;

    @ApiModelProperty(value = "配送费")
    private Double shippingPrice;

    @ApiModelProperty(value = "评论状态")
    private String commentStatus;

    @ApiModelProperty(value = "是否已经删除")
    private Integer disabled;

    @ApiModelProperty(value = "支付方式id")
    private Integer paymentMethodId;

    @ApiModelProperty(value = "支付插件id")
    private String paymentPluginId;

    @ApiModelProperty(value = "支付方式名称")
    private String paymentMethodName;

    @ApiModelProperty(value = "付款账号")
    private String paymentAccount;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNum;

    @ApiModelProperty(value = "发货仓库id")
    private Integer warehouseId;

    @ApiModelProperty(value = "取消原因")
    private String cancelReason;

    @ApiModelProperty(value = "收货地址省Id")
    private Integer shipProvinceId;

    @ApiModelProperty(value = "收货地址市Id")
    private Integer shipCityId;

    @ApiModelProperty(value = "收货地址区Id")
    private Integer shipRegionId;

    @ApiModelProperty(value = "收货地址街道Id")
    private Integer shipTownId;

    @ApiModelProperty(value = "收货省")
    private String shipProvince;

    @ApiModelProperty(value = "收货地址市")
    private String shipCity;

    @ApiModelProperty(value = "收货地址区")
    private String shipRegion;

    @ApiModelProperty(value = "收货地址街道")
    private String shipTown;

    @ApiModelProperty(value = "签收时间")
    private Long signingTime;

    @ApiModelProperty(value = "签收人姓名")
    private String theSign;

    @ApiModelProperty(value = "管理员备注")
    private String adminRemark;

    @ApiModelProperty(value = "收货地址id")
    private Integer addressId;

    @ApiModelProperty(value = "应付金额")
    private Double needPayMoney;

    @ApiModelProperty(value = "发货单号")
    private String shipNo;

    @ApiModelProperty(value = "物流公司Id")
    private Integer logiId;

    @ApiModelProperty(value = "物流公司名称")
    private String logiName;

    @ApiModelProperty(value = "是否需要发票")
    private Integer needReceipt;

    @ApiModelProperty(value = "抬头")
    private String receiptTitle;

    @ApiModelProperty(value = "内容")
    private String receiptContent;

    @ApiModelProperty(value = "售后状态")
    private String serviceStatus;

    @ApiModelProperty(value = "订单来源")
    private String clientType;
    @ApiModelProperty(value = "发票信息")
    private ReceiptVO receiptVO;

    /**
     * @see OrderTypeEnum
     * 因增加拼团业务新增订单类型字段 kingapex 2019/1/28 on v7.1.0
     */
    @ApiModelProperty(value = "订单类型")
    private String orderType;


    /**
     * 订单的扩展数据
     * 为了增加订单的扩展性，个性化的业务可以将个性化数据（如拼团所差人数）存在此字段 kingapex 2019/1/28 on v7.1.0
     */
    @ApiModelProperty(value = "扩展数据", hidden = true)
    private String orderData;


    public void setOrderData(String orderData) {
        this.orderData = orderData;
    }

    /**
     * 无参构造器
     */
    public OrderDTO() {

    }


    /**
     * 用一个购物车购造订单
     *
     * @param cart
     */
    public OrderDTO(CartDO cart) {

        super(cart.getSellerId(), cart.getSellerName());

        // 初始化产品及优惠数据
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
