/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.cart.service.cartbuilder;

import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PriceDetailVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PromotionRule;

import java.util.List;

/**
 * 购物车价格计算器<br/>
 * 对购物车中的{@link PromotionRule}进行计算
 * 形成{@link PriceDetailVO}
 * 文档请参考：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html" >购物车架构</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
public interface CartPriceCalculator {


    /**
     * 计算购物车价格
     *
     * @param cartList
     * @return
     */
    PriceDetailVO countPrice(List<CartVO> cartList);


}
