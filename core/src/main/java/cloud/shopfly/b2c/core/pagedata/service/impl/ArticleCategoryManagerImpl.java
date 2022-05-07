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
package cloud.shopfly.b2c.core.pagedata.service.impl;

import cloud.shopfly.b2c.core.pagedata.model.ArticleCategory;
import cloud.shopfly.b2c.core.pagedata.model.enums.ArticleCategoryType;
import cloud.shopfly.b2c.core.pagedata.model.vo.ArticleCategoryVO;
import cloud.shopfly.b2c.core.pagedata.model.vo.ArticleVO;
import cloud.shopfly.b2c.core.pagedata.service.ArticleCategoryManager;
import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Article classification business class
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-11 15:01:32
 */
@Service
public class ArticleCategoryManagerImpl implements ArticleCategoryManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public Page list(int page, int pageSize, String name) {

        StringBuffer sql = new StringBuffer("select * from es_article_category  where parent_id = 0");
        List<Object> term = new ArrayList<>();
        if (!StringUtil.isEmpty(name)) {
            sql.append(" and name like ?");
            term.add("%" + name + "%");
        }

        sql.append(" order by allow_delete ");
        Page webPage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, ArticleCategory.class, term.toArray());

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ArticleCategory add(ArticleCategory articleCategory) {

        if (articleCategory.getParentId() == null) {
            articleCategory.setParentId(0);
        }
        // Non-top-level classification
        ArticleCategory parent = null;
        if (articleCategory.getParentId() != 0) {
            parent = this.getModel(articleCategory.getParentId());
            if (parent == null) {
                throw new ServiceException(SystemErrorCode.E951.code(), "The parent category does not exist");
            }
            // Replace catPath to match levels according to the PATH rule
            String catPath = parent.getPath().replace("|", ",");
            String[] str = catPath.split(",");
            if (str.length >= 3) {
                throw new ServiceException(SystemErrorCode.E951.code(), "Secondary classification at most,Add failure");
            }
        }
        articleCategory.setAllowDelete(1);
        articleCategory.setType(ArticleCategoryType.OTHER.name());
        // Verify that category names are duplicated
        String sql = "select * from es_article_category where name = ? ";
        List list = this.daoSupport.queryForList(sql, articleCategory.getName());
        if (list.size() > 0) {
            throw new ServiceException(SystemErrorCode.E951.code(), "Classification name duplication");
        }

        this.daoSupport.insert(articleCategory);

        int categoryId = this.daoSupport.getLastId("");
        articleCategory.setId(categoryId);

        if (parent != null) {
            articleCategory.setPath(parent.getPath() + categoryId + "|");
        } else {// Is the top-level category
            articleCategory.setPath("0|" + categoryId + "|");
        }

        sql = "update es_article_category set  path = ? where  id = ?";
        this.daoSupport.execute(sql, articleCategory.getPath(), categoryId);

        return articleCategory;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ArticleCategory edit(ArticleCategory articleCategory, Integer id) {

        ArticleCategory cat = this.getModel(id);
        // Only the value of the type is other can be modified
        if (!ArticleCategoryType.OTHER.name().equals(cat.getType())) {
            throw new ServiceException(SystemErrorCode.E950.code(), "Special article classification, cannot be modified");
        }

        if (articleCategory.getParentId() == null) {
            articleCategory.setParentId(0);
        }

        articleCategory.setPath(0 + "|" + cat.getId() + "|");

        // Non-top-level classification
        if (articleCategory.getParentId() != 0) {
            ArticleCategory parent = this.getModel(articleCategory.getParentId());
            if (parent == null) {
                throw new ServiceException(SystemErrorCode.E951.code(), "The parent category does not exist");
            }
            // Replace catPath to match levels according to the PATH rule
            String catPath = parent.getPath().replace("|", ",");
            String[] str = catPath.split(",");
            if (str.length >= 3) {
                throw new ServiceException(SystemErrorCode.E951.code(), "Secondary classification at most,Modify the failure");
            }

            articleCategory.setPath(parent.getPath() + cat.getId() + "|");
        }

        articleCategory.setAllowDelete(1);
        articleCategory.setType(ArticleCategoryType.OTHER.name());

        // Verify that category names are duplicated
        String sql = "select * from es_article_category where name = ? and id != ?";
        List list = this.daoSupport.queryForList(sql,articleCategory.getName(), id);
        if (list.size() > 0) {
            throw new ServiceException(SystemErrorCode.E951.code(), "Classification name duplication");
        }

        this.daoSupport.update(articleCategory, id);

        return articleCategory;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        ArticleCategory cat = this.getModel(id);
        // Only the value whose type is other can be deleted
        if (cat == null || !ArticleCategoryType.OTHER.name().equals(cat.getType())) {
            throw new ServiceException(SystemErrorCode.E950.code(), "Special article categories cannot be deleted");
        }
        // See if there is a category under the article category
        List<ArticleCategory> children = this.listChildren(id);
        if (children.size() > 0) {
            throw new ServiceException(SystemErrorCode.E950.code(), "There are subcategories under the classification of this article, which cannot be deleted");
        }

        // Check whether there are articles under the article category. If there are articles, the category cannot be deleted
        String sql = "select * from es_article where category_id = ?";
        List list = this.daoSupport.queryForList(sql, id);
        if (list.size() > 0) {
            throw new ServiceException(SystemErrorCode.E950.code(), "There are articles under this article category and cannot be deleted");
        }
        this.daoSupport.delete(ArticleCategory.class, id);
    }

    @Override
    public ArticleCategory getModel(Integer id) {
        return this.daoSupport.queryForObject(ArticleCategory.class, id);
    }


    @Override
    public List<ArticleCategory> listChildren(Integer id) {

        String sql = "select * from es_article_category  where parent_id = ? order by sort";

        return this.daoSupport.queryForList(sql, ArticleCategory.class, id);
    }

    @Override
    public ArticleCategoryVO getCategoryAndArticle(String categoryType) {

        String sql = "select * from es_article_category  where type = ?";
        // Top classification
        ArticleCategoryVO articleCategory = this.daoSupport.queryForObject(sql, ArticleCategoryVO.class, categoryType);
        List<ArticleCategory> list = this.listChildren(articleCategory.getId());
        // A subclass
        List<ArticleCategoryVO> children = new ArrayList<>();
        Integer[] catIds = null;
        if (StringUtil.isNotEmpty(list)) {
            catIds = new Integer[list.size()];
            int i = 0;
            for (ArticleCategory cat : list) {
                ArticleCategoryVO catVO = new ArticleCategoryVO();
                BeanUtils.copyProperties(cat, catVO);
                children.add(catVO);
                catIds[i] = cat.getId();
                i++;
            }
        }
        // Articles under the classification
        if (catIds != null) {

            List<Object> terms = new ArrayList<>();
            String str = SqlUtil.getInSql(catIds, terms);
            sql = "select * from es_article where category_id in (" + str + ")";
            List<ArticleVO> articleList = this.daoSupport.queryForList(sql, ArticleVO.class, terms.toArray());
            if (StringUtil.isNotEmpty(articleList)) {
                Map<Integer, List<ArticleVO>> map = new HashMap<>(16);
                for (ArticleVO article : articleList) {
                    List<ArticleVO> values = map.get(article.getCategoryId());
                    if (values == null) {
                        values = new ArrayList<>();
                    }
                    values.add(article);
                    map.put(article.getCategoryId(), values);
                }

                for (ArticleCategoryVO cat : children) {
                    cat.setArticles(map.get(cat.getId()));
                }

            }

        }

        articleCategory.setChildren(children);

        return articleCategory;
    }

    @Override
    public ArticleCategory getCategoryByCategoryType(String categoryType) {

        String sql = "select * from es_article_category  where type = ?";
        List<ArticleCategory> list = this.daoSupport.queryForList(sql, ArticleCategory.class, categoryType);
        if (StringUtil.isNotEmpty(list)) {
            return list.get(0);
        }

        return null;
    }


    @Override
    public List<ArticleCategoryVO> getCategoryTree() {
        List<ArticleCategoryVO> categoryList = this.daoSupport.queryForList("select * from es_article_category order by id asc", ArticleCategoryVO.class);
        List<ArticleCategoryVO> newCategoryList = new ArrayList<ArticleCategoryVO>();
        for (ArticleCategoryVO category : categoryList) {
            if (category.getParentId().equals(0)) {
                List<ArticleCategoryVO> children = this.getChildren(categoryList, category.getId());
                category.setChildren(children);
                newCategoryList.add(category);
            }
        }
        return newCategoryList;
    }

    /**
     * Find children in a collection
     *
     * @param categoryList All classification set
     * @param parentid     The fatherid
     * @return Subset found
     */
    private List<ArticleCategoryVO> getChildren(List<ArticleCategoryVO> categoryList, Integer parentid) {
        List<ArticleCategoryVO> children = new ArrayList<ArticleCategoryVO>();
        for (ArticleCategoryVO category : categoryList) {
            if (category.getParentId().compareTo(parentid) == 0) {
                category.setChildren(this.getChildren(categoryList, category.getId()));
                children.add(category);
            }
        }
        return children;
    }
}
