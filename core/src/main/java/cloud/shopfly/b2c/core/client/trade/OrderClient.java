/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.trade;

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderDetailVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderStatusNumVO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;

import java.util.List;

/**
 * 订单查询SDK
 *
 * @author Snow create in 2018/5/28
 * @version v2.0
 * @since v7.0.0
 */
public interface OrderClient {
    /**
     * 读取订单状态的订单数
     *
     * @param memberId 会员id
     * @return
     */
    OrderStatusNumVO getOrderStatusNum(Integer memberId);

    /**
     * 根据交易编号查询订单项
     *
     * @param tradeSn
     * @return
     */
    List<OrderDetailDTO> getOrderByTradeSn(String tradeSn);

    /**
     * 根据会员id读取我的所有订单数量
     *
     * @param memberId 会员id
     * @return
     */
    Integer getOrderNumByMemberId(Integer memberId);

    /**
     * 根据会员id读取我的(评论状态)订单数量
     *
     * @param memberId
     * @param commentStatus 评论状态
     * @return
     */
    Integer getOrderCommentNumByMemberId(Integer memberId, String commentStatus);

    /**
     * 读取一个订单详细<br/>
     *
     * @param orderSn 订单编号 必传
     * @return
     */
    OrderDetailDTO getModel(String orderSn);

    /**
     * 更新订单状态
     *
     * @param sn          订单号
     * @param orderStatus 订单状态
     * @return 是否更新成功
     */
    boolean updateOrderStatus(String sn, OrderStatusEnum orderStatus);

    /**
     * 更新交易状态
     *
     * @param sn          交易sn
     * @param orderStatus 交易状态
     * @return 是否更新成功
     */
    boolean updateTradeStatus(String sn, OrderStatusEnum orderStatus);

    /**
     * 更新订单项可退款金额
     *
     * @param orderDO
     */
    void addOrderItemRefundPrice(OrderDO orderDO);

    /**
     * 读取一个订单详细<br/>
     *
     * @param orderSn 订单编号 必传
     * @return
     */
    OrderDetailVO getOrderVO(String orderSn);

    /**
     * 更改订单评论状态
     *
     * @param orderSn
     * @param statusEnum
     */
    void updateOrderCommentStatus(String orderSn, String statusEnum);


    /**
     * 为某个订单的付款
     *
     * @param orderSn       订单编号
     * @param price         支付价格
     * @param returnTradeNo 第三方平台回传的支付单号
     * @param permission    权限 {@link OrderPermission}
     */
    void payOrder(String orderSn, Double price, String returnTradeNo, String permission);


    /**
     * 根据订单号修改第三方支付流水号
     * @param payOrderNo
     * @param sn
     */
    void updateOrderPayOrderNo(String payOrderNo, String sn);


}
