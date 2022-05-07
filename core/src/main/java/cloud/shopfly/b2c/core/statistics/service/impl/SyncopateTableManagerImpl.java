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

import cloud.shopfly.b2c.core.statistics.service.SyncopateTableManager;
import cloud.shopfly.b2c.core.statistics.util.SyncopateUtil;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * SyncopateTableManagerImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 In the morning9:59
 */
@Service
public class SyncopateTableManagerImpl implements SyncopateTableManager {

    @Autowired
    
    private DaoSupport daoSupport;

    /**
     * Daily filling data
     */
    @Override
    public void everyDay() {
        SyncopateUtil.syncopateTable(LocalDate.now().getYear(), daoSupport);
    }

}
