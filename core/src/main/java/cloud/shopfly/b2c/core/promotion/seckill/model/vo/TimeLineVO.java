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
package cloud.shopfly.b2c.core.promotion.seckill.model.vo;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Schedule list element
 *
 * @author Snow create in 2018/7/30
 * @version v2.0
 * @since v7.0.0
 */
public class TimeLineVO {


    @ApiModelProperty(value = "The start time from this group of moments, if0The delegate is in progress." +
            "For example,：If this group of moments is18Point, the present time is zero16Point, then this time is in units of seconds2Hour time")
    private Long distanceTime;

    @ApiModelProperty(value = "Time words")
    private String timeText;

    @ApiModelProperty(value = "How much time is the difference from the start of the next group time, only the time in progress display is used.")
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
