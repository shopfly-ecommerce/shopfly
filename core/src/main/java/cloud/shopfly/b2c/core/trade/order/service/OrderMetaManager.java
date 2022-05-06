/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.service;

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderMetaDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderMetaKeyEnum;

import java.util.List;

/**
 * 订单元信息
 *
 * @author Snow create in 2018/6/27
 * @version v2.0
 * @since v7.0.0
 */
public interface OrderMetaManager {

    /**
     * 添加
     *
     * @param orderMetaDO
     */
    void add(OrderMetaDO orderMetaDO);

    /**
     * 读取订单元信息
     *
     * @param orderSn
     * @param metaKey
     * @return
     */
    String getMetaValue(String orderSn, OrderMetaKeyEnum metaKey);

    /**
     * 读取order meta列表
     *
     * @param orderSn
     * @return
     */
    List<OrderMetaDO> list(String orderSn);

    /**
     * 修改订单元信息
     * @param orderSn
     * @param metaKey
     * @param metaValue
     * @return
     */
    void updateMetaValue(String orderSn,OrderMetaKeyEnum metaKey, String metaValue);
}
