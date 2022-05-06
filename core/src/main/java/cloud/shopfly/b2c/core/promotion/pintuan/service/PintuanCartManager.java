/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.pintuan.service;

import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuOriginVo;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartView;

/**
 * Created by kingapex on 2019-01-23.
 * 拼团购物车业务类接口
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-23
 */
public interface PintuanCartManager {


    /**
     * 获取拼团购物车
     *
     * @return 购物车视图
     */
    CartView getCart();


    /**
     * 将一个拼团的sku加入到购物车中
     *
     * @param skuId
     * @param num   加入的数量
     * @return
     */
    CartSkuOriginVo addSku(Integer skuId, Integer num);


}
