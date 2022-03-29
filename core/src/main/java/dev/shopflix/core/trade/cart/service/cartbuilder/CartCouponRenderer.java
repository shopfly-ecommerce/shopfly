/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.service.cartbuilder;

import dev.shopflix.core.trade.cart.model.vo.CartVO;

import java.util.List;

/**
 * 购物车优惠券渲染器
 * 文档请参考：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html" >购物车架构</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/18
 */
public interface CartCouponRenderer {

    /**
     * 优惠券计算
     * @param cartList
     */
    void render(List<CartVO> cartList);

}
