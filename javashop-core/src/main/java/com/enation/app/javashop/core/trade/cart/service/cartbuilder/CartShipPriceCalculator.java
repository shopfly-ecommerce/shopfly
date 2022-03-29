/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.cart.service.cartbuilder;

import com.enation.app.javashop.core.trade.cart.model.vo.CartVO;

import java.util.List;

/**
 * Created by kingapex on 2018/12/19.
 * 运费计算器
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/19
 */
public interface CartShipPriceCalculator {

    /**
     * 计算运费
     *
     * @param cartList
     */
    void countShipPrice(List<CartVO> cartList);

}
