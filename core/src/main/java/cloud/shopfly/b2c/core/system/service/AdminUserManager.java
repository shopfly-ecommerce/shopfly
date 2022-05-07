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

import cloud.shopfly.b2c.core.system.model.dos.AdminUser;
import cloud.shopfly.b2c.core.system.model.vo.AdminLoginVO;
import cloud.shopfly.b2c.core.system.model.vo.AdminUserVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Platform administrator business layer
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-20 20:38:26
 */
public interface AdminUserManager {

    /**
     * Example Query the platform administrator list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Adding a Platform Administrator
     *
     * @param adminUserVO Platform administrator
     * @return AdminUser Platform administrator
     */
    AdminUser add(AdminUserVO adminUserVO);

    /**
     * Modifying a Platform Administrator
     *
     * @param adminUserVO Platform administrator
     * @param id          Platform administrator primary key
     * @return AdminUser Platform administrator
     */
    AdminUser edit(AdminUserVO adminUserVO, Integer id);

    /**
     * Deleting a Platform Administrator
     *
     * @param id Platform administrator primary key
     */
    void delete(Integer id);

    /**
     * Obtaining a Platform Administrator
     *
     * @param id Platform administrator primary key
     * @return AdminUser  Platform administrator
     */
    AdminUser getModel(Integer id);

    /**
     * Administrator Login
     *
     * @param name     Administrator name
     * @param password Administrator password
     * @return
     */
    AdminLoginVO login(String name, String password);

    /**
     * throughrefreshTokenTo obtainaccessToken
     *
     * @param refreshToken
     * @return
     */
    String exchangeToken(String refreshToken);

    /**
     * Administrator logout
     *
     * @param uid membersid
     */
    void logout(Integer uid);


}
