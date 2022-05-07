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
package cloud.shopfly.b2c.core.statistics.service;

import cloud.shopfly.b2c.core.statistics.model.dos.GoodsPageView;

import java.util.List;

/**
 * visitsmanager
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/8/7 In the morning8:21
 */

public interface DisplayTimesManager {


    /**
     * Accessing an address
     *
     * @param url  Address of access
     * @param uuid The customer onlyid
     */
    void view(String url, String uuid);

    /**
     * Write statistical commodity data to the database
     *
     * @param list
     */
    void countGoods(List<GoodsPageView> list);


    /**
     * Organize existing data immediately
     */
    void countNow();


}
