/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.system;

import dev.shopflix.core.member.model.vo.RegionVO;
import dev.shopflix.core.system.model.dos.Regions;

import java.util.List;

/**
 * @version v7.0
 * @Description: 地区Client
 * @Author: zjp
 * @Date: 2018/7/27 11:14
 */
public interface RegionsClient {
    /**
     * 根据地区id获取其子地区
     *
     * @param regionId 地区id
     * @return 地区集合
     */
    List<Regions> getRegionsChildren(Integer regionId);

    /**
     * 获取地区
     *
     * @param id 地区主键
     * @return Regions  地区
     */
    Regions getModel(Integer id);

    /**
     * 根据深度获取组织地区数据结构的数据
     *
     * @param depth 地区深度
     * @return 地区集合
     */
    List<RegionVO> getRegionByDepth(Integer depth);

}
