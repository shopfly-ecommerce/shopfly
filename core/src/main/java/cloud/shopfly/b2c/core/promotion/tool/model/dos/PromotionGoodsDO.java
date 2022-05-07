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
package cloud.shopfly.b2c.core.promotion.tool.model.dos;

import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillApplyDO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * Entity of effective active commodity comparison table
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-27 15:17:47
 */
@Table(name = "es_promotion_goods")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PromotionGoodsDO implements Serializable {

    private static final long serialVersionUID = 686823101861419L;

    /**
     * Commodity controlsID
     */
    @Id(name = "pg_id")
    @ApiModelProperty(hidden = true)
    private Integer pgId;

    /**
     * productid
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "productid", required = false)
    private Integer goodsId;

    /**
     * goodsid
     */
    @Column(name = "product_id")
    @ApiModelProperty(name = "product_id", value = "goodsid", required = false)
    private Integer productId;

    /**
     * Activity start time
     */
    @Column(name = "start_time")
    @ApiModelProperty(name = "start_time", value = "Activity start time", required = false)
    private Long startTime;

    /**
     * End time
     */
    @Column(name = "end_time")
    @ApiModelProperty(name = "end_time", value = "End time", required = false)
    private Long endTime;

    /**
     * activityid
     */
    @Column(name = "activity_id")
    @ApiModelProperty(name = "activity_id", value = "activityid", required = false)
    private Integer activityId;

    /**
     * Types of promotional tools
     */
    @Column(name = "promotion_type")
    @ApiModelProperty(name = "promotion_type", value = "Types of promotional tools", required = false)
    private String promotionType;

    /**
     * Activity title
     */
    @Column(name = "title")
    @ApiModelProperty(name = "title", value = "Activity title", required = false)
    private String title;

    /**
     * The number of goods participating in the event
     */
    @Column(name = "num")
    @ApiModelProperty(name = "num", value = "The number of goods participating in the event", required = false)
    private Integer num;

    /**
     * The price of goods at the time of the event
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "The price of goods at the time of the event", required = false)
    private Double price;

    public PromotionGoodsDO(SeckillApplyDO seckillApplyDO, long startTime, long endTime) {

        this.setTitle("flash");
        this.setGoodsId(seckillApplyDO.getGoodsId());
        this.setPromotionType(PromotionTypeEnum.SECKILL.name());
        this.setActivityId(seckillApplyDO.getApplyId());
        this.setNum(seckillApplyDO.getSoldQuantity());
        this.setPrice(seckillApplyDO.getPrice());
        // The start time of commodity activity is the period of participation of the current commodity
        this.setStartTime(startTime);
        this.setEndTime(endTime);
    }

    public PromotionGoodsDO() {
    }

    @PrimaryKeyField
    public Integer getPgId() {
        return pgId;
    }

    public void setPgId(Integer pgId) {
        this.pgId = pgId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    @Override
    public String toString() {
        return "PromotionGoodsDO{" +
                "pgId=" + pgId +
                ", goodsId=" + goodsId +
                ", productId=" + productId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", activityId=" + activityId +
                ", promotionType='" + promotionType + '\'' +
                ", title='" + title + '\'' +
                ", num=" + num +
                ", price=" + price +
                '}';
    }
}
