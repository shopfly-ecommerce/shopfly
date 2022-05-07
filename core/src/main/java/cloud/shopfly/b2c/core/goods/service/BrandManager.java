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


import cloud.shopfly.b2c.core.goods.model.dos.BrandDO;
import cloud.shopfly.b2c.core.goods.model.vo.SelectVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Brand business layer
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-16 16:32:45
 */
public interface BrandManager {


	/**
	 * Query brands in a category
	 *
	 * @param categoryId
	 * @return
	 */
	List<BrandDO> getBrandsByCategory(Integer categoryId);

	/**Query all brands
	 * @return
	 */
	List<BrandDO> getAllBrands();

	/**
	 * Query brand list
	 *
	 * @param page
	 *            The page number
	 * @param pageSize
	 *            Number each page
	 * @param name
	 * @return Page
	 */
	Page list(int page, int pageSize, String name);

	/**
	 * Add a brand
	 *
	 * @param brand
	 *            brand
	 * @return Brand brand
	 */
	BrandDO add(BrandDO brand);

	/**
	 * Modify the brand
	 *
	 * @param brand
	 *            brand
	 * @param id
	 *            Brand is the primary key
	 * @return Brand brand
	 */
	BrandDO edit(BrandDO brand, Integer id);

	/**
	 * Delete the brand
	 *
	 * @param ids
	 *            Brand is the primary key
	 */
	void delete(Integer[] ids);

	/**
	 * Access to the brand
	 *
	 * @param id
	 *            Brand is the primary key
	 * @return Brand brand
	 */
	BrandDO getModel(Integer id);

	/**
	 * Query category brands. All brands bound to a category are selected
	 *
	 * @param categoryId
	 * @return
	 */
	List<SelectVO> getCatBrand(Integer categoryId);


}
