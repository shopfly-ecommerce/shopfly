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
     * throughcodeAcquisition logistics company
     * @param code Logistics companycode
     * @return Logistics company
     */
    LogiCompanyDO getLogiByCode(String code);

    /**
     * Acquisition logistics company
     * @param id Logistics company primary key
     * @return Logi  Logistics company
     */
    LogiCompanyDO getModel(Integer id);

    /**
     * Query the list of logistics companies(No paging)
     * @return Page
     */
    List<LogiCompanyDO> list();
}
