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
package cloud.shopfly.b2c.core.trade.cart.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Activities of theVo
 *
 * @author Snow
 * @version v1.0
 * 2017years08month24day14:48:36
 * @since v6.4
 */
@ApiModel(description = "Activities in shopping cartVo")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CartPromotionVo implements Serializable {


    private static final long serialVersionUID = 1867982008597357312L;

    @ApiModelProperty(value = "Activity start time")
    private Long startTime;

    @ApiModelProperty(value = "End time")
    private Long endTime;

    @ApiModelProperty(value = "activityid")
    private Integer activityId;

    @ApiModelProperty(value = "Active tool type")
    private String promotionType;

    @ApiModelProperty(value = "The name of the event")
    private String title;

    @ApiModelProperty(value = "Check whether to participate in this activity,1As a0For no")
    private Integer isCheck;

    /**
     * Some of the activities,There is a separate number of activities, which is the number of remaining activities.
     */
    @ApiModelProperty(value = "Quantity of remaining sold space")
    private Integer remianQuantity;

    @Override
    public String toString() {
        return "CartPromotionVo{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", activityId=" + activityId +
                ", promotionType='" + promotionType + '\'' +
                ", title='" + title + '\'' +
                ", isCheck=" + isCheck +
                ", remianQuantity=" + remianQuantity +
                '}';
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

    public Integer getIsCheck() {
        if (isCheck == null) {
            isCheck = 0;
        }
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public Integer getRemianQuantity() {
        return remianQuantity;
    }

    public void setRemianQuantity(Integer remianQuantity) {
        this.remianQuantity = remianQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CartPromotionVo that = (CartPromotionVo) o;

        return new EqualsBuilder()
                .append(startTime, that.startTime)
                .append(endTime, that.endTime)
                .append(activityId, that.activityId)
                .append(promotionType, that.promotionType)
                .append(title, that.title)
                .append(isCheck, that.isCheck)
                .append(remianQuantity, that.remianQuantity)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(startTime)
                .append(endTime)
                .append(activityId)
                .append(promotionType)
                .append(title)
                .append(isCheck)
                .append(remianQuantity)
                .toHashCode();
    }
}
