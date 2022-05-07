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

import cloud.shopfly.b2c.core.pagedata.model.SiteNavigation;
import cloud.shopfly.b2c.core.pagedata.model.enums.ClientType;
import cloud.shopfly.b2c.core.pagedata.service.SiteNavigationManager;
import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Navigation bar business class
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 17:07:22
 */
@Service
public class SiteNavigationManagerImpl implements SiteNavigationManager {

    @Autowired
    
    private DaoSupport daoSupport;
    @Autowired
    private Cache cache;
    @Autowired
    private MessageSender messageSender;

    @Override
    public Page list(int page, int pageSize, String clientType) {

        String sql = "select * from es_site_navigation  where  client_type = ? order by sort desc";

        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, SiteNavigation.class, clientType);

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SiteNavigation add(SiteNavigation siteNavigation) {

        // Mobile image Address This parameter is mandatory
        if (ClientType.MOBILE.name().equals(siteNavigation.getClientType())) {
            if (StringUtil.isEmpty(siteNavigation.getImage())) {
                throw new ServiceException(SystemErrorCode.E953.code(), "Mobile navigation, pictures will be transmitted");
            }
        }
        // The length of the navigation name cannot exceed 6
        if (siteNavigation.getNavigationName().length() > 12) {
            throw new ServiceException(SystemErrorCode.E953.code(), "The navigation bar menu name has exceeded the maximum limit");
        }

        /**Querying the databasesortThe maximum value is easy to assign to newly added data*/
        Integer sort = this.daoSupport.queryForInt("select MAX(sort) from es_site_navigation");
        siteNavigation.setSort(sort + 1);

        this.daoSupport.insert(siteNavigation);

        int id = this.daoSupport.getLastId("");

        siteNavigation.setNavigationId(id);

        this.avigationChange(siteNavigation);

        return siteNavigation;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SiteNavigation edit(SiteNavigation siteNavigation, Integer id) {

        SiteNavigation siteNav = this.getModel(id);
        if (siteNav == null) {
            throw new ServiceException(SystemErrorCode.E953.code(), "The navigation bar does not exist. Please operate correctly");
        }

        // Mobile image Address This parameter is mandatory
        if (ClientType.MOBILE.name().equals(siteNavigation.getClientType())) {
            if (StringUtil.isEmpty(siteNavigation.getImage())) {
                throw new ServiceException(SystemErrorCode.E953.code(), "Mobile navigation, pictures will be transmitted");
            }
        }
        // The length of the navigation name cannot exceed 6
        if (siteNavigation.getNavigationName().length() > 12) {
            throw new ServiceException(SystemErrorCode.E953.code(), "The navigation bar menu name has exceeded the maximum limit");
        }

        siteNavigation.setSort(siteNav.getSort());

        this.avigationChange(siteNav);

        this.daoSupport.update(siteNavigation, id);

        return siteNavigation;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {

        SiteNavigation siteNav = this.getModel(id);
        if (siteNav == null) {
            throw new ServiceException(SystemErrorCode.E953.code(), "The navigation bar does not exist. Please operate correctly");
        }

        this.avigationChange(siteNav);

        this.daoSupport.delete(SiteNavigation.class, id);
    }

    @Override
    public SiteNavigation getModel(Integer id) {
        return this.daoSupport.queryForObject(SiteNavigation.class, id);
    }

    @Override
    public SiteNavigation updateSort(Integer id, String sort) {

        SiteNavigation siteNav = this.getModel(id);
        if (siteNav == null) {
            throw new ServiceException(SystemErrorCode.E953.code(), "The navigation bar does not exist. Please operate correctly");
        }

        Integer menuSort = siteNav.getSort();
        String sql = "";
        /**Determines whether the operation is moved down or upupMove up or move down*/
        if ("up".equals(sort)) {
            sql = "select * from es_site_navigation where sort >? and client_type=? order by sort asc limit 1";
        } else {
            sql = "select * from es_site_navigation where sort < ? and client_type= ? order by sort desc limit 1 ";
        }
        /** The previous or next record to the current record*/
        SiteNavigation operationSiteMenu = this.daoSupport.queryForObject(sql, SiteNavigation.class, siteNav.getSort(), siteNav.getClientType());
        /**If it isnull Is the highest or lowest level*/
        if (operationSiteMenu != null) {
            Integer operMenuSort = operationSiteMenu.getSort();
            /** Changes the order of the current record*/
            siteNav.setSort(operMenuSort);
            this.daoSupport.update(siteNav, siteNav.getNavigationId());
            operationSiteMenu.setSort(menuSort);
            this.daoSupport.update(operationSiteMenu, operationSiteMenu.getNavigationId());
        }

        this.avigationChange(siteNav);

        return siteNav;
    }

    @Override
    public List<SiteNavigation> listByClientType(String clientType) {

        List<SiteNavigation> list = (List<SiteNavigation>) cache.get(CachePrefix.SITE_NAVIGATION.getPrefix() + clientType);

        if (list == null || list.isEmpty()) {
            String sql = "select * from es_site_navigation  where  client_type = ? order by sort desc";

            list = this.daoSupport.queryForList(sql, SiteNavigation.class, clientType);

            cache.put(CachePrefix.SITE_NAVIGATION.getPrefix() + clientType, list);
        }

        return list;
    }

    /**
     * Navigation bar changes clear cache, sendmqThe message
     *
     * @param siteNav
     */
    private void avigationChange(SiteNavigation siteNav) {

        this.cache.remove(CachePrefix.SITE_NAVIGATION.getPrefix() + siteNav.getClientType());

        this.messageSender.send(new MqMessage(AmqpExchange.TEST_EXCHANGE,
                AmqpExchange.TEST_EXCHANGE + "_ROUTING",
                siteNav.getClientType()));
    }


}
