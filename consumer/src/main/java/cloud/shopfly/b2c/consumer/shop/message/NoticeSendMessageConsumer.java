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
package cloud.shopfly.b2c.consumer.shop.message;

import cloud.shopfly.b2c.consumer.core.event.*;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefuseTypeEnum;
import cloud.shopfly.b2c.core.base.message.*;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.client.member.MemberNoticeLogClient;
import cloud.shopfly.b2c.core.client.system.MessageTemplateClient;
import cloud.shopfly.b2c.core.client.system.NoticeLogClient;
import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.vo.MemberLoginMsg;
import cloud.shopfly.b2c.core.system.model.dos.MessageTemplateDO;
import cloud.shopfly.b2c.core.system.model.dos.NoticeLogDO;
import cloud.shopfly.b2c.core.system.enums.MessageCodeEnum;
import cloud.shopfly.b2c.core.system.model.enums.MessageOpenStatusEnum;
import cloud.shopfly.b2c.core.system.model.enums.NoticeTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderDTO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjp
 * @version v7.0
 * @Description Message template sends in-station messages
 * @ClassName NoticeSendMessageConsumer
 * @since v7.0 In the morning11:43 2018/7/9
 */
@Component
public class NoticeSendMessageConsumer extends AbstractMessage implements OrderStatusChangeEvent, RefundStatusChangeEvent, GoodsChangeEvent, MemberLoginEvent, MemberRegisterEvent, TradeIntoDbEvent, GoodsCommentEvent {

    @Autowired
    private NoticeLogClient noticeLogClient;

    @Autowired
    private MemberNoticeLogClient memberNoticeLogClient;

    @Autowired
    private MessageTemplateClient messageTemplateClient;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private MemberClient memberClient;

    private void sendShopNotice(NoticeLogDO noticeLogDO) {
        noticeLogDO.setIsDelete(0);
        noticeLogDO.setIsRead(0);
        noticeLogDO.setSendTime(DateUtil.getDateline());
        noticeLogClient.add(noticeLogDO);
    }

    private void sendMemberNotice(String content, long sendTime, Integer memberId) {
        memberNoticeLogClient.add(content, sendTime, memberId, "");
    }

    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {

        OrderDO orderDO = orderMessage.getOrderDO();

        NoticeLogDO noticeLogDO = new NoticeLogDO();

        MessageTemplateDO messageTemplate = null;

        // Order Payment reminder
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.PAID_OFF.name())) {
            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("paymentTime", DateUtil.toString(orderDO.getPaymentTime(), "yyyy-MM-dd"));

            // Store order payment reminder
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSPAY);
            // Check whether the function is enabled.
            if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                noticeLogDO.setType(NoticeTypeEnum.ORDER.value());
                noticeLogDO.setNoticeContent(this.replaceContent(messageTemplate.getContent(), valuesMap));
                this.sendShopNotice(noticeLogDO);
            }

            // Member order payment reminder
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSPAY);
            if (messageTemplate != null) {
                // Check whether the function is enabled.
                if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    sendMemberNotice(this.replaceContent(messageTemplate.getContent(), valuesMap), DateUtil.getDateline(), orderDO.getMemberId());
                }
            }
        }

        // Order receipt reminder
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.ROG.name())) {

            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("finishTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));

            // Store order receipt reminder
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSRECEIVE);
            if (messageTemplate != null) {
                if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    noticeLogDO.setNoticeContent(this.replaceContent(messageTemplate.getContent(), valuesMap));
                    noticeLogDO.setType(NoticeTypeEnum.ORDER.value());
                    this.sendShopNotice(noticeLogDO);
                }
            }
            // Member order receipt reminder
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSRECEIVE);
            if (messageTemplate != null) {
                if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    sendMemberNotice(this.replaceContent(messageTemplate.getContent(), valuesMap), DateUtil.getDateline(), orderDO.getMemberId());
                }
            }
        }

        // Order Cancellation Reminder
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.CANCELLED.name())) {

            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("cancelTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));

            // Send membership messages
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSCANCEL);
            if (messageTemplate != null) {

                // Check whether the function is enabled.
                if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    sendMemberNotice(this.replaceContent(messageTemplate.getContent(), valuesMap), DateUtil.getDateline(), orderDO.getMemberId());
                }
            }

            // Send store messages
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSCANCEL);
            if (messageTemplate != null) {
                // Check whether the function is enabled.
                if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    noticeLogDO.setNoticeContent(this.replaceContent(messageTemplate.getContent(), valuesMap));
                    noticeLogDO.setType(NoticeTypeEnum.ORDER.value());
                    this.sendShopNotice(noticeLogDO);
                }
            }
        }

        // Order shipping Reminder
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.SHIPPED.name())) {
            // Member Message sending
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSSEND);
            if (messageTemplate != null) {
                // Check whether the function is enabled.
                if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                    valuesMap.put("ordersSn", orderDO.getSn());
                    valuesMap.put("shipSn", orderDO.getShipNo());
                    valuesMap.put("sendTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));
                    sendMemberNotice(this.replaceContent(messageTemplate.getContent(), valuesMap), DateUtil.getDateline(), orderDO.getMemberId());
                }
            }
        }
    }


    @Override
    public void refund(RefundChangeMsg refundChangeMsg) {
        NoticeLogDO noticeLogDO = new NoticeLogDO();
        OrderDetailDTO orderDetailDTO = orderClient.getModel(refundChangeMsg.getRefund().getOrderSn());

        // Return/payment reminder
        if (refundChangeMsg.getRefundStatusEnum().equals(RefundStatusEnum.APPLY)) {
            if (orderDetailDTO != null) {

                MessageTemplateDO messageTemplate = null;

                // Member Information sending
                // Record cancellation information of member order (check in member Center)
                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.value())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERREFUNDUPDATE);
                }
                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_GOODS.value())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERRETURNUPDATE);
                }

                if (messageTemplate != null) {
                    // Check whether the function is enabled.
                    if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                        Map<String, Object> valuesMap = new HashMap<String, Object>(2);
                        valuesMap.put("refundSn", refundChangeMsg.getRefund().getSn());
                        sendMemberNotice(this.replaceContent(messageTemplate.getContent(), valuesMap), DateUtil.getDateline(), orderDetailDTO.getMemberId());
                    }
                }

                // Store Information sending
                messageTemplate = null;
                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_GOODS.value())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPRETURN);
                }

                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.value())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPREFUND);
                }

                // Record store order cancellation information (view in merchant center)
                if (messageTemplate != null) {
                    // Check whether the function is enabled.
                    if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                        Map<String, Object> valuesMap = new HashMap<String, Object>(2);
                        valuesMap.put("refundSn", refundChangeMsg.getRefund().getSn());
                        noticeLogDO.setNoticeContent(this.replaceContent(messageTemplate.getContent(), valuesMap));
                        noticeLogDO.setType(NoticeTypeEnum.AFTERSALE.value());
                        this.sendShopNotice(noticeLogDO);
                    }
                }
            }
        }

    }

    @Override
    public void goodsChange(GoodsChangeMsg goodsChangeMsg) {
        NoticeLogDO noticeLogDO = new NoticeLogDO();
        // Notification of merchandise removal
        if (GoodsChangeMsg.UNDER_OPERATION == goodsChangeMsg.getOperationType() && !StringUtil.isEmpty(goodsChangeMsg.getMessage())) {
            // Send store messages
            for (Integer goodsId : goodsChangeMsg.getGoodsIds()) {

                CacheGoods goods = goodsClient.getFromCache(goodsId);
                // Record store order cancellation information (view in merchant center)
                MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPGOODSMARKETENABLE);
                if (messageTemplate != null) {

                    // Check whether the function is enabled.
                    if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {

                        Map<String, Object> valuesMap = new HashMap<String, Object>(2);
                        valuesMap.put("name", goods.getGoodsName());
                        valuesMap.put("reason", goodsChangeMsg.getMessage());
                        noticeLogDO.setNoticeContent(this.replaceContent(messageTemplate.getContent(), valuesMap));
                        noticeLogDO.setType(NoticeTypeEnum.GOODS.value());
                        this.sendShopNotice(noticeLogDO);
                    }
                }
            }
        }
    }

    @Override
    public void memberLogin(MemberLoginMsg memberLoginMsg) {
        Member member = memberClient.getModel(memberLoginMsg.getMemberId());
        MessageTemplateDO messageTemplate = null;
        // Record member login success information (check in member center)
        messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERLOGINSUCCESS);

        // Check whether the station message is open
        if (messageTemplate != null) {
            // Check whether SMS is enabled
            if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                Map<String, Object> valuesMap = new HashMap<String, Object>(2);
                valuesMap.put("name", member.getUname());
                valuesMap.put("loginTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));

                sendMemberNotice(this.replaceContent(messageTemplate.getContent(), valuesMap), DateUtil.getDateline(), member.getMemberId());
            }
        }
    }


    @Override
    public void memberRegister(MemberRegisterMsg memberRegisterMsg) {
        Member member = memberClient.getModel(memberRegisterMsg.getMember().getMemberId());
        // Member registration success reminder
        MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERREGISTESUCCESS);
        if (messageTemplate != null) {
            // Check whether the function is enabled.
            if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                Map<String, Object> valuesMap = new HashMap<String, Object>(2);
                valuesMap.put("loginTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));
                valuesMap.put("name", member.getUname());
                sendMemberNotice(this.replaceContent(messageTemplate.getContent(), valuesMap), DateUtil.getDateline(), member.getMemberId());
            }
        }
    }

    @Override
    public void onTradeIntoDb(TradeVO tradeVO) {
        // Store new order creation reminder
        NoticeLogDO noticeLogDO = new NoticeLogDO();
        List<OrderDTO> orderList = tradeVO.getOrderList();
        for (OrderDTO orderDTO : orderList) {
            MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSNEW);
            // Check whether the function is enabled.
            if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                valuesMap.put("ordersSn", orderDTO.getSn());
                valuesMap.put("createTime", DateUtil.toString(orderDTO.getCreateTime(), "yyyy-MM-dd"));
                noticeLogDO.setType(NoticeTypeEnum.ORDER.value());
                noticeLogDO.setNoticeContent(this.replaceContent(messageTemplate.getContent(), valuesMap));
                this.sendShopNotice(noticeLogDO);
            }
        }
    }

    @Override
    public void goodsComment(GoodsCommentMsg goodsCommentMsg) {
        // Product Evaluation Reminder
        NoticeLogDO noticeLogDO = new NoticeLogDO();
        MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSEVALUATE);
        // Check whether the function is enabled.
        if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("sn", goodsCommentMsg.getComment().getOrderSn());
            valuesMap.put("member_name", goodsCommentMsg.getComment().getMemberName());
            noticeLogDO.setType(NoticeTypeEnum.ORDER.value());
            noticeLogDO.setNoticeContent(this.replaceContent(messageTemplate.getContent(), valuesMap));
            this.sendShopNotice(noticeLogDO);
        }
    }

}
