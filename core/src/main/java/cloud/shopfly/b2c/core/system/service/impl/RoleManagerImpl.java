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
package cloud.shopfly.b2c.core.system.service.impl;

import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.system.model.dos.RoleDO;
import cloud.shopfly.b2c.core.system.model.vo.Menus;
import cloud.shopfly.b2c.core.system.model.vo.RoleVO;
import cloud.shopfly.b2c.core.system.service.RoleSeller;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Role table business class
 *
 * @author kingapex
 * @version v1.0.0
 * @since v7.0.0
 * 2018-04-17 16:48:27
 */
@Service
public class RoleManagerImpl implements RoleSeller {

    @Autowired
    
    private DaoSupport systemDaoSupport;

    @Autowired
    private Cache cache;

    @Override
    public Page list(int page, int pageSize) {
        String sql = "select role_id,role_name,role_describe from es_role ";
        Page webPage = this.systemDaoSupport.queryForPage(sql, page, pageSize, RoleDO.class);
        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RoleVO add(RoleVO role) {
        RoleDO roleDO = new RoleDO();
        roleDO.setRoleName(role.getRoleName());
        roleDO.setAuthIds(JsonUtil.objectToJson(role.getMenus()));
        roleDO.setRoleDescribe(role.getRoleDescribe());
        this.systemDaoSupport.insert(roleDO);
        role.setRoleId(systemDaoSupport.getLastId("es_role"));
        // Deletes menu permissions for roles in the cache
        cache.remove(CachePrefix.ADMIN_URL_ROLE.getPrefix());
        return role;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RoleVO edit(RoleVO role, Integer id) {
        // Verify whether the permission exists
        RoleDO roleDO = this.getModel(id);
        if (roleDO == null) {
            throw new ResourceNotFoundException("This role does not exist");
        }
        roleDO.setRoleName(role.getRoleName());
        roleDO.setAuthIds(JsonUtil.objectToJson(role.getMenus()));
        roleDO.setRoleDescribe(role.getRoleDescribe());
        this.systemDaoSupport.update(roleDO, id);
        role.setRoleId(id);
        // Deletes menu permissions for roles in the cache
        cache.remove(CachePrefix.ADMIN_URL_ROLE.getPrefix());
        return role;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        RoleDO roleDO = this.getModel(id);
        if (roleDO == null) {
            throw new ResourceNotFoundException("This role does not exist");
        }

        // Check whether the role has an administrator. If there is an administrator, the role cannot be deleted
        String sql = "select * from es_admin_user where role_id = ? and user_state != -1";
        List list = this.systemDaoSupport.queryForList(sql, id);
        if (StringUtil.isNotEmpty(list)) {
            throw new ServiceException(SystemErrorCode.E924.code(), "The role has an administrator. Delete the administrator and then delete the role");
        }

        this.systemDaoSupport.delete(RoleDO.class, id);
    }

    @Override
    public RoleDO getModel(Integer id) {
        return this.systemDaoSupport.queryForObject(RoleDO.class, id);
    }

    @Override
    public Map<String, List<String>> getRoleMap() {

        Map<String, List<String>> roleMap = new HashMap<>(16);
        String sql = "select * from es_role";
        List<RoleDO> roles = this.systemDaoSupport.queryForList(sql, RoleDO.class);
        for (int i = 0; i < roles.size(); i++) {
            List<Menus> menusList = JsonUtil.jsonToList(roles.get(i).getAuthIds(), Menus.class);
            if (menusList != null && menusList.size() > 0) {
                List<String> authList = new ArrayList<>();
                // Recursively queries the menu permissions of roles
                this.getChildren(menusList, authList);
                roleMap.put(roles.get(i).getRoleName(), authList);
                cache.put(CachePrefix.ADMIN_URL_ROLE.getPrefix(), roleMap);
            }
        }
        return roleMap;
    }

    /**
     * Recursively saves the menu permissions held by this role lock tolist
     *
     * @param menuList Set menu
     * @param authList Collection of permission groups
     */
    private void getChildren(List<Menus> menuList, List<String> authList) {
        for (Menus menus : menuList) {
            // Put the menu permissions that this role has into the list
            if (menus.isChecked()) {
                authList.add(menus.getAuthRegular());
            }
            if (!menus.getChildren().isEmpty()) {
                getChildren(menus.getChildren(), authList);
            }
        }
    }

    @Override
    public List<String> getRoleMenu(Integer id) {
        RoleDO roleDO = this.getModel(id);
        if (roleDO == null) {
            throw new ResourceNotFoundException("This role does not exist");
        }
        List<Menus> menusList = JsonUtil.jsonToList(roleDO.getAuthIds(), Menus.class);
        List<String> authList = new ArrayList<>();
        // Filter menu
        this.reset(menusList, authList);
        return authList;
    }

    /**
     * screeningcheckedfortrueThe menu
     *
     * @param menuList Set menu
     */
    private void reset(List<Menus> menuList, List<String> authList) {
        for (Menus menus : menuList) {
            // Put the menu permissions that this role has into the list
            if (menus.isChecked()) {
                authList.add(menus.getIdentifier());
            }
            if (!menus.getChildren().isEmpty()) {
                reset(menus.getChildren(), authList);
            }
        }
    }

    @Override
    public RoleVO getRole(Integer id) {
        return new RoleVO(this.getModel(id));
    }
}

