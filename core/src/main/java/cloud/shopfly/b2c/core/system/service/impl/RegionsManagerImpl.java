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
package cloud.shopfly.b2c.core.system.service.impl;

import cloud.shopfly.b2c.core.member.model.vo.RegionVO;
import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.system.model.dos.Regions;
import cloud.shopfly.b2c.core.system.model.vo.RegionsVO;
import cloud.shopfly.b2c.core.system.service.RegionsManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Regional business class
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-28 13:49:38
 */
@Service
public class RegionsManagerImpl implements RegionsManager {

    @Autowired
    
    private DaoSupport systemDaoSupport;
    @Autowired
    private Cache cache;

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Regions add(RegionsVO regionsVO) {

        // Add a region name that cannot be duplicated
        String sql = "select * from es_regions where local_name = ? ";
        List list = this.systemDaoSupport.queryForList(sql, regionsVO.getLocalName());
        if (list.size() > 0) {
            throw new ServiceException(SystemErrorCode.E805.code(), "This region has a duplicate name");
        }

        Regions regions = new Regions();
        BeanUtil.copyProperties(regionsVO, regions);
        this.systemDaoSupport.insert("es_regions", regions);
        String regionPath = "";
        int regionId = this.systemDaoSupport.getLastId("es_regions");
        regions = getModel(regionId);
        if (regions.getParentId() != null && regions.getParentId() != 0) {
            Regions p = getModel(regions.getParentId());
            if (p == null) {
                throw new ResourceNotFoundException("Parent region of the current regionidinvalid");
            }
            regionPath = p.getRegionPath() + regionId + ",";
        } else {
            regionPath = "," + regionId + ",";
        }
        // Process the region level
        String subreg = regionPath.substring(0, regionPath.length() - 1);
        subreg = subreg.substring(1);
        String[] regs = subreg.split(",");
        regions.setRegionGrade(regs.length);
        regions.setRegionPath(regionPath);
        // Modify the area
        return this.edit(regions, regionId);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Regions edit(Regions regions, Integer id) {

        // Add a region name that cannot be duplicated
        String sql = "select * from es_regions where local_name = ? and id !=? ";
        List list = this.systemDaoSupport.queryForList(sql, regions.getLocalName(), id);
        if (list.size() > 0) {
            throw new ServiceException(SystemErrorCode.E805.code(), "This region has a duplicate name");
        }

        Regions parentRegion = this.getModel(regions.getParentId());
        if (!regions.getParentId().equals(0) && parentRegion == null) {
            throw new ResourceNotFoundException("Parent region of the current regionidinvalid");
        }
        // Modifying Region Information
        this.systemDaoSupport.update(regions, id);
        // Changed whether cash on delivery to sub-regions is supported
        this.systemDaoSupport.execute("update es_regions set cod = ? where region_path like (?)", regions.getCod(), "%" + regions.getId() + "%");
        // Clear the local cache
        this.clearRegionsCache();
        return regions;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        // Clear the cache
        this.clearRegionsCache();
        Regions regions = this.getModel(id);
        if (regions == null) {
            throw new ResourceNotFoundException("The region does not exist");
        }
        this.systemDaoSupport.execute("delete from es_regions where region_path like '%," + id + ",%'");
    }

    @Override
    public Regions getModel(Integer id) {
        return this.systemDaoSupport.queryForObject(Regions.class, id);
    }

    /**
     * Deleting the local cache
     */
    private void clearRegionsCache() {
        for (int i = 1; i <= 4; i++) {
            this.cache.remove(CachePrefix.REGIONLIDEPTH.getPrefix() + i);
            this.cache.remove(CachePrefix.REGIONALL.getPrefix() + i);
        }
    }


    @Override
    public List<Regions> getRegionsChildren(Integer regionId) {
        Regions region = this.getModel(regionId);
        if (region == null && regionId != 0) {
            throw new ResourceNotFoundException("This region does not exist");
        }
        Integer depth = 0;
        if (!regionId.equals(0)) {
            depth = region.getRegionGrade();
        }
        // Because we need to find the next level, we need to increase the depth by 1
        depth = depth + 1;
        Object obj = this.cache.get(CachePrefix.REGIONLIDEPTH.getPrefix() + depth);
        List<Regions> regions = null;
        // If it is empty, you need to retrieve the data from the database and put it in the cache
        if (obj == null) {
            for (int i = 1; i <= 4; i++) {
                List<Regions> rgs = this.systemDaoSupport.queryForList("select * from es_regions where region_grade = ?", Regions.class, i);
                this.cache.put(CachePrefix.REGIONLIDEPTH.getPrefix() + i, rgs);
                if (i == depth) {
                    regions = rgs;
                }
            }
        } else {
            regions = (List<Regions>) obj;
        }
        List<Regions> rgs = new ArrayList<>();
        for (int i = 0; i < regions.size(); i++) {
            if (regions.get(i).getParentId().equals(regionId)) {
                rgs.add(regions.get(i));
            }
        }
        return rgs;
    }


    @Override
    public List<RegionVO> getRegionByDepth(Integer depth) {
        // If the depth is greater than level 4, change the depth to level 4
        if (depth > 4) {
            depth = 4;
        }
        Object obj = this.cache.get(CachePrefix.REGIONALL.getPrefix() + depth);
        List<RegionVO> regions = null;
        // If the data is retrieved from the cache, the value is directly evaluated. Otherwise, you need to put it in the cache and return the value
        if (obj != null) {
            regions = (List<RegionVO>) obj;
        } else {
            List<RegionVO> rgs = this.getAll(depth);
            this.cache.put(CachePrefix.REGIONALL.getPrefix() + depth, rgs);
            regions = rgs;
        }
        return regions;
    }

    /**
     * Get tree-structured regional data from the database based on depth
     *
     * @param depth The depth of the
     * @return In the collection
     */
    private List<RegionVO> getAll(Integer depth) {
        String sql = "select * from  es_regions";
        List<Regions> data = this.systemDaoSupport.queryForList(sql.toString(), Regions.class);
        List<RegionVO> tree = new ArrayList<>();
        this.sort(1, depth, tree, data);
        return tree;
    }

    /**
     * Responsible for stopping recursion
     *
     * @param level mark
     * @param depth The depth of the
     * @param tree  New tree structure
     * @param data  The original data
     */
    private void sort(int level, int depth, List<RegionVO> tree, List<Regions> data) {
        if (level + 1 > depth) {
            // If its level one, it goes straight back
            if (depth == 1) {
                for (Regions regions : data) {
                    if (regions.getParentId() == 0) {
                        tree.add(regions.toVo());
                    }
                }
            }
            return;
        }
        // A value of 0 initializes top-level data
        if (level == 1) {
            for (Regions regions : data) {
                if (regions.getParentId() == 0) {
                    tree.add(regions.toVo());
                }
            }
        }
        this.recursion(level, tree, data);
        level++;
        this.sort(level, depth, tree, data);
    }

    /**
     * Responsible for creating the tree structure
     *
     * @param level mark
     * @param tree  Tree structure
     * @param data  The original data
     */
    private void recursion(int level, List<RegionVO> tree, List<Regions> data) {
        for (RegionVO vo : tree) {
            if (vo.getLevel() != level) {
                if (vo.getChildren().size() != 0) {
                    this.recursion(level, vo.getChildren(), data);
                }
                continue;
            }
            for (Regions regions : data) {
                if (regions.getParentId().equals(vo.getId())) {
                    vo.getChildren().add(regions.toVo());
                }
            }
        }
    }

}
