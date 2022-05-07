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
package cloud.shopfly.b2c.core.promotion.minus.model.dos;

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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * Single product immediately reduced entity
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 19:52:27
 */
@Table(name = "es_minus")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MinusDO implements Serializable {

    private static final long serialVersionUID = 7245840976162683L;

    /**
     * Single product reduction activityid
     */
    @Id(name = "minus_id")
    @ApiModelProperty(hidden = true)
    private Integer minusId;

    /**
     * Immediate reduction for each item
     */
    @Column(name = "single_reduction_value")
    @Min(value = 0, message = "The amount of immediate reduction cannot be negative")
    @ApiModelProperty(name = "single_reduction_value", value = "Immediate reduction for each item", required = false)
    private Double singleReductionValue;

    /**
     * Starting time
     */
    @Column(name = "start_time")
    @NotNull(message = "Start time of the activity Mandatory")
    @Min(value = 0, message = "The activity start time is incorrect")
    @ApiModelProperty(name = "start_time", value = "Starting time", required = true)
    private Long startTime;

    /**
     * The end of time
     */
    @Column(name = "end_time")
    @NotNull(message = "End time Mandatory")
    @Min(value = 0, message = "The activity end time is incorrect")
    @ApiModelProperty(name = "end_time", value = "The end of time", required = true)
    private Long endTime;

    @Column(name = "start_time_str")
    @ApiModelProperty(name = "start_time_str", value = "Start time string", required = false)
    private String startTimeStr;

    @Column(name = "end_time_str")
    @ApiModelProperty(name = "end_time_str", value = "End time string", required = false)
    private String endTimeStr;

    /**
     * Single product immediately reduce the activity title
     */
    @Column(name = "title")
    @NotEmpty(message = "The title will be")
    @ApiModelProperty(name = "title", value = "Single product immediately reduce the activity title", required = false)
    private String title;

    /**
     * Mode of Commodity participation
     */
    @Column(name = "range_type")
    @NotNull(message = "Please select the mode of product participation")
    @Min(value = 1, message = "Item participation mode value is incorrect")
    @Max(value = 2, message = "Item participation mode value is incorrect")
    @ApiModelProperty(name = "range_type", value = "Mode of Commodity participation,All the goods：1, some commodities：2", required = true)
    private Integer rangeType;

    /**
     * Whether discontinuation
     */
    @Column(name = "disabled")
    @ApiModelProperty(name = "disabled", value = "Whether discontinuation", required = false)
    private Integer disabled;

    /**
     *  describe
     */
    @Column(name = "description")
    @ApiModelProperty(name = "description", value = " describe", required = false)
    private String description;


    @PrimaryKeyField
    public Integer getMinusId() {
        return minusId;
    }

    public void setMinusId(Integer minusId) {
        this.minusId = minusId;
    }

    public Double getSingleReductionValue() {
        return singleReductionValue;
    }

    public void setSingleReductionValue(Double singleReductionValue) {
        this.singleReductionValue = singleReductionValue;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRangeType() {
        return rangeType;
    }

    public void setRangeType(Integer rangeType) {
        this.rangeType = rangeType;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    @Override
    public String toString() {
        return "MinusDO{" +
                "minusId=" + minusId +
                ", singleReductionValue=" + singleReductionValue +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", startTimeStr='" + startTimeStr + '\'' +
                ", endTimeStr='" + endTimeStr + '\'' +
                ", title='" + title + '\'' +
                ", rangeType=" + rangeType +
                ", disabled=" + disabled +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MinusDO minusDO = (MinusDO) o;

        return new EqualsBuilder()
                .append(minusId, minusDO.minusId)
                .append(singleReductionValue, minusDO.singleReductionValue)
                .append(startTime, minusDO.startTime)
                .append(endTime, minusDO.endTime)
                .append(startTimeStr, minusDO.startTimeStr)
                .append(endTimeStr, minusDO.endTimeStr)
                .append(title, minusDO.title)
                .append(rangeType, minusDO.rangeType)
                .append(disabled, minusDO.disabled)
                .append(description, minusDO.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(minusId)
                .append(singleReductionValue)
                .append(startTime)
                .append(endTime)
                .append(startTimeStr)
                .append(endTimeStr)
                .append(title)
                .append(rangeType)
                .append(disabled)
                .append(description)
                .toHashCode();
    }
}
