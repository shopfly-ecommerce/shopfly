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
package cloud.shopfly.b2c.core.promotion.model;

/**
 * Anomalous promotion
 * Currently used to check the participation of goods in the activity, return goods and activities associated with the object
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-28 In the morning5:43
 */
public class PromotionAbnormalGoods {


    /**
     * productid
     */
    private Integer goodsId;

    /**
     * Name
     */
    private String goodsName;

    /**
     * The start time
     */
    private Long startTime;


    /**
     * The end of time
     */
    private Long endTime;

    /**
     * The activity type
     */
    private String promotionType;

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

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    @Override
    public String toString() {
        return "PromotionGoodsDTO{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", promotionType='" + promotionType + '\'' +
                '}';
    }
}
