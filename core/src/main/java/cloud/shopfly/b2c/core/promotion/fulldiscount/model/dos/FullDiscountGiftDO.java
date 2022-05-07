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
package cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos;

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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * Full preferential gift entity
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 17:34:46
 */
@Table(name="es_full_discount_gift")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FullDiscountGiftDO implements Serializable {

    private static final long serialVersionUID = 953152013470095L;

    /**The giftsid*/
    @Id(name = "gift_id")
    @ApiModelProperty(hidden=true)
    private Integer giftId;

    /**Name of gift*/
    @Column(name = "gift_name")
    @ApiModelProperty(name="gift_name",value="Name of gift",required=false)
    private String giftName;

    /**Gift amount*/
    @Column(name = "gift_price")
    @ApiModelProperty(name="gift_price",value="Gift amount",required=false)
    private Double giftPrice;

    /**Gifts pictures*/
    @Column(name = "gift_img")
    @NotEmpty(message = "Please upload pictures of the gifts")
    @ApiModelProperty(name="gift_img",value="Gifts pictures",required=false)
    private String giftImg;

    /**Inventory*/
    @Column(name = "actual_store")
    @NotNull(message = "Please fill in the inventory")
    @ApiModelProperty(name="actual_store",value="Inventory",required=false)
    private Integer actualStore;

    /**Gift type*/
    @Column(name = "gift_type")
    @ApiModelProperty(name="gift_type",value="Gift type",required=false)
    private Integer giftType;

    /**Available*/
    @Column(name = "enable_store")
    @ApiModelProperty(name="enable_store",value="Available",required=false)
    private Integer enableStore;

    /**The activity time*/
    @Column(name = "create_time")
    @ApiModelProperty(name="create_time",value="The activity time",required=false)
    private Long createTime;

    /**Activities of goodsid*/
    @Column(name = "goods_id")
    @ApiModelProperty(name="goods_id",value="Activities of goodsid",required=false)
    private Integer goodsId;

    /**Whether to disable*/
    @Column(name = "disabled")
    @ApiModelProperty(name="disabled",value="Whether to disable",required=false)
    private Integer disabled;

    @PrimaryKeyField
    public Integer getGiftId() {
        return giftId;
    }
    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }
    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Double getGiftPrice() {
        return giftPrice;
    }
    public void setGiftPrice(Double giftPrice) {
        this.giftPrice = giftPrice;
    }

    public String getGiftImg() {
        return giftImg;
    }
    public void setGiftImg(String giftImg) {
        this.giftImg = giftImg;
    }

    public Integer getGiftType() {
        return giftType;
    }
    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }

    public Integer getActualStore() {
        return actualStore;
    }
    public void setActualStore(Integer actualStore) {
        this.actualStore = actualStore;
    }

    public Integer getEnableStore() {
        return enableStore;
    }
    public void setEnableStore(Integer enableStore) {
        this.enableStore = enableStore;
    }

    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getDisabled() {
        return disabled;
    }
    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "FullDiscountGiftDO{" +
                "giftId=" + giftId +
                ", giftName='" + giftName + '\'' +
                ", giftPrice=" + giftPrice +
                ", giftImg='" + giftImg + '\'' +
                ", actualStore=" + actualStore +
                ", giftType=" + giftType +
                ", enableStore=" + enableStore +
                ", createTime=" + createTime +
                ", goodsId=" + goodsId +
                ", disabled=" + disabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        FullDiscountGiftDO giftDO = (FullDiscountGiftDO) o;

        return new EqualsBuilder()
                .append(giftId, giftDO.giftId)
                .append(giftName, giftDO.giftName)
                .append(giftPrice, giftDO.giftPrice)
                .append(giftImg, giftDO.giftImg)
                .append(actualStore, giftDO.actualStore)
                .append(giftType, giftDO.giftType)
                .append(enableStore, giftDO.enableStore)
                .append(createTime, giftDO.createTime)
                .append(goodsId, giftDO.goodsId)
                .append(disabled, giftDO.disabled)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(giftId)
                .append(giftName)
                .append(giftPrice)
                .append(giftImg)
                .append(actualStore)
                .append(giftType)
                .append(enableStore)
                .append(createTime)
                .append(goodsId)
                .append(disabled)
                .toHashCode();
    }
}
