/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.shop.member;

import cloud.shopfly.b2c.consumer.core.event.*;
import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.message.GoodsCommentMsg;
import cloud.shopfly.b2c.core.base.message.MemberRegisterMsg;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.client.member.MemberCommentClient;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.member.model.dos.MemberPointHistory;
import cloud.shopfly.b2c.core.member.model.vo.MemberLoginMsg;
import cloud.shopfly.b2c.core.system.model.vo.PointSetting;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderDTO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderMetaKeyEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;
import cloud.shopfly.b2c.core.trade.order.service.OrderMetaManager;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import dev.shopflix.consumer.core.event.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 会员积分操作
 *
 * @author zh
 * @version v7.0
 * @date 18/7/16 上午10:44
 * @since v7.0
 */
@Component
public class MemberPointConsumer implements MemberLoginEvent, MemberRegisterEvent, GoodsCommentEvent, OrderStatusChangeEvent, TradeIntoDbEvent {

    @Autowired
    private SettingClient settingClient;
    @Autowired
    private MemberClient memberClient;
    @Autowired
    private MemberCommentClient memberCommentClient;

    @Autowired
    private OrderMetaManager orderMetaManager;

    @Override
    public void memberLogin(MemberLoginMsg memberLoginMsg) {
        //获取积分设置
        String pointSettingJson = settingClient.get(SettingGroup.POINT);
        PointSetting pointSetting = JsonUtil.jsonToObject(pointSettingJson, PointSetting.class);
        //会员登录送积分开启
        if (pointSetting.getLogin().equals(1)) {
            //当第一次注册登录的时候上次登录时间为null
            if (memberLoginMsg.getLastLoginTime() == null) {
                this.setPoint(1, pointSetting.getLoginGradePoint(), 1, pointSetting.getLoginConsumerPoint(), "每天首次登录送积分", memberLoginMsg.getMemberId());
            } else {
                //上次登录时间
                long lDate = memberLoginMsg.getLastLoginTime() * 1000;
                Date date = new Date(lDate);
                //当前时间
                Date today = new Date();
                //判断本地登录是否是今天
                if (!DateUtil.toString(date, "yyyy-MM-dd").equals(DateUtil.toString(today, "yyyy-MM-dd"))) {
                    this.setPoint(1, pointSetting.getLoginGradePoint(), 1, pointSetting.getLoginConsumerPoint(), "每天首次登录送积分", memberLoginMsg.getMemberId());
                }
            }


        }

    }


    @Override
    public void memberRegister(MemberRegisterMsg memberRegisterMsg) {
        String pointSettingJson = settingClient.get(SettingGroup.POINT);
        PointSetting pointSetting = JsonUtil.jsonToObject(pointSettingJson, PointSetting.class);
        //会员登录送积分开启
        if (pointSetting.getRegister().equals(1)) {
            this.setPoint(1, pointSetting.getRegisterGradePoint(), 1, pointSetting.getRegisterConsumerPoint(), "会员注册送积分", memberRegisterMsg.getMember().getMemberId());
        }
    }


    @Override
    public void goodsComment(GoodsCommentMsg goodsCommentMsg) {
        String pointSettingJson = settingClient.get(SettingGroup.POINT);
        PointSetting pointSetting = JsonUtil.jsonToObject(pointSettingJson, PointSetting.class);
        //图片评论送积分
        if (pointSetting.getCommentImg().equals(1) && goodsCommentMsg.getComment().getHaveImage().equals(1)) {
            this.setPoint(1, pointSetting.getCommentImgGradePoint(), 1, pointSetting.getCommentImgConsumerPoint(), "图片评论送积分", goodsCommentMsg.getComment().getMemberId());
        }
        //文字评论送积分
        if (pointSetting.getComment().equals(1) && goodsCommentMsg.getComment().getHaveImage().equals(1)) {
            this.setPoint(1, pointSetting.getCommentGradePoint(), 1, pointSetting.getCommentConsumerPoint(), "文字评论送积分", goodsCommentMsg.getComment().getMemberId());
        }
        Integer count = memberCommentClient.getGoodsCommentCount(goodsCommentMsg.getComment().getGoodsId());
        //此处评论数量判断为1，因为此时评论数量已经添加了
        if (pointSetting.getFirstComment().equals(1) && (count.equals(1) || count.equals(0))) {
            this.setPoint(1, pointSetting.getFirstCommentGradePoint(), 1, pointSetting.getFirstCommentConsumerPoint(), "每个商品首次评论", goodsCommentMsg.getComment().getMemberId());
        }
    }

    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {
        String pointSettingJson = settingClient.get(SettingGroup.POINT);
        PointSetting pointSetting = JsonUtil.jsonToObject(pointSettingJson, PointSetting.class);
        //获取订单信息
        OrderDO orderDO = orderMessage.getOrderDO();
        //已付款状态
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.PAID_OFF.name())) {
            //如果开启了在线支付送积分并且订单状态为已付款且订单支付为在线付款 则送积分
            if (pointSetting.getOnlinePay().equals(1) && orderDO.getPaymentType().equals(PaymentTypeEnum.ONLINE.name())) {
                this.setPoint(1, pointSetting.getOnlinePayGradePoint(), 1, pointSetting.getOnlinePayConsumerPoint(), "在线支付送积分", orderDO.getMemberId());
            }
        }
        //已完成状态
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.COMPLETE.name())) {
            //如果开启购买商品送积分并且为订单状态为已完成状态 则送积分
            if (pointSetting.getBuyGoods().equals(1)) {
                this.setPoint(1, Integer.parseInt(new java.text.DecimalFormat("0").format(CurrencyUtil.mul(pointSetting.getBuyGoodsGradePoint(), orderDO.getOrderPrice().intValue()))), 1, Integer.parseInt(new java.text.DecimalFormat("0").format(CurrencyUtil.mul(pointSetting.getBuyGoodsConsumerPoint(), orderDO.getOrderPrice().intValue()))), "购买商品送积分", orderDO.getMemberId());
            }
        }

        // 订单已收货 发放赠送积分
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.ROG.name())) {

            String metaJson = this.orderMetaManager.getMetaValue(orderMessage.getOrderDO().getSn(), OrderMetaKeyEnum.GIFT_POINT);

            if (StringUtil.isEmpty(metaJson) || "0".equals(metaJson)) {
                return;
            }

            this.setPoint(1, 0, 1, new Integer(metaJson), "满赠优惠活动赠送", orderMessage.getOrderDO().getMemberId());

        }
    }

    /**
     * 对积分的操作
     *
     * @param gradePointType  等级积分类型 1为增加积分 ，如果等级积分为0的时候等级积分类型为0则为无操作，如果等级积分不为0的时候积分类型为0则为消费
     * @param gradePoint      等级积分
     * @param consumPointType 消费积分类型 1为增加积分 ，如果消费积分为0的时候消费积分型为0则为无操作，如果消费积分不为0的时候消费积分为0则为消费
     * @param consumPoint     消费积分
     * @param remark          备注
     * @param memberId        会员id
     */
    private void setPoint(Integer gradePointType, Integer gradePoint, Integer consumPointType, Integer consumPoint, String remark, Integer memberId) {

        if (gradePoint == 0 && consumPoint == 0) {
            return;
        }

        MemberPointHistory memberPointHistory = new MemberPointHistory();
        memberPointHistory.setGradePoint(gradePoint);
        memberPointHistory.setGradePointType(gradePointType);
        memberPointHistory.setConsumPointType(consumPointType);
        memberPointHistory.setConsumPoint(consumPoint);
        memberPointHistory.setReason(remark);
        memberPointHistory.setMemberId(memberId);
        memberPointHistory.setOperator("系统");
        memberClient.pointOperation(memberPointHistory);
    }

    @Override
    public void onTradeIntoDb(TradeVO tradeVO) {

        //订单入库，扣减使用积分
        List<OrderDTO> orderList = tradeVO.getOrderList();
        for (OrderDTO orderDTO : orderList) {
            int consumerPoint = orderDTO.getPrice().getExchangePoint();
            if (consumerPoint <= 0) {
                continue;
            }

            MemberPointHistory history = new MemberPointHistory();
            history.setConsumPoint(consumerPoint);
            history.setConsumPointType(0);
            history.setGradePointType(0);
            history.setGradePoint(0);
            history.setMemberId(orderDTO.getMemberId());
            history.setTime(DateUtil.getDateline());
            history.setReason("创建订单，消费积分");
            history.setOperator(orderDTO.getMemberName());
            this.memberClient.pointOperation(history);
        }
    }
}
