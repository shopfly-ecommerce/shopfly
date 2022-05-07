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
package cloud.shopfly.b2c.core.promotion.exchange.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;


/**
 * Points exchange entity
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 11:47:18
 */
@Table(name = "es_exchange")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExchangeDO implements Serializable {

    private static final long serialVersionUID = 2173041743628504L;

    /**
     * A primary key
     */
    @Id(name = "exchange_id")
    @ApiModelProperty(hidden = true)
    private Integer exchangeId;

    /**
     * productid
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goodsId", value = "productid", required = false)
    private Integer goodsId;

    /**
     * Goods belong to the classification of points
     */
    @Column(name = "category_id")
    @ApiModelProperty(name = "categoryId", value = "Goods belong to the classification of points", required = false)
    private Integer categoryId;

    /**
     * Whether exchange is allowed
     */
    @Column(name = "enable_exchange")
    @ApiModelProperty(name = "enable_exchange", value = "Whether exchange is allowed.0:No,1:is", required = false, allowableValues = "0,1")
    private Integer enableExchange;

    /**
     * Amount required for exchange
     */
    @Column(name = "exchange_money")
    @ApiModelProperty(name = "exchange_money", value = "Amount required for exchange")
    private Double exchangeMoney;

    /**
     * Redemption points required
     */
    @Column(name = "exchange_point")
    @ApiModelProperty(name = "exchange_point", value = "Redemption points required")
    private Integer exchangePoint;

    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "Name")
    private String goodsName;

    @Column(name = "goods_price")
    @ApiModelProperty(name = "goods_price", value = "Product  price")
    private Double goodsPrice;

    @Column(name = "goods_img")
    @ApiModelProperty(name = "goods_img", value = "Commodity images")
    private String goodsImg;


    @PrimaryKeyField
    public Integer getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Integer exchangeId) {
        this.exchangeId = exchangeId;
    }


    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getEnableExchange() {
        return enableExchange;
    }

    public void setEnableExchange(Integer enableExchange) {
        this.enableExchange = enableExchange;
    }

    public Double getExchangeMoney() {
        return exchangeMoney;
    }

    public void setExchangeMoney(Double exchangeMoney) {
        this.exchangeMoney = exchangeMoney;
    }

    public Integer getExchangePoint() {
        return exchangePoint;
    }

    public void setExchangePoint(Integer exchangePoint) {
        this.exchangePoint = exchangePoint;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExchangeDO that = (ExchangeDO) o;

        return new EqualsBuilder()
                .append(exchangeId, that.exchangeId)
                .append(goodsId, that.goodsId)
                .append(categoryId, that.categoryId)
                .append(enableExchange, that.enableExchange)
                .append(exchangeMoney, that.exchangeMoney)
                .append(exchangePoint, that.exchangePoint)
                .append(goodsName, that.goodsName)
                .append(goodsPrice, that.goodsPrice)
                .append(goodsImg, that.goodsImg)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(exchangeId)
                .append(goodsId)
                .append(categoryId)
                .append(enableExchange)
                .append(exchangeMoney)
                .append(exchangePoint)
                .append(goodsName)
                .append(goodsPrice)
                .append(goodsImg)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "ExchangeDO{" +
                "settingId=" + exchangeId +
                ", goodsId=" + goodsId +
                ", categoryId=" + categoryId +
                ", enableExchange=" + enableExchange +
                ", exchangeMoney=" + exchangeMoney +
                ", exchangePoint=" + exchangePoint +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsImg='" + goodsImg + '\'' +
                '}';
    }
}
