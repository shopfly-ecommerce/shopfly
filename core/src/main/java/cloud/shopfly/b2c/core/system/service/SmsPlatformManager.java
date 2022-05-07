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

import cloud.shopfly.b2c.core.system.model.dos.SmsPlatformDO;
import cloud.shopfly.b2c.core.system.model.vo.SmsPlatformVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * SMS gateway table service layer
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 11:31:05
 */
public interface SmsPlatformManager {

    /**
     * Example Query the SMS gateway list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Example Add the SMS gateway table
     *
     * @param smsPlatform SMS gatewayvo
     * @return
     */
    SmsPlatformVO add(SmsPlatformVO smsPlatform);

    /**
     * Example Add the SMS gateway table
     *
     * @param smsPlatform SMS gatewayvo
     * @return
     */
    SmsPlatformVO edit(SmsPlatformVO smsPlatform);


    /**
     * Obtain the SMS gateway table
     *
     * @param id Primary key of the SMS gateway table
     * @return Platform  SMS Gateway Table
     */
    SmsPlatformDO getModel(Integer id);


    /**
     * According to thebeanidObtaining the SMS Gateway
     *
     * @param bean beanid
     * @return
     */
    SmsPlatformDO getSmsPlateform(String bean);

    /**
     * To enable the gateway
     *
     * @param bean SMS gatewaybeanid
     */
    void openPlatform(String bean);

    /**
     * According to the SMS gatewaybeanid Obtain the configuration items of the SMS gateway
     *
     * @param bean SMS gatewaybeanid
     * @return SMS gatewayVO
     */
    SmsPlatformVO getConfig(String bean);

    /**
     * Obtain the enabled SMS gateway
     *
     * @return SMS gatewayVO
     */
    SmsPlatformVO getOpen();

}
