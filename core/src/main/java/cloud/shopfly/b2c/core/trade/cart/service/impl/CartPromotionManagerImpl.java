/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 购物车促销信息处理实现类
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
        //如果会员登陆了，则要以会员id为key
        Buyer buyer = UserContext.getBuyer();
        if (buyer != null) {
            cacheKey = CachePrefix.CART_PROMOTION_PREFIX.getPrefix() + buyer.getUid();
        }
        return cacheKey;
    }

    /**
     * 由缓存中读取出用户选择的促销信息
     *
     * @return 用户选择的促销信息
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
                //如果商品失效，
                if (skuVO.getInvalid() == 1) {
                    continue;
                }
                goodsIdStr.append(",");
                goodsIdStr.append(skuVO.getGoodsId());
            }
        }

        long now = DateUtil.getDateline();

        //查询所有正在进行的满减活动
        String sql = "select fd.*,pg.goods_id from es_full_discount fd left join es_promotion_goods pg on fd.fd_id = pg.activity_id  " +
                " where  fd.start_time <? and fd.end_time>? and  pg.goods_id in  (" + goodsIdStr + ") order by fd.fd_id asc";


        List<FullDiscountWithGoodsId> list = tradeDaoSupport.queryForList(sql, FullDiscountWithGoodsId.class, now, now);

        List<FullDiscountVO> fullDiscountVOList = new ArrayList<>();

        //上一个活动id，在变化时说明要生成新的vo
        Integer preFdId = null;
        FullDiscountVO fullDiscountVO = null;
        for (FullDiscountWithGoodsId fullDiscountWithGoodsId : list) {
            Integer fdid = fullDiscountWithGoodsId.getFdId();

            //需要生成新vo
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
        Assert.notNull(promotionType, "未知的促销类型");

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

            //单品立减活动
            if (PromotionTypeEnum.MINUS.equals(promotionType)) {
                MinusVO minusVO = this.minusManager.getFromDB(activityId);
                promotionVO.setMinusVO(minusVO);
                promotionVO.setActivityId(minusVO.getMinusId());
            }

            //第二件半价活动
            if (PromotionTypeEnum.HALF_PRICE.equals(promotionType)) {
                HalfPriceVO halfPriceVO = this.halfPriceManager.getFromDB(activityId);
                promotionVO.setHalfPriceVO(halfPriceVO);
                promotionVO.setActivityId(halfPriceVO.getHpId());
            }

            //限时抢购活动
            if (PromotionTypeEnum.SECKILL.equals(promotionType)) {
                GoodsSkuVO goodsSkuVO = goodsClient.getSkuFromCache(skuId);
                SeckillGoodsVO seckillGoodsVO = this.seckillManager.getSeckillGoods(goodsSkuVO.getGoodsId());
                promotionVO.setSeckillGoodsVO(seckillGoodsVO);
            }
            selectedPromotionVo.setPromotion(promotionVO);
            String cacheKey = this.getOriginKey();
            cache.put(cacheKey, selectedPromotionVo);
            if (logger.isDebugEnabled()){
                logger.debug("使用促销：" + promotionVO);
                logger.debug("促销信息为:" + selectedPromotionVo);
            }
        } catch (Exception e) {
            logger.error("使用促销出错", e);
            throw new ServiceException(TradeErrorCode.E462.code(), "使用促销出错");
        }


    }

    @Override
    public void useCoupon(Integer mcId, double totalPrice) {

        Buyer buyer = UserContext.getBuyer();
        MemberCoupon memberCoupon = this.memberCouponClient.getModel(buyer.getUid(), mcId);
        //如果优惠券Id为0并且优惠券为空则取消优惠券使用
        if (mcId.equals(0)) {
            this.cleanCoupon();
            return;
        }
        //如果优惠券为空则抛出异常
        if (memberCoupon == null) {
            throw new ServiceException(TradeErrorCode.E455.code(), "当前优惠券不存在");
        }
        //校验优惠券的限额
        if (totalPrice < memberCoupon.getCouponThresholdPrice()) {
            throw new ServiceException(TradeErrorCode.E455.code(), "未达到优惠券使用最低限额");
        }
        CouponVO couponVO = CartUtil.setCouponParam(memberCoupon);

        SelectedPromotionVo selectedPromotionVo = getSelectedPromotion();

        if (!CouponValidateUtil.validateCoupon(selectedPromotionVo)) {
            throw new ServiceException(TradeErrorCode.E455.code(), "您选择的商品包含积分兑换的商品不能使用优惠券！");
        }

        selectedPromotionVo.setCoupon( couponVO);
        if (logger.isDebugEnabled()){
            logger.debug("使用优惠券：" + couponVO);
            logger.debug("促销信息为:" + selectedPromotionVo);
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
     * 删除一组sku的促销，
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

        //如果新list是空的，表明这个店铺已经没有促销活动了，如果不为空则清除相关促销活动
        if (newList.isEmpty()) {

            selectedPromotionVo.setSinglePromotionList(null);

        }else{

            selectedPromotionVo.setSinglePromotionList(newList);
        }

        //重新压入缓存
        String cacheKey = this.getOriginKey();
        cache.put(cacheKey, selectedPromotionVo);

    }

    @Override
    public void clean() {
        String cacheKey = this.getOriginKey();
        cache.remove(cacheKey);
    }


    /**
     * 从促销活动列表中删除一批sku的活动
     *
     * @param skuids             skuid数组
     * @param skuPromotionVoList 要清理的活动列表
     * @return 清理后的活动列表
     */
    private List<PromotionVO> deleteBySkus(Integer[] skuids, List<PromotionVO> skuPromotionVoList) {
        List<PromotionVO> newList = new ArrayList<>();
        for (PromotionVO promotionVO : skuPromotionVoList) {
            //如果skuid数组中不包含，则不压入新list中
            if (!ArrayUtils.contains(skuids, promotionVO.getSkuId())) {
                newList.add(promotionVO);
            }
        }
        return newList;
    }


}
