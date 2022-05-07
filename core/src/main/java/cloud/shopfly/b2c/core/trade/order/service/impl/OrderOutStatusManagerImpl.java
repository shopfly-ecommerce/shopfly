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

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderOutStatus;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOutStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOutTypeEnum;
import cloud.shopfly.b2c.core.trade.order.service.OrderOutStatusManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Order outbound status business class
 *
 * @author xlp
 * @version v2.0
 * @since v7.0.0
 * 2018-07-10 14:06:38
 */
@Service
public class OrderOutStatusManagerImpl implements OrderOutStatusManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_order_out_status  ";

        return this.daoSupport.queryForPage(sql, page, pageSize, OrderOutStatus.class);
    }

    @Override
    @Transactional(value = "", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderOutStatus add(OrderOutStatus orderOutStatus) {
        this.daoSupport.insert(orderOutStatus);
        return orderOutStatus;
    }

    @Override
    @Transactional(value = "", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void edit(String orderSn, OrderOutTypeEnum typeEnum, OrderOutStatusEnum statusEnum) {
        String sql = "update es_order_out_status set out_status =? where order_sn=? and out_type=?";
        this.daoSupport.execute(sql, statusEnum.name(), orderSn, typeEnum.name());
    }

    @Override
    @Transactional(value = "", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {

        this.daoSupport.delete(OrderOutStatus.class, id);
    }


    @Override
    public OrderOutStatus getModel(String orderSn, OrderOutTypeEnum typeEnum) {
        String sql = "select * from es_order_out_status where order_sn=? and out_type=?";
        return this.daoSupport.queryForObject(sql, OrderOutStatus.class, orderSn, typeEnum);
    }

}
