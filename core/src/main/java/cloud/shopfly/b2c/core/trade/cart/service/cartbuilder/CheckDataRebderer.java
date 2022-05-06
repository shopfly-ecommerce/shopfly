/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.cart.service.cartbuilder;

import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;

import java.util.List;

/**
 * 数据正确性校验器
 *
 * @author zh
 * @version v7.0
 * @date 18/12/27 上午10:05
 * @since v7.0
 */
public interface CheckDataRebderer {
    /**
     * 数据正确性校验
     *
     * @param cartList 购物车数据
     */
    void checkData(List<CartVO> cartList);
}
