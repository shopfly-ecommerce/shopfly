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
package cloud.shopfly.b2c.core.trade.cart.service.impl;

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.client.member.MemberCouponClient;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.member.model.dos.MemberCoupon;
import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;
import cloud.shopfly.b2c.core.promotion.exchange.service.ExchangeGoodsManager;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyActiveDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyGoodsDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyGoodsVO;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyActiveManager;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyGoodsManager;
import cloud.shopfly.b2c.core.promotion.halfprice.model.vo.HalfPriceVO;
import cloud.shopfly.b2c.core.promotion.halfprice.service.HalfPriceManager;
import cloud.shopfly.b2c.core.promotion.minus.model.vo.MinusVO;
import cloud.shopfly.b2c.core.promotion.minus.service.MinusManager;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillGoodsVO;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillManager;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.FullDiscountWithGoodsId;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.statistics.util.DateUtil;
import cloud.shopfly.b2c.core.trade.TradeErrorCode;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CouponVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.SelectedPromotionVo;
import cloud.shopfly.b2c.core.trade.cart.service.CartPromotionManager;
import cloud.shopfly.b2c.core.trade.cart.util.CartUtil;
import cloud.shopfly.b2c.core.trade.cart.util.CouponValidateUtil;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;

import cloud.shopfly.b2c.framework.security.model.Buyer;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Shopping cart promotion information processing implementation class
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/1
 */
@Service
public class CartPromotionManagerImpl implements CartPromotionManager {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private Cache cache;


    @Autowired
    private ExchangeGoodsManager exchangeGoodsManager;

    @Autowired
    private GroupbuyGoodsManager groupbuyGoodsManager;

    @Autowired
    private GroupbuyActiveManager groupbuyActiveManager;

    @Autowired
    private MinusManager minusManager;

    @Autowired
    private HalfPriceManager halfPriceManager;

    @Autowired
    private SeckillManager seckillManager;


    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private MemberCouponClient memberCouponClient;


    @Autowired
    
    private DaoSupport tradeDaoSupport;


    private String getOriginKey() {
        String cacheKey = "";
        // If the member logs in, the member ID is the key
        Buyer buyer = UserContext.getBuyer();
        if (buyer != null) {
            cacheKey = CachePrefix.CART_PROMOTION_PREFIX.getPrefix() + buyer.getUid();
        }
        return cacheKey;
    }

    /**
     * Read the user selected promotional information from the cache
     *
     * @return User-selected promotional messages
     */
    @Override
    public SelectedPromotionVo getSelectedPromotion() {
        String cacheKey = this.getOriginKey();
        SelectedPromotionVo selectedPromotionVo = (SelectedPromotionVo) cache.get(cacheKey);
        if (selectedPromotionVo == null) {
            selectedPromotionVo = new SelectedPromotionVo();
            cache.put(cacheKey, selectedPromotionVo);
        }

        return selectedPromotionVo;
    }

    @Override
    public List<FullDiscountVO> getFullDiscounPromotion(List<CartVO> cartList) {

        StringBuffer goodsIdStr = new StringBuffer("-1");

        for (CartVO cartVO : cartList) {
            List<CartSkuVO> skuList = cartVO.getSkuList();
            for (CartSkuVO skuVO : skuList) {
                // If the product fails,
                if (skuVO.getInvalid() == 1) {
                    continue;
                }
                goodsIdStr.append(",");
                goodsIdStr.append(skuVO.getGoodsId());
            }
        }

        long now = DateUtil.getDateline();

        // Query all active full reduction activities
        String sql = "select fd.*,pg.goods_id from es_full_discount fd left join es_promotion_goods pg on fd.fd_id = pg.activity_id  " +
                " where  fd.start_time <? and fd.end_time>? and  pg.goods_id in  (" + goodsIdStr + ") order by fd.fd_id asc";


        List<FullDiscountWithGoodsId> list = tradeDaoSupport.queryForList(sql, FullDiscountWithGoodsId.class, now, now);

        List<FullDiscountVO> fullDiscountVOList = new ArrayList<>();

        // The previous active ID, when changed, indicates that a new VO is to be generated
        Integer preFdId = null;
        FullDiscountVO fullDiscountVO = null;
        for (FullDiscountWithGoodsId fullDiscountWithGoodsId : list) {
            Integer fdid = fullDiscountWithGoodsId.getFdId();

            // A new VO needs to be generated
            if (!fdid.equals(preFdId)) {
                fullDiscountVO = new FullDiscountVO();
                BeanUtils.copyProperties(fullDiscountWithGoodsId, fullDiscountVO);
                fullDiscountVOList.add(fullDiscountVO);
                preFdId = fdid;

            }

            fullDiscountVO.getGoodsIdList().add(fullDiscountWithGoodsId.getGoodsId());

        }

        return fullDiscountVOList;

    }


    @Override
    public void usePromotion(Integer skuId, Integer activityId, PromotionTypeEnum promotionType) {
        Assert.notNull(promotionType, "Unknown type of promotion");

        try {

            SelectedPromotionVo selectedPromotionVo = this.getSelectedPromotion();

            PromotionVO promotionVO = new PromotionVO();
            promotionVO.setSkuId(skuId);
            promotionVO.setPromotionType(promotionType.name());

            if (PromotionTypeEnum.EXCHANGE.equals(promotionType)) {
                ExchangeDO exchangeDO = exchangeGoodsManager.getModel(activityId);
                promotionVO.setExchange(exchangeDO);
                promotionVO.setActivityId(exchangeDO.getExchangeId());
            }

            if (PromotionTypeEnum.GROUPBUY.equals(promotionType)) {
                GoodsSkuVO skuVO = goodsClient.getSkuFromCache(skuId);
                GroupbuyActiveDO activeDO = groupbuyActiveManager.getModel(activityId);
                GroupbuyGoodsDO groupbuyGoodsDO = groupbuyGoodsManager.getModel(activityId, skuVO.getGoodsId());
                GroupbuyGoodsVO groupbuyGoodsVO = new GroupbuyGoodsVO();
                BeanUtils.copyProperties(groupbuyGoodsDO, groupbuyGoodsVO);

                groupbuyGoodsVO.setStartTime(activeDO.getStartTime());
                groupbuyGoodsVO.setEndTime(activeDO.getEndTime());
                promotionVO.setGroupbuyGoodsVO(groupbuyGoodsVO);
                promotionVO.setActivityId(groupbuyGoodsVO.getActId());
            }

            // Single product reduction activity
            if (PromotionTypeEnum.MINUS.equals(promotionType)) {
                MinusVO minusVO = this.minusManager.getFromDB(activityId);
                promotionVO.setMinusVO(minusVO);
                promotionVO.setActivityId(minusVO.getMinusId());
            }

            // The second half price event
            if (PromotionTypeEnum.HALF_PRICE.equals(promotionType)) {
                HalfPriceVO halfPriceVO = this.halfPriceManager.getFromDB(activityId);
                promotionVO.setHalfPriceVO(halfPriceVO);
                promotionVO.setActivityId(halfPriceVO.getHpId());
            }

            // Flash sales
            if (PromotionTypeEnum.SECKILL.equals(promotionType)) {
                GoodsSkuVO goodsSkuVO = goodsClient.getSkuFromCache(skuId);
                SeckillGoodsVO seckillGoodsVO = this.seckillManager.getSeckillGoods(goodsSkuVO.getGoodsId());
                promotionVO.setSeckillGoodsVO(seckillGoodsVO);
            }
            selectedPromotionVo.setPromotion(promotionVO);
            String cacheKey = this.getOriginKey();
            cache.put(cacheKey, selectedPromotionVo);
            if (logger.isDebugEnabled()){
                logger.debug("Use of sales promotion：" + promotionVO);
                logger.debug("The promotional information is:" + selectedPromotionVo);
            }
        } catch (Exception e) {
            logger.error("Error in using promotions", e);
            throw new ServiceException(TradeErrorCode.E462.code(), "Error in using promotions");
        }


    }

    @Override
    public void useCoupon(Integer mcId, double totalPrice) {

        Buyer buyer = UserContext.getBuyer();
        MemberCoupon memberCoupon = this.memberCouponClient.getModel(buyer.getUid(), mcId);
        // If the coupon Id is 0 and the coupon is empty, cancel the coupon
        if (mcId.equals(0)) {
            this.cleanCoupon();
            return;
        }
        // If the coupon is empty, an exception is thrown
        if (memberCoupon == null) {
            throw new ServiceException(TradeErrorCode.E455.code(), "Current coupon does not exist");
        }
        // Check coupon limits
        if (totalPrice < memberCoupon.getCouponThresholdPrice()) {
            throw new ServiceException(TradeErrorCode.E455.code(), "The minimum coupon limit has not been reached");
        }
        CouponVO couponVO = CartUtil.setCouponParam(memberCoupon);

        SelectedPromotionVo selectedPromotionVo = getSelectedPromotion();

        if (!CouponValidateUtil.validateCoupon(selectedPromotionVo)) {
            throw new ServiceException(TradeErrorCode.E455.code(), "You cant use coupons for items that include points for redemption！");
        }

        selectedPromotionVo.setCoupon( couponVO);
        if (logger.isDebugEnabled()){
            logger.debug("Use coupons：" + couponVO);
            logger.debug("The promotional information is:" + selectedPromotionVo);
        }
        String cacheKey = this.getOriginKey();
        cache.put(cacheKey, selectedPromotionVo);
    }

    @Override
    public void cleanCoupon() {
        SelectedPromotionVo selectedPromotionVo = getSelectedPromotion();
        selectedPromotionVo.setCoupon(null);
        String cacheKey = this.getOriginKey();
        cache.put(cacheKey, selectedPromotionVo);
    }


    /**
     * To delete a set ofskuThe promotion,
     *
     * @param skuIds
     */
    @Override
    public void delete(Integer[] skuIds) {
        SelectedPromotionVo selectedPromotionVo = this.getSelectedPromotion();
        List<PromotionVO> promotionList = selectedPromotionVo.getSinglePromotionList();


        if (promotionList == null) {
            return;
        }

        List<PromotionVO> newList = deleteBySkus(skuIds, promotionList);

        // If the new list is empty, it indicates that the store has no promotional activities. If it is not empty, relevant promotional activities will be cleared
        if (newList.isEmpty()) {

            selectedPromotionVo.setSinglePromotionList(null);

        }else{

            selectedPromotionVo.setSinglePromotionList(newList);
        }

        // Reload the cache
        String cacheKey = this.getOriginKey();
        cache.put(cacheKey, selectedPromotionVo);

    }

    @Override
    public void clean() {
        String cacheKey = this.getOriginKey();
        cache.remove(cacheKey);
    }


    /**
     * Delete a batch from the promotional listskuThe activities of the
     *
     * @param skuids             skuidAn array of
     * @param skuPromotionVoList List of activities to clean up
     * @return A list of cleaned activities
     */
    private List<PromotionVO> deleteBySkus(Integer[] skuids, List<PromotionVO> skuPromotionVoList) {
        List<PromotionVO> newList = new ArrayList<>();
        for (PromotionVO promotionVO : skuPromotionVoList) {
            // If it is not in the SKUID array, it is not pressed into the new list
            if (!ArrayUtils.contains(skuids, promotionVO.getSkuId())) {
                newList.add(promotionVO);
            }
        }
        return newList;
    }


}
