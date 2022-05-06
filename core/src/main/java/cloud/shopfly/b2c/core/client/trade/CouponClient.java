/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.trade;

import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;

/**
 * @author zh
 * @version v2.0
 * @Description: 优惠券单对外接口
 * @date 2018/7/26 11:21
 * @since v7.0.0
 */
public interface CouponClient {
    /**
     * 获取优惠券
     *
     * @param id 优惠券主键
     * @return Coupon  优惠券
     */
    CouponDO getModel(Integer id);


    /**
     * 增加被领取数量
     *
     * @param couponId
     */
    void addReceivedNum(Integer couponId);


}
