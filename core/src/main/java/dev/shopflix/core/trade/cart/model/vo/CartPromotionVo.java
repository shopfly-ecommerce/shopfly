/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 活动的Vo
 *
 * @author Snow
 * @version v1.0
 * 2017年08月24日14:48:36
 * @since v6.4
 */
@ApiModel(description = "购物车中活动Vo")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CartPromotionVo implements Serializable {


    private static final long serialVersionUID = 1867982008597357312L;

    @ApiModelProperty(value = "活动开始时间")
    private Long startTime;

    @ApiModelProperty(value = "活动结束时间")
    private Long endTime;

    @ApiModelProperty(value = "活动id")
    private Integer activityId;

    @ApiModelProperty(value = "活动工具类型")
    private String promotionType;

    @ApiModelProperty(value = "活动名称")
    private String title;

    @ApiModelProperty(value = "是否选中参与这个活动,1为是 0为否")
    private Integer isCheck;

    /**
     * 有些活动,有单独的活动数量，此数量为剩余的活动数量。
     */
    @ApiModelProperty(value = "剩余售空数量")
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
