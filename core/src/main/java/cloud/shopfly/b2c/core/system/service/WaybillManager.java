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

import cloud.shopfly.b2c.core.system.model.dos.WayBillDO;
import cloud.shopfly.b2c.core.system.model.vo.WayBillVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Electronic plane single business layer
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-06-08 16:26:05
 */
public interface WaybillManager {

    /**
     * Query the electronic plane single column list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Add electron sheet
     *
     * @param wayBill Electronic surface single
     * @return WayBillDO Electronic surface single
     */
    WayBillDO add(WayBillVO wayBill);

    /**
     * Modify the electronic sheet
     *
     * @param wayBill Electronic surface single
     * @return WayBillDO Electronic surface single
     */
    WayBillVO edit(WayBillVO wayBill);

    /**
     * Get electron surface list
     *
     * @param id Electron face single primary bond
     * @return WayBillDO  Electronic surface single
     */
    WayBillDO getModel(Integer id);

    /**
     * Turn on the electronic plane
     *
     * @param bean beanid
     */
    void open(String bean);


    /**
     * According to thebeanidObtain electron plane single scheme
     *
     * @param bean
     * @return
     */
    WayBillDO getWayBillByBean(String bean);


    /**
     * According to thebeanidObtain electron plane single scheme
     *
     * @param bean beanid
     * @return Electronic surface singlevo
     */
    WayBillVO getWaybillConfig(String bean);

    /**
     * Generate an electron plane
     *
     * @param orderSn Order no.
     * @param logiId  Logistics companyid
     * @return
     */
    String createPrintData(String orderSn, Integer logiId);

}
