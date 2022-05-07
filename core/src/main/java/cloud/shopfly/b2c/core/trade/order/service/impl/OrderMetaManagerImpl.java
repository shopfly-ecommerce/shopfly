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

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderMetaDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderMetaKeyEnum;
import cloud.shopfly.b2c.core.trade.order.service.OrderMetaManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Order meta-information
 *
 * @author Snow create in 2018/6/27
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class OrderMetaManagerImpl implements OrderMetaManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public void add(OrderMetaDO orderMetaDO) {

        this.daoSupport.insert(orderMetaDO);
    }


    @Override
    public String getMetaValue(String orderSn, OrderMetaKeyEnum metaKey) {
        String sql = "select meta_value from es_order_meta where order_sn=? and meta_key=?";
        String metaJson = daoSupport.queryForString(sql, orderSn, metaKey.name());
        return metaJson;
    }

    @Override
    public List<OrderMetaDO> list(String orderSn) {

        String sql = "select * from es_order_meta where order_sn=?";

        return daoSupport.queryForList(sql, OrderMetaDO.class, orderSn);
    }

    @Override
    public void updateMetaValue(String orderSn, OrderMetaKeyEnum metaKey, String metaValue) {

        String sql = "update es_order_meta set meta_value = ? where order_sn=? and meta_key=?";
        this.daoSupport.execute(sql, metaValue, orderSn, metaKey.name());

    }


}
