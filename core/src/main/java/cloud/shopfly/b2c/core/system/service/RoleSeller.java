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
package cloud.shopfly.b2c.core.system.service;

import cloud.shopfly.b2c.core.system.model.dos.RoleDO;
import cloud.shopfly.b2c.core.system.model.vo.RoleVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;
import java.util.Map;


/**
 * 角色表业务层
 *
 * @author admin
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-17 16:48:27
 */
public interface RoleSeller {

    /**
     * 查询角色表列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加角色表
     *
     * @param role 角色表
     * @return Role 角色表
     */
    RoleVO add(RoleVO role);

    /**
     * 修改角色表
     *
     * @param role 角色表
     * @param id   角色表主键
     * @return Role 角色表
     */
    RoleVO edit(RoleVO role, Integer id);

    /**
     * 删除角色表
     *
     * @param id 角色表主键
     */
    void delete(Integer id);

    /**
     * 获取角色表
     *
     * @param id 角色表主键
     * @return Role  角色表
     */
    RoleDO getModel(Integer id);

    /**
     * 获取角色表
     *
     * @param id 角色表主键
     * @return Role  角色表
     */
    RoleVO getRole(Integer id);


    /**
     * 获取所有角色的权限对照表
     *
     * @return 权限对照表
     */
    Map<String, List<String>> getRoleMap();


    /**
     * 根据角色id获取所属菜单
     *
     * @param id 角色id
     * @return 菜单唯一标识
     */
    List<String> getRoleMenu(Integer id);


}