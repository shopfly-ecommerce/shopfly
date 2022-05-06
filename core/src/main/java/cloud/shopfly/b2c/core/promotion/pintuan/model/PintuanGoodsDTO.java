/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.pintuan.model;

/**
 * 拼团商品信息，用于传递pintuan活动包含时间参数的DTO
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-28 上午6:38
 */
public class PintuanGoodsDTO extends PintuanGoodsDO {

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

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

    @Override
    public String toString() {
        return "PintuanGoodsDTO{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
