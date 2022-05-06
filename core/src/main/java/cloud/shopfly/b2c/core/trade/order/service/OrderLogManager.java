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

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderLogDO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * 订单日志表业务层
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-16 12:01:34
 */
public interface OrderLogManager {

    /**
     * 查询订单日志表列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);


    /**
     * 查询订单日志表列表
     *
     * @param orderSn 订单编号
     * @return List
     */
    List listAll(String orderSn);


    /**
     * 添加订单日志表
     *
     * @param orderLog 订单日志表
     * @return OrderLog 订单日志表
     */
    OrderLogDO add(OrderLogDO orderLog);

    /**
     * 修改订单日志表
     *
     * @param orderLog 订单日志表
     * @param id       订单日志表主键
     * @return OrderLog 订单日志表
     */
    OrderLogDO edit(OrderLogDO orderLog, Integer id);

    /**
     * 删除订单日志表
     *
     * @param id 订单日志表主键
     */
    void delete(Integer id);

    /**
     * 获取订单日志表
     *
     * @param id 订单日志表主键
     * @return OrderLog  订单日志表
     */
    OrderLogDO getModel(Integer id);

}
