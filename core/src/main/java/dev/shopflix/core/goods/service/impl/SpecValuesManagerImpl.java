/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.service.impl;

import dev.shopflix.core.goods.GoodsErrorCode;
import dev.shopflix.core.goods.model.dos.SpecValuesDO;
import dev.shopflix.core.goods.model.dos.SpecificationDO;
import dev.shopflix.core.goods.model.enums.Permission;
import dev.shopflix.core.goods.service.SpecValuesManager;
import dev.shopflix.core.goods.service.SpecificationManager;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 规格值业务类
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

		//查询规格是否存在
		SpecificationDO spec = specificationManager.getModel(specId);
		if(spec == null){
			throw new ServiceException(GoodsErrorCode.E306.code(),"所属规格不存在");
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
