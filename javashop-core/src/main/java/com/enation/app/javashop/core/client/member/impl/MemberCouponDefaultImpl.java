/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.member.impl;

import com.enation.app.javashop.core.client.member.MemberCouponClient;
import com.enation.app.javashop.core.member.model.dos.MemberCoupon;
import com.enation.app.javashop.core.member.service.MemberCouponManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员优惠券默认实现
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 下午3:54
 * @since v7.0
 */
@Service
@ConditionalOnProperty(value = "javashop.product", havingValue = "stand")
public class MemberCouponDefaultImpl implements MemberCouponClient {

    @Autowired
    private MemberCouponManager memberCouponManager;

    @Override
    public List<MemberCoupon> listByCheckout(Integer memberId) {
        return memberCouponManager.listByCheckout(memberId);
    }

    @Override
    public void receiveBonus(Integer memberId, Integer couponId) {
        memberCouponManager.receiveBonus(memberId, couponId);
    }

    @Override
    public MemberCoupon getModel(Integer memberId, Integer mcId) {
        return memberCouponManager.getModel(memberId, mcId);
    }
}
