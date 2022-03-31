/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.pagedata.service.impl;

import dev.shopflix.core.pagedata.model.ArticleCategory;
import dev.shopflix.core.pagedata.model.enums.ArticleCategoryType;
import dev.shopflix.core.pagedata.model.vo.ArticleCategoryVO;
import dev.shopflix.core.pagedata.model.vo.ArticleVO;
import dev.shopflix.core.pagedata.service.ArticleCategoryManager;
import dev.shopflix.core.system.SystemErrorCode;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.SqlUtil;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章分类业务类
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
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ArticleCategory add(ArticleCategory articleCategory) {

        if (articleCategory.getParentId() == null) {
            articleCategory.setParentId(0);
        }
        // 非顶级分类
        ArticleCategory parent = null;
        if (articleCategory.getParentId() != 0) {
            parent = this.getModel(articleCategory.getParentId());
            if (parent == null) {
                throw new ServiceException(SystemErrorCode.E951.code(), "父分类不存在");
            }
            // 替换catPath 根据path规则来匹配级别
            String catPath = parent.getPath().replace("|", ",");
            String[] str = catPath.split(",");
            if (str.length >= 3) {
                throw new ServiceException(SystemErrorCode.E951.code(), "最多为二级分类,添加失败");
            }
        }
        articleCategory.setAllowDelete(1);
        articleCategory.setType(ArticleCategoryType.OTHER.name());
        //验证分类名称是否重复
        String sql = "select * from es_article_category where name = ? ";
        List list = this.daoSupport.queryForList(sql, articleCategory.getName());
        if (list.size() > 0) {
            throw new ServiceException(SystemErrorCode.E951.code(), "分类名称重复");
        }

        this.daoSupport.insert(articleCategory);

        int categoryId = this.daoSupport.getLastId("");
        articleCategory.setId(categoryId);

        if (parent != null) {
            articleCategory.setPath(parent.getPath() + categoryId + "|");
        } else {// 是顶级类别
            articleCategory.setPath("0|" + categoryId + "|");
        }

        sql = "update es_article_category set  path = ? where  id = ?";
        this.daoSupport.execute(sql, articleCategory.getPath(), categoryId);

        return articleCategory;
    }

    @Override
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ArticleCategory edit(ArticleCategory articleCategory, Integer id) {

        ArticleCategory cat = this.getModel(id);
        //只有类型为other的,才可以修改
        if (!ArticleCategoryType.OTHER.name().equals(cat.getType())) {
            throw new ServiceException(SystemErrorCode.E950.code(), "特殊的文章分类，不可修改");
        }

        if (articleCategory.getParentId() == null) {
            articleCategory.setParentId(0);
        }

        articleCategory.setPath(0 + "|" + cat.getId() + "|");

        // 非顶级分类
        if (articleCategory.getParentId() != 0) {
            ArticleCategory parent = this.getModel(articleCategory.getParentId());
            if (parent == null) {
                throw new ServiceException(SystemErrorCode.E951.code(), "父分类不存在");
            }
            // 替换catPath 根据path规则来匹配级别
            String catPath = parent.getPath().replace("|", ",");
            String[] str = catPath.split(",");
            if (str.length >= 3) {
                throw new ServiceException(SystemErrorCode.E951.code(), "最多为二级分类,修改失败");
            }

            articleCategory.setPath(parent.getPath() + cat.getId() + "|");
        }

        articleCategory.setAllowDelete(1);
        articleCategory.setType(ArticleCategoryType.OTHER.name());

        //验证分类名称是否重复
        String sql = "select * from es_article_category where name = ? and id != ?";
        List list = this.daoSupport.queryForList(sql,articleCategory.getName(), id);
        if (list.size() > 0) {
            throw new ServiceException(SystemErrorCode.E951.code(), "分类名称重复");
        }

        this.daoSupport.update(articleCategory, id);

        return articleCategory;
    }

    @Override
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        ArticleCategory cat = this.getModel(id);
        //只有类型为other的,才可以删除
        if (cat == null || !ArticleCategoryType.OTHER.name().equals(cat.getType())) {
            throw new ServiceException(SystemErrorCode.E950.code(), "特殊的文章分类，不可删除");
        }
        //查看文章分类下是否有分类
        List<ArticleCategory> children = this.listChildren(id);
        if (children.size() > 0) {
            throw new ServiceException(SystemErrorCode.E950.code(), "该文章分类下存在子分类，不能删除");
        }

        //查看文章分类下是否有文章，如果有文章存在则不能删除该分类
        String sql = "select * from es_article where category_id = ?";
        List list = this.daoSupport.queryForList(sql, id);
        if (list.size() > 0) {
            throw new ServiceException(SystemErrorCode.E950.code(), "该文章分类下存在文章，不能删除");
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
        //顶级分类
        ArticleCategoryVO articleCategory = this.daoSupport.queryForObject(sql, ArticleCategoryVO.class, categoryType);
        List<ArticleCategory> list = this.listChildren(articleCategory.getId());
        //子分类
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
        //分类下的文章
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
     * 在一个集合中查找子
     *
     * @param categoryList 所有分类集合
     * @param parentid     父id
     * @return 找到的子集合
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
