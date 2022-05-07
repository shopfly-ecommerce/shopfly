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
package cloud.shopfly.b2c.core.promotion.pintuan.service.impl;

import cloud.shopfly.b2c.core.member.model.dos.MemberAddress;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanCartManager;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanOrderManager;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderDTO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;
import cloud.shopfly.b2c.core.trade.order.service.TradeCreator;
import cloud.shopfly.b2c.core.trade.order.service.impl.DefaultTradeCreator;
import cloud.shopfly.b2c.core.trade.order.service.impl.TradeManagerImpl;
import cloud.shopfly.b2c.core.promotion.pintuan.exception.PintuanErrorCode;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartView;
import cloud.shopfly.b2c.core.trade.order.model.vo.CheckoutParamVO;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kingapex on 2019-01-24.
 * Group trading business class<br/>
 * Inherit the default transaction business class<br/>
 * The difference is:<br/>
 * 1、use{@link PintuanCartManager} Get shopping cart contents<br/>
 * 2、Do not test the legitimacy of preferential activities, because there is no overlap of other activities
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-24
 */
@Service
public class PintuanTradeManagerImpl extends TradeManagerImpl {

    @Autowired
    private PintuanCartManager pintuanCartManager;

    @Autowired
    private PintuanOrderManager pintuanOrderManager;

    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * Create an order and create a group order
     *
     * @param client
     * @param pinTuanOrderId
     * @return
     */
    public TradeVO createTrade(String client, Integer pinTuanOrderId) {

        // Participate in the decision of their own group
        if (pinTuanOrderId != null) {
            Buyer buyer = UserContext.getBuyer();
            this.pintuanOrderManager.getModel(pinTuanOrderId).getParticipants().forEach(participant -> {
                if (participant.getId().equals(buyer.getUid())) {
                    throw new ServiceException(PintuanErrorCode.E5013.code(), "You cannot join a group created by yourself");
                }
            });
        }


        // Set the customer type
        super.setClientType(client);

        CheckoutParamVO param = checkoutParamManager.getParam();

        CartView cartView = pintuanCartManager.getCart();

        MemberAddress memberAddress = this.memberAddressClient.getModel(param.getAddressId());

        if (logger.isDebugEnabled()) {
            logger.debug("Ready to create a group order");
            logger.debug("param:" + param);
            logger.debug("cartView:" + cartView);
            logger.debug("memberAddress:" + memberAddress);
        }

        TradeCreator tradeCreator = new DefaultTradeCreator(param, cartView, memberAddress).setTradeSnCreator(tradeSnCreator).setGoodsClient(goodsClient).setMemberClient(memberClient).setShippingManager(shippingManager);

        // Unlike ordinary transactions, there is no need to test the legality of activities because there is no overlap of activities
        // Check configuration range -> Check item validity -> Create transaction
        TradeVO tradeVO = tradeCreator.checkShipRange().checkGoods().createTrade();
        OrderDTO orderDTO = tradeVO.getOrderList().get(0);
        orderDTO.setOrderType(OrderTypeEnum.pintuan.name());

        if (logger.isDebugEnabled()) {
            logger.debug("Generate trading：" + tradeVO);
        }

        // Order is put in storage
        this.tradeIntodbManager.intoDB(tradeVO);


        // Create a group order
        OrderDTO order = tradeVO.getOrderList().get(0);
        CartSkuVO skuVO = cartView.getCartList().get(0).getSkuList().get(0);

        pintuanOrderManager.createOrder(order, skuVO.getSkuId(), pinTuanOrderId);

        return tradeVO;
    }
}
