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

import cloud.shopfly.b2c.core.trade.cart.model.vo.*;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderDTO;
import cloud.shopfly.b2c.core.trade.order.model.vo.ConsigneeVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;
import cloud.shopfly.b2c.core.trade.order.service.ShippingManager;
import cloud.shopfly.b2c.core.trade.order.service.TradeCreator;
import cloud.shopfly.b2c.core.trade.order.service.TradeSnCreator;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dos.MemberAddress;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.trade.TradeErrorCode;
import cloud.shopfly.b2c.core.trade.cart.model.vo.*;
import cloud.shopfly.b2c.core.trade.order.model.vo.CheckoutParamVO;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingapex on 2019-01-24.
 * Default transaction creator
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-24
 */
@SuppressWarnings("Duplicates")
public class DefaultTradeCreator implements TradeCreator {

    protected CheckoutParamVO param;
    protected CartView cartView;
    protected MemberAddress memberAddress;
    protected ShippingManager shippingManager;
    protected GoodsClient goodsClient;
    protected MemberClient memberClient;
    protected TradeSnCreator tradeSnCreator;

    protected final Log logger = LogFactory.getLog(this.getClass());


    public DefaultTradeCreator() {
    }

    /**
     * The constructor sets up the raw materials needed to build the exchange
     *
     * @param param         Settlement parameter
     * @param cartView      Shopping cart view
     * @param memberAddress Shipping address
     */
    public DefaultTradeCreator(CheckoutParamVO param, CartView cartView, MemberAddress memberAddress) {

        this.param = param;
        this.cartView = cartView;
        this.memberAddress = memberAddress;
    }

    public DefaultTradeCreator setShippingManager(ShippingManager shippingManager) {
        this.shippingManager = shippingManager;
        return this;
    }

    public DefaultTradeCreator setGoodsClient(GoodsClient goodsClient) {
        this.goodsClient = goodsClient;
        return this;
    }

    public DefaultTradeCreator setMemberClient(MemberClient memberClient) {
        this.memberClient = memberClient;
        return this;
    }

    public DefaultTradeCreator setTradeSnCreator(TradeSnCreator tradeSnCreator) {
        this.tradeSnCreator = tradeSnCreator;
        return this;
    }

    @Override
    public TradeVO createTrade() {

        Assert.notNull(tradeSnCreator, "tradeSnCreatorEmpty, please call firstsetTradeSnCreatorSet up the correct transaction number generator");


        Assert.notNull(param.getAddressId(), "Shipping address must be selected");
        Assert.notNull(param.getPaymentType(), "Payment method must be selected");

        Buyer buyer = UserContext.getBuyer();

        List<CartVO> cartList = cartView.getCartList();

        // The consignee
        ConsigneeVO consignee = new ConsigneeVO();
        consignee.setConsigneeId(memberAddress.getAddrId());
        consignee.setAddress(memberAddress.getAddr());
        consignee.setCountry(memberAddress.getCountry());
        consignee.setStateName(memberAddress.getStateName());
        consignee.setCity(memberAddress.getCity());
        consignee.setCountryCode(memberAddress.getCountryCode());
        consignee.setStateCode(memberAddress.getStateCode());
        consignee.setZipCode(memberAddress.getZipCode());
        consignee.setMobile(memberAddress.getMobile());
        consignee.setName(memberAddress.getName());


        String tradeNo = tradeSnCreator.generateTradeSn();
        TradeVO tradeVO = new TradeVO();

        // The consignee
        tradeVO.setConsignee(consignee);

        // Effect of number
        tradeVO.setTradeSn(tradeNo);

        // Payment type
        tradeVO.setPaymentType(param.getPaymentType().value());

        // The member information
        tradeVO.setMemberId(buyer.getUid());
        tradeVO.setMemberName(buyer.getUsername());
        List<OrderDTO> orderList = new ArrayList<OrderDTO>();

        // Order Creation time
        long createTime = DateUtil.getDateline();

        List<CouponVO> couponVOS = new ArrayList<>();
        // To generate orders
        for (CartVO cart : cartList) {

            // Generate order number
            String orderSn = tradeSnCreator.generateOrderSn();

            // Shopping information
            OrderDTO order = new OrderDTO(cart);

            // Last update
            order.setCreateTime(createTime);

            // Purchase membership information
            order.setMemberId(buyer.getUid());
            order.setMemberName(buyer.getUsername());
            order.setTradeSn(tradeNo);
            order.setSn(orderSn);
            order.setConsignee(consignee);

            // The shipping mode parameter is temporarily invalid
            order.setShippingId(0);

            // Payment type
            order.setPaymentType(param.getPaymentType().value());
            // invoice
            order.setNeedReceipt(0);
            if (param.getReceipt() != null) {
                order.setNeedReceipt(1);
            }
            order.setReceiptVO(param.getReceipt());
            // The goods time
            order.setReceiveTime(param.getReceiveTime());

            // The order note
            order.setRemark(param.getRemark());

            // Source of the order
            order.setClientType(param.getClientType());

            // The order price
            order.getPrice().reCountDiscountPrice();

            if (logger.isDebugEnabled()) {
                logger.debug("The order[" + order.getSn() + "]theprice:");
                logger.debug(order.getPrice());
            }


            order.setNeedPayMoney(order.getPrice().getTotalPrice());

            order.setOrderPrice(order.getPrice().getTotalPrice());

            order.setGoodsNum(cart.getSkuList().size());

            orderList.add(order);

            if (cart.getCouponList() != null && cart.getCouponList().size() > 0) {
                for (CouponVO couponVO : cart.getCouponList()) {
                    if (couponVO.getSelected() == 1) {
                        couponVOS.add(couponVO);
                    }
                }
            }


        }

        // Reading settlement price
        PriceDetailVO paymentDetail = cartView.getTotalPrice();
        paymentDetail.reCountDiscountPrice();
        if (logger.isDebugEnabled()) {
            logger.debug("generateTradeVOwhenpricefor");
            logger.debug(paymentDetail);
        }
        // The transaction price
        tradeVO.setPriceDetail(paymentDetail);

        tradeVO.setOrderList(orderList);


        tradeVO.setCouponList(couponVOS);
        return tradeVO;
    }


    @SuppressWarnings("Duplicates")
    @Override
    public TradeCreator checkShipRange() {

        Assert.notNull(shippingManager, "shippingManagerEmpty, please call firstsetShippingManagerSet up the correct delivery management business class");

        if (memberAddress == null) {
            throw new ServiceException(TradeErrorCode.E452.code(), "Please fill in the delivery address");
        }

        // Goods that have been selected for settlement
        List<CartVO> cartList = cartView.getCartList();


        // 2, screening goods not in the distribution area
        List<CacheGoods> list = this.shippingManager.checkArea(cartList,memberAddress.getCountryCode(),memberAddress.getStateCode());

        // A collection of commodity problems after verification
        List<Map> goodsErrorList = new ArrayList();

        if (list.size() > 0) {
            for (CacheGoods goods : list) {
                Map errorMap = new HashMap(16);
                errorMap.put("name", goods.getGoodsName());
                errorMap.put("image", goods.getThumbnail());
                goodsErrorList.add(errorMap);
            }
            throw new ServiceException(TradeErrorCode.E461.code(), "Goods are not in the distribution area", goodsErrorList);
        }


        return this;
    }

    @Override
    public TradeCreator checkGoods() {

        Assert.notNull(goodsClient, "goodsClientEmpty, please call firstsetGoodsClientSet up the right commodity businessClient");


        // Goods that have been selected for settlement
        List<CartVO> cartList = cartView.getCartList();

        // 1. Check whether the shopping cart is empty
        if (cartList == null || cartList.isEmpty()) {
            throw new ServiceException(TradeErrorCode.E452.code(), "Shopping cart empty");
        }
        // A collection of commodity problems after verification
        List<Map> goodsErrorList = new ArrayList();

        boolean flag = true;
        // Iterate through the shopping cart collection
        for (CartVO cartVO : cartList) {

            List<CartSkuVO> skuList = cartVO.getSkuList();

            for (CartSkuVO cartSkuVO : skuList) {
                Map errorMap = new HashMap(16);
                errorMap.put("name", cartSkuVO.getName());
                errorMap.put("image", cartSkuVO.getGoodsImage());

                Integer skuId = cartSkuVO.getSkuId();
                GoodsSkuVO skuVO = this.goodsClient.getSkuFromCache(skuId);

                // Test whether goods exist
                if (skuVO == null) {
                    goodsErrorList.add(errorMap);
                    continue;
                }

                // Detect the loading and unloading status of goods
                if (skuVO.getMarketEnable() != null && skuVO.getMarketEnable().intValue() != 1) {
                    goodsErrorList.add(errorMap);
                    continue;
                }

                // Detects the deletion status of an item
                if (skuVO.getDisabled() != null && skuVO.getDisabled().intValue() != 1) {
                    goodsErrorList.add(errorMap);
                    continue;
                }

                // Read the amount of stock available for this product
                int enableQuantity = skuVO.getEnableQuantity();
                // The quantity of this product to be purchased
                int num = cartSkuVO.getNum();

                // If the number of products to be purchased is greater than the number in Redis, this product cannot be ordered
                if (num > enableQuantity) {
                    flag = false;
                    goodsErrorList.add(errorMap);
                    continue;
                }
            }
        }

        if (!goodsErrorList.isEmpty()) {
            throw new ServiceException(TradeErrorCode.E452.code(), "Sorry, the following items are not available in your area", JsonUtil.objectToJson(goodsErrorList));
        }

        return this;
    }


    @Override
    public TradeCreator checkPromotion() {
        Assert.notNull(memberClient, "memberClientEmpty, please call firstsetMemberClientSet up the right membership businessClient");

        List<CartVO> cartList = cartView.getCartList();

        for (CartVO cartVO : cartList) {

            List<CartSkuVO> skuList = cartVO.getSkuList();

            for (CartSkuVO cartSkuVO : skuList) {
                innerCheckPromotion(cartSkuVO);

            }
        }


        // Read the total transaction price information for the order
        PriceDetailVO detailVO = cartView.getTotalPrice();

        // This transaction requires deducting the users points
        int point = detailVO.getExchangePoint();

        if (point > 0) {
            Buyer buyer = UserContext.getBuyer();
            Member member = this.memberClient.getModel(buyer.getUid());
            int consumPoint = member.getConsumPoint();

            // If the number of consumption points available to the user is less than the number of points to be deducted from the transaction, the user cannot place an order
            if (consumPoint < point) {
                throw new ServiceException(TradeErrorCode.E452.code(), "You do not have enough consumption points available,Please return to your shopping cart to modify the item");
            }
        }

        return this;
    }


    private void innerCheckPromotion(CartSkuVO cartSkuVO) {

        Map errorMap = new HashMap(16);
        errorMap.put("name", cartSkuVO.getName());
        errorMap.put("image", cartSkuVO.getGoodsImage());

        // A collection of promotional activity problems that exist after verification
        List<Map> promotionErrorList = new ArrayList();
        boolean flag = true;
        // A single activity in which this product participates
        List<CartPromotionVo> singlePromotionList = cartSkuVO.getSingleList();
        if (!singlePromotionList.isEmpty()) {
            for (CartPromotionVo promotionGoodsVO : singlePromotionList) {

                // Default participating activity && Non-participating activity status
                if (promotionGoodsVO.getIsCheck().intValue() == 1 && !promotionGoodsVO.getPromotionType().equals(PromotionTypeEnum.NO.name())) {
                    // The expiration time of the current activity
                    long entTime = promotionGoodsVO.getEndTime();

                    // The current time
                    long currTime = DateUtil.getDateline();

                    // If the current time is greater than the expiration time, the activity has expired and cannot be placed
                    if (currTime > entTime) {
                        flag = false;
                        promotionErrorList.add(errorMap);
                        continue;
                    }
                }

            }
        }

        // A combination of activities in which this product participates
        List<CartPromotionVo> groupPromotionList = cartSkuVO.getGroupList();
        if (!groupPromotionList.isEmpty()) {
            for (CartPromotionVo cartPromotionGoodsVo : groupPromotionList) {
                // The expiration time of the current activity
                long entTime = cartPromotionGoodsVo.getEndTime();

                // The current time
                long currTime = DateUtil.getDateline();

                // If the current time is greater than the expiration time, the activity has expired and cannot be placed
                if (currTime > entTime) {
                    flag = false;

                    promotionErrorList.add(errorMap);
                    continue;
                }
            }
        }
    }


}
