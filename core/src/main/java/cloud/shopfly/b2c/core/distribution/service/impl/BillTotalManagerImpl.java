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
package cloud.shopfly.b2c.core.distribution.service.impl;

import cloud.shopfly.b2c.core.distribution.model.dos.BillTotalDO;
import cloud.shopfly.b2c.core.distribution.service.BillTotalManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * General statement processing
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/14 In the morning7:13
 */

@Service
public class BillTotalManagerImpl implements BillTotalManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public Page page(int page, int pageSize) {
        return this.daoSupport.queryForPage("select * from es_bill_total", page, pageSize);
    }


    @Override
    public BillTotalDO add(BillTotalDO billTotal) {
        daoSupport.insert("es_bill_total", billTotal);
        return billTotal;

    }


    @Override
    public BillTotalDO getTotalByStart(Long startTime) {
        return this.daoSupport.queryForObject("select * from es_bill_total where start_time = ? ", BillTotalDO.class, startTime);
    }


}
