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

import cloud.shopfly.b2c.core.system.model.dos.LogiCompanyDO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * 物流公司业务层
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-29 15:10:38
 */
public interface LogiCompanyManager {

    /**
     * 查询物流公司列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加物流公司
     *
     * @param logi 物流公司
     * @return Logi 物流公司
     */
    LogiCompanyDO add(LogiCompanyDO logi);

    /**
     * 修改物流公司
     *
     * @param logi 物流公司
     * @param id   物流公司主键
     * @return Logi 物流公司
     */
    LogiCompanyDO edit(LogiCompanyDO logi, Integer id);

    /**
     * 删除物流公司
     *
     * @param id 物流公司主键
     */
    void delete(Integer[] id);

    /**
     * 获取物流公司
     *
     * @param id 物流公司主键
     * @return Logi  物流公司
     */
    LogiCompanyDO getModel(Integer id);

    /**
     * 通过code获取物流公司
     *
     * @param code 物流公司code
     * @return 物流公司
     */
    LogiCompanyDO getLogiByCode(String code);

    /**
     * 通过快递鸟物流code获取物流公司
     *
     * @param kdcode 快递鸟公司code
     * @return 物流公司
     */
    LogiCompanyDO getLogiBykdCode(String kdcode);

    /**
     * 根据物流名称查询物流信息
     *
     * @param name 物流名称
     * @return 物流公司
     */
    LogiCompanyDO getLogiByName(String name);

    /**
     * 查询物流公司列表(不分页)
     *
     * @return Page
     */
    List<LogiCompanyDO> list();

}