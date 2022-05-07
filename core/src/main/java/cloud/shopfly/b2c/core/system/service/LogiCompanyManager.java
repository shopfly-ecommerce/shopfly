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
 * Logistics company business layer
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-29 15:10:38
 */
public interface LogiCompanyManager {

    /**
     * Query the list of logistics companies
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Add logistics Company
     *
     * @param logi Logistics company
     * @return Logi Logistics company
     */
    LogiCompanyDO add(LogiCompanyDO logi);

    /**
     * Modify logistics company
     *
     * @param logi Logistics company
     * @param id   Logistics company primary key
     * @return Logi Logistics company
     */
    LogiCompanyDO edit(LogiCompanyDO logi, Integer id);

    /**
     * Delete logistics company
     *
     * @param id Logistics company primary key
     */
    void delete(Integer[] id);

    /**
     * Acquisition logistics company
     *
     * @param id Logistics company primary key
     * @return Logi  Logistics company
     */
    LogiCompanyDO getModel(Integer id);

    /**
     * throughcodeAcquisition logistics company
     *
     * @param code Logistics companycode
     * @return Logistics company
     */
    LogiCompanyDO getLogiByCode(String code);

    /**
     * Bird Logistics via express deliverycodeAcquisition logistics company
     *
     * @param kdcode Express Bird Companycode
     * @return Logistics company
     */
    LogiCompanyDO getLogiBykdCode(String kdcode);

    /**
     * Query logistics information by logistics name
     *
     * @param name The name of the logistics
     * @return Logistics company
     */
    LogiCompanyDO getLogiByName(String name);

    /**
     * Query the list of logistics companies(No paging)
     *
     * @return Page
     */
    List<LogiCompanyDO> list();

}
