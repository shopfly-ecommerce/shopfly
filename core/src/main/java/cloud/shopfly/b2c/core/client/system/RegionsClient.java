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

import cloud.shopfly.b2c.core.member.model.vo.RegionVO;
import cloud.shopfly.b2c.core.system.model.dos.Regions;

import java.util.List;

/**
 * @version v7.0
 * @Description: regionClient
 * @Author: zjp
 * @Date: 2018/7/27 11:14
 */
public interface RegionsClient {
    /**
     * According to the regionidObtain subregion
     *
     * @param regionId regionid
     * @return In the collection
     */
    List<Regions> getRegionsChildren(Integer regionId);

    /**
     * Access to areas
     *
     * @param id In the primary key
     * @return Regions  region
     */
    Regions getModel(Integer id);

    /**
     * Obtain data from the data structure of the organization area based on depth
     *
     * @param depth In the depth of the
     * @return In the collection
     */
    List<RegionVO> getRegionByDepth(Integer depth);

}
