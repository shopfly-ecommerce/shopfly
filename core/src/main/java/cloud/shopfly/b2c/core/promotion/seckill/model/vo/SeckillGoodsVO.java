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

import cloud.shopfly.b2c.core.promotion.seckill.model.dto.SeckillGoodsDTO;
import cloud.shopfly.b2c.framework.util.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Product details page displayVO
 *
 * @author Snow
 * @version v1.0
 * @since v7.0.0
 */
public class SeckillGoodsVO extends SeckillGoodsDTO {

    protected final Log logger = LogFactory.getLog(this.getClass());
    /**
     *
     */
    private static final long serialVersionUID = -7280906760276180832L;

    @ApiModelProperty(value = "Its not the time, its the hour, like10")
    private Integer seckillStartTime;

    @ApiModelProperty(value = "Seckill start time. This is the timestamp")
    private Long startTime;

    @ApiModelProperty(value = "The time, in seconds, before the end of the activity")
    private Long distanceEndTime;

    @ApiModelProperty(value = "Whether the activity has already started、1:The campaign is ongoing,0:Not at the")
    private Integer isStart;

    @ApiModelProperty(value = "Remaining available quantity")
    private Integer remainQuantity;

    @ApiModelProperty(value = "The time, in seconds, from the start of the activity. If the event starts at10Point, the server time is8Point, how much time before we start")
    private Long distanceStartTime;

    @ApiModelProperty(value = "merchantsID")
    private Integer sellerId;

    @ApiModelProperty(value = "Seconds kill activityID")
    private Integer seckillId;


    public SeckillGoodsVO() {

    }

    /**
     * The constructor
     *
     * @param goods
     */
    public SeckillGoodsVO(SeckillGoodsVO goods) {
        this.setGoodsId(goods.getGoodsId());
        this.setSeckillPrice(goods.getSeckillPrice());
        this.setSoldQuantity(goods.getSoldQuantity());
        this.setOriginalPrice(goods.getOriginalPrice());
        this.setSoldNum(goods.getSoldNum());
        this.setStartTime(goods.getStartTime());
        this.setSeckillId(goods.getSeckillId());


    }

    public SeckillGoodsVO(SeckillGoodsVO seckillGoods, Integer key) {

        this.setGoodsId(seckillGoods.getGoodsId());
        this.setSeckillPrice(seckillGoods.getSeckillPrice());
        this.setSoldQuantity(seckillGoods.getSoldQuantity());
        this.setOriginalPrice(seckillGoods.getOriginalPrice());
        this.setSoldNum(seckillGoods.getSoldNum());
        this.setSeckillId(seckillGoods.getSeckillId());
        this.setSeckillStartTime(key);
        this.setStartTime(seckillGoods.getStartTime());
        this.setRemainQuantity(seckillGoods.getSoldQuantity() - seckillGoods.getSoldNum());
        countSeckillTime(this);
    }


    /**
     * Calculate the seconds kill activity time
     */
    private void countSeckillTime(SeckillGoodsVO goodsVO) {

        // The number of hours of the current time
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);

        // The time when this flash sale begins
        int timeLine = goodsVO.getSeckillStartTime();

        // The time until the end of the activity
        long distanceEndTime = 0;

        // Time from the start of the activity
        long distanceStartTime = 0;


        // Is it started? 1 indicates the start
        int isStart = 0;

        // If the current time is greater than the start time, it indicates that the activity has started. Calculate the time before the end
        if (hour >= timeLine) {
            // Read the end time for the day
            long endTime = DateUtil.endOfTodDay();

            // The current time
            long currentTime = DateUtil.getDateline();
            distanceEndTime = endTime - currentTime;
            isStart = 1;
        } else {
            // The current time
            long currentTime = DateUtil.getDateline();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            // The start time of the activity
            long startTime = DateUtil.getDateline(df.format(new Date()) + " " + timeLine + ":00:00", "yyyy-MM-dd HH:mm:ss");
            distanceStartTime = currentTime - startTime;
        }
        goodsVO.setSeckillStartTime(timeLine);
        goodsVO.setDistanceEndTime(distanceEndTime);
        goodsVO.setDistanceStartTime(distanceStartTime);
        goodsVO.setIsStart(isStart);
    }


    public Integer getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        if (logger.isDebugEnabled()) {
            this.logger.info("The flash sale start time is set to：" + startTime);
            this.logger.info("The object of：" + this.toString());
        }
        this.startTime = startTime;
    }

    public Integer getSeckillStartTime() {
        return seckillStartTime;
    }

    public void setSeckillStartTime(Integer seckillStartTime) {
        this.seckillStartTime = seckillStartTime;
    }

    public Long getDistanceEndTime() {
        return distanceEndTime;
    }

    public void setDistanceEndTime(Long distanceEndTime) {
        this.distanceEndTime = distanceEndTime;
    }

    public Integer getIsStart() {
        return isStart;
    }

    public void setIsStart(Integer isStart) {
        this.isStart = isStart;
    }

    public Integer getRemainQuantity() {
        if (remainQuantity < 0 || remainQuantity==null) {
            return 0;
        }return remainQuantity;
    }

    public void setRemainQuantity(Integer remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public Long getDistanceStartTime() {
        return distanceStartTime;
    }

    public void setDistanceStartTime(Long distanceStartTime) {
        this.distanceStartTime = distanceStartTime;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public String toString() {
        return "SeckillGoodsVO{" +
                "seckillStartTime=" + seckillStartTime +
                ", startTime=" + startTime +
                ", distanceEndTime=" + distanceEndTime +
                ", isStart=" + isStart +
                ", remainQuantity=" + remainQuantity +
                ", distanceStartTime=" + distanceStartTime +
                ", sellerId=" + sellerId +
                ", seckillId=" + seckillId +
                '}';
    }
}
