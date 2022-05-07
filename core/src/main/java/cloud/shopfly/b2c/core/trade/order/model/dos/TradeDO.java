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
 * Transaction statement entity
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
     * Transaction number
     */
    @Column(name = "trade_sn")
    @ApiModelProperty(name = "trade_sn", value = "Transaction number", required = false)
    private String tradeSn;
    /**
     * buyersid
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "buyersid", required = false)
    private Integer memberId;
    /**
     * Buyer username
     */
    @Column(name = "member_name")
    @ApiModelProperty(name = "member_name", value = "Buyer username", required = false)
    private String memberName;
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
     * The total price
     */
    @Column(name = "total_price")
    @ApiModelProperty(name = "total_price", value = "The total price", required = false)
    private Double totalPrice;
    /**
     * Price
     */
    @Column(name = "goods_price")
    @ApiModelProperty(name = "goods_price", value = "Price", required = false)
    private Double goodsPrice;
    /**
     * freight
     */
    @Column(name = "freight_price")
    @ApiModelProperty(name = "freight_price", value = "freight", required = false)
    private Double freightPrice;
    /**
     * Preferential amount
     */
    @Column(name = "discount_price")
    @ApiModelProperty(name = "discount_price", value = "Preferential amount", required = false)
    private Double discountPrice;
    /**
     * The consigneeid
     */
    @Column(name = "consignee_id")
    @ApiModelProperty(name = "consignee_id", value = "The consigneeid", required = false)
    private Integer consigneeId;
    /**
     * Name of consignee
     */
    @Column(name = "consignee_name")
    @ApiModelProperty(name = "consignee_name", value = "Name of consignee", required = false)
    private String consigneeName;
    /**
     * Receiving countries
     */
    @Column(name = "consignee_country")
    @ApiModelProperty(name = "consignee_country", value = "Receiving countries", required = false)
    private String consigneeCountry;
    /**
     * Country code of receiving goods
     */
    @Column(name = "consignee_country_code")
    @ApiModelProperty(name = "consignee_country_code", value = "Country code of receiving goods", required = false)
    private String consigneeCountryCode;
    /**
     * Receiving state/Province name
     */
    @Column(name = "consignee_state")
    @ApiModelProperty(name = "consignee_state", value = "Receiving state/Province name", required = false)
    private String consigneeState;
    /**
     * Receiving state/Province code
     */
    @Column(name = "consignee_state_code")
    @ApiModelProperty(name = "consignee_state_code", value = "Receiving state/Province code", required = false)
    private String consigneeStateCode;
    /**
     * The goods,
     */
    @Column(name = "consignee_city")
    @ApiModelProperty(name = "consignee_city", value = "The goods,", required = false)
    private String consigneeCity;
    /**
     * Delivery details address
     */
    @Column(name = "consignee_address")
    @ApiModelProperty(name = "consignee_address", value = "Delivery details address", required = false)
    private String consigneeAddress;
    /**
     * Consignees mobile phone number
     */
    @Column(name = "consignee_mobile")
    @ApiModelProperty(name = "consignee_mobile", value = "Consignees mobile phone number", required = false)
    private String consigneeMobile;
    /**
     * Shipping address zip code
     */
    @Column(name = "consignee_zip_code")
    @ApiModelProperty(name = "consignee_zip_code", value = "Shipping address zip code", required = false)
    private String consigneeZipCode;
    /**
     * Transaction creation time
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "Transaction creation time", required = false)
    private Long createTime;
    /**
     * The orderjson(The reserved,7.0May be discarded)
     */
    @Column(name = "order_json")
    @ApiModelProperty(name = "order_json", value = "The orderjson(The reserved,7.0May be discarded)", required = false)
    private String orderJson;
    /**
     * Status
     */
    @Column(name = "trade_status")
    @ApiModelProperty(name = "trade_status", value = "Status", required = false)
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
     * Parameter constructor
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

        // Transaction status
        this.setTradeStatus(TradeStatusEnum.NEW.value());
        this.setMemberId(tradeVO.getMemberId());
        this.setMemberName(tradeVO.getMemberName());
    }

}
