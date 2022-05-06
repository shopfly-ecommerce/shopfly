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
package cloud.shopfly.b2c.core.trade.order.service.impl;

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.service.OrderManager;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderDetailVO;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单操作实现
 *
 * @author Snow create in 2018/5/21
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class OrderManagerImpl implements OrderManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public OrderDetailVO update(OrderDO orderDO) {

        this.daoSupport.update(orderDO, orderDO.getOrderId());
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        BeanUtils.copyProperties(orderDO, orderDetailVO);

        return orderDetailVO;
    }

}
