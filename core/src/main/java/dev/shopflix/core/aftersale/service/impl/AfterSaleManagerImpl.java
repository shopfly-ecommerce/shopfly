/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.aftersale.service.impl;

import dev.shopflix.core.aftersale.AftersaleErrorCode;
import dev.shopflix.core.aftersale.model.dos.RefundDO;
import dev.shopflix.core.aftersale.model.dos.RefundGoodsDO;
import dev.shopflix.core.aftersale.model.dos.RefundLogDO;
import dev.shopflix.core.aftersale.model.dto.RefundDTO;
import dev.shopflix.core.aftersale.model.dto.RefundDetailDTO;
import dev.shopflix.core.aftersale.model.vo.*;
import dev.shopflix.core.aftersale.service.AfterSaleManager;
import dev.shopflix.core.aftersale.service.RefundOperateChecker;
import dev.shopflix.core.base.message.RefundChangeMsg;
import dev.shopflix.core.base.rabbitmq.AmqpExchange;
import dev.shopflix.core.goods.model.enums.Permission;
import dev.shopflix.core.payment.model.dos.PaymentMethodDO;
import dev.shopflix.core.payment.service.PaymentMethodManager;
import dev.shopflix.core.payment.service.RefundManager;
import dev.shopflix.core.trade.cart.model.dos.OrderPermission;
import dev.shopflix.core.trade.order.model.dos.OrderDO;
import dev.shopflix.core.trade.order.model.dos.OrderItemsDO;
import dev.shopflix.core.trade.order.model.enums.OrderStatusEnum;
import dev.shopflix.core.trade.order.model.enums.PayStatusEnum;
import dev.shopflix.core.trade.order.model.enums.PaymentTypeEnum;
import dev.shopflix.core.trade.order.model.enums.ServiceStatusEnum;
import dev.shopflix.core.trade.order.model.vo.CancelVO;
import dev.shopflix.core.trade.order.model.vo.OrderDetailVO;
import dev.shopflix.core.trade.order.model.vo.OrderOperateAllowable;
import dev.shopflix.core.trade.order.model.vo.OrderSkuVO;
import dev.shopflix.core.trade.order.service.OrderOperateManager;
import dev.shopflix.core.trade.order.service.OrderQueryManager;
import dev.shopflix.core.trade.sdk.model.OrderDetailDTO;
import dev.shopflix.core.trade.sdk.model.OrderSkuDTO;
import dev.shopflix.core.aftersale.model.enums.*;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;

import dev.shopflix.framework.security.model.Buyer;
import dev.shopflix.framework.util.*;
import org.apache.commons.collections.map.HashedMap;
import dev.shopflix.framework.rabbitmq.MessageSender;
import dev.shopflix.framework.rabbitmq.MqMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * @Description 售后管理业务类
 * @ClassName AfterSaleManagerImpl
 * @since v7.0 上午11:32 2018/5/8
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
     * 日志记录
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void applyRefund(BuyerRefundApplyVO refundApply) {
        refundApply.setRefuseType(RefuseTypeEnum.RETURN_MONEY.value());
        RefundDO refund = this.buyerRefund(refundApply);
        this.log(refund.getSn(), refund.getMemberName(), "申请退款");
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void applyGoodsReturn(BuyerRefundApplyVO goodsReturnsApply) {
        goodsReturnsApply.setRefuseType(RefuseTypeEnum.RETURN_GOODS.value());
        RefundDO refund = this.buyerRefund(goodsReturnsApply);
        this.log(refund.getSn(), refund.getMemberName(), "申请退货");
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void cancelOrder(BuyerCancelOrderVO buyerCancelOrderVO) {
        //获取登录会员
        Buyer buyer = UserContext.getBuyer();
        //获取订单信息对订单进行属主校验
        OrderDetailDTO orderDetail = orderQueryManager.getModel(buyerCancelOrderVO.getOrderSn());
        if (orderDetail == null || !buyer.getUid().equals(orderDetail.getMemberId())) {
            throw new ServiceException(AftersaleErrorCode.E604.name(), "订单不存在");
        }
        baseCancle(buyerCancelOrderVO, orderDetail, false);
    }


    /**
     * 取消订单通用
     *
     * @param buyerCancelOrderVO
     * @param orderDetail
     */
    private RefundDO baseCancle(BuyerCancelOrderVO buyerCancelOrderVO, OrderDetailDTO orderDetail, boolean isAdmin) {

        //已付款订单才可执行退款操作
        if (!orderDetail.getOrderStatus().equals(OrderStatusEnum.PAID_OFF.value()) && !orderDetail.getPayStatus().equals(PayStatusEnum.PAY_YES.value())) {
            throw new ServiceException(AftersaleErrorCode.E601.name(), "操作不允许");
        }
        //订单操作校验
        OrderOperateAllowable orderOperateAllowableVO = orderDetail.getOrderOperateAllowableVO();
        if (!orderOperateAllowableVO.getAllowServiceCancel()) {
            throw new ServiceException(AftersaleErrorCode.E601.name(), "操作不允许");
        }

        String refundSn = DateUtil.toString(DateUtil.getDateline(), "yyMMddhhmmss");
        //拼装退款单
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

        //判断当前支付方式是否支持原路退回,如果不支持则退款方式不能为空
        PaymentMethodDO paymentMethodDO = paymentMethodManager.getByPluginId(orderDetail.getPaymentPluginId());
        String refundWay = RefundWayEnum.ORIGINAL.name();
        //非原路退回
        if (paymentMethodDO == null || paymentMethodDO.getIsRetrace() == 0) {
            refundWay = RefundWayEnum.OFFLINE.name();
            if (isAdmin) {
                //这里暂时是拼团退款并且是管理员点击确认收款的订单
                refundDO.setAccountType("");
                refundDO.setReturnAccount("");
                refundDO.setBankAccountName("");
                refundDO.setBankAccountNumber("");
                refundDO.setBankDepositName("");
                refundDO.setBankName("");
            } else {
                if (buyerCancelOrderVO.getAccountType() == null) {
                    throw new ServiceException(AftersaleErrorCode.E605.name(), "退款方式必填");
                }
                //银行转账
                if (AccountTypeEnum.BANKTRANSFER.name().equals(buyerCancelOrderVO.getAccountType())) {
                    refundDO.setBankAccountName(buyerCancelOrderVO.getBankAccountName());
                    refundDO.setBankAccountNumber(buyerCancelOrderVO.getBankAccountNumber());
                    refundDO.setBankDepositName(buyerCancelOrderVO.getBankDepositName());
                    refundDO.setBankName(buyerCancelOrderVO.getBankName());
                } else {
                    //支付宝或者微信
                    refundDO.setReturnAccount(buyerCancelOrderVO.getReturnAccount());
                }
                refundDO.setAccountType(buyerCancelOrderVO.getAccountType());
            }

        } else {
            //原路退回WEIXINPAY  ALIPAY
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

        //生成退货商品
        List<OrderSkuDTO> orderSkuList = orderDetail.getOrderSkuList();
        for (OrderSkuDTO orderSkuDTO : orderSkuList) {
            this.refundGoods(orderSkuDTO, orderSkuDTO.getNum(), refundSn);
        }

        //更新订单状态及售后状态
        orderOperateManager.updateOrderServiceStatus(orderDetail.getSn(), ServiceStatusEnum.APPLY.name());

        // 发送申请退款的消息
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
        String operater = "系统自动审核";

        //退款金额校验
        this.checkMoney(refund, refundApproval.getRefundPrice());

        String refundStatus = RefundStatusEnum.REFUSE.value();
        String redundFailReason = "";


        // 判断是否同意退款
        if (refundApproval.getAgree().equals(1)) {
            if (refund.getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.value())) {
                refundStatus = RefundStatusEnum.WAIT_FOR_MANUAL.value();
            }
            if (refund.getRefuseType().equals(RefuseTypeEnum.RETURN_GOODS.value())) {
                refundStatus = RefundStatusEnum.PASS.value();
            }
            refund.setRefundStatus(refundStatus);
            refund.setRefundPrice(refundApproval.getRefundPrice());
            // 发送审核通过的消息
            RefundChangeMsg refundStatusChangeMessage = new RefundChangeMsg(refund, RefundStatusEnum.PASS);
            this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, AmqpExchange.REFUND_STATUS_CHANGE + "_QUEUE",
                    refundStatusChangeMessage));

            // 如果为申请退款且退款方式支持原路退回则审核通过后直接原路退款
            if (refund.getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.value()) && RefundWayEnum.ORIGINAL.name().equals(refund.getRefundWay()) && refundApproval.getRefundPrice() > 0) {
                // 订单号 退款金额
                Map map = refundManager.originRefund(refund.getPayOrderNo(), refund.getSn(), refundApproval.getRefundPrice());
                if ("true".equals(StringUtil.toString(map.get("result")))) {
                    refundStatus = RefundStatusEnum.REFUNDING.value();
                    //发送原路退回成功消息
                    refundStatusChangeMessage = new RefundChangeMsg(refund, RefundStatusEnum.REFUNDING);
                    this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, AmqpExchange.REFUND_STATUS_CHANGE + "_QUEUE",
                            refundStatusChangeMessage));
                } else {
                    refundStatus = RefundStatusEnum.REFUNDFAIL.value();
                    redundFailReason = StringUtil.toString(map.get("fail_reason"));
                }
            }
        } else {
            // 发送审核通过的消息
            RefundChangeMsg refundStatusChangeMessage = new RefundChangeMsg(refund, RefundStatusEnum.REFUSE);
            this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, AmqpExchange.REFUND_STATUS_CHANGE + "_QUEUE",
                    refundStatusChangeMessage));
        }

        this.daoSupport.execute(
                "update es_refund set refund_status=?,seller_remark=? ,refund_price=? ,refund_point=?,refund_fail_reason=? where sn =?",
                refundStatus, refundApproval.getRemark(), refundApproval.getRefundPrice(),
                refundApproval.getRefundPoint(), redundFailReason, refundApproval.getSn());


        // 记录日志
        this.log(refundApproval.getSn(), operater, "审核退货（款），结果为：" + (refundApproval.getAgree() == 1 ? "同意" : "拒绝"));

        return refundApproval;
    }

    @Override
    public FinanceRefundApprovalVO approval(FinanceRefundApprovalVO refundApproval) {
        RefundDO refund = this.daoSupport.queryForObject("select * from es_refund where sn =?", RefundDO.class,
                refundApproval.getSn());
        if (refund == null) {
            throw new ServiceException(AftersaleErrorCode.E603.name(), "退款单不存在");
        }

        //权限校验
        this.checkAllowable(refund, RefundOperateEnum.ADMIN_REFUND);
        //金额校验
        this.checkMoney(refund, refundApproval.getRefundPrice());

        this.daoSupport.execute("update es_refund set refund_price=? ,refund_status=?,finance_remark = ? ,refund_time = ? where sn=?",
                refundApproval.getRefundPrice(), RefundStatusEnum.COMPLETED.value(), refundApproval.getRemark() == null ? "" : refundApproval.getRemark(),
                DateUtil.getDateline(), refundApproval.getSn());
        // 发送管理员审核的消息
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

        // 按时间查询
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

        //退款方式
        if (StringUtil.notEmpty(param.getRefundWay())) {
            sqlSplit.add(" refund_way = ? ");
            termList.add(param.getRefundWay());
        }

        //对sql条件语句进行拼接
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
            throw new ServiceException(AftersaleErrorCode.E603.name(), "退款单不存在");
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
            throw new ServiceException(AftersaleErrorCode.E604.name(), "订单不存在");
        }

        PaymentMethodDO paymentMethodDO = paymentMethodManager.getByPluginId(order.getPaymentPluginId());
        //判断是否支持原路退回
        if (paymentMethodDO != null && paymentMethodDO.getIsRetrace() == 1) {
            refundApplyVO.setOriginalWay("yes");
        } else {
            refundApplyVO.setOriginalWay("no");
        }
        refundApplyVO.setReturnPoint(0);
        //如果不传skuid则为整单申请 退款金额为订单支付金额
        if (skuId == null) {
            //退款申请不退运费
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
                    //退款金额
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
        // 验证退款单是否存在及是否当前卖家的退款单
        if (refund == null) {
            throw new ServiceException(AftersaleErrorCode.E603.name(), "退款单不存在");
        }
        this.checkAllowable(refund, RefundOperateEnum.STOCK_IN);

        // 发送入库的消息
        //TODO 商品库存还原 赠品还原
        RefundChangeMsg refundStatusChangeMessage = new RefundChangeMsg(refund,
                RefundStatusEnum.STOCK_IN);
        this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, AmqpExchange.REFUND_STATUS_CHANGE + "_QUEUE",
                refundStatusChangeMessage));


        String refundStatus = RefundStatusEnum.WAIT_FOR_MANUAL.value();


        String redundFailReason = "";
        // 如果支持原路退回则审核通过后直接原路退款
        if (refund.getRefundWay().equals(RefundWayEnum.ORIGINAL.value()) && refund.getRefundPrice() > 0) {
            // 订单号 退款金额
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
        // 验证退款单是否存在及是否当前卖家的退款单
        if (refund == null) {
            throw new ServiceException(AftersaleErrorCode.E603.name(), "退款单不存在");
        }

        this.checkAllowable(refund, RefundOperateEnum.ADMIN_REFUND);

        this.daoSupport.execute("update es_refund set refund_status=? ,finance_remark = ?,refund_time= ? where sn=?",
                RefundStatusEnum.COMPLETED.value(), remark == null ? "" : remark, DateUtil.getDateline(), sn);
        // 发送管理员审核的消息
        RefundChangeMsg refundStatusChangeMessage = new RefundChangeMsg(refund,
                RefundStatusEnum.REFUNDING);
        this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, "systemRefund-apply-ROUTING_KEY",
                refundStatusChangeMessage));
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sysCancelOrder(BuyerCancelOrderVO buyerCancelOrderVO) {

        OrderDetailDTO orderDetail = orderQueryManager.getModel(buyerCancelOrderVO.getOrderSn());
        //未付款订单直接变成已取消
        if (PayStatusEnum.PAY_NO.value().equals(orderDetail.getPayStatus())) {
            CancelVO cancelVO = new CancelVO();
            cancelVO.setOrderSn(orderDetail.getSn());
            cancelVO.setOperator("系统自动");
            cancelVO.setReason("活动结束未成团");
            this.orderOperateManager.cancel(cancelVO, OrderPermission.admin);
        } else {
            //申请退款单
            RefundDO refund = baseCancle(buyerCancelOrderVO, orderDetail, true);
            //退款单自动审核通过
            AdminRefundApprovalVO refundApproval = new AdminRefundApprovalVO();
            refundApproval.setAgree(1);
            refundApproval.setRefundPoint(0);
            refundApproval.setSn(refund.getSn());
            refundApproval.setRemark("系统自动审核");
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
     * 获取订单项信息
     *
     * @param orderSn 订单编号
     * @param skuId   商品SKUID
     * @return
     */
    private OrderItemsDO getOrderItems(String orderSn, Integer skuId) {
        String sql = "select * from es_order_items where order_sn = ? and product_id = ?";
        OrderItemsDO orderItemsDO = this.daoSupport.queryForObject(sql, OrderItemsDO.class, orderSn, skuId);
        return orderItemsDO;
    }

    /**
     * 售后skuvo 初始化属性
     *
     * @param skuVO
     * @param order
     * @return
     */
    private RefundSkuVO initRefundSkuVO(OrderSkuVO skuVO, OrderDetailVO order) {
        RefundSkuVO refundSkuVO = new RefundSkuVO(skuVO);

        //获取订单项数据
        OrderItemsDO itemsDO = this.getOrderItems(order.getSn(), skuVO.getSkuId());

        //判断订单项的可退款金额是否为空或者为0
        if (itemsDO.getRefundPrice() == null || itemsDO.getRefundPrice().doubleValue() == 0) {
            refundSkuVO.setRefundPrice(0.00);
            refundSkuVO.setLastRefundPrice(0.00);
        } else {

            Double nowPrice = getNowPrice(order.getSn(), skuVO.getPurchasePrice());
            //此商品实际支付总额

            refundSkuVO.setRefundPrice(nowPrice);
            refundSkuVO.setLastRefundPrice(nowPrice);
       /*     int num = skuVO.getNum() - 1;
            refundSkuVO.setLastRefundPrice(CurrencyUtil.sub(skuVO.getSubtotal(), CurrencyUtil.mul(refundSkuVO.getRefundPrice(), num)));*/
        }
        return refundSkuVO;
    }

    /**
     * 卖家退款申请
     *
     * @param buyerRefundApply
     * @return
     */
    private RefundDO buyerRefund(BuyerRefundApplyVO buyerRefundApply) {
        Buyer buyer = UserContext.getBuyer();

        //获取订单信息，判断订单有效性
        OrderDetailDTO order = orderQueryManager.getModel(buyerRefundApply.getOrderSn());

        //不存在的订单或者不属于当前会员的订单进行校验
        if (order == null || !buyer.getUid().equals(order.getMemberId())) {
            throw new ServiceException(AftersaleErrorCode.E604.name(), "订单不存在");
        }
        return innerRefund(buyerRefundApply, order);
    }

    /**
     * 具体操作退款
     *
     * @param buyerRefundApply
     * @param order
     * @return
     */
    private RefundDO innerRefund(BuyerRefundApplyVO buyerRefundApply, OrderDetailDTO order) {

        //实际退款金额
        Double refundPrice = 0.0d;

        //获取订单可退款金额
        double allowRefundPrice = this.orderQueryManager.getOrderRefundPrice(order.getSn());

        //如果订单的可退款金额小于等于0，那么不允许退款
        if (allowRefundPrice <= 0) {
            throw new ServiceException(AftersaleErrorCode.E609.name(), AftersaleErrorCode.E609.describe());
        }

        //获取订单SKU信息
        List<OrderSkuDTO> orderSkuList = order.getOrderSkuList();
        OrderSkuDTO orderSkuDTO = null;

        //根据当前时间生成退款单号
        String refundSn = DateUtil.toString(DateUtil.getDateline(), "yyMMddHHmmss");

        //skuId为空，证明是付款后取消订单；不为空证明是确认收货后申请售后
        if (buyerRefundApply.getSkuId() == null) {

            //实际退款金额等于订单的可退款总额
            refundPrice = allowRefundPrice;

            for (OrderSkuDTO orderSku : orderSkuList) {
                //计算商家修改价格后的真实价格
                Double nowPrice = getNowPrice(order.getSn(), orderSku.getPurchasePrice());

                orderSku.setPurchasePrice(nowPrice);

                //生成退货商品表
                this.refundGoods(orderSku, orderSku.getNum(), refundSn);
            }

            //修改订单的售后状态
            orderOperateManager.updateOrderServiceStatus(order.getSn(), ServiceStatusEnum.APPLY.name());

        } else {

            for (OrderSkuDTO orderSku : orderSkuList) {

                //判断要申请售后的SKU是不是存在于订单SKU信息中
                if (orderSku.getSkuId() == (buyerRefundApply.getSkuId()).intValue()) {
                    //获取订单项数据
                    OrderItemsDO orderItem = this.getOrderItems(order.getSn(), orderSku.getSkuId());

                    //如果退款金额为0(针对单个商品申请退款退货的情况)
                    if (orderItem == null || orderItem.getRefundPrice() == null || orderItem.getRefundPrice() == 0) {
                        throw new ServiceException(AftersaleErrorCode.E609.name(), AftersaleErrorCode.E609.describe());
                    }

                    if (!orderSku.getServiceStatus().equals(ServiceStatusEnum.NOT_APPLY.name())) {
                        throw new ServiceException(AftersaleErrorCode.E601.name(), "操作不允许");
                    }

                    orderSkuDTO = orderSku;

                    Double nowPrice = getNowPrice(order.getSn(), orderSku.getPurchasePrice());
                    orderSku.setPurchasePrice(nowPrice);

                    //如果没有传递退货数量，则默认为购买数量
                    if (buyerRefundApply.getReturnNum() == null) {
                        buyerRefundApply.setReturnNum(orderSku.getNum());
                    }

                    if (orderSku.getNum() < buyerRefundApply.getReturnNum()) {
                        throw new ServiceException(AftersaleErrorCode.E607.name(), "申请售后货品数量不能大于购买数量");
                    }

                    refundPrice = CurrencyUtil.mul(buyerRefundApply.getReturnNum(), nowPrice);

                    //生成退货商品表
                    this.refundGoods(orderSkuDTO, buyerRefundApply.getReturnNum(), refundSn);
                    //修改订单项的售后状态为已申请
                    List<OrderSkuDTO> orderSkuDTOList = new ArrayList<>();
                    orderSku.setServiceStatus(ServiceStatusEnum.APPLY.name());
                    orderSkuDTOList.add(orderSku);
                    orderOperateManager.updateOrderItemServiceStatus(order.getSn(), orderSkuDTOList);
                }
            }
            //判断商品是否存在
            if (orderSkuDTO == null) {
                throw new ServiceException(AftersaleErrorCode.E602.name(), "商品不存在");
            }
        }


        //退款单入库
        RefundDO refund = new RefundDO();
        refund.setSn(refundSn);
        refund.setOrderSn(buyerRefundApply.getOrderSn());
        refund.setCustomerRemark(buyerRefundApply.getCustomerRemark());
        refund.setRefundPrice(refundPrice);
        refund.setRefundReason(buyerRefundApply.getRefundReason());
        refund.setRefundType(RefundTypeEnum.AFTER_SALE.value());
        refund.setPaymentType(order.getPaymentType());
        refund.setPayOrderNo(order.getPayOrderNo());

        //判断当前支付方式是否支持原路退回,如果不支持则退款方式不能为空
        PaymentMethodDO paymentMethodDO = paymentMethodManager.getByPluginId(order.getPaymentPluginId());
        String refundWay = RefundWayEnum.ORIGINAL.name();
        if (paymentMethodDO == null || paymentMethodDO.getIsRetrace() == 0) {
            refundWay = RefundWayEnum.OFFLINE.name();
            if (buyerRefundApply.getAccountType() == null) {
                throw new ServiceException(AftersaleErrorCode.E605.name(), "退款方式必填");
            }

            //银行转账
            if (AccountTypeEnum.BANKTRANSFER.value().equals(buyerRefundApply.getAccountType())) {
                refund.setBankAccountName(buyerRefundApply.getBankAccountName());
                refund.setBankAccountNumber(buyerRefundApply.getBankAccountNumber());
                refund.setBankDepositName(buyerRefundApply.getBankDepositName());
                refund.setBankName(buyerRefundApply.getBankName());
            } else {
                //支付宝或者微信
                refund.setReturnAccount(buyerRefundApply.getReturnAccount());
            }
            refund.setAccountType(buyerRefundApply.getAccountType());
        } else {
            //原路退回WEIXINPAY  ALIPAY
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


        // 发送申请退款的消息
        RefundChangeMsg refundStatusChangeMessage = new RefundChangeMsg(refund, RefundStatusEnum.APPLY);
        this.messageSender.send(new MqMessage(AmqpExchange.REFUND_STATUS_CHANGE, AmqpExchange.REFUND_STATUS_CHANGE + "_QUEUE",
                refundStatusChangeMessage));

        return refund;
    }

    /**
     * 计算商家修改价格后的真实价格
     * @param orderSn
     * @param skuPurchasePrice
     * @return
     */
    private Double getNowPrice(String orderSn, Double skuPurchasePrice) {
        OrderDO orderDO = orderQueryManager.getDoByOrderSn(orderSn);
        //商品成交价在订单商品总价中的占比（保留6位小数）
        Double ratio = CurrencyUtil.div(skuPurchasePrice, orderDO.getGoodsPrice(), 6);
        //商家修改订单价格后的商品价格 = 占比 * 订单价格
        Double nowPrice = CurrencyUtil.mul(ratio, orderDO.getOrderPrice());
        return nowPrice;
    }

    /**
     * 进行操作校验 看此状态下是否允许此操作
     *
     * @param refund        退款VO
     * @param refundOperate 进行的操作
     */
    private void checkAllowable(RefundDO refund, RefundOperateEnum refundOperate) {

        // 退款当前流程状态
        String status = refund.getRefundStatus();
        RefundStatusEnum refundStatus = RefundStatusEnum.valueOf(status);

        // 退货/退款
        String refuseType = refund.getRefuseType();
        RefuseTypeEnum type = RefuseTypeEnum.valueOf(refuseType);

        // 货到付款/在线支付
        String paymentType = refund.getPaymentType();
        PaymentTypeEnum payment = PaymentTypeEnum.valueOf(paymentType);

        boolean allowble = RefundOperateChecker.checkAllowable(type, payment, refundStatus, refundOperate);
        if (!allowble) {
            throw new ServiceException(AftersaleErrorCode.E601.name(), "操作不允许");
        }
    }

    /**
     * 记录操作日志
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
     * 退货商品入库
     *
     * @param orderSkuDTO
     * @param num
     * @param refundSn
     */
    private void refundGoods(OrderSkuDTO orderSkuDTO, Integer num, String refundSn) {
        // 向退货商品表插入数据
        RefundGoodsDO refundGoods = new RefundGoodsDO(orderSkuDTO);
        refundGoods.setReturnNum(num);
        // 实际支付的商品单价
        refundGoods.setPrice(orderSkuDTO.getPurchasePrice());
        refundGoods.setRefundSn(refundSn);
        this.daoSupport.insert("es_refund_goods", refundGoods);
    }

    /**
     * 校验退款金额
     *
     * @param refund
     * @param price
     */
    private void checkMoney(RefundDO refund, Double price) {

        //获取退款单信息
        Double refundPrice;
        if (refund.getRefundType().equals(RefundTypeEnum.CANCEL_ORDER.value())) {
            refundPrice = orderQueryManager.getModel(refund.getOrderSn()).getNeedPayMoney();
        } else {
            refundPrice = refund.getRefundPrice();
        }
        if (price > refundPrice) {
            throw new ServiceException(AftersaleErrorCode.E600.name(), "退款金额不能大于支付金额");
        }
    }

}
