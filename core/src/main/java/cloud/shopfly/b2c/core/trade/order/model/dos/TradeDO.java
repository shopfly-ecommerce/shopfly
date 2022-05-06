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

import cloud.shopfly.b2c.core.trade.order.model.vo.ConsigneeVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PriceDetailVO;
import cloud.shopfly.b2c.core.trade.order.model.enums.TradeStatusEnum;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;


/**
 * 交易表实体
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-09 09:38:06
 */
@Table(name = "es_trade")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TradeDO implements Serializable {

    private static final long serialVersionUID = 8834971381961212L;

    /**
     * trade_id
     */
    @Id(name = "trade_id")
    @ApiModelProperty(hidden = true)
    private Long tradeId;
    /**
     * 交易编号
     */
    @Column(name = "trade_sn")
    @ApiModelProperty(name = "trade_sn", value = "交易编号", required = false)
    private String tradeSn;
    /**
     * 买家id
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "买家id", required = false)
    private Integer memberId;
    /**
     * 买家用户名
     */
    @Column(name = "member_name")
    @ApiModelProperty(name = "member_name", value = "买家用户名", required = false)
    private String memberName;
    /**
     * 支付方式id
     */
    @Column(name = "payment_method_id")
    @ApiModelProperty(name = "payment_method_id", value = "支付方式id", required = false)
    private String paymentMethodId;
    /**
     * 支付插件id
     */
    @Column(name = "payment_plugin_id")
    @ApiModelProperty(name = "payment_plugin_id", value = "支付插件id", required = false)
    private String paymentPluginId;
    /**
     * 支付方式名称
     */
    @Column(name = "payment_method_name")
    @ApiModelProperty(name = "payment_method_name", value = "支付方式名称", required = false)
    private String paymentMethodName;
    /**
     * 支付方式类型
     */
    @Column(name = "payment_type")
    @ApiModelProperty(name = "payment_type", value = "支付方式类型", required = false)
    private String paymentType;
    /**
     * 总价格
     */
    @Column(name = "total_price")
    @ApiModelProperty(name = "total_price", value = "总价格", required = false)
    private Double totalPrice;
    /**
     * 商品价格
     */
    @Column(name = "goods_price")
    @ApiModelProperty(name = "goods_price", value = "商品价格", required = false)
    private Double goodsPrice;
    /**
     * 运费
     */
    @Column(name = "freight_price")
    @ApiModelProperty(name = "freight_price", value = "运费", required = false)
    private Double freightPrice;
    /**
     * 优惠的金额
     */
    @Column(name = "discount_price")
    @ApiModelProperty(name = "discount_price", value = "优惠的金额", required = false)
    private Double discountPrice;
    /**
     * 收货人id
     */
    @Column(name = "consignee_id")
    @ApiModelProperty(name = "consignee_id", value = "收货人id", required = false)
    private Integer consigneeId;
    /**
     * 收货人姓名
     */
    @Column(name = "consignee_name")
    @ApiModelProperty(name = "consignee_name", value = "收货人姓名", required = false)
    private String consigneeName;
    /**
     * 收货国家
     */
    @Column(name = "consignee_country")
    @ApiModelProperty(name = "consignee_country", value = "收货国家", required = false)
    private String consigneeCountry;
    /**
     * 收货国家编码
     */
    @Column(name = "consignee_country_code")
    @ApiModelProperty(name = "consignee_country_code", value = "收货国家编码", required = false)
    private String consigneeCountryCode;
    /**
     * 收货州/省名
     */
    @Column(name = "consignee_state")
    @ApiModelProperty(name = "consignee_state", value = "收货州/省名", required = false)
    private String consigneeState;
    /**
     * 收货州/省名编码
     */
    @Column(name = "consignee_state_code")
    @ApiModelProperty(name = "consignee_state_code", value = "收货州/省名编码", required = false)
    private String consigneeStateCode;
    /**
     * 收货市
     */
    @Column(name = "consignee_city")
    @ApiModelProperty(name = "consignee_city", value = "收货市", required = false)
    private String consigneeCity;
    /**
     * 收货详细地址
     */
    @Column(name = "consignee_address")
    @ApiModelProperty(name = "consignee_address", value = "收货详细地址", required = false)
    private String consigneeAddress;
    /**
     * 收货人手机号
     */
    @Column(name = "consignee_mobile")
    @ApiModelProperty(name = "consignee_mobile", value = "收货人手机号", required = false)
    private String consigneeMobile;
    /**
     * 收货地址邮编
     */
    @Column(name = "consignee_zip_code")
    @ApiModelProperty(name = "consignee_zip_code", value = "收货地址邮编", required = false)
    private String consigneeZipCode;
    /**
     * 交易创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "交易创建时间", required = false)
    private Long createTime;
    /**
     * 订单json(预留，7.0可能废弃)
     */
    @Column(name = "order_json")
    @ApiModelProperty(name = "order_json", value = "订单json(预留，7.0可能废弃)", required = false)
    private String orderJson;
    /**
     * 订单状态
     */
    @Column(name = "trade_status")
    @ApiModelProperty(name = "trade_status", value = "订单状态", required = false)
    private String tradeStatus;

    @PrimaryKeyField
    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Double getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(Double freightPrice) {
        this.freightPrice = freightPrice;
    }

    public Double getDiscountPrice() {
        if (discountPrice == null) {
            discountPrice = 0.0;
        }

        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(Integer consigneeId) {
        this.consigneeId = consigneeId;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeCity() {
        return consigneeCity;
    }

    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }

    public String getConsigneeCountry() {
        return consigneeCountry;
    }

    public void setConsigneeCountry(String consigneeCountry) {
        this.consigneeCountry = consigneeCountry;
    }

    public String getOrderJson() {
        return orderJson;
    }

    public void setOrderJson(String orderJson) {
        this.orderJson = orderJson;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getConsigneeCountryCode() {
        return consigneeCountryCode;
    }

    public void setConsigneeCountryCode(String consigneeCountryCode) {
        this.consigneeCountryCode = consigneeCountryCode;
    }

    public String getConsigneeState() {
        return consigneeState;
    }

    public void setConsigneeState(String consigneeState) {
        this.consigneeState = consigneeState;
    }

    public String getConsigneeStateCode() {
        return consigneeStateCode;
    }

    public void setConsigneeStateCode(String consigneeStateCode) {
        this.consigneeStateCode = consigneeStateCode;
    }

    public String getConsigneeZipCode() {
        return consigneeZipCode;
    }

    public void setConsigneeZipCode(String consigneeZipCode) {
        this.consigneeZipCode = consigneeZipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TradeDO tradeDO = (TradeDO) o;
        return Objects.equals(tradeId, tradeDO.tradeId) &&
                Objects.equals(tradeSn, tradeDO.tradeSn) &&
                Objects.equals(memberId, tradeDO.memberId) &&
                Objects.equals(memberName, tradeDO.memberName) &&
                Objects.equals(paymentMethodId, tradeDO.paymentMethodId) &&
                Objects.equals(paymentPluginId, tradeDO.paymentPluginId) &&
                Objects.equals(paymentMethodName, tradeDO.paymentMethodName) &&
                Objects.equals(paymentType, tradeDO.paymentType) &&
                Objects.equals(totalPrice, tradeDO.totalPrice) &&
                Objects.equals(goodsPrice, tradeDO.goodsPrice) &&
                Objects.equals(freightPrice, tradeDO.freightPrice) &&
                Objects.equals(discountPrice, tradeDO.discountPrice) &&
                Objects.equals(consigneeId, tradeDO.consigneeId) &&
                Objects.equals(consigneeName, tradeDO.consigneeName) &&
                Objects.equals(consigneeCountry, tradeDO.consigneeCountry) &&
                Objects.equals(consigneeCountryCode, tradeDO.consigneeCountryCode) &&
                Objects.equals(consigneeState, tradeDO.consigneeState) &&
                Objects.equals(consigneeStateCode, tradeDO.consigneeStateCode) &&
                Objects.equals(consigneeCity, tradeDO.consigneeCity) &&
                Objects.equals(consigneeAddress, tradeDO.consigneeAddress) &&
                Objects.equals(consigneeMobile, tradeDO.consigneeMobile) &&
                Objects.equals(consigneeZipCode, tradeDO.consigneeZipCode) &&
                Objects.equals(createTime, tradeDO.createTime) &&
                Objects.equals(orderJson, tradeDO.orderJson) &&
                Objects.equals(tradeStatus, tradeDO.tradeStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId, tradeSn, memberId, memberName, paymentMethodId, paymentPluginId, paymentMethodName, paymentType, totalPrice, goodsPrice, freightPrice, discountPrice, consigneeId, consigneeName, consigneeCountry, consigneeCountryCode, consigneeState, consigneeStateCode, consigneeCity, consigneeAddress, consigneeMobile, consigneeZipCode, createTime, orderJson, tradeStatus);
    }

    @Override
    public String toString() {
        return "TradeDO{" +
                "tradeId=" + tradeId +
                ", tradeSn='" + tradeSn + '\'' +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", paymentMethodId='" + paymentMethodId + '\'' +
                ", paymentPluginId='" + paymentPluginId + '\'' +
                ", paymentMethodName='" + paymentMethodName + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", totalPrice=" + totalPrice +
                ", goodsPrice=" + goodsPrice +
                ", freightPrice=" + freightPrice +
                ", discountPrice=" + discountPrice +
                ", consigneeId=" + consigneeId +
                ", consigneeName='" + consigneeName + '\'' +
                ", consigneeCountry='" + consigneeCountry + '\'' +
                ", consigneeCountryCode='" + consigneeCountryCode + '\'' +
                ", consigneeState='" + consigneeState + '\'' +
                ", consigneeStateCode='" + consigneeStateCode + '\'' +
                ", consigneeCity='" + consigneeCity + '\'' +
                ", consigneeAddress='" + consigneeAddress + '\'' +
                ", consigneeMobile='" + consigneeMobile + '\'' +
                ", consigneeZipCode='" + consigneeZipCode + '\'' +
                ", createTime=" + createTime +
                ", orderJson='" + orderJson + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                '}';
    }

    public TradeDO() {

    }

    /**
     * 参数构造器
     *
     * @param tradeVO
     */
    public TradeDO(TradeVO tradeVO) {
        PriceDetailVO priceDetail = tradeVO.getPriceDetail();

        this.setTotalPrice(priceDetail.getTotalPrice());
        this.setGoodsPrice(priceDetail.getGoodsPrice());
        this.setFreightPrice(priceDetail.getFreightPrice());
        this.setDiscountPrice(priceDetail.getDiscountPrice());

        ConsigneeVO consignee = tradeVO.getConsignee();
        this.setConsigneeName(consignee.getName());
        this.setConsigneeAddress(consignee.getAddress());
        this.setConsigneeId(consignee.getConsigneeId());
        this.setConsigneeMobile(consignee.getMobile());
        this.setConsigneeCountry(consignee.getCountry());
        this.setConsigneeCountryCode(consignee.getCountryCode());
        this.setConsigneeState(consignee.getStateName());
        this.setConsigneeCity(consignee.getCity());
        this.setConsigneeCountryCode(consignee.getCountryCode());
        this.setConsigneeStateCode(consignee.getStateCode());
        this.setConsigneeZipCode(consignee.getZipCode());

        this.setTradeSn(tradeVO.getTradeSn());
        this.setPaymentType(tradeVO.getPaymentType());

        //交易状态
        this.setTradeStatus(TradeStatusEnum.NEW.value());
        this.setMemberId(tradeVO.getMemberId());
        this.setMemberName(tradeVO.getMemberName());
    }

}
