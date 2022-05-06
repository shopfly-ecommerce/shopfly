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

import cloud.shopfly.b2c.core.system.model.dos.ExpressPlatformDO;
import cloud.shopfly.b2c.core.system.model.vo.ExpressDetailVO;
import cloud.shopfly.b2c.core.system.model.vo.ExpressPlatformVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 快递平台业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-11 14:42:50
 */
public interface ExpressPlatformManager {

    /**
     * 查询快递平台列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加快递平台
     *
     * @param expressPlatformVO 快递平台
     * @return expressPlatformVO 快递平台
     */
    ExpressPlatformVO add(ExpressPlatformVO expressPlatformVO);

    /**
     * 修改快递平台
     *
     * @param expressPlatformVO 快递平台
     * @return ExpressPlatformDO 快递平台
     */
    ExpressPlatformVO edit(ExpressPlatformVO expressPlatformVO);

    /**
     * 根据beanid获取快递平台
     *
     * @param bean beanid
     * @return
     */
    ExpressPlatformDO getExpressPlatform(String bean);

    /**
     * 根据快递平台的beanid 获取快递平台的配置项
     *
     * @param bean 快递平台beanid
     * @return 快递平台
     */
    ExpressPlatformVO getExoressConfig(String bean);

    /**
     * 开启某个快递平台
     *
     * @param bean
     */
    void open(String bean);

    /**
     * 查询物流信息
     *
     * @param id 物流公司id
     * @param nu  物流单号
     * @return 物流详细
     */
    ExpressDetailVO getExpressDetail(Integer id, String nu);
}