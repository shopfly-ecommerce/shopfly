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
package cloud.shopfly.b2c.core.statistics.service.impl;

import cloud.shopfly.b2c.core.statistics.model.dto.RefundData;
import cloud.shopfly.b2c.core.statistics.service.RefundDataManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 退款变化业务实现类
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/6/4 9:59
 */
@Service
public class RefundDataManagerImpl implements RefundDataManager {

    @Autowired
    
    private DaoSupport daoSupport;


    @Override
    public void put(RefundData refundData) {
        //审核通过
        this.daoSupport.insert("es_sss_refund_data", refundData);

    }

}
