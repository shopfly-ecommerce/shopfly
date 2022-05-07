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
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.promotion.tool.model.dos.PromotionGoodsDO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.TradeErrorCode;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartPromotionVo;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuOriginVo;
import cloud.shopfly.b2c.core.trade.converter.TradePromotionConverter;
import cloud.shopfly.b2c.core.trade.cart.service.CartOriginDataManager;
import cloud.shopfly.b2c.core.trade.cart.service.CartPromotionManager;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.apache.commons.lang.ArrayUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Shopping cart raw data business class implementation<br>
 * Please refer to the documentation.：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html" >Shopping cart architecture</a>
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
     * Read the raw shopping data from the cache
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
            throw new ServiceException(TradeErrorCode.E451.code(), "Item no longer valid, please refresh cart");
        }

        // Read the available inventory of the SKU
        Integer enableQuantity = sku.getEnableQuantity();

        // If the available stock of the SKU is less than or equal to zero or less than the amount purchased by the user, purchases are not allowed
        if (enableQuantity <= 0 || enableQuantity < num) {
            throw new ServiceException(TradeErrorCode.E451.code(), "The goods are out of stock and cannot be purchased.");
        }
        List<CartSkuOriginVo> originList = this.read();

        // First check to see if the SKU exists in the shopping cart
        CartSkuOriginVo skuVo = this.findSku(skuId, originList);

        // Cart already exists, try to update the quantity
        if (skuVo != null && sku.getLastModify().equals(skuVo.getLastModify())) {

            // Determine whether the item has been modified
            int oldNum = skuVo.getNum();
            int newNum = oldNum + num;

            // If the quantity is increased, the inventory is insufficient, and at most, the available inventory
            if (newNum > enableQuantity) {
                newNum = enableQuantity;
            }
            skuVo.setNum(newNum);

        } else {
            // Clean up first if the goods are invalid
            originList.remove(skuVo);
            // If this item does not exist in the shopping cart, create a new one
            skuVo = new CartSkuOriginVo();
            // Copy the same attributes
            BeanUtils.copyProperties(sku, skuVo);
            // Then set the number to add to the shopping cart
            skuVo.setNum(num);
            originList.add(skuVo);
        }


        // New items are selected
        skuVo.setChecked(1);

        // Populate available promotional data
        this.fillPromotion(skuVo, activityId);


        // Reload the cache
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

        Assert.notNull(skuId, "parameterskuIdCant be empty");
        Assert.notNull(num, "parameternumCant be empty");

        GoodsSkuVO sku = this.goodsClient.getSkuFromCache(skuId);
        if (sku == null) {
            throw new ServiceException(TradeErrorCode.E451.code(), "Item no longer valid, please refresh cart");
        }

        // Read the available inventory of the SKU
        Integer enableQuantity = sku.getEnableQuantity();
        List<CartSkuOriginVo> originList = this.read();

        // First check to see if the SKU exists in the shopping cart
        CartSkuOriginVo skuVo = this.findSku(skuId, originList);

        if (skuVo != null) {
            // Telephone stock is running low
            if (num > enableQuantity) {
                throw new ServiceException(TradeErrorCode.E451.code(), "This item is out of stock, stock is[" + enableQuantity + "]");
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
        // Invalid arguments are ignored
        if (checked != 1 && checked != 0) {
            return new CartSkuOriginVo();
        }


        Assert.notNull(skuId, "parameterskuIdCant be empty");
        String originKey = this.getOriginKey();

        // This is the SKU to return this time
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
        // Invalid arguments are ignored
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
        // Invalid arguments are ignored
        if (checked != 1 && checked != 0) {
            return;
        }

        String originKey = this.getOriginKey();

        // This is the SKU to return this time
        List<CartSkuOriginVo> originList = this.read();
        originList.forEach(cartSku -> {
            cartSku.setChecked(checked);
        });

        cache.put(originKey, originList);

    }


    @Override
    public void delete(Integer[] skuIds) {
        Assert.notNull(skuIds, "parameterskuIdsCant be empty");
        String originKey = this.getOriginKey();

        // Delete related promotions
        cartPromotionManager.delete(skuIds);
        // This is the SKU to return this time
        List<CartSkuOriginVo> originList = this.read();
        List<CartSkuOriginVo> newList = new ArrayList<>();
        originList.forEach(cartSku -> {
            System.out.println(skuIds[0]);
            // Find whether to delete
            // If you dont delete it, press it into the new list
            if (!ArrayUtils.contains(skuIds, cartSku.getSkuId())) {
                newList.add(cartSku);
            }

        });

        cache.put(originKey, newList);

    }

    @Override
    public void clean() {
        // Clear all selected promotions for this user
        cartPromotionManager.clean();
        String originKey = this.getOriginKey();
        cache.remove(originKey);
    }

    @Override
    public void cleanChecked() {
        String originKey = this.getOriginKey();

        // This is the SKU to return this time
        List<CartSkuOriginVo> originList = this.read();
        List<CartSkuOriginVo> newList = new ArrayList<>();
        List<Integer> skuIds = new ArrayList<>();
        for (CartSkuOriginVo cartSku : originList) {
            GoodsSkuVO fromCache = goodsClient.getSkuFromCache(cartSku.getSkuId());
            // If it is selected, it will be deleted.
            if (cartSku.getChecked() == 0) {
                newList.add(cartSku);
            } else if (fromCache == null || fromCache.getLastModify() > cartSku.getLastModify()) {
                // Set shopping cart is not selected
                cartSku.setChecked(0);
                newList.add(cartSku);
            } else {
                skuIds.add(cartSku.getSkuId());
            }
        }

        // Clear the relevant SKUs promotional activities
        cartPromotionManager.delete(skuIds.toArray(new Integer[skuIds.size()]));

        // Clear all coupons selected by the user
        cartPromotionManager.cleanCoupon();
        cache.put(originKey, newList);

    }


    /**
     * Read the current member shopping cart raw datakey
     *
     * @return
     */
    protected String getOriginKey() {

        String cacheKey = "";
        // If the member logs in, the member ID is the key
        Buyer buyer = UserContext.getBuyer();
        if (buyer != null) {
            cacheKey = CachePrefix.CART_ORIGIN_DATA_PREFIX.getPrefix() + buyer.getUid();
        }

        return cacheKey;
    }

    /**
     * According to theskuid To find asku
     *
     * @param skuId      Youre looking forskuid
     * @param originList sku list
     * @return Find backskuUnable to find returnNull
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
     * For acart sku Fill in promotional information
     *
     * @param cartSkuVo
     */
    private void fillPromotion(CartSkuOriginVo cartSkuVo, Integer activityId) {

        // Find the current type of activity to use
        PromotionTypeEnum usedType = null;

        // Single product activity collection ^
        List<CartPromotionVo> singleList = new ArrayList<>();

        // Combined activity set
        List<CartPromotionVo> groupList = new ArrayList<>();

        List<PromotionGoodsDO> doList = this.getGoodsEnablePromotion(cartSkuVo.getGoodsId());
        for (PromotionGoodsDO promotionGoodsDO : doList) {


            PromotionVO promotionVO = new PromotionVO();
            BeanUtils.copyProperties(promotionGoodsDO, promotionVO);
            CartPromotionVo promotionGoodsVO = TradePromotionConverter.promotionGoodsVOConverter(promotionVO);
            // Currently only full minus is a combination activity
            if (PromotionTypeEnum.FULL_DISCOUNT.name().equals(promotionVO.getPromotionType())) {
                groupList.add(promotionGoodsVO);
            } else {
                // Find activities to attend
                if (promotionGoodsDO.getActivityId().equals(activityId)) {
                    usedType = PromotionTypeEnum.myValueOf(promotionGoodsDO.getPromotionType());
                }
                singleList.add(promotionGoodsVO);
            }
        }

        cartSkuVo.setGroupList(groupList);
        cartSkuVo.setSingleList(singleList);


        // If no activity is specified to use, try using the first one
        if (activityId == null && doList.size() > 0) {
            PromotionGoodsDO promotionGoodsDO = doList.get(0);
            activityId = promotionGoodsDO.getActivityId();
            usedType = PromotionTypeEnum.myValueOf(promotionGoodsDO.getPromotionType());
        }

        if (usedType != null && activityId != null) {
            // Use the activity to be used
            cartPromotionManager.usePromotion(cartSkuVo.getSkuId(), activityId, usedType);
        }


    }


    /**
     * Reads all activities that an item participates in
     *
     * @param goodsId
     * @return
     */
    private List<PromotionGoodsDO> getGoodsEnablePromotion(Integer goodsId) {

        long currTime = DateUtil.getDateline();

        // Read the activity that this commodity participates in
        String sql = "select   goods_id, start_time, end_time, activity_id, promotion_type,title,num,price " +
                "from es_promotion_goods where ( goods_id=? or goods_id=-1)  and start_time<=? and end_time>=? ";
        List<PromotionGoodsDO> resultList = this.daoSupport.queryForList(sql, PromotionGoodsDO.class, goodsId, currTime, currTime);

        return resultList;
    }


}
