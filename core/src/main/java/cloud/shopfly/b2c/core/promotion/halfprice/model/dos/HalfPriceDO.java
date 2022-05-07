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
package cloud.shopfly.b2c.core.promotion.halfprice.model.dos;

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
 * The second half price entity
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 19:53:42
 */
@Table(name = "es_half_price")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HalfPriceDO implements Serializable {

    private static final long serialVersionUID = 6857594636337885L;

    /**
     * The second half price eventID
     */
    @Id(name = "hp_id")
    @ApiModelProperty(hidden = true)
    private Integer hpId;

    /**
     * Starting time
     */
    @Column(name = "start_time")
    @NotNull(message = "Please fill in the start time of the activity")
    @Min(value = 0, message = "The activity start time is incorrect")
    @ApiModelProperty(name = "start_time", value = "Starting time", required = true)
    private Long startTime;

    /**
     * The end of time
     */
    @Column(name = "end_time")
    @NotNull(message = "Please fill in the deadline")
    @Min(value = 0, message = "The activity end time is incorrect")
    @ApiModelProperty(name = "end_time", value = "The end of time", required = true)
    private Long endTime;

    /**
     * Activity title
     */
    @Column(name = "title")
    @NotEmpty(message = "Please fill in the activity title")
    @ApiModelProperty(name = "title", value = "Activity title", required = true)
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
     * Whether discontinuation0.No stopping1.disable
     */
    @Column(name = "disabled")
    @ApiModelProperty(name = "disabled", value = "Whether discontinuation", required = false)
    private Integer disabled;

    /**
     * Activities that
     */
    @Column(name = "description")
    @ApiModelProperty(name = "description", value = "Activities that", required = false)
    private String description;

    @PrimaryKeyField
    public Integer getHpId() {
        return hpId;
    }

    public void setHpId(Integer hpId) {
        this.hpId = hpId;
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


    @Override
    public String toString() {
        return "HalfPriceDO{" +
                "hpId=" + hpId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
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

        HalfPriceDO that = (HalfPriceDO) o;

        return new EqualsBuilder()
                .append(hpId, that.hpId)
                .append(startTime, that.startTime)
                .append(endTime, that.endTime)
                .append(title, that.title)
                .append(rangeType, that.rangeType)
                .append(disabled, that.disabled)
                .append(description, that.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(hpId)
                .append(startTime)
                .append(endTime)
                .append(title)
                .append(rangeType)
                .append(disabled)
                .append(description)
                .toHashCode();
    }
}
