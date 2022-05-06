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
 * 平台管理员业务层
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-20 20:38:26
 */
public interface AdminUserManager {

    /**
     * 查询平台管理员列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加平台管理员
     *
     * @param adminUserVO 平台管理员
     * @return AdminUser 平台管理员
     */
    AdminUser add(AdminUserVO adminUserVO);

    /**
     * 修改平台管理员
     *
     * @param adminUserVO 平台管理员
     * @param id          平台管理员主键
     * @return AdminUser 平台管理员
     */
    AdminUser edit(AdminUserVO adminUserVO, Integer id);

    /**
     * 删除平台管理员
     *
     * @param id 平台管理员主键
     */
    void delete(Integer id);

    /**
     * 获取平台管理员
     *
     * @param id 平台管理员主键
     * @return AdminUser  平台管理员
     */
    AdminUser getModel(Integer id);

    /**
     * 管理员登录
     *
     * @param name     管理员名称
     * @param password 管理员密码
     * @return
     */
    AdminLoginVO login(String name, String password);

    /**
     * 通过refreshToken重新获取accessToken
     *
     * @param refreshToken
     * @return
     */
    String exchangeToken(String refreshToken);

    /**
     * 管理员注销
     *
     * @param uid 会员id
     */
    void logout(Integer uid);


}