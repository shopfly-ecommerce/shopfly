/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
import dev.shopflix.consumer.core.event.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 消息模版发送短信
 *
 * @author zjp
 * @version v7.0
 * @since v7.0
 * 2018年3月25日 下午3:15:01
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
        //获取模板
        MessageTemplateDO messageTemplate = null;
        //获取系统配置
        SiteSetting siteSetting = this.getSiteSetting();
        //系统联系方式
        InformationSetting infoSetting = this.getInfoSetting();
        //获取当前下单会员信息
        Member member = memberClient.getModel(orderDO.getMemberId());
        //订单支付提醒
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.PAID_OFF.name())) {
            Map<String, Object> valuesMap = new HashMap<String, Object>(4);
            valuesMap.put("ordersSn", orderDO.getSn());
            valuesMap.put("paymentTime", DateUtil.toString(orderDO.getPaymentTime(), "yyyy-MM-dd"));
            valuesMap.put("siteName", siteSetting.getSiteName());
            // 店铺订单支付提醒
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSPAY);
            // 判断短信是否开启
            if (messageTemplate != null) {
                if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    // 发送短信
                    this.sendSms(this.getSmsMessage(infoSetting.getPhone(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
                }
            }
            // 会员订单支付提醒
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSPAY);
            if (messageTemplate != null) {
                // 判断短信是否开启
                if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    this.sendSms(this.getSmsMessage(member.getMobile(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
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
            if (messageTemplate != null) {
                // 判断短信是否开启
                if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    this.sendSms(this.getSmsMessage(infoSetting.getPhone(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
                }
            }
            //会员订单收货提醒
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSRECEIVE);
            if (messageTemplate != null) {
                // 判断短信是否开启
                if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    this.sendSms(this.getSmsMessage(member.getMobile(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
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
            if (messageTemplate != null) {
                // 判断短信是否开启
                if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    this.sendSms(this.getSmsMessage(member.getMobile(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
                }
            }

            // 发送店铺消息
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSCANCEL);
            if (messageTemplate != null) {
                // 判断短信是否开启
                if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                    this.sendSms(this.getSmsMessage(infoSetting.getPhone(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
                }
            }
        }

        //订单发货提醒
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.SHIPPED.name())) {
            // 会员消息发送
            messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERORDERSSEND);
            if (messageTemplate != null) {
                // 判断短信是否开启
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
     * 售后消息
     */
    @Override
    public void refund(RefundChangeMsg refundChangeMsg) {
        SmsSendVO smsSendVO = new SmsSendVO();
        OrderDetailDTO orderDetailDTO = orderClient.getModel(refundChangeMsg.getRefund().getOrderSn());
        smsSendVO.setMobile(orderDetailDTO.getShipTel());
        //获取当前下单会员信息
        Member member = memberClient.getModel(refundChangeMsg.getRefund().getMemberId());
        //获取系统配置
        SiteSetting siteSetting = this.getSiteSetting();
        //系统联系方式
        InformationSetting infoSetting = this.getInfoSetting();
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

                if (messageTemplate != null) {
                    // 判断短信是否开启
                    if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                        this.sendSms(this.getSmsMessage(member.getMobile(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
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
                if (messageTemplate != null) {
                    // 判断短信是否开启
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
        //获取系统配置
        SiteSetting siteSetting = this.getSiteSetting();
        //系统联系方式
        InformationSetting infoSetting = this.getInfoSetting();

        //商品下架消息提醒
        if (GoodsChangeMsg.UNDER_OPERATION == goodsChangeMsg.getOperationType() && !StringUtil.isEmpty(goodsChangeMsg.getMessage())) {
            //发送店铺消息
            for (Integer goodsId : goodsChangeMsg.getGoodsIds()) {
                CacheGoods goods = goodsClient.getFromCache(goodsId);
                smsSendVO.setMobile(infoSetting.getPhone());
                // 记录店铺订单取消信息（商家中心查看）
                MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPGOODSMARKETENABLE);
                if (messageTemplate != null) {
                    // 判断短信是否开启
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
        //获取系统配置
        SiteSetting siteSetting = this.getSiteSetting();
        Member member = memberClient.getModel(memberLoginMsg.getMemberId());

        SmsSendVO smsSendVO = new SmsSendVO();
        smsSendVO.setMobile(member.getMobile());

        MessageTemplateDO messageTemplate = null;

        // 记录会员登陆成功信息（会员中心查看）
        messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERLOGINSUCCESS);
        // 判断站内信是否开启
        if (messageTemplate != null) {
            // 判断短信是否开启
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
        //获取系统配置
        SiteSetting siteSetting = this.getSiteSetting();
        Member member = memberClient.getModel(memberRegisterMsg.getMember().getMemberId());
        SmsSendVO smsSendVO = new SmsSendVO();
        smsSendVO.setMobile(member.getMobile());

        //会员注册成功提醒
        MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.MEMBERREGISTESUCCESS);
        if (messageTemplate != null) {
            // 判断短信是否开启
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
        //获取系统配置
        SiteSetting siteSetting = this.getSiteSetting();
        //系统联系方式
        InformationSetting infoSetting = this.getInfoSetting();
        //店铺新订单创建提醒
        List<OrderDTO> orderList = tradeVO.getOrderList();
        SmsSendVO smsSendVO = new SmsSendVO();
        for (OrderDTO orderDTO : orderList) {
            MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSNEW);
            // 判断是否开启
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
     * 商品评论
     *
     * @param goodsCommentMsg 商品评论消息
     */
    @Override
    public void goodsComment(GoodsCommentMsg goodsCommentMsg) {
        //获取系统配置
        SiteSetting siteSetting = this.getSiteSetting();
        //系统联系方式
        InformationSetting infoSetting = this.getInfoSetting();
        //获取坪林的消息模板
        MessageTemplateDO messageTemplate = messageTemplateClient.getModel(MessageCodeEnum.SHOPORDERSEVALUATE);
        if (messageTemplate != null) {
            if (messageTemplate.getSmsState().equals(MessageOpenStatusEnum.OPEN.value())) {
                Map<String, Object> valuesMap = new HashMap<String, Object>(4);
                valuesMap.put("siteName", siteSetting.getSiteName());
                valuesMap.put("ordersSn", goodsCommentMsg.getComment().getOrderSn());
                valuesMap.put("userName", goodsCommentMsg.getComment().getMemberName());
                valuesMap.put("evalTime", DateUtil.toString(goodsCommentMsg.getComment().getCreateTime(), "yyyy-MM-dd"));
                //获取当前店铺所有者的联系方式
                this.sendSms(this.getSmsMessage(infoSetting.getPhone(), this.replaceContent(messageTemplate.getSmsContent(), valuesMap)));
            }
        }
    }


    /**
     * 组织短信发送的相关信息
     *
     * @param mobile  手机号
     * @param content 内容
     * @return
     */
    private SmsSendVO getSmsMessage(String mobile, String content) {
        SmsSendVO smsSendVO = new SmsSendVO();
        smsSendVO.setMobile(mobile);
        smsSendVO.setContent(content);
        return smsSendVO;
    }


}
