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
package cloud.shopfly.b2c.core.pagedata.service;

import cloud.shopfly.b2c.core.pagedata.model.SiteNavigation;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Navigation bar business layer
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 17:07:22
 */
public interface SiteNavigationManager {

    /**
     * Query the navigation bar list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @param clientType
     * @return Page
     */
    Page list(int page, int pageSize, String clientType);

    /**
     * Add navigation
     *
     * @param siteNavigation The navigation bar
     * @return SiteNavigation The navigation bar
     */
    SiteNavigation add(SiteNavigation siteNavigation);

    /**
     * Modify navigation bar
     *
     * @param siteNavigation The navigation bar
     * @param id             Navigation bar primary key
     * @return SiteNavigation The navigation bar
     */
    SiteNavigation edit(SiteNavigation siteNavigation, Integer id);

    /**
     * Delete navigation bar
     *
     * @param id Navigation bar primary key
     */
    void delete(Integer id);

    /**
     * Get navigation
     *
     * @param id Navigation bar primary key
     * @return SiteNavigation  The navigation bar
     */
    SiteNavigation getModel(Integer id);

    /**
     * Update the sorting
     * @param id The menuid
     * @param sort	Up and down
     * @return Navigation menu
     */
    SiteNavigation updateSort(Integer id, String sort);

    /**
     * Client query list
     * @param clientType
     * @return
     */
    List<SiteNavigation> listByClientType(String clientType);
}
