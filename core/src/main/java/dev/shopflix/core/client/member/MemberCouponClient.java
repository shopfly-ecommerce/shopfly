/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.member;

import dev.shopflix.core.member.model.dos.MemberCoupon;

import java.util.List;

/**
 * 会员优惠券client
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 上午11:48
 * @since v7.0
 */

public interface MemberCouponClient {
    /**
     * 查询优惠券列表
     *
     * @param memberId 会员id
     * @return 优惠券列表
     */
    List listByCheckout(Integer memberId);

    /**
     * 领取优惠券
     *
     * @param memberId 会员id
     * @param couponId 优惠券id
     */
    void receiveBonus(Integer memberId, Integer couponId);

    /**
     * 获取会员优惠券信息
     *
     * @param memberId 会员id
     * @param mcId     优惠券id
     * @return 优惠券对象
     */
    MemberCoupon getModel(Integer memberId, Integer mcId);


}
