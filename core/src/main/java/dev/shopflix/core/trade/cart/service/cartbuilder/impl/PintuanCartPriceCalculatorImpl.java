/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.service.cartbuilder.impl;

import dev.shopflix.core.trade.cart.model.vo.CartSkuVO;
import dev.shopflix.core.trade.cart.model.vo.CartVO;
import dev.shopflix.core.trade.cart.model.vo.PriceDetailVO;
import dev.shopflix.core.trade.cart.service.cartbuilder.CartPriceCalculator;
import dev.shopflix.framework.util.CurrencyUtil;
import dev.shopflix.framework.logs.Logger;
import dev.shopflix.framework.logs.LoggerFactory;
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

        //根据规则计算价格
        PriceDetailVO priceDetailVO = this.countPriceWithRule(cartList);


        return priceDetailVO;
    }


    private PriceDetailVO countPriceWithRule(List<CartVO> cartList) {

        PriceDetailVO price = new PriceDetailVO();

        for (CartVO cart : cartList) {


            PriceDetailVO cartPrice = new PriceDetailVO();
            cartPrice.setFreightPrice(cart.getPrice().getFreightPrice());
            for (CartSkuVO cartSku : cart.getSkuList()) {


                //购物车全部商品的原价合
                cartPrice.setOriginalPrice(CurrencyUtil.add(cartPrice.getGoodsPrice(), cartSku.getSubtotal()));

                //购物车所有小计合
                cartPrice.setGoodsPrice(CurrencyUtil.add(cartPrice.getGoodsPrice(), cartSku.getSubtotal()));

                //累计商品重量
                double weight = CurrencyUtil.mul(cartSku.getGoodsWeight(), cartSku.getNum());
                double cartWeight = CurrencyUtil.add(cart.getWeight(), weight);
                cart.setWeight(cartWeight);


            }


            //总价为商品价加运费
            double totalPrice = CurrencyUtil.add(cartPrice.getGoodsPrice(), cartPrice.getFreightPrice());
            cartPrice.setTotalPrice(totalPrice);
            cart.setPrice(cartPrice);

            price = price.plus(cartPrice);


        }


        logger.debug("计算完优惠后购物车数据为：");
        logger.debug(cartList.toString());
        logger.debug("价格为：");
        logger.debug(price.toString());

        return price;
    }


}
