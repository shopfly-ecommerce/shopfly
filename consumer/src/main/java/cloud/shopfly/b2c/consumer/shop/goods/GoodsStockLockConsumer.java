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
package cloud.shopfly.b2c.consumer.shop.goods;

import cloud.shopfly.b2c.consumer.core.event.TradeIntoDbEvent;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.client.goods.GoodsQuantityClient;
import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.goods.model.enums.QuantityType;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyGoodsManager;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillGoodsManager;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartPromotionVo;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderDTO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
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

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());


    /**
     * Process order creation deduction inventory
     *
     * @param tradeVO
     */
    @Override
    public void onTradeIntoDb(TradeVO tradeVO) {

        if (tradeVO == null) {
            throw new ResourceNotFoundException("The deal doesnt exist.");
        }
        // Gets the collection of orders in the transaction
        List<OrderDTO> orderDTOS = tradeVO.getOrderList();

        // This variable identifies whether the order has been updated successfully. Only when the order status has been updated successfully, the transaction status can be updated to confirmed.
        // If only one order update status fails, the transaction fails, but the order is not affected
        boolean bool = true;

        for (OrderDTO order : orderDTOS) {
            bool = orderIntoDb(order);
        }

        // Process a transaction in which an order status update fails, then the transaction status is failed to eject
        if (!bool) {
            this.updateTradeState(tradeVO.getTradeSn(), 0, OrderStatusEnum.INTODB_ERROR);
        } else {
            this.updateTradeState(tradeVO.getTradeSn(), 0, OrderStatusEnum.CONFIRM);
        }

    }


    /**
     * Order is put in storage
     *
     * @param order
     * @return
     */
    private boolean orderIntoDb(OrderDTO order) {


        // ------------- try locking discount inventory ------------- first
        // If the library lock fails, roll back by itself
        boolean promotionLockResult = lockPromotionStock(order);

        OrderStatusEnum status;

        boolean normalLockResult = false;

        // ------------- try to lock normal inventory ------
        // If the preferential stock lock is successful, try locking regular stock
        if (promotionLockResult) {
            // If the lock fails, roll back (without caller handling)
            normalLockResult = lockNormalStock(order);
        }

        boolean lockResult = normalLockResult && promotionLockResult;

        // If the warehouse is locked successfully, the order status is successful, that is, OrderStatusEnum.CONFIRM;
        // But if the amount is zero, the direct payment is successful
        if (lockResult) {

            // If the agent payment amount is 0, the state becomes paid
            if (order.getNeedPayMoney() == 0 && !order.getPaymentType().equals(PaymentTypeEnum.COD.value())) {
                status = OrderStatusEnum.PAID_OFF;
            } else {
                status = OrderStatusEnum.CONFIRM;
            }

        } else {
            // Lock library failure
            status = OrderStatusEnum.INTODB_ERROR;
        }

        // ------------- Update order status ------------
        // If the order status update fails, retry three times, and roll back the inventory if it finally fails
        boolean updateResult = this.updateState(order.getSn(), 0, status);

        // Send an MQ message if the update is successful
        if (updateResult) {

            // If the order status is updated successfully, the order status change message is sent
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

            // ----- Roll back all inventory -----

            // Roll back the inventory of common goods
            rollbackNormal(order);

            // Roll back preferential inventory
            rollbackPromotionStock(order);


        }

        // The order is successfully locked and the status update is successful
        return updateResult && lockResult;

    }


    /***
     * Roll back normal inventory
     * @param order
     */
    private void rollbackNormal(OrderDTO order) {
        List<CartSkuVO> skuList = order.getSkuList();

        // Get the SKU information in the order
        List<GoodsQuantityVO> goodsQuantityVOList = buildQuantityList(skuList);

        rollbackReduce(goodsQuantityVOList);

    }

    /**
     * Lock general merchandise inventory
     *
     * @param order
     * @return
     */
    private boolean lockNormalStock(OrderDTO order) {

        // Get the SKU information in the order
        List<CartSkuVO> skuList = order.getSkuList();
        List<GoodsQuantityVO> goodsQuantityVOList = buildQuantityList(skuList);

        // Subtract the normal inventory. Note: If this is unsuccessful, the inventory is already rolled back in the script and the program does not need to be rolled back
        boolean normalResult = goodsQuantityClient.updateSkuQuantity(goodsQuantityVOList);

        if (logger.isDebugEnabled()) {
            logger.debug("The order【" + order.getSn() + "】The result of commodity lock inventory is：" + normalResult);
        }

        return normalResult;
    }


    /**
     * Build a list of inventory to deduct from the shopping cart list
     *
     * @param skuList
     * @return
     */
    private List<GoodsQuantityVO> buildQuantityList(List<CartSkuVO> skuList) {

        List<GoodsQuantityVO> goodsQuantityVOList = new ArrayList<>();
        for (CartSkuVO sku : skuList) {
            if(logger.isDebugEnabled()){
                logger.debug("cart num is " + sku.getPurchaseNum());
            }
            GoodsQuantityVO goodsQuantity = new GoodsQuantityVO();
            goodsQuantity.setSkuId(sku.getSkuId());
            goodsQuantity.setGoodsId(sku.getGoodsId());

            // Set it to negative, subtract it
            goodsQuantity.setQuantity(0 - sku.getNum());

            // Set to deduct available inventory
            goodsQuantity.setQuantityType(QuantityType.enable);
            goodsQuantityVOList.add(goodsQuantity);

        }

        return goodsQuantityVOList;
    }


    /**
     * Roll back preferential inventory
     *
     * @param order
     */
    private void rollbackPromotionStock(OrderDTO order) {

        // Get the SKU information in the order
        List<CartSkuVO> skuList = order.getSkuList();
        // flash
        List<PromotionDTO> promotionDTOSekillList = new ArrayList<>();
        // A bulk
        List<PromotionDTO> promotionDTOGroupBuyList = new ArrayList<>();

        buildPromotionList(skuList, promotionDTOSekillList, promotionDTOGroupBuyList);

        seckillApplyManager.rollbackStock(promotionDTOSekillList);
        groupbuyGoodsManager.rollbackStock(promotionDTOGroupBuyList, order.getSn());

    }


    /**
     * Build a list of promotions
     *
     * @param skuList
     * @param callbackSekillList The second kill list to build
     * @param callbackGroupList  A group purchase list to build
     */
    private void buildPromotionList(List<CartSkuVO> skuList, List<PromotionDTO> callbackSekillList, List<PromotionDTO> callbackGroupList) {

        for (CartSkuVO sku : skuList) {

            List<CartPromotionVo> singleList = sku.getSingleList();
            if (singleList != null && singleList.size() > 0) {
                for (CartPromotionVo promotionGoodsVO : singleList) {

                    // If the preferential inventory is sufficient, it can be a second kill order
                    if (sku.getPurchaseNum() > 0) {
                        // A flash sale to decide whether to participate
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
     * Discount inventory
     *
     * @param order
     * @return
     */
    private boolean lockPromotionStock(OrderDTO order) {

        boolean lockResult = false;

        // Get the SKU information in the order
        List<CartSkuVO> skuList = order.getSkuList();
        // flash
        List<PromotionDTO> promotionDTOSekillList = new ArrayList<>();
        // A bulk
        List<PromotionDTO> promotionDTOGroupBuyList = new ArrayList<>();

        buildPromotionList(skuList, promotionDTOSekillList, promotionDTOGroupBuyList);


        // Discount flash sale inventory
        boolean sekillResult = true;
        if (promotionDTOSekillList.size() > 0) {
            sekillResult = this.seckillApplyManager.addSoldNum(promotionDTOSekillList);
            if (logger.isDebugEnabled()) {
                logger.debug("The result of the SEC lock inventory is：" + sekillResult);
            }
        }

        // Deduct group purchase inventory
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
     * Modify order status
     *
     * @param sn    The ordersn
     * @param times The number of
     * @return Check whether the modification is successful.
     */
    private boolean updateState(String sn, Integer times, OrderStatusEnum status) {
        try {
            // Return directly after three failures
            if (times >= 3) {
                logger.error("The order status update failed after three attempts,The order number for" + sn + ",retry");
                return false;
            }
            // Change the order status to Confirmed
            boolean result = orderClient.updateOrderStatus(sn, status);

            if (logger.isDebugEnabled()) {
                logger.debug("Update the order【" + sn + "】The status of[" + status + "]The first[" + times + "time]The results of：" + result);
            }
            if (!result) {
                // If the update fails, wait 1 second and try again
                Thread.sleep(1000);
                return updateState(sn, ++times, status);
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error("Order status update failed,The order number for" + sn + ",retry" + ++times + "time,The message" + e.getMessage());
            updateState(sn, ++times, status);
        }
        return true;
    }

    /**
     * Modify order status
     *
     * @param sn          The ordersn
     * @param times       The number of
     * @param orderStatus Status
     * @return Check whether the modification is successful.
     */
    private boolean updateTradeState(String sn, Integer times, OrderStatusEnum orderStatus) {
        try {
            // Return directly after three failures
            if (times >= 3) {
                logger.error("The transaction status update failed after three attempts,Transaction number is" + sn + ",retry");
                return false;
            }
            // Change the transaction status to Confirmed
            if (!orderClient.updateTradeStatus(sn, orderStatus)) {
                // If the update fails, wait 1 second and try again
                Thread.sleep(1000);
                return updateTradeState(sn, ++times, orderStatus);
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error("Transaction status update failed,The order number for" + sn + ",retry" + ++times + "time,The message" + e.getMessage());
            updateState(sn, ++times, orderStatus);
        }
        return false;
    }
}
