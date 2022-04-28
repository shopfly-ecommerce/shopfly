/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.service.cartbuilder.impl;

import dev.shopflix.core.trade.cart.model.vo.CartVO;
import dev.shopflix.core.trade.cart.service.cartbuilder.CartShipPriceCalculator;
import dev.shopflix.core.trade.order.service.ShippingManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/19
 */
@Service
public class CartShipPriceCalculatorImpl implements CartShipPriceCalculator {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShippingManager shippingManager;

    @Override
    public void countShipPrice(List<CartVO> cartList) {
        shippingManager.setShippingPrice(cartList);
        if (logger.isDebugEnabled()) {
            logger.debug("购物车处理运费结果为：");
            logger.debug(cartList.toString());
        }
    }

}
