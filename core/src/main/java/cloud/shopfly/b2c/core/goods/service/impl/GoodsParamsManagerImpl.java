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
package cloud.shopfly.b2c.core.goods.service.impl;

import cloud.shopfly.b2c.core.goods.model.dos.GoodsParamsDO;
import cloud.shopfly.b2c.core.goods.model.dos.ParameterGroupDO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsParamsGroupVO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsParamsVO;
import cloud.shopfly.b2c.core.goods.service.GoodsParamsManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Product parameters
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month21On the afternoon5:30:11
 */
@Service
public class GoodsParamsManagerImpl implements GoodsParamsManager {

    @Autowired
    
    private DaoSupport daoSupport;


    @Override
    public List<GoodsParamsGroupVO> queryGoodsParams(Integer categoryId, Integer goodsId) {

        String sql = "select * from es_parameter_group where category_id = ?";
        // Querying parameter Groups
        List<ParameterGroupDO> groupList = this.daoSupport.queryForList(sql, ParameterGroupDO.class, categoryId);
        sql = "select p.*,gp.param_value,p.group_id "
                + "from es_parameters p "
                + "left join es_goods_params gp on p.param_id=gp.param_id and gp.goods_id = ?  where p.category_id = ?"
                + " order by sort ";

        List<GoodsParamsVO> paramList = this.daoSupport.queryForList(sql, GoodsParamsVO.class, goodsId, categoryId);

        List<GoodsParamsGroupVO> resList = this.convertParamList(groupList, paramList);

        return resList;
    }

    @Override
    public List<GoodsParamsGroupVO> queryGoodsParams(Integer categoryId) {

        String sql = "select * from es_parameter_group where category_id = ?";
        // Querying parameter Groups
        List<ParameterGroupDO> groupList = this.daoSupport.queryForList(sql, ParameterGroupDO.class, categoryId);
        sql = "select * from es_parameters where category_id = ? order by sort ";

        List<GoodsParamsVO> paramList = this.daoSupport.queryForList(sql, GoodsParamsVO.class, categoryId);

        List<GoodsParamsGroupVO> resList = this.convertParamList(groupList, paramList);

        return resList;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addParams(List<GoodsParamsDO> paramList, Integer goodsId) {
        String sql = "delete from es_goods_params where goods_id = ?";
        this.daoSupport.execute(sql, goodsId);

        if (paramList != null) {
            for (GoodsParamsDO param : paramList) {
                param.setGoodsId(goodsId);
                this.daoSupport.insert(param);
            }
        }
    }

    /**
     * Assemble return value
     *
     * @param groupList
     * @param paramList
     * @return
     */
    private List<GoodsParamsGroupVO> convertParamList(List<ParameterGroupDO> groupList, List<GoodsParamsVO> paramList) {
        Map<Integer, List<GoodsParamsVO>> map = new HashMap<>(16);
        for (GoodsParamsVO param : paramList) {
            if (map.get(param.getGroupId()) != null) {
                map.get(param.getGroupId()).add(param);
            } else {
                List<GoodsParamsVO> list = new ArrayList<>();
                list.add(param);
                map.put(param.getGroupId(), list);
            }
        }
        List<GoodsParamsGroupVO> resList = new ArrayList<>();
        for (ParameterGroupDO group : groupList) {
            GoodsParamsGroupVO list = new GoodsParamsGroupVO();
            list.setGroupName(group.getGroupName());
            list.setGroupId(group.getGroupId());
            list.setParams(map.get(group.getGroupId()));
            resList.add(list);
        }
        return resList;
    }
}
