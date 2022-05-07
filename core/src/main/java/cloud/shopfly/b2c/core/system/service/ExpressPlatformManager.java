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
 * Express delivery platform business layer
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-11 14:42:50
 */
public interface ExpressPlatformManager {

    /**
     * Query the list of delivery platforms
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Add delivery platform
     *
     * @param expressPlatformVO Delivery platform
     * @return expressPlatformVO Delivery platform
     */
    ExpressPlatformVO add(ExpressPlatformVO expressPlatformVO);

    /**
     * Modify the express delivery platform
     *
     * @param expressPlatformVO Delivery platform
     * @return ExpressPlatformDO Delivery platform
     */
    ExpressPlatformVO edit(ExpressPlatformVO expressPlatformVO);

    /**
     * According to thebeanidObtain express delivery platform
     *
     * @param bean beanid
     * @return
     */
    ExpressPlatformDO getExpressPlatform(String bean);

    /**
     * According to the express platformbeanid Obtain the configuration items of the delivery platform
     *
     * @param bean Delivery platformbeanid
     * @return Delivery platform
     */
    ExpressPlatformVO getExoressConfig(String bean);

    /**
     * Open a delivery platform
     *
     * @param bean
     */
    void open(String bean);

    /**
     * Query logistics Information
     *
     * @param id Logistics companyid
     * @param nu  Logistics single number
     * @return Logistics in detail
     */
    ExpressDetailVO getExpressDetail(Integer id, String nu);
}
