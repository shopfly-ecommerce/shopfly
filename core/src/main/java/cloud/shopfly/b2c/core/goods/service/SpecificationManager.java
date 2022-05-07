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
 * Specification business layer
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 09:31:27
 */
public interface SpecificationManager {

	/**
	 * Example Query the specification list
	 * 
	 * @param page
	 *            The page number
	 * @param pageSize
	 *            Number each page
	 * @return Page
	 */
	Page list(int page, int pageSize);

	/**
	 * Add specification item
	 * 
	 * @param specification
	 *            Specification items
	 * @return Specification Specification items
	 */
	SpecificationDO add(SpecificationDO specification);

	/**
	 * Modifying Specifications
	 * 
	 * @param specification
	 *            Specification items
	 * @param id
	 *            Primary key of specifications
	 * @return Specification Specification items
	 */
	SpecificationDO edit(SpecificationDO specification, Integer id);

	/**
	 * Deleting specifications
	 * 
	 * @param ids
	 *            Primary key of specifications
	 */
	void delete(Integer[] ids);

	/**
	 * Obtaining specifications
	 * 
	 * @param id
	 *            Primary key of specifications
	 * @return Specification Specification items
	 */
	SpecificationDO getModel(Integer id);

	/**
	 * Query category binding specifications and system specifications
	 * 
	 * @param categoryId
	 * @return
	 */
	List<SelectVO> getCatSpecification(Integer categoryId);

	/**
	 * Merchant queries the specifications of a category
	 *
	 * @param categoryId
	 * @return
	 */
	List<SpecificationVO> querySpec(Integer categoryId);

}
