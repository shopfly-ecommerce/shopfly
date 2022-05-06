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
package cloud.shopfly.b2c.core.goods.service;

import cloud.shopfly.b2c.core.goods.model.dos.SpecificationDO;
import cloud.shopfly.b2c.core.goods.model.vo.SelectVO;
import cloud.shopfly.b2c.core.goods.model.vo.SpecificationVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * 规格项业务层
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 09:31:27
 */
public interface SpecificationManager {

	/**
	 * 查询规格项列表
	 * 
	 * @param page
	 *            页码
	 * @param pageSize
	 *            每页数量
	 * @return Page
	 */
	Page list(int page, int pageSize);

	/**
	 * 添加规格项
	 * 
	 * @param specification
	 *            规格项
	 * @return Specification 规格项
	 */
	SpecificationDO add(SpecificationDO specification);

	/**
	 * 修改规格项
	 * 
	 * @param specification
	 *            规格项
	 * @param id
	 *            规格项主键
	 * @return Specification 规格项
	 */
	SpecificationDO edit(SpecificationDO specification, Integer id);

	/**
	 * 删除规格项
	 * 
	 * @param ids
	 *            规格项主键
	 */
	void delete(Integer[] ids);

	/**
	 * 获取规格项
	 * 
	 * @param id
	 *            规格项主键
	 * @return Specification 规格项
	 */
	SpecificationDO getModel(Integer id);

	/**
	 * 查询分类绑定的规格，系统规格
	 * 
	 * @param categoryId
	 * @return
	 */
	List<SelectVO> getCatSpecification(Integer categoryId);

	/**
	 * 商家查询某分类的规格
	 *
	 * @param categoryId
	 * @return
	 */
	List<SpecificationVO> querySpec(Integer categoryId);

}