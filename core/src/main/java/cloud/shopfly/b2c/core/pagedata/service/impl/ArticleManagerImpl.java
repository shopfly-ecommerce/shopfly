/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.pagedata.service.impl;

import cloud.shopfly.b2c.core.pagedata.model.Article;
import cloud.shopfly.b2c.core.pagedata.model.ArticleCategory;
import cloud.shopfly.b2c.core.pagedata.model.enums.ArticleShowPosition;
import cloud.shopfly.b2c.core.pagedata.model.vo.ArticleDetail;
import cloud.shopfly.b2c.core.pagedata.service.ArticleCategoryManager;
import cloud.shopfly.b2c.core.pagedata.service.ArticleManager;
import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.IntegerMapper;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章业务类
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 10:43:18
 */
@Service
public class ArticleManagerImpl implements ArticleManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private ArticleCategoryManager articleCategoryManager;

    @Override
    public Page list(int page, int pageSize, String name, Integer categoryId) {

        StringBuffer sql = new StringBuffer("select a.*,ac.name category_name from es_article a left join es_article_category ac on a.category_id=ac.id  ");
        List<Object> term = new ArrayList<>();

        List<String> condition = new ArrayList<>();

        if (!StringUtil.isEmpty(name)) {
            condition.add(" article_name like ? ");
            term.add("%" + name + "%");
        }
        if (categoryId != null) {
            List<ArticleCategory> articleCategorys = daoSupport.queryForList("select * from es_article_category where parent_id = ?", ArticleCategory.class, categoryId);
            //如果是对底级分类查询 则只需要查询其分类就可以，否则需要将其下级分类数据全部查询出来
            if (articleCategorys.size() <= 0) {
                condition.add(" a.category_id = ?");
                term.add(categoryId);
            } else {
                String symbol = "";
                for (ArticleCategory articleCategory : articleCategorys) {
                    term.add(articleCategory.getId());
                    symbol += "?,";
                }
                symbol = symbol.substring(0, symbol.length() - 1);
                condition.add(" a.category_id in (" + symbol + ")");
            }
        }
        sql.append(SqlUtil.sqlSplicing(condition));
        sql.append(" order by article_id desc");
        Page webPage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, ArticleDetail.class, term.toArray());

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Article add(Article article) {

        article.setShowPosition(ArticleShowPosition.OTHER.name());
        article.setCreateTime(DateUtil.getDateline());
        article.setModifyTime(DateUtil.getDateline());
        this.daoSupport.insert(article);
        article.setArticleId(this.daoSupport.getLastId(""));

        return article;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Article edit(Article article, Integer id) {

        Article art = this.getModel(id);
        if (art == null) {
            throw new ServiceException(SystemErrorCode.E955.code(), "文章不存在，请正确操作");
        }
        article.setShowPosition(art.getShowPosition());
        article.setModifyTime(DateUtil.getDateline());
        this.daoSupport.update(article, id);
        return article;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {

        Article article = this.getModel(id);
        if (article == null || !ArticleShowPosition.valueOf(article.getShowPosition()).equals(ArticleShowPosition.OTHER)) {
            throw new ServiceException(SystemErrorCode.E952.code(), "该文章不可删除，只可修改");
        }

        this.daoSupport.delete(Article.class, id);
    }

    @Override
    public Article getModel(Integer id) {
        return this.daoSupport.queryForObject(Article.class, id);
    }

    @Override
    public List<Article> listByPosition(String position) {

        String sql = "select * from es_article where show_position = ? order by sort";

        return this.daoSupport.queryForList(sql, Article.class, position);
    }

    @Override
    public List<Article> listByCategoryType(String categoryType) {

        ArticleCategory category = this.articleCategoryManager.getCategoryByCategoryType(categoryType);
        if (category == null) {
            return null;
        }
        String sql = "select * from es_article where category_id = ? order by sort";

        return this.daoSupport.queryForList(sql, Article.class, category.getId());
    }

    @Override
    public List<Integer> getAllArticleIds() {
        String sql = "select article_id from es_article order by article_id desc";
        return  daoSupport.queryForList(sql, new IntegerMapper());

    }
}
