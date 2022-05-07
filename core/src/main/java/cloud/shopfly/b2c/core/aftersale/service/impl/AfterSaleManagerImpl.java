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
package cloud.shopfly.b2c.core.aftersale.service.impl;

import cloud.shopfly.b2c.core.aftersale.AftersaleErrorCode;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundGoodsDO;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundLogDO;
import cloud.shopfly.b2c.core.aftersale.model.dto.RefundDTO;
import cloud.shopfly.b2c.core.aftersale.model.dto.RefundDetailDTO;
import cloud.shopfly.b2c.core.aftersale.model.enums.*;
import cloud.shopfly.b2c.core.aftersale.model.vo.*;
import cloud.shopfly.b2c.core.aftersale.service.AfterSaleManager;
import cloud.shopfly.b2c.core.aftersale.service.RefundOperateChecker;
import cloud.shopfly.b2c.core.payment.model.dos.PaymentMethodDO;
import cloud.shopfly.b2c.core.payment.service.PaymentMethodManager;
import cloud.shopfly.b2c.core.payment.service.RefundManager;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderItemsDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PayStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.ServiceStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.CancelVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderOperateAllowable;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import cloud.shopfly.b2c.core.trade.order.service.OrderOperateManager;
import cloud.shopfly.b2c.core.trade.order.service.OrderQueryManager;
import cloud.shopfly.b2c.framework.util.*;
import cloud.shopfly.b2c.core.aftersale.model.vo.*;
import cloud.shopfly.b2c.core.base.message.RefundChangeMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.goods.model.enums.Permission;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderDetailVO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderSkuDTO;
import cloud.shopfly.b2c.core.aftersale.model.enums.*;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;

import cloud.shopfly.b2c.framework.security.model.Buyer;
import org.apache.commons.collections.map.HashedMap;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjp
 * @version v7.0
 * @Description After-sale management business
 * @ClassName AfterSaleManagerImpl
 * @since v7.0 In the morning11:32 2018/5/8
 */
@Service
public class AfterSaleManagerImpl implements AfterSaleManager {


    @Autowired

    private DaoSupport daoSupport;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private OrderQueryManager orderQueryManager;

    @Autowired
    private OrderOperateManager orderOperateManager;

    @Autowired
    private RefundManager refundManager;

    @Autowired
    private PaymentMethodManager paymentMethodManager;


    /**
     * logging
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void applyRefund(BuyerRefundApplyVO refundApply) {
        refundApply.setRefuseType(RefuseTypeEnum.RETURN_MONEY.value());
        RefundDO refund = this.buyerRefund(refundApply);
        this.log(refund.getSn(), refund.getMemberName(), "To apply for a refund");
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void applyGoodsReturn(BuyerRefundApplyVO goodsReturnsApply) {
        goodsReturnsApply.setRefuseType(RefuseTypeEnum.RETURN_GOODS.value());
        RefundDO refund = this.buyerRefund(goodsReturnsApply);
        this.log(refund.getSn(), refund.getMemberName(), "To apply for a refund");
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void cancelOrder(BuyerCancelOrderVO buyerCancelOrderVO) {
        // Obtain login membership
        Buyer buyer = UserContext.getBuyer();
        // Obtain the order information to verify the owner of the order
        OrderDetailDTO orderDetail = orderQueryManager.getModel(buyerCancelOrderVO.getOrderSn());
        if (orderDetail == null || !buyer.getUid().equals(orderDetail.getMemberId())) {
            throw new ServiceException(AftersaleErrorCode.E604.name(), "Order does not exist");
        }
        baseCancle(buyerCancelOrderVO, orderDetail, false);
    }


    /**
     * Cancel order general
     *
     * @param buyerCancelOrderVO
     * @param orderDetail
     */
    private RefundDO baseCancle(BuyerCancelOrderVO buyerCancelOrderVO, OrderDetailDTO orderDetail, boolean isAdmin) {

        // Only paid orders can be refunded
        if (!orderDetail.getOrderStatus().equals(OrderStatusEnum.PAID_OFF.value()) && !orderDetail.getPayStatus().equals(PayStatusEnum.PAY_YES.value())) {
            throw new ServiceException(AftersaleErrorCode.E601.name(), "Operation not allowed");
        }
        // Order operation check
        OrderOperateAllowable orderOperateAllowableVO = orderDetail.getOrderOperateAllowableVO();
        if (!orderOperateAllowableVO.getAllowServiceCancel()) {
            throw new ServiceException(AftersaleErrorCode.E601.name(), "Operation not allowed");
        }

        String refundSn = DateUtil.toString(DateUtil.getDateline(), "yyMMddhhmmss");
        // Assembled refund form
        RefundDO refundDO = new RefundDO();
        refundDO.setSn(refundSn);
        refundDO.setCustomerRemark(buyerCancelOrderVO.getCustomerRemark());
        refundDO.setMemberId(orderDetail.getMemberId());
        refundDO.setMemberName(orderDetail.getMemberName());
        refundDO.setOrderSn(orderDetail.getSn());
        refundDO.setRefundStatus(RefundStatusEnum.APPLY.name());
        refundDO.setCreateTime(DateUtil.getDateline());
        refundDO.setRefundPrice(orderDetail.getOrderPrice());
        refundDO.setPayOrderNo(orderDetail.getPayOrderNo());

        // Judge whether the current payment method supports the original way to return, if not, the refund method can not be empty
        PaymentMethodDO paymentMethodDO = paymentMethodManager.getByPluginId(orderDetail.getPaymentPluginId());
        String refundWay = RefundWayEnum.ORIGINAL.name();
        // Go back the other way
        if (paymentMethodDO == null || paymentMethodDO.getIsRetrace() == 0) {
            refundWay = RefundWayEnum.OFFLINE.name();
            if (isAdmin) {
                // Here is the group refund and the administrator click to confirm the payment order
                refundDO.setAccountType("");
                refundDO.setReturnAccount("");
                refundDO.setBankAccountName("");
                refundDO.setBankAccountNumber("");
                refundDO.setBankDepositName("");
                refundDO.setBankName("");
            } else {
                if (buyerCancelOrderVO.getAccountType() == null) {
                    throw new ServiceException(AftersaleErrorCode.E605.name(), "Refund method Mandatory");
                }
                // Bank transfer
                if (AccountTypeEnum.BANKTRANSFER.name().equals(buyerCancelOrderVO.getAccountType())) {
                    refundDO.setBankAccountName(buyerCancelOrderVO.getBankAccountName());
                    refundDO.setBankAccountNumber(buyerCancelOrderVO.getBankAccountNumber());
                    refundDO.setBankDepositName(buyerCancelOrderVO.getBankDepositName());
                    refundDO.setBankName(buyerCancelOrderVO.getBankName());
                } else {
                    // Alipay or wechat
                    refundDO.setReturnAccount(buyerCancelOrderVO.getReturnAccount());
                }
                refundDO.setAccountType(buyerCancelOrderVO.getAccountType());
            }

        } else {
            // The original road back WEIXINPAY ALIPAY
            String pluginId = paymentMethodDO.getPluginId();
            String accountType = null;
            if ("weixinPayPlugin".equals(pluginId)) {
                accountType = "WEIXINPAY";
            }
            if ("alipayDirectPlugin".equals(pluginId)) {
                accountType = "ALIPAY";
            }
            refundDO.setAccountType(accountType);
        }
        refundDO.setRefundWay(refundWay);
        refundDO.setRefundReason(buyerCancelOrderVO.getRefundReason());
        refundDO.setRefundType(RefundTypeEnum.CANCEL_ORDER.name());
        refundDO.setPaymentType(PaymentTypeEnum.ONLINE.name());
        refundDO.setRefuseType(RefuseTypeEnum.RETURN_MONEY.name());
        refundDO.setPayOrderNo(orderDetail.getPayOrderNo());
        this.daoSupport.insert(refundDO);
        refundDO.setId(this.daoSupport.getLastId("es_refund"));

        // Generate returned goods
        List<OrderSkuDTO> orderSkuList = orderDetail.getOrderSkuList();
        for (OrderSkuDTO orderSkuDTO : orderSkuList) {
            this.refundGoods(orderSkuDTO, orderSkuDTO.getNum(), refundSn);
        }

        // Update order status and after-sale status
        orderOperateManager.updateOrderServiceStatus(orderDetail.getSn(), ServiceStatusEnum.APPLY.name());

        // Send a message requesting a refund
        RefundChangeMsg refundStatusChangeMessage = new RefundChangeMsg(refundDO, RefundStatusEnum.APPLY);
        this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, AmqpExchange.REFUND_STATUS_CHANGE + "_QUEUE",
                refundStatusChangeMessage));

        return refundDO;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AdminRefundApprovalVO approval(AdminRefundApprovalVO refundApproval, Permission permission) {

        RefundDO refund = this.daoSupport.queryForObject("select * from es_refund where sn =?", RefundDTO.class,
                refundApproval.getSn());
        String operater = "Automatic system audit";

        // Verification of refund amount
        this.checkMoney(refund, refundApproval.getRefundPrice());

        String refundStatus = RefundStatusEnum.REFUSE.value();
        String redundFailReason = "";


        // Decide if you agree to a refund
        if (refundApproval.getAgree().equals(1)) {
            if (refund.getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.value())) {
                refundStatus = RefundStatusEnum.WAIT_FOR_MANUAL.value();
            }
            if (refund.getRefuseType().equals(RefuseTypeEnum.RETURN_GOODS.value())) {
                refundStatus = RefundStatusEnum.PASS.value();
            }
            refund.setRefundStatus(refundStatus);
            refund.setRefundPrice(refundApproval.getRefundPrice());
            // Send the message that is approved
            RefundChangeMsg refundStatusChangeMessage = new RefundChangeMsg(refund, RefundStatusEnum.PASS);
            this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, AmqpExchange.REFUND_STATUS_CHANGE + "_QUEUE",
                    refundStatusChangeMessage));

            // If you apply for a refund and the refund method supports the original way, you can directly refund the original way after the approval
            if (refund.getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.value()) && RefundWayEnum.ORIGINAL.name().equals(refund.getRefundWay()) && refundApproval.getRefundPrice() > 0) {
                // Order Number refund amount
                Map map = refundManager.originRefund(refund.getPayOrderNo(), refund.getSn(), refundApproval.getRefundPrice());
                if ("true".equals(StringUtil.toString(map.get("result")))) {
                    refundStatus = RefundStatusEnum.REFUNDING.value();
                    // Sending a message indicating that the original route is returned successfully
                    refundStatusChangeMessage = new RefundChangeMsg(refund, RefundStatusEnum.REFUNDING);
                    this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, AmqpExchange.REFUND_STATUS_CHANGE + "_QUEUE",
                            refundStatusChangeMessage));
                } else {
                    refundStatus = RefundStatusEnum.REFUNDFAIL.value();
                    redundFailReason = StringUtil.toString(map.get("fail_reason"));
                }
            }
        } else {
            // Send the message that is approved
            RefundChangeMsg refundStatusChangeMessage = new RefundChangeMsg(refund, RefundStatusEnum.REFUSE);
            this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, AmqpExchange.REFUND_STATUS_CHANGE + "_QUEUE",
                    refundStatusChangeMessage));
        }

        this.daoSupport.execute(
                "update es_refund set refund_status=?,seller_remark=? ,refund_price=? ,refund_point=?,refund_fail_reason=? where sn =?",
                refundStatus, refundApproval.getRemark(), refundApproval.getRefundPrice(),
                refundApproval.getRefundPoint(), redundFailReason, refundApproval.getSn());


        // log
        this.log(refundApproval.getSn(), operater, "Review the return（paragraph）, the results for：" + (refundApproval.getAgree() == 1 ? "agree" : "Refused to"));

        return refundApproval;
    }

    @Override
    public FinanceRefundApprovalVO approval(FinanceRefundApprovalVO refundApproval) {
        RefundDO refund = this.daoSupport.queryForObject("select * from es_refund where sn =?", RefundDO.class,
                refundApproval.getSn());
        if (refund == null) {
            throw new ServiceException(AftersaleErrorCode.E603.name(), "Refund slip does not exist");
        }

        // Permission to check
        this.checkAllowable(refund, RefundOperateEnum.ADMIN_REFUND);
        // Check amount
        this.checkMoney(refund, refundApproval.getRefundPrice());

        this.daoSupport.execute("update es_refund set refund_price=? ,refund_status=?,finance_remark = ? ,refund_time = ? where sn=?",
                refundApproval.getRefundPrice(), RefundStatusEnum.COMPLETED.value(), refundApproval.getRemark() == null ? "" : refundApproval.getRemark(),
                DateUtil.getDateline(), refundApproval.getSn());
        // Sends the message approved by the administrator
        RefundChangeMsg refundStatusChangeMessage = new RefundChangeMsg(refund,
                RefundStatusEnum.REFUNDING);
        this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, AmqpExchange.REFUND_STATUS_CHANGE + "_QUEUE",
                refundStatusChangeMessage));
        return refundApproval;
    }

    @Override
    public Page<RefundDTO> query(RefundQueryParamVO param) {
        StringBuffer sql = new StringBuffer();

        List termList = new ArrayList();
        List<String> sqlSplit = new ArrayList<>();

        sql.append("select * from es_refund ");

        String sn = param.getSn();
        if (StringUtil.notEmpty(sn)) {
            sqlSplit.add(" sn=? ");
            termList.add(sn);
        }

        String refundStatus = param.getRefundStatus();
        if (StringUtil.notEmpty(refundStatus)) {
            sqlSplit.add(" refund_status=? ");
            termList.add(refundStatus);
        }

        Integer memberId = param.getMemberId();
        if (memberId != null) {
            sqlSplit.add(" member_id=? ");
            termList.add(memberId);
        }

        String orderSn = param.getOrderSn();
        if (StringUtil.notEmpty(orderSn)) {
            sqlSplit.add(" order_sn=? ");
            termList.add(orderSn);
        }

        String refuseType = param.getRefuseType();
        if (StringUtil.notEmpty(refuseType)) {
            sqlSplit.add(" refuse_type=? ");
            termList.add(refuseType);
        }

        String refundType = param.getRefundType();
        if (StringUtil.notEmpty(refundType)) {
            sqlSplit.add(" refund_type=? ");
            termList.add(refundType);
        }

        // Query by time
        String startTime = param.getStartTime();
        String endTime = param.getEndTime();
        if (StringUtil.notEmpty(startTime)) {
            sqlSplit.add(" create_time >= ? ");
            termList.add(startTime);
        }

        if (StringUtil.notEmpty(endTime)) {
            sqlSplit.add(" create_time <= ? ");
            termList.add(endTime);

        }

        // The refund way
        if (StringUtil.notEmpty(param.getRefundWay())) {
            sqlSplit.add(" refund_way = ? ");
            termList.add(param.getRefundWay());
        }

        // Concatenate SQL conditional statements
        String sqlSplicing = SqlSplicingUtil.sqlSplicing(sqlSplit);
        if (!StringUtil.isEmpty(sqlSplicing)) {
            sql.append(sqlSplicing);
        }

        sql.append(" order by create_time desc");
        Page page = this.daoSupport.queryForPage(sql.toString(), param.getPageNo(), param.getPageSize(),
                RefundDTO.class, termList.toArray());

        return page;
    }

    @Override
    public RefundDetailDTO getDetail(String sn) {
        RefundDTO refundDTO = this.daoSupport.queryForObject("select * from es_refund where sn =?", RefundDTO.class, sn);

        if (refundDTO == null) {
            throw new ServiceException(AftersaleErrorCode.E603.name(), "Refund slip does not exist");
        }

        List<RefundGoodsDO> refundGoodsDOS = this.daoSupport.queryForList("select * from es_refund_goods where refund_sn=? ", RefundGoodsDO.class, sn);
        RefundDetailDTO refundDetail = new RefundDetailDTO();
        refundDetail.setRefund(refundDTO);
        refundDetail.setRefundGoods(refundGoodsDOS);
        return refundDetail;
    }

    @Override
    public List<RefundDO> queryNoReturnOrder() {
        String sql = "select *,sn refund_sn from es_refund where refund_status = ? and refund_way = 'ORIGINAL'";

        List<RefundDO> list = this.daoSupport.queryForList(sql, RefundDO.class, RefundStatusEnum.REFUNDING.value());

        return list;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(List<RefundDO> list) {
        if (StringUtil.isNotEmpty(list)) {
            for (RefundDO refund : list) {
                Map map = new HashedMap(2);
                map.put("refund_status", refund.getRefundStatus());
                Map where = new HashMap(2);
                where.put("sn", refund.getSn());
                this.daoSupport.update("es_refund", map, where);
            }
        }
    }

    @Override
    public RefundApplyVO refundApply(String orderSn, Integer skuId) {
        RefundApplyVO refundApplyVO = new RefundApplyVO();
        OrderDetailVO order = orderQueryManager.getModel(orderSn, null);

        if (!order.getMemberId().equals(UserContext.getBuyer().getUid())) {
            throw new ServiceException(AftersaleErrorCode.E604.name(), "Order does not exist");
        }

        PaymentMethodDO paymentMethodDO = paymentMethodManager.getByPluginId(order.getPaymentPluginId());
        // Check whether the original route is supported
        if (paymentMethodDO != null && paymentMethodDO.getIsRetrace() == 1) {
            refundApplyVO.setOriginalWay("yes");
        } else {
            refundApplyVO.setOriginalWay("no");
        }
        refundApplyVO.setReturnPoint(0);
        // If skUID is not sent, the refund amount applied for the whole order is the amount paid in the order
        if (skuId == null) {
            // Refund application is not refundable freight
            refundApplyVO.setReturnMoney(CurrencyUtil.sub(order.getNeedPayMoney(), order.getShippingPrice()));
            refundApplyVO.setOrder(order);

            List<RefundSkuVO> refundSkuVOS = new ArrayList<>();
            for (OrderSkuVO orderSkuVo : order.getOrderSkuList()) {
                refundSkuVOS.add(
                        initRefundSkuVO(orderSkuVo, order));
            }
            refundApplyVO.setSkuList(refundSkuVOS);
        } else {
            List<RefundSkuVO> list = new ArrayList<>();
            for (OrderSkuVO orderSkuVO : order.getOrderSkuList()) {
                if (orderSkuVO.getSkuId().equals(skuId)) {
                    RefundSkuVO refundSkuVO = initRefundSkuVO(orderSkuVO, order);
                    list.add(refundSkuVO);
                    // The refund amount
                    double returnMoney = 0.00;
                    if (orderSkuVO.getNum() >= 2) {
                        int num = orderSkuVO.getNum() - 1;
                        returnMoney = CurrencyUtil.add(CurrencyUtil.mul(refundSkuVO.getRefundPrice(), num), refundSkuVO.getLastRefundPrice());
                    } else {
                        returnMoney = CurrencyUtil.mul(refundSkuVO.getRefundPrice(), orderSkuVO.getNum());
                    }
                    refundApplyVO.setReturnMoney(returnMoney);
                }
            }
            refundApplyVO.setOrder(order);
            refundApplyVO.setSkuList(list);
        }
        return refundApplyVO;
    }

    @Override
    public Integer getAfterSaleCount(Integer memberId) {
        StringBuffer sql = new StringBuffer("select count(*) from es_refund where refund_status != ?  ");
        List<Object> term = new ArrayList<>();
        term.add(RefundStatusEnum.COMPLETED.value());
        if (memberId != null) {
            sql.append("and member_id = ? ");
            term.add(memberId);
        }
        return this.daoSupport.queryForInt(sql.toString(), term.toArray());
    }

    @Override
    public List<RefundGoodsDO> getRefundGoods(String sn) {
        List<RefundGoodsDO> list = this.daoSupport.queryForList("select * from es_refund_goods where refund_sn=? ", RefundGoodsDO.class, sn);
        return list;
    }

    @Override
    public void queryRefundStatus() {
        List<RefundDO> refundDOS = this.queryNoReturnOrder();
        for (RefundDO refundDO : refundDOS) {
            String status = refundManager.queryRefundStatus(refundDO.getPayOrderNo(), refundDO.getSn());
            refundDO.setRefundStatus(status);
        }
        this.update(refundDOS);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void stockIn(String sn, String remark) {
        RefundDO refund = this.daoSupport.queryForObject("select * from es_refund where sn =?", RefundDO.class,
                sn);
        // Verify that the refund receipt exists and is the current sellers refund receipt
        if (refund == null) {
            throw new ServiceException(AftersaleErrorCode.E603.name(), "Refund slip does not exist");
        }
        this.checkAllowable(refund, RefundOperateEnum.STOCK_IN);

        // Send a message to the repository
        // TODO merchandise inventory restore gift restore
        RefundChangeMsg refundStatusChangeMessage = new RefundChangeMsg(refund,
                RefundStatusEnum.STOCK_IN);
        this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, AmqpExchange.REFUND_STATUS_CHANGE + "_QUEUE",
                refundStatusChangeMessage));


        String refundStatus = RefundStatusEnum.WAIT_FOR_MANUAL.value();


        String redundFailReason = "";
        // If the original way back is supported, the original way refund will be directly approved
        if (refund.getRefundWay().equals(RefundWayEnum.ORIGINAL.value()) && refund.getRefundPrice() > 0) {
            // Order Number refund amount
            Map map = refundManager.originRefund(refund.getPayOrderNo(), refund.getSn(), refund.getRefundPrice());

            if ("true".equals(map.get("result").toString())) {
                refundStatus = RefundStatusEnum.REFUNDING.value();
            } else {
                refundStatus = RefundStatusEnum.REFUNDFAIL.value();
                redundFailReason = map.get("fail_reason").toString();
            }

        }

        this.daoSupport.execute("update es_refund set refund_status=?,refund_fail_reason=?,warehouse_remark = ? where sn =?", refundStatus,
                redundFailReason, remark == null ? "" : remark, sn);

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void refund(String sn, String remark) {

        RefundDO refund = this.daoSupport.queryForObject("select * from es_refund where sn =?", RefundDO.class,
                sn);
        // Verify that the refund receipt exists and is the current sellers refund receipt
        if (refund == null) {
            throw new ServiceException(AftersaleErrorCode.E603.name(), "Refund slip does not exist");
        }

        this.checkAllowable(refund, RefundOperateEnum.ADMIN_REFUND);

        this.daoSupport.execute("update es_refund set refund_status=? ,finance_remark = ?,refund_time= ? where sn=?",
                RefundStatusEnum.COMPLETED.value(), remark == null ? "" : remark, DateUtil.getDateline(), sn);
        // Sends the message approved by the administrator
        RefundChangeMsg refundStatusChangeMessage = new RefundChangeMsg(refund,
                RefundStatusEnum.REFUNDING);
        this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, "systemRefund-apply-ROUTING_KEY",
                refundStatusChangeMessage));
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sysCancelOrder(BuyerCancelOrderVO buyerCancelOrderVO) {

        OrderDetailDTO orderDetail = orderQueryManager.getModel(buyerCancelOrderVO.getOrderSn());
        // Unpaid orders become cancelled
        if (PayStatusEnum.PAY_NO.value().equals(orderDetail.getPayStatus())) {
            CancelVO cancelVO = new CancelVO();
            cancelVO.setOrderSn(orderDetail.getSn());
            cancelVO.setOperator("The system automatically");
            cancelVO.setReason("The activity ended without a group");
            this.orderOperateManager.cancel(cancelVO, OrderPermission.admin);
        } else {
            // Application for refund Form
            RefundDO refund = baseCancle(buyerCancelOrderVO, orderDetail, true);
            // Refund receipt automatically approved
            AdminRefundApprovalVO refundApproval = new AdminRefundApprovalVO();
            refundApproval.setAgree(1);
            refundApproval.setRefundPoint(0);
            refundApproval.setSn(refund.getSn());
            refundApproval.setRemark("Automatic system audit");
            refundApproval.setRefundPrice(refund.getRefundPrice());
            this.approval(refundApproval, Permission.ADMIN);
        }
    }

    @Override
    public List<ExportRefundExcelVO> exportExcel(long startTime, long endTime) {
        String sql = "select * from es_refund where create_time >= ? and create_time <= ? ";
        return this.daoSupport.queryForList(sql, ExportRefundExcelVO.class, startTime, endTime);

    }

    /**
     * Get order item information
     *
     * @param orderSn Order no.
     * @param skuId   productSKUID
     * @return
     */
    private OrderItemsDO getOrderItems(String orderSn, Integer skuId) {
        String sql = "select * from es_order_items where order_sn = ? and product_id = ?";
        OrderItemsDO orderItemsDO = this.daoSupport.queryForObject(sql, OrderItemsDO.class, orderSn, skuId);
        return orderItemsDO;
    }

    /**
     * after-salesskuvo Initialize properties
     *
     * @param skuVO
     * @param order
     * @return
     */
    private RefundSkuVO initRefundSkuVO(OrderSkuVO skuVO, OrderDetailVO order) {
        RefundSkuVO refundSkuVO = new RefundSkuVO(skuVO);

        // Get order item data
        OrderItemsDO itemsDO = this.getOrderItems(order.getSn(), skuVO.getSkuId());

        // Determine whether the refundable amount of the order item is empty or 0
        if (itemsDO.getRefundPrice() == null || itemsDO.getRefundPrice().doubleValue() == 0) {
            refundSkuVO.setRefundPrice(0.00);
            refundSkuVO.setLastRefundPrice(0.00);
        } else {

            Double nowPrice = getNowPrice(order.getSn(), skuVO.getPurchasePrice());
            // The total amount actually paid for this item

            refundSkuVO.setRefundPrice(nowPrice);
            refundSkuVO.setLastRefundPrice(nowPrice);
       /*     int num = skuVO.getNum() - 1;
            refundSkuVO.setLastRefundPrice(CurrencyUtil.sub(skuVO.getSubtotal(), CurrencyUtil.mul(refundSkuVO.getRefundPrice(), num)));*/
        }
        return refundSkuVO;
    }

    /**
     * Sellers Refund Application
     *
     * @param buyerRefundApply
     * @return
     */
    private RefundDO buyerRefund(BuyerRefundApplyVO buyerRefundApply) {
        Buyer buyer = UserContext.getBuyer();

        // Obtain order information and judge order validity
        OrderDetailDTO order = orderQueryManager.getModel(buyerRefundApply.getOrderSn());

        // Check orders that do not exist or do not belong to current members
        if (order == null || !buyer.getUid().equals(order.getMemberId())) {
            throw new ServiceException(AftersaleErrorCode.E604.name(), "Order does not exist");
        }
        return innerRefund(buyerRefundApply, order);
    }

    /**
     * Specific operation refund
     *
     * @param buyerRefundApply
     * @param order
     * @return
     */
    private RefundDO innerRefund(BuyerRefundApplyVO buyerRefundApply, OrderDetailDTO order) {

        // Actual refund amount
        Double refundPrice = 0.0d;

        // Get the refundable amount of the order
        double allowRefundPrice = this.orderQueryManager.getOrderRefundPrice(order.getSn());

        // If the refundable amount of an order is less than or equal to 0, no refund is allowed
        if (allowRefundPrice <= 0) {
            throw new ServiceException(AftersaleErrorCode.E609.name(), AftersaleErrorCode.E609.describe());
        }

        // Get order SKU information
        List<OrderSkuDTO> orderSkuList = order.getOrderSkuList();
        OrderSkuDTO orderSkuDTO = null;

        // Generate a refund slip number based on the current time
        String refundSn = DateUtil.toString(DateUtil.getDateline(), "yyMMddHHmmss");

        // SkuId is empty, which proves that the order is cancelled after payment; Not empty proof is to confirm receipt of goods after the application for after-sales
        if (buyerRefundApply.getSkuId() == null) {

            // The actual refund amount is equal to the total refundable amount of the order
            refundPrice = allowRefundPrice;

            for (OrderSkuDTO orderSku : orderSkuList) {
                // Calculate the true price of the modified price by the merchant
                Double nowPrice = getNowPrice(order.getSn(), orderSku.getPurchasePrice());

                orderSku.setPurchasePrice(nowPrice);

                // Generate a list of returned goods
                this.refundGoods(orderSku, orderSku.getNum(), refundSn);
            }

            // Modify the after-sale status of the order
            orderOperateManager.updateOrderServiceStatus(order.getSn(), ServiceStatusEnum.APPLY.name());

        } else {

            for (OrderSkuDTO orderSku : orderSkuList) {

                // Determine whether the SKU to be applied for after sales exists in the order SKU information
                if (orderSku.getSkuId() == (buyerRefundApply.getSkuId()).intValue()) {
                    // Get order item data
                    OrderItemsDO orderItem = this.getOrderItems(order.getSn(), orderSku.getSkuId());

                    // If the refund amount is 0(in the case of applying for refund and return of a single commodity)
                    if (orderItem == null || orderItem.getRefundPrice() == null || orderItem.getRefundPrice() == 0) {
                        throw new ServiceException(AftersaleErrorCode.E609.name(), AftersaleErrorCode.E609.describe());
                    }

                    if (!orderSku.getServiceStatus().equals(ServiceStatusEnum.NOT_APPLY.name())) {
                        throw new ServiceException(AftersaleErrorCode.E601.name(), "Operation not allowed");
                    }

                    orderSkuDTO = orderSku;

                    Double nowPrice = getNowPrice(order.getSn(), orderSku.getPurchasePrice());
                    orderSku.setPurchasePrice(nowPrice);

                    // If the returned quantity is not passed, it defaults to the purchased quantity
                    if (buyerRefundApply.getReturnNum() == null) {
                        buyerRefundApply.setReturnNum(orderSku.getNum());
                    }

                    if (orderSku.getNum() < buyerRefundApply.getReturnNum()) {
                        throw new ServiceException(AftersaleErrorCode.E607.name(), "The quantity of goods applied for after sale should not be greater than the purchased quantity");
                    }

                    refundPrice = CurrencyUtil.mul(buyerRefundApply.getReturnNum(), nowPrice);

                    // Generate a list of returned goods
                    this.refundGoods(orderSkuDTO, buyerRefundApply.getReturnNum(), refundSn);
                    // Change the after-sales status of the order item to applied
                    List<OrderSkuDTO> orderSkuDTOList = new ArrayList<>();
                    orderSku.setServiceStatus(ServiceStatusEnum.APPLY.name());
                    orderSkuDTOList.add(orderSku);
                    orderOperateManager.updateOrderItemServiceStatus(order.getSn(), orderSkuDTOList);
                }
            }
            // Determine whether goods exist
            if (orderSkuDTO == null) {
                throw new ServiceException(AftersaleErrorCode.E602.name(), "Goods dont exist");
            }
        }


        // The refund slip is stored
        RefundDO refund = new RefundDO();
        refund.setSn(refundSn);
        refund.setOrderSn(buyerRefundApply.getOrderSn());
        refund.setCustomerRemark(buyerRefundApply.getCustomerRemark());
        refund.setRefundPrice(refundPrice);
        refund.setRefundReason(buyerRefundApply.getRefundReason());
        refund.setRefundType(RefundTypeEnum.AFTER_SALE.value());
        refund.setPaymentType(order.getPaymentType());
        refund.setPayOrderNo(order.getPayOrderNo());

        // Judge whether the current payment method supports the original way to return, if not, the refund method can not be empty
        PaymentMethodDO paymentMethodDO = paymentMethodManager.getByPluginId(order.getPaymentPluginId());
        String refundWay = RefundWayEnum.ORIGINAL.name();
        if (paymentMethodDO == null || paymentMethodDO.getIsRetrace() == 0) {
            refundWay = RefundWayEnum.OFFLINE.name();
            if (buyerRefundApply.getAccountType() == null) {
                throw new ServiceException(AftersaleErrorCode.E605.name(), "Refund method Mandatory");
            }

            // Bank transfer
            if (AccountTypeEnum.BANKTRANSFER.value().equals(buyerRefundApply.getAccountType())) {
                refund.setBankAccountName(buyerRefundApply.getBankAccountName());
                refund.setBankAccountNumber(buyerRefundApply.getBankAccountNumber());
                refund.setBankDepositName(buyerRefundApply.getBankDepositName());
                refund.setBankName(buyerRefundApply.getBankName());
            } else {
                // Alipay or wechat
                refund.setReturnAccount(buyerRefundApply.getReturnAccount());
            }
            refund.setAccountType(buyerRefundApply.getAccountType());
        } else {
            // The original road back WEIXINPAY ALIPAY
            String pluginId = paymentMethodDO.getPluginId();
            String accountType = null;
            if ("weixinPayPlugin".equals(pluginId)) {
                accountType = "WEIXINPAY";
            }
            if ("alipayDirectPlugin".equals(pluginId)) {
                accountType = "ALIPAY";
            }
            refund.setAccountType(accountType);
        }
        refund.setMemberId(order.getMemberId());
        refund.setMemberName(order.getMemberName());
        refund.setTradeSn(order.getTradeSn());
        refund.setPayOrderNo(order.getPayOrderNo());
        refund.setCreateTime(DateUtil.getDateline());
        refund.setRefundStatus(RefundStatusEnum.APPLY.value());
        refund.setRefuseType(buyerRefundApply.getRefuseType());
        refund.setRefundWay(refundWay);
        refund.setRefundGift(JsonUtil.objectToJson(order.getGiftList()));

        this.daoSupport.insert("es_refund", refund);
        refund.setId(this.daoSupport.getLastId("es_refund"));
        buyerRefundApply.setRefundSn(refundSn);


        // Send a message requesting a refund
        RefundChangeMsg refundStatusChangeMessage = new RefundChangeMsg(refund, RefundStatusEnum.APPLY);
        this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, AmqpExchange.REFUND_STATUS_CHANGE + "_QUEUE",
                refundStatusChangeMessage));

        return refund;
    }

    /**
     * Calculate the true price of the modified price by the merchant
     * @param orderSn
     * @param skuPurchasePrice
     * @return
     */
    private Double getNowPrice(String orderSn, Double skuPurchasePrice) {
        OrderDO orderDO = orderQueryManager.getDoByOrderSn(orderSn);
        // Proportion of the transaction price of goods in the total price of goods ordered (keep 6 decimal places)
        Double ratio = CurrencyUtil.div(skuPurchasePrice, orderDO.getGoodsPrice(), 6);
        // Price of goods after modification of order price by merchant = proportion * order price
        Double nowPrice = CurrencyUtil.mul(ratio, orderDO.getOrderPrice());
        return nowPrice;
    }

    /**
     * Verify the operation to see if the operation is allowed in this state
     *
     * @param refund        A refundVO
     * @param refundOperate Operations performed
     */
    private void checkAllowable(RefundDO refund, RefundOperateEnum refundOperate) {

        // Refund current process status
        String status = refund.getRefundStatus();
        RefundStatusEnum refundStatus = RefundStatusEnum.valueOf(status);

        // Return/refund
        String refuseType = refund.getRefuseType();
        RefuseTypeEnum type = RefuseTypeEnum.valueOf(refuseType);

        // Cash on delivery/online payment
        String paymentType = refund.getPaymentType();
        PaymentTypeEnum payment = PaymentTypeEnum.valueOf(paymentType);

        boolean allowble = RefundOperateChecker.checkAllowable(type, payment, refundStatus, refundOperate);
        if (!allowble) {
            throw new ServiceException(AftersaleErrorCode.E601.name(), "Operation not allowed");
        }
    }

    /**
     * Recording Operation Logs
     *
     * @param sn
     * @param operator
     * @param detail
     */
    private void log(String sn, String operator, String detail) {
        RefundLogDO refundLog = new RefundLogDO();
        refundLog.setOperator(operator);
        refundLog.setRefundSn(sn);
        refundLog.setLogtime(DateUtil.getDateline());
        refundLog.setLogdetail(detail);

        this.daoSupport.insert("es_refund_log", refundLog);

    }

    /**
     * Return goods to storage
     *
     * @param orderSkuDTO
     * @param num
     * @param refundSn
     */
    private void refundGoods(OrderSkuDTO orderSkuDTO, Integer num, String refundSn) {
        // Insert data into the returned goods list
        RefundGoodsDO refundGoods = new RefundGoodsDO(orderSkuDTO);
        refundGoods.setReturnNum(num);
        // The unit price of goods actually paid
        refundGoods.setPrice(orderSkuDTO.getPurchasePrice());
        refundGoods.setRefundSn(refundSn);
        this.daoSupport.insert("es_refund_goods", refundGoods);
    }

    /**
     * Verify refund amount
     *
     * @param refund
     * @param price
     */
    private void checkMoney(RefundDO refund, Double price) {

        // Obtain refund receipt information
        Double refundPrice;
        if (refund.getRefundType().equals(RefundTypeEnum.CANCEL_ORDER.value())) {
            refundPrice = orderQueryManager.getModel(refund.getOrderSn()).getNeedPayMoney();
        } else {
            refundPrice = refund.getRefundPrice();
        }
        if (price > refundPrice) {
            throw new ServiceException(AftersaleErrorCode.E600.name(), "The refund amount cannot be greater than the amount paid");
        }
    }

}
