/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.minus.model.dos;

import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.enation.app.javashop.framework.database.annotation.Table;
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
 * 单品立减实体
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
     * 单品立减活动id
     */
    @Id(name = "minus_id")
    @ApiModelProperty(hidden = true)
    private Integer minusId;

    /**
     * 单品立减金额
     */
    @Column(name = "single_reduction_value")
    @Min(value = 0, message = "立减金额不能为负数")
    @ApiModelProperty(name = "single_reduction_value", value = "单品立减金额", required = false)
    private Double singleReductionValue;

    /**
     * 起始时间
     */
    @Column(name = "start_time")
    @NotNull(message = "活动起始时间必填")
    @Min(value = 0, message = "活动起始时间不正确")
    @ApiModelProperty(name = "start_time", value = "起始时间", required = true)
    private Long startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    @NotNull(message = "活动结束时间必填")
    @Min(value = 0, message = "活动结束时间不正确")
    @ApiModelProperty(name = "end_time", value = "结束时间", required = true)
    private Long endTime;

    @Column(name = "start_time_str")
    @ApiModelProperty(name = "start_time_str", value = "起始时间字符串", required = false)
    private String startTimeStr;

    @Column(name = "end_time_str")
    @ApiModelProperty(name = "end_time_str", value = "结束时间字符串", required = false)
    private String endTimeStr;

    /**
     * 单品立减活动标题
     */
    @Column(name = "title")
    @NotEmpty(message = "标题必填")
    @ApiModelProperty(name = "title", value = "单品立减活动标题", required = false)
    private String title;

    /**
     * 商品参与方式
     */
    @Column(name = "range_type")
    @NotNull(message = "请选择商品参与方式")
    @Min(value = 1, message = "商品参与方式值不正确")
    @Max(value = 2, message = "商品参与方式值不正确")
    @ApiModelProperty(name = "range_type", value = "商品参与方式,全部商品：1，部分商品：2", required = true)
    private Integer rangeType;

    /**
     * 是否停用
     */
    @Column(name = "disabled")
    @ApiModelProperty(name = "disabled", value = "是否停用", required = false)
    private Integer disabled;

    /**
     * 描述
     */
    @Column(name = "description")
    @ApiModelProperty(name = "description", value = "描述", required = false)
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
