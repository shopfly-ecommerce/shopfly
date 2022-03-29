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
import com.enation.app.javashop.core.base.model.vo.EmailVO;
import com.enation.app.javashop.core.base.model.vo.SmsSendVO;
import com.enation.app.javashop.core.client.goods.GoodsClient;
import com.enation.app.javashop.core.client.member.MemberClient;
import com.enation.app.javashop.core.client.system.EmailClient;
import com.enation.app.javashop.core.client.system.MessageTemplateClient;
import com.enation.app.javashop.core.client.trade.OrderClient;
import com.enation.app.javashop.core.goods.model.vo.CacheGoods;
import com.enation.app.javashop.core.member.model.dos.Member;
import com.enation.app.javashop.core.member.model.vo.MemberLoginMsg;
import com.enation.app.javashop.core.system.enums.MessageCodeEnum;
import com.enation.app.javashop.core.system.model.dos.MessageTemplateDO;
import com.enation.app.javashop.core.system.model.enums.MessageOpenStatusEnum;
import com.enation.app.javashop.core.system.model.vo.InformationSetting;
import com.enation.app.javashop.core.system.model.vo.SiteSetting;
import com.enation.app.javashop.core.trade.order.model.dos.OrderDO;
import com.enation.app.javashop.core.trade.order.model.dto.OrderDTO;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import com.enation.app.javashop.core.trade.order.model.vo.TradeVO;
import com.enation.app.javashop.core.trade.sdk.model.OrderDetailDTO;
import com.enation.app.javashop.framework.util.DateUtil;
import com.enation.app.javashop.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息模版发送邮件
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018年3月26日 下午4:45:27
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

        //系统联系方式
        InformationSetting infoSetting = this.getInfoSetting();

        Member member = memberClient.getModel(orderDO.getMemberId());

        //系统设置
        SiteSetting siteSetting = this.getSiteSetting();

        EmailVO emailVO = new EmailVO();

        MessageTemplateDO messageTemplate = null;

        //订单支付提醒
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.PAID_OFF.name())) {
            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("paymentTime", DateUtil.toString(orderDO.getPaymentTime(), "yyyy-MM-dd"));
            valuesMap.put("siteName", siteSetting.getSiteName());
            // 店铺订单支付提醒
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSPAY);
            // 判断是否开启
            if (infoSetting.getEmail() != null && messageTemplate != null) {
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    emailVO.setEmail(infoSetting.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
                }
            }
            // 会员订单支付提醒
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSPAY);
            if (member.getEmail() != null && messageTemplate != null) {
                // 判断是否开启
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    emailVO.setEmail(member.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
                }
            }

        }

        //订单收货提醒
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.ROG.name())) {
            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("finishTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));
            valuesMap.put("siteName", siteSetting.getSiteName());
            // 店铺订单收货提醒
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSRECEIVE);
            if (infoSetting.getEmail() != null && messageTemplate != null) {
                // 判断是否开启
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    emailVO.setEmail(infoSetting.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
                }

            }
            //会员订单收货提醒
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSRECEIVE);
            if (member.getEmail() != null && messageTemplate != null) {
                // 判断是否开启
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    emailVO.setEmail(member.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
                }
            }
        }

        //订单取消提醒
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.CANCELLED.name())) {
            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("cancelTime", DateUtil.toString(DateUtil.getDateline(), "yyyy-MM-dd"));
            valuesMap.put("siteName", siteSetting.getSiteName());

            // 发送会员消息
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSCANCEL);
            if (member.getEmail() != null && messageTemplate != null) {
                // 判断是否开启
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    emailVO.setEmail(member.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
                }
            }
            // 发送店铺消息
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSCANCEL);
            if (infoSetting.getEmail() != null && messageTemplate != null) {
                // 判断是否开启
                if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    emailVO.setEmail(infoSetting.getEmail());
                    this.send(emailVO, messageTemplate, valuesMap);
                }
            }
        }

        //订单发货提醒
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.SHIPPED.name())) {
            // 会员消息发送
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSSEND);
            if (member.getEmail() != null && messageTemplate != null) {
                // 判断是否开启
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
        //系统联系方式
        InformationSetting infoSetting = this.getInfoSetting();

        Member member = memberClient.getModel(orderDetailDTO.getMemberId());

        //退货/款提醒
        if (refundChangeMsg.getRefundStatusEnum().equals(RefundStatusEnum.APPLY)) {
            if (orderDetailDTO != null) {

                MessageTemplateDO messageTemplate = null;

                Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                valuesMap.put("refundSn", refundChangeMsg.getRefund().getSn());
                valuesMap.put("siteName", siteSetting.getSiteName());

                // 会员信息发送
                // 记录会员订单取消信息（会员中心查看）
                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_GOODS.name())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERRETURNUPDATE);
                }

                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.name())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERREFUNDUPDATE);
                }

                if (member.getEmail() != null && messageTemplate != null) {
                    // 判断是否开启
                    if (messageTemplate.getEmailState().equals(MessageOpenStatusEnum.OPEN.value())) {
                        emailVO.setEmail(member.getEmail());
                        this.send(emailVO, messageTemplate, valuesMap);
                    }
                }

                // 店铺信息发送
                messageTemplate = null;
                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_GOODS.name())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPRETURN);
                }

                if (refundChangeMsg.getRefund().getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.name())) {
                    messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPREFUND);
                }

                // 记录店铺订单取消信息（商家中心查看）
                if (infoSetting.getEmail() != null && messageTemplate != null) {
                    // 判断是否开启
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
        //商品审核失败提醒
        SiteSetting siteSetting = this.getSiteSetting();
        //系统联系方式
        InformationSetting infoSetting = this.getInfoSetting();

        //商品下架消息提醒
        if (GoodsChangeMsg.UNDER_OPERATION == goodsChangeMsg.getOperationType() && !StringUtil.isEmpty(goodsChangeMsg.getMessage())) {
            //发送店铺消息
            for (Integer goodsId : goodsChangeMsg.getGoodsIds()) {
                CacheGoods goods = goodsClient.getFromCache(goodsId);
                // 记录店铺订单取消信息（商家中心查看）
                MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPGOODSMARKETENABLE);
                if (infoSetting.getEmail() != null && messageTemplate != null) {
                    // 判断是否开启
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
        //如果是会员登录
        EmailVO emailVO = new EmailVO();
        SiteSetting siteSetting = this.getSiteSetting();
        Member member = memberClient.getModel(memberLoginMsg.getMemberId());

        SmsSendVO smsSendVO = new SmsSendVO();
        smsSendVO.setMobile(member.getMobile());

        MessageTemplateDO messageTemplate = null;

        // 记录会员登陆成功信息（会员中心查看）
        messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERLOGINSUCCESS);
        // 判断站内信是否开启
        if (member.getEmail() != null && messageTemplate != null) {
            // 判断是否开启
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
        //会员注册成功提醒
        MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERREGISTESUCCESS);
        if (member.getEmail() != null && messageTemplate != null) {
            // 判断是否开启
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
        //店铺新订单创建提醒
        List<OrderDTO> orderList = tradeVO.getOrderList();
        EmailVO emailVO = new EmailVO();
        //系统联系方式
        InformationSetting infoSetting = this.getInfoSetting();

        for (OrderDTO orderDTO : orderList) {
            MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSNEW);
            // 判断是否开启
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
        //获取模板信息
        MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSEVALUATE);
        //系统联系方式
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
