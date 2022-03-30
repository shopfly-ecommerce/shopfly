/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.goods;

import dev.shopflix.consumer.core.event.TradeIntoDbEvent;
import com.enation.app.javashop.core.base.message.OrderStatusChangeMsg;
import com.enation.app.javashop.core.base.rabbitmq.AmqpExchange;
import com.enation.app.javashop.core.client.goods.GoodsQuantityClient;
import com.enation.app.javashop.core.client.trade.OrderClient;
import com.enation.app.javashop.core.goods.model.enums.QuantityType;
import com.enation.app.javashop.core.goods.model.vo.GoodsQuantityVO;
import com.enation.app.javashop.core.promotion.groupbuy.service.GroupbuyGoodsManager;
import com.enation.app.javashop.core.promotion.seckill.service.SeckillGoodsManager;
import com.enation.app.javashop.core.promotion.tool.model.dto.PromotionDTO;
import com.enation.app.javashop.core.promotion.tool.model.enums.PromotionTypeEnum;
import com.enation.app.javashop.core.trade.cart.model.vo.CartPromotionVo;
import com.enation.app.javashop.core.trade.cart.model.vo.CartSkuVO;
import com.enation.app.javashop.core.trade.order.model.dos.OrderDO;
import com.enation.app.javashop.core.trade.order.model.dto.OrderDTO;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import com.enation.app.javashop.core.trade.order.model.enums.PaymentTypeEnum;
import com.enation.app.javashop.core.trade.order.model.vo.TradeVO;
import com.enation.app.javashop.framework.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.enation.app.javashop.framework.rabbitmq.MessageSender;
import com.enation.app.javashop.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-07-26
 */
@Component
public class GoodsStockLockConsumer implements TradeIntoDbEvent {

    @Autowired
    private GoodsQuantityClient goodsQuantityClient;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private SeckillGoodsManager seckillApplyManager;

    @Autowired
    private GroupbuyGoodsManager groupbuyGoodsManager;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 处理订单创建扣减库存
     *
     * @param tradeVO
     */
    @Override
    public void onTradeIntoDb(TradeVO tradeVO) {

        if (tradeVO == null) {
            throw new ResourceNotFoundException("交易不存在");
        }
        //获取交易中的订单集合
        List<OrderDTO> orderDTOS = tradeVO.getOrderList();

        //此变量标识订单是否全部更新成功，只有订单状态全部更新成功，交易状态才可以更新为已确认，
        // 只要有一个订单更新状态失败，则交易失败，但是不影响订单
        boolean bool = true;

        for (OrderDTO order : orderDTOS) {
            bool = orderIntoDb(order);
        }

        //处理交易,如果此交易中有一个订单状态更新失败，则此交易状态为出库失败
        if (!bool) {
            this.updateTradeState(tradeVO.getTradeSn(), 0, OrderStatusEnum.INTODB_ERROR);
        } else {
            this.updateTradeState(tradeVO.getTradeSn(), 0, OrderStatusEnum.CONFIRM);
        }

    }


    /**
     * 订单入库
     *
     * @param order
     * @return
     */
    private boolean orderIntoDb(OrderDTO order) {


        //-------------先尝试锁优惠库存-------------
        //如果锁库失败，自行回滚
        boolean promotionLockResult = lockPromotionStock(order);

        OrderStatusEnum status;

        boolean normalLockResult = false;

        //-------------尝试锁普通库存------
        //如果优惠库存锁成功，尝试锁普通库存
        if (promotionLockResult) {
            //如果锁库失败，自行回滚(不需调用者处理)
            normalLockResult = lockNormalStock(order);
        }

        boolean lockResult = normalLockResult && promotionLockResult;

        //如果锁库成功，订单状态为出库成功，即OrderStatusEnum.CONFIRM;
        //但如果金额为零，则直接付款成功
        if (lockResult) {

            //如果代支付金额为0，则状态变成已付款
            if (order.getNeedPayMoney() == 0 && !order.getPaymentType().equals(PaymentTypeEnum.COD.value())) {
                status = OrderStatusEnum.PAID_OFF;
            } else {
                status = OrderStatusEnum.CONFIRM;
            }

        } else {
            //锁库失败
            status = OrderStatusEnum.INTODB_ERROR;
        }

        //-------------更新订单状态------------
        // 如果订单状态更新失败，重试三次，如果最终失败则回滚库存
        boolean updateResult = this.updateState(order.getSn(), 0, status);

        //如果更新成功则发mq消息
        if (updateResult) {

            //此处说明订单状态更新成功，则发送订单状态变化消息
            OrderStatusChangeMsg orderStatusChangeMsg = new OrderStatusChangeMsg();
            orderStatusChangeMsg.setOldStatus(OrderStatusEnum.NEW);
            orderStatusChangeMsg.setNewStatus(status);
            OrderDO orderDO = new OrderDO(order);
            orderDO.setTradeSn(order.getTradeSn());
            orderDO.setOrderStatus(status.value());
            orderStatusChangeMsg.setOrderDO(orderDO);

            this.messageSender.send(new MqMessage(AmqpExchange.ORDER_STATUS_CHANGE,
                    AmqpExchange.ORDER_STATUS_CHANGE + "_ROUTING",
                    orderStatusChangeMsg));

        } else {

            //-----回滚所有的库存 -----

            //回滚普通商品的库存
            rollbackNormal(order);

            //回滚优惠库存
            rollbackPromotionStock(order);


        }

        //订单锁库成功 并且状态更新成功才算成功
        return updateResult && lockResult;

    }


    /***
     * 回滚普通库存
     * @param order
     */
    private void rollbackNormal(OrderDTO order) {
        List<CartSkuVO> skuList = order.getSkuList();

        //获取订单中的sku信息
        List<GoodsQuantityVO> goodsQuantityVOList = buildQuantityList(skuList);

        rollbackReduce(goodsQuantityVOList);

    }

    /**
     * 锁普通商品库存
     *
     * @param order
     * @return
     */
    private boolean lockNormalStock(OrderDTO order) {

        //获取订单中的sku信息
        List<CartSkuVO> skuList = order.getSkuList();
        List<GoodsQuantityVO> goodsQuantityVOList = buildQuantityList(skuList);

        //扣减正常库存，注意：如果不成功，库存在脚本里已经回滚，程序不需要回滚
        boolean normalResult = goodsQuantityClient.updateSkuQuantity(goodsQuantityVOList);

        if (logger.isDebugEnabled()) {
            logger.debug("订单【" + order.getSn() + "】商品锁库存结果为：" + normalResult);
        }

        return normalResult;
    }


    /**
     * 根据购物车列表构建要扣减的库存列表
     *
     * @param skuList
     * @return
     */
    private List<GoodsQuantityVO> buildQuantityList(List<CartSkuVO> skuList) {

        List<GoodsQuantityVO> goodsQuantityVOList = new ArrayList<>();
        for (CartSkuVO sku : skuList) {
            logger.debug("cart num is " + sku.getPurchaseNum());
            GoodsQuantityVO goodsQuantity = new GoodsQuantityVO();
            goodsQuantity.setSkuId(sku.getSkuId());
            goodsQuantity.setGoodsId(sku.getGoodsId());

            //设置为负数，要减掉的
            goodsQuantity.setQuantity(0 - sku.getNum());

            //设置为要扣减可用库存
            goodsQuantity.setQuantityType(QuantityType.enable);
            goodsQuantityVOList.add(goodsQuantity);

        }

        return goodsQuantityVOList;
    }


    /**
     * 回滚优惠库存
     *
     * @param order
     */
    private void rollbackPromotionStock(OrderDTO order) {

        //获取订单中的sku信息
        List<CartSkuVO> skuList = order.getSkuList();
        //限时抢购
        List<PromotionDTO> promotionDTOSekillList = new ArrayList<>();
        //团购
        List<PromotionDTO> promotionDTOGroupBuyList = new ArrayList<>();

        buildPromotionList(skuList, promotionDTOSekillList, promotionDTOGroupBuyList);

        seckillApplyManager.rollbackStock(promotionDTOSekillList);
        groupbuyGoodsManager.rollbackStock(promotionDTOGroupBuyList, order.getSn());

    }


    /**
     * 构建促销活动列表
     *
     * @param skuList
     * @param callbackSekillList 要构建的秒杀列表
     * @param callbackGroupList  要构建的团购列表
     */
    private void buildPromotionList(List<CartSkuVO> skuList, List<PromotionDTO> callbackSekillList, List<PromotionDTO> callbackGroupList) {

        for (CartSkuVO sku : skuList) {

            List<CartPromotionVo> singleList = sku.getSingleList();
            if (singleList != null && singleList.size() > 0) {
                for (CartPromotionVo promotionGoodsVO : singleList) {

                    //如果优惠库存充足才可以是秒杀订单
                    if (sku.getPurchaseNum() > 0) {
                        //判断是否参加的限时抢购的活动
                        if (promotionGoodsVO.getPromotionType().equals(PromotionTypeEnum.SECKILL.name())) {
                            PromotionDTO promotionDTO = new PromotionDTO();
                            promotionDTO.setActId(promotionGoodsVO.getActivityId());
                            promotionDTO.setGoodsId(sku.getGoodsId());
                            promotionDTO.setNum(sku.getPurchaseNum());
                            callbackSekillList.add(promotionDTO);
                        }

                        if (promotionGoodsVO.getIsCheck() == 1 && promotionGoodsVO.getPromotionType().equals(PromotionTypeEnum.GROUPBUY.name())) {
                            PromotionDTO promotionDTO = new PromotionDTO();
                            promotionDTO.setActId(promotionGoodsVO.getActivityId());
                            promotionDTO.setGoodsId(sku.getGoodsId());
                            promotionDTO.setNum(sku.getPurchaseNum());
                            callbackGroupList.add(promotionDTO);
                        }

                    }

                }
            }

        }
    }

    /**
     * 扣减优惠库存
     *
     * @param order
     * @return
     */
    private boolean lockPromotionStock(OrderDTO order) {

        boolean lockResult = false;

        //获取订单中的sku信息
        List<CartSkuVO> skuList = order.getSkuList();
        //限时抢购
        List<PromotionDTO> promotionDTOSekillList = new ArrayList<>();
        //团购
        List<PromotionDTO> promotionDTOGroupBuyList = new ArrayList<>();

        buildPromotionList(skuList, promotionDTOSekillList, promotionDTOGroupBuyList);


        //扣减限时抢购库存
        boolean sekillResult = true;
        if (promotionDTOSekillList.size() > 0) {
            sekillResult = this.seckillApplyManager.addSoldNum(promotionDTOSekillList);
            if (logger.isDebugEnabled()) {
                logger.debug("秒杀锁库存结果为：" + sekillResult);
            }
        }

        //扣减团购库存
        boolean groupBuyResult = true;
        if (promotionDTOGroupBuyList.size() > 0) {
            groupBuyResult = this.groupbuyGoodsManager.cutQuantity(order.getSn(), promotionDTOGroupBuyList);
        }

        if (sekillResult && groupBuyResult) {
            lockResult = true;
        }

        return lockResult;

    }


    private void rollbackReduce(List<GoodsQuantityVO> goodsQuantityVOList) {
        goodsQuantityVOList.forEach(goodsQuantityVO -> {
            goodsQuantityVO.setQuantity(0 - goodsQuantityVO.getQuantity());
        });
        goodsQuantityClient.updateSkuQuantity(goodsQuantityVOList);
    }

    /**
     * 修改订单状态
     *
     * @param sn    订单sn
     * @param times 次数
     * @return 是否修改成功
     */
    private boolean updateState(String sn, Integer times, OrderStatusEnum status) {
        try {
            // 失败三次后直接返回
            if (times >= 3) {
                logger.error("订单状态重试三次后更新失败,订单号为" + sn + ",重试");
                return false;
            }
            // 更改订单状态为已确认
            boolean result = orderClient.updateOrderStatus(sn, status);

            if (logger.isDebugEnabled()) {
                logger.debug("更新订单【" + sn + "】状态为[" + status + "]第[" + times + "次]结果：" + result);
            }
            if (!result) {
                // 如果更新失败，则等待1秒重试
                Thread.sleep(1000);
                return updateState(sn, ++times, status);
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error("订单状态更新失败,订单号为" + sn + ",重试" + ++times + "次,消息" + e.getMessage());
            updateState(sn, ++times, status);
        }
        return true;
    }

    /**
     * 修改订单状态
     *
     * @param sn          订单sn
     * @param times       次数
     * @param orderStatus 订单状态
     * @return 是否修改成功
     */
    private boolean updateTradeState(String sn, Integer times, OrderStatusEnum orderStatus) {
        try {
            // 失败三次后直接返回
            if (times >= 3) {
                logger.error("交易状态重试三次后更新失败,交易号为" + sn + ",重试");
                return false;
            }
            // 更改交易状态为已确认
            if (!orderClient.updateTradeStatus(sn, orderStatus)) {
                // 如果更新失败，则等待1秒重试
                Thread.sleep(1000);
                return updateTradeState(sn, ++times, orderStatus);
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error("交易状态更新失败,订单号为" + sn + ",重试" + ++times + "次,消息" + e.getMessage());
            updateState(sn, ++times, orderStatus);
        }
        return false;
    }
}
