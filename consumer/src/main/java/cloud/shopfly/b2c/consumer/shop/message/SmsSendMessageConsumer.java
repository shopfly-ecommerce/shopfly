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
import cloud.shopfly.b2c.core.base.model.vo.SmsSendVO;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.client.system.MessageTemplateClient;
import cloud.shopfly.b2c.core.client.system.SmsClient;
import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.vo.MemberLoginMsg;
import cloud.shopfly.b2c.core.system.enums.MessageCodeEnum;
import cloud.shopfly.b2c.core.system.enums.MessageOpenStatusEnum;
import cloud.shopfly.b2c.core.system.model.dos.MessageTemplateDO;
import cloud.shopfly.b2c.core.system.model.vo.InformationSetting;
import cloud.shopfly.b2c.core.system.model.vo.SiteSetting;
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
 * Message template sends SMS messages
 *
 * @author zjp
 * @version v7.0
 * @since v7.0
 * 2018years3month25On the afternoon3:15:01
 */
@Component
public class SmsSendMessageConsumer extends AbstractMessage implements OrderStatusChangeEvent, RefundStatusChangeEvent, GoodsChangeEvent, MemberLoginEvent, MemberRegisterEvent, TradeIntoDbEvent, GoodsCommentEvent {

    @Autowired
    private MessageTemplateClient messageTemplateClient;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private MemberClient memberClient;

    @Autowired
    private SmsClient smsClient;

    private void sendSms(SmsSendVO smsSendVO) {
        smsClient.send(smsSendVO);
    }


    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {
        OrderDO orderDO = orderMessage.getOrderDO();
        // Access to the template
        MessageTemplateDO messageTemplate = null;
        // Obtaining system Configuration
        SiteSetting siteSetting = this.getSiteSetting();
        // System Contact
        InformationSetting infoSetting = this.getInfoSetting();
        // Get the current order member information
        Member member = memberClient.getModel(orderDO.getMemberId());
        // Order Payment reminder
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.PAID_OFF.name())) {
            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("paymentTime", DateUtil.toString(orderDO.getPaymentTime(), "yyyy-MM-dd"));
            valuesMap.put("siteName", siteSetting.getSiteName());
            // Store order payment reminder
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSPAY);
            // Check whether SMS is enabled
            if (messageTemplate != null) {
                if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    // Send a text message
                    this.sendSms(this.getSmsMessage(infoSetting.getPhone(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
                }
            }
            // Member order payment reminder
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSPAY);
            if (messageTemplate != null) {
                // Check whether SMS is enabled
                if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    this.sendSms(this.getSmsMessage(member.getMobile(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
                }
            }

        }

        // Order receipt reminder
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.ROG.name())) {
            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("finishTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));
            valuesMap.put("siteName", siteSetting.getSiteName());
            // Store order receipt reminder
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSRECEIVE);
            if (messageTemplate != null) {
                // Check whether SMS is enabled
                if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    this.sendSms(this.getSmsMessage(infoSetting.getPhone(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
                }
            }
            // Member order receipt reminder
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSRECEIVE);
            if (messageTemplate != null) {
                // Check whether SMS is enabled
                if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    this.sendSms(this.getSmsMessage(member.getMobile(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
                }
            }
        }

        // Order Cancellation Reminder
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.CANCELLED.name())) {
            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("cancelTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));
            valuesMap.put("siteName", siteSetting.getSiteName());
            // Send membership messages
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSCANCEL);
            if (messageTemplate != null) {
                // Check whether SMS is enabled
                if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    this.sendSms(this.getSmsMessage(member.getMobile(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
                }
            }

            // Send store messages
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSCANCEL);
            if (messageTemplate != null) {
                // Check whether SMS is enabled
                if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    this.sendSms(this.getSmsMessage(infoSetting.getPhone(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
                }
            }
        }

        // Order shipping Reminder
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.SHIPPED.name())) {
            // Member Message sending
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSSEND);
            if (messageTemplate != null) {
                // Check whether SMS is enabled
                if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                    valuesMap.put("ordersSn", orderDO.getSn());
                    valuesMap.put("shipSn", orderDO.getShipNo());
                    valuesMap.put("sendTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));
                    valuesMap.put("siteName", siteSetting.getSiteName());
                    this.sendSms(this.getSmsMessage(member.getMobile(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
                }
            }
        }
    }


    /**
     * After the news
     */
    @Override
    public void refund(RefundChangeMsg refundChangeMsg) {
        SmsSendVO smsSendVO = new SmsSendVO();
        OrderDetailDTO orderDetailDTO = orderClient.getModel(refundChangeMsg.getRefund().getOrderSn());
        smsSendVO.setMobile(orderDetailDTO.getShipTel());
        // Get the current order member information
        Member member = memberClient.getModel(refundChangeMsg.getRefund().getMemberId());
        // Obtaining system Configuration
        SiteSetting siteSetting = this.getSiteSetting();
        // System Contact
        InformationSetting infoSetting = this.getInfoSetting();
        // Return/payment reminder
        if (refundChangeMsg.getRefundStatusEnum().equals(RefundStatusEnum.APPLY)) {
            if (orderDetailDTO != null) {

                MessageTemplateDO messageTemplate = null;

                Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                valuesMap.put("refundSn", refundChangeMsg.getRefund().getSn());
                valuesMap.put("siteName", siteSetting.getSiteName());
                // Member Information sending
                // Record cancellation information of member order (check in member Center)
                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_GOODS.name())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERRETURNUPDATE);
                }

                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.name())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERREFUNDUPDATE);
                }

                if (messageTemplate != null) {
                    // Check whether SMS is enabled
                    if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                        this.sendSms(this.getSmsMessage(member.getMobile(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
                    }
                }


                // Store Information sending
                messageTemplate = null;
                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_GOODS.name())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPRETURN);
                }

                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.name())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPREFUND);
                }

                // Record store order cancellation information (view in merchant center)
                if (messageTemplate != null) {
                    // Check whether SMS is enabled
                    if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                        this.sendSms(this.getSmsMessage(infoSetting.getPhone(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
                    }
                }
            }
        }

    }

    @Override
    public void goodsChange(GoodsChangeMsg goodsChangeMsg) {
        SmsSendVO smsSendVO = new SmsSendVO();
        // Obtaining system Configuration
        SiteSetting siteSetting = this.getSiteSetting();
        // System Contact
        InformationSetting infoSetting = this.getInfoSetting();

        // Notification of merchandise removal
        if (GoodsChangeMsg.UNDER_OPERATION == goodsChangeMsg.getOperationType() && !StringUtil.isEmpty(goodsChangeMsg.getMessage())) {
            // Send store messages
            for (Integer goodsId : goodsChangeMsg.getGoodsIds()) {
                CacheGoods goods = goodsClient.getFromCache(goodsId);
                smsSendVO.setMobile(infoSetting.getPhone());
                // Record store order cancellation information (view in merchant center)
                MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPGOODSMARKETENABLE);
                if (messageTemplate != null) {
                    // Check whether SMS is enabled
                    if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                        Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                        valuesMap.put("siteName", siteSetting.getSiteName());
                        valuesMap.put("name", goods.getGoodsName());
                        valuesMap.put("reason", goodsChangeMsg.getMessage());
                        smsSendVO.setContent(this.replaceContent(messageTemplate.getSmsContent(), valuesMap));
                        this.sendSms(smsSendVO);
                    }
                }
            }
        }
    }

    @Override
    public void memberLogin(MemberLoginMsg memberLoginMsg) {
        // Obtaining system Configuration
        SiteSetting siteSetting = this.getSiteSetting();
        Member member = memberClient.getModel(memberLoginMsg.getMemberId());

        SmsSendVO smsSendVO = new SmsSendVO();
        smsSendVO.setMobile(member.getMobile());

        MessageTemplateDO messageTemplate = null;

        // Record member login success information (check in member center)
        messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERLOGINSUCCESS);
        // Check whether the station message is open
        if (messageTemplate != null) {
            // Check whether SMS is enabled
            if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                valuesMap.put("name", member.getUname());
                valuesMap.put("loginTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));
                valuesMap.put("siteName", siteSetting.getSiteName());
                smsSendVO.setContent(this.replaceContent(messageTemplate.getSmsContent(), valuesMap));
                this.sendSms(smsSendVO);
            }
        }
    }

    @Override
    public void memberRegister(MemberRegisterMsg memberRegisterMsg) {
        // Obtaining system Configuration
        SiteSetting siteSetting = this.getSiteSetting();
        Member member = memberClient.getModel(memberRegisterMsg.getMember().getMemberId());
        SmsSendVO smsSendVO = new SmsSendVO();
        smsSendVO.setMobile(member.getMobile());

        // Member registration success reminder
        MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERREGISTESUCCESS);
        if (messageTemplate != null) {
            // Check whether SMS is enabled
            if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {

                Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                valuesMap.put("name", member.getUname());
                valuesMap.put("siteName", siteSetting.getSiteName());
                valuesMap.put("loginTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));
                smsSendVO.setContent(this.replaceContent(messageTemplate.getSmsContent(), valuesMap));
                this.sendSms(smsSendVO);
            }
        }
    }

    @Override
    public void onTradeIntoDb(TradeVO tradeVO) {
        // Obtaining system Configuration
        SiteSetting siteSetting = this.getSiteSetting();
        // System Contact
        InformationSetting infoSetting = this.getInfoSetting();
        // Store new order creation reminder
        List<OrderDTO> orderList = tradeVO.getOrderList();
        SmsSendVO smsSendVO = new SmsSendVO();
        for (OrderDTO orderDTO : orderList) {
            MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSNEW);
            // Check whether the function is enabled.
            if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                valuesMap.put("ordersSn", orderDTO.getSn());
                valuesMap.put("createTime", DateUtil.toString(orderDTO.getCreateTime(), "yyyy-MM-dd"));
                valuesMap.put("siteName", siteSetting.getSiteName());
                smsSendVO.setMobile(infoSetting.getPhone());
                smsSendVO.setContent(this.replaceContent(messageTemplate.getSmsContent(), valuesMap));
                this.sendSms(smsSendVO);
            }
        }
    }

    /**
     * Product comments
     *
     * @param goodsCommentMsg Product Review messages
     */
    @Override
    public void goodsComment(GoodsCommentMsg goodsCommentMsg) {
        // Obtaining system Configuration
        SiteSetting siteSetting = this.getSiteSetting();
        // System Contact
        InformationSetting infoSetting = this.getInfoSetting();
        // Get pinglins message template
        MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSEVALUATE);
        if (messageTemplate != null) {
            if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                valuesMap.put("siteName", siteSetting.getSiteName());
                valuesMap.put("ordersSn", goodsCommentMsg.getComment().getOrderSn());
                valuesMap.put("userName", goodsCommentMsg.getComment().getMemberName());
                valuesMap.put("evalTime", DateUtil.toString(goodsCommentMsg.getComment().getCreateTime(), "yyyy-MM-dd"));
                // Gets the contact information of the current store owner
                this.sendSms(this.getSmsMessage(infoSetting.getPhone(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
            }
        }
    }


    /**
     * Organize messages to be sent
     *
     * @param mobile  Mobile phone no.
     * @param content content
     * @return
     */
    private SmsSendVO getSmsMessage(String mobile, String content) {
        SmsSendVO smsSendVO = new SmsSendVO();
        smsSendVO.setMobile(mobile);
        smsSendVO.setContent(content);
        return smsSendVO;
    }


}
