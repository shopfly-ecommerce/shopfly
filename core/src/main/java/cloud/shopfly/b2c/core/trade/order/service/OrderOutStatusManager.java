/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
