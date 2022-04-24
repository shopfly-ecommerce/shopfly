/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service.impl;

import com.google.gson.Gson;
import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.client.system.RegionsClient;
import dev.shopflix.core.member.model.vo.RegionVO;
import dev.shopflix.core.system.model.dos.RateAreaDO;
import dev.shopflix.core.system.model.vo.RateAreaVO;
import dev.shopflix.core.system.service.RateAreaManager;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域服务
 * @author cs
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-28 21:44:49
 */
@Service
public class RateAreaManagerImpl implements RateAreaManager {

    @Autowired
    private DaoSupport daoSupport;

    @Autowired
    private Cache cache;

    @Autowired
    private RegionsClient regionsClient;


    @Override
    public Page list(String name, Integer pageNo, Integer pageSize) {

        StringBuffer sqlBuffer = new StringBuffer("select id,name,create_time from es_rate_area  ");
        List<Object> term = new ArrayList<>();
        if (!StringUtil.isEmpty(name)){
            sqlBuffer.append(" where name like ? ");
            term.add("%"+name+"%");
        }
        sqlBuffer.append(" order by create_time desc");
        return daoSupport.queryForPage(sqlBuffer.toString(),pageNo,pageSize, RateAreaDO.class,term.toArray());
    }

    @Override
    public RateAreaDO add(RateAreaVO rateAreaVO) {

        RateAreaDO rateAreaDO = new RateAreaDO();
        rateAreaDO.setName(rateAreaVO.getName());
        rateAreaDO.setCreateTime(DateUtil.getDateline());

        //获取地区id
        String area = rateAreaVO.getArea();
        rateAreaDO.setArea(area);
        Gson gson = new Gson();
        Map<String, Map> map = new HashMap();
        map = gson.fromJson(area, map.getClass());
        StringBuffer areaIdBuffer = new StringBuffer(",");
        // 获取所有的地区
        Object obj = this.cache.get(CachePrefix.REGIONALL.getPrefix() + 4);
        List<RegionVO> allRegions = null;
        if (obj == null) {
            obj = regionsClient.getRegionByDepth(4);
        }
        allRegions = (List<RegionVO>) obj;
        Map<String, RegionVO> regionsMap = new HashMap();
        //循环地区放到Map中，便于取出
        for (RegionVO region : allRegions) {
            regionsMap.put(region.getId() + "", region);
        }

        for (String key : map.keySet()) {
            //拼接地区id
            areaIdBuffer.append(key + ",");
            Map dto = map.get(key);
            //需要取出改地区下面所有的子地区
            RegionVO provinceRegion = regionsMap.get(key);
            List<RegionVO> cityRegionList = provinceRegion.getChildren();

            Map<String, RegionVO> cityRegionMap = new HashMap<>();
            for (RegionVO city : cityRegionList) {
                cityRegionMap.put(city.getId() + "", city);
            }
            //判断下面的地区是否被全选
            if ((boolean) dto.get("selected_all")) {

                //市
                for (RegionVO cityRegion : cityRegionList) {

                    areaIdBuffer.append(cityRegion.getId() + ",");
                    List<RegionVO> regionList = cityRegion.getChildren();
                    //区
                    for (RegionVO region : regionList) {

                        areaIdBuffer.append(region.getId() + ",");
                        List<RegionVO> townList = region.getChildren();
                        //城镇
                        if (townList != null) {
                            for (RegionVO townRegion : townList) {

                                areaIdBuffer.append(townRegion.getId() + ",");
                            }
                        }
                    }
                }
            } else {
                //没有全选，则看选中城市
                Map<String, Map> citiesMap = (Map<String, Map>) dto.get("children");
                for (String cityKey : citiesMap.keySet()) {

                    areaIdBuffer.append(cityKey + ",");

                    Map cityMap = citiesMap.get(cityKey);

                    RegionVO cityRegion = cityRegionMap.get(cityKey);
                    List<RegionVO> regionList = cityRegion.getChildren();
                    //某个城市如果全部选中，需要取出城市下面的子地区
                    if ((boolean) cityMap.get("selected_all")) {
                        //区
                        for (RegionVO region : regionList) {

                            areaIdBuffer.append(region.getId() + ",");
                            List<RegionVO> townList = region.getChildren();
                            //城镇
                            if (townList != null) {
                                for (RegionVO townRegion : townList) {

                                    areaIdBuffer.append(townRegion.getId() + ",");
                                }
                            }
                        }

                    } else {
                        //选中了某个城市下面的几个区
                        Map<String, Map> regionMap = (Map<String, Map>) cityMap.get("children");
                        for (String regionKey : regionMap.keySet()) {

                            areaIdBuffer.append(regionKey + ",");
                            for (RegionVO region : regionList) {
                                if (("" + region.getId()).equals(regionKey)) {
                                    List<RegionVO> townList = region.getChildren();
                                    //城镇
                                    if (townList != null) {
                                        for (RegionVO townRegion : townList) {

                                            areaIdBuffer.append(townRegion.getId() + ",");
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        rateAreaDO.setAreaId(areaIdBuffer.toString());
        this.daoSupport.insert(rateAreaDO);
        int lastId = daoSupport.getLastId("es_rate_area");
        rateAreaDO.setId(lastId);
        return rateAreaDO;
    }

    @Override
    public RateAreaDO edit(RateAreaVO rateAreaVO) {
        Integer id = rateAreaVO.getId();
        //删除区域
        this.delete(id);
        return this.add(rateAreaVO);
    }

    @Override
    public void delete(Integer rateAreaId) {
        this.daoSupport.execute("delete from es_rate_area where id = ?", rateAreaId);
    }

    @Override
    public RateAreaVO getFromDB(Integer rateAreaId) {
        RateAreaDO rateAreaDO = daoSupport.queryForObject(RateAreaDO.class, rateAreaId);

        RateAreaVO rateAreaVO = new RateAreaVO(rateAreaDO);


        return rateAreaVO;
    }
}