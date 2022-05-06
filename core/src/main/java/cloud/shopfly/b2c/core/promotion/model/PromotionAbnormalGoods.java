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
 * 反常促销商品
 * 目前用于检查商品参与活动，返回商品与活动关联的对象
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-28 上午5:43
 */
public class PromotionAbnormalGoods {


    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 开始时间
     */
    private Long startTime;


    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 活动类型
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
