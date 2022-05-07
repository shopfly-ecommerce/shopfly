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
import cloud.shopfly.b2c.core.base.model.vo.EmailVO;
import cloud.shopfly.b2c.core.base.model.vo.SmsSendVO;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.client.system.EmailClient;
import cloud.shopfly.b2c.core.client.system.MessageTemplateClient;
import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.vo.MemberLoginMsg;
import cloud.shopfly.b2c.core.system.enums.MessageCodeEnum;
import cloud.shopfly.b2c.core.system.model.dos.MessageTemplateDO;
import cloud.shopfly.b2c.core.system.model.enums.MessageOpenStatusEnum;
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
 * Message templates send messages
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018years3month26On the afternoon4:45:27
 */
@Component
public class EmailSendMessageConsumer extends AbstractMessage implements OrderStatusChangeEvent, RefundStatusChangeEvent, GoodsChangeEvent, MemberLoginEvent, MemberRegisterEvent, TradeIntoDbEvent, GoodsCommentEvent {


    @Autowired
    private MessageTemplateClient messageTemplateClient;


    @Autowired
    private OrderClient orderClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private MemberClient memberClient;

    @Autowired
    private EmailClient emailClient;

    public void send(EmailVO emailVO, MessageTemplateDO messageTemplate, Map<String, Object> valuesMap) {
        emailVO.setTitle(messageTemplate.getEmailTitle());
        emailVO.setContent(this.replaceContent(messageTemplate.getEmailContent(), valuesMap));
        emailClient.sendEmail(emailVO);
    }

    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {
        OrderDO orderDO = orderMessage.getOrderDO();

        // System Contact
        InformationSetting infoSetting = this.getInfoSetting();

        Member member = memberClient.getModel(orderDO.getMemberId());

        // System Settings
        SiteSetting siteSetting = this.getSiteSetting();

        EmailVO emailVO = new EmailVO();

        MessageTemplateDO messageTemplate = null;

        // Order Payment reminder
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.PAID_OFF.name())) {
            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("paymentTime", DateUtil.toString(orderDO.getPaymentTime(), "yyyy-MM-dd"));
            valuesMap.put("siteName", siteSetting.getSiteName());
            // Store order payment reminder
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSPAY);
            // Check whether the function is enabled.
            if (infoSetting.getEmail() != null && messageTemplate != null) {
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    emailVO.setEmail(infoSetting.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
                }
            }
            // Member order payment reminder
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSPAY);
            if (member.getEmail() != null && messageTemplate != null) {
                // Check whether the function is enabled.
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    emailVO.setEmail(member.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
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
            if (infoSetting.getEmail() != null && messageTemplate != null) {
                // Check whether the function is enabled.
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    emailVO.setEmail(infoSetting.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
                }

            }
            // Member order receipt reminder
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSRECEIVE);
            if (member.getEmail() != null && messageTemplate != null) {
                // Check whether the function is enabled.
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    emailVO.setEmail(member.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
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
            if (member.getEmail() != null && messageTemplate != null) {
                // Check whether the function is enabled.
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    emailVO.setEmail(member.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
                }
            }
            // Send store messages
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSCANCEL);
            if (infoSetting.getEmail() != null && messageTemplate != null) {
                // Check whether the function is enabled.
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    emailVO.setEmail(infoSetting.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
                }
            }
        }

        // Order shipping Reminder
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.SHIPPED.name())) {
            // Member Message sending
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSSEND);
            if (member.getEmail() != null && messageTemplate != null) {
                // Check whether the function is enabled.
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                    valuesMap.put("ordersSn", orderDO.getSn());
                    valuesMap.put("shipSn", orderDO.getShipNo());
                    valuesMap.put("sendTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));
                    valuesMap.put("siteName", siteSetting.getSiteName());
                    emailVO.setEmail(member.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
                }
            }
        }
    }

    @Override
    public void refund(RefundChangeMsg refundChangeMsg) {
        EmailVO emailVO = new EmailVO();
        OrderDetailDTO orderDetailDTO = orderClient.getModel(refundChangeMsg.getRefund().getOrderSn());
        SiteSetting siteSetting = this.getSiteSetting();
        // System Contact
        InformationSetting infoSetting = this.getInfoSetting();

        Member member = memberClient.getModel(orderDetailDTO.getMemberId());

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

                if (member.getEmail() != null && messageTemplate != null) {
                    // Check whether the function is enabled.
                    if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                        emailVO.setEmail(member.getEmail());
                        this.send(emailVO, messageTemplate, valuesMap);
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
                if (infoSetting.getEmail() != null && messageTemplate != null) {
                    // Check whether the function is enabled.
                    if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                        emailVO.setEmail(infoSetting.getEmail());
                        this.send(emailVO, messageTemplate, valuesMap);
                    }
                }
            }
        }
    }

    @Override
    public void goodsChange(GoodsChangeMsg goodsChangeMsg) {
        EmailVO emailVO = new EmailVO();
        // Product audit failure reminder
        SiteSetting siteSetting = this.getSiteSetting();
        // System Contact
        InformationSetting infoSetting = this.getInfoSetting();

        // Notification of merchandise removal
        if (GoodsChangeMsg.UNDER_OPERATION == goodsChangeMsg.getOperationType() && !StringUtil.isEmpty(goodsChangeMsg.getMessage())) {
            // Send store messages
            for (Integer goodsId : goodsChangeMsg.getGoodsIds()) {
                CacheGoods goods = goodsClient.getFromCache(goodsId);
                // Record store order cancellation information (view in merchant center)
                MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPGOODSMARKETENABLE);
                if (infoSetting.getEmail() != null && messageTemplate != null) {
                    // Check whether the function is enabled.
                    if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                        Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                        valuesMap.put("siteName", siteSetting.getSiteName());
                        valuesMap.put("name", goods.getGoodsName());
                        valuesMap.put("reason", goodsChangeMsg.getMessage());
                        emailVO.setEmail(infoSetting.getEmail());
                        this.send(emailVO, messageTemplate, valuesMap);
                    }
                }
            }
        }
    }

    @Override
    public void memberLogin(MemberLoginMsg memberLoginMsg) {
        // If you are a member login
        EmailVO emailVO = new EmailVO();
        SiteSetting siteSetting = this.getSiteSetting();
        Member member = memberClient.getModel(memberLoginMsg.getMemberId());

        SmsSendVO smsSendVO = new SmsSendVO();
        smsSendVO.setMobile(member.getMobile());

        MessageTemplateDO messageTemplate = null;

        // Record member login success information (check in member center)
        messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERLOGINSUCCESS);
        // Check whether the station message is open
        if (member.getEmail() != null && messageTemplate != null) {
            // Check whether the function is enabled.
            if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                valuesMap.put("name", member.getUname());
                valuesMap.put("loginTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));
                valuesMap.put("siteName", siteSetting.getSiteName());
                emailVO.setEmail(member.getEmail());
                this.send(emailVO, messageTemplate, valuesMap);
            }
        }
    }

    @Override
    public void memberRegister(MemberRegisterMsg memberRegisterMsg) {
        SiteSetting siteSetting = this.getSiteSetting();
        Member member = memberClient.getModel(memberRegisterMsg.getMember().getMemberId());
        EmailVO emailVO = new EmailVO();
        // Member registration success reminder
        MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERREGISTESUCCESS);
        if (member.getEmail() != null && messageTemplate != null) {
            // Check whether the function is enabled.
            if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                valuesMap.put("name", member.getUname());
                valuesMap.put("siteName", siteSetting.getSiteName());
                valuesMap.put("loginTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));
                emailVO.setEmail(member.getEmail());
                this.send(emailVO, messageTemplate, valuesMap);
            }
        }
    }

    @Override
    public void onTradeIntoDb(TradeVO tradeVO) {
        SiteSetting siteSetting = this.getSiteSetting();
        // Store new order creation reminder
        List<OrderDTO> orderList = tradeVO.getOrderList();
        EmailVO emailVO = new EmailVO();
        // System Contact
        InformationSetting infoSetting = this.getInfoSetting();

        for (OrderDTO orderDTO : orderList) {
            MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSNEW);
            // Check whether the function is enabled.
            if (infoSetting.getEmail() != null && messageTemplate != null) {
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                    valuesMap.put("ordersSn", orderDTO.getSn());
                    valuesMap.put("createTime", DateUtil.toString(orderDTO.getCreateTime(), "yyyy-MM-dd"));
                    valuesMap.put("siteName", siteSetting.getSiteName());
                    emailVO.setEmail(infoSetting.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
                }
            }

        }
    }

    @Override
    public void goodsComment(GoodsCommentMsg goodsCommentMsg) {
        SiteSetting siteSetting = this.getSiteSetting();
        EmailVO emailVO = new EmailVO();
        // Obtaining Template Information
        MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSEVALUATE);
        // System Contact
        InformationSetting infoSetting = this.getInfoSetting();
        if (infoSetting.getEmail() != null && messageTemplate != null) {
            if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                valuesMap.put("ordersSn", goodsCommentMsg.getComment().getOrderSn());
                valuesMap.put("evalTime", DateUtil.toString(goodsCommentMsg.getComment().getCreateTime(), "yyyy-MM-dd"));
                valuesMap.put("userName", goodsCommentMsg.getComment().getMemberName());
                valuesMap.put("siteName", siteSetting.getSiteName());
                emailVO.setEmail(infoSetting.getEmail());
                this.send(emailVO, messageTemplate, valuesMap);
            }
        }


    }


}
