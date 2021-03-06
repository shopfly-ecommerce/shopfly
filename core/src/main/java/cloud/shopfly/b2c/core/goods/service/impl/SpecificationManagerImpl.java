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
import cloud.shopfly.b2c.core.goods.model.dos.CategorySpecDO;
import cloud.shopfly.b2c.core.goods.model.dos.SpecValuesDO;
import cloud.shopfly.b2c.core.goods.model.dos.SpecificationDO;
import cloud.shopfly.b2c.core.goods.model.vo.SelectVO;
import cloud.shopfly.b2c.core.goods.model.vo.SpecificationVO;
import cloud.shopfly.b2c.core.goods.service.CategoryManager;
import cloud.shopfly.b2c.core.goods.service.SpecificationManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Specification business class
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-20 09:31:27
 */
@Service
public class SpecificationManagerImpl implements SpecificationManager {

    @Autowired
    
    private DaoSupport daoSupport;
    @Autowired
    private CategoryManager categoryManager;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_specification  where disabled = 1 order by spec_id desc";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, SpecificationDO.class);

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SpecificationDO add(SpecificationDO specification) {


        // If the specifications are added by the management end, verify that the names of the management end are the same
        String sql = "select * from es_specification  where disabled = 1 and spec_name = ? ";
        List list = this.daoSupport.queryForList(sql, specification.getSpecName());

        if (list.size() > 0) {
            throw new ServiceException(GoodsErrorCode.E305.code(), "Duplicate specification name");
        }


        specification.setDisabled(1);
        this.daoSupport.insert(specification);
        specification.setSpecId(this.daoSupport.getLastId(""));

        return specification;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SpecificationDO edit(SpecificationDO specification, Integer id) {

        SpecificationDO model = this.getModel(id);
        if (model == null) {
            throw new ServiceException(GoodsErrorCode.E305.code(), "Specifications do not exist");
        }

        String sql = "select * from es_specification  where disabled = 1 and spec_name = ? and spec_id!=? ";
        List list = this.daoSupport.queryForList(sql, specification.getSpecName(),id);

        if (list.size() > 0) {
            throw new ServiceException(GoodsErrorCode.E305.code(), "Duplicate specification name");
        }

        this.daoSupport.update(specification, id);
        return specification;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer[] ids) {

        List<Object> term = new ArrayList<>();
        String idsStr = SqlUtil.getInSql(ids, term);
        // Check whether a category has been bound to this specification
        String sql = "select * from es_category_spec where spec_id in (" + idsStr + ")";
        List<CategorySpecDO> list = this.daoSupport.queryForList(sql, CategorySpecDO.class, term.toArray());
        if (list.size() > 0) {

            throw new ServiceException(GoodsErrorCode.E305.code(), "A category has been bound to specifications to be deleted. Unbind the category specifications first");
        }

        sql = " update es_specification set disabled = 0 where spec_id in (" + idsStr + ")";

        this.daoSupport.execute(sql, term.toArray());
    }

    @Override
    public SpecificationDO getModel(Integer id) {

        return this.daoSupport.queryForObject(SpecificationDO.class, id);
    }

    @Override
    public List<SelectVO> getCatSpecification(Integer categoryId) {

        String sql = "select s.spec_id id,s.spec_name text,  "
                + "case category_id when ? then true else false end selected  "
                + "from es_specification s left join  es_category_spec cs "
                + "on s.spec_id=cs.spec_id and category_id=? where s.disabled=1";

        return this.daoSupport.queryForList(sql, SelectVO.class, categoryId, categoryId);
    }


    @Override
    public List<SpecificationVO> querySpec(Integer categoryId) {
        // The query specification
        String sql = "select s.spec_id,s.spec_name "
                + "from es_specification s inner join es_category_spec cs on s.spec_id=cs.spec_id "
                + "where cs.category_id = ? ";
        List<SpecificationVO> specList = this.daoSupport.queryForList(sql, SpecificationVO.class, categoryId);

        // No specification
        if (specList == null || specList.size() == 0) {
            return new ArrayList<>();
        }
        // A collection of encapsulation specification ids
        String[] temp = new String[specList.size()];
        List<Object> specIdList = new ArrayList<>();

        for (int i = 0; i < specList.size(); i++) {
            specIdList.add(specList.get(i).getSpecId());
            temp[i] = "?";
        }
        String str = StringUtil.arrayToString(temp, ",");

        String sqlValue = "select * from es_spec_values where spec_id in (" + str + ")";
        // The values of all specifications are displayed
        List<SpecValuesDO> valueList = this.daoSupport.queryForList(sqlValue, SpecValuesDO.class, specIdList.toArray());

        Map<Integer, List<SpecValuesDO>> map = new HashMap<>(valueList.size());
        for (SpecValuesDO specValue : valueList) {

            List<SpecValuesDO> list = map.get(specValue.getSpecId());
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(specValue);
            map.put(specValue.getSpecId(), list);
        }
        // Assign a specification value
        for (SpecificationVO vo : specList) {
            vo.setValueList(map.get(vo.getSpecId()));
        }

        return specList;

    }
}
