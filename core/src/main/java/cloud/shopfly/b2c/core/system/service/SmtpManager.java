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

import cloud.shopfly.b2c.core.system.model.dos.SmtpDO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 邮件业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-25 16:16:53
 */
public interface SmtpManager {

    /**
     * 查询邮件列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 修改邮件
     *
     * @param smtp 邮件
     * @param id   邮件主键
     * @return Smtp 邮件
     */
    SmtpDO edit(SmtpDO smtp, Integer id);

    /**
     * 获取邮件
     *
     * @param id 邮件主键
     * @return Smtp  邮件
     */
    SmtpDO getModel(Integer id);

    /**
     * 添加邮件
     *
     * @param smtp 邮件
     * @return Smtp 邮件
     */
    SmtpDO add(SmtpDO smtp);

    /**
     * 删除邮件
     *
     * @param id 邮件主键
     */
    void delete(Integer id);

    /**
     * 发送测试邮件
     *
     * @param send 发送邮件地址
     * @param smtp smtp设置
     */
    void send(String send, SmtpDO smtp);

    /**
     * 获取当前使用的smtp方案
     *
     * @return smtp
     */
    SmtpDO getCurrentSmtp();
}