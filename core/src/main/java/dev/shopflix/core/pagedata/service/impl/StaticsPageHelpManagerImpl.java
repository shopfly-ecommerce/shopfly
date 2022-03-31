/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.pagedata.service.impl;

import dev.shopflix.core.pagedata.service.StaticsPageHelpManager;
import dev.shopflix.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 静态页面实现
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-17 下午3:27
 */
@Service
public class StaticsPageHelpManagerImpl implements StaticsPageHelpManager {

    @Autowired

    private DaoSupport daoSupport;

    /**
     * 获取帮助页面总数
     *
     * @return
     */
    @Override
    public Integer count() {
        return this.daoSupport.queryForInt("select count(0) from es_article");
    }

    /**
     * 分页获取帮助
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List helpList(Integer page, Integer pageSize) {
        return this.daoSupport.queryForListPage("select article_id as id from es_article",page,pageSize);
    }
}
