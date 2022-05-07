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
package cloud.shopfly.b2c.core.promotion.pintuan.service;

import cloud.shopfly.b2c.core.promotion.pintuan.model.PintuanOrderDetailVo;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderDTO;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PintuanChildOrder;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PintuanOrder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kingapex on 2019-01-24.
 * Group order business class
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-24
 */
public interface PintuanOrderManager {


    /**
     * Initiate or participate in group orders
     *
     * @param order          Regular orders
     * @param skuId          sku id
     * @param pinTuanOrderId Spell group orderid , if it is empty, it initiates a group; otherwise, it participates in the group
     * @return Spell group order
     */
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    PintuanOrder createOrder(OrderDTO order, Integer skuId, Integer pinTuanOrderId);

    /**
     * Process payment for a group order
     *
     * @param order Regular orders
     */
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void payOrder(OrderDO order);


    /**
     * According to theidAccess model
     *
     * @param id
     * @return
     */
    PintuanOrder getModel(Integer id);

    /**
     * Find the group master order by the normal order number
     *
     * @param orderSn
     * @return
     */
    PintuanOrderDetailVo getMainOrderBySn(String orderSn);

    /**
     * Read an order for an item to be grouped
     *
     * @param goodsId productid
     * @param skuId   skuId
     * @return Spell group order
     */
    List<PintuanOrder> getWaitOrder(Integer goodsId, Integer skuId);

    /**
     * Reads all suborders of an order
     *
     * @param orderId
     * @return
     */
    List<PintuanChildOrder> getPintuanChild(Integer orderId);

    /**
     * Deal with group orders
     *
     * @param orderId The orderid
     */
    void handle(Integer orderId);

}
