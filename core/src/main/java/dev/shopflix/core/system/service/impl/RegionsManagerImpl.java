/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service.impl;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.member.model.vo.RegionVO;
import dev.shopflix.core.system.SystemErrorCode;
import dev.shopflix.core.system.model.dos.Regions;
import dev.shopflix.core.system.model.vo.RegionsVO;
import dev.shopflix.core.system.service.RegionsManager;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.exception.ResourceNotFoundException;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 地区业务类
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-28 13:49:38
 */
@Service
public class RegionsManagerImpl implements RegionsManager {

    @Autowired
    @Qualifier("systemDaoSupport")
    private DaoSupport systemDaoSupport;
    @Autowired
    private Cache cache;

    @Override
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Regions add(RegionsVO regionsVO) {

        //添加地区名称，不允许重复
        String sql = "select * from es_regions where local_name = ? ";
        List list = this.systemDaoSupport.queryForList(sql, regionsVO.getLocalName());
        if (list.size() > 0) {
            throw new ServiceException(SystemErrorCode.E805.code(), "此地区名称重复");
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
                throw new ResourceNotFoundException("当前地区父地区id无效");
            }
            regionPath = p.getRegionPath() + regionId + ",";
        } else {
            regionPath = "," + regionId + ",";
        }
        //对地区级别进行处理
        String subreg = regionPath.substring(0, regionPath.length() - 1);
        subreg = subreg.substring(1);
        String[] regs = subreg.split(",");
        regions.setRegionGrade(regs.length);
        regions.setRegionPath(regionPath);
        //修改地区
        return this.edit(regions, regionId);
    }

    @Override
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Regions edit(Regions regions, Integer id) {

        //添加地区名称，不允许重复
        String sql = "select * from es_regions where local_name = ? and id !=? ";
        List list = this.systemDaoSupport.queryForList(sql, regions.getLocalName(), id);
        if (list.size() > 0) {
            throw new ServiceException(SystemErrorCode.E805.code(), "此地区名称重复");
        }

        Regions parentRegion = this.getModel(regions.getParentId());
        if (!regions.getParentId().equals(0) && parentRegion == null) {
            throw new ResourceNotFoundException("当前地区父地区id无效");
        }
        //修改地区信息
        this.systemDaoSupport.update(regions, id);
        //修改是否支持货到付款到下级地区
        this.systemDaoSupport.execute("update es_regions set cod = ? where region_path like (?)", regions.getCod(), "%" + regions.getId() + "%");
        //清除地区缓存
        this.clearRegionsCache();
        return regions;
    }

    @Override
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        //清除缓存
        this.clearRegionsCache();
        Regions regions = this.getModel(id);
        if (regions == null) {
            throw new ResourceNotFoundException("该地区不存在");
        }
        this.systemDaoSupport.execute("delete from es_regions where region_path like '%," + id + ",%'");
    }

    @Override
    public Regions getModel(Integer id) {
        return this.systemDaoSupport.queryForObject(Regions.class, id);
    }

    /**
     * 删除地区缓存
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
            throw new ResourceNotFoundException("此地区不存在");
        }
        Integer depth = 0;
        if (!regionId.equals(0)) {
            depth = region.getRegionGrade();
        }
        //因为需要查找下一级，所以需要将深度+1
        depth = depth + 1;
        Object obj = this.cache.get(CachePrefix.REGIONLIDEPTH.getPrefix() + depth);
        List<Regions> regions = null;
        // 如果为空的话需要重数据库中查出数据 然后放入缓存
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
        //如果深度大于4级，则修改深度为最深4级
        if (depth > 4) {
            depth = 4;
        }
        Object obj = this.cache.get(CachePrefix.REGIONALL.getPrefix() + depth);
        List<RegionVO> regions = null;
        //如果从缓存中拿到数据则直接取值，否则需要放到缓存中一份在返回取值
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
     * 根据深度，从数据库获取组织好树结构的地区数据
     *
     * @param depth 深度
     * @return 地区集合
     */
    private List<RegionVO> getAll(Integer depth) {
        String sql = "select * from  es_regions";
        List<Regions> data = this.systemDaoSupport.queryForList(sql.toString(), Regions.class);
        List<RegionVO> tree = new ArrayList<>();
        this.sort(1, depth, tree, data);
        return tree;
    }

    /**
     * 负责递归的停止
     *
     * @param level 标示
     * @param depth 深度
     * @param tree  新的树结构
     * @param data  原始数据
     */
    private void sort(int level, int depth, List<RegionVO> tree, List<Regions> data) {
        if (level + 1 > depth) {
            // 如果是第一级的情况直接返回
            if (depth == 1) {
                for (Regions regions : data) {
                    if (regions.getParentId() == 0) {
                        tree.add(regions.toVo());
                    }
                }
            }
            return;
        }
        // 如果为0 则代表初始化 初始化顶级数据
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
     * 负责树结构的创建
     *
     * @param level 标示
     * @param tree  树结构
     * @param data  原始数据
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
