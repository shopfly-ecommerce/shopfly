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
package cloud.shopfly.b2c.api.buyer.trade;

import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.trade.cart.model.enums.CartType;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartView;
import cloud.shopfly.b2c.core.trade.cart.service.CartPromotionManager;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CartBuilder;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CartPriceCalculator;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CartSkuRenderer;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CheckDataRebderer;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl.DefaultCartBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;

/**
 * Shopping cart price calculation interface
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-12-01 In the afternoon8:26
 */
@Api(description = "Shopping cart price calculationAPI")
@RestController
@RequestMapping("/trade/promotion")
@Validated
public class TradePromotionController {


    @Autowired
    private CartPromotionManager promotionManager;


    /**
     * Shopping cart price calculator
     */
    @Autowired
    private CartPriceCalculator cartPriceCalculator;
    /**
     * Data validation
     */
    @Autowired
    private CheckDataRebderer checkDataRebderer;

    /**
     * The shopping cartskuData renderer
     */
    @Autowired
    private CartSkuRenderer cartSkuRenderer;

    @ApiOperation(value = "Select promotions to participate in")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_id", value = "productid", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "activity_id", value = "activityid", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "promotion_type", value = "The activity type", required = true, dataType = "String", paramType = "query"),})
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public void setPromotion(@ApiIgnore Integer skuId, @ApiIgnore Integer activityId, @ApiIgnore String promotionType) {
        promotionManager.usePromotion(skuId, activityId, PromotionTypeEnum.valueOf(promotionType));
    }


    @ApiOperation(value = "Cancel participation in promotion")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_id", value = "productid", required = true, dataType = "int", paramType = "query")
    })
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public void promotionCancel(Integer skuId) {
        promotionManager.delete(new Integer[]{skuId});
    }


    @ApiOperation(value = "Set coupons", notes = "There are three ways to use coupons：before2Kind of circumstancecouponId Dont for0,Dont for空。第3Kind of circumstancecouponIdfor0," +
            "1、Use coupons:Just before entering the order settlement page, before using any coupons." +
            "2、Switch coupons:in1、情况之后，当用户Switch coupons的时候。" +
            "3、Cancel used coupons:When users dont want to use coupons.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mc_id", value = "couponsID", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping(value = "/{mc_id}/coupon")
    public void setCoupon(@NotNull(message = "couponsidCant be empty") @PathVariable("mc_id") Integer mcId) {

        CartBuilder cartBuilder = new DefaultCartBuilder(CartType.CART, cartSkuRenderer, null, cartPriceCalculator, checkDataRebderer);

        CartView cartView = cartBuilder.renderSku().countPrice().build();


        CartVO cart = cartView.getCartList().get(0);
        double goodsPrice = cart.getPrice().getGoodsPrice();
        promotionManager.useCoupon(mcId, goodsPrice);
    }


}
