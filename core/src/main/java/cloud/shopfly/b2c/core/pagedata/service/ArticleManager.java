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
 * 文章业务层
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 10:43:18
 */
public interface ArticleManager {

    /**
     * 查询文章列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @param name
     * @param categoryId
     * @return Page
     */
    Page list(int page, int pageSize, String name, Integer categoryId);

    /**
     * 添加文章
     *
     * @param article 文章
     * @return Article 文章
     */
    Article add(Article article);

    /**
     * 修改文章
     *
     * @param article 文章
     * @param id      文章主键
     * @return Article 文章
     */
    Article edit(Article article, Integer id);

    /**
     * 删除文章
     *
     * @param id 文章主键
     */
    void delete(Integer id);

    /**
     * 获取文章
     *
     * @param id 文章主键
     * @return Article  文章
     */
    Article getModel(Integer id);

    /**
     * 查询某位置的文章
     * @param position
     * @return
     */
    List<Article> listByPosition(String position);

    /**
     * 某分类类型下的文章
     * @param categoryType
     * @return
     */
    List<Article> listByCategoryType(String categoryType);

    /**
     * 获取所有文章id
     * @return
     */
    List<Integer> getAllArticleIds();
}