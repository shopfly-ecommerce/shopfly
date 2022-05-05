/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.service;

import dev.shopflix.core.trade.cart.model.dos.OrderPermission;
import dev.shopflix.core.trade.order.model.dos.OrderDO;
import dev.shopflix.core.trade.order.model.enums.CommentStatusEnum;
import dev.shopflix.core.trade.order.model.enums.OrderOperateEnum;
import dev.shopflix.core.trade.order.model.enums.OrderStatusEnum;
import dev.shopflix.core.trade.order.model.enums.ServiceStatusEnum;
import dev.shopflix.core.trade.order.model.vo.*;
import dev.shopflix.core.trade.sdk.model.OrderSkuDTO;

import java.util.List;

/**
 * 订单流程操作
 *
 * @author Snow create in 2018/5/15
 * @version v2.0
 * @since v7.0.0
 */
public interface OrderOperateManager {

    /**
     * 确认订单
     *
     * @param confirmVO  订单确认vo
     * @param permission 需要检测的订单权限
     */
    void confirm(ConfirmVO confirmVO, OrderPermission permission);

    /**
     * 为某订单付款<br/>
     *
     * @param orderSn    订单号
     * @param payPrice   本次付款金额
     * @param returnTradeNo   本次付款金额
     * @param permission 需要检测的订单权限
     * @param permission 权限 {@link OrderPermission}
     * @return
     * @throws IllegalArgumentException 下列情形之一抛出此异常:
     *                                  <li>order_sn(订单id)为null</li>
     * @throws IllegalStateException    如果订单支付状态为已支付
     */
    OrderDO payOrder(String orderSn, Double payPrice, String returnTradeNo, OrderPermission permission);


    /**
     * 发货
     *
     * @param deliveryVO 运单</br>
     * @param permission 需要检测的订单权限
     */
    void ship(DeliveryVO deliveryVO, OrderPermission permission);


    /**
     * 订单收货
     *
     * @param rogVO      收货VO
     * @param permission 需要检测的订单权限
     */
    void rog(RogVO rogVO, OrderPermission permission);


    /**
     * 订单取消
     *
     * @param cancelVO   取消vo
     * @param permission 需要检测的订单权限
     */
    void cancel(CancelVO cancelVO, OrderPermission permission);

    /**
     * 订单完成
     *
     * @param completeVO 订单完成vo
     * @param permission 需要检测的订单权限
     */
    void complete(CompleteVO completeVO, OrderPermission permission);

    /**
     * 更新订单的售后状态
     *
     * @param orderSn
     * @param serviceStatus
     */
    void updateServiceStatus(String orderSn, ServiceStatusEnum serviceStatus);


    /**
     * 修改收货人信息
     *
     * @param orderConsignee
     * @return
     */
    OrderConsigneeVO updateOrderConsignee(OrderConsigneeVO orderConsignee);

    /**
     * 修改订单价格
     *
     * @param orderSn
     * @param orderPrice
     */
    void updateOrderPrice(String orderSn, Double orderPrice);

    /**
     * 更新订单的评论状态
     *
     * @param orderSn
     * @param commentStatus
     */
    void updateCommentStatus(String orderSn, CommentStatusEnum commentStatus);

    /**
     * 更新订单项快照ID
     *
     * @param itemsJson
     * @param orderSn
     * @return
     */
    void updateItemJson(String itemsJson, String orderSn);

    /**
     * 更新订单的订单状态
     *
     * @param orderSn
     * @param orderStatus
     */
    void updateOrderStatus(String orderSn, OrderStatusEnum orderStatus);

    /**
     * 更新交易状态
     *
     * @param sn          交易sn
     * @param orderStatus 状态
     */
    void updateTradeStatus(String sn, OrderStatusEnum orderStatus);

    /**
     * 执行操作
     *
     * @param orderSn      订单编号
     * @param permission   权限
     * @param orderOperate 要执行什么操作
     * @param paramVO      参数对象
     */
    void executeOperate(String orderSn, OrderPermission permission, OrderOperateEnum orderOperate, Object paramVO);

    /**
     * 更新订单项可退款金额
     *
     * @param order
     */
    void updateItemRefundPrice(OrderDetailVO order);

    /**
     * 更改订单的售后状态
     *
     * @param orderSn
     * @param statusEnum
     */
    void updateOrderServiceStatus(String orderSn, String statusEnum);

    /**
     * 修改订单货物项的售后状态
     *
     * @param sn
     * @param orderSkuDTOList
     */
    void updateOrderItemServiceStatus(String sn, List<OrderSkuDTO> orderSkuDTOList);

    /**
     * 根据订单号修改第三方支付流水号
     * @param payOrderNo
     * @param sn
     */
    void updateOrderPayOrderNo(String payOrderNo, String sn);

}
