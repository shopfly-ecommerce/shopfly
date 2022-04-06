/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.service.impl;

import dev.shopflix.core.goods.GoodsErrorCode;
import dev.shopflix.core.goods.model.dos.CategoryDO;
import dev.shopflix.core.goods.model.dos.ParameterGroupDO;
import dev.shopflix.core.goods.model.dos.ParametersDO;
import dev.shopflix.core.goods.model.vo.ParameterGroupVO;
import dev.shopflix.core.goods.service.CategoryManager;
import dev.shopflix.core.goods.service.ParameterGroupManager;
import dev.shopflix.core.goods.service.ParametersManager;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;
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
 * 参数组业务类
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

        // 查看分类是否存在
        CategoryDO category = categoryManager.getModel(parameterGroup.getCategoryId());
        if (category == null) {
            throw new ServiceException(GoodsErrorCode.E304.code(), "关联分类不存在");
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
            throw new ServiceException(GoodsErrorCode.E304.code(), "参数组不存在");
        }
        group.setGroupName(groupName);
        // 更新
        this.daoSupport.update(group, id);
        return group;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(ParameterGroupDO.class, id);
        // 删除参数组，需要将参数组下的参数同时删除
        parametersManager.deleteByGroup(id);
    }

    @Override
    public ParameterGroupDO getModel(Integer id) {
        return this.daoSupport.queryForObject(ParameterGroupDO.class, id);
    }

    @Override
    public List<ParameterGroupVO> getParamsByCategory(Integer categoryId) {

		//查询参数组
        String sql = "select * from es_parameter_group where category_id = ? order by sort asc";
        List<ParameterGroupDO> groupList = this.daoSupport.queryForList(sql, ParameterGroupDO.class, categoryId);

        sql = "select p.param_id,p.param_name,p.param_type,p.`options`,p.required,p.group_id,p.is_index "
                + "from es_parameters p where p.category_id = ? order by sort asc";

        List<ParametersDO> paramList = this.daoSupport.queryForList(sql, ParametersDO.class, categoryId);

        List<ParameterGroupVO> resList = this.convertParamList(groupList, paramList);

        return resList;
    }

    /**
     * 拼装参数组和参数的返回值
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
            throw new ServiceException(GoodsErrorCode.E304.code(), "参数组不存在");
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
