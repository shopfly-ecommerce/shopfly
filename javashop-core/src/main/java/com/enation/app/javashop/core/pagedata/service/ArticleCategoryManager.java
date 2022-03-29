/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.pagedata.service;

import com.enation.app.javashop.core.pagedata.model.ArticleCategory;
import com.enation.app.javashop.core.pagedata.model.vo.ArticleCategoryVO;
import com.enation.app.javashop.framework.database.Page;

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