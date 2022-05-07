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
package cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl;

import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PriceDetailVO;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CartPriceCalculator;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kingapex on 2018/12/10.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
@Service
public class PintuanCartPriceCalculatorImpl implements CartPriceCalculator {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public PriceDetailVO countPrice(List<CartVO> cartList) {

        // Calculate the price according to the rules
        PriceDetailVO priceDetailVO = this.countPriceWithRule(cartList);


        return priceDetailVO;
    }


    private PriceDetailVO countPriceWithRule(List<CartVO> cartList) {

        PriceDetailVO price = new PriceDetailVO();

        for (CartVO cart : cartList) {


            PriceDetailVO cartPrice = new PriceDetailVO();
            cartPrice.setFreightPrice(cart.getPrice().getFreightPrice());
            for (CartSkuVO cartSku : cart.getSkuList()) {


                // The original price of all items in the shopping cart
                cartPrice.setOriginalPrice(CurrencyUtil.add(cartPrice.getGoodsPrice(), cartSku.getSubtotal()));

                // Shopping cart all subtotals
                cartPrice.setGoodsPrice(CurrencyUtil.add(cartPrice.getGoodsPrice(), cartSku.getSubtotal()));

                // Accumulated commodity weight
                double weight = CurrencyUtil.mul(cartSku.getGoodsWeight(), cartSku.getNum());
                double cartWeight = CurrencyUtil.add(cart.getWeight(), weight);
                cart.setWeight(cartWeight);


            }


            // Total price is commodity price plus freight
            double totalPrice = CurrencyUtil.add(cartPrice.getGoodsPrice(), cartPrice.getFreightPrice());
            cartPrice.setTotalPrice(totalPrice);
            cart.setPrice(cartPrice);

            price = price.plus(cartPrice);


        }


       if(logger.isDebugEnabled()){
           logger.debug("After calculating the discount, the shopping cart data is：");
           logger.debug(cartList.toString());
           logger.debug("The price for：");
           logger.debug(price.toString());
       }

        return price;
    }


}
