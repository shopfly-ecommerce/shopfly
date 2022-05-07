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
package cloud.shopfly.b2c.core.promotion.groupbuy.service.impl;

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyQuantityLog;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyQuantityLogManager;
import cloud.shopfly.b2c.core.promotion.tool.service.PromotionGoodsManager;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyGoodsManager;
import cloud.shopfly.b2c.core.statistics.util.DateUtil;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Group purchase commodity inventory log table business class
 *
 * @author xlp
 * @version v1.0
 * @since v7.0.0
 * 2018-07-09 15:32:29
 */
@Service
public class GroupbuyQuantityLogManagerImpl implements GroupbuyQuantityLogManager {

    @Autowired

    private DaoSupport daoSupport;

    @Autowired
    private PromotionGoodsManager promotionGoodsManager;

    @Autowired
    private GroupbuyGoodsManager groupbuyGoodsManager;


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<GroupbuyQuantityLog> rollbackReduce(String orderSn) {
        List<GroupbuyQuantityLog> logList = daoSupport.queryForList("select * from es_groupbuy_quantity_log where order_sn=? ", GroupbuyQuantityLog.class, orderSn);
        List<GroupbuyQuantityLog> result = new ArrayList<>();
        for (GroupbuyQuantityLog log : logList) {
            log.setQuantity(log.getQuantity());
            log.setOpTime(DateUtil.getDateline());
            log.setReason("Cancel the order and roll back inventory");
            log.setLogId(null);
            this.add(log);
            result.add(log);

            promotionGoodsManager.cleanCache(log.getGoodsId());
        }
        return result;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public GroupbuyQuantityLog add(GroupbuyQuantityLog groupbuyQuantityLog) {
        this.daoSupport.insert(groupbuyQuantityLog);
        return groupbuyQuantityLog;
    }

}
