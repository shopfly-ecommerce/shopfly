/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.shop.message;

import com.enation.app.javashop.consumer.core.event.*;
import com.enation.app.javashop.core.aftersale.model.enums.RefundStatusEnum;
import com.enation.app.javashop.core.aftersale.model.enums.RefuseTypeEnum;
import com.enation.app.javashop.core.base.message.*;
import com.enation.app.javashop.core.client.goods.GoodsClient;
import com.enation.app.javashop.core.client.member.MemberClient;
import com.enation.app.javashop.core.client.member.MemberNoticeLogClient;
import com.enation.app.javashop.core.client.system.MessageTemplateClient;
import com.enation.app.javashop.core.client.system.NoticeLogClient;
import com.enation.app.javashop.core.client.trade.OrderClient;
import com.enation.app.javashop.core.goods.model.vo.CacheGoods;
import com.enation.app.javashop.core.member.model.dos.Member;
import com.enation.app.javashop.core.member.model.vo.MemberLoginMsg;
import com.enation.app.javashop.core.system.model.dos.MessageTemplateDO;
import com.enation.app.javashop.core.system.model.dos.NoticeLogDO;
import com.enation.app.javashop.core.system.enums.MessageCodeEnum;
import com.enation.app.javashop.core.system.model.enums.MessageOpenStatusEnum;
import com.enation.app.javashop.core.system.model.enums.NoticeTypeEnum;
import com.enation.app.javashop.core.trade.order.model.dos.OrderDO;
import com.enation.app.javashop.core.trade.order.model.dto.OrderDTO;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import com.enation.app.javashop.core.trade.order.model.vo.TradeVO;
import com.enation.app.javashop.core.trade.sdk.model.OrderDetailDTO;
import com.enation.app.javashop.framework.util.DateUtil;
import com.enation.app.javashop.framework.util.StringUtil;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjp
 * @version v7.0
 * @Description 消息模版发送站内信
 * @ClassName NoticeSendMessageConsumer
 * @since v7.0 上午11:43 2018/7/9
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

        //订单支付提醒
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.PAID_OFF.name())) {
            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("paymentTime", DateUtil.toString(orderDO.getPaymentTime(), "yyyy-MM-dd"));

            // 店铺订单支付提醒
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSPAY);
            // 判断是否开启
            if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                noticeLogDO.setType(NoticeTypeEnum.ORDER.value());
                noticeLogDO.setNoticeContent(this.replaceContent(messageTemplate.getContent(), valuesMap));
                this.sendShopNotice(noticeLogDO);
            }

            // 会员订单支付提醒
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSPAY);
            if (messageTemplate != null) {
                // 判断是否开启
                if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    sendMemberNotice(this.replaceContent(messageTemplate.getContent(), valuesMap), DateUtil.getDateline(), orderDO.getMemberId());
                }
            }
        }

        //订单收货提醒
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.ROG.name())) {

            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("finishTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));

            // 店铺订单收货提醒
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSRECEIVE);
            if (messageTemplate != null) {
                if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    noticeLogDO.setNoticeContent(this.replaceContent(messageTemplate.getContent(), valuesMap));
                    noticeLogDO.setType(NoticeTypeEnum.ORDER.value());
                    this.sendShopNotice(noticeLogDO);
                }
            }
            //会员订单收货提醒
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSRECEIVE);
            if (messageTemplate != null) {
                if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    sendMemberNotice(this.replaceContent(messageTemplate.getContent(), valuesMap), DateUtil.getDateline(), orderDO.getMemberId());
                }
            }
        }

        //订单取消提醒
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.CANCELLED.name())) {

            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("cancelTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));

            // 发送会员消息
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSCANCEL);
            if (messageTemplate != null) {

                // 判断是否开启
                if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    sendMemberNotice(this.replaceContent(messageTemplate.getContent(), valuesMap), DateUtil.getDateline(), orderDO.getMemberId());
                }
            }

            // 发送店铺消息
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSCANCEL);
            if (messageTemplate != null) {
                // 判断是否开启
                if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    noticeLogDO.setNoticeContent(this.replaceContent(messageTemplate.getContent(), valuesMap));
                    noticeLogDO.setType(NoticeTypeEnum.ORDER.value());
                    this.sendShopNotice(noticeLogDO);
                }
            }
        }

        //订单发货提醒
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.SHIPPED.name())) {
            // 会员消息发送
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSSEND);
            if (messageTemplate != null) {
                // 判断是否开启
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

        //退货/款提醒
        if (refundChangeMsg.getRefundStatusEnum().equals(RefundStatusEnum.APPLY)) {
            if (orderDetailDTO != null) {

                MessageTemplateDO messageTemplate = null;

                // 会员信息发送
                // 记录会员订单取消信息（会员中心查看）
                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.value())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERREFUNDUPDATE);
                }
                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_GOODS.value())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERRETURNUPDATE);
                }

                if (messageTemplate != null) {
                    // 判断是否开启
                    if (messageTemplate.getNoticeState().equals(MessageOpenStatusEnum.OPEN.value())) {
                        Map<String, Object> valuesMap = new HashMap<String, Object>(2);
                        valuesMap.put("refundSn", refundChangeMsg.getRefund().getSn());
                        sendMemberNotice(this.replaceContent(messageTemplate.getContent(), valuesMap), DateUtil.getDateline(), orderDetailDTO.getMemberId());
                    }
                }

                // 店铺信息发送
                messageTemplate = null;
                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_GOODS.value())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPRETURN);
                }

                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.value())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPREFUND);
                }

                // 记录店铺订单取消信息（商家中心查看）
                if (messageTemplate != null) {
                    // 判断是否开启
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
        //商品下架消息提醒
        if (GoodsChangeMsg.UNDER_OPERATION == goodsChangeMsg.getOperationType() && !StringUtil.isEmpty(goodsChangeMsg.getMessage())) {
            //发送店铺消息
            for (Integer goodsId : goodsChangeMsg.getGoodsIds()) {

                CacheGoods goods = goodsClient.getFromCache(goodsId);
                // 记录店铺订单取消信息（商家中心查看）
                MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPGOODSMARKETENABLE);
                if (messageTemplate != null) {

                    // 判断是否开启
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
        // 记录会员登陆成功信息（会员中心查看）
        messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERLOGINSUCCESS);

        // 判断站内信是否开启
        if (messageTemplate != null) {
            // 判断短信是否开启
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
        //会员注册成功提醒
        MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERREGISTESUCCESS);
        if (messageTemplate != null) {
            // 判断是否开启
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
        //店铺新订单创建提醒
        NoticeLogDO noticeLogDO = new NoticeLogDO();
        List<OrderDTO> orderList = tradeVO.getOrderList();
        for (OrderDTO orderDTO : orderList) {
            MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSNEW);
            // 判断是否开启
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
        //商品评价提醒
        NoticeLogDO noticeLogDO = new NoticeLogDO();
        MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSEVALUATE);
        // 判断是否开启
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
