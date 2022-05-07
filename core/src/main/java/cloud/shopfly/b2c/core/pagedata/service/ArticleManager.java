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

import cloud.shopfly.b2c.core.pagedata.model.Article;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Article Business Layer
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 10:43:18
 */
public interface ArticleManager {

    /**
     * Query the list of articles
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @param name
     * @param categoryId
     * @return Page
     */
    Page list(int page, int pageSize, String name, Integer categoryId);

    /**
     * Add the article
     *
     * @param article The article
     * @return Article The article
     */
    Article add(Article article);

    /**
     * Modify the article
     *
     * @param article The article
     * @param id      The article primary key
     * @return Article The article
     */
    Article edit(Article article, Integer id);

    /**
     * Delete articles
     *
     * @param id The article primary key
     */
    void delete(Integer id);

    /**
     * Access to the article
     *
     * @param id The article primary key
     * @return Article  The article
     */
    Article getModel(Integer id);

    /**
     * Query for articles in a location
     * @param position
     * @return
     */
    List<Article> listByPosition(String position);

    /**
     * An article under a certain classification type
     * @param categoryType
     * @return
     */
    List<Article> listByCategoryType(String categoryType);

    /**
     * Get all articlesid
     * @return
     */
    List<Integer> getAllArticleIds();
}
