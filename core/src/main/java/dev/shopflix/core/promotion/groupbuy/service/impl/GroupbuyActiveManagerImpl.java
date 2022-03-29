/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.groupbuy.service.impl;

import dev.shopflix.core.promotion.PromotionErrorCode;
import dev.shopflix.core.promotion.groupbuy.model.dos.GroupbuyActiveDO;
import dev.shopflix.core.promotion.groupbuy.model.vo.GroupbuyActiveVO;
import dev.shopflix.core.promotion.groupbuy.service.GroupbuyActiveManager;
import dev.shopflix.core.promotion.tool.model.enums.PromotionTypeEnum;
import dev.shopflix.core.promotion.tool.service.impl.AbstractPromotionRuleManagerImpl;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 团购活动表业务类
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 11:52:14
 */
@Service
public class GroupbuyActiveManagerImpl extends AbstractPromotionRuleManagerImpl implements GroupbuyActiveManager {

    @Autowired
    @Qualifier("tradeDaoSupport")
    private DaoSupport daoSupport;

    @Override
    public Page list(int page, int pageSize) {
        String sql = "select * from es_groupbuy_active order by start_time desc";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, GroupbuyActiveVO.class);
        return webPage;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public GroupbuyActiveDO add(GroupbuyActiveDO groupbuyActive) {
        this.verifyTime(groupbuyActive.getStartTime(), groupbuyActive.getEndTime(), PromotionTypeEnum.GROUPBUY, null);
        this.verifyName(groupbuyActive.getActName(), false, 0);
        this.daoSupport.insert(groupbuyActive);
        int id = this.daoSupport.getLastId("es_groupbuy_active");
        groupbuyActive.setActId(id);
        return groupbuyActive;
    }

    @Override
    public GroupbuyActiveDO edit(GroupbuyActiveDO groupbuyActive, Integer id) {
        this.verifyTime(groupbuyActive.getStartTime(), groupbuyActive.getEndTime(), PromotionTypeEnum.GROUPBUY, id);

        this.verifyName(groupbuyActive.getActName(), true, id);
        this.verifyAuth(id);
        this.daoSupport.update(groupbuyActive, id);
        return groupbuyActive;
    }

    @Override
    public void delete(Integer id) {
        this.verifyAuth(id);
        this.daoSupport.delete(GroupbuyActiveDO.class, id);
        //删除参加的团购商品
        String sql = "delete from es_groupbuy_goods where act_id = ? ";
        this.daoSupport.execute(sql, id);
    }

    @Override
    public GroupbuyActiveDO getModel(Integer id) {
        return this.daoSupport.queryForObject(GroupbuyActiveDO.class, id);
    }


    @Override
    public List<GroupbuyActiveDO> getActiveList() {

        long nowTime = DateUtil.getDateline();
        String sql = "select * from es_groupbuy_active where join_end_time>=? order by add_time desc";
        return this.daoSupport.queryForList(sql, GroupbuyActiveDO.class, nowTime);
    }


    @Override
    public void verifyAuth(Integer id) {
        GroupbuyActiveDO activeDO = this.getModel(id);
        long nowTime = DateUtil.getDateline();

        //如果活动起始时间小于现在时间，活动已经开始了。
        if (activeDO.getStartTime().longValue() < nowTime && activeDO.getEndTime().longValue() > nowTime) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "活动已经开始，不能进行编辑删除操作");
        }
    }


}
