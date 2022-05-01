/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.service.impl;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.base.message.OrderStatusChangeMsg;
import dev.shopflix.core.base.rabbitmq.AmqpExchange;
import dev.shopflix.core.promotion.tool.model.enums.PromotionTypeEnum;
import dev.shopflix.core.trade.TradeErrorCode;
import dev.shopflix.core.trade.cart.model.vo.CartPromotionVo;
import dev.shopflix.core.trade.cart.model.vo.CartSkuVO;
import dev.shopflix.core.trade.cart.service.CartOriginDataManager;
import dev.shopflix.core.trade.order.model.dos.*;
import dev.shopflix.core.trade.order.model.dto.OrderDTO;
import dev.shopflix.core.trade.order.model.enums.OrderOutStatusEnum;
import dev.shopflix.core.trade.order.model.enums.OrderOutTypeEnum;
import dev.shopflix.core.trade.order.model.enums.OrderStatusEnum;
import dev.shopflix.core.trade.order.model.enums.TradeStatusEnum;
import dev.shopflix.core.trade.order.model.vo.TradeVO;
import dev.shopflix.core.trade.order.service.CheckoutParamManager;
import dev.shopflix.core.trade.order.service.OrderLogManager;
import dev.shopflix.core.trade.order.service.OrderOutStatusManager;
import dev.shopflix.core.trade.order.service.TradeIntodbManager;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.security.model.Buyer;
import dev.shopflix.framework.util.CurrencyUtil;
import dev.shopflix.framework.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import dev.shopflix.framework.rabbitmq.MessageSender;
import dev.shopflix.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 交易入库业务实现类
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
     * 此处有重要的事务实际应用知识，当同一个类内部两个事务方法调用的时候，不能直接调用，否则事务会不生效。by_Snow
     * 具体请看：https://segmentfault.com/a/1190000008379179
     */
    @Autowired
    private TradeIntodbManagerImpl self;


    @Override
    @Transactional( propagation = Propagation.REQUIRED,
            rollbackFor = {RuntimeException.class, ServiceException.class, Exception.class})
    public void intoDB(TradeVO tradeVO) {
        try {
            self.innerIntoDB(tradeVO);

            //压入缓存
            String cacheKey = CachePrefix.TRADE_SESSION_ID_PREFIX.getPrefix() + tradeVO.getTradeSn();
            this.cache.put(cacheKey, tradeVO, orderCacheTimeout);

            //清除已购买的商品购物车数据
            cartOriginDataManager.cleanChecked();


            //清空备注信息
            this.checkoutParamManager.setRemark("");


            //发送订单创建消息
            this.messageSender.send(new MqMessage(AmqpExchange.ORDER_CREATE,
                    AmqpExchange.ORDER_CREATE + "_ROUTING",
                    cacheKey));

        } catch (Exception e) {

            logger.error("创建订单出错", e);
            throw new ServiceException(TradeErrorCode.E456.code(), "订单创建出现错误，请稍后重试");

        }
    }


    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void innerIntoDB(TradeVO tradeVO) {

        if (tradeVO == null) {
            throw new RuntimeException("交易无法入库，原因：trade为空");
        }

        //订单mq消息
        OrderStatusChangeMsg orderStatusChangeMsg = new OrderStatusChangeMsg();

        Buyer buyer = UserContext.getBuyer();

        // 交易入库
        TradeDO tradeDO = new TradeDO(tradeVO);
        tradeDO.setCreateTime(DateUtil.getDateline());

        //TODO snow测试用
        tradeDO.setTradeStatus(OrderStatusEnum.CONFIRM.name());
        this.daoSupport.insert(tradeDO);

        long createTime = DateUtil.getDateline();
        tradeDO.setCreateTime(createTime);

        // 订单入库
        List<OrderDTO> orderList = tradeVO.getOrderList();
        for (OrderDTO orderDTO : orderList) {

            /**
             * 计算每个商品的成交价：
             * 如果此订单使用了优惠券，要把优惠券的金额按照比例分配到每个商品的价格。
             * 比例按照此商品占此订单商品总金额的百分比。
             */
            //此订单总优惠金额(包含所有活动优惠，优惠券的优惠。)
            Double discountTotalPrice = orderDTO.getPrice().getDiscountPrice();
            List<CartSkuVO> list = orderDTO.getSkuList();

            //总优惠金额 去除单品立减，团购，限时抢购优惠券的金额。
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
                    //原价小计
                    Double originalSubTotal = CurrencyUtil.mul(skuVO.getOriginalPrice(), skuVO.getNum());
                    //总优惠 - 商品立减的优惠
                    discountTotalPrice = CurrencyUtil.sub(discountTotalPrice, CurrencyUtil.sub(originalSubTotal, skuVO.getSubtotal()));
                }
            }

            //将DTO转换DO
            OrderDO orderDO = new OrderDO(orderDTO);
            orderDO.setTradeSn(tradeVO.getTradeSn());
            orderDO.setOrderStatus(OrderStatusEnum.NEW.value());
            orderStatusChangeMsg.setOldStatus(OrderStatusEnum.NEW);
            orderStatusChangeMsg.setNewStatus(OrderStatusEnum.NEW);

            // 为orderDTO 赋默认值，这些值会在orderLineVO中使用
            orderDTO.setOrderStatus(orderDO.getOrderStatus());
            orderDTO.setPayStatus(orderDO.getPayStatus());
            orderDTO.setShipStatus(orderDO.getShipStatus());
            orderDTO.setCommentStatus(orderDO.getCommentStatus());
            orderDTO.setServiceStatus(orderDO.getServiceStatus());


            //TODO snow测试用
            orderDO.setOrderStatus(OrderStatusEnum.CONFIRM.name());
            this.daoSupport.insert(orderDO);

            int orderId = this.daoSupport.getLastId("es_order");
            orderDO.setOrderId(orderId);

            //订单项入库
            for (CartSkuVO skuVO : orderDTO.getSkuList()) {
                OrderItemsDO item = new OrderItemsDO(skuVO);
                item.setOrderSn(orderDO.getSn());
                item.setTradeSn(orderDO.getTradeSn());
                this.daoSupport.insert(item);
            }

            //订单出库状态表
            for (String type : OrderOutTypeEnum.getAll()) {
                OrderOutStatus orderOutStatus = new OrderOutStatus();
                orderOutStatus.setOrderSn(orderDO.getSn());
                orderOutStatus.setOutType(type);
                orderOutStatus.setOutStatus(OrderOutStatusEnum.WAIT.name());
                this.orderOutStatusManager.add(orderOutStatus);
            }


            //发送amqp状态消息
            orderStatusChangeMsg.setOrderDO(orderDO);

            //发送订单创建消息
            this.messageSender.send(new MqMessage(AmqpExchange.ORDER_STATUS_CHANGE,
                    AmqpExchange.ORDER_STATUS_CHANGE + "_ROUTING",
                    orderStatusChangeMsg));


            //记录日志
            OrderLogDO logDO = new OrderLogDO();
            logDO.setOrderSn(orderDO.getSn());
            logDO.setMessage("创建订单");
            logDO.setOpName(buyer.getUsername());
            logDO.setOpTime(DateUtil.getDateline());
            this.orderLogManager.add(logDO);


        }
    }

}
