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


import cloud.shopfly.b2c.core.goods.model.dos.CategoryBrandDO;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.dos.CategorySpecDO;
import cloud.shopfly.b2c.core.goods.model.vo.CategoryVO;

import java.util.List;

/**
 * Commodity classification business layer
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-15 17:22:06
 */
public interface CategoryManager {

    /**
     * Example Query subcategories of a category
     *
     * @param parentId
     * @param format
     * @return
     */
    List list(Integer parentId, String format);

    /**
     * Query all categories, parent - child relationships
     *
     * @param parentId
     * @return
     */
    List<CategoryVO> listAllChildren(Integer parentId);

    /**
     * Get product categories
     *
     * @param id Merchandise category primary key
     * @return CategoryDO Category
     */
    CategoryDO getModel(Integer id);

    /**
     * Gets a category under a category
     *
     * @param categoryId
     * @return
     */
    List<CategoryDO> getCategory(Integer categoryId);

    /**
     * Adding an item category
     *
     * @param category
     *            Category
     * @return Category Category
     */
    CategoryDO add(CategoryDO category);

    /**
     * Modification of commodity classification
     *
     * @param category
     *            Category
     * @param id
     *            Merchandise category primary key
     * @return Category Category
     */
    CategoryDO edit(CategoryDO category, Integer id);

    /**
     * Delete product Categories
     *
     * @param id
     *            Merchandise category primary key
     */
    void delete(Integer id);

    /**
     * Save the brand bound by category
     *
     * @param categoryId
     * @param chooseBrands
     * @return
     */
    List<CategoryBrandDO> saveBrand(Integer categoryId, Integer[] chooseBrands);

    /**
     * Save the category binding specifications
     *
     * @param categoryId
     * @param chooseSpecs
     * @return
     */
    List<CategorySpecDO> saveSpec(Integer categoryId, Integer[] chooseSpecs);


}
