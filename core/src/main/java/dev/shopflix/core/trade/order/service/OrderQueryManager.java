/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.service;

import dev.shopflix.core.trade.order.model.dos.OrderDO;
import dev.shopflix.core.trade.order.model.dos.OrderItemsDO;
import dev.shopflix.core.trade.order.model.dto.OrderQueryParam;
import dev.shopflix.core.trade.order.model.vo.OrderDetailVO;
import dev.shopflix.core.trade.order.model.vo.OrderFlowNode;
import dev.shopflix.core.trade.order.model.vo.OrderStatusNumVO;
import dev.shopflix.core.trade.sdk.model.OrderDetailDTO;
import dev.shopflix.framework.database.Page;

import java.util.List;

/**
 * 订单相关
 *
 * @author Snow create in 2018/5/14
 * @version v2.0
 * @since v7.0.0
 */
public interface OrderQueryManager {
    /**
     * 读取订单状态的订单数
     *
     * @param memberId 会员id
     * @return
     */
    OrderStatusNumVO getOrderStatusNum(Integer memberId);

    /**
     * 读取会员所有的订单数量
     *
     * @param memberId 会员id
     * @return
     */
    Integer getOrderNumByMemberId(Integer memberId);

    /**
     * 读取会员(评论状态)订单数量
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
     * @param buyerId 用户编号
     * @return
     */
    OrderDetailVO getModel(String orderSn, Integer buyerId);

    /**
     * 查询一个订单的详细
     *
     * @param orderSn
     * @return
     */
    OrderDetailDTO getModel(String orderSn);

    /**
     * 获取某订单的订单项
     *
     * @param orderSn 订单编号
     * @return
     */
    List<OrderItemsDO> orderItems(String orderSn);

    /**
     * 获取订单可退款总额
     * @param orderSn 订单编号
     * @return
     */
    double getOrderRefundPrice(String orderSn);


    /**
     * 查询订单表列表
     *
     * @param paramDTO 参数对象
     * @return Page
     */
    Page list(OrderQueryParam paramDTO);

    /**
     * 读取订单列表根据交易编号
     *
     * @param tradeSn
     * @param memberId
     * @return
     */
    List<OrderDetailVO> getOrderByTradeSn(String tradeSn, Integer memberId);

    /**
     * 读取订单列表根据交易编号——系统内部使用 OrderClient
     * @param tradeSn
     * @return
     */
    List<OrderDetailDTO> getOrderByTradeSn(String tradeSn);

    /**
     * 根据订单sn读取，订单的流程
     *
     * @param orderSn 订单编号
     * @return
     */
    List<OrderFlowNode> getOrderFlow(String orderSn);

    /**
     * 查询一个订单DO
     * @param orderSn
     * @return
     */
    OrderDO getDoByOrderSn(String orderSn);


}