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
package cloud.shopfly.b2c.core.promotion.tool.service.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.promotion.PromotionErrorCode;
import cloud.shopfly.b2c.core.promotion.tool.model.dos.PromotionGoodsDO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionDetailDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.promotion.tool.service.PromotionGoodsManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.coupon.service.CouponManager;
import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;
import cloud.shopfly.b2c.core.promotion.exchange.service.ExchangeGoodsManager;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.service.FullDiscountGiftManager;
import cloud.shopfly.b2c.core.promotion.fulldiscount.service.FullDiscountManager;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyGoodsDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyGoodsVO;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyGoodsManager;
import cloud.shopfly.b2c.core.promotion.halfprice.model.vo.HalfPriceVO;
import cloud.shopfly.b2c.core.promotion.halfprice.service.HalfPriceManager;
import cloud.shopfly.b2c.core.promotion.minus.model.vo.MinusVO;
import cloud.shopfly.b2c.core.promotion.minus.service.MinusManager;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillGoodsVO;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillManager;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity goods comparison implementation class
 *
 * @author Snow create in 2018/3/21
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class PromotionGoodsManagerImpl implements PromotionGoodsManager {

    @Autowired

    private DaoSupport daoSupport;
    @Autowired
    private ExchangeGoodsManager exchangeGoodsManager;

    @Autowired
    private GroupbuyGoodsManager groupbuyGoodsManager;

    @Autowired
    private FullDiscountManager fullDiscountManager;

    @Autowired
    private MinusManager minusManager;

    @Autowired
    private HalfPriceManager halfPriceManager;

    @Autowired
    private SeckillManager seckillManager;

    @Autowired
    private FullDiscountGiftManager fullDiscountGiftManager;

    @Autowired
    private CouponManager couponManager;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private Cache cache;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class})
    public void add(List<PromotionGoodsDTO> list, PromotionDetailDTO detailDTO) {

        if (list.isEmpty()) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "No merchandise is available to participate in this activity");
        }

        /**
         * becauseSpring jdbcTemplate No method for bulk inserts is provided,
         * useinsert into table_name (xxx1,xxx2) VALUES (1,2),(3,3) Concatenation parameters, will havesqlInjection problem.
         * Therefore, after communication with the architect, this operation is not a high-frequency operation and can be inserted into the database one by one.
         */
        for (PromotionGoodsDTO goodsDTO : list) {
            this.addModel(goodsDTO.getGoodsId(),detailDTO);
        }

    }

    @Override
    public PromotionGoodsDO addModel(Integer goodsId, PromotionDetailDTO detailDTO) {

        PromotionGoodsDO goodsDO = new PromotionGoodsDO();
        goodsDO.setGoodsId(goodsId);
        goodsDO.setStartTime(detailDTO.getStartTime());
        goodsDO.setEndTime(detailDTO.getEndTime());
        goodsDO.setActivityId(detailDTO.getActivityId());
        goodsDO.setPromotionType(detailDTO.getPromotionType());
        goodsDO.setTitle(detailDTO.getTitle());
        this.daoSupport.insert(goodsDO);

        return goodsDO;
    }

    @Override
    public List<PromotionGoodsDO> getPromotionGoods(Integer activityId, String promotionType) {
        String sql = "select * from es_promotion_goods where activity_id=? and promotion_type=?";
        List<PromotionGoodsDO> goodsDOList = this.daoSupport.queryForList(sql, PromotionGoodsDO.class, activityId, promotionType);
        return goodsDOList;
    }

    @Override
    public void edit(List<PromotionGoodsDTO> list, PromotionDetailDTO detailDTO) {
        this.delete(detailDTO.getActivityId(), detailDTO.getPromotionType());
        this.add(list, detailDTO);
    }

    @Override
    public void delete(Integer activityId, String promotionType) {
        String sql = "DELETE FROM es_promotion_goods WHERE activity_id=? and promotion_type= ? ";
        this.daoSupport.execute(sql, activityId, promotionType);
    }

    @Override
    public void delete(Integer goodsId, Integer activityId, String promotionType) {
        String sql = "DELETE FROM es_promotion_goods WHERE activity_id=? and promotion_type= ? and goods_id = ?";
        this.daoSupport.execute(sql, activityId, promotionType, goodsId);
    }

    @Override
    public void delete(Integer goodsId) {
        String sql = "DELETE FROM es_promotion_goods WHERE goods_id = ? and end_time >= ? and promotion_type != ?";
        this.daoSupport.execute(sql,  goodsId,DateUtil.getDateline(), PromotionTypeEnum.EXCHANGE.name());
    }

    @Override
    public List<PromotionVO> getPromotion(Integer goodsId) {

        long currTime = DateUtil.getDateline();

        String currDate = DateUtil.toString(currTime, "yyyyMMdd");
        // Use keyword + current date +goods_id as keyword
        String promotionKey = CachePrefix.PROMOTION_KEY.getPrefix() + currDate + goodsId;
        // Read item activity information from the cache
        List<PromotionVO> promotionVOList = (List<PromotionVO>) cache.get(promotionKey);

        if (promotionVOList == null || promotionVOList.isEmpty()) {
            promotionVOList = new ArrayList<>();


            // Read the activity that this commodity participates in
            String sql = "select distinct goods_id, start_time, end_time, activity_id, promotion_type,title,num,price " +
                    "from es_promotion_goods where goods_id=? and start_time<=? and end_time>=?";
            List<PromotionGoodsDO> resultList = this.daoSupport.queryForList(sql, PromotionGoodsDO.class, goodsId, currTime, currTime);

            // Query the activities that all commodities participate in under the following conditions:
            // The product ID is greater than or equal to the start time && less than or equal to the end time && is not a group purchase event && is not a flash sale event && Merchant ID
            Integer totalGoodsId = -1;
            sql = "select distinct goods_id, start_time, end_time, activity_id, promotion_type,title,num,price " +
                    " from es_promotion_goods where goods_id=? and start_time<=? and end_time>=? " +
                    " and promotion_type != '" + PromotionTypeEnum.GROUPBUY.name() + "' " +
                    " and promotion_type != '" + PromotionTypeEnum.SECKILL.name()+ "' ";

            CacheGoods cacheGoods = this.goodsClient.getFromCache(goodsId);
            List<PromotionGoodsDO> promotionGoodsDOList = this.daoSupport.queryForList(sql, PromotionGoodsDO.class, totalGoodsId, currTime, currTime);
            resultList.addAll(promotionGoodsDOList);

            for (PromotionGoodsDO promotionGoodsDO : resultList) {
                PromotionVO promotionVO = new PromotionVO();
                BeanUtils.copyProperties(promotionGoodsDO, promotionVO);

                // Integral for
                if (promotionGoodsDO.getPromotionType().equals(PromotionTypeEnum.EXCHANGE.name())) {
                    ExchangeDO exchange = exchangeGoodsManager.getModel(promotionGoodsDO.getActivityId());
                    promotionVO.setExchange(exchange);
                }

                // A bulk
                if (promotionGoodsDO.getPromotionType().equals(PromotionTypeEnum.GROUPBUY.name())) {
                    GroupbuyGoodsDO groupbuyGoodsDO = groupbuyGoodsManager.getModel(promotionGoodsDO.getActivityId(), promotionGoodsDO.getGoodsId());

                    GroupbuyGoodsVO groupbuyGoodsVO = new GroupbuyGoodsVO();
                    BeanUtils.copyProperties(groupbuyGoodsDO, groupbuyGoodsVO);

                    promotionVO.setGroupbuyGoodsVO(groupbuyGoodsVO);
                }

                // Full discount
                if (promotionGoodsDO.getPromotionType().equals(PromotionTypeEnum.FULL_DISCOUNT.name())) {
                    FullDiscountVO fullDiscountVO = this.fullDiscountManager.getModel(promotionGoodsDO.getActivityId());

                    // The gifts
                    if (fullDiscountVO.getIsSendGift() != null && fullDiscountVO.getIsSendGift() == 1 && fullDiscountVO.getGiftId() != null) {
                        FullDiscountGiftDO giftDO = fullDiscountGiftManager.getModel(fullDiscountVO.getGiftId());
                        fullDiscountVO.setFullDiscountGiftDO(giftDO);
                    }

                    // Determine whether gift coupons are available
                    if (fullDiscountVO.getIsSendBonus() != null && fullDiscountVO.getIsSendBonus() == 1 && fullDiscountVO.getBonusId() != null) {
                        CouponDO couponDO = this.couponManager.getModel(fullDiscountVO.getBonusId());
                        fullDiscountVO.setCouponDO(couponDO);
                    }

                    // Determine whether gift coupons are available
                    if (fullDiscountVO.getIsSendPoint() != null && fullDiscountVO.getIsSendPoint() == 1 && fullDiscountVO.getPointValue() != null) {
                        fullDiscountVO.setPoint(fullDiscountVO.getPointValue());
                    }


                    promotionVO.setFullDiscountVO(fullDiscountVO);
                }

                // Single product reduction activity
                if (promotionGoodsDO.getPromotionType().equals(PromotionTypeEnum.MINUS.name())) {
                    MinusVO minusVO = this.minusManager.getFromDB(promotionGoodsDO.getActivityId());
                    promotionVO.setMinusVO(minusVO);
                }

                // The second half price event
                if (promotionGoodsDO.getPromotionType().equals(PromotionTypeEnum.HALF_PRICE.name())) {
                    HalfPriceVO halfPriceVO = this.halfPriceManager.getFromDB(promotionGoodsDO.getActivityId());
                    promotionVO.setHalfPriceVO(halfPriceVO);
                }
                // Flash sales
                if (promotionGoodsDO.getPromotionType().equals(PromotionTypeEnum.SECKILL.name())) {
                    SeckillGoodsVO seckillGoodsVO = this.seckillManager.getSeckillGoods(promotionGoodsDO.getGoodsId());
                    promotionVO.setSeckillGoodsVO(seckillGoodsVO);
                }

                promotionVOList.add(promotionVO);
            }

        } else {


            for (PromotionVO promotionVO : promotionVOList) {

                // The current time is greater than the start time of the activity && the current time is less than the end time of the activity
                if (currTime > promotionVO.getStartTime() && currTime < promotionVO.getEndTime()) {

                    // Initializes flash sale data to prevent returned timestamp errors
                    promotionVO.setSeckillGoodsVO(null);

                    // Flash sale activities need to recalculate the time stamp from the end back to the front end
                    if (promotionVO.getPromotionType().equals(PromotionTypeEnum.SECKILL.name())) {
                        SeckillGoodsVO seckillGoodsVO = this.seckillManager.getSeckillGoods(promotionVO.getGoodsId());
                        promotionVO.setSeckillGoodsVO(seckillGoodsVO);
                    }
                    promotionVOList.add(promotionVO);
                }
            }

        }

        return promotionVOList;
    }

    @Override
    public void cleanCache(Integer goodsId) {
        long currTime = DateUtil.getDateline();
        String currDate = DateUtil.toString(currTime, "yyyyMMdd");
        // Use keyword + current date +goods_id as keyword
        String promotionKey = CachePrefix.PROMOTION_KEY.getPrefix() + currDate + goodsId;
        this.cache.remove(promotionKey);

    }

    /**
     * Rewrite cache
     *
     * @param goodsId
     */
    @Override
    public void reputCache(Integer goodsId) {
        this.cleanCache(goodsId);
        this.getPromotion(goodsId);
    }

}
