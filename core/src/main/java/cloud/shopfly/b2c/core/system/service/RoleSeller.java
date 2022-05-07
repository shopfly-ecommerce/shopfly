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
 * Role table business layer
 *
 * @author admin
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-17 16:48:27
 */
public interface RoleSeller {

    /**
     * Example Query the role list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Adding a role table
     *
     * @param role Character sheet
     * @return Role Character sheet
     */
    RoleVO add(RoleVO role);

    /**
     * Modifying the role table
     *
     * @param role Character sheet
     * @param id   Primary key of the role table
     * @return Role Character sheet
     */
    RoleVO edit(RoleVO role, Integer id);

    /**
     * Deleting a role table
     *
     * @param id Primary key of the role table
     */
    void delete(Integer id);

    /**
     * Get the role table
     *
     * @param id Primary key of the role table
     * @return Role  Character sheet
     */
    RoleDO getModel(Integer id);

    /**
     * Get the role table
     *
     * @param id Primary key of the role table
     * @return Role  Character sheet
     */
    RoleVO getRole(Integer id);


    /**
     * Obtain the permission mapping table of all roles
     *
     * @return Permission comparison table
     */
    Map<String, List<String>> getRoleMap();


    /**
     * According to the characteridGet owning menu
     *
     * @param id roleid
     * @return Menu unique identifier
     */
    List<String> getRoleMenu(Integer id);


}
