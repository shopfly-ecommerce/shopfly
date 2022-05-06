/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.service;

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderDetailVO;

/**
 * 订单操作接口
 *
 * @author Snow create in 2018/5/21
 * @version v2.0
 * @since v7.0.0
 */
public interface OrderManager {

    /**
     * 修改订单信息
     *
     * @param orderDO
     * @return
     */
    OrderDetailVO update(OrderDO orderDO);


}
