/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.service.impl;

import dev.shopflix.core.base.message.OrderStatusChangeMsg;
import dev.shopflix.core.base.rabbitmq.AmqpExchange;
import dev.shopflix.core.trade.TradeErrorCode;
import dev.shopflix.core.trade.cart.model.dos.OrderPermission;
import dev.shopflix.core.trade.order.model.dos.OrderDO;
import dev.shopflix.core.trade.order.model.dos.OrderLogDO;
import dev.shopflix.core.trade.order.model.dos.TradeDO;

import dev.shopflix.core.trade.order.model.enums.*;
import dev.shopflix.core.trade.order.model.vo.*;
import dev.shopflix.core.trade.order.service.*;
import dev.shopflix.core.trade.order.support.OrderOperateChecker;
import dev.shopflix.core.trade.order.support.OrderOperateFlow;
import dev.shopflix.core.trade.order.support.OrderStep;
import dev.shopflix.core.trade.sdk.model.OrderSkuDTO;
import dev.shopflix.framework.context.AdminUserContext;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.exception.NoPermissionException;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.security.model.Buyer;
import dev.shopflix.framework.util.CurrencyUtil;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.rabbitmq.MessageSender;
import dev.shopflix.framework.rabbitmq.MqMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单流程操作
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class OrderOperateManagerImpl implements OrderOperateManager {


    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private OrderQueryManager orderQueryManager;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private OrderLogManager orderLogManager;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private TradeQueryManager tradeQueryManager;

    @Autowired
    private TradePriceManager tradePriceManager;

    @Autowired
    private OrderMetaManager orderMetaManager;


    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void confirm(ConfirmVO confirmVO, OrderPermission permission) {
        executeOperate(confirmVO.getOrderSn(), permission, OrderOperateEnum.CONFIRM, confirmVO);
    }


    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public OrderDO payOrder(String orderSn, Double payPrice, String returnTradeNo, OrderPermission permission) {

        PayParam payParam = new PayParam();
        payParam.setPayPrice(payPrice);
        payParam.setReturnTradeNo(returnTradeNo);

        executeOperate(orderSn, permission, OrderOperateEnum.PAY, payParam);
        return null;
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void ship(DeliveryVO deliveryVO, OrderPermission permission) {

        String orderSn = deliveryVO.getOrderSn();
        executeOperate(orderSn, permission, OrderOperateEnum.SHIP, deliveryVO);
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void rog(RogVO rogVO, OrderPermission permission) {

        String orderSn = rogVO.getOrderSn();
        executeOperate(orderSn, permission, OrderOperateEnum.ROG, rogVO);
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void cancel(CancelVO cancelVO, OrderPermission permission) {

        String orderSn = cancelVO.getOrderSn();
        executeOperate(orderSn, permission, OrderOperateEnum.CANCEL, cancelVO);
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void complete(CompleteVO completeVO, OrderPermission permission) {
        String orderSn = completeVO.getOrderSn();
        executeOperate(orderSn, permission, OrderOperateEnum.COMPLETE, completeVO);
    }


    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateServiceStatus(String orderSn, ServiceStatusEnum serviceStatus) {
        String sql = "update es_order set service_status = ?  where sn = ? ";
        this.daoSupport.execute(sql, serviceStatus.value(), orderSn);
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public OrderConsigneeVO updateOrderConsignee(OrderConsigneeVO orderConsignee) {

        OrderDO orderDO = this.orderQueryManager.getDoByOrderSn(orderConsignee.getOrderSn());

        orderDO.setShipProvince(orderConsignee.getRegion().getProvince());
        orderDO.setShipProvinceId(orderConsignee.getRegion().getProvinceId());
        orderDO.setShipCity(orderConsignee.getRegion().getCity());
        orderDO.setShipCityId(orderConsignee.getRegion().getCityId());
        orderDO.setShipCounty(orderConsignee.getRegion().getCounty());
        orderDO.setShipCountyId(orderConsignee.getRegion().getCountyId());
        orderDO.setShipTown(orderConsignee.getRegion().getTown());
        orderDO.setShipTownId(orderConsignee.getRegion().getTownId());

        orderDO.setShipAddr(orderConsignee.getShipAddr());
        orderDO.setShipMobile(orderConsignee.getShipMobile());
        orderDO.setShipTel(orderConsignee.getShipTel());
        orderDO.setReceiveTime(orderConsignee.getReceiveTime());
        orderDO.setShipName(orderConsignee.getShipName());
        orderDO.setRemark(orderConsignee.getRemark());

        this.orderManager.update(orderDO);
        return orderConsignee;
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateOrderPrice(String orderSn, Double orderPrice) {

        //修改的订单价格不能小于等于0
        if (orderPrice <= 0) {
            throw new ServiceException(TradeErrorCode.E471.code(), "订单金额必须大于0");
        }

        //获取订单详情信息
        OrderDetailVO orderDetailVO = this.orderQueryManager.getModel(orderSn, null);

        //订单权限判断
        this.checkPermission(OrderPermission.seller, orderDetailVO);

        //获取交易单信息
        TradeDO tradeDO = tradeQueryManager.getModel(orderDetailVO.getTradeSn());

        //获取原订单金额
        Double oldPrice = orderDetailVO.getOrderPrice();
        //计算出原订单金额和修改后订单金额的差额
        Double differencePrice = CurrencyUtil.sub(oldPrice, orderPrice);
        //交易总价=原交易价格-差额
        Double tradePrice = CurrencyUtil.sub(tradeDO.getTotalPrice(), differencePrice);
        //优惠总额=原优惠金额-差额
        Double discountPrice = CurrencyUtil.add(tradeDO.getDiscountPrice(), differencePrice);

        //如果优惠总额小于0，那么将优惠总额设置为0
        if (discountPrice < 0) {
            discountPrice = 0.0;
        }

        //修改交易价格
        this.tradePriceManager.updatePrice(tradeDO.getTradeSn(), tradePrice, discountPrice);

        //订单修改价格后的优惠金额 = 订单原优惠金额 - 改价差额
        Double orderDiscoutPrice = CurrencyUtil.add(orderDetailVO.getDiscountPrice(), differencePrice);
        //如果订单修改价格后的优惠金额小于0，那么将订单修改价格后的优惠金额设置为0，防止当修改后的订单总价高于原价时返现金额和商品总价为负数
        if (orderDiscoutPrice < 0) {
            orderDiscoutPrice = 0.0;
        }

        //修改订单元数据信息，此处是为了退款时的金额计算正确所做的操作
        Double fullMinus = Double.valueOf(this.orderMetaManager.getMetaValue(orderSn, OrderMetaKeyEnum.FULL_MINUS));
        Double cashBack = Double.valueOf(this.orderMetaManager.getMetaValue(orderSn, OrderMetaKeyEnum.CASH_BACK));


        //修改订单价格
        String sql = "update es_order set order_price = ? ,need_pay_money = ? ,discount_price = ?  where sn = ?";
        this.daoSupport.execute(sql, orderPrice, orderPrice, orderDiscoutPrice, orderSn);

        this.orderMetaManager.updateMetaValue(orderSn, OrderMetaKeyEnum.FULL_MINUS, CurrencyUtil.add(fullMinus, differencePrice).toString());
        this.orderMetaManager.updateMetaValue(orderSn, OrderMetaKeyEnum.CASH_BACK, CurrencyUtil.add(cashBack, differencePrice).toString());

        //记录操作日志
        OrderLogDO orderLogDO = new OrderLogDO();
        orderLogDO.setMessage("商家修改订单价格");
        orderLogDO.setOrderSn(orderSn);
        orderLogDO.setOpName("管理员");
        orderLogDO.setOpTime(DateUtil.getDateline());
        this.orderLogManager.add(orderLogDO);
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateCommentStatus(String orderSn, CommentStatusEnum commentStatus) {
        String sql = "update es_order set comment_status = ? where sn = ? ";
        this.daoSupport.execute(sql, commentStatus.name(), orderSn);
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateItemJson(String itemsJson, String orderSn) {
        String sql = "update es_order set items_json = ? where  sn = ? ";
        this.daoSupport.execute(sql, itemsJson, orderSn);
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateOrderStatus(String orderSn, OrderStatusEnum orderStatus) {

        StringBuffer sqlBuffer = new StringBuffer("update es_order set order_status = ? ");

        if (OrderStatusEnum.PAID_OFF.equals(orderStatus)) {
            sqlBuffer.append(",pay_status = '" + PayStatusEnum.PAY_YES.value() + "'");
        }

        sqlBuffer.append(" where sn = ? ");

        this.daoSupport.execute(sqlBuffer.toString(), orderStatus.value(), orderSn);
    }


    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void executeOperate(String orderSn, OrderPermission permission, OrderOperateEnum orderOperate, Object paramVO) {
        // 获取此订单
        OrderDetailVO orderDetailVO = orderQueryManager.getModel(orderSn, null);

        //1、验证操作者的权限
        this.checkPermission(permission, orderDetailVO);

        //2、验证此订单可进行的操作
        this.checkAllowable(permission, orderDetailVO, orderOperate);

        long nowTime = DateUtil.getDateline();

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderDetailVO, orderDO);

        //要变更的订单状态
        OrderStatusEnum newStatus = null;

        //日志信息
        String logMessage = "操作信息";

        String operator = "系统默认";

        switch (orderOperate) {
            case CONFIRM:

                ConfirmVO confirmVO = (ConfirmVO) paramVO;
                logMessage = "确认订单";
                newStatus = OrderStatusEnum.CONFIRM;
                this.daoSupport.execute("update es_order set order_status=?  where sn=? ", OrderStatusEnum.CONFIRM.value(),
                        confirmVO.getOrderSn());
                orderDO.setOrderStatus(OrderStatusEnum.CONFIRM.name());
                break;

            case PAY:

                logMessage = "支付订单";
                newStatus = OrderStatusEnum.PAID_OFF;
                PayParam payParam = (PayParam) paramVO;
                Double payPrice = payParam.getPayPrice();
                String returnTradeNo = payParam.getReturnTradeNo();

                switch (permission) {
                    case buyer:
                        operator = orderDetailVO.getMemberName();
                        break;

                    case client:
                        break;

                    case seller:
                        operator = orderDetailVO.getSellerName();
                        break;

                    case admin:
                        operator = AdminUserContext.getAdmin().getUsername();
                        // 后台点击确认收款，清空支付方式
                        this.daoSupport.execute("update es_order set payment_plugin_id = null,payment_method_name=null where sn=?", orderDO.getSn());
                        break;
                    default:
                        break;
                }

                //款到发货订单 卖家不能确认收款
                if (permission.equals(OrderPermission.seller) && orderDO.getPaymentType().equals(PaymentTypeEnum.ONLINE.name())) {
                    throw new NoPermissionException("无权操作此订单");
                }
                // 付款金额和订单金额不相等
                if (payPrice.compareTo(orderDO.getNeedPayMoney()) != 0) {
                    throw new ServiceException(TradeErrorCode.E454.code(), "付款金额和应付金额不一致");
                }

                //判断支付方式
                if (orderDO.getPaymentType().equals(PaymentTypeEnum.COD.value())) {
                    this.daoSupport.execute("update es_order set order_status=? ,pay_status=?,ship_status = ? ,pay_money=? ,payment_time = ? where sn=? ",
                            OrderStatusEnum.PAID_OFF.value(), PayStatusEnum.PAY_YES.value(), ShipStatusEnum.SHIP_ROG.value(), payPrice, nowTime, orderDO.getSn());
                    orderDO.setShipStatus(ShipStatusEnum.SHIP_ROG.value());
                } else {
                    this.daoSupport.execute("update es_order set order_status=? ,pay_status=?,service_status=? ,pay_money=? ,payment_time = ? ,pay_order_no=? where sn=? ",
                            OrderStatusEnum.PAID_OFF.value(), PayStatusEnum.PAY_YES.value(), ServiceStatusEnum.EXPIRED.value(), payPrice, nowTime, returnTradeNo, orderDO.getSn());
                    orderDO.setServiceStatus(ServiceStatusEnum.EXPIRED.value());
                }
                this.daoSupport.execute("update es_trade set trade_status=? where trade_sn=?", TradeStatusEnum.PAID_OFF.value(), orderDO.getTradeSn());

                orderDO.setOrderStatus(OrderStatusEnum.PAID_OFF.name());
                orderDO.setPayStatus(PayStatusEnum.PAY_YES.value());
                orderDO.setPayMoney(payPrice);
                orderDO.setPaymentTime(nowTime);

                break;
            case SHIP:

                //检测订单是否已经申请售后
                if (ServiceStatusEnum.APPLY.name().equals(orderDO.getServiceStatus())
                        || ServiceStatusEnum.PASS.name().equals(orderDO.getServiceStatus())) {
                    throw new ServiceException(TradeErrorCode.E455.code(), "订单已申请退款，不能发货");
                }

                DeliveryVO deliveryVO = (DeliveryVO) paramVO;
                logMessage = "订单发货";
                newStatus = OrderStatusEnum.SHIPPED;
                operator = deliveryVO.getOperator();

                this.daoSupport.execute("update es_order set order_status=? ,ship_status=?,service_status=?,ship_no=? ,ship_time = ?,logi_id=?,logi_name=? where sn=? ",
                        OrderStatusEnum.SHIPPED.value(), ShipStatusEnum.SHIP_YES.value(), ServiceStatusEnum.NOT_APPLY.value(), deliveryVO.getDeliveryNo(), nowTime,
                        deliveryVO.getLogiId(), deliveryVO.getLogiName(), orderDetailVO.getSn());

                orderDO.setOrderStatus(OrderStatusEnum.SHIPPED.name());
                orderDO.setShipStatus(ShipStatusEnum.SHIP_YES.value());
                orderDO.setServiceStatus(ServiceStatusEnum.NOT_APPLY.value());
                orderDO.setShipNo(deliveryVO.getDeliveryNo());
                orderDO.setShipTime(nowTime);
                orderDO.setLogiId(deliveryVO.getLogiId());
                orderDO.setLogiName(deliveryVO.getLogiName());

                break;
            case ROG:

                RogVO rogVO = (RogVO) paramVO;
                logMessage = "确认收货";
                newStatus = OrderStatusEnum.ROG;
                operator = rogVO.getOperator();

                //订单售后状态
                String orderServiceStatus = ServiceStatusEnum.EXPIRED.value();

                this.daoSupport.execute("update es_order set order_status=? ,ship_status=?,service_status=?,signing_time = ?  where sn=? ",
                        OrderStatusEnum.ROG.value(), ShipStatusEnum.SHIP_ROG.value(), orderServiceStatus, nowTime, orderDO.getSn());

                orderDO.setOrderStatus(OrderStatusEnum.ROG.name());
                orderDO.setShipStatus(ShipStatusEnum.SHIP_ROG.value());
                orderDO.setServiceStatus(orderServiceStatus);
                orderDO.setSigningTime(nowTime);

                break;

            case CANCEL:

                CancelVO cancelVO = (CancelVO) paramVO;
                logMessage = "取消订单";
                newStatus = OrderStatusEnum.CANCELLED;
                operator = cancelVO.getOperator();

                this.daoSupport.execute("update es_order set order_status=? , cancel_reason=? where sn=? ",
                        OrderStatusEnum.CANCELLED.value(), cancelVO.getReason(), orderDO.getSn());
                orderDO.setOrderStatus(OrderStatusEnum.CANCELLED.name());
                orderDO.setCancelReason(cancelVO.getReason());
                break;

            case COMPLETE:

                CompleteVO completeVO = (CompleteVO) paramVO;
                logMessage = "订单已完成";
                newStatus = OrderStatusEnum.COMPLETE;
                operator = completeVO.getOperator();

                this.daoSupport.execute("update es_order set order_status=?,complete_time=?  where sn=? ", OrderStatusEnum.COMPLETE.value(),
                        nowTime, orderSn);
                orderDO.setOrderStatus(OrderStatusEnum.COMPLETE.name());
                orderDO.setCompleteTime(nowTime);

                break;

            default:
                break;
        }

        OrderStatusChangeMsg message = new OrderStatusChangeMsg();
        message.setOrderDO(orderDO);
        message.setOldStatus(OrderStatusEnum.valueOf(orderDO.getOrderStatus()));
        message.setNewStatus(newStatus);
        //发送订单状态变化消息
        this.messageSender.send(new MqMessage(AmqpExchange.ORDER_STATUS_CHANGE, "order-change-queue", message));

        // 记录日志
        OrderLogDO orderLogDO = new OrderLogDO();
        orderLogDO.setMessage(logMessage);
        orderLogDO.setOrderSn(orderSn);
        orderLogDO.setOpName(operator);
        orderLogDO.setOpTime(DateUtil.getDateline());
        this.orderLogManager.add(orderLogDO);

    }

    @Override
    public void updateTradeStatus(String sn, OrderStatusEnum orderStatus) {
        String sql = "update es_trade set trade_status = ? where trade_sn = ?";
        this.daoSupport.execute(sql, orderStatus.value(), sn);
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateItemRefundPrice(OrderDetailVO order) {
        //获取订单的满减优惠总额
        double fullMinus = order.getFullMinus();
        //获取订单优惠券优惠的总额
        double couponPrice = order.getCouponPrice();
        //获取订单商品集合
        List<OrderSkuVO> skuVOList = order.getOrderSkuList();

        //订单参与满减促销活动商品的总额（还未减去满减优惠的金额总计）
        double fmTotal = 0.00;
        //订单所有商品的总额（还未减去满减优惠和优惠券优惠的金额总计）
        double allTotal = 0.00;
        //订单参与满减促销活动的商品集合
        List<OrderSkuVO> fmList = new ArrayList<>();
        //订单未参与满减促销活动的商品集合
        List<OrderSkuVO> noFmList = new ArrayList<>();

        for (OrderSkuVO orderSkuVO : skuVOList) {
            if (orderSkuVO.getGroupList() != null && orderSkuVO.getGroupList().size() != 0) {
                fmTotal = CurrencyUtil.add(fmTotal, orderSkuVO.getSubtotal());
                fmList.add(orderSkuVO);
            } else {
                noFmList.add(orderSkuVO);
            }

            allTotal = CurrencyUtil.add(allTotal, orderSkuVO.getSubtotal());
        }

        //订单商品没有参与满减优惠活动也没有使用优惠券
        if (fmList.size() == 0 && couponPrice == 0 && noFmList.size() != 0) {
            updateItemRefundPrice(order.getSn(), skuVOList);

            //订单商品全部参与满减活动并且没有使用优惠券
        } else if (fmList.size() != 0 && couponPrice == 0 && noFmList.size() == 0) {
            updateFmItemsRefundPrice(order.getSn(), fmList, fullMinus, fmTotal);

            //订单商品一部分参与满减活动，一部分没有参与满减活动并且没有使用优惠券
        } else if (fmList.size() != 0 && couponPrice == 0 && noFmList.size() != 0) {
            updateFmItemsRefundPrice(order.getSn(), fmList, fullMinus, fmTotal);

            updateItemRefundPrice(order.getSn(), noFmList);

            //订单商品全部参与满减活动并且使用了优惠券
        } else if (fmList.size() != 0 && couponPrice != 0 && noFmList.size() == 0) {
            updateFmItemsRefundPrice(order.getSn(), fmList, CurrencyUtil.add(fullMinus, couponPrice), fmTotal);

            //订单商品一部分参与满减活动，一部分没有参与满减活动并且使用了优惠券
        } else if (fmList.size() != 0 && couponPrice != 0 && noFmList.size() != 0) {
            updateFmCouponItemsRefundPrice(order.getSn(), fmList, noFmList, couponPrice, allTotal, fullMinus, fmTotal);

            //订单商品全部没有参与满减活动并且使用了优惠券
        } else if (fmList.size() == 0 && couponPrice != 0 && noFmList.size() != 0) {
            //获取未参与满减活动商品总数-1的数值（为了兼容金额比例无法整除而导致多个商品的退款金额总和与退款金额不一致的问题）
            int noFmNum = noFmList.size() - 1;

            //剩余的订单优惠券优惠总额
            double surplusCouponPrice = couponPrice;

            updateCouponItemRefundPrice(order.getSn(), noFmList, couponPrice, allTotal, noFmNum, surplusCouponPrice);
        }
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateOrderServiceStatus(String orderSn, String statusEnum) {

        ServiceStatusEnum serviceStatusEnum = ServiceStatusEnum.valueOf(statusEnum);
        this.updateServiceStatus(orderSn, serviceStatusEnum);

        OrderDetailVO orderDetailVO = this.orderQueryManager.getModel(orderSn, null);
        List<OrderSkuVO> orderSkuVOList = orderDetailVO.getOrderSkuList();
        for (OrderSkuVO orderSkuVO : orderSkuVOList) {
            orderSkuVO.setServiceStatus(serviceStatusEnum.name());
        }
        this.updateItemJson(JsonUtil.objectToJson(orderSkuVOList), orderSn);

    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateOrderItemServiceStatus(String sn, List<OrderSkuDTO> orderSkuDTOList) {
        OrderDetailVO orderDetailVO = this.orderQueryManager.getModel(sn, null);
        List<OrderSkuVO> orderSkuVOList = orderDetailVO.getOrderSkuList();
        for (OrderSkuVO orderSkuVO : orderSkuVOList) {
            for (OrderSkuDTO orderSkuDTO : orderSkuDTOList) {
                if (orderSkuVO.getSkuId().equals(orderSkuDTO.getSkuId())) {
                    orderSkuVO.setServiceStatus(orderSkuDTO.getServiceStatus());
                }
            }
        }
        this.updateItemJson(JsonUtil.objectToJson(orderSkuVOList), sn);
    }


    /**
     * 修改订单项的退款金额
     * 调用说明：1.针对订单全部商品都没有参与满减活动也没有使用优惠券的情况
     * 2.针对订单部分商品都没有参与满减活动也没有使用优惠券的情况
     *
     * @param orderSn   订单编号
     * @param skuVOList 订单商品数据
     */
    private void updateItemRefundPrice(String orderSn, List<OrderSkuVO> skuVOList) {
        for (OrderSkuVO orderSkuVO : skuVOList) {
            double refundPrice = orderSkuVO.getSubtotal();
            updateRefundPrice(orderSn, orderSkuVO, refundPrice);
        }
    }

    /**
     * 修改订单项的退款金额
     * 调用说明：1.针对订单全部商品都参与了满减促销活动并且没有使用优惠券的情况
     * 2.针对订单全部商品都参与了满减促销活动并且使用了优惠券的情况
     * 3.针对订单一部分商品参与了满减促销活动并且没有使用优惠券的情况
     *
     * @param orderSn   订单编号
     * @param fmList    参与满减促销活动的订单商品集合
     * @param fullMinus 满减的总金额
     * @param fmTotal   参与满减促销活动的商品金额总计（还未减去满减优惠的金额总计）
     */
    private void updateFmItemsRefundPrice(String orderSn, List<OrderSkuVO> fmList, double fullMinus, double fmTotal) {
        //获取参与满减活动商品总数-1的数值（为了兼容金额比例无法整除而导致多个商品的退款金额总和与退款金额不一致的问题）
        int num = fmList.size() - 1;
        //剩余的订单满减总额
        double surplusFmPrice = fullMinus;

        for (int i = 0; i < fmList.size(); i++) {
            OrderSkuVO orderSkuVO = fmList.get(i);
            //当前商品的应退款金额
            double refundPrice = 0.00;

            if (i != num) {
                //获取当前商品满减的占比金额
                double fmRatioPrice = CurrencyUtil.mul(CurrencyUtil.div(orderSkuVO.getSubtotal(), fmTotal, 4), fullMinus);
                //当前商品应退款金额=金额总计-满减的占比金额
                refundPrice = CurrencyUtil.sub(orderSkuVO.getSubtotal(), fmRatioPrice);
                //计算剩余的满减总额
                surplusFmPrice = CurrencyUtil.sub(surplusFmPrice, fmRatioPrice);
            } else {
                //当前商品应退款金额=金额总计-剩余的满减总额
                refundPrice = CurrencyUtil.sub(orderSkuVO.getSubtotal(), surplusFmPrice);
            }

            updateRefundPrice(orderSn, orderSkuVO, refundPrice);
        }
    }

    /**
     * 修改订单项的退款金额
     * 调用说明：针对订单一部分商品参与了满减促销活动，一部分商品没有参与满减促销活动并且还使用了优惠券的情况
     *
     * @param orderSn     订单编号
     * @param fmList      参与满减促销活动的订单商品集合
     * @param noFmList    未参与满减促销活动的订单商品集合
     * @param couponPrice 订单使用的优惠券优惠金额
     * @param allTotal    所有商品的金额总计（还未减去满减优惠和优惠券优惠的金额总计）
     * @param fullMinus   满减的总金额
     * @param fmTotal     参与满减促销活动的商品金额总计（还未减去满减优惠的金额总计）
     */
    private void updateFmCouponItemsRefundPrice(String orderSn, List<OrderSkuVO> fmList, List<OrderSkuVO> noFmList, double couponPrice, double allTotal, double fullMinus, double fmTotal) {
        //获取参与满减活动商品总数-1的数值（为了兼容金额比例无法整除而导致多个商品的退款金额总和与退款金额不一致的问题）
        int fmMum = fmList.size() - 1;
        //获取未参与满减活动商品总数-1的数值（为了兼容金额比例无法整除而导致多个商品的退款金额总和与退款金额不一致的问题）
        int noFmNum = noFmList.size() - 1;

        //剩余的订单满减总额
        double surplusFmPrice = fullMinus;
        //剩余的订单优惠券优惠总额
        double surplusCouponPrice = couponPrice;

        for (int i = 0; i < fmList.size(); i++) {
            OrderSkuVO orderSkuVO = fmList.get(i);
            double refundPrice = 0.00;

            if (i != fmMum) {
                //获取当前商品满减的占比金额
                double fmRatioPrice = CurrencyUtil.mul(CurrencyUtil.div(orderSkuVO.getSubtotal(), fmTotal, 4), fullMinus);
                //获取当前商品优惠券优惠的占比金额
                double couponRatioPrice = CurrencyUtil.mul(CurrencyUtil.div(orderSkuVO.getSubtotal(), allTotal, 4), couponPrice);
                //当前商品应退款金额=金额总计-(满减的占比金额+优惠券优惠的占比金额)
                refundPrice = CurrencyUtil.sub(orderSkuVO.getSubtotal(), CurrencyUtil.add(fmRatioPrice, couponRatioPrice));
                //计算剩余的满减总额
                surplusFmPrice = CurrencyUtil.sub(surplusFmPrice, fmRatioPrice);
                //计算剩余的优惠券优惠总额
                surplusCouponPrice = CurrencyUtil.sub(surplusCouponPrice, couponRatioPrice);
            } else {
                //获取当前商品优惠券优惠的占比金额
                double couponRatioPrice = CurrencyUtil.mul(CurrencyUtil.div(orderSkuVO.getSubtotal(), allTotal, 4), couponPrice);
                //当前商品应退款金额=金额总计-(优惠券优惠的占比金额+剩余的满减总额)
                refundPrice = CurrencyUtil.sub(orderSkuVO.getSubtotal(), CurrencyUtil.add(couponRatioPrice, surplusFmPrice));
                //计算剩余的优惠券优惠总额
                surplusCouponPrice = CurrencyUtil.sub(surplusCouponPrice, couponRatioPrice);
            }

            updateRefundPrice(orderSn, orderSkuVO, refundPrice);
        }

        updateCouponItemRefundPrice(orderSn, noFmList, couponPrice, allTotal, noFmNum, surplusCouponPrice);
    }


    /**
     * 修改订单项的退款金额
     * 调用说明：1.针对订单一部分商品参与了满减促销活动，一部分商品没有参与满减促销活动并且还使用了优惠券的情况
     * 2.针对订单商品全部没有参与满减活动并且使用了优惠券的情况
     *
     * @param orderSn            订单编号
     * @param noFmList           没有参与满减促销活动的订单商品集合
     * @param couponPrice        订单使用的优惠券优惠金额
     * @param allTotal           所有商品的金额总计（还未减去满减优惠和优惠券优惠的金额总计）
     * @param noFmNum            获取未参与满减活动商品总数-1的数值（为了兼容金额比例无法整除而导致多个商品的退款金额总和与退款金额不一致的问题）
     * @param surplusCouponPrice 剩余的订单优惠券优惠总额
     */
    private void updateCouponItemRefundPrice(String orderSn, List<OrderSkuVO> noFmList, double couponPrice, double allTotal, int noFmNum, double surplusCouponPrice) {
        for (int i = 0; i < noFmList.size(); i++) {
            OrderSkuVO orderSkuVO = noFmList.get(i);
            double refundPrice = 0.00;

            if (i != noFmNum) {
                //获取当前商品优惠券优惠的占比金额
                double couponRatioPrice = CurrencyUtil.mul(CurrencyUtil.div(orderSkuVO.getSubtotal(), allTotal, 4), couponPrice);
                //当前商品应退款金额=金额总计-优惠券优惠的占比金额
                refundPrice = CurrencyUtil.sub(orderSkuVO.getSubtotal(), couponRatioPrice);
                //计算剩余的优惠券优惠总额
                surplusCouponPrice = CurrencyUtil.sub(surplusCouponPrice, couponRatioPrice);
            } else {
                //当前商品应退款金额=金额总计-计算剩余的优惠券优惠总额
                refundPrice = CurrencyUtil.sub(orderSkuVO.getSubtotal(), surplusCouponPrice);
            }

            updateRefundPrice(orderSn, orderSkuVO, refundPrice);
        }
    }

    /**
     * 修改订单项可退款金额的公共方法
     *
     * @param orderSn     订单编号
     * @param orderSkuVO  订单商品明细
     * @param refundPrice 可退款金额
     */
    private void updateRefundPrice(String orderSn, OrderSkuVO orderSkuVO, double refundPrice) {
        String sql = "update es_order_items set refund_price = ? where order_sn = ? and goods_id = ? and product_id = ?";
        this.daoSupport.execute(sql, refundPrice, orderSn, orderSkuVO.getGoodsId(), orderSkuVO.getSkuId());
    }

    /**
     * 对要操作的订单进行权限检查
     *
     * @param permission 需要的权限
     * @param order      相应的订单
     */
    private void checkPermission(OrderPermission permission, OrderDetailVO order) {

        if (permission != null) {
            if (order == null) {
                throw new NoPermissionException("无权操作此订单");
            }

            // 校验买家权限
            if (permission.equals(OrderPermission.buyer)) {
                Buyer buyer = UserContext.getBuyer();
                if (buyer == null || buyer.getUid() == null
                        || buyer.getUid().intValue() != order.getMemberId().intValue()) {
                    throw new NoPermissionException("无权操作此订单");
                }
            }

            // 校验管理权限
            if (permission.equals(OrderPermission.admin)) {

            }

            // 目前客户端不用校栓任何权限
            if (permission.equals(OrderPermission.client)) {

            }

        }
    }


    /**
     * 进行可操作校验
     * 看此状态下是否允许此操作
     *
     * @param order
     * @param orderOperate
     */
    private void checkAllowable(OrderPermission permission, OrderDetailVO order, OrderOperateEnum orderOperate) {
        //如果是client权限，则不验证下一步操作
        if (OrderPermission.client.equals(permission)) {
            return;
        }

        OrderStatusEnum status = OrderStatusEnum.valueOf(order.getOrderStatus());

        PaymentTypeEnum paymentType = PaymentTypeEnum.valueOf(order.getPaymentType());

        Map<OrderStatusEnum, OrderStep> flow = OrderOperateFlow.getFlow(paymentType, OrderTypeEnum.valueOf(order.getOrderType()));
        OrderOperateChecker orderOperateChecker = new OrderOperateChecker(flow);

        boolean isAllowble = orderOperateChecker.checkAllowable(status, orderOperate);

        if (!isAllowble) {
            throw new ServiceException(TradeErrorCode.E460.code(), "订单" + status.description() + "状态不能进行" + orderOperate.description() + "操作");
        }

    }


    /**
     * 内部类，为了传递参数使用
     */
    private class PayParam {
        private Double payPrice;
        private String returnTradeNo;

        public Double getPayPrice() {
            return payPrice;
        }

        public String getReturnTradeNo() {
            return returnTradeNo;
        }

        public void setPayPrice(Double payPrice) {
            this.payPrice = payPrice;
        }

        public void setReturnTradeNo(String returnTradeNo) {
            this.returnTradeNo = returnTradeNo;
        }
    }


}
