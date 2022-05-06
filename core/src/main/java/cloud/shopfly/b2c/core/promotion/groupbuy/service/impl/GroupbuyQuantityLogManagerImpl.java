/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.groupbuy.service.impl;

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyQuantityLog;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyQuantityLogManager;
import cloud.shopfly.b2c.core.promotion.tool.service.PromotionGoodsManager;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyGoodsManager;
import cloud.shopfly.b2c.core.statistics.util.DateUtil;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 团购商品库存日志表业务类
 *
 * @author xlp
 * @version v1.0
 * @since v7.0.0
 * 2018-07-09 15:32:29
 */
@Service
public class GroupbuyQuantityLogManagerImpl implements GroupbuyQuantityLogManager {

    @Autowired

    private DaoSupport daoSupport;

    @Autowired
    private PromotionGoodsManager promotionGoodsManager;

    @Autowired
    private GroupbuyGoodsManager groupbuyGoodsManager;


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<GroupbuyQuantityLog> rollbackReduce(String orderSn) {
        List<GroupbuyQuantityLog> logList = daoSupport.queryForList("select * from es_groupbuy_quantity_log where order_sn=? ", GroupbuyQuantityLog.class, orderSn);
        List<GroupbuyQuantityLog> result = new ArrayList<>();
        for (GroupbuyQuantityLog log : logList) {
            log.setQuantity(log.getQuantity());
            log.setOpTime(DateUtil.getDateline());
            log.setReason("取消订单，回滚库存");
            log.setLogId(null);
            this.add(log);
            result.add(log);

            promotionGoodsManager.cleanCache(log.getGoodsId());
        }
        return result;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public GroupbuyQuantityLog add(GroupbuyQuantityLog groupbuyQuantityLog) {
        this.daoSupport.insert(groupbuyQuantityLog);
        return groupbuyQuantityLog;
    }

}
