/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service;

import dev.shopflix.core.member.model.vo.RegionVO;
import dev.shopflix.core.system.model.dos.Regions;
import dev.shopflix.core.system.model.vo.RegionsVO;

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