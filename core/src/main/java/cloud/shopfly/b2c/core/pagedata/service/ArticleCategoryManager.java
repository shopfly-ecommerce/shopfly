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
 * 文章分类业务层
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-11 15:01:32
 */
public interface ArticleCategoryManager {

    /**
     * 查询文章分类列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @param name     分类名称
     * @return Page
     */
    Page list(int page, int pageSize, String name);

    /**
     * 添加文章分类
     *
     * @param articleCategory 文章分类
     * @return ArticleCategory 文章分类
     */
    ArticleCategory add(ArticleCategory articleCategory);

    /**
     * 修改文章分类
     *
     * @param articleCategory 文章分类
     * @param id              文章分类主键
     * @return ArticleCategory 文章分类
     */
    ArticleCategory edit(ArticleCategory articleCategory, Integer id);

    /**
     * 删除文章分类
     *
     * @param id 文章分类主键
     */
    void delete(Integer id);

    /**
     * 获取文章分类
     *
     * @param id 文章分类主键
     * @return ArticleCategory  文章分类
     */
    ArticleCategory getModel(Integer id);

    /**
     * 查询某分类的子分类
     *
     * @param id
     * @return
     */
    List<ArticleCategory> listChildren(Integer id);

    /**
     * 查询某个分类及相应的文章
     *
     * @param categoryType
     * @return
     */
    ArticleCategoryVO getCategoryAndArticle(String categoryType);

    /**
     * 查询某个分类
     *
     * @param categoryType
     * @return
     */
    ArticleCategory getCategoryByCategoryType(String categoryType);

    /**
     * 获取文章分类树
     *
     * @return 文章分类树
     */
    List<ArticleCategoryVO> getCategoryTree();


}