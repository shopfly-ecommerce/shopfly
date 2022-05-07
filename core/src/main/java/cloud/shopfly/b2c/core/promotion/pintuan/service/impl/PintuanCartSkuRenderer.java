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

import cloud.shopfly.b2c.core.promotion.pintuan.model.PintuanGoodsDO;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.promotion.pintuan.exception.PintuanErrorCode;
import cloud.shopfly.b2c.core.trade.cart.model.enums.CartType;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuOriginVo;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CartSkuRenderer;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl.CartSkuFilter;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingapex on 2019-01-23.
 * Cluster shopping cart renderer
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2019-01-23
 */
@Service
public class PintuanCartSkuRenderer implements CartSkuRenderer {

    @Autowired
    
    private DaoSupport tradeDaoSupport;


    @Autowired
    private PintuanManager pintuanManager;
    @Autowired
    private Cache cache;

    @SuppressWarnings("Duplicates")
    @Override
    public void renderSku(List<CartVO> cartList, CartType cartType) {

        String originKey = this.getOriginKey();
        CartSkuOriginVo goodsSkuVO = (CartSkuOriginVo) cache.get(originKey);

        String sql = "select * from es_pintuan_goods pg inner join es_pintuan p on p.promotion_id = pg.pintuan_id where sku_id=? and start_time < ? and end_time > ?";
        PintuanGoodsDO pintuanGoodsDO = tradeDaoSupport.queryForObject(sql, PintuanGoodsDO.class, goodsSkuVO.getSkuId(), DateUtil.getDateline(), DateUtil.getDateline());

        if (pintuanGoodsDO == null) {
            throw new ResourceNotFoundException("This group event has been cancelled and cannot be launched");
        }
        CartSkuVO skuVO = new CartSkuVO();

        skuVO.setSellerId(pintuanGoodsDO.getSellerId());
        skuVO.setSellerName(pintuanGoodsDO.getSellerName());
        skuVO.setGoodsId(pintuanGoodsDO.getGoodsId());
        skuVO.setSkuId(pintuanGoodsDO.getSkuId());
        skuVO.setCatId(goodsSkuVO.getCategoryId());
        skuVO.setGoodsImage(goodsSkuVO.getThumbnail());
        skuVO.setName(pintuanGoodsDO.getGoodsName());
        skuVO.setSkuSn(pintuanGoodsDO.getSn());


        // Group purchase price
        skuVO.setPurchasePrice(pintuanGoodsDO.getSalesPrice());

        // Group commodity original price
        skuVO.setOriginalPrice(pintuanGoodsDO.getPrice());

        skuVO.setSpecList(goodsSkuVO.getSpecList());
        skuVO.setIsFreeFreight(goodsSkuVO.getGoodsTransfeeCharge());
        skuVO.setGoodsWeight(goodsSkuVO.getWeight());
        skuVO.setTemplateId(goodsSkuVO.getTemplateId());
        skuVO.setEnableQuantity(goodsSkuVO.getEnableQuantity());
        skuVO.setLastModify(goodsSkuVO.getLastModify());
        skuVO.setNum(goodsSkuVO.getNum());
        skuVO.setChecked(1);
        skuVO.setGoodsType(goodsSkuVO.getGoodsType());

        // Calculate subtotals
        double subTotal = CurrencyUtil.mul(skuVO.getNum(), skuVO.getPurchasePrice());
        skuVO.setSubtotal(subTotal);

        List<CartSkuVO> skuList = new ArrayList<>();
        skuList.add(skuVO);

        Integer sellerId = goodsSkuVO.getSellerId();
        String sellerName = goodsSkuVO.getSellerName();

        CartVO cartVO = new CartVO(sellerId, sellerName, cartType);
        cartVO.setSkuList(skuList);

        // If the purchase limit is exceeded if the purchase limit is empty, the purchase limit is not verified
        Integer pintuan = pintuanManager.getModel(pintuanGoodsDO.getPintuanId()).getLimitNum();
        if (pintuan != null && pintuan < skuVO.getNum()) {
            throw new ServiceException(PintuanErrorCode.E5018.code(), PintuanErrorCode.E5018.describe());
        }

        cartList.add(cartVO);

    }

    @Override
    public void renderSku(List<CartVO> cartList, CartSkuFilter cartFilter, CartType cartType) {

        // Create a temporary list
        List<CartVO> tempList = new ArrayList<>();

        // Render the temporary list well
        renderSku(tempList, cartType);

        // filtering
        tempList.forEach(cartVO -> {

            cartVO.getSkuList().forEach(cartSkuVO -> {
                // Continue if the filter succeeds
                if (!cartFilter.accept(cartSkuVO)) {
                    cartList.add(cartVO);
                }
            });

        });
    }


    /**
     * Read the current member shopping cart raw datakey
     *
     * @return
     */
    @SuppressWarnings("Duplicates")
    protected String getOriginKey() {

        String cacheKey = "";
        // If the member logs in, the member ID is the key
        Buyer buyer = UserContext.getBuyer();
        if (buyer != null) {
            cacheKey = CachePrefix.CART_SKU_PREFIX.getPrefix() + buyer.getUid();
        }

        return cacheKey;
    }
}
