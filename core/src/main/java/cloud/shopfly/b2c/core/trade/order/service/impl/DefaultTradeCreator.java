/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 默认交易创建器
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
     * 通过构造器设置构建交易所需要的原料
     *
     * @param param         结算参数
     * @param cartView      购物车视图
     * @param memberAddress 收货地址
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

        Assert.notNull(tradeSnCreator, "tradeSnCreator为空，请先调用setTradeSnCreator设置正确的交易号生成器");


        Assert.notNull(param.getAddressId(), "必须选择收货地址");
        Assert.notNull(param.getPaymentType(), "必须选择支付方式");

        Buyer buyer = UserContext.getBuyer();

        List<CartVO> cartList = cartView.getCartList();

        //收货人
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

        //收货人
        tradeVO.setConsignee(consignee);

        //效果编号
        tradeVO.setTradeSn(tradeNo);

        //支付类型
        tradeVO.setPaymentType(param.getPaymentType().value());

        //会员信息
        tradeVO.setMemberId(buyer.getUid());
        tradeVO.setMemberName(buyer.getUsername());
        List<OrderDTO> orderList = new ArrayList<OrderDTO>();

        //订单创建时间
        long createTime = DateUtil.getDateline();

        List<CouponVO> couponVOS = new ArrayList<>();
        //生成订单
        for (CartVO cart : cartList) {

            //生成订单编号
            String orderSn = tradeSnCreator.generateOrderSn();

            //购物信息
            OrderDTO order = new OrderDTO(cart);

            //创建时间
            order.setCreateTime(createTime);

            //购买的会员信息
            order.setMemberId(buyer.getUid());
            order.setMemberName(buyer.getUsername());
            order.setTradeSn(tradeNo);
            order.setSn(orderSn);
            order.setConsignee(consignee);

            //配送方式 这个参数暂时无效
            order.setShippingId(0);

            //支付类型
            order.setPaymentType(param.getPaymentType().value());
            //发票
            order.setNeedReceipt(0);
            if (param.getReceipt() != null) {
                order.setNeedReceipt(1);
            }
            order.setReceiptVO(param.getReceipt());
            //收货时间
            order.setReceiveTime(param.getReceiveTime());

            //订单备注
            order.setRemark(param.getRemark());

            //订单来源
            order.setClientType(param.getClientType());

            //订单价格
            order.getPrice().reCountDiscountPrice();

            if (logger.isDebugEnabled()) {
                logger.debug("订单[" + order.getSn() + "]的price:");
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

        //读取结算价格
        PriceDetailVO paymentDetail = cartView.getTotalPrice();
        paymentDetail.reCountDiscountPrice();
        if (logger.isDebugEnabled()) {
            logger.debug("生成TradeVO时price为");
            logger.debug(paymentDetail);
        }
        //交易价格
        tradeVO.setPriceDetail(paymentDetail);

        tradeVO.setOrderList(orderList);


        tradeVO.setCouponList(couponVOS);
        return tradeVO;
    }


    @SuppressWarnings("Duplicates")
    @Override
    public TradeCreator checkShipRange() {

        Assert.notNull(shippingManager, "shippingManager为空，请先调用setShippingManager设置正确的交配送管理业务类");

        if (memberAddress == null) {
            throw new ServiceException(TradeErrorCode.E452.code(), "请填写收货地址");
        }

        //已选中结算的商品
        List<CartVO> cartList = cartView.getCartList();


        //2、筛选不在配送区域的商品
        List<CacheGoods> list = this.shippingManager.checkArea(cartList,memberAddress.getCountryCode(),memberAddress.getStateCode());

        //验证后存在商品问题的集合
        List<Map> goodsErrorList = new ArrayList();

        if (list.size() > 0) {
            for (CacheGoods goods : list) {
                Map errorMap = new HashMap(16);
                errorMap.put("name", goods.getGoodsName());
                errorMap.put("image", goods.getThumbnail());
                goodsErrorList.add(errorMap);
            }
            throw new ServiceException(TradeErrorCode.E461.code(), "商品不在配送区域", goodsErrorList);
        }


        return this;
    }

    @Override
    public TradeCreator checkGoods() {

        Assert.notNull(goodsClient, "goodsClient为空，请先调用setGoodsClient设置正确的商品业务Client");


        //已选中结算的商品
        List<CartVO> cartList = cartView.getCartList();

        //1、检测购物车是否为空
        if (cartList == null || cartList.isEmpty()) {
            throw new ServiceException(TradeErrorCode.E452.code(), "购物车为空");
        }
        //验证后存在商品问题的集合
        List<Map> goodsErrorList = new ArrayList();

        boolean flag = true;
        //遍历购物车集合
        for (CartVO cartVO : cartList) {

            List<CartSkuVO> skuList = cartVO.getSkuList();

            for (CartSkuVO cartSkuVO : skuList) {
                Map errorMap = new HashMap(16);
                errorMap.put("name", cartSkuVO.getName());
                errorMap.put("image", cartSkuVO.getGoodsImage());

                Integer skuId = cartSkuVO.getSkuId();
                GoodsSkuVO skuVO = this.goodsClient.getSkuFromCache(skuId);

                //检测商品是否存在
                if (skuVO == null) {
                    goodsErrorList.add(errorMap);
                    continue;
                }

                //检测商品的上下架状态
                if (skuVO.getMarketEnable() != null && skuVO.getMarketEnable().intValue() != 1) {
                    goodsErrorList.add(errorMap);
                    continue;
                }

                //检测商品的删除状态
                if (skuVO.getDisabled() != null && skuVO.getDisabled().intValue() != 1) {
                    goodsErrorList.add(errorMap);
                    continue;
                }

                //读取此产品的可用库存数量
                int enableQuantity = skuVO.getEnableQuantity();
                //此产品将要购买的数量
                int num = cartSkuVO.getNum();

                //如果将要购买的产品数量大于redis中的数量，则此产品不能下单
                if (num > enableQuantity) {
                    flag = false;
                    goodsErrorList.add(errorMap);
                    continue;
                }
            }
        }

        if (!goodsErrorList.isEmpty()) {
            throw new ServiceException(TradeErrorCode.E452.code(), "抱歉，您以下商品所在地区无货", JsonUtil.objectToJson(goodsErrorList));
        }

        return this;
    }


    @Override
    public TradeCreator checkPromotion() {
        Assert.notNull(memberClient, "memberClient为空，请先调用setMemberClient设置正确的会员业务Client");

        List<CartVO> cartList = cartView.getCartList();

        for (CartVO cartVO : cartList) {

            List<CartSkuVO> skuList = cartVO.getSkuList();

            for (CartSkuVO cartSkuVO : skuList) {
                innerCheckPromotion(cartSkuVO);

            }
        }


        //读取订单的总交易价格信息
        PriceDetailVO detailVO = cartView.getTotalPrice();

        //此交易需要扣除用户的积分
        int point = detailVO.getExchangePoint();

        if (point > 0) {
            Buyer buyer = UserContext.getBuyer();
            Member member = this.memberClient.getModel(buyer.getUid());
            int consumPoint = member.getConsumPoint();

            //如果用户可使用的消费积分小于 交易需要扣除的积分时，则不能下单
            if (consumPoint < point) {
                throw new ServiceException(TradeErrorCode.E452.code(), "您可使用的消费积分不足,请返回购物车修改商品");
            }
        }

        return this;
    }


    private void innerCheckPromotion(CartSkuVO cartSkuVO) {

        Map errorMap = new HashMap(16);
        errorMap.put("name", cartSkuVO.getName());
        errorMap.put("image", cartSkuVO.getGoodsImage());

        //验证后存在促销活动问题的集合
        List<Map> promotionErrorList = new ArrayList();
        boolean flag = true;
        //此商品参与的单品活动
        List<CartPromotionVo> singlePromotionList = cartSkuVO.getSingleList();
        if (!singlePromotionList.isEmpty()) {
            for (CartPromotionVo promotionGoodsVO : singlePromotionList) {

                // 默认参与的活动 && 非不参与活动的状态
                if (promotionGoodsVO.getIsCheck().intValue() == 1 && !promotionGoodsVO.getPromotionType().equals(PromotionTypeEnum.NO.name())) {
                    //当前活动的失效时间
                    long entTime = promotionGoodsVO.getEndTime();

                    //当前时间
                    long currTime = DateUtil.getDateline();

                    //如果当前时间大于失效时间，则此活动已经失效了，不能下单
                    if (currTime > entTime) {
                        flag = false;
                        promotionErrorList.add(errorMap);
                        continue;
                    }
                }

            }
        }

        //此商品参与的组合活动
        List<CartPromotionVo> groupPromotionList = cartSkuVO.getGroupList();
        if (!groupPromotionList.isEmpty()) {
            for (CartPromotionVo cartPromotionGoodsVo : groupPromotionList) {
                //当前活动的失效时间
                long entTime = cartPromotionGoodsVo.getEndTime();

                //当前时间
                long currTime = DateUtil.getDateline();

                //如果当前时间大于失效时间，则此活动已经失效了，不能下单
                if (currTime > entTime) {
                    flag = false;

                    promotionErrorList.add(errorMap);
                    continue;
                }
            }
        }
    }


}
