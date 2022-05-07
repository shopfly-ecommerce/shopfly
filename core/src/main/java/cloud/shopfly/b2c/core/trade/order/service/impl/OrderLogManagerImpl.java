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

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderLogDO;
import cloud.shopfly.b2c.core.trade.order.service.OrderLogManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Order log table business class
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-16 12:01:34
 */
@Service
public class OrderLogManagerImpl implements OrderLogManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_order_log  ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, OrderLogDO.class);

        return webPage;
    }

    @Override
    public List listAll(String orderSn) {

        String sql = "select * from es_order_log where order_sn = ?";
        List<OrderLogDO> list = this.daoSupport.queryForList(sql, OrderLogDO.class, orderSn);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderLogDO add(OrderLogDO orderLog) {
        this.daoSupport.insert(orderLog);
        return orderLog;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderLogDO edit(OrderLogDO orderLog, Integer id) {
        this.daoSupport.update(orderLog, id);
        return orderLog;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(OrderLogDO.class, id);
    }

    @Override
    public OrderLogDO getModel(Integer id) {
        return this.daoSupport.queryForObject(OrderLogDO.class, id);
    }
}
