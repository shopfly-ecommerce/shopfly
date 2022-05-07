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
package cloud.shopfly.b2c.core.trade.order.service.impl;

import cloud.shopfly.b2c.core.trade.order.model.dos.*;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderDTO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOutStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOutTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;
import cloud.shopfly.b2c.core.trade.order.service.CheckoutParamManager;
import cloud.shopfly.b2c.core.trade.order.service.OrderLogManager;
import cloud.shopfly.b2c.core.trade.order.service.OrderOutStatusManager;
import cloud.shopfly.b2c.core.trade.order.service.TradeIntodbManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.trade.TradeErrorCode;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartPromotionVo;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.service.CartOriginDataManager;
import cloud.shopfly.b2c.core.trade.order.model.dos.*;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Transaction entry business implementation class
 *
 * @author Snow create in 2018/5/9
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class TradeIntodbManagerImpl implements TradeIntodbManager {

    protected final Log logger = LogFactory.getLog(this.getClass());


    private Integer orderCacheTimeout = 60 * 60;

    @Autowired
    
    private DaoSupport daoSupport;
    @Autowired

    private Cache cache;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private CheckoutParamManager checkoutParamManager;

    @Autowired
    private OrderLogManager orderLogManager;

    @Autowired
    private OrderOutStatusManager orderOutStatusManager;

    @Autowired
    private CartOriginDataManager cartOriginDataManager;

    /**
     * The important practical application of transaction knowledge here is that when two transaction methods are called within the same class, they should not be called directly, otherwise the transaction will not take effect.by_Snow
     * Specific please see：https://segmentfault.com/a/1190000008379179
     */
    @Autowired
    private TradeIntodbManagerImpl self;


    @Override
    @Transactional( propagation = Propagation.REQUIRED,
            rollbackFor = {RuntimeException.class, ServiceException.class, Exception.class})
    public void intoDB(TradeVO tradeVO) {
        try {
            self.innerIntoDB(tradeVO);

            // Pressure into the cache
            String cacheKey = CachePrefix.TRADE_SESSION_ID_PREFIX.getPrefix() + tradeVO.getTradeSn();
            this.cache.put(cacheKey, tradeVO, orderCacheTimeout);

            // Clear shopping cart data for purchased items
            cartOriginDataManager.cleanChecked();


            // Clearing Remarks
            this.checkoutParamManager.setRemark("");


            // Send the order creation message
            this.messageSender.send(new MqMessage(AmqpExchange.ORDER_CREATE,
                    AmqpExchange.ORDER_CREATE + "_ROUTING",
                    cacheKey));

        } catch (Exception e) {

            logger.error("Error creating order", e);
            throw new ServiceException(TradeErrorCode.E456.code(), "An error occurred in order creation. Please try again later");

        }
    }


    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void innerIntoDB(TradeVO tradeVO) {

        if (tradeVO == null) {
            throw new RuntimeException("Transaction cannot be stored, reason：tradeIs empty");
        }

        // Order MQ message
        OrderStatusChangeMsg orderStatusChangeMsg = new OrderStatusChangeMsg();

        Buyer buyer = UserContext.getBuyer();

        // Trading warehousing
        TradeDO tradeDO = new TradeDO(tradeVO);
        tradeDO.setCreateTime(DateUtil.getDateline());

        this.daoSupport.insert(tradeDO);

        long createTime = DateUtil.getDateline();
        tradeDO.setCreateTime(createTime);

        // Order is put in storage
        List<OrderDTO> orderList = tradeVO.getOrderList();
        for (OrderDTO orderDTO : orderList) {

            /**
             * Calculate the transaction price of each item：
             * If a coupon is used for this order, the coupon amount should be proportionally allocated to the price of each item.
             * Proportion refers to the percentage of the goods in the total amount of the goods in this order.
             */
            // Total discount amount of this order (including all activity discounts, coupon discounts).
            Double discountTotalPrice = orderDTO.getPrice().getDiscountPrice();
            List<CartSkuVO> list = orderDTO.getSkuList();

            // Total discount amount excluding single product reduction, group purchase, flash sale coupon amount.
            for (CartSkuVO skuVO : list) {

                List<CartPromotionVo> singleList = skuVO.getSingleList();
                if (singleList == null) {
                    continue;
                }
                String promotionType = "";

                for (CartPromotionVo promotionGoodsVO : singleList) {
                    if (promotionGoodsVO.getIsCheck() != null && promotionGoodsVO.getIsCheck() == 1) {
                        promotionType = promotionGoodsVO.getPromotionType();
                    }
                }

                if (promotionType.equals(PromotionTypeEnum.MINUS.name())
                        || promotionType.equals(PromotionTypeEnum.GROUPBUY.name())
                        || promotionType.equals(PromotionTypeEnum.SECKILL.name())
                        || promotionType.equals(PromotionTypeEnum.HALF_PRICE.name())) {
                    // The original subtotal
                    Double originalSubTotal = CurrencyUtil.mul(skuVO.getOriginalPrice(), skuVO.getNum());
                    // Gross Discount - a discount for immediate reduction of a product
                    discountTotalPrice = CurrencyUtil.sub(discountTotalPrice, CurrencyUtil.sub(originalSubTotal, skuVO.getSubtotal()));
                }
            }

            // Convert DTO to DO
            OrderDO orderDO = new OrderDO(orderDTO);
            orderDO.setTradeSn(tradeVO.getTradeSn());
            orderDO.setOrderStatus(OrderStatusEnum.NEW.value());
            orderStatusChangeMsg.setOldStatus(OrderStatusEnum.NEW);
            orderStatusChangeMsg.setNewStatus(OrderStatusEnum.NEW);

            // Assign default values to orderDTO that will be used in orderLineVO
            orderDTO.setOrderStatus(orderDO.getOrderStatus());
            orderDTO.setPayStatus(orderDO.getPayStatus());
            orderDTO.setShipStatus(orderDO.getShipStatus());
            orderDTO.setCommentStatus(orderDO.getCommentStatus());
            orderDTO.setServiceStatus(orderDO.getServiceStatus());


            this.daoSupport.insert(orderDO);

            int orderId = this.daoSupport.getLastId("es_order");
            orderDO.setOrderId(orderId);

            // Order entry into storage
            for (CartSkuVO skuVO : orderDTO.getSkuList()) {
                OrderItemsDO item = new OrderItemsDO(skuVO);
                item.setOrderSn(orderDO.getSn());
                item.setTradeSn(orderDO.getTradeSn());
                this.daoSupport.insert(item);
            }

            // Order outgoing status table
            for (String type : OrderOutTypeEnum.getAll()) {
                OrderOutStatus orderOutStatus = new OrderOutStatus();
                orderOutStatus.setOrderSn(orderDO.getSn());
                orderOutStatus.setOutType(type);
                orderOutStatus.setOutStatus(OrderOutStatusEnum.WAIT.name());
                this.orderOutStatusManager.add(orderOutStatus);
            }


            // Send an AMQP status message
            orderStatusChangeMsg.setOrderDO(orderDO);

            // Send the order creation message
            this.messageSender.send(new MqMessage(AmqpExchange.ORDER_STATUS_CHANGE,
                    AmqpExchange.ORDER_STATUS_CHANGE + "_ROUTING",
                    orderStatusChangeMsg));


            // log
            OrderLogDO logDO = new OrderLogDO();
            logDO.setOrderSn(orderDO.getSn());
            logDO.setMessage("Create the order");
            logDO.setOpName(buyer.getUsername());
            logDO.setOpTime(DateUtil.getDateline());
            this.orderLogManager.add(logDO);


        }
    }

}
