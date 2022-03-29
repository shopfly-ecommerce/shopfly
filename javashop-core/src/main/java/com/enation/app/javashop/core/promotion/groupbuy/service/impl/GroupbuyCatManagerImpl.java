/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.groupbuy.service.impl;

import com.enation.app.javashop.core.promotion.PromotionErrorCode;
import com.enation.app.javashop.core.promotion.groupbuy.model.dos.GroupbuyCatDO;
import com.enation.app.javashop.core.promotion.groupbuy.service.GroupbuyCatManager;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 团购分类业务类
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 16:08:03
 */
@Service
public class GroupbuyCatManagerImpl implements GroupbuyCatManager {

    @Autowired
    @Qualifier("tradeDaoSupport")
    private DaoSupport daoSupport;

    @Override
    public Page list(Integer pageNo, Integer pageSize) {
        String sql = "select * from es_groupbuy_cat  ";
        Page webPage = this.daoSupport.queryForPage(sql, pageNo, pageSize, GroupbuyCatDO.class);
        return webPage;
    }

    @Override
    public List<GroupbuyCatDO> getList(Integer parentId) {
        String sql = "select * from es_groupbuy_cat where parent_id = ? order by cat_order ";
        List<GroupbuyCatDO> list = this.daoSupport.queryForList(sql, GroupbuyCatDO.class, parentId);
        return list;
    }

    @Override
    public GroupbuyCatDO add(GroupbuyCatDO groupbuyCat) {

        //验证团购分类名称是否重复
        String sql = "select * from es_groupbuy_cat where cat_name = ?";
        List list = this.daoSupport.queryForList(sql, groupbuyCat.getCatName());
        if (list.size() > 0) {
            throw new ServiceException(PromotionErrorCode.E408.code(), "团购分类名称重复");
        }
        if (groupbuyCat.getParentId() == null) {
            groupbuyCat.setParentId(0);
        }
        this.daoSupport.insert(groupbuyCat);
        int id = this.daoSupport.getLastId("es_groupbuy_cat");
        groupbuyCat.setCatId(id);
        return groupbuyCat;
    }

    @Override
    public GroupbuyCatDO edit(GroupbuyCatDO groupbuyCat, Integer id) {

        //验证团购分类名称是否重复
        String sql = "select * from es_groupbuy_cat where cat_name = ? and cat_id != ?";
        List list = this.daoSupport.queryForList(sql, groupbuyCat.getCatName(), id);
        if (list.size() > 0) {
            throw new ServiceException(PromotionErrorCode.E408.code(), "团购分类名称重复");
        }

        this.daoSupport.update(groupbuyCat, id);
        return groupbuyCat;
    }

    @Override
    public void delete(Integer id) {
        this.daoSupport.delete(GroupbuyCatDO.class, id);
    }

    @Override
    public GroupbuyCatDO getModel(Integer id) {
        return this.daoSupport.queryForObject(GroupbuyCatDO.class, id);
    }
}
