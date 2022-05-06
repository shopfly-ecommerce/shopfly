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
 * 地区业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-28 13:49:38
 */
public interface RegionsManager {

    /**
     * 添加地区
     *
     * @param regionsVO 地区
     * @return Regions 地区
     */
    Regions add(RegionsVO regionsVO);

    /**
     * 修改地区
     *
     * @param regions 地区
     * @param id      地区主键
     * @return Regions 地区
     */
    Regions edit(Regions regions, Integer id);

    /**
     * 删除地区
     *
     * @param id 地区主键
     */
    void delete(Integer id);

    /**
     * 获取地区
     *
     * @param id 地区主键
     * @return Regions  地区
     */
    Regions getModel(Integer id);

    /**
     * 根据地区id获取其子地区
     *
     * @param regionId 地区id
     * @return 地区集合
     */
    List<Regions> getRegionsChildren(Integer regionId);

    /**
     * 根据深度获取组织地区数据结构的数据
     *
     * @param depth 地区深度
     * @return 地区集合
     */
    List<RegionVO> getRegionByDepth(Integer depth);


}