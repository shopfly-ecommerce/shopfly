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

import cloud.shopfly.b2c.core.system.model.dos.RateAreaDO;
import cloud.shopfly.b2c.core.system.model.vo.AreaVO;
import cloud.shopfly.b2c.core.system.model.vo.RateAreaVO;
import cloud.shopfly.b2c.core.system.service.RateAreaManager;
import cloud.shopfly.b2c.core.system.service.ShipTemplateManager;
import com.alibaba.fastjson.JSON;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Regional service
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
    private ShipTemplateManager shipTemplateManager;



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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, ServiceException.class})
    public RateAreaDO add(RateAreaVO rateAreaVO) {

        RateAreaDO rateAreaDO = new RateAreaDO();
        convertDO(rateAreaVO, rateAreaDO);
        this.daoSupport.insert(rateAreaDO);
        int lastId = daoSupport.getLastId("es_rate_area");
        rateAreaDO.setId(lastId);
        return rateAreaDO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, ServiceException.class})
    public RateAreaDO edit(RateAreaVO rateAreaVO) {
        Integer id = rateAreaVO.getId();
        RateAreaDO rateAreaDO = new RateAreaDO();
        convertDO(rateAreaVO, rateAreaDO);
        rateAreaDO.setId(id);
        this.daoSupport.update(rateAreaDO,id);
        shipTemplateManager.removeCache(id);
        return rateAreaDO;
    }

    private void convertDO(RateAreaVO rateAreaVO, RateAreaDO rateAreaDO) {
        rateAreaDO.setName(rateAreaVO.getName());
        rateAreaDO.setCreateTime(DateUtil.getDateline());

        // Obtaining the region ID
        List<AreaVO> areas = rateAreaVO.getAreas();
        rateAreaDO.setAreaJson(JSON.toJSONString(areas));

        StringBuffer areaIdBuffer = new StringBuffer(",");
        StringBuffer areaBuffer = new StringBuffer(",");
        for (AreaVO vo : areas) {
            areaIdBuffer.append(vo.getCode()).append(",");
            areaBuffer.append(vo.getName()).append(",");
            if (vo.getChildren() != null && vo.getChildren().size() > 0) {
                for (AreaVO child : vo.getChildren()) {
                    areaIdBuffer.append(child.getCode()).append(",");
                    areaBuffer.append(child.getName()).append(",");
                }
            }
        }
        rateAreaDO.setAreaId(areaIdBuffer.toString());
        rateAreaDO.setArea(areaBuffer.toString());
    }

    @Override
    public void delete(Integer rateAreaId) {
        Integer count = shipTemplateManager.getCountByAreaId(rateAreaId);
        if (count!=null && count>0){
            throw new ServiceException("500","The rate area is in used");
        }
        this.daoSupport.execute("delete from es_rate_area where id = ?", rateAreaId);

    }

    @Override
    public RateAreaVO getFromDB(Integer rateAreaId) {
        RateAreaDO rateAreaDO = daoSupport.queryForObject(RateAreaDO.class, rateAreaId);

        if (rateAreaDO==null){
            return null;
        }

        RateAreaVO rateAreaVO = new RateAreaVO(rateAreaDO);


        return rateAreaVO;
    }
}
