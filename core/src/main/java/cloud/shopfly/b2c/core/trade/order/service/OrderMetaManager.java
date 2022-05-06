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
