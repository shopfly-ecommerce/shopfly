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

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.system.model.dos.RateAreaDO;
import cloud.shopfly.b2c.core.system.model.dos.ShipTemplateDO;
import cloud.shopfly.b2c.core.system.model.dos.ShipTemplateSettingDO;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateSellerVO;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateSettingVO;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateVO;
import cloud.shopfly.b2c.core.system.service.ShipTemplateManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.system.model.vo.*;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ShipTemplateDO save(ShipTemplateSellerVO template) {

        ShipTemplateDO t = new ShipTemplateDO();
        BeanUtils.copyProperties(template, t);

        this.daoSupport.insert(t);
        int id = this.daoSupport.getLastId("es_ship_template");
        t.setId(id);
        //保存运费模板子模板
        List<ShipTemplateSettingVO> items = template.getItems();

        this.addTemplateChildren(items, id);

        cache.remove(CachePrefix.SHIP_TEMPLATE.getPrefix());
        return t;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ShipTemplateDO edit(ShipTemplateSellerVO template) {
        ShipTemplateDO t = new ShipTemplateDO();
        BeanUtils.copyProperties(template, t);

        Integer id = template.getId();
        this.daoSupport.update(t, id);
        //删除子模板
        this.daoSupport.execute("delete from es_ship_template_setting where template_id = ?", id);

        //保存运费模板子模板
        List<ShipTemplateSettingVO> items = template.getItems();
        this.addTemplateChildren(items, id);

        //移除缓存某个VO
        this.cache.remove(CachePrefix.SHIP_TEMPLATE_ONE.getPrefix() + id);

        cache.remove(CachePrefix.SHIP_TEMPLATE.getPrefix());

        return t;
    }

    /**
     * 添加运费模板子模板
     */
    private void addTemplateChildren(List<ShipTemplateSettingVO> items, Integer templateId) {

        List<Integer> rateAreaIds = Lists.newArrayList();
        for (ShipTemplateSettingVO settingVO : items) {
                rateAreaIds.add(settingVO.getRateAreaId());
        }

        List term = new ArrayList<>();
        String idsStr = SqlUtil.getInSql(rateAreaIds.toArray(new Integer[rateAreaIds.size()]), term);

        //检测是否有分类关联
        String sql = "select id,name from es_rate_area where id in (" + idsStr + ")";
        List<RateAreaDO> areaDOList = this.daoSupport.queryForList(sql,RateAreaDO.class,term.toArray());
        for (ShipTemplateSettingVO settingVO : items) {
            List<ShipTemplateSettingDO> voItems = settingVO.getItems();
            RateAreaDO areaDO = areaDOList.stream().filter(rateAreaDO -> rateAreaDO.getId().equals(settingVO.getRateAreaId())).findFirst().get();
            for (ShipTemplateSettingDO  settingDO:voItems) {
                settingDO.setTemplateId(templateId);
                settingDO.setRateAreaId(settingVO.getRateAreaId());
                settingDO.setRateAreaName(areaDO.getName());
                this.daoSupport.insert(settingDO);
            }
        }
    }

    @Override
    public List<ShipTemplateSellerVO> getStoreTemplate() {
        List<ShipTemplateSellerVO> list = (List<ShipTemplateSellerVO>) cache.get(CachePrefix.SHIP_TEMPLATE.getPrefix());
        if (list == null) {
            list = this.daoSupport.queryForList("select * from es_ship_template", ShipTemplateSellerVO.class);

            if (list != null) {
                for (ShipTemplateSellerVO vo : list) {
                    String sql = "select * from es_ship_template_setting where template_id = ?";
                    List<ShipTemplateSettingDO> settingDOList = this.daoSupport.queryForList(sql, ShipTemplateSettingDO.class, vo.getId());
                    List<ShipTemplateSettingVO> items = convert(settingDOList,true);
                    vo.setItems(items);
                }
            }
            cache.put(CachePrefix.SHIP_TEMPLATE.getPrefix(), list);
        }

        return list;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer templateId) {
        GoodsDO goodsDO = this.goodsClient.checkShipTemplate(templateId);
        if (goodsDO != null) {
            throw new ServiceException(SystemErrorCode.E226.code(), "模版被商品【" + goodsDO.getGoodsName() + "】使用，无法删除该模版");
        }


        //删除运费模板
        this.daoSupport.execute("delete from es_ship_template where id=?", templateId);
        //删除运费模板关联地区
        this.daoSupport.execute("delete from es_ship_template_setting where template_id = ?", templateId);

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
            String sql = "select * from es_ship_template_setting where template_id = ?";
            List<ShipTemplateSettingDO> settingDOList = this.daoSupport.queryForList(sql, ShipTemplateSettingDO.class, templateId);

            List<ShipTemplateSettingVO> items = convert(settingDOList,true);
            tpl.setItems(items);

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
        String sql = "select * from es_ship_template_setting where template_id = ?";
        List<ShipTemplateSettingDO> settingDOList = this.daoSupport.queryForList(sql, ShipTemplateSettingDO.class, templateId);

        List<ShipTemplateSettingVO> items = convert(settingDOList,true);

        tpl.setItems(items);

        return tpl;
    }

    @Override
    public Integer getCountByAreaId(Integer rateAreaId) {

        return daoSupport.queryForInt("select count(0) from es_ship_template_setting where rate_area_id = ? ",rateAreaId);
    }

    @Override
    public void removeCache(Integer rateAreaId) {
        List<ShipTemplateSettingDO> list= daoSupport.queryForList("select distinct template_id from es_ship_template_setting where rate_area_id = ?",ShipTemplateSettingDO.class,rateAreaId);
        if (list!=null&&list.size()>0){
            for (ShipTemplateSettingDO settingDO : list) {
                //移除缓存某个VO
                this.cache.remove(CachePrefix.SHIP_TEMPLATE_ONE.getPrefix() + settingDO.getTemplateId());
            }
            //移除缓存某商家的VO列表
            this.cache.remove(CachePrefix.SHIP_TEMPLATE.getPrefix());
        }
    }

    /**
     *
     * ShipTemplateSettingDO在转换VO，增加地区相关信息
     * @param settingDOList
     * @param flag
     * @return
     */
    private List<ShipTemplateSettingVO> convert(List<ShipTemplateSettingDO> settingDOList,boolean flag) {
        if (settingDOList==null||settingDOList.size()==0){
            return null;
        }
        String sql;
        List<ShipTemplateSettingVO> items = new ArrayList<>();
        if (settingDOList != null) {
            List<Integer> rateAreaIds = Lists.newArrayList();
            for (ShipTemplateSettingDO settingDO:settingDOList) {
                if (rateAreaIds.contains(settingDO.getRateAreaId())){
                    continue;
                }
                rateAreaIds.add(settingDO.getRateAreaId());
            }
            List term = new ArrayList<>();

            String idsStr = SqlUtil.getInSql(rateAreaIds.toArray(new Integer[rateAreaIds.size()]), term);

            //检测是否有分类关联
            sql = "select * from es_rate_area where id in (" + idsStr + ")";
            List<RateAreaDO> areaDOList = this.daoSupport.queryForList(sql,RateAreaDO.class, term.toArray());

            for (Integer rateAreaId:rateAreaIds) {
                RateAreaDO rateAreaDO = areaDOList.stream().filter(rateArea -> rateArea.getId().equals(rateAreaId)).findFirst().get();
                List<ShipTemplateSettingDO> dos = settingDOList.stream().filter(settingDO -> settingDO.getRateAreaId().equals(rateAreaId)).collect(Collectors.toList());
                ShipTemplateSettingVO settingVO = new ShipTemplateSettingVO(dos,rateAreaDO,flag);
                items.add(settingVO);
            }
        }
        return items;
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
