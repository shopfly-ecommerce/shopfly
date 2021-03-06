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

import cloud.shopfly.b2c.core.goods.GoodsErrorCode;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.dos.ParameterGroupDO;
import cloud.shopfly.b2c.core.goods.model.dos.ParametersDO;
import cloud.shopfly.b2c.core.goods.model.vo.ParameterGroupVO;
import cloud.shopfly.b2c.core.goods.service.CategoryManager;
import cloud.shopfly.b2c.core.goods.service.ParameterGroupManager;
import cloud.shopfly.b2c.core.goods.service.ParametersManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parameter group business class
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 16:14:17
 */
@Service
public class ParameterGroupManagerImpl implements ParameterGroupManager {

    @Autowired
    
    private DaoSupport daoSupport;
    @Autowired
    private ParametersManager parametersManager;
    @Autowired
    private CategoryManager categoryManager;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_parameter_group  ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, ParameterGroupDO.class);

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ParameterGroupDO add(ParameterGroupDO parameterGroup) {

        // Check whether categories exist
        CategoryDO category = categoryManager.getModel(parameterGroup.getCategoryId());
        if (category == null) {
            throw new ServiceException(GoodsErrorCode.E304.code(), "The association classification does not exist");
        }
        String sql = "select * from es_parameter_group where category_id = ? order by sort desc limit 0,1";
        ParameterGroupDO grouptmp = this.daoSupport.queryForObject(sql, ParameterGroupDO.class,
                parameterGroup.getCategoryId());
        if (grouptmp == null) {
            parameterGroup.setSort(1);
        } else {
            parameterGroup.setSort(grouptmp.getSort() + 1);
        }

        this.daoSupport.insert(parameterGroup);
        parameterGroup.setGroupId(this.daoSupport.getLastId(""));
        return parameterGroup;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ParameterGroupDO edit(String groupName, Integer id) {
        ParameterGroupDO group = this.getModel(id);
        if (group == null) {
            throw new ServiceException(GoodsErrorCode.E304.code(), "The parameter group does not exist");
        }
        group.setGroupName(groupName);
        // update
        this.daoSupport.update(group, id);
        return group;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(ParameterGroupDO.class, id);
        // To delete a parameter group, delete all parameters in the parameter group
        parametersManager.deleteByGroup(id);
    }

    @Override
    public ParameterGroupDO getModel(Integer id) {
        return this.daoSupport.queryForObject(ParameterGroupDO.class, id);
    }

    @Override
    public List<ParameterGroupVO> getParamsByCategory(Integer categoryId) {

		// Querying parameter Groups
        String sql = "select * from es_parameter_group where category_id = ? order by sort asc";
        List<ParameterGroupDO> groupList = this.daoSupport.queryForList(sql, ParameterGroupDO.class, categoryId);

        sql = "select p.param_id,p.param_name,p.param_type,p.`options`,p.required,p.group_id,p.is_index "
                + "from es_parameters p where p.category_id = ? order by sort asc";

        List<ParametersDO> paramList = this.daoSupport.queryForList(sql, ParametersDO.class, categoryId);

        List<ParameterGroupVO> resList = this.convertParamList(groupList, paramList);

        return resList;
    }

    /**
     * Assembles the parameter group and the return value of the parameter
     *
     * @param groupList
     * @param paramList
     * @return
     */
    private List<ParameterGroupVO> convertParamList(List<ParameterGroupDO> groupList, List<ParametersDO> paramList) {
        Map<Integer, List<ParametersDO>> map = new HashMap<>(paramList.size());
        for (ParametersDO param : paramList) {

            List<ParametersDO> list = map.get(param.getGroupId());
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(param);
            map.put(param.getGroupId(), list);
        }
        List<ParameterGroupVO> resList = new ArrayList<>();
        for (ParameterGroupDO group : groupList) {
            ParameterGroupVO groupVo = new ParameterGroupVO();
            groupVo.setGroupId(group.getGroupId());
            groupVo.setGroupName(group.getGroupName());
            groupVo.setParams(map.get(group.getGroupId()) == null ? new ArrayList<>() : map.get(group.getGroupId()));
            resList.add(groupVo);
        }
        return resList;
    }

    @Override
    public void groupSort(Integer groupId, String sortType) {

        String sql = "";
        ParameterGroupDO curGroup = this.daoSupport.queryForObject(ParameterGroupDO.class, groupId);
        if (curGroup == null) {
            throw new ServiceException(GoodsErrorCode.E304.code(), "The parameter group does not exist");
        }

        if ("up".equals(sortType)) {
            sql = "select * from es_parameter_group where sort<? and category_id=? order by sort desc limit 0,1";
        } else if ("down".equals(sortType)) {
            sql = "select * from es_parameter_group where sort>? and category_id=? order by sort asc limit 0,1";
        }

        ParameterGroupDO changeGroup = this.daoSupport.queryForObject(sql, ParameterGroupDO.class, curGroup.getSort(),
                curGroup.getCategoryId());

        if(changeGroup != null){
            sql = "update es_parameter_group set sort = ? where group_id = ?";
            this.daoSupport.execute(sql, changeGroup.getSort(), curGroup.getGroupId());
            this.daoSupport.execute(sql, curGroup.getSort(), changeGroup.getGroupId());
        }

    }
}
