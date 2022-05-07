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
package cloud.shopfly.b2c.core.pagedata.service;

import cloud.shopfly.b2c.core.pagedata.model.ArticleCategory;
import cloud.shopfly.b2c.core.pagedata.model.vo.ArticleCategoryVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * This paper classifies the business layer
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-11 15:01:32
 */
public interface ArticleCategoryManager {

    /**
     * Query the article category list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @param name     name
     * @return Page
     */
    Page list(int page, int pageSize, String name);

    /**
     * Add article categories
     *
     * @param articleCategory The article classification
     * @return ArticleCategory The article classification
     */
    ArticleCategory add(ArticleCategory articleCategory);

    /**
     * Modify article categories
     *
     * @param articleCategory The article classification
     * @param id              Article category primary key
     * @return ArticleCategory The article classification
     */
    ArticleCategory edit(ArticleCategory articleCategory, Integer id);

    /**
     * Delete article categories
     *
     * @param id Article category primary key
     */
    void delete(Integer id);

    /**
     * Get article classification
     *
     * @param id Article category primary key
     * @return ArticleCategory  The article classification
     */
    ArticleCategory getModel(Integer id);

    /**
     * Example Query the subcategories of a category
     *
     * @param id
     * @return
     */
    List<ArticleCategory> listChildren(Integer id);

    /**
     * Query a category and corresponding articles
     *
     * @param categoryType
     * @return
     */
    ArticleCategoryVO getCategoryAndArticle(String categoryType);

    /**
     * Querying a category
     *
     * @param categoryType
     * @return
     */
    ArticleCategory getCategoryByCategoryType(String categoryType);

    /**
     * Gets the article classification tree
     *
     * @return Article classification tree
     */
    List<ArticleCategoryVO> getCategoryTree();


}
