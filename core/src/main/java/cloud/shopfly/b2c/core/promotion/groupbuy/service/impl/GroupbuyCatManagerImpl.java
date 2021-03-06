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

import cloud.shopfly.b2c.core.promotion.PromotionErrorCode;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyCatDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyCatManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Group purchase classification business class
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 16:08:03
 */
@Service
public class GroupbuyCatManagerImpl implements GroupbuyCatManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public Page list(Integer pageNo, Integer pageSize) {
        String sql = "select * from es_groupbuy_cat  ";
        Page webPage = this.daoSupport.queryForPage(sql, pageNo, pageSize, GroupbuyCatDO.class);
        return webPage;
    }

    @Override
    public List<GroupbuyCatDO> getList(Integer parentId) {
        String sql = "select * from es_groupbuy_cat where parent_id = ? order by cat_order ";
        List<GroupbuyCatDO> list = this.daoSupport.queryForList(sql, GroupbuyCatDO.class, parentId);
        return list;
    }

    @Override
    public GroupbuyCatDO add(GroupbuyCatDO groupbuyCat) {

        // Verify whether the group purchase category name is repeated
        String sql = "select * from es_groupbuy_cat where cat_name = ?";
        List list = this.daoSupport.queryForList(sql, groupbuyCat.getCatName());
        if (list.size() > 0) {
            throw new ServiceException(PromotionErrorCode.E408.code(), "Group purchase classification name duplication");
        }
        if (groupbuyCat.getParentId() == null) {
            groupbuyCat.setParentId(0);
        }
        this.daoSupport.insert(groupbuyCat);
        int id = this.daoSupport.getLastId("es_groupbuy_cat");
        groupbuyCat.setCatId(id);
        return groupbuyCat;
    }

    @Override
    public GroupbuyCatDO edit(GroupbuyCatDO groupbuyCat, Integer id) {

        // Verify whether the group purchase category name is repeated
        String sql = "select * from es_groupbuy_cat where cat_name = ? and cat_id != ?";
        List list = this.daoSupport.queryForList(sql, groupbuyCat.getCatName(), id);
        if (list.size() > 0) {
            throw new ServiceException(PromotionErrorCode.E408.code(), "Group purchase classification name duplication");
        }

        this.daoSupport.update(groupbuyCat, id);
        return groupbuyCat;
    }

    @Override
    public void delete(Integer id) {
        this.daoSupport.delete(GroupbuyCatDO.class, id);
    }

    @Override
    public GroupbuyCatDO getModel(Integer id) {
        return this.daoSupport.queryForObject(GroupbuyCatDO.class, id);
    }
}
