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
package cloud.shopfly.b2c.core.pagedata.service.impl;

import cloud.shopfly.b2c.core.pagedata.service.StaticsPageHelpManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Static page implementation
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-17 In the afternoon3:27
 */
@Service
public class StaticsPageHelpManagerImpl implements StaticsPageHelpManager {

    @Autowired

    private DaoSupport daoSupport;

    /**
     * Get the total number of help pages
     *
     * @return
     */
    @Override
    public Integer count() {
        return this.daoSupport.queryForInt("select count(0) from es_article");
    }

    /**
     * Paging for help
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List helpList(Integer page, Integer pageSize) {
        return this.daoSupport.queryForListPage("select article_id as id from es_article",page,pageSize);
    }
}
