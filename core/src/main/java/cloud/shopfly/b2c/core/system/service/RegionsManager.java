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

import cloud.shopfly.b2c.core.member.model.vo.RegionVO;
import cloud.shopfly.b2c.core.system.model.dos.Regions;
import cloud.shopfly.b2c.core.system.model.vo.RegionsVO;

import java.util.List;

/**
 * Regional business layer
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-28 13:49:38
 */
public interface RegionsManager {

    /**
     * Add region
     *
     * @param regionsVO region
     * @return Regions region
     */
    Regions add(RegionsVO regionsVO);

    /**
     * Modify the area
     *
     * @param regions region
     * @param id      In the primary key
     * @return Regions region
     */
    Regions edit(Regions regions, Integer id);

    /**
     * Delete the region
     *
     * @param id In the primary key
     */
    void delete(Integer id);

    /**
     * Access to areas
     *
     * @param id In the primary key
     * @return Regions  region
     */
    Regions getModel(Integer id);

    /**
     * According to the regionidObtain subregion
     *
     * @param regionId regionid
     * @return In the collection
     */
    List<Regions> getRegionsChildren(Integer regionId);

    /**
     * Obtain data from the data structure of the organization area based on depth
     *
     * @param depth In the depth of the
     * @return In the collection
     */
    List<RegionVO> getRegionByDepth(Integer depth);


}
