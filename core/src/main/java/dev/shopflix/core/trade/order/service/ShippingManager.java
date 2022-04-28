/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.service;

import dev.shopflix.core.goods.model.vo.CacheGoods;
import dev.shopflix.core.trade.cart.model.vo.CartVO;

import java.util.List;

/**
 * 运费计算业务层接口
 *
 * @author Snow create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
public interface ShippingManager {

    /**
     * 获取购物车价格
     *
     * @param cartVO 购物车
     * @return
     */
    Double getShipPrice(CartVO cartVO);

    /**
     * 设置运费
     *
     * @param cartList 购物车集合
     */
    void setShippingPrice(List<CartVO> cartList);


    /**
     * 校验地区
     *
     * @param cartList 购物车
     * @param countryCode   国家code
     * @param stateCode      洲code
     * @return
     */
    List<CacheGoods> checkArea(List<CartVO> cartList, String countryCode,String stateCode);


}
