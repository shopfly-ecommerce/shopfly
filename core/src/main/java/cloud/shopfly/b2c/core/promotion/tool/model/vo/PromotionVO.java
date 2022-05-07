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
package cloud.shopfly.b2c.core.promotion.tool.model.vo;


import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyGoodsVO;
import cloud.shopfly.b2c.core.promotion.halfprice.model.vo.HalfPriceVO;
import cloud.shopfly.b2c.core.promotion.minus.model.vo.MinusVO;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillGoodsVO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Comparison table of active goods
 *
 * @author Snow
 * @version v1.0 2017years08month22day
 * @since v6.4
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PromotionVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4796645552318671313L;

    @ApiModelProperty(value = "productid")
    private Integer goodsId;

    @ApiModelProperty(value = "Commodity images")
    private String thumbnail;

    @ApiModelProperty(value = "Name")
    private String name;

    @ApiModelProperty(value = "goodsid")
    private Integer skuId;

    @ApiModelProperty(value = "Activity start time")
    private Long startTime;

    @ApiModelProperty(value = "End time")
    private Long endTime;

    @ApiModelProperty(value = "activityid")
    private Integer activityId;

    /**
     * This field identifies the specific activity type
     * This field corresponds to an enumerated value
     *
     * @see PromotionTypeEnum
     */
    @ApiModelProperty(value = "Active tool type")
    private String promotionType;

    @ApiModelProperty(value = "The name of the event")
    private String title;

    @ApiModelProperty(value = "Redemption object")
    private ExchangeDO exchange;

    @ApiModelProperty(value = "Group purchase activity object")
    private GroupbuyGoodsVO groupbuyGoodsVO;

    @ApiModelProperty(value = "Full discount")
    private FullDiscountVO fullDiscountVO;

    @ApiModelProperty(value = "Full of giftsVO")
    private FullDiscountGiftDO fullDiscountGift;

    @ApiModelProperty(value = "Single product reduction activity")
    private MinusVO minusVO;

    @ApiModelProperty(value = "The second half price event")
    private HalfPriceVO halfPriceVO;

    @ApiModelProperty(value = "Flash sales")
    private SeckillGoodsVO seckillGoodsVO;


    @ApiModelProperty(value = "The number sold out")
    private Integer num;

    @ApiModelProperty(value = "Activity price")
    private Double price;

    public PromotionVO() {
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ExchangeDO getExchange() {
        return exchange;
    }

    public void setExchange(ExchangeDO exchange) {
        this.exchange = exchange;
    }

    public FullDiscountVO getFullDiscountVO() {
        return fullDiscountVO;
    }

    public void setFullDiscountVO(FullDiscountVO fullDiscountVO) {
        this.fullDiscountVO = fullDiscountVO;
    }

    public FullDiscountGiftDO getFullDiscountGift() {
        return fullDiscountGift;
    }

    public void setFullDiscountGift(FullDiscountGiftDO fullDiscountGift) {
        this.fullDiscountGift = fullDiscountGift;
    }

    public MinusVO getMinusVO() {
        return minusVO;
    }

    public void setMinusVO(MinusVO minusVO) {
        this.minusVO = minusVO;
    }

    public HalfPriceVO getHalfPriceVO() {
        return halfPriceVO;
    }

    public void setHalfPriceVO(HalfPriceVO halfPriceVO) {
        this.halfPriceVO = halfPriceVO;
    }

    public SeckillGoodsVO getSeckillGoodsVO() {
        return seckillGoodsVO;
    }

    public void setSeckillGoodsVO(SeckillGoodsVO seckillGoodsVO) {

        this.seckillGoodsVO = seckillGoodsVO;
    }


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public GroupbuyGoodsVO getGroupbuyGoodsVO() {
        return groupbuyGoodsVO;
    }

    public void setGroupbuyGoodsVO(GroupbuyGoodsVO groupbuyGoodsVO) {
        this.groupbuyGoodsVO = groupbuyGoodsVO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PromotionVO that = (PromotionVO) o;

        return new EqualsBuilder()
                .append(goodsId, that.goodsId)
                .append(thumbnail, that.thumbnail)
                .append(name, that.name)
                .append(skuId, that.skuId)
                .append(startTime, that.startTime)
                .append(endTime, that.endTime)
                .append(activityId, that.activityId)
                .append(promotionType, that.promotionType)
                .append(title, that.title)
                .append(exchange, that.exchange)
                .append(groupbuyGoodsVO, that.groupbuyGoodsVO)
                .append(fullDiscountVO, that.fullDiscountVO)
                .append(fullDiscountGift, that.fullDiscountGift)
                .append(minusVO, that.minusVO)
                .append(halfPriceVO, that.halfPriceVO)
                .append(seckillGoodsVO, that.seckillGoodsVO)
                .append(num, that.num)
                .append(price, that.price)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(goodsId)
                .append(thumbnail)
                .append(name)
                .append(skuId)
                .append(startTime)
                .append(endTime)
                .append(activityId)
                .append(promotionType)
                .append(title)
                .append(exchange)
                .append(groupbuyGoodsVO)
                .append(fullDiscountVO)
                .append(fullDiscountGift)
                .append(minusVO)
                .append(halfPriceVO)
                .append(seckillGoodsVO)
                .append(num)
                .append(price)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "PromotionVO{" +
                "goodsId=" + goodsId +
                ", thumbnail='" + thumbnail + '\'' +
                ", name='" + name + '\'' +
                ", skuId=" + skuId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", activityId=" + activityId +
                ", promotionType='" + promotionType + '\'' +
                ", title='" + title + '\'' +
                ", exchange=" + exchange +
                ", groupbuyGoodsVO=" + groupbuyGoodsVO +
                ", fullDiscountVO=" + fullDiscountVO +
                ", fullDiscountGift=" + fullDiscountGift +
                ", minusVO=" + minusVO +
                ", halfPriceVO=" + halfPriceVO +
                ", seckillGoodsVO=" + seckillGoodsVO +
                ", num=" + num +
                ", price=" + price +
                '}';
    }
}
