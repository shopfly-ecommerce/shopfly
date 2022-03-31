/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service.impl;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.client.goods.GoodsClient;
import dev.shopflix.core.client.system.RegionsClient;
import dev.shopflix.core.goods.model.dos.GoodsDO;
import dev.shopflix.core.member.model.vo.RegionVO;
import dev.shopflix.core.system.SystemErrorCode;
import dev.shopflix.core.system.model.dos.ShipTemplateChild;
import dev.shopflix.core.system.model.dos.ShipTemplateDO;
import dev.shopflix.core.system.model.vo.ShipTemplateChildBuyerVO;
import dev.shopflix.core.system.model.vo.ShipTemplateChildSellerVO;
import dev.shopflix.core.system.model.vo.ShipTemplateSellerVO;
import dev.shopflix.core.system.model.vo.ShipTemplateVO;
import dev.shopflix.core.system.service.ShipTemplateManager;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.exception.ServiceException;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运费模版业务类
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-28 21:44:49
 */
@Service
public class ShipTemplateManagerImpl implements ShipTemplateManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private Cache cache;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private RegionsClient regionsClient;


    @Override
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ShipTemplateDO save(ShipTemplateSellerVO template) {

        ShipTemplateDO t = new ShipTemplateDO();
        BeanUtils.copyProperties(template, t);

        this.daoSupport.insert(t);
        int id = this.daoSupport.getLastId("es_ship_template");
        t.setId(id);
        //保存运费模板子模板
        List<ShipTemplateChildSellerVO> items = template.getItems();

        this.addTemplateChildren(items, id);

        cache.remove(CachePrefix.SHIP_TEMPLATE.getPrefix());
        return t;
    }

    @Override
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ShipTemplateDO edit(ShipTemplateSellerVO template) {
        ShipTemplateDO t = new ShipTemplateDO();
        BeanUtils.copyProperties(template, t);

        Integer id = template.getId();
        this.daoSupport.update(t, id);
        //删除子模板
        this.daoSupport.execute("delete from es_ship_template_child where template_id = ?", id);

        //保存运费模板子模板
        List<ShipTemplateChildSellerVO> items = template.getItems();
        this.addTemplateChildren(items, id);

        //移除缓存某个VO
        this.cache.remove(CachePrefix.SHIP_TEMPLATE_ONE.getPrefix() + id);

        cache.remove(CachePrefix.SHIP_TEMPLATE.getPrefix());

        return t;
    }

    /**
     * 添加运费模板子模板
     */
    private void addTemplateChildren(List<ShipTemplateChildSellerVO> items, Integer templateId) {

        for (ShipTemplateChildSellerVO child : items) {

            ShipTemplateChild shipTemplateChild = new ShipTemplateChild();
            BeanUtils.copyProperties(child, shipTemplateChild);
            shipTemplateChild.setTemplateId(templateId);
            //获取地区id
            String area = child.getArea();

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
            shipTemplateChild.setAreaId(areaIdBuffer.toString());
            this.daoSupport.insert(shipTemplateChild);
        }

    }

    @Override
    public List<ShipTemplateSellerVO> getStoreTemplate() {
        List<ShipTemplateSellerVO> list = (List<ShipTemplateSellerVO>) cache.get(CachePrefix.SHIP_TEMPLATE.getPrefix());
        if (list == null) {
            list = this.daoSupport.queryForList("select * from es_ship_template", ShipTemplateSellerVO.class);

            if (list != null) {
                for (ShipTemplateSellerVO vo : list) {
                    String sql = "select first_company,first_price,continued_company,continued_price,area from es_ship_template_child where template_id = ?";
                    List<ShipTemplateChild> children = this.daoSupport.queryForList(sql, ShipTemplateChild.class, vo.getId());
                    List<ShipTemplateChildSellerVO> items = new ArrayList<>();
                    if (children != null) {
                        for (ShipTemplateChild child : children) {
                            ShipTemplateChildSellerVO childvo = new ShipTemplateChildSellerVO(child, true);
                            items.add(childvo);
                        }
                    }
                    vo.setItems(items);
                }
            }
            cache.put(CachePrefix.SHIP_TEMPLATE.getPrefix(), list);
        }

        return list;
    }

    @Override
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer templateId) {
        GoodsDO goodsDO = this.goodsClient.checkShipTemplate(templateId);
        if (goodsDO != null) {
            throw new ServiceException(SystemErrorCode.E226.code(), "模版被商品【" + goodsDO.getGoodsName() + "】使用，无法删除该模版");
        }

        ShipTemplateDO template = this.getOneDB(templateId);

        //删除运费模板
        this.daoSupport.execute("delete from es_ship_template where id=?", templateId);
        //删除运费模板关联地区
        this.daoSupport.execute("delete from es_ship_template_child where template_id = ?", templateId);

        //移除缓存某个VO
        this.cache.remove(CachePrefix.SHIP_TEMPLATE_ONE.getPrefix() + templateId);
        //移除缓存某商家的VO列表
        this.cache.remove(CachePrefix.SHIP_TEMPLATE.getPrefix());
    }

    @Override
    public ShipTemplateVO getFromCache(Integer templateId) {
        ShipTemplateVO tpl = (ShipTemplateVO) this.cache.get(CachePrefix.SHIP_TEMPLATE_ONE.getPrefix() + templateId);
        if (tpl == null) {
            //编辑运费模板的查询一个运费模板
            ShipTemplateDO template = this.getOneDB(templateId);
            tpl = new ShipTemplateVO();
            BeanUtils.copyProperties(template, tpl);

            //查询运费模板的子模板
            String sql = "select * from es_ship_template_child where template_id = ?";
            List<ShipTemplateChildBuyerVO> children = this.daoSupport.queryForList(sql, ShipTemplateChildBuyerVO.class, templateId);

            tpl.setItems(children);

            cache.put(CachePrefix.SHIP_TEMPLATE_ONE.getPrefix() + templateId, tpl);
        }
        return tpl;

    }

    @Override
    public ShipTemplateSellerVO getFromDB(Integer templateId) {

        ShipTemplateDO template = this.getOneDB(templateId);
        ShipTemplateSellerVO tpl = new ShipTemplateSellerVO();
        BeanUtils.copyProperties(template, tpl);

        //查询运费模板的子模板
        String sql = "select first_company,first_price,continued_company,continued_price,area from es_ship_template_child where template_id = ?";
        List<ShipTemplateChild> children = this.daoSupport.queryForList(sql, ShipTemplateChild.class, templateId);

        List<ShipTemplateChildSellerVO> items = new ArrayList<>();
        if (children != null) {
            for (ShipTemplateChild child : children) {
                ShipTemplateChildSellerVO childvo = new ShipTemplateChildSellerVO(child, false);
                items.add(childvo);
            }
        }

        tpl.setItems(items);

        return tpl;
    }

    /**
     * 数据库中查询运费模板
     *
     * @param templateId
     * @return
     */
    private ShipTemplateDO getOneDB(Integer templateId) {

        return this.daoSupport.queryForObject(ShipTemplateDO.class, templateId);
    }

}
