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
import cloud.shopfly.b2c.core.goods.model.dos.SpecValuesDO;
import cloud.shopfly.b2c.core.goods.model.dos.SpecificationDO;
import cloud.shopfly.b2c.core.goods.model.enums.Permission;
import cloud.shopfly.b2c.core.goods.service.SpecValuesManager;
import cloud.shopfly.b2c.core.goods.service.SpecificationManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Specification value business class
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-20 10:23:53
 */
@Service
public class SpecValuesManagerImpl implements SpecValuesManager {

	@Autowired

	private DaoSupport daoSupport;
	@Autowired
	private SpecificationManager specificationManager;

	@Override
	@Transactional( propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public SpecValuesDO add(SpecValuesDO specValues)	{
		
		this.daoSupport.insert(specValues);
		
		specValues.setSpecValueId(this.daoSupport.getLastId(""));
		
		return specValues;
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public SpecValuesDO edit(SpecValuesDO specValues, Integer id){
		this.daoSupport.update(specValues, id);
		return specValues;
	}
	
	@Override
	public SpecValuesDO getModel(Integer id)	{
		return this.daoSupport.queryForObject(SpecValuesDO.class, id);
	}

	@Override
	public List<SpecValuesDO> listBySpecId(Integer specId, Permission permission) {
		
		StringBuffer sql = new StringBuffer(" select * from es_spec_values where spec_id = ? ");
		
		List<SpecValuesDO> list = this.daoSupport.queryForList(sql.toString(), SpecValuesDO.class, specId);
		
		return list;
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public List<SpecValuesDO> saveSpecValue(Integer specId, String[] valueList) {

		// Check whether specifications exist
		SpecificationDO spec = specificationManager.getModel(specId);
		if(spec == null){
			throw new ServiceException(GoodsErrorCode.E306.code(),"The owning specification does not exist");
		}
		String sql = "delete from es_spec_values where spec_id=? ";

		this.daoSupport.execute(sql, specId);
		List<SpecValuesDO> res = new ArrayList<>();
		for (String value : valueList) {
			SpecValuesDO specValue = new SpecValuesDO( specId, value);
			specValue.setSpecName(spec.getSpecName());
			this.daoSupport.insert(specValue);
			specValue.setSpecId(this.daoSupport.getLastId(""));
			res.add(specValue);
		}
		return res;
		
	}
}
