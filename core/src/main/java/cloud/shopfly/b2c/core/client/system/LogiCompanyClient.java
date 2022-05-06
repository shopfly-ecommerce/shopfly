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
package cloud.shopfly.b2c.core.client.system;

import cloud.shopfly.b2c.core.system.model.dos.LogiCompanyDO;

import java.util.List;

/**
 * @version v7.0
 * @Description:
 * @Author: zjp
 * @Date: 2018/7/26 14:17
 */
public interface LogiCompanyClient {
    /**
     * 通过code获取物流公司
     * @param code 物流公司code
     * @return 物流公司
     */
    LogiCompanyDO getLogiByCode(String code);

    /**
     * 获取物流公司
     * @param id 物流公司主键
     * @return Logi  物流公司
     */
    LogiCompanyDO getModel(Integer id);

    /**
     * 查询物流公司列表(不分页)
     * @return Page
     */
    List<LogiCompanyDO> list();
}
