/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.service;


import dev.shopflix.core.trade.cart.model.vo.CartView;

/**
 * 购物车只读操作业务接口<br>
 * 包含对购物车读取操作
 *
 * @author Snow
 * @version v2.0
 * 2018年03月19日21:55:53
 * @since v7.0.0
 */
public interface CartReadManager {


    /**
     * 读取购物车数据，并计算优惠和价格
     *
     * @return
     */
    CartView getCartListAndCountPrice();


    /**
     * 由缓存中取出已勾选的购物列表<br>
     *
     * @return
     */
    CartView getCheckedItems();


}
