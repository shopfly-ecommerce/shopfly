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
 * 拼团订单业务类
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-24
 */
public interface PintuanOrderManager {


    /**
     * 发起或参与拼团订单
     *
     * @param order          常规订单
     * @param skuId          sku id
     * @param pinTuanOrderId 拼团订单id ，如果为空则为发起拼团，否则参与此拼团
     * @return 拼团订单
     */
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    PintuanOrder createOrder(OrderDTO order, Integer skuId, Integer pinTuanOrderId);

    /**
     * 对一个拼团订单进行支付处理
     *
     * @param order 普通订单
     */
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void payOrder(OrderDO order);


    /**
     * 根据id获取模型
     *
     * @param id
     * @return
     */
    PintuanOrder getModel(Integer id);

    /**
     * 通过普通订单号查找拼团主订单
     *
     * @param orderSn
     * @return
     */
    PintuanOrderDetailVo getMainOrderBySn(String orderSn);

    /**
     * 读取某个商品待成团的订单
     *
     * @param goodsId 商品id
     * @param skuId   skuId
     * @return 拼团订单
     */
    List<PintuanOrder> getWaitOrder(Integer goodsId, Integer skuId);

    /**
     * 读取某订单的所有子订单
     *
     * @param orderId
     * @return
     */
    List<PintuanChildOrder> getPintuanChild(Integer orderId);

    /**
     * 处理拼团订单
     *
     * @param orderId 订单id
     */
    void handle(Integer orderId);

}
