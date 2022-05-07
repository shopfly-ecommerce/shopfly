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
package cloud.shopfly.b2c.core.promotion.seckill.model.dos;

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
 * Flash sale application entity
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 17:30:09
 */
@Table(name = "es_seckill_apply")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SeckillApplyDO implements Serializable {

    private static final long serialVersionUID = 2980175459354215L;

    /**
     * A primary keyID
     */
    @Id(name = "apply_id")
    @ApiModelProperty(hidden = true)
    private Integer applyId;

    /**
     * activityid
     */
    @Column(name = "seckill_id")
    @ApiModelProperty(name = "seckill_id", value = "activityid", required = true)
    private Integer seckillId;

    /**
     * moment
     */
    @Column(name = "time_line")
    @ApiModelProperty(name = "time_line", value = "moment")
    private Integer timeLine;

    /**
     * Commencement Date
     */
    @Column(name = "start_day")
    @ApiModelProperty(name = "start_day", value = "Commencement Date")
    private Long startDay;

    /**
     * productID
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "productID")
    private Integer goodsId;

    /**
     * Name
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "Name")
    private String goodsName;

    /**
     * Price
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "Price")
    private Double price;

    /**
     * The number sold out
     */
    @Column(name = "sold_quantity")
    @ApiModelProperty(name = "sold_quantity", value = "The number sold out")
    private Integer soldQuantity;

    @Column(name = "sales_num")
    @ApiModelProperty(name = "sales_num", value = "The number sold")
    private Integer salesNum;

    @Column(name = "original_price")
    @ApiModelProperty(name = "original_price", value = "Original price of goods")
    private Double originalPrice;


    @PrimaryKeyField
    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
    }

    public Integer getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(Integer timeLine) {
        this.timeLine = timeLine;
    }

    public Long getStartDay() {
        return startDay;
    }

    public void setStartDay(Long startDay) {
        this.startDay = startDay;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getSalesNum() {
        if (salesNum == null) {
            salesNum = 0;
        }
        return salesNum;
    }

    public void setSalesNum(Integer salesNum) {
        this.salesNum = salesNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeckillApplyDO applyDO = (SeckillApplyDO) o;

        return new EqualsBuilder()
                .append(applyId, applyDO.applyId)
                .append(seckillId, applyDO.seckillId)
                .append(timeLine, applyDO.timeLine)
                .append(startDay, applyDO.startDay)
                .append(goodsId, applyDO.goodsId)
                .append(goodsName, applyDO.goodsName)
                .append(price, applyDO.price)
                .append(soldQuantity, applyDO.soldQuantity)
                .append(salesNum, applyDO.salesNum)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(applyId)
                .append(seckillId)
                .append(timeLine)
                .append(startDay)
                .append(goodsId)
                .append(goodsName)
                .append(price)
                .append(soldQuantity)
                .append(salesNum)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SeckillApplyDO{" +
                "applyId=" + applyId +
                ", seckillId=" + seckillId +
                ", timeLine=" + timeLine +
                ", startDay=" + startDay +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", price=" + price +
                ", soldQuantity=" + soldQuantity +
                ", salesNum=" + salesNum +
                '}';
    }
}
