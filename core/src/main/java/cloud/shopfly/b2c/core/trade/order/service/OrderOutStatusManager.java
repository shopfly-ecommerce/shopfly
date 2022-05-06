/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.service;

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderOutStatus;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOutTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOutStatusEnum;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 订单出库状态业务层
 *
 * @author xlp
 * @version v2.0
 * @since v7.0.0
 * 2018-07-10 14:06:38
 */
public interface OrderOutStatusManager {

    /**
     * 查询订单出库状态列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加订单出库状态
     *
     * @param orderOutStatus 订单出库状态
     * @return OrderOutStatus 订单出库状态
     */
    OrderOutStatus add(OrderOutStatus orderOutStatus);

    /**
     * 修改订单出库状态
     *
     * @param orderSn    订单编号
     * @param typeEnum   出库类型
     * @param statusEnum 出库状态
     * @return OrderOutStatus 订单出库状态
     */
    void edit(String orderSn, OrderOutTypeEnum typeEnum, OrderOutStatusEnum statusEnum);

    /**
     * 删除订单出库状态
     *
     * @param id 订单出库状态主键
     */
    void delete(Integer id);

    /**
     * 获取订单出库状态
     *
     * @param orderSn  订单编号
     * @param typeEnum 出库类型
     * @return OrderOutStatus  订单出库状态
     */
    OrderOutStatus getModel(String orderSn, OrderOutTypeEnum typeEnum);

}
