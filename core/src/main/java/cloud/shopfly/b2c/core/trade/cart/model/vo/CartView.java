/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.cart.model.vo;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;

/**
 * 购物车视图<br/>
 * 购物车构建器最终要构建的成品
 * 文档请参考：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html#购物车显示" >购物车显示</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
public class CartView {

    /**
     * 购物车列表
     */
    @ApiModelProperty(value = "购物车列表")
    private List<CartVO> cartList;

    /**
     * 购物车计算后的总价
     */
    @ApiModelProperty(value = "车计算后的总价")
    private PriceDetailVO totalPrice;

    public CartView(List<CartVO> cartList, PriceDetailVO totalPrice) {
        this.cartList = cartList;
        this.totalPrice = totalPrice;
    }

    public List<CartVO> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartVO> cartList) {
        this.cartList = cartList;
    }

    public PriceDetailVO getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(PriceDetailVO totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartView{" +
                "cartList=" + cartList +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CartView cartView = (CartView) o;

        return new EqualsBuilder()
                .append(cartList, cartView.cartList)
                .append(totalPrice, cartView.totalPrice)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(cartList)
                .append(totalPrice)
                .toHashCode();
    }
}
