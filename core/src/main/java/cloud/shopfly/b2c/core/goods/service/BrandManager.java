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
 * 品牌业务层
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-16 16:32:45
 */
public interface BrandManager {


	/**
	 * 查询某分类下的品牌
	 *
	 * @param categoryId
	 * @return
	 */
	List<BrandDO> getBrandsByCategory(Integer categoryId);

	/**查询全部的品牌
	 * @return
	 */
	List<BrandDO> getAllBrands();

	/**
	 * 查询品牌列表
	 *
	 * @param page
	 *            页码
	 * @param pageSize
	 *            每页数量
	 * @param name
	 * @return Page
	 */
	Page list(int page, int pageSize, String name);

	/**
	 * 添加品牌
	 *
	 * @param brand
	 *            品牌
	 * @return Brand 品牌
	 */
	BrandDO add(BrandDO brand);

	/**
	 * 修改品牌
	 *
	 * @param brand
	 *            品牌
	 * @param id
	 *            品牌主键
	 * @return Brand 品牌
	 */
	BrandDO edit(BrandDO brand, Integer id);

	/**
	 * 删除品牌
	 *
	 * @param ids
	 *            品牌主键
	 */
	void delete(Integer[] ids);

	/**
	 * 获取品牌
	 *
	 * @param id
	 *            品牌主键
	 * @return Brand 品牌
	 */
	BrandDO getModel(Integer id);

	/**
	 * 查询分类品牌，所有品牌，分类绑定的品牌为已选中状态
	 *
	 * @param categoryId
	 * @return
	 */
	List<SelectVO> getCatBrand(Integer categoryId);


}