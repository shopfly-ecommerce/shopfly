/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.model.dos;

import com.enation.app.javashop.core.trade.cart.model.vo.PriceDetailVO;
import com.enation.app.javashop.core.trade.order.model.enums.TradeStatusEnum;
import com.enation.app.javashop.core.trade.order.model.vo.ConsigneeVO;
import com.enation.app.javashop.core.trade.order.model.vo.TradeVO;
import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;


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
     * 收货国家id
     */
    @Column(name = "consignee_country_id")
    @ApiModelProperty(name = "consignee_country_id", value = "收货国家id", required = false)
    private Integer consigneeCountryId;
    /**
     * 收货省
     */
    @Column(name = "consignee_province")
    @ApiModelProperty(name = "consignee_province", value = "收货省", required = false)
    private String consigneeProvince;
    /**
     * 收货省id
     */
    @Column(name = "consignee_province_id")
    @ApiModelProperty(name = "consignee_province_id", value = "收货省id", required = false)
    private Integer consigneeProvinceId;
    /**
     * 收货市
     */
    @Column(name = "consignee_city")
    @ApiModelProperty(name = "consignee_city", value = "收货市", required = false)
    private String consigneeCity;
    /**
     * 收货市id
     */
    @Column(name = "consignee_city_id")
    @ApiModelProperty(name = "consignee_city_id", value = "收货市id", required = false)
    private Integer consigneeCityId;
    /**
     * 收货区
     */
    @Column(name = "consignee_county")
    @ApiModelProperty(name = "consignee_county", value = "收货区", required = false)
    private String consigneeCounty;
    /**
     * 收货区id
     */
    @Column(name = "consignee_county_id")
    @ApiModelProperty(name = "consignee_county_id", value = "收货区id", required = false)
    private Integer consigneeCountyId;
    /**
     * 收货镇
     */
    @Column(name = "consignee_town")
    @ApiModelProperty(name = "consignee_town", value = "收货镇", required = false)
    private String consigneeTown;
    /**
     * 收货镇id
     */
    @Column(name = "consignee_town_id")
    @ApiModelProperty(name = "consignee_town_id", value = "收货镇id", required = false)
    private Integer consigneeTownId;
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
     * 收货人电话
     */
    @Column(name = "consignee_telephone")
    @ApiModelProperty(name = "consignee_telephone", value = "收货人电话", required = false)
    private String consigneeTelephone;
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

    public String getConsigneeCounty() {
        return consigneeCounty;
    }

    public void setConsigneeCounty(String consigneeCounty) {
        this.consigneeCounty = consigneeCounty;
    }

    public Integer getConsigneeCountyId() {
        return consigneeCountyId;
    }

    public void setConsigneeCountyId(Integer consigneeCountyId) {
        this.consigneeCountyId = consigneeCountyId;
    }

    public String getConsigneeProvince() {
        return consigneeProvince;
    }

    public void setConsigneeProvince(String consigneeProvince) {
        this.consigneeProvince = consigneeProvince;
    }

    public Integer getConsigneeProvinceId() {
        return consigneeProvinceId;
    }

    public void setConsigneeProvinceId(Integer consigneeProvinceId) {
        this.consigneeProvinceId = consigneeProvinceId;
    }

    public String getConsigneeCity() {
        return consigneeCity;
    }

    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }

    public Integer getConsigneeCityId() {
        return consigneeCityId;
    }

    public void setConsigneeCityId(Integer consigneeCityId) {
        this.consigneeCityId = consigneeCityId;
    }

    public String getConsigneeCountry() {
        return consigneeCountry;
    }

    public void setConsigneeCountry(String consigneeCountry) {
        this.consigneeCountry = consigneeCountry;
    }

    public Integer getConsigneeCountryId() {
        return consigneeCountryId;
    }

    public void setConsigneeCountryId(Integer consigneeCountryId) {
        this.consigneeCountryId = consigneeCountryId;
    }

    public String getOrderJson() {
        return orderJson;
    }

    public void setOrderJson(String orderJson) {
        this.orderJson = orderJson;
    }

    public String getConsigneeTown() {
        return consigneeTown;
    }

    public void setConsigneeTown(String consigneeTown) {
        this.consigneeTown = consigneeTown;
    }

    public Integer getConsigneeTownId() {
        return consigneeTownId;
    }

    public void setConsigneeTownId(Integer consigneeTownId) {
        this.consigneeTownId = consigneeTownId;
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

    public String getConsigneeTelephone() {
        return consigneeTelephone;
    }

    public void setConsigneeTelephone(String consigneeTelephone) {
        this.consigneeTelephone = consigneeTelephone;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TradeDO tradeDO = (TradeDO) o;

        return new EqualsBuilder()
                .append(tradeId, tradeDO.tradeId)
                .append(tradeSn, tradeDO.tradeSn)
                .append(memberId, tradeDO.memberId)
                .append(memberName, tradeDO.memberName)
                .append(paymentMethodId, tradeDO.paymentMethodId)
                .append(paymentPluginId, tradeDO.paymentPluginId)
                .append(paymentMethodName, tradeDO.paymentMethodName)
                .append(paymentType, tradeDO.paymentType)
                .append(totalPrice, tradeDO.totalPrice)
                .append(goodsPrice, tradeDO.goodsPrice)
                .append(freightPrice, tradeDO.freightPrice)
                .append(discountPrice, tradeDO.discountPrice)
                .append(consigneeId, tradeDO.consigneeId)
                .append(consigneeName, tradeDO.consigneeName)
                .append(consigneeCountry, tradeDO.consigneeCountry)
                .append(consigneeCountryId, tradeDO.consigneeCountryId)
                .append(consigneeProvince, tradeDO.consigneeProvince)
                .append(consigneeProvinceId, tradeDO.consigneeProvinceId)
                .append(consigneeCity, tradeDO.consigneeCity)
                .append(consigneeCityId, tradeDO.consigneeCityId)
                .append(consigneeCounty, tradeDO.consigneeCounty)
                .append(consigneeCountyId, tradeDO.consigneeCountyId)
                .append(consigneeTown, tradeDO.consigneeTown)
                .append(consigneeTownId, tradeDO.consigneeTownId)
                .append(consigneeAddress, tradeDO.consigneeAddress)
                .append(consigneeMobile, tradeDO.consigneeMobile)
                .append(consigneeTelephone, tradeDO.consigneeTelephone)
                .append(createTime, tradeDO.createTime)
                .append(orderJson, tradeDO.orderJson)
                .append(tradeStatus, tradeDO.tradeStatus)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(tradeId)
                .append(tradeSn)
                .append(memberId)
                .append(memberName)
                .append(paymentMethodId)
                .append(paymentPluginId)
                .append(paymentMethodName)
                .append(paymentType)
                .append(totalPrice)
                .append(goodsPrice)
                .append(freightPrice)
                .append(discountPrice)
                .append(consigneeId)
                .append(consigneeName)
                .append(consigneeCountry)
                .append(consigneeCountryId)
                .append(consigneeProvince)
                .append(consigneeProvinceId)
                .append(consigneeCity)
                .append(consigneeCityId)
                .append(consigneeCounty)
                .append(consigneeCountyId)
                .append(consigneeTown)
                .append(consigneeTownId)
                .append(consigneeAddress)
                .append(consigneeMobile)
                .append(consigneeTelephone)
                .append(createTime)
                .append(orderJson)
                .append(tradeStatus)
                .toHashCode();
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
                ", consigneeCountryId=" + consigneeCountryId +
                ", consigneeProvince='" + consigneeProvince + '\'' +
                ", consigneeProvinceId=" + consigneeProvinceId +
                ", consigneeCity='" + consigneeCity + '\'' +
                ", consigneeCityId=" + consigneeCityId +
                ", consigneeCounty='" + consigneeCounty + '\'' +
                ", consigneeCountyId=" + consigneeCountyId +
                ", consigneeTown='" + consigneeTown + '\'' +
                ", consigneeTownId=" + consigneeTownId +
                ", consigneeAddress='" + consigneeAddress + '\'' +
                ", consigneeMobile='" + consigneeMobile + '\'' +
                ", consigneeTelephone='" + consigneeTelephone + '\'' +
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

        this.setConsigneeProvince(consignee.getProvince());
        this.setConsigneeCity(consignee.getCity());
        this.setConsigneeCounty(consignee.getCounty());

        this.setConsigneeProvinceId(consignee.getProvinceId());
        this.setConsigneeCityId(consignee.getCityId());
        this.setConsigneeCountyId(consignee.getCountyId());
        this.setConsigneeTown(consignee.getTown());

        this.setTradeSn(tradeVO.getTradeSn());
        this.setPaymentType(tradeVO.getPaymentType());

        //交易状态
        this.setTradeStatus(TradeStatusEnum.NEW.value());
        this.setMemberId(tradeVO.getMemberId());
        this.setMemberName(tradeVO.getMemberName());
    }

}
