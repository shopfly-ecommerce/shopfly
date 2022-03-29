/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.service;

import com.enation.app.javashop.core.goods.model.vo.CacheGoods;
import com.enation.app.javashop.core.trade.cart.model.vo.CartVO;

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
     * 获取运费
     *
     * @param cartVOS 购物车
     * @param areaId  地区id
     * @return 运费
     */
    Double getShipPrice(CartVO cartVOS, Integer areaId);

    /**
     * 设置运费
     *
     * @param cartList 购物车集合
     */
    void setShippingPrice(List<CartVO> cartList);

    /**
     * 检测是否有不能配送的区域
     *
     * @param cartList 购物车
     * @param areaId   地区
     * @return
     */
    List<CacheGoods> checkArea(List<CartVO> cartList, Integer areaId);


}
