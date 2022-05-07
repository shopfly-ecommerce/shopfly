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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Member Point operation
 *
 * @author zh
 * @version v7.0
 * @date 18/7/16 In the morning10:44
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
        // Get the integral setting
        String pointSettingJson = settingClient.get(SettingGroup.POINT);
        PointSetting pointSetting = JsonUtil.jsonToObject(pointSettingJson, PointSetting.class);
        // Member login to send points open
        if (pointSetting.getLogin().equals(1)) {
            // The last login time is null during the first login
            if (memberLoginMsg.getLastLoginTime() == null) {
                this.setPoint(1, pointSetting.getLoginGradePoint(), 1, pointSetting.getLoginConsumerPoint(), "Bonus points for first login every day", memberLoginMsg.getMemberId());
            } else {
                // Last login time
                long lDate = memberLoginMsg.getLastLoginTime() * 1000;
                Date date = new Date(lDate);
                // The current time
                Date today = new Date();
                // Determine if the local login is today
                if (!DateUtil.toString(date, "yyyy-MM-dd").equals(DateUtil.toString(today, "yyyy-MM-dd"))) {
                    this.setPoint(1, pointSetting.getLoginGradePoint(), 1, pointSetting.getLoginConsumerPoint(), "Bonus points for first login every day", memberLoginMsg.getMemberId());
                }
            }


        }

    }


    @Override
    public void memberRegister(MemberRegisterMsg memberRegisterMsg) {
        String pointSettingJson = settingClient.get(SettingGroup.POINT);
        PointSetting pointSetting = JsonUtil.jsonToObject(pointSettingJson, PointSetting.class);
        // Member login to send points open
        if (pointSetting.getRegister().equals(1)) {
            this.setPoint(1, pointSetting.getRegisterGradePoint(), 1, pointSetting.getRegisterConsumerPoint(), "Bonus points for member registration", memberRegisterMsg.getMember().getMemberId());
        }
    }


    @Override
    public void goodsComment(GoodsCommentMsg goodsCommentMsg) {
        String pointSettingJson = settingClient.get(SettingGroup.POINT);
        PointSetting pointSetting = JsonUtil.jsonToObject(pointSettingJson, PointSetting.class);
        // Photo comments give credit
        if (pointSetting.getCommentImg().equals(1) && goodsCommentMsg.getComment().getHaveImage().equals(1)) {
            this.setPoint(1, pointSetting.getCommentImgGradePoint(), 1, pointSetting.getCommentImgConsumerPoint(), "Photo comments give credit", goodsCommentMsg.getComment().getMemberId());
        }
        // Text comments give points
        if (pointSetting.getComment().equals(1) && goodsCommentMsg.getComment().getHaveImage().equals(1)) {
            this.setPoint(1, pointSetting.getCommentGradePoint(), 1, pointSetting.getCommentConsumerPoint(), "Text comments give points", goodsCommentMsg.getComment().getMemberId());
        }
        Integer count = memberCommentClient.getGoodsCommentCount(goodsCommentMsg.getComment().getGoodsId());
        // The number of comments is 1, because the number of comments has already been added
        if (pointSetting.getFirstComment().equals(1) && (count.equals(1) || count.equals(0))) {
            this.setPoint(1, pointSetting.getFirstCommentGradePoint(), 1, pointSetting.getFirstCommentConsumerPoint(), "First comments", goodsCommentMsg.getComment().getMemberId());
        }
    }

    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {
        String pointSettingJson = settingClient.get(SettingGroup.POINT);
        PointSetting pointSetting = JsonUtil.jsonToObject(pointSettingJson, PointSetting.class);
        // Get order information
        OrderDO orderDO = orderMessage.getOrderDO();
        // Paid status
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.PAID_OFF.name())) {
            // If online payment is enabled and the order status is paid and the order payment is online payment, points will be awarded
            if (pointSetting.getOnlinePay().equals(1) && orderDO.getPaymentType().equals(PaymentTypeEnum.ONLINE.name())) {
                this.setPoint(1, pointSetting.getOnlinePayGradePoint(), 1, pointSetting.getOnlinePayConsumerPoint(), "Pay online and send points", orderDO.getMemberId());
            }
        }
        // Completed state
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.COMPLETE.name())) {
            // Credits are awarded if the purchase is enabled and the order status is completed
            if (pointSetting.getBuyGoods().equals(1)) {
                this.setPoint(1, Integer.parseInt(new java.text.DecimalFormat("0").format(CurrencyUtil.mul(pointSetting.getBuyGoodsGradePoint(), orderDO.getOrderPrice().intValue()))), 1, Integer.parseInt(new java.text.DecimalFormat("0").format(CurrencyUtil.mul(pointSetting.getBuyGoodsConsumerPoint(), orderDO.getOrderPrice().intValue()))), "Give points for purchases", orderDO.getMemberId());
            }
        }

        // The order has been received and distributed bonus points
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.ROG.name())) {

            String metaJson = this.orderMetaManager.getMetaValue(orderMessage.getOrderDO().getSn(), OrderMetaKeyEnum.GIFT_POINT);

            if (StringUtil.isEmpty(metaJson) || "0".equals(metaJson)) {
                return;
            }

            this.setPoint(1, 0, 1, new Integer(metaJson), "Full complimentary preferential activities free", orderMessage.getOrderDO().getMemberId());

        }
    }

    /**
     * Operations on integrals
     *
     * @param gradePointType  Grade integral type1To increase the integral, if the grade integral is zero0的时候Grade integral type为0Is no operation, if the grade integral is not0Is the integral type0For the consumer
     * @param gradePoint      Level score
     * @param consumPointType Consumption Credit type1To increase the credits, if the consumption credits are0Is the consumption integral type0Is no operation, if the consumption points are not0When the consumption points are0For the consumer
     * @param consumPoint     consumption score
     * @param remark          note
     * @param memberId        membersid
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
        memberPointHistory.setOperator("system");
        memberClient.pointOperation(memberPointHistory);
    }

    @Override
    public void onTradeIntoDb(TradeVO tradeVO) {

        // Order in storage, deduction of use points
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
            history.setReason("Create orders and spend points");
            history.setOperator(orderDTO.getMemberName());
            this.memberClient.pointOperation(history);
        }
    }
}
