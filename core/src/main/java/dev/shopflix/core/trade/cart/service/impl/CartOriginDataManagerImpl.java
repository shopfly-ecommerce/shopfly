/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.service.impl;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.client.goods.GoodsClient;
import dev.shopflix.core.goods.model.vo.GoodsSkuVO;
import dev.shopflix.core.promotion.tool.model.dos.PromotionGoodsDO;
import dev.shopflix.core.promotion.tool.model.enums.PromotionTypeEnum;
import dev.shopflix.core.promotion.tool.model.vo.PromotionVO;
import dev.shopflix.core.trade.TradeErrorCode;
import dev.shopflix.core.trade.cart.model.vo.CartPromotionVo;
import dev.shopflix.core.trade.cart.model.vo.CartSkuOriginVo;
import dev.shopflix.core.trade.cart.service.CartOriginDataManager;
import dev.shopflix.core.trade.cart.service.CartPromotionManager;
import dev.shopflix.core.trade.converter.TradePromotionConverter;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.security.model.Buyer;
import dev.shopflix.framework.util.DateUtil;
import org.apache.commons.lang.ArrayUtils;
import dev.shopflix.framework.logs.Logger;
import dev.shopflix.framework.logs.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 购物车原始数据业务类实现<br>
 * 文档请参考：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html" >购物车架构</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/11
 */
@Service
public class CartOriginDataManagerImpl implements CartOriginDataManager {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Cache cache;

    @Autowired
    private GoodsClient goodsClient;


    @Autowired

    private DaoSupport daoSupport;

    @Autowired
    private CartPromotionManager cartPromotionManager;

    /**
     * 由缓存中读取购物原始数据
     *
     * @return
     */
    @Override
    public List<CartSkuOriginVo> read() {

        String originKey = this.getOriginKey();
        List<CartSkuOriginVo> originList = (List<CartSkuOriginVo>) cache.get(originKey);
        if (originList == null) {
            return new ArrayList<>();
        }
        return originList;
    }

    @Override
    public CartSkuOriginVo add(int skuId, int num, Integer activityId) {
        GoodsSkuVO sku = this.goodsClient.getSkuFromCache(skuId);
        if (sku == null) {
            throw new ServiceException(TradeErrorCode.E451.code(), "商品已失效，请刷新购物车");
        }

        //读取sku的可用库存
        Integer enableQuantity = sku.getEnableQuantity();

        //如果sku的可用库存小于等于0或者小于用户购买的数量，则不允许购买
        if (enableQuantity <= 0 || enableQuantity < num) {
            throw new ServiceException(TradeErrorCode.E451.code(), "商品库存已不足，不能购买。");
        }
        List<CartSkuOriginVo> originList = this.read();

        //先看看购物车中是否存在此sku
        CartSkuOriginVo skuVo = this.findSku(skuId, originList);

        //购物车中已经存在，试着更新数量
        if (skuVo != null && sku.getLastModify().equals(skuVo.getLastModify())) {

            //判断是商品否被修改
            int oldNum = skuVo.getNum();
            int newNum = oldNum + num;

            //增加数量的话库存已经不足，则最多为可用库存
            if (newNum > enableQuantity) {
                newNum = enableQuantity;
            }
            skuVo.setNum(newNum);

        } else {
            //先清理一下 如果商品无效的话
            originList.remove(skuVo);
            //购物车中不存在此商品，则新建立一个
            skuVo = new CartSkuOriginVo();
            //将相同的属性copy
            BeanUtils.copyProperties(sku, skuVo);
            //再设置加入购物车的数量
            skuVo.setNum(num);
            originList.add(skuVo);
        }


        //新加入的商品都是选中的
        skuVo.setChecked(1);

        //填充可用的促销数据
        this.fillPromotion(skuVo, activityId);


        //重新压入缓存
        String originKey = this.getOriginKey();
        cache.put(originKey, originList);

        return skuVo;
    }

    @Override
    public void buy(Integer skuId, Integer num, Integer activityId) {
        delete(new Integer[]{skuId});
        checkedAll(0);
        add(skuId, num, activityId);
    }


    @Override
    public CartSkuOriginVo updateNum(int skuId, int num) {

        Assert.notNull(skuId, "参数skuId不能为空");
        Assert.notNull(num, "参数num不能为空");

        GoodsSkuVO sku = this.goodsClient.getSkuFromCache(skuId);
        if (sku == null) {
            throw new ServiceException(TradeErrorCode.E451.code(), "商品已失效，请刷新购物车");
        }

        //读取sku的可用库存
        Integer enableQuantity = sku.getEnableQuantity();
        List<CartSkuOriginVo> originList = this.read();

        //先看看购物车中是否存在此sku
        CartSkuOriginVo skuVo = this.findSku(skuId, originList);

        if (skuVo != null) {
            //话库存已经不足
            if (num > enableQuantity) {
                throw new ServiceException(TradeErrorCode.E451.code(), "此商品已经超出库存，库存为[" + enableQuantity + "]");
            } else {
                skuVo.setNum(num);
            }
        }

        String originKey = this.getOriginKey();

        cache.put(originKey, originList);

        return skuVo;

    }

    @Override
    public CartSkuOriginVo checked(int skuId, int checked) {
        //不合法的参数，忽略掉
        if (checked != 1 && checked != 0) {
            return new CartSkuOriginVo();
        }


        Assert.notNull(skuId, "参数skuId不能为空");
        String originKey = this.getOriginKey();

        //这是本次要返回的sku
        AtomicReference<CartSkuOriginVo> skuOriginVo = new AtomicReference<>();
        List<CartSkuOriginVo> originList = this.read();
        originList.forEach(cartSku -> {
            if (cartSku.getSkuId() == skuId) {
                cartSku.setChecked(checked);
                skuOriginVo.set(cartSku);
                return;
            }
        });

        cache.put(originKey, originList);

        return skuOriginVo.get();
    }

    @Override
    public void checkedSeller(int sellerId, int checked) {
        //不合法的参数，忽略掉
        if (checked != 1 && checked != 0) {
            return;
        }

        String originKey = this.getOriginKey();

        List<CartSkuOriginVo> originList = this.read();
        originList.forEach(cartSku -> {
            cartSku.setChecked(checked);
        });

        cache.put(originKey, originList);

    }

    @Override
    public void checkedAll(int checked) {
        //不合法的参数，忽略掉
        if (checked != 1 && checked != 0) {
            return;
        }

        String originKey = this.getOriginKey();

        //这是本次要返回的sku
        List<CartSkuOriginVo> originList = this.read();
        originList.forEach(cartSku -> {
            cartSku.setChecked(checked);
        });

        cache.put(originKey, originList);

    }


    @Override
    public void delete(Integer[] skuIds) {
        Assert.notNull(skuIds, "参数skuIds不能为空");
        String originKey = this.getOriginKey();

        //删除相关促销活动
        cartPromotionManager.delete(skuIds);
        //这是本次要返回的sku
        List<CartSkuOriginVo> originList = this.read();
        List<CartSkuOriginVo> newList = new ArrayList<>();
        originList.forEach(cartSku -> {
            System.out.println(skuIds[0]);
            //查找是否要删除
            //如果不删除压入到新的list中
            if (!ArrayUtils.contains(skuIds, cartSku.getSkuId())) {
                newList.add(cartSku);
            }

        });

        cache.put(originKey, newList);

    }

    @Override
    public void clean() {
        //清空此用户所有选择的促销活动
        cartPromotionManager.clean();
        String originKey = this.getOriginKey();
        cache.remove(originKey);
    }

    @Override
    public void cleanChecked() {
        String originKey = this.getOriginKey();

        //这是本次要返回的sku
        List<CartSkuOriginVo> originList = this.read();
        List<CartSkuOriginVo> newList = new ArrayList<>();
        List<Integer> skuIds = new ArrayList<>();
        for (CartSkuOriginVo cartSku : originList) {
            GoodsSkuVO fromCache = goodsClient.getSkuFromCache(cartSku.getSkuId());
            //如果是选中的则要删除（也就是未选中的才压入list）
            if (cartSku.getChecked() == 0) {
                newList.add(cartSku);
            } else if (fromCache == null || fromCache.getLastModify() > cartSku.getLastModify()) {
                //设置购物车未选中
                cartSku.setChecked(0);
                newList.add(cartSku);
            } else {
                skuIds.add(cartSku.getSkuId());
            }
        }

        //清除相关sku的优惠活动
        cartPromotionManager.delete(skuIds.toArray(new Integer[skuIds.size()]));

        //清除用户选择的所有的优惠券
        cartPromotionManager.cleanCoupon();
        cache.put(originKey, newList);

    }


    /**
     * 读取当前会员购物车原始数据key
     *
     * @return
     */
    protected String getOriginKey() {

        String cacheKey = "";
        //如果会员登陆了，则要以会员id为key
        Buyer buyer = UserContext.getBuyer();
        if (buyer != null) {
            cacheKey = CachePrefix.CART_ORIGIN_DATA_PREFIX.getPrefix() + buyer.getUid();
        }

        return cacheKey;
    }

    /**
     * 根据skuid 查找某个sku
     *
     * @param skuId      要查找的skuid
     * @param originList sku list
     * @return 找到返回sku，找不到返回Null
     */
    private CartSkuOriginVo findSku(int skuId, List<CartSkuOriginVo> originList) {
        for (CartSkuOriginVo cartSkuOriginVo : originList) {
            if (cartSkuOriginVo.getSkuId().equals(skuId)) {
                return cartSkuOriginVo;
            }
        }
        return null;

    }


    /**
     * 为某个cart sku 填充促销信息
     *
     * @param cartSkuVo
     */
    private void fillPromotion(CartSkuOriginVo cartSkuVo, Integer activityId) {

        //找到当前要使用活动的类型
        PromotionTypeEnum usedType = null;

        //单品活动集合^
        List<CartPromotionVo> singleList = new ArrayList<>();

        //组合活动集合
        List<CartPromotionVo> groupList = new ArrayList<>();

        List<PromotionGoodsDO> doList = this.getGoodsEnablePromotion(cartSkuVo.getGoodsId());
        for (PromotionGoodsDO promotionGoodsDO : doList) {


            PromotionVO promotionVO = new PromotionVO();
            BeanUtils.copyProperties(promotionGoodsDO, promotionVO);
            CartPromotionVo promotionGoodsVO = TradePromotionConverter.promotionGoodsVOConverter(promotionVO);
            //目前只有满减是组合活动
            if (PromotionTypeEnum.FULL_DISCOUNT.name().equals(promotionVO.getPromotionType())) {
                groupList.add(promotionGoodsVO);
            } else {
                //找到要参加的活动
                if (promotionGoodsDO.getActivityId().equals(activityId)) {
                    usedType = PromotionTypeEnum.myValueOf(promotionGoodsDO.getPromotionType());
                }
                singleList.add(promotionGoodsVO);
            }
        }

        cartSkuVo.setGroupList(groupList);
        cartSkuVo.setSingleList(singleList);


        //如果没有指定使用的活动，试着使用第一个
        if (activityId == null && doList.size() > 0) {
            PromotionGoodsDO promotionGoodsDO = doList.get(0);
            activityId = promotionGoodsDO.getActivityId();
            usedType = PromotionTypeEnum.myValueOf(promotionGoodsDO.getPromotionType());
        }

        if (usedType != null && activityId != null) {
            //使用要使用的活动
            cartPromotionManager.usePromotion(cartSkuVo.getSkuId(), activityId, usedType);
        }


    }


    /**
     * 读取某个商品参与的所有活动
     *
     * @param goodsId
     * @return
     */
    private List<PromotionGoodsDO> getGoodsEnablePromotion(Integer goodsId) {

        long currTime = DateUtil.getDateline();

        //读取此商品参加的活动
        String sql = "select   goods_id, start_time, end_time, activity_id, promotion_type,title,num,price " +
                "from es_promotion_goods where ( goods_id=? or goods_id=-1)  and start_time<=? and end_time>=? ";
        List<PromotionGoodsDO> resultList = this.daoSupport.queryForList(sql, PromotionGoodsDO.class, goodsId, currTime, currTime);

        return resultList;
    }


}
