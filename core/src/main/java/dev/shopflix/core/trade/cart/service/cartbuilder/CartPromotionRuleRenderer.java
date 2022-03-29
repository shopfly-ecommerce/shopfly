/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.service.cartbuilder;

import dev.shopflix.core.trade.cart.model.vo.CartVO;
import dev.shopflix.core.trade.cart.model.vo.PromotionRule;
import dev.shopflix.core.trade.cart.model.vo.SelectedPromotionVo;

import java.util.List;

/**
 * 购物车促销信息渲染器<br/>
 * 使用{@link SelectedPromotionVo}物料生产出
 * {@link PromotionRule}
 * 文档请参考：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html" >购物车架构</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
public interface CartPromotionRuleRenderer {


    /**
     * 对购物车进行渲染促销数据
     * @param cartList
     * @param includeCoupon
     */
    void render(List<CartVO> cartList, boolean includeCoupon);

}
