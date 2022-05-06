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
package cloud.shopfly.b2c.core.promotion.pintuan.model;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created by kingapex on 2019-01-22.
 * 拼团商品信息VO
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2019-01-22
 */
public class PinTuanGoodsVO extends PintuanGoodsDO {

    @ApiModelProperty(name = "required_num", value = "成团人数")
    private Integer requiredNum;

    @ApiModelProperty(name = "time_left", value = "剩余时间，秒数")
    private Long timeLeft;

    @ApiModelProperty(name = "promotion_rule", value = "拼团规则")
    private String promotionRule;

    @ApiModelProperty(name = "limit_num", value = "限购数量")
    private Integer limitNum;

    @ApiModelProperty(hidden = true, value = "结束时间戳")
    private Long endTime;

    @ApiModelProperty(name = "enable_quantity", value = "可用库存")
    private Integer enableQuantity;

    public Integer getRequiredNum() {
        return requiredNum;
    }

    public void setRequiredNum(Integer requiredNum) {
        this.requiredNum = requiredNum;
    }

    public Long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Long timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getPromotionRule() {
        return promotionRule;
    }

    public void setPromotionRule(String promotionRule) {
        this.promotionRule = promotionRule;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getEnableQuantity() {
        return enableQuantity;
    }

    public void setEnableQuantity(Integer enableQuantity) {
        this.enableQuantity = enableQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PinTuanGoodsVO that = (PinTuanGoodsVO) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(requiredNum, that.requiredNum)
                .append(timeLeft, that.timeLeft)
                .append(promotionRule, that.promotionRule)
                .append(limitNum, that.limitNum)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(requiredNum)
                .append(timeLeft)
                .append(promotionRule)
                .append(limitNum)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "PinTuanGoodsVO{" +
                "requiredNum=" + requiredNum +
                ", timeLeft=" + timeLeft +
                ", promotionRule='" + promotionRule + '\'' +
                ", limitNum=" + limitNum +
                "} " + super.toString();
    }
}
