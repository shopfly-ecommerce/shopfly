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
package cloud.shopfly.b2c.core.promotion.groupbuy.model.dos;

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
 * Group purchase activity table entity
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 11:52:14
 */
@Table(name = "es_groupbuy_active")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GroupbuyActiveDO implements Serializable {

    private static final long serialVersionUID = 8396241558782003L;

    /**
     * activityId
     */
    @Id(name = "act_id")
    @ApiModelProperty(hidden = true)
    private Integer actId;

    /**
     * The name of the event
     */
    @Column(name = "act_name")
    @ApiModelProperty(name = "act_name", value = "The name of the event", required = false)
    private String actName;

    /**
     * Activity opening time
     */
    @Column(name = "start_time")
    @ApiModelProperty(name = "start_time", value = "Activity opening time", required = false)
    private Long startTime;

    /**
     * Group purchase end time
     */
    @Column(name = "end_time")
    @ApiModelProperty(name = "end_time", value = "Group purchase end time", required = false)
    private Long endTime;

    /**
     * Deadline for group purchase registration
     */
    @Column(name = "join_end_time")
    @ApiModelProperty(name = "join_end_time", value = "Deadline for group purchase registration", required = false)
    private Long joinEndTime;

    /**
     * Add time for group purchase
     */
    @Column(name = "add_time")
    @ApiModelProperty(name = "add_time", value = "Add time for group purchase", required = false)
    private Long addTime;

    /**
     * Group purchase activity tagsId
     */
    @Column(name = "act_tag_id")
    @ApiModelProperty(name = "act_tag_id", value = "Group purchase activity tagsId", required = false)
    private Integer actTagId;

    /**
     * Participate in group purchase quantity
     */
    @Column(name = "goods_num")
    @ApiModelProperty(name = "goods_num", value = "Participate in group purchase quantity", required = false)
    private Integer goodsNum;


    @Override
    public String toString() {
        return "GroupbuyActiveDO{" +
                "actId=" + actId +
                ", actName='" + actName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", joinEndTime=" + joinEndTime +
                ", addTime=" + addTime +
                ", actTagId=" + actTagId +
                ", goodsNum=" + goodsNum +
                '}';
    }

    @PrimaryKeyField
    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
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

    public Long getJoinEndTime() {
        return joinEndTime;
    }

    public void setJoinEndTime(Long joinEndTime) {
        this.joinEndTime = joinEndTime;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Integer getActTagId() {
        return actTagId;
    }

    public void setActTagId(Integer actTagId) {
        this.actTagId = actTagId;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GroupbuyActiveDO activeDO = (GroupbuyActiveDO) o;

        return new EqualsBuilder()
                .append(actId, activeDO.actId)
                .append(actName, activeDO.actName)
                .append(startTime, activeDO.startTime)
                .append(endTime, activeDO.endTime)
                .append(joinEndTime, activeDO.joinEndTime)
                .append(addTime, activeDO.addTime)
                .append(actTagId, activeDO.actTagId)
                .append(goodsNum, activeDO.goodsNum)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(actId)
                .append(actName)
                .append(startTime)
                .append(endTime)
                .append(joinEndTime)
                .append(addTime)
                .append(actTagId)
                .append(goodsNum)
                .toHashCode();
    }
}
