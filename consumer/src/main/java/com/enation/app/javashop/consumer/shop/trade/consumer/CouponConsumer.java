/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.shop.trade.consumer;

import com.enation.app.javashop.consumer.core.event.OrderStatusChangeEvent;
import com.enation.app.javashop.consumer.core.event.TradeIntoDbEvent;
import com.enation.app.javashop.core.base.message.OrderStatusChangeMsg;
import com.enation.app.javashop.core.client.member.MemberClient;
import com.enation.app.javashop.core.member.model.dos.MemberCoupon;
import com.enation.app.javashop.core.promotion.coupon.service.CouponManager;
import com.enation.app.javashop.core.trade.cart.model.vo.CouponVO;
import com.enation.app.javashop.core.trade.order.model.enums.OrderMetaKeyEnum;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import com.enation.app.javashop.core.trade.order.model.vo.TradeVO;
import com.enation.app.javashop.core.trade.order.service.OrderMetaManager;
import com.enation.app.javashop.framework.util.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 确认收款发放促销活动赠送优惠券
 *
 * @author Snow create in 2018/5/22
 * @version v2.0
 * @since v7.0.0
 */
@Component
public class CouponConsumer implements OrderStatusChangeEvent, TradeIntoDbEvent {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private OrderMetaManager orderMetaManager;

    @Autowired
    private MemberClient memberClient;

    @Autowired
    private CouponManager couponManager;


    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        if ((orderMessage.getNewStatus().name()).equals(OrderStatusEnum.PAID_OFF.name())) {

            //读取已发放的优惠券json
            String itemJson = this.orderMetaManager.getMetaValue(orderMessage.getOrderDO().getSn(), OrderMetaKeyEnum.COUPON);
            List<CouponVO> couponList = JsonUtil.jsonToList(itemJson, CouponVO.class);
            if (couponList != null && couponList.size() > 0) {

                // 循环发放的优惠券
                for (CouponVO couponVO : couponList) {
                    this.memberClient.receiveBonus(orderMessage.getOrderDO().getMemberId(), couponVO.getCouponId());
                }
            }
        }
    }


    @Override
    public void onTradeIntoDb(TradeVO tradeVO) {
        try {

            //优惠券状态提前变更
            //将使用过的优惠券变为已使用
            List<CouponVO> useCoupons = tradeVO.getCouponList();
            if (useCoupons != null) {
                for (CouponVO couponVO : useCoupons) {
                    this.memberClient.usedCoupon(couponVO.getMemberCouponId());
                    MemberCoupon memberCoupon = this.memberClient.getModel(tradeVO.getMemberId(), couponVO.getMemberCouponId());
                    //修改店铺已经使用优惠券数量
                    this.couponManager.addUsedNum(memberCoupon.getCouponId());
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug("更改优惠券的状态完成");
            }

        } catch (Exception e) {
            logger.error("更改优惠券的状态出错", e);
        }
    }
}
