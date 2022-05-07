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
package cloud.shopfly.b2c.core.promotion.tool.model.dto;

import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyActiveDO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import io.swagger.annotations.ApiModelProperty;

/**
 * Promotional informationDTO
 *
 * @author Snow create in 2018/4/16
 * @version v2.0
 * @since v7.0.0
 */
public class PromotionDetailDTO {

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

    public PromotionDetailDTO(GroupbuyActiveDO activeDO) {
        this.startTime = activeDO.getStartTime();
        this.endTime = activeDO.getEndTime();
        this.activityId = activeDO.getActId();
        this.promotionType = PromotionTypeEnum.GROUPBUY.name();
        this.title = activeDO.getActName();
    }

    public PromotionDetailDTO() {
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

    @Override
    public String toString() {
        return "PromotionDetailDTO{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", activityId=" + activityId +
                ", promotionType='" + promotionType + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
