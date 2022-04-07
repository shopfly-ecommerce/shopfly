/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.groupbuy.model.dos;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;


/**
 * 团购活动表实体
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
     * 活动Id
     */
    @Id(name = "act_id")
    @ApiModelProperty(hidden = true)
    private Integer actId;

    /**
     * 活动名称
     */
    @Column(name = "act_name")
    @ApiModelProperty(name = "act_name", value = "活动名称", required = false)
    private String actName;

    /**
     * 活动开启时间
     */
    @Column(name = "start_time")
    @ApiModelProperty(name = "start_time", value = "活动开启时间", required = false)
    private Long startTime;

    /**
     * 团购结束时间
     */
    @Column(name = "end_time")
    @ApiModelProperty(name = "end_time", value = "团购结束时间", required = false)
    private Long endTime;

    /**
     * 团购报名截止时间
     */
    @Column(name = "join_end_time")
    @ApiModelProperty(name = "join_end_time", value = "团购报名截止时间", required = false)
    private Long joinEndTime;

    /**
     * 团购添加时间
     */
    @Column(name = "add_time")
    @ApiModelProperty(name = "add_time", value = "团购添加时间", required = false)
    private Long addTime;

    /**
     * 团购活动标签Id
     */
    @Column(name = "act_tag_id")
    @ApiModelProperty(name = "act_tag_id", value = "团购活动标签Id", required = false)
    private Integer actTagId;

    /**
     * 参与团购商品数量
     */
    @Column(name = "goods_num")
    @ApiModelProperty(name = "goods_num", value = "参与团购商品数量", required = false)
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