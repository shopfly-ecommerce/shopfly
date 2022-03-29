/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.seckill.model.vo;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 时刻表列表元素
 *
 * @author Snow create in 2018/7/30
 * @version v2.0
 * @since v7.0.0
 */
public class TimeLineVO {


    @ApiModelProperty(value = "距离此组时刻的开始时间，如果为0代表正在进行中。" +
            "例如：如果此组时刻为18点，现在的时间为16点，那么此时间是已秒为单位的2小时的时间")
    private Long distanceTime;

    @ApiModelProperty(value = "时刻文字")
    private String timeText;

    @ApiModelProperty(value = "距离下组时刻开始，还差多少时间，仅正在进行中的时刻展示使用。")
    private Long nextDistanceTime;


    public Long getDistanceTime() {
        return distanceTime;
    }

    public void setDistanceTime(Long distanceTime) {
        this.distanceTime = distanceTime;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public Long getNextDistanceTime() {
        return nextDistanceTime;
    }

    public void setNextDistanceTime(Long nextDistanceTime) {
        this.nextDistanceTime = nextDistanceTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        TimeLineVO that = (TimeLineVO) o;

        return new EqualsBuilder()
                .append(distanceTime, that.distanceTime)
                .append(timeText, that.timeText)
                .append(nextDistanceTime, that.nextDistanceTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(distanceTime)
                .append(timeText)
                .append(nextDistanceTime)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "TimeLineVO{" +
                "distanceTime=" + distanceTime +
                ", timeText='" + timeText + '\'' +
                ", nextDistanceTime='" + nextDistanceTime + '\'' +
                '}';
    }
}
