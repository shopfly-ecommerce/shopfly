/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 商品详情页展示VO
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

    @ApiModelProperty(value = "秒杀开始时刻，注意不是时间，是钟点，比如10")
    private Integer seckillStartTime;

    @ApiModelProperty(value = "秒杀开始时间，这个是时间戳")
    private Long startTime;

    @ApiModelProperty(value = "距离活动结束的时间，秒为单位")
    private Long distanceEndTime;

    @ApiModelProperty(value = "活动是否已经开始、1:活动正在进行中，0:未开始")
    private Integer isStart;

    @ApiModelProperty(value = "剩余可售数量")
    private Integer remainQuantity;

    @ApiModelProperty(value = "距离活动开始的时间，秒为单位。如果活动的开始时间是10点，服务器时间为8点，距离开始还有多少时间")
    private Long distanceStartTime;

    @ApiModelProperty(value = "商家ID")
    private Integer sellerId;

    @ApiModelProperty(value = "秒杀活动ID")
    private Integer seckillId;


    public SeckillGoodsVO() {

    }

    /**
     * 构造器
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
     * 计算秒杀活动时间
     */
    private void countSeckillTime(SeckillGoodsVO goodsVO) {

        //当前时间的小时数
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);

        //此商品限时抢购活动的 开始时刻
        int timeLine = goodsVO.getSeckillStartTime();

        //距离活动结束的时间
        long distanceEndTime = 0;

        //距离活动开始的时间
        long distanceStartTime = 0;


        //是否已经开始，1为开始
        int isStart = 0;

        //当前时间大于活动开始的时刻，说明已经开始，计算距离结束的时间
        if (hour >= timeLine) {
            //读取今天的结束时间
            long endTime = DateUtil.endOfTodDay();

            //当前时间
            long currentTime = DateUtil.getDateline();
            distanceEndTime = endTime - currentTime;
            isStart = 1;
        } else {
            //当前时间
            long currentTime = DateUtil.getDateline();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //活动的开始时间
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
            this.logger.info("限时抢购开始时间被置为：" + startTime);
            this.logger.info("对象为：" + this.toString());
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
