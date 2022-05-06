/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.dos.MemberCoupon;
import cloud.shopfly.b2c.core.member.model.dto.MemberCouponQueryParam;
import cloud.shopfly.b2c.core.member.model.vo.MemberCouponNumVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * 会员优惠券
 *
 * @author Snow create in 2018/5/24
 * @version v2.0
 * @since v7.0.0
 */
public interface MemberCouponManager {

    /**
     * 领取优惠券
     *
     * @param memberId 会员id
     * @param couponId 优惠券id
     */
    void receiveBonus(Integer memberId, Integer couponId);

    /**
     * 查询我的所有优惠券
     *
     * @param param
     * @return
     */
    Page list(MemberCouponQueryParam param);


    /**
     * 读取我的优惠券
     *
     * @param memberId
     * @param mcId
     * @return
     */
    MemberCoupon getModel(Integer memberId, Integer mcId);

    /**
     * 将优惠券变为已使用
     *
     * @param mcId
     */
    void usedCoupon(Integer mcId);


    /**
     * 检测已领取数量
     *
     * @param couponId
     */
    void checkLimitNum(Integer couponId);

    /**
     * 结算页—查询会员优惠券
     *
     * @param memberId  会员id
     * @return
     */
    List<MemberCoupon> listByCheckout(Integer memberId);

    /**
     * 优惠券各个状态数量
     *
     * @return
     */
    MemberCouponNumVO statusNum();

}
