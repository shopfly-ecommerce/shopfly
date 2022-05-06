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

import cloud.shopfly.b2c.core.trade.order.model.dos.PayLog;
import cloud.shopfly.b2c.core.trade.order.model.dto.PayLogQueryParam;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * 收款单业务层
 *
 * @author xlp
 * @version v2.0
 * @since v7.0.0
 * 2018-07-18 10:39:51
 */
public interface PayLogManager {

    /**
     * 查询收款单列表
     *
     * @param queryParam 查询参数
     * @return Page
     */
    Page list(PayLogQueryParam queryParam);

    /**
     * 添加收款单
     *
     * @param payLog 收款单
     * @return PayLog 收款单
     */
    PayLog add(PayLog payLog);

    /**
     * 修改收款单
     *
     * @param payLog 收款单
     * @param id     收款单主键
     * @return PayLog 收款单
     */
    PayLog edit(PayLog payLog, Integer id);

    /**
     * 删除收款单
     *
     * @param id 收款单主键
     */
    void delete(Integer id);

    /**
     * 获取收款单
     *
     * @param id 收款单主键
     * @return PayLog  收款单
     */
    PayLog getModel(Integer id);

    /**
     * 根据订单号
     *
     * @param orderSn
     * @return
     */
    PayLog getModel(String orderSn);

    /**
     * 返回不分页的数据
     *
     * @param queryParam
     * @return
     */
    List<PayLog> exportExcel(PayLogQueryParam queryParam);
}
